<template>
    <div>
        <div id="title-box">
            <!-- 根据动态路由传递参数判断 title -->
            <div v-show="type === 'admin'">管理员账号管理</div>
            <div v-show="type === 'user'">用户账号管理</div>
        </div>
        <hr class="hr" />
        <div id="content-box">
            <div>
                <div class="add-and-search-box">
                    <div class="add-button-box">
                        <button class="add-button"
                            @click="doOperation('insert', type, -1)">添加账号</button>
                    </div>
                    <div class="search-bar">
                        <!-- 实现查询实时显示 -->
                        <el-input placeholder="搜索用户名（普通用户可搜索昵称）" v-model="searchInput" 
                                class="search-input" width="100%">
                            <!-- 点击进行搜索 -->
                            <el-button slot="append" icon="el-icon-search"
                                        @click="doSearch()"></el-button>
                        </el-input>
                    </div>
                    <div class="refresh-box">
                        <img id="refresh-icon" src="@/../public/imgs/icons/refresh.png" height=70px
                                @click="load()"/>
                    </div>
                </div>
                <div>
                    <!-- 分页 -->
                    <el-pagination style="zoom: 220%; margin-bottom: 20px"
                                @size-change="handleSizeChange" 
                                @current-change="handleCurrentChange" 
                                :current-page.sync="currentPage"
                                :page-size="pageSize" 
                                layout="prev, pager, next, jumper" 
                                :total="totalNumber">
                    </el-pagination>

                    <!-- 内容列表 -->
                    <ul v-for="item in items" :key="item.id" style="list-style-type: none">
                        <div class="item-box">
                            <li>
                                <!-- 成员id、成员用户名、操作 -->
                                <div class="item-id">{{ item.id }}</div>
                                <div class="item-detail">
                                    <div class="item-detail-username">
                                        {{ item.username }}
                                    </div>

                                    <div class="item-detail-operation">
                                        <button 
                                            class="item-detail-operation-button" 
                                            style="background-color: #8beeff"
                                            @click="doOperation('updateinfo', type, item.id)">更新</button>
                                            <!-- 点击按钮，执行doOperaion(operation, contentType, objId) -->
                                            <!-- operation: 操作类型 -->
                                            <!-- contentType: 账号类型 -->
                                            <!-- objId: 账号id -->
                                        <button 
                                            class="item-detail-operation-button" 
                                            style="background-color: #f64530"
                                            @click="doOperation('delete', type, item.id)">删除</button>
                                        <button 
                                            class="item-detail-operation-button" 
                                            style="background-color: orange"
                                            @click="doOperation('updatepwd', type, item.id)">修改密码</button>
                                    </div>

                                    <!-- 修改用户状态 -->
                                    <!-- 最后一个管理员账户不能禁用 -->
                                    <div class="item-detail-status">
                                        <el-switch 
                                            id="switch"
                                            style="zoom: 220%" 
                                            active-color="#8beeff" 
                                            inactive-color="#f64530" 
                                            :active-value="1" 
                                            active-text="启用"
                                            :inactive-value="0" 
                                            inactive-text="禁用" 
                                            v-model="item.status"
                                            @change="changeStatus(item, type)">
                                        </el-switch>
                                    </div>
                                </div>
                            </li>
                        </div>
                    </ul>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import request from "@/utils/request";
import code from '@/utils/code';
import message from "@/utils/message";

export default {
    name: "dashaccount",
    // 从父页面注入 reload 依赖
    inject: ['reload'],
    data() {
        return {
            // 请求时字符串拼接
            typeStr: '',
            // 账户信息
            accountInfo: '',

            // 根据路由的不同，后端传入响应数据
            items: [],
            allItems: [],

            // for search
            searchInput: '',
            isSearching: false,

            // for pagination
            // 当前页
            currentPage: 1,
            // 每页条数
            pageSize: 10,
            // 数据总数
            totalNumber: 0,
        }
    },
    created() {
        this.load()
    },

    watch: {
        // 含输入的记得掐空格
        searchInput(val) {
            this.currentPage = 1;

            if (val.trim() === '')
            {
                this.load();
                return;
            }
            this.isSearching = true;
            // 延迟 0.2s 进行实时显示
            setTimeout(() => {
                request.get("/" + this.typeStr + "/" + val.trim() + 
                        "/" + this.currentPage + "/" + this.pageSize).then(res => {
                        if (res.code === code.GET_OK && res.data.total)
                        {
                            this.items = res.data.list;
                            this.totalNumber = res.data.total;
                        }
                        else 
                        {
                            this.items = [];
                            this.totalNumber = 0;
                            // this.$notify.error({
                            //     title: message.FIND_ERR,
                            // })
                        }
                    }).catch(err => {
                        this.$notify.error({
                            title: message.REQUEST_ERR,
                            offset: code.OFFSET
                        })
                    })
            }, 200);
        }
    },

    methods:
    {
        clearSearch() {
            this.searchInput = '';
            this.isSearching = false;
        },

        load() {
            // 清除搜索
            this.clearSearch();
            // 获取当前登录账号信息
            this.accountInfo = this.$storage.get('accountInfo');
            // 拼接字符串，减少代码重复
            this.setTypeStr();
            // 分页查询 getAll
            this.setItems();
            // 不分页查询所有
            this.setAllItems();
        },
        // 拼接字符串，减少代码重复
        setTypeStr()
        {
            if (this.type === 'admin') {
                this.typeStr = 'admins';
            }
            else if (this.type === 'user') {
                this.typeStr = 'users';
            }
        },
        // 分页查询 getAll
        setItems()
        {
            request.get("/" + this.typeStr + "/" + this.currentPage + "/" + this.pageSize).then(res => {
                if (res.code === code.GET_OK) {
                    this.items = res.data.list;
                    this.totalNumber = res.data.total;
                    // console.log("total number: " + this.totalNumber);
                }
            }).catch(err => {
                console.log(err)
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                });
            })
        },
        // 不分页查询 getAll
        setAllItems() {
            request.get("/" + this.typeStr).then(res => {
                if (res.code === code.GET_OK)
                {
                    this.allItems = res.data;
                    // console.log("all items: " + this.allItems);
                }
            }).catch(err => {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            })
        },

        /**
         * 执行操作
         * @param {*} operation: 操作类型
         * @param {*} contentType: 账号类型
         * @param {*} objId: 账号id
         */
        doOperation(operation, contentType, objId) {
            // 修改vuex中的state数据
            this.$store.commit('setOperation', operation);
            this.$store.commit('setContentType', contentType);
            this.$store.commit('setObjId', objId);
            // 向父路由emit一个show-dialog事件
            this.$emit('show-dialog', 'account')
        },
        
        // 修改启用禁用状态
        changeStatus(obj, type) {
            // 修改前先查询全部
            this.setAllItems();
            // 拼接字符串，减少代码重复
            if (type === 'admin')
            {
                let canChange = false;
                // 管理员账号关闭前检查是否还有其他启用账号，若无，不能禁用
                // 这里得是所有账号，而不是分页账号
                for (let i = 0; i < this.allItems.length; i ++)
                {
                    // 存在账户为启用且不是正在修改状态的自己
                    if ((this.allItems[i].status === 1) && (this.allItems[i].id !== obj.id))
                    {
                        // 可以修改，退出循环
                        canChange = true;
                        break;
                    }
                }
                // 否则，不能修改
                // 修改回启用状态
                // 刷新
                if (canChange === false) {
                    this.reload();

                    this.$notify.error({
                        title: '无法禁用最后一个启用账号',
                        offset: code.OFFSET
                    })
                    return;
                }
            }
            // 可以修改
            // 更新
            request.put("/" + this.typeStr, obj).then(res => {
                    if (res.code === code.UPDATE_OK)
                    {
                        this.$notify.success({
                            title: message.UPDATE_OK,
                            offset: code.OFFSET
                        });
                    }
                }).catch(err => {
                    this.$notify.error({
                        title: message.REQUEST_ERR,
                        offset: code.OFFSET
                    });
                }) 
                // 修改后再更新全部
                this.setAllItems();
        },

        // pagination
        // 页面条数修改时（没有设置这个功能）
        handleSizeChange(val) {
            // console.log(`每页 ${val} 条`);
        },
        async handleCurrentChange(val) {
            let res;
            if (!this.isSearching)
            {
                res = await request.get("/" + this.typeStr + "/" + this.currentPage + "/" + this.pageSize);
            }
            else
            {
                res = await request.get("/" + this.typeStr + "/" + this.searchInput.trim() + 
                                    "/" + this.currentPage + "/" + this.pageSize);
            }
            if (res.code === code.GET_OK) {
                this.items = res.data.list;
                this.totalNumber = res.data.total;
                // console.log("total number: " + this.totalNumber);
            }
            else 
            {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            }
            // console.log(`当前页: ${val}`);
        },

        // search
        doSearch() {
            // 若输入为空，刷新、退出
            if (this.searchInput.trim() === '')
            {
                this.load();
                return;
            }
            // 搜索状态
            this.isSearching = true;
            request.get("/" + this.typeStr + "/" + this.searchInput.trim() + 
                        "/" + this.currentPage + "/" + this.pageSize).then(res => {
                        if (res.code === code.GET_OK && res.data.total)
                        {
                            this.items = res.data.list;
                            this.totalNumber = res.data.total;
                            this.$notify.success({
                                title: message.FIND_OK + "，共 " + this.totalNumber + " 条",
                                offset: code.OFFSET
                            })
                        }
                        else 
                        {
                            this.items = [];
                            this.totalNumber = 0;
                            this.$notify.error({
                                title: message.FIND_ERR,
                                offset: code.OFFSET
                            })
                        }
                    }).catch(err => {
                        this.$notify.error({
                            title: message.REQUEST_ERR,
                            offset: code.OFFSET
                        })
                    })
        },
    },
    props: ['type']
}
</script>

<style scoped src="@/../public/css/dashaccount-style.css">
</style>
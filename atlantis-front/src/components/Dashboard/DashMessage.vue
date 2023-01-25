<template>
    <div>
        <div id="title-box">
            <div>
                <!-- 根据动态路由传递参数判断title -->
                <div v-show="type === 'apply'">管理员申请</div>
                <!-- <div v-show="type === 'reset'">重置密码申请</div> -->
            </div>
        </div>
        <hr class="hr" />
        <div id="content-box">
            <div>
                <div class="refuse-and-search-box">
                    <div class="refuse-button-box">
                        <button class="refuse-button" 
                        @click="doOperation('refuse', type, -1)">整页拒绝</button>
                    </div>
                    <div class="search-bar">
                        <!-- 实现查询实时显示 -->
                        <el-input placeholder="搜索用户名" 
                            v-model="searchInput" class="search-input"
                            width="100%">
                            <!-- 点击进行搜索 -->
                            <el-button slot="append" icon="el-icon-search" @click="doSearch()"></el-button>
                        </el-input>
                    </div>
                    <div class="refresh-box">
                        <img id="refresh-icon" src="@/../public/imgs/icons/refresh.png" height=70px 
                            @click="load()" />
                    </div>
                </div>
                <div>
                    <!-- pagination -->
                    <el-pagination style="zoom: 220%;
                                margin-bottom: 20px"  
                                @size-change="handleSizeChange"
                                @current-change="handleCurrentChange" 
                                :current-page.sync="currentPage" 
                                :page-size="pageSize"
                                layout="prev, pager, next, jumper" 
                                :total="totalNumber">
                    </el-pagination>

                    <div v-if="isShowFlag">
                        <img src="@/../public/imgs/commons/ae86.png" height="150px"
                            style="filter: drop-shadow(0px 5px 15px #eb1d2a);"/>
                        <p style="color: rgb(200, 200, 200);zoom:2">这里什么也没有~</p>
                    </div>

                    <ul v-for="item in items" :key="item.id" style="list-style-type: none">
                        <div class="item-box">
                            <li>
                                <!-- 修改 -->  
                                <div class="item-id">{{ item.id }}</div>                   
                                <div class="item-detail">
                                    <div class="item-detail-username">
                                        {{ item.username }}
                                    </div>
                                    <div class="item-detail-operation">
                                        <button 
                                            class="item-detail-operation-button" 
                                            style="background-color: #eb1d2a"
                                            @click="doOperation('agree', type, item.id)">同意</button>
                                        <button 
                                            class="item-detail-operation-button" 
                                            style="background-color: #f64530"
                                            @click="doOperation('refuse', type, item.id)">拒绝</button>
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
import request from '@/utils/request';
import code from '@/utils/code';
import message from '@/utils/message';

export default {
    name: "dashmessage",
    inject: ['reload'],
    data() {
        return {
            // for search
            searchInput: '',
            isSearching: false,

            // 消息数据
            items: [],

            isShowFlag: false,

            // for pagination
            currentPage: 1,
            pageSize: 10,
            totalNumber: 0,

            typeStr: '',
        }
    },
    methods: {
        setTypeStr() {
            // 拼接字符串
            if (this.type === 'apply')
            {
                this.typeStr = 'applies';
            }
            else if (this.type === 'reset')
            {
                this.typeStr = 'resets';
            }
        },
        setIsShowFlag(val) {
            this.isShowFlag = val;
        },
        // 分页查询
        async setItems() {
            try {
                let res = await  
                    request.get("/" + this.typeStr + "/" + this.currentPage + "/" + this.pageSize);
                if (res.code === code.GET_OK)
                {
                    this.items = res.data.list;
                    this.totalNumber = res.data.total;
                    this.setIsShowFlag(false);
                }
                else if (res.code === code.GET_ERR)
                {
                    this.items = [];
                    this.totalNumber = 0;
                    this.setIsShowFlag(true);
                }
            } catch (err) {
                this.setIsShowFlag(true);
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            }
        },
        clearSearch() {
            this.searchInput = '';
            this.isSearching = false;
        },

        load() {
            this.setTypeStr();
            this.setItems();
            this.clearSearch();
        },

        doOperation(operation, type, objId) {
            // 单个拒绝
            if (operation === 'refuse' && objId !== -1)
            {
                // 删除申请消息
                request.delete('/' + this.typeStr + '/' + objId).then(res => {
                    if (res.code === code.DELETE_OK) 
                    {
                        this.$notify.success({
                            title: message.DELETE_OK,
                            offset: code.OFFSET
                        })
                        // 延迟 300ms 刷新子路由
                        setTimeout(() => {
                            this.reload(); // 执行方法
                        }, 300);
                    }
                }).catch(err => {
                    this.$notify.error({
                        title: message.REQUEST_ERR,
                        offset: code.OFFSET
                    })
                })
            }
            // 整页拒绝
            else if (operation === 'refuse' && objId === -1)
            {
                let hasError = false;
                for (let i = 0; i < this.items.length; i++) {
                    // 删除申请消息
                    request.delete('/' + this.typeStr + '/' + this.items[i].id).then(res => {
                        // 至少有一个存在删除错误，循环跳出
                        if (res.code !== code.DELETE_OK) {
                            hasError = true;
                        }
                    }).catch(err => {
                        hasError = true;
                    })
                }
                // 循环跳出，存在错误
                if (hasError)
                {
                    this.$notify.error({
                        title: message.REQUEST_ERR,
                        offset: code.OFFSET
                    })
                }
                else
                {
                    this.$notify.success({
                        title: message.DELETE_OK,
                        offset: code.OFFSET
                    })
                }
                // 延迟 300ms 刷新子路由
                setTimeout(() => {
                            this.reload(); // 执行方法
                        }, 300);
            }
            // 同意
            else if (operation === 'agree')
            {
                request.get("/" + this.typeStr + "/agree/" + objId).then(res => {
                    if (res.code === code.GET_OK)
                    {
                        // 同意成功
                        // 进行apply删除
                        request.delete("/applies/" + objId).then(res => {
                            if (res.code === code.DELETE_OK) {
                                this.$notify.success({
                                    title: "已同意",
                                    offset: code.OFFSET
                                })
                                // 延迟 300ms 刷新子路由
                                setTimeout(() => {
                                    this.reload();
                                }, 300);
                            }
                        }).catch(err => {
                            this.$notify.error({
                            title: message.REQUEST_ERR,
                            offset: code.OFFSET
                            })
                        })
                    }
                    else
                    {
                        this.$notify.error({
                            title: "这个申请存在问题，请点击拒绝以进行删除",
                            offset: code.OFFSET
                        })
                    }
                }).catch(err => {
                    this.$notify.error({
                            title: message.REQUEST_ERR,
                            offset: code.OFFSET
                    })
                })
            }
        },

        async doSearch() {
            // 若输入为空，刷新、退出
            if (this.searchInput.trim() === '')
            {
                this.load();
                return;
            }
            this.isSearching = true;
            try {
                let res = await request.get("/" + this.typeStr + "/" + this.searchInput.trim() + 
                        "/" + this.currentPage + "/" + this.pageSize);
                if (res.code === code.GET_OK)
                {
                    this.items = res.data.list;
                    this.totalNumber = res.data.total;
                    this.setIsShowFlag(false);
                    this.$notify.success({
                        title: message.FIND_OK + "，共 " + this.totalNumber + " 条",
                        offset: code.OFFSET
                    })
                }
                else if (res.code === code.GET_ERR)
                {
                    this.items = [];
                    this.totalNumber = 0;
                    this.setIsShowFlag(true);
                    this.$notify.error({
                        title: message.FIND_ERR,
                        offset: code.OFFSET
                    })
                }
            } catch (err) {
                this.setIsShowFlag(true);
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            }
        },

        handleSizeChange(val) {
            // console.log("val: " + val);
        },
        async handleCurrentChange(val) {
            try {
                let res;
                if (!this.isSearching) {
                    res = await request.get("/" + this.typeStr + "/" + this.currentPage + "/" + this.pageSize);
                }
                else {
                    res = await request.get("/" + this.typeStr + "/" + this.searchInput.trim() +
                        "/" + this.currentPage + "/" + this.pageSize);
                }
                if (res.code === code.GET_OK) {
                    this.items = res.data.list;
                    this.totalNumber = res.data.total;
                    this.setIsShowFlag(false);
                    // console.log("total number: " + this.totalNumber);
                }
                else if (res.code === code.GET_ERR) {
                    this.items = [];
                    this.totalNumber = 0;
                    this.setIsShowFlag(true);
                }
            }
            catch (err) {
                this.setIsShowFlag(true);
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            }
            // console.log(`当前页: ${val}`);
        },
    },
    created() {
        this.load();
    },
    props: ['type'],

    watch: {
        // 含输入的记得掐空格
        async searchInput(val) {
            this.currentPage = 1;

            if (val.trim() === '')
            {
                this.load();
                return;
            }
            this.isSearching = true;
            // 延迟 0.2s 进行实时显示
            setTimeout(async () => {
                try {
                    let res = await request.get("/" + this.typeStr + "/" + val.trim() +
                        "/" + this.currentPage + "/" + this.pageSize)
                    if (res.code === code.GET_OK) {
                        this.items = res.data.list;
                        this.totalNumber = res.data.total;
                        this.setIsShowFlag(false);
                    }
                    else if (res.code === code.GET_ERR) {
                        this.items = [];
                        this.totalNumber = 0;
                        this.setIsShowFlag(true);
                    }
                }
                catch (err) {
                    this.setIsShowFlag(true);
                    this.$notify.error({
                        title: message.REQUEST_ERR,
                        offset: code.OFFSET
                    })
                }
            }, 200);
        }
    },
}
</script>

<style scoped src="@/../public/css/dashmessage-style.css">
</style>
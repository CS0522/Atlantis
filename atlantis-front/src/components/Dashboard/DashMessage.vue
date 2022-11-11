<template>
    <div>
        <div id="title-box">
            <div>
                <!-- 根据动态路由传递参数判断title并渲染 -->
                <div v-show="type === 'apply'">管理员申请</div>
                <div v-show="type === 'reset'">重置密码申请</div>
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
                                            style="background-color: #8beeff"
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

            items: [],

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
        // 分页查询
        setItem() {
            request.get("/" + this.typeStr + "/" + this.currentPage + "/" + this.pageSize).then(res => {
                if (res.code === code.GET_OK)
                {
                    this.items = res.data.list;
                    this.totalNumber = res.data.total;
                }
            }).catch(error => {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: 150
                })
            })
        },

        load() {
            this.setTypeStr();
            this.setItem();
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
                            offset: 150
                        })
                        // 延迟 300ms 刷新子路由
                        setTimeout(() => {
                            this.reload(); // 执行方法
                        }, 300);
                    }
                }).catch(err => {
                    this.$notify.error({
                        title: message.REQUEST_ERR,
                        offset: 150
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
                        if (res.code !== code.DELETE_OK) {
                            hasError = true;
                        }
                    }).catch(err => {
                        hasError = true;
                    })
                }
                // 循环跳出:
                if (hasError)
                {
                    this.$notify.error({
                        title: message.REQUEST_ERR,
                        offset: 150
                    })
                }
                else
                {
                    this.$notify.success({
                        title: message.DELETE_OK,
                        offset: 150
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
                // 加入 admin 表，密码、名字保持不变，返回id主键
                // id 添加到 user_admin 表中
                // 发送到用户的消息中心进行提示
                // 登录到用户账号时，先检测是否同时是管理员：是，将申请按钮不显示，替代为文字
                // 后台进行处理
                request.get("/" + this.typeStr + "/agree/" + objId).then(res => {
                    if (res.code === code.GET_OK)
                    {
                        // 同意成功
                        // 进行apply删除
                        request.delete("/applies/" + objId).then(res => {
                            if (res.code === code.DELETE_OK) {
                                this.$notify.success({
                                    title: "已同意",
                                    offset: 150
                                })
                                // 延迟 300ms 刷新子路由
                                setTimeout(() => {
                                    this.reload(); // 执行方法
                                }, 300);
                            }
                        }).catch(err => {
                            this.$notify.error({
                            title: message.REQUEST_ERR,
                            offset: 150
                            })
                        })
                    }
                    else
                    {
                        this.$notify.error({
                            title: "这个申请存在问题，请点击拒绝以进行删除",
                            offset: 150
                        })
                    }
                }).catch(err => {
                    this.$notify.error({
                            title: message.REQUEST_ERR,
                            offset: 150
                    })
                })
            }
        },

        doSearch() {
            // 若输入为空，刷新、退出
            if (this.searchInput.trim() === '')
            {
                this.load();
                return;
            }
            request.get("/" + this.typeStr + "/" + this.searchInput.trim() + 
                        "/" + this.currentPage + "/" + this.pageSize).then(res => {
                        if (res.code === code.GET_OK && res.data.total)
                        {
                            this.items = res.data.list;
                            this.totalNumber = res.data.total;
                            this.$notify.success({
                                title: message.FIND_OK + "，共 " + this.totalNumber + " 条",
                                offset: 150
                            })
                        }
                        else 
                        {
                            this.items = [];
                            this.totalNumber = 0;
                            this.$notify.error({
                                title: message.FIND_ERR,
                                offset: 150
                            })
                        }
                    }).catch(err => {
                        this.$notify.error({
                            title: message.REQUEST_ERR,
                            offset: 150
                        })
                    })
        },

        handleSizeChange(val) {
            console.log("val: " + val);
        },
        handleCurrentChange(val) {
            console.log("val: " + val);
            request.get("/" + this.typeStr + "/" + this.currentPage + "/" + this.pageSize).then(res => {
                if (res.code === code.GET_OK) {
                    this.items = res.data.list;
                    this.totalNumber = res.data.total;

                    console.log("total number: " + this.totalNumber);
                }
            }).catch(err => {
                console.log(err)
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: 150
                });
            })
            // console.log(`当前页: ${val}`);
        },
    },
    created() {
        this.load();
    },
    props: ['type'],

    watch: {
        // 含输入的记得掐空格！！！
        searchInput(val) {
            if (val.trim() === '')
            {
                this.load();
                return;
            }
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
                            offset: 150
                        })
                    })
            }, 200);
        }
    },
}
</script>

<style scoped src="@/../public/css/dashmessage-style.css">
</style>
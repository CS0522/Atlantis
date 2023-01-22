<template>
    <div>
        <div id="title-box">
            <div>论坛 - 评论管理</div>
        </div>
        <hr class="hr" />
        <div id="content-box">
            <div>
                <div class="add-and-search-box">
                    <div class="add-button-box">
                        <button class="add-button" 
                        @click="function() {
                            $router.push('/dashboard/forum/thread')
                        }">帖子管理</button> 
                    </div>
                    <div class="search-bar">
                        <!-- 实现查询实时显示 -->
                        <el-input placeholder="搜索评论用户、内容或帖子标题" 
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
                                margin-bottom: 20px;" 
                                @size-change="handleSizeChange"
                                @current-change="handleCurrentChange" 
                                :current-page.sync="currentPage"
                                :page-size="pageSize"
                                layout="prev, pager, next, jumper"  
                                :total="totalNumber">
                    </el-pagination>
                    <div class="comment-item" v-for="item in items" :key="item.index">
                        <div class="comment-item-detail">
                            <div class="comment-item-info">
                                <!-- <div class="comment-item-index">
                                    {{ item.index }}
                                </div> -->
                                <div class="comment-item-name">
                                    {{ item.name }}
                                </div>

                                <div class="comment-item-operation">
                                    <button class="delete-button"
                                        @click="doOperation('delete', item.index)">删除</button>
                                </div>

                                <div class="comment-item-date">
                                    {{ item.createDate }}
                                </div>

                                <div class="comment-item-title">
                                    《{{ item.title }}》
                                </div>
                            </div>
                            <div class="comment-item-content">
                                {{ item.content }}
                            </div>
                        </div>
                    </div>
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
    name: "dashforumcomment",
    inject: ['reload'],
    data() {
        return {
            // for search
            searchInput: '',
            isSearching: false,

            // 消息数据
            items: [],

            // for pagination
            currentPage: 1,
            pageSize: 10,
            totalNumber: 0,

        }
    },
    methods: {
        // 分页查询
        setItems() {
            // get all
            request.get("/comments/" + this.currentPage + "/" + this.pageSize).then(res => {
                if (res.code === code.GET_OK)
                {
                    this.items = res.data.list;
                    this.totalNumber = res.data.total;
                }
                else 
                {
                    this.$notify.error({
                        title: message.REQUEST_ERR,
                        offset: code.OFFSET
                    })
                }
            })
        },
        clearSearch() {
            this.searchInput = '';
            this.isSearching = false;
        },

        load() {
            this.setItems();
            this.clearSearch();
        },

        doOperation(operation, objIndex) {
            // delete
            // 向vuex中保存数据
            this.$store.commit('setOperation', operation);
            this.$store.commit('setObjId', objIndex);
            // 向父路由emit一个show-dialog事件
            this.$emit('show-dialog', 'comment');
        },

        doSearch() {
            // 若输入为空，刷新、退出
            if (this.searchInput.trim() === '')
            {
                this.load();
                return;
            }
            this.isSearching = true;
            request.get("/comments/search/" + this.searchInput.trim() + "/" + 
                            this.currentPage + "/" + this.pageSize).then(res => {
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

        handleSizeChange(val) {
            // console.log("val: " + val);
        },
        async handleCurrentChange(val) {
            // console.log("val: " + val);
            let res;
            if (!this.isSearching)
            {
                res = await request.get("/comments/" + this.currentPage + "/" + this.pageSize);
            }
            else
            {
                res = await request.get("/comments/search/" + this.searchInput + "/" + 
                                        this.currentPage + "/" + this.pageSize);
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
                });
            }
            // console.log(`当前页: ${val}`);
        },
    },
    created() {
        this.load();
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
                request.get("/comments/search/" + val.trim() + 
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
}
</script>

<style scoped src="@/../public/css/dashforumcomment-style.css">
</style>
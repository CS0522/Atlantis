<template>
    <div>
        <div id="title-box">
            <div>论坛 - 话题管理</div>
        </div>
        <hr class="hr" />
        <div id="content-box">
            <div>
                <div class="add-and-search-box">
                    <div class="add-button-box">
                        <button class="add-button" 
                        @click="doOperation('add', -1)">添加话题</button> 
                    </div>
                    <div class="search-bar">
                        <!-- 实现查询实时显示 -->
                        <el-input placeholder="搜索话题" 
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
                    <el-pagination style="zoom: 220%; margin-bottom: 20px;" 
                                @size-change="handleSizeChange"
                                @current-change="handleCurrentChange" 
                                :current-page.sync="currentPage" 
                                :page-size="pageSize"
                                layout="prev, pager, next, jumper"  
                                :total="totalNumber">
                    </el-pagination>
                    <!-- 数据还未加载完成时 -->
                    <div v-if="isShowFlag">
                        <img src="@/../public/imgs/commons/ae86.png" height="150px"
                            style="filter: drop-shadow(0px 5px 15px #eb1d2a);"/>
                        <p style="color: rgb(200, 200, 200);zoom:2">这里什么也没有~</p>
                    </div>

                    <ul v-for="item in items" :key="item.id" style="list-style-type: none">
                        <div class="topic-item-box">
                            <li>
                                <!-- 修改 -->  
                                <!-- <div class="topic-item-index">{{ item.index }}</div> -->
                                <div class="topic-item-detail">
                                    <div class="topic-item-detail-type">
                                        {{ item.type }}
                                    </div>
                                    <div class="topic-item-detail-count">
                                        帖子数量：{{ item.count }}
                                    </div>
                                    <!-- <div class="topic-item-detail-count" 
                                            v-if="!counts.get(item.index)">
                                        帖子数量：0
                                    </div> -->
                                    <div class="topic-item-detail-operation">
                                        <button 
                                            class="topic-item-detail-operation-button" 
                                            style="background-color: #eb1d2a"
                                            @click="doOperation('edit', item.index)">编辑</button>
                                        <button 
                                            class="topic-item-detail-operation-button" 
                                            style="background-color: #f64530"
                                            @click="doOperation('delete', item.index)">删除</button>
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
    name: "dashforumtopic",
    inject: ['reload'],
    data() {
        return {
            // for search
            searchInput: '',
            isSearching: false,

            // 数据
            items: [],
            isShowFlag: false,

            // for pagination
            currentPage: 1,
            pageSize: 9,
            totalNumber: 0,
        }
    },

    methods: {
        setIsShowFlag(val) {
            this.isShowFlag = val;
        },
        // 分页查询
        async setItems() {
            try
            {
                let res = await request.get("/topics/count/" + this.currentPage + "/" + this.pageSize);
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
            }
            catch (error) {
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
            this.setItems();
            this.clearSearch();
        },

        doOperation(operation, objIndex) {
            // 修改vuex中的state数据
            this.$store.commit('setOperation', operation);
            this.$store.commit('setObjId', objIndex);
            // 向父路由emit一个show-dialog事件
            this.$emit('show-dialog', 'topic');
        },

        async doSearch() {
            // 若输入为空，刷新、退出
            if (this.searchInput.trim() === '')
            {
                this.load();
                return;
            }
            this.isSearching = true;
            try
            {
                let res = await request.get("/topics/search/" + this.searchInput.trim() + "/" + 
                            this.currentPage + "/" + this.pageSize)
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
            }
            catch (err) {
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
            // console.log("val: " + val);
            try {
                let res;
                if (!this.isSearching)
                {
                    res = await request.get("/topics/count/" + this.currentPage + "/" + this.pageSize);
                }
                else
                {
                    res = await request.get("/topics/search/" + this.searchInput.trim() + "/" + 
                                            this.currentPage + "/" + this.pageSize);
                }
                if (res.code === code.GET_OK) {
                    this.items = res.data.list;
                    this.totalNumber = res.data.total;
                    this.setIsShowFlag(false);
                    // console.log("total number: " + this.totalNumber);
                }
                else if (res.code === code.GET_ERR)
                {
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
                    let res = await request.get("/topics/search/" + val.trim() + "/" +
                        this.currentPage + "/" + this.pageSize)
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

<style scoped src="@/../public/css/dashforumtopic-style.css">
</style>
<template>
    <div>
        <div id="content-box">
            <div class="search-box">
                <!-- 单纯对齐用 -->
                <div class="add-button-box">
                    <button style="cursor:auto; opacity: 0"></button>
                </div>
                <div class="search-bar">
                    <!-- 实现查询实时显示 -->
                    <el-input placeholder="搜索标题或内容" 
                        v-model="searchInput" class="search-input" width="100%">
                        <!-- 点击进行搜索 -->
                        <el-button slot="append" icon="el-icon-search" @click="doSearch()"></el-button>
                    </el-input>
                </div>
                <div class="refresh-box">
                    <img id="refresh-icon" 
                        src="@/../public/imgs/icons/refresh.png" height=70px @click="load()" />
                </div>
            </div>
            <!-- 分页 -->
            <el-pagination style="zoom: 220%; margin-bottom: 20px"
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
                <p style="color: rgb(200, 200, 200);">这里什么也没有~</p>
            </div>

            <div>
                <ul v-for="item in items" :key="item.id" style="list-style-type: none">
                    <div class="item-box">
                        <li>
                            <!-- 显示标题、日期 -->
                            <div class="item-detail" @click="goToItemDetail(item.id)">
                                <!-- <div class="item-detail-thumbnail">
                                </div> -->
                                <div class="item-detail-title">
                                    {{ item.title }}
                                </div>
                                <div class="item-detail-date">
                                    {{ item.createDate }}
                                </div>
                                <div class="item-detail-view">
                                    浏览: {{ item.view }}
                                </div>
                            </div>
                        </li>
                    </div>
                </ul>
            </div>
        </div>
    </div>
</template>

<script>
import code from '@/utils/code';
import message from '@/utils/message';
import request from '@/utils/request';

export default {
    name: "subcontent",
    data() {
        return {
            // 当前页
            currentPage: 1,
            // 每页条数
            pageSize: 10,
            // 数据总数
            totalNumber: 0,

            // for search
            searchInput: '',
            isSearching: false,

            // 资讯的类别
            typeIndex: '',

            // 根据路由的不同，后端传入数据
            items: [],

            isShowFlag: false,
        }
    },
    created() {
        this.load()
    },

    methods: {
        load() {
            this.setTypeIndex();
            this.setItems();
            this.clearSearch();
        },

        setIsShowFlag(val) {
            this.isShowFlag = val;
        },

        clearSearch() {
            this.searchInput = '';
            this.isSearching = false;
        },
        setTypeIndex() {
            // 1 == news, 2 == announces
            if (this.type === 'news')
            {
                this.typeIndex = '1';
            }
            else if (this.type === 'announces')
            {
                this.typeIndex = '2';
            }
        },
        async setItems() {
            try {
                let res = await request.get("/newsArticles/" + this.typeIndex +
                    "/" + this.currentPage + "/" + this.pageSize + "/create_date desc");
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
                let res = await request.get("/newsArticles/search/" + this.searchInput.trim() +
                    "/" + this.currentPage + "/" + this.pageSize);
                if (res.code === code.GET_OK) {
                    this.items = res.data.list;
                    this.totalNumber = res.data.total;
                    this.setIsShowFlag(false);
                    this.$notify.success({
                        title: message.FIND_OK + "，共 " + this.totalNumber + " 条",
                        offset: code.OFFSET
                    })
                }
                else if (res.code === code.GET_ERR) {
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

        // 点击跳转到 id = objId 的新闻详情
        goToItemDetail(objId) {
            // 带查询参数
            this.$router.push({
                path: "/page/news/" + this.type + "/detail",
                query: {
                    id: objId
                }
            })
        },

        handleSizeChange(val) {
            // console.log(`每页 ${val} 条`);
        },
        async handleCurrentChange(val) {
            try {
                let res;
                if (!this.isSearching) {
                    res = await request.get("/newsArticles/" + this.typeIndex + "/" +
                        this.currentPage + "/" + this.pageSize + "/create_date desc");
                }
                else {
                    res = await request.get("/newsArticles/search/" + this.searchInput.trim() +
                        "/" + this.currentPage + "/" + this.pageSize);
                }
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
            } catch (err) {
                this.setIsShowFlag(true);
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            }
        },
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
                    let res = await request.get("/newsArticles/search/" + val.trim() +
                        "/" + this.currentPage + "/" + this.pageSize);
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
    props: ['type']
}
</script>

<style scoped src="@/../public/css/subcontent-style.css">
</style>
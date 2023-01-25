<template>
    <div>
        <div id="title-box">
            <div>资讯管理</div>
        </div>
        <hr class="hr"/>
        <div id="content-box">
            <div>
                <div class="add-and-search-box">
                    <div class="add-button-box">
                        <button class="add-button"
                            @click="doOperation('add', -1)">添加资讯</button>
                    </div>
                    <div class="search-bar">
                        <!-- 实现查询实时显示 -->
                        <el-input placeholder="搜索标题或内容" v-model="searchInput" 
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
                    <!-- 选择全部显示、分类显示 -->
                    <div class="select-box">
                        <select name="selectType" class="selectType" v-model="typeIndex">
                            <option value="" selected>全部</option>
                            <option value="/1">游戏新闻</option>
                            <option value="/2">游戏公告</option>
                        </select>
                        <!-- 选择排序方式 -->
                        <select name="selectType" class="selectType" v-model="order">
                            <option value="id asc" selected>按id递增顺序</option>
                            <option value="create_date desc">从新到旧顺序</option>
                        </select>
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
                        <p style="color: rgb(200, 200, 200);zoom:2">这里什么也没有~</p>
                    </div>

                    <ul v-for="item in items" :key="item.id" style="list-style-type: none">
                        <div class="item-box">
                            <li>
                                <!-- 显示文章id、标题、操作 -->
                                <div class="item-id">{{ item.id }}</div>
                                <div class="item-detail">
                                    <div class="item-detail-title">
                                        {{ item.title }}
                                    </div>

                                    <div class="item-detail-operation">
                                        <button 
                                            class="item-detail-operation-button" 
                                            style="background-color: #eb1d2a"
                                            @click="doOperation('edit', item.id)">编辑</button>
                                            <!-- operation: 操作类型 -->
                                            <!-- objId: 文章id -->
                                        <button 
                                            class="item-detail-operation-button" 
                                            style="background-color: #f64530"
                                            @click="doOperation('delete', item.id)">删除</button>
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
import code from '@/utils/code';
import message from '@/utils/message';
import request from '@/utils/request';

export default {
    name: "dashnews",
    // 刷新路由操作，注入reload依赖
    inject: ['reload'],
    data() {
        return {
            // search
            searchInput: '',
            isSearching: false,

            // pagination
            // 当前页
            currentPage: 1,
            // 每页条数
            pageSize: 10,
            // 数据总数
            totalNumber: 0,

            items: [],

            isShowFlag: false,

            // 发送请求时的拼接字符串
            typeIndex: "",
            // 排序方式
            order: "id asc",
        }
    },
    methods: {
        load() {
            this.setItem();
            this.clearSearch();
        },

        setIsShowFlag(val) {
            this.isShowFlag = val;
        },

        async setItem() {
            try {
                let res = await request.get("/newsArticles" + this.typeIndex +
                    "/" + this.currentPage + "/" + this.pageSize + "/" + this.order);
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
        clearSearch() {
            this.searchInput = '';
            this.isSearching = false;
        },

        doOperation(operation, objId)
        {
            // delete
            if (operation === 'delete')
            {
                // 向vuex中保存数据
                this.$store.commit('setOperation', operation);
                this.$store.commit('setObjId', objId);
                // 向父路由emit一个show-dialog事件
                this.$emit('show-dialog', 'news');
            }
            else
            {
                // push到editor界面
                this.$router.push("editor/news/" + objId);
            }
        },

        // search
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
                let res = await request.get("/newsArticles/search/" + this.searchInput.trim() +
                    "/" + this.currentPage + "/" + this.pageSize)
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

        // pagination
        // 页面条数修改时（没有设置这个功能）
        handleSizeChange(val) {
            // console.log(`每页 ${val} 条`);
        },
        async handleCurrentChange(val) {
            // console.log("val: " + val);
            try {
                let res;
                if (!this.isSearching) {
                    res = await request.get("/newsArticles" + this.typeIndex +
                        "/" + this.currentPage + "/" + this.pageSize + "/" + this.order);
                }
                else {
                    res = await request.get("/newsArticles/search/" + this.searchInput.trim() +
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
            } catch (err) {
                this.setIsShowFlag(true);
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            }
        },
    },

    created() {
        this.load();
    },

    watch: {
        async typeIndex(val) {
            try {
                let res = await request.get("/newsArticles" + val +
                    "/" + this.currentPage + "/" + this.pageSize + "/" + this.order);
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
        
        async order(val) {
            try {
                let res = await request.get("/newsArticles" + this.typeIndex +
                    "/" + this.currentPage + "/" + this.pageSize + "/" + val);
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

        // 含输入的记得掐空格
        async searchInput(val) {
            this.currentPage = 1;

            if (val.trim() === '')
            {
                // 使选择标签可选
                document.getElementsByClassName("selectType")[0].removeAttribute("disabled");
                document.getElementsByClassName("selectType")[1].removeAttribute("disabled");
                this.load();
                return;
            }
            // 使选择标签不可选
            document.getElementsByClassName("selectType")[0].setAttribute("disabled", "disabled");
            document.getElementsByClassName("selectType")[1].setAttribute("disabled", "disabled");
            // 正在搜索状态
            this.isSearching = true;
            // 延迟 0.2s 进行实时显示
            setTimeout(async () => {
                try {
                    let res = await request.get("/newsArticles/search/" + this.searchInput.trim() +
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
                } catch (err) {
                    this.setIsShowFlag(true);
                    this.$notify.error({
                        title: message.REQUEST_ERR,
                        offset: code.OFFSET
                    })
                }
            }, 200);
        }
    }
}
</script>

<style scoped src="@/../public/css/dashnews-style.css">
</style>
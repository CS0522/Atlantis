<template>
    <div>
        <div id="title-box">
            <div>论坛 - 帖子管理</div>
        </div>
        <hr class="hr" />
        <div id="content-box">
            <div>
                <div class="add-and-search-box">
                    <div class="add-button-box">
                        <button class="add-button"
                        @click="function() {
                            $router.push('/dashboard/forum/topic')
                        }">话题管理</button> 
                    </div>
                    <div class="search-bar">
                        <!-- 实现查询实时显示 -->
                        <el-input placeholder="搜索作者或帖子" 
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
                    <!-- 选择全部显示、分类显示 -->
                    <div class="select-box">
                        <select name="selectType" class="selectType" v-model="typeIndex">
                            <option value="" selected>全部</option>
                            <option 
                                v-for="topicItem in topicItems" 
                                :key="topicItem.index"
                                :value="topicItem.index">
                                {{ topicItem.type }}
                            </option>
                        </select>
                        
                        <!-- 选择排序方式 -->
                        <select name="selectType" class="selectType" v-model="order">
                            <option value="id asc" selected>按id递增顺序</option>
                            <option value="create_date desc">从新到旧顺序</option>
                        </select>

                    </div>

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
                    <ul v-for="item in items" :key="item.id" style="list-style-type: none">
                        <div class="item-box">
                            <li>
                                <!-- 修改 -->  
                                <div class="item-id" 
                                @click="goToItemDetail(item.id, item.index)">{{ item.id }}</div>                   
                                <div class="item-detail"
                                @click="goToItemDetail(item.id, item.index)">
                                    <div class="item-detail-title">
                                        {{ item.title }}
                                    </div>

                                    <div class="item-detail-author">
                                        作者: {{ item.author }}
                                    </div>
                                </div>

                                <div class="item-detail-operation">
                                    <button 
                                        class="item-detail-operation-button" 
                                        style="background-color: #eb1d2a"
                                        @click="doOperation('edit', item.id)">编辑</button>
                                    <button 
                                        class="item-detail-operation-button" 
                                        style="background-color: #f64530"
                                        @click="doOperation('delete', item.id)">删除</button>
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
    name: "dashforumthread",
    inject: ['reload'],
    data() {
        return {
            // for search
            searchInput: '',
            isSearching: false,

            // 数据
            items: [],
            topicItems: [],

            // for pagination
            currentPage: 1,
            pageSize: 10,
            totalNumber: 0,

            typeIndex: '',
            order: 'id asc',
        }
    },
    methods: {
        // 分页查询
        async setTopicItems() {
            // 获取
            let res = await request.get("/topics");
            if (res.code === code.GET_OK)
            {
                this.topicItems = res.data;
            }
            else 
            {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            }
        },
        async setItems() {
            let res = await request.get("/forumArticles" + this.typeIndex + 
                                "/" + this.currentPage + "/" + this.pageSize + "/" + this.order);
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
        },
        clearSearch() {
            this.searchInput = '';
            this.isSearching = false;
        },

        load() {
            this.setTopicItems();
            this.setItems();
            this.clearSearch();
        },

        // 点击跳转到 id = objId 的详情
        goToItemDetail(objId, objIndex) {
            // this.$router.push({
            //     path: "/page/forum/" + objIndex + "/thread",
            //     query: {
            //         id: objId
            //     }
            // })
            let newUrl = this.$router.resolve({
                path: "/page/forum/" + objIndex + "/thread",
                query: {
                    id: objId
                }
            });
            window.open(newUrl.href, "_blank");
        },

        doOperation(operation, objId) {
            // edit
            if (operation === 'edit')
            {
                this.$router.push({
                    path: "/dashboard/threadEditor",
                    query: {
                        id: objId
                    }
                });

                return;
            }
            // delete
            // 向vuex中保存数据
            this.$store.commit('setOperation', operation);
            this.$store.commit('setObjId', objId);
            // 向父路由emit一个show-dialog事件
            this.$emit('show-dialog', 'thread');
        },

        doSearch() {
            // 若输入为空，刷新、退出
            if (this.searchInput.trim() === '')
            {
                this.load();
                return;
            }
            this.isSearching = true;
            request.get("/forumArticles/search/" + this.searchInput.trim() + "/" + 
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
                res = await request.get("/forumArticles" + this.typeIndex + 
                                    "/" + this.currentPage + "/" + this.pageSize + "/" + this.order);
            }
            else
            {
                res = await request.get("/forumArticles/search/" + this.searchInput.trim() + 
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
    },
    created() {
        this.load();
    },

    watch: {
        typeIndex(val) {
            // 如果val为空（查询全部），则不需要加/
            // 不为空，则要加/，因为后端的请求路径是这样的
            if (val !== '')
            {
                val = '/' + val;
            }
            request.get("/forumArticles" + val + 
                    "/" + this.currentPage + "/" + this.pageSize + "/" + this.order).then(res => {
                        if (res.code === code.GET_OK)
                        {
                            this.items = res.data.list;
                            this.totalNumber = res.data.total;
                        }
                    }).catch(err => {
                        this.$notify.error({
                            title: message.REQUEST_ERR,
                            offset: code.OFFSET
                        })
                    })
        },
        
        order(val) {
            let tempStr;
            if (this.typeIndex === '')
            {
                tempStr = "/forumArticles";
            }
            else 
            {
                tempStr = "/forumArticles/";
            }
            request.get(tempStr + this.typeIndex + 
                    "/" + this.currentPage + "/" + this.pageSize + "/" + val).then(res => {
                        if (res.code === code.GET_OK)
                        {
                            this.items = res.data.list;
                            this.totalNumber = res.data.total;
                        }
                    }).catch(err => {
                        this.$notify.error({
                            title: message.REQUEST_ERR,
                            offset: code.OFFSET
                        })
                    })
        },

        // 含输入的记得掐空格
        searchInput(val) {
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
            
            this.isSearching = true;
            // 延迟 0.2s 进行实时显示
            setTimeout(() => {
                request.get("/forumArticles/search/" + this.searchInput.trim() + 
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
                        // this.$notify.error({
                        //     title: message.REQUEST_ERR,
                        //     offset: code.OFFSET
                        //})
                    })
            }, 200);
        }
    },
}
</script>

<style scoped src="@/../public/css/dashforumthread-style.css">
</style>
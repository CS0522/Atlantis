<template>
    <div>
        <div id="title-box">
            <div>教程管理</div>
        </div>
        <hr class="hr" />
        <div id="content-box">
            <div>
                <div class="add-and-search-box">
                    <div class="add-button-box">
                        <button class="add-button"
                            @click="doOperation('add', -1)">添加教程</button>
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
                    <div class="add-button-box" style="margin: auto auto; margin-bottom: 40px">
                        <button class="add-button" 
                            style="width: 33%; margin: auto 10px"
                            @click="doOperation('addtype', -1)">添加分类</button>
                        <button class="add-button" 
                            style="width: 33%; margin: auto 10px; background-color: #f64530"
                            @click="doOperation('deletetype', -1)">删除分类</button>
                    </div>
                </div>
                <div>
                    <!-- 选择全部显示、分类显示 -->
                    <div class="select-box">
                        <select name="selectType" class="selectType" v-model="typeIndex">
                            <option value="" selected>全部</option>
                            <option 
                                v-for="categoryItem in categoryItems" 
                                :key="categoryItem.index"
                                :value="categoryItem.index">
                                {{ categoryItem.type }}
                            </option>
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

                    <ul v-for="item in items" :key="item.id" style="list-style-type: none">
                        <div class="item-box">
                            <li>
                                <div class="item-id">{{ item.id }}</div>
                                <div class="item-detail">
                                    <div class="item-detail-title">
                                        {{ item.title }}
                                    </div>

                                    <div class="item-detail-operation">
                                        <button 
                                            class="item-detail-operation-button" 
                                            style="background-color: #8beeff"
                                            @click="doOperation('edit', item.id)">编辑</button>
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
            categoryItems: [],

            typeIndex: '',
            order: "id asc",
        }
    },
    methods: {
        load() {
            // this.categoryItems = this.$storage.get('tutorialCategoryItems');
            this.setCategoryItems();
            this.clearSearch();
            this.setItem();
        },

        async setCategoryItems() {
            // 获取有哪些分类
            let res = await request.get("/categories");
            if (res.code === code.GET_OK)
            {
                this.categoryItems = res.data;
            }
            else 
            {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            }
        },

        async setItem() {
            let res = await request.get("/tutorialArticles" + this.typeIndex + 
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

        doOperation(operation, objId)
        {
            // delete
            if (operation === 'delete')
            {
                // 向vuex中保存数据
                this.$store.commit('setOperation', operation);
                this.$store.commit('setObjId', objId);
                // 向父路由emit一个show-dialog事件
                this.$emit('show-dialog', 'tutorial');
            }
            else if (operation.includes('type'))
            {
                // 向vuex中保存数据
                this.$store.commit('setOperation', operation);
                this.$store.commit('setObjId', objId);
                // 向父路由emit一个show-dialog事件
                this.$emit('show-dialog', 'tutorial');
            }
            else
            {
                // push到editor界面
                this.$router.push("editor/tutorial/" + objId);
            }
        },

        // search
        doSearch() {
            // 若输入为空，刷新、退出
            if (this.searchInput.trim() === '')
            {
                this.load();
                return;
            }
            this.isSearching = true;
            request.get("/tutorialArticles/search/" + this.searchInput.trim() + 
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

        // 页面条数修改时（没有设置这个功能）
        handleSizeChange(val) {
            // console.log(`每页 ${val} 条`);
        },
        async handleCurrentChange(val) {
            // console.log("val: " + val);
            let res;
            if (!this.isSearching)
            {
                res = await request.get("/tutorialArticles" + this.typeIndex + 
                                    "/" + this.currentPage + "/" + this.pageSize + "/" + this.order);
            }
            else
            {
                res = await request.get("/tutorialArticles/search/" + this.searchInput.trim() + 
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

    // 监控select选择的变化
    watch: {
        typeIndex(val) {
            // 如果val为空（查询全部），则不需要加/
            // 不为空，则要加/，因为后端的请求路径是这样的
            if (val !== '')
            {
                val = '/' + val;
            }
            request.get("/tutorialArticles" + val + 
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
                tempStr = "/tutorialArticles";
            }
            else 
            {
                tempStr = "/tutorialArticles/";
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
                request.get("/tutorialArticles/search/" + this.searchInput.trim() + 
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
    }
}
</script>

<style scoped src="@/../public/css/dashtutorial-style.css">
</style>
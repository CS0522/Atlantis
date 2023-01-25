<template>
    <div>
        <!-- <hr class="hr" /> -->
        <div id="content-box">
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

                <div v-if="isShowFlag">
                    <img src="@/../public/imgs/commons/ae86.png" height="150px"
                        style="filter: drop-shadow(0px 5px 15px #eb1d2a);"/>
                    <p style="color: rgb(200, 200, 200);">这里什么也没有~</p>
                </div>

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
                                <!-- <div class="item-detail-author">
                                    作者: {{ item.author }}
                                </div> -->
                                <div class="item-detail-view">
                                    浏览: {{ item.view }}
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
</template>

<script>
import request from '@/utils/request';
import code from '@/utils/code';
import message from '@/utils/message';

export default {
    name: "personalthread",
    data() {
        return {
            // for pagination
            currentPage: 1,
            pageSize: 10,
            totalNumber: 0,

            id: this.$storage.get("accountInfo").id,
            // 后端传入数据
            userData: {},

            // data
            items: [],

            isShowFlag: false,
        }
    },
    methods: {
        setIsShowFlag(val) {
            this.isShowFlag = val;
        },
        
        async setUserData() {
            try {
                let res = await request.get('/users/' + this.id);
                if (res.code === code.GET_OK) {
                    this.userData = res.data;
                }
                else if (res.code === code.GET_ERR) {
                    this.$notify.error({
                        title: message.GET_ERR,
                        offset: code.OFFSET
                    })
                }
            } catch (err) {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            }
        },

        async setItems() {
            // get username
            await this.setUserData();
            // get forum articles by author
            try {
                let res = await request.get("/forumArticles/author/" + this.currentPage + "/" + 
                    this.pageSize + "/" + this.userData.username);
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

        async load() {
            // this.setUserData();
            await this.setItems();
        },

        goToItemDetail(objId, objIndex) {
            let newUrl = this.$router.resolve({
                path: "/page/forum/" + objIndex + "/thread",
                query: {
                    id: objId
                }
            });
            window.open(newUrl.href, "_blank");
        },

        doOperation(operation, objId) {
            if (operation === 'delete')
            {
                // 向vuex中保存数据
                this.$store.commit('setOperation', operation);
                this.$store.commit('setObjId', objId);
                // 向父路由emit一个show-dialog事件
                this.$emit('show-dialog', 'thread');
            }
            // edit
            else if (operation === 'edit')
            {
                this.$router.push({
                    path: "/personal/threadEditor",
                    query: {
                        id: objId
                    }
                });

                return;
            }
        },

        handleSizeChange(val) {
            // console.log("val: " + val);
        },
        async handleCurrentChange(val) {
            try {
                let res;
                res = await request.get("/forumArticles/author/" + this.currentPage + "/" + this.pageSize +
                    "/" + this.userData.username);
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
    created() {
        this.load();
    },
    watch: {

    }
}
</script>

<style scoped src="@/../public/css/personalthread-style.css">
</style>
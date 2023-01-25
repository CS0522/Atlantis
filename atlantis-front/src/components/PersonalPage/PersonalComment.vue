<template>
    <div>
        <!-- <hr class="hr" /> -->
        <div id="content-box">
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
</template>

<script>
import request from '@/utils/request';
import code from '@/utils/code';
import message from '@/utils/message';

export default {
    name: 'personalcomment',
    data() {
        return {

            id: this.$storage.get("accountInfo").id,
            // 后端传入数据
            userData: {},

            // 数据
            items: [],

            isShowFlag: false,

            // for pagination
            currentPage: 1,
            pageSize: 10,
            totalNumber: 0,

        }
    },
    methods: {
        async load() {
            await this.setItems();
        },

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
        // 分页查询
        async setItems() {
            await this.setUserData();
            // get all
            try {
                let res = await request.get("/comments/name/" + this.currentPage + "/" + this.pageSize
                    + "/" + this.userData.username + "/users");
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

        doOperation(operation, objIndex) {
            // 向vuex中保存数据
            this.$store.commit('setOperation', operation);
            this.$store.commit('setObjId', objIndex);
            // 向父路由emit一个show-dialog事件
            this.$emit('show-dialog', 'comment');
        },

        handleSizeChange(val) {
            // console.log("val: " + val);
        },
        async handleCurrentChange(val) {
            try {
                let res;
                res = await request.get("/comments/name/" + this.currentPage + "/" + this.pageSize
                    + "/" + this.userData.username + "/users");
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

<style scoped src="@/../public/css/personalcomment-style.css">
</style>
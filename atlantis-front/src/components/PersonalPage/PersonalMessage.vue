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

                <ul v-for="item in items" :key="item.index" style="list-style-type: none">
                    <div class="item-box" id="item-box">
                        <li>
                            <!-- 修改 -->
                            <div class="item-detail" v-if="item.status===0"
                                @click="goToItemDetail(item.index, item.source)">
                                <span style="color: #eb1d2a">
                                    <div class="item-detail-title" >
                                        {{ item.title }}
                                    </div>
                                    <div class="item-detail-content">
                                        {{ item.content }}
                                    </div>
                                </span>
                            </div>
                            <div class="item-detail" v-else="item.status===1"
                                @click="goToItemDetail(item.index, item.source)">
                                <div class="item-detail-title" >
                                    {{ item.title }}
                                </div>
                                <div class="item-detail-content">
                                    {{ item.content }}
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
import request from '@/utils/request';
import code from '@/utils/code';
import message from '@/utils/message';

export default {
    name: "personalmessage",
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
            // get messages by author
            try {
                let res = await request.get("/usermessages/destination/" + this.currentPage + "/" + 
                    this.pageSize + "/" + this.userData.id);
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

        // 页面跳转
        async goToItemDetail(messageIndex, threadId) {
            // 将红色消除，即更新status
            let sendData = {
                index: messageIndex,
                status: 1
            }
            request.put("/usermessages", sendData).then(res => {
            }).catch(err => {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            })
            if (threadId === -1)
            {
                return;
            }
            try {
                let res = await request.get("/forumArticles/" + threadId);
                if (res.code === code.GET_OK)
                {
                    let threadIndex = res.data.index;
                    // jump
                    let newUrl = this.$router.resolve({
                    path: "/page/forum/" + threadIndex + "/thread",
                    query: {
                        id: threadId
                    }
                    });
                    window.open(newUrl.href, "_blank");
                }
            }
            catch(err) {
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
                res = await request.get("/usermessages/destination/" + this.currentPage + "/" + this.pageSize +
                    "/" + this.userData.id);
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

<style scoped src="@/../public/css/personalmessage-style.css">
</style>
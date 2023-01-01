<template>
    <div>
        <div id="detail-box">
            <h1>
                {{ item.title }}
            </h1>
            <p style="display: inline; margin: 20px">
                作者: {{ item.author }} 
            </p>
            <p style="display: inline; margin: 20px">
                时间: {{ item.createDate }}
            </p>
            <div class="markdown-body">
                <VueMarkDown class="content" :source="item.content"></VueMarkDown>
            </div>
        </div>
        <!-- 留言区 -->
        <div id="comment-box">
            <div class="comment-box-title">
                <h3>留言 <span style="font-size: 30px">{{ commentNum }}</span></h3>
            </div>
            <div class="make-comment">
                <textarea class="comment-textarea" placeholder="有什么想说的，欢迎留言~"
                          style="resize: vertical; min-height: 250px;"
                          v-model="sendCommentContent">
                </textarea>
                <div class="comment-button-box">
                    <button class="comment-button"
                            style="background-color: #8beeff"
                            @click="doInsert()">写下留言</button>
                    <div class="comment-noname">
                        <input class="comment-button-noname" type="checkbox" name="noname"
                                v-model="isNoName"/>
                        <label for="noname">匿名</label>
                    </div>
                </div>
            </div>
            <!-- pagination -->
            <el-pagination style="zoom: 220%; margin-bottom: 20px"
                            @size-change="handleSizeChange" 
                            @current-change="handleCurrentChange" 
                            :current-page.sync="currentPage"
                            :page-size="pageSize" 
                            layout="prev, pager, next, jumper" 
                            :total="totalNumber">
            </el-pagination>
            <div class="comment-item" v-for="comment in comments" :key="comment.index">
                <div class="comment-item-detail">
                    <!-- <div class="comment-item-userphoto">
                    </div> -->
                    <div class="comment-item-info">
                        <!-- 当留言者为管理员时，显示管理员标记 -->
                        <div class="comment-item-admin-mark"
                             v-if="comment.loginType==='admins'">
                            <span style="color: #f64530">A.&nbsp;</span>
                        </div>
                        <div class="comment-item-name">
                            {{ comment.name }}
                        </div>
                        <div class="comment-item-date">
                            {{ comment.createDate }}
                        </div>
                        <!-- 只有当用户名和本地存储的账户名相同时，才会显示删除 -->
                        <div class="comment-item-operation"
                             v-if="accountInfo">
                            <button class="delete-button"
                                    v-if="comment.name===accountInfo.username"
                                    @click="doDelete(comment.index)">删除</button>
                        </div>
                    </div>
                    <div class="comment-item-content">
                        {{ comment.content }}
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<script>
import code from '@/utils/code';
import request from '@/utils/request';
import message from '@/utils/message';
// 将markdown转换成html
import VueMarkDown from 'vue-markdown';

export default {
    name: "thread",
    components: {
        VueMarkDown,
    },
    data() {
        return {
            // markdown要解析的数据
            item: {},

            // 帖子id
            id: '',

            // for pagination
            // 当前页
            currentPage: 1,
            // 每页条数
            pageSize: 10,
            // 数据总数
            totalNumber: 0,

            // 写下留言
            sendCommentContent: '',
            isNoName: false,

            // 评论区测试数据
            comments: [],

            // 本地存储的 accountInfo 和 loginType
            accountInfo: this.$storage.get('accountInfo'),
            loginType: this.$storage.get('loginType'),
        }
    },
    computed: {
        // 获取comments的个数
        commentNum: function() {
            // return this.comments.length;
            return this.totalNumber;
        }
    },

    methods: {
        load()
        {
            // 获取查询id
            this.getId();

            // 获取帖子文章
            this.setItem();

            // 获取评论
            this.getComments();
            
        },
        getId() {
            const id = this.$route.query.id;
            this.id = id;
        },
        setItem() {
            request.get("/forumArticles/" + this.id).then(res => {
                if (res.code === code.GET_OK)
                {
                    this.item = res.data;
                    // markdown 解析
                    this.item.content;
                }
            }).catch(err => {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET,
                })
            })
        }, 
        getComments() {
            console.log("getting comments...");
            request.get("/comments/id/" + this.currentPage + "/" + 
                    this.pageSize + "/" + this.id).then(res => {
                if (res.code === code.GET_OK)
                {
                    this.comments = res.data.list;
                    this.totalNumber = res.data.total;
                }
            }).catch(err => {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET,
                })
            })
        },
        // 插入留言
        doInsert() {
            // send comment item
            let sendCommentItem = {
                id: '',
                loginType: 'users',
                name: '匿名',
                content: ''
            };
            // set id
            sendCommentItem.id = this.id;
            // set loginType and name
            if (!this.isNoName && this.accountInfo)
            {
                sendCommentItem.loginType = this.loginType;
                sendCommentItem.name = this.accountInfo.username;
            }
            // set content
            sendCommentItem.content = this.sendCommentContent;
            // send
            request.post("/comments", sendCommentItem).then(res => {
                if (res.code === code.INSERT_OK)
                {
                    this.$notify.success({
                        title: '留言成功',
                        offset: code.OFFSET
                    })
                    // clear text
                    this.sendCommentContent = '';
                    // reload
                    setTimeout(() => {
                        this.getComments();
                    }, 500);

                    this.currentPage = 1;
                }
            }).catch(err => {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            })
        },
        // 删除留言
        doDelete(objIndex) {
            request.delete("/comments/" + objIndex).then(res => {
                if (res.code === code.DELETE_OK)
                {
                    this.$notify.success({
                        title: message.DELETE_OK,
                        offset: code.OFFSET,
                    })
                    // reload
                    setTimeout(() => {
                        this.getComments();
                    }, 500);
                }
            }).catch(err => {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            })
        },

        // pagination
        handleSizeChange(val) {
            console.log(`每页 ${val} 条`);
        },
        handleCurrentChange(val) {
            // 重新请求
            request.get("/comments/id/" + this.currentPage + "/" + 
                    this.pageSize + "/" + this.id).then(res => {
                if (res.code === code.GET_OK)
                {
                    this.comments = res.data.list;
                    this.totalNumber = res.data.total;
                }
            }).catch(err => {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET,
                })
            })
        },
    },
    
    created() {
        this.load();
    },
}
</script>

<style scoped src="@/../public/css/subcontentdetail-style.css">
</style>
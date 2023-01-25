<template>
    <div>
        <TopBar v-if="!topBarReload"></TopBar>

        <transition :duration="{enter:200,leave:200}" name="el-zoom-in-center">
            <BaseDialog
                v-if="isShowBase"
                :dialogType="operationType"
                @close="closeDialog"
                @confirm="confirm">
                <!-- 使用具名slot插槽 -->
                <template v-slot:title>
                    <div>{{ dialogTitle }}</div>
                </template>
                <template v-slot:content>
                    <div>{{ dialogContent }}</div>
                </template>
            </BaseDialog>

            <PersonalThreadDialog
            v-if="isShowThread"
            @close="closeDialog"
            @confirm="confirm">
            </PersonalThreadDialog>

            <PersonalCommentDialog
            v-if="isShowComment"
            @close="closeDialog"
            @confirm="confirm">
            </PersonalCommentDialog>
        </transition>

        <div id="personal">
            <div id="personal-nav-box">
                <div id="personal-title">
                    <!-- 设置点击图片跳转至首页 -->
                    <img src="imgs/logo/jmvt-logo-inline.png" class="logo-img" height="180px" 
                         @click="toHome()"/>
                </div>
                <div id="personal-sub-nav">

                    <router-link to="/personal/profile">
                        <div class="sub-tab">个人资料</div>
                    </router-link>

                    <router-link to="/personal/message">
                        <div class="sub-tab">消息中心</div>
                    </router-link>

                    <router-link to="/personal/thread">
                        <div class="sub-tab">我的帖子</div>
                    </router-link>

                    <router-link to="/personal/comment">
                        <div class="sub-tab">我的评论</div>
                    </router-link>
                </div>
            </div>
            <div id="personal-content-box">
                <div id="title-box">
                    <!-- 后台传递的用户的头像 -->
                    <div id="img-box">
                        <el-upload 
                            class = "avatar-uploader" 
                            :action = "actionUrl"
                            :show-file-list = "false"
                            :on-success = "handleAvatarSuccess" 
                            :before-upload = "beforeAvatarUpload">
                            <img v-if = "imageUrl" :src = "imageUrl" class = "avatar">
                            <i v-else class = "el-icon-plus avatar-uploader-icon"></i>
                        </el-upload>
                    </div>
                    <div id="welcome-box">
                        <h1>&nbsp;欢迎</h1>
                    </div>
                    <!-- 申请管理员、加入车队 -->
                    <div class="operation-box">
                        <table v-if="isAdmin===''">
                        </table>
                        <table v-else-if="!isAdmin"
                            class="table-operation" cellpadding=10px cellspacing=15px>
                            <tr align="center">
                                <td>
                                    <button class="operation" style="background-color: #eb1d2a"
                                    @click="doApplyAdmin()">管理员申请</button>
                                </td>
                                <td>
                                    <button class="operation" style="background-color: #f64530"
                                    @click="doDeleteApply()">撤销申请</button>
                                </td>
                            </tr>
                        </table>
                        <table v-else-if="isAdmin"
                            class="table-operation" cellpadding=10px cellspacing=15px>
                            <tr align="center">
                                <td>
                                    <button class="operation"
                                            style="background-color: #eb1d2a">你已经是管理员</button>
                                </td>
                            </tr>
                        </table>
                    </div>
                </div>
                <hr class="hr" />
                <router-view v-if="isRouterAlive"
                            @show-dialog="showDialog"
                            :key="$route.fullPath">
                </router-view>
            </div>
        </div>
    </div>
</template>

<script>
import code from '@/utils/code';
import request from '@/utils/request';
import TopBar from '@/components/HomePage/TopBar.vue';
import BaseDialog from '@/components/Dialog/BaseDialog.vue';
import PersonalThreadDialog from '@/components/Dialog/PersonalThreadDialog.vue';
import PersonalCommentDialog from '@/components/Dialog/PersonalCommentDialog.vue';
import message from '@/utils/message';

export default {
    name: "personal",
    components: {
        TopBar,
        BaseDialog,
        PersonalThreadDialog,
        PersonalCommentDialog,
    },
    // inject: ['reload'],

    provide() {
        return {
            reload: this.reload,
            doTopBarReload: this.doTopBarReload,
        }     
    },
    data() {
        return {
            // 显示Dialog
            isShowBase: false,
            dialogTitle: '',
            dialogContent: '',
            operationType: '',
            isShowThread: false,
            isShowComment: false,

            // 需要通过userId获取图像
            imgBaseUrl: this.$store.state.userImgBaseUrl,
            userId: '',
            actionUrl: '',
            imageUrl: '',

            topBarReload: false,
            isRouterAlive: true,

            // 判断是否是管理员，用于申请按钮的显示隐藏
            isAdmin: '',
        }
    },
    created() {
        document.title = '用户主页';
        this.load();
    },
    methods: {
        toHome() {
            window.location.href = "/";
        },

        // 刷新路由
        reload() {
            this.isRouterAlive = false;
            this.$nextTick(function() {
                this.isRouterAlive = true;
            })
        },
        // 刷新 topbar
        doTopBarReload() {
            this.topBarReload = true;
            this.$nextTick(function() {
                this.topBarReload = false;
            })
        },
        load()
        {
            this.title = this.$storage.get("accountInfo").username;
            // console.log(this.title);
            this.userId = this.$storage.get("accountInfo").id;
            // set action url
            this.actionUrl = this.imgBaseUrl + 'upload/' + this.userId;

            // if userphoto exists, get it
            this.getUserPhoto();
            // if is admin
            this.checkIsAdmin();
            // get
            this.getCategoriesAndTopics();
        },
        // 加载教程分类，放在这一层避免闪烁
        // 加载论坛话题
        async getCategoriesAndTopics() {
            // console.log("loading...")
            // 获取分类
            let res1 = await request.get("/categories");
            if (res1.code === code.GET_OK)
            {
                // 保存在localStorage中，方便后续组件读取，避免每次都要请求导致闪烁
                this.$storage.set('tutorialCategoryItems', res1.data, 24 * 60 * 60 * 1000);
            }
            else 
            {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            }
            // 获取话题
            let res2 = await request.get("/topics");
            if (res2.code === code.GET_OK)
            {
                // 保存在localStorage中，方便后续组件读取，避免每次都要请求导致闪烁
                this.$storage.set("forumTopicItems", res2.data, 24 * 60 * 60 * 1000);
            }
            else
            {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            }
        },

        getUserPhoto() {
            request.get("/users/download/" + this.userId).then(res => {
                // 没有userphoto
                if (res.code === code.DOWNLOAD_ERR)
                {
                    // this.imageUrl = "https://cube.elemecdn.com/3/7c/3ea6beec64369c2642b92c6726f1epng.png";
                }
                else 
                {
                    this.imageUrl = this.imgBaseUrl + "download/" + this.userId;
                }
            })
        },
        // 需要同步加载
        async checkIsAdmin() {
            let res = await request.get("/useradmin/" + this.userId);
            if (res.code === code.GET_OK)
            {
                this.isAdmin = true;
            }
            else
            {
                this.isAdmin = false;
            }
        },

        // 管理员申请
        async doApplyAdmin() {
            // 先检查是否申请过
            let res = await request.get("/applies/" + this.userId);
            if (res.code === code.GET_OK)
            {
                this.$notify.error({
                        title: '你已经申请过管理员',
                        offset: code.OFFSET
                    })
                    return;
            }
            // TODO 再检查是否满足申请要求

            // 设置内容
            this.dialogTitle = '管理员申请';
            this.dialogContent = '加入管理员后，账号不消失，可以不同身份登录。确认申请？';
            this.operationType = 'add';

            this.isShowBase = true;
        },

        // 撤销申请
        async doDeleteApply() {
            let res = await request.get("/applies/" + this.userId);
            if (res.code === code.GET_ERR)
            {
                this.$notify.error({
                        title: '你没有申请过管理员',
                        offset: code.OFFSET
                    })
                    return;
            }
            // 设置内容
            this.dialogTitle = '撤销申请';
            this.dialogContent = '确认撤销申请？';
            this.operationType = 'delete';

            this.isShowBase = true;
        },

        // 显示dialog
        showDialog(showWhich) {
            if (showWhich === 'thread')
            {
                this.isShowThread = true;
            }
            else if (showWhich === 'comment')
            {
                this.isShowComment = true;
            }
        },

        // Dialog点击按钮的事件
        // 点击取消按钮
        closeDialog()
        {
            this.isShowBase = false;
            this.isShowThread = false;
            this.isShowComment = false;
        },
        // 点击确定按钮
        // type值是操作的类型
        confirm(type) {
            this.closeDialog();
            // 确定后采取操作
            // console.log("type: " + type)
            if (type === 'add')
            {
                let apply = {
                    id: this.$storage.get("accountInfo").id,
                    username: this.$storage.get("accountInfo").username
                }
                request.post("/applies", apply).then(res => {
                    if (res.code === code.INSERT_OK)
                    {
                        this.$notify.success({
                            title: '申请成功，等待管理员审核',
                            offset: code.OFFSET
                        })
                    }
                }).catch(err => {
                    this.$notify.error({
                        title: message.REQUEST_ERR,
                        offset: code.OFFSET
                    })
                })
            }
            else if (type === 'delete')
            {
                request.delete("/applies/" + this.userId).then(res => {
                    if (res.code === code.DELETE_OK)
                    {
                        this.$notify.success({
                            title: '已撤销',
                            offset: code.OFFSET
                        })
                    }
                }).catch(err => {
                    this.$notify.error({
                        title: message.REQUEST_ERR,
                        offset: code.OFFSET
                    })
                })
            }
            // 其他 threaddialog & commentdialog
            else 
            {
                // 延迟 300ms 刷新子路由
                setTimeout(() => {
                    this.reload(); // 执行方法
                }, 300);
            }
        },

        // 用户头像上传
        handleAvatarSuccess(res, file) {
            this.imageUrl = this.imgBaseUrl + "download/" + this.userId;
            this.reload();
            this.$notify.success({
                title: '头像更新成功',
                offset: code.OFFSET
            })
        },
        beforeAvatarUpload(file) {
            if (file) {
                console.log("uploading...actionUrl: " + this.actionUrl);
                const postfix = file.name.split('.')[1]
                const isSizeOk = file.size < (code.MAX_SIZE * 1024 * 1024);
                if (['png', 'jpeg', 'jpg'].indexOf(postfix) < 0) {
                    this.$notify.error({
                        title: '头像仅支持 png, jpg, jpeg 格式',
                        offset: code.OFFSET
                    })
                    this.$refs.upload.clearFiles()
                    return false
                }
                if (!isSizeOk) {
                    this.$notify.error({
                        title: '上传头像大小不能超过' + String(code.MAX_SIZE) + 'MB',
                        offset: code.OFFSET
                    })
                    return false
                }
                return file
            }
        }
    }
}
</script>

<style scoped src="@/../public/css/personal-style.css">
</style>

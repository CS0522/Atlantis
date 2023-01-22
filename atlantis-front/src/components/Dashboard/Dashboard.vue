<template>
    <div>
        <transition :duration="{enter:200,leave:200}" name="el-zoom-in-center">
            <AccountDialog 
            v-if="isShowAccount"
            @close="closeDialog"
            @confirm="confirm">
            </AccountDialog>

            <AboutDialog 
            v-if="isShowAbout"
            @close="closeDialog"
            @confirm="confirm">
            </AboutDialog>

            <NewsDialog 
            v-if="isShowNews"
            @close="closeDialog"
            @confirm="confirm">
            </NewsDialog>

            <TutorialDialog 
            v-if="isShowTutorial"
            @close="closeDialog"
            @confirm="confirm">
            </TutorialDialog>

            <ForumTopicDialog 
            v-if="isShowForumTopic"
            @close="closeDialog"
            @confirm="confirm">
            </ForumTopicDialog>

            <ForumThreadDialog 
            v-if="isShowForumThread"
            @close="closeDialog"
            @confirm="confirm">
            </ForumThreadDialog>

            <ForumCommentDialog 
            v-if="isShowForumComment"
            @close="closeDialog"
            @confirm="confirm">
            </ForumCommentDialog>
    </transition>

        <TopBar v-if="!topBarReload"></TopBar>

        <div id="dashboard">
            <div id="dash-nav-box">
                <div id="dash-title">
                    <!-- 设置点击图片跳转至首页 -->
                    <img src = "imgs/logo/jmvt-logo-inline.png" class="logo-img" height = "180px" 
                         @click="toHome()"/>
                </div>
                <!-- 点击显示菜单 -->
                <div id="dash-sub-nav">
                    <!-- 二级菜单 -->
                    <div class="hov-sub-tab-menu1">
                        <a><div class="sub-tab" @click="showMenu1()">用户管理</div></a>
                        <router-link to="/dashboard/account/admin">
                            <div class="hov-sub-tab-item">管理员账号管理</div>
                        </router-link>
                        <router-link to="/dashboard/account/user">
                            <div class="hov-sub-tab-item">用户账号管理</div>
                        </router-link>                          
                    </div>

                    <!-- 二级菜单 -->
                    <div class="hov-sub-tab-menu2">
                        <a><div class="sub-tab" @click="showMenu2()">消息中心</div></a>
                        <router-link to="/dashboard/message/apply">
                            <div class="hov-sub-tab-item">管理员申请</div>
                        </router-link>
                        <!-- <router-link to="/dashboard/message/reset">
                            <div class="hov-sub-tab-item">重置密码申请</div>
                        </router-link> -->
                    </div>

                    <router-link to="/dashboard/news">
                        <div class="sub-tab">资讯管理</div>
                    </router-link>

                    <router-link to="/dashboard/tutorial">
                        <div class="sub-tab">教程管理 </div>
                    </router-link>

                    <!-- 二级菜单 -->
                    <div class="hov-sub-tab-menu3">
                        <a><div class="sub-tab" @click="showMenu3()">论坛管理</div></a>
                        <router-link to="/dashboard/forum/topic">
                            <div class="hov-sub-tab-item">话题管理</div>
                        </router-link>
                        <router-link to="/dashboard/forum/thread">
                            <div class="hov-sub-tab-item">帖子管理</div>
                        </router-link>  
                        <router-link to="/dashboard/forum/comment">
                            <div class="hov-sub-tab-item">评论管理</div>
                        </router-link>                        
                    </div>

                    <router-link to="/dashboard/about">
                        <div class="sub-tab">车队管理 </div>
                    </router-link>

                </div>
            </div>
            <div id="dash-content-box">
                <!-- 注意这个地方一定要绑定key，路由改变即刷新 -->
                <!-- 子组件传递参数，@绑定方法函数 -->
                <router-view v-if="isRouterAlive"
                    :key="$route.fullPath"
                    @show-dialog="showDialog"
                    ></router-view>
            </div>
        </div>
    </div>
</template>

<script>
import AccountDialog from '@/components/Dialog/AccountDialog.vue'
import AboutDialog from '@/components/Dialog/AboutDialog.vue'
import NewsDialog from '@/components/Dialog/NewsDialog.vue'
import TutorialDialog from '@/components/Dialog/TutorialDialog.vue'
import ForumTopicDialog from '@/components/Dialog/ForumTopicDialog.vue';
import ForumThreadDialog from '@/components/Dialog/ForumThreadDialog.vue';
import ForumCommentDialog from '@/components/Dialog/ForumCommentDialog.vue';
import TopBar from '../HomePage/TopBar.vue'
import code from '@/utils/code';
import message from '@/utils/message';
import request from '@/utils/request';

export default {
    name: "dashboard",
    components: {
        AccountDialog,
        AboutDialog,
        NewsDialog,
        TutorialDialog,
        ForumTopicDialog,
        ForumThreadDialog,
        ForumCommentDialog,
        TopBar,
    },
    data() {
        return {
            isShowAccount: false,
            isShowAbout: false,
            isShowNews: false,
            isShowTutorial: false,
            isShowForumTopic: false,
            isShowForumThread: false,
            isShowForumComment: false,

            isRouterAlive: true,
            topBarReload: false,
        }
    },
    // 刷新路由操作
    provide() {
        return {
            reload: this.reload,
            doTopBarReload: this.doTopBarReload,
        }     
    },

    methods: {
        toHome() {
            window.location.href = "/";
        },

        // 通过定时器将高度逐渐修改从而达到二级菜单逐渐展开的动画
        showMenu1() {
            // console.log("do showMenu1")
            if(document.getElementsByClassName("hov-sub-tab-menu1")[0].clientHeight<330){
             var showTimer=setInterval(function(){
                // 获取上一次移动之后的高度
                var oldValveMenu1=document.getElementsByClassName("hov-sub-tab-menu1")[0].clientHeight;
                 // 判断移动后的结果是否完全展开，即高度的变化范围为130px-330px
                 if(oldValveMenu1+8>=330){
                    clearInterval(showTimer);
                }
                // 将高度每10ms移动8像素的距离，从而达到0.25s完全展开的效果
                document.getElementsByClassName("hov-sub-tab-menu1")[0].style.height=oldValveMenu1+8+"px";
            },10)
            }

            if(document.getElementsByClassName("hov-sub-tab-menu1")[0].clientHeight>130){
             var hideTimer=setInterval(function(){
                var oldValveMenu1=document.getElementsByClassName("hov-sub-tab-menu1")[0].clientHeight;
                if(oldValveMenu1-8<=130){
                    clearInterval(hideTimer);
                }
                document.getElementsByClassName("hov-sub-tab-menu1")[0].style.height=oldValveMenu1-8+"px";
            },10)
            }
        },

        showMenu2(){
            // console.log("do showMenu2")
            if(document.getElementsByClassName("hov-sub-tab-menu2")[0].clientHeight<230){
            var showTimer=setInterval(function(){
                
                var oldValveMenu2=document.getElementsByClassName("hov-sub-tab-menu2")[0].clientHeight;
                if(oldValveMenu2+4>=230){
                    clearInterval(showTimer);
                }
                document.getElementsByClassName("hov-sub-tab-menu2")[0].style.height=oldValveMenu2+4+"px";
            },5)

            }
            if(document.getElementsByClassName("hov-sub-tab-menu2")[0].clientHeight>130){
             var hideTimer=setInterval(function(){
                
                var oldValveMenu2=document.getElementsByClassName("hov-sub-tab-menu2")[0].clientHeight;
                if(oldValveMenu2-4<=130){
                    clearInterval(hideTimer);
                }
                document.getElementsByClassName("hov-sub-tab-menu2")[0].style.height=oldValveMenu2-4+"px";   
            },5)
            }
        },

        showMenu3() {
            // console.log("do showMenu1")
            if(document.getElementsByClassName("hov-sub-tab-menu3")[0].clientHeight<330){
             var showTimer=setInterval(function(){
                // 获取上一次移动之后的高度
                var oldValveMenu3=document.getElementsByClassName("hov-sub-tab-menu3")[0].clientHeight;
                 // 判断移动后的结果是否完全展开，即高度的变化范围为130px-430px
                 if(oldValveMenu3+12>=430){
                    clearInterval(showTimer);
                }
                // 将高度每10ms移动8像素的距离，从而达到0.25s完全展开的效果
                document.getElementsByClassName("hov-sub-tab-menu3")[0].style.height=oldValveMenu3+12+"px";
            },15)
            }

            if(document.getElementsByClassName("hov-sub-tab-menu3")[0].clientHeight>130){
             var hideTimer=setInterval(function(){
                var oldValveMenu3=document.getElementsByClassName("hov-sub-tab-menu3")[0].clientHeight;
                if(oldValveMenu3-12<=130){
                    clearInterval(hideTimer);
                }
                document.getElementsByClassName("hov-sub-tab-menu3")[0].style.height=oldValveMenu3-12+"px";
            },15)
            }
        },
        
        // 显示dialog
        showDialog(showWhich) {
            if (showWhich === 'account')
            {
                this.isShowAccount = true;
            }
            else if (showWhich === 'about')
            {
                this.isShowAbout = true;
            }
            else if (showWhich === 'news')
            {
                this.isShowNews = true;
            }
            else if (showWhich === 'tutorial')
            {
                this.isShowTutorial = true;
            }
            else if (showWhich === 'topic')
            {
                this.isShowForumTopic = true;
            }
            else if (showWhich === 'thread')
            {
                this.isShowForumThread = true;
            }
            else if (showWhich === 'comment')
            {
                this.isShowForumComment = true;
            }
        },
        // Dialog点击按钮的事件
        // 点击取消按钮
        closeDialog()
        {
            this.isShowAccount = false;
            this.isShowAbout = false;
            this.isShowNews = false;
            this.isShowTutorial = false;
            this.isShowForumTopic = false;
            this.isShowForumThread = false;
            this.isShowForumComment = false;
        },
        // 点击确定按钮
        confirm() {
            this.closeDialog();
            // 确定后采取操作
            // 延迟 300ms 刷新子路由
            setTimeout(() => {
                this.reload(); // 执行方法
            }, 300);

        },
        // 刷新路由
        reload() {
            this.isRouterAlive = false;
            this.$nextTick(function() {
                this.isRouterAlive = true;
            })
        },
        // 刷新topbar
        doTopBarReload() {
            this.topBarReload = true;
            this.$nextTick(function() {
                this.topBarReload = false;
            })
        },

        // 加载教程分类，放在这一层避免闪烁
        // 加载论坛话题
        async load() {
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
    },

    created() {
        document.title = '管理面板';
        this.load();
    },
}

</script>

<style scoped src='@/../public/css/dashboard-style.css'>

</style>
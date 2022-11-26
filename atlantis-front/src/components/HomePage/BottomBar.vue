<template>
    <div id="bottom-bar">
        <div class="bottom-nav">
            <table class="bottom-nav-table">
                <tr>
                    <th>游戏介绍</th>
                </tr>
                <tr>
                    <td class="td">
                        <router-link to="/page/introduction" class="routerlink">
                            <div @click="toTop()">游戏介绍</div></router-link>
                    </td>
                </tr>
            </table>
        </div>
        <!-- 点击后同一个页面跳转到相应位置 -->
        <div class="bottom-nav">
            <table class="bottom-nav-table">
                <tr>
                    <th>游戏资讯</th>
                </tr>
                <tr>
                    <td class="td">
                        <router-link to="/page/news/news/list" class="routerlink">
                            <div @click="toTop()">游戏新闻</div></router-link>
                    </td>
                </tr>
                <tr>
                    <td class="td">
                        <router-link to="/page/news/announces/list" class="routerlink">
                            <div @click="toTop()">游戏公告</div></router-link>
                    </td>
                </tr>
            </table>
        </div>
        <div class="bottom-nav">
            <table class="bottom-nav-table">
                <tr>
                    <th>游戏教程</th>
                </tr>
                <tr>
                    <td class="td">
                        <router-link to="/page/tutorial/basic" class="routerlink">
                            <div @click="toTop()">入门</div></router-link>
                    </td>
                </tr>
                <tr>
                    <td class="td">
                        <router-link to="/page/tutorial/premium" class="routerlink">
                            <div @click="toTop()">进阶</div></router-link>
                    </td>
                </tr>
                <tr>
                    <td class="td">
                        <router-link to="/page/tutorial/online" class="routerlink">
                            <div @click="toTop()">联机</div></router-link>
                    </td>
                </tr>
                <tr>
                    <td class="td">
                        <router-link to="/page/tutorial/rules" class="routerlink">
                            <div @click="toTop()">守则</div></router-link>
                    </td>
                </tr>
            </table>
        </div>
        <div class="bottom-nav">
            <table class="bottom-nav-table">
                <tr>
                    <th>关于车队</th>
                </tr>
                <tr>
                    <td class="td">
                        <router-link to="/page/about/introduction" class="routerlink">
                            <div @click="toTop()">车队简介</div></router-link>
                    </td>
                </tr>
                <tr>
                    <td class="td">
                        <router-link to="/page/about/member" class="routerlink">
                            <div @click="toTop()">车队成员</div></router-link>
                    </td>
                </tr>
                <tr>
                    <td class="td">
                        <router-link to="/page/about/contact" class="routerlink">
                            <div @click="toTop()">联系我们</div></router-link>
                    </td>
                </tr>
            </table>
        </div>
        <div class="admin-login-box">
            <!-- 跳转管理员登录界面 -->
            <router-link to="/login/admin" target="_blank" style="text-decoration: underline;">管理员登录
            </router-link>
        </div>
    </div>
</template>

<script>

export default {
    data()
    {
        return {
            
        }
    },
    created() {
    },
    methods:
    {
        toTop() {
            // console.log("to top");
            // 定位滚动条
            // window.scrollTo(window.pageXOffset, 520);
            // 定位到某个元素
            document.getElementById("nav-box-anchor").scrollIntoView();
        }
    },
}
</script>

<style scoped src="@/../public/css/bottombar-style.css">
</style>
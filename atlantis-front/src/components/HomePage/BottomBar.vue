<template>
    <div id="bottom-bar">
        <div class="bottom-nav">
            <table class="bottom-nav-table">
                <tr>
                    <th>游戏介绍</th>
                </tr>
            </table>
        </div>
        <!-- TODO 点击后同一个页面跳转到相应位置（或者回顶部） -->
        <div class="bottom-nav">
            <table class="bottom-nav-table">
                <tr>
                    <th>游戏资讯</th>
                </tr>
                <tr>
                    <td class="td">
                        <router-link to="/page/news/news" class="routerlink">游戏新闻</router-link>
                    </td>
                </tr>
                <tr>
                    <td class="td">
                        <router-link to="/page/news/announces" class="routerlink">游戏公告</router-link>
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
                        <router-link to="/page/tutorial/basic" class="routerlink">入门</router-link>
                    </td>
                </tr>
                <tr>
                    <td class="td">
                        <router-link to="/page/tutorial/premium" class="routerlink">进阶</router-link>
                    </td>
                </tr>
                <tr>
                    <td class="td">
                        <router-link to="/page/tutorial/online" class="routerlink">联机</router-link>
                    </td>
                </tr>
                <tr>
                    <td class="td">
                        <router-link to="/page/tutorial/rules" class="routerlink">守则</router-link>
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
                        <router-link to="/page/about/introduction" class="routerlink"
                        @click="toTop()">车队简介</router-link>
                    </td>
                </tr>
                <tr>
                    <td class="td">
                        <router-link to="/page/about/member" class="routerlink">车队成员</router-link>
                    </td>
                </tr>
                <tr>
                    <td class="td">
                        <router-link to="/page/about/contact" class="routerlink">联系我们</router-link>
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
        // this.setBottomBarSize();
    },
    methods:
    {
        toTop() {
            // 实现点击返回
            console.log("relocation")
            document.body.scrollTop = 0;
        },
    }
}
</script>

<style scoped src="@/../public/css/bottombar-style.css">
</style>
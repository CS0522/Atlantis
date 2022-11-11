<template>
    <div id="box">
        <div id="sub-nav-box">
            <div id="nav-title">
                游戏资讯
            </div>
            <div class="sub-nav">
                    <router-link to="/page/news/news">
                        <div class="sub-tab">游戏新闻</div>
                    </router-link>
                
                    <router-link to="/page/news/announces">
                        <div class="sub-tab">游戏公告</div>
                    </router-link>
                
            </div>
        </div>
        <div id="sub-content">
            <router-view></router-view>
        </div>
    </div>
</template>

<script>

export default {
    name: "news",
    components: {

    },
    data()
    {
        return {
        }
    }
}
</script>

<style scoped src="@/../public/css/pages-style.css">
</style>

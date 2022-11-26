<template>
    <div>
        <div id="title-box">
            <!-- 根据动态路由传递参数判断title并渲染 -->
            <div v-if="type==='news'">游戏新闻</div>
            <div v-else-if="type==='announces'">游戏公告</div>
        </div>
        <hr class="hr"/>
        <router-view
            :key="$route.fullPath"></router-view>
    </div>
</template>

<script>
export default {
    props: ['type']
}
</script>

<style scoped src="@/../public/css/subcontent-style.css">
</style>
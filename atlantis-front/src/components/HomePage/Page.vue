<template>
    <div>
        <TopBar 
        v-if="!topBarReload"
        @reload="doTopBarReload()"></TopBar>
        <!-- 主界面 -->
        <MainPage></MainPage>
        <!-- 底部导航 -->
        <BottomBar></BottomBar>
    </div>
</template>

<script>
import TopBar from './TopBar.vue'
import MainPage from './MainPage.vue'
import BottomBar from './BottomBar.vue';

import request from '@/utils/request';
import message from '@/utils/message';
import code from '@/utils/code';

export default {
    name: "page",
    components: {
        TopBar,
        MainPage,
        BottomBar
    },
    data() {
        return {
            topBarReload: false,
        }
    },
    provide() {
        return {
            doTopBarReload: this.doTopBarReload
        }     
    },
    methods: {
        doTopBarReload() {
            this.topBarReload = true;
            this.$nextTick(function() {
                this.topBarReload = false;
            })
        },
        // 加载 教程 分类，放在这一层避免闪烁
        load() {
            // 多次执行，试试放在上一层路由，只执行一次，vuex传递
            console.log("loading...")
            // 获取有哪些分类
            request.get("/categories").then(res => {
                if (res.code === code.GET_OK)
                {
                    this.$storage.set('tutorialCategoryItems', res.data, 24 * 60 * 60);
                }
            }).catch(err => {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: 150
                })
            })
        },
    },

    created() {
        this.load();
    },
}
</script>

<style>
</style>
<template>
    <div>
        <!-- 顶栏 -->
        <TopBar 
        v-if="!topBarReload"></TopBar>
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
        // 加载教程分类，放在这一层避免闪烁
        load() {
            // console.log("loading...")
            // 获取有哪些分类
            request.get("/categories").then(res => {
                if (res.code === code.GET_OK)
                {
                    // 保存在localStorage中，方便后续组件读取，避免每次都要请求导致闪烁
                    this.$storage.set('tutorialCategoryItems', res.data, 24 * 60 * 60);
                }
            }).catch(err => {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
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
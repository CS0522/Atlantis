<template>
    <div class="main">
        <!-- 主界面 -->
        <Carousel></Carousel>
        <div class="main-page">
            <div class="nav-location-box">
                <!-- 放入当前的路由路径 -->
                <!-- 怎么实现中文化 -->
                <div class="nav-location">
                    当前位置: &nbsp;<p style="display: inline;">{{ showPath }}</p>
                </div>
            </div>
            <!-- 放入主要页面导航，主要页面内放入子页面导航 -->
            <div class="nav-box" id="nav-box-anchor">
                
                    <router-link to="/page/introduction">
                        <div class="tab" tabindex="1">游戏介绍</div>
                    </router-link>
                
                    <router-link to="/page/news">
                        <div class="tab" tabindex="2">游戏资讯</div>
                    </router-link>

                    <router-link to="/page/tutorial">
                        <div class="tab" tabindex="3">游戏教程</div>
                    </router-link>
                
                    <router-link to="/page/forum">
                        <div class="tab" tabindex="4">游戏论坛</div>
                    </router-link>
                
                    <router-link to="/page/about">
                        <div class="tab" tabindex="5">关于车队</div>
                    </router-link>
            </div>
            <div class="sub-page">
                <router-view :key="$route.fullPath"></router-view> 
            </div>
        </div>
        
    </div>
</template>

<script>
import Carousel from './Carousel.vue'
import {translate} from '@/utils/translateRoute.js'

export default {
    name: 'MainPage',
    components: {
        Carousel
    },
    data()
    {
        return {
            
        }
    },
    created() {
    },
    methods:
    {
        // 实现获取router路由路径
        getPath()
        {
            let path = this.$route.path;
            // 当在论坛界面的时候，就只显示 forum
            // 拼接 [2, 4), 因为0是空, 1是page, 3可能为空, 不影响
            let pathList = path.split("/").slice(2, 4);
            // translate
            pathList = translate(pathList);

            return pathList.join(" / ");
        },
    },
    computed:
    {
        // 计算属性实时转换
        showPath: function() {
            return this.getPath();
        }
    }
}
</script>

<style scoped src="@/../public/css/mainpage-style.css">
</style>

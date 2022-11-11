<template>
    <div id="box">
        <div id="sub-nav-box">
            <div id="nav-title">
                关于车队
            </div>
            <div class="sub-nav">
                
                    <router-link to="/page/about/introduction">
                        <div class="sub-tab">车队简介</div>
                    </router-link>
                
                    <router-link to="/page/about/member">
                        <div class="sub-tab">车队成员</div>
                    </router-link>
                
                    <router-link to="/page/about/contact">
                        <div class="sub-tab">联系我们</div>
                    </router-link>
            </div>
        </div>
        <div id="sub-content">
            <router-view></router-view>
        </div>
    </div>
</template>

<style scoped src="@/../public/css/pages-style.css">
</style>

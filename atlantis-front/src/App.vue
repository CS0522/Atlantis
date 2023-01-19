<template>
    <div id="app">
        <!-- <router-view ></router-view> -->
        <router-view v-if="!toReload"></router-view>
    </div>
</template>

<script>
export default {
    name: 'App',
    components: {

    },
    data() {
        return {
            toReload: false,
        }
    },
    provide() {
        return {
            reload: this.reload
        }
    },

  methods: {
        reload() {
            this.toReload = true;
            this.$nextTick(function() {
                this.toReload = false;
            })
        }
    }
}
</script>

<style>
#app {
    font-family: 'Microsoft YaHei';
    -webkit-font-smoothing: antialiased;
    -moz-osx-font-smoothing: grayscale;
    text-align: center;
    /* color: #2c3e50; */
    color: #fff;
    margin-top: 60px;

    /* 设置全局文字不能被选中 */
    -webkit-user-select: none;

    -moz-user-select: none;

    -ms-user-select: none;

    user-select: none;

    zoom: 0.5;
}

@import url("@/../public/css/global-style.css");

</style>

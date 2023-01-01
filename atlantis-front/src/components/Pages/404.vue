<template>
    <div id="bg-blur">
        <div id="img-box">
            <router-link to="/">
                <img src="imgs/logo/atlantis-logo.png" class="logo-img" height="150px" />
            </router-link>
        </div>
        <div id="not-found-box">
            <img src="imgs/404/404.png" height="500px" style="margin: 10px"/>
        </div>
    </div>
</template>

<script>
import code from '@/utils/code';

export default {
    name: "page not found",
    methods: {
        doRedirect() {
            this.$notify.info({
                title: "5 秒后跳转回主页",
                offset: code.OFFSET
            })
            // 延迟5s跳转回主页
            setTimeout(() => {
                this.$router.push('/');
            }, 5000);
        }
    },
    created() {
        setTimeout(() => {
            this.doRedirect();
        }, 1000);
    }
}
</script>

<style scoped>
#not-found-box
{
    overflow: hidden;
    border-radius: 15px;
    width: 35%;
    position: relative;
    top: 150px;
    margin: 0 auto;

    background-color: rgba(252, 242, 241, 0.7);
    border-radius: 15px;
    animation: shadow 3s ease;
    animation-iteration-count: infinite;
    -webkit-backdrop-filter: blur(10px);
    backdrop-filter: blur(10px);  /* css滤镜 */

    box-shadow: inset 0px 0px 40px 40px rgba(252, 242, 241, 0.7);
}

.title
{
    margin: 0 0;
}

.logo-img
{
    transition: all .3s;
}

.logo-img:hover
{
    transform: scale(1.02);
    cursor: pointer;
}
</style>
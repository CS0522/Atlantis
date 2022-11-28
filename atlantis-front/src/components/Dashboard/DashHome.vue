<template>
    <div>
        <div id="title-box">
            <div style="font-size: 50px; width: 1500px; text-align: left;">
                <h1>欢迎进入管理员界面</h1></div>
        </div>
        <hr class="hr" />
        <div id="content-box">
            <div style="font-size: 60px">
                管理员 {{ accountInfo.username }}，请在左侧导航栏选择管理选项</div>
        </div>
    </div>
</template>

<script>
export default {
    data() {
        return {
            accountInfo: this.$storage.get('accountInfo'),
        }
    }
}

</script>

<style scoped src="@/../public/css/dashaccount-style.css">
</style>
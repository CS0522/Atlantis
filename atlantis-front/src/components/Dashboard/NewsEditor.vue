<template>
    <div>
        <div id="title-box">
            <div>资讯编辑</div>
        </div>
        <hr class="hr" />
        <div id="content-box">
        </div>
    </div>
</template>

<script>
import code from '@/utils/code';
import message from '@/utils/message';
import request from '@/utils/request';

export default {
    name: "newseditor",
    data() {
        return {
            item: {},
            form: {},
        }
    },
    methods: {
        load() {

        },
    },
    created() {
        this.load();
    },
    props: ['id'],
}
</script>

<style scoped src="@/../public/css/newseditor-style.css">
</style>
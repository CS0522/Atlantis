<template>
    <div>
        <div id="title-box">
            {{ title }}
        </div>
        <hr class="hr"/>
        <div id="sub-content">
            <router-view :key="$route.fullPath"></router-view>
        </div>
    </div>
</template>

<script>

export default {
    name: "forumcontent",
    props: ['topicIndex'],
    data() {
        return {
            // 获取 topic Items
            topicItems: [],

            title: '',

            showList: true,
            showThread: false,
        }
    },
    methods: {
        load() {
            this.topicItems = this.$storage.get("forumTopicItems");

            this.setTitle();
        },
        setTitle() {
            // 遍历数组中的对象，如果 topicIndex === index，则是对应的页面
            for (let i = 0; i < this.topicItems.length; i++)
            {
                if (String(this.topicItems[i].index) === this.topicIndex)
                {
                    this.title = this.topicItems[i].type;
                    // 保存到vuex中
                    this.$store.commit('setForumCurrentTopic', this.topicItems[i]);
                }
            }
        },
    },
    created() {
        this.load();
    }
}
</script>

<style scoped src="@/../public/css/subcontent-style.css">
</style>
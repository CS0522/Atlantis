<template>
    <div id="box">
        <div id="sub-nav-box">
            <div id="nav-title">
                游戏论坛
            </div>
            <div class="sub-nav">
                <ul
                    v-for="topicItem in topicItems" 
                    :key="topicItem.index"
                    style="list-style-type: none; margin: 0px; padding: 0px;">
                    <li>
                        <router-link :to="'/page/forum/' + topicItem.index + '/list'">
                            <div class="sub-tab">
                                {{ topicItem.type }}</div>
                        </router-link>
                    </li>
                </ul>
            </div>
        </div>
        <div id="sub-content">
            <router-view :key="$route.fullPath"></router-view>
        </div>
    </div>

</template>

<script>
export default {
    name: 'forum',
    data() {
        return {
            topicItems: [],
        }
    },
    methods: {
        load() {
            // 从 localStorage 中获取 forumTopicItems
            this.topicItems = this.$storage.get('forumTopicItems');
        },
    },

    created() {
        this.load();
    },
}
</script>


<style scoped src="@/../public/css/pages-style.css">
</style>

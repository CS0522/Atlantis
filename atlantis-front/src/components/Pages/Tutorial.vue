<template>
    <div id="box">
        <div id="sub-nav-box">
            <div id="nav-title">
                游戏教程
            </div>
            <div class="sub-nav">
                <ul
                    v-for="categoryItem in categoryItems" 
                    :key="categoryItem.index"
                    style="list-style-type: none; margin: 0px; padding: 0px;">
                    <li>
                        <router-link :to="'/page/tutorial/' + categoryItem.route + '/list'">
                            <div class="sub-tab">{{categoryItem.type}}</div>
                        </router-link>
                    </li>
                </ul>
            </div>
        </div>
        <div id="sub-content">
            <router-view></router-view>
        </div>
    </div>

</template>

<script>

export default {
    name: 'tutorial',
    data() {
        return {
            categoryItems: [],
        }
    },
    methods: {
        load() {
            this.categoryItems = this.$storage.get('tutorialCategoryItems');
        }
    },

    created() {
        this.load();
    }
}
</script>


<style scoped src="@/../public/css/pages-style.css">
</style>

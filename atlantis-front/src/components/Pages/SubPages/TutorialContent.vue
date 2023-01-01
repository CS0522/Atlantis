<template>
    <div>
        <div id="title-box">
            {{ title }}
        </div>
        <hr class="hr"/>
        <div id="sub-content">
            <router-view
            :key="$route.fullPath"></router-view>
        </div>
    </div>
</template>

<script>
export default {
    props: ['type'],
    name: "tutorialcontent",
    data() {
        return {
            // 获取category items
            categoryItems: [],

            title: '',
        }
    },
    methods: {
        load() {
            this.categoryItems = this.$storage.get('tutorialCategoryItems');

            this.setTitle();
        },
        setTitle() {
            // 遍历数组中的对象，如果 type === route，则是对应的页面
            for (let i = 0; i < this.categoryItems.length; i++)
            {
                if (this.categoryItems[i].route === this.type)
                {
                    this.title = this.categoryItems[i].type;
                    // 保存到vuex中
                    this.$store.commit('setTutorialCurrentCategory', this.categoryItems[i]);
                }
            }
        }
    },
    created() {
        this.load();
    }
}
</script>

<style scoped src="@/../public/css/subcontent-style.css">
</style>
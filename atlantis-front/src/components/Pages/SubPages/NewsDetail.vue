<template>
    <div>
        <div id="detail-box">
            <h1>
                {{ item.title }}
            </h1>
            <p>
                时间：{{ item.createDate }}
            </p>
            <div class="markdown-body">
                <VueMarkDown class="content" :source="item.content"></VueMarkDown>
            </div>
        </div>
        
    </div>
</template>

<script>
import code from '@/utils/code';
import request from '@/utils/request';
import message from '@/utils/message';
// 将markdown转换成html
import VueMarkDown from 'vue-markdown';
import 'github-markdown-css/github-markdown-dark.css'

export default {
    name: "detail",
    components: {
        VueMarkDown,
    },
    data() {
        return {
            // markdown要解析的数据
            item: {},
            
            id: '',
        }
    },
    methods: {
        load()
        {
            // 获取查询id
            this.getId();

            // 后台获取数据
            this.setItem();
        },
        getId() {
            const id = this.$route.query.id;
            this.id = id;
        },
        setItem() {
            request.get("/newsArticles/" + this.id).then(res => {
                if (res.code === code.GET_OK)
                {
                    this.item = res.data;
                    // markdown 解析
                    this.item.content;
                }
            }).catch(err => {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET,
                })
            })
        },
    },
    
    created() {
        this.load();
    },

    props: ['type'],
}
</script>

<style scoped src="@/../public/css/subcontentdetail-style.css">
</style>
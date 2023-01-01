import Vue from 'vue'
import Vuex from 'vuex'
import baseUrl from '@/utils/baseUrl.js'

Vue.use(Vuex)

const store = new Vuex.Store({
    // vuex里的store刷新页面后内容会丢失
    state: {

        // 用于DashContent子路由和AccountDialog之间的数据通信
        operation: '',
        objId: '',
        contentType: '',
        // 保存CRUD操作类型，以及目标item的id

        // 保存当前正在哪一个分类下
        tutorialCurrentCategory: {},
        // 保存当前正在哪一个话题下
        forumCurrentTopic: {},

        // img base url
        userImgBaseUrl: baseUrl.baseURL + "/users/",
        memberImgBaseUrl: baseUrl.baseURL + "/members/",
        newsArticleImgBaseUrl: baseUrl.baseURL + "/newsArticles/",
        // tutorialArticleImgBaseUrl: '',
    },
    mutations: {
        setOperation(state, newOperation){
            state.operation = newOperation;
        },
        setObjId(state, newId)
        {
            state.objId = newId;
        },
        setContentType(state, newContentType)
        {
            state.contentType = newContentType;
        },
        setTutorialCurrentCategory(state, newCategory)
        {
            state.tutorialCurrentCategory = newCategory;
        },
        setForumCurrentTopic(state, newTopic)
        {
            state.forumCurrentTopic = newTopic;
        },
    },
})

export default store
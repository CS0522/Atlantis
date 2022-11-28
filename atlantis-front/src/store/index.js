import Vue from 'vue'
import Vuex from 'vuex'
import request from '../utils/request'

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

        // img base url
        userImgBaseUrl: "http://localhost:8081/users/",
        memberImgBaseUrl: "http://localhost:8081/members/",
        newsArticleImgBaseUrl: "http://localhost:8081/newsArticles/",
        tutorialArticleImgBaseUrl: '',
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
        }
    },
})

export default store
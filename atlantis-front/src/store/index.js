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

        // tutorial page's category items
        // tutorialCategoryItems: [],

        // 登录成功后保存用户信息(X! vuex里的刷新后内容丢失)
        // accountInfo: {},
        // isLogin: false,
        // loginType: '',
        // admins, users

        // img base url
        userImgBaseUrl: "http://172.16.12.103:8081/users/",
        memberImgBaseUrl: "http://172.16.12.103:8081/members/",
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
        // setTutorialCategoryItems(state, newItems)
        // {
        //     state.tutorialCategoryItems  = newItems;
        // }
    },
})

export default store
import Vue from 'vue'
import App from './App.vue'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css';
import 'font-awesome/css/font-awesome.min.css'
import router from './router/index.js'
import store from './store/index.js'
import '@/../theme/index.css'
import {VueJsonp} from 'vue-jsonp'
import storage from './utils/storage.js'
import mavonEditor from 'mavon-editor'
import 'mavon-editor/dist/css/index.css'
// import 'github-markdown-css/github-markdown-dark.css'
import axios from 'axios'

Vue.prototype.$jsonp = VueJsonp;
Vue.prototype.$axios = axios;
Vue.prototype.$storage=storage;

Vue.config.productionTip = false

Vue.use(VueJsonp)
Vue.use(ElementUI)
Vue.use(mavonEditor)

new Vue({
  render: h => h(App),
  router: router,
  store: store,
}).$mount('#app')

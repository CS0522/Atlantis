// 轻度封装 axios 请求

import axios from 'axios'

const request = axios.create({
    baseURL: 'http://172.16.10.210:8081',
    timeout: 5000,
    // 解决前后session不一致问题
    withCredentials: true,
})

// request 拦截器
// 可以自请求发送前对请求做一些处理
request.interceptors.request.use(config => {
    config.headers['Content-Type'] = 'application/json;charset=utf-8';

    return config
}, error => {
    return Promise.reject(error)
});

// response 拦截器
// 可以在接口响应后统一处理结果
request.interceptors.response.use(
    response => {
        let res = response.data;

        return res;
    },
    error => {
        // console.log('err' + error)
        return Promise.reject(error)
    }
)

export default request
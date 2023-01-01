<template>
    <div id="bg-blur">
        <div id="img-box">
            <router-link to="/">
                <img src="imgs/logo/atlantis-logo.png" class="logo-img" height="150px" />
            </router-link>
        </div>
        <div id="login-box">
            <form class="login-form" :model="loginData">
                <h1 v-if="type==='user'" style="font-size: 80px">欢迎登录</h1>
                <h1 v-if="type==='admin'" style="font-size: 80px">管理登录</h1>
                <!-- 需要表单检验 -->
                <!-- 设置enter响应事件 -->
                <table class="table" cellpadding=10px cellspacing=15px>
                    <tr align="right">
                        <td>用户名: </td>
                        <td>
                            <input type="text" name="username" class="form-input"
                                    v-model="loginData.username" @focus="clearErrorMessage()"
                                    @keyup.enter="doLogin()"/>
                        </td>
                    </tr>
                    <tr align="right">
                        <td>密码: </td>
                        <td>
                            <input type="password" name="password" class="form-input"
                                    v-model="loginData.password" @focus="clearErrorMessage()"
                                    @keyup.enter="doLogin()"/>
                        </td>
                    </tr>
                    <!-- 用户登录，记住我功能 -->
                    <tr align="right" v-if="type==='user'">
                        <td>记住我: </td>
                        <td align="left">
                                <input type="checkbox" name="rememberme" class="form-check"
                                    v-model="isChecked"/>
                        </td>
                    </tr>
                </table>
                <div class="message-box">
                    <!-- 提示语句 -->
                    <span style="color: red">{{errorMessage}}</span>
                </div>
            </form>
            <div class="submit-box">
                    <button class="submit-button" @click="doLogin()">
                        <span v-if="!isLoading">登录</span>
                        <span v-if="isLoading">正在登录</span>
                    </button>
            </div>
            <div v-if="type==='user'" class="register-box">
                <router-link to="/signup" style="text-decoration: underline">没有账号? 点击这里</router-link>
            </div>
            <div v-if="type==='admin'" class="register-box">
                &nbsp;
            </div>
            <div v-if="type==='user'" class="admin-box">
            <!-- 跳转管理员登录界面 -->
                <router-link to="/login/admin" 
                            style="text-decoration: underline;">管理员登录</router-link>
            </div>
            <div v-if="type==='admin'" class="admin-box">
            <!-- 跳转管理员登录界面 -->
                <router-link to="/login/user" 
                            style="text-decoration: underline;">用户登录</router-link>
            </div>
        </div>
    </div>
</template>

<script>
import code from '@/utils/code';
import message from '@/utils/message';
import request from '@/utils/request';
import { hex_md5 } from '@/utils/createmd5';

export default {
    name: "login",
    data() {
        return {
            // 是否正在登录，用于修改登录按钮的字样
            isLoading: false,
            // 错误提示信息
            errorMessage: '',
            // 登录提交的表单
            loginData: {},
            // 登录角色
            typeStr: '',
            // 记住我是否勾选
            isChecked: '',
        }
    },
    created() {
        document.title = '登录';
        this.load();
    },
    methods:
    {
        // 页面初始加载，清除所有信息（如果有）
        load() {
            this.clearErrorMessage();
            this.clearForm();
            
            if (this.type === 'user')
            {
                // 记住我功能，首先检测是否有 rememberMe 保存在本地
                let savedLoginForm = this.$storage.get("rememberMe");
                if (savedLoginForm != null)
                {
                    this.loginData.username = savedLoginForm.username;
                    this.loginData.password = savedLoginForm.password;

                    this.isChecked = true;
                }
            }
            
        },

        // 加载时，检测apply和reset表中是否有消息，有则进行提示
        // 需要设置为同步
        async afterload() {
            let messageCount = 0;
            let res1 = await request.get("/applies")
            if (res1.code === code.GET_OK)
            {
                let items = res1.data;
                messageCount += items.length;
            }
            // let res2 = await request.get("/resets")
            // if (res2.code === code.GET_OK)
            // {
            //     let items = res2.data;
            //     messageCount += items.length;
            // }
            // console.log(messageCount);
            if (messageCount)
            {
                this.$notify.info({ 
                    title: messageCount + ' 条消息待处理，请前往消息中心查看',
                    offset: code.OFFSET
                })
            }
        },

        // 清除
        clearForm() {
            this.loginData = {};
        },
        clearErrorMessage() {
            this.errorMessage = '';
        },

        doLogin() {
            // console.log("md5 encrypt: " + hex_md5(this.loginData.password));
            if (!this.loginData.username || !this.loginData.password)
            {
                return;
            }

            this.isLoading = true;
            // console.log(type);
            if (this.type === 'admin') {
                this.typeStr = 'admins';
            }
            else if (this.type === 'user') {
                this.typeStr = 'users';
            }
            // 先判断是否已存在登录用户，存在则return并提示错误
            if (this.$storage.get("accountInfo") != null)
            {
                this.$notify.error({
                    title: '已存在登录用户！正在返回主界面',
                    offset: code.OFFSET
                })
                setTimeout(() => {
                    this.isLoading = false;
                    // 跳转回主界面
                    this.$router.push('/')
                }, 1000);
                return;
            }

            // 保存原始loginData
            let loginDataCopy = {
                username: ''
            }
            loginDataCopy.username = this.loginData.username;

            // 定义一个用来发送的变量
            let sendLoginData = JSON.parse(JSON.stringify(this.loginData));
            // 首先进行md5加密
            sendLoginData.password = hex_md5(sendLoginData.password);
            // 发送加密后数据
            request.post('/' + this.typeStr + '/login', sendLoginData).then(res => {
                    if (res.code === code.LOGIN_OK) {
                        // 登录成功，保存至本地
                        this.$storage.set("accountInfo", res.data, 24 * 60 * 60 * 1000);
                        this.$storage.set("loginType", this.typeStr, 24 * 60 * 60 * 1000);
                        // 记住我功能
                        if (this.typeStr === 'users')
                        {
                            if (this.isChecked)
                            {
                                this.$storage.set("rememberMe", loginDataCopy, 24 * 60 * 60 * 1000);
                            }
                            else
                            {
                                this.$storage.remove("rememberMe");
                            }
                        }
                        // 跳转至管理界面
                        if (this.typeStr === 'admins') {
                            this.$router.push('/dashboard')
                        }
                        else if (this.typeStr === 'users') {
                            this.$router.push('/personal')
                        }
                        // console.log("success");
                        this.$notify.success({
                            title: '登录成功',
                            offset: code.OFFSET
                        })
                        // 登录时弹出消息通知，是否有未读消息
                        if (this.typeStr === 'admins')
                        {
                            setTimeout(() => {
                                this.afterload();
                            }, 1500);
                        }
                    }
                    else if (res.code === code.STATUS_OFF) {
                        this.errorMessage = '该账号无权限登录';
                        this.isLoading = false;

                        this.loginData.password = '';
                    }
                    else if (res.code === code.LOGIN_ERR) {
                        this.errorMessage = '用户名或密码错误';
                        this.isLoading = false;

                        this.loginData.password = '';
                    }
                    else {
                        this.$notify.error({
                            title: '登录失败',
                            offset: code.OFFSET
                        })
                        this.isLoading = false;

                        this.loginData.password = '';
                    }
                }).catch(err => {
                    this.$notify.error({
                        title: message.REQUEST_ERR,
                        offset: code.OFFSET
                    })
                    this.isLoading = false;

                    this.loginData.password = '';
                })
        }
    },
    props: ['type'],
}
</script>

<style scoped src="@/../public/css/login-signup-style.css">
</style>
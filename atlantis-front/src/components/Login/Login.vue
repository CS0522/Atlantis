<template>
    <div id="bg-blur">
        <div id="img-box">
            <router-link to="/">
                <img src="imgs/logo/atlantis-logo.png" height="150px" />
            </router-link>
        </div>
        <div id="login-box">
            <!-- 需要提交 -->
            <form class="login-form" :model="loginData">
                <h1 v-if="type==='user'" style="font-size: 80px">欢迎登录</h1>
                <h1 v-if="type==='admin'" style="font-size: 80px">管理登录</h1>
                <!-- OK: TODO 提示消息框 -->
                <!-- 使用cookie 记住我 功能 -->
                <!-- 表单检验功能 -->
                <table class="table" cellpadding=10px cellspacing=15px>
                    <tr align="right">
                        <td>用户名: </td>
                        <td>
                            <input type="text" name="username" class="form-input"
                                    v-model="loginData.username" @focus="clearErrorMessage()"/>
                        </td>
                    </tr>
                    <tr align="right">
                        <td>密码: </td>
                        <td>
                            <input type="password" name="password" class="form-input"
                                    v-model="loginData.password" @focus="clearErrorMessage()"/>
                        </td>
                    </tr>
                    <tr align="right" v-if="type==='user'">
                        <td>记住我: </td>
                        <td align="left">
                                <input type="checkbox" name="rememberme" class="form-check"
                                    v-model="isChecked"/>
                        </td>
                    </tr>
                </table>
                <div class="message-box">
                    <!-- 用于插入提示语句 -->
                    <span style="color: red">{{errorMessage}}</span>
                </div>
            </form>
            <div class="submit-box">
                    <!-- 需要修改 click 事件 -->
                    <button class="submit-button" @click="doLogin(type)">
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

export default {
    name: "login",
    data() {
        return {
            isLoading: false,
            errorMessage: '',
            loginData: {},
            typeStr: '',

            isChecked: '',
        }
    },
    created() {
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
                // 记住我功能，首先检测是否有rememberMe保存在本地
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
            
            console.log(messageCount);
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

        // type用来判断登录的用户类型
        doLogin(type) {
            this.isLoading = true;
            // console.log(type);
            if (type === 'admin') {
                this.typeStr = 'admins';
            }
            else if (type === 'user') {
                this.typeStr = 'users';
            }
            // 先判断是否已存在登录用户，存在则return并提示错误
            if (this.$storage.get("accountInfo") != null)
            {
                this.$notify.error({
                    title: '已存在登录用户！请刷新界面',
                    offset: code.OFFSET
                })
                setTimeout(() => {
                    this.isLoading = false;
                }, 1000);
                return;
            }
            request.post('/' + this.typeStr + '/login',
                JSON.stringify(this.loginData)).then(res => {
                    if (res.code === code.LOGIN_OK) {
                        // 登录成功，保存至本地
                        this.$storage.set("accountInfo", res.data, 24 * 60 * 60 * 1000);
                        this.$storage.set("loginType", this.typeStr, 24 * 60 * 60 * 1000);
                        console.log(this.$storage.get('loginType'))
                        // 记住我功能
                        if (this.typeStr === 'users')
                        {
                            console.log("is checked: " + this.isChecked)
                            if (this.isChecked)
                            {
                                this.$storage.set("rememberMe", this.loginData, 24 * 60 * 60 * 1000);
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
                        // 登录时弹出消息通知
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
                        // this.clearForm();
                    }
                    else if (res.code === code.LOGIN_ERR) {
                        this.errorMessage = '用户名或密码错误';
                        this.isLoading = false;
                        // this.clearForm();
                    }
                    else {
                        this.$notify.error({
                            title: '登录失败',
                            offset: code.OFFSET
                        })
                        this.isLoading = false;
                        // this.clearForm();
                    }
                }).catch(err => {
                    this.$notify.error({
                        title: message.REQUEST_ERR,
                        offset: code.OFFSET
                    })
                    this.isLoading = false;
                    // this.clearForm();
                })
        }
    },
    props: ['type'],
}
</script>

<style scoped src="@/../public/css/login-signup-style.css">

/* 需要背景模糊效果 */

</style>
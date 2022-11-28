<template>
    <div id="bg-blur">
        <div id="img-box">
            <router-link to="/">
                <img src="imgs/logo/atlantis-logo.png" height="150px" />
            </router-link>
        </div>
        <div id="signup-box">
            <form class="signup-form" :model="signupData">
                <h1 style="font-size: 80px">欢迎注册</h1>
                <table class="table" cellpadding=10px cellspacing=15px>
                    <tr align="right">
                        <td>用户名: </td>
                        <td>
                            <input type="text" name="username" class="form-input"
                                    v-model="signupData.username" @focus="clearErrorMessage()"/>
                        </td>
                    <tr align="right">
                        <td>昵称: </td>
                        <td>
                            <input type="text" name="nickname" 
                            class="form-input" v-model="signupData.nickname" @focus="clearErrorMessage()"/>
                        </td>
                    </tr>
                    </tr>
                    <tr align="right">
                        <td>密码: </td>
                        <td>
                            <input type="password" name="password" class="form-input"
                                    v-model="signupData.password" @focus="clearErrorMessage()"/>
                        </td>
                    </tr>
                    <tr align="right">
                        <td>确认: </td>
                        <td>
                            <input type="password" name="confirm" class="form-input"
                                    v-model="signupData.confirm" @focus="clearErrorMessage()"/>
                        </td>
                    </tr>
                    <tr align="right">
                        <td>验证码: </td>
                        <td style="text-align:justify;text-justify:distribute-all-lines;text-align-last:justify">
                            <div id="verifycode-box">
                                <input type="text" name="verifycode" class="form-input" style="width: 95%" 
                                        v-model="verifyCode" @focus="clearErrorMessage()"/>
                            </div>
                            &nbsp;
                            <div id="verifycode-box">
                                <img :src="verifyCodeImgBase64" width="100%" height="100%"
                                    @click="getVerifyCodeImg()"/>
                            </div> 
                        </td>
                    </tr>
                </table>
                <div class="message-box">
                    <!-- 插入提示语句 -->
                    <span style="color: red">{{errorMessage}}</span>
                </div>               
            </form>
            <div class="submit-box">
                        <button class="submit-button" @click="doSignup()">
                            <span v-if="!isLoading">注册</span>
                            <span v-if="isLoading">正在注册</span>
                        </button>
            </div>
            <div class="register-box">
                <router-link to="/login/user" style="text-decoration: underline">已有账号? 返回登录</router-link>
            </div>
        </div>
    </div>
</template>

<script>
import code from '@/utils/code';
import message from '@/utils/message';
import request from '@/utils/request';
import { checkIsValid } from '@/utils/checkvalid';
import { hex_md5 } from '@/utils/createmd5';

export default {
    name: "signup",
    data() {
        return {
            isLoading: false,
            errorMessage: '*6-15个字符，仅包含英文大小写、数字及下划线 (昵称除外)',

            signupData: {},

            verifyCodeImgBase64: '',
            verifyCode: '',
        }
    },
    created() {
        this.load();
    },
    methods:{
        clearErrorMessage() {
            this.errorMessage = '*6-15个字符，仅包含英文大小写、数字及下划线 (昵称除外)';
        },
        clearForm() {
            this.signupData = {};
        },
        getVerifyCodeImg() {
            // 请求或更换前先清除验证码
            this.verifyCode = '';
            request.get("/verify").then(res => {
                this.verifyCodeImgBase64 = res.data;
            })
        },

        load() {
            this.clearForm();
            this.getVerifyCodeImg();
        },

        doSignup() {
            this.isLoading = true;
            // 首先发送验证码进行确认
            request.post("/verify/" + this.verifyCode).then(res => {
                // succeeded
                if (res.code === code.VERIFY_OK) {
                    // 验证码确认完毕，再进行注册验证
                    // 不满足输入要求
                    if (!checkIsValid([this.signupData.username, this.signupData.password])) {
                        this.clearForm();
                        this.isLoading = false;
                        this.getVerifyCodeImg();

                        return;
                    }
                    // 昵称为空
                    if (!this.signupData.nickname) {
                        this.errorMessage = '昵称不为空';
                        this.isLoading = false;
                        this.getVerifyCodeImg();

                        return;
                    }
                    // 两次密码不正确
                    if (this.signupData.password !== this.signupData.confirm) {
                        this.errorMessage = '两次密码不相同';
                        this.signupData.confirm = '';
                        this.isLoading = false;
                        this.getVerifyCodeImg();

                        return;
                    }

                    // md5加密
                    this.signupData.password = hex_md5(this.signupData.password);
                    // 新增
                    request.post("/users", this.signupData).then(res => {
                        if (res.code === code.INSERT_OK) {
                            this.$notify.success({
                                title: '注册成功！正在返回登录页面',
                                offset: code.OFFSET
                            })
                            setTimeout(() => {
                                this.$router.push('/login/user');
                            }, 2000);
                        }
                        else {
                            this.$notify.error({
                                title: '注册失败，可能已经存在该用户',
                                offset: code.OFFSET
                            })
                            this.isLoading = false;
                            this.getVerifyCodeImg();
                        }
                    }).catch(err => {
                        this.$notify.error({
                            title: message.REQUEST_ERR,
                            offset: code.OFFSET
                        })
                        this.isLoading = false;
                        this.getVerifyCodeImg();
                    })
                }
                // failed
                else
                {
                    this.errorMessage = '验证码错误';
                    this.isLoading = false;
                    // refresh verify code
                    this.getVerifyCodeImg();
                }
            }).catch(err => {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
                this.isLoading = false;
                this.getVerifyCodeImg();
            })
        },
    },
}
</script>

<style scoped src="@/../public/css/login-signup-style.css">
</style>
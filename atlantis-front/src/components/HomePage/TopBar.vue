<template>
    <div class="top-bar">
        <div class="logo" style="display:inline-block; border-radius: 15px;">
            &nbsp;
            <!-- 设置点击图片跳转至首页 -->
            <router-link to="/" target="_self">
                <img src="@/../public/imgs/logo/atlantis-logo.png" height="135px" />
            </router-link>
        </div>
        <div class="search-bar">
            <el-input placeholder="搜索 (开发中)" v-model="searchInput" class="search-input" disabled>
                <!-- 点击进行搜索 -->
                <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>
        </div>
        <div class="tool-bar">
            <table cellpadding=10px; cellspacing=15px>
                <tr>
                    <th>
                        <DropDownMenu></DropDownMenu>
                    </th>
                    <!-- <th><a href="#">夜间模式</a></th> -->
                    <!-- 消息中心、个人主页需要登录后才能进行显示 -->
                    <th v-if="isLogin && (adminOrUser === 'users')">
                        <router-link to="/personal/message" target="_blank">消息中心</router-link>
                    </th>
                    <th v-if="isLogin">
                        <router-link to="/personal" target="_blank" v-if="adminOrUser === 'users'">
                            个人主页</router-link>
                        <router-link to="/dashboard" target="_blank" v-if="adminOrUser === 'admins'">
                            后台管理</router-link>
                    </th>
                    <th>
                        <router-link to="/login/user" target="_blank" v-if="!isLogin">登录/注册</router-link>
                        <!-- 点击用户名，出现问候和天气 -->
                        <a v-if="isLogin && adminOrUser === 'admins'" @click="greeting()">
                            {{ info.username }}</a>
                        <a v-if="isLogin && adminOrUser === 'users'" @click="greeting()">
                            {{ info.nickname }}</a>
                    </th>
                    <th v-if="isLogin">
                        <img src="imgs/icons/logout.png" class="outLogin" alt="退出" @click="logout()" />
                    </th>
                </tr>
            </table>
        </div>
    </div>
</template>

<script>
import code from '@/utils/code';
import message from '@/utils/message';
import request from '@/utils/request';
import DropDownMenu from './DropDownMenu.vue'
import { getWeather } from '@/utils/getweather'

export default {
    name: "TopBar",
    components: { DropDownMenu },
    inject: ['doTopBarReload'],
    data() {
        return {
            isLogin: false,
            // 是admin登录还是user登录
            adminOrUser: '',
            info: {},
            typeStr: '',

            // 用于greeting中获取地理位置、展示天气
            key_getLocation: 'NKTBZ-X3Y3X-2VQ4O-TXKB4-JM2FV-4TF5S',
            result: {},

            // search
            searchInput: '',
        }
    },
    created() {
        this.load();
    },
    methods:
    {
        load() {
            // set adminOrUser
            this.adminOrUser = this.$storage.get('loginType');
            const accountInfo = this.$storage.get('accountInfo');
            // console.log("adminOrUser: " + this.adminOrUser)

            if (accountInfo !== null) {
                this.info = accountInfo;
                this.isLogin = true;

                // set typeStrs
                this.setTypeStr();
            }
        },
        // set typeStrs
        setTypeStr() {
            if (this.adminOrUser === 'admins') {
                // admin
                this.typeStr = 'admins';
            }
            else if (this.adminOrUser === 'users') {
                // user
                this.typeStr = 'users';
            }
        },

        logout() {
            request.post('/' + this.typeStr + '/logout', this.info).then(res => {
                if (res.code === code.LOGOUT_OK) {
                    // 删除响应数据
                    this.$storage.remove('accountInfo');
                    this.$storage.remove('loginType');
                    this.isLogin = false;

                    // reload
                    this.doTopBarReload();
                    // back to main page
                    this.$notify.success({
                        title: '退出成功，正在返回首页',
                        offset: code.OFFSET
                    })
                    setTimeout(() => {
                        window.location.href = "/";
                    }, 2000);
                }
                else {
                    this.$notify.error({
                        title: '退出失败，请重试',
                        offset: code.OFFSET
                    })
                }
            }).catch(err => {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            })
        },

        // greetings
        greeting() {
            this.getCityAndWeather();
        },
        // get city and weather
        getCityAndWeather() {
            var data = {
                key: this.key_getLocation, //申请的密钥
            }
            var url = 'https://apis.map.qq.com/ws/location/v1/ip' //地理位置信息接口
            data.output = 'jsonp'
            this.$jsonp(url, data).then((res) => {
                // js中的天气查询函数
                getWeather(res.result.ad_info.city).then(res => {
                    this.result = res;
                });

                // 弹出问候
                if (this.adminOrUser === 'admins') {
                    this.$notify.info({
                        title: '你好，' + this.info.username,
                        offset: code.OFFSET
                    })
                }
                else if (this.adminOrUser === 'users') {
                    this.$notify.info({
                        title: '你好，' + this.info.nickname,
                        offset: code.OFFSET
                    })
                }

                // 弹出显示周几
                let days = ['周日', '周一', '周二', '周三', '周四', '周五', '周六']
                setTimeout(() => {
                    this.$notify.info({
                        title: '今天是' + days[new Date().getDay()],
                        offset: code.OFFSET
                    })
                }, 1500);

                // 弹出显示地理和天气
                setTimeout(() => {
                    this.$notify.info({
                        title: this.result.city + '天气' + this.result.weather +
                            '，当前温度 ' + this.result.temperature + ' ℃',
                            offset: code.OFFSET
                    })
                }, 3000);
                setTimeout(() => {
                    this.$notify.info({
                        title: '祝每天生活愉快！',
                        offset: code.OFFSET
                    })
                }, 4500)

            }).catch(err => {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            })
        },
    },
}
</script>

<style scoped src="@/../public/css/topbar-style.css">

</style>
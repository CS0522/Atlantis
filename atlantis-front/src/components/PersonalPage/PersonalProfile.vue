<template>
    <div>
        <hr class="hr" />
        <div id="content-box">
                <br/>
                <form id="userprofile-form">
                    <table class="table" cellpadding=10px cellspacing=15px>
                        <tr align="right">
                            <td>用户名：</td>
                            <td>
                                <input type="text" name="username" 
                                class="form-input" v-model="userData.username" 
                                disabled/>
                            </td>
                            <td>
                                <p style="color: red;">
                                    *用户名不可修改
                                </p>
                            </td>
                        </tr>
                        <tr align="right">
                            <td>昵称：</td>
                            <td>
                                <input type="text" name="nickname" 
                                class="form-input" v-model="userData.nickname"/>
                            </td>
                        </tr>
                        <tr align="right">
                            <td>简介：</td>
                            <td>
                                <textarea v-model="userData.description" name="description"
                                class="form-input" style="resize: vertical; min-height: 100px">
                                </textarea>
                            </td>
                        </tr>
                        <tr align="right">
                            <td>性别：</td>
                            <td>
                                <!-- 根据获取到的数据显示绑定，根据选中的数据也要绑定 -->
                                <select class="form-input" 
                                name="gender"  id="selectGender" v-model="userData.gender">
                                    <option value="">暂不透露</option>
                                    <option value="male">男</option>
                                    <option value="female">女</option>
                                </select>
                            </td>
                        </tr>
                        <tr align="right">
                            <td>生日：</td>
                            <td>
                                <input type="date" name="birth" 
                                class="form-input" v-model="userData.birth"/>
                            </td>
                        </tr>
                        <tr align="right">
                            <td>加入时间：</td>
                            <td>
                                <input type="date" name="join" 
                                class="form-input" v-model="userData.joinDate"
                                disabled/>
                            </td>
                        </tr>
                    </table>
                </form>
                <div id="operation-box">
                    <table class="table-operation" cellpadding=10px cellspacing=15px>
                        <tr align="center">
                            <td>
                                <button class="operation" style="background-color: #8beeff"
                                @click="doUpdateInfo()">资料更新</button>
                            </td>
                            <td>
                                <button class="operation"
                                @click="reset()">重置</button>
                            </td>
                        </tr>
                    </table>
                </div>

                <form id="changepwd-form">
                    <span style="color: red">{{ errorMessage }}</span>                 
                    <table class="table" cellpadding=10px cellspacing=15px>
                        <tr align="right">
                            <td>原密码：</td>
                            <td>
                                <input type="password" name="old-password"
                                class="form-input" v-model="oldPwd"/>
                            </td>
                        </tr>
                        <tr align="right">
                            <td>新密码：</td>
                            <td>
                                <input type="password" name="new-password"
                                class="form-input" v-model="newPwd"/>
                            </td>
                            <td>
                                <p style="color: red;">
                                    *新旧密码不相同
                                </p>
                            </td>
                        </tr>
                        <tr align="right">
                            <td>确认密码：</td>
                            <td>
                                <input type="password" name="new-confirm"
                                class="form-input" v-model="confirmPwd"/>
                            </td>
                        </tr>
                    </table>
                </form>
                <div id="operation-box">
                    <table class="table-operation" cellpadding=10px cellspacing=15px>
                        <tr align="center">
                            <td>
                                <button class="operation" style="background-color: #8beeff"
                                @click="doUpdatePwd()">密码更新</button>
                            </td>
                            <td>
                                <button class="operation"
                                @click="reset()">重置</button>
                            </td>
                        </tr>
                    </table>
                </div>
        </div>
    </div>
</template>

<script>
import request from '@/utils/request';
import message from '@/utils/message';
import code from '@/utils/code';
import moment from 'moment';
import { checkIsValid } from '@/utils/checkvalid';
import { hex_md5 } from '@/utils/createmd5';

export default {
    name: "dashcontent",
    data() {
        return {
            id: this.$storage.get("accountInfo").id,
            // 后端传入数据
            userData: {},
            
            errorMessage: '*6-15个字符，仅包含英文大小写、数字及下划线',

            oldPwd: '',
            newPwd: '',
            confirmPwd: '',
        }
    },
    methods:
    {
        load() {
            // console.log("loading...")
            // console.log(this.id);
            this.setUserData();
            // 每次加载密码清零
            this.clearPwd();
        },
        clearPwd() {
            this.oldPwd = '';
            this.newPwd = '';
            this.confirmPwd = '';
        },
        setUserData() {
            request.get('/users/' + this.id).then(res => {
                if (res.code === code.GET_OK)
                {
                    this.userData = res.data;
                }
                else
                {
                    this.$notify.error({
                        title: message.GET_ERR,
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

        // 进行更新info操作
        doUpdateInfo() {
            // 昵称不能为空
            if (this.userData.nickname.trim() === '')
            {
                this.$notify.error({
                    title: '昵称不能为空',
                    offset: code.OFFSET
                })
                return;
            }
            if (this.userData.birth > moment().format()) {
                this.$notify.error({
                    title: '日期设置错误',
                    offset: code.OFFSET
                })
                return;
            }
            // 去除昵称中的所有空格！！！
            this.userData.nickname.replace(/\s/g,"");

            request.put('/users/info', this.userData).then(res => {
                if (res.code === code.UPDATE_OK) {
                    this.load();
                    this.$notify.success({
                        title: message.UPDATE_OK,
                        offset: code.OFFSET
                    })
                }
                else {
                    this.load();
                    this.$notify.error({
                        title: message.UPDATE_ERR,
                        offset: code.OFFSET
                    })
                }
            }).catch(err => {
                this.load();
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            })
        }, 
        // 进行更新pwd操作
        doUpdatePwd() {
            // 检查密码正确性
            if (hex_md5(this.oldPwd) !== this.userData.password)
            {
                this.$notify.error({
                    title: '原密码错误',
                    offset: code.OFFSET
                })
                this.clearPwd();
            }
            else if (this.oldPwd === this.newPwd)
            {
                this.$notify.error({
                    title: '新旧密码不能相同',
                    offset: code.OFFSET
                })
                // refresh the pwds
                // this.clearPwd();
                this.newPwd = '';
                this.confirmPwd = '';
            }
            else if (this.newPwd !== this.confirmPwd)
            {
                this.$notify.error({
                    title: '两次密码不一致',
                    offset: code.OFFSET
                })
                // this.clearPwd();
                this.newPwd = '';
                this.confirmPwd = '';
            }
            // 成功
            else if (hex_md5(this.oldPwd) === this.userData.password && this.newPwd === this.confirmPwd)
            {
                if (!checkIsValid([this.newPwd]))
                {
                    this.$notify.error({
                        title: '6-15个字符，仅包含英文大小写、数字及下划线',
                        offset: code.OFFSET
                    })
                    // this.clearPwd();
                    this.newPwd = '';
                    this.confirmPwd = '';
                    return;
                }

                // 定义一个发送的变量
                let sendUserData = JSON.parse(JSON.stringify(this.userData));
                sendUserData.password = this.newPwd;
                // md5加密
                sendUserData.password = hex_md5(sendUserData.password);

                request.put('/users/password', sendUserData).then(res => {
                    if (res.code === code.UPDATE_OK) {
                        this.load();
                        this.$notify.success({
                            title: message.UPDATE_OK,
                            offset: code.OFFSET
                        })
                    }
                    else {
                        this.load();
                        this.$notify.error({
                            title: message.UPDATE_ERR,
                            offset: code.OFFSET
                        })
                    }
                }).catch(err => {
                    this.$notify.error({
                        title: message.REQUEST_ERR,
                        offset: code.OFFSET
                    })
                })
            }
        },

        reset() {
            this.load();
            this.$notify.info({
                title: '已重置',
                offset: code.OFFSET
            })
        },
    },

    created ()
    {
        this.load();
    },
}
</script>

<style scoped src="@/../public/css/personalprofile-style.css">

</style>
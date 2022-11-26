<template>
    <div id="dialog-box">
        <div id="title-box">
            {{ title }}
        </div>
        <hr class="hr" />
        <div id="content-box">
            <span style="color: red">{{ errorMessage }}</span>
            <!-- 当title为更新时，显示更新的界面 -->
            <div v-show="title === '更新'">
                <form class="update-form" :model="form">
                    <table class="table" cellpadding=10px cellspacing=15px>
                        <tr align="right">
                            <td>用户名：</td>
                            <td>
                                <input type="text" name="username" class="form-input" 
                                        v-model="item.username"/>
                            </td>
                        </tr>
                        <!-- 根据type判断是否显示 -->
                        <tr align="right" v-if="type === 'user'">
                            <td>昵称：</td>
                            <td>
                                <input type="text" name="nickname" 
                                class="form-input" v-model="item.nickname" @focus="clearHint()"/>
                            </td>
                        </tr>
                        <tr align="right">
                            <td>简介：</td>
                            <td>
                                <textarea name="description" class="form-input"
                                        style="resize: vertical; min-height: 100px" 
                                        v-model="item.description">
                            </textarea>
                            </td>
                        </tr>
                        <!-- 根据type判断是否显示 -->
                        <tr align="right" v-if="type === 'user'">
                            <td>性别：</td>
                            <td>
                                <!-- 根据获取到的数据显示绑定，根据选中的数据也要绑定 -->
                                <select class="form-input" 
                                name="gender"  id="selectGender-update" v-model="item.gender">
                                    <option value="">暂不透露</option>
                                    <option value="male">男</option>
                                    <option value="female">女</option>
                                </select>
                            </td>
                        </tr>
                        <!-- 根据type判断是否显示 -->
                        <!-- 修改：时间格式显示问题 -->
                        <tr align="right" v-if="type === 'user'">
                            <td>生日：</td>
                            <td>
                                <input type="date" name="birth" min="1920-01-01"
                                class="form-input" v-model="item.birth" false/>
                            </td>
                        </tr>
                        <tr align="right">
                            <td>加入时间：</td>
                            <td>
                                <input type="date" name="join" class="form-input" 
                                        v-model="item.joinDate"
                                        disabled/>
                            </td>
                            <td>
                                <p style="color: red;">
                                    *不可修改
                                </p>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <!-- 当title为删除时，显示此内容 -->
            <div v-show="title === '删除'">
                <div style="height: 300px; line-height: 300px; font-size: 40px; color: red;">
                    确定要执行删除操作么？
                </div>
            </div>
            <!-- 当title为修改密码时，显示此内容 -->
            <div v-show="title === '修改密码'">         
                <form class="update-form" :model="form">
                    <table class="table" cellpadding="10px" cellspacing="15px">
                        <tr align="right">
                            <td>新密码：</td>
                            <td>
                                <input type="password" name="new-password" class="form-input" 
                                        v-model="newPwd" @focus="clearHint()"/>
                            </td>
                        </tr>
                        <tr align="right">
                            <td><span style="color: red">*</span>请确认：</td>
                            <td>
                                <input type="password" name="confirm" class="form-input" 
                                        v-model="confirmPwd" @focus="clearHint()"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <!-- 当title为添加账号时 -->
            <div v-show="title === '添加账号'">
                <form class="add-form" :model="form">
                    <table class="table" cellpadding=10px cellspacing=15px>
                        <tr align="right">
                            <td>用户名：</td>
                            <td>
                                <input type="text" name="username" class="form-input" 
                                        v-model="form.username"/>
                            </td>
                        </tr>
                        <!-- 根据type判断是否显示 -->
                        <tr align="right" v-if="type === 'user'">
                            <td>昵称：</td>
                            <td>
                                <input type="text" name="nickname" 
                                class="form-input" v-model="form.nickname" @focus="clearHint()"/>
                            </td>
                        </tr>
                        <tr align="right">
                            <td>密码：</td>
                            <td>
                                <input type="password" name="new-password" class="form-input" 
                                       v-model="form.password"/>
                            </td>
                        </tr>
                        <!-- 根据type判断是否显示 -->
                        <tr align="right" v-if="type === 'user'">
                            <td>性别：</td>
                            <td>
                                <!-- 根据获取到的数据显示绑定，根据选中的数据也要绑定 -->
                                <select class="form-input" 
                                name="gender" id="selectGender-insert" v-model="form.gender">
                                    <option value="">暂不透露</option>
                                    <option value="male">男</option>
                                    <option value="female">女</option>
                                </select>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
        </div>
        <hr class="hr" />
        <div id="operation-box">
            <table class="table-operation" cellpadding=10px cellspacing=15px>
                <tr align="center">
                    <td>
                        <!-- 改成button -->
                        <button class="operation" @click="clickConfirm()" 
                                style="background-color: #8beeff">确定</button>
                    </td>
                    <td>
                        <!-- 改成button -->
                        <button class="operation" @click="cancel()">取消</button>
                    </td>
                </tr>
            </table>
        </div>
    </div>
</template>

<script>
import request from '@/utils/request';
import code from '@/utils/code';
import message from '@/utils/message';
import moment from 'moment'
import { checkIsValid } from '@/utils/checkvalid';

export default {
    name: "accountdialog",
    data() {
        return {
            // 从vuex的state中取得响应数据
            type: this.$store.state.contentType,
            objId: this.$store.state.objId,
            operation: this.$store.state.operation,

            // 拼接字符串
            typeStr: '',
            // 错误消息
            errorMessage: '*6-15个字符，仅包含英文大小写、数字及下划线',

            newPwd: '',
            confirmPwd: '',

            // 向后端提交表单，用于添加账号时
            form: {},

            title: '',
            // 用于更新操作
            item: {},
        }
    },
    created()
    {
        this.load();
    },
    methods: {
        cancel() {
            this.$emit('close');
        },
        clickConfirm() {
            // 添加账号，则要发送对应的item
            if (this.operation === 'insert')
            {
                this.doInsert();
            }
            // 删除账号
            else if (this.operation === 'delete')
            {
                this.doDelete()
            }
            // 更新对应账号
            else if (this.operation === 'updateinfo')
            {
                this.doUpdateInfo()
            }
            else if (this.operation === 'updatepwd')
            {
                this.doUpdatePwd()
            }
        },
        // 清除错误提示
        clearHint() {
            if (this.type === 'user')
            {
                if (this.operation === 'updatepwd')
                {
                    this.errorMessage = '*6-15个字符，仅包含英文大小写、数字及下划线';
                }
                else
                {
                    this.errorMessage = '*6-15个字符，仅包含英文大小写、数字及下划线 (昵称除外)'
                } 
            }
            else if (this.type === 'admin')
            {
                this.errorMessage = '*6-15个字符，仅包含英文大小写、数字及下划线';
            }
        },

        // 设置typeStr
        setTypeStr() {
            if (this.type === 'admin')
            {
                this.typeStr = 'admins';
            }
            else if (this.type === 'user')
            {
                this.typeStr = 'users';
            }
        },
        // 设置标题
        setTitle() {
            if (this.operation === 'updateinfo')
            {
                this.title = '更新'
                if (this.type === 'user')
                {
                    this.errorMessage = '*6-15个字符，仅包含英文大小写、数字及下划线 (昵称除外)'
                }
            }
            else if (this.operation === 'delete')
            {
                this.title = '删除'
                this.errorMessage = '';
            }
            else if (this.operation === 'updatepwd')
            {
                this.title = '修改密码'
            }
            else if (this.operation === 'insert')
            {
                this.title = '添加账号'
                if (this.type === 'user')
                {
                    this.errorMessage = '*6-15个字符，仅包含英文大小写、数字及下划线 (昵称除外)'
                }
            }
        },
        // 设置item
        setItem() {
            request.get(''+ this.typeStr + '/' + this.objId).then(res => {
                if (res.code === code.GET_OK) {
                    this.item = res.data;
                }
            }).catch(err => {
                console.log(err)
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: 70
                });
            })
        },

        // 组件加载时进行item显示
        load() {
            // 先设置typeStr
            this.setTypeStr();
            console.log(this.typeStr);
            // 设置标题
            this.setTitle();
            // 设置item
            this.setItem();
        },


        // 功能函数，CRUD
        // 发送表单
        // 表单检验
        doInsert() {
            // 先进行校验
            // is invalid
            if (!checkIsValid([this.form.username, this.form.password]))
            {
                this.form = {};
                return;
            }
            // 如果是user, 昵称不能为空
            if (this.type === 'user')
            {
                if (this.form.nickname.trim() === '')
                {
                    this.errorMessage = '昵称不能为空'
                    return;
                }
                this.form.nickname = this.form.nickname.trim();
            }
            // is valid
            // // 去除昵称中的所有空格！！！
            // if (this.type === 'user')
            // {
            //     this.form.nickname.replace(/\s/g,"");
            // }
            request.post('/' + this.typeStr, this.form).then(res => {
                    if (res.code === code.INSERT_OK) {
                        this.$notify.success({
                            title: message.INSERT_OK,
                            offset: 70
                        })
                        this.$emit('confirm');
                    }
                    else {
                        this.$notify.error({
                            title: message.INSERT_ERR + '，请检查是否存在同名用户',
                            offset: 70
                        })
                    }
                }).catch(error => {
                    this.$notify.error({
                        title: message.REQUEST_ERR,
                        offset: 70
                    })
                })
        },

        doDelete() {
            request.delete('/' + this.typeStr + '/' + this.objId).then(res => {
                if (res.code === code.DELETE_OK) {
                    this.$notify.success({
                        title: message.DELETE_OK,
                        offset: 70
                    })
                    this.$emit("confirm");
                }
                else {
                    this.$notify.error({
                        title: message.DELETE_ERR,
                        offset: 70
                    })
                }
            }).catch(err => {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: 70
                })
            })
        },

        doUpdateInfo() {
            // 需要表单检验
            // is invalid
            if (!checkIsValid([this.item.username]))
            {
                // 将input还原
                this.load();
                return;
            }
            // 如果是user，昵称不能为空，生日在今天之前
            if (this.type === 'user')
            {
                if (this.item.nickname.trim() === '')
                {
                    this.errorMessage = '昵称不能为空'
                    return;
                }
                if (this.item.birth > moment().format())
                {
                    this.errorMessage = '日期设置错误'
                    return;
                }

                this.item.nickname = this.item.nickname.trim();
            }
            // is valid
            // if (this.type === 'user')
            // {
            //     this.form.nickname.replace(/\s/g,"");
            // }
            // 发送的js对象要进行JSON格式化
            request.put('/' + this.typeStr + '/info', JSON.stringify(this.item)).then(res => {
                    if (res.code === code.UPDATE_OK)
                    {
                        this.$notify.success({
                            title: message.UPDATE_OK,
                            offset: 70
                        })
                        this.$emit("confirm");
                    }
                    else 
                    {
                        this.$notify.error({
                            title: message.UPDATE_ERR,
                            offset: 70
                        })
                    }
                }).catch(err => {
                    this.$notify.error({
                        title: message.REQUEST_ERR,
                        offset: 70
                    })
                })

        },

        // 简单测试，之后需要进行修改
        // 加密、解密
        // 管理员修改密码无需比较原密码
        doUpdatePwd() {
            // 输入正确
            // 需要表单检验
            // is invalid
            if (!checkIsValid([this.newPwd, this.confirmPwd]))
                {
                    this.newPwd = '';
                    this.confirmPwd = '';
                    return;
                }
                // is valid
            if (this.newPwd === this.confirmPwd) {
                // item设置新密码
                this.item.password = this.newPwd;
                // 发送新数据
                request.put('/' +this.typeStr + '/password', JSON.stringify(this.item)).then(res => {
                    if (res.code === code.UPDATE_OK) {
                        this.$notify.success({
                            title: message.UPDATE_OK,
                            offset: 70
                        })
                        this.$emit("confirm");
                    }
                    else {
                        this.$notify.error({
                            title: message.UPDATE_ERR,
                            offset: 70
                        })
                    }
                }).catch(err => {
                    this.$notify.error({
                        title: message.REQUEST_ERR,
                        offset: 70
                    })
                })
            }
            // 两次输入不匹配
            else
            {
                // 清空
                this.confirmPwd = '';
                this.errorMessage = "两次密码不同"
            }
        }
    },
}
</script>

<style scoped src="@/../public/css/dialog-style.css">
</style>
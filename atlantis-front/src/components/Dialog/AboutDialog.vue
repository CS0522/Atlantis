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
                            <td>成员名：</td>
                            <td>
                                <input type="text" name="realname" class="form-input" 
                                        v-model="item.realname" @focus="clearHint()"/>
                            </td>
                        </tr>
                        <tr align="right">
                            <td>简介：</td>
                            <td>
                                <textarea name="description" class="form-input"
                                        style="resize: vertical; min-height: 100px" 
                                        v-model="item.description" @focus="clearHint()">
                            </textarea>
                            </td>
                        </tr>
                        <!-- 根据type判断是否显示 -->
                        <!-- 修改：时间格式显示问题 -->
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
                        <tr align="right">
                            <td>队内职位：</td>
                            <td>
                                <!-- <input type="text" name="position" class="form-input" 
                                        v-model="item.position" @focus="clearHint()"/> -->
                                <select class="form-input" @focus="clearHint()"
                                    name="position" v-model="item.position">
                                    <option value="队长">队长</option>
                                    <option value="群主">群主</option>
                                    <option value="管理">管理</option>
                                    <option value="成员">成员</option>
                                    <option value="测试">测试</option>
                                </select>
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
            <!-- 当title为添加成员时 -->
            <div v-show="title === '添加成员'">
                <form class="add-form" :model="form">
                    <table class="table" cellpadding=10px cellspacing=15px>
                        <tr align="right">
                            <td>成员名：</td>
                            <td>
                                <input type="text" name="realname" class="form-input" 
                                        v-model="form.realname" @focus="clearHint()"/>
                            </td>
                        </tr>
                        <tr align="right">
                            <td>队内职位：</td>
                            <td>
                                <select class="form-input" @focus="clearHint()"
                                    name="position" v-model="form.position">
                                    <option value="队长">队长</option>
                                    <option value="群主">群主</option>
                                    <option value="管理">管理</option>
                                    <option value="成员">成员</option>
                                    <option value="测试">测试</option>
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

export default {
    name: "aboutdialog",
    data() {
        return {
            // 从vuex的state中取得响应数据
            objId: this.$store.state.objId,
            operation: this.$store.state.operation,

            // 错误消息
            errorMessage: '',

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
            // 添加成员，则要发送对应的item
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
            else if (this.operation === 'update')
            {
                this.doUpdate()
            }
        },
        // 清除错误提示
        clearHint() {
            this.errorMessage = '';
        },

        // 设置标题
        setTitle() {
            if (this.operation === 'update')
            {
                this.title = '更新'
            }
            else if (this.operation === 'delete')
            {
                this.title = '删除'
            }
            else if (this.operation === 'insert')
            {
                this.title = '添加成员'
            }
        },
        // 设置item
        setItem() {
            request.get('/members/' + this.objId).then(res => {
                if (res.code === code.GET_OK) {
                    this.item = res.data;
                }
            }).catch(err => {
                console.log(err)
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                });
            })
        },

        // 组件加载时进行item显示
        load() {
            // 设置标题
            this.setTitle();
            // 设置item
            this.setItem();
        },


        // 功能函数，CRUD
        // 发送表单
        doInsert() {
            // 成员名不能为空
            if (this.form.realname.trim() === '')
            {
                this.errorMessage = '成员名不能为空';
                return;
            }
            this.form.realname = this.form.realname.trim();
            request.post('/members', this.form).then(res => {
                    if (res.code === code.INSERT_OK) {
                        this.$notify.success({
                            title: message.INSERT_OK,
                            offset: code.OFFSET
                        })
                        this.$emit('confirm');
                    }
                    else {
                        this.$notify.error({
                            title: message.INSERT_ERR + '，请检查是否存在同名成员',
                            offset: code.OFFSET
                        })
                    }
                }).catch(error => {
                    this.$notify.error({
                        title: message.REQUEST_ERR,
                        offset: code.OFFSET
                    })
                })
        },

        doDelete() {
            request.delete('/members/' + this.objId).then(res => {
                if (res.code === code.DELETE_OK) {
                    this.$notify.success({
                        title: message.DELETE_OK,
                        offset: code.OFFSET
                    })
                    this.$emit("confirm");
                }
                else {
                    this.$notify.error({
                        title: message.DELETE_ERR,
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

        doUpdate() {
            // 成员名不能为空
            if (this.item.realname.trim() === '')
            {
                this.errorMessage = '成员名不能为空';
                return;
            }
            // 发送的js对象要进行JSON格式化
            this.item.realname = this.item.realname.trim();
            request.put('/members', JSON.stringify(this.item)).then(res => {
                    if (res.code === code.UPDATE_OK)
                    {
                        this.$notify.success({
                            title: message.UPDATE_OK,
                            offset: code.OFFSET
                        })
                        this.$emit("confirm");
                    }
                    else 
                    {
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
        },

    },
}
</script>

<style scoped src="@/../public/css/dialog-style.css">
</style>
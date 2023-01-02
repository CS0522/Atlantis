<template>
    <div id="dialog-box">
        <div id="title-box">
            {{ title }}
        </div>
        <hr class="hr" />
        <div id="content-box">
            <span style="color: red">{{ errorMessage }}</span>
            <!-- title为删除时 -->
            <div v-show="title==='删除'">
                <div style="height: 300px; line-height: 300px; font-size: 40px; color: red;">
                    确定要执行删除操作么？
                </div>
            </div>
            <!-- title为添加分类时 -->
            <div v-show="title==='添加分类'">
                <form class="add-form" :model="form">
                    <table class="table" cellpadding=10px cellspacing=15px>
                        <tr align="right">
                            <td>分类名：</td>
                            <td>
                                <input type="text" name="type" class="form-input" 
                                        v-model="form.type" @focus="clearHint()"/>
                            </td>
                        </tr>
                        <tr align="right">
                            <td>路径：</td>
                            <td>
                                <input type="text" name="type" class="form-input" 
                                        v-model="form.route" @focus="clearHint()"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <!-- title为删除分类时 -->
            <div v-show="title==='删除分类'">
                <form class="add-form">
                    <table class="table" cellpadding=10px cellspacing=15px>
                        <tr align="right">
                            <td>选择分类：</td>
                            <td>
                                <select name="selectType" class="form-input" v-model="deleteIndex"
                                        style="min-width: 100px">
                                    <option 
                                        v-for="categoryItem in categoryItems" 
                                        :key="categoryItem.index"
                                        :value="categoryItem.index">
                                        {{ categoryItem.type }}
                                    </option>
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
                        <button class="operation" @click="clickConfirm()" 
                                style="background-color: #8beeff">确定</button>
                    </td>
                    <td>
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
import {checkIsValidForLowercase} from '@/utils/checkvalid'

export default {
    name: "tutorialdialog",
    data() {
        return {
            title: '',
            errorMessage: '路径建议为类名的英文小写',
            categoryItems: [],

            // 从vuex的state中取得响应数据
            objId: this.$store.state.objId,
            operation: this.$store.state.operation,

            // 添加
            form: {
                index: '',
                type: '',
                route: ''
            },
            // 删除
            deleteIndex: '',
        }
    },
    created()
    {
        this.load();
    },
    methods: {
        load() {
            this.clearHint();

            this.setCategoryItems();

            this.setTitle();            
        },
        clearHint() {
            this.errorMessage = '路径建议为类名的英文小写';
        },

        async setCategoryItems() {
            // 获取有哪些分类
            let res = await request.get("/categories");
            if (res.code === code.GET_OK)
            {
                this.categoryItems = res.data;
            }
            else 
            {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            }
        },

        setTitle() {
            if (this.operation === 'delete')
            {
                this.title = "删除";
                this.errorMessage = '';
            }
            else if (this.operation === 'addtype')
            {
                this.title = "添加分类";
            }
            else if (this.operation === 'deletetype')
            {
                this.title = "删除分类";
                this.errorMessage = '确定要执行删除操作么？将会删除该分类下的所有文章';
            }
        },

        cancel() {
            this.$emit('close');
        },
        clickConfirm() {
            if (this.operation === 'delete')
            {
                this.doDelete();
            }
            else if (this.operation === 'addtype')
            {
                this.doAddType();
            }
            else if (this.operation === 'deletetype')
            {
                this.doDeleteType();
            }
        },

        doDelete() 
        {
            request.delete('/tutorialArticles/' + this.objId).then(res => {
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

        doAddType() {
            // 加入之前先进行表单检验，route必须为英文
            if (this.form.route === '')
            {
                this.errorMessage = '输入不可为空';
                return;
            }
            if (!checkIsValidForLowercase(this.form.route))
            {
                this.errorMessage = '路径必须为英文小写';
                return;
            }
            // valid
            request.post("/categories", this.form).then(res => {
                if (res.code === code.INSERT_OK)
                {
                    this.$notify.success({
                            title: message.INSERT_OK,
                            offset: code.OFFSET
                    })
                    this.$emit('confirm');
                }
                else 
                {
                    this.$notify.error({
                            title: message.INSERT_ERR + "，可能已存在相同分类",
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

        doDeleteType() {
            // 如果要删除的id不合法
            if (this.deleteIndex <= 0)
            {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
                return;
            }
            // 删除分类
            request.delete("/categories/" + this.deleteIndex).then(res => {
                if (res.code === code.DELETE_OK)
                {
                    // 分类删除成功，删除文章
                    request.delete("/tutorialArticles/index/" + this.deleteIndex).then(res => {
                        // 只要返回的不是系统错误，就算成功
                        if (res.code !== code.SYS_ERR)
                        {
                            this.$notify.success({
                                title: message.DELETE_OK,
                                offset: code.OFFSET
                            })
                            this.$emit('confirm');
                        }
                    })
                }
                else 
                {
                    this.$notify.error({
                        title: message.DELETE_ERR,
                        offset: code.OFFSET
                    })
                    return;
                }
            }).catch(err => { 
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            })
        }
    },
}
</script>

<style scoped src="@/../public/css/dialog-style.css">
</style>
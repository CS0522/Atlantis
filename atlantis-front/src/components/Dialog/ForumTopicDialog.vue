<template>
    <div id="dialog-box">
        <div id="title-box">
            {{ title }}
        </div>
        <hr class="hr" />
        <div id="content-box">
            <span style="color: red">{{ errorMessage }}</span>
            <!-- title为删除 -->
            <div v-show="title==='删除'">
                <div style="height: 300px; line-height: 300px; font-size: 40px; color: red;">
                    确定要执行删除操作么？将会删除该话题下的所有帖子
                </div>
            </div>
            <!-- title为添加话题 -->
            <div v-show="title==='添加话题'">
                <form class="add-form" :model="form">
                    <table class="table" cellpadding=10px cellspacing=15px>
                        <tr align="right">
                            <td>话题名：</td>
                            <td>
                                <input type="text" name="type" class="form-input" 
                                        v-model="form.type" @focus="clearHint()"/>
                            </td>
                        </tr>
                    </table>
                </form>
            </div>
            <!-- title为编辑话题 -->
            <div v-show="title==='编辑话题'">
                <form class="edit-form">
                    <table class="table" cellpadding=10px cellspacing=15px>
                        <tr align="right">
                            <td>话题名：</td>
                            <td>
                                <input type="text" name="type" class="form-input"
                                        v-model="form.type" @focus="clearHint()"/>
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
                                style="background-color: #eb1d2a">确定</button>
                    </td>
                    <td>
                        <button class="operation" @click="cancel()"
                                style="background-color: #f64530">取消</button>
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
    name: "forumtopicdialog",
    data() {
        return {
            title: '',
            errorMessage: '',

            // 从vuex的state中取得响应数据
            objIndex: this.$store.state.objId,
            operation: this.$store.state.operation,

            // 添加
            form: {
                index: '',
                type: '',
                route: ''
            },
        }
    },
    created()
    {
        this.load();
    },
    methods: {
        load() {
            this.clearHint();
            this.setContent();
            this.setTitle();            
        },
        clearHint() {
            this.errorMessage = '';
        },
        setContent() {
            if (this.objIndex !== -1 && this.operation === 'edit')
            {
                request.get("/topics/" + this.objIndex).then(res => {
                    if (res.code === code.GET_OK)
                    {
                        this.form = res.data; 
                    }
                })
            }
        },
        setTitle() {
            if (this.operation === 'delete')
            {
                this.title = "删除";
            }
            else if (this.operation === 'add')
            {
                this.title = "添加话题";
            }
            else if (this.operation === 'edit')
            {
                this.title = "编辑话题";
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
            else if (this.operation === 'add')
            {
                this.doAdd();
            }
            else if (this.operation === 'edit')
            {
                this.doEdit();
            }
        },

        doDelete() 
        {
            request.delete("/topics/" + this.objIndex).then(res => {
                if (res.code === code.DELETE_OK)
                {
                    this.$notify.success({
                        title: message.DELETE_OK,
                        offset: code.OFFSET
                    })
                    this.$emit('confirm');
                }
                else if (res.code === code.DELETE_ERR)
                {
                    this.$notify.error({
                        title: message.DELETE_ERR,
                        offset: code.OFFSET,
                    })
                }
            }).catch(err => {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            })
        },

        doAdd() {
            request.post("/topics", this.form).then(res => {
                if (res.code === code.INSERT_OK)
                {
                    this.$notify.success({
                        title: message.INSERT_OK,
                        offset: code.OFFSET
                    })
                    this.$emit('confirm');
                }
                else if (res.code === code.INSERT_ERR)
                {
                    this.$notify.error({
                        title: message.INSERT_ERR + "，可能已存在相同话题",
                        offset: code.OFFSET,
                    })
                }
            }).catch(err => {
                this.$notify.error({
                    title: message.REQUEST_ERR,
                    offset: code.OFFSET
                })
            })
        },

        doEdit() {
            request.put("/topics", this.form).then(res => {
                if (res.code === code.UPDATE_OK)
                {
                    this.$notify.success({
                        title: message.UPDATE_OK,
                        offset: code.OFFSET
                    })
                    this.$emit('confirm');
                }
                else if (res.code === code.UPDATE_ERR)
                {
                    this.$notify.error({
                        title: message.UPDATE_ERR + "，可能已存在相同话题",
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
}
</script>

<style scoped src="@/../public/css/dialog-style.css">
</style>
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
                    确定要执行删除操作么？
                </div>
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
    name: "personalthreaddialog",
    data() {
        return {
            title: '',
            errorMessage: '',

            // 从vuex的state中取得响应数据
            objId: this.$store.state.objId,
            operation: this.$store.state.operation,

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

        },
        setTitle() {
            if (this.operation === 'delete')
            {
                this.title = "删除";
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
        },

        doDelete() 
        {
            request.delete("/forumArticles/" + this.objId).then(res => {
                if (res.code === code.DELETE_OK)
                {
                    this.$notify.success({
                        title: message.DELETE_OK,
                        offset: code.OFFSET
                    })
                    this.$emit('confirm', 'thread');
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
    },
}
</script>

<style scoped src="@/../public/css/dialog-style.css">
</style>
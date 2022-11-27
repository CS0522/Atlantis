<template>
    <div id="dialog-box">
        <div id="title-box">
            {{ title }}
        </div>
        <hr class="hr" />
        <div id="content-box">
            <div>
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
    name: "newsdialog",
    data() {
        return {
            title: '',
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
            request.delete('/newsArticles/' + this.objId).then(res => {
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
        }
    },
}
</script>

<style scoped src="@/../public/css/dialog-style.css">
</style>
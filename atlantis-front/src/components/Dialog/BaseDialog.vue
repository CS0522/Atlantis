<template>
    <div id="dialog-box">
        <div id="title-box">
            <slot name="title"></slot>
        </div>
        <hr class="hr" />
        <div id="content-box">
            <div>
                <div style="height: 300px; line-height: 300px; font-size: 40px;">
                    <slot name="content"></slot>
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

export default {
    name: "basedialog",
    props: ['dialogType'],
    data() {
        return {
            
        }
    },
    created()
    {
    },
    methods: {
        cancel() {
            this.$emit('close');
        },
        clickConfirm() {
            console.log(this.dialogType);
            this.$emit('confirm', this.dialogType);
        },
    },
}
</script>

<style scoped src="@/../public/css/dialog-style.css">
</style>
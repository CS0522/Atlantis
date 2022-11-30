<template>
  <div class="dropdown">
    <a style="font-size: 30px;" @mouseenter="menuShow()" @mouseleave="menuHide()">游戏官网⏷</a>
    <el-collapse-transition>
            <div class="dropdown-content" v-show="isShow" 
                @mouseenter="mouseEnterEvent()" @mouseout="mouseOutEvent()">
                <a href="https://initialunity.online/" target="_blank">
                  <div class="content"
                  @mouseenter="mouseEnterEvent()" @mouseout="mouseOutEvent()">
                    &nbsp;&nbsp;Initial Unity
                  </div>
                </a>
                <a href="https://touge-shakai.com" target="_blank">
                  <div class="content"
                  @mouseenter="mouseEnterEvent()" @mouseout="mouseOutEvent()">
                    &nbsp;&nbsp;Touge Shakai
                  </div>
                </a>
            </div>
    </el-collapse-transition>
  </div>
</template>

<script>
export default {
    name: "dropdown",
    data() {
        return {
            isShow: false,
            mouseIsInMenu: false,
        }
    
    },
    methods: {
        menuShow() {
            this.isShow = true;
            this.mouseIsInMenu = true;
        },
        menuHide() {
            this.mouseIsInMenu = false;
            setTimeout(() => {
                // 菜单显示了，但鼠标不在菜单中，则自动收回去（延迟 1s）
                if (this.isShow === true && this.mouseIsInMenu === false)
                {
                    this.isShow = false;
                }
            }, 1000)
        },

        mouseEnterEvent() {
            this.isShow = true;
            this.mouseIsInMenu = true;
        },
        mouseOutEvent() {
            this.isShow = false;
            this.mouseIsInMenu = false;
        }
    }
}
</script>
  
<style scoped src="@/../public/css/topbar-style.css">
</style>
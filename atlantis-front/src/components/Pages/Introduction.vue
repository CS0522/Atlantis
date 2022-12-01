<template>
    <div id="introduction-box">
        <!-- 插入视频 -->
        <div id="video-box">
            <video id="video" height="1000" autoplay muted="true" loop="loop">
                <source :src="videourl" type="video/mp4">
            </video>
        </div>
        <p style="font-size: 35px">默认
            <a style="cursor: pointer;" @click="muteOrUnmute()">
                <span style="color: #2c3e50">[<img src="@/../public/imgs/icons/volume.png" height=25px/> 静音]</span></a>
            并自动
            <a style="cursor: pointer;" @click="playOrPause()">
                <span style="color: #2c3e50">[<img src="@/../public/imgs/icons/play.png" height=24px/> 播放]</span></a>
        </p>
        <br/>
        <div class="introduction-part">
            <div class="title-box">
                <img class="title-img" src="@/../public/imgs/introductions/What.png"/>
            </div>
            <div class="paragraph-box">
                Touge Shakai (山道赛车社会)，是一款基于90年代的日本山道赛车文化的线上游戏。游戏强调现实和有趣的游戏玩法之间的平衡，物理是为竞技漂移赛车量身定制的，同时给所有玩家带来挑战。游戏建立在同作者制作的同人作品 Initial Unity 之上。
            </div>
            <div class="img-box">
                <img class="paragraph-img" src="@/../public/imgs/introductions/01.png"/>
            </div>
            <div class="paragraph-box">
                Touge Shakai 虽然是模拟驾驶物理，但目标并不是制作赛车模拟器。每台汽车的驾驶特性和性能都经过个别调教并与其他汽车截然不同，以避免每台车的驾驶手感相似。游戏物理的目标是增加赛车娱乐性的同时，趋近于真实及可信的驾驶物理。游戏物理比起真实的驾驶物理有一大不同：对于大多数后轮驱动车辆来说，使用漂移甩尾会比用抓地力驾驶更快。
            </div>
            <div class="title-box">
                <img class="title-img" src="@/../public/imgs/introductions/Gameplay.png"/>
            </div>
            <div class="paragraph-box" style="font-weight: bold">
                个人计时赛
            </div>
            <div class="img-box">
                <img class="paragraph-img" src="@/../public/imgs/introductions/02.png"/>
            </div>
            <div class="paragraph-box">
                通过计时赛来不断增进每台赛车和赛道的速度与其他排行榜上的玩家竞争。玩家还可以根据其他玩家的路线幻影，挑战其他玩家的“幽灵赛车”并从中学习他们的驾驶技术。游戏奖牌会根据每个赛道的目标时间给予奖励，并为每台赛车提供更进阶定制化的选项。
            </div>
            <div class="paragraph-box" style="font-weight: bold">
                双人对战
            </div>
            <div class="img-box">
                <img class="paragraph-img" src="@/../public/imgs/introductions/03.png"/>
            </div>
            <div class="paragraph-box">
                经典的山道追逐賽。 游戏规则包括“终点赛制”和“追逐赛制”模式。“追逐赛制”模式会有一方为领跑者，另一方为追逐者，并且试图拉开彼此的距离。如果领跑者到达终点线时距离追逐者超过设定的距离，即可获胜。如果领跑者无法在抵达终点线时甩开追逐者超过设定的距离，这轮比赛则以平手结束。双方将攻守交换并进入下一轮。反之亦然，如果追逐者在抵达终点线时超过领跑者，则由追逐者获胜。 我们推荐玩家使用“追逐赛制”模式，因为在山路上超车既困难又危险，此模式是相对的最公平的竞争模式。车队竞赛由同时进行的多个玩家互相竞争，取得胜局最多的团队将获得最终的胜利。
            </div>
            <div class="paragraph-box" style="font-weight: bold">
                练习模式
            </div>
            <div class="img-box">
                <img class="paragraph-img" src="@/../public/imgs/introductions/04.png"/>
            </div>
            <div class="paragraph-box">
                练习、交流并与其他玩家一起开心地进行友谊赛！我们希望玩家向他人互相学习，在无论输赢的压力下尽情享受赛道，向其他车手和车队展现你独一无二的赛车和驾驶技巧。
            </div>
            <div class="title-box">
                <img class="title-img" src="@/../public/imgs/introductions/Features.png"/>
            </div>
            <div class="paragraph-box">
                <p>
                以下是 Touge Shakai 将开发的重点功能：<br/>
                ● 专用服务器<br/>
                ● 赛车定制化选项 - 车件和外观修改<br/>
                ● 线上匹配和排名<br/>
                ● 多种线上游戏模式<br/>
                ● 车手和车队生涯<br/>
                ● 重播镜头修改器 - 像专业导演一样利用关键帧设定拍摄镜头角度录制赛事<br/>
                ● 游戏内建音乐播放器 - 可播放自带音乐<br/>
                ● 不同的天气路況 - 雨天和山雾<br/>
                ● 玩家成长等级 - 达到游戏目标即可解锁新赛车和零件<br/>
                ● 方向盘和动力回馈支持<br/>
                ● 虚拟现实设备支持<br/>
                </p>
            </div>
            <div class="title-box">
                <img class="title-img" src="@/../public/imgs/introductions/Team.png"/>
            </div>
            <div class="paragraph-box">
                感谢: <br/>
                领导开发者: @AugGust (Singapore) <br/>
                共同开发者: @Auranapse (Singapore)
            </div>
        </div>
    </div>
</template>

<script>

export default {
    name: "introduction",
    data() {
        return {
            videourl: require("@/../public/imgs/introductions/video_promotion.mp4"),
            coverurl: require("@/../public/imgs/introductions/cover.png"),
        }
    },
    methods:
    {
        // 调整静音
        muteOrUnmute()
        {
            let video = document.getElementById("video");
            if (video.muted)
            {
                video.muted = false;
            }
            else
            {
                video.muted = true;
            }
        },
        // 调整播放
        playOrPause()
        {
            let video = document.getElementById("video");
            if (video.paused)
            {
                video.play();
            }
            else
            {
                video.pause();
            }
        }
    }
}
</script>

<style scoped src="@/../public/css/introduction-style.css">

</style>
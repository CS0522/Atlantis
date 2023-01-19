<template>
    <div>
        <div id="title">
            <div>车队简介</div>
        </div>
        <hr class="hr"/>
        <div id="introduction-box">
            <div id="about-video-box">
                <video id="video" width="1280" height="720" autoplay muted="true" loop="loop">
                    <source :src="videourl" type="video/mp4">
                </video>
            </div>
            <p style="font-size: 35px">默认
                <a style="cursor: pointer;" @click="muteOrUnmute()">
                    <span style="color: #fff">[<img src="@/../public/imgs/icons/volume.png" height=25px style="filter: invert(100%)"/> 静音]</span></a>
                并自动
                <a style="cursor: pointer;" @click="playOrPause()">
                    <span style="color: #fff">[<img src="@/../public/imgs/icons/play.png" height=24px style="filter: invert(100%)"/> 播放]</span></a>
            </p>
            <br/>
            <div class="introduction-part">
                <div class="paragraph-box">
                    Atlantis Racer JMVT 车队，是以日系车、改装、与其惊人的驾驶技术作为特色的业余车队。由反对山路恶劣管理的反叛军首领 Shark-Melon 组建。“放荡不羁的漂移技术”与“牢固的抓地跑法”是车队两大核心要素。车队内有两名汽车改装专业级人员与数名优秀的飙车手。  
                </div>
                <div class="paragraph-box">
                    <p>
                    目前车队活跃成员包括：<br/>
                    ● 汽车改装师兼车队最速的转子车下坡手 - Fastest-Takumi <br/>
                    ● 转子引擎操控大师上坡手 - Shark-Melon <br/>
                    ● 稳定高速的 MR2 下坡手 - Seven-7 <br/>
                    ● 车队网络系统组建与管理者 - Izumi (精通碰撞回正技术和鱼雷技术) <br/>
                    ● 汽车改装师兼 AE86 No.2 下坡手 - God-Collide (已隐退) <br/>
                    ● 车队网站建设者兼退役 AE86 最速车手 - Frank (已隐退) <br/>
                    ......
                    </p>
                </div>
                <div class="paragraph-box">
                    我们随时欢迎各位爱好者的加入！
                </div>
            </div>
        </div>
    </div>
</template>

<script>
export default {
    name: "aboutintroduction",
    data() {
        return {
            videourl: require("@/../public/imgs/abouts/video_promotion.mp4"),
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
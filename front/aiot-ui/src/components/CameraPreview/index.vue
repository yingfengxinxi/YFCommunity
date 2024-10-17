<template>
  <div class="app-container home">
    <div id="play_window" :style="`width:${width};height:${height};`"></div>
  </div>

</template>

<script>
import { loadJs,createPlayer,play,stopPlay,stopAllPlay } from "@/utils/camera"

export default {
  name: "CameraPreview",
  props: {
    playURL: {
      type: String,
      default: ""
    },
    width: {
      type: String,
      default: ""
    },
    height: {
      type: String,
      default: ""
    },
  },
  data() {
    return {
      // 当前窗口下标
      curIndex: 0,
      // 播放器实例
      player: null,
      playback: {
        rate: ''
      },
    };
  },
  created() {
    loadJs('/h5player/h5player.min.js').then(() => {
      this.player = createPlayer("play_window","/h5player", 1);
    }).finally(() => {})
  },
  mounted() {
    window.onresize = () => {
      return (() => {
        let playWindow = document.getElementById("play_window");
        let clientWidth = playWindow.parentNode.clientWidth;
        let clientHeight = playWindow.parentNode.clientHeight;
        let width = Math.ceil(clientWidth * 880 / 920) + "px";
        let height = Math.ceil(clientHeight * 580 / 620) + "px";
        if (this.player != null) {
          this.player.JS_Resize(width,height)
        }
      })();
    };
  },
  methods: {
    preview(){
      if(null == this.player){
        loadJs('/h5player/h5player.min.js').then(() => {
          this.player = createPlayer("play_window","/h5player", 1);
          console.log(this.player)
          play(this.player, this.playURL);
        }).finally(() => {})
      } else {
        play(this.player, this.playURL);
      }
    },
    stopPlay() {
      stopPlay(this.player);
    },
    stopAllPlay() {
      stopAllPlay(this.player);
    },
  },
};
</script>

<style lang="scss" scoped>
.el-image {
  border-radius: 5px;
  background-color: #ebeef5;
  box-shadow: 0 0 5px 1px #ccc;
  ::v-deep .el-image__inner {
    transition: all 0.3s;
    cursor: pointer;
    &:hover {
      transform: scale(1.2);
    }
  }
  ::v-deep .image-slot {
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    height: 100%;
    color: #909399;
    font-size: 30px;
  }
}
</style>

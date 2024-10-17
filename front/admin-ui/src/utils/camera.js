import $modal from "@/plugins/modal"
// 加载js
export function loadJs(url) {
  return new Promise((resolve, reject) => {
    const script = document.createElement('script')
    script.src = url
    script.type = 'text/javascript'
    document.body.appendChild(script)
    script.onload = () => {
      resolve()
    }
  })
}

// 创建实例
export function createPlayer(szId, szBasePath, split) {
  let player = new window.JSPlugin({
    szId: szId, //需要英文字母开头 必填
    szBasePath: szBasePath, // 必填,引用H5player.min.js的js相对路径

    // 当容器div#play_window有固定宽高时，可不传iWidth和iHeight，窗口大小将自适应容器宽高
    // iWidth: 600,
    // iHeight: 400,

    // 分屏播放，默认最大分屏4*4
    iMaxSplit: split,
    iCurrentSplit: split,

    // 样式
    // oStyle: {
    //   border: "#343434",
    //   borderSelect: "#FFCC00",
    //   background: "#000"
    // }
  });
  // 事件回调绑定
  player.JS_SetWindowControlCallback({
    windowEventSelect: function(iWndIndex) { //插件选中窗口回调
      console.log('windowSelect callback: ', iWndIndex);
    },
    pluginErrorHandler: function(iWndIndex, iErrorCode, oError) { //插件错误回调
      console.log('pluginError callback: ', iWndIndex, iErrorCode, oError);
      let msg = codeFormat(iErrorCode);
      if(msg){
        $modal.msgError(msg)
      }
    },
    windowFullCcreenChange: function(bFull) { //全屏切换回调
      console.log('fullScreen callback: ', bFull);
    },
    firstFrameDisplay: function(iWndIndex, iWidth, iHeight) { //首帧显示回调
      console.log('firstFrame loaded callback: ', iWndIndex, iWidth, iHeight);
    },
    performanceLack: function() { //性能不足回调
      console.log('performanceLack callback: ');
    }
  });
  return player;
}

// 播放
export function play(player, url, index) {
  player.JS_Play(
    url,
    {playURL: url, mode:0},
    index
  ).then(() => {
    console.log('play success')
  }, e => {
    if("0x01b01307" == e){
      $modal.msgError("视频流获取失败！！！")
    } else {
      $modal.msgError("预览失败！！！")
    }
  })
}

// 停止播放
export function stopPlay(player, curIndex) {
  if(null != player) {
    player.JS_Stop(curIndex).then(() => {
        //this.playback.rate = 0;
        console.log('stop play success')
      }, e => {
        console.error("stop play:", e)
      }
    )
  }
}

// 停止所有播放
export function stopAllPlay(player) {
  if(null != player) {
    player.JS_StopRealPlayAll().then(() => {
        // this.playback.rate = 0;
        console.log('stopAllPlay success')
      }, e => {
        console.error("stop all play:", e)
      }
    )
  }
}

// 状态码
export function codeFormat(code) {
  if("0x12f900001" == code) {
    return "接口调用参数错误"
  }
  if("0x12f900002" == code) {
    return "不在播放状态"
  }
  if("0x12f900003" == code) {
    return "仅回放支持该功能"
  }
  if("0x12f900004" == code) {
    return "普通模式不支持该功能"
  }
  if("0x12f900005" == code) {
    return "高级模式不支持该功能"
  }
  if("0x12f900006" == code) {
    return "高级模式的解码库加载失败"
  }
  if("0x12f910000" == code) {
    return "websocket连接失败，请检查网络是否通畅，URL是否正确"
  }
  if("0x12f910010" == code) {
    return "取流失败"
  }
  if("0x12f910011" == code) {
    return "流中断，电脑配置过低，程序卡主线程都可能导致流中断"
  }
  if("0x12f910014" == code) {
    return "没有音频数据"
  }
  if("0x12f910015" == code) {
    return "未找到对应websocket，取流套接字被动关闭的报错"
  }
  if("0x12f910016" == code) {
    return "websocket不在连接状态"
  }
  if("0x12f910017" == code) {
    return "不支持智能信息展示"
  }
  if("0x12f910018" == code) {
    return "websocket长时间未收到message"
  }
  if("0x12f910019" == code) {
    return "wss连接失败，端口尚未开通、证书未安装、证书不安全"
  }
  if("0x12f910020" == code) {
    return "单帧回放时不能暂停"
  }
  if("0x12f910021" == code) {
    return "已是最大倍速"
  }
  if("0x12f910022" == code) {
    return "已是最小倍速"
  }
  if("0x12f910023" == code) {
    return "ws/wss连接超时，默认6s超时时间，网络异常，网络不通"
  }
  if("0x12f920000" == code) {
    return "储存空间配额失败"
  }
  if("0x12f920001" == code) {
    return "请求文件系统失败"
  }
  if("0x12f920002" == code) {
    return "获取文件失败"
  }
  if("0x12f920003" == code) {
    return "创建writer失败"
  }
  if("0x12f920004" == code) {
    return "写数据失败"
  }
  if("0x12f930000" == code) {
    return "内存不足"
  }
  if("0x12f950000" == code) {
    return "采集音频失败，可能是在非https域下使用对讲导致"
  }
  if("0x12f950001" == code) {
    return "对讲不支持这种音频编码格式"
  }
  return ""
}

<template>
  <div>
    <el-row>
      <el-col :span="6">
        <el-row v-for="indexCode in indexCodes">
          <span style="line-height: 50px;cursor: pointer" @click="playIndex(indexCode)">{{indexCode}}</span>
        </el-row>
      </el-col>
      <el-col :span="18">
        <div id="playWnd"></div>
      </el-col>
    </el-row>

  </div>
</template>

<script>

export default {
  name: "",
  data() {
    return {
      oWebControl:null,
      pubKey:'',
      width: 800,
      height: 600,
      indexCodes: [
        "7ec0f1a1d76e40ca8d4588cf3d1841fe","0f696177aeb740719de997da8ef666da","35a90122d1634c35a062dee6bda6e05d",
        "576dcde3b4fe4e0ba012bc7b785eaa25","ceebc443d730426798c32ad5b4b5c2c1","dc2573d7dd204103b7c28df0fee39467",
        "6d19cbd321dd462aa940dab5a57eafb3","8ad4bf1edf794036939ea501ce8c0ae3","26c9082efaab4852b45e1ca3baeca43c"
      ],
      hik:{
        appKey: "24164922",
        secret: "oi0F1Kkm2EiK8WUU1wFQ",
        ip: "112.123.228.85",
        port: "443",
        cameraIndexCode: "7ec0f1a1d76e40ca8d4588cf3d1841fe",
      },
      argumentList:[],
      index: 1
    }
  },

  created() {
    this.initHik();
  },
  mounted() {

  },
  destroyed() {

  },
  methods: {
    /** 初始化配置*/
    initHik(){
      this.initPlugin();
    },
    initPlugin(){
      let that = this;
      // 创建 WebControl 实例
      that.oWebControl=new WebControl({
        //指定容器id
        szPluginContainer: "playWnd",
        //指定起止端口号，建议使用该值
        iServicePortStart: 15900,
        iServicePortEnd: 15909,
        //用于IE10使用ActiveX的clsid
        szClassId:"23BF3B0A-2C56-4D97-9C03-0CB103AA8F11",
        //创建WebControl实例成功
        cbConnectSuccess: function () {
          console.log("cbConnectSuccess");
          // WebControl实例创建成功后需要启动服务
          that.oWebControl.JS_StartService("window", {
            //WebControlPlugin中的dll文件
            dllPath: "./VideoPluginConnect.dll"
          }).then(function(){
            // 启动插件服务成功 设置消息回调
            that.oWebControl.JS_SetWindowControlCallback({
              cbIntegrationCallBack: that.cbIntegrationCallBack
            });
            //that.width = document.getElementById("playWnd").scrollWidth;
            console.log(that.width)
            //JS_CreateWnd创建视频播放窗口，宽高可设定
            that.oWebControl.JS_CreateWnd("playWnd", that.width, that.height).then(function () {
              // 创建播放实例成功后初始化
              console.log("JS_CreateWnd success");
              // 创建播放实例成功后初始化
              that.getPubKey();
            });
          }, function () {
            //启动插件服务失败
            console.log("startServiceError");
            //that.msgError("启动插件服务失败");
          });
        },
        // 创建WebControl实例失败
        cbConnectError: function () {
          that.oWebControl = null;
          // that.msgError("插件未启动，正在尝试启动，请稍候...");
          // 程序未启动时执行error函数，采用wakeup来启动程序
          WebControl.JS_WakeUp("VideoWebPlugin://");
          that.initCount ++;
          if (that.initCount < 3) {
            setTimeout(function () {
              that.initPlugin();
            }, 3000)
          } else {
            console.log("cbConnectError");
            //that.msgError("插件启动失败，请检查插件是否安装！");
          }
        },
        cbConnectClose: function (bNormalClose) {
          // 异常断开：bNormalClose = false
          // JS_Disconnect正常断开：bNormalClose = true
          console.log("cbConnectClose");
          that.oWebControl = null;
        }
      });
    },

    getPubKey(){
      let that = this;
      that.oWebControl.JS_RequestInterface({
        funcName: "getRSAPubKey",
        argument: JSON.stringify({
          keyLength: 1024
        })
      }).then(function (oData) {
        console.log(oData);
        if (oData.responseMsg.data) {
          that.pubKey = oData.responseMsg.data;
          that.callback();
        }
      })
    },


    callback(){
      let that = this;
      //综合安防管理平台提供的appkey，必填
      var appkey = that.hik.appKey;
      //综合安防管理平台提供的secret，必填
      var secret = that.setEncrypt(that.hik.secret);
      //综合安防管理平台IP地址，必填
      var ip =  that.hik.ip;
      //初始播放模式：0-预览，1-回放
      var playMode = 0;
      //综合安防管理平台端口，若启用HTTPS协议，默认443
      var port = parseInt(that.hik.port);
      //抓图存储路径
      var snapDir = "D:\\SnapDir";
      //紧急录像或录像剪辑存储路径
      var videoDir = "D:\\VideoDir";
      //playMode指定模式的布局
      var layout = "3x3";
      //是否启用HTTPS协议与综合安防管理平台交互，这里总是填1
      var enableHTTPS = 1;
      //加密字段，默认加密领域为secret
      var encryptedFields = 'secret';
      //是否显示工具栏，0-不显示，非0-显示
      var showToolbar = 1;
      //是否显示智能信息（如配置移动侦测后画面上的线框），0-不显示，非0-显示
      var showSmart = 1;
      //自定义工具条按钮
      var buttonIDs = "0,16,256,257,258,259,260,512,513,514,515,516,517,768,769";

      that.oWebControl.JS_RequestInterface({
        funcName: "init",
        argument: JSON.stringify({
          appkey: appkey,
          secret: secret,
          ip: ip,
          playMode: playMode,
          port: port,
          snapDir: snapDir,
          videoDir: videoDir,
          layout: layout,
          enableHTTPS: enableHTTPS,
          encryptedFields: encryptedFields,
          showToolbar: showToolbar,
          showSmart: showSmart,
          buttonIDs: buttonIDs
        })
      }).then(function (oData) {
        // 初始化后resize一次，规避firefox下首次显示窗口后插件窗口未与DIV窗口重合问题
        that.oWebControl.JS_Resize(that.width, that.height);
        that.startPreview();
      });
    },

    setEncrypt (value) {
      var encrypt = new JSEncrypt();
      encrypt.setPublicKey(this.pubKey);
      return encrypt.encrypt(value);
    },

    // 设置消息回调
    cbIntegrationCallBack(oData){
      console.log(JSON.stringify(oData.responseMsg));
    },

    getArgumentList(){
      let argument = {
        ezvizDirect: 0,
        gpuMode: 0,
        streamMode: 0,
        transMode: 1
      };
      for (let i=0; i < this.indexCodes.length; i++) {
        let argument  = {
          cameraIndexCode: this.indexCodes[i],
          ezvizDirect: 0,
          gpuMode: 0,
          streamMode: 0,
          transMode: 1,
          wndId: (i+1)
        }
        this.argumentList.push(argument);
      }
      this.index = 9;
    },

    // 开始预览
    startPreview(){
      this.getArgumentList();
      //获取输入的监控点编号值，必填
      var cameraIndexCode  = this.hik.cameraIndexCode;
      //主子码流标识：0-主码流，1-子码流
      var streamMode = 0;
      //传输协议：0-UDP，1-TCP
      var transMode = 1;
      //是否启用GPU硬解，0-不启用，1-启用
      var gpuMode = 0;
      //播放窗口序号（在2x2以上布局下可指定播放窗口）
      var wndId = -1;

      cameraIndexCode = cameraIndexCode.replace(/(^\s*)/g, "");
      cameraIndexCode = cameraIndexCode.replace(/(\s*$)/g, "");
      this.oWebControl.JS_RequestInterface({
        funcName: "startMultiPreviewByCameraIndexCode",
        argument:{
          list:this.argumentList
        }
      }).then(res => {
        console.log(res)
      });
      /*this.oWebControl.JS_RequestInterface({
        funcName: "startPreview",
        argument: JSON.stringify({
          cameraIndexCode:cameraIndexCode,
          streamMode: streamMode,
          transMode: transMode,
          gpuMode: gpuMode,
          wndId:wndId
        })
      })*/
    },

    playIndex(val){
      console.log(val)
      //获取输入的监控点编号值，必填
      var cameraIndexCode  = val;
      //主子码流标识：0-主码流，1-子码流
      var streamMode = 0;
      //传输协议：0-UDP，1-TCP
      var transMode = 1;
      //是否启用GPU硬解，0-不启用，1-启用
      var gpuMode = 0;
      //播放窗口序号（在2x2以上布局下可指定播放窗口）
      //var wndId = -1;
      if(this.index == 9){
        this.index = 1;
      }else {
        this.index++;
      }
      var wndId = this.index;
      this.oWebControl.JS_RequestInterface({
        funcName: "startPreview",
        argument: JSON.stringify({
          cameraIndexCode:cameraIndexCode,
          streamMode: streamMode,
          transMode: transMode,
          gpuMode: gpuMode,
          wndId:wndId
        })
      })
    },

    //停止预览
    stopAllPreview(){
      if (this.oWebControl != null){
        this.oWebControl.JS_RequestInterface({
          funcName: "stopAllPreview"
        });
        // 先让窗口隐藏，规避可能的插件窗口滞后于浏览器消失问题
        this.oWebControl.JS_HideWnd();
        this.oWebControl.JS_Disconnect().then(function(){
          console.log("断开与插件服务连接成功");
        },function() {
          console.log("断开与插件服务连接失败");
        });
      }
    },
    previewCancel(){
      this.stopAllPreview();
    },
  }
};
</script>

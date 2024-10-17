<template>
  <div class="app-container">
    <el-form>
      <el-row v-for="data in warnEvent.videoDataList">
        <el-row>
          <h4 class="form-header">扩展信息</h4>
          <el-col :span="6">
            <el-form-item label="数据模型标识：">{{data.dataType}}</el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="事件类型名称：">{{eventFormatter(warnEvent.eventType)}}</el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="设备的IP：">{{data.ipAddress}}</el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="设备端口号：">{{data.portNo}}</el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="设备通道号：">{{data.channelId}}</el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="数据触发时间：">{{data.dateTime}}</el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="数据发送时间：">{{data.sendTime}}</el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="数据接收时间：">{{data.recvTime}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-row v-for="result in data.smokeFireList">
            <el-col :span="12">
              <h4 class="form-header">火点检测信息</h4>
              <el-col :span="12">
                <el-form-item label="火点框宽度：">{{result.fireRegionWidth}}</el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="火点框高度：">{{result.fireRegionHeight}}</el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="最高温度点坐标X：">{{result.fireHighestX}}</el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="最高温度点坐标Y：">{{result.fireHighestY}}</el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="绝对高度-海拔：">{{result.fireElevation}}</el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="绝对高度-方位：">{{result.fireAzimuth}}</el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="绝对高度-绝对缩放：">{{result.fireZoom}}</el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="最大温度：">{{result.fireMaxTemperature}} {{tempUnitFormatter(result.temperatureUnit)}} </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="目标距离：">{{result.targetDistance}}</el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="火点扫描等待模式：">{{result.fireScanWaitMode}}</el-form-item>
              </el-col>
            </el-col>
            <el-col :span="6">
              <h4 class="form-header">烟雾检测信息</h4>
              <el-form-item label="烟雾框宽度：">{{result.smokeRegionWidth}}</el-form-item>
              <el-form-item label="烟雾框高度：">{{result.smokeRegionHeight}}</el-form-item>
              <el-form-item label="绝对高度-海拔：">{{result.smokeElevation}}</el-form-item>
              <el-form-item label="绝对高度-方位：">{{result.smokeAzimuth}}</el-form-item>
              <el-form-item label="绝对高度-绝对缩放：">{{result.smokeZoom}}</el-form-item>
            </el-col>
            <el-col :span="6">
              <h4 class="form-header">分析结果</h4>
              <el-form-item label="监控点：">{{result.cameraIndexCode}}</el-form-item>
              <el-form-item label="URL：">
                <el-image :src="result.imageUrl" style="width: 35%"/>
              </el-form-item>
            </el-col>
          </el-row>
        </el-row>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import {equals} from "@/utils/common";
export default {
  name: "FireData",
  dicts: ['sys_event_type'],
  props: {
    warnEvent: {
      type: Object,
      default: {}
    },
  },
  data() {
    return {}
  },
  methods: {
    eventFormatter(eventType){
      return this.selectDictLabel(this.dict.type.sys_event_type, eventType);
    },
    tempUnitFormatter(temperatureUnit){
      if(equals(temperatureUnit, "celsius")){
        return "（摄氏度）"
      }
      if(equals(temperatureUnit, "fahrenheit")){
        return "（华氏度）"
      }
      if(equals(temperatureUnit, "kelvin")){
        return "（开尔文）"
      }
      return "";
    },
  }
}
</script>

<style scoped>

</style>


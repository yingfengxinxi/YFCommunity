<template>
  <div class="app-container">
    <el-form>
      <el-row v-for="data in warnEvent.videoDataList">
        <el-col :span="14">
          <h4 class="form-header">扩展信息</h4>
          <el-col :span="12">
            <el-form-item label="数据模型标识：">{{data.dataType}}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="事件类型名称：">{{eventFormatter(warnEvent.eventType)}}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备的IP：">{{data.ipAddress}}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备端口号：">{{data.portNo}}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备通道号：">{{data.channelId}}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数据触发时间：">{{data.dateTime}}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数据发送时间：">{{data.sendTime}}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数据接收时间：">{{data.recvTime}}</el-form-item>
          </el-col>
        </el-col>
        <el-col :span="10">
          <h4 class="form-header">分析结果</h4>
          <el-row v-for="result in data.videoResultList">
            <el-form-item label="监控点：">{{result.channelName}} ( {{ result.cameraIndexCode}} )</el-form-item>
            <el-form-item label="URL：">
              <el-image v-if="result.imageUrl" :src="result.imageUrl" style="width: 35%"/>
            </el-form-item>
          </el-row>
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "VideoData",
  dicts: ['sys_event_type'],
  props: {
    warnEvent: {
      type: Object,
      default: {}
    },
  },
  data() {
    return {};
  },
  created() {
  },
  methods: {
    eventFormatter(eventType){
      return this.selectDictLabel(this.dict.type.sys_event_type, eventType);
    },
  }
}
</script>

<style scoped>

</style>


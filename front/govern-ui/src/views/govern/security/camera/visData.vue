<template>
  <div class="app-container">
    <el-form>
      <el-row v-for="data in warnEvent.visDataList">
        <el-col :span="8">
          <el-form-item label="人员通道号：">{{
            data.extAccessChannel
          }}</el-form-item>
          <el-form-item label="进人数：">{{ data.entryTimes }}</el-form-item>
          <el-form-item label="出人数：">{{ data.exitTimes }}</el-form-item>
          <el-form-item label="总通行人数：">{{
            data.totalTimes
          }}</el-form-item>
          <el-form-item label="卡号：">{{ data.extEventCardNo }}</el-form-item>
          <el-form-item label="门编号：">{{
            data.extEventDoorId
          }}</el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="人员类型：">{{
            userTypeFormatter(data.userType)
          }}</el-form-item>
          <el-form-item label="身份证ID：">{{ data.idNum }}</el-form-item>
          <el-form-item label="姓名：">{{ data.userName }}</el-form-item>
          <el-form-item label="人员类型：">{{
            sexFormatter(data.userSex)
          }}</el-form-item>
          <el-form-item label="人员编号：">{{
            data.extEventPersonNo
          }}</el-form-item>
          <el-form-item label="进出方向：">
            <span v-if="data.extEventPersonNo === '1'">进</span>
            <span v-else>出</span>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="刷卡次数：">{{
            data.extEventSwipNum
          }}</el-form-item>
          <el-form-item label="接收时间：">{{
            momentFormat(data.extReceiveTime)
          }}</el-form-item>
          <el-image :src="data.extEventPictureUrl" style="width: 60%" />
        </el-col>
      </el-row>
    </el-form>
  </div>
</template>

<script>
import { equals, isEmpty } from "@/utils/common";
export default {
  name: "VisData",
  props: {
    warnEvent: {
      type: Object,
      default: {},
    },
  },
  data() {
    return {};
  },
  methods: {
    sexFormatter(userSex) {
      if (equals(userSex, "1")) {
        return "男";
      } else if (equals(userSex, "2")) {
        return "女";
      }
      return "";
    },
    userTypeFormatter(userType) {
      if (equals(userType, "1")) {
        return "普通";
      } else if (equals(userType, "2")) {
        return "来宾";
      } else if (equals(userType, "3")) {
        return "黑名单";
      } else if (equals(userType, "4")) {
        return "管理员";
      }
      return "未知";
    },
    momentFormat(time) {
      if (isEmpty(time)) {
        return "";
      }
      let date = new Date();
      date.setTime(time);
      return this.$moment(date).format("YYYY-MM-DD HH:mm:ss");
    },
  },
};
</script>

<style scoped>
</style>


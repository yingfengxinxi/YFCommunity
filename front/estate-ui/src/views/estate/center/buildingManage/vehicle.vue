<template>
  <div class="app-container" style="padding: 5px 20px 20px 20px">
    <el-tabs v-model="activeTab">
      <el-tab-pane label="业主车辆" name="ali" :lazy="true">
        <ali />
      </el-tab-pane>
      <el-tab-pane label="物业车辆" name="wechat" :lazy="true">
        <wechat />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import ali from "./vehicleManage";
 import wechat from "../../manage/staffProperty/staffList";

export default {
  name: "vehicle",
  components: { ali,wechat },
  data() {
    return {
      activeTab: "ali"
    }
  },
}
</script>

<style scoped>

</style>

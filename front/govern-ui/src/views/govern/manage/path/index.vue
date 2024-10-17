<template>
  <div class="app-container">
    <el-tabs v-model="tableName">
      <el-tab-pane label="人员轨迹" name="userPath">
        <user ref="userPath"/>
      </el-tab-pane>

      <el-tab-pane label="车辆轨迹" name="vehiclePath">
        <vehicle ref="vehiclePath"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>

import user from "@/views/govern/manage/path/user"
import vehicle from "@/views/govern/manage/path/vehicle";

export default {

  components: {
    vehicle,
    user,
  },
  watch: {
    //监视标签页变化
    tableName: {
      handler(newValue, oldValue) {
        this.$refs[newValue].getList();
        this.$refs[newValue].getCommuntiyList();
      }
    }
  },
  data() {
    return {
      //标签页名
      tableName: 'userPath',
    }
  },
}

</script>

<style scoped>

</style>

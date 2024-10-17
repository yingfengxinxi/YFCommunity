<template>
  <div class="app-container" style="padding: 5px 20px 20px 20px">
    <el-tabs v-model="activeTab">
      <el-tab-pane label="业主短信通知记录" name="owner" :lazy="true">
        <owner />
      </el-tab-pane>
      <el-tab-pane label="物业短信通知记录" name="estate" :lazy="true">
        <estate />
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import owner from "./sendSmsRecordOwner";
import estate from "./sendSmsRecordEstate";

export default {
  name: "sendSmsRecord",
  components: { owner, estate },
  data() {
    return {
      activeTab: "owner"
    }
  },
}
</script>

<style scoped>

</style>

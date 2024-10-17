<template>
  <div class="app-container" style="padding: 5px 20px 20px 20px">
    <el-tabs v-model="activeTab">
      <el-tab-pane label="支付宝账号" name="ali" :lazy="true">
        <ali />
      </el-tab-pane>
      <el-tab-pane label="微信账号" name="wechat" :lazy="true">
        <wechat />
      </el-tab-pane>
<!--      <el-tab-pane label="农行账号" name="abc" :lazy="true">
        <abc />
      </el-tab-pane>-->
    </el-tabs>
  </div>
</template>

<script>
import ali from "./ali";
import wechat from "./wechat";
import abc from "./abc";

export default {
  name: "AccountManagement",
  components: { ali, wechat, abc },
  data() {
    return {
      activeTab: "ali"
    }
  },
}
</script>

<style scoped>

</style>

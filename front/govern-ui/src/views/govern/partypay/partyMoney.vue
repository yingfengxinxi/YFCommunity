<template>
  <div class="app-container">
    <el-tabs v-model="activeTab" class="part-money-tabs" @tab-click="handleClick">
      <el-tab-pane label="缴费统计" name="statistics">
        <Statistics v-if="isSta"/>
      </el-tab-pane>
      <el-tab-pane label="党员党费" name="memberFee">
        <MemberFee v-if="isMember"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import Statistics from "./statistics";
import MemberFee from "./memberFee";
export default {
  name: "partyMoney",
  components: { Statistics,MemberFee },
  data() {
    return {
      activeTab: "statistics",
      isSta:true,
      isMember:false,
      isSet:false,
    };
  },
  created() {
  },
  methods: {
    handleClick(tab) {
      if (tab.name == "statistics") {
        this.isSta = true;
        this.isMember = false;
        this.isSet=false;
      } else if (tab.name == "memberFee") {
        this.isSta = false;
        this.isMember = true;
        this.isSet=false;
      }
    }
  }

}
</script>

<style scoped lang="scss">
::v-deep .part-money-tabs .el-tabs__header{
  margin-top: -20px;
  margin-bottom: 5px;
}
</style>

<template>
  <div class="app-container">
    <el-tabs v-model="activeName" >
            <el-tab-pane label="物业移动端隐私政策" name="estatePrivacyPolicy">
              <editor v-model="estatePrivacyPolicy" :min-height="192" class="position"/>
              <div style="float: right;">
                <el-button type="primary" size="middle" @click="estatePrivacyPolicySave">保存</el-button>
              </div>
            </el-tab-pane>
            <el-tab-pane label="物业移动端用户协议" name="estateThumbnail">
              <editor v-model="estateThumbnail" :min-height="192" class="position"/>
              <div style="float: right;">
                <el-button type="primary" size="middle" @click="estateThumbnailSave">保存</el-button>
              </div>
            </el-tab-pane>

            <el-tab-pane label="业主移动端隐私政策" name="ownerPrivacyPolicy">
              <editor v-model="ownerPrivacyPolicy" :min-height="192" class="position"/>
              <div style="float: right;">
                <el-button type="primary" size="middle" @click="ownerPrivacyPolicySave">保存</el-button>
              </div>
            </el-tab-pane>

            <el-tab-pane label="业主移动端用户协议" name="ownerThumbnail">
              <editor v-model="ownerThumbnail" :min-height="192" class="position"/>
              <div style="float: right;">
                <el-button type="primary" size="middle" @click="ownerThumbnailSave">保存</el-button>
              </div>
            </el-tab-pane>
          </el-tabs>
  </div>
</template>

<script>

import {
  getAgreement,
  saveAgreement
} from "@/api/estate/agreement/agreement";
export default {
  name: "lost",
  data(){
    return{
      activeName: 'estatePrivacyPolicy',
      ownerThumbnail: '',
      ownerPrivacyPolicy: '',
      estateThumbnail: '',
      estatePrivacyPolicy:'',
      // 树筛选文本
      filterText:'',
      openView:false,
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
    }
  },
  created() {
    this.getAgreement();
  },
  methods:{
    /** 查询列表 */
    getAgreement() {
      this.openView = 0;
      this.loading = true;
      getAgreement().then(res => { 
        this.estatePrivacyPolicy = res.data.estatePrivacyPolicy;
        this.estateThumbnail = res.data.estateThumbnail;
        this.ownerPrivacyPolicy = res.data.ownerPrivacyPolicy;
        this.ownerThumbnail=res.data.ownerThumbnail;
      })
      this.loading = false;
    },
    /** 物业移动端隐私政策保存 */
    estatePrivacyPolicySave() {
      var obj = {
        estatePrivacyPolicy: this.estatePrivacyPolicy
      };
      saveAgreement(obj).then(res => { 
        this.$modal.msgSuccess("保存成功");
      })
    },
      /** 物业移动端用户协议 */
      estateThumbnailSave() {
      var obj = {
        estateThumbnail: this.estateThumbnail
      };
      saveAgreement(obj).then(res => { 
        this.$modal.msgSuccess("保存成功");
      })
    },

      /** 业主移动端隐私政策 */
      ownerPrivacyPolicySave() {
      var obj = {
        ownerPrivacyPolicy: this.ownerPrivacyPolicy
      };
      saveAgreement(obj).then(res => { 
        this.$modal.msgSuccess("保存成功");
      })
    },

       /** 业主移动端用户协议 */
      ownerThumbnailSave() {
      var obj = {
        ownerThumbnail: this.ownerThumbnail
      };
      saveAgreement(obj).then(res => { 
        this.$modal.msgSuccess("保存成功");
      })
    },
  }
}
</script>

<style scoped>
/*设置高亮颜色*/
/deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #b3f1b6 !important;
}
.content {
  font-size: 14px;
}
.thumbnail {
  height:100px;
  width: 200px
}
</style>

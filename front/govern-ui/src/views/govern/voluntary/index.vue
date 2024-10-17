<template>
  <div class="app-container">
    <!-- list -->
      <div>
        <el-row>
          <el-col>
            <el-tabs v-model="activeName" type="border-card"  @tab-click="handleClick">
              <el-tab-pane label="活动管理" name="voluntary">
                <voluntary/>
              </el-tab-pane>
              <el-tab-pane label="社区救助" name="rescue">
                <rescue ref="rescue"/>
              </el-tab-pane>
            </el-tabs>
          </el-col>
        </el-row>
      </div>
  </div>
</template>
<script>
  import voluntary from './voluntary'
  import rescue from './rescue'
  export default {
    name: "index",
    components:{voluntary,rescue},
    data(){
      return{
        activeName:'voluntary',
      }
    },
    methods: {
      handleClick(tab, event) {
        if (tab.name == 'rescue') {

          if (this.$refs['rescue'].currentList == '') {

            this.$message.error('暂无小区...')
          }
        }
      },

    }
  }
</script>

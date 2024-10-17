<template>
  <div class="app-container">
    <div>
      <el-row>
        <el-col>
          <el-tabs v-model="activeName">
            <el-tab-pane label="业主申请" name="ownerOrder">
              <owner-order/>
            </el-tab-pane>
            <el-tab-pane label="员工申请" name="staffOrder">
              <staff-order/>
            </el-tab-pane>
          </el-tabs>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import ownerOrder from './ownerOrder'
import staffOrder from './staffOrder'

export default {
  name: "audit",
  components:{
    staffOrder,ownerOrder
  },
  data(){
    return{
      activeName:'ownerOrder'
    }
  },

}
</script>

<style scoped>

</style>

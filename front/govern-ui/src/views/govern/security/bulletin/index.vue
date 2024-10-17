<template>
  <div class="app-container">
    <el-tabs v-model="tableName">
      <el-tab-pane label="通报人员" name="bulletin">
        <bulletin ref="bulletin" @toArchived="handleArchived"/>
      </el-tab-pane>

      <el-tab-pane label="归档信息" name="archived">
        <archived ref="archived"/>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
import archived from '@/views/govern/security/bulletin/archived'
import bulletin from "@/views/govern/security/bulletin/bulletin";

export default {
  name: "index",
  components: {
    archived, bulletin
  },
  watch: {
    tableName(newValue) {
      this.$refs[newValue].getList()
    }
  },

  data() {
    return {
      //标签页名
      tableName: 'bulletin',
    }
  },
  methods: {
    //切换归档页面 并且初始化
    handleArchived() {
      this.tableName = 'archived'
      this.$refs.archived.getList()
    }
  },
}

</script>

<style scoped>

</style>

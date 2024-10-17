<template>
  <div class="app-container">
    <div v-show="openView === 0">
      <el-row :gutter="20">
        <!-- 树结构     -->
        <el-col :span="4" :xs="24">
          <div class="head-container">
            <el-input v-model="filterText" placeholder="请输入小区名称" clearable size="small"
                      prefix-icon="el-icon-search" style="margin-bottom: 20px"/>
          </div>
          <div >
            <el-tree
              :data="comOptions"
              :props="defaultProps"
              :expand-on-click-node="false"
              :filter-node-method="filterNode"
              ref="tree"
              highlight-current
              node-key="communityId"
              @node-click="handleNodeClick"
            >
              <!-- 插入楼栋图标 -->
              <span slot-scope="{ node }">
	   		          <i class="el-icon-office-building" />
	   		          <span class="content"  >{{node.label}}</span>
		          </span>
            </el-tree>
          </div>
        </el-col>

          <image-upload v-model="thumbnail" :limit="1" style="margin-left: 35%;"/>
          <div style="margin-left: 53%; margin-top: 25px;">
            <el-button size="small" @click="save"  type="primary">保存</el-button>
          </div>
      </el-row>
    </div>

    
  </div>
</template>

<script>

import {getCurrentList} from "@/api/estate/community";
import {saveSecondHand, getSecondHandList} from "@/api/estate/customer/secondhand/secondHand";

export default {
  name: "secondHand",
  data(){
    return{
      // 树筛选文本
      filterText:'',
      openView:false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 表格数据
      secondHandList: [],
      //树结构
      comOptions:[],
      defaultProps: {
        children: "communityId",
        label: "communityName"
      },
      thumbnail:'',
      communityId: null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: null,
        handBrand:null,
        handStatus:null,
        accountName:null,
        contactPhone:null
      },
      // 表单参数
      form: {},
    }
  },
  created() {
    this.getTreeSelect();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods:{
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.communityId = data.communityId;
      // this.handleQuery();
      this.communityId = data.communityId;
      this.getList();
    },
    /** 树结构 */
    getTreeSelect() {
      getCurrentList().then(response => {
        this.comOptions = response.data;
        this.queryParams.communityId = this.comOptions[0].communityId;
        this.communityId = this.comOptions[0].communityId;

        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.comOptions[0].communityId);
        });
        this.getList();
      });
    },
    save() { 
      saveSecondHand(this.queryParams.communityId,this.thumbnail).then(res => { 
        this.$modal.msgSuccess("保存成功");
      })
    },
    // 取消按钮
    cancel() {
      this.openView = 0;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
      // this.getTreeSelect();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 查询列表 */
    getList() {
      this.openView = 0;
      this.loading = false;
      getSecondHandList(this.queryParams.communityId).then(response => {
        if (response.data) {
          this.thumbnail = response.data.thumbnail;
        } else { 
          this.thumbnail ="";
        }
        
        this.loading = false;
      });
    },
    /** 查看详情*/
    handleGoods(row){
      this.openView = 1;
      this.form = row;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$modal.confirm('是否确认删除选中的数据项？').then(function() {
        return deleteSecondHand(row.handId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
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

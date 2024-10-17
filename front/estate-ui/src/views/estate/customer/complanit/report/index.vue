<template>
  <div class="app-container">
    <div v-show="view === 1">
      <el-row :span="24">
        <el-col :span="4">
          <div class="head-container">
            <el-input
              placeholder="请输入小区名称"
              v-model="filterText"
              clearable
            >
            </el-input>

            <el-tree
              class="filter-tree"
              :data="currentList"
              :props="currentListProps"
              default-expand-all
              :filter-node-method="filterNode"
              @node-click="handleNodeClick"
              ref="tree">
            </el-tree>
          </div>
        </el-col>
        <!--      右侧-->
        <el-col :span="20">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
            <el-form-item label="违法类型" prop="illegalType">
              <el-select v-model="queryParams.illegalType" placeholder="违法类型" clearable >
                <el-option
                  v-for="dict in dict.type.sys_illegal_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>

          <el-table
            v-loading="loading"
            :data="reportList"
          >
            <el-table-column prop="illegalType" label="违法举报类型"  align="center">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.sys_illegal_type" :value="scope.row.illegalType"/>
              </template>
            </el-table-column>
            <el-table-column prop="illegalUrl" label="图片"  align="center">
              <template slot-scope="scope">
                <el-image
                  style="width: 100px; height: 40px"
                  :src="scope.row.illegalUrl"
                  fit="contain"></el-image>
              </template>
            </el-table-column>
            <el-table-column prop="createTime" label="创建时间"  align="center">
              <template slot-scope="scope">
                {{parseTime(scope.row.createTime)}}
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleSelect(scope.row)"
                  v-hasPermi="['bussiness:report:list']"
                >查看</el-button>
              </template>
            </el-table-column>
          </el-table>

          <!-- 分页  -->
          <pagination
            v-show="total>0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
          />
        </el-col>
      </el-row>
    </div>

    <!--  查看页  -->
    <div v-show="view === 2">
      <div>
        <span>违法举报详情</span>
        <el-divider></el-divider>
        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">违法举报类型: </span><dict-tag :options="dict.type.sys_illegal_type" :value="reportObj.illegalType" style="display: inline-block"/>
          </el-col>

          <el-col :span="8">
            <span class="span-font">创建时间: </span>{{parseTime(reportObj.createTime)}}
          </el-col>

          <el-col :span="8">
            <span class="span-font">图片: </span><el-image
            style="width: 100px; height: 100px;vertical-align: top"
            :src="reportObj.illegalUrl"
            fit="contain"></el-image>
          </el-col>
        </el-row>

        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">举报内容:</span><div style="display: inline-block;width: 80%;vertical-align: top">{{reportObj.remark}}</div>
          </el-col>
        </el-row>

        <el-row :span="24">
          <el-col :span="8" :offset="21">
            <el-button @click="cancel">取 消</el-button>
          </el-col>
        </el-row>
      </div>
    </div></div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {getTableList,getTableListById} from "@/api/estate/customer/complanint/report/report";

export default {
  name: "Report",
  dicts:['sys_illegal_type'],
  data(){
    return {
      view:1,
      //总数据
      total:0,
      filterText:'',
      // 遮罩层
      loading: false,
      // 重新渲染表格状态
      refreshTable: true,
      // 搜索显示
      showSearch: true,
      // 查询条件
      queryParams:{
        pageNum: 1,
        pageSize: 10,
        communityId:undefined,
        illegalType:undefined
      },
      // 树结构
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      // 举报列表
      reportList: [],
      currentList:[],
      reportObj:{}
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getTree();
    // this.getList()
  },
  methods:{
    // 过滤树
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 获取树数据
    getTree(){
      getCurrentList().then(res =>{
        this.currentList = res.data;
        this.queryParams.communityId = res.data[0].communityId
        this.getList()
      })
    },
    // 列表
    getList(){
      this.loading = true;
      getTableList(this.queryParams).then(resp => {
        this.total = resp.total;
        this.reportList = resp.rows;
        this.loading = false;
      })
    },
    // 搜索按钮
    handleQuery(){
      this.queryParams.pageNum = 1
      this.getList();
    },
    // 重置按钮
    resetQuery(){
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        communityId:this.queryParams.communityId,
        illegalType:undefined
      }
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 表格查看按钮
    handleSelect(row){
      getTableListById(row.illegalId).then(resp => {
        this.reportObj = resp.data
        this.view = 2
      })
    },
    // 树点击事件
    handleNodeClick(row){
      this.queryParams.communityId = row.communityId
      this.getList();
    },
    // 取消
    cancel(){
      this.view = 1
      this.reportObj = {}
    }
  },
};
</script>

<style scoped>
.span-font {
  display: inline-block;
  font-size: 16px;
  color: gray;
  margin-bottom: 50px;
}
</style>


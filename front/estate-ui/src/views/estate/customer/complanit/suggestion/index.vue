<template>
  <div class="app-container">
    <div v-show="view === 1">
      <el-row :span="24">
        <!--   选择树   -->
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
              icon-class="el-icon-star-on"
              @node-click="handleNodeClick"
              ref="tree">
            </el-tree>
          </div>
        </el-col>
        <!--   右侧   -->
        <el-col :span="20">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
            <el-form-item label="业主名称" prop="accountNick">
              <el-input
                v-model="queryParams.accountNick"
                placeholder="请输入业主名称"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            <el-form-item label="建议类型" prop="suggestType">
              <el-select v-model="queryParams.suggestType" placeholder="建议类型" clearable >
                <el-option
                  v-for="dict in dict.type.sys_suggest_type"
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
            v-if="refreshTable"
            v-loading="loading"
            :data="suggestList"
          >
            <el-table-column prop="accountNick" label="业主名称" width="150" align="center"></el-table-column>
            <el-table-column prop="buildingName" label="所属楼栋" width="150" align="center"></el-table-column>
            <el-table-column prop="suggestType" label="建议类型" width="150" align="center">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.sys_suggest_type" :value="scope.row.suggestType"/>
              </template>
            </el-table-column>
            <el-table-column prop="remark" label="建议内容" width="350" align="center" show-overflow-tooltip></el-table-column>
            <el-table-column prop="createTime" label="创建时间" width="250" align="center"></el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="handleSelect(scope.row)"
                  v-hasPermi="['bussiness:suggestion:list']"
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
        <span>建议人详情</span>
        <el-divider></el-divider>
        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">业主名称: </span>{{suggestObj.accountNick}}
          </el-col>

          <el-col :span="8">
            <span class="span-font">所属小区: </span>{{suggestObj.communityName}}
          </el-col>

          <el-col :span="8">
            <span class="span-font">所属楼栋: </span>{{suggestObj.buildingName}}
          </el-col>
        </el-row>

        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">所属单元:</span>{{suggestObj.unitName}}
          </el-col>

          <el-col :span="8">
            <span class="span-font">所属房号:</span>{{suggestObj.roomName}}
          </el-col>

          <el-col :span="8">
            <span class="span-font">建议类型:</span><dict-tag :options="dict.type.sys_suggest_type" :value="suggestObj.suggestType" style="display: inline-block"/>
          </el-col>
        </el-row>

        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">创建时间:</span>{{suggestObj.createTime}}
          </el-col>

          <el-col :span="8">
            <span class="span-font">建议内容:</span><div style="display: inline-block;width: 80%;vertical-align: top">{{suggestObj.remark}}</div>
          </el-col>

        </el-row>

        <el-row :span="24">
          <el-col :span="8" :offset="21">
            <el-button @click="cancel">取 消</el-button>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {getTable,getTableListById} from "@/api/estate/customer/complanint/suggest/suggest";

export default {
  name: "Suggest",
  dicts:['sys_suggest_type'],
  data(){
    return {
      view: 1,
      // 数据总数
      total: 0,
      // 树筛选文本
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
        accountNick:undefined,
        suggestType:undefined,
        communityId:undefined
      },
      // 意见箱列表
      suggestList: [],
      // 树结构
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      //小区数据集合
      currentList:[],
      // 查看页数据
      suggestObj:{}
    }
  },
  created() {
    this.getTree();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
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
        this.queryParams.communityId = res.data[0].communityId
        this.currentList = res.data;
        this.getList();
      })
    },
    // 列表
    getList(){
      this.loading = true;
      getTable(this.queryParams).then(resp => {
        this.total = resp.total
        this.suggestList = resp.rows
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
        accountNick:undefined,
        suggestType:undefined,
        communityId:this.queryParams.communityId
      }
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 表格查看按钮
    handleSelect(row){
      this.view = 2
      getTableListById(row.suggestId).then(resp => {
        this.suggestObj = resp.data
      })
    },
    // 树点击事件
    handleNodeClick(row){
      this.queryParams.communityId = row.communityId
      this.getList();
    },
    // 查看页取消
    cancel(){
      this.view = 1
      this.suggestObj = {}
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


<template>
  <div class="app-container">
    <div v-show="openView === 0">
      <el-row :gutter="20">
        <!--   选择树   -->
        <el-col :span="4" :xs="24">
          <div class="head-container">
            <el-input
              placeholder="请输入小区名称"
              clearable
              size="small"
              prefix-icon="el-icon-search"
              v-model="filterText">
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
        <el-col :span="20" :xs="24">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="82px">
            <el-form-item label="出入口名称" prop="passageName">
              <el-input
                v-model="queryParams.passageName"
                placeholder="请输入出入口名称"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item >
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>

          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button
                type="primary"
                plain
                icon="el-icon-plus"
                size="mini"
                @click="handleAdd"
                v-hasPermi="['manage:passage:add']"
              >新增
              </el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="danger"
                plain
                icon="el-icon-delete"
                size="mini"
                :disabled="multiple"
                @click="handleDelete"
                v-hasPermi="['manage:passage:delete']"
              >删除
              </el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
          </el-row>

          <el-table v-loading="loading" :data="passageList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="出入口名称" align="center" prop="passageName" />
            <el-table-column label="修改时间" align="center" prop="updateTime" />
            <el-table-column label="修改人" align="center" prop="updateBy" />
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['manage:passage:update']"
                >修改</el-button>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  style="color: #f56c6c"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['manage:passage:delete']"
                >删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <pagination
            v-show="total>0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
          />

        </el-col>
      </el-row>
      <!-- 添加或修改变更申请对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
        <el-form ref="form" :model="form"  label-width="100px" :rules="rules">
          <el-form-item label="出入口名称" prop="passageName" >
            <el-input v-model="form.passageName" placeholder="请输入出入口名称" :maxlength = "15"/>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>
  </div>

</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {addPassage, getPassageInfo, listPassage, removePassage, updatePassage} from "@/api/estate/manage/passage";

export default {
  name: "index",
  data(){
    return{
      openView:false,
      // 遮罩层
      loading: true,
      // 表单校验
      rules: {passageName: [{required: true, message: '出入口名称不能为空', trigger: 'blur'}],},
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
      passageList: [],
      classifyList:[],
      //小区数据集合
      currentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 树筛选文本
      filterText:'',
      //树结构
      comOptions:[],
      communityId :null,
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        passageName:null,
        //passageId:undefined,
      },
      // 表单参数
      form: {},
    }
  },
  created() {
    this.getTree();
    //this.getList();
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
    handleNodeClick(row){
      this.queryParams.communityId = row.communityId
      this.communityId = row.communityId
      this.getList();
    },
    // 获取树数据
    getTree(){
      getCurrentList().then(res =>{
        this.currentList = res.data;
        this.queryParams.communityId = this.currentList[0].communityId;
        this.communityId = this.currentList[0].communityId;
        this.getList();
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 新增按钮
    handleAdd(){
      this.reset();
      this.open = true;
      this.title = "新增出入口"
    },
    /** 编辑按钮 */
    handleUpdate(row) {
      getPassageInfo(row.passageId).then(resp => {
        this.form = resp.data
      })
      this.open = true;
      this.title = "编辑出入口";
    },
    /** 删除按钮 */
    handleDelete(row) {
      const passageIds = row.passageId || this.ids;
      this.$modal.confirm('是否确认删除用户编号为"' + passageIds + '"的数据项？').then(function() {
        return removePassage(passageIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 提交按钮 */
    submitForm: function () {
      //this.form.createBy = "admin";
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.passageId != null) {
            updatePassage(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.communityId = this.communityId;
            addPassage(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 表单重置*/
    reset() {
      this.form = {
        passageName: undefined,
      };
    },
    /** 搜索按钮操作 */
    handleQuery() {
      //this.getTree();
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];

      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.passageId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 查询列表 */
    getList() {
      this.openView = 0;
      this.loading = false;
      listPassage(this.queryParams).then(response => {
        this.passageList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
  }
}
</script>

<style scoped>

</style>

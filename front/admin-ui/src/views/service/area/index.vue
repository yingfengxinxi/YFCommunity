<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" class="cus-fm">
      <el-form-item label="地区名称" prop="areaName">
        <el-input
          v-model="queryParams.areaName"
          placeholder="请输入地区名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
          maxlength="20"
        />
      </el-form-item>
      <el-form-item>
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
          v-hasPermi="['system:area:add']"
        >新增地区</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="areaList"
      row-key="areaId"
      default-expand-all
      :tree-props="{children: 'children', hasChildren: 'hasChildren'}"
    >
      <el-table-column prop="areaName" label="部门名称"></el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column prop="createBy" label="创建人"></el-table-column>
      <el-table-column label="操作" align="left" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:area:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            style="color: #f56c6c"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:area:remove']"
          >删除</el-button>
          <el-button
            v-if="scope.row.viewFlag == 0"
            size="mini"
            type="text"
            icon="el-icon-plus"
            @click="handleAdd(scope.row)"
            v-hasPermi="['system:area:add']"
          >添加下级</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <h4 class="form-header">基本信息</h4>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="所属上级" prop="parentId">
          <treeselect v-model="form.parentId"
                      :options="areaOptions"
                      :normalizer="normalizer"
                      :default-expand-level="Infinity"
                      placeholder="选择所属上级" />
        </el-form-item>
        <el-form-item label="地区名称" prop="areaName">
          <el-input v-model="form.areaName" placeholder="请输入地区名称"/>
        </el-form-item>
      </el-form>
      <h4 class="form-header"></h4>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import * as AREA from "@/api/system/community/area.js";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {equals, isEmpty, isNotEmpty} from "@/utils/common";

export default {
  name: "Area",
  components: { Treeselect },
  data() {
    return {
      submitFlag:true,
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 表格树数据
      areaList: [],
      // 部门树选项
      areaOptions: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态数据字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        areaName: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        areaName: [
          { required: true, message: "地区名称不能为空", trigger: "blur" },
          { max: 30, message: '文本输入过长', trigger: 'blur' }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询部门列表 */
    getList() {
      this.loading = true;
      AREA.listArea(this.queryParams).then(response => {
        if(isNotEmpty(response.data)){
          this.areaList = this.handleTree(response.data, "areaId");
        }
        this.loading = false;
      });
    },
    /** 转换部门数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.areaId,
        label: node.areaName,
        children: node.children
      };
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        areaId: undefined,
        parentId: undefined,
        areaName: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.submitFlag = true;
      if (row != undefined) {
        this.form.parentId = row.areaId;

      }
      this.open = true;
      this.title = "添加地区";
      AREA.listAreaExcludeDistrict().then(response => {
        let areaData = response.data;
        if(isEmpty(areaData)){
          this.form.parentId=undefined;
        }else {
          this.areaOptions = this.handleTree(areaData, "areaId");
        }
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.submitFlag = true;
      AREA.getArea(row.areaId).then(response => {
        this.form = response.data;
        if(equals(response.data.parentId,0)){
          this.form.parentId=undefined;
        }
        this.open = true;
        this.title = "修改地区";
      });
      AREA.listAreaExcludeChild(row.areaId).then(response => {

        this.areaOptions = this.handleTree(response.data, "areaId");
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid && this.submitFlag) {
          this.submitFlag = false;
          if (isEmpty(this.form.parentId)) {
            this.form.parentId=0;
          }
          if (this.form.areaId != undefined) {
            AREA.updateArea(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.submitFlag = true;
              this.getList();
            });
          } else {
            AREA.addArea(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.submitFlag = true;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('是否确认删除名称为"' + row.areaName + '"的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return AREA.delArea(row.areaId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
    }
  }
};
</script>

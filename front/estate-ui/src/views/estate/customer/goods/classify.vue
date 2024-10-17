<template>
  <div class="app-container">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
        <el-form-item label="最后操作时间" prop="dateRange">
          <el-date-picker
            v-model="dateRange"
            type="daterange"
            value-format="yyyy-MM-dd"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="最后操作人" prop="updateBy">
          <el-input
            v-model="queryParams.updateBy"
            placeholder="请输入用户名称"
            clearable
            @keyup.enter.native="handleQuery"
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
            v-hasPermi="['business:classify:insert']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['business:classify:delete']"
          >删除</el-button>
        </el-col>

        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="classifyList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="分类名称" align="center" prop="classifyName" show-overflow-tooltip/>
        <el-table-column label="状态" align="center" prop="status">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.classifyStatus"
              active-value="0"
              inactive-value="1"
              @change="handleStatusChange(scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="说明" align="center" prop="remark" show-overflow-tooltip/>
        <el-table-column label="最后操作人" align="center" prop="updateBy" />
        <el-table-column label="最后操作时间" align="center" prop="updateTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.updateTime) }}</span>
          </template>
        </el-table-column>

        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['business:classify:update']"
            >修改</el-button>
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


    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="分类名称" prop="classifyName">
          <el-input v-model="form.classifyName" placeholder="请输入分类名称" maxlength="10"/>
        </el-form-item>
        <el-form-item label="说明" prop="remark">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入说明" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>

import {
  addClassify,
  changeUserStatus,
  delClassify,
  getClassifyList,
  getInfo,
  updateClassify
} from "@/api/estate/customer/goods/classify";

export default {
  name: 'classify',
  data(){
    return{
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
      classifyList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        dateRange:null,
        updateBy:null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        classifyName:[
          { required: true, message: "分类信息不能为空", trigger: "blur" }
        ],
        remark: [
          { required: true, message: "说明不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getList();
  },
  methods:{
    /** 查询列表 */
    getList() {
      getClassifyList(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.classifyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },

    /** 表单重置*/
    reset() {
      this.form = {
        classifyId: undefined,
        estateId: undefined,
        classifyName: undefined,
        classifyStatus: undefined,
        remark: undefined,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
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
      this.ids = selection.map(item => item.classifyId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.open = true;
      this.title = "新增物品分类";
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const classifyId = row.classifyId || this.ids;
      this.$modal.confirm('是否确认删除此分类?(删除后此分类无法查询)').then(function() {
        return delClassify(classifyId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const classifyId = row.classifyId;
      getInfo(classifyId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改物品分类";
      });
    },
    /** 提交按钮 */
    submitForm() {
       this.$refs["form"].validate(valid => {
         if (valid) {
           if (this.form.classifyId != undefined) {
             updateClassify(this.form).then(response => {
               this.$modal.msgSuccess("修改成功");
               this.open = false;
               this.getList();
             });
           } else {
             addClassify(this.form).then(response => {
               this.$modal.msgSuccess("新增成功");
               this.open = false;
               this.getList();
             });
           }
         }
       });
    },
    // 状态修改
    handleStatusChange(row) {
      let text = row.classifyStatus === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要' + text + '"' + row.classifyName + '"分类吗？').then(function () {
        return changeUserStatus(row.classifyId, row.classifyStatus);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function () {
        row.classifyStatus = row.classifyStatus === "0" ? "1" : "0";
      });
    },

  }
}

</script>

<style scoped>

</style>


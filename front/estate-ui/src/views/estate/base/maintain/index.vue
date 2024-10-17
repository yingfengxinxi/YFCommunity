<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="最后操作时间" label-width="120px">
        <el-date-picker
          v-model="dateRange"
          size="small"
          style="width: 220px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始时间"
          end-placeholder="结束时间"
        ></el-date-picker>
      </el-form-item>
      <el-form-item label="最后操作人" prop="updateBy" label-width="120px">
        <el-input
          v-model="queryParams.updateBy"
          placeholder="请输入最后操作人"
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
        >新增</el-button>
      </el-col>
      <el-col :span="1.5">
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
<!--      <el-col :span="1.5">
        <el-button
          type="warning"
          plain
          icon="el-icon-download"
          size="mini"
          @click="handleExport"

        >导入</el-button>
      </el-col>-->
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="workList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="维修工种" align="center" prop="workName" />
      <el-table-column label="状态" align="center" prop="workStatus" >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.workStatus"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="说明" align="center" prop="remark" />
      <el-table-column label="最后操作人" align="center" prop="updateBy" />
      <el-table-column label="组后操作时间" align="center" prop="updateTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
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

    <!-- 添加或修改物业信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="维修工种" prop="workName">
          <el-input v-model="form.workName" placeholder="请输入维修工种" />
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

import { listMainTain, getMainTain, delMainTain, addMainTain, updateMainTain ,changeWorkStatus} from "@/api/estate/base/maintain";

export default {
  name: "Estate",
  data() {
    return {
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
      // 物业信息表格数据
      workList: [],
      // 日期范围
      dateRange: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        updateBy: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        workName: [
          { required: true, message: "维修工种不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询物业信息列表 */
    getList() {
      this.loading = true;
      listMainTain(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.workList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        estateId: null,
        workId:null,
        workStatus:null,
        workName:null,
        updateBy:null,
        updateTime:null,
        remark:null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
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
      this.ids = selection.map(item => item.workId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增维修工种";
    },
    /** 查看按钮操作 */
   /* handleFind(row) {
      this.showGoods = true;
      this.open =true;
      const id = row.goodsId || this.ids
      getGoods(id).then(response => {
        this.form = response.data;
        this.title = "查看物品列表";
      });
    },*/
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const workId = row.workId || this.ids
      getMainTain(workId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改维修工种";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.workId != null) {
            updateMainTain(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMainTain(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    // 任务状态修改
    /*handleStatusChange(row) {
      let text = row.workStatus === "0" ? "启用" : "停用";
      this.$confirm('确认要' + text + ' "' + row.workName + '" 维修工种吗?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return changeWorkStatus(row.workId, row.workStatus);
      }).then(() => {
        this.msgSuccess(text + "成功");
      }).catch(function() {
        row.workStatus = row.workStatus === "0" ? "1" : "0";
      });
    },*/
    // 角色状态修改
    handleStatusChange(row) {
      let text = row.workStatus === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要' + text + ' "' + row.workName + '" 维修工种吗?').then(function() {
        return changeWorkStatus(row.workId, row.workStatus);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.workStatus = row.workStatus === "0" ? "1" : "0";
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const workIds = row.workId || this.ids;
      this.$modal.confirm('是否确认删除该维修工种数据项？').then(function() {
        return delMainTain(workIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download('system/estate/export', {
        ...this.queryParams
      }, `estate_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

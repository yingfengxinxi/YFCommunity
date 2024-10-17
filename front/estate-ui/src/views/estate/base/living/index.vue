<template>
  <div class="app-container">
    <div v-show="!open">
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

    <el-table v-loading="loading" :data="homeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="居家报修类型" align="center" prop="repairName" />
      <el-table-column label="状态" align="center" prop="repairStatus" >
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.repairStatus"
            active-value="0"
            inactive-value="1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="说明" align="center" prop="remark" />
      <el-table-column label="最后操作人" align="center" prop="updateBy" />
      <el-table-column label="最后操作时间" align="center" prop="updateTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleFind(scope.row)"
          >查看</el-button>
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
</div>
    <!-- 添加或修改物业信息对话框 -->
    <el-dialog :title="title" :visible.sync="showAdd" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="居家报修类型" prop="repairName" label-width="120px">
          <el-input v-model="form.repairName" placeholder="请输入居家报修类型" />
        </el-form-item>
        <el-form-item label="说明" prop="remark" label-width="120px">
          <el-input v-model="form.remark" type="textarea" placeholder="请输入说明" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <div v-show="showGoods">
      <h4 class="form-header">灾害类型的基本信息</h4>
      <el-form ref="form" :model="form" label-width="120px">
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="灾害类型：">{{ form.repairName }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="状态：">
              <div v-if="form.repairStatus == 0">启用</div>
              <div v-else-if="form.repairStatus == 1">停用</div>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="起始操作人：">{{ form.createBy }}</el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="起始操作时间：">{{ form.createTime }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="最后操作人：">
              {{ form.updateBy }}
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="最后操作时间：">
              {{ form.updateTime}}
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="说明：">{{ form.remark }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>


      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel" style="float: right">关 闭</el-button>
      </div>
    </div>

  </div>
</template>

<script>

import { listLiving, getLiving, delLiving, addLiving, updateLiving ,changeRepairStatus} from "@/api/estate/base/living";
import {changeWorkStatus} from "@/api/estate/base/maintain";


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
      homeList: [],
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
        repairName: [
          { required: true, message: "居家维修类型不能为空", trigger: "blur" }
        ],
      },
      //详情页面
      showGoods:false,
      //显示添加或修改页面
      showAdd:false,
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询物业信息列表 */
    getList() {
      this.loading = true;
      listLiving(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.homeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.showGoods = false;
      this.showAdd = false;
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        repairId:null,
        repairName:null,
        repairStatus:null,
        estateId: null,
        updateBy:null,
        updateTime:null,
        remark:null,
        createBy:null,
        createTime:null,
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
      this.ids = selection.map(item => item.repairId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.showAdd = true;
      this.title = "新增居家维修类型";
    },
    /** 查看按钮操作 */
    handleFind(row) {
      this.showGoods = true;
      this.open =true;
      const repairId = row.repairId || this.ids
      getLiving(repairId).then(response => {
        this.form = response.data;
        this.title = "查看居家维修类型";
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const repairId = row.repairId || this.ids
      getLiving(repairId).then(response => {
        this.form = response.data;
        this.showAdd = true;
        this.open = true;
        this.title = "修改居家维修类型";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.repairId != null) {
            updateLiving(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.showAdd = false;
              this.open = false;
              this.getList();
            });
          } else {
            addLiving(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.showAdd = false;
              this.getList();
            });
          }
        }
      });
    },
    // 任务状态修改
    /*handleStatusChange(row) {
      let text = row.repairStatus === "0" ? "启用" : "停用";
      this.$confirm('确认要' + text + ' "' + row.repairName + '" 居家维修类型吗?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return changeRepairStatus(row.repairId, row.repairStatus);
      }).then(() => {
        this.msgSuccess(text + "成功");
      }).catch(function() {
        row.repairStatus = row.repairStatus === "0" ? "1" : "0";
      });
    },*/
    handleStatusChange(row) {
      let text = row.repairStatus === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要' + text + ' "' + row.repairName + '" 居家维修类型吗?').then(function() {
        return changeRepairStatus(row.repairId, row.repairStatus);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.repairStatus = row.repairStatus === "0" ? "1" : "0";
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const repairIds = row.repairId || this.ids;
      this.$modal.confirm('是否确认删除该居家维修类型数据项？').then(function() {
        return delLiving(repairIds);
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

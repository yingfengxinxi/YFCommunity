<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
      <el-form-item label="巡更类型" prop="tourName">
        <el-input
          v-model="queryParams.tourName"
          placeholder="请输入巡更类型"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="tourStatus">
        <el-select
          v-model="queryParams.tourStatus"
          placeholder="请选择状态"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="status in statusList"
            :key="status.dictValue"
            :label="status.dictLabel"
            :value="status.dictValue"
          />
        </el-select>
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
        >删除
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="list" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="巡更类型" align="center" prop="tourName" show-overflow-tooltip/>
      <el-table-column label="状态" align="center" prop="tourStatus">
        <template slot-scope="scope">
          <span :class="{'statusGreen': scope.row.tourStatus==='0','statusRed': scope.row.tourStatus==='1'}">
            {{ tourStatusFormat(scope.row.tourStatus) }}
          </span>
        </template>
      </el-table-column>
      <el-table-column label="最后操作人" align="center" prop="updateBy"/>
      <el-table-column label="最后操作时间" align="center" prop="updateTime" width="180"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" style="{'color':}">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改
          </el-button>
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getListPage"
    />

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="巡更类型" prop="tourName">
          <el-input v-model="form.tourName" placeholder="请输入巡更类型" maxlength="32"/>
        </el-form-item>
        <el-form-item label="状态" prop="tourStatus">
          <el-radio-group v-model="form.tourStatus">
            <el-switch
              v-model="form.tourStatus"
              active-color="#13ce66"
              inactive-color="#ff4949"
              active-value="0"
              inactive-value="1"
            >
            </el-switch>
          </el-radio-group>
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
import * as tourType from "@/api/estate/work/nightPatrol/nightPatrolType";
import {getDicts} from "@/api/system/dict/data";

export default {
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
      // 表格数据
      list: [],
      // 巡更状态list
      statusList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tourStatus: undefined,
        tourName: undefined
      },
      // 表单参数
      form: {
        tourId: undefined,
        tourName: "",
        tourStatus: "0",
        tourType: ""
      },
      // 表单校验
      rules: {
        tourName: [
          {required: true, message: "巡更类型不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getListPage();
    this.getStatus();
  },
  methods: {
    // 查询列表
    getListPage() {
      this.loading = true;
      tourType.getListPage(this.queryParams).then(response => {
          this.list = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 查询系统状态字典
    getStatus() {
      getDicts("sys_normal_disable").then(response => {
        this.statusList = response.data;
      });
    },
    tourStatusFormat(status) {
      const filter = this.statusList.filter(item => item.dictValue == status);
      return filter[0].dictLabel;
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        tourId: undefined,
        tourName: undefined,
        tourStatus: "0"
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getListPage();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加巡更类型";
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.tourId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.title = "修改巡更类型";
      this.form = {
        tourId: row.tourId,
        tourName: row.tourName,
        tourStatus: row.tourStatus,
        tourType: row.tourType
      };
      this.open = true;
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.tourId !== undefined) {
            tourType.edit(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getListPage();
            });
          } else {
            tourType.add(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getListPage();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const dictIds = row.tourId || this.ids;
      this.$modal.confirm('是否确认删除所选巡更类型?(删除后此类型无法查询)').then(function () {
        return tourType.remove(dictIds);
      }).then(() => {
        this.getListPage();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.statusGreen {
  color: #008000;
}

.statusRed {
  color: #ff0000;
}
</style>

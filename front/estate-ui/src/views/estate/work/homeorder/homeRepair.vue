<template>
  <div class="app-container">
    <div v-show="mainOpen">
      <el-form :model="queryParams" ref="queryForm" v-show="showSearch" :inline="true" class="cus-fm">
        <el-form-item label="居家维修类型" prop="repairName">
          <el-input
            v-model="queryParams.repairName"
            placeholder="请输入居家维修类型"
            clearable
            size="small"
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态" prop="repairStatus">
          <el-select
            v-model="queryParams.repairStatus"
            placeholder="状态"
            clearable
            size="small"
            style="width: 240px">
            <el-option label="开启" value="0"></el-option>
            <el-option label="停用" value="1"></el-option>
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
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="disasterList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column label="居家维修类型" prop="repairName"/>
        <el-table-column label="状态" align="center" prop="repairStatus" :formatter="statusFormatter">
        </el-table-column>
        <el-table-column label="说明" align="center" prop="remark" show-overflow-tooltip></el-table-column>
        <el-table-column label="最后操作人" align="center" prop="updateBy"></el-table-column>
        <el-table-column label="最后操作时间" align="center" prop="updateTime">
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
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
        @pagination="getList"
      />
    </div>
    <!-- 添加或修改灾害报事信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" @close="cancel">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="10" :offset="1">
            <el-form-item label="居家维修类型" prop="repairName">
              <el-input v-model="form.repairName" style="width: 250px" placeholder="请输入居家维修类型" :maxlength="10"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10" :offset="1">
            <el-form-item label="定金" prop="deposit">
              <el-input v-model="form.deposit" style="width: 250px" placeholder="请输入定金" :maxlength="5"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="15" :offset="1">
            <el-form-item label="状态" prop="repairStatus">
              <el-radio-group>
                <el-switch
                  v-model="form.repairStatus"
                  active-value="0"
                  inactive-value="1"
                  active-color="#13ce66"
                  inactive-color="#ff4949"
                >
                </el-switch>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="15" :offset="1">
            <el-form-item label="说明">
              <el-input v-model="form.remark" type="textarea" :rows="5" placeholder="请输入说明（限制100字以内）" :maxlength="100"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-row>
          <el-col :span="18">
            <el-button @click="cancel">取 消</el-button>
          </el-col>
          <el-col :span="4">
            <el-button type="primary" @click="submitForm" :style="{ display: visibleLine }">确 定</el-button>
          </el-col>
        </el-row>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import {
  addHomeRepair,
  delHomeRepair,
  getInfo,
  listHomeRepair,
  upHomeRepair
} from "@/api/estate/work/homeorder/homeRepair";
import {equals} from "@/utils/common";

export default {
  name: "homeRepair",
  data() {
    return {
      value: true,
      visibleLine: '',  //隐藏
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
      mainOpen: true,
      // 总条数
      total: 0,
      // 角色表格数据
      disasterList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态数据字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        repairName: undefined,
        repairStatus: undefined
      },
      importDataBtnText: "导入",
      importDataDisabled: false,

      // 表单参数
      form: {
        repairStatus: "0"
      },
      // 表单校验
      rules: {
        repairName: [
          {required: true, message: "请输入居家维修类型", trigger: "blur"},
        ],
        deposit: [
          {required: true, message: "请输入定金", trigger: "blur"},
          {pattern: /^[0-9]*$/, message: "请输入正确定金", trigger: "blur"},
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      listHomeRepair(this.queryParams).then(response => {
        this.disasterList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /**
     * 新增
     */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加居家维修类型";
    },
    /** 状态改变 */
    repairStatus(value) {
      this.form.repairStatus = value;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const disasterId = row.repairId || this.ids;
      this.$modal.confirm('是否确认删除此居家维修类型?(删除后此类型无法查询)').then(function () {
        return delHomeRepair(disasterId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /**
     * 修改
     */
    handleUpdate(row) {
      this.reset();
      this.open = true;
      this.title = "修改居家维修";
      getInfo(row.repairId).then(response => {
        this.form = response.data;
      });
    },
    /**
     * 导入
     */
    // handleImport(){},

    // /**
    //  * 查看
    //  */
    // handleInfo(data){
    //     this.form=data,
    //     this.open=true
    //     this.title ="灾害报事详情"
    //     this.visibleLine = 'none'
    // },
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        disasterId: undefined,
        estateId: undefined,
        disasterName: undefined,
        remark: undefined,
        repairStatus: "0"
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.repairId != undefined) {
            upHomeRepair(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.value = true;
              this.getList();
            });
          } else {
            addHomeRepair(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.value = true;
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.repairId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /**
     * 后台返回的状态改变
     */
    statusFormatter(row, column) {
      const status = row.repairStatus
      if (status == 0) {
        return '启用'
      } else if (status == 1) {
        return '停用'
      }
    }
    //    //处理导入事件
    // beforeUpload() {
    //   this.importDataBtnText = '正在导入';
    //   this.importDataDisabled = true;
    // },
    // //导入成功
    // onSuccess() {
    //   this.importDataBtnText = '导入数据';
    //   this.importDataDisabled = false;
    //   this.getList();
    // },
    // //导入失败
    // onError() {
    //   this.importDataBtnText = '导入数据';
    //   this.importDataDisabled = false;
    // },
  }
}
</script>

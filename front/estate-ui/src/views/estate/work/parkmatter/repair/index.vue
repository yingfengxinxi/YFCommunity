<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true"  label-width="68px">
      <el-form-item label="报修类型" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="状态" prop="state">
        <el-select
          v-model="queryParams.state"
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
          v-hasPermi="['work:parkrepair:save']"
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
          v-hasPermi="['work:parkrepair:removes']"
        >删除
        </el-button>
      </el-col>
    </el-row>

    <el-table v-loading="loading" :data="list" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="报事类型" align="center" prop="repair_name" show-overflow-tooltip/>
      <el-table-column label="状态" align="center">
        <template slot-scope="scope">
          <el-tag type="success" v-show="scope.row.repair_status=='0'">正常</el-tag>
          <el-tag type="danger" v-show="scope.row.repair_status=='1'">停用</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="说明" align="center" prop="remark" show-overflow-tooltip/>
      <el-table-column label="最后操作人" align="center" prop="update_by" show-overflow-tooltip/>
      <el-table-column label="最后操作时间" align="center" width="180" show-overflow-tooltip>
        <template slot-scope="scope">
          {{parseTime(scope.row.update_time)}}
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" style="{'color':}">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['work:parkrepair:update']"
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

    <!-- 添加或修改参数配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="报事类型" prop="repair_name">
          <el-input v-model="form.repair_name" placeholder="请输入报事类型"   :maxlength="55"/>
        </el-form-item>
        <el-form-item label="说明" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入报事说明" :maxlength="480"/>
        </el-form-item>
        <el-form-item label="状态" prop="repair_status">
          <el-switch
            v-model="form.repair_status"
            active-value='0'
            inactive-value='1'
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import  {save,search,removes,update} from "@/api/estate/work/parkmatter/repair";
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
      // 总条数
      total: 0,
      // 表格数据
      list: [],
      // 巡查状态list
      statusList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name:'',
        state:''
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        repair_name: [
          {required: true, message: "报事类型不能为空", trigger: "blur"}
        ],
        remark: [
          {required: true, message: "报事说明不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getStatus();
  },
  methods: {
    // 查询列表
    getList() {
      this.loading = true;
       search(this.queryParams).then(response => {
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
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {

      this.form= {
        repair_status:"0"
      };
      this.getList()
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
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加报事类型";
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.repair_id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.title = "修改报事类型";
      this.form = row;
      this.open = true;
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.repair_id !== undefined) {
           update(this.form).then(e => {
             const {code,msg}=e
                if (code==200){
                  this.$modal.msgSuccess(msg);
                  this.open = false;
                  this.getList();
                }else {
                    this.$modal.msgError(msg)
                }
            });
          } else {
            save(this.form).then(e => {
              const {code,msg}=e
              if (code==200){
                this.$modal.msgSuccess(msg);
                this.open = false;
                this.getList();
              }else {
                this.$modal.msgError(msg)
              }
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.repair_id || this.ids;
      this.$modal.confirm('是否确认删除所选报事类型?(删除后此类型无法查询)').then(function () {
        return removes(ids);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    }
  }
};
</script>

<template>
  <div class="app-container">
    <div v-show="!open">
      <el-form :model="queryParams" ref="queryForm" v-show="showSearch" :inline="true" class="cus-fm">
        <el-form-item label="角色名称" prop="roleName">
          <el-input
            v-model="queryParams.roleName"
            placeholder="请输入角色名称"
            clearable
            size="small"
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态" prop="status">
          <el-select
            v-model="queryParams.status"
            placeholder="角色状态"
            clearable
            size="small"
            style="width: 240px"
          >
            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="创建时间">
          <el-date-picker
            v-model="dateRange"
            size="small"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
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
            v-hasPermi="['system:role_ver:add']"
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
            v-hasPermi="['system:role_ver:remove']"
          >删除</el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
      <el-table v-loading="loading" :data="roleList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="角色名称" prop="roleName"/>
        <el-table-column label="状态" align="center">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.status"
              active-value="0"
              inactive-value="1"
              @change="handleStatusChange(scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
        <el-table-column label="服务版本" align="center" prop="versionName"></el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:role_ver:edit']"
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
    <!-- 添加或修改角色配置对话框 -->
    <div v-show="open">
      <h4 class="form-header">基本信息</h4>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="角色名称" prop="roleName">
              <el-input v-model="form.roleName" placeholder="请输入角色名称" />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="3 ">
            <el-form-item label="角色顺序" prop="roleSort">
              <el-input-number style="width: 100%" v-model="form.roleSort" controls-position="right" :min="0" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="服务版本" prop="versionId">
              <el-select v-model="form.versionId" placeholder="请选择" style="width: 100%">
                <el-option
                  v-for="item in roleVerList"
                  :key="item.versionId"
                  :label="item.versionName"
                  :value="item.versionId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="3">
            <el-form-item label="状态">
              <el-radio-group v-model="form.status">
                <el-radio
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                >{{dict.dictLabel}}</el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="15" :offset="1">
            <el-form-item label="备注">
              <el-input v-model="form.remark" type="textarea" :rows="5" placeholder="请输入内容"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <h4 class="form-header"></h4>
      <div slot="footer" class="dialog-footer">
        <el-row>
          <el-col :offset="20" :span="2"><el-button @click="cancel">取 消</el-button></el-col>
          <el-col :span="2"><el-button type="primary" @click="submitForm">确 定</el-button></el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import * as PROP_ROLE from "@/api/system/property/roleVer.js";

export default {
  name: "PropRole",
  data() {
    return {
      submitFlag:true,
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
      // 角色表格数据
      roleList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 状态数据字典
      statusOptions: [],
      //服务版本
      roleVerList:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        roleType: "00",
        roleName: undefined,
        roleKey: undefined,
        status: undefined
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        roleName: [
          { required: true,max:50, message: "角色名称不能为空", trigger: "blur" },
          { min: 3, max: 15, message: '长度在 3 到 15 个字符', trigger: 'blur' }
        ],
        versionId: [
          { required: true, message: "请选择服务版本", trigger: "blur" }
        ],
        roleSort: [
          { required: true, message: "角色顺序不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询角色列表 */
    getList() {
      this.loading = true;
      this.queryParams.roleType = "10";
      PROP_ROLE.listPropRole(this.addDateRange(this.queryParams, this.dateRange)).then(
        response => {
          this.roleList = response.rows;
          this.total = response.total;
          this.loading = false;
          console.log("res",response)
        }
      );
    },
    // 角色状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$confirm('确认要"' + text + '""' + row.roleName + '"角色吗?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return PROP_ROLE.changeRoleStatus(row.roleId, row.status);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
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
        roleId: undefined,
        roleName: undefined,
        roleKey: undefined,
        roleType: "10",
        roleSort: 0,
        dataScope: "1",
        status: "0",
        remark: undefined
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
      this.ids = selection.map(item => item.roleId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.submitFlag = true;
      this.open = true;
      this.title = "添加物业角色";
      PROP_ROLE.listRoleVer().then(response => {
          this.roleVerList = response.data;
        }
      );
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.submitFlag = true;
      this.reset();
      const roleId = row.roleId
      PROP_ROLE.getPropRole(roleId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改物业角色";
      });
      PROP_ROLE.listRoleVer().then(response => {
          this.roleVerList = response.data;
        }
      );
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid && this.submitFlag) {
          this.submitFlag = false;
          if (this.form.roleId != undefined) {
            PROP_ROLE.updatePropRole(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.submitFlag = true;
              this.getList();
            });
          } else {
            this.form.roleType = "10";
            this.form.dataScope = "4";
            PROP_ROLE.addPropRole(this.form).then(response => {
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
      const roleIds = row.roleId || this.ids;
      this.$confirm('是否确认删除所选数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return PROP_ROLE.delPropRole(roleIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
    }
  }
}
</script>

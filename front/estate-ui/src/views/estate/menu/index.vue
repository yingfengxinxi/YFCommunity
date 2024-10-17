<template>
  <div class="app-container">
    
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
          <el-form-item label="菜单编号" prop="estateMenuCode">
            <el-input
              v-model="queryParams.estateMenuCode"
              placeholder="请输入菜单编号"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="菜单名称" prop="estateMenuName">
            <el-input
              v-model="queryParams.estateMenuName"
              placeholder="请输入菜单名称"
              clearable
              style="width: 240px"
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
          
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="menuList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="菜单编号" prop="estateMenuCode"/>
          <el-table-column label="菜单名称" prop="estateMenuName"/>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini" type="text" icon="el-icon-edit"
                class="text-info"
                @click="handleUpdate(scope.row)"
              >修改</el-button>
              <el-button
                size="mini" type="text" icon="el-icon-delete"
                class="text-danger"
                @click="handleDelete(scope.row)"
              >删除</el-button>
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
      
    <!-- 添加或修改角色配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="菜单编号" prop="estateMenuCode">
          <el-input :disabled="isDisabled" v-model="form.estateMenuCode" placeholder="请输入菜单编号" style="width:260px ;"/>
        </el-form-item>
        <el-form-item label="菜单名称" prop="estateMenuName">
          <el-input v-model="form.estateMenuName" placeholder="请输入菜单名称" style="width:260px ;"/>
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
  list,
  getById,
  deleById,
  add,
  update
} from "@/api/estate/menu/menu";

export default {
  name: "menu",

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
      isDisabled:false,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 角色表格数据
      menuList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 菜单列表
      menuOptions: [],
      // 部门列表
      deptOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        estateMenuName: undefined,
        estateMenuCode:undefined
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        estateMenuCode: [
          { required: true, message: "菜单编号不能为空", trigger: "blur" }
        ],
        estateMenuName: [
          { required: true, message: "菜单名称不能为空", trigger: "blur" }
        ]
      }
    };
  },
  watch: {

  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询角色列表 */
    getList() {
      this.loading = true;
      list(this.queryParams).then(response => {
          this.menuList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },

 
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
        this.form = {
          estateMenuId: undefined,
          estateMenuName: undefined,
          estateMenuCode:undefined
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
      this.ids = selection.map(item => item.estateMenuId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
   
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.isDisabled = false;
      this.open = true;
      this.title = "添加菜单";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.isDisabled = true;
      const estateMenuId = row.estateMenuId || this.ids
      getById(estateMenuId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改菜单";
      });
    },
  
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.estateMenuId != undefined) {
           
            update(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
           
            add(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const estateMenuIds = row.estateMenuId || this.ids;
      this.$modal.confirm('是否确认删除角色编号为"' + estateMenuIds + '"的数据项？').then(function() {
        return deleById(estateMenuIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('system/role/export', {
        ...this.queryParams
      }, `role_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

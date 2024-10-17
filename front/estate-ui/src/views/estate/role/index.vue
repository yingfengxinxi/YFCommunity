<template>
  <div class="app-container">
    
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
          <el-form-item label="角色名称" prop="estateRoleName">
            <el-input
              v-model="queryParams.estateRoleName"
              placeholder="请输入角色名称"
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

        <el-table v-loading="loading" :data="roleList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="角色编号" prop="estateRoleId"/>
          <el-table-column label="角色名称" prop="estateRoleName"/>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini" type="text" icon="el-icon-edit"
                class="text-info"
                @click="handleUpdate(scope.row)"
              >修改</el-button>
              <el-button
                size="mini" type="text" icon="el-icon-edit"
                class="text-info"
                @click="handleAddMenu(scope.row)"
              >授权菜单</el-button>
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
        <el-form-item label="角色名称" prop="estateRoleName">
          <el-input v-model="form.estateRoleName" placeholder="请输入角色名称" style="width:260px ;"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 角色授权菜单配置对话框 -->
    <el-dialog :title="authMenuTitle"
      :visible.sync="authMenuOpen"
      width="50%"
      center>
        
          <el-transfer
            filterable
            :filter-method="filterMethod"
            filter-placeholder="请输入菜单名称"
            :titles="['可选菜单', '授权菜单']"
            v-model="yxMenuList"
            :data="menuList">
          </el-transfer>
      
        <span slot="footer" class="dialog-footer">
          <el-button @click="reBack()" style="margin:0px 30px 0px 30px">返回</el-button>
          <el-button type="success" @click="saveChoose()">保存</el-button>
        </span>
      </el-dialog>

  </div>
</template>

<script>
import {
  list,
  getById,
  deleById,
  add,
  update,
  listRoleMenu,
  insertRoleMenu
} from "@/api/estate/role/role";
import {
  listMenu
} from "@/api/estate/menu/menu";

export default {
  name: "menu",

  data() {
   
    return {
      yxMenuList: [],
      filterMethod(query, item) {
          return item.pinyin.indexOf(query) > -1;
      },
      authMenuOpen: false,
      authMenuTitle:'',
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
      roleList: [],
      menuList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 角色列表
      menuOptions: [],
      // 部门列表
      deptOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        estateRoleName: undefined
      },
      estateRoleId:'',
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        estateRoleName: [
          { required: true, message: "角色名称不能为空", trigger: "blur" }
        ]
      }
    };
  },
  watch: {

  },
  created() {
    this.handleQuery();
  },
  methods: {
    /** 查询角色列表 */
    getList() {
      this.loading = true;
      list(this.queryParams).then(response => {
          this.roleList = response.rows;
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
          estateRoleId: undefined,
          estateRoleName: undefined,
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
      this.ids = selection.map(item => item.estateRoleId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    //授权菜单
    saveChoose() { 
      console.log("this.yxMenuList>>", this.yxMenuList);
      if (this.yxMenuList.length==0) { 
        this.$modal.msgError("请选择授权菜单");
        return;
      }
      var roleMenuList = [];
      console.log("this.estateRoleId>>", this.estateRoleId);
      this.yxMenuList.forEach(item => { 
        roleMenuList.push({"estateRoleId":parseFloat(this.estateRoleId),"estateMenuId":item})
      })
      insertRoleMenu(roleMenuList).then(res => { 
        this.$modal.msgSuccess("授权成功");
        this.authMenuOpen = false;
        this.getList();
      })
    },
    reBack() { 
      this.authMenuOpen = false;
      this.getList();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.isDisabled = false;
      this.open = true;
      this.title = "添加角色";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.isDisabled = true;
      const estateRoleId = row.estateRoleId || this.ids
      getById(estateRoleId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改角色";
      });
    },
    handleAddMenu(row) { 
      this.authMenuTitle=row.estateRoleName+"授权菜单"
      this.authMenuOpen = true;
      this.yxMenuList = [];
      this.estateRoleId = parseFloat(row.estateRoleId);
      //查询已授权菜单
      listRoleMenu(row.estateRoleId).then(res => { 
        var yxMenuList = res.data;
        if (yxMenuList) { 
          yxMenuList.forEach(item => { 
            this.yxMenuList.push(item.estateMenuId);
          })
        }
      })
      //查询全部菜单
      listMenu().then(res => { 
        this.menuList = res.data;
        if (this.menuList) { 
          this.menuList.forEach(item => { 
            item.label = item.estateMenuName;
            item.key = item.estateMenuId;
            item.pinyin = item.estateMenuName;
          })
        }
      })
    },
  
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.estateRoleId != undefined) {
           
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
      const estateRoleIds = row.estateRoleId || this.ids;
      this.$modal.confirm('是否确认删除角色编号为"' + estateRoleIds + '"的数据项？').then(function() {
        return deleById(estateRoleIds);
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

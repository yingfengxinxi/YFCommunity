<template>
  <div class="app-container">
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
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="versionList">
<!--      <el-table-column align="center" label="版本id" prop="versionId"/>-->
      <el-table-column align="center" label="版本名称" prop="versionName" :show-overflow-tooltip="true"/>
      <el-table-column align="center" label="创建时间" prop="createTime">
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
            v-hasPermi="['system:role:edit']"
          >修改</el-button>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            style="color: #f56c6c"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:role:remove']"
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
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="版本名称" prop="versionName">
          <el-input v-model="form.versionName" placeholder="请输入版本名称" />
        </el-form-item>
        <el-form-item label="菜单权限">
          <el-checkbox v-model="menuExpand" @change="handleCheckedTreeExpand($event, 'menu')">展开/折叠</el-checkbox>
          <el-checkbox v-model="menuNodeAll" @change="handleCheckedTreeNodeAll($event, 'menu')">全选/全不选</el-checkbox>
<!--          <el-checkbox v-model="form.menuCheckStrictly" @change="handleCheckedTreeConnect($event, 'dept')">父子联动</el-checkbox>-->
          <el-checkbox v-model="menuLinkage" v-show="false">父子联动</el-checkbox>
          <el-tree
            class="tree-border"
            :data="menuOptions"
            show-checkbox
            ref="menu"
            node-key="id"
            empty-text="加载中，请稍后"
            :props="defaultProps"
          ></el-tree>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import * as ROLE_VER from "@/api/system/property/serverver";
import {treeForVer as menuTreeselect} from "@/api/system/menu";

export default {
  name: "RoleVer",
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
      //表格数据
      versionList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层（数据权限）
      openDataScope: false,
      menuExpand: false,
      menuNodeAll: false,
      menuLinkage: true,
      // 日期范围
      dateRange: [],
      // 菜单列表
      menuOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        versionName: [
          { required: true, message: "版本名称不能为空", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询角色列表 */
    getList() {
      this.loading = true;
      ROLE_VER.listRoleVersion(this.queryParams).then(response => {
          this.versionList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 查询菜单树结构 */
    getMenuTree() {
      menuTreeselect().then(response => {
        this.menuOptions = response.data;
      });
    },
    // 所有菜单节点数据
    getMenuAllCheckedKeys() {
      // 目前被选中的菜单节点
      let checkedKeys = this.$refs.menu.getCheckedKeys();
      // 半选中的菜单节点
      let halfCheckedKeys = this.$refs.menu.getHalfCheckedKeys();
      checkedKeys.unshift.apply(checkedKeys, halfCheckedKeys);
      return checkedKeys;
    },
    /** 根据角色ID查询菜单树结构 */
    getRoleVerMenuTree(versionId) {
      return ROLE_VER.roleVerMenuTree(versionId).then(response => {
        this.menuOptions = response.menus;
        return response;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      if (this.$refs.menu != undefined) {
        this.$refs.menu.setCheckedKeys([]);
      }
      this.menuExpand = false,
      this.menuNodeAll = false,
      this.form = {
          versionId: undefined,
          versionName: undefined,
          menuIds: [],
          menuCheckStrictly: true
      };
      this.resetForm("form");
    },
    // 树权限（展开/折叠）
    handleCheckedTreeExpand(value, type) {
      let treeList = this.menuOptions;
      for (let i = 0; i < treeList.length; i++) {
        this.$refs.menu.store.nodesMap[treeList[i].id].expanded = value;
      }
    },
    // 树权限（全选/全不选）
    handleCheckedTreeNodeAll(value, type) {
      this.$refs.menu.setCheckedNodes(value ? this.menuOptions: []);
    },
    // 树权限（父子联动）
    handleCheckedTreeConnect(value, type) {
      this.form.menuCheckStrictly = false;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getMenuTree();
      this.submitFlag = true;
      this.open = true;
      this.title = "添加服务版本";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.submitFlag = true;
      const versionId = row.versionId
      const versionMenu = this.getRoleVerMenuTree(versionId);
      ROLE_VER.getRoleVersion(versionId).then(response => {
        this.form = response.data;
        this.open = true;
        this.$nextTick(() => {
          versionMenu.then(res => {
            let checkedKeys = res.checkedKeys
            checkedKeys.forEach((v) => {
              this.$nextTick(()=>{
                this.$refs.menu.setChecked(v, true ,false);
              })
            })
          });
        });
        this.title = "修改服务版本";
      });
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid && this.submitFlag) {
          this.submitFlag = false;
          if (this.form.versionId != undefined) {
            this.form.menuIds = this.getMenuAllCheckedKeys();
            ROLE_VER.updateRoleVersion(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.submitFlag = true;
              this.getList();
            });
          } else {
            this.form.menuIds = this.getMenuAllCheckedKeys();
            ROLE_VER.addRoleVersion(this.form).then(response => {
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
      const versionId = row.versionId;
      this.$confirm('是否确认删除该数据?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return ROLE_VER.delRoleVersion(versionId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
    }
  }
};
</script>

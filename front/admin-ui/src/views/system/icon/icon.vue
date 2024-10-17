<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
    >
      <el-form-item label="名称" prop="Name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="分类" prop="classify">
        <el-select
          v-model="queryParams.classify"
          placeholder="请选择分类"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in typeLIst"
            :key="dict.id"
            :label="dict.name"
            :value="dict.id"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="状态" prop="status">
        <el-select
          v-model="queryParams.status"
          placeholder="状态"
          clearable
          style="width: 240px"
        >
          <el-option
            v-for="dict in statusList"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
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
          v-hasPermi="['system:icon:add']"
          >新增</el-button
        >
      </el-col>
      <!--      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-edit"
          size="mini"
          :disabled="single"
          @click="handleUpdate"
          v-hasPermi="['system:role:edit']"
        >修改</el-button>
      </el-col>-->
      <el-col :span="1.5">
        <el-button
          type="danger"
          plain
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
          v-hasPermi="['system:icon:remove']"
          >删除</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="iconList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="序号" type="index" width="120" />
      <el-table-column
        label="icon名称"
        prop="name"
        :show-overflow-tooltip="true"
        width="150"
      />
      <el-table-column
        label="icon图片"
        prop="url"
        :show-overflow-tooltip="true"
        width="150"
      >
        <template slot-scope="scope">
          <el-image
            style="width: 40px; height: 40px"
            :src="scope.row.url"
            fit="scale-down"
          ></el-image>
        </template>
      </el-table-column>
      <el-table-column label="分类" prop="roleSort" width="100">
        <template slot-scope="scope">
          {{ getLabel(typeLIst, scope.row.classify, "id", "name") }}
        </template>
      </el-table-column>
      <el-table-column label="跳转类型" prop="goType" width="100">
        <template slot-scope="scope">
          {{ getLabel(urlTypeList, scope.row.goType, "value", "label") }}
        </template>
      </el-table-column>
      <el-table-column label="状态" align="center" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.status"
            :active-value="1"
            :inactive-value="0"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="置顶" align="center" width="100">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.topStatus"
            :active-value="1"
            :inactive-value="0"
            @change="handleStatusChange1(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-hasPermi="['system:icon:edit']"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-hasPermi="['system:icon:remove']"
            >删除</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改角色配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="icon名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入icon名称" />
        </el-form-item>
        <el-form-item label="icon图标" prop="url">
          <ImageUpload v-model="form.url" :limit="1" :value="form.url" />
          <el-alert
            class="el-alert"
            title="图片尺寸：88 * 88 px"
            type="warning"
            show-icon
            :closable="false"
          >
          </el-alert>
        </el-form-item>
        <el-form-item label="分类" prop="classify">
          <el-select
            v-model="form.classify"
            placeholder="请选择分类"
            clearable
            style="width: 240px"
          >
            <el-option
              v-for="dict in typeLIst"
              :key="dict.id"
              :label="dict.name"
              :value="dict.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="跳转类型" prop="goType">
          <el-select
            v-model="form.goType"
            placeholder="请选择跳转类型"
            clearable
            style="width: 240px"
          >
            <el-option
              v-for="dict in urlTypeList"
              :key="dict.value"
              :label="dict.label"
              :value="dict.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          label="跳转路径"
          prop="goTo"
          v-if="form.goType == 2 || form.goType == 0"
        >
          <el-input v-model="form.goTo" placeholder="请输入跳转路径" />
        </el-form-item>
        <el-form-item label="跳转图片" v-if="form.goType == 3" prop="goTo">
          <ImageUpload v-model="form.goTo" :limit="1" :value="form.goTo" />
        </el-form-item>
        <el-form-item label="排序" prop="sort">
          <el-input-number
            v-model="form.sort"
            controls-position="right"
            :min="0"
          />
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
  typeListAll,
  iconList,
  iconEdit,
  iconAdd,
  removeIcon,
  switchStatusIcon,
  switchStatusIcontop,
} from "@/api/system/icon";

export default {
  name: "Role",
  dicts: ["sys_normal_disable"],
  data() {
    //验证路径
    var validatorr = (rule, value, callback) => {
      if (!this.form.goTo) {
        if (this.form.goType == "3") {
          callback(new Error("图片不能为空"));
        } else {
          callback(new Error("路径不能为空"));
        }
      }

      callback();
    };
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
      // 角色表格数据
      iconList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示弹出层（数据权限）
      openDataScope: false,
      menuExpand: false,
      menuNodeAll: false,
      deptExpand: true,
      deptNodeAll: false,
      // 日期范围
      dateRange: [],
      // 数据范围选项
      dataScopeOptions: [
        {
          value: "1",
          label: "全部数据权限",
        },
        {
          value: "2",
          label: "自定数据权限",
        },
        {
          value: "3",
          label: "本部门数据权限",
        },
        {
          value: "4",
          label: "本部门及以下数据权限",
        },
        {
          value: "5",
          label: "仅本人数据权限",
        },
      ],
      // 菜单列表
      menuOptions: [],
      // 部门列表
      deptOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        classify: undefined,
        status: undefined,
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label",
      },
      // 状态列表
      statusList: [
        {
          label: "正常",
          value: 1,
        },
        {
          label: "禁用",
          value: 0,
        },
      ],
      // 分类列表
      typeLIst: [],
      urlTypeList: [
        {
          label: "内部页面",
          value: "0",
        },
        {
          label: "H5",
          value: "2",
        },
        {
          label: "图片",
          value: "3",
        },
      ],
      // 表单校验
      rules: {
        name: [
          { required: true, message: "icon名称不能为空", trigger: "blur" },
        ],
        url: [
          { required: true, message: "icon图标不能为空", trigger: "change" },
        ],
        classify: [
          { required: true, message: "分类不能为空", trigger: "change" },
        ],
        goType: [
          { required: true, message: "跳转类型不能为空", trigger: "change" },
        ],
        goTo: [{ required: true, validator: validatorr, trigger: "change" }],
        // goTo: [
        //   { required: true, message: "icon图标不能为空", trigger: "change" },
        // ],
        // Image: [{ required: true, message: "图片不能为空", trigger: "change" }],
        // roleSort: [
        //   { required: true, message: "不能为空", trigger: "blur" },
        // ],
      },
    };
  },
  created() {
    this.getList();
    this.getTypeListAll();
  },
  methods: {
    /**
     * 根据传入的值,返回对应的中文name，常用的地方是表格那里
     * list: 传入的源数组
     * id: 传入的值
     * value: 源数组中为了匹配id值的字段名称
     * label: 源数组中需要返回显示中文的字段名称
     * 示例：arr:[{dictValue: 0,dictLabel:'前端工程师'},{dictValue: 1,dictLabel:'Java工程师'}]
     * 调用getLabel(arr, 1, "dictValue", "dictLabel")返回了 Java工程师
     * */
    getLabel(list, id, value, label) {
      if (id != "" && Array.isArray(list) && list.length != 0) {
        return !list.find((item) => item[value] == id)
          ? id
          : list.find((item) => item[value] == id)[label];
      } else {
        return id;
      }
    },
    // 查询分类列表
    getTypeListAll() {
      typeListAll().then((res) => {
        this.typeLIst = res.data;
      });
    },
    /** 查询角色列表 */
    getList() {
      this.loading = true;
      iconList(this.queryParams).then((response) => {
        this.iconList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查询菜单树结构 */
    getMenuTreeselect() {
      typeListAll().then((response) => {
        this.typeLIst = response.data;
      });
    },
    // 角色状态修改
    handleStatusChange(row) {
      let text = row.status == "0" ? "禁用" : "开启";
      this.$modal
        .confirm("确认要" + text + "该" + row.name + "吗？")
        .then(function () {
          return switchStatusIcon({ id: row.id, status: row.status });
        })
        .then(() => {
          this.$modal.msgSuccess(text + "成功");
        })
        .catch(function () {
          row.status = row.status == 0 ? 1 : 0;
        });
    },
    // 置顶
    handleStatusChange1(row) {
      console.log(row.topStatus, "row.topStatus");
      let text = row.topStatus == "0" ? "取消置顶" : "置顶";
      this.$modal
        .confirm("确认要" + text + "该" + row.name + "吗？")
        .then(function () {
          return switchStatusIcontop({ id: row.id, topStatus: row.topStatus });
        })
        .then(() => {
          this.$modal.msgSuccess(text + "成功");
        })
        .catch(function () {
          row.topStatus = row.topStatus == 0 ? 1 : 0;
        });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      // this.reset();
    },
    // 取消按钮（数据权限）
    cancelDataScope() {
      this.openDataScope = false;
      this.reset();
    },
    // 表单重置
    reset() {
      if (this.$refs.menu != undefined) {
        this.$refs.menu.setCheckedKeys([]);
      }
      (this.deptExpand = true),
        (this.deptNodeAll = false),
        (this.form = {
          id: undefined,
          name: undefined,
          classify: undefined,
          url: "",
          goTo: "",
          goType: null,
          sort: null,
          Image: null,
        });
      this.resetForm("form");
      console.log(this.form);
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
      this.ids = selection.map((item) => item.id);
      this.ids = this.ids.join(",");
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加icon配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      console.log(row, "row");
      // this.reset();
      row.classify = Number(row.classify);
      this.form = row;
      this.open = true;
      this.title = "修改角色";
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id != undefined) {
            // this.form.menuIds = this.getMenuAllCheckedKeys();
            iconEdit(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            // this.form.menuIds = this.getMenuAllCheckedKeys();
            iconAdd(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 提交按钮（数据权限） */
    submitDataScope: function () {
      if (this.form.roleId != undefined) {
        this.form.deptIds = this.getDeptAllCheckedKeys();
        dataScope(this.form).then((response) => {
          this.$modal.msgSuccess("修改成功");
          this.openDataScope = false;
          this.getList();
        });
      }
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const roleIds = row.id || this.ids;
      this.$modal
        .confirm("是否确认删除所选数据项？")
        .then(function () {
          return removeIcon(roleIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download(
        "system/role/export",
        {
          ...this.queryParams,
        },
        `role_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
<style>
.el-alert {
  line-height: initial;
  margin-top: 10px;
}
</style>

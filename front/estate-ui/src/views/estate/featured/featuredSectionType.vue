<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item  label="所属小区" prop="communityId">
        <el-select v-model="queryParams.communityId" placeholder="请选择" style="width: 100%;">
          <el-option
            v-for="(item, index) in currentList"
              :key="index"
                          :label="item.communityName"
                          :value="item.communityId"
                        ></el-option>
          </el-select>
      </el-form-item>
      <el-form-item label="名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
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
          >新增</el-button
        >
      </el-col>

      
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="schoolList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="封面" align="center" prop="cover">
        <template slot-scope="scope">
          <image-preview :src="scope.row.postersImage" :width="50" :height="50" />
        </template>
      </el-table-column>
      <el-table-column label="名称" align="center" prop="name" />
      <el-table-column label="顺序" align="center" prop="sort"/>
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
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            style="color: #f56c6c"
            @click="handleDelete(scope.row)"
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

    <!-- 添加或修改类型对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="110px"
      >
        <el-form-item label="所属小区" prop="communityId">
          <el-select v-model="form.communityId" placeholder="请选择" style="width:300px">
          <el-option
            v-for="(item, index) in currentList"
              :key="index"
                          :label="item.communityName"
                          :value="item.communityId"
                        ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="封面" prop="cover">
          <image-upload v-model="form.cover" :limit="1" />
        </el-form-item>
        <el-form-item label="主图" prop="postersImage">
          <image-upload v-model="form.postersImage" :limit="1" />
        </el-form-item>
        <el-form-item label="名称" prop="name">
          <el-input v-model="form.name" placeholder="请输入名称"  style="width:300px"/>
        </el-form-item>
        <el-form-item label="小程序页面地址" prop="pageUrl">
          <el-input v-model="form.pageUrl" placeholder="请输入小程序页面地址" style="width:300px" />
        </el-form-item>
       
        <el-form-item label="群二维码" prop="groupCode">
          <image-upload v-model="form.groupCode" :limit="1" />
        </el-form-item>
        <el-form-item label="顺序" prop="sort">
          <el-input-number
            :max="99"
            :min="1"
            v-model="form.sort"
            placeholder="请输入顺序"
            style="width:300px"
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
  getListPage,
  insert,
  update,
  del,
  getById
} from "@/api/estate/featured/featuredSectionType";
import {
  getCurrentList
} from "@/api/estate/community";
export default {
  name: "featuredSectionType",
  data() {
    return {
      // 遮罩层
      loading: true,
      keyUrl: "",
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      currentList:[],
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 学校表格数据
      schoolList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
     
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        communityId:undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        communityId: [
          { required: true, message: "请选择所属小区", trigger: "blur" },
        ],
        cover: [
          { required: true, message: "请上传封面", trigger: "blur" },
        ],
        postersImage: [
          { required: true, message: "请上传主图", trigger: "blur" },
        ],
        name: [
          { required: true, message: "名称不能为空", trigger: "blur" },
        ],
        
        sort: [{ required: true, message: "顺序不能为空", trigger: "blur" }],
      },
    };
  },
  created() {
    //所属小区
    getCurrentList().then(res => { 
      this.currentList = res.data;
    })
    this.getList();
  },

  methods: {
    // 任务状态修改
    handleStatusChange(row) {
      let text = row.status === "0" ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '"当前板块类型吗？').then(function() {
        return update(row);
      }).then(() => {
        this.$modal.msgSuccess(text + "成功");
        this.getList();
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    /** 查询列表 */
    getList() {
      this.loading = true;
      getListPage(this.queryParams).then((response) => {
        this.schoolList = response.rows;
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
        id: null,
        name: null,
        sort: 0,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
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
      this.queryParams.name = undefined;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加社区关怀类型";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getById(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改社区关怀类型";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          
          if (this.form.id != null) {
            update(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            insert(this.form).then((response) => {
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
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除编号为"' + ids + '"的数据项？')
        .then(function () {
          return del(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
  },
};
</script>
<style lang="scss">
.el-input-group__append,
.el-input-group__prepend {
  background-color: #1890ff !important;
  color: #fff !important;
  border-color: #1890ff !important;
  border-radius: 0 4px 4px 0;
}
.cascaderW {
  width: 350px;
}

</style>
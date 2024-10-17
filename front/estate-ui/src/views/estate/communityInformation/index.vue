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
      <el-form-item label="资讯名称" prop="msgTitle">
        <el-input
          v-model="queryParams.msgTitle"
          placeholder="请输入资讯名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属小区" prop="communityId" label-width="80px">
        <el-select
          v-model="queryParams.communityId"
          placeholder="请选择"
          style="width: 240px"
        >
          <el-option
            v-for="(item, index) in currentList"
            :key="index"
            :label="item.communityName"
            :value="item.communityId"
          ></el-option>
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
          >新增</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          :disabled="single"
          icon="el-icon-edit"
          size="mini"
          @click="handleZhiDing"
          >置顶</el-button
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
      <el-table-column label="资讯封面" align="center" prop="activePicture">
        <template slot-scope="scope">
          <image-preview
            :src="scope.row.activePicture"
            :width="50"
            :height="50"
          />
        </template>
      </el-table-column>
      <el-table-column label="所属小区" align="center" prop="communityId">
        <template slot-scope="scope">
          {{ getCommunityName(scope.row.communityId) }}
        </template>
      </el-table-column>

      <el-table-column
        label="资讯名称"
        width="200"
        show-overflow-tooltip
        align="center"
        prop="msgTitle"
      />
      <el-table-column label="发布时间" align="center" prop="createTime" />
      <el-table-column label="发布人" align="center" prop="createBy" />
      <el-table-column label="显示顺序" align="center" prop="sortNumber" />

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

    <!-- 添加或修改活动对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="92px">
        <el-form-item label="资讯封面" prop="activePicture">
          <image-upload v-model="form.activePicture" :limit="1" />
        </el-form-item>
        <el-form-item label="所属小区" prop="communityId">
          <el-select
            v-model="form.communityId"
            placeholder="请选择"
            style="width: 100%"
          >
            <el-option
              v-for="(item, index) in currentList"
              :key="index"
              :label="item.communityName"
              :value="item.communityId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="资讯名称" prop="msgTitle">
          <el-input v-model="form.msgTitle" :maxlength="30" placeholder="请输入资讯名称" />
        </el-form-item>
        <el-form-item label="资讯内容" prop="content">
          <editor v-model="form.content" :min-height="192" />
        </el-form-item>
        <el-form-item label="显示顺序" prop="sortNumber">
          <el-input-number
            :min="1"
            :max="1000"
            v-model="form.sortNumber"
            placeholder="请输入显示顺序"
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
  getById,
  updateTop,
} from "@/api/estate/communityInformation/communityInformation";
import { getCurrentList } from "@/api/estate/community";
export default {
  name: "CommunityInformation",
  data() {
    return {
      currentList: [],
      studentJobTotal: 0,
      openloation: false,
      modalMap: false,
      // 遮罩层
      loading: true,
      keyUrl: "",
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
      // 学校表格数据
      schoolList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      studentLoading: true,

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        msgTitle: null,
        communityCareId: undefined,
      },
      // 表单参数
      form: {
        content: undefined,
      },
      // 表单校验
      rules: {
        activePicture: [
          { required: true, message: "请上传资讯封面", trigger: "blur" },
        ],
        communityId: [
          { required: true, message: "请选择所属小区", trigger: "blur" },
        ],
        msgTitle: [
          { required: true, message: "资讯名称不能为空", trigger: "blur" },
        ],
        content: [
          { required: true, message: "请输入资讯内容", trigger: "blur" },
        ],
        sortNumber: [
          { required: true, message: "请输入显示顺序", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    //所属小区
    getCurrentList().then((res) => {
      this.currentList = res.data;
    });
    this.getList();
  },

  methods: {
    getCommunityName(communityId) {
      var obj = this.currentList.filter(
        (item) => item.communityId == communityId
      )[0];
      if (obj) {
        return obj.communityName;
      }
    },
    /** 列表 */
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
        content: undefined,
        msgTitle: null,
        activePicture: null,
        communityId: undefined,
        sortNumber: 0,
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
      this.queryParams.msgTitle = undefined;
      this.queryParams.communityId = undefined;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      console.log(">>>>", this.ids);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加社区资讯";
    },
    handleZhiDing() {
      console.log("this.ids>>", this.ids);
      const id = this.ids[0];
      this.$modal
        .confirm('是否确认置顶编号为"' + id + '"的数据项？')
        .then(function () {
          return updateTop(id);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("置顶成功");
        })
        .catch(() => {});
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getById(id).then((response) => {
        this.form = response.data;

        this.open = true;
        this.title = "修改社区资讯";
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
          return del(ids, row.top);
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
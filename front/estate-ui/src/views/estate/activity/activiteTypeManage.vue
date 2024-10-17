<template>
  <div class="app-container">
    <div v-show="openView === 0">
      <el-row :span="24">
        <!--   选择树   -->
        <el-col :span="4">
          <div class="head-container">
            <el-input placeholder="请输入小区名称" v-model="filterText">
            </el-input>

            <el-tree
              class="filter-tree hide-tree"
              :data="currentList"
              :props="currentListProps"
              default-expand-all
              :filter-node-method="filterNode"
              icon-class="el-icon-star-on"
              @node-click="handleNodeClick"
              highlight-current
              ref="tree"
            >
            </el-tree>
          </div>
        </el-col>
        <!--   右侧   -->
        <el-col :span="20" :xs="24">
          <el-form
            :model="queryParams"
            ref="queryForm"
            size="small"
            :inline="true"
            v-show="showSearch"
            label-width="68px"
          >
            <el-form-item label="活动类型" prop="typeName">
              <el-input
                v-model="queryParams.typeName"
                placeholder="请输入活动类型名称"
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
              <el-button
                icon="el-icon-refresh"
                size="mini"
                float="right"
                @click="resetQuery"
                >重置</el-button
              >
            </el-form-item>
          </el-form>
          <div>
            <el-button
              type="primary"
              plain
              icon="el-icon-add"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['business:activitetype:insert']"
              >新增
            </el-button>
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              >删除</el-button
            >
          </div>

          <el-table
            v-loading="loading"
            :data="activiteList"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="序号" align="center" prop="typeId" />
            <el-table-column
              label="活动类型名称"
              align="center"
              prop="typeName"
            />
            <el-table-column
              label="创建时间"
              align="center"
              prop="createTime"
            />
            <el-table-column
              label="修改时间"
              align="center"
              prop="updateTime"
            />
            <el-table-column label="操作人" align="center" prop="updateBy" />
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
                  v-hasPermi="['business:activitetype:update']"
                  >修改</el-button
                >
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  style="color: #f56c6c"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['business:activitetype:delete']"
                  >删除
                </el-button>
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
        </el-col>
      </el-row>
      <!-- 添加或修改变更申请对话框 -->
      <el-dialog
        :title="title"
        :visible.sync="open"
        width="500px"
        append-to-body
      >
        <el-form ref="form" :model="form" label-width="120px" :rules="rules">
          <el-form-item label="活动类型名称" prop="typeName">
            <el-input
              v-model="form.typeName"
              placeholder="请输入活动类型名称"
              maxlength="10"
            />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { getCurrentList } from "@/api/estate/community";
import {
  addActivite,
  getActiviteInfo,
  listManage,
  removeActivite,
  updateActivite,
} from "@/api/estate/activity/activiteType";

export default {
  name: "activiteTypeManage",
  data() {
    return {
      openView: false,
      // 遮罩层
      loading: true,
      // 表单校验
      rules: {
        typeName: [
          { required: true, message: "类型名不能为空", trigger: "blur" },
        ],
      },
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
      activiteList: [],
      classifyList: [],
      //小区数据集合
      currentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 树筛选文本
      filterText: "",
      //树结构
      comOptions: [],
      communityId: null,
      currentListProps: {
        children: "communityId",
        label: "communityName",
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        activityName: null,
        typeId: undefined,
        typeName: undefined,
      },
      // 表单参数
      form: {},
    };
  },
  created() {
    this.getTree();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
  methods: {
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(row) {
      this.queryParams.communityId = row.communityId;
      this.communityId = row.communityId;
      this.getList();
    },
    // 获取树数据
    getTree() {
      getCurrentList().then((res) => {
        this.currentList = res.data;
        this.queryParams.communityId = this.currentList[0].communityId;
        this.communityId = this.currentList[0].communityId;
        this.getList();
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.typeId);
      this.multiple = !selection.length;
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 新增按钮
    handleAdd() {
      if (this.communityId == null) {
        this.$modal.msgError("请选择小区");
      } else {
        this.reset();
        this.open = true;
        this.title = "新增活动类型";
      }
    },
    /** 编辑按钮 */
    handleUpdate(row) {
      getActiviteInfo(row.typeId).then((resp) => {
        this.form = resp.data;
      });
      this.open = true;
      this.title = "编辑活动类型";
    },
    /** 批量删除按钮 */
    handleDelete(row) {
      if (row.typeId != null) {
        this.ids = [];
        this.ids.push(row.typeId);
      }
      this.$modal
        .confirm("是否确认删除该数据项？")
        .then(() => {
          removeActivite(this.ids).then(() => {});
        })
        .then(() => {
          this.$modal.msgSuccess("删除成功");
          this.getList();
        })
        .catch(() => {});
    },
    /** 提交按钮 */
    submitForm: function () {
      this.form.createBy = "admin";
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.typeId != null) {
            updateActivite(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.communityId = this.communityId;
            addActivite(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 表单重置*/
    reset() {
      this.form = {
        typeId: undefined,
        typeName: undefined,
      };
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        communityId: this.currentList[0].communityId,
        communityName: undefined,
        activityName: null,
        typeId: undefined,
        typeName: undefined,
        submitDate: [],
      };
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 查询列表 */
    getList() {
      this.openView = 0;
      this.loading = false;
      listManage(this.queryParams).then((response) => {
        this.activiteList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
  },
};
</script>

<style scoped>
</style>

<template>
  <div class="app-container">
    <div v-show="openView === 0">
      <el-form
        :model="queryParams"
        ref="queryForm"
        size="small"
        :inline="true"
        v-show="showSearch"
      >
        <el-form-item label="公示标题" prop="investTitle">
          <el-input
            style="width: 215px"
            v-model="queryParams.investTitle"
            placeholder="请输入公示标题"
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

        <el-form-item label="公示类型" prop="investTitle">
          <el-select
            v-model="queryParams.investType"
            placeholder="请选择公示类型"
            style="width: 180px"
          >
            <el-option label="物业招商公示" value="0" />
            <el-option label="物业通报公示" value="1" />
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
            v-hasPermi="['estate:invest:add']"
            >新增</el-button
          >

          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            style="color: #f56c6c"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['estate:invest:remove']"
            >删除</el-button
          >
        </el-col>
        <right-toolbar
          :showSearch.sync="showSearch"
          @queryTable="getList"
        ></right-toolbar>
      </el-row>

      <!--数据列表-->
      <el-table
        v-loading="loading"
        :data="investList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column label="公示标题" align="center" prop="investTitle" />
        <el-table-column label="公示类型" align="center" prop="investType">
          <template slot-scope="scope">
            {{ scope.row.investType == "0" ? "物业招商公示" : "物业通报公示" }}
          </template>
        </el-table-column>
        <el-table-column label="所属小区" align="center" prop="communityId">
          <template slot-scope="scope">
            {{ getCommunityName(scope.row.communityId) }}
          </template>
        </el-table-column>
        <el-table-column label="公示发布时间" align="center" prop="createTime">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.updateTime) }}</span>
          </template>
        </el-table-column>

        <el-table-column label="状态" align="center" prop="investStatus">
          <template slot-scope="scope">
            {{ scope.row.investStatus == "0" ? "已发布" : "已撤回" }}
          </template>
        </el-table-column>
        <el-table-column label="阅读数" align="center" prop="readNum" />
        <el-table-column label="创建人" align="center" prop="createBy" />
        <el-table-column label="创建时间" align="center" prop="createTime">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
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
              icon="el-icon-view"
              @click="handleView(scope.row)"
              v-hasPermi="['estate:invest:query']"
              >查看
            </el-button>
            <!-- <br> -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['estate:invest:edit']"
              >修改</el-button
            >
            <!-- <br> -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleBack(scope.row)"
              v-if="['0'].includes(scope.row.investStatus)"
              v-hasPermi="['estate:invest:back']"
              >撤回</el-button
            >
            <!-- <br v-if="['0'].includes(scope.row.investStatus)"> -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUp(scope.row)"
              v-if="['1'].includes(scope.row.investStatus)"
              v-hasPermi="['estate:invest:up']"
              >发布</el-button
            >
            <!-- <br v-if="['1'].includes(scope.row.investStatus)"> -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              style="color: #f56c6c"
              @click="handleDelete(scope.row)"
              v-hasPermi="['estate:invest:remove']"
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
    </div>

    <!--查看详情页-->
    <div v-show="openView === 1">
      <h4 class="form-header" style="color: #0558e7">公示详情</h4>
      <div style="display: flex">
        <el-form ref="form" :model="form" :rules="rules" label-width="180px">
          <el-col :span="20">
            <el-row>
              <el-col :span="6">
                <el-form-item label="公示标题：">
                  <div style="width: 280px">{{ investObj.investTitle }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="7">
                <el-form-item label="公示类型：">
                  <div style="width: 280px">
                    {{
                      investObj.investType == "0"
                        ? "物业招商公示"
                        : "物业通报公示"
                    }}
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item label="发布时间：">
                  <div style="width: 280px">{{ investObj.updateTime }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="7">
                <el-form-item label="通报物业：">
                  <div style="width: 280px">{{ investObj.estateName }}</div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item label="阅读数：">
                  <div style="width: 280px">{{ investObj.readNum }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="10" :offset="7">
                <el-form-item label="状态：">{{
                  consumerStatusFormat(investObj)
                }}</el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item label="发布人：">
                  <div style="width: 280px">{{ investObj.createBy }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="10" :offset="7">
                <el-form-item label="创建时间：">
                  <div style="width: 280px">
                    <div style="width: 280px">{{ investObj.createTime }}</div>
                  </div>
                </el-form-item>
              </el-col>
              <el-form-item label="简介：">
                <div style="width: 280px">
                  <div style="width: 280px">
                    {{ investObj.briefIntroduction }}
                  </div>
                </div>
              </el-form-item>
              <el-col>
                <el-form-item label="公示内容：">
                  <editor
                    class="editor-view"
                    v-model="investObj.content"
                    read-only="read-only"
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="cancel">关闭</el-button>
      </div>
    </div>

    <!-- 添加或修改用户配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        size="medium"
        label-width="92px"
      >
        <el-form-item label="标题：" prop="investTitle">
          <el-input
            v-model="form.investTitle"
            placeholder="请输入"
            controls-position="right"
            maxlength="50"
          ></el-input>
        </el-form-item>
        <el-form-item label="公示类型：" prop="investType">
          <el-select
            v-model="form.investType"
            placeholder="请选择公示类型"
            style="width: 100%"
          >
            <el-option label="物业招商公示" value="0" />
            <el-option label="物业通报公示" value="1" />
          </el-select>
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

        <el-form-item label="简介" prop="briefIntroduction">
          <el-input
            type="textarea"
            v-model="form.briefIntroduction"
            maxlength="500"
            show-word-limit
          ></el-input>
        </el-form-item>
        <el-form-item label="公示内容" prop="content">
          <editor v-model="form.content" :min-height="192" />
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
  listInvest,
  addInvest,
  backInvest,
  upInvest,
  updateInvest,
  delInvest,
  getInvest,
} from "@/api/estate/info/invest";
import { getCurrentList } from "@/api/estate/community";
export default {
  dicts: ["estate_notice_status", "estate_invest"],
  name: "invest",
  data() {
    return {
      currentList: [],
      openView: false,
      investObj: "",
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
      //项目集合
      communityList: [],
      // 总条数
      total: 0,
      // 社区公示表格数据
      investList: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 表单参数
      form: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        investTitle: undefined,
        investType: undefined,
      },
      // 表单校验
      rules: {
        investTitle: [
          { required: true, message: "标题不能为空", trigger: "blur" },
        ],
        communityId: [
          { required: true, message: "请选择所属小区", trigger: "blur" },
        ],
        investType: [
          { required: true, message: "公示类型标题不能为空", trigger: "blur" },
        ],
        briefIntroduction: [
          { required: true, message: "公示简介不能为空", trigger: "blur" },
        ],
        content: [
          { required: true, message: "公示内容不能为空", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    //所属小区
    getCurrentList().then((res) => {
      this.currentList = res.data;
    });
    this.openView = 0;
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
    /** 查询用户列表 */
    getList() {
      this.loading = true;
      listInvest(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.investList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 取消按钮
    cancel() {
      this.openView = 0;
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        investId: undefined,
        investTitle: undefined,
        investType: undefined,
        createBy: null,
        status: "0",
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
      this.queryParams.investType = undefined;
      this.resetForm("queryForm");
      this.handleQuery();
    },

    consumerStatusFormat(row, column) {
      return this.selectDictLabel(
        this.dict.type.estate_notice_status,
        row.investStatus
      );
    },

    consumerTypeFormat(row, column) {
      return this.selectDictLabel(this.dict.type.estate_invest, row.investType);
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.investId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增招商公示";
    },

    /*查看按钮操作*/
    handleView(row) {
      this.openView = 1;
      getInvest(row.investId).then((response) => {
        this.investObj = response.data;
      });
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const investId = row.investId || this.ids;
      getInvest(investId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改招商公示";
      });
    },

    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.content == null) {
            this.$modal.msgWarning("请输入内容");
            return;
          }
          if (this.form.investId != undefined) {
            updateInvest(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addInvest(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

    /** 撤回按钮操作 */
    handleBack(row) {
      const investId = row.investId;
      this.$modal
        .confirm("确定撤回该条公示吗")
        .then(function () {
          return backInvest(investId);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("撤回成功");
        })
        .catch(() => {});
    },

    /** 发布按钮操作 */
    handleUp(row) {
      const investId = row.investId || this.ids;
      this.$modal
        .confirm("是否确认重新发布该条公示")
        .then(function () {
          return upInvest(investId);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("发布成功");
        })
        .catch(() => {});
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const investIds = row.investId || this.ids;
      this.$modal
        .confirm("是否确认删除数据项")
        .then(function () {
          return delInvest(investIds);
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

<style>
.el-tooltip__popper {
  max-width: 20%;
}
.el-tooltip__popper,
.el-tooltip__popper.is-dark {
  background: rgb(48, 65, 86) !important;
  color: #fff !important;
  line-height: 24px;
}
</style>

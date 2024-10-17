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
        <el-form-item label="标题" prop="noticeTitle">
          <el-input
            style="width: 215px"
            v-model="queryParams.noticeTitle"
            placeholder="请输入标题"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="所属小区" prop="communityId">
          <el-select v-model="queryParams.communityId" placeholder="请选择">
            <el-option
              v-for="(item, index) in currentList"
              :key="index"
              :label="item.communityName"
              :value="item.communityId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="状态" prop="noticeStatus">
          <el-select
            v-model="queryParams.noticeStatus"
            @keyup.enter.native="handleQuery"
            placeholder="请选择"
          >
            <el-option label="已发布" value="0"></el-option>
            <el-option label="已撤回" value="1"></el-option>
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

          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            style="color: #e50000"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
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
        :data="noticeList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="50" align="center" />
        <el-table-column
          label="标题"
          align="center"
          prop="noticeTitle"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="发布范围"
          align="center"
          prop="communityId"
          :show-overflow-tooltip="true"
        >
          <template slot-scope="scope">
            {{ getCommunityName(scope.row.communityId) }}
          </template>
        </el-table-column>

        <el-table-column label="发布时间" align="center" prop="createTime">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.updateTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="发布人" align="center" prop="createBy" />
        <el-table-column label="状态" align="center" prop="noticeStatus">
          <template slot-scope="scope">
            {{ scope.row.noticeStatus == "0" ? "已发布" : "已撤回" }}
          </template>
        </el-table-column>
        <el-table-column label="阅读数" align="center" prop="readNum" />

        <el-table-column
          label="操作"
          align="center"
          width="200"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleView(scope.row)"
              >查看
            </el-button>
            <!-- <br> -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              >修改</el-button
            >
            <!-- <br /> -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleBack(scope.row)"
              v-if="['0'].includes(scope.row.noticeStatus)"
              >撤回</el-button
            >
            <!-- <br v-if="['0'].includes(scope.row.noticeStatus)"> -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUp(scope.row)"
              v-if="['1'].includes(scope.row.noticeStatus)"
              >发布</el-button
            >
            <!-- <br v-if="['1'].includes(scope.row.noticeStatus)"> -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              style="color: #e90000"
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
    </div>

    <!--查看详情页-->
    <div v-show="openView === 1">
      <h4 class="form-header" style="color: #0558e7">公示详情</h4>
      <div style="display: flex">
        <el-form ref="form" :model="form" :rules="rules" label-width="180px">
          <el-col :span="20">
            <el-row>
              <el-col :span="6">
                <el-form-item label="公告标题:">
                  <div style="width: 280px">{{ noticeObj.noticeTitle }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="7">
                <el-form-item label="发布范围:">
                  <div style="width: 280px">
                    {{ getCommunityName(noticeObj.communityId) }}
                  </div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item label="发布时间:">
                  <div style="width: 280px">{{ noticeObj.updateTime }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="7">
                <el-form-item label="通报物业:">
                  <div style="width: 280px">{{ noticeObj.estateName }}</div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item label="阅读数:">
                  <div style="width: 280px">{{ noticeObj.readNum }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="10" :offset="7">
                <el-form-item label="状态:">{{
                  noticeObj.noticeStatus == "0" ? "已发布" : "已撤回"
                }}</el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item label="发布人:">
                  <div style="width: 280px">{{ noticeObj.createBy }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="10" :offset="7">
                <el-form-item label="创建时间:">
                  <div style="width: 280px">
                    <div style="width: 280px">{{ noticeObj.createTime }}</div>
                  </div>
                </el-form-item>
              </el-col>
              <el-col>
                <el-form-item label="简介:">
                  {{ noticeObj.briefIntroduction }}
                </el-form-item>
              </el-col>

              <el-col>
                <el-form-item label="内容:">
                  <editor
                    class="editor-view"
                    v-model="noticeObj.content"
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
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        size="medium"
        label-width="92px"
      >
        <el-form-item label="标题:" prop="noticeTitle">
          <el-input
            v-model="form.noticeTitle"
            placeholder="请输入标题"
          ></el-input>
        </el-form-item>
        <el-form-item label="所属小区:" prop="communityId">
          <el-select
            v-model="form.communityId"
            placeholder="请选择所属小区"
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
        <el-form-item label="简介:" prop="briefIntroduction">
          <el-input
            type="textarea"
            v-model="form.briefIntroduction"
            placeholder="请输入简介"
            :autosize="{ minRows: 5, maxRows: 50 }"
          ></el-input>
        </el-form-item>
        <el-form-item label="活动内容:" prop="content">
          <editor
            v-model="form.content"
            :min-height="192"
            :upload-url="uploadUrl"
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
  listNotice,
  addNotice,
  backNotice,
  upNotice,
  updateNotice,
  delNotice,
  getNotice,
} from "@/api/estate/info/notice";
import { getCurrentList } from "../../../../api/estate/community";
export default {
  dicts: ["estate_notice_status"],
  name: "communityNotice",
  data() {
    return {
      //富文本回显地址
      uploadUrl: process.env.VUE_APP_BASE_API + "/common/upload",
      openView: false,
      noticeObj: "",
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      pi: null,
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
      noticeList: null,
      // 弹出层标题
      title: "",
      currentList: [],
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
        range: "0",
        noticeTitle: undefined,
        noticeStatus: undefined,
      },
      rangeList: [
        { value: "0", label: "全部" },
        { value: "1", label: "物业" },
        { value: "2", label: "业主" },
      ],
      // 表单校验
      rules: {
        communityId: [
          { required: true, message: "请选择发布范围", trigger: "blur" },
        ],
        noticeTitle: [
          { required: true, message: "标题不能为空", trigger: "blur" },
        ],
        range: [
          { required: true, message: "发布范围不能为空", trigger: "blur" },
        ],
        briefIntroduction: [
          { required: true, message: "请输入简介", trigger: "blur" },
        ],
        content: [
          {
            required: true,
            message: "活动内容不能为空",
            trigger: "change",
          },
        ],
      },
    };
  },
  created() {
    this.openView = 0;
    this.getCommunityList();
    this.getList();
  },
  methods: {
    getRangeList(range) {
      if (range) {
        return this.rangeList.filter((item) => item.value == range)[0].label;
      }
    },
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
      listNotice(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.noticeList = response.rows;
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
        noticeId: undefined,
        noticeTitle: undefined,
        range: "0",
        createBy: null,
        status: "0",
        communityIds: [],
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
      this.ids = selection.map((item) => item.noticeId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "新增通知公告";
    },

    getCommunityList() {
      getCurrentList().then((res) => {
        this.currentList = res.data;
      });
    },

    /*查看按钮操作*/
    handleView(row) {
      this.openView = 1;
      getNotice(row.noticeId).then((response) => {
        this.noticeObj = response.data;
      });
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const noticeId = row.noticeId || this.ids;
      getNotice(noticeId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改通知公告";
      });
    },

    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.noticeId != undefined) {
            updateNotice(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addNotice(this.form).then((response) => {
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
      const noticeId = row.noticeId;
      this.$modal
        .confirm("确定撤回该条公示吗")
        .then(function () {
          return backNotice(noticeId);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("撤回成功");
        })
        .catch(() => {});
    },

    /** 发布按钮操作 */
    handleUp(row) {
      const noticeId = row.noticeId || this.ids;
      this.$modal
        .confirm("是否确认重新发布该条公示")
        .then(function () {
          return upNotice(noticeId);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("发布成功");
        })
        .catch(() => {});
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const noticeIds = row.noticeId || this.ids;
      this.$modal
        .confirm("是否确认删除数据项")
        .then(function () {
          return delNotice(noticeIds);
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

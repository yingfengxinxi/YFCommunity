<template>
  <div class="app-container">
    <el-row style="margin-left: 2%">
      <el-row>
        <el-form
          :model="queryParams"
          ref="queryForm"
          size="small"
          :inline="true"
        >
          <el-form-item label="活动创建时间" prop="time">
            <el-date-picker
              v-model="queryParams.time"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              value-format="yyyy-MM-dd"
              end-placeholder="结束日期"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="活动名称" prop="name">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入"
              clearable
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
      </el-row>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="
              form = { need_num: 1, cover_url: '' };
              dialogVisible = true;
            "
            v-hasPermi="['voluntary:service:save']"
            >新增</el-button
          >
        </el-col>
      </el-row>
      <el-row>
        <el-table v-loading="loading" :data="dataList" ref="multipleTable">
          <el-table-column
            label="活动名称"
            align="center"
            prop="act_title"
            show-overflow-tooltip
          />
          <el-table-column label="封面" align="center">
            <template slot-scope="scope">
              <img
                :src="scope.row.cover_url"
                width="100"
                height="100"
                @click="openWindow(scope.row.cover_url)"
              />
            </template>
          </el-table-column>
          <el-table-column
            label="开始时间"
            align="center"
            width="180"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.start_time) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="结束时间"
            align="center"
            width="180"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.end_time) }}</span>
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
                @click="show(scope.row.voluntary_id)"
                >查看成员</el-button
              >
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="
                  form = scope.row;
                  dialogVisible = true;
                "
                v-hasPermi="['voluntary:service:update']"
                >修改</el-button
              >
              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                style="color: red"
                @click="deleted(scope.row.voluntary_id)"
                v-hasPermi="['voluntary:service:remove']"
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
      </el-row>
    </el-row>
    <!-- 成员 list -->
    <div>
      <el-dialog title="成员列表" :visible.sync="uList" width="50%">
        <el-row>
          <el-form
            :model="queryParamsUser"
            ref="queryFormUser"
            size="small"
            :inline="true"
          >
            <el-form-item label="成员名称" prop="name">
              <el-input
                v-model="queryParamsUser.name"
                placeholder="请输入"
                clearable
              />
            </el-form-item>
            <el-form-item>
              <el-button
                type="primary"
                icon="el-icon-search"
                size="mini"
                @click="handleQueryUser"
                >搜索</el-button
              >
              <el-button
                icon="el-icon-refresh"
                size="mini"
                @click="resetQueryUser"
                >重置</el-button
              >
            </el-form-item>
          </el-form>
        </el-row>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['voluntary:service:export']"
              >一键导出</el-button
            >
          </el-col>
        </el-row>
        <el-row>
          <el-table
            v-loading="loadingUser"
            :data="dataListUser"
            ref="multipleTable"
          >
            <el-table-column
              label="姓名"
              align="center"
              prop="user_name"
              show-overflow-tooltip
            />
            <el-table-column
              label="年龄"
              align="center"
              prop="user_age"
              show-overflow-tooltip
            />
            <el-table-column
              label="性别"
              align="center"
              width="180"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <span>{{
                  scope.row.gender == "0"
                    ? "男"
                    : scope.row.gender == "1"
                    ? "女"
                    : ""
                }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="所属小区"
              align="center"
              width="180"
              prop="community_name"
              show-overflow-tooltip
            />
            <el-table-column
              label="申请日期"
              align="center"
              width="180"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.apply_time) }}</span>
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
                  @click="
                    uView = true;
                    formUser = scope.row;
                  "
                  >查看</el-button
                >
              </template>
            </el-table-column>
          </el-table>

          <pagination
            v-show="totalUser > 0"
            :total="totalUser"
            :page.sync="queryParamsUser.pageNum"
            :limit.sync="queryParamsUser.pageSize"
            @pagination="getListUser"
          />
        </el-row>
      </el-dialog>
    </div>
    <!-- 成员 preview -->
    <div>
      <el-dialog title="" :visible.sync="uView" width="50%">
        <el-descriptions
          title="志愿者信息"
          :column="1"
          border
          :label-style="{ 'line-height': '40px' }"
        >
          <el-descriptions-item label="姓名">{{
            formUser.user_name
          }}</el-descriptions-item>
          <el-descriptions-item label="人员照片">
            <img :src="formUser.avatar" width="100" height="100" />
          </el-descriptions-item>
          <el-descriptions-item label="性别">
            {{
              formUser.gender == "0" ? "男" : formUser.gender == "1" ? "女" : ""
            }}
          </el-descriptions-item>
          <el-descriptions-item label="年龄">{{
            formUser.user_age
          }}</el-descriptions-item>
          <el-descriptions-item label="证件类型">{{
            formUser.dict_label
          }}</el-descriptions-item>
          <el-descriptions-item label="证件号码">{{
            formUser.card_no
          }}</el-descriptions-item>
          <el-descriptions-item label="所属小区">{{
            formUser.community_name
          }}</el-descriptions-item>
          <el-descriptions-item label="联系电话">{{
            formUser.user_phone
          }}</el-descriptions-item>
        </el-descriptions>
      </el-dialog>
    </div>
    <!-- form -->
    <div>
      <el-dialog
        :title="form.voluntary_id ? '修改活动' : '新增活动'"
        :visible.sync="dialogVisible"
        @close="closer"
        width="50%"
      >
        <el-form
          ref="form"
          :model="form"
          label-width="100px"
          style="margin: 30px"
          :rules="rules"
        >
          <el-row>
            <el-col>
              <el-form-item label="活动名称：" prop="act_title">
                <el-input v-model="form.act_title" clearable :maxlength="50" />
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="文章封面：" prop="cover_url">
                <el-upload
                  class="avatar-uploader"
                  action="#"
                  :show-file-list="false"
                  accept="image/*"
                  :before-upload="beforeUpload"
                  :on-change="uploadCoverUrl"
                >
                  <img
                    v-show="form.cover_url"
                    :src="form.cover_url"
                    class="avatar"
                  />
                  <i
                    v-show="!form.cover_url"
                    class="el-icon-plus avatar-uploader-icon"
                  />
                </el-upload>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="发布小区：" prop="communityIds">
                <el-select
                  v-model="form.communityIds"
                  placeholder="可多选"
                  multiple
                  collapse-tags
                >
                  <el-option
                    v-for="item in currentList"
                    :key="item.communityId"
                    :label="item.communityName"
                    :value="item.communityId"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="活动小区：" prop="community_id">
                <el-select
                  v-model="form.community_id"
                  placeholder="请选择"
                  clearable
                >
                  <el-option
                    v-for="item in currentList"
                    :key="item.communityId"
                    :label="item.communityName"
                    :value="item.communityId"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="开始时间：" prop="start_time">
                <el-date-picker
                  v-model="form.start_time"
                  type="datetime"
                  placeholder="选择日期时间"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="结束时间：" prop="end_time">
                <el-date-picker
                  v-model="form.end_time"
                  type="datetime"
                  placeholder="选择日期时间"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="招募人数：" prop="need_num">
                <el-input-number v-model="form.need_num" :min="1" :max="19" />
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="活动内容：" prop="content">
                <editor v-model="form.content" :height="500" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <span slot="footer" class="dialog-footer">
          <el-button @click="closer">关 闭</el-button>
          <el-button type="primary" @click="save" v-show="!form.voluntary_id"
            >确 定</el-button
          >
          <el-button type="primary" @click="update" v-show="form.voluntary_id"
            >修 改</el-button
          >
        </span>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import {
  searchList,
  saveVoluntary,
  removeVoluntary,
  updateVoluntary,
  upload,
  searchListUser,
} from "@/api/govern/voluntary/voluntary";
import { getCurrentList } from "@/api/govern/community";
import moment from "moment";
import { desensitization } from "../../../../../estate-ui/src/utils/common";
export default {
  name: "voluntary",
  data() {
    let rulesCheck = (rule, value, callback) => {
      if (!this.isFirstLoad) {
        if (!value || value.length === 0) {
          callback(new Error("请选择发布小区"));
        } else {
          callback();
        }
      } else {
        this.isFirstLoad = false;
      }
    };
    return {
      isFirstLoad: true, //首次加载
      uList: false,
      uView: false,
      voluntary_id: "",
      dialogVisible: false,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        time: "",
        name: "",
      },
      queryParamsUser: {
        pageNum: 1,
        pageSize: 10,
        name: "",
      },
      formUser: {},
      form: {
        need_num: 1,
        cover_url: "",
      },
      // 表单校验
      rules: {
        act_title: [
          { required: true, message: "请输入活动名称", trigger: "blur" },
          { pattern: /[^ \x22]+/, message: "不能输入空格", trigger: "blur" },
        ],
        cover_url: [
          {
            required: true,
            message: "请选择封面",
            trigger: ["blur", "change"],
          },
        ],
        communityIds: [
          {
            required: true,
            message: "请选择发布小区",
            trigger: "change",
            validator: rulesCheck,
          },
        ],
        community_id: [
          {
            required: true,
            message: "请选择活动小区",
            trigger: ["blur", "change"],
          },
        ],
        start_time: [
          {
            required: true,
            message: "请选择开始时间",
            trigger: ["blur", "change"],
          },
        ],
        end_time: [
          {
            required: true,
            message: "请选择结束时间",
            trigger: ["blur", "change"],
          },
        ],
        need_num: [
          { required: true, message: "请输入招募人数", trigger: "blur" },
        ],
      },
      // 总条数
      total: 0,
      // 遮罩层
      loading: true,
      dataList: [],
      // 总条数
      totalUser: 0,
      // 遮罩层
      loadingUser: true,
      dataListUser: [],
      currentList: [],
    };
  },
  mounted() {
    getCurrentList().then((e) => {
      this.currentList = e.data;
    });
    this.getList();
  },
  methods: {
    /** 导出按钮操作 */
    handleExport() {
      let params = {
        id: this.voluntary_id,
      };
      this.download(
        "/govern/voluntary/export",
        { ...params },
        `${this.parseTime(new Date())}志愿者信息.xlsx`
      );
    },
    openWindow(url) {
      window.open(url);
    },
    getList() {
      this.queryParams["dateTime"] = this.queryParams["time"] + "";
      searchList(this.queryParams).then((e) => {
        this.dataList = e.rows;
        this.total = e.total;
        this.loading = false;
      });
    },
    closer() {
      this.dialogVisible = false;
      (this.form = {
        need_num: 1,
        cover_url: "",
      }),
        this.$refs["form"].resetFields();
      this.getList();
    },
    save() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.compareTime()) {
            this.formatTime();
            saveVoluntary(this.form).then((e) => {
              if (e.code == 200) {
                this.$message.success(e.msg);
                this.closer();
              } else {
                this.$message.error(e.msg);
              }
            });
          } else {
            this.$message.error("活动结束时间只能高于活动开始时间!!");
          }
        }
      });
    },
    update() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.compareTime()) {
            this.formatTime();
            updateVoluntary(this.form).then((e) => {
              if (e.code == 200) {
                this.$message.success(e.msg);
                this.closer();
              } else {
                this.$message.error(e.msg);
              }
            });
          } else {
            this.$message.error("活动结束时间只能高于活动开始时间!!");
          }
        }
      });
    },
    formatTime() {
      this.form["start_time"] = moment(this.form["start_time"]).format(
        "YYYY-MM-DD hh:mm:ss"
      );
      this.form["end_time"] = moment(this.form["end_time"]).format(
        "YYYY-MM-DD hh:mm:ss"
      );
    },
    compareTime() {
      //时间对比
      let time = {
        start: this.form["start_time"],
        end: this.form["end_time"],
      };
      if (new Date(time.end).getTime() <= new Date(time.start).getTime()) {
        return false;
      }

      return true;
    },
    deleted(id) {
      this.$confirm("是否确认删除数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return removeVoluntary(id);
        })
        .then(() => {
          this.$message.success("删除成功!!");
          this.getList();
        });
    },
    // 上传预处理
    beforeUpload(file) {
      return false;
    },
    //上传文章封面
    uploadCoverUrl(file) {
      const isJPG =
        file.raw.type === "image/jpeg" || file.raw.type === "image/png";

      if (!isJPG) {
        this.$message.error("上传图片只能是 JPG/PNG 格式!");
        return isJPG;
      }
      let formData = new FormData();
      const { raw } = file;
      formData.append("file", raw);
      upload(formData).then((e) => {
        const { code } = e;
        if (code == 200) {
          const {
            data: { url },
          } = e;
          this.form.cover_url = url;
        } else {
          this.$message.error("上传失败!!");
        }
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    show(id) {
      this.uList = true;
      this.voluntary_id = id;
      this.getListUser();
    },
    /** 搜索按钮操作 */
    handleQueryUser() {
      this.queryParamsUser.pageNum = 1;
      this.getListUser();
    },
    /** 重置按钮操作 */
    resetQueryUser() {
      this.resetForm("queryFormUser");
      this.handleQueryUser();
    },
    //用户
    getListUser() {
      this.dataListUser = [];
      this.queryParamsUser["voluntary_id"] = this.voluntary_id;
      searchListUser(this.queryParamsUser).then((e) => {
        this.dataListUser = e.rows;
        this.totalUser = e.total;
        this.loadingUser = false;
      });
    },
  },
};
</script>

<style scoped>
.avatar-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  width: 178px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

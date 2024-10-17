<!--
 * @Author: 自由命名
 * @Date: 2024-03-01 18:07:44
 * @LastEditors: 自由命名
 * @LastEditTime: 2024-04-22 16:20:34
 * @FilePath: \govern-ui\src\views\govern\memberact\memberAct.vue
 * @filePurpose:
-->

<template>
  <div class="app-container">
    <div v-show="openView === 0">
      <el-form
        v-show="showSearch"
        ref="queryForm"
        :model="queryParams"
        :inline="true"
        label-width="90px"
      >
        <el-form-item label="活动主题" prop="actTheme">
          <el-input
            v-model.trim="queryParams.actTheme"
            v-emoji
            placeholder="请输入活动主题"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="活动发起人" prop="createBy">
          <el-input
            v-model.trim="queryParams.createBy"
            v-emoji
            placeholder="请输入活动发起人"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="创建时间">
          <el-date-picker
            v-model="dateRange"
            size="small"
            style="width: 220px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          />
        </el-form-item>
        <!--        <el-form-item label="活动时间" prop="startTime">
                  <el-date-picker
                    style="width: 100%"
                    v-model="queryParams.startTime"
                    type="date"
                    placeholder="请选择活动时间">
                  </el-date-picker>
                </el-form-item>-->
        <el-form-item label="活动状态" prop="status" label-width="70px">
          <el-select
            v-model="queryParams.status"
            placeholder="请选择类型"
            style="width: 80%"
          >
            <el-option
              v-for="item in statusOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value"
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
            v-hasPermi="['govern:memberAct:add']"
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            >新增
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            v-hasPermi="['govern:memberAct:remove']"
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            >删除
          </el-button>
        </el-col>
        <right-toolbar :show-search.sync="showSearch" @queryTable="getList" />
      </el-row>
      <el-table
        v-loading="loading"
        :data="activityList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column
          label="活动主题"
          align="center"
          prop="actTheme"
          show-overflow-tooltip
        />
        <el-table-column
          label="活动地点"
          align="center"
          prop="address"
          show-overflow-tooltip
        />
        <el-table-column label="活动发起人" align="center" prop="createBy" />
        <el-table-column label="活动开始时间" align="center" prop="startTime">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.startTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="活动结束时间" align="center" prop="endTime">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.endTime) }}</span>
          </template>
        </el-table-column>
        <!-- <el-table-column
          label="活动状态"
          align="center"
          prop="status"
          :formatter="statusFormat"
        /> -->
        <el-table-column
          label="活动状态"
          align="center"
          prop="planStatus"
          :formatter="planStatusFormat"
        />
        <el-table-column
          label="已报名人数"
          align="center"
          prop="toSignUpNumber"
        />
        <el-table-column label="发布对象" align="center" prop="communityName" />
        <el-table-column
          label="创建时间"
          align="center"
          prop="createTime"
          width="180"
        >
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
              v-hasPermi="['govern:memberAct:query']"
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleActivity(scope.row)"
              >查看
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
    </div>

    <!--查看详情  -->
    <div v-show="openView === 1">
      <el-tabs v-model="activeName">
        <el-tab-pane label="活动信息" name="first">
          <el-button size="middle" style="float: right" @click="before"
            >关 闭</el-button
          >
          <h4 class="form-header" style="color: #0558e7">活动基本信息</h4>
          <el-form ref="form" :model="form" label-width="120px">
            <!-- <h4 class="form-header">党员活动详情</h4> -->
            <el-row>
              <el-col :span="20" :offset="3">
                <el-form-item label="活动主题：" prop="theme">
                  {{ form.actTheme }}
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="20" :offset="3">
                <el-form-item label="活动详情：" prop="content">
                  {{ form.content }}
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6" :offset="3">
                <el-form-item label="活动地点：" prop="address">
                  {{ form.address }}
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="3">
                <el-form-item label="已报名人数：" prop="applyNumber">
                  {{ form.toSignUpNumber }}
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="9" :offset="3">
                <el-form-item label="活动开始时间：" prop="startTime">{{
                  form.startTime
                }}</el-form-item>
              </el-col>
              <el-col :span="9" :offset="0">
                <el-form-item label="活动结束时间：" prop="startTime">{{
                  form.endTime
                }}</el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6" :offset="3">
                <el-form-item label="活动截止时间：" prop="closeTime">{{
                  form.closeTime
                }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="3">
                <el-form-item label="活动状态：">
                  {{
                    form.planStatus == "0"
                      ? "活动待开启"
                      : form.planStatus == "1"
                      ? "活动进行中"
                      : 活动已结束
                  }}</el-form-item
                >
              </el-col>
              <!-- <el-col :span="6" :offset="3">
                <el-form-item label="活动进度：">{{
                  form.planStatus == "0" ? "活动待开启" : "活动报名中"
                }}</el-form-item>
              </el-col> -->
            </el-row>
            <el-row>
              <el-col :span="6" :offset="3">
                <el-form-item label="活动图片:">
                  <el-image
                    style="width: 450px; height: 210px"
                    :src="imageUrl"
                    :preview-src-list="imageList"
                  >
                  </el-image>
                  <!-- <img :src="imageUrl" :style="`width:450px;height:210px;`" /> -->
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="参与人员信息" name="second">
          <el-button size="middle" style="float: right" @click="before"
            >关 闭</el-button
          >
          <h4 class="form-header" style="color: #0558e7">活动基本信息</h4>
          <div style="display: flex">
            <el-form ref="form" :model="form" label-width="180px">
              <el-col :span="24">
                <el-row>
                  <el-col :span="4" :offset="1">
                    <el-form-item label="已报名人数：">
                      <div style="width: 280px">{{ form.toSignUpNumber }}</div>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-col>
            </el-form>
          </div>
          <h4 class="form-header" style="color: #0558e7">参与人员明细</h4>
          <div>
            <el-form
              v-show="showSearch"
              ref="queryForm1"
              :model="queryParams2"
              size="small"
              :inline="true"
              label-width="100px"
            >
              <el-form-item label="姓名" prop="joinName" label-width="95px">
                <el-input
                  v-model="queryParams2.joinName"
                  placeholder="请输入姓名"
                  style="width: 215px"
                  clearable
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item label="手机号" prop="ownerPhone" label-width="95px">
                <el-input
                  v-model="queryParams2.joinPhone"
                  placeholder="请输入手机号"
                  style="width: 215px"
                  clearable
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item>
                <el-button
                  type="primary"
                  icon="el-icon-search"
                  size="mini"
                  @click="handleQuery2"
                  >搜索</el-button
                >
                <el-button
                  icon="el-icon-refresh"
                  size="mini"
                  float="right"
                  @click="resetQuery2"
                  >重置</el-button
                >
              </el-form-item>
            </el-form>
            <el-table v-loading="loading" :data="joinPeopleList">
              <el-table-column
                label="姓名"
                align="center"
                prop="joinName"
                width="280px"
                show-overflow-tooltip
              />
              <el-table-column
                label="手机号"
                align="center"
                prop="joinPhone"
                width="280px"
              />
              <el-table-column
                label="报名时间"
                align="center"
                prop="createTime"
                width="280px"
              />
              <el-table-column label="备注" align="center" prop="remark" />
            </el-table>
            <pagination
              v-show="totals > 0"
              :total="totals"
              :page.sync="queryParams2.pageNum"
              :limit.sync="queryParams2.pageSize"
              @pagination="getJoin"
            />
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <!-- 添加或修改党员活动对话框 -->
    <el-dialog :title="title" append-to-body :visible.sync="dialogTableVisible">
      <h4 class="form-header">{{ title }}</h4>
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="活动主题" prop="actTheme">
              <el-input
                v-model="form.actTheme"
                v-emoji
                on-keypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
                placeholder="请输入活动主题"
                :maxlength="20"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="活动地点" prop="address">
              <el-input
                v-model="form.address"
                v-emoji
                on-keypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
                placeholder="请输入活动地点"
                :maxlength="20"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <!-- <el-row>
          <el-col :span="12">
            <el-form-item label="活动状态" prop="status">
              <el-select
                v-model="form.status"
                placeholder="请选择活动状态"
                style="width: 100%"
              >
                <el-option
                  v-for="item in statusOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="活动进度" prop="planStatus">
              <el-select
                v-model="form.planStatus"
                placeholder="请选择活动进度"
                style="width: 100%"
              >
                <el-option
                  v-for="item in planStatusOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row> -->
        <el-row>
          <el-col :span="12">
            <el-form-item label="活动时间:" prop="startEndTime">
              <el-date-picker
                v-model="startEndTime"
                style="width: 100%"
                :picker-options="pickerOptions"
                value-format="yyyy-MM-dd HH:mm:ss"
                type="datetimerange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              />
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="报名截止时间:" prop="closeTime">
              <el-date-picker
                v-model="closeTime"
                value-format="yyyy-MM-dd HH:mm:ss"
                type="datetime"
                style="width: 100%"
                placeholder="选择日期时间"
                :picker-options="pickerOptions"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="发布对象" prop="communityId">
              <el-select
                v-model="form.communityId"
                placeholder="请选择发布对象"
                style="width: 100%"
              >
                <el-option
                  v-for="item in communityList"
                  :key="item.communityId"
                  :label="item.communityName"
                  :value="item.communityId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-form-item label="活动详情" prop="content">
            <el-input
              v-model="form.content"
              v-emoji
              type="textarea"
              rows="5"
              on-keypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
              placeholder="请输入活动详情（200字以内）"
              :maxlength="200"
            />
          </el-form-item>
        </el-row>
        <el-row>
          <el-form-item
            label="活动图片:"
            prop="pictureUrl"
            style="width: 200px; height: 200px"
          >
            <el-upload
              class="avatar-uploader party-img"
              style="width: 460px; height: 200px; overflow: hidden"
              action="#"
              :show-file-list="false"
              accept="image/*"
              :before-upload="beforeUpload"
              :on-change="uploadLogo"
            >
              <img v-if="imageUrl" :src="imageUrl" class="avatar" />
              <i v-else class="el-icon-plus avatarUploaderIcon" />
            </el-upload>
            <!--              <imageUpload v-model="form.logoUrl" />-->
          </el-form-item>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { equals, isEmpty } from "@/utils/common";
import {
  addMemberAct,
  delMemberAct,
  memberActList,
  joinPeopleListManage,
} from "@/api/govern/memberact/memberAct";
import { getCurrentList } from "@/api/govern/community";
import { uploadImg } from "@/api/govern/party/party";

export default {
  name: "MemberAct",
  data() {
    const validatorr = (rule, value, callback) => {
      if (isEmpty(this.startEndTime)) {
        callback(new Error("发布时间不能为空"));
      } else {
        callback();
      }
    };
    const validatorDeadline = (rule, value, callback) => {
      if (isEmpty(this.closeTime)) {
        callback(new Error("发布时间不能为空"));
      } else {
        callback();
      }
    };

    const validatorImage = (rule, value, callback) => {
      if (isEmpty(this.imageUrl)) {
        callback(new Error("活动图片不能为空"));
      } else {
        callback();
      }
    };

    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        },
      },
      startEndTime: [],
      closeTime: "",
      dialogTableVisible: false,
      dialogFormVisible: false,
      // 遮罩层
      loading: true,
      imageUrl: "",
      imageList: [],
      joinPeopleList: [],
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
      totals: 0,
      // 日期范围
      dateRange: [],
      // 党员活动表格数据
      activityList: [],
      // 党组织列表信息
      nameList: [],
      // 项目列表
      communityList: [],
      // 弹出层标题
      title: "",
      titles: "",
      // 是否显示弹出层
      // open: false,
      openView: 0,
      // 党组织信息列表
      partyOrganizationList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        queryParams: undefined,
        status: undefined,
        createBy: undefined,
      },
      queryParams2: {
        pageNum: 1,
        pageSize: 1,
        joinPhone: undefined,
        joinName: undefined,
      },
      activeName: "first",
      // 表单参数
      form: {
        status: "0",
        planStatus: "1",
      },
      // 表单校验
      rules: {
        actTheme: [
          { required: true, message: "活动主题不能为空", trigger: "blur" },
        ],
        address: [
          { required: true, message: "活动地点不能为空", trigger: "blur" },
          {
            pattern:
              /^[\u4e00-\u9fa5_a-zA-Z0-9_(_（_)_）_,_，_:_：_—_\-_/_\n]+$/,
            message: "请正确输入文本框",
            trigger: "blur",
          },
        ],
        startEndTime: [
          { required: true, trigger: "blur", validator: validatorr },
        ],
        closeTime: [
          { required: true, trigger: "blur", validator: validatorDeadline },
        ],
        communityId: [
          { required: true, message: "发布对象不能为空", trigger: "blur" },
        ],
        content: [
          { required: true, message: "活动详情不能为空", trigger: "blur" },
        ],
        pictureUrl: [
          { required: true, trigger: "blur", validator: validatorImage },
        ],
        status: [
          { required: true, message: "请选择活动状态", trigger: "blur" },
        ],
        planStatus: [
          { required: true, message: "请选择活动进度", trigger: "blur" },
        ],
      },
      statusOptions: [
        { label: "开启", value: "0" },
        { label: "结束", value: "1" },
      ],
      planStatusOptions: [
        { label: "活动待开启", value: "0" },
        { label: "活动报名中", value: "1" },
        { label: "活动结束", value: "2" },
      ],
      communityId: [
        { required: true, message: "小区不能为空", trigger: "blur" },
      ],
    };
  },
  created() {
    this.getList();
  },
  methods: {
    statusFormat(row) {
      if (equals(row.status, "0")) {
        return "开启";
      } else if (equals(row.status, "1")) {
        return "结束";
      }
    },
    planStatusFormat(row) {
      if (equals(row.planStatus, "0")) {
        return "活动待开启";
      } else if (equals(row.planStatus, "1")) {
        return "活动报名中";
      } else if (equals(row.planStatus, "2")) {
        return "活动已结束";
      }
    },
    // 参与人员列表
    getJoin() {
      joinPeopleListManage(this.queryParams2).then((response) => {
        this.joinPeopleList = response.rows;
        console.log(response, "response");
        this.totals = response.total;
      });
    },
    // 参与人员列表搜索
    handleQuery2() {
      this.queryParams2.pageNum = 1;
      this.getJoin();
    },
    // 重置按钮2
    resetQuery2() {
      this.resetForm("queryForm1");
      this.queryParams2.pageNum = 1;
      this.queryParams2.queryParams = undefined;
      this.queryParams2.joinPhone = undefined;
      this.queryParams2.joinName = undefined;
      // eslint-disable-next-line semi
      this.handleQuery2();
    },

    /** 查询党员活动列表 */
    getList() {
      this.loading = true;
      memberActList(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.activityList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 所属小区 */
    getCurrentList() {
      getCurrentList().then((response) => {
        this.communityList = response.data;
        this.getList();
      });
    },
    // 取消按钮
    cancel() {
      this.dialogFormVisible = false;
      this.dialogTableVisible = false;
      this.reset();
    },
    // 关闭查看详情
    closeDetail() {
      this.dialogFormVisible = false;
      this.getList();
    },
    // 返回
    before() {
      // this.reset();
      this.openView = 0;
      this.getList();
    },
    // 表单重置
    reset() {
      this.form = {
        status: "0",
        planStatus: "0",
      };
      this.form = {};
      this.startEndTime = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = null;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 覆盖默认的上传行为
    requestUpload() {},
    // 上传预处理
    beforeUpload(file) {
      return false;
    },
    // 上传图片
    uploadLogo(file, fileList) {
      const formData = new FormData();
      formData.append("file", file.raw);
      uploadImg(formData).then((response) => {
        this.form.pictureUrl = response.imgUrl;
        this.imageUrl = response.imgUrl;
        this.$modal.msgSuccess("上传成功");
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.activityId);
      this.titles = selection.map((item) => item.theme);
      // eslint-disable-next-line eqeqeq
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.dialogTableVisible = true;
      this.reset();
      // 查出所有可用小区拱选择
      // eslint-disable-next-line no-unused-vars
      const param = {
        communityType: "1",
      };
      this.title = "添加党员活动";
      this.imageUrl = "";
      this.getCurrentList();
    },
    /** 查看详情*/
    handleActivity(row) {
      this.openView = 1;
      this.activeName = "first";
      this.queryParams.activityId = row.activityId;
      this.queryParams2.activityId = row.activityId;
      // this.dialogFormVisible = true;
      this.form = row;
      this.imageUrl = this.form.pictureUrl;
      this.imageList = [];
      this.imageList.push(this.imageUrl);
      console.log(this.imageList, "imageList");
      this.getJoin();
    },
    /** 提交按钮 */
    submitForm: function () {
      console.log(this.$store.state.user.name, "this.$store.state.user.name");
      this.form.startTime = this.startEndTime[0];
      this.form.endTime = this.startEndTime[1];
      this.form.closeTime = this.closeTime;
      this.form.status = "0";
      this.form.planStatus = "0";
      this.form.createBy = this.$store.state.user.name;
      console.log(this.form);
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.closeTime >= this.form.startTime) {
            this.$modal.msgError("报名截止时间在开始时间之前");
            return;
          }
          addMemberAct(this.form).then((response) => {
            this.$modal.msgSuccess("新增成功");
            this.dialogTableVisible = false;
            this.getList();
          });
          console.log(this.form);
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const activityIds = row.activityId || this.ids;
      this.$confirm("是否确认删除所选的数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delMemberAct(activityIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        });
    },
  },
};
</script>

<style>
.party-img .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  height: 200px;
}

.party-img .el-upload:hover {
  border-color: #409eff;
}

.avatarUploaderIcon {
  font-size: 28px;
  color: #8c939d;
  width: 450px;
  height: 200px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 200px;
  height: 200px;
  display: block;
  object-fit: cover;
}
</style>

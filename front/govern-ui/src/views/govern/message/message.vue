<template>
  <div class="app-container">
    <div v-show="openView === 0">
      <el-form
        v-show="showSearch"
        ref="queryForm"
        :model="queryParams"
        :inline="true"
        label-width="68px"
      >
        <el-form-item label="标题" prop="msgTitle">
          <el-input
            v-model.trim="queryParams.msgTitle"
            v-emoji
            placeholder="请输入标题"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="创建日期">
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
            v-hasPermi="['govern:message:add']"
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
            v-hasPermi="['govern:message:remove']"
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            >删除</el-button
          >
        </el-col>
        <right-toolbar :show-search.sync="showSearch" @queryTable="getList" />
      </el-row>

      <el-table
        v-loading="loading"
        :data="advisoryList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column
          label="标题"
          align="center"
          prop="msgTitle"
          show-overflow-tooltip
        />
        <el-table-column
          label="创建日期"
          align="center"
          prop="createTime"
          width="180"
        >
          <template slot-scope="scope">
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column label="创建人" align="center" prop="createBy" />
        <el-table-column label="阅读数" align="center" prop="lookNumber" />
        <el-table-column label="所属小区" align="center" prop="communityName" />
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <!-- v-if="scope.row.top == 0" -->
            <el-button
              v-if="scope.row.top == 0"
              size="mini"
              type="text"
              icon="el-icon-top"
              @click="bulletinSwitch(scope.row)"
              >置顶
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleAdvisory(scope.row)"
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
    <el-dialog title="党建资讯详情" :visible.sync="dialogFormVisible">
      <el-form ref="form" :model="form" label-width="120px">
        <h4 class="form-header">党建资讯详情</h4>
        <el-row>
          <el-col :span="12">
            <el-form-item label="标题：" prop="msgTitle">
              {{ form.msgTitle }}
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="创建日期：" prop="createTime"
              >{{ form.createTime }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="发布组织：" prop="partyName">
              {{ form.partyName }}
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="阅读数：" prop="lookNumber">
              {{ form.lookNumber }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="创建人：" prop="createBy">
              {{ form.createBy }}
            </el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="所属小区：" prop="createBy">
              {{ form.communityName }}
            </el-form-item>
          </el-col>
        </el-row>
        .<el-row>
          <el-col :span="12">
            <el-form-item label="上传图片:">
              <img :src="imageUrl" :style="`width:525px;height:auto;`" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="内容" prop="content">
              <editor
                v-model="form.content"
                :min-height="200"
                read-only="read-only"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <h4 class="form-header" />
      <el-row>
        <el-col :offset="22" :span="2">
          <el-button @click="closeDetail">关 闭</el-button>
        </el-col>
      </el-row>
    </el-dialog>
    <!-- 添加或修改党建资讯对话框 -->
    <el-dialog title="添加" :visible.sync="dialogTableVisible">
      <h4 class="form-header">基本信息</h4>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8" :offset="0">
            <el-form-item label="标题" prop="msgTitle">
              <el-input
                v-model="form.msgTitle"
                v-emoji
                on-keypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
                placeholder="请输入标题"
                style="width: 240px"
                :maxlength="50"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8" :offset="0">
            <el-form-item label="所属小区" prop="communityId">
              <el-select
                v-model="form.communityId"
                placeholder="请选择所属小区"
                style="width: 240px"
                @change="getPartyList"
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
          <el-col :span="6" :offset="0">
            <el-form-item label="发布组织" prop="partyIds">
              <el-select
                v-model="form.partyIds"
                multiple
                placeholder="请选择发布组织"
                style="width: 240px"
              >
                <el-option
                  v-for="item in organizationList"
                  :key="item.partyId"
                  :label="item.partyName"
                  :value="item.partyId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="30">
            <el-form-item label="内容" prop="content">
              <editor v-model="content" :min-height="200" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item
            label="上传图片:"
            prop="pictureUrl"
            style="width: 200px; height: 200px"
          >
            <el-upload
              class="avatar-uploader party-img"
              style="width: 460px; height: 250px; overflow: hidden"
              action="#"
              :show-file-list="false"
              accept="image/*"
              :before-upload="beforeUpload"
              :on-change="uploadLogo"
            >
              <img
                v-if="imageUrl"
                :src="imageUrl"
                class="avatar"
                style="width: 100%; height: 100%"
              />
              <i v-else class="el-icon-plus avatarUploaderIcon" />
            </el-upload>
            <!--              <imageUpload v-model="form.logoUrl" />-->
          </el-form-item>
        </el-row>
      </el-form>

      <div slot="footer" class="dialog-footer">
        <el-button
          type="primary"
          onkeydown="javascript:if(event.keyCode == 13)event.returnValue = false;"
          @click="submitForm"
          >确 定</el-button
        >
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import Editor from "@/components/Editor";
import { Base64 } from "js-base64";
import {
  messageList,
  addMessage,
  delMessage,
  topMessage,
} from "@/api/govern/message/message";
import { getCurrentList } from "@/api/govern/community";
import { equals, isEmpty } from "@/utils/common";
import { partyList } from "@/api/govern/partypay/partyMoney";
import { uploadImg } from "@/api/govern/party/party";

export default {
  name: "PartyAdvisory",
  components: {
    Editor,
  },
  data() {
    const validatorImage = (rule, value, callback) => {
      if (isEmpty(this.imageUrl)) {
        callback(new Error("上传图片不能为空"));
      } else {
        callback();
      }
    };
    return {
      imageUrl: "",
      communityList: [],
      dialogTableVisible: false,
      dialogFormVisible: false,
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
      // 日期范围
      dateRange: [],
      // 党建资讯表格数据
      advisoryList: [],
      // 党组织列表
      organizationList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      openView: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        msgTitle: undefined,
        skipUrl: undefined,
        organizationId: undefined,
        lookNumber: undefined,
        sortNumber: undefined,
        keyWordId: undefined,
        delFlag: undefined,
        organizationName: undefined,
      },
      // 表单参数
      form: {},
      content: "",
      // 表单校验
      rules: {
        msgTitle: [
          { required: true, message: "标题不能为空", trigger: "blur" },
        ],
        communityId: [
          { required: true, message: "请选择小区", trigger: "change" },
        ],
        partyIds: [{ required: true, message: "请选择组织", trigger: "blur" }],
        content: [{ required: true, message: "请输入内容", trigger: "blur" }],
        pictureUrl: [
          { required: true, trigger: "blur", validator: validatorImage },
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    // 上传预处理
    beforeUpload(file) {
      return false;
    },
    /** 上传图片 */
    uploadLogo(file, fileList) {
      const formData = new FormData();
      formData.append("file", file.raw);
      uploadImg(formData).then((response) => {
        this.form.skipUrl = response.imgUrl;
        this.imageUrl = response.imgUrl;
        this.$modal.msgSuccess("上传成功");
      });
    },
    /** 查询党建资讯列表 */
    getList() {
      this.loading = true;
      messageList(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.advisoryList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 岗位状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 置顶
    async bulletinSwitch(row) {
      const messageId = row.messageId;
      this.$confirm("是否确认置顶此数据?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return topMessage({ messageId: messageId });
        })
        .then(() => {
          this.queryParams.pageNum = 1;
          this.getList();
          this.$modal.msgSuccess("置顶成功");
        });
    },
    /** 所属小区 */
    getCurrentList() {
      getCurrentList().then((response) => {
        this.communityList = response.data;
        if (this.communityList.length > 0) {
          this.queryParams.communityId = this.communityList[0].communityId;
        }
        this.getList();
      });
    },
    // 取消按钮
    cancel() {
      // this.open = false;
      this.dialogTableVisible = false;
      this.reset();
    },
    // 关闭查看详情
    closeDetail() {
      this.dialogFormVisible = false;
      this.getList();
    },
    // 表单重置
    reset() {
      this.form = {};
      this.content = "";
      this.imageUrl = "";
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.messageId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 根据小区查党组织 */
    getPartyList(value) {
      if (!equals(this.form.partyIds, null)) {
        this.form.partyIds = null;
      }
      partyList(value).then((response) => {
        this.organizationList = response.data;
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.dialogTableVisible = true;
      this.reset();
      this.title = "添加党建资讯";
      this.getCurrentList();
    },
    /** 查看详情*/
    handleAdvisory(row) {
      this.dialogFormVisible = true;
      this.form = row;
      // this.content = Base64.decode(this.form.content);
      this.imageUrl = row.skipUrl;
      console.log(row, "row", this.imageUrl);
    },
    /** 提交按钮 */
    submitForm: function () {
      console.log(this.content);
      this.form.content = Base64.encode(this.content);
      console.log(this.form.content);
      this.$refs["form"].validate((valid) => {
        if (valid) {
          addMessage(this.form).then((response) => {
            this.$modal.msgSuccess("新增成功");
            this.cancel();
            this.getList();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const advisoryIds = row.messageId || this.ids;
      this.$confirm("是否确认删除所选党建资讯的数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delMessage(advisoryIds);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
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
  width: 100%;
  height: 100%;
}
.avatar-uploader {
  font-size: 28px;
  color: #8c939d;
  width: 450px;
  height: 200px;
  line-height: 178px;
  text-align: center;
}
</style>

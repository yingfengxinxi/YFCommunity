<template>
  <div class="app-container">
    <div v-show="openView === 0">
      <el-form
        :model="queryParams"
        ref="queryForm"
        :inline="true"
        v-show="showSearch"
        label-width="68px"
        size="small"
      >
        <el-form-item label="姓名" prop="name" size="small">
          <el-input
            v-emoji
            v-model="queryParams.name"
            placeholder="请输入姓名"
            clearable
            type="text"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="手机号码" prop="phone" size="small">
          <el-input
            v-emoji
            v-model="queryParams.phone"
            placeholder="请输入手机号码"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="车牌号" prop="plateNo" size="small">
          <el-input
            v-emoji
            v-model="queryParams.plateNo"
            placeholder="请输入车牌号"
            clearable
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="审核状态" prop="auditStatus">
          <el-select
            v-model="queryParams.auditStatus"
            placeholder="请选择审核状态"
            clearable
          >
            <el-option
              v-for="(item, index) in auditStatusOptions"
              :key="index"
              :label="item.dictLabel"
              :value="item.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="getList"
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
            @click="addMethod"
            >新增</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="info"
            plain
            icon="el-icon-upload2"
            size="mini"
            @click="handleImport"
            >导入</el-button
          >
        </el-col>

        <right-toolbar
          :showSearch.sync="showSearch"
          @queryTable="getList"
        ></right-toolbar>
      </el-row>
      <el-table
        ref="multipleTable"
        v-loading="loading"
        :data="staffList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="姓名" align="center" prop="name" />
        <el-table-column label="账号/手机号" align="center" prop="phone" />
        <el-table-column label="所属小区" align="center" prop="communityName" />
        <el-table-column label="车牌号" align="center" prop="plateNo" />
        <el-table-column label="车辆品牌" align="center" prop="vehicleBrand" />
        <el-table-column label="车辆颜色" align="center" prop="vehicleColor">
          <template slot-scope="scope">
            <dict-tag
              :options="dict.type.sys_vehicle_color"
              :value="scope.row.vehicleColor"
            />
          </template>
        </el-table-column>
        <el-table-column label="车辆类型" align="center" prop="vehicleType">
          <template slot-scope="scope">
            <dict-tag
              :options="dict.type.vehicle_type"
              :value="scope.row.vehicleType"
            />
          </template>
        </el-table-column>
        <el-table-column label="车位编号" align="center" prop="vehicleNo" />
        <el-table-column label="车辆状态" align="center" prop="vehicleStatus">
          <template slot-scope="scope">
            {{ scope.row.vehicleStatus == "0" ? "正常" : "闲置" }}
          </template>
        </el-table-column>
        <el-table-column label="审核状态" align="center" prop="auditStatus">
          <template slot-scope="scope">
            {{ auditStatusFormat(scope.row.auditStatus) }}
          </template>
        </el-table-column>
        <el-table-column label="审核时间" align="center" prop="auditTime" />
        <el-table-column label="审核人" align="center" prop="auditBy" />
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
          fixed="right"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="headUpdate(scope.row)"
              >修改</el-button
            >
            <!-- <br> -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              style="color: #f56c6c"
              @click="handleDelete(scope.row)"
              >删除</el-button
            >
            <!-- <br> -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="getRepulse(scope.row)"
              v-if="scope.row.auditStatus == '2'"
              >查看驳回原因</el-button
            >
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdateStatusPass(scope.row)"
              v-if="scope.row.auditStatus == '0'"
              >审核</el-button
            >
            <!-- <br v-if="scope.row.auditStatus=='0'"> -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              style="color: #f56c6c"
              @click="repulse(scope.row)"
              v-if="scope.row.auditStatus == '0'"
              >驳回
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

      <el-dialog
        :title="title"
        :visible.sync="open"
        width="500px"
        append-to-body
      >
        <el-form
          ref="rejectForm"
          :model="rejectForm"
          :rules="rules"
          label-width="80px"
        >
          <el-form-item label="驳回原因：" label-width="90" prop="rejectReason">
            <el-input
              :disabled="isDisabled"
              v-model="rejectForm.rejectReason"
              type="textarea"
              placeholder="请输入内容"
            />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button
            v-if="!isDisabled"
            type="primary"
            @click="submitForm('rejectForm')"
            >确 定</el-button
          >
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>
    <el-dialog
      :visible.sync="addOpen"
      @close="addClose"
      :title="title"
      width="80%"
      append-to-body
    >
      <!-- 新增页面 -->
      <!-- <div v-show="addOpen"> -->
      <el-form
        ref="addForm"
        :rules="addRules"
        :model="addForm"
        label-width="180px"
      >
        <h5><b>所属业户信息</b></h5>
        <el-divider></el-divider>

        <!-- 第一行 -->
        <el-row>
          <el-col :offset="0" :span="7">
            <el-form-item label="所属小区: " prop="communityId">
              <div v-if="isDisabled">
                {{ addForm.communityName }}
              </div>
              <el-select
                v-if="!isDisabled"
                v-model="addForm.communityId"
                placeholder="请选择所属小区"
                clearable
                style="width: 200px"
                @change="getStaffUserId(addForm.communityId)"
              >
                <el-option
                  v-for="community in allCommunityList"
                  :key="community.communityId"
                  :label="community.communityName"
                  :value="community.communityId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :offset="0" :span="7">
            <el-form-item label="物业人员: " prop="userId">
              <div v-if="isDisabled">
                {{ addForm.userName }}
              </div>
              <el-select
                v-if="!isDisabled"
                v-model="addForm.userId"
                clearable
                placeholder="请选择物业人员"
                @change="staffChange(addForm.userId)"
                style="width: 200px"
              >
                <el-option
                  v-for="item in staffOptions"
                  :key="item.userId"
                  :label="item.staffName"
                  :value="item.userId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :offset="0" :span="7">
            <el-form-item label="联系方式: ">
              {{ staffPhones }}
            </el-form-item>
          </el-col>
        </el-row>
        <h5><b>车辆信息</b></h5>
        <el-divider></el-divider>
        <!-- 车辆信息第一行 -->
        <el-row>
          <el-col :offset="0" :span="7">
            <el-form-item label="车位编号: " prop="vehicleNo">
              <el-select
                filterable
                v-model="addForm.vehicleNo"
                placeholder="请选择车位编号"
                clearable
                style="width: 216px"
              >
                <el-option
                  v-for="d in carportNoList"
                  :key="d.carportNoId"
                  :label="d.carportNo"
                  :value="d.carportNoId"
                  :disabled="d.isCarport >= 1"
                />
              </el-select>
            </el-form-item>
          </el-col>

          <el-col :offset="0" :span="7">
            <el-form-item label="车牌号: " prop="plateNo">
              <el-input
                clearable
                v-model="addForm.plateNo"
                placeholder="请输入车牌号"
                style="width: 200px"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :offset="0" :span="7">
            <el-form-item label="车辆颜色: " prop="vehicleColor">
              <el-select
                v-model="addForm.vehicleColor"
                clearable
                placeholder="请选择车辆颜色"
                style="width: 200px"
              >
                <el-option
                  v-for="item in carColour"
                  :key="item.dictValue"
                  :label="item.dictLabel"
                  :value="item.dictValue"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 车辆信息第二行 -->
        <el-row>
          <el-col :offset="0" :span="7">
            <el-form-item label="车辆类型: " prop="vehicleType">
              <el-select
                v-model="addForm.vehicleType"
                clearable
                placeholder="请选择车辆类型"
                style="width: 200px"
              >
                <el-option
                  v-for="item in vehicleTypeOptions"
                  :key="item.dictValue"
                  :label="item.dictLabel"
                  :value="item.dictValue"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :offset="0" :span="7">
            <el-form-item label="车辆品牌: " prop="vehicleBrand">
              <el-input
                clearable
                v-model="addForm.vehicleBrand"
                placeholder="请输入车辆品牌"
                style="width: 200px"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :offset="0" :span="20">
            <el-form-item label="上传行驶证: " prop="drivingLicense">
              <image-upload v-model="addForm.drivingLicense" :limit="3" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :offset="0" :span="20">
            <el-form-item label="身份证图片: " prop="idCard">
              <image-upload v-model="addForm.idCard" :limit="3" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :offset="0" :span="20">
            <el-form-item label="上传其他: " prop="other">
              <image-upload v-model="addForm.other" :limit="3" />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <el-row>
        <el-col :offset="20" :span="2">
          <div style="width: 280px">
            <el-button type="primary" @click="addSuccess(addForm)"
              >确定</el-button
            >
            <el-button @click="addClose">取消</el-button>
          </div>
        </el-col>
      </el-row>
    </el-dialog>

    <!-- 房屋导入对话框 -->
    <el-dialog
      :title="uploadImprot.title"
      :visible.sync="uploadImprot.open"
      width="400px"
      append-to-body
    >
      <el-upload
        ref="uploadImprot"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="uploadImprot.headers"
        :action="uploadImprot.url"
        :disabled="uploadImprot.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <!-- <div class="el-upload__tip" slot="tip">
                <el-checkbox v-model="upload.updateSupport" />
                是否更新已经存在的用户数据
              </div> -->
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link
            type="primary"
            :underline="false"
            style="font-size: 12px; vertical-align: baseline"
            @click="importTemplate"
            >下载模板</el-link
          >
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="uploadImprot.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {
  listStaff,
  updateStatusPass,
  updateStatusReject,
  deleteById,
  get,
  update,
  add,
} from "@/api/estate/manage/staffProperty/staffProperty";
import { getAllCommunityList } from "@/api/estate/community";
import { getCommunityIdStaffList } from "@/api/estate/manage/staff/staff";
import { getCommunityCarportList } from "@/api/estate/center/buildingManage/carPositionManage/carPositionManage";
import { getToken } from "@/utils/auth";
export default {
  name: "Staff",
  dicts: ["sys_vehicle_color", "vehicle_type"],
  data() {
    return {
      expireTimeOPtion: {
        disabledDate(time) {
          return time.getTime() > Date.now() - 8.64e6; //如果没有后面的-8.64e6就是不可以选择今天的
        },
      },
      // 新增表单参数
      addForm: {
        vehicleColor: "",
        plateType: "",
        vehicleType: "",
      },
      staffPhones: "",
      staffType: "",
      isDisabled: false,
      // communityName: "",
      // communityId: "",
      sommempList: true,
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      open: false,
      plateTypeList: [],
      isDisabled: false,
      staffOptions: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      auditStatusOptions: [
        { dictLabel: "待审核", dictValue: "0" },
        { dictLabel: "已通过", dictValue: "1" },
        { dictLabel: "驳回", dictValue: "2" },
      ],
      // 总条数
      total: 0,
      // 员工信息表格数据
      staffList: [],
      classifyList: [],
      //小区数据集合
      currentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      //open: false,
      openView: false,
      // 性别：0=男，1=女字典
      genderOptions: [],
      //车辆品牌
      vehicleColorOptions: [],
      //车辆类型
      vehicleTypeOptions: [],
      allCommunityList: [],
      plateColorList: [],
      addOpen: false,
      //判断是否显示
      isShow: false,
      showRelation: false,
      // 树筛选文本
      filterText: "",
      //树结构
      comOptions: [],
      communityId: null,
      currentListProps: {
        children: "communityId",
        label: "communityName",
      },
      carportNoList: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        phone: null,
      },
      title: "",
      // 表单参数
      rejectForm: {
        id: "",
        rejectReason: "",
      },
      carColour: [],
      data: [],
      // 房屋信息导入参数
      uploadImprot: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/estate/staffProperty/importData",
      },
      // 表单校验
      rules: {
        rejectReason: [
          { required: true, message: "驳回原因不能为空！", trigger: "blur" },
        ],
      },
      // 表单校验
      addRules: {
        communityId: [
          { required: true, message: "请选择所属小区", trigger: "change" },
        ],
        userId: [
          { required: true, message: "请选择物业人员", trigger: "change" },
        ],
        vehicleNo: [
          { required: true, message: "车位编号不能为空", trigger: "blur" },
        ],
        plateNo: [
          { required: true, message: "车牌号不能为空", trigger: "blur" },
        ],
        plateColor: [
          { required: true, message: "车牌颜色不为空", trigger: "change" },
        ],

        vehicleBrand: [
          { required: true, message: "车辆品牌不为空", trigger: "blur" },
        ],
        vehicleType: [
          { required: true, message: "车辆类型不为空", trigger: "change" },
        ],
        vehicleColor: [
          { required: true, message: "请选择车辆颜色", trigger: "change" },
        ],
      },
    };
  },
  created() {
    this.getDict();
    this.getAllCommunityList();
    this.getList();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
  methods: {
    //修改
    headUpdate(row) {
      this.title = "修改资产";
      this.isDisabled = true;
      this.getStaffUserId(row.communityId);
      get(row.id).then((res) => {
        this.addForm = res.data;
        if (this.addForm.userId) {
          this.addForm.userId = parseFloat(this.addForm.userId);
        }
        if (this.addForm.vehicleNo) {
          this.addForm.vehicleNo = this.addForm.vehicleNo;
        }
        this.staffPhones = this.addForm.phone;
        this.openView = false;
        // 打开新增页面
        this.addOpen = true;
      });
    },
    // 打开新增弹窗
    addMethod() {
      this.isDisabled = false;
      // 关闭主页面
      this.openView = false;
      // 打开新增页面
      this.addOpen = true;
      this.addForm = {};
      this.title = "新增资产";
    },
    getAllCommunityList() {
      getAllCommunityList().then((response) => {
        this.allCommunityList = response.data;
      });
    },

    /** 导入按钮操作 */
    handleImport() {
      this.uploadImprot.title = "物业人员车辆资产信息导入";
      this.uploadImprot.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download(
        "estate/staffProperty/importTemplate",
        {},
        `物业人员车辆资产信息导入模板_${new Date().getTime()}.xlsx`
      );
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.uploadImprot.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.uploadImprot.open = false;
      this.uploadImprot.isUploading = false;
      this.$refs.uploadImprot.clearFiles();
      this.$alert(
        "<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" +
          response.msg +
          "</div>",
        "导入结果",
        { dangerouslyUseHTMLString: true }
      );
      this.treeList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.uploadImprot.submit();
    },

    // 关闭新增弹窗
    addClose() {
      this.$refs.addForm.resetFields();
      this.addOpen = false;
      this.showDetail = false;
      this.openView = true;
      this.addForm.ownerPhone = "";
      this.addForm.accountId = null;
      // 列表查询
      this.getList();
    },
    getStaffUserId(communityId) {
      this.addForm.userId = undefined;
      this.staffPhones = undefined;
      getCommunityIdStaffList(communityId, []).then((res) => {
        this.staffOptions = res.data;
      });
      //查询车位编号
      getCommunityCarportList(communityId).then((res) => {
        this.carportNoList = res.data;
      });
    },

    addSuccess() {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          if (this.addForm.id) {
            update(this.addForm).then((res) => {
              // 打开主页面
              this.openView = true;
              // 打开新增页面
              this.addOpen = false;
              this.getList();
              this.$modal.msgSuccess("修改成功");
            });
          } else {
            add(this.addForm).then((res) => {
              // 打开主页面
              this.openView = true;
              // 打开新增页面
              this.addOpen = false;
              this.getList();
              this.$modal.msgSuccess("新增成功");
            });
          }
        }
      });
    },

    // 查字典表数据
    getDict() {
      this.getDicts("sys_vehicle_color").then((response) => {
        this.carColour = response.data;
      });
      // 车牌颜色
      this.getDicts("sys_plate_color").then((response) => {
        this.plateColorList = response.data;
      });
      // 车牌类型
      this.getDicts("sys_plate_type").then((response) => {
        this.plateTypeList = response.data;
      });
      this.getDicts("vehicle_type").then((response) => {
        this.vehicleTypeOptions = response.data;
      });
    },

    //删除按钮
    handleDelete(item) {
      this.$modal
        .confirm("是否确认删除当前数据？")
        .then(() => {
          deleteById(item.id).then(() => {});
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 查询信息列表 */
    getList() {
      this.openView = 0;
      this.loading = false;
      listStaff(this.queryParams).then((response) => {
        this.staffList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    // 车辆颜色字典翻译
    vehicleColorFormat(vehicleColor) {
      if (vehicleColor) {
        return this.vehicleColorOptions.filter(
          (item) => item.dictValue == vehicleColor
        )[0].dictLabel;
      }
    },
    // 车辆类型字典翻译
    vehicleTypeFormat(vehicleType) {
      if (vehicleType) {
        return this.vehicleTypeOptions.filter(
          (item) => item.dictValue == vehicleType
        )[0].dictLabel;
      }
    },
    // 状态字典翻译
    auditStatusFormat(auditStatus) {
      if (auditStatus) {
        return this.auditStatusOptions.filter(
          (item) => item.dictValue == auditStatus
        )[0].dictLabel;
      }
    },

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
    // 取消按钮
    cancel() {
      this.open = false;
      this.openView = 0;
      this.getList();
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: "",
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    staffChange(userId) {
      console.log("this.staffOptions>", this.staffOptions);
      var obj = this.staffOptions.filter((item) => item.userId == userId)[0];
      if (obj) {
        this.staffPhones = obj.staffPhone;
      }
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.queryParams.name = null;
      this.queryParams.phone = null;
      this.queryParams.plateNo = null;
      this.queryParams.auditStatus = null;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },

    //驳回
    repulse(row) {
      this.open = true;
      this.isDisabled = false;
      this.title = "驳回原因";
      this.rejectForm.rejectReason = "";
      this.rejectForm.id = row.id;
    },
    //查看驳回原因
    getRepulse(row) {
      this.open = true;
      this.isDisabled = true;
      this.title = "驳回原因";
      this.rejectForm.rejectReason = row.rejectReason;
      this.rejectForm.id = row.id;
    },
    /** 审核按钮操作 */
    handleUpdateStatusPass(row) {
      const id = row.id || this.ids;
      this.$modal
        .confirm("是否确认审核所选数据")
        .then(function () {
          return updateStatusPass(id);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("审核成功");
        })
        .catch(() => {});
    },
    //驳回
    submitForm(rejectForm) {
      this.$refs[rejectForm].validate((valid) => {
        if (valid) {
          updateStatusReject(this.rejectForm).then((response) => {
            this.$modal.msgSuccess("驳回成功");
            this.cancel();
          });
        }
      });
    },
  },
};
</script>

<style lang="scss">
.staff-avatar .el-upload {
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 85%;
}

.staff-avatar .el-upload:hover {
  border-color: #409eff;
}

.staff-avatar .image-slot {
  justify-content: center;
}

.avatar-uploader-icon {
  font-size: 14px;
  color: #8c939d;
  /*width: 80%;*/
  width: 160px;
  height: 160px;
  line-height: 160px;
  text-align: center;
  border: silver 1px solid;
}

.avatar {
  width: 100%;
  height: 200px;
  display: block;
}

.showAvatar {
  width: 160;
  height: 160px;
  margin-left: 60px;
  display: block;
  margin-bottom: 10px;
  border: 1px solid #eceff7;
}

.el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #b3f1b6;
}
</style>

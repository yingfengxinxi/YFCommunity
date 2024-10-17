<template>
  <div>
    <div v-show="zhuye == 0">
      <div class="app-container">
        <el-form
          :model="queryParams"
          ref="queryForm"
          size="small"
          :inline="true"
          v-show="showSearch"
          label-width="95px"
        >
          <el-form-item label="车主姓名" prop="accountName">
            <el-input
              v-model="queryParams.accountName"
              placeholder="请输入姓名"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="车主手机号" prop="loginName">
            <el-input
              v-model="queryParams.loginName"
              placeholder="请输入手机号码"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="所属小区" prop="communityId">
            <el-select
              v-model="queryParams.communityId"
              placeholder="请选择所属小区"
              clearable
            >
              <el-option
                v-for="d in communityIds"
                :key="d.communityId"
                :label="d.communityName"
                :value="d.communityId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="审核状态" prop="auditStatus">
            <el-select
              v-model="queryParams.auditStatus"
              placeholder="审核状态"
              clearable
            >
              <el-option label="审核中" value="0" />
              <el-option label="通过" value="1" />
              <el-option label="驳回" value="2" />
            </el-select>
          </el-form-item>
          <el-form-item label="审核时间">
            <el-date-picker
              v-model="dateRange"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
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
          <!-- <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col> -->
          <!-- <el-col :span="1.5">
        <el-button
          type="info"
          plain
          icon="el-icon-upload2"
          size="mini"
          @click="handleImport"
          >导入</el-button
        >
      </el-col> -->
          <right-toolbar
            :showSearch.sync="showSearch"
            @queryTable="getList"
          ></right-toolbar>
        </el-row>

        <el-table
          v-loading="loading"
          :data="configList"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="车牌号" align="center" prop="plateNo" />
          <el-table-column label="车主姓名" align="center" prop="accountName" />
          <el-table-column label="手机号码" align="center" prop="loginName" />
          <el-table-column
            label="与户主关系"
            align="center"
            prop="ownerRelation"
          >
            <template slot-scope="scope">
              {{ getSysOldmanHuzhu(scope.row.ownerRelation) }}
              <!-- <div v-if="scope.row.accountType==0">业主</div>
          <div v-if="scope.row.accountType==1">租客</div>
          <div v-if="scope.row.accountType==2">访客</div>
          <div v-if="scope.row.accountType==3">其他</div> -->
            </template>
          </el-table-column>
          <el-table-column
            label="车辆品牌"
            align="center"
            prop="vehicleBrand"
          />
          <el-table-column
            label="所属小区"
            align="center"
            prop="communityName"
          />
          <el-table-column label="申请时间" align="center" prop="createTime">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="审核时间" align="center" prop="auditTime">
            <template slot-scope="scope">
              <span v-if="scope.row.auditTime == null">-</span>
              <span else>{{ parseTime(scope.row.auditTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="审核状态" align="center" prop="auditStatus">
            <template slot-scope="scope">
              <div v-if="scope.row.auditStatus == 0">待审核</div>
              <div v-if="scope.row.auditStatus == 1">审核通过</div>
              <div v-if="scope.row.auditStatus == 2" style="color: red">
                驳回
              </div>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                v-if="scope.row.auditStatus == 0"
                type="text"
                size="mini"
                icon="el-icon-check"
                @click="handleUpdate(scope.row)"
                style="color: lawngreen"
                >审核</el-button
              >
              <el-button
                v-if="scope.row.auditStatus != 0"
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="handleUpdate(scope.row)"
                >查看</el-button
              ><br />
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
      </div>
    </div>
    <div v-if="zhuye == 1">
      <el-form ref="form" :model="form" label-width="200px">
        <div>
          <h5 style="display: inline-block; margin-left: 20px; font-size: 17px">
            所属业户信息<b></b>
          </h5>
          <!-- <h5 style="margin-left:20px;font-size: 17px"><b>所属业户信息</b></h5> -->
          <el-button
            style="
              float: right;
              padding: 3px 25px;
              font-size: 15px;
              margin-right: 10px;
              margin-top: 20px;
            "
            type="primary"
            @click="back"
            plain
            >返回</el-button
          >
          <el-button
            style="
              float: right;
              padding: 3px 25px;
              font-size: 15px;
              color: lawngreen;
              margin-right: 10px;
              margin-top: 20px;
            "
            type="success"
            plain
            v-show="chakanyushenhe"
            @click="pass(form.vehicleId)"
            >通过</el-button
          >
          <el-button
            style="
              float: right;
              padding: 3px 25px;
              font-size: 15px;
              color: red;
              margin-right: 10px;
              margin-top: 20px;
            "
            type="danger"
            plain
            v-show="chakanyushenhe"
            @click="repulse()"
            >驳回</el-button
          >
        </div>
        <el-divider></el-divider>
        <!-- 第一行 -->
        <el-row>
          <el-col :offset="0" :span="4">
            <el-form-item label="所属小区: ">
              <div style="width: 400px; overflow: hidden">
                {{ form.communityName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-form-item label="所属楼栋: ">
              <div style="width: 400px; overflow: hidden">
                {{ form.buildingName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-form-item label="所属单元: ">
              <div style="width: 280px; overflow: hidden">
                {{ form.unitName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-form-item label="所属房间: ">
              <div style="width: 280px; overflow: hidden">
                {{ form.roomName }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第二行 -->
        <el-row>
          <el-col :offset="0" :span="4">
            <el-form-item label="业户姓名: ">
              <div style="width: 280px; overflow: hidden">
                {{ form.accountName }}
              </div>
            </el-form-item>
          </el-col>

          <el-col :offset="1" :span="4">
            <el-form-item label="联系方式: ">
              <div style="width: 280px; overflow: hidden">
                {{ form.loginName }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <!--第三行-->
        <el-row>
          <el-col :offset="0" :span="20" v-if="form.auditStatus == 2">
            <el-form-item label="驳回原因: ">
              <div style="width: 600px; overflow: hidden">
                {{ form.rejectReason }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 车辆信息 -->
        <div>
          <!-- <h5 style="margin-left:20px;font-size: 17px"><b>车辆信息</b></h5> -->
          <h5 style="display: inline-block; margin-left: 20px; font-size: 17px">
            车辆信息<b></b>
          </h5>
        </div>
        <el-divider></el-divider>
        <!-- 第一行 -->
        <el-row>
          <el-col :offset="0" :span="4">
            <el-form-item label="车牌号: ">
              <div style="width: 400px; overflow: hidden">
                {{ form.plateNo }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-form-item label="车辆颜色: ">
              <div style="width: 280px; overflow: hidden">
                {{ cartTypeFormat(form.vehicleColor) }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-form-item label="车辆类型: ">
              <div style="width: 280px; overflow: hidden">
                <div v-if="form.vehicleType == 0">其他车</div>
                <div v-if="form.vehicleType == 1">小型车</div>
                <div v-if="form.vehicleType == 2">大型车</div>
                <div v-if="form.vehicleType == 3">摩托车</div>
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-form-item label="车辆品牌: ">
              <div style="width: 280px; overflow: hidden">
                {{ form.vehicleBrand }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第二行 -->
        <el-row>
          <el-col :offset="0" :span="4">
            <el-form-item label="状态: ">
              <div style="width: 280px; overflow: hidden">
                <div v-if="form.vehicleStatus == 0">正常</div>
                <div v-if="form.vehicleStatus == 1">关闭</div>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第二行 -->
        <el-row>
          <el-col :offset="0" :span="4">
            <el-form-item label="行驶证: ">
              <image-preview
                v-if="form.realEstateList"
                v-for="item in form.realEstateList"
                :src="item"
                :width="50"
                :height="50"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :offset="0" :span="4">
            <el-form-item label="身份证正面: ">
              <image-preview
                v-if="form.frontIdCard"
                :src="form.frontIdCard"
                :width="50"
                :height="50"
              />
            </el-form-item>
          </el-col>
          <el-col :offset="0" :span="4">
            <el-form-item label="身份证反面: ">
              <image-preview
                v-if="form.backIdCard"
                :src="form.backIdCard"
                :width="50"
                :height="50"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :offset="0" :span="4">
            <el-form-item label="其他照片: ">
              <image-preview
                v-if="form.otherPhotoList"
                v-for="item in form.otherPhotoList"
                :src="item"
                :width="50"
                :height="50"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="驳回原因：" label-width="90" prop="RejectReason">
          <el-input
            v-model="form.RejectReason"
            type="textarea"
            placeholder="请输入内容"
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm(form.vehicleId)"
          >确 定</el-button
        >
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 车辆信息导入对话框 -->
    <el-dialog
      :title="upload.title"
      :visible.sync="upload.open"
      width="400px"
      append-to-body
    >
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url"
        :disabled="upload.isUploading"
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
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listConfig,
  listdict,
  getConfig,
  delConfig,
  passAndRepulse,
} from "@/api/estate/center/audit/carAudit/carAudit";
import { getToken } from "@/utils/auth";
export default {
  name: "propertyReview",
  dicts: [
    "sys_yes_no",
    "sys_certificate",
    "sys_normal_disable",
    "sys_vehicle_color",
    "sys_plate_color",
    "sys_plate_type",
    "sys_oldman_huzhu",
  ],
  data() {
    return {
      //驳回信息框
      open: false,
      //主页
      zhuye: 0,
      //查看与审核按钮开关
      chakanyushenhe: true,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      //下拉列表
      communityIds: [],
      // 表单参数
      form: {
        RejectReason: "",
        realEstateList: [],
        otherPhoto: [],
      },
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 车辆导入参数
      upload: {
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
        url: process.env.VUE_APP_BASE_API + "/estate/cl/importData",
      },
      // 参数表格数据
      configList: [],
      // 弹出层标题
      title: "",
      // 日期范围
      dateRange: [],
      //审核参数
      audit: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        accountName: undefined,
        loginName: undefined,
        communityId: undefined,
        auditStatus: undefined,
      },
      // 表单校验
      rules: {
        RejectReason: [
          { required: true, message: "驳回原因不能为空！", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    this.getList();
    this.getdictList();
  },
  methods: {
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "车辆导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download(
        "estate/cl/importTemplate",
        {},
        `车辆信息导入模板_${new Date().getTime()}.xlsx`
      );
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    getSysOldmanHuzhu(val) {
      return this.selectDictLabel(this.dict.type.sys_oldman_huzhu, val);
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(
        "<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" +
          response.msg +
          "</div>",
        "导入结果",
        { dangerouslyUseHTMLString: true }
      );
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    //车辆颜色
    cartTypeFormat(cardType) {
      return this.selectDictLabel(this.dict.type.sys_vehicle_color, cardType);
    },
    //车牌颜色
    cartplateTypeFormat(cardType) {
      return this.selectDictLabel(this.dict.type.sys_plate_color, cardType);
    },
    //车牌类型
    plateTypeFormat(cardType) {
      return this.selectDictLabel(this.dict.type.sys_plate_type, cardType);
    },
    /** 查询参数列表 */
    getList() {
      this.loading = true;
      listConfig(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.configList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },

    /** 查询下拉列表列表 */
    getdictList() {
      this.loading = true;
      listdict().then((response) => {
        this.communityIds = response.data;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.audit = [];
    },
    // 表单重置
    reset() {
      this.form = {
        configId: undefined,
        configName: undefined,
        configKey: undefined,
        configValue: undefined,
        configType: "Y",
        remark: undefined,
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
      this.ids = selection.map((item) => item.vehicleId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const vehicleId = row.vehicleId;
      this.$modal
        .confirm("是否确认删除车牌号为" + row.plateNo + "的数据项？")
        .then(function () {
          return delConfig(vehicleId);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    //返回主页
    back() {
      this.zhuye = 0;
      this.chakanyushenhe = true;
    },
    //通过审核
    pass(vehicleId) {
      this.$confirm("是否通过该数据的审核!", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          const auditStatus = "1";
          this.audit = { vehicleId, auditStatus };
          passAndRepulse(this.audit).then((res) => {
            this.zhuye = 0;
            this.audit = [];
            this.getList();
            this.$message({
              type: "success",
              message: "审核通过!",
            });
          });
        })
        .catch(() => {});
    },
    //驳回
    repulse(ownerId) {
      this.open = true;
      this.title = "驳回原因";
    },
    submitForm(vehicleId) {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.$confirm("是否确认驳回？", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              const auditStatus = "2";
              const rejectReason = this.form.RejectReason;
              (this.audit = { auditStatus, vehicleId, rejectReason }),
                passAndRepulse(this.audit).then((res) => {
                  this.form = {};
                  this.open = false;
                  this.zhuye = 0;
                  this.getList();
                  this.$message({
                    type: "success",
                    message: "驳回成功!",
                  });
                });
            })
            .catch(() => {});
        }
      });
    },
    //审核与查看
    handleUpdate(row) {
      this.reset();
      const vehicleId = row.vehicleId;
      this.chakanyushenhe = false;
      getConfig(vehicleId).then((response) => {
        if (response.data) {
          this.form = response.data;
          var realEstateList = [];
          if (this.form.realEstate) {
            realEstateList = this.form.realEstate.split(",");
          }
          this.form.realEstateList = realEstateList;

          var otherPhotoList = [];
          if (this.form.otherPhoto) {
            otherPhotoList = this.form.otherPhoto.split(",");
          }
          this.form.otherPhotoList = otherPhotoList;
          if (response.data.auditStatus == 0) {
            //状态为驳回时
            this.chakanyushenhe = true;
          }
        }
        this.zhuye = 1;
      });
    },
  },
};
</script>
<style  scoped>
/deep/.el-divider--horizontal {
  margin: -20px 0 30px 0 !important;
}
</style>

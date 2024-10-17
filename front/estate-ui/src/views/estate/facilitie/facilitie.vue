<template>
  <div class="app-container">
    <div v-show="openView">
      <el-row :gutter="20">
        <!--小区数据-->
        <el-col :span="4">
          <div class="head-container">
            <el-input
              placeholder="请输入小区名称"
              clearable
              v-model="filterText"
            >
            </el-input>
            <el-tree
              class="filter-tree"
              :data="currentList"
              :props="currentListProps"
              :expand-on-click-node="false"
              default-expand-all
              :filter-node-method="filterNode"
              ref="tree"
              highlight-current
              node-key="communityId"
              @node-click="handleNodeClick"
            >
            </el-tree>
          </div>
        </el-col>
        <!--装修数据-->
        <el-col :span="20" :xs="24">
          <el-form
            :model="queryParams"
            ref="queryForm"
            size="small"
            :inline="true"
            v-show="showSearch"
            label-width="68px"
          >
            <el-form-item label="设备名称" prop="equipmentName">
              <el-input
                v-model="queryParams.equipmentName"
                placeholder="请输入设备名称"
                clearable
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="设备类型" prop="facilitieEquipmentType">
              <el-select
                v-model="queryParams.facilitieEquipmentType"
                placeholder="请选择设备类型"
                clearable
                style="width: 160px"
              >
                <el-option
                  v-for="item in dict.type.facilitie_equipment_type"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="巡检时间" prop="facilitieEquipmentType">
              <el-date-picker
                v-model="dateRange"
                size="small"
                style="width: 220px"
                value-format="yyyy-MM-dd"
                type="daterange"
                range-separator="-"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
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
            <el-col :span="1.5">
              <el-button
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
                type="warning"
                plain
                icon="el-icon-download"
                size="mini"
                @click="handleImport()"
                >导入
              </el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="warning"
                plain
                icon="el-icon-download"
                size="mini"
                @click="handleExport"
                >导出</el-button
              >
            </el-col>
          </el-row>
          <right-toolbar
            :showSearch.sync="showSearch"
            @queryTable="getList"
          ></right-toolbar>

          <el-table v-loading="loading" :data="decorationList">
            <el-table-column
              label="设备名称"
              align="center"
              prop="equipmentName"
            />
            <el-table-column
              label="设备类型"
              align="center"
              prop="facilitieEquipmentType"
              :formatter="facilitieEquipmentName"
            />
            <el-table-column
              label="设备代码"
              align="center"
              prop="productNumber"
            />
            <el-table-column
              label="安装位置"
              align="center"
              prop="installationLocation"
            />
            <el-table-column
              label="设备是否存在异常"
              align="center"
              prop="equipmentStatus"
            >
              <template slot-scope="scope">
                <el-switch
                  v-model="scope.row.equipmentStatus"
                  active-value="1"
                  inactive-value="0"
                  @change="handleStatusChange(scope.row)"
                ></el-switch>
              </template>
            </el-table-column>
            <el-table-column
              label="最近巡检日期"
              align="center"
              key="updateTime"
              prop="updateTime"
              :show-overflow-tooltip="true"
            />

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
                  @click="handleQrCodeDetail(scope.row)"
                  >二维码</el-button
                >
                <!-- <br> -->
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="handleDetail(scope.row)"
                  >查看</el-button
                >
                <!-- <br> -->
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  >修改</el-button
                >
                <!-- <br> -->
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  style="color: #f56c6c"
                  @click="handleDelete(scope.row)"
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
    </div>

    <el-dialog title="二维码" :visible.sync="qrCodeOpen" width="350px">
      <el-form ref="form" :model="form" label-width="120px">
        <el-row>
          <el-col :span="5">
            <img
              style="height: 200px; width: 200px"
              :src="form.qrCode"
              class="showAvatar"
            />
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="5" :offset="9" style="margin-top: 8px">
            <el-button
              style="margin-left: 5px"
              type="primary"
              @click="downLoadImg(form.qrCode, form.equipmentName)"
              >下载</el-button
            >
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>

    <!--  添加/修改设施设备  -->

    <div v-show="facilitieShow" v-cloak>
      <el-tabs v-model="activeName">
        <el-tab-pane label="设备基本信息" name="house">
          <el-form
            ref="form"
            :disabled="isDisabled"
            :model="form"
            :rules="rules"
            label-width="111px"
          >
            <h4 class="form-header">房屋信息</h4>
            <el-row>
              <el-col>
                <el-row>
                  <el-col :span="8" :offset="0">
                    <el-form-item label="设备名称:" prop="equipmentName">
                      <el-input
                        v-model="form.equipmentName"
                        placeholder="请输入设备名称"
                        clearable
                        style="width: 200px"
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="8" :offset="0">
                    <el-form-item
                      label="设备类型:"
                      prop="facilitieEquipmentType"
                    >
                      <el-select
                        v-model="form.facilitieEquipmentType"
                        placeholder="请选择设备类型"
                        clearable
                        style="width: 200px"
                      >
                        <el-option
                          v-for="item in dict.type.facilitie_equipment_type"
                          :key="item.value"
                          :label="item.label"
                          :value="item.value"
                        />
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8" :offset="0">
                    <el-form-item label="品牌:" prop="bindName">
                      <el-input
                        v-model="form.bindName"
                        placeholder="请输入品牌"
                        style="width: 200px"
                        clearable
                      />
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="8" :offset="0">
                    <el-form-item label="规格型号:" prop="specificationsModel">
                      <el-input
                        v-model="form.specificationsModel"
                        placeholder="请输入规格型号"
                        style="width: 200px"
                        clearable
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="8" :offset="0">
                    <el-form-item label="供货单位:" prop="unitSupply">
                      <el-input
                        v-model="form.unitSupply"
                        placeholder="请输入供货单位"
                        style="width: 200px"
                        clearable
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="8" :offset="0">
                    <el-form-item label="安装时间:" prop="installationTime">
                      <el-date-picker
                        v-model="form.installationTime"
                        value-format="yyyy-MM-dd"
                        type="date"
                        style="width: 200px"
                        placeholder="请选择安装时间"
                      ></el-date-picker>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="8" :offset="0">
                    <el-form-item label="启用时间:" prop="enableTime">
                      <el-date-picker
                        v-model="form.enableTime"
                        value-format="yyyy-MM-dd"
                        type="date"
                        style="width: 200px"
                        placeholder="请选择启用时间"
                      ></el-date-picker>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8" :offset="0">
                    <el-form-item label="质保时间:" prop="warrantyExpireTime">
                      <el-date-picker
                        v-model="form.warrantyExpireTime"
                        value-format="yyyy-MM-dd"
                        type="date"
                        style="width: 200px"
                        placeholder="请选择启用时间"
                      ></el-date-picker>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="8" :offset="0">
                    <el-form-item label="维保单位:" prop="originalUnit">
                      <el-input
                        v-model="form.originalUnit"
                        placeholder="请输入维保单位"
                        style="width: 200px"
                        clearable
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="8" :offset="0">
                    <el-form-item label="营业执照号码:" prop="businessLicense">
                      <el-input
                        v-model="form.businessLicense"
                        placeholder="请输入营业执照号码"
                        style="width: 200px"
                        clearable
                      />
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="8" :offset="0">
                    <el-form-item
                      label="承担管理维护负责人:"
                      prop="chargeName"
                      label-width="153px"
                    >
                      <el-input
                        v-model="form.chargeName"
                        placeholder="请输入承担管理维护负责人"
                        style="width: 200px"
                        clearable
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="10" :offset="0">
                    <el-form-item
                      label="承担管理维护负责人联系方式:"
                      prop="chargePhone"
                      label-width="220px"
                    >
                      <el-input
                        v-model="form.chargePhone"
                        placeholder="请输入承担管理维护负责人联系方式"
                        style="width: 200px"
                        clearable
                      />
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="8" :offset="0">
                    <el-form-item label="按装位置:" prop="installationLocation">
                      <el-input
                        v-model="form.installationLocation"
                        placeholder="请输入按装位置"
                        style="width: 200px"
                        clearable
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="8" :offset="0">
                    <el-form-item label="设备代码:" prop="productNumber">
                      <el-input
                        :disabled="isDisProductNumber"
                        v-model="form.productNumber"
                        placeholder="请输入设备代码"
                        style="width: 200px"
                        clearable
                      />
                    </el-form-item>
                  </el-col>
                  <el-col :span="8" :offset="0">
                    <el-form-item
                      label="使用登记证编号:"
                      prop="registrationNumber"
                      label-width="125px"
                    >
                      <el-input
                        v-model="form.registrationNumber"
                        placeholder="请输入使用登记证编号"
                        style="width: 200px"
                        clearable
                      />
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="16" :offset="0">
                    <el-form-item label="设备图片:" prop="equipmentImages">
                      <image-upload
                        :isShowTip="!isDisabled"
                        v-model="form.equipmentImages"
                        :limit="3"
                      />
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="要件信息" name="document">
          <el-form :model="form" :disabled="isDisabled" label-width="140px">
            <h4 class="form-header">要件信息</h4>
            <el-row>
              <el-col :span="20">
                <el-row>
                  <el-col :span="18" :offset="0">
                    <el-form-item label="标识牌:" prop="signs">
                      <file-upload v-model="form.signs">点击上传</file-upload>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="18" :offset="0">
                    <el-form-item label="设施设备说明书:" prop="instructions">
                      <file-upload v-model="form.instructions"
                        >点击上传</file-upload
                      >
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="18" :offset="0">
                    <el-form-item label="其他材料:" prop="other">
                      <file-upload v-model="form.other">点击上传</file-upload>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>
        <!-- <el-tab-pane label="维保记录" name="handle">
            <el-form  :disabled="isDisabled"  label-width="140px">
              <h4 class="form-header">
                记录列表
              </h4>
            </el-form>

          </el-tab-pane> -->
        <el-tab-pane label="巡检记录" name="xjhandle">
          <el-form label-width="140px">
            <h4 class="form-header">巡检记录列表</h4>
            <el-table :data="patrolList">
              <el-table-column label="工单编号" align="center" prop="no" />
              <el-table-column
                label="巡检状态"
                align="center"
                prop="patrolStatus"
                :formatter="ps"
              />
              <el-table-column label="巡检人" align="center" prop="name" />
              <el-table-column
                label="巡检时间"
                align="center"
                key="updateTime"
                prop="updateTime"
                :show-overflow-tooltip="true"
              />
              <el-table-column
                label="巡检图片"
                align="center"
                prop="patrolImagesList"
              >
                <template slot-scope="scope">
                  <image-preview
                    v-for="image in scope.row.patrolImagesList"
                    :key="image"
                    :src="image"
                    :width="50"
                    :height="50"
                    style="margin: 0px 10px"
                  />
                </template>
              </el-table-column>
            </el-table>

            <pagination
              style="margin-bottom: 40px"
              v-show="patrolTotal > 0"
              :total="patrolTotal"
              :page.sync="patrolQueryParams.pageNum"
              :limit.sync="patrolQueryParams.pageSize"
              @pagination="getPatrolList"
            />
          </el-form>
        </el-tab-pane>
      </el-tabs>
      <el-row>
        <el-col :span="4" :offset="20" style="margin-top: 5px">
          <el-button type="primary" v-if="!isDisabled" @click="submit"
            >提 交</el-button
          >
          <el-button @click="cancelFacilitle">取 消</el-button>
        </el-col>
      </el-row>
    </div>

    <!-- 导入对话框 -->
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
        :action="upload.url + '?communityId=' + this.queryParams.communityId"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link
            type="primary"
            :underline="false"
            style="font-size: 12px; vertical-align: baseline"
            @click="importTemplate"
            >下载模板
          </el-link>
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
  listFacilitieEquipment,
  getFacilitieEquipmentDetail,
  del,
  add,
  update,
  updateEquipmentStatus,
} from "@/api/estate/facilitie/facilitie";
import { listPatrolWorkOrder } from "@/api/estate/patrol/patrol";
import { getCurrentList } from "@/api/estate/community";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getPersonList } from "../../../api/estate/community";
import { getToken } from "@/utils/auth";
export default {
  name: "facilitie",
  components: { Treeselect },
  dicts: ["facilitie_equipment_type", "patrol_status"],
  data() {
    return {
      qrCodeOpen: false,
      // 遮罩层
      loading: true,
      patrolLoading: true,
      patrolList: [],
      patrolTotal: 0,
      // 选中数组
      ids: [],
      active: 1,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      //主页面展示
      openView: true,
      //详情表单数据
      detailForm: {},
      violationList: [],
      dateRange: [],
      // 树筛选文本
      filterText: "",
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 装修表格数据
      decorationList: null,
      //装修工信息
      worker: {},
      //小区数据集合
      currentList: [],
      currentListProps: {
        children: "communityId",
        label: "communityName",
      },
      // 用户导入参数
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
        url:
          process.env.VUE_APP_BASE_API +
          "/estate/facilitieEquipment/importData",
      },
      facilitieShow: false,
      isDisabled: false,
      isDisProductNumber: false,
      // 弹出层标题
      title: "",
      activeName: "house",
      // 是否显示弹出层
      open: false,
      // 默认密码
      initPassword: undefined,
      // 表单参数
      form: {
        id: undefined,
        communityId: undefined,
        equipmentName: undefined,
        bindName: undefined,
        facilitieEquipmentType: undefined,
        specificationsModel: undefined,
        equipmentStatus: undefined,
        unitSupply: undefined,
        installationTime: undefined,
        enableTime: undefined,
        warrantyExpireTime: undefined,
        originalUnit: undefined,
        businessLicense: undefined,
        chargeName: undefined,
        chargePhone: undefined,
        installationLocation: undefined,
        productNumber: undefined,
        registrationNumber: undefined,
        other: undefined,
        signs: undefined,
        instructions: undefined,
        qrCode: undefined,
      },
      renovationAuditStatusOptions: [],
      defaultProps: {
        children: "children",
        label: "label",
      },
      // 表单校验
      rules: {
        equipmentName: [
          { required: true, message: "设备姓名不能为空", trigger: "blur" },
          { pattern: /[^ \x22]+/, message: "不能输入空格", trigger: "blur" },
        ],
        // bindName: [
        //   {required: true, message: "设备品牌不能为空", trigger: "blur"},
        //   {pattern: /[^ \x22]+/, message: "不能输入空格", trigger: "blur"},
        // ],
        facilitieEquipmentType: [
          { required: true, message: "请选择设备类型", trigger: "blur" },
        ],
        //specificationsModel: [
        //  {required: true, message: "规格型号不能为空",  trigger: "blur"}
        //],
        //unitSupply: [
        //  {required: true, message: "供货单位不能为空",  trigger: "blur"}
        //],
        //installationTime: [
        //  {required: true, message: "安装时间不能为空",  trigger: "blur"}
        //],
        //enableTime: [
        //  {required: true, message: "启用时间不能为空",  trigger: "blur"}
        //],
        //warrantyExpireTime: [
        //  {required: true, message: "质保到期时间不能为空",  trigger: "blur"}
        //],
        //originalUnit: [
        //  {required: true, message: "维保单位不能为空",  trigger: "blur"}
        //],
        //businessLicense: [
        //  {required: true, message: "营业执照号码不能为空",  trigger: "blur"}
        //],
        //chargeName: [
        //  {required: true, message: "承担管理维护负责人不能为空",  trigger: "blur"}
        //],
        //chargePhone: [
        //  {required: true, message: "承担管理维护负责人联系方式不能为空",  trigger: "blur"}
        //],
        installationLocation: [
          {required: true, message: "安装位置不能为空",  trigger: "blur"}
        ],
        productNumber: [
          { required: true, message: "设备代码不能为空", trigger: "blur" },
        ],
        //registrationNumber: [
        //  {required: true, message: "使用登记证编号不能为空",  trigger: "blur"}
        //],
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipmentName: undefined,
        facilitieEquipmentType: undefined,
        communityId: undefined,
      },
      // 查询参数
      patrolQueryParams: {
        pageNum: 1,
        pageSize: 10,
        facilitieEquipmentId: undefined,
      },
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
  created() {
    this.getTree();
  },
  methods: {
    ps(row, column) {
      return this.selectDictLabel(
        this.dict.type.patrol_status,
        row.patrolStatus
      );
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(row) {
      this.queryParams.communityId = row.communityId;
      this.form.communityId = row.communityId;
      this.getList();
    },
    // 状态修改
    handleStatusChange(row) {
      let text = row.equipmentStatus === "0" ? "正常" : "维修";
      this.$modal
        .confirm('确认要修改设备状态为"' + text + '"吗？')
        .then(function () {
          return updateEquipmentStatus(row.id, row.equipmentStatus);
        })
        .then(() => {
          this.$modal.msgSuccess("修改成功");
        })
        .catch(function () {
          row.equipmentStatus = row.equipmentStatus === "0" ? "1" : "0";
        });
    },
    //装修办理提交
    submit() {
      this.$refs["form"].validate((valid) => {
        this.form.communityId = this.queryParams.communityId;
        if (valid) {
          if (this.form.id) {
            update(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.cancelFacilitle();
            });
          } else {
            add(this.form).then((response) => {
              this.$modal.msgSuccess("添加成功");
              this.cancelFacilitle();
            });
          }
        }
      });
    },
    //取消
    cancelFacilitle() {
      this.reset();
      this.facilitieShow = false;
      this.openView = true;
      this.getList();
    },
    getPatrolList() {
      listPatrolWorkOrder(this.patrolQueryParams).then((response) => {
        this.patrolList = response.rows;
        this.patrolTotal = response.total;
      });
    },
    /** 查询装修列表 */
    getList() {
      if (this.dateRange) {
        this.queryParams.startTime = this.dateRange[0];
        this.queryParams.endTime = this.dateRange[1];
      }
      this.loading = true;
      listFacilitieEquipment(this.queryParams).then((response) => {
        this.decorationList = response.rows;
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
      this.activeName = "house";
      this.form = {
        id: undefined,
        communityId: undefined,
        equipmentName: undefined,
        bindName: undefined,
        facilitieEquipmentType: undefined,
        specificationsModel: undefined,
        equipmentStatus: undefined,
        unitSupply: undefined,
        installationTime: undefined,
        enableTime: undefined,
        warrantyExpireTime: undefined,
        originalUnit: undefined,
        businessLicense: undefined,
        chargeName: undefined,
        chargePhone: undefined,
        installationLocation: undefined,
        productNumber: undefined,
        registrationNumber: undefined,
        other: undefined,
        signs: undefined,
        instructions: undefined,
        qrCode: undefined,
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

    // 获取树数据
    getTree() {
      getCurrentList().then((res) => {
        this.currentList = res.data;
        this.queryParams.communityId = this.currentList[0].communityId;
        this.getList();

        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.currentList[0].communityId);
        });
      });
    },

    // 任务组名字典翻译
    facilitieEquipmentName(row, column) {
      return this.selectDictLabel(
        this.dict.type.facilitie_equipment_type,
        row.facilitieEquipmentType
      );
    },

    // 任务组名字典翻译
    auditStatusName(row, column) {
      return this.selectDictLabel(
        this.dict.type.renovation_audit_status,
        row.renovationAuditStatus
      );
    },

    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "设备信息导入";
      this.upload.open = true;
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },

    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
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

    /** 下载模板操作 */
    importTemplate() {
      this.download(
        "/estate/facilitieEquipment/importTemplate",
        {},
        `设备信息导入模板_${new Date().getTime()}.xlsx`
      );
    },
    handleExport() {
      this.download(
        "estate/facilitieEquipment/export",
        {
          ...this.queryParams,
        },
        `设施设备_${new Date().getTime()}.xlsx`
      );
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.isDisabled = false;
      this.isDisProductNumber = false;
      this.reset();
      this.facilitieShow = true;
      this.openView = false;
    },
    //查看二维码
    handleQrCodeDetail(row) {
      this.reset();
      const id = row.id;
      getFacilitieEquipmentDetail(id).then((response) => {
        this.form = response.data;
      });
      this.qrCodeOpen = true;
    },
    /** 查看详情按钮操作 */
    handleDetail(row) {
      this.reset();
      const id = row.id;
      getFacilitieEquipmentDetail(id).then((response) => {
        this.form = response.data;
      });
      this.patrolQueryParams.facilitieEquipmentId = row.id;
      this.getPatrolList();
      this.isDisabled = true;
      this.isDisProductNumber = true;
      this.openView = false;
      this.facilitieShow = true;
    },
    //修改
    handleUpdate(row) {
      this.isDisabled = false;
      this.isDisProductNumber = true;
      this.reset();
      const id = row.id;
      getFacilitieEquipmentDetail(id).then((response) => {
        this.form = response.data;
      });
      this.openView = false;
      this.facilitieShow = true;
      this.patrolQueryParams.facilitieEquipmentId = row.id;
      this.getPatrolList();
    },
    // 删除按钮
    handleDelete(row) {
      this.$modal
        .confirm("是否确认删除选中的数据项？")
        .then(function () {
          return del(row.id);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    //图片下载
    downLoadImg(url, name) {
      const image = new Image();
      image.setAttribute("crossOrigin", "anonymous");
      image.src = url;
      image.onload = () => {
        const canvas = document.createElement("canvas");
        canvas.width = image.width;
        canvas.height = image.height;
        const context = canvas.getContext("2d");
        context.drawImage(image, 0, 0, image.width, image.height);
        canvas.toBlob((blob) => {
          const url = URL.createObjectURL(blob);
          const a = document.createElement("a");
          a.download = name || "photo";
          a.href = url;
          a.click();
          a.remove();
          URL.revokeObjectURL(url);
        });
      };
    },
    //关闭详情页
    canceldetail() {
      this.detailForm = {};
      this.openView = true;
    },
  },
};
</script>

<style scoped>
/*设置高亮颜色*/
/deep/
  .el-tree--highlight-current
  .el-tree-node.is-current
  > .el-tree-node__content {
  background-color: #b3f1b6 !important;
}

/deep/ .stepsStyle .el-step__title.is-process,
.el-step__title.is-wait {
  font-weight: 400 !important;
  color: #ccc4cf !important;
  border-color: #a8a8a8 !important;
}
/* 这里设置的是下一步和等待状态里的文字颜色 */
/deep/.stepsStyle .el-step__head.is-process,
.el-step__heads.is-wait .el-step__icon.is-text {
  color: #a8a8a8 !important;
  border-color: #a8a8a8 !important;
}
/deep/.stepsStyle .el-step__head.is-finish {
  color: #2a82e4 !important;
}
/deep/.el-step__main .el-step__title.is-finish {
  padding-bottom: 0px;
}
/* 这里设置的是已完成状态里的文字颜色 */
/deep/.stepsStyle .el-step__head.is-finish .el-step__icon.is-text {
  color: #2a82e4 !important;
  border-color: #2a82e4 !important;
}
.el-step__title.is-finish {
  color: #2a82e4 !important;
}

/* 这里设置的是完成后过渡线条颜色 */
.el-step__line-inner {
  border-color: #2a82e4 !important;
}

/* 这里设置的是完成后的颜色 */
.el-step__head.is-success,
.el-step__title.is-success {
  color: #2a82e4 !important;
}
</style>

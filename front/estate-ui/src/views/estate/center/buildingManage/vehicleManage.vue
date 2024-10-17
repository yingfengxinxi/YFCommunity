<template>
  <div class="app-container">
    <el-row :gutter="20">
      <!--部门数据-->
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            v-model="search"
            placeholder="请输入小区名称"
            clearable
            size="small"
            prefix-icon="el-icon-search"
            style="margin-bottom: 20px"
          />
        </div>
        <el-table
          ref="singleTable"
          type="index"
          el-icon-office-building
          highlight-current-row
          :show-header="false"
          :data="viewDeptData"
          @cell-click="listClick"
        >
          <el-table-column prop="communityName" />
        </el-table>
      </el-col>
      <el-col :span="20" :xs="24">
        <!-- 条件搜索 -->
        <el-form
          :model="queryParams"
          ref="queryForm"
          size="small"
          :inline="true"
        >
          <el-form-item label="车牌号" prop="plateNo">
            <el-input
              v-model="queryParams.plateNo"
              placeholder="请输入车牌号"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="车主角色" prop="accountType">
            <el-select
              v-model="queryParams.accountType"
              clearable
              placeholder="请选择车主角色"
            >
              <el-option
                v-for="item in accountTypeOptions"
                :key="item.value"
                :label="item.name"
                :value="item.value"
              >
              </el-option>
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
        <!-- 按钮 -->
        <div>
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="addMethod"
            >新增</el-button
          >
          <el-button
            type="info"
            plain
            icon="el-icon-upload2"
            size="mini"
            @click="handleImport"
            >导入</el-button
          >
        </div>

        <!-- 列表展示 -->
        <el-table
          stripe
          v-loading="loading"
          :data="injList"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" align="center" />
          <el-table-column
            label="车牌号"
            align="center"
            prop="plateNo"
            type="number"
          />
          <el-table-column label="车主姓名" align="center" prop="accountName" />

          <el-table-column label="手机号码" align="center" prop="loginPhone" />
          <el-table-column label="车主角色" align="center" prop="accountType" />

          <el-table-column
            label="车辆品牌"
            align="center"
            prop="vehicleBrand"
          />
          <el-table-column label="录入时间" align="center" prop="createTime" />
          <el-table-column
            label="操作"
            align="center"
            fixed="right"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="view(scope.row)"
                >查看</el-button
              >
              <br>
              <el-button size="mini" type="text" @click="update(scope.row)"
                ><el-link
                  style="font-size: 12px"
                  icon="el-icon-edit"
                  type="success"
                  :underline="false"
                  >修改</el-link
                ></el-button
              >
              <br>
              <el-button size="mini" type="text" @click="remove(scope.row)"
                ><el-link
                  icon="el-icon-delete"
                  style="font-size: 12px; color: #f56c6c"
                  type="danger"
                  :underline="false"
                  >删除</el-link
                ></el-button
              >
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getBlackList"
      /></el-col>
    </el-row>

    <!-- 新增 -->
    <el-dialog
      :visible.sync="addOpen"
      @close="addClose"
      :title="title"
      append-to-body
      width="80%"
    >
      <!-- 新增页面 -->
      <el-form ref="form" :rules="rules" :model="addForm" label-width="101px">
        <h4 class="form-header">所属业户信息</h4>
        <!-- 第一行 -->
        <el-row>
          <el-col :offset="0" :span="6">
            <el-form-item label="所属小区: ">
              <div style="width: 280px; overflow: hidden">
                {{ addForm.communityName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="2" :span="6">
            <el-form-item label="所属楼栋: " prop="buildingValue">
              <el-select
                v-model="buildingValue"
                clearable
                placeholder="请选择楼栋"
                :disabled="isDisabled"
                @change="buildingChange(buildingValue)"
                style="width: 200px"
              >
                <el-option
                  v-for="item in buildingData"
                  :key="item.buildingId"
                  :label="item.buildingName"
                  :value="item.buildingId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :offset="2" :span="6">
            <el-form-item label="所属单元: " prop="unitValue">
              <el-select
                v-model="unitValue"
                clearable
                placeholder="请选择单元"
                :disabled="isDisabled"
                @change="unitChange(unitValue)"
                style="width: 200px"
              >
                <el-option
                  v-for="item in unitData"
                  :key="item.unitId"
                  :label="item.unitName"
                  :value="item.unitId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第二行 -->
        <el-row>
          <el-col :offset="0" :span="6">
            <el-form-item label="所属房号: " prop="roomValue">
              <el-select
                v-model="roomValue"
                clearable
                placeholder="请选择房号"
                :disabled="isDisabled"
                @change="roomChange(roomValue)"
                style="width: 200px"
              >
                <el-option
                  v-for="item in roomData"
                  :key="item.roomId"
                  :label="item.roomName"
                  :value="item.roomId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :offset="2" :span="6">
            <el-form-item label="业户姓名: " prop="ownerValue">
              <el-select
                v-model="addForm.ownerValue"
                clearable
                placeholder="请选择业户"
                :disabled="isDisabled"
                @change="ownerChange(addForm.ownerValue)"
                style="width: 200px"
              >
                <el-option
                  v-for="item in ownerData"
                  :key="item.ownerId"
                  :label="item.ownerName"
                  :value="item.ownerId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :offset="2" :span="6">
            <el-form-item label="联系方式: ">
              {{ ownerPhones }}
            </el-form-item>
          </el-col>
        </el-row>

        <h4 class="form-header">车辆信息</h4>
        <!-- 车辆信息第一行 -->
        <el-row>
          <el-col :offset="0" :span="6">
            <el-form-item label="车牌号: " prop="plateNo">
              <el-input
                style="width: 200px"
                clearable
                v-model="addForm.plateNo"
                placeholder="请输入车牌号"
                @input="getPlateNo(addForm.plateNo)"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :offset="2" :span="6">
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

          <el-col :offset="2" :span="6">
            <el-form-item label="车辆类型: " prop="vehicleType">
              <div style="width: 200px; overflow: hidden">
                <el-select
                  v-model="addForm.vehicleType"
                  clearable
                  placeholder="请选择车辆类型:"
                >
                  <el-option label="其他车" value="0" />
                  <el-option label="小型车" value="1" />
                  <el-option label="大型车" value="2" />
                  <el-option label="摩托车" value="3" />
                </el-select>
              </div>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 车辆信息第二行 -->
        <el-row>
          <el-col :offset="0" :span="6">
            <el-form-item label="车辆品牌: " prop="vehicleBrand">
              <div style="width: 200px; overflow: hidden">
                <el-input
                  clearable
                  v-model="addForm.vehicleBrand"
                  placeholder="请输入车辆品牌"
                  @input="getVehicleBrand(addForm.vehicleBrand)"
                ></el-input>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <el-row>
        <el-col :offset="20" :span="2">
          <div style="width: 280px">
            <el-button type="primary" @click="addSuccess()">确定</el-button>
            <el-button @click="addClose">取消</el-button>
          </div>
        </el-col>
      </el-row>
      <!-- </div> -->
    </el-dialog>

    <!-- 查看 -->
    <el-dialog
      :visible.sync="viewOpen"
      @close="addClose"
      title="车辆信息"
      append-to-body
      width="80%"
    >
      <!-- 查看页面 -->
      <el-form
        ref="form"
        :rules="rules"
        :model="addForm"
        label-width="101px"
        disabled
      >
        <h4 class="form-header">所属业户信息</h4>
        <!-- 第一行 -->
        <el-row>
          <el-col :offset="0" :span="6">
            <el-form-item label="所属小区: ">
              <div style="width: 280px; overflow: hidden">
                {{ addForm.communityName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="2" :span="6">
            <el-form-item label="所属楼栋: " prop="buildingValue">
              <el-select
                v-model="buildingValue"
                clearable
                placeholder="请选择楼栋"
                :disabled="isDisabled"
                @change="buildingChange(buildingValue)"
                style="width: 200px"
              >
                <el-option
                  v-for="item in buildingData"
                  :key="item.buildingId"
                  :label="item.buildingName"
                  :value="item.buildingId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :offset="2" :span="6">
            <el-form-item label="所属单元: " prop="unitValue">
              <el-select
                v-model="unitValue"
                clearable
                placeholder="请选择单元"
                :disabled="isDisabled"
                @change="unitChange(unitValue)"
                style="width: 200px"
              >
                <el-option
                  v-for="item in unitData"
                  :key="item.unitId"
                  :label="item.unitName"
                  :value="item.unitId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第二行 -->
        <el-row>
          <el-col :offset="0" :span="6">
            <el-form-item label="所属房号: " prop="roomValue">
              <el-select
                v-model="roomValue"
                clearable
                placeholder="请选择房号"
                :disabled="isDisabled"
                @change="roomChange(roomValue)"
                style="width: 200px"
              >
                <el-option
                  v-for="item in roomData"
                  :key="item.roomId"
                  :label="item.roomName"
                  :value="item.roomId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :offset="2" :span="6">
            <el-form-item label="业户姓名: " prop="ownerValue">
              <el-select
                v-model="addForm.ownerValue"
                clearable
                placeholder="请选择业户"
                :disabled="isDisabled"
                @change="ownerChange(addForm.ownerValue)"
                style="width: 200px"
              >
                <el-option
                  v-for="item in ownerData"
                  :key="item.ownerId"
                  :label="item.ownerName"
                  :value="item.ownerId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :offset="2" :span="6">
            <el-form-item label="联系方式: ">
              {{ ownerPhones }}
            </el-form-item>
          </el-col>
        </el-row>
        <h4 class="form-header">车位信息</h4>
        <el-row>
          <el-col :offset="0" :span="6">
            <el-form-item label="车位编号: " prop="carportNo">
              <el-select
                :disabled="isDisabled"
                filterable
                v-model="addForm.carportNo"
                placeholder="请选择车位编号"
                clearable
                style="width: 200px"
                @change="getCarport(addForm.carportNo)"
              >
                <el-option
                  v-for="d in carportNoList"
                  :key="d.carportNoId + ''"
                  :label="d.carportNo"
                  :value="d.carportNoId + ''"
                  :disabled="d.isCarport >= 1"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :offset="2" :span="6">
            <el-form-item label="车位属性: ">
              {{ getCarportNature(addForm.carportNature) }}
            </el-form-item>
          </el-col>
          <el-col :offset="2" :span="6">
            <el-form-item label="租售类型: ">
              {{ getRentalTypeName(addForm.rentalType) }}
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第二行 -->
        <el-row>
          <el-col :offset="0" :span="6">
            <el-form-item label="金额:" prop="carportFee">
              <el-input-number
                v-model="addForm.carportFee"
                :min="0"
                :max="999999"
                :precision="2"
                :step="0.1"
                style="width: 200px"
              ></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :offset="2" :span="6" v-if="addForm.rentalType != '0'">
            <el-form-item label="租赁开始时间:" prop="rentalStart">
              <el-date-picker
                style="width: 200px"
                v-model="addForm.rentalStart"
                value-format="yyyy-MM-dd"
                placeholder="请选择开始时间"
                type="date"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :offset="2" :span="6" v-if="addForm.rentalType != '0'">
            <el-form-item label="租赁结束时间:" prop="rentalEnd">
              <el-date-picker
                style="width: 200px"
                v-model="addForm.rentalEnd"
                value-format="yyyy-MM-dd"
                placeholder="请选择结束时间"
                type="date"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>

        <h4 class="form-header">绑定车辆信息</h4>
        <!-- 车辆信息第一行 -->
        <el-row>
          <el-col :offset="0" :span="6">
            <el-form-item label="车牌号: " prop="plateNo">
              <el-input
                style="width: 200px"
                clearable
                v-model="addForm.plateNo"
                placeholder="请输入车牌号"
                @input="getPlateNo(addForm.plateNo)"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :offset="2" :span="6">
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

          <el-col :offset="2" :span="6">
            <el-form-item label="车辆类型: " prop="vehicleType">
              <div style="width: 200px; overflow: hidden">
                <el-select
                  v-model="addForm.vehicleType"
                  clearable
                  placeholder="请选择车辆类型:"
                >
                  <el-option label="其他车" value="0" />
                  <el-option label="小型车" value="1" />
                  <el-option label="大型车" value="2" />
                  <el-option label="摩托车" value="3" />
                </el-select>
              </div>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 车辆信息第二行 -->
        <el-row>
          <el-col :offset="0" :span="6">
            <el-form-item label="车辆品牌: " prop="vehicleBrand">
              <div style="width: 200px; overflow: hidden">
                <el-input
                  clearable
                  v-model="addForm.vehicleBrand"
                  placeholder="请输入车辆品牌"
                  @input="getVehicleBrand(addForm.vehicleBrand)"
                ></el-input>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <el-row>
        <el-col :offset="20" :span="2">
          <div style="width: 280px">
            <el-button @click="viewClose">取消</el-button>
          </div>
        </el-col>
      </el-row>
      <!-- </div> -->
    </el-dialog>

    <!-- 用户导入对话框 -->
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
        <!-- <div class="el-upload__tip" slot="tip">
          <el-checkbox v-model="upload.updateSupport" />
          是否更新已经存在的用户数据
        </div> -->
        <div class="el-upload__tip text-center" slot="tip">
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
import { getToken } from "@/utils/auth";
import {
  getBuildingList,
  getCurrentList,
  getOwnerList,
  getRoomList,
  getUnitList,
} from "@/api/estate/community";
import {
  getDetail,
  listPage,
  remove,
  removes,
  saveOrUpdate,
  selectOwnerCar,
  selectOwnerName,
  updateDetailInfo,
} from "@/api/estate/center/buildingManage/vehicleManage/vehicleManage";
import { desensitization, equals } from "@/utils/common";
import {
  getCommunityCarportList,
  updateDetail,
} from "@/api/estate/center/buildingManage/carPositionManage/carPositionManage";
export default {
  data() {
    return {
      // 搜索
      search: "",
      viewOpen: false,
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: false,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 请求的地址
        // 例如  http://localhost:81/params/keyword 则 localurl : /params/keyword
        localurl: "/estate/carVehicle",
        // 请求上传模版的地址
        url: process.env.VUE_APP_BASE_API + "/estate/carVehicle/importData",
      },
      // 车辆ID 新增修改判断
      vehicleIds: null,
      //新增页面显示手机号
      ownerPhones: null,
      isDisabled: false,
      // 新增表单参数
      addForm: {
        vehicleColor: "",
        plateType: "",
        vehicleType: "",
        rentalStart: "",
        rentalEnd: "",
      },
      // 车辆颜色下拉框
      carColour: [],
      // 车牌颜色下拉框
      plateColorList: [],
      // 车辆类型下拉框
      plateTypeList: [],
      carColourValue: "",
      // 楼栋下拉框
      buildingData: [],
      buildingValue: "",
      // 单元下拉框
      unitData: [],
      unitValue: "",
      // 房号下拉框
      roomData: [],
      roomValue: "",
      // 业主车辆下拉框
      ownerCarData: [],
      // ownerCarValueId: "", // 业主车辆ID
      // 业户姓名
      // 临时假数据 { ownerId: 11, ownerName: "内容" }
      ownerData: [],
      // ownerValue: "",
      // 首页显示
      homeShow: true,
      // 单选框默认单个添加
      radio: "1",
      // 机构列表
      deptData: [],
      // 新增弹窗
      addOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        vehicleStatus: "",
        plateNo: "",
        // 小区ID
        communityId: undefined,
        // 小区ID 新增修改传参
        communityIds: null,
      },
      // 遮罩层
      loading: true,
      // 表格数据
      injList: [],
      // 选中数组
      ids: [],

      show: true,
      accountTypeOptions: [
        { value: "0", name: "业主" },
        { value: "1", name: "租客" },
        { value: "2", name: "访客" },
      ],
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      carportNoList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        carportNo: [
          { required: true, message: "车位编号不能为空", trigger: "blur" },
        ],
        carportNature: [
          { required: true, message: "车位属性不能为空", trigger: "blur" },
        ],
        rentalType: [
          { required: true, message: "租售类型不能为空", trigger: "blur" },
        ],
        carportFee: [
          { required: true, message: "金额不能为空", trigger: "blur" },
        ],
        plateNo: [
          { required: true, message: "车牌号不能为空", trigger: "blur" },
        ],
        plateColor: [
          { required: true, message: "车牌颜色不为空", trigger: "change" },
        ],
        vehicleStatus: [
          { required: true, message: "请选择车辆状态", trigger: "change" },
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
        ownerValue: [
          { required: true, message: "请选择业户姓名", trigger: "change" },
        ],
        plateType: [
          { required: true, message: "请选择车牌类型", trigger: "change" },
        ],
      },
    };
  },

  created() {
    // 机构下拉框
    this.getTypeList();
    // 车辆颜色查询
    this.getDict();
  },

  computed: {
    viewDeptData: function () {
      let search = this.search;
      let viewDeptData = this.deptData;
      if (search) {
        viewDeptData = this.deptData.filter((dataNews) => {
          return dataNews.communityName.indexOf(search) !== -1;
        });
      }
      return viewDeptData;
    },
  },

  methods: {
    carportNatureChange(carportNature) {
      this.addForm.disType = carportNature != "0";
    },
    getRentalType(rentalType) {
      if (rentalType == "0") {
        this.addForm.rentalStart = "";
        this.addForm.rentalEnd = "";
      }
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getBlackList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      // this.queryParams.dropDownForm = null;
      this.searchTime = "";
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 查询岗位列表 */
    getBlackList() {
      this.loading = false;
      listPage(this.queryParams).then((response) => {
        this.injList = response.rows;
        this.total = response.total;
      });
    },
    getCarport(carportNo) {
      var obj = { carportNo: carportNo };
      updateDetail(obj).then((res) => {
        this.addForm.carportNature = res.data.carportNature;
        this.addForm.rentalType = res.data.rentalType;
        this.addForm.disType = res.data.carportNature != "0";
        this.addForm.carportNo = carportNo;
        this.getRentalType(this.addForm.rentalType);
        this.$forceUpdate();
      });
    },
    getCarportNature(carportNature) {
      console.log("carportNature>>", carportNature);
      if (carportNature == "0") {
        return "人防";
      }
      if (carportNature == "1") {
        return "非人防";
      }
    },
    getRentalTypeName(rentalType) {
      if (rentalType == "0") {
        return "售";
      }
      if (rentalType == "1") {
        return "年租";
      }
      if (rentalType == "2") {
        return "月租";
      }
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.vehicleId);
      this.multiple = !selection.length;
    },

    // 表单重置
    reset() {
      this.form = {};
      this.resetForm("form");
    },

    // 取消详情按钮
    detailCancel() {
      this.open = false;

      this.show = true;
      this.homeShow = true;
      this.getBlackList();
    },

    selectView(row) {
      // this.reset();
      let params = {
        vehicleId: row.vehicleId,
      };
      getDetail(params).then((response) => {
        this.form = response.data;
        this.open = true;
      });
    },

    // 打开新增弹窗
    addMethod() {
      this.vehicleIds = null;
      this.isDisabled = false;
      // 关闭主页面
      this.homeShow = false;
      this.title = "新增车辆";
      // 打开新增页面
      this.addOpen = true;
      // 根据所属小区ID查所属楼栋
      this.selectFloor();
      // 清空addForm表单
      this.addFormEmpty();
      this.getCarportList(this.queryParams.communityId);
    },
    viewClose() {
      this.viewOpen = false;
      this.homeShow = true;
      this.addForm.ownerPhone = "";
      this.addForm.accountId = null;
      // 列表查询
      this.getBlackList();
      // 清空addForm表单
      this.addFormEmpty();
    },
    // 关闭新增弹窗
    addClose() {
      this.addOpen = false;

      this.homeShow = true;
      this.addForm.ownerPhone = "";
      this.addForm.accountId = null;
      // 列表查询
      this.getBlackList();
      // 清空addForm表单
      this.addFormEmpty();
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
    },

    //删除按钮
    handleDelete() {
      this.$modal
        .confirm("是否确认删除选中的多个数据项？")
        .then(() => {
          removes(this.ids).then(() => {});
        })
        .then(() => {
          this.getBlackList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },

    // 机构下拉框
    getTypeList() {
      getCurrentList().then((response) => {
        this.deptData = response.data;
        this.queryParams.communityId = this.deptData[0].communityId;
        this.queryParams.communityName = this.deptData[0].communityName;
        this.getCarportList(this.queryParams.communityId);
        this.getBlackList();
        this.$refs.singleTable.setCurrentRow(this.deptData[0]);
        this.addForm.communityId = this.deptData[0].communityId;
        this.addForm.communityName = this.deptData[0].communityName;
        this.communityIds = this.deptData[0].communityId;
      });
    },

    // 单元格点击时查列表
    listClick(row) {
      this.queryParams.communityId = row.communityId;
      // 点击单元格时赋值所属小区
      this.addForm.communityId = row.communityId;
      this.addForm.communityName = row.communityName;
      this.communityIds = row.communityId;
      this.getCarportList(this.queryParams.communityId);
      this.getBlackList();
    },
    //查询房屋信息信息
    getCarportList(communityId) {
      getCommunityCarportList(communityId).then((res) => {
        this.carportNoList = res.data;
      });
    },
    getPlateNo(plateNo) {
      this.addForm.plateNo = plateNo;
      this.$forceUpdate();
    },
    getVehicleBrand(vehicleBrand) {
      this.addForm.vehicleBrand = vehicleBrand;
      this.$forceUpdate();
    },
    view(row) {
      // 关闭主页面
      this.homeShow = false;
      // 打开新增页面(编辑)
      this.viewOpen = true;
      // 根据所属小区ID查所属楼栋
      this.selectFloor();
      // 编辑页面详情
      this.updateDetails(row.vehicleId);
    },
    //编辑按钮
    update(row) {
      // this.addForm
      this.vehicleIds = row.vehicleId;
      this.isDisabled = true;
      this.title = "修改车辆";
      // 关闭主页面
      this.homeShow = false;
      // 打开新增页面(编辑)
      this.addOpen = true;
      // 根据所属小区ID查所属楼栋
      this.selectFloor();
      // 编辑页面详情
      this.updateDetails(row.vehicleId);
    },

    // 编辑页面详情展示
    updateDetails(vehicleId) {
      let params = {
        vehicleId: vehicleId,
      };
      updateDetailInfo(params).then((response) => {
        response.data.ownerValue = response.data.ownerId - 0;
        this.addForm = response.data;
        if (this.addForm.carportNo) {
          this.isDisabled = true;
        } else {
          this.isDisabled = false;
        }
        this.addForm.vehicleType = response.data.vehicleType + "";
        this.addForm.plateColor = response.data.plateColor + "";
        this.addForm.plateType = response.data.plateType + "";

        if (response.data.buildingId) {
          this.buildingValue = response.data.buildingId;
          getUnitList(this.buildingValue).then((response) => {
            this.unitData = response.data;
          });
          if (response.data.unitId) {
            this.unitValue = response.data.unitId;
            getRoomList(this.unitValue).then((response) => {
              this.roomData = response.data;
            });
          }

          if (response.data.roomId) {
            this.roomValue = response.data.roomId;
            getOwnerList({ roomId: this.roomValue }).then((response) => {
              this.ownerData = response.data;
            });
          }

          this.ownerPhones = response.data.ownerPhone;
        } else {
          this.isDisabled = false;
          this.ownerPhones = "";
          this.addForm.ownerValue = "";
        }
      });
    },

    // 列表删除按钮
    remove(row) {
      let params = {
        vehicleId: row.vehicleId,
      };
      this.$confirm("此操作将删除该车辆信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          remove(params).then(() => {});
          this.getBlackList();
          this.$message({
            type: "success",
            message: "删除成功!",
          });
        })
        .catch(() => {});
    },

    // 根据小区ID查楼栋
    selectFloor() {
      getBuildingList(this.communityIds).then((response) => {
        this.buildingData = response.data;
      });
    },

    // 根据楼栋查单元
    buildingChange(buildingId) {
      if (buildingId != "") {
        // 变化时先清空
        this.unitValue = null;
        this.roomValue = null;
        this.addForm.ownerValue = "";
        this.ownerPhones = null;
        // 给addForm赋值
        this.addForm.buildingId = buildingId;
        getUnitList(buildingId).then((response) => {
          this.unitData = response.data;
        });
      } else {
        this.buildingValue = null;
        this.unitValue = null;
        this.roomValue = null;
        this.addForm.ownerValue = "";
        this.ownerPhones = null;
      }
    },

    // 根据单元查房号
    unitChange(unitId) {
      if (unitId != "") {
        // 先清空
        this.roomValue = null;
        this.addForm.ownerValue = "";
        this.ownerPhones = null;
        // 给addForm赋值
        this.addForm.unitId = unitId;
        getRoomList(unitId).then((response) => {
          this.roomData = response.data;
        });
      } else {
        this.unitValue = null;
        this.roomValue = null;
        this.addForm.ownerValue = "";
        this.ownerPhones = null;
      }
    },

    // 根据房号查业户姓名
    roomChange(roomId) {
      if (roomId != "") {
        // 先清空
        this.addForm.ownerValue = "";
        this.ownerPhones = null;
        // 给addForm赋值
        this.addForm.roomId = roomId;
        let params = {
          roomId: roomId,
        };
        getOwnerList(params).then((response) => {
          this.ownerData = response.data;
        });
      } else {
        this.addForm.ownerValue = "";
        this.ownerPhones = null;
      }
    },

    // 改变业户姓名时
    ownerChange(ownerValue) {
      if (ownerValue != "") {
        // 先清空
        this.ownerPhones = null;
        //查找业主手机号
        this.selectOwnerName(ownerValue);
      } else {
        this.ownerPhones = null;
      }
    },

    //查找业主手机号
    selectOwnerName(ownerValue) {
      // 查联系方式
      let params = {
        ownerId: ownerValue,
      };
      selectOwnerName(params).then((response) => {
        this.ownerPhones = response.data.loginPhone;
        this.addForm.loginPhone = response.data.loginPhone;
        this.addForm.accountId = response.data.accountId;
      });
    },

    //查找业主车辆 -- 废弃
    selectOwnerCar(ownerId) {
      // 查联系方式
      let params = {
        ownerId: ownerId,
      };
      selectOwnerCar(params).then((response) => {
        this.ownerCarData = response.data;
      });
    },

    // 确认新增或修改
    addSuccess() {
      if (!this.addForm.ownerValue) {
        this.$modal.msgWarning("请选择业主姓名");
        return;
      }
      // if (!this.addForm.carportNo) {
      //     this.$modal.msgWarning("请选择车位编号");
      //     return;
      // }
      // if (!this.addForm.carportFee) {
      //     this.$modal.msgWarning("请输入金额");
      //     return;
      // }
      // if (this.addForm.rentalType != '0') {
      //       if (!this.addForm.rentalStart) {
      //         this.$modal.msgWarning("请输入租赁开始时间");
      //         return;
      //       }
      //       if (!this.addForm.rentalEnd) {
      //         this.$modal.msgWarning("请输入租赁结束时间");
      //         return;
      //       }
      // }
      if (!this.addForm.plateNo) {
        this.$modal.msgWarning("请输入车牌号");
        return;
      }
      if (!this.addForm.vehicleColor) {
        this.$modal.msgWarning("请选择车辆颜色");
        return;
      }
      if (!this.addForm.vehicleType) {
        this.$modal.msgWarning("请选择车辆类型");
        return;
      }
      if (!this.addForm.vehicleBrand) {
        this.$modal.msgWarning("请输入车辆品牌");
        return;
      }

      this.addForm.communityId = this.communityIds;
      this.addForm.vehicleId = this.vehicleIds;
      saveOrUpdate(this.addForm).then((response) => {
        if (this.addForm.vehicleId) {
          this.$modal.msgSuccess("修改成功");
        } else {
          this.$modal.msgSuccess("添加成功");
        }

        // 清空addForm表单
        this.addFormEmpty();
        // 关闭新增页面
        this.addOpen = false;
        // 打开主页面
        this.homeShow = true;
        // 列表查询
        this.getBlackList();
      });
    },

    // 清空addForm表单
    addFormEmpty() {
      this.buildingData = null;
      this.unitData = null;
      this.roomData = null;
      this.ownerData = null;
      this.ownerCarData = null;

      this.buildingValue = null;
      this.unitValue = null;
      this.roomValue = null;
      this.addForm.ownerValue = "";

      this.ownerPhones = undefined;
      this.addForm.plateNo = undefined;
      this.addForm.plateColor = undefined;
      this.addForm.plateType = undefined;
      this.addForm.vehicleBrand = undefined;
      this.addForm.vehicleType = undefined;
      this.addForm.vehicleColor = undefined;
      this.addForm.carportNo = undefined;
      this.addForm.carportNature = undefined;
      this.addForm.rentalType = undefined;
      this.addForm.carportFee = undefined;
      this.addForm.rentalStart = undefined;
      this.addForm.rentalEnd = undefined;
    },

    handleImport() {
      this.upload.title = "导入数据";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download(
        this.upload.localurl + "/importTemplate",
        {},
        `车辆信息导入模板_${new Date().getTime()}.xlsx`
      );
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
      // 列表查询
      this.getBlackList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },

    /** 手机号脱敏 */
    phoneFormatter(phone) {
      if (!equals(phone + "", undefined)) {
        return desensitization(phone + "", 3, 7);
      }
    },

    /** 身份证号脱敏 */
    cardFormatter(card) {
      if (!equals(card, undefined)) {
        return desensitization(card, 6, 14);
      }
    },
  },
};
</script>
<style scoped>
</style>

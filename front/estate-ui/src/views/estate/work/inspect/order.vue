<template>
  <div class="app-container">
    <div v-show="homeShow">
      <el-row :gutter="20">
        <!--部门数据-->
        <el-col :span="4" :xs="24">
          <div class="head-container">
            <el-input
              placeholder="请输入小区名称"
              clearable
              size="small"
              prefix-icon="el-icon-search"
              style="margin-bottom: 20px"
              v-model="search"
            />
          </div>
          <el-table
            ref="singleTable"
            type="index"
            highlight-current-row
            :data="viewDeptData"
            :show-header="false"
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
            <el-form-item label="工单名称:" prop="inspectName">
              <el-input
                v-model="queryParams.inspectName"
                placeholder="请输入工单名称"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="工单来源:" prop="dataSource">
              <el-select
                clearable
                v-model="queryParams.dataSource"
                placeholder="请选择工单来源"
                style="width: 150px"
              >
                <el-option label="计划生成" value="0"/>
                <el-option label="后台添加" value="1"/>
                <el-option label="其他工单" value="2"/>
              </el-select>
            </el-form-item>
            <el-form-item label="分配方式:" prop="allotWay">
              <el-select
                clearable
                v-model="queryParams.allotWay"
                placeholder="请选择分配方式"
                style="width: 150px"
              >
                <el-option label="自动分配" value="0"/>
                <el-option label="管理员分配" value="1"/>
                <el-option label="抢单" value="2"/>
              </el-select>
            </el-form-item>
            <el-form-item label="执行人:" prop="staffName">
              <el-input
                v-model="queryParams.staffName"
                placeholder="请输入执行人姓名"
                style="width: 150px"
              />
            </el-form-item>
            <el-form-item label="订单状态:">
              <el-select
                clearable
                v-model="queryParams.orderStatus"
                placeholder="请选择订单状态"
                style="width: 150px"
              >
                <!-- 1待分配，2待接单，3待执行，4转交中，5执行中，6已完成，7超时未完成，8超时完成 -->
                <el-option label="待分配" value="1"/>
                <el-option label="待接单" value="2"/>
                <el-option label="待执行" value="3"/>
                <el-option label="转交中" value="4"/>
                <el-option label="执行中" value="5"/>
                <el-option label="已完成" value="6"/>
                <el-option label="超时未完成" value="7"/>
                <el-option label="超时完成" value="8"/>
              </el-select>
            </el-form-item>

            <el-form-item>
              <el-button
                type="primary"
                icon="el-icon-search"
                size="mini"
                @click="handleQuery"
              >搜索
              </el-button
              >
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
              >重置
              </el-button
              >
            </el-form-item>
          </el-form>

          <div>
            <!-- 权限 v-hasPermi="['estate:carport:remove']" -->
            <el-button
              v-hasPermi="['estate:ins:add']"
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="addMethod"
            >新增
            </el-button
            >
            <el-button
              v-hasPermi="['estate:ins:remove']"
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
            >删除
            </el-button
            >
          </div>

          <!-- 列表展示 -->
          <el-table
            stripe
            v-loading="loading"
            :data="injList"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column
              label="工单编号"
              align="center"
              prop="inspectNo"
              :show-overflow-tooltip="true"
            >
            </el-table-column>
            <el-table-column
              label="工单名称"
              align="center"
              prop="inspectName"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="巡检类型"
              align="center"
              prop="tourName"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="工单来源"
              align="center"
              prop="dataSource"
              :show-overflow-tooltip="true"
              :formatter="dataSourceChange"
            />
            <el-table-column
              label="分配方式"
              align="center"
              prop="allotWay"
              :show-overflow-tooltip="true"
              :formatter="allotWayChange"
            />
            <el-table-column
              label="负责人"
              align="center"
              prop="staffName"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="设备地址"
              align="center"
              prop="addressName"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="分配状态"
              align="center"
              prop="acceptStatus"
              :show-overflow-tooltip="true"
              :formatter="acceptStatusChange"
            />
            <el-table-column
              label="接单状态"
              align="center"
              prop="executeStatus"
              :show-overflow-tooltip="true"
              :formatter="executeStatusChange"
            />
            <el-table-column
              label="执行状态"
              align="center"
              prop="allotStatus"
              :show-overflow-tooltip="true"
              :formatter="allotStatusChange"
            />
            <el-table-column
              label="操作"
              align="center"
              fixed="right"
              width="280"
              class-name="small-padding fixed-width"
            >
              <template slot-scope="scope">
                <el-button size="mini" type="text" @click="detail(scope.row)"
                >
                  <el-link icon="el-icon-view" type="brand" :underline="false"
                  >详情
                  </el-link
                  >
                </el-button
                >
                <el-button size="mini" type="text" @click="update(scope.row)"
                >
                  <el-link
                    icon="el-icon-edit"
                    type="success"
                    :underline="false"
                    v-if="scope.row.allotStatus == '0'"
                  >修改
                  </el-link
                  >
                </el-button
                >
                <el-button
                  size="mini"
                  type="text"
                  @click="share(scope.row)"
                  v-hasPermi="['estate:ins:share']"
                >
                  <el-link
                    icon="el-icon-edit"
                    type="brand"
                    :underline="false"
                    v-if="scope.row.allotStatus == '0'"
                  >分配
                  </el-link
                  >
                </el-button
                >
                <el-button
                  size="mini"
                  type="text"
                  @click="remove(scope.row)"
                  v-hasPermi="['estate:ins:remove']"
                >
                  <el-link
                    icon="el-icon-delete"
                    type="danger"
                    :underline="false"
                    v-if="scope.row.allotStatus == '0'"
                  >删除
                  </el-link
                  >
                </el-button
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
          />
        </el-col>
      </el-row>
    </div>

    <!-- 新增页面 -->
    <el-dialog
      :visible.sync="addOpen"
      width="630px"
      @close="addClose"
      :title="addTitle"
      :modal="false"
    >
      <div>
        <el-form
          ref="addForm"
          :rules="addRules"
          :model="addForm"
          label-width="130px"
        >
          <el-row>
            <el-col>
              <el-form-item label="工单名称: " prop="inspectName">
                <el-input
                  clearable
                  v-model="addForm.inspectName"
                  placeholder="请输入工单名称"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="巡检类型: " prop="tourId">
                <el-select
                  v-model="addForm.tourId"
                  clearable
                  placeholder="请选择巡检类型"
                >
                  <el-option
                    v-for="item in tourList"
                    :key="item.tourId"
                    :label="item.tourName"
                    :value="item.tourId"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
<!--
            <el-col>
              <el-form-item label="设备类型: " prop="deviceType">
                <el-select
                  v-model="addForm.deviceType"
                  clearable
                  placeholder="请选择设备类型"
                  @change="deviceChange"
                >
                  <el-option
                    v-for="item in deviceTypeList"
                    :key="item.deviceType"
                    :label="item.deviceTypeName"
                    :value="item.deviceType"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
-->
            <el-col>
              <el-form-item label="设备编号: " prop="indexCode">
                <el-select
                  v-model="currentIndexCode"
                  value-key="indexCode"
                  placeholder="请选择设备编号"
                  @change="changeDevice"
                >
                  <el-option
                    v-for="item in deviceNoList"
                    :key="item.indexCode"
                    :label="item.facilityName"
                    :value="item"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="巡检开始时间: " prop="startTime">
                <el-date-picker
                  v-model="addForm.startTime"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  type="datetime"
                  placeholder="选择巡检开始时间"
                  :picker-options="pickerOptions"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="巡检结束时间: " prop="endTime">
                <el-date-picker
                  v-model="addForm.endTime"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  type="datetime"
                  placeholder="选择巡检结束时间"
                  :picker-options="pickerOptions"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="备注: " prop="remark">
                <el-input
                  v-model="addForm.remark"
                  type="textarea"
                  placeholder="请输入内容"
                  maxlength="1000"
                  show-word-limit
                />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :offset="3" :span="8"></el-col>
          </el-row>
          <el-row>
            <el-col :offset="10" :span="2">
              <div style="width: 280px">
                <el-button type="primary" @click="addSuccess(addForm)"
                >确定
                </el-button
                >
                <el-button @click="addClose">取消</el-button>
              </div>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-dialog>

    <!-- 分配页面 -->
    <el-dialog
      :visible.sync="shareOpen"
      width="630px"
      @close="shareClose"
      title="分配"
    >
      <el-form
        ref="shareForm"
        :rules="shareRules"
        :model="shareForm"
        label-width="130px"
      >
        <el-row>
          <el-col>
            <el-form-item label="负责人: " prop="staffId">
              <el-select
                v-model="shareForm.staffId"
                clearable
                placeholder="请选择负责人"
              >
                <el-option
                  v-for="item in getShareNameList"
                  :key="item.staffId"
                  :label="item.staffName"
                  :value="item.staffId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <!--
                    <el-col v-if="this.shareForm.allotWay == '1'">
                      <el-form-item label="执行人: " prop="executeId">
                        <el-select
                          v-model="shareForm.executeId"
                          clearable
                          placeholder="请选择执行人"
                        >
                          <el-option
                            v-for="item in getShareNameList"
                            :key="item.staffId"
                            :label="item.staffName"
                            :value="item.staffId"
                          >
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
          -->
        </el-row>

        <el-row>
          <el-col :offset="10" :span="2">
            <div style="width: 280px">
              <el-button type="primary" @click="shareSuccess">确定</el-button>
              <el-button @click="shareClose">取消</el-button>
            </div>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>

    <!-- 详情页面 -->
    <div v-show="showDetail">
      <el-form ref="form" :model="detailForm" label-width="180px">
        <el-row>
          <el-col :offset="23" :span="24">
            <el-button @click="cancel">关 闭</el-button>
          </el-col>
        </el-row>
        <!--  ----------------------------- 工单详情 --------------------------- -->
        <h5><b>工单详情</b></h5>
        <el-divider></el-divider>
        <!-- 第一行 -->
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="工单编号: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.inspectNo }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="工单名称: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.inspectName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="巡检类型: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.tourName }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第二行 -->
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="工单来源类型: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.dataSourceName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="来源工单: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.orderNo }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="主要负责人: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.staffName }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第三行 -->
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="工单开始时间: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.startTime }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="工单结束时间: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.endTime }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="完成时间: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.finishTime }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第四行 -->
        <div v-if="detailForm.executeStatus === '2' ||detailForm.executeStatus === '3'">
          <div>
            <h5><b>工单完成情况</b></h5>
          </div>
          <el-row>
            <el-col :offset="1" :span="5">
              <el-form-item label="完成备注: ">
                <div style="width: 280px; overflow: hidden">
                  {{ detailForm.remark }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :offset="1" :span="5">
              <el-form-item label="完成时间:">{{ detailForm.finishTime }}</el-form-item>
            </el-col>
            <el-col :offset="1" :span="5">
              <el-form-item label="完成时图片:">
                <el-image
                  style="width: 100px; height: 100px"
                  :src="detailForm.imageUrl"
                  fit="contain"></el-image>
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <!--  ----------------------------- 设备详情 --------------------------- -->
        <div>
          <h5><b>设备详情</b></h5>
        </div>
        <el-divider></el-divider>
        <el-row>
          <el-col :span="24">
            <el-table
              border
              type="index"
              highlight-current-row
              :data="ownerListData"
            >
              <el-table-column
                label="设备编号"
                align="center"
                :show-overflow-tooltip="true"
                prop="facilityNo"
              />
              <el-table-column
                label="设备名称"
                align="center"
                :show-overflow-tooltip="true"
                prop="facilityName"
              />
              <el-table-column
                label="设备类型号"
                align="center"
                :show-overflow-tooltip="true"
                prop="facilityType"
              />
              <el-table-column
                label="设备地址"
                align="center"
                :show-overflow-tooltip="true"
                prop="addressName"
              />
            </el-table>
          </el-col>
        </el-row>

        <!--  ----------------------------- 工单执行人信息 --------------------------- -->
        <div>
          <h5><b>工单协助人信息</b></h5>
        </div>
        <el-divider></el-divider>
        <el-row>
          <el-col :span="24">
            <el-table
              border
              type="index"
              highlight-current-row
              :data="deatilListGenerate"
            >
              <el-table-column
                label="人员姓名"
                align="center"
                :show-overflow-tooltip="true"
                prop="staffName"
              />
              <el-table-column
                label="手机号"
                align="center"
                :show-overflow-tooltip="true"
                prop="staffPhone"
              />
              <el-table-column
                label="身份证号"
                align="center"
                :show-overflow-tooltip="true"
                prop="cardNo"
              />
              <!-- <el-table-column
                label="主要负责人"
                align="center"
                :show-overflow-tooltip="true"
                prop="mainStaffName"
              /> -->
              <el-table-column
                label="人员类别"
                align="center"
                :show-overflow-tooltip="true"
                prop="personnelType"
              />
            </el-table>
          </el-col>
        </el-row>

        <!--  ----------------------------- 执行详情 --------------------------- -->
        <div>
          <h5><b>执行详情</b></h5>
        </div>
        <el-divider></el-divider>
        <el-row>
          <div v-for="(item, index) in deatilListThreeList" :key="index">
            <el-descriptions>
              <el-descriptions-item :label="item.createTime"
              ><b>{{ item.orderResult }}</b></el-descriptions-item
              >
              <el-descriptions-item label="提交人"
              ><b>{{ item.userName }}</b></el-descriptions-item
              >
              <el-descriptions-item v-if="item.relevant != ''" label="相关人"
              ><b>{{ item.relevant }}</b></el-descriptions-item
              >
            </el-descriptions>
          </div>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {
  deatilListThree,
  deatilListTwo,
  detailList,
  getDeviceNoList,
  getDeviceTypeList,
  getShareName,
  listPage,
  removes,
  saveOrUpdate,
  shareUpdate,
  updateDetail,
} from "@/api/estate/work/inspect/order";
import {getTourList} from "@/api/estate/work/inspect/type";
import {desensitization, equals} from "@/utils/common";

export default {
  data() {
    return {
      search: "",
      asd: true,
      // 新增修改页面弹窗提示
      addTitle: "",
      // 巡检类型
      tourList: [],
      // 新增时获取设备类型
      deviceTypeList: [],
      // 新增时获取设备编码
      deviceNoList: [],
      //  转让时获取转让人ID
      getShareNameList: [],
      // 详情表单
      detailForm: {},
      // 新增页面表单
      addForm: {
        tourName: "",
        inspectName: "",
        tourId: undefined,
        indexCode: undefined,
        startTime: undefined,
        endTime: undefined,
        remark: undefined,
      },
      currentIndexCode: {indexCode: undefined},
      // 分配弹窗
      shareForm: {},
      // 小区ID
      communityId: undefined,
      // 小区ID 新增修改传参
      communityIds: null,
      // 详情内表格数据
      ownerListData: [],
      // 详情内 - 工单生成列表
      deatilListGenerate: [],
      // 详情内 - 执行详情列表
      deatilListThreeList: [],
      // 机构列表
      deptData: [],
      // 机构下拉框
      deptValue: "",
      // 新增弹窗
      addOpen: false,
      // 分配弹窗
      shareOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        // 小区ID
        communityId: undefined,
        dataSource: "",
        allotWay: "",
        staffName: "",
        orderStatus: "",
        inspectName: "",
      },
      // 遮罩层
      loading: true,
      // 表格数据
      injList: [],
      // 选中数组
      ids: [],
      idss: [],
      // 详情页面默认关闭
      showDetail: false,
      homeShow: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 表单参数
      form: {},
      // 新增表单校验
      addRules: {
        inspectName: [
          {required: true, message: "工单名称不能为空", trigger: "blur"},
        ],
        deviceType: [
          {required: true, message: "设备类型不能为空", trigger: "blur"},
        ],
        tourId: [
          {required: true, message: "巡检类型不能为空", trigger: "blur"},
        ],
        indexCode: [
          {required: true, message: "设备编号不能为空", trigger: "blur"},
        ],
        startTime: [
          {required: true, message: "巡检开始时间不能为空", trigger: "blur"},
        ],
        endTime: [
          {required: true, message: "巡检结束时间不能为空", trigger: "blur"},
        ],
      },
      shareRules: {
        staffId: [
          {required: true, message: "负责人不能为空", trigger: "change"},
        ],
        executeId: [
          {required: true, message: "执行人不能为空", trigger: "change"},
        ],
      },
      // 设置不能选则当天之前的时间    写data()中
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        },
      },
    };
  },

  created() {
    // 机构下拉框
    this.getTypeList();
    // 列表查询
    // this.getBlackList();
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
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getBlackList();
    },

    /** 重置按钮操作 */
    resetQuery() {
      this.searchTime = "";
      this.queryParams.orderStatus = null;
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

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.inspectId);
      this.multiple = !selection.length;
    },

    // 表单重置
    reset() {
      this.form = {};
      this.resetForm("form");
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.showDetail = false;
      this.homeShow = true;
      this.getBlackList();
      //this.reset();
    },

    selectView(row) {
      let params = {
        inspectId: row.inspectId,
      };
      let paramss = {
        orderId: row.inspectId,
        orderType: "2",
      };
      detailList(params).then((response) => {
        this.ownerListData = response.data;
      });
      deatilListTwo(paramss).then((response) => {
        this.deatilListGenerate = response.data;
      });
      deatilListThree(paramss).then((response) => {
        this.deatilListThreeList = response.data;
      });
    },

    /** 详情 */
    detail(row) {
      this.showDetail = true;
      this.homeShow = false;
      this.detailForm.inspectName = row.inspectName;
      this.detailForm.inspectNo = row.inspectNo;
      this.detailForm.startTime = row.startTime;
      this.detailForm.endTime = row.endTime;
      this.detailForm.dataSource = row.dataSource;
      this.detailForm.finishTime = row.finishTime;
      this.detailForm.remark = row.remark;
      this.detailForm.tourName = row.tourName;
      this.detailForm.staffName = row.staffName;
      this.detailForm.executeStatus = row.executeStatus;
      this.detailForm.imageUrl = row.imageUrl;

      if (this.detailForm.dataSource == "2") {
        this.detailForm.orderNo = row.orderNo;
      } else {
        this.detailForm.orderNo = "暂无";
      }
      this.dataSourceChange(row);
      this.selectView(row);
    },

    // 机构下拉框
    getTypeList() {
      getCurrentList().then((response) => {
        this.deptData = response.data;
        this.queryParams.communityId = this.deptData[0].communityId;
        // this.queryParams.communityName = this.deptData[0].communityName;
        this.$refs.singleTable.setCurrentRow(this.deptData[0]);
        this.addForm.communityId = this.deptData[0].communityId;
        this.addForm.communityName = this.deptData[0].communityName;
        this.communityIds = this.deptData[0].communityId;
        this.getBlackList();
      });
    },

    // 单元格点击时查列表
    listClick(row) {
      this.queryParams.communityId = row.communityId;
      // // 点击单元格时赋值所属小区
      this.addForm.communityId = row.communityId;
      this.addForm.communityName = row.communityName;
      this.communityIds = row.communityId;
      this.getBlackList();
    },

    //删除按钮
    handleDelete() {
      this.$modal
        .confirm("是否确认删除选中的数据项？")
        .then(() => {
          removes(this.ids).then(() => {
            this.getBlackList();
            this.$modal.msgSuccess("删除成功");
          });
        })
        .catch(() => {
          this.$modal.msgError("删除失败");
        });
    },

    // 打开新增弹窗
    addMethod() {
      this.currentIndexCode = {indexCode :undefined};
      this.addForm.inspectId = "";
      this.addTitle = "新增工单";
      // this.carportIds = null;
      this.addOpen = true;
      this.beforeMethod();
      this.deviceChange();
    },

    // 关闭新增弹窗
    addClose() {
      this.$refs.addForm.resetFields();
      this.addOpen = false;
      this.getBlackList();
      this.addFormEmpty();
    },

    // 修改
    update(row) {
      // this.addForm.deviceTypes = this.addForm.deviceType;
      this.addTitle = "修改工单";
      this.addOpen = true;
      this.updateDetails(row.inspectId);
      this.addForm.inspectId = row.inspectId;
      this.beforeMethod();
      this.deviceChange();
    },

    // 编辑页面详情展示
    updateDetails(inspectId) {
      let params = {
        inspectId: inspectId,
      };
      updateDetail(params).then((response) => {
        this.addForm = response.data;
        this.currentIndexCode = {indexCode: this.addForm.indexCode};
      });
    },

    // 提交
    addSuccess(addForm) {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          delete this.addForm["communityName"];
          if (
            Date.parse(this.addForm.startTime) >= Date.parse(this.addForm.endTime)
          ) {
            this.$message({
              type: "error",
              message: "结束时间不能大于等于开始时间!",
            });
          } else {
            this.$confirm("是否确定提交?", "提示", {
              confirmButtonText: "确定",
              cancelButtonText: "取消",
              type: "warning",
            })
              .then(() => {
                saveOrUpdate(this.addForm).then((response) => {
                  // this.addForm = response.data;
                  if (response.code != 200) {
                    this.$modal.msgError(response.msg);
                  } else {
                    this.$modal.msgSuccess(response.msg);
                  }
                  this.addFormEmpty();
                  this.getBlackList();
                  this.getTypeList();
                  this.addOpen = false;
                });
              })
              .catch(() => {
                this.$message({
                  type: "info",
                  message: "已取消提交",
                });
              });
          }
        }
      });
    },


    // 清空addForm表单
    addFormEmpty() {
      this.addForm.inspectName = "";
      this.addForm.deviceType = "";
      this.addForm.indexCode = "";
      this.addForm.startTime = "";
      this.addForm.endTime = "";
      this.addForm.remark = "";
      this.currentIndexCode = {indexCode: undefined};
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

    remove(row) {
      let idss = this.idss;
      idss.push(row.inspectId);
      this.$modal
        .confirm("是否确认删除选中的数据项？")
        .then(() => {
          removes(idss).then(() => {
            this.getBlackList();
            this.$modal.msgSuccess("删除成功");
          });
        })
        .catch(() => {
          this.$modal.msgError("删除失败");
        });
      this.idss = [];
    },

    dataSourceChange(row) {
      if (row.dataSource === "0") {
        this.detailForm.dataSourceName = "计划生成";
        return "计划生成";
      }
      if (row.dataSource === "1") {
        this.detailForm.dataSourceName = "后台添加";
        return "后台添加";
      }
      if (row.dataSource === "2") {
        this.detailForm.dataSourceName = "其他工单";
        return "其他工单";
      }
    },

    allotWayChange(row) {
      if (row.allotWay === "0") {
        return "自动分配";
      }
      if (row.allotWay === "1") {
        return "管理员分配";
      }
      if (row.allotWay === "2") {
        return "抢单";
      }
    },

    acceptStatusChange(row) {
      if (row.allotStatus === "0") {
        return "待分配";
      }
      if (row.allotStatus === "1") {
        return "已分配";
      }
    },

    executeStatusChange(row) {
      if (row.acceptStatus === "0") {
        return "待接单";
      }
      if (row.acceptStatus === "1") {
        return "已接单";
      }
    },

    allotStatusChange(row) {
      if (row.executeStatus === "0") {
        return "待执行";
      }
      if (row.executeStatus === "1") {
        return "执行中";
      }
      if (row.executeStatus === "2") {
        return "已完成";
      }
      if (row.executeStatus === "3") {
        return "超时完成";
      }
      if (row.executeStatus === "4") {
        return "超时未完成";
      }
    },

    // 新增修改弹框前搜索
    beforeMethod() {
      this.addForm.communityId = this.queryParams.communityId;
      let params = {
        communityId: this.queryParams.communityId,
      };

      // 查找设备类型
      getDeviceTypeList().then((response) => {
        this.deviceTypeList = response.data;
      });

      let query = {
        tourType: "2",
      };
      getTourList(query).then((response) => {
        this.tourList = response.data;
      });
    },

    deviceChange() {
      // 查找设备编号
      getDeviceNoList(this.queryParams.communityId).then((response) => {
        this.deviceNoList = response.data;
      });
    },

    // 打开分配弹窗
    share(row) {
      this.shareForm.allotWay = row.allotWay;
      this.shareForm.inspectId = row.inspectId;
      this.shareOpen = true;
      // this.shareForm.inspectId = row.inspectId;
      // this.shareForm.communityId = row.communityId;
      let params = {
        allotType: 2,
        communityId: row.communityId,
      };
      getShareName(params).then((response) => {
        this.getShareNameList = response.data;
      });
    },

    // 关闭分配弹窗
    shareClose() {
      this.$refs.shareForm.resetFields();
      this.shareOpen = false;
      // this.getBlackList();
      this.shareForm.staffId = "";
      this.shareForm.executeId = "";
    },

    // 分配弹窗确定
    shareSuccess() {
      if (this.shareForm.allotWay != "1") {
        this.shareForm.executeId = null;
      }
      this.$refs.shareForm.validate((valid) => {
        if (valid) {
          this.$confirm("是否确定提交?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              shareUpdate(this.shareForm).then((response) => {
                // this.shareForm = response.data;
              });
              this.$message({
                type: "success",
                message: "提交成功!",
              });
              this.getBlackList();
              this.getTypeList();
              this.shareOpen = false;
              this.shareForm.userName = "";
              this.shareForm.staffId = "";
              this.shareForm.executeId = "";
              this.shareForm.inspectId = undefined;
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消提交",
              });
              this.shareForm.userName = "";
              this.shareForm.staffId = "";
              this.shareForm.executeId = "";
            });
        }
      });
    },
    changeDevice(item){
      this.addForm.deviceType = item.deviceType;
      this.addForm.indexCode = item.indexCode;
      this.currentIndexCode = {indexCode: item.indexCode};
    }
  },
};
</script>
<style scoped>
</style>

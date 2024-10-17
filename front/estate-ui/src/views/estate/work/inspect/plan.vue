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
                v-model="search"
                prefix-icon="el-icon-search"
                style="margin-bottom: 20px"
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
            <el-table-column prop="communityName"/>
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
            <el-form-item label="计划名称" prop="planName">
              <el-input
                  v-model="queryParams.planName"
                  placeholder="请输入计划名称"
                  @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="分配方式" prop="allotWay">
              <el-select
                  clearable
                  v-model="queryParams.allotWay"
                  placeholder="请选择分配方式"
              >
                <el-option label="自动分配" value="0"/>
                <el-option label="管理员分配" value="1"/>
                <el-option label="抢单" value="2"/>
              </el-select>
            </el-form-item>
            <el-form-item label="状态" prop="planOrder">
              <el-select
                  clearable
                  v-model="queryParams.planOrder"
                  placeholder="请选择状态"
              >
                <el-option label="关闭" value="0"/>
                <el-option label="开启" value="1"/>
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
                type="primary"
                plain
                icon="el-icon-plus"
                v-hasPermi="['estate:plan:add']"
                size="mini"
                @click="addMethod"
            >新增
            </el-button
            >
            <el-button
                type="danger"
                plain
                icon="el-icon-delete"
                size="mini"
                :disabled="multiple"
                v-hasPermi="['estate:plan:remove']"
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
                label="计划名称"
                align="center"
                prop="planName"
                :show-overflow-tooltip="true"
            >
            </el-table-column>
            <el-table-column
                label="巡检类型"
                align="center"
                prop="tourName"
                :show-overflow-tooltip="true"
            />
            <el-table-column
                label="分配方式"
                align="center"
                prop="allotWayName"
                :show-overflow-tooltip="true"
            />
            <el-table-column
                label="巡检设备"
                align="center"
                prop="deviceTypeName"
                :show-overflow-tooltip="true"
            />
            <el-table-column
                label="巡检日期"
                align="center"
                prop="startAndEndTime"
                :show-overflow-tooltip="true"
            />
            <el-table-column
                label="工单生成"
                align="center"
                prop="planOrder"
                :show-overflow-tooltip="true"
            >
              <template slot-scope="scope">
                <el-switch
                    v-model="scope.row.planOrder"
                    active-value="1"
                    inactive-value="0"
                    @change="handleStatusChange(scope.row)"
                ></el-switch>
              </template>
            </el-table-column>
            <el-table-column
                label="操作"
                align="center"
                fixed="right"
                width="190"
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
                <el-button
                    size="mini"
                    type="text"
                    v-if="scope.row.planOrder == '1'"
                    @click="update(scope.row)"
                    v-hasPermi="['estate:plan:add']"
                >
                  <el-link
                      icon="el-icon-edit"
                      type="success"
                      :underline="false"
                  >修改
                  </el-link
                  >
                </el-button
                >
                <el-button
                    size="mini"
                    type="text"
                    v-if="scope.row.planOrder == '1'"
                    @click="remove(scope.row)"
                    v-hasPermi="['estate:plan:remove']"
                >
                  <el-link
                      icon="el-icon-delete"
                      type="danger"
                      :underline="false"
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
        :title="title"
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
              <el-form-item label="计划名称: " prop="planName">
                <el-input
                    clearable
                    v-model="addForm.planName"
                    placeholder="请输入计划名称"
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
                      v-for="item in tourNameList"
                      :key="item.tourId"
                      :label="item.tourName"
                      :value="item.tourId"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <!-- ----------------------------------  -->
            <el-col>
              <el-form-item label="巡检模式: " prop="planModel">
                <template>
<!--                  <el-radio
                      @change="modelRadio"
                      v-model="addForm.planModel"
                      label="0"
                  >一日多检
                  </el-radio>-->
                  <el-radio
                      @change="modelRadios"
                      v-model="addForm.planModel"
                      label="1"
                  >多日一检
                  </el-radio
                  >
                </template>
              </el-form-item>
            </el-col>

<!--            <div v-if="deviceNoOne">
              <el-col>
                <el-form-item label="设备编号(多选): " prop="tourListValueTwos">
                  <el-select
                      v-model="addForm.tourListValueTwos"
                      multiple
                      clearable
                      filterable
                      placeholder="请选择设备编号(多选)"
                      value-key="indexCode"
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
            </div>-->

            <div>
              <el-col>
                <el-form-item label="设备编号: " prop="tourListValueTwos">
                  <el-select
                      clearable
                      multiple
                      filterable
                      v-model="addForm.tourListValueTwos"
                      placeholder="请选择设备编号(多选)"
                      value-key="indexCode"
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
            </div>

<!--            <div v-if="inspectOne">
              <el-col>
                <el-form-item label="巡查次数: " prop="planNum">
                  <el-input-number
                      clearable
                      :min="0"
                      v-model="addForm.planNum"
                      placeholder="请输入巡查次数"
                  ></el-input-number>
                  <b> 天/次</b>
                </el-form-item>
              </el-col>
            </div>-->

            <div>
              <el-col>
                <el-form-item label="巡查频率: " prop="planNum">
                  <el-input-number
                      clearable
                      :min="0"
                      v-model="addForm.planNum"
                      placeholder="请输入巡查频率"
                  ></el-input-number>
                  <b> 天</b>
                </el-form-item>
              </el-col>
            </div>

            <!-- ----------------------------------  -->

<!--            <div v-if="startTimeOne">
              <el-col>
                <el-form-item label="巡检开始时间: " prop="startTime">
                  <el-time-picker
                      v-model="addForm.startTime"
                      placeholder="选择开始时间"
                      format="HH:mm"
                      value-format="HH:mm"
                  >
                  </el-time-picker>
                </el-form-item>
              </el-col>
            </div>-->

            <div>
              <el-col>
                <el-form-item label="巡检开始日期: " prop="startTime">
                  <el-date-picker
                      v-model="addForm.startTime"
                      type="date"
                      placeholder="选择日期"
                      format="yyyy-MM-dd"
                      value-format="yyyy-MM-dd"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </div>

<!--            <div v-if="endTimeOne">
              <el-col>
                <el-form-item label="巡检结束时间: " prop="endTime">
                  <el-time-picker
                      v-model="addForm.endTime"
                      placeholder="选择结束时间"
                      format="HH:mm"
                      value-format="HH:mm"
                  >
                  </el-time-picker>
                </el-form-item>
              </el-col>
            </div>-->

            <div>
              <el-col>
                <el-form-item label="巡检结束日期: " prop="endTime">
                  <el-date-picker
                      v-model="addForm.endTime"
                      type="date"
                      placeholder="选择日期"
                      format="yyyy-MM-dd"
                      value-format="yyyy-MM-dd"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </div>
          </el-row>

          <el-row>
            <el-col :offset="3" :span="8"></el-col>
          </el-row>
          <el-row>
            <el-col :offset="10" :span="2">
              <div style="width: 280px">
                <el-button type="primary" @click="addSuccess()"
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

    <!-- 详情页面 -->
    <div v-show="showDetail">
      <el-form ref="form" :model="detailForm" label-width="180px">
        <el-row>
          <el-col :offset="23" :span="24">
            <el-button @click="cancel">关 闭</el-button>
          </el-col>
        </el-row>
        <!--  ----------------------------- 计划详情 --------------------------- -->
        <h5><b>计划详情</b></h5>
        <el-divider></el-divider>
        <el-row>
          <!--
            <el-col :offset="1" :span="5">
            <el-form-item label="计划编号: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.planId }}
              </div>
            </el-form-item>
          </el-col>
        -->
          <el-col :offset="1" :span="5">
            <el-form-item label="计划名称: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.planName }}
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
        <!-- <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="设备地址: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.addressName }}
              </div>
            </el-form-item>
          </el-col>
        </el-row> -->

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
                  prop="noName"
              />
              <el-table-column
                  label="设备类型号"
                  align="center"
                  :show-overflow-tooltip="true"
                  prop="typeName"
              />
              <el-table-column
                  label="设备名称"
                  align="center"
                  :show-overflow-tooltip="true"
                  prop="facilityName"
              />
              <el-table-column
                  label="巡更点地址"
                  align="center"
                  :show-overflow-tooltip="true"
                  prop="addressName"
              />
            </el-table>
          </el-col>
        </el-row>

        <!--  ----------------------------- 巡检设置 --------------------------- -->
        <h5><b>巡检设置</b></h5>
        <el-divider></el-divider>
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="巡检模式: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.planModelName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="巡检次数: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.planNum }}次/ 天
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="间隔时间: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.gaptimeName }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="计划开始时间: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.startTime }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="计划结束时间: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.endTime }}
              </div>
            </el-form-item>
          </el-col>

          <el-col :offset="1" :span="5">
            <el-form-item label="人员类别: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.staffTypeName }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>

        <!--  ----------------------------- 工单生成历史 --------------------------- -->
        <div>
          <h5><b>工单生成历史</b></h5>
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
                  label="工单编号"
                  align="center"
                  :show-overflow-tooltip="true"
                  prop="inspectNo"
              />
              <el-table-column
                  label="工单名称"
                  align="center"
                  :show-overflow-tooltip="true"
                  prop="inspectName"
              />
              <el-table-column
                  label="巡检类型"
                  align="center"
                  :show-overflow-tooltip="true"
                  prop="planModelName"
              />
              <el-table-column
                  label="分配方式"
                  align="center"
                  :show-overflow-tooltip="true"
                  prop="allotWayName"
              />
              <!-- <el-table-column
                label="主要负责人"
                align="center"
                :show-overflow-tooltip="true"
                prop="staffName"
              /> -->
              <el-table-column
                  label="巡检设备"
                  align="center"
                  :show-overflow-tooltip="true"
                  prop="facilityName"
              />
              <el-table-column
                  label="生成时间"
                  align="center"
                  :show-overflow-tooltip="true"
                  prop="createTime"
              />
            </el-table>
          </el-col>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {
  changeStatus,
  deatilListTwo,
  detail,
  detailList,
  getDeviceNoList,
  getTourNameList,
  listPage,
  removes,
  saveOrUpdate,
  updateDetail,
} from "@/api/estate/work/inspect/plan";
import {desensitization, equals} from "@/utils/common";

export default {
  data() {
    return {
      // 搜索
      search: "",
      deviceNoOne: false,
      deviceNoTwo: false,
      inspectOne: false,
      inspectTwo: false,
      startTimeOne: false,
      startTimeTwo: false,
      endTimeOne: false,
      endTimeTwo: false,
      // 新增时获取设备名称列表
      deviceNoList: [],
      // 新增时获取巡查类型
      tourNameList: [],
      // 详情表单
      detailForm: {},
      // 新增页面表单
      addForm: {
        planNum: undefined,
        communityId: undefined,
        tourId: undefined,
        planModel: "1",
        planName: "",
        startTime: "",
        endTime: "",
        tourListValueTwos: [],
      },
      // 小区ID
      communityId: undefined,
      // 小区ID 新增修改传参
      communityIds: null,
      // 详情内表格数据
      deptData: [],
      ownerListData: [],
      // 详情内 - 工单生成列表
      deatilListGenerate: [],
      // 机构列表
      // 机构下拉框
      deptValue: "",
      // 新增弹窗
      addOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        // 小区ID
        communityId: undefined,
        dataSource: "",
        allotWay: "",
        staffName: "",
        planOrder: "",
        planName: "",
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
        planModel: [
          {required: true, message: "巡检模式不能为空", trigger: "blur"},
        ],
        planName: [
          {required: true, message: "计划名称不能为空", trigger: "blur"},
        ],
        tourId: [
          {required: true, message: "巡检类型不能为空", trigger: "blur"},
        ],
        planNum: [
          {required: true, message: "巡查次数不能为空", trigger: "blur"},
        ],
        tourListValueTwos: [
          {required: true, message: "请选择设备编号", trigger: "blur"},
        ],
        startTime: [
          {
            required: true,
            message: "巡检开始时间不能为空",
            trigger: "blur",
          },
        ],
        endTime: [
          {
            required: true,
            message: "巡检结束时间不能为空",
            trigger: "blur",
          },
        ],
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
    // 巡检模式多选
    // validate(rules, value, callBack) {
    //  // console.log(value);
    //   if (this.deviceNoOne && this.inspectOne) {
    //     if (!value) {
    //       return callBack(new Error("请选择内容"));
    //     }
    //   } else {
    //     return callBack();
    //   }
    // },

    // 多选
    modelRadio() {
      this.deviceNoOne = true;
      this.deviceNoTwo = false;
      this.inspectOne = true;
      this.inspectTwo = false;
      this.startTimeOne = true;
      this.startTimeTwo = false;
      this.endTimeOne = true;
      this.endTimeTwo = false;
      this.addForm.planNum = undefined;
      this.addForm.startTime = "";
      this.addForm.endTime = "";
      this.addForm.tourListValueTwos = [];
    },

    // 巡检模式单选
    modelRadios() {
      this.deviceNoOne = false;
      this.deviceNoTwo = true;
      this.inspectOne = false;
      this.inspectTwo = true;
      this.startTimeOne = false;
      this.startTimeTwo = true;
      this.endTimeOne = false;
      this.endTimeTwo = true;
      this.addForm.planNum = undefined;
      this.addForm.startTime = "";
      this.addForm.endTime = "";
      this.addForm.tourListValueTwos = [];
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
      this.queryParams.communityId = this.communityId;
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
      this.ids = selection.map((item) => item.planId);
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
        planId: row.planId,
      };
      detail(params).then((response) => {
        this.detailForm = response.data;
      });
      detailList(params).then((response) => {
        this.ownerListData = response.data;
      });
      deatilListTwo(params).then((response) => {
        this.deatilListGenerate = response.data;
      });
    },

    /** 详情 */
    detail(row) {
      this.showDetail = true;
      this.homeShow = false;
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
        this.communityId = this.deptData[0].communityId;
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
      this.communityId = row.communityId;
      this.getBlackList();
    },

    //删除按钮
    handleDelete() {
      this.$modal
          .confirm("是否确认删除选中的数据项？")
          .then(() => {
            removes(this.ids).then(() => {
            });
          })
          .then(() => {
            this.getBlackList();
            this.$modal.msgSuccess("删除成功");
          })
          .catch(() => {
          });
    },

    // 打开新增弹窗
    addMethod() {
      this.title = '新增巡检计划';
      this.addOpen = true;
      // 默认巡检模式
      this.addForm.planModel = "1";
      // 多选编号
      this.deviceNoOne = true;
      this.deviceNoTwo = false;
      this.inspectOne = true;
      this.inspectTwo = false;
      this.startTimeOne = true;
      this.startTimeTwo = false;
      this.endTimeOne = true;
      this.endTimeTwo = false;
      delete this.addForm["planId"];
      this.beforeMethod();
    },

    // 新增修改弹框前搜索
    beforeMethod() {
      // 查找巡查类型
      getTourNameList({communityId: this.queryParams.communityId}).then((response) => {
        this.tourNameList = response.data;
      });
      // 查找设备编号
      getDeviceNoList(this.queryParams.communityId).then((response) => {
        this.deviceNoList = response.data;
      });
    },

    // 关闭新增弹窗
    addClose() {
      this.addOpen = false;
      this.getBlackList();
      this.addFormEmpty();
      this.$refs.addForm.clearValidate();
    },

    // 修改
    update(row) {
      this.title = '修改巡检计划';
      this.addOpen = true;
      this.beforeMethod();
      this.updateDetails(row.planId);
      this.addForm.planId = row.planId;
    },

    // 编辑页面详情展示
    updateDetails(planId) {
      let params = {
        planId: planId,
      };
      updateDetail(params).then((response) => {
        if (response.data.planModel === "1") {
          this.modelRadios();
        } else {
          this.modelRadio();
        }
        this.addForm.planNum = response.data.planNum;
        this.addForm.planModel = response.data.planModel;
        this.addForm.tourId = response.data.tourId;
        this.addForm.planName = response.data.planName;
        this.addForm.startTime = response.data.startTime;
        this.addForm.endTime = response.data.endTime;
        this.addForm.tourListValueTwos = response.data.tourListValueTwos;
      });
    },

    // 提交
    addSuccess() {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          this.$confirm("是否确定提交?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
              .then(() => {
                saveOrUpdate(this.addForm).then((response) => {
                  this.getBlackList();
                  this.addOpen = false;
                  this.addFormEmpty();
                  this.$message({
                    type: "success",
                    message: response.msg,
                  });
                });
              })
              .catch(() => {
                this.$message({
                  type: "info",
                  message: "已取消提交",
                });
              });
        }
      });
    },

    // 更改车位状态
    handleStatusChange(row) {
      let params = {
        planId: row.planId,
        planOrder: row.planOrder,
      };
      let text = row.planOrder === "1" ? "开启" : "关闭";
      this.$modal
          .confirm("确认要" + text + "计划 " + row.planName + " 吗？")
          .then(function () {
            changeStatus(params).then(() => {
            });
          })
          .then(() => {
            this.$modal.msgSuccess(text + "成功");
            this.getBlackList();
          })
          .catch(function () {
            row.planOrder = row.planOrder === "0" ? "1" : "0";
          });
    },

    // 清空addForm表单
    addFormEmpty() {
      this.resetForm("addForm");
      this.addForm = {
        planNum: undefined,
        tourId: undefined,
        planModel: "0",
        planName: "",
        startTime: "",
        endTime: "",
        tourListValueTwos: []
      };
      this.addForm.communityId = this.communityId;
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
      idss.push(row.planId);
      this.$modal
          .confirm("是否确认删除选中的数据项？")
          .then(() => {
            removes(idss).then(() => {
            });
          })
          .then(() => {
            this.getBlackList();
            this.$modal.msgSuccess("删除成功");
          })
          .catch(() => {
          });
      this.idss = [];
    },
  },
};
</script>
<style scoped>
</style>

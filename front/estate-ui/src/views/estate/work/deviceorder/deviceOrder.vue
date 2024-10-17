<template>
  <div class="app-container">
    <!--部门数据-->
    <div v-if="deviceView">
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
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
          highlight-current-row
          :data="deptData"
          :show-header="false"
          @cell-click="listClick"
        >
          <el-table-column prop="communityName"/>
        </el-table>
      </el-col>
      <el-col :span="20" :xs="24">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                 label-width="68px">

          <el-form-item label="工单名称" prop="orderName">
            <el-input
              v-model="queryParams.orderName"
              placeholder="请输入工单名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>

<!--          <el-form-item label="户主姓名" prop="executeId">
            <el-input
              v-model="queryParams.executeId"
              placeholder="请输入户主姓名"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>-->

          <el-form-item label="设备类型" prop="deviceType">
            <el-select v-model="queryParams.deviceType" placeholder="请选择设备类型" clearable>
              <el-option
                v-for="dict in dict.type.device_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="状态" prop="executeStatus" label-width="10">
            <el-select v-model="queryParams.executeStatus" placeholder="请选择状态" clearable>
              <el-option
                v-for="dict in dict.type.execute_status"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
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
              v-hasPermi="['work:deviceOrder:add']"
            >新增
            </el-button>
          </el-col>

          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['work:deviceOrder:remove']"
            >删除
            </el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="deviceOrderList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="工单编号" align="center" prop="orderNo" show-overflow-tooltip/>
          <el-table-column label="工单名称" align="center" prop="orderName" show-overflow-tooltip/>
          <el-table-column label="设备类型" align="center" prop="deviceType">
            <template slot-scope="scope">
              <span>{{ parseDeviceType(scope.row.deviceType) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="工单分配方式" align="center" prop="allotWay">
            <template slot-scope="scope">
              <span>{{ parseAllotWay(scope.row.allotWay) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="主要负责人" align="center" prop="staffName">
            <template slot-scope="scope">
              <span>{{ scope.row.staffName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="分配状态" align="center">
            <template slot-scope="scope">
              <span>{{ parseAllotStatus(scope.row.allotStatus) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="接单状态" align="center" >
            <template slot-scope="scope">
              <span>{{ parseAcceptStatus(scope.row.acceptStatus) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="执行状态" align="center">
            <template slot-scope="scope">
              <span>{{ parseExecuteStatus(scope.row.executeStatus) }}</span>
            </template>
          </el-table-column>
<!--          <el-table-column label="执行状态" align="center" width="180">
            <template slot-scope="scope">
              <span>{{ parseStatus(scope.row) }}</span>
            </template>
          </el-table-column>-->

          <el-table-column label="创建人" align="center" prop="createBy">
            <template slot-scope="scope">
              <span>{{ scope.row.createBy }}</span>
            </template>
          </el-table-column>
          <el-table-column label="创建时间" align="center" prop="createTime" width="180">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime, '{y}-{m}-{d}') }}</span>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="handleSee(scope.row)"
              >查看
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                v-if="scope.row.acceptStatus == 0"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['work:deviceOrder:edit']"
              >修改
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit-outline"
                @click="handleDistribution(scope.row)"
                v-if="scope.row.allotStatus == 0"
                v-hasPermi="['work:deviceOrder:edit']"
              >分配
              </el-button>

            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />

        <!-- 添加或修改设备维修工单对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
          <el-form ref="form" :model="form" :rules="rules" label-width="120px">
            <el-form-item label="工单名称" prop="orderName">
              <el-input v-model="form.orderName" placeholder="请输入工单名称" maxlength="64"/>
            </el-form-item>

            <el-form-item label="设备类型" prop="deviceType">
              <el-select v-model="form.deviceType" @change="deviceCodeOptionsChange(form.deviceType)"
                         placeholder="请选择设备类型">
                <el-option
                  v-for="dict in dict.type.device_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>

            <el-form-item label="设备名称" prop="indexCode">
              <el-select v-model="form.indexCode" @change="" placeholder="请输入设备名称">
                <el-option
                  v-for="dict in deviceCodeOptions"
                  :key="dict.indexCode"
                  :label="dict.deviceName"
                  :value="dict.indexCode"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="计划开始时间" prop="planStart">
              <el-date-picker clearable
                              v-model="form.planStart"
                              type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              placeholder="请选择计划开始时间"
                              :picker-options="pickerOptions">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="计划结束时间" prop="planEnd">
              <el-date-picker clearable
                              v-model="form.planEnd"
                              type="datetime"
                              value-format="yyyy-MM-dd HH:mm:ss"
                              placeholder="请选择计划结束时间"
                              :picker-options="pickerOptions">
              </el-date-picker>
            </el-form-item>

<!--            <el-form-item label="备注" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入内容" maxlength="1000"/>
            </el-form-item>-->

          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>
        <!-- 分配设备维修工单对话框 -->
        <el-dialog :title="titleDistribution" :visible.sync="openDistribution" width="500px" append-to-body>
          <el-form ref="formDistribution" :model="formDistribution" :rules="rulesDistribution" label-width="100px">
            <el-form-item label="主要负责人" prop="staffId">
              <el-select v-model="formDistribution.staffId"
                         placeholder="请选择主要负责人">
                <el-option
                  v-for="dict in leisureBusyStaffOptions"
                  :key="dict.staffId"
                  :label="dict.staffName"
                  :value="dict.staffId"
                />
              </el-select>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitFormDistribution">确 定</el-button>
            <el-button @click="cancelDistribution">取 消</el-button>
          </div>
        </el-dialog>
      </el-col>
    </div>
    <!-- 详情页面 -->
    <div v-if="!deviceView">
      <el-form ref="deviceView" :model="deviceViewForm" label-width="180px">
        <el-row>
          <el-col :offset="23" :span="24">
            <el-button @click="cancelDeviceView">关闭</el-button>
          </el-col>
        </el-row>
        <!--  ----------------------------- 工单详情 --------------------------- -->
        <h4 class="form-header">工单详情</h4>
        <el-divider></el-divider>
        <!-- 第一行 -->
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="工单编号: ">
              <div style="width: 280px; overflow: hidden">
                {{ deviceViewForm.orderNo }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="工单名称: ">
              <div style="width: 280px; overflow: hidden">
                {{ deviceViewForm.orderName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="工单类型: ">
              <div style="width: 280px; overflow: hidden">
                {{ "设备维修工单" }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第二行 -->
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="维修设备类型: ">
              <div style="width: 280px; overflow: hidden">
                {{ parseDeviceType(deviceViewForm.deviceType) }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="设备名称: ">
              <div style="width: 280px; overflow: hidden">
                {{ parseDeviceName(deviceViewForm.indexCode) }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="主要负责人: ">
              <div style="width: 280px; overflow: hidden">
                {{ deviceViewForm.staffName }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第三行 -->
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="工单计划开始时间: ">
              <div style="width: 280px; overflow: hidden">
                {{ parseTime(deviceViewForm.planStart, '{y}-{m}-{d} {h}:{i}:{s}') }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="工单计划结束时间: ">
              <div style="width: 280px; overflow: hidden">
                {{ parseTime(deviceViewForm.planEnd, '{y}-{m}-{d} {h}:{i}:{s}') }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="完成时间: ">
              <div style="width: 280px; overflow: hidden">
                {{ deviceViewForm.finishTime }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第四行 -->
<!--
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="异常状态: ">
              <div style="width: 280px; overflow: hidden">
                {{ parseAbnormalState(deviceViewForm.abnormal) }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
-->
        <!-- 第五行 -->
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="备注: ">
              <div style="width: 280px; overflow: hidden">
                {{ deviceViewForm.remark }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>

        <!--  ----------------------------- 工单协助人信息 --------------------------- -->
        <orderAssist :father="queryRecord"></orderAssist>

        <!--  ----------------------------- 执行详情 --------------------------- -->
        <orderRecord :father="queryRecord"></orderRecord>


      </el-form>
    </div>
  </div>
</template>

<script>
import {
  listDeviceOrder,
  getDeviceOrder,
  delDeviceOrder,
  addDeviceOrder,
  updateDeviceOrder, listDeviceOrderCode, getDeviceOrderById
} from "@/api/estate/work/deviceorder/deviceOrder";
import {getCurrentList} from "@/api/estate/community";
import {listLeisureBusyStaffs} from "@/api/estate/manage/staff/staff";
import orderRecord from '@/components/work/orderRecord';
import orderAssist from '@/components/work/orderAssist';
import {desensitizationName, equals} from "@/utils/common";

export default {
  name: "DeviceOrder",
  components: {
    orderRecord, orderAssist
  },
  dicts: ['device_type', 'execute_status', 'accept_status', 'allot_status', 'allot_way', 'work_post'],
  data() {
    return {
      openView: false,
      deptData: [],
      addForm: {},
      //详情查看
      deviceViewForm: {},
      //显示隐藏
      deviceView: true,
      //设备名称
      deviceCodeOptions: [],
      // 工单分配
      leisureBusyStaffOptions: [],
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
      // 设备维修工单表格数据
      deviceOrderList: [],
      // 弹出层标题
      title: "",
      //分配弹出层标题
      titleDistribution: "",
      //执行情况详情信息
      detailListThree: {
        detailListThreeList: []
      },
      // 是否显示弹出层
      open: false,
      //是否显示分配弹出层
      openDistribution: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: null,
        orderNo: null,
        orderName: null,
        deviceType: null,
        indexCode: null,
        planStart: null,
        planEnd: null,
        allotWay: null,
        staffId: null,
        assistNum: null,
        transferStatus: null,
        allotStatus: null,
        acceptStatus: null,
        executeStatus: null,
        assessResult: null,
        executeId: null,
        executeTime: null,
        finishTime: null,
        imageUrl: null,
      },
      // 表单参数
      form: {},
      //
      queryRecord: {
        orderType: '6',
        orderId: 0
      },
      //分配表单参数
      formDistribution: {},
      //分配表单校验
      rulesDistribution: {
        staffId: [
          {required: true, message: "主要负责人不能为空", trigger: "blur"}
        ],
      },
      // 表单校验
      rules: {
        orderNo: [
          {required: true, message: "工单编号不能为空", trigger: "blur"}
        ],
        orderName: [
          {required: true, message: "工单名称不能为空", trigger: "blur"}
        ],
        deviceType: [
          {required: true, message: "设备类型不能为空", trigger: "change"}
        ],
        indexCode: [
          {required: true, message: "设备唯一标识不能为空", trigger: "blur"}
        ],
        planStart: [
          {required: true, message: "计划开始时间不能为空", trigger: "blur"}
        ],
        planEnd: [
          {required: true, message: "计划结束时间不能为空", trigger: "blur"}
        ],
        remark: [
          {required: true, message: "备注不能为空", trigger: "blur"}
        ],
      },
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        }
      },
    };
  },
  created() {
    // 机构下拉框
    this.getTypeList();
  },
  methods: {
    // 机构下拉框
    getTypeList() {
      getCurrentList().then((response) => {
        this.deptData = response.data;
        this.queryParams.communityId = this.deptData[0].communityId;
        this.$refs.singleTable.setCurrentRow(this.deptData[0]);
        this.addForm.communityId = this.deptData[0].communityId;
        this.addForm.communityName = this.deptData[0].communityName;
        this.communityIds = this.deptData[0].communityId;
        this.getList();
      });
    },
    // 单元格点击时查列表
    listClick(row) {
      this.queryParams.communityId = row.communityId;
      // // 点击单元格时赋值所属小区
      this.addForm.communityId = row.communityId;
      this.addForm.communityName = row.communityName;
      this.communityIds = row.communityId;
      this.getList();
    },
    //姓名脱敏
    handleName(name) {
      return desensitizationName(name)
    },

    /** 查询设备维修工单列表 */
    getList() {
      this.loading = true;
      listDeviceOrder(this.queryParams).then(response => {
        console.log(response);
        this.deviceOrderList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 取消按钮
    cancelDeviceView() {
      this.deviceView = true;
    },
    // 取消按钮
    cancelDistribution() {
      this.openDistribution = false;
    },
    // 表单重置
    reset() {
      this.form = {
        orderId: null,
        communityId: null,
        orderNo: null,
        orderName: null,
        deviceType: null,
        indexCode: null,
        planStart: null,
        planEnd: null,
        allotWay: null,
        staffId: null,
        assistNum: null,
        transferStatus: "0",
        allotStatus: "0",
        acceptStatus: "0",
        executeStatus: "0",
        assessResult: null,
        executeId: null,
        executeTime: null,
        finishTime: null,
        imageUrl: null,
        remark: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null
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
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加设备维修工单";
    },
    deviceCodeOptionsChange(deviceType) {

      this.deviceCodeOptions.splice(0, this.deviceCodeOptions.length);
      this.form.indexCode = "";
      let query = {
        "deviceType": deviceType,
        "communityId": this.queryParams.communityId
      };
      listDeviceOrderCode(query).then(response => {
        this.deviceCodeOptions = response.data;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const orderId = row.orderId || this.ids
      getDeviceOrder(orderId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改设备维修工单";
      });
      this.deviceCodeOptionsChange(row.deviceType);
    },
    /** 分配按钮操作 */
    handleDistribution(row) {
      this.openDistribution = true;
      this.titleDistribution = "分配设备维修工单";
      let query = {
        "allotType": "6",
        "communityId": this.queryParams.communityId
      };
      this.formDistribution.orderId = row.orderId;
      listLeisureBusyStaffs(query).then(res => {
        this.leisureBusyStaffOptions = res.data;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.form.communityId = this.queryParams.communityId;
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.orderId != null) {
            updateDeviceOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDeviceOrder(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 提交按钮 */
    submitFormDistribution() {
      this.$refs["formDistribution"].validate(valid => {
        if (valid) {
          if (this.formDistribution.orderId != null) {
            updateDeviceOrder(this.formDistribution).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.openDistribution = false;
              this.getList();
            });
          } else {
          }
        }
      });
    },
    //查询详情
    handleSee(row) {
      this.deviceView = false;
      getDeviceOrderById(row.orderId).then(response => {
        this.deviceViewForm = response.data;
      });
      // 查询工单协助人信息
      this.queryRecord.orderId = row.orderId;
      let query = {
        "deviceType": row.deviceType,
        "communityId": this.queryParams.communityId
      };
      listDeviceOrderCode(query).then(response => {
        this.deviceCodeOptions = response.data;
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const orderIds = row.orderId || this.ids;
      this.$modal.confirm('是否确认删除设备维修工单编号为"' + orderIds + '"的数据项？').then(function () {
        return delDeviceOrder(orderIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },

    //设备类型
    parseDeviceType(deviceType) {
      return this.selectDictLabel(this.dict.type.device_type, deviceType);
    },
    //设备名称
    parseDeviceName(orderNo) {
      let c = this.deviceCodeOptions.filter(item => item.indexCode == orderNo)[0];
      if (c) {
        return c.deviceName;
      }
      return "";
    },
    // 异常状态
    parseAbnormalState(state) {
      console.log(state)
      if (equals('0', state)) {
        return "正常";
      } else if (equals('1', state)){
        return "异常"
      }
      return "-"
    },
    //工单分配方式
    parseAllotWay(allotWay) {
      return this.selectDictLabel(this.dict.type.allot_way, allotWay);
    },
    //分配状态
    parseAllotStatus(allotStatus){
      return this.selectDictLabel(this.dict.type.allot_status, allotStatus);
    },
    //接单状态
    parseAcceptStatus(acceptStatus){
      return this.selectDictLabel(this.dict.type.accept_status, acceptStatus);
    },
    //执行状态
    parseExecuteStatus(executeStatus){
      return this.selectDictLabel(this.dict.type.execute_status, executeStatus);
    },
   /* //状态
    parseStatus(row) {
      let aStatus = this.selectDictLabel(this.dict.type.allot_status, row.allotStatus);
      if (aStatus) {
        let bStatus = this.selectDictLabel(this.dict.type.accept_status, row.acceptStatus);
        if (bStatus) {
          let cStatus = this.selectDictLabel(this.dict.type.execute_status, row.executeStatus);
          if (cStatus) {
            return aStatus + "-" + bStatus + "-" + cStatus;
          }
          return aStatus + "-" + bStatus;
        }
        return aStatus;
      }
      return "";
    },*/
  }
};
</script>

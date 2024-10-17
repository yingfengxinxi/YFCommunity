<template>
  <div class="app-container">
    <!--部门数据-->
    <div v-if="deviceView">
      <!-- 树结构     -->
      <el-col :span="3" :xs="24">
        <div class="head-container">
          <el-input v-model="filterText" placeholder="请输入小区名称" clearable size="small"
                    prefix-icon="el-icon-search" style="margin-bottom: 20px"/>
        </div>
        <div >
          <el-tree
            :data="deptData"
            :props="defaultProps"
            :expand-on-click-node="false"
            :filter-node-method="filterNode"
            ref="tree"
            highlight-current
            node-key="communityId"
            @node-click="handleNodeClick"
          >
            <!-- 插入楼栋图标 -->
            <span slot-scope="{ node }">
	   		          <i class="el-icon-office-building" />
	   		          <span class="content"  style="padding-left:5px;">{{node.label}}</span>
		          </span>
          </el-tree>
        </div>
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

          <el-form-item label="户主姓名" prop="ownerName">
            <el-input
              v-model="queryParams.ownerName"
              placeholder="请输入户主姓名"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>

          <el-form-item label="维修类型" prop="repairId">
            <el-select v-model="queryParams.repairId" placeholder="维修类型" clearable>
              <el-option
                v-for="item in homeRepairs"
                :key="item.repairId"
                :label="item.repairName"
                :value="item.repairId"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="执行状态" prop="executeStatus">
            <el-select v-model="queryParams.executeStatus" placeholder="请选择执行状态" clearable>
              <el-option
                v-for="dict in executeStatusOptions"
                :key="dict.dictValue"
                :label="dict.dictLabel"
                :value="dict.dictValue"
              />
            </el-select>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="deviceOrderList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="工单编号" align="center" prop="orderNo" show-overflow-tooltip/>
          <el-table-column label="工单名称" align="center" prop="orderName" show-overflow-tooltip/>
          <el-table-column label="维修类型" align="center" prop="repairName" show-overflow-tooltip/>
          <el-table-column label="提交人姓名" align="center" prop="ownerName"/>
          <el-table-column label="手机号" align="center" prop="ownerPhone" show-overflow-tooltip/>
          <el-table-column label="工单分配方式" align="center" prop="allotWay">
            <template slot-scope="scope">
              <span>{{ parseAllotWay(scope.row.allotWay) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="接单人员" align="center">
            <template slot-scope="scope">
              <span>{{ scope.row.staffName == null ? '-' : scope.row.staffName }}</span>
            </template>
          </el-table-column>
          <el-table-column label="分配状态" align="center" prop="allotStatus" :formatter="allotStatusFormat"/>
          <el-table-column label="接单状态" align="center" prop="acceptStatus" :formatter="acceptStatusFormat"/>
          <el-table-column label="执行状态" align="center" prop="executeStatus" :formatter="executeStatusFormat"/>
          <el-table-column label="支付状态" align="center" prop="appointStatus" :formatter="appointStatusFormat"/>
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
                icon="el-icon-edit-outline"
                @click="handleDistribution(scope.row)"
                v-hasPermi="['work:homeOrder:distribution']"
                v-show="scope.row.acceptStatus === '0'"
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
        <!-- 分配设备维修工单对话框 -->
        <el-dialog :title="titleDistribution" :visible.sync="openDistribution" width="600px" append-to-body>
          <el-form ref="formDistribution" :model="formDistribution" :rules="rules" label-width="160px">
            <el-form-item label="分配员工" prop="staffId">
              <el-select v-model="formDistribution.staffId"
                         placeholder="请选择要分配的员工">
                <el-option
                  v-for="item in leisureBusyStaffOptions"
                  :key="item.staffId"
                  :label="item.staffName"
                  :value="item.staffId"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="计划开始与结束时间">
              <el-date-picker
                v-model="dateRange"
                value-format="yyyy-MM-dd HH:mm:ss"
                type="datetimerange"
                range-separator="-"
                start-placeholder="开始日期"
                end-placeholder="结束日期">
              </el-date-picker>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancelDistribution">关 闭</el-button>
          </div>
        </el-dialog>
      </el-col>
    </div>
    <!-- 详情页面 -->
    <div v-show="!deviceView">
      <el-form ref="deviceView" :model="deviceViewForm" label-width="180px">
        <!--  ----------------------------- 工单详情 --------------------------- -->
        <h5><b>工单详情</b></h5>
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
                {{ "居家维修工单" }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第二行 -->
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="报事类型: ">
              <div style="width: 280px; overflow: hidden">
                {{ deviceViewForm.repairName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="报事地址: ">
              <div style="width: 280px; overflow: hidden">
                {{ deviceViewForm.address }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1">
            <el-form-item label="上传图片: ">
              <div class="images">
                <div v-for="(item, index) in deviceViewForm.photos" :key="index" class="image-middle">
                  <el-popover placement="top-start" trigger="focus">
                    <img :src="item" slot="reference" class="image"/>
                    <div>
                      <img :src="item" class="imagePreview"/>
                    </div>
                  </el-popover>
                </div>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第三行 -->
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="计划工单开始时间: ">
              <div style="width: 280px; overflow: hidden">
                {{ deviceViewForm.planStart == null || deviceViewForm.planStart == '' ? "-" : deviceViewForm.planStart }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="计划工单结束时间: ">
              <div style="width: 280px; overflow: hidden">
                {{ deviceViewForm.planEnd == null || deviceViewForm.planEnd == '' ? "-" : deviceViewForm.planEnd }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="完成时间: ">
              <div style="width: 280px; overflow: hidden">
                {{ deviceViewForm.finishTime == null || deviceViewForm.finishTime == '' ? "-" : deviceViewForm.finishTime }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第四行 -->
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="业主评价: ">
              <div style="width: 280px; overflow: hidden">
                {{ deviceViewForm.complaintType }}
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
        <orderAssist :father="queryRecord" v-if="isShow"/>
        <!--  ----------------------------- 执行详情 --------------------------- -->
        <orderRecord :father="queryRecord" v-if="isShow"/>
        <el-row>
          <el-col :offset="23" :span="24">
            <el-button @click="cancelDeviceView">关闭</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {listLeisureBusyStaffs} from "@/api/estate/manage/staff/staff";
import {listHomeOrder, getHomeRepair, getInfo, distribution} from "@/api/estate/work/homeorder/homeOrder";
import {desensitization, desensitizationName, equals} from "@/utils/common";
import orderRecord from "@/components/work/orderRecord";
import orderAssist from "@/components/work/orderAssist";

export default {
  name: "homeOrder",
  components: {
    orderRecord, orderAssist
  },
  data() {
    return {
      dateRange:[],
      defaultProps: {
        children: "communityId",
        label: "communityName"
      },
      // 小区筛选条件
      filterText: '',
      // 居家维修类型
      homeRepairs: [],
      openView: false,
      deptData: [],
      addForm: {},
      //详情查看
      deviceViewForm: {},
      //显示隐藏
      deviceView: true,
      //设备类型
      deviceTypeOptions: [],
      //设备名称
      deviceCodeOptions: [],
      //工单执行状态
      executeStatusOptions: [],
      //工单分配方式
      allotWayOptions: [],
      //工单接单状态
      acceptStatusOptions: [],
      //工单分配状态
      allotStatusOptions: [],
      //
      leisureBusyStaffOptions: [],
      // 工单执行人信息
      deatilListGenerate: [],
      // 执行详情
      deatilListThreeList: [],
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
      // 查看
      isShow: false,
      // 是否显示弹出层
      open: false,
      //是否显示分配弹出层
      openDistribution: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: null,
        orderName: null,
        ownerName: null,
        repairId: null,
        executeStatus: null,
      },
      // 表单参数
      form: {},
      // 协助人查询参数对象
      queryRecord: {
        orderType: '5',
        orderId: 0
      },
      //分配表单参数
      formDistribution: {},
      // 分配表单校验
      rules: {
        staffId: [
          {required: true, message: "请选择分配人", trigger: "change"}
        ],
        dateRange: [
          {required: true, message: "请选择计划时间", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    // 机构下拉框
    this.getTypeList();
    //工单执行状态
    this.getDicts("execute_status").then(response => {
      this.executeStatusOptions = response.data;
    });
    //工单接单状态
    this.getDicts("accept_status").then(response => {
      this.acceptStatusOptions = response.data;
    });
    //工单分配状态
    this.getDicts("allot_status").then(response => {
      this.allotStatusOptions = response.data;
    });
    //工单分配方式
    this.getDicts("allot_way").then(response => {
      this.allotWayOptions = response.data;
    });
    // 获取居家维修类型
    this.getHomeRepair();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.communityId = data.communityId;
      this.communityId = data.communityId;
      this.getList();
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 机构下拉框
    getTypeList() {
      getCurrentList().then((response) => {
        this.deptData = response.data;
        this.queryParams.communityId = this.deptData[0].communityId;
        this.communityIds = this.deptData[0].communityId;
        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.deptData[0].communityId);
        });
        this.getList();
      });
    },
    // 获取居家维修类型
    getHomeRepair() {
      getHomeRepair().then(response => {
        this.homeRepairs = response.data;
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

    /** 查询设备维修工单列表 */
    getList() {
      this.loading = true;
      listHomeOrder(this.queryParams).then(response => {
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
      this.deviceView = true
      this.deviceViewForm = {};
      this.queryRecord = {orderType: '5', orderId: 0};
      this.isShow = false;
    },
    // 取消按钮
    cancelDistribution() {
      this.formDistribution = {};
      this.dateRange = [];
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
      let query = {
        "deviceType": deviceType,
        "communityId": this.queryParams.communityId
      };
      listDeviceOrderCode(query).then(response => {
        this.deviceCodeOptions = response.data;
      });
    },
    /** 分配按钮操作 */
    handleDistribution(row) {
      this.openDistribution = true;
      this.titleDistribution = "分配工单";
      let query = {
        "allotType": "5",
        "communityId": this.queryParams.communityId
      };
      this.formDistribution.orderId = row.orderId;
      listLeisureBusyStaffs(query).then(res => {
        this.leisureBusyStaffOptions = res.data;
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["formDistribution"].validate(valid => {
        if (this.dateRange.length == 0){
          this.$notify({
            title: '警告',
            message: '请选择计划时间',
            type: 'warning'
          });
          return;
        }
        if (valid) {
          if (this.formDistribution.orderId != null) {
            distribution(this.addDateRange(this.formDistribution, this.dateRange)).then(response => {
              this.$modal.msgSuccess("分配成功");
              this.cancelDistribution();
              this.getList();
            });
          }
        }
      });
    },
    handleSee(row) {
      this.deviceView = false;
      this.isShow = true;
      // 查询工单协助人信息
      this.queryRecord.orderId = row.orderId;
      getInfo(row.orderId).then(res => {
        this.deviceViewForm = res.data;
      });
    },
    //工单分配方式
    parseAllotWay(allotWay) {
      let c = this.allotWayOptions.filter(item => item.dictValue == allotWay)[0];
      if (c) {
        return c.dictLabel;
      }
      return "";
    },
    // 分配状态
    allotStatusFormat(row) {
      if (equals(row.allotStatus, '0')) {
        return '待分配';
      }
      return '已分配';
    },
    // 接单状态
    acceptStatusFormat(row) {
      if (equals(row.acceptStatus, '0')) {
        return '待接单';
      }
      return '已接单';
    },
    // 执行状态
    executeStatusFormat(row) {
      if (equals(row.executeStatus, '0')) {
        return '待执行';
      } else if (equals(row.executeStatus, '1')) {
        return '执行中';
      } else if (equals(row.executeStatus, '2')) {
        return '已完成';
      } else if (equals(row.executeStatus, '3')) {
        return '超时完成';
      }
      return '超时未完成';
    },
    // 支付状态
    appointStatusFormat(row) {
      if (equals(row.appointStatus, '0')) {
        return '未支付';
      }
      return '已支付';
    },
    ownerNameFormat(row){
      return desensitizationName(row.ownerName);
    },
    staffNameFormat(row){
      return desensitizationName(row.staffName);
    },
    ownerPhoneFormat(row){
      return desensitization(row.ownerPhone, 3, 7)
    }
  }
};
</script>
<style scoped>
/* 图片总布局，样式 */
.images {
  display: flex;
  margin-top: 20px;
  margin-left: 20px;
  margin-right: 20px;
  flex-wrap: wrap;
}

/* 图片之间 */
.image-middle {
  margin-right: 15px;
  margin-bottom: 15px;
}

/* 单张图片样式 */
.image {
  width: 150px;
  height: 150px;
}

/*设置高亮颜色*/
/deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #b3f1b6 !important;
}
.content {
  font-size: 14px;
}
.thumbnail {
  height:100px;
  width: 200px
}
</style>

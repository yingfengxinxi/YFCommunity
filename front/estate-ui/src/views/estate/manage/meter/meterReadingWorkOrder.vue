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
              v-model="filterText">
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
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="82px">
            
            <el-form-item label="抄表状态" prop="meterReadingStatus">
              <el-select
                v-model="queryParams.meterReadingStatus"
                placeholder="请选择抄表状态"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="item in dict.type.meter_reading_status"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="能耗类型" prop="energyConsumptionType">
              <el-select
                v-model="queryParams.energyConsumptionType"
                placeholder="请选择能耗类型"
                clearable
                style="width: 160px"
                @change="getMeterReadingTypeOptions(queryParams.energyConsumptionType)"
              >
                <el-option
                  v-for="item in dict.type.energy_consumption_type"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            
            <el-form-item label="设备类型" prop="meterReadingType">
              <el-select
                v-model="queryParams.meterReadingType"
                placeholder="请选择设备类型"
                clearable
                style="width: 160px"
              >
                <el-option
                  v-for="item in meterReadingTypeOptions"
                  :key="item.dictValue"
                  :label="item.dictLabel"
                  :value="item.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="操作时间" prop="dateRange">
                <el-date-picker
                  v-model="dateRange"
                  size="small"
                  style="width: 240px"
                  value-format="yyyy-MM-dd"
                  type="daterange"
                  range-separator="-"
                  start-placeholder="开始时间"
                  end-placeholder="结束时间"
                ></el-date-picker>
            </el-form-item>
            
            <el-form-item label="抄表人" prop="name" >
              <el-select
                v-model="queryParams.name"
                placeholder="请选择抄表人"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="item in executorByOptions"
                  :key="item.userId+''"
                  :label="item.staffName"
                  :value="item.userId+''"
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>
          <!-- <el-col :span="1.5">

            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button
                  type="warning"
                  plain
                  icon="el-icon-download"
                  size="mini"
                  @click="handleExport"
                >导出</el-button>
              </el-col>
            </el-row>
        
      </el-col> -->
          <right-toolbar :showSearch.sync="showSearch" @queryTable="list"></right-toolbar>

          <el-table v-loading="loading" :data="list">
            <el-table-column label="工单编号" align="center" key="no" prop="no"/>
            <el-table-column label="能耗类型" align="center" prop="energyConsumptionType" :formatter="ect"/>
            <el-table-column label="设备类型" align="center" prop="meterReadingType" :formatter="mrt"/>
            <el-table-column label="抄表时间" align="center"  prop="meterReadingTime"/>
            <el-table-column label="工单状态" align="center"  prop="meterReadingStatus" :formatter="mrs"/>
            <el-table-column label="抄表人员" align="center" key="name" prop="name">
              <template slot-scope="scope">
                  {{ scope.row.name==null?'--':scope.row.name }}
              </template>
            </el-table-column>
            <el-table-column label="操作时间" align="center" key="updateTime" prop="updateTime">
              <template slot-scope="scope">
                  {{ scope.row.meterReadingStatus=='1'?scope.row.updateTime:""}}
              </template>
            </el-table-column>
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
                  @click="handleDetail(scope.row)"
                >查看</el-button>
                
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
        </el-col>
      </el-row>
    </div>

    <!--  详情页  -->
    <div v-show="detailShow">
      <el-form ref="detailForm" :model="detailForm" label-width="140px">
        <h4 class="form-header">工单信息</h4>
          <el-row>
            <el-col :span="7" :offset="1">
              <el-form-item label="工单编码:">{{ detailForm.no }}</el-form-item>
            </el-col>
            <el-col :span="7" :offset="1">
              <el-form-item label="所属小区:">{{getCurrentList(detailForm.communityId) }}</el-form-item>
            </el-col>
            <el-col :span="7" :offset="1">
              <el-form-item label="工单状态:">{{mrs(detailForm) }}</el-form-item>
            </el-col>
            <el-col :span="7" :offset="1">
              <el-form-item label="工单创建时间:">{{detailForm.createTime}}</el-form-item>
            </el-col>
            <el-col :span="7" :offset="1">
              <el-form-item label="工单操作时间:">{{detailForm.updateTime}}</el-form-item>
            </el-col>
            <el-col :span="7" :offset="1">
              <el-form-item label="工单截止时间:">{{detailForm.orderExpirationTime }}</el-form-item>
            </el-col>
          </el-row>
          <h4 class="form-header">抄表人员信息</h4>
        <el-row>
          <el-col :span="7" :offset="1">
            <el-form-item label="姓名:">{{ detailForm.name==null?"--":detailForm.name }}</el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="联系方式:">{{ detailForm.phone==null?"--":detailForm.phone }}</el-form-item>
          </el-col>
        </el-row>
        <h4 class="form-header">抄表详情</h4>
        <el-row>
          <el-col :span="7" :offset="1">
            <el-form-item label="能耗类型:">{{ect(detailForm)}}</el-form-item>
          </el-col>
          <el-col :span="7" :offset="1">
            <el-form-item label="设备类型:">{{ mrt(detailForm)}}</el-form-item>
          </el-col>
          <el-col :span="7" :offset="1">
            <el-form-item label="抄表时间:">{{ detailForm.meterReadingTime }}</el-form-item>
          </el-col>
        </el-row>
        <div v-for="(item,index) in recordWorkOrderList" :key="index">
          <el-row>
            <el-col :span="7" :offset="1">
                <el-form-item label="设备编号:">{{item.equipmentNo}}</el-form-item>
            </el-col>
            <el-col :span="7" :offset="1">
                <el-form-item label="消耗总量:">{{item.data}}</el-form-item>
            </el-col>
          </el-row>
        </div>
        
        
        <div style="float: right">
          <el-button @click="canceldetail">关 闭</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import {
  listMeterReading,
  getMeterReadingDetail
} from "@/api/estate/manage/meter/meterReadingWorkOrder";
import { getCurrentList } from "@/api/estate/community";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getCommunityIdStaffList } from "@/api/estate/manage/staff/staff";
export default {
  name: "meterReadingWorkOrder",
  components: { Treeselect },
  dicts: ['meter_reading_type','energy_consumption_type','meter_reading_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      list:[],
      // 选中数组
      ids: [],
      active:1,
      // 非单个禁用
      single: true,
      patrolContent:[],
      // 非多个禁用
      multiple: true,
      recordWorkOrderList:[],
      //主页面展示
      openView: true,
      meterReadingStatusOptions: [],
      facilitieEquipmentTypeOptions:[],
      cleaningStatusList1: [],
      cleaningStatusList2:[],
      dateRange: [],
      executorByOptions: [],
      //详情页是否展示
      detailShow: false,
      //详情表单数据
      detailForm: {},
      violationList: [],
      dateRange:[],
      // 树筛选文本
      filterText:'',
      // 显示搜索条件
      showSearch: true,
      meterReadingTypeOptions:[],
      // 总条数
      total: 0,
      //装修工信息
      worker: {},
      //小区数据集合
      currentList: [],
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      communityName:undefined,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 默认密码
      initPassword: undefined,
      // 表单参数
      form: {},
      reportRepairTypeOptions: [],
      reportRepairStatusOptions:[],
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        name: undefined,
        meterReadingType: undefined,
        meterReadingStatus: undefined,
        energyConsumptionType: undefined,
      },
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    
    this.getTree();
  },
  methods: {
    ect(row) { 
      return this.selectDictLabel(this.dict.type.energy_consumption_type, row.energyConsumptionType)
    },
    mrt(row) { 
      return this.selectDictLabel(this.dict.type.meter_reading_type, row.meterReadingType)
    },
    mrs(row) { 
      return this.selectDictLabel(this.dict.type.meter_reading_status, row.meterReadingStatus)
    },
    getMeterReadingTypeOptions(energyConsumptionType) {
      this.queryParams.meterReadingType = undefined;
      this.form.meterReadingType = undefined;
      this.getDicts("meter_reading_type").then(response => {
        var meterReadingTypeOptions = response.data;
        this.meterReadingTypeOptions=meterReadingTypeOptions.filter(item => item.remark == energyConsumptionType);
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(row){
      this.queryParams.communityId = row.communityId
      this.form.communityId = row.communityId;
      this.getExecutorByOptions();
      this.getList();
    },
    getReportRepairTypeName(val) { 
      if (val) { 
        var obj = this.reportRepairTypeOptions.filter(item => item.dictValue == val)[0];
        if (obj) { 
          return obj.dictLabel;
        }
      }
    },
    getPatrolStatusName(val) { 
      if (val) { 
        var obj = this.meterReadingStatusOptions.filter(item => item.dictValue == val)[0];
        if (obj) { 
          return obj.dictLabel;
        }
      }
    },
    getFacilitieEquipmentTypeName(val) { 
      if (val) { 
        var obj = this.facilitieEquipmentTypeOptions.filter(item => item.dictValue == val)[0];
        if (obj) { 
          return obj.dictLabel;
        }
      }
    },
    getCurrentList(val) { 
      if (val) { 
        var obj = this.currentList.filter(item=>item.communityId == val)[0];
        if (obj) { 
          return obj.communityName;
        }
      }
    },
    /** 查询装修列表 */
    getList() {
      this.loading = true;
      if (this.dateRange) { 
        this.queryParams.startTime = this.dateRange[0];
        this.queryParams.endTime=this.dateRange[1];
      }
      listMeterReading(this.queryParams).then(response => {
          this.list = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        ownerName: undefined,
        ownerPhone: undefined,
        buildingName: undefined,
        roomName: undefined
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
    getTree(){
      getCurrentList().then(res =>{
        this.currentList = res.data;
        this.queryParams.communityId = this.currentList[0].communityId;
        this.communityName = this.currentList[0].communityName;
        this.getExecutorByOptions();
        this.getList();

        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.currentList[0].communityId);
        });
      })
    },
    getExecutorByOptions() { 
       //抄表人员
       var workPostList = ['8'];
        getCommunityIdStaffList(this.queryParams.communityId,workPostList).then(res => {
          this.executorByOptions = res.data;
        });
    },
    handleExport() { 
      this.download('estate/cleaningWorkOrder/export', {
        ...this.queryParams
      }, this.communityName+`小区清洁工单_${new Date().getTime()}.xlsx`)
    },


    /** 查看详情按钮操作 */
    handleDetail(row) {
      this.reset();
      this.recordWorkOrderList = [];
      const id = row.id || this.ids
      getMeterReadingDetail(id).then(response => {
        this.detailForm = response.data;
        if (this.detailForm.recordWorkOrderList) { 
          this.recordWorkOrderList=this.detailForm.recordWorkOrderList;
        }
      });
      this.openView = false
      this.detailShow = true
    },

    //关闭详情页
    canceldetail() {
      this.detailForm = {}
      this.detailShow = false
      this.openView = true;
    }
  }
};
</script>

<style scoped>


/deep/.el-radio__input.is-disabled.is-checked .el-radio__inner {

background-color: #1890ff;

}

/deep/.el-checkbox__input.is-disabled.is-checked .el-checkbox__inner{
  background-color: #1890ff;
}

/* 禁用时字体颜色 */

/deep/.el-input.is-disabled .el-input__inner {

border-color: #1890ff;

color: #1890ff;

}

</style>

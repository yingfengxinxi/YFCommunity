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
            
            <el-form-item label="巡检状态" prop="patrolStatus">
              <el-select
                v-model="queryParams.patrolStatus"
                placeholder="请选择巡检状态"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="item in patrolStatusOptions"
                  :key="item.dictValue"
                  :label="item.dictLabel"
                  :value="item.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="巡检类型" prop="facilitieEquipmentType">
              <el-select
                v-model="queryParams.facilitieEquipmentType"
                placeholder="请选择巡检类型"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="item in facilitieEquipmentTypeOptions"
                  :key="item.dictValue"
                  :label="item.dictLabel"
                  :value="item.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="巡检时间" prop="dateRange">
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
            
            <el-form-item label="巡检人" prop="name" >
              <el-select
                v-model="queryParams.name"
                placeholder="请选择巡检人"
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
          <right-toolbar :showSearch.sync="showSearch" @queryTable="listPatrolList"></right-toolbar>

          <el-table v-loading="loading" :data="listPatrolList">
            <el-table-column label="工单编号" align="center" key="no" prop="no"/>
            <el-table-column label="设备编号" align="center" key="facilitieEquipmentNo" prop="facilitieEquipmentNo"/>
            <el-table-column label="巡检点" align="center" key="patroltAddress" prop="patroltAddress"/>
            <el-table-column label="巡检类型" align="center"  prop="facilitieEquipmentType">
              <template slot-scope="scope">
                  {{ getFacilitieEquipmentTypeName(scope.row.facilitieEquipmentType) }}
              </template>
            </el-table-column>
            <el-table-column label="巡检状态" align="center" key="patrolStatus" prop="patrolStatus">
              <template slot-scope="scope">
                  {{ getPatrolStatusName(scope.row.patrolStatus) }}
              </template>
            </el-table-column>
            <el-table-column label="巡检人员" align="center" key="name" prop="name">
              <template slot-scope="scope">
                  {{ scope.row.name==null?'--':scope.row.name }}
              </template>
            </el-table-column>
            <el-table-column label="巡检时间" align="center" key="createTime" prop="createTime"/>
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
              <el-form-item label="工单状态:">{{getPatrolStatusName(detailForm.patrolStatus) }}</el-form-item>
            </el-col>
            <el-col :span="7" :offset="1">
              <el-form-item label="工单创建时间:">{{detailForm.createTime}}</el-form-item>
            </el-col>
            <el-col :span="7" :offset="1">
              <el-form-item label="工单截止时间:">{{detailForm.orderExpirationTime }}</el-form-item>
            </el-col>
          </el-row>
          <h4 class="form-header">巡检人信息</h4>
        <el-row>
          <el-col :span="7" :offset="1">
            <el-form-item label="姓名:">{{ detailForm.name==null?"--":detailForm.name }}</el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="联系方式:">{{ detailForm.phone==null?"--":detailForm.phone }}</el-form-item>
          </el-col>
        </el-row>
        <h4 class="form-header">巡检内容</h4>
        <el-row>
          <el-col :span="7" :offset="1">
            <el-form-item label="巡检类型:">{{getFacilitieEquipmentTypeName(detailForm.facilitieEquipmentType)}}</el-form-item>
          </el-col>
          <el-col :span="7" :offset="1">
            <el-form-item label="巡检点:">{{ detailForm.patroltAddress }}</el-form-item>
          </el-col>
          <el-col :span="7" :offset="1">
            <el-form-item label="巡检日期:">{{ detailForm.patrolTime }}</el-form-item>
          </el-col>
          <el-col :span="7" :offset="1">
            <el-form-item label="设备编号:">{{ detailForm.facilitieEquipmentNo }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="7" :offset="1">
            <el-form-item label="巡检内容:">
              
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="detailForm.facilitieEquipmentType=='0'">
          <el-col :span="7" :offset="4">
            <div v-for="(item,index) in patrolContent" :key="index" style="margin: 10px 10px;">
               {{index+1}}、{{item.name }}
               <br>
               <el-radio-group v-model="item.status" >
                  <div v-for="(itemRemark,index) in item.remark" :key="index" style="margin: 10px 10px;">
                    <el-radio disabled :label="itemRemark.value">{{ itemRemark.label }}</el-radio>
                  </div>
               </el-radio-group>
            </div>
          </el-col>
        </el-row>
        <!-- 高压配电房 -->
        <el-row v-if="detailForm.facilitieEquipmentType=='2'">
          <el-col :span="7" :offset="4">
            <div v-for="(item,index) in patrolContent" :key="index" style="margin: 10px 10px;">
               {{index+1}}、{{item.name }}
               <br>
               <div v-for="(itemRemark,index) in item.remark" :key="index" style="margin: 10px 30px;">
                  {{itemRemark.name }}
                  <span style="margin-left: 10px;" v-if="itemRemark.flag=='0'">
                    {{ itemRemark.status }}
                  </span>
                  <span style="margin-left: 10px;" v-else>
                    <span style="color:red"  v-if="itemRemark.status=='0'">否</span>
                    <span style="color:#1ed76d" v-if="itemRemark.status=='1'">是</span>
                  </span>
               </div>
            </div>
          </el-col>
        </el-row>
        <!-- 低压配电房 -->
        <el-row v-if="detailForm.facilitieEquipmentType=='3'">
          <el-col :span="7" :offset="4">
            <div v-for="(item,index) in patrolContent" :key="index" style="margin: 10px 10px;">
               {{index+1}}、{{item.name }}
               <br>
                <el-checkbox-group  v-model="item.status" style="margin: 5px 10px;">
                   <el-checkbox disabled true-label="1">{{item.remark}}</el-checkbox>
                </el-checkbox-group>
            </div>
          </el-col>
          
        </el-row>
        <!-- 风机房 -->
        <el-row v-if="detailForm.facilitieEquipmentType=='6'">
          <el-col :span="18" :offset="4">
            <div v-for="(item,index) in patrolContent" :key="index" style="margin: 10px;padding-bottom: 10px;">
               <span >{{index+1}}、{{item.name }}</span>
               <br>
               <span style="margin-left: 10px;">{{item.remark}}</span>
               <span v-if="item.status=='1'" style="margin-left: 10px;color:#1ed76d">是</span>
               <span v-if="item.status=='0'" style="margin-left: 10px;color:red">否</span>
            </div>
          </el-col>
          
        </el-row>
        <!-- 智能化设备 -->
        <el-row v-if="detailForm.facilitieEquipmentType=='10'">
          <el-col :span="10" :offset="4">
            <div v-for="(item,index) in patrolContent" :key="index" style="margin: 10px 10px;">
               {{index+1}}、{{item.name }}
               <br>
               <div v-for="(itemRemark,index) in item.remark" :key="index" style="margin: 10px 10px;">
                {{itemRemark.name }}
               <br>
                  <span v-if="item.status=='A'">
                    <el-radio-group v-model="itemRemark.status">
                        <div v-for="(itemr,index) in itemRemark.remark" :key="index" style="margin: 10px 10px;">
                          <el-radio disabled :label="itemr.value">{{ itemr.name }}</el-radio>
                        </div>
                    </el-radio-group>
                  </span>
                  <span v-if="item.status=='B'">
                    <div v-for="(itemr,index) in itemRemark.remark" :key="index" style="margin: 10px 10px;">
                      {{itemr.name }}
                    <br>
                          <el-radio-group v-model="itemr.status">
                              <div v-for="(itemrr,index) in itemr.remark" :key="index" style="margin: 10px 10px;">
                                <el-radio disabled :label="itemrr.value">{{ itemrr.name }}</el-radio>
                              </div>
                          </el-radio-group>
                    </div>
                  </span>
                </div>
            </div>
          </el-col>
        </el-row>
        <!-- 消火栓 -->
        <el-row v-if="detailForm.facilitieEquipmentType=='11'">
          <el-col :span="7" :offset="4">
            <div v-for="(item,index) in patrolContent" :key="index" style="margin: 10px 10px;">
               {{index+1}}、{{item.name }}
               <br>
               <el-radio-group v-model="item.status" >
                  <div v-for="(itemRemark,index) in item.remark" :key="index" style="margin: 10px 10px;">
                    <el-radio disabled :label="itemRemark.value">{{ itemRemark.label }}</el-radio>
                  </div>
               </el-radio-group>
            </div>
          </el-col>
        </el-row>
        <!-- 消火器 -->
        <el-row v-if="detailForm.facilitieEquipmentType=='12'">
          <el-col :span="7" :offset="4">
            <div v-for="(item,index) in patrolContent" :key="index" style="margin: 10px 10px;">
               {{index+1}}、{{item.name }}
                  <span style="margin-left: 10px;" v-if="item.flag=='0'">
                    {{ item.status }}
                  </span>
                  <span style="margin-left: 10px;" v-else>
                    <span style="color:red"  v-if="item.status=='0'">否</span>
                    <span style="color:#1ed76d" v-if="item.status=='1'">是</span>
                  </span>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="7" :offset="1">
            <el-form-item label="设备是否正常:">{{ detailForm.equipmentStatus=='0'?'正常':'异常' }}</el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="detailForm.facilitieEquipmentType=='11'">
          <el-col :span="7" :offset="1">
              <el-form-item label="其他备注:">{{detailForm.otherRemark}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="7" :offset="1">
              <el-form-item label="备注:">{{detailForm.remarks}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="18" :offset="1">
            <el-form-item label="现场图片:">
              <image-preview v-for="image in detailForm.patrolImagesList" :src="image" :width="50" :height="50" style="margin: 0 5px;"/>
            </el-form-item>
          </el-col>
        </el-row>
        <div style="float: right">
          <el-button @click="canceldetail">关 闭</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import {
  listPatrolWorkOrder,
  getPatrolWorkOrderDetail
} from "@/api/estate/patrol/patrol";
import { getCurrentList } from "@/api/estate/community";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getCommunityIdStaffList } from "@/api/estate/manage/staff/staff";
export default {
  name: "cleaningWorkOrder",
  components: { Treeselect },
  dicts: ['renovation_audit_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      listPatrolList:[],
      // 选中数组
      ids: [],
      active:1,
      // 非单个禁用
      single: true,
      patrolContent:[],
      // 非多个禁用
      multiple: true,
      //主页面展示
      openView: true,
      patrolStatusOptions: [],
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
      // 总条数
      total: 0,
      // 装修表格数据
      list: null,
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
        facilitieEquipmentType: undefined,
        patrolStatus: undefined,
      },
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getDicts("patrol_content").then((response) => {
      this.facilitieEquipmentTypeOptions = response.data;
    });
    this.getDicts("patrol_status").then((response) => {
      this.patrolStatusOptions = response.data;
    });
   
    this.getTree();
  },
  methods: {
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(row){
      this.queryParams.communityId = row.communityId
      this.form.communityId = row.communityId
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
        var obj = this.patrolStatusOptions.filter(item => item.dictValue == val)[0];
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
      listPatrolWorkOrder(this.queryParams).then(response => {
          this.listPatrolList = response.rows;
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
         //清洁人员
        var workPostList = ['7'];
        getCommunityIdStaffList(this.queryParams.communityId,workPostList).then(res => {
          this.executorByOptions = res.data;
        });
        this.getList();

        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.currentList[0].communityId);
        });
      })
    },

    handleExport() { 
      this.download('estate/cleaningWorkOrder/export', {
        ...this.queryParams
      }, this.communityName+`小区清洁工单_${new Date().getTime()}.xlsx`)
    },


    /** 查看详情按钮操作 */
    handleDetail(row) {
      this.reset();
      const id = row.id || this.ids
      getPatrolWorkOrderDetail(id).then(response => {
        this.detailForm = response.data;
        var jsonContent = JSON.parse(this.detailForm.patrolContent);
        console.log("oldJsonContent>>>",jsonContent);
        if (jsonContent) {
          if (this.detailForm.facilitieEquipmentType == "0") { 
            //电梯
            console.log("电梯")
            jsonContent.forEach(item => {
                //0=正常,1=不正常,2=调整、维修,3、已通知维保公司维修
                var remarks = item.remark.split(",");
                var valueList = [];
                remarks.forEach(re => { 
                  var values = re.split("=");
                  valueList.push({ "value": values[0], "label": values[1] });
                })
                item.remark = valueList;
            });
            console.log("newJsonContent>>>", jsonContent);
            
          }
          if (this.detailForm.facilitieEquipmentType == "2") {
            //高压配电房 不用解析数据
            console.log("高压配电房")
          }
          if (this.detailForm.facilitieEquipmentType == "3") { 
            //低压配电房不用解析数据
            console.log("低压配电房")
            
          }
          if (this.detailForm.facilitieEquipmentType == "6") { 
            //风机房不用解析数据
            console.log("风机房")
          }
          if (this.detailForm.facilitieEquipmentType == "10") { 
            //智能化设备不用解析数据
            console.log("风机房")

          }
          if (this.detailForm.facilitieEquipmentType == "11") { 
            //消火栓
            console.log("消火栓")
            jsonContent.forEach(item => {
                
                var remarks = item.remark.split(",");
                var valueList = [];
                remarks.forEach(re => { 
                  var values = re.split("=");
                  valueList.push({ "value": values[0], "label": values[1] });
                })
                item.remark = valueList;
            });
          }
          if (this.detailForm.facilitieEquipmentType == "12") { 
            //消防器
            console.log("消防器")
          }
          this.patrolContent = jsonContent;
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

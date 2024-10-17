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
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
            <el-form-item label="关键字" prop="searchValue">
              <el-input
                v-model="queryParams.searchValue"
                placeholder="报修人姓名/报修人电话/维修人员"
                clearable
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="工单编号" prop="no">
              <el-input
                v-model="queryParams.no"
                placeholder="请输入工单编号"
                clearable
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="报事类型" prop="reportType">
              <el-select
                v-model="queryParams.reportType"
                placeholder="报事类型"
                clearable
                style="width: 160px"
              >
                <el-option
                  v-for="item in reportRepairTypeOptions"
                  :key="item.dictValue"
                  :label="item.dictLabel"
                  :value="item.dictValue"
                />
              </el-select>
            </el-form-item>
            
            <el-form-item label="流程进度" prop="reportStatus">
              <el-select
                v-model="queryParams.reportStatus"
                placeholder="流程进度"
                clearable
                style="width: 160px"
              >
                <el-option
                  v-for="item in reportRepairStatusOptions"
                  :key="item.dictValue"
                  :label="item.dictLabel"
                  :value="item.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>

          <el-table v-loading="loading" :data="reportingWarrantyList">
            <el-table-column label="工单编号" align="center" key="no" prop="no"/>
            <el-table-column label="报事类型" align="center" key="reportType" prop="reportType">
              <template slot-scope="scope">
                  {{ getReportRepairTypeName(scope.row.reportType) }}
              </template>
            </el-table-column>
            <el-table-column label="具体位置" align="center" prop="reportAddress" />
            <el-table-column label="流程进度" align="center" prop="reportStatus">
              <template slot-scope="scope">
                  {{ getReportRepairStatusName(scope.row.reportStatus) }}
              </template>
            </el-table-column>
            <el-table-column label="报修姓名" align="center" key="name" prop="name"/>
            <el-table-column label="报修电话" align="center" key="phone" prop="phone"/>
            <el-table-column label="维修人员姓名" align="center" key="executorBy" prop="executorBy"/>
            <el-table-column label="维修人员电话" align="center" key="executorPhone" prop="executorPhone"/>
            <el-table-column label="创建时间" align="center" key="createTime" prop="createTime"/>
          
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
        <h4 class="form-header">处理流程</h4>
        
        <el-steps :active=active class="stepsStyle"> 
          <!-- v-for="(item) in caseHistoryData" :key="item.id" -->
          <el-step v-for="(item) in reportRepairStatusOptions" :key="item.dictValue" :title="item.dictLabel">
            <template slot="description">
              <div class="putOnRecord_p">
                    <p v-if="item.dictValue=='0'" style="color: rgb(0, 0, 0);padding-bottom: 5px;color: #6290ff;">
                      {{ detailForm.createTime }}
                    </p>
                    <p v-if="item.dictValue=='3'" style="color: rgb(0, 0, 0);padding-bottom: 5px;color: #6290ff;">
                      {{ detailForm.orderEndTime }}
                    </p>
                  </div>
            </template>
          </el-step>
        </el-steps>
        <h4 class="form-header">工单信息</h4>
          <el-row>
            <el-col :span="7" :offset="1">
              <el-form-item label="工单编码:">{{ detailForm.no }}</el-form-item>
            </el-col>
            <el-col :span="7" :offset="1">
              <el-form-item label="工单状态:">{{ getReportRepairStatusName(detailForm.reportStatus) }}</el-form-item>
            </el-col>
            <el-col :span="7" :offset="0">
              <el-form-item label="工单提交时间:">{{detailForm.createTime }}</el-form-item>
            </el-col>
            
          </el-row>
          <h4 class="form-header">发布人</h4>
          <el-row>
            <el-col :span="7" :offset="1">
              <el-form-item label="发布人姓名:">{{ detailForm.name }}</el-form-item>
            </el-col>
            <el-col :span="10" :offset="2">
              <el-form-item label="联系方式:">{{detailForm.phone }}</el-form-item>
            </el-col>
            
          </el-row>
        <h4 class="form-header">报事详情</h4>
        <el-row>
          <el-col :span="7" :offset="1">
            <el-form-item label="名称:">{{ detailForm.reportName }}</el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
              <el-form-item label="报事类型:">{{ getReportRepairTypeName(detailForm.reportType) }}</el-form-item>
            </el-col>
         
          
        </el-row>
        <el-row>
          <el-col :span="7" :offset="1">
            <el-form-item label="地址:">{{ detailForm.reportAddress }}</el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="备注:">{{ detailForm.reportRemark }}</el-form-item>
          </el-col>
          
        </el-row>
        <el-row>
          <el-col :span="7" :offset="1">
            <el-form-item label="维修人员:">{{ detailForm.executorBy }}</el-form-item>
          </el-col>
          <el-col :span="10" :offset="2">
            <el-form-item label="维修人员电话:">{{ detailForm.executorPhone }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10" :offset="1">
            <el-form-item label="现场图片:">
              <image-preview v-for="image in detailForm.reportImages" :src="image" :width="50" :height="50" style="margin: 0 5px;"/>
            </el-form-item>
          </el-col>
        </el-row>
        <h4 class="form-header">工单反馈</h4>
        <el-row>
          <el-col :span="10" :offset="1">
            <el-form-item label="现场维修图片:">
              <image-preview v-for="image in detailForm.orderImages" :src="image" :width="50" :height="50" style="margin: 0 5px;"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10" :offset="1">
            <el-form-item label="反馈内容:">{{ detailForm.orderFeedback }}</el-form-item>
          </el-col>
        </el-row>
        <h4 class="form-header">业主评价</h4>
        <el-row>
          <el-col :span="12" :offset="1">
            <el-rate disabled v-model="detailForm.evaluate"></el-rate>
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
  listReportingWarranty,
  getReportingWarrantyDetail
} from "@/api/estate/customer/reportingWarranty/reportingWarranty";
import { getCurrentList } from "@/api/estate/community";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {desensitization, equals} from "@/utils/common";

export default {
  name: "reportingWarranty",
  components: { Treeselect },
  dicts: ['renovation_audit_status'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      active:1,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      //主页面展示
      openView: true,
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
      reportingWarrantyList: null,
      //装修工信息
      worker: {},
      //小区数据集合
      currentList: [],
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
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
        searchValue: undefined,
        no: undefined,
        reportStatus: undefined,
        reportType:undefined
      },
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getDicts("report_repair_type").then((response) => {
      this.reportRepairTypeOptions = response.data;
    });
    this.getDicts("report_repair_status").then((response) => {
      this.reportRepairStatusOptions = response.data;
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
    getReportRepairStatusName(val) { 
      if (val) { 
        var obj = this.reportRepairStatusOptions.filter(item => item.dictValue == val)[0];
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
      listReportingWarranty(this.queryParams).then(response => {
          this.reportingWarrantyList = response.rows;
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
      this.resetForm("queryForm");
      this.handleQuery();
    },

    // 获取树数据
    getTree(){
      getCurrentList().then(res =>{
        this.currentList = res.data;
        this.queryParams.communityId = this.currentList[0].communityId;
        this.getList();

        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.currentList[0].communityId);
        });
      })
    },

     // 任务组名字典翻译
    auditStatusName(row, column) {
      return this.selectDictLabel(this.dict.type.renovation_audit_status, row.renovationAuditStatus);
    },

    handleExport() { 
      this.download('estate/decoration/export', {
        ...this.queryParams
      }, `装修评定_${new Date().getTime()}.xlsx`)
    },


    /** 查看详情按钮操作 */
    handleDetail(row) {
      this.reset();
      const id = row.id || this.ids
      getReportingWarrantyDetail(id).then(response => {
        this.detailForm = response.data;
        this.active=parseInt(this.detailForm.reportStatus)+1;
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


/*设置高亮颜色*/
      /deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
        background-color: #b3f1b6 !important;
      }


     /deep/ .stepsStyle .el-step__title.is-process,.el-step__title.is-wait{
        font-weight: 500;
        color: #a8a8a8 !important;
     }
     /* 这里设置的是下一步和等待状态里的文字颜色 */
     /deep/.stepsStyle .el-step__head.is-process,.el-step__heads.is-wait .el-step__icon.is-text{
       
         color: #a8a8a8 !important;
         border-color: #a8a8a8 !important;
         font-weight: 500;
       
     }
     /deep/.stepsStyle .el-step__head.is-finish{
       color: #2a82e4 !important;
     }
     .stepsStyle{
      width: 70%;
      margin: auto;
     }
     /deep/.el-step__main .el-step__title.is-finish{
      padding-bottom:0px;
     }
     /* 这里设置的是已完成状态里的文字颜色 */
     /deep/.stepsStyle .el-step__head.is-finish .el-step__icon.is-text{
         color: #2a82e4 !important;
         border-color: #2a82e4 !important;
       }
     .el-step__title.is-finish{
       color: #2a82e4 !important;
     }
     
     /* 这里设置的是完成后过渡线条颜色 */
     .el-step__line-inner{
       border-color: #2a82e4 !important;
     }
     
     /* 这里设置的是完成后的颜色 */
     .el-step__head.is-success,.el-step__title.is-success{
       color: #2a82e4 !important;
     }

</style>

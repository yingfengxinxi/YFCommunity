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
            <el-form-item label="房屋地址" prop="houseAddress">
              <el-input
                v-model="queryParams.houseAddress"
                placeholder="请输入房屋地址"
                clearable
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="装修进度" prop="renovationAuditStatus">
              <el-select
                v-model="queryParams.renovationAuditStatus"
                placeholder="装修进度"
                clearable
                style="width: 160px"
              >
                <el-option
                  v-for="item in renovationAuditStatusOptions"
                  :key="item.dictValue"
                  :label="item.dictLabel"
                  :value="item.dictValue"
                />
              </el-select>
            </el-form-item>
            
            <el-form-item label="巡查时间" prop="auditStatus">
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
            <el-form-item label="执行人" prop="updateBy">
              <el-input
                v-model="queryParams.updateBy"
                placeholder="请输入执行人"
                clearable
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>
          
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
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>

          <el-table v-loading="loading" :data="decorationList">
            <el-table-column label="工单编号" align="center" key="no" prop="no"/>
            <el-table-column label="房屋地址" align="center" key="houseAddress" prop="houseAddress"/>
            <el-table-column label="装修开始时间" align="center" prop="planStart" />
            <el-table-column label="装修结束时间" align="center" prop="planEnd" />
            <el-table-column label="最近执行人" align="center" key="updateBy" prop="updateBy" :show-overflow-tooltip="true" />
            <el-table-column label="装修进度" align="center" key="renovationAuditStatus" prop="renovationAuditStatus" :show-overflow-tooltip="true">
              <template slot-scope="scope">
                  {{ auditStatusName(scope.row) }}
              </template>
            </el-table-column>
            <el-table-column label="上次巡查时间" align="center" key="updateTime" prop="updateTime" :show-overflow-tooltip="true"/>
          
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
            <el-col :span="5" :offset="1">
              <el-form-item label="工单状态:">{{detailForm.renovationAuditStatusName }}</el-form-item>
            </el-col>
            <el-col :span="7" :offset="1">
              <el-form-item label="工单提交时间:">{{ detailForm.createTime }}</el-form-item>
            </el-col>
          </el-row>
       
        
        <h4 class="form-header">房屋信息</h4>
        <el-row>
          <el-col :span="10" :offset="2">
            <el-form-item label="所属小区:">{{ detailForm.communityName }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="5" :offset="2">
            <el-form-item label="所属楼栋:">{{ detailForm.buildingName }}</el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="单元号:">{{ detailForm.unitName }}</el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="门牌号:">{{ detailForm.roomName }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="5" :offset="2">
            <el-form-item label="所在楼层:">{{ detailForm.floor }}层</el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="房屋面积:">{{ detailForm.totalArea }}</el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="房屋坐落:">{{ detailForm.houseAddress }}</el-form-item>
          </el-col>
        </el-row>
        <h4 class="form-header">装修信息</h4>
        <el-row>
          <el-col :span="7" :offset="2">
            <el-form-item label="计划装修开始时间:">{{ detailForm.planStart }}</el-form-item>
          </el-col>
          <el-col :span="7" :offset="2">
            <el-form-item label="计划装修结束时间:">{{ detailForm.planEnd }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="7" :offset="2">
            <el-form-item label="装修公司名称:">{{ detailForm.companyName }}</el-form-item>
          </el-col>
          <el-col :span="7" :offset="2">
            <el-form-item label="负责人:">{{ detailForm.personName }}</el-form-item>
          </el-col>
          <el-col :span="7" :offset="2">
            <el-form-item label="联系方式:">{{ detailForm.personPhone }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12" :offset="2">
            <el-form-item label="装修/安装项目:">{{ detailForm.projectItem }}</el-form-item>
          </el-col>
        </el-row>
       

        <h4 class="form-header">要件信息</h4>

        <el-row>
          <el-col :span="10" :offset="2">
            <el-form-item label="房屋所有权证:">
              <image-preview v-for="image in detailForm.warrants" :src="image" :width="50" :height="50" style="margin: 0 5px;"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10" :offset="2">
            <el-form-item label="身份证正反面:">
              <image-preview v-if="detailForm.cardFont" :src="detailForm.cardFont" :width="50" :height="50" style="margin: 0 5px;"/>
              <image-preview v-if="detailForm.cardBack" :src="detailForm.cardBack" :width="50" :height="50" style="margin: 0 5px;"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10" :offset="2">
            <el-form-item label="装修设计方案:">
              <image-preview v-for="image in detailForm.designSchemes" :src="image" :width="50" :height="50" style="margin: 0 5px;"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10" :offset="2">
            <el-form-item label="维修合同:">
              <image-preview v-for="image in detailForm.decorationContracts" :src="image" :width="50" :height="50" style="margin: 0 5px;"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10" :offset="2">
            <el-form-item label="其他:">
              <image-preview v-for="image in detailForm.others" :src="image" :width="50" :height="50" style="margin: 0 5px;"/>
            </el-form-item>
          </el-col>
        </el-row>
        <h4 class="form-header">节点记录</h4>
        
        <el-steps :active=active  direction="vertical" class="stepsStyle"> 
          <!-- v-for="(item) in caseHistoryData" :key="item.id" -->
         
          <el-step v-for="(item,index) in violationList" :key="index" :title="item.renovationAuditStatus">
            
            <template slot="description">
              <div class="putOnRecord_p">
                    <p style="color: rgb(0, 0, 0);padding-bottom: 5px;color: #6290ff;">
                      {{ item.createTime }}
                    </p>
                    <p style="background-color: rgb(248, 248, 249);color: rgb(0, 0, 0);width: 39%;padding: 5px 10px;border-radius: 5px;">
                      {{ item.content }}
                    </p>
                  </div>
                  <div class="putOnRecordLA" style="margin-bottom: 10px;">
                    <image-preview v-for="image in item.images" :src="image" :width="50" :height="50" style="margin: 0 5px;"/>
                  </div>
            </template>
          </el-step>
        </el-steps>
        <div style="font-size: 15px;margin-left: 41px;color: #606266;" v-if="violationList.length==0">暂无节点记录</div>

        <div style="float: right">
          <el-button @click="canceldetail">关 闭</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import { listDecoration, decorateAdopt, getDecorationDetail, decorateReject, decorateDelay } from "@/api/estate/customer/decorate/decoration";
import { getCurrentList } from "@/api/estate/community";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {desensitization, equals} from "@/utils/common";

export default {
  name: "Decoration",
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
      decorationList: null,
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
      renovationAuditStatusOptions:[],
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        startTime:undefined,
        endTime: undefined,
        houseAddress:undefined,
        renovationAuditStatus: undefined,
        updateBy: undefined,
        communityId: undefined
      },
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getDicts("renovation_audit_status").then((response) => {
      this.renovationAuditStatusOptions = response.data;
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
    /** 查询装修列表 */
    getList() {
      if (this.dateRange) { 
        this.queryParams.startTime = this.dateRange[0];
        this.queryParams.endTime = this.dateRange[1];
      }
      this.loading = true;
      listDecoration(this.queryParams).then(response => {
          this.decorationList = response.rows;
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
      getDecorationDetail(id).then(response => {
        this.detailForm = response.data;
        this.violationList = response.violationList;
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
        font-weight: 400 !important;
        color: #ccc4cf !important;
         border-color: #a8a8a8 !important;
     }
     /* 这里设置的是下一步和等待状态里的文字颜色 */
     /deep/.stepsStyle .el-step__head.is-process,.el-step__heads.is-wait .el-step__icon.is-text{
       
         color: #a8a8a8 !important;
         border-color: #a8a8a8 !important;
       
     }
     /deep/.stepsStyle .el-step__head.is-finish{
       color: #2a82e4 !important;
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

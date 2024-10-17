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
              default-expand-all
              :filter-node-method="filterNode"
              icon-class="el-icon-star-on"
              @node-click="handleNodeClick"
              ref="tree"
              highlight-current
              node-key="communityId"
            >
            </el-tree>
          </div>
        </el-col>
        <!--装修数据-->
        <el-col :span="20" :xs="24">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
            <el-form-item label="业主姓名" prop="ownerName">
              <el-input
                v-model="queryParams.ownerName"
                placeholder="请输入业主姓名"
                clearable
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="联系方式" prop="ownerPhone">
              <el-input
                v-model="queryParams.ownerPhone"
                placeholder="请输入联系方式"
                clearable
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="所属楼栋" prop="buildingName">
              <el-input
                v-model="queryParams.buildingName"
                placeholder="请输入所属楼栋"
                clearable
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="门牌号" prop="roomName">
              <el-input
                v-model="queryParams.roomName"
                placeholder="请输入门牌号"
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
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>

          <el-table v-loading="loading" :data="ledgerList">
            <el-table-column label="业主姓名" align="center" key="ownerName" prop="ownerName" :show-overflow-tooltip="true"/>
            <el-table-column label="联系方式" align="center" key="ownerPhone" prop="ownerPhone" :show-overflow-tooltip="true">
              <template scope="scope">
                <span>{{ phoneFormatter(scope.row.ownerPhone) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="所属楼栋" align="center" key="buildingName" prop="buildingName" :show-overflow-tooltip="true"/>
            <el-table-column label="单元号" align="center" key="unitName" prop="unitName" :show-overflow-tooltip="true"/>
            <el-table-column label="门牌号" align="center" key="roomName" prop="roomName" :show-overflow-tooltip="true"/>
            <el-table-column label="计划装修开始时间" align="center" prop="planStart" width="160" :show-overflow-tooltip="true"/>
            <el-table-column label="计划装修结束时间" align="center" prop="planEnd" width="160" :show-overflow-tooltip="true"/>
            <el-table-column label="装修押金" align="center" key="deposit" prop="deposit" :show-overflow-tooltip="true"/>
            <el-table-column
              label="操作"
              align="center"
              width="160"
              class-name="small-padding fixed-width"
            >
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="handleDetail(scope.row)"
                  v-hasPermi="['customer:ledger:detail']"
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
        <h4 class="form-header">装修详情</h4>

        <el-row>
          <el-col :span="4" :offset="2">
            <el-form-item label="业主姓名:">{{ detailForm.ownerName }}</el-form-item>
          </el-col>
          <el-col :span="4" :offset="2">
            <el-form-item label="联系方式:">{{ detailForm.ownerPhone }}</el-form-item>
          </el-col>
          <el-col :span="4" :offset="2">
            <el-form-item label="所属楼栋:">{{ detailForm.buildingName }}</el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="4" :offset="2">
            <el-form-item label="单元号:">{{ detailForm.unitName }}</el-form-item>
          </el-col>
          <el-col :span="4" :offset="2">
            <el-form-item label="门牌号:">{{ detailForm.roomName }}</el-form-item>
          </el-col>
<!--
          <el-col :span="4" :offset="2">
            <el-form-item label="装修押金:">{{ detailForm.deposit }}</el-form-item>
          </el-col>
-->
        </el-row>

        <el-row>
          <el-col :span="4" :offset="2">
            <el-form-item label="计划装修开始时间:">{{ detailForm.planStart }}</el-form-item>
          </el-col>
          <el-col :span="4" :offset="2">
            <el-form-item label="计划装修结束时间:">{{ detailForm.planEnd }}</el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12" :offset="2">
            <el-form-item label="备注:">{{ detailForm.remark }}</el-form-item>
          </el-col>
        </el-row>

        <h4 class="form-header">审批详情</h4>

        <el-row>
          <el-col :span="4" :offset="2">
            <el-form-item label="申请类型:">{{ detailForm.decorateType }}</el-form-item>
          </el-col>
          <el-col :span="4" :offset="2">
            <el-form-item label="审批状态:">{{ detailForm.auditStatus }}</el-form-item>
          </el-col>
          <el-col :span="4" :offset="2">
            <el-form-item label="审批人:">{{ detailForm.auditBy }}</el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="4" :offset="2">
            <el-form-item label="审批时间:">{{ detailForm.auditTime }}</el-form-item>
          </el-col>
          <el-col :span="4" :offset="2" v-if="detailForm.auditStatus == '2'">
            <el-form-item label="驳回原因:">{{ detailForm.rejectReason }}</el-form-item>
          </el-col>
        </el-row>

        <h4 class="form-header">装修人员信息</h4>

        <el-row>
          <el-table :data="workerList" >
            <el-table-column label="姓名" align="center" key="workerName" prop="workerName"/>
            <el-table-column label="联系方式" align="center" key="workerPhone" prop="workerPhone"/>
            <el-table-column label="身份证号" align="center" key="cardNo" prop="cardNo"/>
            <el-table-column label="审核时间" align="center" key="auditTime" prop="auditTime"/>
          </el-table>
        </el-row>

        <div style="float: right">
          <el-button @click="canceldetail">关 闭</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import { listLedger, getLedgerDetail } from "@/api/estate/customer/decorate/ledger";
import { getCurrentList } from "@/api/estate/community";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {desensitization, equals} from "@/utils/common";

export default {
  name: "Ledger",
  components: { Treeselect },
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
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
      // 显示搜索条件
      showSearch: true,
      // 树筛选文本
      filterText:'',
      // 总条数
      total: 0,
      //小区数据集合
      currentList: [],
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      // 装修表格数据
      ledgerList: null,
      //装修工信息
      workerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 默认密码
      initPassword: undefined,
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ownerName: undefined,
        ownerPhone: undefined,
        buildingName: undefined,
        roomName: undefined,
        communityId: undefined
      },
    };
  },
  watch: {
    // 根据名称筛选部门树
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
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
      this.loading = true;
      listLedger(this.queryParams).then(response => {
          this.ledgerList = response.rows;
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

    /** 查看详情按钮操作 */
    handleDetail(row) {
      this.reset();
      const decorateId = row.decorateId || this.ids
      getLedgerDetail(decorateId).then(response => {
        this.detailForm = response.data;
        this.workerList = response.workerList;
      });
      this.openView = false
      this.detailShow = true
    },

    /** 联系方式脱敏 */
    phoneFormatter(phone){
      if(!equals(phone,undefined)){
        return desensitization(phone,3,7);
      }
    },

    /** 身份证号脱敏 */
    cardFormatter(card){
      if(!equals(card,undefined)){
        return desensitization(card,6,14);
      }
    },

    //关闭详情页
    canceldetail() {
      this.detailForm = {}
      this.detailShow = false
      this.openView = true;
    },
  }
};
</script>
<style scoped>
/*设置高亮颜色*/
/deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #b3f1b6 !important;
}
</style>

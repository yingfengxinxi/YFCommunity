<template>
  <div class="app-container">
    <div v-show="openView === 0">
      <el-row :gutter="20">
        <!--   选择树   -->
        <el-col :span="4" :xs="24">
          <div class="head-container">
            <el-input
              placeholder="请输入小区名称"
              clearable
              size="small"
              prefix-icon="el-icon-search"
              style="margin-bottom: 20px"
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
              highlight-current
              node-key="communityId"
              ref="tree">
              <!-- 插入楼栋图标 -->
              <span slot-scope="{ node }">
	   		          <i class="el-icon-office-building" />
	   		          <span class="content"  style="padding-left:5px;">{{node.label}}</span>
		          </span>
            </el-tree>
          </div>
        </el-col>
        <!--   右侧   -->
        <el-col :span="20" :xs="24">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="82px">
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
            <el-form-item label="维修类型" prop="repairName">
              <el-input
                v-model="queryParams.repairName"
                placeholder="请输入维修类型"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            <el-form-item  style="margin-left: 5px">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>

          <el-table v-loading="loading" :data="homLedList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="工单编号" align="center" prop="orderNo" :show-overflow-tooltip="true" />
            <el-table-column label="工单名称" align="center" prop="orderName" :show-overflow-tooltip='true'/>

            <el-table-column label="户主姓名" align="center" prop="ownerName" />
            <el-table-column label="维修类型" align="center" prop="repairName" />
            <el-table-column label="定金" align="center" prop="deposit" />
            <el-table-column label="接单人员" align="center" prop="staffName" />
            <el-table-column label="接单时间" align="center" prop="executeTime" />
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="handleDetail(scope.row)"
                  v-hasPermi="['work:homLed:query']"
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
    <!-- 详情 -->
    <div v-if="openView === 1" v-show="openView === 1">
      <el-form ref="form" :model="form" label-width="110px">
        <h4 class="form-header">工单详情</h4>
        <el-row>
          <el-col :span="6" :offset="1" >
            <el-form-item label="工单编号：">{{ form.orderNo }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="工单名称：">{{ form.orderName }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="报事类型：">{{ form.repairName }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="完成图片：" >
              <img style="width: 70px; height: 50px;padding-right: 5px" :src="form.imageUrl" />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="报事地址：" >{{ form.address }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="计划开始时间：">{{ form.planStart }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="计划结束时间：">{{form.planEnd}}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="完成时间：">{{form.finishTime}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="异常状态：">{{form.status}}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="主要负责人：">{{form.staffName}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="备注：">{{ form.remark }}</el-form-item>
          </el-col>
        </el-row>
        <h4 class="form-header" align="left">工单协助人信息</h4>
        <el-row>
          <el-col :span="24">
            <el-table
              stripe
              type="index"
              highlight-current-row
              :data="staffList"
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
<!--                <template scope="scope">
                  <span>{{ phoneFormatter(scope.row.staffPhone) }}</span>
                </template>
              </el-table-column>-->
              <el-table-column
                label="身份证号"
                align="center"
                :show-overflow-tooltip="true"
                prop="cardNo"
              />
<!--                <template scope="scope">
                  <span>{{ cardFormatter(scope.row.cardNo) }}</span>
                </template>
              </el-table-column>-->
              <el-table-column
                label="人员类型"
                align="center"
                :show-overflow-tooltip="true"
                prop="workPost"
              >
                <template slot-scope="scope">
                  <dict-tag :options="dict.type.work_post" :value="scope.row.workPost"/>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>

        <orderRecord :father="queryRecord"></orderRecord>
      </el-form>
      <div slot="footer" class="dialog-footer" style="float: right">
        <el-button @click="close()">关 闭</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {desensitization, equals} from "@/utils/common";
import {getHomLedInfo, getHomStaList, listHomLed} from "@/api/estate/work/orderLedger/homLed";
import orderRecord from '@/components/work/orderRecord';

export default {
  name: "homeLedger",
  dicts: ['allot_way','work_post'],
  components: { orderRecord },
  data(){
    return{
      openView:false,
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
      // 表格数据
      homLedList: [],
      classifyList:[],
      //小区数据集合
      currentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 树筛选文本
      filterText:'',
      //树结构
      communityId :null,
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        orderName: null,
        ownerName: null,
        repairName: null,
      },
      // 表单参数
      form: {},
      queryRecord: {
        orderType: '5',
        orderId: 0
      },
      staffList:[],
      imgList: [],
    }
  },
  created() {
    this.getTree();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods:{
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(row){
      this.queryParams.communityId = row.communityId
      this.communityId = row.communityId
      this.getList();
    },
    // 获取树数据
    getTree(){
      getCurrentList().then(res =>{
        this.currentList = res.data;
        this.queryParams.communityId = this.currentList[0].communityId;
        this.communityId = this.currentList[0].communityId;
        this.getList();
        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.currentList[0].communityId);
        });

      })
    },

    // 关闭按钮
    close(){
      this.openView = 0;
    },
    //工单来源
    dataSourceChange(row) {
      if (row.dataSource === "0") {
        return "计划生成";
      }
      if (row.dataSource === "1") {
        return "后台添加";
      }
      if (row.dataSource === "2") {
        return "其他工单";
      }
      if (row.dataSource === "3") {
        return "场馆使用";
      }
    },

    /** 查看按钮 */
    handleDetail(row) {
      this.openView = 1;
      this.title = "查看详情";
      const id = row.orderId
      getHomLedInfo(id).then(response => {
        this.form = response.data;
      });
      getHomStaList(id).then(response => {
          this.staffList = response.rows;
        }
      );
      this.queryRecord.orderId = id;
    },



    /** 表单重置*/
    reset() {
      this.form = {
      };
    },
    /** 搜索按钮操作 */
    handleQuery() {
      //this.getTree();
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];

      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.passageId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 查询列表 */
    getList() {
      this.openView = 0;
      this.loading = false;
      listHomLed(this.queryParams).then(response => {
        this.homLedList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 手机号脱敏 */
    phoneFormatter(phone) {
      if (!equals(phone, undefined)) {
        return desensitization(phone, 3, 7);
      }
    },

    /** 身份证号脱敏 */
    cardFormatter(card) {
      if (!equals(card, undefined)) {
        return desensitization(card, 6, 14);
      }
    },
  }
}
</script>

<style scoped>
/deep/.el-form--inline .el-form-item{
  margin-right: 0px !important;
}

/*设置高亮颜色*/
/deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #b3f1b6 !important;
}
.content {
  font-size: 14px;
}
</style>

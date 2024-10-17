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
              style="margin-bottom: 20px"
              prefix-icon="el-icon-search"
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
            <el-form-item label="巡检类型" prop="tourName">
              <el-select v-model="queryParams.tourName" placeholder="请选择巡检类型" clearable >
                <el-option
                  v-for="item in tourNameList"
                  :value="item.tourName"

                />
              </el-select>
            </el-form-item>
            <el-form-item label="工单来源" prop="dataSource">
              <el-select
                v-model="queryParams.dataSource"
                placeholder="请选择工单来源"
                clearable
              >
              <el-option label="计划生成" value="0"/>
              <el-option label="后台添加" value="1"/>
              <el-option label="其他工单" value="2"/>
              </el-select>
            </el-form-item>

            <el-form-item label="分配方式" prop="allotWay">
              <el-select v-model="queryParams.allotWay" placeholder="请选择分配方式" clearable >
                <el-option
                  v-for="dict in dict.type.allot_way"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="执行人" prop="executeName">
              <el-input
                v-model="queryParams.executeName"
                placeholder="请输入执行人"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item  style="margin-left: 5px">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>

          <el-table v-loading="loading" :data="insLedList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="工单编号" align="center" prop="inspectNo" :show-overflow-tooltip='true' />
            <el-table-column label="工单名称" align="center" prop="inspectName" :show-overflow-tooltip='true' />
            <el-table-column label="巡检类型" align="center" prop="tourName" />
            <el-table-column label="工单来源" align="center" prop="dataSource" :formatter="dataSourceChange"/>
            <el-table-column label="分配方式" align="center" prop="allotWay" >
              <template slot-scope="scope">
                <dict-tag :options="dict.type.allot_way" :value="scope.row.allotWay"/>
              </template>
            </el-table-column>
            <el-table-column label="执行人" align="center" prop="executeName" />
<!--            <el-table-column label="巡查点" align="center" prop="updateBy" />-->
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="handleDetail(scope.row)"
                  v-hasPermi="['work:inspectLedger:query']"
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
            <el-form-item label="工单编号：">{{ form.inspectNo }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="工单名称：">{{ form.inspectName }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="巡检类型：">{{ form.tourName }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="工单来源类型：">
              <div v-if="form.dataSource == '0'">计划生成</div>
              <div v-if="form.dataSource == '1'">后台添加</div>
              <div v-if="form.dataSource == '2'">其他工单</div>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="来源工单：" >{{ form.orderNO }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="完成图片：" >
              <img style="width: 70px; height: 50px;padding-right: 5px" :src="form.imageUrl" />
            </el-form-item>
          </el-col>

        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="计划开始时间：">{{ form.startTime }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="计划结束时间：">{{form.endTime}}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="完成时间：">{{form.finishTime}}</el-form-item>
          </el-col>

        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="异常状态：">
              {{form.status}}
            </el-form-item>
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

        <h4 class="form-header" align="left">设备详情</h4>
        <el-row>
          <el-col :span="24">
            <el-table
              stripe
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
                label="设备类型"
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
import {
  detailList,
  getInspectInfo,
  getInsStaList,
  getTourNameList,
  listInsLed
} from "@/api/estate/work/orderLedger/insLed";
import {desensitization, equals} from "@/utils/common";
import orderRecord from "@/components/work/orderRecord";

export default {
  name: "inspectLedger",
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
      insLedList: [],
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
        tourName:null,
        allotWay:null,
        dataSource:null,
        executeName:null,
      },
      // 表单参数
      form: {},
      queryRecord: {
        orderType: '2',
        orderId: 0
      },
      staffList:[],
      ownerListData:[],
      tourNameList:[],
      deatilListThreeList: [],
    }
  },
  created() {
    this.getTree();
    //this.getList();
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
      this.open = false;
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
    },

    /** 查看按钮 */
    handleDetail(row) {
      this.openView = 1;
      this.open = true;
      this.title = "查看详情";
      const id = row.inspectId
      getInspectInfo(id).then(response => {
        this.form = response.data;
      });
      getInsStaList(id).then(response => {
          this.staffList = response.rows;
        }
      );
      detailList(id).then((response) => {
        this.ownerListData = response.data;
      });
      /*deatilListThree(id).then((response) => {
        this.deatilListThreeList = response.data;
      });*/
      this.queryRecord.orderId = id;
    },



    /** 表单重置*/
    reset() {
      this.form = {
        /*passageName: undefined,*/
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
      listInsLed(this.queryParams).then(response => {
        this.insLedList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      //getTourNameList
      getTourNameList(this.queryParams.communityId).then(response => {
        this.tourNameList = response.data;
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

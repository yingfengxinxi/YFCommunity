<template>
  <div class="app-container">
    <div v-show="openView">
      <el-row :gutter="20">
        <!--小区数据-->
        <el-col :span="4">
          <div class="head-container">
            <el-input
              clearable
              size="small"
              prefix-icon="el-icon-search"
              style="margin-bottom: 20px"
              placeholder="请输入小区名称"
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
            <el-form-item label="房间号" prop="roomName">
              <el-input
                v-model="queryParams.roomName"
                placeholder="请输入房间号"
                clearable
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="分配方式" prop="allotWay">
              <el-select v-model="queryParams.allotWay" size="small"
                         style="width: 100%" >
                <el-option value="0" label="自动分配"/>
                <el-option value="1" label="管理员分配"/>
                <el-option value="2" label="抢单"/>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>

          <el-table ref="multipleTable" v-loading="loading" :data="decorateOrderList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
<!--            <el-table-column label="工单编号" align="center" key="orderNo" prop="orderNo" width="180" :show-overflow-tooltip="true"/>-->
            <el-table-column label="业主姓名" align="center" key="ownerName" prop="ownerName" :show-overflow-tooltip="true"/>
            <el-table-column label="房间号" align="center" key="roomName" prop="roomName" :show-overflow-tooltip="true"/>
            <el-table-column label="地址" align="center" key="address" prop="address" width="200" :show-overflow-tooltip="true"/>
            <el-table-column label="分配方式" align="center" key="allotWay" prop="allotWay" :show-overflow-tooltip="true">
              <template slot-scope="scope">
                <span v-if="scope.row.allotWay === '0'">自动分配</span>
                <span v-else-if="scope.row.allotWay === '1'">管理员分配</span>
                <span v-else-if="scope.row.allotWay === '2'">抢单</span>
              </template>
            </el-table-column>
            <el-table-column label="操作人" align="center" key="updateBy" prop="updateBy" :show-overflow-tooltip="true"/>
            <el-table-column label="最后操作时间" align="center" prop="updateTime" width="160" :show-overflow-tooltip="true"/>
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
                  v-hasPermi="['work:decLed:query']"
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
    <div v-if="!openView" v-show="detailShow">
      <el-form ref="detailForm" :model="detailForm" label-width="140px">
        <h4 class="form-header">工单详情</h4>

        <el-row>
          <el-col :span="5" :offset="2">
            <el-form-item label="工单编号:">{{ detailForm.orderNo }}</el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="工单名称:">{{ detailForm.orderName }}</el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="工单类型:">装修评定工单</el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="5" :offset="2">
            <el-form-item label="开始时间:">{{ detailForm.startTime }}</el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="结束时间:">{{ detailForm.endTime }}</el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="完成时间:">{{ detailForm.finishTime }}</el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="5" :offset="2">
            <el-form-item label="异常状态:">{{ detailForm.abnormalType }}</el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="主要负责人:">{{ detailForm.staffName }}</el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="完成图片：">
              <div style="width: 280px">
                <img style="width: 80px; height: 50px;vertical-align: middle" :src="detailForm.imageUrl" />
              </div>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12" :offset="2">
            <el-form-item label="备注:">{{ detailForm.remark }}</el-form-item>
          </el-col>
        </el-row>

        <h4 class="form-header">工单协助人信息</h4>

        <el-row>
          <el-table :data="staffList" >
            <el-table-column label="人员姓名" align="center" key="staffName" prop="staffName"/>
            <el-table-column label="手机号" align="center" key="staffPhone" prop="staffPhone"/>
<!--              <template scope="scope">
                <span>{{ phoneFormatter(scope.row.staffPhone) }}</span>
              </template>
            </el-table-column>-->
            <el-table-column label="身份证号" align="center" key="cardNo" prop="cardNo"/>
<!--              <template scope="scope">
                <span>{{ cardFormatter(scope.row.cardNo) }}</span>
              </template>
            </el-table-column>-->
            <el-table-column label="人员类型" align="center" key="workPost" prop="workPost">
              <template slot-scope="scope">
                <span v-if="scope.row.workPost === '0'">安防人员</span>
                <span v-else-if="scope.row.workPost === '1'">维修人员</span>
                <span v-else-if="scope.row.workPost === '2'">装修人员</span>
                <span v-else-if="scope.row.workPost === '3'">场馆人员</span>
                <span v-else-if="scope.row.workPost === '4'">保洁人员</span>
                <span v-else-if="scope.row.workPost === '5'">志愿者</span>
              </template>
            </el-table-column>
          </el-table>
        </el-row>

        <orderRecord :father="queryRecord"></orderRecord>

        <div style="float: right">
          <el-button @click="canceldetail">关 闭</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import {getDecorateOrderDetail, listDecorateOrder} from "@/api/estate/work/orderLedger/decLed";
import {getCurrentList} from "@/api/estate/community";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {desensitization, equals} from "@/utils/common";
import orderRecord from '@/components/work/orderRecord';

export default {
  name: "decorateLedger",
  components: { orderRecord },
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
      // 树筛选文本
      filterText:'',
      // 显示搜索条件
      showSearch: true,
      //工单协助人信息
      staffList: [],
      //装修列表
      decorateList: [],
      shareNameList: [],
      //小区数据集合
      currentList: [],
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      // 总条数
      total: 0,
      // 装修表格数据
      decorateOrderList: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openDistribution: false,
      // 默认密码
      initPassword: undefined,
      // 表单参数
      form: {},
      distributionform: {},
      queryRecord: {
        orderType: '4',
        orderId: 0
      },
      // 表单校验
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ownerName: undefined,
        roomName: undefined,
        allotWay: undefined
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
      listDecorateOrder(this.queryParams).then(response => {
          this.decorateOrderList = response.rows;
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
    cancelDistribution() {
      this.openDistribution = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        ownerName: undefined,
        allotWay: undefined,
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

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.orderId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },

    /** 查看详情按钮操作 */
    handleDetail(row) {
      this.reset();
      const orderId = row.orderId;
      getDecorateOrderDetail(orderId).then(response => {
        this.detailForm = response.data;
        this.staffList = response.staffList;
      });
      this.openView = false
      this.detailShow = true
      this.queryRecord.orderId = orderId;
    },

    //关闭详情页
    canceldetail() {
      this.detailForm = {}
      this.detailShow = false
      this.openView = true;
    },

    /** 手机号脱敏 */
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

    // 装修申请监听
    decorateChange(item) {
      getDecorateDetail(item).then((response) => {
        this.form.ownerName = response.data.ownerName;
        this.form.roomName = response.data.roomName;
        this.form.address = response.data.address;
      });
    },


    /** 导出按钮操作 */
    handleExport() {
      //清空多选按钮
      this.$refs.multipleTable.clearSelection();
      this.queryParams.ids = this.ids;
      this.download('estate/decorateOrder/export', {
        ...this.queryParams
      }, `装修评定工单.xlsx`)
    },
  }
};
</script>

<style scoped>

/*设置高亮颜色*/
/deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #b3f1b6 !important;
}
.content {
  font-size: 14px;
}
</style>

<template>
  <div class="app-container">
    <div v-show="openView">
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
            <el-form-item label="工单名称" prop="cleanName">
              <el-input
                v-model="queryParams.cleanName"
                placeholder="请输入工单名称"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="负责人" prop="staffName">
              <el-input
                v-model="queryParams.staffName"
                placeholder="请输入负责人"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            <el-form-item  style="margin-left: 5px">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>

          <el-table v-loading="loading" :data="cleLedList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="工单编号" align="center" prop="cleanNo" :show-overflow-tooltip='true'/>
            <el-table-column label="工单名称" align="center" prop="cleanName" :show-overflow-tooltip='true'/>

            <el-table-column label="工单来源" align="center" prop="dataSource" :formatter="dataSourceChange"/>
            <el-table-column label="保洁地址" align="center" prop="address" :show-overflow-tooltip='true'/>
<!--            <el-table-column label="分配方式" align="center" prop="allotWay" >
              <template slot-scope="scope">
                <dict-tag :options="dict.type.allot_way" :value="scope.row.allotWay"/>
              </template>
            </el-table-column>-->
            <el-table-column label="执行人" align="center" prop="staffName" />
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="handleDetail(scope.row)"
                  v-hasPermi="['work:cleLed:query']"
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
    <div v-if="!openView" v-show="detailShow">
      <el-form ref="form" :model="form" label-width="110px">
        <h4 class="form-header">工单详情</h4>
        <el-row>
          <el-col :span="6" :offset="1" >
            <el-form-item label="工单编号：">{{ form.cleanNo }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="工单名称：">{{ form.cleanName }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="工单来源类型：">
              <div v-if="form.dataSource == '0'">计划生成</div>
              <div v-if="form.dataSource == '1'">后台添加</div>
              <div v-if="form.dataSource == '2'">其他工单</div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
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
import {getCleLedInfo, getCleStaList, listCleLed} from "@/api/estate/work/orderLedger/cleLed";
import orderRecord from '@/components/work/orderRecord';

export default {
  name: "cleanLedger",
  dicts: ['allot_type','allot_way','work_post'],
  components: { orderRecord },
  data(){
    return{
      openView:true,
      //详情页是否展示
      detailShow: false,
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
      cleLedList: [],
      classifyList:[],
      //小区数据集合
      currentList: [],
      queryRecord: {
        orderType: '3',
        orderId: 0
      },
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
        cleanName: null,
        staffName: null,
      },
      // 表单参数
      form: {},
      staffList:[],
      deatilListThreeList: [],
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
      this.detailShow = false
      this.openView = true
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
      this.open = true;
      this.title = "查看详情";
      const id = row.cleanId
      getCleLedInfo(id).then(response => {
        this.form = response.data;
      });
      getCleStaList(id).then(response => {
          this.staffList = response.rows;
        }
      );
      this.openView = false
      this.detailShow = true
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
      this.loading = false;
      listCleLed(this.queryParams).then(response => {
        this.cleLedList = response.rows;
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

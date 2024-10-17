<template>
  <div class="app-container">
    <div v-show="openView">
      <el-row :gutter="20">
        <!--小区数据-->
        <el-col :span="4">
          <div class="head-container">
            <el-input
              placeholder="请输入小区名称" size="small"
              prefix-icon="el-icon-search" style="margin-bottom: 20px"
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
                style="width: 100%"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="房间号" prop="roomName">
              <el-input
                v-model="queryParams.roomName"
                placeholder="请输入房间号"
                clearable
                style="width: 100%"
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
            <el-form-item label="评定结果" prop="assessResult">
              <el-select v-model="queryParams.assessResult" size="small"
                         style="width: 100%" >
                <el-option value="0" label="待评定"/>
                <el-option value="1" label="合格"/>
                <el-option value="2" label="违规"/>
                <el-option value="3" label="整改完成"/>
              </el-select>
            </el-form-item>
            <el-form-item label="执行状态" prop="executeStatus">
              <el-select v-model="queryParams.executeStatus" size="small"
                         style="width: 100%" >
                <el-option value="0" label="待执行"/>
                <el-option value="1" label="执行中"/>
                <el-option value="2" label="已完成"/>
                <el-option value="3" label="超时完成"/>
                <el-option value="4" label="超时未完成"/>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>

          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button
                type="primary"
                plain
                icon="el-icon-plus"
                size="mini"
                @click="handleAdd"
                v-hasPermi="['work:decorateOrder:add']"
              >新增</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="success"
                plain
                icon="el-icon-edit"
                size="mini"
                @click="handleExport"
                v-hasPermi="['work:decorateOrder:export']"
              >导出</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="danger"
                plain
                icon="el-icon-delete"
                size="mini"
                :disabled="multiple"
                @click="handleDelete"
                v-hasPermi="['work:decorateOrder:delete']"
              >删除</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
          </el-row>

          <el-table ref="multipleTable" v-loading="loading" :data="decorateOrderList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" :selectable="selectable"/>
            <el-table-column label="工单编号" align="center" key="orderNo" prop="orderNo" :show-overflow-tooltip="true"/>
            <el-table-column label="业主姓名" align="center" key="ownerName" prop="ownerName" :show-overflow-tooltip="true"/>
            <el-table-column label="房间号" align="center" key="roomName" prop="roomName" :show-overflow-tooltip="true"/>
            <el-table-column label="地址" align="center" key="address" prop="address" :show-overflow-tooltip="true"/>
            <el-table-column label="分配方式" align="center" key="allotWay" prop="allotWay" :show-overflow-tooltip="true">
              <template slot-scope="scope">
                <span v-if="scope.row.allotWay === '0'">自动分配</span>
                <span v-else-if="scope.row.allotWay === '1'">管理员分配</span>
                <span v-else-if="scope.row.allotWay === '2'">抢单</span>
              </template>
            </el-table-column>
            <el-table-column label="操作人" align="center" key="updateBy" prop="updateBy" :show-overflow-tooltip="true"/>
            <el-table-column label="最后操作时间" align="center" prop="updateTime" width="160" :show-overflow-tooltip="true"/>
            <el-table-column label="评定结果" align="center" key="assessResult" prop="assessResult" :show-overflow-tooltip="true">
              <template slot-scope="scope">
                <span v-if="scope.row.assessResult === '0'">待评定</span>
                <span v-else-if="scope.row.assessResult === '1'">合格</span>
                <span v-else-if="scope.row.assessResult === '2'">违规</span>
                <span v-else-if="scope.row.assessResult === '3'">整改完成</span>
              </template>
            </el-table-column>
            <el-table-column label="分配状态" align="center" key="allotStatus" prop="allotStatus" :show-overflow-tooltip="true">
              <template slot-scope="scope">
                <span v-if="scope.row.allotStatus === '0'">待分配</span>
                <span v-else-if="scope.row.allotStatus === '1'">已分配</span>
              </template>
            </el-table-column>
            <el-table-column label="接单状态" align="center" key="acceptStatus" prop="acceptStatus" :show-overflow-tooltip="true">
              <template slot-scope="scope">
                <span v-if="scope.row.acceptStatus === '0'">待接单</span>
                <span v-else-if="scope.row.acceptStatus === '1'">已接单</span>
              </template>
            </el-table-column>
            <el-table-column label="执行状态" align="center" key="executeStatus" prop="executeStatus" :show-overflow-tooltip="true">
              <template slot-scope="scope">
                <span v-if="scope.row.executeStatus === '0'">待执行</span>
                <span v-else-if="scope.row.executeStatus === '1'">执行中</span>
                <span v-else-if="scope.row.executeStatus === '2'">已完成</span>
                <span v-else-if="scope.row.executeStatus === '3'">超时完成</span>
                <span v-else-if="scope.row.executeStatus === '4'">超时未完成</span>
              </template>
            </el-table-column>
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
                  v-hasPermi="['work:decorateOrder:detail']"
                >查看</el-button>
                <el-button v-if="scope.row.allotStatus === '0'"
                  size="mini"
                  type="text"
                  class="text-info"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['work:decorateOrder:update']"
                >修改</el-button>
                <el-button v-if="scope.row.allotStatus === '0'"
                  size="mini"
                  type="text"
                  class="text-info"
                  icon="el-icon-edit"
                  @click="handleDistribution(scope.row)"
                  v-hasPermi="['work:decorateOrder:distribution']"
                >分配</el-button>
                <el-button v-if="scope.row.assessResult === '1' || scope.row.assessResult === '3'"
                  size="mini"
                  type="text"
                  class="text-danger"
                  icon="el-icon-delete"
                  style="color: #f56c6c"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['work:decorateOrder:delete']"
                >删除</el-button>
                <el-button v-if="scope.row.assessResult === '2'"
                  size="mini"
                  type="text"
                  class="text-info"
                  icon="el-icon-edit"
                  @click="handleRectification(scope.row)"
                  v-hasPermi="['work:decorateOrder:rectification']"
                >整改</el-button>
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
            <!-- 评定结果：0=待评定，1=合格，2=违规，3=整改完成 -->
            <el-form-item label="评定结果:">{{ formatAssessResult(detailForm.assessResult) }}</el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="主要负责人:">{{ detailForm.staffName }}</el-form-item>
          </el-col>
          <el-col :span="5" :offset="2" v-if="detailForm.imageUrl !== '' && detailForm.imageUrl !== undefined && detailForm.imageUrl !== null">
            <el-form-item label="完成图片:">
              <img class="image" :src="detailForm.imageUrl" style="width: 150px; height: 100px;vertical-align: middle"/>
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
            <el-table-column label="身份证号" align="center" key="cardNo" prop="cardNo"/>
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

    <!-- 添加或修改岗位对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="工单名称:" prop="orderName">
          <el-input v-model="form.orderName" placeholder="工单名称"/>
        </el-form-item>
        <el-form-item label="装修申请:" prop="decorateId">
          <el-select
            v-model="form.decorateId"
            clearable
            placeholder="请选择装修申请"
            @change="decorateChange"
          >
            <el-option
              v-for="item in decorateList"
              :key="item.decorateId"
              :label="item.decorateName"
              :value="item.decorateId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="业主姓名:" prop="ownerName">
          <el-input v-model="form.ownerName" readonly/>
        </el-form-item>
        <el-form-item label="房间号:" prop="roomName">
          <el-input v-model="form.roomName" readonly/>
        </el-form-item>
        <el-form-item label="地址:" prop="address">
          <el-input v-model="form.address" readonly/>
        </el-form-item>
        <!--备注-->
<!--        <el-form-item label="备注:" prop="remark">
&lt;!&ndash;          <el-input v-model="form.remark" readonly/>&ndash;&gt;
          <el-input
            type="textarea"
            :rows="2"
            placeholder="请输入内容"
            v-model="form.remark">
          </el-input>
        </el-form-item>-->

        <el-form-item label="结束时间: " prop="endTime">
          <el-date-picker
            v-model="form.endTime"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="datetime"
            placeholder="请选择结束时间"
            :picker-options="pickerOptions">
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="装修评定标准:" label-width="120px">
          <span>住户装修评定标准</span>
        </el-form-item>
        <el-form-item label="住户是否损坏公共区域:" label-width="170px" prop="damageStatus">
          <el-radio-group v-model="form.damageStatus" >
            <el-radio v-model="form.damageStatus" label="1">是</el-radio>
            <el-radio v-model="form.damageStatus" label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="住户装修楼道整洁度:" label-width="170px" prop="neatStatus">
          <el-radio-group v-model="form.neatStatus" >
            <el-radio v-model="form.neatStatus" label="1">是</el-radio>
            <el-radio v-model="form.neatStatus" label="0">否</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>

    <el-dialog :title="title" :visible.sync="openDistribution" width="500px" append-to-body>
      <el-form ref="form" :model="distributionform" label-width="100px">
        <el-form-item label="负责人: " prop="staffId">
          <el-select
            v-model="distributionform.staffId"
            clearable
            placeholder="请选择负责人"
          >
            <el-option
              v-for="item in shareNameList"
              :key="item.staffId"
              :label="item.staffName"
              :value="item.staffId"
            >
            </el-option>
          </el-select>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormDistribution">确 定</el-button>
        <el-button @click="cancelDistribution">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listDecorateOrder, addDecorateOrder, getDecorateOrderDetail, deleteDecorateOrder, decorateOrderDistribution, getDecorateList, getDecorateDetail, rectificationDecorateOrder, getUpdateDetail, updateDecorateOrder, getShareNameList } from "@/api/estate/work/fitment/decorateOrder";
import { getCurrentList } from "@/api/estate/community";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {desensitization, equals} from "@/utils/common";
import orderRecord from '@/components/work/orderRecord';

export default {
  name: "DecorateOrder",
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
      rules: {
        orderName: [
          { required: true, message: "工单名称不能为空", trigger: "blur" }
        ],
        damageStatus: [
          { required: true, message: "住户是否损坏公共区域不能为空", trigger: "blur" }
        ],
        neatStatus: [
          { required: true, message: "住户装修楼道整洁度不能为空", trigger: "blur" }
        ]
      },
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        }
      },
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ownerName: undefined,
        assessResult: undefined,
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
    formatAssessResult(assessResult){
      if (assessResult === '0'){
        return "待评定";
      }
      if (assessResult === '1'){
        return "合格";
      }
      if (assessResult === '2'){
        return "违规";
      }
      if (assessResult === '3'){
        return "整改完成";
      }
    },
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
        assessResult: undefined,
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

    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      getDecorateList(this.queryParams.communityId).then(response => {
        this.decorateList = response.data;
      });
      this.open = true;
      this.title = "新建装修评定工单";
    },

    /** 分配按钮操作 */
    handleDistribution(row) {
      this.reset();
      getShareNameList(row.communityId).then(response => {
        this.distributionform.orderId = row.orderId;
        this.shareNameList = response.data;
      });
      this.openDistribution = true;
      this.title = "分配";
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

    //新增/修改 提交
    submitForm: function () {
      if (this.form.decorateId === undefined) {
        this.$modal.msgError(`装修申请不能为空!`);
        return;
      }
      if (this.form.endTime === undefined) {
        this.$modal.msgError(`结束时间不能为空!`);
        return;
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.orderId != undefined) {
            updateDecorateOrder(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addDecorateOrder(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

    // 设置多选是否可选择
    selectable(row) {
      return row.assessResult === '1' || row.assessResult === '3';
    },

    // 装修申请监听
    decorateChange(item) {
      getDecorateDetail(item).then((response) => {
        this.form.ownerName = response.data.ownerName;
        this.form.roomName = response.data.roomName;
        this.form.address = response.data.address;
      });
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const orderIds = row.orderId || this.ids;
      this.$modal.confirm('是否确认删除？').then(function() {
        return deleteDecorateOrder(orderIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },

    /** 整改按钮操作 */
    handleRectification(row) {
      const orderId = row.orderId;
      this.$modal.confirm('是否确认整改？').then(function() {
        return rectificationDecorateOrder(orderId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("整改成功");
      }).catch(() => {});
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const orderId = row.orderId;
      getUpdateDetail(orderId,this.queryParams.communityId).then(response => {
        this.form = response.data;
        this.decorateList = response.decorateList;
        this.open = true;
        this.title = "修改装修评定工单";
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

    /** 分配 */
    submitFormDistribution: function () {
      if (this.distributionform.staffId === undefined) {
        this.$modal.msgError(`负责人不能为空!`);
        return;
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          decorateOrderDistribution(this.distributionform).then(response => {
            this.$modal.msgSuccess("分配成功");
            this.openDistribution = false;
            this.getList();
          });
        }
      });
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

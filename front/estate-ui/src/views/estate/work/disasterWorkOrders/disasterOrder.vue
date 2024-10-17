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
              v-model="filterText"
            >
            </el-input>

            <el-tree
              class="filter-tree"
              :data="currentList"
              :props="currentListProps"
              :filter-node-method="filterNode"
              icon-class="el-icon-star-on"
              @node-click="handleNodeClick"
              highlight-current
              ref="tree"
            >
            </el-tree>
          </div>
        </el-col>
        <!--   右侧   -->
        <el-col :span="20" :xs="24">
          <el-form
            :model="queryParams"
            ref="queryForm"
            size="small"
            :inline="true"
            v-show="showSearch"
            label-width="82px"
          >
            <el-form-item label="工单名称" prop="orderName">
              <el-input
                v-model.trim="queryParams.orderName"
                placeholder="请输入工单名称"
                clearable
                size="small"
                style="width: 240px"
              />
            </el-form-item>
            <el-form-item label="灾害类型" prop="disasterName">

             <el-select
                v-model.trim="queryParams.disasterId"
                placeholder="请选择灾害类型"
                clearable
                popper-class="select-dropdown"
              >
                <el-option
                      v-for="item in typeList"
                      :key="item.disasterId"
                      :label="item.disasterName"
                      :value="item.disasterId"
                    ></el-option>
                <!-- <el-option label="一般灾害" value="0" />
                <el-option label="后台添加" value="1" />
                <el-option label="其他工单" value="2" /> -->
              </el-select>
            </el-form-item>

            <el-form-item label="主要负责人" prop="staffName" style="margin-left:1%">
              <el-input
                v-model.trim="queryParams.staffName"
                placeholder="请输入主要负责人"
                clearable
                size="small"
                style="width: 240px"
              />
            </el-form-item>
            <el-form-item
              label="状态"
              prop="executeStatus"

            >
              <el-select
                v-model.trim="queryParams.executeStatus"
                placeholder="状态"
                clearable
                size="small"
                style="width: 240px"
              >
                <el-option label="待执行" value="0" />
                <el-option label="执行中" value="1" />
                <el-option label="已完成" value="2" />
                <el-option label="超时完成" value="3" />
                <el-option label="超时未完成" value="4" />
                <el-option label="待分配" value="5" />
                <el-option label="待接单" value="6" />
                <el-option label="转交中" value="7" />
              </el-select>
            </el-form-item>
            <el-form-item style="margin-left: 80px">
              <el-button
                type="primary"
                icon="el-icon-search"
                size="mini"
                @click="handleQuery"
                >搜索</el-button
              >
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
                >重置</el-button
              >
            </el-form-item>
          </el-form>

          <el-table
            v-loading="loading"
            :data="patLedList"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="工单编号" align="center" prop="orderNo" show-overflow-tooltip/>
            <el-table-column label="工单名称" align="center" prop="orderName" show-overflow-tooltip/>
            <el-table-column
              label="灾害类型"
              align="center"
              prop="disasterName"
            />
            <el-table-column
              label="分配方式"
              align="center"
              prop="allotWay"
              :formatter="assignmentFormatter"
            />
            <el-table-column label="主要负责人员" align="center" prop="staffName">
            </el-table-column>

            <!-- <el-table-column
              label="接单时间"
              align="center"
              prop="executeTime"
            /> -->
            <el-table-column label="分配状态" align="center" prop="allotStatus" :formatter="allotStatusFormat"/>
          <el-table-column label="接单状态" align="center" prop="acceptStatus" :formatter="acceptStatusFormat"/>
           <el-table-column label="执行状态" align="center" prop="executeStatus" :formatter="executeStatusFormat"/>
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
                  >查看</el-button
                >
                <el-button
                  size="mini"
                  type="text"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['work:disasterOrder:updateOrAdd']"
                  ><el-link
                    icon="el-icon-edit"
                    type="success"
                    :underline="false"
                    v-if="scope.row.allotStatus == '0'"
                    >修改</el-link
                  ></el-button
                >
                <el-button
                  size="mini"
                  type="text"
                  v-hasPermi="['work:disasterOrder:shareUpdate']"
                  @click="handleassignment(scope.row)"
                  ><el-link
                    icon="el-icon-plus"
                    type="danger"
                    :underline="false"
                    v-if="scope.row.allotStatus == '0'"
                    >分配</el-link
                  ></el-button
                >
                <el-button
                  size="mini"
                  type="text"
                  v-hasPermi="['work:disasterOrder:deleteByOrder/{orderId}']"
                  @click="handleDelete(scope.row)"
                  ><el-link
                    icon="el-icon-delete"
                    type="danger"
                    v-if="scope.row.allotStatus == '0'"
                    :underline="false"
                    >删除</el-link
                  ></el-button
                >
              </template>
            </el-table-column>
          </el-table>

          <pagination
            v-show="total > 0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
          />
        </el-col>
      </el-row>
    </div>
    <!-- 分配页面 -->
    <el-dialog
      :visible.sync="shareOpen"
      width="630px"
      @close="shareClose"
      title="分配"
    >
      <el-form
        ref="addForm"
        :rules="shareRules"
        :model="shareForm"
        label-width="130px"
      >
        <el-row>
          <el-col>
            <el-form-item label="执行人: " prop="staffId">
              <el-select
                v-model="shareForm.staffId"
                clearable
                placeholder="请选择执行人"
              >
                <el-option
                  v-for="item in getShareNameList"
                  :key="item.staffId"
                  :label="item.staffName"
                  :value="item.staffId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col>
              <el-form-item label="计划开始时间: " prop="planStart">
                <el-date-picker
                  v-model="shareForm.planStart"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  type="datetime"
                  placeholder="选择计划开始时间"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="计划结束时间: " prop="planEnd">
                <el-date-picker
                  v-model="shareForm.planEnd"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  type="datetime"
                  placeholder="选择计划结束时间"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
        </el-row>
        <el-row>
          <el-col :offset="10" :span="2">
            <div style="width: 280px; margin-left: -40px">
              <el-button type="primary" @click="shareSuccess">确定</el-button>
              <el-button @click="shareClose">取消</el-button>
            </div>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>
    <!-- 详情 -->
    <div v-show="openView === 1">
      <el-button size="middle" style="margin-left: 80%" @click="close"
      >关 闭</el-button>
      <el-form ref="form" :model="form" label-width="140px">
        <h4 class="form-header">工单详情</h4>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="工单编号：">{{ form.orderNo }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="工单名称：">{{ form.orderName }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="工单类型：">{{ "灾害报事工单" }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="报事类型：">{{
              form.disasterName
            }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="报事地址：">{{ form.address }}</el-form-item>
          </el-col>
        </el-row>
        <el-descriptions
          :label-style="{ 'line-height': '40px' }"
          :content-style="{ 'line-height': '40px' }"
          style="margin-left: 50px"
          :column="1"
        >
          <el-descriptions-item :span="1">
            <el-row>
              <el-col>
                <el-form-item label="上传图片：">
                  <el-image
                    :src="image"
                    fit="fit"
                    class="image"
                    v-for="(image, index) in form.imageList"
                    :key="index"
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </el-descriptions-item>
        </el-descriptions>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="计划开始时间：">{{
              form.planStart
            }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="计划结束时间：">{{
              form.planEnd
            }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="完成时间：">{{
              form.finishTime
            }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="业主评价：">
              <div style="width: 280px; float: left">
                {{ form.complaintType }}
                <!-- <el-button type="text" @click="dialogVisible=true" style="margin-left:10px" v-if="form.yc==1">查看异常</el-button> -->
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="提交人备注：">{{ form.remark }}</el-form-item>
          </el-col>
        </el-row>

        <orderAssist :father="queryRecord" v-if="show"/>
        <h4 class="form-header" align="left">执行详情</h4>
        <el-card class="box-card">
            <el-timeline :reverse="reverse">
              <el-timeline-item
                v-for="(item, index) in detailRecordList"
                :key="index"
                color='rgb(179, 241, 182)'
                :timestamp="item.createTime">
                  <span>{{item.orderResult}}</span> <span style="margin-left: 20px">操作人:{{item.userName}}</span>
              </el-timeline-item>
            </el-timeline>
          </el-card>
      </el-form>

    </div>
    <!-- 新增和修改页面 -->
    <el-dialog
      :visible.sync="AddOrUpateOpen"
      width="630px"
      @close="AddOrUpateClose"
      :title="title"
    >
      <div>
        <el-form
          ref="AddOrUpateForm"
          :rules="AddOrUpateRules"
          :model="AddOrUpateForm"
          label-width="130px"
        >
          <el-row>
            <el-col>
              <el-form-item label="工单名称: " prop="orderName">
                <el-input
                  clearable
                  v-model="AddOrUpateForm.orderName"
                  placeholder="请输入工单名称"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="灾害类型: " prop="disasterName">
                <el-input
                  clearable
                  v-model="AddOrUpateForm.disasterName"
                  placeholder="请输入灾害类型"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="计划开始时间: " prop="planStart">
                <el-date-picker
                  v-model="AddOrUpateForm.planStart"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  type="datetime"
                  placeholder="选择计划开始时间"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="计划结束时间: " prop="planEnd">
                <el-date-picker
                  v-model="AddOrUpateForm.planEnd"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  type="datetime"
                  placeholder="选择计划结束时间"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="完成时间: " prop="finishTime">
                <el-date-picker
                  v-model="AddOrUpateForm.finishTime"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  type="datetime"
                  placeholder="选择计划结束时间"
                >
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="备注: " prop="remark">
                <el-input
                  v-model="AddOrUpateForm.remark"
                  type="textarea"
                  placeholder="请输入内容"
                  maxlength="1000"
                  show-word-limit
                />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :offset="3" :span="8"> </el-col>
          </el-row>
          <el-row>
            <el-col :offset="10" :span="2">
              <div style="width: 280px">
                <el-button type="primary" @click="addSuccess(AddOrUpateForm)"
                  >确定</el-button
                >
                <el-button @click="AddOrUpateClose">取消</el-button>
              </div>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-dialog>
    <!-- 异常对话框 -->
    <el-dialog title="查看异常" :visible.sync="dialogVisible" width="40%">
      <el-timeline>
        <el-timeline-item
          :timestamp="parseTime(item.create_time)"
          placement="top"
          v-for="(item, index) of errorList"
          :key="index"
        >
          <el-card>
            <el-descriptions
              title="工单异常"
              :column="1"
              :label-style="{ 'line-height': '40px' }"
              :content-style="{ 'line-height': '40px' }"
            >
              <el-descriptions-item label="异常描述">{{
                item.abnormal_info
              }}</el-descriptions-item>
              <el-descriptions-item label="异常图片">
                <el-image
                  :src="item.image_url"
                  fit="contain"
                  style="width: 300px; height: 200px"
                />
              </el-descriptions-item>
            </el-descriptions>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </el-dialog>
  </div>
</template>

<script>
import { getCurrentList } from "@/api/estate/community";
import {
  listDisasterOrder,
  detailList,
  saveOrUpdate,
  updateBusDisaster,
  shareUpdate,
  deleteDisasterId,
  recordInfo,
} from "@/api/estate/work/orderDisaster/DisasterOrder";
import { desensitization, equals } from "@/utils/common";
import { detailRecordGeneral } from "@/api/estate/work/orderDisaster/DisasterOrder";
import {getOrderType} from "@/api/estate/work/orderDisaster/DisasterLedger";
import { queryError } from "@/api/estate/work/orderDisaster/BusDisasterAudit";
import { listLeisureBusyStaffs } from "@/api/estate/manage/staff/staff";
import orderAssist from "@/components/work/orderAssist";
export default {
  name: "patrolLedger",
  dicts: ["allot_type", "allot_way"],
  components: {
    orderAssist
  },
  data() {
    return {
      show: false,
      // 协助人查询参数对象
      queryRecord: {
        orderType: '8',
        orderId: 0
      },
       reverse:true,
      stara:require('../../../../assets/images/start_light.png'), //亮星星
      starb:require('../../../../assets/images/start_grey.png'), //暗星星
      list:[0,1,2,3,4],
      dialogVisible: false,
      //执行详情列表
      recordInfoList: [],
      //  转让时获取转让人ID
      getShareNameList: [],
      // 分配弹窗
      shareForm: {},
      //时间
      dateRange:[],
      shareRules: {
        staffId: [
          {required: true, message: "负责人不能为空",  trigger: ["blur", "change"]}
        ],
        planStart: [
          { required: true, message: "请选择计划开始时间", trigger: ["blur", "change"] }
        ],
        planEnd: [
          { required: true, message: "请选择计划结束时间", trigger: ["blur", "change"] }
        ],
      },
      //分配方式弹窗
      shareOpen: false,
      openView: false,
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
      patLedList: [],
      classifyList: [],
      //小区数据集合
      currentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 树筛选文本
      filterText: "",
      //树结构
      currentListProps: {
        children: "communityId",
        label: "communityName",
      },
      // 状态数据字典
      statusOptions: [],
      //灾害类型
      disasterOptions: [],

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        orderName: undefined,
        disasterId: '',
        allotWay: null,
        dataSource: null,
        executeStatus: undefined,
        staffName: '',
        tourName: null,
      },
      typeList:[],
      // 表单参数
      form: {},
      detailRecordList:[],
      AddOrUpateForm: {},
      staffList: [],
      tourNameList: [],
      //添加和修改表单
      AddOrUpateOpen: false,
      // 新增表单校验
      AddOrUpateRules: {
        // carportNo: [
        //   { required: true, message: "车位编号不能为空", trigger: "blur" },
        //
      },
      errorList: [],
      ycStatus: {
        0: "正常",
        1: "异常",
      },
      execute: {
        0: "未评价",
        1: "已评价",
      },
      drawer: false,
      info: {},
    };
  },
  created() {
    this.getTree();
    this.getDicts("sys_oldman_auditstatus").then((response) => {
      this.statusOptions = response.data;
    });
    this.getList();
    this.getOrder();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
  methods: {
     clickStars(i){
      this.xing = i+1;
    },

    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(row) {
      this.queryParams.communityId = row.communityId;
      // this.communityId = row.communityId;
      this.getList();
    },
    // 获取树数据
    getTree() {
      getCurrentList().then((res) => {
        this.currentList = res.data;
        this.queryParams.communityId = this.currentList[0].communityId;
        // this.communityId = this.currentList[0].communityId;

        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.currentList[0].communityId);
        });
          this.getList();
      });
    },

    // 打开分配弹窗
    handleassignment(row) {
      let query = {
        allotType: "8",
        communityId: row.communityId,
      };
      this.shareOpen = true;
      this.shareForm.orderId = row.orderId;
      this.shareForm.communityId = row.communityId;
      listLeisureBusyStaffs(query).then((res) => {
        this.getShareNameList = res.data;
      });
    },
    //关闭添加和修改弹窗
    AddOrUpateClose() {
      this.AddOrUpateOpen = false;
      this.reset();
    },
    // 关闭分配弹窗
    shareClose() {
      this.shareOpen = false;
      this.getList();
    },

    // 关闭按钮
    close() {
      this.open = false;
      this.openView = 0;
      this.queryRecord = {orderType: '8', orderId: 0};
      this.show = false;
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
    selectView(row) {
      let params = {
        orderId: row.orderId,
      };
      let query = {
        orderType: "8",
        orderId: row.orderId,
      };
      detailList(params).then((response) => {
        this.staffList = response.data;
      });
      //执行详情
      recordInfo(params.orderId).then((response) => {
        this.recordInfoList = response.data;
      });
    },
    // 清空AddOrUpateForm表单
    addFormEmpty() {
      this.AddOrUpateForm.orderName = "";
      this.AddOrUpateForm.disasterName = "";
      this.AddOrUpateForm.planStart = "";
      this.AddOrUpateForm.planEnd = "";
      this.AddOrUpateForm.finishTime = "";
      this.AddOrUpateForm.remark = "";
    },

    //新增和修改
    addSuccess(AddOrUpateForm) {
      this.$refs.AddOrUpateForm.validate((valid) => {
        if (valid) {
          delete this.AddOrUpateForm["AddOrUpateForm"];
          this.AddOrUpateForm = AddOrUpateForm;
          // this.addFormEmpty();
          //     this.getBlackList();
          //     this.addOpen = false;

          this.$confirm("是否确定提交?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              saveOrUpdate(this.AddOrUpateForm).then((response) => {
                this.form = response.data;
                this.AddOrUpateOpen = false;
                this.getList();
              });
              this.$message({
                type: "success",
                message: "提交成功!",
              });
              this.getList();
              this.addFormEmpty();
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消提交",
              });
            });
        }
      });
    },

    //删除
    handleDelete(row) {
      const orderId = row.orderId;
      this.$modal
        .confirm("是否确认删除数据项")
        .then(function () {
          return deleteDisasterId(orderId);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },

    /** 查看按钮 */
    handleDetail(row) {
      this.openView = 1;
      this.open = true;
      this.title = "查看详情";
      this.form = row;
      // 查询工单协助人信息
      this.queryRecord.orderId = row.orderId;
      this.show = true;
      //工单执行人信息
      this.selectView(row);
      queryError({ id: row.orderId }).then((e) => {
        this.errorList = e.data;
      });
      this.drawer = true;
      this.info = row;
      detailRecordGeneral({orderType:'8',orderId:row.orderId}).then(e=>{
          this.detailRecordList=e.data
      })
    },
    /** 手机号脱敏 */
    phoneFormatter(phone) {
      if (!equals(phone + "", undefined)) {
        return desensitization(phone + "", 3, 7);
      }
    },

    /** 身份证号脱敏 */
    cardFormatter(card) {
      if (!equals(card, undefined)) {
        return desensitization(card, 6, 14);
      }
    },
    /** 表单重置*/
    reset() {
      this.form = {
        /*passageName: undefined,*/
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      //this.getTree();
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("form");
      this.resetForm("queryForm");
      this.queryParams.communityId = undefined;
      this.queryParams.disasterName = undefined;
      this.queryParams.disasterId = undefined;
      this.resetForm("queryParams");
      this.getList();
      // this.handleQuery();
    },
    //修改按钮操作
    handleUpdate(row) {
      (this.title = "修改"), (this.AddOrUpateOpen = true);
      this.AddOrUpateForm = JSON.parse(JSON.stringify(row));
      this.reset();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.passageId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 查询列表 */
    getList() {
      this.openView = 0;
      this.loading = false;
      // this.queryParams.communityId = undefined;
      listDisasterOrder(this.queryParams).then((response) => {
        this.patLedList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getOrder(){
      getOrderType().then((res)=>{
        this.typeList=res.data;
      })
    },
    /**
     * 后台返回的状态改变
     */
    statusFormatter(row, column) {
      const status = row.auditStatus;
      if (status == 0) {
        return "审批中";
      } else if (status == 1) {
        return "已通过";
      } else if (status == 2) {
        return "已驳回";
      }
    },

    //分配类别
    assignmentFormatter(row, column) {
      const status = row.allotWay;
      if (status == 0) {
        return "自动分配";
      } else if (status == 1) {
        return "管理员分配";
      } else if (status == 2) {
        return "抢单";
      }
    },
    // //接单类别
    // acceptStatusFormatter(row, column) {
    //   const status = row.acceptStatus;
    //   if (status == 0) {
    //     return "待接单";
    //   } else if (status == 1) {
    //     return "接单";
    //   }
    // },

    //人员类别岗位：0=安防人员，1=维修人员，2=装修人员，3=场馆人员，4=保洁人员，5=志愿者
    workStatus(row, column) {
      const status = row.workPost;
      if (status == 0) {
        return "安防人员";
      } else if (status == 1) {
        return "维修人员";
      } else if (status == 2) {
        return "装修人员";
      } else if (status == 3) {
        return "场馆人员";
      } else if (status == 4) {
        return "保洁人员";
      } else if (status == 1) {
        return "志愿者";
      }
    },
 // 执行状态
    executeStatusFormat(row) {
      if (equals(row.executeStatus, '0')) {
        return '待执行';
      } else if (equals(row.executeStatus, '1')) {
        return '执行中';
      } else if (equals(row.executeStatus, '2')) {
        return '已完成';
      } else if (equals(row.executeStatus, '3')) {
        return '超时完成';
      }
      return '超时未完成';
    },
      // 分配状态
    allotStatusFormat(row) {
      if (equals(row.allotStatus, '0')) {
        return '待分配';
      }
      return '已分配';
    },
       // 接单状态
    acceptStatusFormat(row) {
      if (equals(row.acceptStatus, '0')) {
        return '待接单';
      }
      return '已接单';
    },
    // 分配弹窗确定
    shareSuccess() {
      this.$confirm("是否确定提交?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          shareUpdate(this.shareForm).then((response) => {
            this.shareOpen = false;
            this.getList();
          });
          this.$message({
            type: "success",
            message: "提交成功!",
          });
          this.getList();
          this.shareOpen = false;
          this.shareForm.userName = "";
        })
        .catch(() => {});
    },
  },
};
</script>

<style scoped>
/deep/.el-form--inline .el-form-item {
  margin-right: 0px !important;
}
.image {
  height: 100px;
  width: 100px;
}
.StarsWrap{
  list-style: none;
  width: 100%;
  height: 0.2rem;
  margin: 0.1rem 0;
  margin-left:-30px
}
.StarsWrap li{
  float: left;
  margin-right: 0.1rem;
}
.d2 {
    margin-top: 30px;
    float: right;
    margin-left: 20px;
}
.select-dropdown {
  max-width: 100%;
}
.select-dropdown .el-select-dropdown__item {
  overflow: visible;
  display: block;
}

</style>

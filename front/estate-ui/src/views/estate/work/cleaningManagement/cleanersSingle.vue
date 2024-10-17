<template>
  <div class="app-container">
    <div v-show="homeShow">
      <el-row :gutter="20">
        <!--部门数据-->
        <el-col :span="4" :xs="24">
          <div class="head-container">
            <el-input
              placeholder="请输入小区名称"
              clearable
              size="small"
              v-model="search"
              prefix-icon="el-icon-search"
              style="margin-bottom: 20px"
            />
          </div>
          <el-table
            ref="singleTable"
            type="index"
            highlight-current-row
            :data="viewDeptData"
            :show-header="false"
            @cell-click="listClick"
          >
            <el-table-column prop="communityName" />
          </el-table>
        </el-col>
        <el-col :span="20" :xs="24">
          <!-- 条件搜索 -->
          <el-form
            :model="queryParams"
            ref="queryForm"
            size="small"
            :inline="true"
          >
            <el-form-item label="工单编号" prop="cleanNo">
              <el-input
                v-model="queryParams.cleanNo"
                placeholder="请输入工单编号"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="工单名称" prop="cleanName">
              <el-input
                v-model="queryParams.cleanName"
                placeholder="请输入工单名称"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="负责人" prop="address">
              <el-input
                v-model="queryParams.address"
                placeholder="请输入负责人名称"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="状态" prop="executeStatus">
              <el-select
                v-model="queryParams.executeStatus"
                placeholder="请选择执行状态"
              >
                <el-option label="待执行" value="0" />
                <el-option label="执行中" value="1" />
                <el-option label="已完成" value="2" />
                <el-option label="超时完成" value="3" />
                <el-option label="超时未完成" value="4" />
              </el-select>
            </el-form-item>
            <el-form-item>
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

          <div>
            <!-- 权限 v-hasPermi="['estate:carport:remove']" -->
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              v-hasPermi="['estate:carport:add']"
              @click="addMethod"
            >新增</el-button
            >
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              v-hasPermi="['estate:carport:remove']"
              @click="handleDelete"
            >删除</el-button
            >
          </div>

          <!-- 列表展示 -->
          <el-table
            stripe
            v-loading="loading"
            :data="injList"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column
              label="工单编号"
              align="center"
              prop="cleanNo"
              type="number"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="工单名称"
              align="center"
              prop="cleanName"
              :show-overflow-tooltip="true"
            >
            </el-table-column>
            <el-table-column
              label="工单来源"
              align="center"
              prop="dataSource"
              :show-overflow-tooltip="true"
            >
              <template slot-scope="scope">
                <div v-if="scope.row.dataSource==0">计划生成</div>
                <div v-if="scope.row.dataSource==1">后台添加</div>
                <div v-if="scope.row.dataSource==2">其他工单</div>
                <div v-if="scope.row.dataSource==3">场馆使用</div>
              </template>

            </el-table-column>
            <el-table-column
              label="保洁地址"
              align="center"
              prop="address"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="分配方式"
              align="center"
              prop="allotWay"
              :show-overflow-tooltip="true"
            >
              <template slot-scope="scope">
                <div v-if="scope.row.allotWay==0">自动分配</div>
                <div v-if="scope.row.allotWay==1">管理员分配</div>
                <div v-if="scope.row.allotWay==2">抢单</div>
              </template>
            </el-table-column>
            <el-table-column
              label="负责人"
              align="center"
              prop="executeName"
              :show-overflow-tooltip="true"
            >
            <template slot-scope="scope">
              <div v-if="scope.row.executeName!=null">{{ scope.row.staffName}}</div>
              <div v-if="scope.row.executeName==null">--</div>
            </template>
            </el-table-column>
            <el-table-column
              label="分配状态"
              align="center"
              prop="executeStatus"
              :show-overflow-tooltip="true"
            >
              <template slot-scope="scope">
                <div v-if="scope.row.staffName==0 || scope.row.staffName ==null">待分配</div>
                <div v-if="scope.row.allotStatus==1 || scope.row.staffName !=null">已分配</div>
              </template>
            </el-table-column>
            <el-table-column
              label="接单状态"
              align="center"
              prop="executeStatus"
              :show-overflow-tooltip="true"
            >
              <template slot-scope="scope">
                <div v-if="scope.row.acceptStatus==0 ">待接单</div>
                <div v-if="scope.row.acceptStatus==1 ">已接单</div>
              </template>
            </el-table-column>
            <el-table-column
              label="执行状态"
              align="center"
              prop="executeStatus"
              :show-overflow-tooltip="true"
            >
              <template slot-scope="scope">
                <div v-if="scope.row.executeStatus==0">待执行</div>
                <div v-if="scope.row.executeStatus==1 ">执行中</div>
                <div v-if="scope.row.executeStatus==2 ">已完成</div>
                <div v-if="scope.row.executeStatus==3">超时完成</div>
                <div v-if="scope.row.executeStatus==4">超时未完成</div>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              align="center"
              fixed="right"
              width="190"
              class-name="small-padding fixed-width"
            >
              <template slot-scope="scope">
                <el-button v-show="scope.row.staffName==null || scope.row.staffName== 0 " size="mini" type="text" @click="share(scope.row)">
                  <el-link icon="el-icon-view" type="brand" :underline="false">分配</el-link>
                </el-button>
                <el-button size="mini" type="text" @click="detail(scope.row)">
                  <el-link icon="el-icon-view" type="brand" :underline="false">详细</el-link>
                </el-button>
                <el-button
                  v-show="scope.row.staffName==null || scope.row.staffName== 0 "
                  size="mini"
                  type="text"
                  v-hasPermi="['estate:carport:add']"
                  @click="update(scope.row)">
                <el-link
                  icon="el-icon-edit"
                  type="success"
                  :underline="false">修改
                </el-link>
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <pagination
            v-show="total > 0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getBlackList"
          /></el-col>
      </el-row>
    </div>

    <!-- 新增页面 -->
    <el-dialog
      :visible.sync="addOpen"
      width="660px"
      @close="addClose"
      title="新增保洁工单（管理员分配）"
    >
      <div>
        <el-form
          ref="addForm"
          :rules="addRules"
          :model="addForm"
          label-width="170px"
        >
          <el-row>
            <el-col>
              <el-form-item label="工单名称: " prop="cleanName">
                <el-input
                  clearable
                  v-model="addForm.cleanName"
                  placeholder="请输入工单名称"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="保洁地点: " prop="address">
                <el-input
                  clearable
                  v-model="addForm.address"
                  placeholder="请输入保洁地点"
                ></el-input>
              </el-form-item>
            </el-col>
            <!-- ----------------------------------  -->
            <el-col>
              <el-form-item label="保洁模式: ">
                <template>
<!--                  <el-radio-->
<!--                    @change="modelRadionumber"-->
<!--                    v-model="rediot"-->
<!--                    label="0">一日多次</el-radio>-->
                  <el-radio
                    @change="modelRadioTime"
                    v-model="rediot"
                    label="1">多日一次</el-radio>
                </template>
              </el-form-item>
            </el-col>
            <div v-show="number">
              <el-col>
                <el-form-item label="保洁次数: " prop="patrolNum">
                  <el-input-number
                    clearable
                    v-model="addForm.patrolNum"
                    placeholder="请输入次数/天数"
                  ></el-input-number>
                </el-form-item>
              </el-col>
              <el-col>
                <el-form-item label="每次保洁间隔时间: " prop="gapTime">
                  <el-input-number v-model='addForm.gapTime' :precision='2' :step='0.5' size='Max'></el-input-number>  小时
                </el-form-item>

              </el-col>
            </div>
            <div v-show="frequency">
              <el-col>
                <el-form-item label="保洁频率: " prop="patrolNum">
                  <el-input-number
                    clearable
                    v-model="addForm.patrolNum"
                    placeholder="请输入天/次"
                  ></el-input-number>  天数
                </el-form-item>
              </el-col>
            </div>
            <!-- ----------------------------------  -->

            <el-col>
              <el-form-item label="计划保洁开始时间: " prop="startTime">
                <el-date-picker
                  v-model="addForm.startTime"

                  type="datetime"
                  placeholder="选择日期时间"
                  :picker-options="pickerOptions">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="计划保洁结束时间: " prop="endTime">
                  <el-date-picker
                    v-model="addForm.endTime"
                    type="datetime"
                    :picker-options="pickerOptionsEnd"
                    placeholder="选择日期时间">
                  </el-date-picker>
              </el-form-item>
            </el-col>

            <el-col>
              <el-form-item label="备注: " prop="remark">
                <el-input
                  type="textarea"
                  placeholder="请输入内容（200字内）"
                  v-model="addForm.remark"
                  maxlength="200"
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
                <el-button type="primary" @click="addSuccess"
                >确定</el-button
                >
                <el-button @click="addClose">取消</el-button>
              </div>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-dialog>

    <!--分配弹窗-->
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
            <el-form-item label="负责人: " prop="staffId">
              <el-select
                v-model="shareForm.staffId"
                clearable
                placeholder="请选择负责人"
              >
                <el-option
                  v-for="staff in shareNameList"
                  :key="staff.staffId"
                  :label="staff.staffName"
                  :value="staff.staffId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :offset="10" :span="2">
            <div style="width: 280px">
              <el-button type="primary" @click="shareSuccess">确定</el-button>
              <el-button @click="shareClose">取消</el-button>
            </div>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>

    <!-- 详情页面 -->
    <div v-show="showDetail">
      <el-form ref="form" :model="detailForm" label-width="180px">
        <!--  ----------------------------- 工单详情 --------------------------- -->
        <h5><b>工单详情</b></h5>
        <el-divider></el-divider>
        <!-- 第一行 -->
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="工单编号: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.cleanNo }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="工单名称: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.cleanName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="工单类型: ">
              <div style="width: 280px; overflow: hidden">
                  保洁工单
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第二行 -->
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="工单来源类型: ">
              <div style="width: 280px; overflow: hidden">
                <div v-if="detailForm.dataSource==0">计划生成</div>
                <div v-if="detailForm.dataSource==1">后台添加</div>
                <div v-if="detailForm.dataSource==2">其他工单</div>
                <div v-if="detailForm.dataSource==3">场馆使用</div>
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="来源工单: ">
              <div style="width: 280px; overflow: hidden">
                <div v-if="detailForm.orderNo!='' && detailForm.dataSource==2">{{detailForm.orderNo}}</div>
                <div v-if="detailForm.orderNo=='' || detailForm.dataSource!=2">——</div>
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="工单状态: ">
              <div style="width: 280px; overflow: hidden">
                <div v-if="detailForm.staffName==0 || detailForm.staffName ==null">待分配</div>
                <div v-if="detailForm.acceptStatus==0 && detailForm.staffName !=null || detailForm.allotStatus == 1 && detailForm.executeStatus==0">待接单</div>
                <div v-if="detailForm.executeStatus==0 && detailForm.allotStatus==1 &&detailForm.acceptStatus==1">待执行</div>
                <div v-if="detailForm.executeStatus==1 && detailForm.allotStatus==1 &&detailForm.acceptStatus==1">执行中</div>
                <div v-if="detailForm.executeStatus==2 && detailForm.allotStatus==1 &&detailForm.acceptStatus==1">已完成</div>
                <div v-if="detailForm.executeStatus==3 && detailForm.allotStatus==1 &&detailForm.acceptStatus==1">超时完成</div>
                <div v-if="detailForm.executeStatus==4 && detailForm.allotStatus==1 &&detailForm.acceptStatus==1">超时未完成</div>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第三行 -->
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="工单开始时间: ">
              <div style="width: 280px; overflow: hidden">
                {{ parseTime(detailForm.startTime) }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="工单结束时间: ">
              <div style="width: 280px; overflow: hidden">
                {{ parseTime(detailForm.endTime) }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="完成时间: ">
              <div style="width: 280px; overflow: hidden">
                <div v-if="detailForm.finishTime!=null"> {{ parseTime(detailForm.finishTime) }}</div>
                <div v-if="detailForm.finishTime==null">——</div>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第四行 -->
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="备注: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.remark }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="主要负责人: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.staffName }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <!--  ----------------------------- 工单执行人信息 --------------------------- -->
        <div>
          <h5><b>工单协助人信息</b></h5>
        </div>
        <el-divider></el-divider>
        <el-row>
          <el-col :span="24">
            <el-table
              border
              type="index"
              highlight-current-row
              :data="deatilListGenerate">
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
              <el-table-column
                label="身份证号"
                align="center"
                :show-overflow-tooltip="true"
                prop="cardNo"
              />
              <el-table-column
                label="人员类别"
                align="center"
                :show-overflow-tooltip="true"
                prop="personnelType"
              >
                <template slot-scope="scope">
                  <div v-if="scope.row.personnelType==3">保洁人员</div>
                  <div v-if="scope.row.personnelType!=3">——</div>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>

        <!--  ----------------------------- 执行详情 --------------------------- -->
        <div>
          <h5><b>执行详情</b></h5>
        </div>
        <el-divider></el-divider>
        <el-row>
          <el-col :span="24">
            <el-table
              stripe
              v-loading="loading"
              :data="OrderRecordList"
            >
              <el-table-column
                label="执行时间"
                align="center"
                prop="createTime"
                type="number"
                :show-overflow-tooltip="true"
              />
              <el-table-column
                label="执行流程名称"
                align="center"
                prop="orderResult"
                :show-overflow-tooltip="true"
              >
              </el-table-column>
              <el-table-column
                label="有关人员"
                align="center"
                prop="userName"
                :show-overflow-tooltip="true"
              >
                <template slot-scope="scope">
                  <div v-if="scope.row.orderResult=='工单分配'">主要负责人: {{scope.row.userName}}</div>
                  <div v-if="scope.row.orderResult=='工单转交'">转交人: {{scope.row.userName}}   接收人:  {{scope.row.relevant}}</div>
                  <div v-if="scope.row.orderResult=='请求协助'">申请人: {{scope.row.userName}}   接收人:  {{scope.row.relevant}}</div>
                  <div v-if="scope.row.orderResult=='转交工单审核通过'">审核人: {{scope.row.userName}}</div>
                  <div v-if="scope.row.orderResult=='接单'">接单人: {{scope.row.userName}}</div>
                  <div v-if="scope.row.orderResult=='协助确认'">协助人: {{scope.row.userName}}</div>
                  <div v-if="scope.row.orderResult=='开始执行' || scope.row.orderResult=='执行完成'">执行人: {{scope.row.userName}}</div>
                  <div v-if="scope.row.orderResult=='请求协助审核通过' || scope.row.orderResult=='请求协助审核驳回' ">审核人: {{scope.row.userName}}</div>
                </template>
              </el-table-column>
            </el-table>
          </el-col>
        </el-row>

        <el-row>
          <el-col :offset="23" :span="24">
            <el-button @click="cancel">返回</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script>
import {
  getCurrentList,
} from "@/api/estate/community";
import {
  listPage,
  getorderRecordList,
  getDetailList,
  removes,
  saveOrUpdate,
  updateDetail,
  getsafftName,
  shareUpdate,
} from "@/api/estate/work/cleaningManagement/cleanersSingle";
import {listLeisureBusyStaffs} from "@/api/estate/manage/staff/staff";

export default {
  data() {
    var _this = this;
    return {
      // 搜索
      search: '',
      pickerOptions: {
        disabledDate(time) {
          if (_this.addForm.endTime) {
            return (
              time.getTime() > new Date(_this.addForm.endTime).getTime()
            );
          }else {
            return time.getTime() < Date.now() - 8.64e7;
          }
        }
      },
      pickerOptionsEnd: {
        disabledDate(time) {
          if (_this.addForm.startTime) {
            return (
                time.getTime() < new Date(_this.addForm.startTime).getTime()
            );
          }else {
            return time.getTime() < Date.now() - 8.64e7;
          }
        }
      },
      // 详情内 - 工单执行人详细
      deatilListGenerate: [],
      // 分配弹窗
      shareOpen: false,
      // 员工集合
      shareNameList: [],
      // 分配弹窗数据
      shareForm: {
        allotStatus:"1"
      },
      //一天多次
      number: false,
      //多少天1次
      frequency:false,
      //单选按钮开关
      rediot:1,
      // 详情表单
      detailForm: {},
      // 新增页面表单
      addForm: {},
      //执行流程数据
      OrderRecordList:[],
      // 小区ID
      communityId: undefined,
      // 小区ID 新增修改传参
      communityIds: null,
      // 详情内表格数据
      ownerListData: [],
      // 机构列表
      deptData: [],
      // 机构下拉框
      deptValue: "",
      // 新增弹窗
      addOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        // 小区ID
        communityId: undefined,
        cleanNo: "",
        cleanName: "",
        address: null,
        executeStatus: null,
      },
      // 遮罩层
      loading: true,
      // 表格数据
      injList: [],
      // 选中数组
      ids: [],
      // 详情页面默认关闭
      showDetail: false,
      homeShow: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 表单参数
      form: {},
      // 新增表单校验
      addRules: {
        cleanName: [
          { required: true, message: "计划名称不能为空", trigger: "blur" },
        ],
        address: [
          { required: true, message: "保洁地址不能为空", trigger: "blur" },
        ],
        startTime: [
          { required: true, message: "请选择计划开始时间", trigger: "blur" },
        ],
        patrolNum: [
          { required: true, message: "请选择保洁次数", trigger: "blur" },
        ],
        gapTime: [
          { required: true, message: "请选择保洁间隔时间", trigger: "blur" },
        ],
        planModel: [
          { required: true, message: "请选择保洁模式", trigger: "blur" },
        ],
        endTime: [
          { required: true, message: "请选择计划结束时间", trigger: "blur" },
        ],
      },
      shareRules:{
        staffId:[
          { required: true, message: "请选择负责人", trigger: "input" },
        ]
      }
    };
  },
  computed: {
    viewDeptData: function () {
      let search = this.search;
      let viewDeptData = this.deptData;
      if (search) {
        viewDeptData = this.deptData.filter(dataNews => {
          return dataNews.communityName.indexOf(search) !== -1;
        })
      }
      return viewDeptData;
    }
  },
  created() {
    // 机构下拉框
    this.getTypeList();
    // 列表查询
    // this.getBlackList();
  },

  methods: {
    //一天多次
    modelRadionumber() {
      this.number = true;
      this.frequency = false;
    },
    //多天一次
    modelRadioTime() {
      this.number = false;
      this.frequency = true;
      this.addForm.gapTime = null;
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getBlackList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      // this.queryParams.dropDownForm = null;
      this.searchTime = "";
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 查询岗位列表 */
    getBlackList() {
      this.loading = false;
      listPage(this.queryParams).then((response) => {
        this.injList = response.rows;
        this.total = response.total;
      });
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.cleanId);
      this.multiple = !selection.length;
    },

    // 打开分配弹窗
    share(row) {
      this.shareForm.cleanId = row.cleanId;
      this.shareOpen = true;
      listLeisureBusyStaffs({allotType: '3',communityId:  row.communityId}).then((response) => {
        this.shareNameList = response.data;
      });
    },

    // 关闭分配弹窗
    shareClose() {
      // this.$refs[shareForm].resetFields();
      this.shareOpen = false;
      this.$refs.addForm.clearValidate()
      this.getBlackList();
    },

    // 表单重置
    reset() {
      this.form = {};
      this.resetForm("form");
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.showDetail = false;
      this.homeShow = true;
      this.getBlackList();
    },

    selectView(row) {
      //工单详细
      updateDetail(row.cleanId).then((response) => {
        this.detailForm = response.data;
        this.open = true;
      });
      //工单执行人
      getDetailList(row.cleanId).then((response) => {
        this.deatilListGenerate = response.data;
        this.open = true;
      });
      //执行情况
      getorderRecordList(row.cleanId).then((response) => {
        this.OrderRecordList = response.data;
        this.open = true;
      });
    },

    /** 详情 */
    detail(row) {
      this.showDetail = true;
      this.homeShow = false;
      this.selectView(row);
    },


    // 机构下拉框
    getTypeList() {
      getCurrentList().then((response) => {
        this.deptData = response.data;
        this.queryParams.communityId = this.deptData[0].communityId;
        this.$refs.singleTable.setCurrentRow(this.deptData[0]);
        this.addForm.communityId = this.deptData[0].communityId;
        this.addForm.communityName = this.deptData[0].communityName;
        this.communityIds = this.deptData[0].communityId;
        this.getBlackList();
      });
    },

    // 单元格点击时查列表
    listClick(row) {
      this.queryParams.communityId = row.communityId;
      // // 点击单元格时赋值所属小区
      this.addForm.communityId = row.communityId;
      this.addForm.communityName = row.communityName;
      this.communityIds = row.communityId;
      this.getBlackList();
    },

    // 新增按钮
    handleAdd() {},

    //删除按钮
    handleDelete() {
      this.$modal
        .confirm("是否确认删除选中的数据项？")
        .then(() => {
          removes(this.ids).then(() => {
            this.getBlackList();
            this.$modal.msgSuccess("删除成功");
          });
        }).catch(() => {
        this.$modal.msgSuccess("取消删除");
      });
    },

    // 打开新增弹窗
    addMethod() {
      //默认为一天多次
      this.rediot = "1";
      //打开一天多次框
      this.number = true;
      //隐藏多天一次
      this.frequency = false;
      this.cleanId = null;
      this.addOpen = true;
      this.modelRadioTime()
    },

    // 关闭新增弹窗
    addClose() {
      this.$refs.addForm.clearValidate()
      this.addOpen = false;
      this.getBlackList();
      this.addFormEmpty();
    },

    // 分配弹窗确定
    shareSuccess() {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          this.$confirm("是否确定提交?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
            shareUpdate(this.shareForm).then(() => {
              this.getBlackList();
              this.shareOpen = false;
              this.shareForm.staffId = "";
              this.shareForm.executeId = "";
              this.$message({
                type: "success",
                message: "提交成功!",
              });
            })
          }).catch(() => {
            this.$message({
              type: "info",
              message: "已取消提交",
            });
            this.shareForm.staffId = "";
            this.shareForm.executeId = "";
          });
        }
      });
    },

    // 修改
    update(row) {
      this.cleanId = row.cleanId;
      if (row.gapTime == 0) {
        this.rediot = "1";
        this.number = false;
        this.frequency = true;
      }
      if (row.gapTime != 0) {
        this.rediot = "0";
        this.number = true;
        this.frequency = false;
      }
      this.addOpen = true;
      this.updateDetails(row.cleanId);
      this.addForm.cleanId = row.cleanId;

    },

    // 编辑页面详情展示
    updateDetails(cleanId) {
      updateDetail(cleanId).then((response) => {
        this.addForm = response.data;
      });
    },

    // 提交
    addSuccess() {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          this.$confirm("是否确定提交?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          }).then(() => {
              saveOrUpdate(this.addForm).then(() => {
                this.getBlackList();
                this.addFormEmpty();
                this.addOpen = false;
                this.$message({
                  type: "success",
                  message: "提交成功!",
                });
              })
            }).catch(() => {
              this.$message({
                type: "info",
                message: "已取消提交",
              });
            });
        }
      });
    },

    // 清空addForm表单
    addFormEmpty() {
      this.addForm.cleanName = null;
      this.addForm.address = null;
      this.addForm.patrolNum = null;
      this.addForm.startTime = null;
      this.addForm.endTime = null;
      this.addForm.gapTime = null;
      this.addForm.cleanId = null;
      this.addForm.remark = null;
    }
  },
};
</script>
<style scoped>
</style>

<template>
  <div class="app-container">
    <div v-show="mainShow">
      <el-row :gutter="20">
        <!--部门数据-->
        <el-col :span="4" :xs="24">
          <!-- <div
            class="head-container"
            style="text-align: center"
          >
          </div> -->
          <div class="head-container"  >
            <el-input
              placeholder="请输入小区名称"
              clearable
              size="small"
              prefix-icon="el-icon-search"
              v-model="filterText"
            />
             <el-tree
               class="filter-tree"
               :data="communityList"
               :props="defaultProps"
               :filter-node-method="filterNode"
               icon-class="el-icon-star-on"
               @node-click="pickTree"
               node-key="communityId"
               highlight-current
               ref="tree"
            >
            </el-tree>
           </div>
        </el-col>
      </el-row>
    </div>
    <div class="position" style="position:fixed;top:0">
      <!-- 右边tabs -->
      <el-tabs v-model="activeName" >
        <el-tab-pane label="业主申请" name="first" v-if="ownerInfoIsShow">
          <div v-show="open">
            <el-form
              :model="queryParams"
              ref="queryForm"
              v-show="showSearch"
              :inline="true"
              class="cus-fm"
            >
              <el-form-item label="名称" prop="auditName">
                <el-input
                  v-model.trim="queryParams.auditName"
                  placeholder="请输入名称"
                  clearable
                  size="small"
                  style="width: 240px"
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item label="申请人" prop="ownerName">
                <el-input
                  v-model.trim="queryParams.ownerName"
                  placeholder="请输入申请人"
                  clearable
                  size="small"
                  style="width: 240px"
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
<!--              <el-form-item label="申请类别" prop="auditType">
                 <el-select
                v-model.trim="queryParams.auditType"
                placeholder="请选择申请类别"
                clearable
                style="width: 240px"
              >
                &lt;!&ndash; <el-option
                    v-for="dict in disasterOptions"
                    :key="dict.dictValue"
                    :label="dict.dictLabel"
                    :value="dict.dictValue"
                  /> &ndash;&gt;
                <el-option label="工单转交" value="0" />
                <el-option label="工单协助" value="1" />
              </el-select>
              </el-form-item>-->
              <el-form-item
                label="状态"
                prop="auditStatus"
                style="margin-left: 30px"
              >
                <el-select
                  v-model.trim="queryParams.auditStatus"
                  placeholder="状态"
                  clearable
                  size="small"
                  style="width: 240px"
                >
                  <el-option
                    v-for="dict in statusOptions"
                    :key="dict.dictValue"
                    :label="dict.dictLabel"
                    :value="dict.dictValue"
                  />
                </el-select>
              </el-form-item>
              <el-form-item style="margin-left: 30px">
                <el-button
                  type="primary"
                  icon="el-icon-search"
                  size="mini"
                  @click="handleQuery"
                  >搜索</el-button
                >
                <el-button
                  icon="el-icon-refresh"
                  size="mini"
                  @click="resetQuery"
                  >重置</el-button
                >
              </el-form-item>
            </el-form>

            <el-table
              v-loading="loading"
              :data="disasterList"
              v-if="activeName === 'first'"
            >
              <el-table-column type="selection" width="55" align="center" />
              <el-table-column
                label="名称"
                align="center"
                prop="auditName"
              ></el-table-column>
              <el-table-column
                label="申请人"
                align="center"
                prop="ownerName"
              ></el-table-column>
              <el-table-column
                label="备注"
                align="center"
                prop="remark"
                show-overflow-tooltip
              ></el-table-column>
              <el-table-column
                label="状态"
                align="center"
                prop="auditStatus"
                :formatter="statusFormatter"
              >
              </el-table-column>
              <el-table-column
                label="操作"
                align="center"
                class-name="small-padding fixed-width"
              >
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="text"
                    @click="handleInfo(scope.row)"
                    v-if="
                      scope.row.auditStatus == 1 ||
                      scope.row.auditStatus == 2 ||
                      scope.row.auditStatus == 0
                    "
                    ><el-link
                      icon="el-icon-view"
                      type="brand"
                      :underline="false"
                      >查看</el-link
                    ></el-button
                  >
                  <el-button
                    size="mini"
                    type="text"
                    @click="handlePass(scope.row)"
                    v-if="scope.row.auditStatus == 0"
                    ><el-link
                      icon="el-icon-edit"
                      type="success"
                      :underline="false"
                      v-hasPermi="['work:disasterAudit:pass']"
                      >通过</el-link
                    ></el-button
                  >
                  <el-button
                    size="mini"
                    type="text"
                    v-if="scope.row.auditStatus == 0"
                    @click="handleOverrule(scope.row)"
                    ><el-link
                      icon="el-icon-refresh"
                      type="danger"
                      :underline="false"
                      >驳回</el-link
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
              style="height: 80px"
            />
          </div>
          <!-- 详情页面 -->
          <div v-show="showDetail" >
            <el-form ref="form" :model="form" label-width="180px" >
              <h5>
                <b>{{ titleInfo }}</b>
              </h5>
              <el-col :span="24">
                <el-row>
                  <el-col :span="6">
                    <el-form-item label="名称：">
                      <div style="width: 280px">{{ form.auditName }}</div>
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item label="报事类型：">
                      <span style="width: 280px">{{ "灾害报事" }}
                      </span>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="6">
                    <el-form-item label="灾害类型：">
                      <div style="width: 280px">{{ form.disasterName }}</div>
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item label="备注：">
                      <div style="width: 280px">{{ form.remark }}</div>
                    </el-form-item>
                  </el-col>
                </el-row>
           <el-descriptions :label-style="{'line-height':'40px'}" :content-style="{'line-height':'40px'}" style="margin-left:-15px"  :column="1">
                    <el-descriptions-item :span="1">
                      <el-row>
                        <el-col>
                          <el-form-item label="上传图片：">
                            <el-image  :src="image" fit="fit"  class="image" v-for="(image,index) in form.imageList" :key="index" />
                          </el-form-item>
                        </el-col>
                      </el-row>
                    </el-descriptions-item>
           </el-descriptions>
              </el-col>
              <h4 class="form-header" style="color: #0558e7">申请人详情</h4>
              <el-row>
                <el-col :span="6">
                  <el-form-item label="申请人：">
                    <div style="width: 280px">{{ form.ownerName }}</div>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="手机号：" style="margin-left:-10px">
                    <div style="width: 280px">{{ form.ownerPhone }}</div>
                  </el-form-item>
                </el-col>
                <!-- <el-col :span="2">
                  <el-form-item label="人员类别：">
                    <!-- <div style="width: 280px">{{ peoType[form.population] }}</div> -->
                    <!-- <div style="width: 280px">{{ populationType[form.population] }}</div> -->
                  <!-- </el-form-item> -->
                <!-- </el-col> -->
              </el-row>
              <h4 class="form-header" style="color: #0558e7">审批详情</h4>
              <el-row>
                <el-col :span="6">
                  <el-form-item label="审批状态：">
                    <div>{{ passStatus[form.auditStatus] }}</div>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="驳回原因：">
                    <div style="width: 280px">{{ form.rejectReason }}</div>
                  </el-form-item>
                </el-col>
                <el-col :span="2">
                  <el-form-item label="审批人：">
                    <div style="width: 280px">{{ form.auditBy }}</div>
                  </el-form-item>
                </el-col>
                <el-row>
                  <el-col>
                    <el-form-item label="审批时间：">
                      <div style="width: 280px">{{ form.auditTime }}</div>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-row>
              <el-button size="middle" style="margin-left: 80%" @click="cancel"
                >关闭</el-button
              >
            </el-form>
          </div>
        </el-tab-pane>
        <el-tab-pane label="员工申请" name="second" v-if="estateInfoIsShow">
          <!-- <EmpApply v-if="activeName==='second'"></EmpApply> -->
          <div v-show="openEmp">
            <el-form
              :model="queryParamsEmp"
              ref="queryFormEmp"
              v-show="showSearchEmp"
              :inline="true"
              class="cus-fm"
            >
              <el-form-item label="工单编号" prop="orderNo">
                <el-input
                  v-model.trim="queryParamsEmp.orderNo"
                  placeholder="请输入工单编号"
                  clearable
                  size="small"
                  style="width: 240px"
                />
              </el-form-item>
              <el-form-item label="工单名称" prop="orderName">
                <el-input
                  v-model.trim="queryParamsEmp.orderName"
                  placeholder="请输入工单名称"
                  clearable
                  size="small"
                  style="width: 240px"
                />
              </el-form-item>
              <el-form-item label="申请人" prop="staffName">
                <el-input
                  v-model.trim="queryParamsEmp.staffName"
                  placeholder="请输入申请人"
                  clearable
                  size="small"
                  style="width: 240px"
                />
              </el-form-item>
              <el-form-item label="申请类别" prop="auditType">
                  <el-select
                v-model.trim="queryParamsEmp.auditType"
                placeholder="请选择申请类别"
                clearable
                style="width: 240px"
              >
                <!-- <el-option
                    v-for="dict in disasterOptions"
                    :key="dict.dictValue"
                    :label="dict.dictLabel"
                    :value="dict.dictValue"
                  /> -->
                 <el-option
                  v-for="auditType in auditTypeList"
                  :key="auditType.value"
                  :label="auditType.label"
                  :value="auditType.value"
                />
              </el-select>
              </el-form-item>
              <el-form-item
                label="状态"
                prop="auditStatus"
                style="margin-left: 30px"
              >
                <el-select
                  v-model="queryParamsEmp.auditStatus"
                  placeholder="状态"
                  clearable
                  size="small"
                  style="width: 240px"
                >
                 <el-option
                    v-for="dict in statusOptions"
                    :key="dict.dictValue"
                    :label="dict.dictLabel"
                    :value="dict.dictValue"
                  />
                </el-select>
              </el-form-item>
              <el-form-item style="margin-left: 30px">
                <el-button
                  type="primary"
                  icon="el-icon-search"
                  size="mini"
                  @click="handleQueryEmp"
                  >搜索</el-button
                >
                <el-button
                  icon="el-icon-refresh"
                  size="mini"
                  @click="resetQueryEmp"
                  >重置</el-button
                >
              </el-form-item>
            </el-form>

            <el-table
              v-loading="loadingEmp"
              :data="disasterListEmp"
              v-if="activeName === 'second'"
            >
              <el-table-column type="selection" width="55" align="center" />
               <el-table-column
              label=" 工单编号"
              align="center"
              prop="orderNo"
              type="number"
              :show-overflow-tooltip="true"
            />
              <el-table-column
                label="工单名称"
                align="center"
                prop="orderName"
              ></el-table-column>
              <el-table-column
                label="申请人"
                align="center"
                prop="staffName"
              ></el-table-column>
              <!-- staffName -->
              <el-table-column
                label="申请类别"
                align="center"
                prop="auditType"
                :formatter="auditTypeChange"
              ></el-table-column>
              <el-table-column
                label="状态"
                align="center"
                prop="auditStatus"
                :formatter="statusFormatter"
              >
              </el-table-column>
              <el-table-column
                label="操作"
                align="center"
                class-name="small-padding fixed-width"
              >
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="text"
                    @click="handleInfoEmp(scope.row)"
                    v-if="
                      scope.row.auditStatus == 1 ||
                      scope.row.auditStatus == 2 ||
                      scope.row.auditStatus == 0
                    "
                    ><el-link
                      icon="el-icon-view"
                      type="brand"
                      :underline="false"
                      >查看</el-link
                    ></el-button
                  >
                  <el-button
                    size="mini"
                    type="text"
                    @click="handlePassEmp(scope.row)"
                    v-if="scope.row.auditStatus == 0"
                    ><el-link
                      icon="el-icon-edit"
                      type="success"
                      :underline="false"
                      v-hasPermi="['work:disasterAudit:pass']"
                      >通过</el-link
                    ></el-button
                  >
                  <el-button
                    size="mini"
                    type="text"
                    @click="handleOverruleEmp(scope.row)"
                    v-if="scope.row.auditStatus == 0"
                    ><el-link
                      icon="el-icon-refresh"
                      type="danger"
                      :underline="false"
                      v-hasPermi="['work:disasterAudit:reject']"
                      >驳回</el-link
                    ></el-button
                  >
                </template>
              </el-table-column>
            </el-table>

            <pagination
              v-show="totalEmp > 0"
              :total="totalEmp"
              :page.sync="queryParamsEmp.pageNum"
              :limit.sync="queryParamsEmp.pageSize"
              @pagination="getEmpList"
              style="height: 80px"
            />
          </div>
          <!-- 详情页面 -->
          <div v-show="showDetail">
            <el-form ref="form" :model="formEmp" label-width="180px">
              <h5>
                <b>{{ titleInfo }}</b>
              </h5>
              <el-col :span="24">
                <el-row>
                  <el-col :span="5">
                    <el-form-item label="工单编号：">
                      <div style="width: 280px">{{ formEmp.orderNo }}</div>
                    </el-form-item>
                  </el-col>

                  <el-col :span="5" :offset="1">
                    <el-form-item label="工单名称：">
                      <div style="width: 280px">{{ formEmp.orderName }}</div>
                    </el-form-item>
                  </el-col>
                  <!-- <el-col :span="5" :offset="1">
                    <el-form-item label="接收人">
                      <div style="width: 280px">{{ formEmp.receiveName }}
                        <!-- busOrderType[formEmp.orderType] -->
                      <!-- </div>
                    </el-form-item>
                  </el-col> -->
                </el-row>
                <el-row>
                  <!-- <el-col :span="2">
                    <el-form-item label="接受人">
                      <div style="width: 280px">{{ allotStatus[formEmp.allotWay] }}</div>
                    </el-form-item>
                  </el-col> -->
                  <el-col :span="5" >
                    <el-form-item label="接收人：">
                      <div style="width: 280px">{{ formEmp.userName }}</div>
                    </el-form-item>
                  </el-col>
                  <el-col :span="6" :offset="1">
                    <el-form-item label="接收人手机号：">
                      <div style="width: 280px">{{ formEmp.phonenumber }}</div>
                    </el-form-item>
                  </el-col>
                </el-row>
                <!-- <el-row>
                  <el-col :span="2">
                    <el-form-item label="备注：">
                      <div style="width: 280px">{{ formEmp.remark }}</div>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="6">
                    <el-form-item label="计划开始时间：">
                      <div style="width: 280px">{{ formEmp.planStart }}</div>
                    </el-form-item>
                  </el-col>
                  <el-col :span="2">
                    <el-form-item label="计划结束时间：">
                      <div style="width: 280px">{{ formEmp.planEnd }}</div>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="2">
                    <el-form-item label="异常状态：">
                      <div style="width: 280px;float:left">{{ ycStatus[formEmp.yc] }}
                        <el-button type="text" @click="dialogVisible=true" style="margin-left:10px" v-if="formEmp.yc==1">查看异常</el-button>
                      </div>

                    </el-form-item>
                  </el-col>
                </el-row> -->
        <!-- <div v-if="formEmp.orderType==='8'">
          <h4 class="form-header">工单协助人信息</h4>
          <el-row>
            <el-table :data="formEmp.staffList">
              <el-table-column label="人员姓名" align="center" key="staffName" prop="staffName"/>
              <el-table-column label="手机号" align="center" key="staffPhone" prop="staffPhone"/>
              <el-table-column label="身份证号" align="center" key="cardNo" prop="cardNo"/>
              <el-table-column label="人员类别" align="center" key="workPost" prop="workPost"/>
            </el-table>
          </el-row>
        </div> -->
              </el-col>
        <div v-if="formEmp.auditType==='1'">
          <h4 class="form-header">工单协助人信息</h4>
          <el-row>
            <el-table :data="formEmp.staffList">
              <el-table-column label="人员姓名" align="center" key="staffName" prop="staffName"/>
              <el-table-column label="手机号" align="center" key="staffPhone" prop="staffPhone"/>
              <el-table-column label="身份证号" align="center" key="cardNo" prop="cardNo"/>
              <el-table-column label="人员类别" align="center" key="workPost" prop="workPost" :formatter="workPostChange"/>
            </el-table>
          </el-row>
        </div>
              <h4 class="form-header" style="color: #0558e7">审批详情</h4>
              <el-row>
                <el-col :span="6">
                  <el-form-item label="申请类别：">
                    <div style="width: 280px">{{ auditTypeStatus[formEmp.auditType] }}</div>
                  </el-form-item>
                </el-col>
                <el-col :span="6">
                  <el-form-item label="审批状态：">
                    <div style="width: 280px">
                      {{ passStatus[formEmp.auditStatus] }}
                    </div>
                  </el-form-item>
                </el-col>
                <el-col :span="2">
                  <el-form-item label="审批人：">
                    <div style="width: 280px">{{ formEmp.userName }}</div>
                  </el-form-item>
                </el-col>
                <el-row>
                  <el-col>
                    <el-form-item label="驳回原因：">
                      <div style="width: 280px">{{ formEmp.rejectReason }}</div>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-row>
              <el-button
                size="middle"
                style="margin-left: 80%"
                @click="cancelEmp"
                >返回</el-button
              >
            </el-form>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
    <!-- 工单异常对话框 -->
     <!-- <el-dialog
      title="查看异常"
      :visible.sync="dialogVisible"
      width="40%">
      <el-timeline>
        <el-timeline-item :timestamp="parseTime(item.create_time)" placement="top" v-for="(item,index) of errorList " :key="index">
          <el-card>
            <el-descriptions title="工单异常" :column="1" :label-style="{'line-height':'40px'}" :content-style="{'line-height':'40px'}">
              <el-descriptions-item label="异常描述">{{item.abnormal_info}}</el-descriptions-item>
              <el-descriptions-item label="异常图片">
                <el-image  :src="item.image_url" fit="contain"  style="width: 300px; height: 200px"/>
              </el-descriptions-item>
            </el-descriptions>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </el-dialog> -->
  </div>
</template>

<script>
import { treeRoom, roomSearchListPage } from "@/api/estate/center/room";
import {
  listDisasterAudit,
  passBusDisaster,
  rejectBusDisaster,
  ListEmp,
  queryError
} from "@/api/estate/work/orderDisaster/BusDisasterAudit";
import {detail} from "@/api/estate/work/inspect/examine";
import { getCurrentList } from "@/api/estate/community";
export default {
  name: "index",
  created() {
    // 左侧树数据加载
    this.getTypeList();
    this.getDicts("sys_oldman_auditstatus").then((response) => {
      this.statusOptions = response.data;
    });
    this.getAuditStatusList();
  },
    watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
  data() {

    return {
      //业主申请显隐
      ownerInfoIsShow:true,
      //员工申请显隐
      estateInfoIsShow:true,
       // 审核状态列表
      auditStatusList: [],
      //工单对话框
      dialogVisible:false,
      // 是否显示弹出层
      open: true,
      errorList:[],
      openEmp: true,
      activeName: "first",
      showDetail: false,
      // 业主申请表格数据
      disasterList: [],
      //员工申请表格数据
      disasterListEmp:[],
      // 列表选中的ids
      ids: [],
      // 表单参数
      form: {},
      formEmp: {},
      filterText:'',
      shallOpen: true,
      // 状态数据字典
      statusOptions: [],
      // 页面显示
      // 主页面显示
      mainShow: true,
      // 显示搜索条件
      showSearch: true,
      showSearchEmp:true,
      // 主页面内部列表显示
      listShow: true,
      // 主页面内部图形显示
      graphShow: false,
      // 遮罩层
      loading: false,
      loadingEmp:false,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      drawer: false,
      info:{},
      // tree
      currentNodeKey: undefined,

      // // 图片
      // // 导入参数
      // upload: {
      //   // 设置上传的请求头部
      //   headers: { Authorization: "Bearer " + getToken() },
      //   // 上传的地址
      //   url: process.env.VUE_APP_BASE_API + "/file/upload",
      // },
      titleInfo: "",
      // 外来人员表单数据
      foreignForm: {},
      // 总条数
      total: 0,
      totalEmp: 0,
      ownerTotal: 0,
      // 弹出层标题
      title: "",
      roomGraphData: {},
      selectData: {},
      // 查询参数
      queryParams: {
        id: undefined,
        pageNum: 1,
        pageSize: 10,
        orderId:undefined,
        auditName:undefined,
        ownerName: undefined,
        disasterName:undefined,
        auditType:undefined,
        communityId:undefined,
        orderType: '8',
        auditStatus:undefined
      },
      queryParamsEmp:{
        id: undefined,
        pageNum: 1,
        pageSize: 10,
        orderNo:undefined,
        disasterName:undefined,
        orderType: '8',
        ownerName: undefined,
        auditType:undefined,
        communityId:undefined,
        auditStatus:undefined
      },

      // 单元下拉数据
      selectRoomUnitList: [],

      // 左侧tree数据
      communityList: [],
      // tree默认props
      defaultProps: {
        children: "communityId",
        label: "communityName",
      },
      passStatus: {
        0: "审核中",
        1: "已通过",
        2: "已驳回",
      },
      auditTypeStatus:{
        0:"工单转交",
        1:"工单协助"
      },
      //人员类别岗位：0=安防人员，1=维修人员，2=装修人员，3=场馆人员，4=保洁人员，5=志愿者
      peoType: {
        0: "安防人员",
        1: "维修人员",
        2: "装修人员",
        3: "场馆人员",
        4: "保洁人员",
        5: "志愿者",
      },
      //  //人员类别岗位：0=安防人员，1=维修人员，2=装修人员，3=场馆人员，4=保洁人员，5=志愿者
      // populationType: {
      //   "0": "户籍人口",
      //   "1": "常住人口",
      //   "2": "暂住人口"
      // },
      //申请类别
      allotStatus:{
        0:"自动分配",
        1:"管理员分配",
        2:"抢单"
      },
      ycStatus:{
        "0":"正常",
        "1":"异常"
      },
       // 申请类别列表
      auditTypeList: [{label: '工单转交', value: '0'}, {label: '工单协助', value: '1'}],
      //执行状态：0=待执行，1=执行中，2=已完成，3=超时完成，4=超时未完成
      executeStatus:{
           0:"待执行",
        1:"执行中",
        2:"已完成",
        3:"超时完成",
        4:"超时未完成"
      },
      //工单类型
      busOrderType:{
          "8":"灾害报事工单"
      },
      //工单转让类型
      transStatus:{
        0:"无需转交",
        1:"转交待确认"
      },
      detailForm: {},
      inhabitantList: [],

      imageUrl: "",
      cardFontImageUrl: "",
      cardBackImageUrl: "",
      faceUrl: "",
      // isPlain:false
    };
  },

  methods: {
    /** 查询业主申请列表 */
    getList() {
      this.loading = true;
      listDisasterAudit(this.queryParams).then((response) => {
        this.disasterList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
     /** 查询用户申请列表 */
    getEmpList(){
      this.loadingEmp = true;
      ListEmp(this.queryParamsEmp).then((response) => {
        this.disasterListEmp = response.rows;
        this.totalEmp = response.total;
        this.loadingEmp = false;
      });
    },

       // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
      // 机构下拉框
    getTypeList() {
      getCurrentList().then((res) => {
        this.communityList = res.data;
        this.queryParams.communityId = this.communityList[0].communityId;
        this.queryParamsEmp.communityId = this.communityList[0].communityId;
        this.communityId = this.communityList[0].communityId;
        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.communityId);
        });

        this.getList();
        this.getEmpList();
      });
    },
    /**
     * 查看业主申请
     */
    handleInfo(row) {
      this.estateInfoIsShow=false;
      this.titleInfo = "申请详情";
      this.mainShow = false;
      this.showDetail = true;
      this.open = false;
      this.form = row;
      this.form.orderId=row.orderId
      // this.getList();
    },
    cancel() {
      this.estateInfoIsShow=true;
      this.open = true;
      this.reset();
      this.showDetail = false;
      this.mainShow = true;
      // this.getList();
    },
    /**
     * 查看员工申请
     */
    handleInfoEmp(row) {
      this.ownerInfoIsShow=false;
      this.titleInfo = "工单详情";
      this.mainShow = false;
      this.showDetail = true;
      this.openEmp = false;
      this.formEmp = row;
       queryError({id:row.orderId}).then(e=>{
          this.errorList=e.data
      })
      this.queryParamsEmp.auditId = row.auditId
       detail(this.formEmp).then(e=>{
          this.formEmp=e.data
      })
      this.drawer=true;
      this.info=row;
    },
    cancelEmp() {
      this.ownerInfoIsShow=true;
      this.openEmp = true;
      this.reset();
      this.showDetail = false;
      this.mainShow = true;
      // this.getEmpList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams={},
      this.resetForm("queryParams");
      this.queryParams.communityId = this.communityId;
      this.getList();
    },
      /** 重置按钮操作 */
    resetQueryEmp() {
      this.queryParamsEmp={},
      this.resetForm("queryParamsEmp");
      this.queryParamsEmp.communityId = this.communityId;
      this.getEmpList();
    },
    // 表单重置
    reset() {
      this.queryParamsEmp = {
        orderNo: undefined,
        orderName:undefined,
        disasterId: undefined,
        estateId: undefined,
        disasterName: undefined,
        remark: undefined,
        auditType:undefined,
        auditStatus: undefined,
      };

      this.resetForm("queryParams");
      this.resetForm("queryParamsEmp")
    },
    // 分配方式格式化
    auditTypeFormat(auditTypeId) {
      for (const auditType of this.auditTypeList) {
        if (auditType.value === auditTypeId) {
          return auditType.label;
        }
      }
    },


    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    handleQueryEmp(){
      this.queryParamsEmp.pageNum = 1;
      this.getEmpList();
    },

    // 获取分配方式列表
    getAuditStatusList() {
      this.getDicts("sys_oldman_auditstatus").then(response => {
        this.auditStatusList = response.data;
      });
    },
    /**
     * 后台返回的状态改变
     */
    statusFormatter(row, column) {
     for (const auditStatus of this.auditStatusList) {
        if (auditStatus.dictValue === row.auditStatus) {
          return auditStatus.dictLabel;
        }
      }
    },
    //申请类别
    allotFormatter(row, column) {
      const status = row.allotWay;
      if (status == 0) {
        return "自动分配";
      } else if (status == 1) {
        return "管理员分配";
      } else if (status == 2) {
        return "抢单";
      }
    },
      auditTypeChange(row) {
      if (row.auditType === "0") {
        return "工单转交";
      }
      if (row.auditType === "1") {
        return "工单协助";
      }
    },
    workPostChange(row){
       if (row.workPost === "0") {
        return "工单转交";
      }
      if (row.workPost === "1") {
        return "工单协助";
      }

    },
    //用户通过
    handlePass(row) {
      row.status ='0';
      this.$confirm(
        "确定审核通过吗?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(() => {
          passBusDisaster(row).then((resp) => {
            const {msg,code} = resp;
            if (code==200){
              this.$modal.msgSuccess(msg);
            }else {
              this.$modal.msgError(msg);
            }
              this.getList();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消通过",
          });
        });
    },
    //员工通过
    handlePassEmp(row) {
      row.status ='1';
      this.$confirm(
        "确定审核通过吗?",
        "提示",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(() => {
          passBusDisaster(row).then((resp) => {
            this.$modal.msgSuccess(resp.msg);
            this.getEmpList();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消通过",
          });
        });
    },
    pickTree(row){
      this.queryParams.communityId = row.communityId;
      this.queryParamsEmp.communityId = row.communityId;
      this.communityId = row.communityId;
      this.getList();
      this.getEmpList();
    },

 //用户驳回
    handleOverrule(row) {
      // this.form = data;
      //  passBusDisaster(this.form).then(response => {
      //       this.$modal.msgSuccess("通过成功");
      //       this.open = false;
      //       this.getList();
      //     });
      row.status ='0';
      this.$prompt('请输入', '驳回原因', {
        confirmButtonText: '提交',
        cancelButtonText: '取消',
        inputPattern:/^.{1,16}$/,
        inputErrorMessage: '驳回原因限制在 1 至 16 字符以内， 请重新输入'
      })
        .then(async({ value }) => {
          row.rejectReason=value;
          rejectBusDisaster(row).then((resp) => {
              this.getList();
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消通过",
          });
        });
    },
     //员工驳回
    handleOverruleEmp(row) {
      // this.form = data;
      //  passBusDisaster(this.form).then(response => {
      //       this.$modal.msgSuccess("通过成功");
      //       this.open = false;
      //       this.getList();
      //     });
      row.status="1";
      this.$prompt('请输入', '驳回原因', {
        confirmButtonText: '提交',
        cancelButtonText: '取消',
        inputPattern:/^.{1,16}$/,
        inputErrorMessage: '驳回原因限制在 1 至 16 字符以内， 请重新输入'
      })
        .then(async({ value }) => {
          row.rejectReason=value;
          rejectBusDisaster(row).then((resp) => {
            if (resp) {
              this.getEmpList();
            }
          });
        })
        .catch(() => {
          this.$message({
            type: "info",
            message: "已取消通过",
          });
        });
    },

  },
};
</script>

<style scoped>
aside {
  background: none;
  display: flex;
  justify-content: center;
  align-items: center;
}

.statisticsClass span {
  margin-left: 20px;
}

.colorStatisticsClass div {
  width: 18px;
  height: 18px;
  display: inline-block;
  margin-left: 6px;
}

.avatar-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 178px;
  height: 178px;
}

.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.image{
  width: 100px;
  height: 100px;
  float:left;
}

.position{
  margin-left: 17%;
  width: 64%;
  margin-top: 3.5%;
  z-index: 1;
}
</style>

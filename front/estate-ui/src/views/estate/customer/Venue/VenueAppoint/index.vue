<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="3" :xs="24">
        <div class="head-container">
          <div class="tag-group">
            <el-tag type="success"  @click.native="l">列表界面</el-tag>
            <el-tag type="success"  @click.native="t">图形界面</el-tag>
            <hr>
          </div>
          <el-input
            placeholder="请输入小区名称"
            clearable
            v-model="filterText">
          </el-input>
          <el-tree
            :data="currentList"
            :props="currentListProps1"
            :expand-on-click-node="false"
            ref="tree"
            default-expand-all
            highlight-current
            node-key="communityId"
            :filter-node-method="filterNode"
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>

      <el-col :span="21" :xs="24">
        <div v-show="openView === 0">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
            <el-form-item label="预约人姓名" prop="userName">
              <el-input
                v-model="queryParams.userName"
                placeholder="请输入预约人姓名"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="预约人手机号" prop="userPhone">
              <el-input
                v-model="queryParams.userPhone"
                placeholder="请输入预约人手机号"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="场馆名称" prop="venueId" label-width="70px">
              <el-select v-model="queryParams.venueId" placeholder="请选择场馆名称">
                <el-option
                  v-for="(item, index) in venueList1"
                  :key="index"
                  :label="item.venueName"
                  :value="item.venueId"
                ></el-option>
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
                v-hasPermi="['system:appoint:add']"
              >新增</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="success"
                plain
                icon="el-icon-edit"
                size="mini"
                :disabled="single"
                @click="handleUpdate"
                v-hasPermi="['system:appoint:edit']"
              >修改</el-button>
            </el-col>
            <el-col :span="1.5">
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="warning"
                plain
                icon="el-icon-download"
                size="mini"
                @click="handleExport"
                v-hasPermi="['system:appoint:export']"
              >导出</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
          </el-row>

          <el-table v-loading="loading" :data="appointList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
<!--            <el-table-column label="主键" align="center" prop="appointId" width="80" fixed />-->
            <el-table-column label="预约人姓名" align="center" prop="userName" width="150" fixed/>
            <el-table-column label="预约人手机号" align="center" prop="userPhone" width="150" :show-overflow-tooltip="true"/>
            <el-table-column label="活动名称" align="center" prop="activityName" width="150" :show-overflow-tooltip="true"/>
            <el-table-column label="场馆名称" align="center" prop="venueName" width="150" :show-overflow-tooltip="true" />
            <el-table-column label="计划开始时间" align="center" prop="planStart" width="180">
            </el-table-column>
            <el-table-column label="计划结束时间" align="center" prop="planEnd" width="180">
            </el-table-column>

            <el-table-column label="使用状态" align="center" prop="statusName" width="100">
              <template slot-scope="scope">
                <span v-if="scope.row.statusName == '0'">进行中</span>
                <span v-else-if="scope.row.statusName == '1'">未开始</span>
                <span v-else-if="scope.row.statusName == '2'">已结束</span>
                <span v-else-if="scope.row.statusName == '4'">已终止</span>
              </template>
            </el-table-column>

            <el-table-column label="支付状态" align="center" prop="appointStatus" width="100">
              <template slot-scope="scope">
                <span v-if="scope.row.appointStatus == '0'">未支付</span>
                <span v-else-if="scope.row.appointStatus == '1'">已支付</span>
                <span v-else-if="scope.row.appointStatus == '2'">已退款</span>
                <span v-else-if="scope.row.appointStatus == '3'">无需退款</span>
                <span v-else>其他</span>
              </template>
            </el-table-column>
            <el-table-column label="支付方式" align="center" prop="payWay" width="100">
              <template slot-scope="scope">
                <span v-if="scope.row.payWay == '0'">微信</span>
                <span v-else-if="scope.row.payWay == '1'">支付宝</span>
                <span v-else-if="scope.row.payWay == '2'">现金</span>
                <span v-else-if="scope.row.payWay == '3'">-</span>
                <span v-else>其他</span>
              </template>
            </el-table-column>
            <el-table-column label="总金额" align="center" prop="payAmount" width="100" />
            <el-table-column label="操作" fixed="right" align="center" class-name="small-padding fixed-width" width="200">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="look(scope.row)"
                >查看
                </el-button>
                <!--          <span v-if="scope.row.statusName === '1'">-->
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  v-if="scope.row.statusName === '1'"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['system:appoint:edit']"
                >修改
                </el-button>
                <!--          </span>-->
                <!--          <span v-else-if="!(scope.row.statusName !== '0' || scope.row.statusName === '4')">-->
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-video-pause"
                  @click="z(scope.row)"
                  style="color: orange"
                  v-else-if="!(scope.row.statusName !== '0' || scope.row.statusName === '4')"
                >终止
                </el-button>
                <!--          </span>-->
                <!--          <span v-else-if="scope.row.statusName === '2' && scope.row.appointStatus === '0'">-->
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="j(scope.row)"
                  v-else-if="scope.row.statusName === '2' && scope.row.appointStatus === '0'"
                >结账
                </el-button>
                <!--          </span>-->
                <span v-if="scope.row.statusName !== '0' ">

                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    style="color: #f56c6c"
                    @click="handleDelete(scope.row)"
                    v-hasPermi="['system:appoint:remove']"
                  >删除
                </el-button>

                </span>


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




          <!--          查看-->
          <el-dialog :title="title" :visible.sync="v1lsjl" width="1300px">
            <div>
              <el-row  :gutter="20">
                <el-form >
                  <el-col :span="8">
                    <el-form-item label="预约人姓名">
                      <span>{{lklist.userName}}</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="预约人手机号">
                      <span>{{lklist.userPhone}}</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="预约人身份证号">
                      <span>{{lklist.cardNo}}</span>
                    </el-form-item>
                  </el-col>
                </el-form>


                <el-form >
                  <el-col :span="8">
                    <el-form-item label="时间">
                      <span>{{lklist.planStart}} 至 {{lklist.planEnd}}</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="活动类型">
                      <span>{{lklist.actName}}</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="活动名称">
                      <span>{{lklist.activityName}}</span>
                    </el-form-item>
                  </el-col>
                </el-form>


                <el-form >
                  <el-col :span="8">
                    <el-form-item label="结账时间">
                      <span>{{lklist.currentEnd}}</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="总金额">
                      <span>{{lklist.payAmount}}</span>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8">
                    <el-form-item label="使用状态">
                      <span v-if="lklist.statusName === '0'">进行中</span>
                      <span v-else-if="lklist.statusName === '1'">未开始</span>
                      <span v-else-if="lklist.statusName === '2'">已结束</span>
                      <span v-else-if="lklist.statusName === '4'">已终止</span>
                    </el-form-item>
                  </el-col>
                </el-form>


              </el-row>
            </div>
          </el-dialog>





          <!--终止-->
          <el-dialog title="终止" :append-to-body="true" :visible.sync="open1" width="30%">

            <el-form label-width="90px">
              <el-form-item label="是否退款">
                <el-radio v-model="ozl.appointStatus" label="2">是</el-radio>
                <el-radio v-model="ozl.appointStatus" label="3">否</el-radio>
              </el-form-item>
              <span v-if="ozl.appointStatus === '2'">

          <el-form-item label="退款金额">
          <el-input v-model.number="ozl.refundAmount" type="number" placeholder="请输入金额" />
          </el-form-item>

          <el-form-item label="退款方式">
            <el-select v-model="ozl.refundWay" placeholder="退款方式" clearable size="small">
              <el-option
                v-for="dict in dict.type.pay_way"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="退款原因">
          <el-input v-model="ozl.refundReason" placeholder="请输退款原因" />
          </el-form-item>

        </span>
            </el-form>
            <el-button @click="cancel">取 消</el-button>
            <el-button type="primary" @click="ozb">确 定</el-button>
          </el-dialog>

          <!--    结账-->
          <el-dialog title="结账" :append-to-body="true" :visible.sync="open2" width="30%" >

            <el-form label-width="90px" :model="ojl" :rules="ojlRules" ref="ojl" >
              <el-form-item label="结账金额" prop="payAmount">
<!--                <el-input v-model.number="ojl.payAmount"   placeholder="请输入" />-->
                <el-input-number v-model.number="ojl.payAmount" :min="0" :max="99999" :precision="2" :step="1"  ></el-input-number>
              </el-form-item>

              <el-form-item label="结账方式" prop="payWay">
                <el-select v-model="ozl.payWay" placeholder="结账方式" clearable size="small"  @change="payWayHandler">
                  <el-option
                    v-for="dict in dict.type.pay_way"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  />
                </el-select>
              </el-form-item>

            </el-form>
            <el-button @click="cancel">取 消</el-button>
            <el-button type="primary" @click="ojb">确 定</el-button>
          </el-dialog>



          <!-- 添加或修改场馆活动对话框 -->
          <el-dialog :title="title" :visible.sync="open" width="1200px" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="120px">
              <el-form-item label="预约人姓名" prop="userName">
                <el-input v-model="form.userName" placeholder="请输入预约人姓名" />
              </el-form-item>
              <el-form-item label="预约人手机号" prop="userPhone">
                <el-input v-model="form.userPhone" placeholder="请输入预约人手机号" />
              </el-form-item>
              <el-form-item label="预约人身份证号" prop="cardNo">
                <el-input v-model="form.cardNo" placeholder="请输入预约人身份证号" />
              </el-form-item>
              <el-form-item label="场馆名称" prop="venueId">
                <el-select v-model="form.venueId" placeholder="请输入场馆名称">
                  <el-option
                    v-for="(item, index) in venueList"
                    :key="index"
                    :label="item.venueName"
                    :value="item.venueId"
                  ></el-option>
                </el-select>
              </el-form-item>
              <el-form-item label="活动类型" prop="activityId">

                <el-select v-model="form.activityId"  placeholder="请选择活动类型">
                  <el-option
                    v-for="(item, index) in venueactList"
                    :key="index"
                    :label="item.activityName"
                    :value="item.activityId"
                  ></el-option>
                </el-select>

              </el-form-item>
              <el-form-item label="活动名称" prop="activityName">
                <el-input v-model="form.activityName" placeholder="请输入活动名称" />
              </el-form-item>

              <el-form-item label="关于时间说明">
                <span>使用时间段: {{Availabletimeperiod}}</span>
              </el-form-item>
              <el-form-item label="预约时间段">
                <el-time-select
                  placeholder="起始时间"
                  v-model="form.planStart1"
                  value-format="HH:mm"
                  format="HH:mm"
                  :picker-options="{
                  start: d1,
                  end: d2
                  }">
                </el-time-select>

                <el-time-select
                  placeholder="结束时间"
                  v-model="form.planEnd1"
                  value-format="HH:mm"
                  format="HH:mm"
                  :picker-options="{
                  start: d1,
                  end: d2,
                  minTime: form.planStart1
                  }">
                </el-time-select>
              </el-form-item>

              <el-form-item label="预约日期" prop="date">
                <el-date-picker
                  v-model="form.date"
                  type="date"
                  placeholder="选择日期"
                  value-format="yyyy-MM-dd"
                >
                </el-date-picker>
              </el-form-item>


            </el-form>
            <div slot="footer" class="dialog-footer">
              <el-button type="primary" @click="submitForm">确 定</el-button>
              <el-button @click="cancel">取 消</el-button>
            </div>
          </el-dialog>
        </div>

        <div v-show="openView === 1">
          <div>
            <el-row :gutter="20" >
                <span>可用场馆</span>
                <hr>
              <div style="display: flex;flex-wrap: wrap;">
              <el-col :span="6" v-for="(item,index) in tlistdata" :key="index">
                <el-card v-if="tlistdata[index].venueStatus === '0'">
                  <div slot="header" class="clearfix">
                    <span style="color: green">{{tlistdata[index].venueName}}</span>
                    <br>
                    <span style="color: #8c939d;font-size: 1px">{{tlistdata[index].address}}</span>
                    <el-text style="float: right; paduserding: 3px 0" type="text">状态：开启</el-text>
                  </div>

                  <div v-if="tlistdata[index].user!=null" >
                    <span v-for="(item,i) in tlistdata[index].user" :key="i" class="text" style="font-size: 1px;text-align: right;">
                    <br>{{'日期：'+tlistdata[index].user[i].planStart+'——'+tlistdata[index].user[i].planEnd+'  '+tlistdata[index].user[i].userName+"    已预约"}}
                    </span>
                  </div>
                  <div v-else>
                    <span>今日无预约</span>
                  </div>
                  <br>
                  <el-button @click="vebuesee(tlistdata[index].venueId)">查看</el-button>
                </el-card>
                <br>
              </el-col>
              </div>
            </el-row>
            <span>不可用场馆</span>
            <hr>
            <el-row :gutter="20">
              <el-col :span="6" v-for="(item,index1) in tlistdata1" >
                <el-card v-if="tlistdata1[index1].venueStatus === '1'">
                  <div slot="header" class="clearfix">
                    <span style="color: green">{{tlistdata1[index1].venueName}}</span>
                    <el-text style="float: right; padding: 3px 0" type="text">状态：关闭</el-text>
                  </div>
                </el-card>
                <br>
                <br>
              </el-col>
            </el-row>
          </div>
          <el-dialog :title="title" :visible.sync="v2lsjl" width="1100px" append-to-body>
            <div >
              <span>
                今日预约状况
              </span>
              <hr>
              <div v-for="(item,idx) in venueLsjl[0]" :key="idx">
                <div>
                  今日第{{idx+1}}个预约
                  <el-row  :gutter="20">
                    <el-form >
                      <el-col :span="6">
                        <el-form-item label="人员姓名">
                          <span>{{venueLsjl[0][idx].userName}}</span>
                        </el-form-item>
                      </el-col>
                      <el-col :span="6">
                        <el-form-item label="手机号">
                          <span>{{venueLsjl[0][idx].userPhone}}</span>
                        </el-form-item>
                      </el-col>
                      <el-col :span="6">
                        <el-form-item label="身份证号">
                          <span>{{venueLsjl[0][idx].cardNo}}</span>
                        </el-form-item>
                      </el-col>
                    </el-form>
                  </el-row>

                  <el-row >
                    <el-form >
                      <el-col :span="6">
                        <el-form-item label="时间">
                          <span>{{venueLsjl[0][idx].planStart}}—{{venueLsjl[0][idx].planEnd}}</span>
                        </el-form-item>
                      </el-col>
                      <el-col :span="6">
                        <el-form-item label="活动类型">
                          <span>{{venueLsjl[0][idx].actName}}</span>
                        </el-form-item>
                      </el-col>
                      <el-col :span="6">
                        <el-form-item label="活动名称">
                          <span>{{venueLsjl[0][idx].activityName}}</span>
                        </el-form-item>
                      </el-col>
                    </el-form>
                  </el-row>

                  <el-row >
                    <el-form >
                      <el-col :span="6">
                        <el-form-item label="押金">
                          <span>{{venueLsjl[0][idx].deposit}}</span>
                        </el-form-item>
                      </el-col>
                      <el-col :span="6">
                        <el-form-item label="总金额">
                          <span>{{venueLsjl[0][idx].payAmount}}</span>
                        </el-form-item>
                      </el-col>
                      <el-col :span="6">
                        <el-form-item label="状态">
                          <span v-if="venueLsjl[0][idx].statusName === '0'">进行中</span>
                          <span v-else-if="venueLsjl[0][idx].statusName === '1'">未开始</span>
                          <span v-else-if="venueLsjl[0][idx].statusName === '2'">已结束</span>
                        </el-form-item>
                      </el-col>
                    </el-form>
                  </el-row>

                </div>
              </div>
            </div>
            <span>
              历史预约详情
            </span>
            <hr>
            <el-table :data="venueLsjl[1]">
              <el-table-column label="预约人" align="center" prop="userName" />
              <el-table-column label="手机号" align="center" prop="userPhone" />
              <el-table-column label="身份证" align="center" prop="cardNo" />
              <el-table-column label="计划开始时间" align="center" prop="planStart" width="180">
              </el-table-column>
              <el-table-column label="计划结束时间" align="center" prop="planEnd" width="180">
              </el-table-column>
              <el-table-column label="活动类型" align="center" prop="activityId" />
              <el-table-column label="活动名称" align="center" prop="activityName" />
              <el-table-column label="押金" align="center" prop="deposit" />
              <el-table-column label="总金额" align="center" prop="payAmount" />
              <el-table-column label="预约时间" align="center" prop="createTime" width="180"/>
              <el-table-column label="使用状态" align="center" prop="statusName" >
                <template slot-scope="scope">
                  <span v-if="scope.row.statusName == '0'">进行中</span>
                  <span v-else-if="scope.row.statusName == '1'">未开始</span>
                  <span v-else-if="scope.row.statusName == '2'">已结束</span>
                  <span v-else-if="scope.row.statusName == '4'">已终止</span>
                  <span v-else>其他</span>
                </template>
              </el-table-column>
            </el-table>
            <div>
              <el-button type="primary" @click="cancel">返 回</el-button>
            </div>
          </el-dialog>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {
  listAppoint,
  getAppoint,
  delAppoint,
  addAppoint,
  updateAppoint,
  getvenue,
  getvenueact,
  zz, jzc, jz, tlist, getvenueByvenueId, getvenue1, getAppoint1
} from "@/api/estate/customer/Venue/VenueAppoint";
import {addrule, gl,updaterule} from "@/api/estate/customer/Venue/VenueRule";
import {getCurrentList} from "@/api/estate/community";
export default {
  name: "Appoint",
  dicts: ['pay_way'],
  data() {
    return {
      datetime: [],
      filterText:'',
      //小区数据集合
      currentList:[],
      //场馆集合
      venueList:[],
      //活动集合
      venueactList:[],
      venueList1:[],
      venueLsjl:[],
      lklist:[],
      //终止是否退款
      ist:"0",
      Availabletimeperiod:null,
      d1:null,
      d2:null,
      // 遮罩层
      loading: true,
      open1:false,
      open2:false,
      openView:0,
      wyy:'今日无预约',
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
      // 场馆活动表格数据
      appointList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      v1lsjl :false,
      v2lsjl:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        userPhone: null,
        venueId: null,
      },
      currentListProps1: {
        children: "communityId",
        label: "communityName"
      },
      // 表单参数
      form: {},
      ozl:{},
      ojl:{
        payAmount:''//校验金额
      },
      tlistdata:{},
      tlistdata1:{},

      // 表单校验
      rules: {
        userName: [
          { required: true, message: "预约人不能为空", trigger: "blur" }
        ],
        userPhone: [
          { required: true, message: "预约人手机号不能为空", trigger: "blur" },
          {pattern:/^((0\d{2,3}-\d{7,8})|(1[34578]\d{9}))$/, message: '请正确输入手机号', trigger: 'blur' }
        ],
        cardNo: [
          { required: true, message: "预约人身份证不能为空", trigger: "blur" },
          { pattern:/(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/, message: '请正确输入身份证号码', trigger: 'blur' }
        ],
        activityId: [
          { required: true, message: "活动类型不能为空", trigger: "blur" }
        ],
        activityName: [
          { required: true, message: "活动名称不能为空", trigger: "blur" },
          {min: 0,max: 10,message: '活动名称不能超过十个字符', trigger: 'blur' }
        ],
        venueId: [
          { required: true, message: "场馆不能为空", trigger: "blur" }
        ],
        date: [
          { required: true, message: "预约日期不能为空", trigger: "blur" }
        ],
        communityId: [
          { required: true, message: "小区不能为空", trigger: "blur" }
        ],
      },
      ojlRules:{
        payAmount: [//只能输入金额
          { required: true, message: '结账金额不能为空'},
          { type: 'number', message: '结账金额必须为数字值'}
        ],
        payWay: [
          { required: true, message: "结账方式不能为空", trigger: "change" }
        ],
      },
      tdata:{},
    };
  },
  created() {
    this.getCurrent();
    // this.getv1();
    },

  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },


  methods: {

    payWayHandler(val){
      this.ojl.payWay=val;
    },

    // 过滤树
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },


    //列表预约查看
    look(row){
      this.reset();
      getAppoint(row.appointId).then(response => {
        this.lklist = response.data
        this.v1lsjl = true;
      });
    },


    //场馆历史记录
    vebuesee(row){
      getvenueByvenueId(row).then(res =>{
        this.venueLsjl = res.data
        this.v2lsjl = true;
      })
    },

    tuxing(){
      if (this.queryParams.communityId !==null || this.queryParams.communityId !=='' || this.queryParams.communityId !== undefined){
        this.tdata.communityId = this.queryParams.communityId
      }
      tlist(this.tdata).then(res =>{
        this.tlistdata = res.data[0];
        this.tlistdata1 = res.data[1];
      })
    },

    //图形界面
    t(){
      this.openView = 1;
    },
    //列表界面
    l(){
      this.openView = 0;
    },
    //终止
    z(row){
      this.ozl = {};
      this.ozl.appointId = row.appointId;
      this.open1 = true;
    },
    ozb(){
      if (this.ozl.appointStatus === undefined || this.ozl.appointStatus === null){
        console.log(this.ozl.appointStatus)
        this.$message.warning("请选择是否退款")
        return
      }
      if (this.ozl.appointStatus === '2'){
        if (this.ozl.refundAmount === null || this.ozl.refundAmount==="" || this.ozl.refundAmount===undefined){
          this.$message.warning("请正确的填写金额")
          return
        }
        if ((this.ozl.refundAmount*1) <0){
          this.$message.warning("金额必须大于0")
          return
        }
        if (this.ozl.refundWay === null || this.ozl.refundWay==="" || this.ozl.refundWay===undefined){
          this.$message.warning("请选择退款方式")
          return
        }

      }
      zz(this.ozl).then(res =>{
        this.$modal.msgSuccess("终止成功");
        this.open1 = false;
        this.getList();
      })
    },
    resetOjl() {
      this.ojl={
        payAmount:undefined,
        payWay:undefined,
      }
      this.resetForm("ojl");
    },
    //结账
    j(row) {
      // this.ojl = {};
      this.resetOjl();//重置 ojl表单
      jzc(row.appointId).then(res => {
        this.$set(this.ojl, "payAmount", res.data.payAmount)
        this.$set(this.ojl, "payWay", res.data.payWay)
        this.$set(this.ojl, "planStart", row.planStart)
        this.$set(this.ojl, "planEnd", row.planEnd)
        this.ojl.appointId = row.appointId;

      })
      this.open2 = true;
    },
    ojb() {
      if (this.ozl.payWay === undefined) {
        this.$modal.msgError(`结账方式不能为空!`);
        return;
      }
      this.$refs["ojl"].validate(valid => { //校验表单
        if (valid) {
          jz(this.ojl).then(res => {
            this.$modal.msgSuccess("结账成功");
            this.open2 = false;
            this.getList();
          })
        }
      });

    },

    getv(){
      this.venueList = []
      this.venueactList = []
      this.form.venueId = null
      this.form.activityId = null
      if (this.form.communityId !=null){
        getvenue(this.form.communityId).then(res =>{
          this.venueList = res.data;
        })
        getvenueact(this.form.communityId).then(res =>{
          this.venueactList = res.data
        })
      }
    },
    getv1(){
      this.venueList = []
      this.venueactList = []
      if (this.form.communityId !=null){
        getvenue(this.form.communityId).then(res =>{
          this.venueList = res.data;
        })
        getvenueact(this.form.communityId).then(res =>{
          this.venueactList = res.data
        })
      }
    },

    getv2(){
      this.venueList1 = []
      if (this.queryParams.communityId != null){
        getvenue(this.queryParams.communityId).then(res =>{
          this.venueList1 = res.data;
        })
      }
    },


    // getv1(){
    //   this.venueList1 = []
    //   getvenue1().then(res =>{
    //     this.venueList1 = res.data;
    //   })
    // },

    getCurrent(){
      getCurrentList().then(res =>{
        this.currentList = res.data;
        this.queryParams.communityId = this.currentList[0].communityId;
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.currentList[0].communityId);
        });
        this.tuxing();
        this.getList();
      })
    },

    /** 查询场馆活动列表 */
    getList() {

      if(isNaN(this.queryParams.venueId)){
        this.$modal.msgWarning("场馆id不能是非数值");
        return false;
      }

      this.loading = true;
      listAppoint(this.queryParams).then(response => {
        this.appointList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.open1 = false;
      this.open2 = false;
      this.v2lsjl =false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        appointId: null,
        communityId: null,
        userName: null,
        userPhone: null,
        cardNo: null,
        activityId: null,
        activityName: null,
        venueId: null,
        planStart: null,
        planEnd: null,
        deposit: null,
        appointStatus: "0",
        payWay: null,
        payAmount: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null
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
      this.filterText =''
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.appointId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      if (this.queryParams.communityId == null){
        this.$message.warning("请选择小区后新增")
        return
      }
      this.form.communityId = this.queryParams.communityId;
      // 查看场馆可预约时间段
      gl().then(res =>{
        console.log(res)
        this.Availabletimeperiod = res.data.startTime + " 至 " + res.data.endTime
        this.d1 = res.data.startTime;
        this.d2 = res.data.endTime;
      })
      this.getv();
      this.open = true;
      this.title = "新增场馆预约";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      // 查看场馆可预约时间段
      gl().then(res =>{
        console.log(res)
        this.Availabletimeperiod = res.data.startTime + " 至 " + res.data.endTime
        this.d1 = res.data.startTime;
        this.d2 = res.data.endTime;
      })
      this.reset();
      const appointId = row.appointId || this.ids
      getAppoint1(appointId).then(response => {
        this.form = response.data;
        console.log(this.form.venueId)
        //获取场馆和类型
        this.getv1();
        this.open = true;
        this.title = "修改场馆预约";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.appointId != null) {
            if (this.form.planStart1 == null || this.form.planEnd1 == null){
              this.$modal.msgError("请选择时间段");
              return false
            }
            if (this.comparDate(this.form.date)){
              this.$modal.msgError("请勿选择小于当前日期的数据");
              return false
            }
            let starttime = this.form.date +" "+this.form.planStart1
            let endtime = this.form.date +" "+this.form.planEnd1
            this.form.planStart = starttime;
            this.form.planEnd = endtime;
            updateAppoint(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            if (this.form.planStart1 == null || this.form.planEnd1 == null){
              this.$modal.msgError("请选择时间段");
              return false
            }
            if (this.comparDate(this.form.date)){
              this.$modal.msgError("请勿选择小于当前日期的数据");
              return false
            }
            let starttime = this.form.date +" "+this.form.planStart1
            let endtime = this.form.date +" "+this.form.planEnd1
            this.form.planStart = starttime
            this.form.planEnd = endtime
            addAppoint(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

    comparDate(dateValue) {
      return new Date().getTime() >= new Date(dateValue).getTime() + 3600 * 1000 * 24;
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const appointIds = row.appointId || this.ids;
      this.$modal.confirm('是否确认删除?').then(function() {
        return delAppoint(appointIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },

    handleNodeClick(ro){
      this.queryParams.communityId = ro.communityId
      this.getv2();
      this.tuxing();
      this.getList();
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download('system/appoint/export', {
        ...this.queryParams
      }, `appoint_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

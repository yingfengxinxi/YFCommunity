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
            <el-form-item label="计划名称" prop="carportNo">
              <el-input
                v-model="queryParams.planName"
                placeholder="请输入计划名称"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="分配方式" prop="carportNature">
              <el-select
                v-model="queryParams.allotWay"
                placeholder="请选择分配方式"
              >
                <el-option label="自动分配" value="0" />
                <el-option label="管理员分配" value="1" />
                <el-option label="抢单" value="2" />
              </el-select>
            </el-form-item>
            <el-form-item label="状态" prop="carportStatus">
              <el-select
                v-model="queryParams.planOrder"
                placeholder="请选择状态"
              >
                <el-option label="开启" value="1" />
                <el-option label="关闭" value="0" />
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
              @click="addMethod"
            >新增</el-button
            >
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
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
              label="计划名称"
              align="center"
              prop="planName"
              type="number"
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
              label="保洁地点"
              align="center"
              prop="address"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="保洁日期"
              align="center"
              prop="startTime"
              :show-overflow-tooltip="true">
              <template slot-scope="scope">{{scope.row.startTime}} -{{scope.row.endTime}}</template>
            </el-table-column>
              <el-table-column
              label="状态"
              align="center"
              prop="planOrder"
              :show-overflow-tooltip="true"
            >
              <template slot-scope="scope">
                <el-switch
                  v-model="scope.row.planOrder"
                  active-value="1"
                  inactive-value="0"
                  @change="handleStatusChange(scope.row)"
                ></el-switch>
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
                <el-button size="mini" type="text" @click="detail(scope.row)"
                ><el-link icon="el-icon-view" type="brand" :underline="false"
                >详情</el-link
                ></el-button>
                <el-button
                  v-show="scope.row.planOrder==0"
                  size="mini"
                  type="text"
                  @click="update(scope.row)"
                ><el-link
                  icon="el-icon-edit"
                  type="success"
                  :underline="false"
                >修改</el-link
                ></el-button>
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
      :title="titles"
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
              <el-form-item label="计划名称: " prop="planName">
                <el-input
                  clearable
                  v-model="addForm.planName"
                  placeholder="请输入计划名称"
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
              <el-form-item label="保洁模式: " prop="planModel">
                <template>
<!--
                  <el-radio
                    @change="modelRadionumber"
                    v-model="addForm.planModel"
                    label="0">一日多次</el-radio>
-->
                  <el-radio
                    @change="modelRadioTime"
                    v-model="addForm.planModel"
                    label="1">多日一次</el-radio>
                </template>
              </el-form-item>
            </el-col>
            <div v-show="number">
              <el-col>
                <el-form-item label="保洁次数: " prop="planNum">
                  <el-input-number
                    clearable
                    v-model="addForm.planNum"
                    placeholder="请输入次数/天数"
                    :min='1'
                  ></el-input-number>
                </el-form-item>
              </el-col>
              <el-col>
                <el-form-item label="每次保洁间隔时间: " prop="gapTime">
                  <el-input-number v-model='addForm.gapTime' :precision='2' :step='0.5' :min="0" size='Max'></el-input-number>  小时
                </el-form-item>

              </el-col>
            </div>
            <div v-show="frequency">
              <el-col>
                <el-form-item label="保洁频率: " prop="planNum">
                  <el-input-number
                    clearable
                    v-model="addForm.planNum"
                    placeholder="请输入天/次"
                    :min="1"
                  ></el-input-number>   天数
                </el-form-item>
              </el-col>
            </div>
            <!-- ----------------------------------  -->

            <el-col>
              <el-form-item label="计划保洁开始时间: " prop="startTime">
                <el-time-picker
                   v-show="time"
                   v-model="addForm.startTime"
                   placeholder="选择开始时间"
                   format="HH:mm"
                  value-format="HH:mm"
                   >
                  </el-time-picker>

                <el-date-picker
                  v-show="datatime"
                  v-model="addForm.startTime"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col>
              <el-form-item label="计划保洁结束时间: " prop="endTime">
                <el-time-picker
                  v-show="time"
                  v-model="addForm.endTime"
                  placeholder="选择结束时间"
                  format="HH:mm"
                  value-format="HH:mm"
                >
                </el-time-picker>


                <el-date-picker
                  v-show="datatime"
                  v-model="addForm.endTime"
                  format="yyyy-MM-dd"
                  value-format="yyyy-MM-dd"
                  placeholder="选择日期">
                </el-date-picker>
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

    <!-- 详情页面 -->
    <div v-show="showDetail">
      <el-form ref="form" :model="detailForm" label-width="180px">
        <!--  ----------------------------- 计划详情 --------------------------- -->
        <h5><b>计划详情</b></h5>
        <el-divider></el-divider>
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="计划编号: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.planId }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="计划名称: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.planName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="巡检类型: ">
              <div style="width: 280px; overflow: hidden">
                保洁工单
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="计划状态: ">
              <div style="width: 280px; overflow: hidden">
                <div v-if="detailForm.planOrder==1">开启</div>
                <div v-if="detailForm.planOrder==0">关闭</div>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <!--  ----------------------------- 保洁设置 --------------------------- -->
        <h5><b>保洁设置</b></h5>
        <el-divider></el-divider>
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="保洁模式: ">
              <div style="width: 280px; overflow: hidden">
                <div v-if="detailForm.planModel==1">多日一次</div>
                <div v-if="detailForm.planModel==0">一日多次</div>
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="保洁次数: ">
              <div style="width: 280px; overflow: hidden">
                <div v-if="detailForm.planModel==0"> {{ detailForm.planNum }}次/ 天</div>
                <div v-if="detailForm.planModel==1 ">{{ detailForm.planNum }}天/ 次</div>
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="间隔时间: ">
              <div style="width: 280px; overflow: hidden">
                <div v-if="detailForm.planModel==0">{{ detailForm.gapTime }} h</div>
                <div v-if="detailForm.planModel==1">--</div>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="计划开始时间: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.startTime }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="计划结束时间: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.endTime }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>

        <!--  ----------------------------- 工单生成历史 --------------------------- -->
        <div>
          <h5><b>工单生成历史</b></h5>
        </div>
        <el-divider></el-divider>
        <el-row>
          <el-col :span="24">
            <el-table
              border
              type="index"
              highlight-current-row
              :data="busCleanList"
            >
              <el-table-column
                label="工单编号"
                align="center"
                :show-overflow-tooltip="true"
                prop="cleanNo"
              />
              <el-table-column
                label="工单名称"
                align="center"
                :show-overflow-tooltip="true"
                prop="cleanName"
              />
              <el-table-column
                label="巡检类型"
                align="center"
                :show-overflow-tooltip="true"
                prop="planModelName"
              >
                <template slot-scope="scope">
                 保洁类型
                </template>
              </el-table-column>
              <el-table-column
                label="分配方式"
                align="center"
                :show-overflow-tooltip="true"
                prop="allotWay"
              >
                <template slot-scope="scope">
                  <div v-if="scope.row.allotWay==0">自动分配</div>
                  <div v-if="scope.row.allotWay==1">管理员分配</div>
                  <div v-if="scope.row.allotWay==2">抢单</div>
                </template>
              </el-table-column>
              <el-table-column
                label="主要负责人"
                align="center"
                :show-overflow-tooltip="true"
                prop="staffName"
              />
              <el-table-column
                label="生成时间"
                align="center"
                :show-overflow-tooltip="true"
                prop="createTime"
              >
                <template slot-scope="scope">
                  {{ parseTime(scope.row.createTime) }}
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
  removes,
  saveOrUpdate,
  updateDetail,
  changeCarportStatus,
  detailList
} from "@/api/estate/work/cleaningManagement/plan";
export default {
  data() {
    return {
      //时间框显示
      datatime: false,
      time: false,
      // 搜索
      search: '',
      //一天多次
      number: false,
      //多少天1次
      frequency:false,
      // 楼栋下拉框
      buildingData: [],
      buildingValue: "",
      //详细
      deatilListGenerate:{},
      // 新增页面表单
      addForm: {},
      // 小区ID
      communityId: undefined,
      // 小区ID 新增修改传参
      communityIds: null,
      // 详情内表格数据
      busCleanList: [],
      //小区集合
      deptData:[],
      // 详情表单
      detailForm: {},
      // 机构下拉框
      deptValue: "",
      // 新增弹窗
      addOpen: false,
      //标题
      titles: "",
      // 转出弹窗
      cedeOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        // 小区ID
        communityId: undefined,
        planName: undefined,
        allotWay:undefined,
        planOrder: undefined,
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
        planName: [
          { required: true, message: "计划名称不能为空", trigger: "blur" },
        ],
        address: [
          { required: true, message: "保洁地址不能为空", trigger: "blur" },
        ],
        startTime: [
          { required: true, message: "请选择计划开始时间", trigger: "blur" },
        ],
        planNum: [
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
  },

  methods: {
    //一天多次
    modelRadionumber() {
      this.number = true;
      this.frequency = false;
      this.time = true;
      this.datatime =false;
      this.addForm.startTime = "";
      this.addForm.endTime = "";
    },
    //多天一次
    modelRadioTime() {
      this.number = false;
      this.frequency = true;
      this.time = false;
      this.datatime =true;
      this.addForm.gapTime = null;
      this.addForm.startTime = "";
      this.addForm.endTime = "";
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getBlackList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.planName = null;
      this.queryParams.allotWay = null;
      this.queryParams.planOrder = null;
      this.handleQuery();
    },
    /** 查询保洁计划列表 */
    getBlackList() {
      this.loading = false;
      listPage(this.queryParams).then((response) => {
        this.injList = response.rows;
        this.total = response.total;
      });
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.planId);
      this.multiple = !selection.length;
    },

    // 表单重置
    reset() {
      this.form = {
        planName: undefined,
        allotWay: undefined,
        planOrder: undefined,
      };
      this.addForm={
        planName: undefined,
        address: undefined,
        planModel: undefined,
        planNum: undefined,
        startTime: undefined,
        endTime: undefined
      };
      this.resetForm("form");
      this.resetForm("addForm");
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.showDetail = false;
      this.homeShow = true;
      this.getBlackList();
    },

    /** 详情 */
    detail(row) {
      this.showDetail = true;
      this.homeShow = false;
      this.selectView(row);
    },

    selectView(row) {
      let params = {
        planId: row.planId,
        communityId: row.communityId,
      };
      updateDetail(row.planId).then((response) => {
        this.detailForm = response.data;
      });
      detailList(params).then((response) => {
        this.busCleanList = response.data;
      });
    },
    // 机构下拉框
    getTypeList() {
      getCurrentList().then((response) => {
        this.deptData = response.data;
        this.queryParams.communityId = this.deptData[0].communityId;
        this.$refs.singleTable.setCurrentRow(this.deptData[0]);
        this.addForm.communityId = this.deptData[0].communityId;
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
      this.reset();
      //默认为一天多次
      this.addForm.planModel = "1";
      //当前所在小区
      this.addForm.communityId = this.communityIds;
      //打开一天多次框
      this.number = true;
      //打开时间框
      this.time = true;
      this.datatime = false;
      //隐藏多天一次
      this.frequency = false;
      this.planId = null;
      this.addOpen = true;
      this.titles="新增保洁计划";
    },

    // 关闭新增弹窗
    addClose() {
      this.addOpen = false;
      this.reset();
      this.getBlackList();
    },


    // 修改按键时间
    update(row) {
      this.planId = row.planId;
      if (row.planModel == 1) {
        this.number = false;
        this.frequency = true;
        this.datatime = true;
        this.time = false;
      }
      if (row.planModel == 0) {
        this.number = true;
        this.frequency = false;
        this.time = true;
        this.datatime = false;
      }
      this.addOpen = true;
      this.titles="修改保洁计划";
      //根据ID找出单个保洁计划
      this.updateDetails(row.planId);
      this.addForm.planId = row.planId;
    },

    //根据ID找出单个保洁计划
    updateDetails(planId) {
      updateDetail(planId).then((response) => {
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
          })
            .then(() => {
              saveOrUpdate(this.addForm).then(() => {
                this.getBlackList();
                this.addOpen = false;
                this.$refs.addForm.resetFields();
                this.$message({
                  type: "success",
                  message: "提交成功!",
                });
              })
            })
            .catch(() => {
              this.reset();
              this.cancel();
              this.$refs.addForm.resetFields();
              this.$message({
                type: "info",
                message: "已取消提交",
              });
            });
        }
      });
    },

    // 更改状态
    handleStatusChange(row) {
      let text = row.planOrder === "0" ? "关闭" : "开启";
      this.$modal.confirm("确认要修改状态 " + row.planName + " 为 " + text + " 吗？")
        .then(function () {
          return changeCarportStatus(row.planId);
        }).then(() => {
        this.getBlackList();
        if(text=="关闭"){
          this.$modal.msgSuccess("计划已关闭！");
        }
        if(text=="开启"){
          this.$modal.msgSuccess("计划已开启，工单将在第二日0点生成！");
        }
      }).catch(() => {
        row.planOrder = row.planOrder === "0" ? "1" : "0";
      });
    },

  }
}
</script>
<style scoped>
</style>

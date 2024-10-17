<template>
  <div class="app-container">
    <div v-show="!detailShow">
      <el-row :gutter="20">
        <!-- 树结构     -->
        <el-col :span="3" :xs="24">
          <div class="head-container">
            <el-input v-model="filterText" placeholder="请输入小区名称" clearable size="small"
                      prefix-icon="el-icon-search" style="margin-bottom: 20px"/>
          </div>
          <div>
            <el-tree
              :data="communityTreeData"
              :props="defaultProps"
              :expand-on-click-node="false"
              :filter-node-method="filterNode"
              ref="tree"
              highlight-current
              node-key="communityId"
              @node-click="handleNodeClick"
            >
              <!-- 插入楼栋图标 -->
              <span slot-scope="{ node }">
	   		          <i class="el-icon-office-building"/>
	   		          <span class="content" style="padding-left:5px;">{{ node.label }}</span>
		          </span>
            </el-tree>
          </div>
        </el-col>

        <el-col :span="21" :xs="24">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
            <el-form-item label="计划名称" prop="planName">
              <el-input placeholder="请输入计划名称" clearable v-model="queryParams.planName"/>
            </el-form-item>
            <el-form-item label="巡查类型" prop="patrolType">
              <el-input v-model="queryParams.patrolType" placeholder="请输入巡查类型"/>
<!--              <el-select
                v-model="queryParams.patrolTypeId"
                placeholder="请选择巡查类型"
                clearable
                style="width: 100%"
              >
                <el-option
                  v-for="patrolType in patrolTypeList"
                  :key="patrolType.tourId"
                  :label="patrolType.tourName"
                  :value="patrolType.tourId"
                />
              </el-select>-->
            </el-form-item>
            <el-form-item label="状态" prop="planOrder">
              <el-select
                v-model="queryParams.planOrder"
                placeholder="请选择状态"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="dict in dict.type.sys_normal_disable"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
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
                v-hasPermi="['patrol:plan:add']"
              >新增
              </el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="danger"
                plain
                icon="el-icon-delete"
                size="mini"
                :disabled="multiple"
                @click="handleDelete"
                v-hasPermi="['patrol:plan:delete']"
              >删除
              </el-button>
            </el-col>
          </el-row>

          <el-table ref="planRef" v-loading="loading" :data="list" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" :selectable="checkSelect"/>
            <el-table-column label="计划名称" align="center" prop="planName" :show-overflow-tooltip="true"/>
            <el-table-column label="巡查类型" align="center" prop="tourName"/>
            <el-table-column label="巡查点" align="center" prop="pointName"/>
            <el-table-column label="创建时间" align="center" prop="createTime"/>
            <el-table-column label="工单生成状态" align="center">
              <template slot-scope="scope">
                <el-switch
                  v-model="scope.row.planOrder"
                  active-color="#1890ff"
                  inactive-color="#c0c4cc"
                  active-value="0"
                  inactive-value="1"
                  @change="changeSwitch(scope.row,$event)"
                >
                </el-switch>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width" style="{'color':}">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="handleView(scope.row)"
                >查看
                </el-button>

                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['patrol:plan:edit']"
                  v-if="scope.row.planOrder === '1'"
                >修改
                </el-button>

                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  style="color: #f56c6c"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['patrol:plan:delete']"
                  v-if="scope.row.planOrder === '1'"
                >删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <pagination
            v-show="total>0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getListPage"
          />
        </el-col>
      </el-row>

      <!-- 添加或修改参数配置对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="660px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" label-width="120px">
          <el-form-item label="计划名称" prop="planName">
            <el-input v-model="form.planName" placeholder="请输入计划名称"/>
          </el-form-item>
          <el-form-item label="巡查类型" prop="tourId">
            <el-select
              v-model="form.tourId"
              placeholder="请选择巡查类型"
              clearable
              style="width: 100%"
            >
              <el-option
                v-for="patrolType in patrolTypeList"
                :key="patrolType.tourId"
                :label="patrolType.tourName"
                :value="patrolType.tourId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="巡查点" prop="pointId">
            <el-select
              v-model="form.pointId"
              placeholder="请选择巡查点"
              clearable
              style="width: 100%"
            >
              <el-option
                v-for="point in pointList"
                :key="point.pointId"
                :label="point.pointName"
                :value="point.pointId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="巡查模式" prop="planModel">
            <el-radio v-model="form.planModel" :label="planModel.id" border v-for="planModel in planModelList">
              {{ planModel.value }}
            </el-radio>
          </el-form-item>

          <!-- 一日多查 -->
          <div v-show="form.planModel === '0'">
            <el-form-item label="巡查次数" prop="planNum">
              <el-input-number v-model="form.planNum" controls-position="right" :min="1" :max="10"
                               style="width: 100%"></el-input-number>
            </el-form-item>
            <el-form-item label="计划时间" prop="timeRange">
              <el-time-picker
                is-range
                range-separator="至"
                v-model="form.timeRange"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围"
                value-format="HH:mm:ss"
                style="width: 100%">
              </el-time-picker>
            </el-form-item>
            <el-form-item label="巡查间隔时间" prop="gapTime">
              <el-input-number v-model="form.gapTime" controls-position="right" :min="1" :max="10"
                               :step="0.5" style="width: 100%"></el-input-number>
            </el-form-item>
          </div>

          <!-- 一日多查 -->
          <div v-show="form.planModel === '1'">
            <el-form-item label="巡查频率" prop="planNum">
              <el-input-number v-model="form.planNum" controls-position="right" :min="1" :max="10"
                               style="width: 100%"></el-input-number>
            </el-form-item>
            <el-form-item label="计划时间" prop="timeRange">
              <el-time-picker
                is-range
                range-separator="至"
                v-model="form.timeRange"
                start-placeholder="开始时间"
                end-placeholder="结束时间"
                placeholder="选择时间范围"
                value-format="HH:mm:ss"
                style="width: 100%">
              </el-time-picker>
            </el-form-item>
          </div>

        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">关 闭</el-button>
        </div>
      </el-dialog>
    </div>

    <!--  详情页  -->
    <div v-show="detailShow" v-cloak>
      <el-col :span="4" :offset="23" style="margin-top: 5px;">
        <el-button @click="cancelDetail">关 闭</el-button>
      </el-col>
      <el-form ref="detailForm" :model="detailData" label-width="140px">
        <h4 class="form-header">
          计划详情
        </h4>
        <el-row>
          <el-col :span="20">
            <el-row>
              <el-col :span="6" :offset="0">
                <el-form-item label="计划名称:">{{ detailData.planName }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="0">
                <el-form-item label="工单类型:">巡查工单</el-form-item>
              </el-col>
              <el-col :span="6" :offset="0">
                <el-form-item label="巡查类型:">{{ detailData.tourName }}</el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="6" :offset="0">
                <el-form-item label="巡查点:">{{ detailData.pointName }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="0">
                <el-form-item label="计划状态:">{{ detailData.planOrder }}</el-form-item>
              </el-col>
            </el-row>

            <h4 class="form-header">巡查设置</h4>
            <el-row>
              <el-col :span="6" :offset="0">
                <el-form-item label="巡查模式:">{{ planModelFormat(detailData.planModel) }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="0">
                <el-form-item label="巡查次数:">{{ detailData.planNum }}天/次</el-form-item>
              </el-col>
<!--              <el-col :span="6" :offset="0">-->
<!--                <el-form-item label="间隔时间:">{{ detailData.gapTime }}</el-form-item>-->
<!--              </el-col>-->
            </el-row>

            <el-row>
              <el-col :span="6" :offset="0">
                <el-form-item label="计划开始时间:">{{ detailData.startTime }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="0">
                <el-form-item label="计划结束时间:">{{ detailData.endTime }}</el-form-item>
              </el-col>
            </el-row>

          </el-col>
        </el-row>
        <h4 class="form-header">工单生成历史</h4>
        <el-row>
          <el-table :data="detailData.patrolOrderList">
            <el-table-column label="工单编号" align="center" key="patrolNo" prop="patrolNo"/>
            <el-table-column label="工单名称" align="center" key="patrolName" prop="patrolName"/>
            <el-table-column label="巡查类型" align="center" key="tourName" prop="tourName"/>
            <el-table-column label="分配方式" align="center" key="allotWay" prop="allotWay"/>
            <el-table-column label="主要负责人" align="center" key="staffName" prop="staffName"/>
            <el-table-column label="巡查点" align="center" key="pointName" prop="pointName"/>
            <el-table-column label="生成时间" align="center" key="createTime" prop="createTime"/>
          </el-table>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script>
import * as plan from "@/api/estate/work/patrol/patrolPlan";
import * as patrolType from "@/api/estate/work/patrol/patrolType";
import {getCurrentList} from "@/api/estate/community";

export default {
  dicts: ['sys_normal_disable'],
  data() {
    // 校验间隔时间
    const validateGapTime = (rule, value, callback) => {
      if (this.form.planModel === '0' && value === undefined) {
        callback(new Error(rule.message))
      } else {
        callback()
      }
    }
    return {
      // 树筛选文本
      filterText: '',
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
      list: [],
      // 巡查类型list
      patrolTypeList: [],
      // 巡查点list
      pointList: [],
      planModelList: [
        // {value: "一日多查", id: '0'},
        {value: "多日一查", id: '1'},
      ],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示详情
      detailShow: false,
      communityTreeData: [],
      defaultProps: {
        children: "communityId",
        label: "communityName"
      },
      // 工单生成历史列表
      listOrderHistories: [],
      // 详情数据
      detailData: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        patrolTypeId: undefined,
        patrolType: '',
        planName: undefined,
        planOrder: undefined,
        communityId: undefined
      },
      // 表单参数
      form: {
        tourStatus: '0',
        planModel: '1',
        // 计划时间范围
        timeRange: []
      },
      // 表单校验
      rules: {
        planName: [
          {required: true, message: "计划名称不能为空", trigger: "blur"},
          {max: 32, message: '长度不超过32个字符', trigger: 'blur' }
        ],
        tourId: [
          {required: true, message: "请选择巡查类型", trigger: "blur"}
        ],
        pointId: [
          {required: true, message: "请选择巡查点", trigger: "blur"}
        ],
        planNum: [
          {required: true, message: "巡查次数/频率不能为空", trigger: "blur"}
        ],
        timeRange: [
          {required: true, message: "计划时间不能为空", trigger: "blur"}
        ],
        gapTime: [
          {validator: validateGapTime, message: "巡查间隔频率不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    // 初始化
    this.init();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    /** 树结构 */
    init() {
      // 查询左侧树data
      getCurrentList().then(response => {
        this.communityTreeData = response.data;
        this.queryParams.communityId = this.communityTreeData[0].communityId;

        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.communityTreeData[0].communityId);
        });
        // 查询列表数据
        this.getListPage();
      });
      this.selectPatrolTypeList();
    },
    // 查询列表
    getListPage() {
      this.loading = true;
      plan.getListPage(this.queryParams).then(response => {
          this.list = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 查询巡查类型列表
    selectPatrolTypeList() {
      patrolType.getList({tourStatus: '0'}).then(response => {
        this.patrolTypeList = response.data;
      });
    },
    // 查询巡查点列表
    selectPointList() {
      plan.selectPointList(this.queryParams.communityId).then(response => {
        this.pointList = response.data;
      });
    },
    changeSwitch(row, value) {
      let hint = '';
      if (value === '0'){
        hint = "计划已开启，工单将在第二日0点生成！";
      }else {
        hint = "计划已关闭！";
      }
      plan.updatePatrolPlanOrder({planId: row.planId, planOrder: value}).then(response => {
        this.$modal.msgSuccess(hint);
      });
      // 取消表格的勾选
      this.$refs.planRef.toggleRowSelection(row, false);
    },
    // 筛选树节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.communityId = data.communityId;
      this.getListPage();
      this.selectPointList();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        tourStatus: "0",
        planModel: "1"
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getListPage();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.form.communityId = this.queryParams.communityId
      this.title = "添加巡查计划";
      if (this.pointList.length === 0) {
        this.selectPointList();
      }
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.planId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 查看按钮操作 */
    handleView(row) {
      plan.getDetail(row.planId).then(response => {
        this.detailData = response.data;
      });
      this.detailShow = true;
    },
    cancelDetail() {
      this.detailShow = false
      this.detailData = {};
    },
    getStatus(){
      this.getDicts("sys_oldman_auditstatus");
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.title = "修改巡查计划";
      this.open = true;
      if (this.pointList.length === 0) {
        this.selectPointList();
      }

      plan.getInfo(row.planId).then(response => {
        this.form = response.data;
        this.form.timeRange = [];
        this.form.timeRange.push(this.form.startTime);
        this.form.timeRange.push(this.form.endTime);
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.startTime = this.form.timeRange[0];
          this.form.endTime = this.form.timeRange[1];
          if (this.form.planId !== undefined) {
            plan.edit(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getListPage();
            });
          } else {
            plan.add(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getListPage();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const dictIds = row.planId || this.ids;
      this.$modal.confirm('是否确认删除所选巡查计划？').then(function () {
        return plan.remove(dictIds);
      }).then(() => {
        this.getListPage();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    // 巡查模式格式化
    planModelFormat(planModel) {
      for (const planModelStr of this.planModelList) {
        if (planModelStr.id === planModel) {
          return planModelStr.value;
        }
      }
    },
    //判断是否可选
    checkSelect(row, index) {
      let isChecked = true;
      if (row.planOrder === '1') { // 判断里面是否存在某个参数
        isChecked = true
      } else {
        isChecked = false
      }
      return isChecked
    },
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.statusGreen {
  color: #008000;
}

.statusRed {
  color: #ff0000;
}
</style>

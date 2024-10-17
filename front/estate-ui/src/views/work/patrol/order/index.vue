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
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                   label-width="68px">
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
            <el-form-item label="分配方式" prop="allotWay">
              <el-select
                v-model="queryParams.allotWay"
                placeholder="请选择分配方式"
                clearable
                style="width: 100%"
              >
                <el-option
                  v-for="dict in dict.type.allot_way"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="工单来源" prop="dataSource">
              <el-select
                v-model="queryParams.dataSource"
                placeholder="请选择工单来源"
                clearable
                style="width: 100%"
              >
                <el-option
                  v-for="dict in dict.type.order_source"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="负责人" prop="staffName">
              <el-input v-model="queryParams.staffName" placeholder="请输入负责人名称"/>
            </el-form-item>
            <el-form-item label="执行状态" prop="executeStatus">
              <el-select
                v-model="queryParams.executeStatus"
                placeholder="请选择执行状态"
                clearable
                style="width: 100%"
              >
                <el-option
                  v-for="dict in dict.type.execute_status"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="巡查点" prop="pointId">
              <el-select
                v-model="queryParams.pointId"
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
                v-hasPermi="['patrol:order:add']"
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
                v-hasPermi="['patrol:order:delete']"
              >删除
              </el-button>
            </el-col>
          </el-row>

          <el-table v-loading="loading" :data="list" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column label="工单编号" align="center" prop="patrolNo" :show-overflow-tooltip="true"/>
            <el-table-column label="工单名称" align="center" prop="patrolName" :show-overflow-tooltip="true"/>
            <el-table-column label="巡查类型" align="center" prop="tourName" :show-overflow-tooltip="true"/>
            <el-table-column label="工单来源" align="center">
              <template v-slot="scope">
                {{ selectDictLabel(dict.type.order_source, scope.row.dataSource) }}
              </template>
            </el-table-column>
            <el-table-column label="分配方式" align="center">
              <template v-slot="scope">
                {{ selectDictLabel(dict.type.allot_way, scope.row.allotWay) }}
              </template>
            </el-table-column>
            <el-table-column label="主要负责人" align="center" prop="staffName"/>
            <el-table-column label="巡查点" align="center" prop="pointName" :show-overflow-tooltip="true"/>
            <el-table-column label="分配状态" align="center">
              <template v-slot="scope">
                {{ selectDictLabel(dict.type.allot_status,scope.row.allotStatus) }}
              </template>
            </el-table-column>
            <el-table-column label="接单状态" align="center">
              <template v-slot="scope">
                {{ selectDictLabel(dict.type.accept_status, scope.row.acceptStatus) }}
              </template>
            </el-table-column>
            <el-table-column label="执行状态" align="center">
              <template v-slot="scope">
                {{ selectDictLabel(dict.type.execute_status, scope.row.executeStatus) }}
              </template>
            </el-table-column>
            <el-table-column label="创建时间" align="center" prop="createTime" width="155px"/>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template v-slot="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="handleView(scope.row)"
                >查看
                </el-button>

                <el-popover style="padding-left: 20px"
                            placement="right"
                            trigger="click">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="handleUpdate(scope.row)"
                    v-hasPermi="['patrol:order:edit']"
                  >修改
                  </el-button>
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="handleAllot(scope.row)"
                    v-if="scope.row.staffName ==='' || scope.row.staffName === undefined || scope.row.staffName === null"
                    v-hasPermi="['patrol:order:allot']"
                  >分配
                  </el-button>
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    style="color: #f56c6c"
                    @click="handleDelete(scope.row)"
                    v-hasPermi="['patrol:order:delete']"
                  >删除
                  </el-button>
                  <el-button size="mini" type="text" slot="reference" icon="el-icon-more"></el-button>
                </el-popover>
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
          <el-form-item label="工单名称" prop="patrolName">
            <el-input v-model="form.patrolName" placeholder="请输入工单名称"/>
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

          <!-- 一日多查 -->
<!--          <el-form-item label="巡查次数" prop="patrolNum">
            <el-input-number v-model="form.patrolNum" controls-position="right" :min="1" :max="10"
                             style="width: 100%"></el-input-number>
          </el-form-item>-->
          <el-form-item label="工单时间" prop="timeRange">
            <el-date-picker
              v-model="form.timeRange"
              type="datetimerange"
              format='yyyy-MM-dd HH:mm'
              range-separator="至"
              placeholder="选择时间范围"
              start-placeholder="开始日期"
              value-format="yyyy-MM-dd HH:mm"
              end-placeholder="结束日期"
              :picker-options="expireTimeOPtion"
              style="width: 100%">
            </el-date-picker>
          </el-form-item>
          <el-form-item label="巡查间隔时间" prop="gapTime">
            <el-input-number v-model="form.gapTime" controls-position="right" :min="1" :max="10"
                             :step="0.5" style="width: 100%"></el-input-number>
          </el-form-item>
          <el-form-item label="备注" prop="remark">
            <el-input
              type="textarea"
              :rows="2"
              placeholder="请输入内容"
              v-model="form.remark">
            </el-input>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>

      <!-- 添加或修改参数配置对话框 -->
      <el-dialog :title="title" :visible.sync="allotOpen" width="660px" append-to-body>
        <el-form ref="allotForm" :model="allotForm" :rules="rules" label-width="120px">
          <el-form-item label="负责人" prop="staffId">
            <el-select
              v-model="allotForm.staffId"
              placeholder="请选择负责人"
              clearable
              style="width: 100%"
            >
              <el-option
                v-for="staff in shareNameList"
                :key="staff.staffId"
                :label="staff.staffName"
                :value="staff.staffId"
              />
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitAllotForm">确 定</el-button>
          <el-button @click="cancel">关 闭</el-button>
        </div>
      </el-dialog>

    </div>

    <!--  详情页  -->
    <div v-if="detailShow" v-cloak>
      <el-col :span="4" :offset="23" style="margin-top: 5px;">
        <el-button @click="cancelDetail">关 闭</el-button>
      </el-col>
      <el-form ref="detailForm" :model="detailData" label-width="140px">
        <h4 class="form-header">
          工单详情
        </h4>
        <el-row>
          <el-col :span="20">
            <el-row>
              <el-col :span="6" :offset="0">
                <el-form-item label="工单编号:">{{ detailData.patrolNo }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="0">
                <el-form-item label="工单名称:">{{ detailData.patrolName }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="0">
                <el-form-item label="工单类型:">巡查工单</el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="6" :offset="0">
                <el-form-item label="巡查类型:">{{ detailData.tourName }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="0">
                <el-form-item label="工单来源:">{{ detailData.dataSource }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="0">
                <el-form-item label="来源工单:">{{ detailData.otherOrderName }}</el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="6" :offset="0">
                <el-form-item label="巡查点:">{{ detailData.pointName }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="0">
                <el-form-item label="巡查开始时间:">{{ detailData.startTime }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="0">
                <el-form-item label="巡查结束时间:">{{ detailData.endTime }}</el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="6" :offset="0">
                <el-form-item label="结束时间:">{{ detailData.finishTime }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="0">
                <el-form-item label="异常状态:">{{ detailData.errorStatus === 1 ? "异常" : "正常" }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="0">
                <el-form-item label="主要负责人:">{{ detailData.staffName }}</el-form-item>
              </el-col>
            </el-row>

            <div v-if="detailData.executeStatus === 2 ||detailData.executeStatus === 3">
              <h4 class="form-header">工单完成情况</h4>
              <el-row>
                <el-col :span="6" :offset="0">
                  <el-form-item label="完成备注:">{{ detailData.remark }}</el-form-item>
                </el-col>
                <el-col :span="6" :offset="0">
                  <el-form-item label="完成时间:">{{ detailData.finishTime }}</el-form-item>
                </el-col>
                <el-col :span="6" :offset="0">
                  <el-form-item label="完成时图片:">
                    <el-image
                      style="width: 100px; height: 100px"
                      :src="detailData.imageUrl"
                      fit="contain"></el-image>
                  </el-form-item>
                </el-col>
              </el-row>
            </div>

            <h4 class="form-header">工单协助人信息</h4>
            <el-row>
              <el-table :data="detailData.staffList">
                <el-table-column label="人员姓名" align="center" key="staffName" prop="staffName"/>
                <el-table-column label="手机号" align="center" key="staffPhone" prop="staffPhone"/>
                <el-table-column label="身份证号" align="center" key="cardNo" prop="cardNo"/>
                <el-table-column label="人员类别" align="center" key="workPost" prop="workPost"/>
              </el-table>
            </el-row>

            <orderRecord :father="queryRecord"></orderRecord>

          </el-col>
        </el-row>
      </el-form>
    </div>
  </div>
</template>

<script>
import * as plan from "@/api/estate/work/patrol/patrolPlan";
import * as order from "@/api/estate/work/patrol/patrolOrder";
import * as patrolType from "@/api/estate/work/patrol/patrolType";
import {listLeisureBusyStaffs} from "@/api/estate/manage/staff/staff";
import {getCurrentList} from "@/api/estate/community";
import orderRecord from "@/components/work/orderRecord"

export default {
  components: {
    orderRecord
  },
  dicts: ['allot_way', 'order_source', 'execute_status', 'allot_status', 'accept_status'],
  data() {
    return {
      expireTimeOPtion: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;  //如果没有后面的-8.64e7就是不可以选择今天的
        }
      },
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
      // 主要负责人列表
      shareNameList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      allotOpen: false,
      // 是否显示详情
      detailShow: false,
      communityTreeData: [],
      defaultProps: {
        children: "communityId",
        label: "communityName"
      },
      queryRecord: {
        orderType: '0',
        orderId: 0
      },
      // 工单生成历史列表
      listOrderHistories: [],
      // 详情数据
      detailData: {},
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        pointId: undefined,
        staffName: undefined,
        executeStatus: undefined,
        dataSource: undefined,
        allotWay: undefined,
        patrolTypeId: undefined,
        patrolType: ''
      },
      // 表单参数
      form: {
        tourStatus: '0',
        planModel: 0,
        // 工单时间范围
        timeRange: []
      },
      // 分配表单参数
      allotForm: {},
      // 表单校验
      rules: {
        patrolName: [
          {required: true, message: "工单名称不能为空", trigger: "blur"},
          {max: 32, message: '长度不超过32个字符', trigger: 'blur' }
        ],
        tourId: [
          {required: true, message: "请选择巡查类型", trigger: "blur"}
        ],
        staffId: [
          {required: true, message: "请选择负责人", trigger: "blur"}
        ],
        pointId: [
          {required: true, message: "请选择巡查点", trigger: "blur"}
        ],
        patrolNum: [
          {required: true, message: "巡查次数/频率不能为空", trigger: "blur"}
        ],
        timeRange: [
          {required: true, message: "工单时间不能为空", trigger: "blur"}
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
        // 查询巡查点列表
        this.selectPointList();
      });
      // 查询巡查类型列表
      this.selectPatrolTypeList();
    },
    // 查询列表
    getListPage() {
      this.loading = true;
      order.getListPage(this.queryParams).then(response => {
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
    // 筛选树节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.communityId = data.communityId;
      this.selectPointList();
      this.getListPage();
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.allotOpen = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        tourStatus: "0",
        planModel: "0"
      };
      this.resetForm("form");
      this.allotForm = {};
      this.resetForm("allotForm");
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
      this.title = "添加巡查工单";
      if (this.pointList.length === 0) {
        this.selectPointList();
      }
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.patrolId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 查看按钮操作 */
    handleView(row) {
      this.queryRecord.orderId = row.patrolId;
      order.getDetail(row.patrolId).then(response => {
        this.detailData = response.data;
      });
      this.detailShow = true;

    },
    cancelDetail() {
      this.detailShow = false
      this.detailData = {};
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.title = "修改巡查工单";
      this.open = true;
      if (this.pointList.length === 0) {
        this.selectPointList();
      }

      order.getInfo(row.patrolId).then(response => {
        this.form = response.data;
        this.form.timeRange = [];
        this.form.timeRange.push(this.form.startTime);
        this.form.timeRange.push(this.form.endTime);
      });
    },
    handleAllot(row) {
      this.reset();
      this.title = "分配负责人";
      this.allotOpen = true;
      this.allotForm.patrolId = row.patrolId;
      listLeisureBusyStaffs({allotType: '0',communityId: this.queryParams.communityId}).then(res => {
        this.shareNameList = res.data;
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.form.startTime = this.form.timeRange[0];
          this.form.endTime = this.form.timeRange[1];
          if (this.form.patrolId !== undefined) {
            order.edit(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getListPage();
            });
          } else {
            order.add(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getListPage();
            });
          }
        }
      });
    },
    // 提交分配
    submitAllotForm: function () {
      this.$refs["allotForm"].validate(valid => {
        if (valid) {
          order.allot(this.allotForm).then(response => {
            this.$modal.msgSuccess("分配成功");
            this.allotOpen = false;
            this.getListPage();
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const dictIds = row.patrolId || this.ids;
      this.$modal.confirm('是否确认删除所选巡查工单？').then(function () {
        return order.remove(dictIds);
      }).then(() => {
        this.getListPage();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
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

<template>
  <div class="app-container">
    <div v-show="homeShow">
      <el-row :gutter="20">
        <!-- 树结构     -->
        <el-col :span="3" :xs="24">
          <div class="head-container">
            <el-input v-model="search" placeholder="请输入小区名称" clearable size="small"
                      prefix-icon="el-icon-search" style="margin-bottom: 20px"/>
          </div>
          <div >
            <el-tree
              :data="deptData"
              :props="defaultProps"
              :expand-on-click-node="false"
              :filter-node-method="filterNode"
              ref="tree"
              highlight-current
              node-key="communityId"
              @node-click="listClick"
            >
              <!-- 插入楼栋图标 -->
              <span slot-scope="{ node }">
	   		          <i class="el-icon-office-building" />
	   		          <span class="content"  style="padding-left:5px;">{{node.label}}</span>
		          </span>
            </el-tree>
          </div>
        </el-col>
        <el-col :span="20" :xs="24">
          <!-- 条件搜索 -->
          <el-form
            :model="queryParams"
            ref="queryForm"
            size="small"
            :inline="true"
          >
            <el-form-item label="工单编号" prop="orderNo">
              <el-input
                v-model="queryParams.orderNo"
                placeholder="请输入工单编号"
                @keyup.enter.native="handleQuery"
                style="width: 170px"
              />
            </el-form-item>
            <el-form-item label="工单名称" prop="orderName">
              <el-input
                v-model="queryParams.orderName"
                placeholder="请输入工单名称"
                @keyup.enter.native="handleQuery"
                style="width: 170px"
              />
            </el-form-item>
            <el-form-item label="申请人" prop="applyName">
              <el-input
                v-model="queryParams.applyName"
                placeholder="请输入申请人姓名"
                @keyup.enter.native="handleQuery"
                style="width: 170px"
              />
            </el-form-item>
            <el-form-item label="申请类别" prop="auditType">
              <el-select
                clearable
                v-model="queryParams.auditType"
                placeholder="请选择申请类别"
                style="width: 170px"
              >
                <el-option
                  v-for="auditType in auditTypeList"
                  :key="auditType.value"
                  :label="auditType.label"
                  :value="auditType.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="状态" prop="auditStatus">
              <el-select
                clearable
                v-model="queryParams.auditStatus"
                placeholder="请选择状态"
                style="width: 120px"
              >
                <el-option
                  v-for="dict in dict.type.sys_oldman_auditstatus"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>

            <el-form-item>
              <el-button
                type="primary"
                icon="el-icon-search"
                size="mini"
                @click="handleQuery"
              >搜索
              </el-button
              >
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
              >重置
              </el-button
              >
            </el-form-item>
          </el-form>

          <!-- 列表展示 -->
          <el-table
            stripe
            v-loading="loading"
            :data="list"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55" align="center"/>
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
              :show-overflow-tooltip="true"
            >
            </el-table-column>
            <el-table-column
              label="申请人"
              align="center"
              prop="applyName"
              :show-overflow-tooltip="true"
            >
              <template slot-scope="scope">
                <span>{{ handleName(scope.row.applyName) }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="申请类别"
              align="center"
              prop="auditType"
              :show-overflow-tooltip="true"
              :formatter="auditTypeChange"
            />
            <el-table-column
              label="状态"
              align="center"
              prop="auditStatus"
              :show-overflow-tooltip="true"
              :formatter="auditStatusChange"
            />
            <el-table-column
              label="操作"
              align="center"
              fixed="right"
              width="190"
              class-name="small-padding fixed-width"
            >
              <template slot-scope="scope">
                <el-button size="mini" type="text" @click="detail(scope.row)"
                >
                  <el-link icon="el-icon-view" type="brand" :underline="false"
                  >详情
                  </el-link>
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  @click="adopt(scope.row)"
                  v-if="scope.row.auditStatus == '0'"
                  v-hasPermi='[adoptPermission]'
                >
                  <el-link
                    icon="el-icon-check"
                    type="success"
                    :underline="false"
                  >通过
                  </el-link
                  >
                </el-button
                >
                <el-button
                  size="mini"
                  type="text"
                  @click="reject(scope.row)"
                  v-if="scope.row.auditStatus == '0'"
                  v-hasPermi='[rejectPermission]'
                >
                  <el-link
                    icon="el-icon-close"
                    type="danger"
                    :underline="false"
                  >驳回
                  </el-link
                  >
                </el-button
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

    <!-- 详情页面 -->
    <div v-show="showDetail">
      <el-form ref="form" :model="detailForm" label-width="180px">
        <el-col :offset="23" :span="24">
          <el-button @click="cancel">关 闭</el-button>
        </el-col>
        <!--  ----------------------------- 工单详情 --------------------------- -->
        <h5><b>工单详情</b></h5>
        <el-divider></el-divider>
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="工单编号: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.orderNo }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="工单名称: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.orderName }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-if="detailForm.auditType==='0'">
          <el-col :offset="1" :span="5">
            <el-form-item label="接收人: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.receiveName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="接收人手机号: ">
              <div style="width: 280px; overflow: hidden">
                {{ handlePhone(detailForm.receivePhone) }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <div v-if="detailForm.auditType==='1'">
          <h4 class="form-header">工单协助人信息</h4>
          <el-row>
            <el-table :data="detailForm.staffList">
              <el-table-column label="人员姓名" align="center" key="staffName" prop="staffName">
                <template slot-scope="scope">
                  <span>{{ handleName(scope.row.staffName) }}</span>
                </template>
              </el-table-column>
              <el-table-column label="手机号" align="center" key="staffPhone" prop="staffPhone">
                <template slot-scope="scope">
                  <span>{{ handlePhone(scope.row.staffPhone) }}</span>
                </template>
              </el-table-column>
              <el-table-column label="身份证号" align="center" key="cardNo" prop="cardNo">
                <template slot-scope="scope">
                  <span>{{ handleCard(scope.row.cardNo) }}</span>
                </template>
              </el-table-column>
              <el-table-column label="人员类别" align="center" key="workPost" prop="workPost"/>
            </el-table>
          </el-row>
        </div>
        <!--  ----------------------------- 审批详情 --------------------------- -->
        <h5><b>审批详情</b></h5>
        <el-divider></el-divider>
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="申请类别: ">
              <div style="width: 280px; overflow: hidden">
                {{ auditTypeFormat(detailForm.auditType) }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="申请人: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.applyName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="审批状态: ">
              <div style="width: 280px; overflow: hidden">
                {{ auditStatusFormat(detailForm.auditStatus) }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :offset="1" :span="5">
            <el-form-item label="审批人: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.userName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="审批时间: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.auditTime }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="5">
            <el-form-item label="驳回原因: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.rejectReason }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <!-- 驳回弹窗 -->
    <el-dialog :visible.sync="auditOpen" width="630px" @close="rejectClose">
      <el-form ref="auditForm" :model="auditForm" :rules="rules" label-width="130px">
        <el-row>
          <el-col>
            <el-form-item label="驳回理由: " prop="rejectReason">
              <el-input
                type="textarea"
                v-model="auditForm.rejectReason"
                maxlength="500"
                show-word-limit
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :offset="10" :span="2">
            <div style="width: 280px">
              <el-button type="primary" @click="shareSuccess">确定</el-button>
              <el-button @click="rejectClose">关闭</el-button>
            </div>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {adopt, listPage, reject, detail} from "@/api/estate/work/inspect/examine";
import {desensitizationName, desensitization, equals} from "@/utils/common";

export default {
  name: "workOrderAudit",
  dicts: ['sys_oldman_auditstatus'],
  props: {
    orderType: {
      type: String,
      required: true,
      default: ""
    },
    adoptPermission: {
      type: String,
      required: true
    },
    rejectPermission: {
      type: String,
      required: true
    }
  },
  data() {
    return {
      defaultProps: {
        children: "communityId",
        label: "communityName"
      },
      search: '',
      // 审核表单
      auditForm: {
        rejectReason: ''
      },
      // 详情表单
      detailForm: {},
      // 小区ID
      communityId: undefined,
      // 小区ID 新增修改传参
      communityIds: null,
      // 机构列表
      deptData: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        orderType: this.orderType,
        auditStatus: undefined,
        auditType: undefined,
        applyName: undefined,
        orderName: undefined,
        orderNo: undefined
      },
      // 遮罩层
      loading: true,
      // 表格数据
      list: [],
      // 审核状态列表
      auditStatusList: [],
      // 申请类别列表
      auditTypeList: [{label: '工单转交', value: '0'}, {label: '工单协助', value: '1'}],
      // 选中数组
      ids: [],
      // 详情页面默认关闭
      showDetail: false,
      homeShow: true,
      // 驳回弹窗
      auditOpen: false,
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
      // 表单验证
      rules: {
        rejectReason: [
          {required: true, message: "请输入驳回理由", trigger: "blur"},
          {
            pattern: /^[\u4e00-\u9fa5_a-zA-Z0-9_(_（_)_）_,_，_:_：_—_\-_/_\n]+$/,
            message: "请正确输入文本框",
            trigger: "blur"
          }
        ],
      }
    }
      ;
  },

  created() {
    // 列表查询
    this.getList();
    // 机构下拉框
    this.getTypeList();
    // 获取分配方式列表
    this.getAuditStatusList();
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
  methods: {
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      // this.queryParams.dropDownForm = null;
      this.searchTime = "";
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 查询列表 */
    getList() {
      this.loading = false;
      listPage(this.queryParams).then((response) => {
        this.list = response.rows;
        this.total = response.total;
      });
    },
//姓名脱敏
    handleName(name) {
      return desensitizationName(name)
    },

    //脱敏身份证号
    handleCard(card) {
      return desensitization(card, 4, 14)
    },
    //脱敏身份证号
    handlePhone(card) {
      return card == null ? card : desensitization(card, 3, 7);
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.carportId);
      this.multiple = !selection.length;
    },

    // 表单重置
    reset() {
      this.form = {};
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.showDetail = false;
      this.homeShow = true;
      this.getList();
      //this.reset();
    },

    selectView(row) {
      this.queryParams.auditId = row.auditId
      detail(this.queryParams).then((response) => {
        this.detailForm = response.data;
      });

    },

    // 获取分配方式列表
    getAuditStatusList() {
      this.getDicts("sys_oldman_auditstatus").then(response => {
        this.auditStatusList = response.data;
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
        // this.$refs.singleTable.setCurrentRow(this.deptData[0]);
        this.auditForm.communityId = this.deptData[0].communityId;
        this.auditForm.communityName = this.deptData[0].communityName;
        this.communityIds = this.deptData[0].communityId;
        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.deptData[0].communityId);
        });
        this.getList();
      });
    },

    // 单元格点击时查列表
    listClick(row) {
      this.queryParams.communityId = row.communityId;
      // // 点击单元格时赋值所属小区
      this.auditForm.communityId = row.communityId;
      this.auditForm.communityName = row.communityName;
      this.communityIds = row.communityId;
      this.getList();
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
    // 审核状态转换
    auditStatusChange(row) {
      for (const auditStatus of this.auditStatusList) {
        if (auditStatus.dictValue === row.auditStatus) {
          return auditStatus.dictLabel;
        }
      }
    },
    // 分配方式格式化
    auditStatusFormat(auditStatusId) {
      for (const auditStatus of this.auditStatusList) {
        if (auditStatus.dictValue === auditStatusId) {
          return auditStatus.dictLabel;
        }
      }
    },
    // 分配方式格式化
    auditTypeFormat(auditTypeId) {
      for (const auditType of this.auditTypeList) {
        if (auditType.value === auditTypeId) {
          return auditType.label;
        }
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

    // 驳回弹窗
    reject(row) {
      this.auditOpen = true;
      this.auditForm.rejectReason = undefined;
      this.auditForm.auditId = row.auditId;
    },

    // 驳回弹窗确定按钮
    shareSuccess() {
      this.$refs["auditForm"].validate(valid => {
        if (valid) {
          reject(this.auditForm).then(() => {
            this.getList();
            this.$modal.msgSuccess("审核成功！");
            this.auditOpen = false;
          }).catch(() => {
          });
        }
      });
    },

    // 驳回弹窗取消按钮
    rejectClose() {
      this.auditOpen = false;
      this.auditForm = {rejectReason: ''};
    },

    // 通过按钮
    adopt(row) {

      this.$confirm('是否审核通过?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.auditForm.auditId = row.auditId;
        adopt(this.auditForm).then(() => {
          this.getList();
          this.$message({
            type: 'success',
            message: '审核成功!'
          });
        }).catch(() => {
          this.$message({
            type: 'error',
            message: '审核失败!'
          });
        });
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消审核'
        });
      });
    },
  },
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
.thumbnail {
  height:100px;
  width: 200px
}

</style>

<template>
  <div class="app-container">
    <div v-show="openView">
      <el-row :gutter="20">
        <!--小区数据-->
        <el-col :span="4">
          <div class="head-container">
            <el-input
              placeholder="请输入小区名称"
              clearable
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
              ref="tree"
              highlight-current
              node-key="communityId"
            >
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
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="联系方式" prop="ownerPhone">
              <el-input
                v-model="queryParams.ownerPhone"
                placeholder="请输入联系方式"
                clearable
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="所属楼栋" prop="buildingName">
              <el-input
                v-model="queryParams.buildingName"
                placeholder="请输入所属楼栋"
                clearable
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="门牌号" prop="roomName">
              <el-input
                v-model="queryParams.roomName"
                placeholder="请输入门牌号"
                clearable
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>

          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button
                type="success"
                plain
                icon="el-icon-edit"
                size="mini"
                :disabled="multiple"
                @click="handleAdopt"
                v-hasPermi="['customer:workerNew:adopt']"
              >通过</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="danger"
                plain
                icon="el-icon-delete"
                style="color: #f56c6c"
                size="mini"
                :disabled="multiple"
                @click="handleReject"
                v-hasPermi="['customer:workerNew:reject']"
              >驳回</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
          </el-row>

          <el-table v-loading="loading" :data="workerNewList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" :selectable="selectable"/>
            <el-table-column label="业主姓名" align="center" key="ownerName" prop="ownerName" :show-overflow-tooltip="true"/>
            <el-table-column label="联系方式" align="center" key="ownerPhone" prop="ownerPhone" :show-overflow-tooltip="true">
              <template scope="scope">
                <span>{{ phoneFormatter(scope.row.ownerPhone) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="所属楼栋" align="center" key="buildingName" prop="buildingName" :show-overflow-tooltip="true"/>
            <el-table-column label="单元号" align="center" key="unitName" prop="unitName" :show-overflow-tooltip="true"/>
            <el-table-column label="门牌号" align="center" key="roomName" prop="roomName" :show-overflow-tooltip="true"/>
            <el-table-column label="装修工姓名" align="center" key="workerName" prop="workerName" :show-overflow-tooltip="true"/>
            <el-table-column label="联系方式" align="center" key="workerPhone" prop="workerPhone" :show-overflow-tooltip="true">
              <template scope="scope">
                <span>{{ phoneFormatter(scope.row.workerPhone) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="审批状态" align="center" key="auditStatus" prop="auditStatus" :show-overflow-tooltip="true">
              <template slot-scope="scope">
                <span v-if="scope.row.auditStatus === '0'">审核中</span>
                <span v-else-if="scope.row.auditStatus === '1'">驳回</span>
                <span v-else-if="scope.row.auditStatus === '2'">通过</span>
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
                  v-hasPermi="['customer:workerNew:detail']"
                >查看</el-button>
                <el-button v-if="scope.row.auditStatus === '0'"
                  size="mini"
                  type="text"
                  class="text-info"
                  icon="el-icon-edit"
                  @click="handleAdopt(scope.row)"
                  v-hasPermi="['customer:workerNew:adopt']"
                >通过</el-button>
                <el-button v-if="scope.row.auditStatus === '0'"
                  size="mini"
                  type="text"
                  class="text-danger"
                  icon="el-icon-delete"
                  style="color: #f56c6c"
                  @click="handleRejectOne(scope.row)"
                  v-hasPermi="['customer:workerNew:reject']"
                >驳回</el-button>
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
    <div v-show="detailShow">
      <el-form ref="detailForm" :model="detailForm" label-width="140px">
        <h4 class="form-header">装修详情</h4>

        <el-row>
          <el-col :span="4" :offset="2">
            <el-form-item label="业主姓名:">{{ detailForm.ownerName }}</el-form-item>
          </el-col>
          <el-col :span="4" :offset="2">
            <el-form-item label="联系方式:">{{ detailForm.ownerPhone }}</el-form-item>
          </el-col>
          <el-col :span="4" :offset="2">
            <el-form-item label="所属楼栋:">{{ detailForm.buildingName }}</el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="4" :offset="2">
            <el-form-item label="单元号:">{{ detailForm.unitName }}</el-form-item>
          </el-col>
          <el-col :span="4" :offset="2">
            <el-form-item label="门牌号:">{{ detailForm.roomName }}</el-form-item>
          </el-col>
<!--
          <el-col :span="4" :offset="2">
            <el-form-item label="装修押金:">{{ detailForm.deposit }}</el-form-item>
          </el-col>
-->
        </el-row>

        <el-row>
          <el-col :span="4" :offset="2">
            <el-form-item label="计划装修开始时间:">{{ detailForm.planStart }}</el-form-item>
          </el-col>
          <el-col :span="4" :offset="2">
            <el-form-item label="计划装修结束时间:">{{ detailForm.planEnd }}</el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12" :offset="2">
            <el-form-item label="备注:">{{ detailForm.remark }}</el-form-item>
          </el-col>
        </el-row>

        <h4 class="form-header">审批详情</h4>

        <el-row>
          <el-col :span="4" :offset="2">
            <el-form-item label="申请类型:">{{ detailForm.decorateType }}</el-form-item>
          </el-col>
          <el-col :span="4" :offset="2">
            <el-form-item label="审批状态:">{{ detailForm.auditStatus }}</el-form-item>
          </el-col>
          <el-col :span="4" :offset="2">
            <el-form-item label="审批人:">{{ detailForm.auditBy }}</el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="4" :offset="2">
            <el-form-item label="审批时间:">{{ detailForm.auditTime }}</el-form-item>
          </el-col>
          <el-col :span="4" :offset="2" v-if="detailForm.auditStatus == '2'">
            <el-form-item label="驳回原因:">{{ detailForm.rejectReason }}</el-form-item>
          </el-col>
        </el-row>

        <h4 class="form-header">装修人员信息</h4>

        <el-row>
          <el-col :span="4" :offset="2">
            <el-form-item label="姓名:">{{ detailForm.workerName }}</el-form-item>
          </el-col>
          <el-col :span="4" :offset="2">
            <el-form-item label="联系方式:">{{ detailForm.workerPhone }}</el-form-item>
          </el-col>
          <el-col :span="4" :offset="2">
            <el-form-item label="身份证号:">{{ detailForm.cardNo }}</el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="4" :offset="2">
            <el-form-item label="审批人:">{{ detailForm.workerAuditBy }}</el-form-item>
          </el-col>
          <el-col :span="4" :offset="2">
            <el-form-item label="审批时间:">{{ detailForm.workerAuditTime }}</el-form-item>
          </el-col>
          <el-col :span="4" :offset="2" v-if="detailForm.workerRejectReason != '' && detailForm.workerRejectReason != undefined">
            <el-form-item label="驳回原因:">{{ detailForm.workerRejectReason }}</el-form-item>
          </el-col>
        </el-row>

        <div style="float: right">
          <el-button @click="canceldetail">关 闭</el-button>
        </div>
      </el-form>
    </div>

    <!-- 批量驳回 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" label-width="100px">
        <el-form-item label="驳回理由">
          <el-input v-model="form.rejectReason" type="textarea" placeholder="请输入驳回理由" maxlength="500"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>

    <!-- 单个驳回 -->
    <el-dialog :title="title" :visible.sync="openOne" width="500px" append-to-body>
      <el-form ref="form" :model="form" label-width="100px">
        <el-form-item label="驳回理由">
          <el-input v-model="form.rejectReason" type="textarea" placeholder="请输入驳回理由" maxlength="500"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormOne">确 定</el-button>
        <el-button @click="cancelOne">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listWorkerNew, workerNewAdopt, getWorkerNewDetail, workerNewReject, workerNewRejectOne } from "@/api/estate/customer/decorate/workerNew";
import { getCurrentList } from "@/api/estate/community";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { equals, desensitization } from "@/utils/common";

export default {
  name: "WorkerNew",
  components: { Treeselect },
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
      //小区数据集合
      currentList: [],
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      // 总条数
      total: 0,
      // 装修表格数据
      workerNewList: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      openOne: false,
      // 默认密码
      initPassword: undefined,
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ownerName: undefined,
        ownerPhone: undefined,
        buildingName: undefined,
        roomName: undefined,
        communityId: undefined
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
      listWorkerNew(this.queryParams).then(response => {
          this.workerNewList = response.rows;
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
    cancelOne() {
      this.openOne = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        ownerName: undefined,
        ownerPhone: undefined,
        buildingName: undefined,
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
      this.ids = selection.map(item => item.workerId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },

    /** 通过按钮操作 */
    handleAdopt(row) {
      const workerIds = row.workerId || this.ids;
      this.$modal.confirm('是否确认审核通过').then(function() {
        return workerNewAdopt(workerIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("审核通过成功");
      }).catch(() => {});
    },

    /** 驳回按钮操作 */
    handleReject() {
      this.reset();
      this.form.workerIds = this.ids;
      this.open = true;
      this.title = "审核驳回";
    },

    /** 单个驳回按钮操作 */
    handleRejectOne(row) {
      this.reset();
      this.form.workerId = row.workerId;
      this.openOne = true;
      this.title = "审核驳回";
    },

    /** 查看详情按钮操作 */
    handleDetail(row) {
      this.reset();
      const workerId = row.workerId || this.ids
      getWorkerNewDetail(workerId).then(response => {
        this.detailForm = response.data;
      });
      this.openView = false
      this.detailShow = true
    },

    //关闭详情页
    canceldetail() {
      this.detailForm = {}
      this.detailShow = false
      this.openView = true;
    },

    /** 联系方式脱敏 */
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

    /** 批量驳回 */
    submitForm: function () {
      if (this.form.rejectReason === undefined) {
        this.$modal.msgError(`驳回理由不能为空!`);
        return;
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          workerNewReject(this.form).then(response => {
            this.$modal.msgSuccess("驳回成功");
            this.open = false;
            this.getList();
          });
        }
      });
    },

    // 设置多选是否可选择
    selectable(row) {
      return row.auditStatus === '0';
    },

    /** 单个驳回 */
    submitFormOne: function () {
      if (this.form.rejectReason === undefined) {
        this.$modal.msgError(`驳回理由不能为空!`);
        return;
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          workerNewRejectOne(this.form).then(response => {
            this.$modal.msgSuccess("驳回成功");
            this.openOne = false;
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
</style>

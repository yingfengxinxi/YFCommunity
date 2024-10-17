<template>
  <div class="app-container">
    <div v-show="open">
      <el-form
        :model="queryParams"
        ref="queryForm"
        v-show="showSearch"
        :inline="true"
        class="cus-fm"
      >
        <el-form-item label="工单编号" prop="disasterName">
          <el-input
            v-model="queryParams.disasterName"
            placeholder="请输入工单编号"
            clearable
            size="small"
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="工单名称" prop="disasterName">
          <el-input
            v-model="queryParams.disasterName"
            placeholder="请输入工单名称"
            clearable
            size="small"
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="申请人" prop="disasterName">
          <el-input
            v-model="queryParams.disasterName"
            placeholder="请输入申请人"
            clearable
            size="small"
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="申请类别" prop="disasterName">
          <el-input
            v-model="queryParams.disasterName"
            placeholder="请输入申请类别"
            clearable
            size="small"
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态" prop="auditStatus" style="margin-left: 30px">
          <el-select
            v-model="queryParams.auditStatus"
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
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
            >重置</el-button
          >
        </el-form-item>
      </el-form>

      <el-table
        v-loading="loading"
        :data="disasterList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="工单编号" prop="disasterName" />
        <el-table-column
          label="工单名称"
          align="center"
          prop="remark"
        ></el-table-column>
        <el-table-column
          label="申请人"
          align="center"
          prop="remark"
        ></el-table-column>
        <el-table-column
          label="申请类别"
          align="center"
          prop="remark"
        ></el-table-column>
        <el-table-column label="状态" align="center" prop="disasterStatus" :formatter="statusFormatter">
        </el-table-column>
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
              <el-button size="mini" type="text" @click="handleInfo(scope.row)"
              v-if="scope.row.disasterStatus == 1 || scope.row.disasterStatus == 2 || scope.row.disasterStatus == 0"
                ><el-link icon="el-icon-view" type="brand" :underline="false"
                >查看</el-link
                ></el-button
                >
                <el-button
                  size="mini"
                  type="text"
                  @click="handlePass(scope.row)"
                    v-if="scope.row.disasterStatus == 0"
                ><el-link
                  icon="el-icon-edit"
                  type="success"
                  :underline="false"
                >通过</el-link
                ></el-button
                >
                <el-button
                  size="mini"
                  type="text"
                  @click="handleOverrule(scope.row)"
                  v-if="scope.row.disasterStatus == 0"
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
      />
    </div>
    <!-- 详情页面 -->
    <div v-show="showDetail">
      <el-form ref="form" :model="form"  label-width="180px">
         <h5><b>{{titleInfo}}</b></h5>
          <el-col :span="24">
            <el-row>
              <el-col :span="5">
                <el-form-item label="工单编号：">
                  <div style="width: 280px">{{ form.activityName }}</div>
                </el-form-item>
              </el-col>
        
              <el-col :span="5" :offset="1">
                <el-form-item label="工单名称：">
                  <div style="width: 280px">{{ form.typeName }}</div>
                </el-form-item>
              </el-col>
             <el-col :span="5" :offset="1">
                <el-form-item label="工单类型：">
                  <div style="width: 280px">{{ form.typeName }}</div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="3">
                <el-form-item label="报事类型：">
                  <div style="width: 280px">{{ form.createTime }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="5" :offset="3">
                <el-form-item label="报事目的：">
                  <div style="width: 280px">{{ form.startTime }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="5" :offset="1">
                <el-form-item label="工单状态：">
                  <div style="width: 280px">{{ form.endTime }}</div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
                   <el-col :span="2" >
                <el-form-item label="上传图片：">
                  <img :src="this.form.actUrl" class="image"/>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="2">
                <el-form-item label="备注：">
                  <div style="width: 280px">{{ form.createBy }}</div>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
          <h4 class="form-header" style="color: #0558e7">申请人详情</h4>
          <el-row>
            <el-col :span="6">
              <el-form-item label="申请人：">
                <div style="width: 280px">{{ form.supplyName }}</div>
              </el-form-item>
            </el-col>
             <el-col :span="6">
                <el-form-item label="手机号：">
                  <div style="width: 280px">{{ form.createBy }}</div>
                </el-form-item>
              </el-col>
               <el-col :span="2">
                <el-form-item label="人员类别：">
                  <div style="width: 280px">{{ form.createBy }}</div>
                </el-form-item>
              </el-col>
          </el-row>
          <h4 class="form-header" style="color: #0558e7">期望执行人详情</h4>
         <el-row>
            <el-col :span="6">
              <el-form-item label="申请人：">
                <div style="width: 280px">{{ form.supplyName }}</div>
              </el-form-item>
            </el-col>
             <el-col :span="6">
                <el-form-item label="手机号：">
                  <div style="width: 280px">{{ form.createBy }}</div>
                </el-form-item>
              </el-col>
               <el-col :span="2">
                <el-form-item label="人员类别：">
                  <div style="width: 280px">{{ form.createBy }}</div>
                </el-form-item>
              </el-col>
          </el-row>
           <h4 class="form-header" style="color: #0558e7">审批详情</h4>
         <el-row>
            <el-col :span="6">
              <el-form-item label="审批状态：">
                <div style="width: 280px">{{ form.supplyName }}</div>
              </el-form-item>
            </el-col>
             <el-col :span="6">
                <el-form-item label="驳回原因：">
                  <div style="width: 280px">{{ form.createBy }}</div>
                </el-form-item>
              </el-col>
               <el-col :span="2">
                <el-form-item label="审批人：">
                  <div style="width: 280px">{{ form.createBy }}</div>
                </el-form-item>
              </el-col>
              <el-row>
              <el-col>
                <el-form-item label="审批时间：">
                  <div style="width: 280px">{{ form.createBy }}</div>
                </el-form-item>
              </el-col>
              </el-row>
          </el-row>
          <el-button  size="middle" style='margin-left:80%'   @click="cancel">返回</el-button>
        </el-form>
    </div>
  </div>
</template>

<script>
import {
  listDisasterLedger
} from "@/api/estate/work/orderLedger/DisasterLedger";
export default {
  name: "PropRole",
  data() {
    return {
           // 详情页面默认关闭
      showDetail: false,
      titleInfo:"",
      submitFlag: true,
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
      // 角色表格数据
      disasterList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: true,
      // 状态数据字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        auditStatus: undefined,
      },
      // 表单参数
      form: {},
      defaultProps: {
        children: "children",
        label: "label",
      },

    //   // 表单校验
    //   rules: {
    //     roleName: [
    //       {
    //         required: true,
    //         max: 50,
    //         message: "灾害类型不能为空",
    //         trigger: "blur",
    //       },
    //     ],
    //   },
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then((response) => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 查询角色列表 */
    getList() {
      this.loading = true;
      listDisasterLedger(this.queryParams).then((response) => {
        this.disasterList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /**
     * 通过
     */
    handlePass(){

    },
    /**
     * 驳回
     */
    handleOverrule(){

    },

     /**
     * 查看
     */
    handleInfo(data) {
        this.titleInfo="用户申请灾害报事详情"
        this.showDetail=true;
        this.open=false
        this.form=data
    },
    cancel() {
      this.open = true;
      this.showDetail=false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        disasterId: undefined,
        estateId: undefined,
        disasterName: undefined,
        remark: undefined,
        disasterStatus: "0",
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.disasterId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /**
     * 后台返回的状态改变
     */
statusFormatter(row, column){
				const status = row.disasterStatus
      			if (status == 0) {
        			return '审批中'
      			} else if (status == 1) {
        			return '通过'
      			} else if (status == 2) {
        			return '驳回'
      			}
			}
  },
};
</script>

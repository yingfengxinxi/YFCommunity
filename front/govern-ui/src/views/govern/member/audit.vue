<template>
  <div class="app-container">
    <div v-show="!open">
      <el-form
        :model="queryParams"
        ref="queryForm"
        :inline="true"
        v-show="showSearch"
        label-width="90px"
      >
        <el-form-item label="入党时间" prop="joinTime">
          <el-date-picker
            v-model="dateRange"
            size="small"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="姓名" prop="memberName">
          <el-input
            v-emoji
            v-model.trim="queryParams.memberName"
            placeholder="请输入党员姓名"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
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
      <el-row :gutter="10" class="mb8">
        <right-toolbar
          :showSearch.sync="showSearch"
          @queryTable="getList"
        ></right-toolbar>
      </el-row>
      <el-table
        v-loading="loading"
        :data="memberList"
        v-show="show"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column
          label="姓名"
          align="center"
          prop="memberName"
          show-overflow-tooltip
        />
        <el-table-column
          label="手机号"
          align="center"
          prop="memberPhone"
          show-overflow-tooltip
        />
        <el-table-column
          label="入党日期"
          align="center"
          prop="joinTime"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.joinTime, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="审核状态"
          align="center"
          prop="auditStatus"
          :formatter="auditStatusFormat"
        />
        <el-table-column
          label="党内职务"
          align="center"
          prop="memberDuty"
          show-overflow-tooltip
        >
        </el-table-column>
        <el-table-column
          label="申请时间"
          align="center"
          prop="createTime"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="住址"
          align="center"
          prop="address"
          width="150px"
          show-overflow-tooltip
        />
        <el-table-column
          label="操作"
          align="center"
          width="150px"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-check"
              @click="handleView(scope.row)"
              v-hasPermi="['govern:member:edit']"
              >审核
            </el-button>
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
    <!-- 查看党员列表-->
    <el-dialog
      title="党员审核详情"
      :visible.sync="showDetail"
      :before-close="closeDetail"
    >
      <el-form ref="form" :model="form" label-width="100px">
        <el-row>
          <el-col :span="6" :offset="3">
            <el-form-item label="姓名:">{{ form.memberName }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="3">
            <el-form-item label="联系电话:">{{
              form.memberPhone
            }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8" :offset="3">
            <el-form-item label="地址:">{{ form.address }}</el-form-item>
          </el-col>
          <el-col :span="8" :offset="1">
            <el-form-item label="入党时间:">{{ form.joinTime }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="3">
            <el-form-item label="党内职务：">{{
              form.memberDuty
            }}</el-form-item>
          </el-col>
          <el-col :span="8" :offset="3">
            <el-form-item label="申请时间:">{{ form.createTime }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8" :offset="3">
            <el-form-item label="认证图片:">
              <img
                :src="imageUrl"
                v-if="imageUrl"
                style="width: 280px; height: 400%"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="danger" plain @click="checkNo(form)">驳回</el-button>
        <el-popover
          placement="bottom"
          width="160"
          v-model="visible"
          style="margin-left: 10px"
        >
          <p>确定要通过审核吗？</p>
          <div style="text-align: right; margin: 0">
            <el-button size="mini" type="text" @click="visible = false"
              >取消</el-button
            >
            <el-button type="primary" size="mini" @click="checkOk(form)"
              >确定</el-button
            >
          </div>
          <el-button slot="reference" style="background-color: #33cc66"
            >通 过</el-button
          >
        </el-popover>
        <el-button @click="closeDetail()" style="margin-left: 10px"
          >关 闭</el-button
        >
      </div>
    </el-dialog>

    <!-- 驳回理由 -->
    <el-dialog
      :title="title"
      :visible.sync="showReason"
      width="500px"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="驳回理由" prop="rejectReason">
          <el-input
            onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
            type="textarea"
            :rows="5"
            v-model="form.rejectReason"
            placeholder="请输入驳回理由"
            style="width: 100%"
            show-word-limit
          />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel1">取 消</el-button>
        <el-button type="primary" @click="handleCheckNo(form)">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {
  auditList,
  checkNo,
  getAudit,
  checkOk,
} from "@/api/govern/member/audit";
import { equals } from "@/utils/common";

export default {
  name: "audit",
  components: {},
  data() {
    return {
      imageUrl: "",
      visible: false,
      // 住址
      address: "",
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
      dateRange: [],
      open1: false,
      // 列表数据
      memberList: [],
      organizationList: [],
      ownerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //楼栋数据
      buildingList: [],
      //单元数据
      unitList: [],
      //房间数据
      roomList: [],
      communityList: [],
      //选择下拉楼宇
      showBuilding: false,
      //选择下拉单元
      showUnit: false,
      //选择下拉房间
      showRoom: false,
      // $comment字典
      sexOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        partyId: undefined,
        memberName: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        rejectReason: [
          { required: true, message: "驳回理由不能为空", trigger: "blur" },
        ],
      },
      //是否可见新增时姓名下拉
      ownerOption: false,
      //是否可见修改时姓名
      ownerInput: false,
      //详情页面
      showDetail: false,
      //显示页面
      show: true,
      //显示添加或修改页面
      showAdd: false,
      //是否弹出输入驳回理由
      showReason: false,
      //owner详情页面
      details: false,
      showUpdate: false,
      //关闭按钮
      showClose: false,
      //审核按钮显示
      showPass: false,
      //隐藏文本框
      showInput: false,
      //失败文本框
      errorInput: false,
      //隐藏审核按钮
      showPassButton: false,
    };
  },

  created() {
    // 列表
    this.getList();
  },

  methods: {
    /** 查询列表 */
    getList() {
      this.loading = true;
      auditList(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.memberList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /**关闭查看*/
    closeDetail() {
      this.showDetail = false;
      this.open = false;
      this.cancel1();
      this.showPass = false;
      this.showPassButton = false;
      this.details = false;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.submitFlag = true;
      this.reset();
      this.open = true;
      this.showUpdate = true;
      this.ownerInput = true;
      this.ownerOption = false;
      // 获取详情
      getMember(row.memberId).then((response) => {
        this.form = response.data;
        this.address = response.data.address;
        // 获取人员列表
        this.getOwnerList();
        this.title = "修改党员信息";
      });
    },
    // 取消按钮
    cancel1() {
      this.showUpdate = false;
      this.open = false;
      this.showReason = false;
    },
    // 表单重置
    reset() {
      this.form = {};
      this.address = "";
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.memberId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /**驳回审核弹框*/
    checkNo(row) {
      this.showReason = true;
    },

    /** 审核按钮操作 */
    handlePass(row) {
      this.showPass = true;
      this.title = "审核列表弹框";
      this.open1 = true;
      this.form = row;
    },
    /** 查看按钮操作 */
    handleView(row) {
      this.showDetail = true;
      this.open = true;
      getAudit(row.memberId).then((response) => {
        this.form = response.data;
        this.imageUrl = this.form.proveUrl;
      });
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["forms"].validate((valid) => {
        if (valid) {
          if (this.form.memberId != null) {
            upMember(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.showUpdate = false;
              this.getList();
            });
          } else {
            addMember(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.showUpdate = false;
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /**驳回审核*/
    handleCheckNo(row) {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          checkNo(row).then((response) => {
            this.showReason = false;
            this.open = false;
            this.showCheckMember = false;
            this.getList();
            this.showDetail = false;
            this.$modal.msgSuccess("驳回成功");
          });
        }
      });
    },
    /**审核通过*/
    checkOk(row) {
      if (this.visible == true) {
        checkOk(row.memberId).then((response) => {
          this.showReason = false;
          this.open = false;
          this.showCheckMember = false;
          this.getList();
          this.showDetail = false;
          this.$modal.msgSuccess("操作成功");
          this.visible = false;
        });
      }
    },
    auditStatusFormat(row) {
      if (equals(row.auditStatus, "0")) {
        return "审核中";
      } else if (equals(row.auditStatus, "1")) {
        return "通过";
      } else if (equals(row.auditStatus, "2")) {
        return "驳回";
      }
      return "-";
    },
  },
};
</script>

<style scoped>
.avatar {
  width: 400px;
  height: 200px;
  display: block;
}

.avatar2 {
  width: 400px;
  height: 200px;
  display: block;
}

.peopleImg {
  width: 120px;
  height: 160px;
  display: block;
}

.pass {
  margin-top: 30px;
  margin-right: 80px;
  background-color: #33cc66;
}

.reject {
  margin-top: 30px;
  margin-right: 120px;
  background-color: #ff3300;
}
</style>

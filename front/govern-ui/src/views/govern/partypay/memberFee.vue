<template>
  <div class="app-container">
    <el-row :gutter="12">
      <el-form :model="queryParams" ref="queryForm" :inline="true">
        <el-form-item label="党组织" prop="partyId">
          <el-select v-model="queryParams.partyId" clearable  placeholder="请选择党组织名称">
            <el-option
              v-for="item in organizationList"
              :key="item.partyId"
              :label="item.partyName"
              :value="item.partyId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="姓名" prop="memberName">
          <el-input
            v-model.trim="queryParams.memberName"
            placeholder="请输入党员姓名"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="手机号码" prop="memberPhone">
          <el-input
            v-model.trim="queryParams.memberPhone"
            placeholder="请输入党员手机号"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
    </el-row>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button type="primary"
                   plain :disabled="multiple"
                   icon="el-icon-edit"
                   size="mini"
                   v-hasPermi="['govern:memberFee:edit']"
                   @click="handleSetPartyFee">
          党费设置 </el-button>
      </el-col>
    </el-row>
    <el-table v-loading="loading" :data="memberList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="姓名" align="center" prop="memberName"/>
      <el-table-column label="手机号" align="center" prop="memberPhone"/>
      <el-table-column label="所属党组织" align="center" prop="partyName" />
      <el-table-column label="所属小区" align="center" prop="communityName" />
      <el-table-column label="人员类型" align="center" prop="accountType" :formatter="accountTypeFormat"/>
      <el-table-column label="党费" align="center" prop="partyFee" />
    </el-table>
    <pagination
      v-show="total>0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getMemberList()"
      style="margin-bottom: 20px"
    />
    <el-dialog title="党费设置" :visible.sync="feeSet" width="30%">
      <el-form :model="feeForm" :rules="feeRules" ref="feeForm" label-width="80px">
        <el-form-item label="党费：" prop="partyFee">
          <el-input-number v-model="feeForm.partyFee" :min="0" :max="10000" style="width: 100%"></el-input-number>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="feeSetSubmit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

import {memberList, partyAll} from "@/api/govern/member/member";
import {equals} from "@/utils/common";
import {partyFeeEdit} from "@/api/govern/partypay/memberFee";

export default {
  name: "MemberFee",
  data() {
    return {
      loading:true,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        partyId:undefined,
        memberName:'',
        memberPhone:'',
      },
      organizationList:[],
      multiple:true,
      memberList:[],
      submitFlag: true,
      total:0,
      ids:[],
      feeSet:false,
      feeForm:{
        ids:[],
        partyFee:0,
      },
      feeRules:{
        partyFee: [
          {required: true, message: "请输入党费", trigger: "blur"}
        ],
      },
    };
  },
  created() {
    this.getOrganizationList();
    this.getMemberList();
  },
  methods: {
    //查询党组织列表
    getOrganizationList() {
      partyAll().then(response => {
        if (response.code == 200) {
          this.organizationList = response.data;
        } else {
          this.msgError(response.msg);
        }
      });
    },
    getMemberList(){
      memberList(this.queryParams).then(response => {
        this.memberList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //搜索按钮
    handleQuery(){
      this.queryParams.pageNum = 1;
      this.getMemberList();
    },
    //重置按钮
    resetQuery(){
      this.reset();
      this.resetForm("queryForm");
      this.handleQuery();
    },
    reset() {
      this.feeForm = {};
      this.queryParams={
        pageNum: 1,
        pageSize: 10,
        ownerName:'',
        ownerPhone:'',
      }
      this.resetForm("feeForm");
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.memberId)
      this.multiple = !selection.length
    },
    //党费设置
    handleSetPartyFee(){
      this.feeSet=true;
    },
    // 取消操作
    cancel() {
      this.resetForm("feeForm");
      this.feeSet = false;
    },
    feeSetSubmit(){
      this.feeForm.memberIds = this.ids
      this.$refs["feeForm"].validate(valid => {
        if (valid) {
          this.submitFlag = false;
          partyFeeEdit(this.feeForm).then(response => {
            this.$modal.msgSuccess("设置成功");
            this.submitFlag = true;
            this.feeSet = false;
            this.getMemberList();
          });
        }
      });
    },
    accountTypeFormat(row) {
      if (equals(row.accountType, "0")) {
        return "业主";
      } else if (equals(row.accountType, "1")) {
        return "租客";
      }
      return "-";
    },
  },
}
</script>

<style scoped>

</style>

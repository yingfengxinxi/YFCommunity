<template>
  <div class="app-container">
    <el-row :gutter="12">
      <el-form :model="queryParams" :inline="true">
        <el-col :span="6" :offset="19">
          <el-form-item label="缴费月份">
            <el-date-picker
              v-model="queryParams.createTime"
              type="month"
              :editable="false"
              :clearable="false"
              @change="queryStatistics"
              placeholder="选择缴费月份"
            >
            </el-date-picker>
          </el-form-item>
        </el-col>
      </el-form>
    </el-row>
    <el-row>
      <el-col :span="4">
        <el-card class="total-money">
          <div>
            <h5 style="color: white; margin-top: 1px">
              已缴人数：{{ totalMember }} / {{ payersNum }}
            </h5>
          </div>
          <h3 style="color: white; margin-top: 1px">已缴费总金额:</h3>
          <h1 style="color: white; font-size: 30px; margin-top: 1px">
            {{ moneySum }}
          </h1>
        </el-card>
      </el-col>
      <el-col :span="4" :offset="1">
        <el-card class="normal-pay">
          <h5 style="color: white; margin-top: 1px">
            正常缴费人数:{{ normalPayNum }}
          </h5>
          <h3 style="color: white; margin-top: 1px">正常缴费总金额：</h3>
          <h1 style="color: white; font-size: 30px; margin-top: 1px">
            {{ normalPaySum }}
          </h1>
        </el-card>
      </el-col>
      <el-col :span="4" :offset="1">
        <el-card class="advance-pay">
          <h5 style="color: white; margin-top: 1px">
            预缴人数:{{ prepaidNum }}
          </h5>
          <h3 style="color: white; margin-top: 1px">预缴总金额:</h3>
          <h1 style="color: white; font-size: 30px; margin-top: 1px">
            {{ prepaidSum }}
          </h1>
        </el-card>
      </el-col>
      <el-col :span="4" :offset="1">
        <el-card class="make-up">
          <h5 style="color: white; margin-top: 1px">
            补缴人数:{{ backFeeNum }}
          </h5>
          <h3 style="color: white; margin-top: 1px">补缴总金额:</h3>
          <h1 style="color: white; font-size: 30px; margin-top: 1px">
            {{ backFeeSum }}
          </h1>
        </el-card>
      </el-col>
      <el-col :span="4" :offset="1">
        <el-card class="offline-pay">
          <h5 style="color: white; margin-top: 1px">
            线下缴费人数:{{ offlineNum }}
          </h5>
          <h3 style="color: white; margin-top: 1px">线下缴费总金额:</h3>
          <h1 style="color: white; font-size: 30px; margin-top: 1px">
            {{ offlineSum }}
          </h1>
        </el-card>
      </el-col>
    </el-row>
    <el-row>
      <el-form
        :model="queryParams"
        ref="queryForm"
        :inline="true"
        style="margin-top: 20px"
      >
        <el-form-item label="所属小区">
          <el-select
            v-model="queryParams.communityId"
            placeholder="请选择所属小区"
            style="width: 100%"
            @change="tChange($event)"
          >
            <el-option
              v-for="item in communityList"
              :key="item.communityId"
              :label="item.communityName"
              :value="item.communityId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="党组织" prop="partyId">
          <el-select
            v-model="queryParams.partyId"
            clearable
            placeholder="请选择党组织名称"
          >
            <el-option
              v-for="item in organizationLists"
              :key="item.partyId"
              :label="item.partyName"
              :value="item.partyId"
            >
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
    </el-row>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="success"
          plain
          icon="el-icon-circle-plus"
          size="mini"
          v-hasPermi="['govern:partyPay:add']"
          @click="handlePay"
          >新增缴费
        </el-button>
        <el-button
          type="warning"
          plain
          icon="el-icon-circle-check"
          :disabled="multiple"
          size="mini"
          v-hasPermi="['govern:partyPay:edit']"
          @click="handleCollect"
          >归集
        </el-button>
      </el-col>
    </el-row>
    <el-table
      v-loading="loading"
      :data="partyMoneyList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="姓名" align="center" prop="memberName" />
      <el-table-column label="手机号" align="center" prop="memberPhone" />
      <el-table-column label="所属党组织" align="center" prop="partyName" />
      <el-table-column label="缴费金额" align="center" prop="partyFee" />
      <el-table-column
        label="缴费类型"
        align="center"
        prop="payType"
        :formatter="payTypeFormat"
      />
      <el-table-column
        label="缴费方式"
        align="center"
        prop="payWay"
        :formatter="payWayFormat"
      />
      <el-table-column label="缴费月份" align="center" prop="payMonth" />
      <el-table-column label="缴费时间" align="center">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.payTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column
        label="归集状态"
        align="center"
        prop="collectStatus"
        :formatter="collectStatusFormat"
      />
      <el-table-column label="归集人" align="center">
        <template slot-scope="scope">
          <span v-if="scope.row.collectName === ''">-</span>
          <span v-else>{{ scope.row.collectName }}</span>
        </template>
      </el-table-column>
    </el-table>
    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList()"
      style="margin-bottom: 20px"
    />
    <el-dialog
      title="党费缴费"
      :visible.sync="offlineShow"
      :before-close="cancel"
      width="30%"
    >
      <el-form
        :model="offlineForm"
        :rules="offlineRules"
        ref="offlineForm"
        label-width="100px"
      >
        <el-row>
          <el-form-item label="党组织：" prop="partyId">
            <el-select
              v-model="offlineForm.partyId"
              clearable
              placeholder="请选择党组织"
              @change="queryMember"
              style="width: 100%"
            >
              <el-option
                v-for="item in organizationList"
                :key="item.partyId"
                :label="item.partyName"
                :value="item.partyId"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="党员：" prop="memberId">
            <el-select
              v-model="offlineForm.memberId"
              clearable
              placeholder="请选择党员"
              style="width: 100%"
            >
              <el-option
                v-for="item in memberList"
                :key="item.memberId"
                :label="item.memberName + '-' + item.memberPhone"
                :value="item.memberId"
              >
              </el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="缴费方式：" prop="payWay">
            <el-select
              v-model="offlineForm.payWay"
              clearable
              placeholder="请选择缴费方式"
              style="width: 100%"
            >
              <el-option value="1" label="支付宝"></el-option>
              <el-option value="2" label="微信"></el-option>
              <el-option value="3" label="线下"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="缴费月份" prop="payMonth">
            <el-date-picker
              v-model="offlineForm.payMonth"
              type="month"
              :editable="false"
              :clearable="false"
              placeholder="选择缴费月份"
            >
            </el-date-picker>
          </el-form-item>
        </el-row>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="offlinePaySubmit">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { partyAll } from "@/api/govern/member/member";
import { getCurrentList } from "@/api/govern/community";
import {
  listPartyMoney,
  queryStatistics,
  queryMember,
  addPartyMoney,
  updatePayStatus,
  partyList,
} from "@/api/govern/partypay/partyMoney";
import { equals } from "@/utils/common";

export default {
  name: "Statistics",
  data() {
    return {
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        createTime: new Date(),
        payTime: this.defaultDate(new Date()),
        partyId: undefined,
        memberName: "",
        memberPhone: "",
        communityId: null,
      },
      //总党员数
      totalMember: 0,
      //缴费人数
      payersNum: 0,
      //已缴费总金额
      moneySum: 0,
      //正常缴费人数
      normalPayNum: 0,
      //正常缴费总金额
      normalPaySum: 0,
      //补缴人数
      backFeeNum: 0,
      //补缴总金额
      backFeeSum: 0,
      //预缴人数
      prepaidNum: 0,
      //预缴总金额
      prepaidSum: 0,
      //线下缴费人数
      offlineNum: 0,
      //线下缴费总金额
      offlineSum: 0,
      //党组织
      organizationList: [],
      //党组织
      organizationLists: [],
      // 项目列表
      communityList: [],
      tsp: null,
      form: {},
      loading: true,
      //缴费记录
      partyMoneyList: [],
      //总条数
      total: 0,
      offlineShow: false,
      submitFlag: true,
      offlineForm: {
        partyOrganizationId: undefined,
        partyMemberId: undefined,
        payWay: "",
      },
      offlineRules: {
        partyId: [{ required: true, message: "请选择党组织", trigger: "blur" }],
        memberId: [{ required: true, message: "请选择党员", trigger: "blur" }],
        payWay: [
          { required: true, message: "请选择支付方式", trigger: "blur" },
        ],
        payMonth: [
          { required: true, message: "请选择缴费月份", trigger: "blur" },
        ],
      },
      //党员
      memberList: [],
      ids: [],
      multiple: true,
      feeCollect: false,
    };
  },
  created() {
    // 查小区
    this.listCommunityList();
    // 查党组织
    this.getOrganizationList();
  },
  methods: {
    defaultDate(date) {
      console.log(date);
      let year = date.getFullYear();
      let month = date.getMonth() + 1;
      if (month < 9) {
        return year + "-0" + month + "-01";
      }
      return year + "-" + month + "-01";
    },
    //查询统计数据
    queryStatistics() {
      this.queryParams.payMonth = this.defaultDate(this.queryParams.createTime);
      queryStatistics(this.queryParams).then((response) => {
        this.totalMember = response.totalMember;
        this.payersNum = response.payersNum;
        this.moneySum = response.moneySum == null ? 0 : response.moneySum;
        this.normalPayNum = response.normalPayNum;
        this.normalPaySum =
          response.normalPaySum == null ? 0 : response.normalPaySum;
        this.backFeeNum = response.backFeeNum;
        this.backFeeSum = response.backFeeSum == null ? 0 : response.backFeeSum;
        this.prepaidNum = response.prepaidNum;
        this.prepaidSum = response.prepaidSum == null ? 0 : response.prepaidSum;
        this.offlineNum = response.offlineNum;
        this.offlineSum = response.offlineSum == null ? 0 : response.offlineSum;
      });
    },
    //搜索按钮操作
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 查询列表 */
    getList() {
      this.loading = true;
      listPartyMoney(this.queryParams).then((response) => {
        this.partyMoneyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 根据小区查党组织
    tChange(value) {
      this.tsp = value;
      // 查党组织
      this.getPartyList(value);
      // 查激费统计
      this.queryStatistics();
    },
    /** 查询党组织列表 */
    getOrganizationList() {
      partyAll().then((response) => {
        if (response.code == 200) {
          this.organizationList = response.data;
        } else {
          this.msgError(response.msg);
        }
      });
    },
    /** 根据小区查党组织 */
    getPartyList(communityId) {
      if (!equals(this.queryParams.partyId, null)) {
        this.queryParams.partyId = null;
      }
      if (!equals(null, communityId)) {
        partyList(communityId).then((response) => {
          this.organizationLists = response.data;
        });
      }
    },
    /** 获取项目 */
    listCommunityList() {
      getCurrentList().then((response) => {
        this.communityList = response.data;
        if (this.communityList.length > 0) {
          this.queryParams.communityId = this.communityList[0].communityId;
        }
        this.getList();
        // 激费统计
        this.queryStatistics();
        // 查党组织
        this.getPartyList(this.queryParams.communityId);
      });
    },
    //重置按钮操作
    resetQuery() {
      this.reset();
      this.resetForm("queryForm");
      this.resetForm("queryParams");
      this.handleQuery();
    },
    // 表单重置
    reset() {
      this.form = {};
      this.resetForm("offlineForm");
      // this.queryStatistics();
    },
    //线下缴费
    handlePay() {
      this.offlineShow = true;
    },
    //根据党组织id查询党员
    queryMember() {
      if (!equals(this.offlineForm.memberId, null)) {
        this.offlineForm.memberId = null;
      }
      queryMember(this.offlineForm.partyId).then((response) => {
        this.memberList = response.data;
      });
    },
    // 取消操作
    cancel() {
      this.resetForm("offlineForm");
      this.offlineShow = false;
    },
    offlinePaySubmit() {
      // 项目id
      this.offlineForm.communityId = this.queryParams.communityId;
      this.offlineForm.payMonth = this.defaultDate(this.offlineForm.payMonth);
      console.log(this.offlineForm.payMonth);
      this.$refs["offlineForm"].validate((valid) => {
        if (valid) {
          addPartyMoney(this.offlineForm).then((response) => {
            this.$modal.msgSuccess("新增缴费成功");
            this.cancel();
            this.queryStatistics();
            this.getList();
          });
        }
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.logId);
      this.multiple = !selection.length;
    },
    //党费归集
    handleCollect() {
      let ids = this.ids;
      this.$confirm("是否确认归集所选数据?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return updatePayStatus(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("归集成功");
        });
    },
    payTypeFormat(row) {
      if (equals(row.payType, "1")) {
        return "正常";
      } else if (equals(row.payType, "2")) {
        return "预缴";
      } else if (equals(row.payType, "3")) {
        return "补缴";
      }
      return "-";
    },
    payWayFormat(row) {
      if (equals(row.payWay, "1")) {
        return "支付宝";
      } else if (equals(row.payWay, "2")) {
        return "微信";
      } else if (equals(row.payWay, "3")) {
        return "线下";
      }
      return "-";
    },
    collectStatusFormat(row) {
      if (equals(row.collectStatus, "0")) {
        return "未归集";
      } else if (equals(row.collectStatus, "1")) {
        return "已归集";
      }
      return "-";
    },
  },
};
</script>

<style lang="scss">
.total-money {
  display: -webkit-box;
  align-items: flex-start;
  height: 100%;
  background-image: url("~@/assets/images/Rectangle 106.png");
  background-size: cover;
  align-content: center;
  flex-direction: column;
}

.normal-pay {
  display: -webkit-box;
  align-items: flex-start;
  height: 100%;
  background-image: url("~@/assets/images/Rectangle 107.png");
  background-size: cover;
  align-content: center;
  flex-direction: column;
}

.make-up {
  display: -webkit-box;
  align-items: flex-start;
  height: 100%;
  background-image: url("~@/assets/images/Rectangle 108.png");
  background-size: cover;
  align-content: center;
  flex-direction: column;
}

.advance-pay {
  display: -webkit-box;
  align-items: flex-start;
  height: 100%;
  background-image: url("~@/assets/images/Rectangle 109.png");
  background-size: cover;
  align-content: center;
  flex-direction: column;
}

.offline-pay {
  display: -webkit-box;
  align-items: flex-start;
  height: 100%;
  background-image: url("~@/assets/images/Rectangle 110.png");
  background-size: cover;
  align-content: center;
  flex-direction: column;
}
</style>

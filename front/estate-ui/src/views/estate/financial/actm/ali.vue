<template>
  <div class="app-container">

    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">

      <el-form-item label="所属小区:" prop="communityId">
        <el-select v-model="queryParams.communityId" placeholder="请选择" style="width: 240px">
          <el-option
            v-for="(item, index) in currentList"
            :key="index"
            :label="item.communityName"
            :value="item.communityId"
          ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="账号名称" prop="alipayName">
        <el-input
          v-model="queryParams.alipayName"
          placeholder="请输入账号名称"
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAliAdd"
          v-if="total<=0"
        >账号添加
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getAliList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="aliList">
      <el-table-column label="账号名称" align="center" prop="alipayName"/>
      <el-table-column label="应用ID" align="center" prop="appId"/>
      <el-table-column label="所属小区" align="center" prop="communityName"/>
      <el-table-column label="状态" align="center" prop="aliStatus">
          <template slot-scope="scope">
              <el-tag type="success" v-show="scope.row.aliStatus === '0'">启用</el-tag>
              <el-tag type="danger" v-show="scope.row.aliStatus === '1'">停用</el-tag>
          </template>
      </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" class="text-info"
            @click="handleAliUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" class="text-danger"
            @click="handleAliDel(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getAliList"/>

    <!-- 新增-->
    <el-dialog :title="aliTitle" :visible.sync="aliOpen" width="900px">
      <el-form ref="aliForm" :model="aliForm" label-width="90px" :rules="aliRules">
        <el-row>
          <el-col :span="12">
            <el-form-item label="所属小区:" prop="communityId">
              <el-select v-model="aliForm.communityId" placeholder="请选择小区" style="width: 100%">
                <el-option
                  v-for="(item, index) in currentList"
                  :key="index"
                  :label="item.communityName"
                  :value="item.communityId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="账户名称:" prop="alipayName">
              <el-input v-model="aliForm.alipayName" placeholder="请输入账户名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
                <el-form-item label="应用ID:" prop="appId">
                    <el-input v-model="aliForm.appId" placeholder="请输入应用ID"></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="状态:" prop="aliStatus">
                    <el-select v-model="aliForm.aliStatus" placeholder="请选择账号状态" style="width: 100%">
                        <el-option label="启用" value="0"></el-option>
                        <el-option label="停用" value="1"></el-option>
                    </el-select>
                </el-form-item>
            </el-col>
        </el-row>
        <el-form-item label="公钥串:" prop="publicKey">
          <el-input type="textarea" rows="5" v-model="aliForm.publicKey" placeholder="请输入公钥串"></el-input>
        </el-form-item>
        <el-form-item label="私钥串:" prop="privateKey">
          <el-input type="textarea" rows="18" v-model="aliForm.privateKey" placeholder="请输入私钥串"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitAliPay">确 定</el-button>
        <el-button @click="reset">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {aliList, alipayAdd, alipayUpdate, delAlipay} from "@/api/estate/financial/AccountManagement";
import {getCurrentList} from "@/api/estate/community";

export default {
  name: "AliAccount",
  data() {
    return {
      currentList: [],
      // 显示搜索条件
      showSearch: true,
      // 遮罩层
      loading: true,
      total: 0,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: null,
        alipayName: "",
      },
      //  支付宝账号集合
      aliList: [],
      aliTitle: null,
      aliOpen: false,
      aliForm: {},

      defaultProps: {
        children: "communityId",
        label: "communityName"
      },
      // 表单校验
      aliRules: {
        communityId: [
          {required: true, message: "请选择小区", trigger: "blur"}
        ],
        alipayName: [
          {required: true, message: "账户名称不能为空", trigger: "blur"}
        ],
        aliStatus: [
          {required: true, message: "请选择账号状态", trigger: "blur"}
        ],
        appId: [
          {required: true, message: "应用ID不能为空", trigger: "blur"}
        ],
        publicKey: [
          {required: true, message: "公钥串不能为空", trigger: "blur"}
        ],
        privateKey: [
          {required: true, message: "私钥串不能为空", trigger: "blur"}
        ],
      },

    }
  },
  created() {
    this.getCurrentList();
    this.getAliList();
  },
  methods: {
    getCurrentList() {
      getCurrentList().then(res => {
        this.currentList = res.data
      })
    },

    getAliList() {
        aliList(this.queryParams).then(res => {
          this.aliList = res.rows;
          this.total = res.total;
          this.loading = false;
        })
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getAliList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },

    //新增
    handleAliAdd() {
      this.aliTitle = "支付宝账号添加";
      this.aliOpen = true;
    },

    handleAliUpdate(row) {
      this.aliForm = row
      this.aliTitle = "支付宝账号修改";
      this.aliOpen = true;
    },

    submitAliPay() {
      this.$refs["aliForm"].validate(valid => {
        if (valid) {
          if (this.aliForm.alipayId == null) {
              alipayAdd(this.aliForm).then(res => {
                  this.$modal.msgSuccess("添加成功")
                  this.getAliList();
                  this.reset();
              })
          } else {
              alipayUpdate(this.aliForm).then(res => {
                  this.$modal.msgSuccess("修改成功")
                  this.getAliList();
                  this.reset();
              })
          }
        }
      });

    },

    handleAliDel(row) {
      this.$modal.confirm('是否确认删除此支付宝账号？').then(function() {
        return delAlipay(row.alipayId);
      }).then(() => {
        this.getAliList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },

    reset() {
      this.aliForm = {};
      this.aliOpen = false;
      this.aliTitle = "";
    },

  },
}
</script>

<style scoped>

</style>

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

      <el-form-item label="账号名称" prop="accountName">
        <el-input
          v-model="queryParams.accountName"
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
          @click="handleAbcAdd"
        >账号添加
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getAbcList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="abcList">
      <el-table-column label="账号名称" align="center" prop="accountName"/>
      <el-table-column label="商户号" align="center" prop="merchantId"/>
      <el-table-column label="所属小区" align="center" prop="communityName"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" class="text-info"
                     @click="handleAbcUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" class="text-danger"
                     @click="handleAbcDel(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getAbcList"/>

    <!-- 新增-->
    <el-dialog :title="abcTitle" :visible.sync="abcOpen" width="600px">
      <el-form ref="abcForm" :model="abcForm" label-width="100px" :rules="abcRules">
        <el-form-item label="所属小区:" prop="communityId">
          <el-select v-model="abcForm.communityId" placeholder="请选择小区" style="width: 100%">
            <el-option
              v-for="(item, index) in currentList"
              :key="index"
              :label="item.communityName"
              :value="item.communityId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="账户名称:" prop="accountName">
          <el-input v-model="abcForm.accountName" placeholder="请输入账户名称"></el-input>
        </el-form-item>
        <el-form-item label="商户号:" prop="merchantId">
          <el-input v-model="abcForm.merchantId" placeholder="请输入商户号"></el-input>
        </el-form-item>
        <el-form-item label="私钥文件:" prop="privateUrl">
          <!-- <file-upload v-model="abcForm.privateUrl">点击上传</file-upload> -->
          <el-input v-model="abcForm.privateUrl" placeholder="请输入私钥文件路径"></el-input>
          <!-- <el-upload class="upload-demo" action="#" accept=".pem,.p12"
                     :before-upload="beforeUpload" :on-change="uploadPrivateFile">
            <el-button size="small" type="primary">点击上传</el-button> <span> {{ privateKeyName }} </span>
          </el-upload> -->
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitAbcPay">确 定</el-button>
        <el-button @click="reset">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {wechatList, addWechat, updateWechat, delWechat} from "@/api/estate/financial/AccountManagement";
import {getCurrentList} from "@/api/estate/community";

export default {
  name: "WechatAccount",
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
        accountName: "",
      },
      //  支付宝账号集合
      abcList: [],
      abcTitle: null,
      abcOpen: false,
      abcForm: {},
      privateKeyName: "",

      defaultProps: {
        children: "communityId",
        label: "communityName"
      },
      // 表单校验
      abcRules: {
        communityId: [
          {required: true, message: "请选择小区", trigger: "blur"}
        ],
        accountName: [
          {required: true, message: "账户名称不能为空", trigger: "blur"}
        ],
        merchantId: [
          {required: true, message: "商户号不能为空", trigger: "blur"}
        ],
        privateUrl: [
          {required: true, message: "请选择商户私钥文件", trigger: "blur"}
        ],
      },

    }
  },
  created() {
    this.getCurrentList();
    this.getAbcList();
  },
  methods: {
    getCurrentList() {
      getCurrentList().then(res => {
        this.currentList = res.data
      })
    },

    getAbcList() {
        wechatList(this.queryParams).then(res => {
          this.abcList = res.rows;
          this.total = res.total;
          this.loading = false;
        })
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getAbcList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },

    //新增
    handleAbcAdd() {
      this.abcTitle = "农行账号添加";
      this.abcOpen = true;
    },

    handleAbcUpdate(row) {
      this.abcForm = row
      this.abcTitle = "农行账号修改";
      this.abcOpen = true;
    },

    uploadPrivateFile(file, fileList) {
      console.log(file)
      let formData = new FormData();
      formData.append('file', file.raw)
      console.log(formData)
    },
    beforeUpload(file) {
      this.privateKeyName = file.name;
      this.abcForm.privateUrl = file.name;
      return false;
    },

    submitAbcPay() {
      this.$refs["abcForm"].validate(valid => {
        if (valid) {
          if (this.abcForm.wechatId == null) {
              addWechat(this.abcForm).then(res => {
                this.$message.success(res.msg)
                this.getAbcList();
                this.reset();
              })
          } else {
              updateWechat(this.abcForm).then(res => {
                this.$message.success("修改成功")
                this.getAbcList();
                this.reset();
              })
          }
        }
      });

    },

    handleAbcDel(row) {
      this.$modal.confirm('是否确认删除此微信账号？').then(function() {
        return delWechat(row.wechatId);
      }).then(() => {
        this.getAbcList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },

    reset() {
      this.abcForm = {};
      this.abcOpen = false;
      this.abcTitle = "";
    },

  },
}
</script>

<style scoped>

</style>

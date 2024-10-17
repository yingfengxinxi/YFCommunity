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

      <el-form-item label="账号名称" prop="wechatName">
        <el-input
          v-model="queryParams.wechatName"
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
          @click="handleWxAdd"
        >账号添加
        </el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getWxList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="wechatList">
      <el-table-column label="所属小区" align="center" prop="communityName"/>
      <el-table-column label="账号名称" align="center" prop="wechatName" :show-overflow-tooltip="true"/>
      <el-table-column label="商户号" align="center" prop="mchId"/>
        <el-table-column label="状态" align="center" prop="aliStatus">
            <template slot-scope="scope">
                <el-tag type="success" v-show="scope.row.wechatStatus === '0'">启用</el-tag>
                <el-tag type="danger" v-show="scope.row.wechatStatus === '1'">停用</el-tag>
            </template>
        </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button size="mini" type="text" icon="el-icon-edit" class="text-info"
                     @click="handleWxUpdate(scope.row)">修改</el-button>
          <el-button size="mini" type="text" icon="el-icon-edit" class="text-danger"
                     @click="handleWxDel(scope.row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getWxList"/>

    <!-- 新增-->
    <el-dialog :title="wxTitle" :visible.sync="wxOpen" width="900px">
      <el-form ref="wxForm" :model="wxForm" label-width="120px" :rules="wxRules">
        <el-row>
          <el-col :span="12">
            <el-form-item label="所属小区:" prop="communityId">
              <el-select :disabled="isDisabled" v-model="wxForm.communityId" placeholder="请选择小区" style="width: 100%">
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
            <el-form-item label="账户名称:" prop="wechatName">
              <el-input v-model="wxForm.wechatName" placeholder="请输入账户名称"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
                <el-form-item label="物业小程序ID:" prop="appId">
                    <el-input v-model="wxForm.appId" placeholder="请输入应用ID"></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="物业小程序秘钥:" prop="appSecret">
                    <el-input v-model="wxForm.appSecret" placeholder="请输入应用秘钥"></el-input>
                </el-form-item>
            </el-col>
        </el-row>
        <el-row>
            <el-col :span="12">
                <el-form-item label="业主小程序ID:" prop="ownerAppId">
                    <el-input v-model="wxForm.ownerAppId" placeholder="请输入应用ID"></el-input>
                </el-form-item>
            </el-col>
            <el-col :span="12">
                <el-form-item label="业主小程序秘钥:" prop="ownerAppSecret">
                    <el-input v-model="wxForm.ownerAppSecret" placeholder="请输入应用秘钥"></el-input>
                </el-form-item>
            </el-col>
        </el-row>
        <el-form-item label="状态:" prop="wechatStatus">
          <el-select v-model="wxForm.wechatStatus" placeholder="请选择账号状态" style="width: 100%">
            <el-option label="启用" value="0"></el-option>
            <el-option label="停用" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="商户号:" prop="mchId">
          <el-input v-model="wxForm.mchId" placeholder="请输入商户号"></el-input>
        </el-form-item>
        <el-form-item label="证书序列号:" prop="serialNumber">
          <el-input v-model="wxForm.serialNumber" placeholder="请输入证书序列号"></el-input>
        </el-form-item>
        <el-form-item label="API_V3_KEY:" prop="apiKey">
          <el-input v-model="wxForm.apiKey" placeholder="请输入证书序列号"></el-input>
        </el-form-item>
        <el-form-item label="商户私钥文件:" prop="privateUrl">
          <el-input v-model="wxForm.privateUrl" placeholder="请输入商户私钥路径"></el-input>
          <!-- <file-upload v-model="wxForm.privateUrl" :fileType="['pem','p12']">点击上传</file-upload> -->
          <!-- <el-upload class="upload-demo" action="#" accept=".pem,.p12"
            :before-upload="beforeUpload" :on-change="uploadPrivateFile">
            <el-button size="small" type="primary">点击上传</el-button> <span> {{ privateKeyName }} </span>
          </el-upload> -->
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitWxPay">确 定</el-button>
        <el-button @click="reset">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {wechatList, addWechat, updateWechat, delWechat, uploadPrivateKey } from "@/api/estate/financial/AccountManagement";
import {getCurrentList} from "@/api/estate/community";

export default {
  name: "WechatAccount",
  data() {
    return {
      isDisabled:false,
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
        wechatName: "",
      },
      //  支付宝账号集合
      wechatList: [],
      wxTitle: null,
      wxOpen: false,
      wxForm: {},
      privateKeyName: "",

      defaultProps: {
        children: "communityId",
        label: "communityName"
      },
      // 表单校验
      wxRules: {
        communityId: [
          {required: true, message: "请选择小区", trigger: "blur"}
        ],
        wechatName: [
          {required: true, message: "账户名称不能为空", trigger: "blur"}
        ],
        wechatStatus: [
            {required: true, message: "请选择账号状态", trigger: "blur"}
        ],
        // appId: [
        //   {required: true, message: "应用ID不能为空", trigger: "blur"}
        // ],
        // appSecret: [
        //   {required: true, message: "应用秘钥不能为空", trigger: "blur"}
        // ],
        mchId: [
          {required: true, message: "商户号不能为空", trigger: "blur"}
        ],
        serialNumber: [
          {required: true, message: "证书序列号不能为空", trigger: "blur"}
        ],
        apiKey: [
          {required: true, message: "API_V3_KEY 不能为空", trigger: "blur"}
        ],
        privateUrl: [
          {required: true, message: "请选择商户私钥文件", trigger: "blur"}
        ],
      },

    }
  },
  created() {
    this.getCurrentList();
    this.getWxList();
  },
  methods: {
    getCurrentList() {
      getCurrentList().then(res => {
        this.currentList = res.data
      })
    },

    getWxList() {
        wechatList(this.queryParams).then(res => {
          this.wechatList = res.rows;
          this.total = res.total;
          this.loading = false;
        })
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getWxList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },

    //新增
    handleWxAdd() {
      this.isDisabled = false;
      this.wxTitle = "微信账号添加";
      this.wxOpen = true;
    },

    handleWxUpdate(row) {
      this.isDisabled = true;
      this.wxForm = row;
      this.privateKeyName = this.wxForm.privateUrl;
      this.wxTitle = "微信账号修改";
      this.wxOpen = true;
    },

    uploadPrivateFile(file, fileList) {
        let formData = new FormData();
        formData.append('file', file.raw)
        uploadPrivateKey(formData).then(response => {
            this.privateKeyName = file.name;
            this.wxForm.privateUrl = response.fileUrl;
            this.$modal.msgSuccess("上传成功");
        });
    },
    beforeUpload(file) {
      return false;
    },

    submitWxPay() {
      this.$refs["wxForm"].validate(valid => {
        if (valid) {
          if (this.wxForm.wechatId == null) {
              addWechat(this.wxForm).then(res => {
                  this.$modal.msgSuccess("添加成功")
                  this.getWxList();
                  this.reset();
              })
          } else {
              updateWechat(this.wxForm).then(res => {
                  this.$modal.msgSuccess("修改成功")
                  this.getWxList();
                  this.reset();
              })
          }
        }
      });

    },

    handleWxDel(row) {
      this.$modal.confirm('是否确认删除此微信账号？').then(function() {
        return delWechat(row.wechatId);
      }).then(() => {
        this.getWxList();
          this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },

    reset() {
      this.wxForm = {};
      this.wxOpen = false;
      this.wxTitle = "";
    },



  },
}
</script>

<style scoped>

</style>

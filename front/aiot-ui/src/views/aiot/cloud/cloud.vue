<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" class="cus-fm">
      <el-form-item label="小区名称" prop="communityId">
        <el-select v-model="queryParams.communityId" placeholder="请选择小区">
          <el-option
            v-for="item in communityList"
            :key="item.communityId"
            :label="item.communityName"
            :value="item.communityId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="设备名称" prop="accessName">
        <el-input
          v-emoji
          v-model.trim="queryParams.accessName"
          placeholder="请输入设备名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备序列号" prop="accessSerial">
        <el-input
          v-emoji
          v-model.trim="queryParams.accessSerial"
          placeholder="请输入设备序列号"
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
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
        >添加设备</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning" plain
          icon="el-icon-edit"
          :disabled="multiple"
          size="mini" @click="handleBatchQr"
        >设置设备二维码</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="danger" plain
          icon="el-icon-delete"
          :disabled="multiple"
          size="mini" @click="handleDelete"
        >删除</el-button>
      </el-col>
      <right-toolbar  @queryTable="getAccessList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="accessList"  width="60px" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="门禁名称" prop="accessName" :show-overflow-tooltip="true"/>
      <el-table-column label="设备序列号" prop="accessSerial" :show-overflow-tooltip="true"/>
      <el-table-column label="设备IP" prop="accessIp" :show-overflow-tooltip="true"/>
      <el-table-column label="读头A门禁点" prop="doorInName" :show-overflow-tooltip="true"/>
      <el-table-column label="读头B门禁点" prop="doorOutName" :show-overflow-tooltip="true"/>
      <el-table-column label="修改时间" prop="updateTime" :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" width="150px" class-name="small-padding fixed-width" >
        <template slot-scope="scope">
          <el-button
            size="mini" type="text" icon="el-icon-view"
            class="text-info"
            @click="handleView(scope.row)"
          >查看</el-button>
          <el-button
            size="mini" type="text" icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getAccessList"/>
    <!--  查看  -->
    <el-dialog :title="title" :visible.sync="showDetail" width="45%">
      <el-form :model="form" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="设备名称：">{{form.accessName}}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备序列号：">{{form.accessSerial}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="设备IP：">{{form.accessIp}}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备MAC地址：">{{form.accessMac}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="读头A门禁点：">{{form.doorInName}}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="读头B门禁点：">{{form.doorOutName}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="固件版本：">{{form.accessVer}}</el-form-item>
            <el-form-item label="所属小区：">{{form.communityName}}</el-form-item>
            <el-form-item label="所在位置：">{{form.installLocation}}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="设备二维码：" v-if="form.deviceQr">
              <el-image :src="form.deviceQr" style="width: 60%"/>
            </el-form-item>
            <el-form-item label="设备二维码：" v-else>未设置</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
    <!--  添加 修改 -->
    <el-dialog :title="title" :visible.sync="show" width="30%">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="所属小区" prop="communityId">
          <el-select v-model="form.communityId" placeholder="请选择小区" style="width: 100%" @change="selectDoors">
            <el-option
              v-for="item in communityList"
              :key="item.communityId"
              :label="item.communityName"
              :value="item.communityId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备名称：" prop="accessName">
          <el-input v-model="form.accessName" placeholder="请输入设备名称" />
        </el-form-item>
        <el-form-item label="设备序列号：" prop="accessSerial">
          <el-input v-model="form.accessSerial" placeholder="请输入设备序列号" />
        </el-form-item>
        <el-form-item label="设备IP：" prop="accessIp">
          <el-input v-model="form.accessIp" placeholder="请输入设备IP" />
        </el-form-item>
        <el-form-item label="设备MAC：" prop="accessMac">
          <el-input v-model="form.accessMac" placeholder="请输入设备MAC" />
        </el-form-item>
        <el-form-item label="固件版本：" prop="accessVer">
          <el-input v-model="form.accessVer" placeholder="请输入固件版本" />
        </el-form-item>
        <el-form-item label="读头A门禁点：" prop="readerIn">
          <el-select v-model="form.readerIn" placeholder="请选择门禁点" style="width: 100%">
            <el-option
              v-for="item in doorList"
              :key="item.doorId"
              :label="item.doorName"
              :value="item.doorId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="读头B门禁点：" prop="readerOut">
          <el-select v-model="form.readerOut" placeholder="请选择门禁点" style="width: 100%">
            <el-option
              v-for="item in doorList"
              :key="item.doorId"
              :label="item.doorName"
              :value="item.doorId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所在位置：" prop="installLocation">
          <el-input v-model="form.installLocation" placeholder="请输入所在位置" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getCurrentList } from "@/api/aiot/community"
import {
  selectCloudList, insertCloud, selectCloudById, updateCloud,
  batchUpdateDeviceQr, removeCloud, selectUnbindDoors
} from "@/api/aiot/cloud"
import { isEmpty } from "@/utils/common"
export default {
  name: "CloudAccess",
  data() {
    return {
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        accessName: undefined,
        accessSerial: undefined
      },
      //小区列表
      communityList: [],
      communityOptions: [],
      // 遮罩层
      loading: true,
      // 门禁列表
      accessList: [],
      // 门禁点列表
      doorList: [],
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      //id
      ids: [],
      form: {},
      showDetail: false,
      title:'',
      show: false,
      // 表单校验
      rules: {
        communityId: [
          { required: true, message: "请选择小区", trigger: "blur" }
        ],
        accessName: [
          { required: true, message: "请输入设备名称", trigger: "blur" }
        ],
        accessSerial: [
          { required: true, message: "请输入设备序列号", trigger: "blur" }
        ],
        accessIp: [
          { required: true, message: "请输入设备IP", trigger: "blur" }
        ],
        readerIn: [
          { required: true, message: "请选择读头A门禁点", trigger: "blur" }
        ],
        readerOut: [
          { required: true, message: "请选择读头B门禁点", trigger: "blur" }
        ],
        installLocation: [
          { required: true, message: "请输入所在位置", trigger: "blur" }
        ],
        accessMac: [
          { required: true, message: "请输入设备MAC", trigger: "blur" }
        ],
        accessVer: [
          { required: true, message: "请输入固件版本", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.listCommunity();
  },
  methods: {
    // 获取小区数据
    listCommunity() {
      getCurrentList().then(response => {
        this.communityOptions = response.data;
        this.communityList = response.data;
        if (this.communityOptions.length > 0) {
          this.queryParams.communityId = this.communityOptions[0].communityId
          this.getAccessList();
        }
      });
    },
    /** 查询列表 */
    getAccessList() {
      this.loading = true;
      selectCloudList(this.queryParams).then(response => {
          this.accessList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 10;
      this.getAccessList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 10;
      this.resetForm("queryForm");
      this.getAccessList();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.accessId)
      this.multiple = !selection.length
    },
    // 设备添加
    handleAdd() {
      this.show = true;
      this.title = "设备添加";
    },
    selectDoors(){
      let data = {
        communityId: this.form.communityId,
        accessId: null
      }
      this.getDoorList(data);
    },

    /**查看*/
    handleView(row) {
      const accessId = row.accessId
      selectCloudById(accessId).then(response => {
        this.form = response.data;
        this.title = "门禁详情";
        this.showDetail = true;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const accessId = row.accessId
      let data = {
        communityId: row.communityId,
        accessId: accessId,
      }
      this.getDoorList(data);
      selectCloudById(accessId).then(response => {
        this.form = response.data;
        this.showDetail = false;
        this.title = "修改门禁";
        this.show = true;
      });
    },
    getDoorList(data){
      selectUnbindDoors(data).then(response => {
        this.doorList = response.data;
      });
    },
    /** 删除操作 */
    handleDelete() {
      let ids = this.ids;
      this.$modal.confirm('是否删除所选数据').then(function() {
        return removeCloud(ids);
      }).then(() => {
        this.$modal.msgSuccess("删除成功");
        this.getAccessList();
      }).catch(() => {});
    },

    // 表单重置
    reset() {
      this.form = {};
      this.resetForm("form");
    },
    // 取消按钮
    cancel() {
      this.show = false;
      this.showDetail =false;
      this.reset();
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.queryParams.communityId = this.form.communityId;
          if(isEmpty(this.form.accessId)){
            insertCloud(this.form).then(response => {
              this.$modal.msgSuccess("添加成功");
              this.show = false;
              this.getAccessList();
            });
          }else {
            updateCloud(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.show = false;
              this.getAccessList();
            });
          }
        }
      });
    },
    // 设置设备二维码
    handleBatchQr(){
      batchUpdateDeviceQr(this.ids).then(response => {
        this.$modal.msgSuccess(response.msg);
        this.getAccessList();
      });
    },
  }
};
</script>

<style scoped>

</style>

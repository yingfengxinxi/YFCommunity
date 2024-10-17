<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" class="cus-fm">
      <el-form-item label="小区名称">
        <el-select v-model="queryParams.communityId" placeholder="请选择小区">
          <el-option
            v-for="item in communityList"
            :key="item.communityId"
            :label="item.communityName"
            :value="item.communityId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="门禁点名称" prop="doorName">
        <el-input
          v-emoji
          v-model.trim="queryParams.doorName"
          placeholder="请输入门禁点名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="门禁点状态" prop="online">
        <el-select v-model="queryParams.online" placeholder="请选择门禁点状态">
          <el-option
            v-for="item in dict.type.door_status"
            :key="item.value"
            :label="item.label"
            :value="item.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="设备唯一标识" prop="indexCode">
        <el-input
          v-emoji
          v-model.trim="queryParams.indexCode"
          placeholder="请输入设备唯一标识"
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
          icon="el-icon-refresh"
          size="mini"
          @click="syncDoor"
          :disabled="!isSync"
        >同步设备信息</el-button>
      </el-col>
     <el-col :span="1.5">
        <el-button
          type="primary" plain
          icon="el-icon-finished"
          :disabled="multiple"
          size="mini" @click="checkDoorOnline"
        >更新设备状态</el-button>
      </el-col>
      <right-toolbar  @queryTable="getDoorList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="doorList"   @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column align="center" label="门禁点名称" prop="doorName" />
      <el-table-column align="center" label="唯一标识" prop="indexCode"/>
     <el-table-column  align="center" label="设备状态" prop="online" :formatter="onlineFormat"/>
      <el-table-column align="center" label="数据同步时间" prop="renewalTime" />
      <el-table-column align="center" label="操作" width="150px" class-name="small-padding fixed-width" >
        <template slot-scope="scope">
          <el-button
            size="mini" type="text" icon="el-icon-view"
            class="text-info"
            @click="handleView(scope.row)"
          >查看</el-button>
          <br>
         <el-button
            size="mini" type="text" icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
          <br>
          <el-button
            size="mini" type="text" icon="el-icon-view"
            class="text-info"
            @click="handleCxView(scope.row)"
          >查看出行数据</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getDoorList"/>
    <!--  查看  -->
    <el-dialog :title="title" :visible.sync="showDetail" width="80%">
      <el-form :model="form" label-width="120px">
        <el-row>
          <el-col :span="12">
            <el-form-item label="所属小区：">{{form.communityName}}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="所在位置：">{{form.address}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="资源唯一编码：">{{form.indexCode}}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="资源名称：">{{form.doorName}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="门禁点状态：">
             {{ onlineFormat(form) }}
            </el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="门序号：">{{form.doorSerial}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="通道类型：">{{form.channelType}}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="通道号：">{{form.channelNo}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="一级控制器：">{{form.controlOneId}}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="二级控制器：">{{form.controlTwoId}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="读卡器1：">{{form.readerInId}}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="读卡器2：">{{form.readerOutId}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="12">
            <el-form-item label="数据同步人：">{{form.renewalBy}}</el-form-item>
          </el-col>
          <el-col :span="12">
            <el-form-item label="数据同步时间：">{{form.renewalTime}}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
    <!--  修改  -->
    <el-dialog :title="title" :visible.sync="updateShow" width="30%">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="资源唯一编码：">{{form.indexCode}}</el-form-item>
        <el-form-item label="资源名称：">{{form.doorName}}</el-form-item>
        <el-form-item label="所属小区：">{{form.communityName}}</el-form-item>
        <el-form-item label="所在位置：" prop="address">
          <el-input v-model="form.address" placeholder="请输入所在位置" />
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
import { selectDoorList,syncDoorData,checkOnline,selectDoorById,updateDoor } from "@/api/aiot/door"
import { isEmpty } from "@/utils/common"
export default {
  name: "Door",
  dicts:['door_status'],
  data() {
    return {
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        doorName: undefined,
        indexCode: undefined,
        online: undefined
      },
      isSync:true,
      //小区列表
      communityList: [],
      // 遮罩层
      loading: true,
      //小区列表
      doorList: [],
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      //id
      ids: [],
      indexCodeList: [],
      form: {},
      showDetail: false,
      title:'',
      updateShow: false,
      // 表单校验
      rules: {
        address: [
          { required: true, message: "请输入所在位置", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.listCommunity();
  },
  methods: {
    onlineFormat(row){
      return this.selectDictLabel(this.dict.type.door_status,row.online);
    },
    handleCxView(row) {
      let query = {
        indexCode: row.indexCode,
      };
      this.$router.replace({ path: '/access/doorEvent', query })
    },
    handleFkView(row) {
      let query = {
        indexCode: row.indexCode,
      };
      this.$router.replace({ path: '/access/visitingSecord', query })
    },
    // 获取小区数据
    listCommunity() {
      getCurrentList().then(response => {
        this.communityList = response.data;
        if (this.communityList.length > 0) {
          this.queryParams.communityId = this.communityList[0].communityId
          this.getDoorList();
        }
      });
    },
    /** 查询列表 */
    getDoorList() {
      this.loading = true;
      selectDoorList(this.queryParams).then(response => {
          this.doorList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 10;
      this.getDoorList();
    },
    /** 重置按钮操作 */
    resetQuery() {
     var communityId= this.queryParams.communityId;
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        communityId: communityId,
        doorName: undefined,
        indexCode: undefined,
        online: undefined
      };
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /**时间转换*/
    momentFormat(time) {
      if (isEmpty(time)) {
        return "";
      }
      return this.$moment(time).format("YYYY-MM-DD HH:mm:ss");
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.doorId)
      this.indexCodeList = selection.map(item => item.indexCode)
      this.multiple = !selection.length
    },
    // 设备同步
    syncDoor() {
      this.isSync = false;
      syncDoorData(this.queryParams.communityId).then(response => {
        if (response.code == 200) {
          this.$modal.msgSuccess(response.msg);
          this.isSync = true;
          this.getDoorList();
        } else { 
          this.isSync = true;
          this.$modal.msgError(response.msg);
          this.getDoorList();
        }
      })
    },
    // 更新设备状态
    checkDoorOnline(){
      let data = {
        communityId: this.queryParams.communityId,
        indexCodeList: this.indexCodeList
      }
      checkOnline(data).then(response => {
        this.$modal.msgSuccess(response.msg);
        this.getDoorList();
      })
    },

    /**查看*/
    handleView(row) {
      const doorId = row.doorId
      selectDoorById(doorId).then(response => {
        this.form = response.data;
        this.title = "门禁点详情";
        this.showDetail = true;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const doorId = row.doorId
      selectDoorById(doorId).then(response => {
        this.form = response.data;
        this.showDetail = false;
        this.title = "修改门禁点所在位置";
        this.updateShow = true;
      });
    },
    // 表单重置
    reset() {
      this.resetForm("form");
    },
    // 取消按钮
    cancel() {
      this.showDetail =false;
      this.updateShow =false;
      this.reset();
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateDoor(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.updateShow = false;
            this.getDoorList();
          });
        }
      });
    }
  }
};
</script>

<style scoped>

</style>

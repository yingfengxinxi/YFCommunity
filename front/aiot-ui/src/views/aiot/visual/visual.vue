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
      <el-form-item label="设备名称" prop="visualName">
        <el-input
          v-emoji
          v-model.trim="queryParams.visualName"
          placeholder="请输入设备名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="设备状态" prop="online">
        <el-select v-model="queryParams.online" placeholder="请选择门禁点状态">
          <el-option label="未检测" value="-1"> </el-option>
          <el-option label="离线" value="0"> </el-option>
          <el-option label="在线" value="1"> </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="资源类型" prop="resourceType">
        <el-select v-model="queryParams.resourceType" placeholder="请选择资源类型">
          <el-option
            v-for="item in resourceTypeOptions"
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
          :disabled="!isSync"
          @click="syncVisual"
        >同步设备信息</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary" plain
          icon="el-icon-finished"
          :disabled="multiple"
          size="mini" @click="checkAccessOnline"
        >更新设备状态</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="primary" plain
          icon="el-icon-menu"
          :disabled="multiple"
          size="mini" @click="handleBatchQr"
        >设置设备二维码</el-button>
      </el-col> -->
      <!-- <el-col :span="1.5">
        <el-button
          type="warning" plain
          icon="el-icon-s-promotion"
          size="mini" @click="handleSubscribe"
        >设备事件订阅</el-button>
      </el-col> -->
      <!-- <el-col :span="1.5">
        <el-button
          type="warning" plain
          icon="el-icon-s-promotion"
          size="mini" @click="handleUnsubscribe"
        >设备事件取消订阅</el-button>
      </el-col> -->
      <right-toolbar  @queryTable="getVisualList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="visualList"  width="60px" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="设备名称" align="center" prop="visualName" />
      <el-table-column label="唯一标识" align="center" prop="indexCode" :show-overflow-tooltip="true"/>
      <el-table-column label="设备状态" align="center" prop="online">
        <template slot-scope="scope">
          <span v-if="scope.row.online === 0"><el-tag type="danger">离线</el-tag></span>
          <span v-else-if="scope.row.online === 1"><el-tag type="success">在线</el-tag></span>
          <span v-else><el-tag type="info">未检测</el-tag></span>
        </template>
      </el-table-column>
      <el-table-column label="资源类型" align="center" prop="resourceType" :formatter="resourceFormatter"/>
      <el-table-column label="所在楼栋" align="center" prop="buildingName" />
      <el-table-column label="所在单元" align="center" prop="unitName" />
      <el-table-column label="数据同步时间" align="center" prop="renewalTime" :show-overflow-tooltip="true"/>
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
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getVisualList"/>
    <!--  查看  -->
    <el-dialog :title="title" :visible.sync="showDetail" width="80%">
      <el-form :model="form" label-width="120px">
        
        <el-row>
          <el-col :span="20">
            <el-form-item label="资源唯一编码：">{{form.indexCode}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="资源名称：">{{form.visualName}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="设备状态：">
              <span v-if="form.online === 0"><el-tag type="danger">离线</el-tag></span>
              <span v-else-if="form.online === 1"><el-tag type="success">在线</el-tag></span>
              <span v-else><el-tag type="info">未检测</el-tag></span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="资源类型：">{{resourceFormatter(form)}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="设备长号：">{{form.longNum}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="设备驱动：">{{form.deviceKey}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="设备系列：">{{form.deviceType}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="接入协议：">{{form.treatyType}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="所属网域：">{{form.netZoneId}}</el-form-item>
          </el-col>
       
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="设备卡容量：">{{form.cardCapacity}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="指纹容量：">{{form.fingerCapacity}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="人脸容量：">{{form.faceCapacity}}</el-form-item>
          </el-col>
        </el-row>
        
        <el-row>
          <el-col :span="16">
            <el-row>
              <el-col :span="12">
                <el-form-item label="数据同步人：">{{form.renewalBy}}</el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="数据同步时间：">{{form.renewalTime}}</el-form-item>
              </el-col>
            </el-row>
            
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
        <el-form-item label="资源名称：">{{form.visualName}}</el-form-item>
        <el-form-item label="所属小区：">{{form.communityName}}</el-form-item>
        <el-form-item label="所在楼栋：" prop="buildingId">
          <el-select v-model="form.buildingId" placeholder="请选择所在楼栋" style="width: 100%" @change="getUnits">
            <el-option
              v-for="item in buildingList"
              :key="item.buildingId"
              :label="item.buildingName"
              :value="item.buildingId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所在单元：" prop="unitId">
          <el-select v-model="form.unitId" placeholder="请选择所在单元" style="width: 100%">
            <el-option
              v-for="item in unitList"
              :key="item.unitId"
              :label="item.unitName"
              :value="item.unitId">
            </el-option>
          </el-select>
        </el-form-item>
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
import { getCurrentList,getBuildingList,getUnitList } from "@/api/aiot/community"
import {
  selectVisualList, syncVisualData, checkOnline, selectVisualById,
  updateVisual, batchUpdateDeviceQr, subscribeLock, unsubscribeLock
} from "@/api/aiot/visual"
import { isEmpty,equals } from "@/utils/common"
export default {
  name: "Visual",
  data() {
    return {
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        visualName: undefined,
        indexCode: undefined,
        resourceType: undefined,
        online: undefined,
      },
      isSync:true,
      //小区列表
      communityList: [],
      // 楼栋数据
      buildingList: [],
      // 单元数据
      unitList: [],
      // 遮罩层
      loading: true,
      // 设备列表
      visualList: [],
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      //id
      ids: [],
      resourceTypeOptions: [
        {"value":"visDeviceInDoor","label":"可视对讲室内机"},
        { "value": "visDeviceOutDoor", "label":"可视对讲门口机"},
        { "value": "visDeviceWallDoor", "label":"可视对讲围墙机"},
        {"value":"visDeviceManager","label":"可视对讲管理机"},
      ],
      indexCodeList: [],
      form: {},
      showDetail: false,
      title:'',
      updateShow: false,
      // 表单校验
      rules: {
        buildingId: [
          { required: true, message: "请选择所在楼栋", trigger: "blur" }
        ],
        unitId: [
          { required: true, message: "请选择所在单元", trigger: "blur" }
        ],
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
    // 获取小区数据
    listCommunity() {
      getCurrentList().then(response => {
        this.communityList = response.data;
        if (this.communityList.length > 0) {
          this.queryParams.communityId = this.communityList[0].communityId
          this.getVisualList();
        }
      });
    },

    /** 查询列表 */
    getVisualList() {
      this.loading = true;
      selectVisualList(this.queryParams).then(response => {
          this.visualList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    resourceFormatter(row){
      if(isEmpty(row.resourceType)){
        return "未知"
      }
      var resourceTypeName="";
      var resourceTypeList = row.resourceType.split(",");
      resourceTypeList.forEach(resourceType => {
        console.log("resourceType>>>", resourceType);
        var obj = this.resourceTypeOptions.filter(item => item.value == resourceType)[0];  
        if (obj != null) {
          resourceTypeName = obj.label + ",";
        }
      });
      
      if (resourceTypeName) { 
        return resourceTypeName.substring(0, resourceTypeName.length - 1);
      }
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 10;
      this.getVisualList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      // this.queryParams = {};
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
      this.ids = selection.map(item => item.visualId)
      this.indexCodeList = selection.map(item => item.indexCode)
      this.multiple = !selection.length
    },
    // 设备同步
    syncVisual() {
      this.isSync = false;
      syncVisualData(this.queryParams.communityId).then(response => {
        if (response.code == 200) {
          this.$modal.msgSuccess(response.msg);
          this.isSync = true;
          this.getVisualList();
        } else { 
          this.$modal.msgError(response.msg);
          this.isSync = true;
          this.getVisualList();
        }
      })
    },
    // 更新设备状态
    checkAccessOnline(){
      let data = {
        communityId: this.queryParams.communityId,
        indexCodeList: this.indexCodeList
      }
      checkOnline(data).then(response => {
        this.$modal.msgSuccess(response.msg);
        this.getVisualList();
      })
    },

    /**查看*/
    handleView(row) {
      const visualId = row.visualId
      selectVisualById(visualId).then(response => {
        this.form = response.data;
        this.title = "设备详情";
        this.showDetail = true;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const visualId = row.visualId
      this.getBuildings();
      selectVisualById(visualId).then(response => {
        this.form = response.data;
        if(equals(this.form.buildingId,0)){
          this.form.buildingId = undefined;
        }
        if(equals(this.form.unitId,0)){
          this.form.unitId = undefined;
        }else {
          getUnitList(this.form.buildingId).then(response => {
            this.unitList = response.data;
          });
        }
        this.showDetail = false;
        this.title = "修改设备";
        this.updateShow = true;
      });
    },
    getBuildings(){
      getBuildingList(this.queryParams.communityId).then(response => {
        this.buildingList = response.data;
      });
    },
    // 查询单元
    getUnits(){
      this.form.unitId = undefined;
      getUnitList(this.form.buildingId).then(response => {
        this.unitList = response.data;
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
          updateVisual(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.updateShow = false;
            this.getVisualList();
          });
        }
      });
    },
    // 设置设备二维码
    handleBatchQr(){
      batchUpdateDeviceQr(this.ids).then(response => {
        this.$modal.msgSuccess(response.msg);
        this.getVisualList();
      });
    },
    // 设备事件订阅
    handleSubscribe(){
      let communityId = this.queryParams.communityId;
      if(isEmpty(communityId)){
        this.$modal.msgWarning("请选择要订阅事件的小区！！！");
        return
      }
      this.$modal.confirm('是否订阅该小区“开锁事件”？"').then(function() {
        return subscribeLock(communityId);
      }).then(() => {
        this.$modal.msgSuccess("订阅成功");
      }).catch(() => {});
    },
    // 设备事件取消订阅
    handleUnsubscribe(){
      let communityId = this.queryParams.communityId;
      if(isEmpty(communityId)){
        this.$modal.msgWarning("请选择要取消订阅的小区！！！");
        return
      }
      this.$modal.confirm('是否订阅该小区“开锁事件”？"').then(function() {
        return unsubscribeLock(communityId);
      }).then(() => {
        this.$modal.msgSuccess("取消成功");
      }).catch(() => {});
    },
  }
};
</script>

<style scoped>

</style>

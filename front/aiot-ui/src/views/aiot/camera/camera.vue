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
      <el-form-item label="监控点名称" prop="cameraName">
        <el-input
          v-emoji
          v-model.trim="queryParams.cameraName"
          placeholder="请输入监控点名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="监控点状态">
        <el-select v-model="queryParams.online" placeholder="请选择监控点状态">
          <el-option label="未检测" value="-1"> </el-option>
          <el-option label="离线" value="0"> </el-option>
          <el-option label="在线" value="1"> </el-option>
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
          @click="syncCamera"
        >同步设备信息</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary" plain
          icon="el-icon-finished"
          :disabled="multiple"
          size="mini" @click="checkCameraOnline"
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
      <right-toolbar  @queryTable="getCameraList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="cameraList"  width="60px" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="监控点名称" prop="cameraName" :show-overflow-tooltip="true"/>
      <el-table-column label="唯一标识" prop="indexCode" :show-overflow-tooltip="true"/>
      <el-table-column label="监控点类型" prop="cameraType">
        <template slot-scope="scope">
          <span>{{ cameraTypeFormat(scope.row.cameraType) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="设备状态" prop="online">
        <template slot-scope="scope">
          <span v-if="scope.row.online === 0"><el-tag type="danger">离线</el-tag></span>
          <span v-else-if="scope.row.online === 1"><el-tag type="success">在线</el-tag></span>
          <span v-else><el-tag type="info">未检测</el-tag></span>
        </template>
      </el-table-column>
      <el-table-column label="设备类型" prop="deviceType">
        <template slot-scope="scope">
          <span>{{ deviceTypeFormat(scope.row.deviceType) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="所在楼栋" prop="buildingName" :show-overflow-tooltip="true"/>
      <el-table-column label="所在单元" prop="unitName" :show-overflow-tooltip="true"/>
      <el-table-column label="数据同步时间" prop="renewalTime" :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" width="150px" class-name="small-padding fixed-width" >
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
            size="mini" type="text" icon="el-icon-video-camera"
            class="text-warning"
            @click="handlePlay(scope.row)"
     
          >预览</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getCameraList"/>
    <!--  查看  -->
    <el-dialog :title="title" :visible.sync="showDetail" width="80%">
      <el-form :model="form" label-width="120px">
        <el-row>
              <el-col :span="10">
                <el-form-item label="所属小区：">{{form.communityName}}</el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="所在楼栋：">{{form.buildingName}}</el-form-item>
              </el-col>
              <el-col :span="6">
                <el-form-item label="所在单元：">{{form.unitName}}</el-form-item>
              </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="资源唯一编码：">{{form.indexCode}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="资源名称：">{{form.cameraName}}</el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="设备状态：">
              <span v-if="form.online === 0"><el-tag type="danger">离线</el-tag></span>
              <span v-else-if="form.online === 1"><el-tag type="success">在线</el-tag></span>
              <span v-else><el-tag type="info">未检测</el-tag></span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="监控点类型：">{{cameraTypeFormat(form.cameraIp)}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="设备类型：">{{ deviceTypeFormat(form.deviceType) }}</el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="通道号：">{{form.chanNum}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="通道子类型：">{{form.channelType}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="解码模式：">{{form.decodeTag}}</el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="传输协议：">
              <span v-if="form.transType === 0">UDP</span>
              <span v-else-if="form.transType === 1">TCP</span>
              <span v-else>未知</span>
            </el-form-item>
          </el-col>
        </el-row>
        
        <el-row>
          <el-col :span="10">
            <el-form-item label="接入协议：">{{form.treatyType}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="所属DAC编号：">{{form.dacIndexCode}}</el-form-item>
          </el-col>
          <el-col :span="6">
                <el-form-item label="所在位置：">{{form.address}}</el-form-item>
          </el-col>
          </el-row>
          <el-row>
              <el-col :span="10">
                <el-form-item label="数据同步人：">{{form.renewalBy}}</el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="数据同步时间：">{{form.renewalTime}}</el-form-item>
              </el-col>
            </el-row>
            <!-- <el-row>
              <el-col :span="8">
                <el-form-item label="设备二维码：" v-if="form.deviceQr">
                  <el-image :src="form.deviceQr"/>
                </el-form-item>
                <el-form-item label="设备二维码：" v-else>未设置</el-form-item>
              </el-col>
            </el-row> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
    <!--  修改  -->
    <el-dialog :title="title" :visible.sync="updateShow" width="30%">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="资源唯一编码：">{{form.indexCode}}</el-form-item>
        <el-form-item label="资源名称：">{{form.cameraName}}</el-form-item>
        <el-form-item label="所属小区：">{{form.communityName}}</el-form-item>
        <el-form-item label="所在楼栋：">
          <el-select v-model="form.buildingId" placeholder="请选择所在楼栋" style="width: 100%" @change="getUnits">
            <el-option
              v-for="item in buildingList"
              :key="item.buildingId"
              :label="item.buildingName"
              :value="item.buildingId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所在单元：">
          <el-select v-model="form.unitId" placeholder="请选择所在单元" style="width: 100%">
            <el-option
              v-for="item in unitList"
              :key="item.unitId"
              :label="item.unitName"
              :value="item.unitId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备类型：" prop="deviceType">
          <el-select v-model="form.deviceType" placeholder="请选择设备类型" style="width: 100%">
            <el-option
              v-for="dict in deviceTypeOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            ></el-option>
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
    <el-dialog id="playDialog" title="监控预览" :visible.sync="playShow" width="50%" append-to-body :show-close="false">
      <CameraPreview ref="camera" :playURL="playURL" :width="cameraWidth" :height="cameraHeight"/>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeView">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import CameraPreview from "@/components/CameraPreview/index"
import { getCurrentList,getBuildingList,getUnitList } from "@/api/aiot/community"
import {
  selectCameraList,syncCameraData,checkOnline,selectCameraById,
  updateCamera,batchUpdateDeviceQr,cameraUrl
} from "@/api/aiot/camera"
import { isEmpty,equals } from "@/utils/common"
export default {
  name: "Camera",
  dicts: ['sys_camera_type'],
  components: { CameraPreview },
  data() {
    return {
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        cameraName: undefined,
        indexCode: undefined,
        online: undefined
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
      // 监控点列表
      cameraList: [],
      // 监控点点列表
      doorList: [],
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      //id
      ids: [],
      indexCodeList: [],
      deviceTypeOptions: [],
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
        deviceType: [
          { required: true, message: "请选择设备类型", trigger: "blur" }
        ],
      },
      playShow: false,
      playURL: "",
      cameraWidth: "880px",
      cameraHeight: "580px",
    }
  },
  created() {
    this.getDicts("sys_camera_type").then(response => {
      this.deviceTypeOptions = response.data;
    });
    this.listCommunity();
  },
  methods: {
    cameraTypeFormat(cameraType){
      if(equals(cameraType, 0)){
        return "枪机"
      }
      if(equals(cameraType, 1)){
        return "半球"
      }
      if(equals(cameraType, 2)){
        return "快球"
      }
      if(equals(cameraType, 3)){
        return "带云台枪机"
      }
      return "未知";
    },
    deviceTypeFormat(deviceType){
      return this.selectDictLabel(this.dict.type.sys_camera_type,deviceType);
    },
    // 获取小区数据
    listCommunity() {
      getCurrentList().then(response => {
        this.communityList = response.data;
        if (this.communityList.length > 0) {
          this.queryParams.communityId = this.communityList[0].communityId
          this.getCameraList();
        }
      });
    },
    /** 查询列表 */
    getCameraList() {
      this.loading = true;
      selectCameraList(this.queryParams).then(response => {
          this.cameraList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 10;
      this.getCameraList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      var communityId=this.queryParams.communityId;
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        communityId: communityId,
        cameraName: undefined,
        indexCode: undefined,
        online: undefined
      };
      this.resetForm("queryForm");
      this.getCameraList();
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
      this.ids = selection.map(item => item.cameraId)
      this.indexCodeList = selection.map(item => item.indexCode)
      this.multiple = !selection.length
    },
    // 设备同步
    syncCamera() {
      this.isSync = false;
      syncCameraData(this.queryParams.communityId).then(response => {
        if (response.code=200) {
          this.isSync = true;
          this.$modal.msgSuccess(response.msg);
          this.getCameraList();
        } else { 
          this.isSync = true;
          this.$modal.msgError(response.msg);
          this.getCameraList();
        }
      })
    },
    // 更新设备状态
    checkCameraOnline(){
      console.log(this.queryParams.communityId)
      let data = {
        communityId: this.queryParams.communityId,
        indexCodeList: this.indexCodeList
      }
      checkOnline(data).then(response => {
        this.$modal.msgSuccess(response.msg);
        this.getCameraList();
      })
    },

    /**查看*/
    handleView(row) {
      const cameraId = row.cameraId
      selectCameraById(cameraId).then(response => {
        this.form = response.data;
        this.title = "监控点详情";
        this.showDetail = true;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.unitList = [];
      const cameraId = row.cameraId
      this.getBuildings();
      selectCameraById(cameraId).then(response => {
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
        this.title = "修改监控点";
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
          updateCamera(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.updateShow = false;
            this.getCameraList();
          });
        }
      });
    },
    // 设置设备二维码
    handleBatchQr(){
      batchUpdateDeviceQr({ids:this.ids}).then(response => {
        this.$modal.msgSuccess(response.msg);
        this.getCameraList();
      });
    },
    // 预览
    handlePlay(row){
      let timeKey = "timeKey" + row.indexCode;
      let urlKey = "urlKey" + row.indexCode
      let diff = 5 * 60;
      let nowTime = Math.ceil(Date.now() / 1000)
      let cacheTime = localStorage.getItem(timeKey);
      if(isEmpty(cacheTime)){
        cacheTime = 0;
      }
      console.log(cacheTime, nowTime)
      if(cacheTime >0 && nowTime - diff < cacheTime) {
        this.playURL = localStorage.getItem(urlKey);
        console.log("缓存内容未过期",this.playURL);
        this.play();
      } else {
        cameraUrl(row).then(response => {
          this.playURL = response.data;
          console.log("缓存内容过期",this.playURL);
          localStorage.setItem(urlKey,this.playURL);
          let now = Math.ceil(Date.now() / 1000)
          localStorage.setItem(timeKey,now);
          this.play();
        });
      }

    },
    play(){
      let that = this;
      this.playShow = true;
      this.$nextTick(() =>{
        let playWindow = document.getElementById("play_window");
        let clientWidth = playWindow.parentNode.clientWidth;
        let clientHeight = playWindow.parentNode.clientHeight;
        that.cameraWidth = Math.ceil(clientWidth * 880 / 920) + "px";
        that.clientHeight = Math.ceil(clientHeight * 580 / 620) + "px";
        this.$refs.camera.preview();
      })
    },
    closeView() {
      this.$nextTick(() =>{
        this.$refs.camera.stopPlay();
        this.playShow = false;
      })
    },

  }
};
</script>

<style scoped>

</style>

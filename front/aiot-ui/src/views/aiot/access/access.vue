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
      <el-form-item label="门禁名称" prop="accessName">
        <el-input
          v-emoji
          v-model.trim="queryParams.accessName"
          placeholder="请输入门禁名称"
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
          @click="syncAccess"
          :disabled="!isSync"
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
          icon="el-icon-edit"
          :disabled="multiple"
          size="mini" @click="handleBatchUpdate"
         
        >修改设备</el-button>
      </el-col> -->
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
         
        >门禁事件订阅</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning" plain
          icon="el-icon-s-promotion"
          size="mini" @click="handleUnsubscribe"
     
        >门禁事件取消订阅</el-button>
      </el-col> -->
      <right-toolbar  @queryTable="getAccessList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="accessList"  width="60px" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="门禁名称" prop="accessName" :show-overflow-tooltip="true"/>
      <el-table-column label="唯一标识" prop="indexCode" :show-overflow-tooltip="true"/>
      <el-table-column label="设备状态" prop="online">
        <template slot-scope="scope">
          <span v-if="scope.row.online === 0"><el-tag type="danger">离线</el-tag></span>
          <span v-else-if="scope.row.online === 1"><el-tag type="success">在线</el-tag></span>
          <span v-else><el-tag type="info">未检测</el-tag></span>
        </template>
      </el-table-column>
      <el-table-column label="进出类型" prop="inoutType">
        <template slot-scope="scope">
          <span v-if="scope.row.inoutType === '0'">入口</span>
          <span v-else-if="scope.row.inoutType === '1'">出口</span>
          <span v-else>未设置</span>
        </template>
      </el-table-column>
      <el-table-column label="关联的门禁点" prop="doorName" :show-overflow-tooltip="true"/>
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
          <br v-if="scope.row.doorName!=null">
          <el-button
            v-if="scope.row.doorName!=null"
            size="mini" type="text" icon="el-icon-view"
            class="text-info"
            @click="handleCxView(scope.row)"
          >查看出行数据</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getAccessList"/>
    <!--  查看  -->
    <el-dialog :title="title" :visible.sync="showDetail" width="80%">
      <el-form :model="form" label-width="120px">
        <el-row>
              <el-col :span="8">
                <el-form-item label="所属小区：">{{form.communityName}}</el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="所在楼栋：">{{form.buildingName}}</el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="所在单元：">{{form.unitName}}</el-form-item>
              </el-col>
        </el-row>
        <el-row>
          <el-col :span="20">
            <el-form-item label="所在位置：">{{form.address}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="20">
            <el-form-item label="资源唯一编码：">{{form.indexCode}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="20">
            <el-form-item label="设备序列号：">{{form.devSerialNum}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="资源名称：">{{form.accessName}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="设备状态：">
              <span v-if="form.online === 0"><el-tag type="danger">离线</el-tag></span>
              <span v-else-if="form.online === 1"><el-tag type="success">在线</el-tag></span>
              <span v-else><el-tag type="info">未检测</el-tag></span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="版本号：">{{form.dataVersion}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="设备IP：">{{form.accessIp}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="设备端口：">{{form.accessPort}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="设备类型编码：">{{form.devTypeCode}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="设备类型型号：">{{form.devTypeDesc}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="设备卡容量：">{{form.cardCapacity}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="指纹容量：">{{form.fingerCapacity}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="人脸容量：">{{form.faceCapacity}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="门容量：">{{form.doorCapacity}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="进出类型：">
                  <span v-if="form.inoutType === '0'">入口</span>
                  <span v-else-if="form.inoutType === '1'">出口</span>
                  <span v-else>未设置</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="关联的门禁点：">{{form.doorName}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="数据同步人：">{{form.renewalBy}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="数据同步时间：">{{form.renewalTime}}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
    <!--  修改  -->
    <el-dialog :title="title" :visible.sync="updateShow" width="50%">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="资源唯一编码：">{{form.indexCode}}</el-form-item>
        <el-form-item label="资源名称：">{{form.accessName}}</el-form-item>
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
        <el-form-item label="进出类型：">
          <el-select v-model="form.inoutType" placeholder="请选择进出类型" style="width: 100%">
            <el-option label="入口" value="0" selected="selected"></el-option>
            <el-option label="出口" value="1"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="门禁点：" prop="doorId">
          <el-select v-model="form.doorId" placeholder="请选择门禁点" style="width: 100%">
            <el-option
              v-for="item in doorList"
              :key="item.doorId"
              :label="item.doorName"
              :value="item.doorId">
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

    <!--  修改  -->
    <el-dialog :title="title" :visible.sync="batchShow" width="30%">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="进出类型：" prop="inoutType">
          <el-select v-model="form.inoutType" placeholder="请选择进出类型" style="width: 100%">
            <el-option label="入口" value="0" selected="selected"></el-option>
            <el-option label="出口" value="1"></el-option>
          </el-select>
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
  selectAccessList,syncAccessData,checkOnline,selectAccessById,selectUnbindDoors,
  updateAccess,batchUpdateAccess,batchUpdateDeviceQr,subscribeLegalCard,unsubscribeLegalCard
} from "@/api/aiot/access"
import { isEmpty,equals } from "@/utils/common"
export default {
  name: "Access",
  data() {
    return {
      isSync:true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        accessName: undefined,
        indexCode: undefined,
        online:undefined
      },
      //小区列表
      communityList: [],
      // 楼栋数据
      buildingList: [],
      // 单元数据
      unitList: [],
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
      indexCodeList: [],
      form: {},
      showDetail: false,
      title:'',
      updateShow: false,
      batchShow: false,
      // 表单校验
      rules: {
        inoutType: [
          { required: true, message: "请选择进出类型", trigger: "blur" }
        ],
        doorId: [
          { required: true, message: "请选择门禁点", trigger: "blur" }
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
          this.getAccessList();
        }
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
    /** 查询列表 */
    getAccessList() {
      this.loading = true;
      selectAccessList(this.queryParams).then(response => {
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
      var communityId=this.queryParams.communityId;
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        communityId: communityId,
        accessName: undefined,
        indexCode: undefined,
        online:undefined
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
      this.ids = selection.map(item => item.accessId)
      this.indexCodeList = selection.map(item => item.indexCode)
      this.multiple = !selection.length
    },
    // 设备同步
    syncAccess() {
      this.isSync = false;
      syncAccessData(this.queryParams.communityId).then(response => {
        this.$modal.msgSuccess(response.msg);
        this.isSync = true;
        this.getAccessList();
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
        this.getAccessList();
      })
    },
    handleCxView(row) {
      let query = {
        indexCode: row.indexCode,
      };
      this.$router.replace({ path: '/access/doorEvent', query })
    },
    handleFkView(row) {
      let query = {
        indexCode: row.doorIndexCode,
      };
      this.$router.replace({ path: '/access/visitingSecord', query })
    },
    
    /**查看*/
    handleView(row) {
      const accessId = row.accessId
      selectAccessById(accessId).then(response => {
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
        accessId: accessId
      }
      this.getBuildings();
      this.getDoorList(data);
      selectAccessById(accessId).then(response => {
        this.form = response.data;
        console.log(this.form)
        if(equals(this.form.buildingId, 0)){
          this.form.buildingId = undefined;
        }
        if(equals(this.form.unitId, 0)){
          this.form.unitId = undefined;
        }else {
          // 查询单元
          getUnitList(this.form.buildingId).then(response => {
            this.unitList = response.data;
          });
        }
        this.showDetail = false;
        this.title = "修改门禁";
        this.updateShow = true;
        this.batchShow = false;
      });
    },
    getDoorList(data){
      selectUnbindDoors(data).then(response => {
        this.doorList = response.data;
      });
    },
    /** 修改按钮操作 */
    handleBatchUpdate() {
      this.reset();
      this.showDetail = false;
      this.title = "修改门禁";
      this.updateShow = false;
      this.batchShow = true;
    },

    // 表单重置
    reset() {
      this.resetForm("form");
    },
    // 取消按钮
    cancel() {
      this.showDetail =false;
      this.updateShow =false;
      this.batchShow =false;
      this.reset();
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if(this.batchShow){
            this.form.ids = this.ids;
            batchUpdateAccess(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.batchShow = false;
              this.getAccessList();
            });
          }else {
            updateAccess(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.updateShow = false;
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
    // 门禁事件订阅
    handleSubscribe(){
      let communityId = this.queryParams.communityId;
      if(isEmpty(communityId)){
        this.$modal.msgWarning("请选择要订阅事件的小区！！！");
        return
      }
      this.$modal.confirm('是否订阅该小区“合法卡比对事件”？"').then(function() {
        return subscribeLegalCard(communityId);
      }).then(() => {
        this.$modal.msgSuccess("订阅成功");
      }).catch(() => {});
    },
    // 门禁事件取消订阅
    handleUnsubscribe(){
      let communityId = this.queryParams.communityId;
      if(isEmpty(communityId)){
        this.$modal.msgWarning("请选择要取消订阅的小区！！！");
        return
      }
      this.$modal.confirm('是否订阅该小区“合法卡比对事件”？"').then(function() {
        return unsubscribeLegalCard(communityId);
      }).then(() => {
        this.$modal.msgSuccess("取消成功");
      }).catch(() => {});
    },
  }
};
</script>

<style scoped>

</style>

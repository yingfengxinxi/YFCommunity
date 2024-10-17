<template>
  <div class="app-container">
    <div v-show="!open">
      <el-form :model="queryParams" ref="queryForm" v-show="show" :inline="true" class="cus-fm">
        <el-form-item label="所属小区" prop="communityId" label-width="100px">
          <el-select v-model="queryParams.communityId" placeholder="请选择小区" @change="getBuildingList(queryParams.communityId,'0')">
            <el-option
              v-for="item in communityList"
              :key="item.communityId"
              :label="item.communityName"
              :value="item.communityId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="楼栋" prop="buildingId" label-width="100px">
          <el-select v-model="queryParams.buildingId" placeholder="请选择楼栋"  @change="getUnitList(queryParams.buildingId,'0')">
            <el-option
              v-for="item in buildingData"
              :key="item.buildingId"
              :label="item.buildingName"
              :value="item.buildingId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="单元楼" prop="unitId" label-width="100px">
          <el-select v-model="queryParams.unitId" placeholder="请选择单元楼">
            <el-option
              v-for="item in unitData"
              :key="item.unitId"
              :label="item.unitName"
              :value="item.unitId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="设备名称" prop="deviceName" label-width="100px">
              <el-input
                style="width: 215px"
                v-model="queryParams.deviceName"
                placeholder="请输入访客姓名"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
        <el-form-item label="设备类型" prop="eventType" label-width="100px">
          <el-select v-model="queryParams.eventType" placeholder="请选择设备类型">
            <el-option
              v-for="item in eventTypeOptions"
              :key="item.value"
              :label="item.label"
              :value="item.value">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="人员姓名" prop="personName" label-width="100px">
              <el-input
                style="width: 215px"
                v-model="queryParams.personName"
                placeholder="请输入人员姓名"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="人员手机号" prop="phoneNo" label-width="100px">
              <el-input
                style="width: 215px"
                v-model="queryParams.phoneNo"
                placeholder="请输入人员手机号"
                clearable
                @keyup.enter.native="handleQuery"
              />
        </el-form-item>
        <el-form-item label="出入日期" label-width="90px" prop="eventTime">
          <el-date-picker
            v-model="queryParams.dateRange"
            type="daterange"
            value-format="yyyy-MM-dd"
            format="yyyy-MM-dd"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期">
          </el-date-picker>
        </el-form-item>

        <el-form-item >
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>
      <el-table v-loading="loading" :data="dataList" v-show="show" width="60px">
        
        <el-table-column align="center" label="所属小区" prop="communityName"/>
        <el-table-column align="center" label="所属楼栋" prop="buildingName">
          <template slot-scope="scope">
            <span>{{ scope.row.buildingName==null?'-':scope.row.buildingName }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="所属单元" prop="unitName">
          <template slot-scope="scope">
            <span>{{ scope.row.unitName==null?'-':scope.row.unitName }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="人员名称" prop="personName">
          <template slot-scope="scope">
            <span>{{ scope.row.personName==null?'-':scope.row.personName }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="性别" prop="gender">
          <template slot-scope="scope">
            <span>{{ scope.row.gender==null?'-':scope.row.gender=='1'?'男':'女' }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="证件类型" prop="certificateType" :formatter="getCertificateType"/>
        <el-table-column align="center" label="证件号" prop="certificateNo">
          <template slot-scope="scope">
            <span>{{ scope.row.certificateNo==null?'-':scope.row.certificateNo }}</span>
          </template>
        </el-table-column>
        <el-table-column align="center" label="设备名称" prop="deviceName"/>
        <el-table-column align="center" label="事件类型" prop="eventType" :formatter="eventTypeFormat"/>
        <el-table-column align="center" label="事件产生时间" prop="eventTime">
          <template slot-scope="scope">
            <span>{{ momentFormat(scope.row.eventTime) }}</span>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="total>0 && show"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
  </div>
</template>
<script>
import {listTalkBackInOut} from "@/api/estate/log/talkBackInOut";
import {getCurrentList,getBuildingList,getUnitList} from "@/api/estate/community"
import { equals, isEmpty } from "@/utils/common";

export default {
  name: "VisLog",
  data() {
    return {
      // 遮罩层
      loading: true,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //是否显示详情
      showDetail: false,
      //显示是否添加
      showAdd: false,
      //监控预览
      preview: false,
      //是否显示列表
      show: true,
      // 日期范围
      dateRange: [],
      //小区列表
      communityList: [],
      buildingData: [],
      unitData:[],
      //数据
      dataList: [],
      //id
      ids: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        deviceName: undefined,
        eventType: undefined,
        startTime: undefined,
        endTime: undefined,
        personName: undefined,
        phoneNo: undefined,
        buildingId: undefined,
        unitId:undefined,
        dateRange:[],
      },
      eventTypeOptions: [
        { "value": "983297", "label": "业主密码开锁 —密码开锁（87）" },
        { "value": "983298", "label": "劫持密码开锁 —劫持开锁（87）" },
        { "value": "983299", "label": "刷卡开锁" },
        { "value": "983300", "label": "室内机开锁 —户主开锁（87）" },
        { "value": "983301", "label": "中心平台开锁" },
        { "value": "983307", "label": "蓝牙开锁" },
        { "value": "983308", "label": "二维码开锁" },
        { "value": "983309", "label": "人脸开锁" },
        { "value": "983310", "label": "指纹开锁" }
      ],
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  mounted() {
    this.listCommunity();
  },
  methods: {
    getCertificateType(row) {
      var certificateType=row.certificateType;
      if (certificateType == '111') {
        return "身份证";
      } else if (certificateType == '414') {
        return "护照";
      } else if (certificateType == '113') {
        return "户口簿";
      } else if (certificateType == '335') {
        return "驾驶证";
      } else if (certificateType == '131') {
        return "工作证";
      } else if (certificateType == '133') {
        return "学生证";
      } else if (certificateType == '990') {
        return "其他";
      } else { 
        return '-'
      }
    },
    /**事件类型数据转换*/
    eventTypeFormat(row) {
      var obj=this.eventTypeOptions.filter(item => item.value == row.eventType)[0];
      if (obj != null) { 
        return obj.label;
      }
    },
    /** 查询账号列表 */
    getList() {
      this.loading = true;
      if(this.queryParams.dateRange){
        this.queryParams.startTime = this.queryParams.dateRange[0];
        this.queryParams.endTime = this.queryParams.dateRange[1];
      }
      listTalkBackInOut(this.queryParams).then(response => {
          this.dataList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /**获取小区*/
    listCommunity(){
      let param = {
        communityType: "1",
      }
      getCurrentList(param).then(response => {
        this.communityList = response.data;
        const {communityList}=this
        if (communityList&&communityList.length>0){

          this.queryParams.communityId = communityList[0].communityId;
          this.getBuildingList(this.queryParams.communityId,"1");
        }
        
        this.getList();
      });
    },

    getBuildingList(communityId, flag) {
      if (flag == '0') { 
        this.queryParams.buildingId = undefined;
        this.queryParams.unitId = undefined;
      } 
      getBuildingList(communityId).then((response) => {
        this.buildingData = response.data;
      });
    },
    getUnitList(buildingId, flag) {
      if (flag == '0') { 
        this.queryParams.unitId = undefined;
      }
      getUnitList(buildingId).then((response) => {
        this.unitData = response.data;
      });
    },

    // 取消按钮
    cancel() {
      this.showAdd=false;
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 10;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      var communityId=this.queryParams.communityId;
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        communityId: communityId,
        deviceName: undefined,
        eventType: undefined,
        startTime: undefined,
        endTime: undefined,
        personName: undefined,
        phoneNo: undefined,
        buildingId: undefined,
        unitId:undefined,
        dateRange:[],
      };
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /**时间转换*/
    momentFormat(time){
      if(isEmpty(time)){
        return "";
      }
      return this.$moment(time).format("YYYY-MM-DD HH:mm:ss");
    },
  }
};
</script>

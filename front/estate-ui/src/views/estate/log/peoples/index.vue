<template xmlns="http://www.w3.org/1999/html">
  <div class="app-container">
    
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
        <el-form-item label="所属小区" prop="communityId" label-width="80px">
          <el-select  v-model="queryParams.communityId" placeholder="请选择小区名称" style="width: 200px">
            <el-option
              v-for="(item, index) in deptList"
              :key="index"
              :label="item.communityName"
              :value="item.communityId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="门禁点" prop="indexCode" label-width="80px">
          <el-select  v-model="queryParams.indexCode" placeholder="请选择门禁点" style="width: 200px">
            <el-option
              v-for="(item, index) in indexCodeOptions"
              :key="index"
              :label="item.doorName"
              :value="item.indexCode"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="事件类型">
        <el-select v-model="queryParams.eventType" placeholder="请选择事件类型">
          <el-option
            v-for="dict in dict.type.event_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="出入时间" prop="eventTime">
        <el-date-picker v-model="dateRange" size="small" style="width: 240px"
                        value-format="yyyy-MM-dd" type="daterange" range-separator="-"
                        start-placeholder="开始日期" end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
        <el-form-item label="人员姓名" prop="personName" label-width="80px">
          <el-input
            v-model="queryParams.personName"
            placeholder="请输入人员姓名"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          >
          </el-input>
        </el-form-item>

        <el-form-item label-width="50px">
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8">
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="quitList">
        <el-table-column align="center"  label="卡号" prop="cardNo"/>
        <el-table-column align="center"  label="人员名称" prop="personName" />
        <el-table-column align="center"  label="人员名称" prop="certNo" />
        <el-table-column align="center"  label="人员所属组织名称" prop="orgName" />
        <el-table-column align="center"  label="事件类型" prop="eventType">
          <template slot-scope="scope">
            <dict-tag :options="dict.type.event_type" :value="scope.row.eventType"/>
          </template>
        </el-table-column>
        <el-table-column align="center"  label="进/出" prop="inAndOutType">
          <template slot-scope="scope">
            {{getInAndOutType(scope.row.inAndOutType) }}
          </template>
        </el-table-column>
        <el-table-column align="center"  label="门禁点名称" prop="doorName" />
        <el-table-column align="center"  label="出入时间" prop="eventTime" :show-overflow-tooltip="true"/>
        <el-table-column label="操作" align="center" width="150px" class-name="small-padding fixed-width" >
          <template slot-scope="scope">
            <el-button
              size="mini" type="text" icon="el-icon-view"
              class="text-info"
              @click="handleView(scope.row)"
            >详情</el-button>
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    
    <!--  查看  -->
    <el-dialog title="详情" :visible.sync="openView" width="80%">
      <el-form :model="form" label-width="140px">
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
          <el-col :span="8">
            <el-form-item label="门禁点编号：">{{form.doorNo}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="门禁点序列号：">{{form.doorSerial}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="通道类型：">{{form.channelType}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="资源名称：">{{form.devName}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="设备状态：">
              <span v-if="form.online === 0"><el-tag type="danger">离线</el-tag></span>
              <span v-else-if="form.online === 1"><el-tag type="success">在线</el-tag></span>
              <span v-else><el-tag type="info">未检测</el-tag></span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="人员所属组织名称：">{{form.orgName}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="关联的门禁点：">{{form.doorName}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="事件类型：">{{getEventType(form.eventType)}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="卡号：">{{form.cardNo}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="人员姓名：">{{form.personName}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="身份证号：">{{form.certNo}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="进出类型：">
                  <span v-if="form.inAndOutType === '0'">出行</span>
                  <span v-else-if="form.inAndOutType === '1'">进入</span>
                  <span v-else-if="form.inAndOutType === '-1'">未知</span>
                  <span v-else>未设置</span>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item v-if="form.inAndOutType=='0'" label="出行时间：">{{form.eventTime}}</el-form-item>
            <el-form-item v-else-if="form.inAndOutType=='1'" label="进入时间：">{{form.eventTime}}</el-form-item>
            <el-form-item v-else label="出入时间：">{{form.eventTime}}</el-form-item>
          </el-col>
          
        </el-row>
        
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  selectDoorEventList,
  getAllDoorList,
  selectDoorEventById
} from "@/api/estate/log/peoples";

import {
  getCurrentList
} from "@/api/estate/community";



export default {
  name: "peoples",
  dicts: ['event_type'],
  data() {
    return {
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],

      curentTableName:undefined,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      dateRange:[],
      deptList:[],
      indexCodeOptions:[],

      // 总条数
      total: 0,
      // 角色表格数据
      quitList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //是否显出查看弹框
      openView: false,

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        indexCode: undefined,
        eventType:undefined,
        personName:undefined,
        startTime: undefined,
        endTime:undefined
      },
      // 表单参数
      form: {},
      rules: {

      }
    };
  },
  created() {
    this.getAllDoorList();
    this.getDeptList();
  },
  methods: {
    getList() {
     
      if (this.dateRange) { 
        this.queryParams.startTime = this.dateRange[0];
        this.queryParams.endTime = this.dateRange[1];
      }
      selectDoorEventList(this.queryParams).then(response => {
        this.quitList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    // 表单重置
    reset() {
      this.form = {

      };
      this.resetForm("queryForm");
      this.resetForm("from");
    },
    // 取消按钮
    cancel() {
      this.openView=false;
      this.getDeptList();
    },
    getEventType(eventType){
      return this.selectDictLabel(this.dict.type.event_type,eventType);
    },
    /*查看按钮操作*/
    handleView(row) {
      this.openView = true;
      selectDoorEventById(row.id).then(response => {
        this.form = response.data;
      })
    },
    getAllDoorList() { 
      getAllDoorList().then(res => { 
        this.indexCodeOptions = res.data;
      })
    },
    getInAndOutType(inAndOutType) { 
      if (inAndOutType == '0') {
        return "出";
      } else if (inAndOutType == '1') {
        return "进";
      } else if (inAndOutType == '-1') {
        return "未知";
      } else { 
        return "--";
      }
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      var communityId=this.queryParams.communityId;
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        communityId: communityId,
        indexCode: undefined,
        eventType:undefined,
        personName:undefined,
        startTime: undefined,
        endTime:undefined
      }
      this.dateRange = [];
      this.resetForm("queryForm");
      
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    getDeptList(){
      getCurrentList().then(res => {
        this.deptList = res.data;
        const {deptList}=this
        if (deptList&&deptList.length>0){

          this.queryParams.communityId=deptList[0].communityId
        }
        this.getList();
      })
    }


  }
};
</script>

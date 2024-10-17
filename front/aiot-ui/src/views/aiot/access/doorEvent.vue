<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" class="cus-fm">
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
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <right-toolbar  @queryTable="getAccessList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="accessList"  width="60px" @selection-change="handleSelectionChange">
      <el-table-column align="center"  label="卡号" prop="cardNo"/>
      <el-table-column align="center"  label="人员名称" prop="personName" />
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
      <el-table-column align="center"  label="控制器名称" prop="devName" />
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
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getAccessList"/>
    <!--  查看  -->
    <el-dialog :title="title" :visible.sync="showDetail" width="80%">
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
  selectDoorEventList,selectDoorEventById
} from "@/api/aiot/doorEvent"
export default {
  name: "DoorEvent",
  dicts:['event_type'],
  data() {
    return {
      isSync:true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        doorIndexCode: undefined,
        eventType: undefined,
        startTime: undefined,
        endTime:undefined
      },
      //小区列表
      communityList: [],
      // 楼栋数据
      buildingList: [],
      // 单元数据
      unitList: [],
      // 遮罩层
      loading: true,
      // 日期范围
      dateRange: [],
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
    var doorIndexCode = this.$route.query && this.$route.query.indexCode;
    this.queryParams.doorIndexCode = doorIndexCode;
    this.getAccessList();
  },
  methods: {
    getEventType(eventType){
      return this.selectDictLabel(this.dict.type.event_type,eventType);
    },
    /** 查询列表 */
    getAccessList() {
      this.loading = true;
      if (this.dateRange) { 
        this.queryParams.startTime = this.dateRange[0];
        this.queryParams.endTime = this.dateRange[1];
      }
      selectDoorEventList(this.queryParams).then(response => {
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
      var doorIndexCode=this.queryParams.doorIndexCode;
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        doorIndexCode: doorIndexCode,
        eventType: undefined,
        startTime: undefined,
        endTime:undefined
      };
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.indexCodeList = selection.map(item => item.indexCode)
      this.multiple = !selection.length
    },

 

    /**查看*/
    handleView(row) {
      const id = row.id
      selectDoorEventById(id).then(response => {
        this.form = response.data;
        this.title = "详情";
        this.showDetail = true;
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
      this.batchShow =false;
      this.reset();
    },
  }
};
</script>

<style scoped>

</style>

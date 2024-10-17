<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" label-width="68px">
      <el-form-item label="小区名称">
        <el-select v-model="queryParams.communityId" placeholder="请选择小区" size="small">
          <el-option
            v-for="item in communityList"
            :key="item.communityId"
            :label="item.communityName"
            :value="item.communityId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="事件类型" prop="eventType">
        <el-select  v-model="queryParams.eventType" placeholder="请选择事件类型" clearable size="small">
          <el-option
            v-for="dict in dict.type.sys_event_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
          />
        </el-select>
      </el-form-item>
      <el-form-item label="记录时间">
        <el-date-picker v-model="dateRange" size="small" style="width: 240px"
                        value-format="yyyy-MM-dd" type="daterange" range-separator="-"
                        start-placeholder="开始日期" end-placeholder="结束日期">
        </el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="warning" plain
            icon="el-icon-s-promotion"
            size="mini" @click="handleSubscribe"
            v-if="eventStatus=='0'"
          >预警事件订阅</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger" plain
            icon="el-icon-delete"
            size="mini" @click="handleUnsubscribe"
            v-if="eventStatus=='1'"
          >预警事件取消订阅</el-button>
        </el-col>
        <!-- <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"

          >删除</el-button>
        </el-col> -->
      </el-row>
    </el-form>
    <el-table v-loading="loading" :data="eventList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="事件规则名称" align="center" prop="eventName">
        <template slot-scope="scope">
          {{ scope.row.eventName==null || scope.row.eventName==''?'-':scope.row.eventName }}
        </template>
      </el-table-column>
      <el-table-column label="事件类型" align="center" prop="eventType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.sys_event_type" :value="scope.row.eventType"/>
        </template>
      </el-table-column>
      <el-table-column label="事件源类型" align="center" prop="srcType"/>
      <el-table-column label="事件状态" align="center" prop="status">
        <template slot-scope="scope">
          {{ statusFormatter(scope.row.status) }}
        </template>
      </el-table-column>
      <el-table-column label="事件类别" align="center" prop="ability" />
      <el-table-column label="事件发生时间" align="center" prop="happenTime" >
        <template slot-scope="scope">
          <span>{{ momentFormat(scope.row.happenTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="事件结束时间" align="center" prop="stopTime" >
        <template slot-scope="scope">
          <span>{{scope.row.stopTime==null || scope.row.stopTime==''?'-':momentFormat(scope.row.stopTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="事件记录时间" align="center" prop="recordTime" />
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            
            @click="handleView(scope.row)"
          >查看</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getWarnList"/>

    <!--  查看  -->
    <el-dialog :title="title" :visible.sync="showDetail" width="60%">
      <AcsData :warnEvent="form" v-if="acsShow"/>
      <VisData :warnEvent="form" v-if="visShow"/>
      <VideoData :warnEvent="form" v-if="videoShow"/>
      <FireData :warnEvent="form" v-if="fireShow"/>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import AcsData from "./acsData";
import VisData from "./visData";
import VideoData from "./videoData";
import FireData from "./fireData";
import { getCurrentList } from "@/api/aiot/community"
import {selectEventList, selectEventById, removeEventByIds, subscribeEvent, unsubscribeEvent,} from "@/api/aiot/warn";
import {equals, isEmpty, isNotEmpty} from "@/utils/common";

export default {
  name: "WarnEvent",
  dicts: ['sys_event_type'],
  components: {AcsData,VisData,VideoData,FireData},
  data() {
    return {
      //小区
      communityList:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId:undefined,
        eventType:undefined,
        startTime:'',
        endTime:'',
      },
      // 日期范围
      dateRange: [],
      // 遮罩层
      loading: false,
      //预警
      eventList:[],
      // 总条数
      total: 0,
      eventStatus:0,
      // 选中数组
      ids: [],
      // 非多个禁用
      multiple: true,
      title:'',
      showDetail:false,
      acsShow:false,
      visShow:false,
      videoShow:false,
      fireShow: false,
      //门禁事件类型
      acsEvent:[],
      //可视对讲事件类型
      visEvent:[],
      //烟火事件类型
      fireEvent:[],
      //视频事件类型
      videoEvent:[930335,131585,131588],
      form:{},
    };
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
          this.getWarnList();
        }
      });
    },
    /** 查询列表 */
    getWarnList(){
      if(this.dateRange  == null){
        this.dateRange=[];
      } else {
        this.queryParams.startTime = this.dateRange[0];
        this.queryParams.endTime = this.dateRange[1];
      }
      selectEventList(this.queryParams).then(response=>{
        this.eventList= response.pageList.rows;
        this.total = response.pageList.total;
        this.eventStatus = response.eventStatus;
        this.loading = false;
      })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 10;
      this.getWarnList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 10;
      this.dateRange = []
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 预警事件订阅
    handleSubscribe(){
      let communityId = this.queryParams.communityId;
      if(isEmpty(communityId)){
        this.$modal.msgWarning("请选择要订阅事件的小区！！！");
        return
      }
      this.$modal.confirm('是否订阅该小区预警事件？').then(function() {
        return subscribeEvent(communityId);
      }).then(() => {
        this.getWarnList();
        this.$modal.msgSuccess("订阅成功");
      }).catch(() => {});
    },
    // 预警事件取消订阅
    handleUnsubscribe(){
      let communityId = this.queryParams.communityId;
      if(isEmpty(communityId)){
        this.$modal.msgWarning("请选择要取消订阅的小区！！！");
        return
      }
      this.$modal.confirm('是否取消订阅该小区预警事件？').then(function() {
        return unsubscribeEvent(communityId);
      }).then(() => {
        this.getWarnList();
        this.$modal.msgSuccess("取消成功");
      }).catch(() => {});
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.warnId)
      this.multiple = !selection.length
    },
    handleDelete(){
      const warnIds= this.ids;
      this.$confirm('是否确认删除所选中的预警数据?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return removeEventByIds(warnIds);
      }).then(() => {
        this.getWarnList();
        this.msgSuccess("删除成功");
      })
    },
    eventFormatter(eventType){
      return this.selectDictLabel(this.dict.type.sys_event_type, eventType);
    },
    statusFormatter(status){
      if(equals(status,'0')){
        return "瞬时"
      }else if(equals(status,'1')){
        return "开始"
      }else if(equals(status,'2')){
        return "停止"
      }else if(equals(status,'4')){
        return "事件联动结果更新"
      }else if(equals(status,'5')){
        return "事件图片异步上传"
      }
      return "-"
    },
    /**时间转换*/
    momentFormat(time) {
      if (isEmpty(time)) {
        return "";
      }
      return this.$moment(time).format("YYYY-MM-DD HH:mm:ss");
    },
    /**查看*/
    handleView(row) {
      const warnId = row.warnId
      selectEventById(warnId).then(response => {
        this.form = response.data;
        this.showDetail = true;
        console.log(this.form)
        if(!equals(this.acsEvent.indexOf(row.eventType),-1)){
          this.title = "门禁事件";
          this.acsShow = true;
        } else if(!equals(this.visEvent.indexOf(row.eventType),-1)){
          this.title = "可视对讲事件";
          this.visShow = true;
        } else if(!equals(this.fireEvent.indexOf(row.eventType),-1)){
          this.title = "烟火事件";
          this.fireShow = true;
        } else if(!equals(this.videoEvent.indexOf(row.eventType),-1)){
          this.title = "监控事件";
          this.videoShow = true;
        }
      });
    },
    // 取消按钮
    cancel() {
      this.showDetail =false;
      this.videoShow=false;
      this.visShow=false;
      this.acsShow=false;
      this.fireShow=false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.resetForm("form");
    },
  }
}
</script>

<style scoped>

</style>

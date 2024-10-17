<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
    <el-form-item label="所属小区" prop="communityId" label-width="80px">
        <el-select v-model="queryParams.communityId" placeholder="请选择" style="width: 200px">
          <el-option
            v-for="(item, index) in currentList"
              :key="index"
                          :label="item.communityName"
                          :value="item.communityId"
                        ></el-option>
                      </el-select>
          </el-form-item>
      <el-form-item label="发送状态" prop="status" label-width="80px">
        <el-select v-model="queryParams.status" placeholder="请选择" style="width: 200px">
          <el-option label="待发送" value="0" ></el-option>
          <el-option label="发送成功" value="1" ></el-option>
          <el-option label="发送失败" value="2" ></el-option>
        </el-select>
      </el-form-item>    
      <el-form-item label="业主名称" prop="name">
        <el-input
          v-model="queryParams.name"
          placeholder="请输入业主名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      
      <el-form-item  label="发送时间" prop="dateRange">
        <el-date-picker
          v-model="dateRange"
          style="width: 200px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          :disabled="multiple"
          @click="handleBachSms('0')"
          >批量发送站内消息</el-button
        >
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          :disabled="multiple"
          @click="handleBachSms('1')"
          >批量发送短信消息</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="list"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" :selectable="selectEnable" />
      <el-table-column label="所属小区" align="center" prop="communityName"/>
      <el-table-column label="所属楼栋" align="center" prop="buildingName"/>
      <el-table-column label="所属单元" align="center" prop="unitName"/>
      <el-table-column label="房号" align="center" prop="roomName" />
      <el-table-column label="业主名称" align="center" prop="name" />
      <el-table-column label="类型" align="center" prop="templateType">
        <template slot-scope="scope">
          {{ scope.row.templateType=='0'?'节假日':'生日祝福' }}      
        </template>
      </el-table-column>
      <el-table-column label="节日名称" align="center" prop="festivalName">
        <template slot-scope="scope">
          {{ scope.row.festivalName=='' || scope.row.festivalName==null?'--': scope.row.festivalName}}      
        </template>
      </el-table-column>
      
      <el-table-column label="发送状态" align="center" prop="status">
        <template slot-scope="scope">
          {{ getStatusName(scope.row.status)}}
        </template>
      </el-table-column>
      <el-table-column label="失败原因" align="center" prop="errorMsg" :show-overflow-tooltip="true"/>
      <el-table-column label="发送时间" align="center" prop="updateTime" />
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
        
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleSms(scope.row,'1')"
            v-if="scope.row.status!='1'"
            >发送短信通知</el-button
          >
          <br>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleSms(scope.row,'0')"
            v-if="scope.row.status!='1'"
            >发送站内通知</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />
  </div>
</template>

<script>
import {
  getSendSmsOwnerList,
  sendSms
} from "@/api/estate/send/sendSmsRecord";
import {
  getCurrentList
} from "@/api/estate/community";
import { getCommunityIdBuildingName} from "@/api/estate/center/room";
export default {
  name: "sendSmsRecordOwner",
  components: {
    
  },
  data() {
    return {
      currentList:[],
      openAccount:false,
     
      dateRange:[],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 业主短信发送记录表格数据
      list: [],
      buildingOptions:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        name: undefined,
        startTime: undefined,
        endTime:undefined,
        communityId:undefined,
      },
      // 表单参数
      form: {
        "releaseStatus":"0"
      },
      // 表单校验
      rules: {
       
      },
    };
  },
  created() {
    //所属小区
    getCurrentList().then(res => { 
      this.currentList = res.data;
    })
    this.getList();
  },
  methods: {
    getStatusName(status) { 
      if (status == '0') { 
        return "待发送";
      }
      if (status == '1') { 
        return "发送成功";
      }
      if (status == '2') { 
        return "发送失败";
      }
    },
    selectEnable(row) { // 复选框可选情况
      const {status}=row
      if (status=='1') { // 禁用发送成功的短信按钮
        return false;
      } else {
        return true;
      }

    },
  
    //小区下所有楼栋
    getBuildingOptions(communityId) { 
      getCommunityIdBuildingName(communityId).then(res => { 
        this.buildingOptions = res.data;
      })
    },
    getCommunityName(communityId) { 
     return this.currentList.filter(item => item.communityId == communityId)[0].communityName;
    },
    handleFind(row) { 
      // this.openAccount=true;
      // this.$nextTick(() => {
      //   this.$refs.indexAccountRef.receiveValue(row.id)
      // })     
    },
    
   
    /** 查询业主短信发送记录列表 */
    getList() {
      if (this.dateRange) { 
        this.queryParams.startTime = this.dateRange[0];
        this.queryParams.endTime=this.dateRange[1];
      }
    
      this.loading = true;
      getSendSmsOwnerList(this.queryParams).then((response) => {
        this.list = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
  
    // 表单重置
    reset() {
      this.form = {
      }
      
        this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 重新发送短信 */
    handleBachSms(type) {
      const ids = this.ids;
      var text = "短信通知";
      if (type == '0') {
        text = "站内通知";
      }
      this.loading = true;
      this.$modal
        .confirm('是否确认批量发送'+text+'吗？')
        .then(function () {
         
          return sendSms(ids + '',type);
        })
        .then(() => {
          this.loading = false;
          this.getList();
          this.$modal.msgSuccess("发送成功");
        })
        .catch(() => {
          this.loading = false;
        });
    },
    /** 重新发送短信 */
    handleSms(row,type) {
      const ids = row.id;
      var text = "短信通知";
      if (type == '0') {
        text = "站内通知";
      }
      this.$modal
        .confirm('是否确认发送'+text+'吗？')
        .then(function () {
         
          return sendSms(ids + '',type);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("发送成功");
        })
        .catch(() => {
        });
    },
  },
};
</script>

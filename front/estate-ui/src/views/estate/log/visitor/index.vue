<template>
  <div class="app-container">
      <el-row :gutter="20">
        <el-col>
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">

            <el-form-item label="所属小区" prop="communityId" label-width="80px">
              <el-select v-model="queryParams.communityId"  placeholder="请选择小区">
                <el-option
                  v-for="item in communityList"
                  :key="item.communityId"
                  :label="item.communityName"
                  :value="item.communityId"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="访客姓名" prop="visitorName" label-width="100px">
              <el-input
                style="width: 215px"
                v-model="queryParams.visitorName"
                placeholder="请输入访客姓名"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="访客手机号" prop="phoneNo" label-width="100px">
              <el-input
                style="width: 215px"
                v-model="queryParams.phoneNo"
                placeholder="请输入访客手机号"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="车牌号" prop="plateNo" label-width="100px">
              <el-input
                style="width: 215px"
                v-model="queryParams.plateNo"
                placeholder="请输入车牌号"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="访客状态" prop="visitorStatus" label-width="100px">
              <el-select v-model="queryParams.visitorStatus"  placeholder="请选择访客状态">
                <el-option
                  v-for="item in visitorStatusOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
            
            <el-form-item label="被访问姓名" prop="receptionistName" label-width="100px">
              <el-input
                style="width: 215px"
                v-model="queryParams.receptionistName"
                placeholder="请输入被访问姓名"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="被访问手机号" prop="receptionistPhoneNo" label-width="100px">
              <el-input
                style="width: 215px"
                v-model="queryParams.receptionistPhoneNo"
                placeholder="请输入被访问手机号"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            
            <el-form-item label="来访时间" prop="visitStartTime" label-width="100px">
              <el-date-picker
                v-model="dateRange"
                style="width: 215px"
                value-format="yyyy-MM-dd"
                type="daterange"
                range-separator="-"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              ></el-date-picker>
            </el-form-item>

            <el-form-item label="离开时间" prop="visitEndTime" label-width="100px">
              <el-date-picker
                v-model="dateRange1"
                style="width: 215px"
                value-format="yyyy-MM-dd"
                type="daterange"
                range-separator="-"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              ></el-date-picker>
            </el-form-item>

            <el-form-item label-width="80px">
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>
          <el-row :gutter="10" class="mb8">
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
          </el-row>

          <!--数据列表-->
          <el-table v-loading="loading" :data="headlineList">
            
            <el-table-column align="center"  label="来访单位" prop="visitorWorkUnit"/>
            <el-table-column align="center"  label="访客名称" prop="visitorName"/>
            <el-table-column align="center"  label="访客性别" prop="gender">
              <template slot-scope="scope">
                {{ scope.row.gender=='1'?'男':'女' }}
              </template>
            </el-table-column>
            <el-table-column align="center"  label="访客手机号" prop="phoneNo" />
            <el-table-column align="center"  label="访客证件类型" prop="certificateType">
              <template slot-scope="scope">
                {{getIdentityId(scope.row.certificateType)}}
              </template>
            </el-table-column>
            <el-table-column align="center"  label="访客证件号" prop="certificateNo" />
            <el-table-column align="center"  label="访客状态" prop="visitorStatus"/>
            <el-table-column align="center"  label="访客状态" prop="visitorStatus">
              <template slot-scope="scope">
                {{getVisitorStatus(scope.row.visitorStatus)}}
              </template>
            </el-table-column>
            
            <el-table-column align="center"  label="被访人姓名" prop="receptionistName" />
            <el-table-column align="center"  label="被访人手机号" prop="receptionistPhoneNo" />
            <el-table-column align="center"  label="被访人性别" prop="receptionistGender">
              <template slot-scope="scope">
                {{ scope.row.receptionistGender=='1'?'男':'女' }}
              </template>
            </el-table-column>
            <el-table-column align="center"  label="被访人证件类型" prop="receptionistCertificateType">
              <template slot-scope="scope">
                {{getIdentityId(scope.row.receptionistCertificateType)}}
              </template>
            </el-table-column>
            <el-table-column align="center"  label="被访人证件号" prop="receptionistCertificateNo" />
            <el-table-column align="center"  label="来访事由" prop="visitPurpose" />
            <el-table-column align="center"  label="来访时间" prop="visitStartTime"/>
            <el-table-column align="center"  label="离开时间" prop="visitEndTime"/>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width" >
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
        </el-col>
      </el-row>
     <!--  查看  -->
     <el-dialog title="详情" :visible.sync="openView" width="80%">
      <el-form :model="form" label-width="140px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="访客车牌号：">{{form.plateNo}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="访客姓名：">{{form.visitorName}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="访客性别：">{{form.gender=='1'?'男':'女'}}</el-form-item>
          </el-col>
          
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="访客手机号：">{{form.phoneNo}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="访客证件类型：">{{getIdentityId(form.certificateType)}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="访客证件号：">{{form.certificateNo}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="访客状态：">{{getVisitorStatus(form.visitorStatus)}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="访客事由：">{{form.visitPurpose}}</el-form-item>
          </el-col>
          
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="来访单位：">{{form.visitorWorkUnit}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="来访人数：">{{form.personNum}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="来访时间：">{{form.visitStartTime}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="离开时间：">{{form.visitEndTime}}</el-form-item>
          </el-col>
        </el-row>
        
        <el-row>
          <el-col :span="8">
            <el-form-item label="被访人姓名：">{{form.receptionistName}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="被访人性别：">{{form.receptionistGender=='1'?'男':'女'}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="被访人手机号：">{{form.receptionistPhoneNo}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="被访人证件类型：">{{getIdentityId(form.receptionistCertificateType)}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="被访人证件号：">{{form.receptionistCertificateNo}}</el-form-item>
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
import { selectVisitingRecordList, selectVisitingRecordById } from "@/api/estate/log/visitor";
import {getCurrentList} from "@/api/estate/community";
export default {
  name: 'visitor',
  dicts: ['sys_healthy_color'],
  data() {
    return {
      openView: false,
      BASE_API: process.env.VUE_APP_BASE_API,
      // 遮罩层
      loading: true,
      curentTableName:undefined,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      //项目集合
      communityList: [],
      // 总条数
      total: 0,
      // 用户分类设置表格数据
      headlineList: null,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      dateRange1:[],
      // 表单参数
      form: {},
      visitorStatusOptions: [
        { "value": "5", "label": "超期自动签离" },
        { "value": "6", "label": "已签离" },
        { "value": "7", "label": "超期未签离" },
        { "value": "8", "label": "已到达"}
      ],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        visitorName: undefined,
        plateNo:undefined,
        visitorStatus:undefined,
        receptionistName:undefined,
        receptionistPhoneNo:undefined,
        visitStartStartDate: undefined,
        visitStartEndDate: undefined,
        visitEndStartDate: undefined,
        visitEndEndDate:  undefined
      },
      // 表单校验
      rules: {
      }
    };
  },
  created() {
    this.getCommunityList();
  },
  methods: {
    getVisitorStatus(visitorStatus) { 
      var obj = this.visitorStatusOptions.filter(item=>item.value == visitorStatus)[0];
      if (obj) { 
        return obj.label;
      }
      
    },
    //111=身份证414=护照113=户口簿335=驾驶证131=工作证133=学生证990=其他
    getIdentityId(identityId) { 
      if (identityId == '111') { 
        return "身份证";
      }
      if (identityId == '414') { 
        return "护照";
      }
      if (identityId == '113') { 
        return "户口簿";
      }
      if (identityId == '335') { 
        return "驾驶证";
      }
      if (identityId == '131') { 
        return "工作证";
      }
      if (identityId == '133') { 
        return "学生证";
      }
     
      if (identityId == '990') { 
        return "其他";
      }
    },
    getCardType(cardType) { 
     
      if (cardType == '0') { 
        return "身份证号";
      }
      if (cardType == '1') { 
        return "身份证序列号";
      }
      if (cardType == '2') { 
        return "IC卡";
      }
      if (cardType == '3') { 
        return "二维码";
      }
      if (cardType == '4') { 
        return "车牌号码";
      }
    },
    /** 查询用户列表 */
    getList() {
      this.loading = true;
      if (this.dateRange) { 
        this.queryParams.visitStartStartDate = this.dateRange[0];
        this.queryParams.visitStartEndDate = this.dateRange[1];
      }
      if (this.dateRange1) { 
        this.queryParams.visitEndStartDate = this.dateRange1[0];
        this.queryParams.visitEndEndDate = this.dateRange1[1];
      }
      

      selectVisitingRecordList(this.queryParams).then(response => {
          this.headlineList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 取消按钮
    cancel() {
      this.openView = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        headlineId: undefined,
        headlineTitle: undefined,
        createBy: null,
        status: "0",
        roleIds: []
      };
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
      this.dateRange1 = [];
      var communityId=this.queryParams.communityId;
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        communityId: communityId,
        visitorName: undefined,
        plateNo:undefined,
        visitorStatus:undefined,
        receptionistName:undefined,
        receptionistPhoneNo:undefined,
        visitStartStartDate: undefined,
        visitStartEndDate: undefined,
        visitEndStartDate: undefined,
        visitEndEndDate:  undefined
      };
     
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.headlineId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },


    getCommunityList(){
      let param = {
        communityType: "1",
      }
      getCurrentList(param).then(res => {
        this.communityList = res.data;
        const {communityList}=this
        if (communityList&&communityList.length>0){

          this.queryParams.communityId=communityList[0].communityId
        }
        this.getList();
      })
    },

    /*查看按钮操作*/
    handleView(row) {
      this.openView = true;
      selectVisitingRecordById(row.id).then(response => {
        this.form = response.data;
      })
    },



  }
};
</script>



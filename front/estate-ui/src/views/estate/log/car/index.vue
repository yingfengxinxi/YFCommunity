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
        
        <el-form-item label="车牌号" prop="plateNo" label-width="80px">
          <el-input
            v-model="queryParams.plateNo"
            placeholder="请输入车牌号"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          >
          </el-input>
        </el-form-item>
        <el-form-item label="车主姓名" prop="personName" label-width="80px">
          <el-input
            v-model="queryParams.personName"
            placeholder="请输入车主姓名"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          >
          </el-input>
        </el-form-item>
      <el-form-item label="通行时间" prop="crossTime">
        <el-date-picker v-model="dateRange" size="small" style="width: 240px"
                        value-format="yyyy-MM-dd" type="daterange" range-separator="-"
                        start-placeholder="开始日期" end-placeholder="结束日期">
        </el-date-picker>
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
        
        <el-table-column align="center"  label="车牌号" prop="plateNo"/>
        <el-table-column align="center"  label="车辆类型" prop="vehicleType">
          <template slot-scope="scope">
            {{getVehicleType(scope.row.vehicleType) }}
          </template>
        </el-table-column>
        <el-table-column align="center"  label="车辆分类名称" prop="carCategoryName"/>
        <el-table-column align="center"  label="车主姓名" prop="personName">
        <template slot-scope="scope">
            {{scope.row.personName==null||scope.row.personName==''?'--':scope.row.personName }}
          </template>
        </el-table-column>
        <el-table-column align="center"  label="车主手机号" prop="phoneNo">
          <template slot-scope="scope">
            {{scope.row.phoneNo==null || scope.row.phoneNo==''?'--':scope.row.phoneNo }}
          </template>
        </el-table-column>
        <el-table-column align="center"  label="停车库名称" prop="parkName"/>
        <el-table-column align="center"  label="出入口名称" prop="entranceName" />
        <el-table-column align="center"  label="车道名称" prop="roadwayName" />
        
        <el-table-column align="center"  label="进/出" prop="vehicleOut">
          <template slot-scope="scope">
            {{getVehicleOut(scope.row.vehicleOut) }}
          </template>
        </el-table-column>
        
        <el-table-column align="center"  label="放行模式" prop="releaseMode">
          <template slot-scope="scope">
            {{getReleaseMode(scope.row.releaseMode) }}
          </template>
        </el-table-column>
        <el-table-column align="center"  label="放行方式" prop="releaseWay">
          <template slot-scope="scope">
            {{getReleaseWay(scope.row.releaseWay) }}
          </template>
        </el-table-column>
        <el-table-column align="center"  label="放行原因" prop="releaseReason">
          <template slot-scope="scope">
            {{getReleaseReason(scope.row.releaseReason) }}
          </template>
        </el-table-column>
        
        <el-table-column align="center"  label="通过时间" prop="crossTime"/>
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
                <el-form-item label="车主姓名：">{{form.personName}}</el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="车主性别：">{{form.gender=='1'?'男':'女'}}</el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="联系方式：">{{form.phoneNo}}</el-form-item>
              </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
                <el-form-item label="证件类型：">{{getCertificateType(form.certificateType)}}</el-form-item>
              </el-col>
              <el-col :span="10">
                <el-form-item label="证件号：">{{form.certificateNo}}</el-form-item>
              </el-col>
        </el-row>
        <el-row>
              <el-col :span="8">
                <el-form-item label="车牌号：">{{form.plateNo}}</el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="通行时间：">{{form.crossTime}}</el-form-item>
              </el-col>
        </el-row>
        <el-row>
              <el-col :span="8">
                <el-form-item label="车辆颜色：">{{getVehicleColor(form.vehicleColor)}}</el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="车辆类型：">{{getVehicleType(form.vehicleType)}}</el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="车牌颜色：">{{getPlateColor(form.plateColor)}}</el-form-item>
              </el-col>
        </el-row>
        <el-row>
              <el-col :span="8">
                <el-form-item label="车牌类型：">{{getPlateType(form.plateType)}}</el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="车辆分类名称：">{{form.carCategoryName}}</el-form-item>
              </el-col>
        </el-row>
        <el-row>
              <el-col :span="20">
                <el-form-item label="车辆描述：">{{form.description}}</el-form-item>
              </el-col>
        </el-row>
        
        
        <el-row>
              <el-col :span="8">
                <el-form-item label="停车库名称：">{{form.parkName}}</el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="出入口名称：">{{form.entranceName}}</el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="车道名称：">{{form.roadwayName}}</el-form-item>
              </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="出入场：">{{getVehicleOut(form.vehicleOut)}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="放行模式：">{{getReleaseMode(form.releaseMode)}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="放行结果：">
              <span v-if="form.releaseResult=='0'"><el-tag type="danger">未放行</el-tag></span>
              <span v-if="form.releaseResult=='1'"><el-tag type="success">正常放行</el-tag></span>
              <span v-if="form.releaseResult=='2'"><el-tag type="success">离线放行</el-tag></span>
            </el-form-item>
          </el-col>
          
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="放行方式：">{{getReleaseWay(form.releaseWay)}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="放行原因：">{{getReleaseReason(form.releaseReason)}}</el-form-item>
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
  selectCrossRecordList,
  selectcrossRecordById
} from "@/api/estate/log/car";


import {
  getCurrentList
} from "@/api/estate/community";



export default {
  name: "peoples",
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
        plateNo: undefined,
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

    this.getDeptList();
  },
  methods: {
    getList() {
     
      if (this.dateRange) { 
        this.queryParams.startTime = this.dateRange[0];
        this.queryParams.endTime = this.dateRange[1];
      }
      selectCrossRecordList(this.queryParams).then(response => {
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

    /*查看按钮操作*/
    handleView(row) {
      this.openView = true;
      selectcrossRecordById(row.id).then(response => {
        this.form = response.data;
      })
    },
    getPlateType(plateType){
        //0:标准民用车,1:02式民用车,3:警车,4:民用车双行尾牌车,5:使馆车,6:农用车,7:摩托车,8:新能源车
        if(plateType=='0'){
          return "标准民用车"
        }
        if(plateType=='1'){
          return "02式民用车"
        }
        if(plateType=='3'){
          return "警车"
        }
        if(plateType=='4'){
          return "民用车双行尾牌车"
        }
        if(plateType=='5'){
          return "使馆车"
        }
        if(plateType=='6'){
          return "农用车"
        }
        if(plateType=='7'){
          return "摩托车"
        }
        if(plateType=='8'){
          return "新能源车"
        }


    },
    getPlateColor(plateColor){
      //0:蓝色,1:黄色,2:白色,3:黑色,4:绿色,5:民航黑色, 255:其他颜色
      if(plateColor=='0'){
        return "蓝色";
      }
      if(plateColor=='1'){
        return "黄色";
      }
      if(plateColor=='2'){
        return "白色";
      }
      if(plateColor=='3'){
        return "黑色";
      }
      if(plateColor=='4'){
        return "绿色";
      }
      if(plateColor=='5'){
        return "民航黑色";
      }
      if(plateColor=='255'){
        return "其他颜色";
      }
    },
    getVehicleType(vehicleType) { 
      //0：其他车；1：小型车；2：大型车；3：摩托车
      if (vehicleType == '0') { 
        return "其他车"
      }
      if (vehicleType == '1') { 
        return "小型车"
      }
      if (vehicleType == '2') { 
        return "大型车"
      }
      if (vehicleType == '3') { 
        return "摩托车"
      }
    },
    getReleaseReason(releaseReason){
      //100=固定车自动放行101=临时车自动放行102=预约车自动放行103=一户多车自动放行
      if(releaseReason=='100'){
        return "固定车自动放行";
      }
      if(releaseReason=='101'){
        return "临时车自动放行";
      }
      if(releaseReason=='102'){
        return "预约车自动放行";
      }
      if(releaseReason=='103'){
        return "一户多车自动放行";
      }
    },
    getReleaseWay(releaseWay){
        //10-未开闸11-自动开闸12-人工/人工开闸13-遥控器开闸
        if(releaseWay=='10'){
          return "未开闸";
        }
        if(releaseWay=='11'){
          return "自动开闸";
        }
        if(releaseWay=='12'){
          return "人工/人工开闸";
        }
        if(releaseWay=='13'){
          return "遥控器开闸";
        }
    },
    getReleaseResult(releaseResult){
        //0-未放行1-正常放行2-离线放行
        if(releaseResult=='0'){
          return "未放行";
        }
        if(releaseResult=='1'){
          return "正常放行";
        }
        if(releaseResult=='2'){
          return "离线放行";
        }
    },
    getReleaseMode(releaseMode) { 
      //0-禁止放行，1-固定车包期，2-临时车入场，3-预约车入场，10-离线出场，11-缴费出场，
      //12-预付费出场，13-免费出场，30- 非法卡不放行，31-手动放行，32-特殊车辆放行，
      //33-节假日放行，35-群组放行，36-遥控器开闸
      if(releaseMode=='0'){
        return "禁止放行";
      }
      if(releaseMode=='1'){
        return "固定车包期";
      }
      if(releaseMode=='2'){
        return "临时车入场";
      }
      if(releaseMode=='3'){
        return "预约车入场";
      }
      if(releaseMode=='10'){
        return "离线出场";
      }
      if(releaseMode=='11'){
        return "缴费出场";
      }
      if(releaseMode=='12'){
        return "预付费出场";
      }
      if(releaseMode=='13'){
        return "免费出场";
      }
      if(releaseMode=='30'){
        return "非法卡不放行";
      }
      if(releaseMode=='31'){
        return "手动放行";
      }
      if(releaseMode=='32'){
        return "特殊车辆放行";
      }
      if(releaseMode=='33'){
        return "节假日放行";
      }
      if(releaseMode=='35'){
        return "群组放行";
      }
      if(releaseMode=='36'){
        return "遥控器开闸";
      }
      
    },
    getVehicleOut(vehicleOut) { 
      if (vehicleOut == '0') {
        return "进场";
      } else if (vehicleOut == '1') {
        return "出厂";
      }else{ 
        return "--";
      }
    },
    getVehicleColor(vehicleColor){
        //0：其他颜色；1：白色；2：银色；3：灰色；4：黑色；5：红色；
        //6：深蓝色7：蓝色；8：黄色；9：绿色；10：棕色；11：粉色；12：紫色’
        if(vehicleColor=='0'){
          return "其他颜色"
        }
        if(vehicleColor=='1'){
          return "白色"
        }
        if(vehicleColor=='2'){
          return "银色"
        }
        if(vehicleColor=='3'){
          return "灰色"
        }
        if(vehicleColor=='4'){
          return "黑色"
        }
        if(vehicleColor=='5'){
          return "红色"
        }
        if(vehicleColor=='6'){
          return "深蓝色"
        }
        if(vehicleColor=='7'){
          return "蓝色"
        }
        if(vehicleColor=='8'){
          return "黄色"
        }
        if(vehicleColor=='9'){
          return "绿色"
        }
        if(vehicleColor=='10'){
          return "棕色"
        }
        if(vehicleColor=='11'){
          return "粉色"
        }
        if(vehicleColor=='12'){
          return "紫色"
        }
       
    },
    getCertificateType(certificateType){
      if(certificateType=='111'){
        return "身份证";
      }
      if(certificateType=='414'){
        return "护照";
      }
      if(certificateType=='113'){
        return "户口簿";
      }
      if(certificateType=='335'){
        return "驾驶证";
      }
      if(certificateType=='131'){
        return "工作证";
      }
      if(certificateType=='133'){
        return "学生证";
      }
      if(certificateType=='990'){
        return "其他";
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
        plateNo: undefined,
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

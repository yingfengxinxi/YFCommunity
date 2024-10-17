<template>
  <div class="app-container">
    <div>
      <el-row style="margin-top: 20px;margin-left: 15px">
        <el-col :span="5">
          <el-input
            placeholder="请输入小区名称"
            v-model="filterText">
          </el-input>
          <div class="head-container">
            <el-tree
              class="filter-tree hide-tree"
              :data="data"
              :props="{children:''}"
              style="margin-top: 10px"
              :highlight-current="true"
              node-key="id"
              :filter-node-method="filterNode"
              :expand-on-click-node="false"
              :current-node-key="default_id"
              @node-click="getCommunityId"
              ref="tree">
              <!-- 插入楼栋图标 -->
              <span slot-scope="{ node }">
	   		          <i class="el-icon-office-building" />
	   		          <span style="padding-left:5px;font-size: 14px">{{node.label}}</span>
		          </span>
            </el-tree>
          </div>
        </el-col>
        <el-col style="margin-left: 15px" :span="18">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
            <el-form-item label="工单名称" prop="order_name">
              <el-input
                v-model="queryParams.order_name"
                placeholder="请输入"
                clearable
              />
            </el-form-item>
            <el-form-item label="报事类型" prop="repair_type">
              <el-select v-model="queryParams.repair_type" placeholder="请选择" clearable>
                <el-option
                  v-for="type in repairList"
                  :key="type.repair_id"
                  :label="type.repair_name"
                  :value="type.repair_id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="负责人" prop="accept_person">
              <el-input
                v-model="queryParams.accept_person"
                placeholder="请输入"
                clearable
              />
            </el-form-item>
            <el-form-item label="执行状态" prop="state">
              <el-select
                v-model="queryParams.state"
                placeholder="请选择执行状态"
                clearable
                style="width: 100%"
              >
                <el-option
                  v-for="executeStatus in executeStatusList"
                  :key="executeStatus.dictValue"
                  :label="executeStatus.dictLabel"
                  :value="executeStatus.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>
          <el-table v-loading="loading" :data="dataList">
            <el-table-column label="工单编号" align="center" prop="order_no" show-overflow-tooltip/>
            <el-table-column label="工单名称" align="center" prop="order_name" show-overflow-tooltip/>
            <el-table-column label="报事类型" align="center" prop="repair_name" show-overflow-tooltip/>
            <el-table-column label="分配方式" align="center" show-overflow-tooltip>
              <template v-slot="scope">
                {{ allotWayFormat(scope.row.allot_way) }}
              </template>
            </el-table-column>
            <el-table-column label="主要负责人" align="center" prop="staff_name" show-overflow-tooltip/>
            <el-table-column label="分配状态" align="center" show-overflow-tooltip>
              <template v-slot="scope">
                {{ allotStatusFormat(scope.row.allot_status) }}
              </template>
            </el-table-column>
            <el-table-column label="接单状态" align="center" show-overflow-tooltip>
              <template v-slot="scope">
                {{ acceptStatusFormat(scope.row.accept_status) }}
              </template>
            </el-table-column>
            <el-table-column label="执行状态" align="center" show-overflow-tooltip>
              <template v-slot="scope">
                {{ getExecuteStateLabel(scope.row.execute_status) }}
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="200">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="show(scope.row)"
                >查看</el-button>
                <template>
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    v-hasPermi="['work:parkorder:updateorder']"
                    @click="form=scope.row;open=true"
                  >修改
                  </el-button>
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    v-hasPermi="['work:parkorder:update']"
                    v-show="scope.row.allot_status == '0'"
                    @click="allotStaff(scope.row);allotFlag=true"
                  >分配
                  </el-button>
<!--
                  <el-button
                    size="mini"
                    type="text"
                    v-hasPermi="['work:parkorder:remove']"
                    v-show="scope.row.execute_status != '1'"
                    icon="el-icon-delete"
                    style="color: #f56c6c"
                    @click="handleDelete(scope.row)"
                  >删除
                  </el-button>
-->
                </template>
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
    </div>
    <el-drawer
      title="预览"
      size="60%"
      :visible.sync="drawer"
    >
      <div style="margin: 15px">
        <div>
          <span>工单详情</span>
          <el-divider/>
          <el-descriptions :label-style="{'line-height':'40px'}" :content-style="{'line-height':'40px'}" style="margin: 20px" :column="3">
            <el-descriptions-item label="工单编号">{{info.order_no}}</el-descriptions-item>
            <el-descriptions-item label="工单名称">{{ info.order_name }}</el-descriptions-item>
            <el-descriptions-item label="工单类型">园区保修</el-descriptions-item>
            <el-descriptions-item label="报事类型">{{info.repair_name}}</el-descriptions-item>
            <el-descriptions-item label="报事地址">{{ info.address }}</el-descriptions-item>
            <el-descriptions-item label="计划开始时间">{{ parseTime(info.plan_start)}}</el-descriptions-item>
            <el-descriptions-item label="计划结束时间">{{ parseTime(info.plan_end)}}</el-descriptions-item>
            <el-descriptions-item label="完成时间">{{ parseTime(info.execute_time)}}</el-descriptions-item>
            <el-descriptions-item label="业主评价">
              {{ info.complaintType }}
<!--              <div v-show="info.error_count>0">
                <el-tag type="danger">异常</el-tag>
              </div>
              <el-tag type="success" v-show="info.error_count==0">无异常</el-tag>-->
            </el-descriptions-item>
            <el-descriptions-item label="上传图片" :span="3">
              <el-image  :src="item.image_url" fit="contain"  style="width: 180px; height: 100px" v-for="(item,index) of info.orderImg" :key="index+'i'"/>
            </el-descriptions-item>
            <el-descriptions-item label="提交人备注" :span="3">{{ info.bpo_remark }}</el-descriptions-item>
            <el-descriptions-item label="主要负责人">{{info.staff_name}}</el-descriptions-item>
          </el-descriptions>
        </div>
        <div>
          <span>工单协助人信息</span>
          <el-divider/>
          <el-table  :data="assistList" style="margin: 20px; width: 96%">
            <el-table-column label="人员姓名" align="center" prop="staff_name" />
            <el-table-column label="手机号" align="center" >
              <template slot-scope="scope">
                {{hide( scope.row.staff_phone,3,7)}}
              </template>
            </el-table-column>
            <el-table-column label="身份证号" align="center">
              <template slot-scope="scope">
                {{hide( scope.row.card_no,4,14)}}
              </template>
            </el-table-column>
            <el-table-column label="人员类别" align="center" prop="dict_label"/>
          </el-table>
        </div>
        <div style="margin-top: 50px;">
          <span>执行详情</span>
          <el-divider/>
          <el-card class="box-card">
            <el-timeline :reverse="reverse">
              <el-timeline-item
                v-for="(item, index) in detailRecordList"
                :key="index"
                color='rgb(179, 241, 182)'
                :timestamp="parseTime(item.createTime)">
                  <span>{{item.orderResult}}</span> <span style="margin-left: 20px">操作人:{{item.userName}}</span>
              </el-timeline-item>
            </el-timeline>
          </el-card>
        </div>
      </div>
    </el-drawer>
    <el-dialog
      title="分配"
      :visible.sync="allotFlag"
      width="25%">
      <el-form :model="allot" ref="allot" :rules="ruleAllot" size="small" :inline="true">
        <el-form-item label="负责人" prop="staff_id" label-width="140px">
          <el-select v-model="allot.staff_id" placeholder="请选择负责人" clearable @change="getStaffName">
            <el-option
              v-for="type in allotStaffList"
              :key="type.staffId"
              :label="type.staffName"
              :value="type.staffId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="工单计划开始时间" prop="plan_start">
          <el-date-picker
            v-model="allot.plan_start"
            type="datetime"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="工单计划结束时间" prop="plan_end">
          <el-date-picker
            v-model="allot.plan_end"
            type="datetime"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
    <el-button @click="allotFlag = false;resetForm('allot')">关 闭</el-button>
    <el-button type="primary" @click="AllotOrder">确 定</el-button>
  </span>
    </el-dialog>
    <!-- 参数配置对话框 -->
    <el-dialog title="修改工单信息" :visible.sync="open" width="660px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="140px">
        <el-form-item label="工单名称" prop="order_name">
          <el-input v-model="form.order_name" placeholder="请输入工单名称" :maxlength="50"/>
        </el-form-item>
        <el-form-item label="工单计划开始时间" prop="plan_start">
          <el-date-picker
            v-model="form.plan_start"
            type="datetime"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="工单计划结束时间" prop="plan_end">
          <el-date-picker
            v-model="form.plan_end"
            type="datetime"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="工单备注" prop="remark">
          <el-input v-model="form.remark" placeholder="请输入工单备注"  type="textarea" :maxlength="900"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="open=false;resetForm('form')">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {getDicts} from "@/api/system/dict/data";
import {query,update,updateOrder,remove} from '@/api/estate/work/parkmatter/order'
import {queryAssist,queryRepair} from "@/api/estate/work/orderLedger/parkLedger";
import {detailRecordGeneral} from "@/api/estate/work/orderDisaster/DisasterOrder"
import moment from 'moment'
import {desensitization} from "@/utils/common";
import {listLeisureBusyStaffs} from "@/api/estate/manage/staff/staff";
export default {
  name: "audit",
  data(){
    return{
      disasterList:[],
      dialogVisible:false,
      allotFlag:false,
      drawer: false,
      reverse:true,
      repairList:[],
      assistList:[],
      open:false,
      info:{},
      filterText:'',
      allot:{
        plan_start:'',
        plan_end:''
      },
      detailRecordList:[],
      errorList:[],
      default_id:'',
      data:[],
      ruleAllot:{
        staff_id: [
          {required: true, message: "负责人不能为空",  trigger: ["blur", "change"]}
        ],
        plan_start: [
          { required: true, message: "请选择计划开始时间", trigger: ["blur", "change"] }
        ],
        plan_end: [
          { required: true, message: "请选择计划结束时间", trigger: ["blur", "change"] }
        ],
      },
      // 总条数
      total: 0,
      form:{},
      // 表单校验
      rules: {
        order_name: [
          {required: true, message: "工单名称不能为空", trigger: "blur"}
        ],
        remark: [
          {required: true, message: "工单备注不能为空", trigger: "blur"}
        ],
        plan_start: [
          { required: true, message: "请选择计划开始时间", trigger: ["blur", "change"] }
        ],
        plan_end: [
          { required: true, message: "请选择计划结束时间", trigger: ["blur", "change"] }
        ],
      },
      //
      dataList:[],
      executeStatusList:[],
      allotStatusList:[],
      acceptStatusList:[],
      allotStaffList:[],
      // 遮罩层
      loading: true,
      queryParams:{
        pageNum: 1,
        pageSize: 10,
        order_name:'',
        repair_type:'',
        accept_person:'',
        state:''
      },
    }
  },
  watch: {
    //小区过滤
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  mounted() {
    this.initData()
  },
  methods:{
    hide(str,begin,end){
      if(str){
        return  desensitization(str,begin,end)
      }
      return  "";
    },
    show(row){
      queryAssist({id:row.order_id}).then(e=>{
        this.assistList=e.data
      })
      detailRecordGeneral({orderType:'7',orderId:row.order_id}).then(e=>{
          this.detailRecordList=e.data
      })
      this.drawer=true;
      this.info=row;

    },
    //删除工单
    handleDelete(row){
      this.$confirm('是否确认删除数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then( ()=>{
        return remove({audit_id:row.audit_id,order_id:row.order_id});
      }).then(() => {
        this.$message.success('删除成功!!')
        this.getList();
      })
    },
    //修改工单数据
    submitForm(){
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.compareTime('form')) {
            this.formatTime('form')
            updateOrder(this.form).then(e => {
              const {msg, code} = e
              if (code == 200) {
                this.$message.success(msg)
              }
              this.getList()
              this.open = false
            })
          }else {
            this.$message.error('计划结束时间只能高于计划开始时间!!')
          }
        }
      })
    },
    formatTime(form){

        if (form == 'form'){
          this.form['plan_start']=moment( this.form['plan_start']).format('YYYY-MM-DD HH:mm:ss')
          this.form['plan_end']=moment( this.form['plan_end']).format('YYYY-MM-DD HH:mm:ss')
        }else {
          this.allot['plan_start']=moment( this.allot['plan_start']).format('YYYY-MM-DD HH:mm:ss')
          this.allot['plan_end']=moment( this.allot['plan_end']).format('YYYY-MM-DD HH:mm:ss')
        }
    },
    compareTime(form){//时间对比
      let time={}
      if (form == 'form'){
        time={
          start: this.form['plan_start'],
          end: this.form['plan_end']
        }
      }else {
        time={
          start: this.allot['plan_start'],
          end: this.allot['plan_end']
        }
      }
      if (new Date(time.end).getTime()<=new Date(time.start).getTime()){

        return false;
      }

      return true;
    },
    AllotOrder(){//分配
      this.$refs["allot"].validate(valid => {
        if (valid) {
          if (this.compareTime('allot')) {
            this.formatTime('allot')
            update(this.allot).then(e=>{
              const {msg,code}=e
              if (code==200){
                this.$message.success(msg)
              }
              this.getList()
              this.resetForm('allot')
              this.allotFlag=false
            })
          }else {
            this.$message.error('计划结束时间只能高于计划开始时间!!')
          }

        }
      }
      )
    },
    allotStaff(row){
      listLeisureBusyStaffs({allotType: '7', communityId: this.queryParams['community_id']}).then(e => {
        this.allotStaffList=e.data
      });
      this.allot['orderId']=row.order_id
    },
    // 获取分配状态列表
    getAllotStatus() {
      getDicts("allot_status").then(response => {
        this.allotStatusList = response.data;
      });
    },
    // 获取分配方式列表
    getAllotWay() {
      getDicts("allot_way").then(response => {
        this.allotWayList = response.data;
      });
    },
    // 获取接单状态列表
    getAcceptStatus() {
      getDicts("accept_status").then(response => {
        this.acceptStatusList = response.data;
      });
    },
    // 分配方式格式化
    allotWayFormat(allotWayValue) {
      for (const allotWay of this.allotWayList) {
        if (allotWay.dictValue === allotWayValue) {
          return allotWay.dictLabel;
        }
      }
    },
    // 分配状态格式化
    allotStatusFormat(allotStatusValue) {
      for (const allotStatus of this.allotStatusList) {
        if (allotStatus.dictValue === allotStatusValue) {
          return allotStatus.dictLabel;
        }
      }
    },
    // 接单状态格式化
    acceptStatusFormat(acceptStatusValue) {
      for (const acceptStatus of this.acceptStatusList) {
        if (acceptStatus.dictValue === acceptStatusValue) {
          return acceptStatus.dictLabel;
        }
      }
    },
    getStaffName(id){
      this.allotStaffList.map(e=>{
        const {staff_name,staff_id}=e
        if (staff_id==id){
          this.allot['staffName']=staff_name
          return

        }
      })
    },
    // 查询系统状态字典
    getStatus() {
      getDicts("execute_status").then(response => {
        this.executeStatusList = response.data;
      });
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    getExecuteStateLabel(value){
      if (value){
        let filter = this.executeStatusList.filter(e=>e.dictValue==value);

        return filter[0].dictLabel
      }else {
        return  ''
      }

    },
    //初始化数据
    initData(){
      getCurrentList().then(e => {
        const {data}=e
        if (data){
          let arr=new Array();
          data.forEach((item)=>{
            const {communityId,communityName}=item
            arr.push({
              id:communityId,
              label:communityName,
              children: [{}]
            });
          })
          this.data=arr;
          this.queryParams['community_id']=data[0].communityId
          this.default_id=data[0].communityId

          this.$nextTick(() => {
            this.$refs['tree'].setCurrentKey(this.default_id);
          });
          this.getList();
        }else {
          this.$message.error('暂无小区')
        }
      })
      this.getStatus()
      queryRepair().then(e=>{
        this.repairList=e.data
      })
      this.getAcceptStatus()
      this.getAllotStatus()
      this.getAllotWay()
    },
    //获取点击小区id
    getCommunityId(e){
      this.queryParams['community_id']=e.id
      this.getList()
    },
    getList(){
        query(this.queryParams).then(e=>{
            this.dataList=e.rows
            this.total = e.total;
            this.loading = false;
        })
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum=1
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
  }
}
</script>

<style scoped>
/*设置高亮颜色*/
/deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #b3f1b6 !important;
}
</style>

<template>
  <div>
    <div>
      <el-row style="margin-top: 20px;margin-left: 15px">
        <el-col :span="5">
          <el-input
            placeholder="请输入小区名称"
            v-model="filterText">
          </el-input>
          <div class="head-container">
            <el-tree
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
	   		          <span class="content"  style="padding-left:5px;">{{node.label}}</span>
		          </span>
            </el-tree>
          </div>
        </el-col>
        <el-col style="margin-left: 15px" :span="18">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
            <el-form-item label="工单名称" prop="order_name">
              <el-input
                v-model="queryParams.order_name"
                placeholder="请输入用户名称"
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
            <el-form-item label="主要负责人" prop="accept_person">
              <el-input
                v-model="queryParams.accept_person"
                placeholder="请输入"
                clearable
              />
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>
          <el-table v-loading="loading" :data="dataList">
            <el-table-column label="工单编号" align="center" prop="order_no" />
            <el-table-column label="工单名称" align="center" prop="order_name" />
            <el-table-column label="报事类型" align="center" prop="repair_name" />
            <el-table-column label="主要负责人" align="center" prop="staff_name"/>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="show(scope.row)"
                >查看</el-button>
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
              <el-descriptions-item label="异常状态">
                <div v-show="info.error_count>0">
                  <el-tag type="danger">异常</el-tag>
                </div>
                <el-tag type="success" v-show="info.error_count==0">无异常</el-tag>
              </el-descriptions-item>
              <el-descriptions-item label="上传图片" :span="3">
                <el-image  :src="item.image_url" fit="contain"  style="width: 180px; height: 100px" v-for="(item,index) of info.orderImg" :key="index+'i'"/>
              </el-descriptions-item>
              <el-descriptions-item label="提交人备注" :span="3">{{ info.bpo_remark }}</el-descriptions-item>
              <el-descriptions-item label="主要负责人">{{ info.staff_name }}</el-descriptions-item>
            </el-descriptions>
          </div>
          <div>
            <span>工单执行人信息</span>
            <el-divider/>
            <el-table  :data="assistList" style="margin: 20px; width: 96%">
              <el-table-column label="人员姓名" align="center" prop="staff_name" />
              <el-table-column label="手机号" align="center" prop="staff_phone" />
              <el-table-column label="身份证号" align="center" prop="card_no" />
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
    </div>
  </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {query,queryAssist,queryRepair} from "@/api/estate/work/orderLedger/parkLedger"
import {detailRecordGeneral} from "@/api/estate/work/orderDisaster/DisasterOrder";
export default {
  name: "parkLedger",
  data(){
    return{
      repairList:[],
      reverse:true,
      dialogVisible:false,
      drawer: false,
      info:{},
      filterText:'',
      detailRecordList:[],
      default_id:'',
      iconClasses: ['icon-rate-face-1', 'icon-rate-face-2', 'icon-rate-face-3'],
      data:[],
      // 总条数
      total: 0,
      //
      dataList:[],
      assistList:[],
      // 遮罩层
      loading: true,
      queryParams:{
        pageNum: 1,
        order_name:'',
        pageSize: 10,
        repair_type:'',
        accept_person:''
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
    show(row){
      this.drawer=true;
      this.info=row;
      queryAssist({id:row.order_id}).then(e=>{
        this.assistList=e.data
      })
      detailRecordGeneral({orderType:'7',orderId:row.order_id}).then(e=>{
        this.detailRecordList=e.data
      })
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
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
          queryRepair().then(e=>{
            this.repairList=e.data
          })
          this.getList();
        }else {
          this.$message.error('暂无小区')
        }
      })
    },
    //获取点击小区id
    getCommunityId(e){
      this.queryParams['community_id']=e.id
      this.getList()
    },
    getList(){
      query(this.queryParams).then(e=>{

        this.dataList=e.rows
        this.total=e.total
        this.loading=false
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

</style>

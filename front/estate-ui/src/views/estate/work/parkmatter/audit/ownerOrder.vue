<template>
    <div class="app-container">
    <div v-show="listFlag">
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
            <el-form-item label="名称" prop="auditName">
              <el-input
                v-model="queryParams.auditName"
                placeholder="请输入"
                clearable
              />
            </el-form-item>
            <el-form-item label="申请人" prop="ownerName">
              <el-input
                v-model="queryParams.ownerName"
                placeholder="请输入"
                clearable
              />
            </el-form-item>
            <el-form-item label="状态" prop="state">
              <el-select
                v-model="queryParams.state"
                placeholder="请选择状态"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="status in statusList"
                  :key="status.dictValue"
                  :label="status.dictLabel"
                  :value="status.dictValue"
                />
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>
          <el-table v-loading="loading" :data="dataList">
            <el-table-column label="名称" align="center" prop="audit_name" show-overflow-tooltip/>
            <el-table-column label="申请人" align="center" prop="owner_name" show-overflow-tooltip/>
            <el-table-column label="状态" align="center" show-overflow-tooltip>
              <template slot-scope="scope">
                <el-tag type="warning" v-show="scope.row.audit_status=='0'">{{getStateLabel(scope.row.audit_status)}}</el-tag>
                <el-tag type="success" v-show="scope.row.audit_status=='1'">{{getStateLabel(scope.row.audit_status)}}</el-tag>
                <el-tag type="danger" v-show="scope.row.audit_status=='2'">{{getStateLabel(scope.row.audit_status)}}</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="show(scope.row)"
                >查看</el-button>
                <template v-if="scope.row.audit_status=='0'">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-check"
                    style="color: #0DCB0F"
                    v-hasPermi="['work:parkaudit:pass']"
                    @click="pass(scope.row)"
                  >通过</el-button>
                  <el-button
                    size="mini"
                    type="text"
                    style="color:red;"
                    v-hasPermi="['work:parkaudit:rebut']"
                    icon="el-icon-close"
                    @click="dialogVisible=true;form['auditId']=scope.row.audit_id;form['communityId']=scope.row.community_id"
                  >驳回</el-button>
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
      <div v-show="drawer"
      >
        <div style="margin: 15px">
          <div>
            <span>详情</span>
            <el-button @click="drawer=false;listFlag=true" style="margin-top: 15px;margin-left: 90%">关 闭</el-button>
            <el-divider/>
            <el-descriptions :label-style="{'line-height':'40px'}" :content-style="{'line-height':'40px'}" style="margin: 20px" :column="3">
              <el-descriptions-item label="名称">{{ info.audit_name }}</el-descriptions-item>
              <el-descriptions-item label="报事类型">园区报事</el-descriptions-item>
              <el-descriptions-item label="报事类型">{{info.repair_name}}</el-descriptions-item>
              <el-descriptions-item label="报事地址">{{ info.address }}</el-descriptions-item>
              <el-descriptions-item label="发布人">{{info.owner_name}}</el-descriptions-item>
              <el-descriptions-item label="所属小区">{{info.community_name}}</el-descriptions-item>
              <el-descriptions-item label="手机号">{{info.owner_phone}}</el-descriptions-item>
              <el-descriptions-item label="身份证号" :span="2">{{info.card_no}}</el-descriptions-item>
              <el-descriptions-item label="上传图片" :span="3">
                <el-image  :src="item.image_url" fit="contain"  style="width: 180px; height: 100px" v-for="(item,index) of info.orderImg" :key="index+'i'"/>
              </el-descriptions-item>
              <el-descriptions-item label="提交人备注" :span="3">{{ info.remark }}</el-descriptions-item>
            </el-descriptions>
          </div>
          <div style="margin-top: 50px;">
            <span>审批详情</span>
            <el-divider/>
            <el-descriptions :label-style="{'line-height':'40px'}" :content-style="{'line-height':'40px'}" style="margin: 20px" :column="3">
              <el-descriptions-item label="申批状态">
                {{getStateLabel(info.audit_status)}}
              </el-descriptions-item>
              <el-descriptions-item label="驳回原因" v-if="info.audit_status === '2'">{{ info.reject_reason }}</el-descriptions-item>
              <el-descriptions-item label="审批人">{{info.audit_by}}</el-descriptions-item>
              <el-descriptions-item label="审批时间">{{parseTime(info.audit_time)}}</el-descriptions-item>
            </el-descriptions>
          </div>
        </div>
      </div>
      <el-dialog
        title="工单驳回"
        :show-close="false"
        :visible.sync="dialogVisible"
        width="30%">
        <el-form ref="form" :model="form" :rules="rules" label-width="80px">
          <el-form-item label="驳回理由" prop="remark">
            <el-input
              type="textarea"
              :rows="2"
              placeholder="请输入内容"
              :maxLength="450"
              v-model="form.remark">
            </el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
            <el-button @click="dialogVisible = false;form={};">关 闭</el-button>
            <el-button type="primary" @click="rebut">确 定</el-button>
          </span>
      </el-dialog>
    </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {getDicts} from "@/api/system/dict/data";
import {search,passState,rebutState,detail} from '@/api/estate/work/parkmatter/audit'
import {desensitization} from "@/utils/common";
import moment from "moment";
  //业主园区工单申请
  export default {
    name: "ownerOrder",
    data(){
      return{
        listFlag:true,
        disasterList:[],
        dialogVisible:false,
        drawer: false,
        info:{},
        filterText:'',
        default_id:'',
        data:[],
        // 总条数
        total: 0,
        form:{},
        // 表单校验
        rules: {
          remark: [
            {required: true, message: "驳回理由不能为空", trigger: "blur"}
          ],
        },
        rulesPark:{
          planStart: [
            { required: true, message: "请选择开始时间", trigger: ["blur", "change"] }
          ],
          planEnd: [
            { required: true, message: "请选择结束时间", trigger: ["blur", "change"] }
          ],
        },
        //
        dataList:[],
        executeStatus:[],
        statusList:[],
        // 遮罩层
        loading: true,
        queryParams:{
          pageNum: 1,
          pageSize: 10,
          auditName:'',
          ownerName:'',
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
      show(row){

        detail({auditId:row.audit_id}).then(e=>{
          this.drawer=true;
          this.listFlag=false
          this.info=e.data
        })
      },
      // 查询系统状态字典
      getStatus() {
        getDicts("sys_oldman_auditstatus").then(response => {
          this.statusList = response.data;
        });
        getDicts("execute_status").then(response => {
          this.executeStatus = response.data;
        });
      },
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      getStateLabel(value){
        if (value){
          var filter = this.statusList.filter(e=>e.dictValue==value);

          return filter[0].dictLabel
        }else {
          return  ''
        }

      },
      //驳回
      rebut(){
        this.$refs["form"].validate(valid => {
          if (valid) {
            this.$modal.confirm('是否确认驳回？').then(()=>{
              this.form['state']='2'
              return rebutState(this.form);
            }).then(() => {
              this.$modal.msgSuccess("驳回成功!!");
              this.dialogVisible=false
              this.getList();
            })
          }
        })
      },
      //通过
      pass(row){
              this.$modal.confirm('是否确认通过？').then(()=>{
                let formPark = {
                  state: '1',
                  auditId : row.audit_id,
                  communityId : row['community_id'],
                  orderName : row.audit_name,
                  remark : row.remark
                }
                console.log(formPark);
                return passState(formPark);
              }).then((e) => {
                const {msg,code}=e
                if (code==200){
                  this.$modal.msgSuccess(msg);
                }else {
                  this.$modal.msgError(msg);
                }
                this.getList();
              })
      },
      getExecuteStateLabel(value){
        if (value){
          let filter = this.executeStatus.filter(e=>e.dictValue==value);

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
      },
      //获取点击小区id
      getCommunityId(e){
        this.queryParams['community_id']=e.id
        this.getList()
      },
      getList(){
        search(this.queryParams).then(e=>{
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

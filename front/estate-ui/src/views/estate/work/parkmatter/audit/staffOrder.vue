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
         <el-form
           :model="queryParams"
           ref="queryForm"
           size="small"
           :inline="true"
         >
           <el-form-item label="工单编号" prop="orderNo">
             <el-input
               v-model="queryParams.orderNo"
               placeholder="请输入工单编号"
               style="width: 170px"
             />
           </el-form-item>
           <el-form-item label="工单名称" prop="orderName">
             <el-input
               v-model="queryParams.orderName"
               placeholder="请输入工单名称"
               style="width: 170px"
             />
           </el-form-item>
           <el-form-item label="申请人" prop="applyName">
             <el-input
               v-model="queryParams.applyName"
               placeholder="请输入申请人姓名"
               style="width: 170px"
             />
           </el-form-item>
           <el-form-item label="申请类别" prop="auditType">
             <el-select
               clearable
               v-model="queryParams.auditType"
               placeholder="请选择申请类别"
               style="width: 170px"
             >
               <el-option
                 v-for="auditType in auditTypeList"
                 :key="auditType.value"
                 :label="auditType.label"
                 :value="auditType.value"
               />
             </el-select>
           </el-form-item>
           <el-form-item label="状态" prop="auditStatus">
             <el-select
               clearable
               v-model="queryParams.auditStatus"
               placeholder="请选择状态"
               style="width: 120px"
             >
               <el-option
                 v-for="dict in auditStatusList"
                 :key="dict.dictValue"
                 :label="dict.dictLabel"
                 :value="dict.dictValue"
               />
             </el-select>
           </el-form-item>

           <el-form-item>
             <el-button
               type="primary"
               icon="el-icon-search"
               size="mini"
               @click="handleQuery"
             >搜索
             </el-button
             >
             <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
             >重置
             </el-button
             >
           </el-form-item>
         </el-form>
         <!-- 列表展示 -->
         <el-table
           v-loading="loading"
           :data="list"
         >
           <el-table-column
             label=" 工单编号"
             align="center"
             prop="orderNo"
             type="number"
             :show-overflow-tooltip="true"
           />
           <el-table-column
             label="工单名称"
             align="center"
             prop="orderName"
             :show-overflow-tooltip="true"
           >
           </el-table-column>
           <el-table-column
             label="申请人"
             align="center"
             prop="applyName"
             :show-overflow-tooltip="true"
           />
           <el-table-column
             label="申请类别"
             align="center"
             prop="auditType"
             :show-overflow-tooltip="true"
             :formatter="auditTypeChange"
           />
           <el-table-column
             label="状态"
             align="center"
             prop="auditStatus"
             :show-overflow-tooltip="true"
             :formatter="auditStatusChange"
           />
           <el-table-column
             label="操作"
             align="center"
             width="190"
             class-name="small-padding fixed-width"
           >
             <template slot-scope="scope">
               <el-button
                 size="mini"
                 type="text"
                 @click="detail(scope.row)"
                 icon="el-icon-view">
                 详情</el-button>
               <el-button
                 size="mini"
                 icon="el-icon-check"
                 type="text"
                 style="color: #0DCB0F"
                 v-hasPermi="['work:orderaudit:adopt']"
                 @click="adopt(scope.row)"
                 v-show="scope.row.auditStatus == '0'">
                 通过</el-button
               >
               <el-button
                 size="mini"
                 type="text"
                 v-hasPermi="['work:orderaudit:reject']"
                 style="color:red;"
                 icon="el-icon-close"
                 @click="reject(scope.row)"
                 v-show="scope.row.auditStatus == '0'">
                 驳回</el-button>
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
       </el-col>
     </el-row>
   </div>
    <div v-show="drawer"
    >
      <div style="margin: 15px">
        <div>
          <span>工单详情</span>
          <el-button @click="drawer=false;listFlag=true" style="margin-top: 15px;margin-left: 90%">关 闭</el-button>
          <el-divider/>
          <el-descriptions :label-style="{'line-height':'40px'}" :content-style="{'line-height':'40px'}" style="margin: 20px" :column="2">
            <el-descriptions-item label="工单编号">{{ detailForm.orderNo }}</el-descriptions-item>
            <el-descriptions-item label="工单名称">{{ detailForm.orderName }}</el-descriptions-item>
          </el-descriptions>
          <el-descriptions :label-style="{'line-height':'40px'}" :content-style="{'line-height':'40px'}" style="margin: 20px" :column="2" v-show="detailForm.auditType==='0'">
            <el-descriptions-item label="接收人">{{ detailForm.receiveName }}</el-descriptions-item>
            <el-descriptions-item label="接收人手机号">{{detailForm.receivePhone}}</el-descriptions-item>
          </el-descriptions>
        </div>
        <div v-if="detailForm.auditType==='1'" style="margin-top: 20px">
          <span>工单协助人</span>
          <el-divider/>
          <el-table :data="detailForm.staffList">
            <el-table-column label="人员姓名" align="center" key="staffName" prop="staffName"/>
            <el-table-column label="手机号" align="center" key="staffPhone">
              <template slot-scope="scope">
                {{scope.row.staffPhone}}
              </template>
            </el-table-column>
            <el-table-column label="身份证号" align="center" key="cardNo">
              <template slot-scope="scope">
                  {{scope.row.cardNo}}
              </template>
            </el-table-column>
            <el-table-column label="人员类别" align="center" key="workPost" prop="workPost"/>
          </el-table>
        </div>
        <div style="margin-top: 20px">
          <span>审批详情</span>
          <el-divider/>
          <el-descriptions :label-style="{'line-height':'40px'}" :content-style="{'line-height':'40px'}" style="margin: 20px" :column="4">
            <el-descriptions-item label="申请类别">{{ auditTypeFormat(detailForm.auditType) }}</el-descriptions-item>
            <el-descriptions-item label="申请人"> {{ detailForm.applyName }}</el-descriptions-item>
            <el-descriptions-item label="审批状态"> {{ auditStatusFormat(detailForm.auditStatus) }}</el-descriptions-item>
            <el-descriptions-item label="审批人"> {{ detailForm.userName }}</el-descriptions-item>
            <el-descriptions-item label="审批时间"> {{ detailForm.auditTime }}</el-descriptions-item>
            <el-descriptions-item label="驳回原因" v-if="detailForm.auditStatus === '2'"> {{ detailForm.rejectReason }}</el-descriptions-item>
          </el-descriptions>
        </div>
      </div>
    </div>
    <!-- 驳回弹窗 -->
    <el-dialog :visible.sync="auditOpen" width="630px" append-to-body>
      <el-form ref="auditForm" :model="auditForm" :rules="rules" label-width="130px">
        <el-row>
          <el-col>
            <el-form-item label="驳回理由: " prop="rejectReason">
              <el-input
                type="textarea"
                v-model="auditForm.rejectReason"
                :maxlength="500"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :offset="10" :span="2">
            <div style="width: 280px">
              <el-button type="primary" @click="shareSuccess">确定</el-button>
              <el-button @click="auditOpen=false;auditForm={}">关闭</el-button>
            </div>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>
  </div>
</template>

<script>
  import {getCurrentList} from "@/api/estate/community";
  import {adopt, detail, listPage, reject} from "@/api/estate/work/inspect/examine";
  import {desensitization} from "@/utils/common";
  //物业员工园区工单申请
  export default {
    name: "staffOrder",
    data(){
      return{
        // 驳回弹窗
        auditOpen: false,
        listFlag:true,
        drawer:false,
        filterText:'',
        // 审核表单
        auditForm: {},
        default_id:'',
        detailForm:{},
        // 表格数据
        list: [],
        loading:false,
        data:[],
        // 总条数
        total: 0,
        form:{},
        // 申请类别列表
        auditTypeList: [{label: '工单转交', value: '0'}, {label: '工单协助', value: '1'}],
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          orderType: '7',
          orderNo:'',
          orderName:'',
          applyName:'',
          auditType:'',
          auditStatus:''
        },
        auditStatusList:[],
        rules: {
          rejectReason: [
            {required: true, message: "驳回理由不能为空", trigger: "blur"}
          ],
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
      // 驳回弹窗确定按钮
      shareSuccess() {
        reject(this.auditForm).then(() => {
          this.getList();
          this.$modal.msgSuccess("驳回成功！");
          this.auditOpen = false;
        }).catch(() => {
        });
      },
      // 驳回弹窗
      reject(row) {
        this.auditOpen = true;
        this.auditForm.auditId = row.auditId;
      },
      // 分配方式格式化
      auditStatusFormat(auditStatusId) {
        for (const auditStatus of this.auditStatusList) {
          if (auditStatus.dictValue === auditStatusId) {
            return auditStatus.dictLabel;
          }
        }
      },
      // 分配方式格式化
      auditTypeFormat(auditTypeId) {
        for (const auditType of this.auditTypeList) {
          if (auditType.value === auditTypeId) {
            return auditType.label;
          }
        }
      },
      // 通过按钮
      adopt(row) {
        this.auditForm.auditId = row.auditId;
        this.$confirm('是否确认要通过?', "警告", {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning"
        }).then( ()=>{
          return  adopt(this.auditForm);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess('通过成功!!');
        })
      },
      /** 详情 */
      detail(row) {
        this.drawer = true;
        this.listFlag=false
        this.selectView(row);
      },
      selectView(row) {
        this.queryParams.auditId = row.auditId
        detail(this.queryParams).then((response) => {

          this.detailForm = response.data;
        });

      },
      //获取点击小区id
      getCommunityId(e){
        this.queryParams['communityId']=e.id
        this.getList()
      },
      // 审核状态转换
      auditStatusChange(row) {
        for (const auditStatus of this.auditStatusList) {
          if (auditStatus.dictValue === row.auditStatus) {
            return auditStatus.dictLabel;
          }
        }
      },
      // 获取分配方式列表
      getAuditStatusList() {
        this.getDicts("sys_oldman_auditstatus").then(response => {
          this.auditStatusList = response.data;
        });
      },
      auditTypeChange(row) {
        if (row.auditType === "0") {
          return "工单转交";
        }
        if (row.auditType === "1") {
          return "工单协助";
        }
      },
      /** 搜索按钮操作 */
      handleQuery() {
        this.queryParams.pageNum = 1;
        this.getList();
      },
      /** 重置按钮操作 */
      resetQuery() {

        this.resetForm("queryForm");
        this.handleQuery();
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
            this.queryParams['communityId']=data[0].communityId
            this.default_id=data[0].communityId

            this.$nextTick(() => {
              this.$refs['tree'].setCurrentKey(this.default_id);
            });
            this.getList();
          }else {
            this.$message.error('暂无小区')
          }
        })
        this.getAuditStatusList()
      },
      filterNode(value, data) {
        if (!value) return true;
        return data.label.indexOf(value) !== -1;
      },
      getList(){
        this.loading = false;
        listPage(this.queryParams).then((response) => {
          this.list = response.rows;
          this.total = response.total;
        });
      }
    }
  }
</script>

<style scoped>
/*设置高亮颜色*/
/deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #b3f1b6 !important;
}
</style>

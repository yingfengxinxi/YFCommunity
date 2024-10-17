<template>
  <div class="app-container">
    <div v-show="view === 1">
      <el-row :span="24">
        <!--   选择树   -->
        <el-col :span="4">
          <div class="head-container">
            <el-input
              placeholder="请输入小区名称"
              v-model="filterText"
           clearable
            >
            </el-input>

            <el-tree
              class="filter-tree"
              :data="currentList"
              :props="currentListProps"
              node-key="communityId"
              default-expand-all
              :filter-node-method="filterNode"
              icon-class="el-icon-star-on"
              highlight-current
              @node-click="handleNodeClick"
              :current-node-key="queryParams.communityId"
              ref="tree">
            </el-tree>
          </div>
        </el-col>
        <!--   右侧   -->
        <el-col :span="20">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
            <el-form-item label="类型" prop="complaintType">
              <el-select v-model="queryParams.complaintType" placeholder="类型" clearable >
                <el-option
                  v-for="dict in dict.type.sys_praise_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>

            <el-form-item label="提交人" prop="accountName">
              <el-input
                v-model="queryParams.accountName"
                placeholder="请输入提交人姓名"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            <el-form-item label="数据源" prop="dataSource">
              <el-select v-model="queryParams.dataSource" placeholder="数据源" clearable >
                <el-option
                  v-for="dict in dict.type.sys_conplaint_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>

            <el-form-item label="提交时间" prop="dateRange">
              <el-date-picker
                v-model="queryParams.dateRange"
                style="width: 215px"
                value-format="yyyy-MM-dd"
                type="daterange"
                range-separator="-"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
              ></el-date-picker>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>

          <el-table
            v-if="refreshTable"
            v-loading="loading"
            :data="praiseList"
          >
            <el-table-column prop="title" label="标题" width="150" align="center"></el-table-column>
            <el-table-column prop="complaintType" label="类型" width="100" align="center">
              <template slot-scope="scope">
                <div v-if="scope.row.complaintType == '0' ">业主表扬</div>
                <div v-if="scope.row.complaintType == '1' ">业主投诉</div>
              </template>
            </el-table-column>
            <el-table-column prop="orderId" label="关联工单号"   width="300" align="center"></el-table-column>
            <el-table-column prop="accountName" label="提交人"   width="100" align="center"></el-table-column>
            <el-table-column prop="createTime" label="提交时间"   width="200" align="center"></el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <div v-if="scope.row.publicStatus == 0">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-view"
                    @click="handleSelect(scope.row)"
                    v-hasPermi="['bussiness:praise:list']"
                  >查看</el-button>

                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-view"
                    @click="handleView(scope.row)"
                    v-hasPermi="['bussiness:praise:add']"
                  >公示</el-button>
                </div>
                <div v-if="scope.row.publicStatus == 1">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-view"
                    @click="handleView1(scope.row)"
                    v-hasPermi="['bussiness:praise:list']"
                  >已公示</el-button>
                </div>
              </template>
            </el-table-column>
          </el-table>
          <!-- 分页  -->
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
    <!--  查看详情页  -->
    <div v-show="view === 2">
      <div>
        <span>基础信息</span>
        <el-divider></el-divider>
        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">编号: </span>{{detailObj.complaintId}}
          </el-col>

          <el-col :span="8">
            <span class="span-font">标题:</span>{{detailObj.title}}
          </el-col>

          <el-col :span="8">
            <span class="span-font">类型:</span><span v-if="detailObj.complaintType == '0'">业主表扬</span><span v-else>业主投诉</span>
          </el-col>
        </el-row>

        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">提交人:</span>{{detailObj.accountName}}
          </el-col>

          <el-col :span="8">
            <span class="span-font">联系电话:</span>{{detailObj.loginName}}
          </el-col>

          <el-col :span="8">
            <span class="span-font">提交时间:</span>{{parseTime(detailObj.createTime)}}
          </el-col>
        </el-row>

        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">表扬内容:</span><div style="display: inline-block;width: 60%;vertical-align: middle">{{detailObj.remark}}</div>
          </el-col>

          <el-col :span="8">
            <span class="span-font">上传图片:</span><el-image
                                                      style="width: 80px; height: 50px;vertical-align: middle"
                                                      :src="detailObj.complaintUrl"
                                                      fit="scale-down"></el-image>
          </el-col>

          <el-col :span="8">
            <span class="span-font">是否关联工单:</span><span v-if="detailObj.orderId == 0 || detailObj.orderId == undefined">否</span><span v-else>是</span>
          </el-col>
        </el-row>
      </div>

      <div>
        <span>工单详情</span>
        <el-divider></el-divider>
        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">工单编号:</span>
          </el-col>

          <el-col :span="8">
            <span class="span-font">工单名称:</span>
          </el-col>

          <el-col :span="8">
            <span class="span-font">工单类型:</span>
          </el-col>
        </el-row>

        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">维修类型:</span>
          </el-col>

          <el-col :span="8">
            <span class="span-font">上传图片:</span>
          </el-col>

          <el-col :span="8">
            <span class="span-font">维修地址:</span>
          </el-col>
        </el-row>

        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">工单状态:</span>
          </el-col>

          <el-col :span="8">
            <span class="span-font">订单提交时间:</span>
          </el-col>

          <el-col :span="8">
            <span class="span-font">接单时间:</span>
          </el-col>
        </el-row>

        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">完成时间:</span>
          </el-col>
        </el-row>
      </div>


     <div>
       <span>执行人详情</span>
       <el-divider></el-divider>
       <el-row :span="24">
         <el-col :span="8">
           <span class="span-font">执行人:</span>
         </el-col>

         <el-col :span="8">
           <span class="span-font">手机号:</span>
         </el-col>

         <el-col :span="8">
           <span class="span-font">人员类别:</span>
         </el-col>
       </el-row>
     </div>


      <div>
        <span>订金详情</span>
        <el-divider></el-divider>
        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">金额:</span>
          </el-col>
        </el-row>
      </div>


     <div>
       <span>执行详情</span>
       <el-divider></el-divider>
       <el-timeline>

       </el-timeline>
     </div>

      <el-row :span="24">
        <el-col :span="8" :offset="21">
          <el-button @click="cancel">取 消</el-button>
        </el-col>
      </el-row>

    </div>

    <div v-show="view == 3">
      <div>
        <span>公告详情</span>
        <el-divider></el-divider>
        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">标题: </span>{{publicDetails.title}}
          </el-col>

          <el-col :span="8">
            <span class="span-font">发布范围:</span>{{publicDetails.communityName}}
          </el-col>

          <el-col :span="8">
            <span class="span-font">发布时间:</span>{{parseTime(publicDetails.publicTime)}}
          </el-col>
        </el-row>

        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">阅读数:</span>
          </el-col>

          <el-col :span="8">
            <span class="span-font">状态:</span><span v-if="publicDetails.status == '0'">未公示</span><span v-else>已公示</span>
          </el-col>

        </el-row>
      </div>

      <div>
        <span>表扬基本信息</span>
        <el-divider></el-divider>
        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">编号:</span>{{publicDetails.complaintId}}
          </el-col>

          <el-col :span="8">
            <span class="span-font">标题:</span>{{publicDetails.title}}
          </el-col>

          <el-col :span="8">
            <span class="span-font">类型:</span><span v-if="publicDetails.complaintType == '0'">业主表扬</span><span v-else>业主投诉</span>
          </el-col>
        </el-row>

        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">提交人:</span> {{publicDetails.accountName}}
          </el-col>

          <el-col :span="8">
            <span class="span-font">联系电话:</span>{{publicDetails.phone,3,7}}
          </el-col>

          <el-col :span="8">
            <span class="span-font">提交时间:</span>{{parseTime(publicDetails.createTime)}}
          </el-col>
        </el-row>

        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">内容:</span><div style="width: 80%;vertical-align: top;display: inline-block">{{publicDetails.remark}}</div>
          </el-col>

          <el-col :span="8">
            <span class="span-font">上传图片:</span><el-image
            style="width: 80px; height: 50px;vertical-align: middle"
            :src="publicDetails.pictureUrl"
            fit="scale-down"></el-image>
          </el-col>
        </el-row>

        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">是否关联工单:</span><span v-if="publicDetails.orderId == 0 || publicDetails.orderId == undefined">否</span><span v-else>是</span>
          </el-col>
        </el-row>
      </div>



      <div>
        <span>工单信息</span>
        <el-divider></el-divider>
        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">工单编号:</span>{{publicDetails.orderNo}}
          </el-col>

          <el-col :span="8">
            <span class="span-font">工单名称:</span>{{publicDetails.orderName}}
          </el-col>

          <el-col :span="8">
            <span class="span-font">工单类型:</span><span v-if="publicDetails.orderType == '0'">无</span><span v-if="publicDetails.orderType == '1'">居家维修</span>
            <span v-if="publicDetails.orderType == '2'">园区报事</span>
            <span v-if="publicDetails.orderType == '3'">灾害报事</span>
          </el-col>
        </el-row>

        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">维修类型:</span> {{publicDetails.repairName}}
          </el-col>

          <el-col :span="8">
            <span class="span-font">上传图片:</span><el-image
            style="width: 80px; height: 50px;vertical-align: middle"
            :src="publicDetails.imageUrl"
            fit="scale-down"></el-image>
          </el-col>

          <el-col :span="8">
            <span class="span-font">维修地址:</span>{{publicDetails.address}}
          </el-col>
        </el-row>

        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">工单状态:</span><div style="width: 80%;vertical-align: top;display: inline-block">{{publicDetails.executeStatus}}</div>
          </el-col>
          <el-col :span="8">
            <span class="span-font">订单提交时间:</span><div style="width: 80%;vertical-align: top;display: inline-block">{{publicDetails.SubmitTime}}</div>
          </el-col>
          <el-col :span="8">
            <span class="span-font">接单时间:</span><div style="width: 80%;vertical-align: top;display: inline-block">{{publicDetails.executeTime}}</div>
          </el-col>

        </el-row>

        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">完成时间:</span><div style="width: 80%;vertical-align: top;display: inline-block">{{publicDetails.finishTime}}</div>
          </el-col>
        </el-row>

      </div>








      <div>
        <span>执行人详情</span>
        <el-divider></el-divider>
        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">执行人:</span><div style="width: 80%;vertical-align: top;display: inline-block">{{publicDetails.staffName}}</div>
          </el-col>

          <el-col :span="8">
            <span class="span-font">手机号:</span><div style="width: 80%;vertical-align: top;display: inline-block">{{publicDetails.staffPhone}}</div>
          </el-col>

          <el-col :span="8">
            <span class="span-font">人员类别:</span><div style="width: 80%;vertical-align: top;display: inline-block">{{publicDetails.workPost}}</div>
          </el-col>
        </el-row>
      </div>

      <div>
        <span>定金详情</span>
        <el-divider></el-divider>
        <el-row :span="24">
          <el-col :span="8">
            <span class="span-font">金额:</span><div style="width: 80%;vertical-align: top;display: inline-block">{{publicDetails.deposit}}</div>
          </el-col>
        </el-row>
      </div>

      <span>执行详情</span>
      <el-divider></el-divider>
      <el-row :span="24">
        <el-col :span="4">{{parseTime(publicDetails.executeTime)}}  </el-col>
        <el-col :span="4">{{publicDetails.submitName}}  </el-col>
        <el-col :span="4">主要负责人: {{publicDetails.staffName}}  </el-col>
      </el-row>


      <el-row :span="24" v-if="publicDetails.coTime != null">
        <el-col :span="4">{{parseTime(publicDetails.coTime)}}  </el-col>
        <el-col :span="4">{{publicDetails.submitName}}  </el-col>
        <el-col :span="4">转交人: {{publicDetails.coName}}  </el-col>
        <el-col :span="4">接收人: {{publicDetails.receiveName}}  </el-col>
      </el-row>

      <el-row :span="24">
        <el-col :span="4">{{parseTime(publicDetails.executeTime)}}  </el-col>
        <el-col :span="4">{{publicDetails.acceptType}}  </el-col>
        <el-col :span="4">审核人: {{publicDetails.examineTime}}  </el-col>
      </el-row>

      <el-row :span="24">
        <el-col :span="4">{{parseTime(publicDetails.submitTime)}}  </el-col>
        <el-col :span="4">{{publicDetails.dataType}}  </el-col>
        <el-col :span="4">接单人: {{publicDetails.submitName}}  </el-col>
      </el-row>
      <div>


        <el-row :span="24">
          <el-col :span="8" :offset="21">
            <el-button @click="cancel2">取 消</el-button>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import {getTableListWithPraise,getTableListById,add,publicDetail} from '@/api/estate/customer/complanint/complanint';
import {getCurrentList} from "@/api/estate/community";
import {desensitization} from "@/utils/common";
import {addLease} from "@/api/estate/manage/contract/lease";
export default {
  name: "Praise",
  dicts: ['sys_conplaint_type','sys_praise_type'],
  data(){
    return {

      centerDialogVisible:false,
      showClose: false,
      // 树筛选文本
      filterText:'',
      // 详情显示
      view: 1,
      // 总数
      total: 0,
      // 遮罩层
      loading: false,
      // 重新渲染表格状态
      refreshTable: true,
      // 搜索显示
      showSearch: true,
      // 查询条件
      queryParams:{
        pageNum: 1,
        pageSize: 10,
        communityId:undefined,
        communityName:undefined,
        complaintType: undefined,
        accountName: undefined,
        dataSource: undefined,
        dateRange:[]
      },
      // 表扬列表
      praiseList: [],
      // 查看页数据
      detailObj: {},
      // 树结构
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      //小区数据集合
      currentList:[],
      form:{},
      publicDetails:{},
      //校验
      rules:{
        title: [
          {required: true, message: "标题不能为空", trigger: "blur"}
        ],
        remark: [
          {required: true, message: "公告内容不能为空", trigger: "blur"}
        ]
      }
    }
  },
  created() {
    this.getTree();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods:{
    // 过滤树
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 获取树数据
    getTree(){
      getCurrentList().then(res =>{
        this.queryParams.communityId = res.data[0].communityId
        this.currentList = res.data;
        this.getList();

        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.currentList[0].communityId);
        });

      })
    },
    // 重置表单
    reset(){
      this.detailObj = []
    },
    // 列表
    getList(){
      this.loading = true;
      getTableListWithPraise(this.addDateRange(this.queryParams, this.queryParams.dateRange)).then(resp => {
        this.total = resp.total;
        this.praiseList = resp.rows;
        this.loading = false;
      })
    },
    // 搜索按钮
    handleQuery(){
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 重置按钮
    resetQuery(){
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        communityId:this.queryParams.communityId,
        communityName:undefined,
        complaintType: undefined,
        accountName: undefined,
        dataSource: undefined,
        submitDate: []
      }
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 表格查看按钮
    handleSelect(row){
      this.view = 2
      getTableListById(row.complaintId).then(resp => {
        this.detailObj = resp.data
        console.log(this.detailObj);
        if (this.detailObj.phone !== undefined || this.detailObj.phone !== null ){
          this.detailObj.phone = desensitization(this.detailObj.phone,3,7)
        }
      })
    },
    // 取消
    cancel(){
      this.view = 1
      this.reset()
    },
    // 树点击事件
    handleNodeClick(row){
      this.queryParams.communityId = row.communityId
      this.getList();
    },
    // 新增公示
    handleView(row){
      this.$confirm('此操作将公式到物业办, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        add(row.complaintId).then(resp => {
          this.$modal.msgSuccess(resp.msg)
          this.centerDialogVisible = false
          this.getList();
          this.$message({
            type: 'success',
            message: '操作成功!'
          });
        })
      }).catch(() => {
        this.$message({
          type: 'info',
          message: '已取消'
        });
      });
    },
    // 新增取消
    cancel1(){
      this.centerDialogVisible = false
      this.form = {}

    },
    // 已公示详情查看
    handleView1(row){
      publicDetail(row.complaintId).then(resp => {
        this.publicDetails  = resp.data
        if (this.publicDetails.phone !== undefined || this.publicDetails.phone !== null ){
          this.publicDetails.phone = desensitization(this.publicDetails.phone,3,7)
        }
        this.view = 3
      })
    },
    // 已公告取消
    cancel2(){
      this.view = 1
      this.publicDetails = {}
    }
  },
};
</script>

<style scoped>
.span-font {
  display: inline-block;
  font-size: 16px;
  color: gray;
  margin-bottom: 50px;
}

/deep/ .el-dialog__header{
  background-color: #409EFF;
  color: white;
  font-size: 20px;
}
/deep/ .el-dialog__title{
  color: white;
  font-size: 22px;
}


/*设置高亮颜色*/
/deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #b3f1b6 !important;
}

</style>


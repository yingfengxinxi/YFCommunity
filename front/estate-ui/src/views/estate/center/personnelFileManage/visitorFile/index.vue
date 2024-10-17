<template>
  <div class="app-container">
    <!-- list-->
    <div v-show="isList">
      <el-row style="margin-top: 20px;margin-left: 15px">
        <el-col :span="4">
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
        <el-col style="margin-left: 15px" :span="19">
          <el-row>
            <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
              <el-form-item label="访客姓名" prop="visitorName">
                <el-input
                  v-model="queryParams.visitorName"
                  placeholder="请输入访客姓名"
                  clearable
                />
              </el-form-item>
              <el-form-item label="访客手机号码" prop="visitorPhone">
                <el-input
                  v-model="queryParams.visitorPhone"
                  placeholder="请输入手机号码"
                  clearable
                />
              </el-form-item>
              <el-form-item label="拜访业主名称" prop="ownerName">
                <el-input
                  v-model="queryParams.ownerName"
                  placeholder="请输入拜访业主名称"
                  clearable
                />
              </el-form-item>
              <el-form-item label="拜访业主手机号" prop="ownerPhone">
                <el-input
                  v-model="queryParams.ownerPhone"
                  placeholder="请输入拜访业主手机号"
                  clearable
                />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
              </el-form-item>
            </el-form>
          </el-row>
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button
                type="info"
                plain
                icon="el-icon-upload2"
                size="mini"
                @click="handleImport"
                >导入</el-button
              >
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="warning"
                plain
                icon="el-icon-download"
                size="mini"
                @click="handleExport"
              
              >导出</el-button>
            </el-col>
          </el-row>
          <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange" ref="multipleTable">
            <el-table-column type="selection" width="50" align="center"/>
            <el-table-column label="访客姓名" align="center" prop="visitorName"/>
            <el-table-column label="访客手机号码" align="center" prop="visitorPhone"/>
            <el-table-column label="拜访地址" align="center" prop="address"/>
            <el-table-column label="访客人数" align="center" prop="withNum" />
            <el-table-column label="拜访时间" align="center" prop="beginTime" />
            <el-table-column label="有效时间" align="center" prop="endTime" />
            <el-table-column label="拜访业主姓名" align="center" prop="ownerName" />
            <el-table-column label="拜访业主手机号" align="center" prop="ownerPhone" />
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
    </div>
    <el-dialog
      :visible.sync="isShow"
      width="80%"
    >
      <el-form ref="form" :model="form"  label-width="130px" style="margin: 30px" :rules="rules">
        <h4 class="form-header">拜访人信息</h4>
        <el-row>
            <el-col :span="5" :offset="1">
              <el-form-item label="拜访人姓名:">{{ form.visitorName }}</el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="联系方式:">{{form.visitorPhone }}</el-form-item>
            </el-col>
            <el-col :span="4" :offset="2">
              <el-form-item label="访客人数:">{{form.withNum }}</el-form-item>
            </el-col>
          </el-row>
          <h4 class="form-header">车辆信息</h4>
          <el-row>
            <el-col :span="5" :offset="1">
              <el-form-item label="通行车辆:">{{ form.plantNo==''?'无':'有' }}</el-form-item>
            </el-col>
            <el-col :span="6" :offset="2" v-if="form.plantNo">
              <el-form-item label="车牌号:">{{ form.plantNo }}</el-form-item>
            </el-col>
          </el-row>
          <h4 class="form-header">通行信息</h4>
          <el-row>
            <el-col :span="6.5" :offset="1">
              <el-form-item label="拜访时间:">{{ form.beginTime}}</el-form-item>
            </el-col>
            <el-col :span="8" :offset="0">
              <el-form-item label="有效时间:" label-width="156px">{{ form.endTime }}</el-form-item>
            </el-col>
          </el-row>
          <h4 class="form-header">拜访地址信息</h4>
          <el-row>
            <el-col :span="5" :offset="1">
              <el-form-item label="拜访人姓名:">{{ form.ownerName}}</el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="拜访人联系方式:">{{ form.ownerPhone }}</el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="13" :offset="1">
              <el-form-item label="拜访地址:">{{form.address}}</el-form-item>
            </el-col>
          </el-row>

      </el-form>
      <div style="margin-left: 75%">
          <span slot="footer" class="dialog-footer">
               <el-button type="primary" @click="isShow=false">关 闭</el-button>
          </span>
      </div>
    </el-dialog>
<!-- 房屋导入对话框 -->
<el-dialog
      :title="uploadImprot.title"
      :visible.sync="uploadImprot.open"
      width="400px"
      append-to-body
    >
      <el-upload
        ref="uploadImprot"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="uploadImprot.headers"
        :action="uploadImprot.url + '?communityId=' + this.queryParams.communityId"
        :disabled="uploadImprot.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <!-- <div class="el-upload__tip" slot="tip">
                <el-checkbox v-model="upload.updateSupport" />
                是否更新已经存在的用户数据
              </div> -->
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link
            type="primary"
            :underline="false"
            style="font-size: 12px; vertical-align: baseline"
            @click="importTemplate"
            >下载模板</el-link
          >
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="uploadImprot.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getCurrentList} from  '@/api/estate/community'
import {desensitization} from '@/utils/common'
import { query, getVisitorById } from '@/api/estate/center/personnelFileManage/visitorFile'
import { getToken } from "@/utils/auth";
export default {
  name: "index",
  data() {
    return {
      default_id:'',
      date:'',
      //查找条件
      queryParams:{
        visitorPhone:undefined,
        visitorName: undefined,
        ownerPhone: undefined,
        ownerName:undefined,
        pageNum: 1,
        pageSize: 10,
      },
      timeline:false,
      isShow:false,
      alter:false,
      form:{
        visitorName:'',
        visitorPhone:'',
        cardNo:'',
      },
      visitor_num:'',
      account_id:'',
      liaisonList:[],
      // 表单校验
      rules: {
       
      },
      // 访客档案导入参数
      uploadImprot: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/estate/interview/importData",
      },
      isList:true,
      filterText: '',
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      ids:[],
      // 总条数
      total: 0,
      rentList:[],
      carPortList:[],
      carList:[],
      dataList:[],
      data: [],
      cardTypeList:[],
      // 遮罩层
      loading: true,
    };
  },

  created() {

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
        this.getList()
      }else {
        this.$message.error('暂无小区')
      }
    })
  },
  watch: {
    //小区过滤
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },

  methods: {
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    //详情
    show(row) {
      getVisitorById(row.visitorId).then(res => { 
        this.form = res.data;
        this.isShow=true
      })
    },
/** 导入按钮操作 */
handleImport() {
      this.uploadImprot.title = "访客档案导入";
      this.uploadImprot.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download(
        "estate/interview/importTemplate",
        {},
        `访客档案导入模板_${new Date().getTime()}.xlsx`
      );
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.uploadImprot.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.uploadImprot.open = false;
      this.uploadImprot.isUploading = false;
      this.$refs.uploadImprot.clearFiles();
      this.$alert(
        "<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" +
          response.msg +
          "</div>",
        "导入结果",
        { dangerouslyUseHTMLString: true }
      );
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.uploadImprot.submit();
    },
    /** 导出按钮操作 */
    handleExport() {
      this.queryParams.ids = this.ids;
      this.download('/estate/interview/export',{...this.queryParams}, `访客档案_${new Date().getTime()}.xlsx`)
      //清空多选按钮
      this.$refs.multipleTable.clearSelection();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.visitor_id)
      this.multiple = !selection.length
    },
    hide(str,begin,end){
      if (str){
        return  desensitization(str,begin,end);
      }
      return  "";
    },
    //查询数据
    getList() {
      this.loading = true;

      query(this.queryParams).then((e) => {
        this.dataList=e.rows
        this.total=e.total
        this.loading=false
      })
    },
    //获取点击小区id
    getCommunityId(e){
      this.queryParams['communityId']=e.id
      this.getList()
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
  },

}
</script>
<style scoped>
  .el-timeline-item__tail {
  border-left:2px solid rgb(254, 239, 193);
}
  /*设置高亮颜色*/
  /deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
    background-color: #b3f1b6 !important;
  }
</style>

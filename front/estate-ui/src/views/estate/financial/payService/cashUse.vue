<template>
  <div class="app-container">
    <div v-show="openView === 0">
      <el-row :span="24">
        <!--   选择树   -->
        <el-col :span="4">
          <div class="head-container">
            <el-input
              placeholder="请输入小区名称"
              v-model="filterText">
            </el-input>

            <el-tree
              class="filter-tree hide-tree"
              :data="currentList"
              :props="currentListProps"
              default-expand-all
              :filter-node-method="filterNode"
              icon-class="el-icon-star-on"
              @node-click="handleNodeClick"
              ref="tree">
            </el-tree>
          </div>
        </el-col>
        <!--   右侧   -->
        <el-col :span="20" :xs="24">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
            <el-form-item label="问卷名称" prop="voteTitle">
              <el-input
                v-model="queryParams.voteTitle"
                placeholder="请输入问卷名称"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="发起人" prop="ownerName">
              <el-input
                v-model="queryParams.ownerName"
                placeholder="请输入发起人"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="拨款情况" prop="grantStatus">
              <el-select v-model="queryParams.grantStatus" placeholder="请选择拨款情况" style="width: 180px">
                <el-option label="未拨款" value="0"/>
                <el-option label="已拨款" value="1"/>
              </el-select>
            </el-form-item>
            <el-form-item label="创建时间" prop="applyTime">
              <el-date-picker
                v-model="queryParams.applyTime"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item >
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" float=right @click="resetQuery">重置</el-button>
            </el-form-item>

          </el-form>
          <el-table v-loading="loading" :data="cashApplyList" >
            <el-table-column label="问卷名称" align="center" prop="voteTitle" show-overflow-tooltip/>
            <el-table-column label="发起人" align="center" prop="ownerName" />
            <el-table-column label="基金类型" align="center" prop="voteType" >
              <template slot-scope="scope">
                <el-tag v-if="scope.row.voteType == '0'" >住宅专项维修基金</el-tag>
                <el-tag v-else-if="scope.row.voteType == '1'" >公共维修基金</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="问卷发起时间" align="center" prop="applyTime" show-overflow-tooltip/>
            <el-table-column label="拨款情况" align="center" prop="grantStatus" />
            <el-table-column label="拨款金额" align="center" prop="grantAmount" />
            <el-table-column label="拨款方式" align="center" prop="grantType" >
            <template slot-scope="scope">
              <el-tag v-if="scope.row.grantType == '0'" >转账</el-tag>
              <el-tag v-else-if="scope.row.grantType == '1'" >现金</el-tag>
            </template>
            </el-table-column>
            <el-table-column label="拨款时间" align="center" prop="grantTime" show-overflow-tooltip/>
            <el-table-column label="创建时间" align="center" prop="applyTime" show-overflow-tooltip/>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUp2(scope.row)"
                  v-hasPermi="['business:cashUse:check']"
                >查看</el-button>
                <el-button
                  v-if="scope.row.grantStatus == '未拨款'"
                  size="mini"
                  type="text"
                  icon="el-icon-check"
                  style="color: green"
                  @click="handleAdd(scope.row)"
                  v-hasPermi="['business:borrow:update']"
                >上传拨款情况</el-button>
              </template>
            </el-table-column>
          </el-table>
          <pagination
            v-show="total>0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList()"
            style="margin-bottom: 20px"
          />
        </el-col>
      </el-row>
    </div>
    <!-- 第一次查看跳转  v-if="scope.row.grantStatus == '未拨款'" -->
    <div v-show="openView === 1">
      <h4 class="form-header" style="color: #0558e7">基金详情</h4>
      <div style="display: flex">
        <el-form ref="form1" :model="form1" label-width="180px">
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="问卷名称：">
                <div style="width: 180px">{{ form1.voteTitle}}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="发起人：">
                <div style="width: 180px">{{ form1.ownerName }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="基金类型：">
                <div style="width: 180px">
                  <div v-if="form1.voteType == '0'">住宅专项维修基金</div>
                  <div v-if="form1.voteType == '1'">公共维修基金</div>
                </div>
              </el-form-item>
            </el-col>

          </el-row>
          <h4 class="form-header" style="color: #0558e7">拨款详情</h4>
          <el-row>
            <el-col :span="4" :offset="1">
              <el-form-item label="拨款情况：">
                <div style="width: 180px">{{ form1.grantStatus}}</div>
              </el-form-item>
            </el-col>
            <el-col :span="4" :offset="2">
              <el-form-item label="拨款方式：">
                <div style="width: 180px">
                  <div v-if="form1.grantType == '0'">现金</div>
                  <div v-if="form1.grantType == '1'">转账</div>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="4" :offset="2">
              <el-form-item label="拨款金额：">
                <div style="width: 180px">{{ form1.grantAmount }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="4" :offset="2">
              <el-form-item label="拨款时间：">
                <div style="width: 180px">{{ form1.grantTime }}</div>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <h4 class="form-header" style="color: #0558e7">合同附件</h4>
            <el-row>
              <el-col :span="6" :offset="1">
                <el-form-item label="附件:" prop="annexUrl">
                  <el-button
                    style="margin-top: 20px"
                    size="small"
                    type="text"
                    icon="el-icon-edit"
                    v-hasPermi="['manage:serve:upload']"
                    v-if="form1.annexUrl !== '' || form1.annexUrl != null"
                  ><a :href="form1.annexUrl" target="view_window">附件下载</a>
                  </el-button>
                </el-form-item>
              </el-col>
            </el-row>
          </el-row>
          <el-row>
            <el-button
              type="primary"
              plain
              icon="el-icon-add"
              size="mini"
              @click="returnBefore"
            >返回
            </el-button>
          </el-row>
        </el-form>
      </div>
    </div>
    <!-- 驳回理由 -->
    <el-dialog :title="title" :visible.sync="showReason" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="90px">
        <el-form-item label="拨款方式" prop="grantStatus">
          <el-select v-model="form.grantType" placeholder="请选择拨款方式">
            <el-option
              v-for="item in options"
              :key="item.grantType"
              :label="item.label"
              :value="item.grantType">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="拨款金额" prop="grantAmount" style="width: 67%">
          <el-input v-model="form.grantAmount" placeholder="请输入内容"></el-input>
        </el-form-item>
        <el-form-item label="拨款时间" prop="grantTime">
          <el-date-picker
            v-model="form.grantTime"
            value-format="yyyy-MM-dd HH:mm:ss"
            type="datetime"
            placeholder="选择日期时间">
          </el-date-picker>
        </el-form-item>
        <el-form-item label="附件:" prop="annexUrl">
          <el-upload
            class="avatar-uploader"
            ref="annexUrl"
            :action="imgUrl"
            :show-file-list="true"
            :on-success="(res,file)=>{handleFileSuccess(res,file,1)} "
            :on-change="beforeFileUpload"
            :auto-upload="true"
            :headers="headers"
            :limit="1">
            <i class="el-icon-plus avatar-uploader-icons">点我上传</i>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {cashUseList, cashUseUpdata, infoByVoteId} from "@/api/estate/financial/cashUse";
import {getToken} from "@/utils/auth";

export default {
  name: "cashUse",
  data(){
    return{
      showReason: false,
      imgUrl: process.env.VUE_APP_BASE_API + "/estate/cashUse/uploadPicture",
      //token头
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      openView:false,
      // 遮罩层
      loading: true,
      // 表单校验
      rules: {auditStatus: [{required: true, message: '类别名不能为空', trigger: 'blur'}],
        grantAmount: [{required: true, message: '类别名不能为空', trigger: 'blur'}],
        grantTime: [{required: true, message: '类别名不能为空', trigger: 'blur'}],},
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
      // 表格数据
      cashApplyList: [],
      ownerVoteList:[],
      options: [{
        grantType: '0',
        label: '转账'
      }, {
        grantType: '1',
        label: '现金'
      }],
      //小区数据集合
      currentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 树筛选文本
      filterText:'',
      voteId:'',
      //树结构
      comOptions:[],
      communityId:null,
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        activityName:null,
        typeId: undefined,
        typeName: undefined,
        applyTime: undefined,
        grantStatus: undefined,
      },
      // 表单参数
      form: {},
      form1: {},
    }
  },
  created() {
    this.getList();
    this.getTree();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods:{
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(row){
      this.queryParams.communityId = row.communityId
      this.communityId = row.communityId
      this.getList();
    },
    // 获取树数据
    getTree(){
      getCurrentList().then(res =>{
        this.currentList = res.data;
      })
    },
    // 取消按钮
    cancel() {
      this.openView = 0;
      this.showReason = false;
      this.reset();
    },
    // 查看详情
    handleUp2(row){
      this.openView = 1;
      this.queryParams.voteId = row.voteId;
      infoByVoteId(row.voteId).then(response => {
        this.form1 = response.data
      })
    },
    // 返回
    returnBefore(){
      this.openView = 0;
    },
    /**拨款情况弹框*/
    handleAdd(row) {
      this.showReason = true;
      this.form = row;
      this.title = "拨款情况";
    },
    /** 表单重置*/
    reset() {
      this.form = {
        typeId: undefined,
        typeName: undefined,
      };
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 导出按钮操作 */
    handleExport() {
      this.$confirm('是否确认导出数据?', "导出确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      }).then(()=> {
        this.download('/estate/cashapply/export', {
          ...this.queryParams
        }, `业主列表_${new Date().getTime()}.xlsx`)
      }).catch(function (err){
      })
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.communityId =undefined,
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        communityId:undefined,
        communityName:undefined,
        activityName:null,
        typeId: undefined,
        typeName: undefined,
        submitDate: []
      }
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          cashUseUpdata(this.form).then(response => {
            this.$message.success("新增成功");
            this.showReason = false;
            this.$refs.annexUrl.clearFiles()
            this.getList();
          });
          this.resetForm("form");
        } else {
          return false;
        }
      })

    },
    //图片下载
    downLoadImg(url, name) {
      const image = new Image();
      image.setAttribute("crossOrigin", "anonymous");
      image.src = url;
      image.onload = () => {
        const canvas = document.createElement("canvas");
        canvas.width = image.width;
        canvas.height = image.height;
        const context = canvas.getContext("2d");
        context.drawImage(image, 0, 0, image.width, image.height);
        canvas.toBlob((blob) => {
          const url = URL.createObjectURL(blob);
          const a = document.createElement("a");
          a.download = name || "photo";
          a.href = url;
          a.click();
          a.remove();
          URL.revokeObjectURL(url);
        });
      };
    },
    //文件上传成功方法
    handleFileSuccess(res, file,index) {
      if (res.code === 200) {
        if(index==0){
          this.form.imageUrl = res.imgPath
        }else if(index==1){
          this.form.annexUrl = res.imgPath
        }
      }
    },
    //文件上传前校验格式
    beforeFileUpload(file) {
      //上传文件的格式
      var suffix = file.name.substring(file.name.lastIndexOf("."))
      const idJPG = (suffix === '.doc') || (suffix === '.docx') || (suffix === '.wps')
        || (suffix === '.pdf') || (suffix === '.xls') || (suffix === '.xlsx') || (suffix === '.zip') || (suffix === '.rar')
      //上传文件的大小
      const isLt2M = (file.size / 1024 / 1024) < 50;
      if (!idJPG) {
        this.$message.error("上传的文件只能是.doc 、.docx 、.wps 、.pdf 、.xls 、.xlsx 或者.zip .rar格式的压缩文件");
        this.$refs.annexUrl.clearFiles()
        return false;
      }
      if (!isLt2M) {
        this.$message.error("上传的文件大小不能超过50MB");
        this.$refs.annexUrl.clearFiles()
        return false;
      }
      return true;
    },
    /** 查询列表 */
    getList() {
      this.openView = 0;
      this.loading = false;
      this.queryParams.communityId = this.communityId;
      cashUseList(this.queryParams).then(response => {
        this.cashApplyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
  }
}
</script>

<style scoped>

</style>

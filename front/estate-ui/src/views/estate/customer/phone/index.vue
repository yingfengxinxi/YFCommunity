<template>
  <div class="app-container">
    <div v-show="view === 1">
    <el-row :span="24">
      <!--   选择树   -->
      <el-col :span="4">
        <div class="head-container">
          <el-input
            placeholder="请输入小区名称"
            clearable
            v-model="filterText">
          </el-input>

          <el-tree
            class="filter-tree"
            :data="currentList"
            :props="currentListProps"
            node-key="communityId"
            default-expand-all
            :filter-node-method="filterNode"
            icon-class="el-icon-star-on"
            @node-click="handleNodeClick"
            :current-node-key="queryParams.communityId"
            ref="tree">
          </el-tree>
        </div>
      </el-col>
      <!--   右侧   -->
      <el-col :span="20">
       <el-row :span="24">
         <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
           <el-form-item label="联系人姓名" prop="contactName">
             <el-input
               v-model="queryParams.contactName"
               placeholder="请输入联系人姓名"
               clearable
               @keyup.enter.native="handleQuery"
             />
           </el-form-item>

           <el-form-item label="联系方式" prop="contactPhone">
             <el-input
               v-model="queryParams.contactPhone"
               placeholder="请输入联系方式"
               clearable
               @keyup.enter.native="handleQuery"
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
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['bussiness:phone:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
                type="success"
                plain
                icon="el-icon-download"
                size="mini"
            @click="handleExport"
            v-hasPermi="['bussiness:phone:export']"
          >导出</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-upload2"
            size="mini"
            @click="handleImport"
            v-hasPermi="['bussiness:phone:import']"
          >导入</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['bussiness:phone:remove']"
          >删除</el-button>
        </el-col>
      </el-row>
      <el-row :span="24">
      <el-table
          v-if="refreshTable"
          v-loading="loading"
          :data="phoneList"
          @selection-change="handleSelectionChange"
        >
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column prop="contactPhone" label="联系方式" width="200" align="center"></el-table-column>
          <el-table-column prop="contactName" label="联系人员" width="100" align="center" :show-overflow-tooltip="true"></el-table-column>
          <el-table-column prop="companyName" label="所属机构" width="250" align="center" :show-overflow-tooltip="true"></el-table-column>
          <el-table-column prop="address" label="机构地址"   width="300" align="center" :show-overflow-tooltip="true"></el-table-column>
          <el-table-column prop="updateTime" label="最后操作时间"   width="200" align="center"></el-table-column>
          <el-table-column prop="updateBy" label="操作人"   width="200" align="center"></el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
            <template slot-scope="scope">
              <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['bussiness:phone:edit']"
              >修改</el-button>
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
      </el-row>
      </el-col>
    </el-row>
    </div>
    <!-- 导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
        <el-upload
          ref="upload"
          :limit="1"
          accept=".xlsx, .xls"
          :headers="upload.headers"
          :action="upload.url + '?updateSupport=' + upload.updateSupport+'&communityId='+this.queryParams.communityId"
          :disabled="upload.isUploading"
          :on-progress="handleFileUploadProgress"
          :on-success="handleFileSuccess"
          :auto-upload="false"
          drag
        >
          <i class="el-icon-upload"></i>
          <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
          <div class="el-upload__tip text-center" slot="tip">
            <span>仅允许导入xls、xlsx格式文件。</span>
            <el-link type="primary" :underline="false" style="font-size:12px;vertical-align: baseline;"
                     @click="importTemplate">下载模板
            </el-link>
          </div>
        </el-upload>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitFileForm">确 定</el-button>
          <el-button @click="upload.open = false">取 消</el-button>
        </div>
      </el-dialog>

    <!--  新增修改弹窗  -->
    <el-dialog
      :title="add.title"
      :visible.sync="add.centerDialogVisible"
      width="50%"
      :show-close="showClose"
      >
      <el-row :span="24">
        <el-col :span="12" :offset="5" >
          <el-form ref="form" :model="add.form" label-width="100px" >
            <el-form-item label="联系人员:" prop="contactName" :rules="[{ required: true, message: '联系人员不能为空', trigger: 'blur'}]">
              <el-input
                placeholder="请输入联系人员"
                v-model="add.form.contactName"
                :maxlength="10">
              </el-input>
            </el-form-item>
            <el-form-item label="联系方式:" prop="contactPhone" :rules="[{ required: true, message: '联系方式不能为空', trigger: 'blur'}]">
              <!-- :rules="[{ required: true, message: '联系方式不能为空' , trigger: 'blur'}, {pattern: /^(13[0-9]|14[01456879]|15[0-35-9]|16[2567]|17[0-8]|18[0-9]|19[0-35-9])\d{8}$/, message: '请输入合法手机号', trigger: 'blur'}]" -->
              <el-input
                placeholder="请输入联系方式"
                v-model="add.form.contactPhone" maxlength = "11">
              </el-input>
            </el-form-item>
            <el-form-item label="所属机构:" prop="companyName" :rules="[{ required: true, message: '所属机构不能为空', trigger: 'blur'}]">
              <el-input
                placeholder="请输入所属机构"
                v-model="add.form.companyName"
                :maxlength="20">
              </el-input>
            </el-form-item>
            <el-form-item label="机构地址:" prop="address" :rules="[{ required: true, message: '机构地址不能为空', trigger: 'blur'}]">
              <el-input
                placeholder="请输入机构地址"
                v-model="add.form.address"
                :maxlength="20">
              </el-input>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
    <el-button @click="cancel">取 消</el-button>
    <el-button type="primary" @click="submit('form')">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {getTableList, del, add, queryDeatil,updateData} from '@/api/estate/customer/phone/phone';
import { getToken } from "@/utils/auth";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "Praise",
  components: { Treeselect },
  data(){
    return {
      showClose:false,
      // 新增参数
      add:{
        title:'',
        centerDialogVisible:false,
        form:{}
      },
      // 导入参数
      upload: {
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
        url: process.env.VUE_APP_BASE_API + "/estate/phone/importData"
      },
      ids:[],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      total:0,
      filterText:'',
      view: 1,
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
        contactName:undefined,
        contactPhone:undefined
      },
      // 常用电话列表
      phoneList: [],
      // 树结构
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      //小区数据集合
      currentList:[],
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
        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.currentList[0].communityId);
        });
        this.getList();
      })
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.contactId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    // 列表
    getList(){
      this.loading = true;
      getTableList(this.queryParams).then(resp =>{
        this.total = resp.total;
        this.phoneList = resp.rows;
        this.loading = false
      })
    },
    // 搜索按钮
    handleQuery(){
      this.getList();
    },
    // 重置按钮
    resetQuery(){
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 修改按钮
    handleUpdate(row){
      queryDeatil(row.contactId).then(resp => {
          this.add.form = resp.data
          this.add.title = '修改常用电话'
          this.add.centerDialogVisible=true
      })
    },
    // 树点击事件
    handleNodeClick(row){
      this.queryParams.communityId = row.communityId
      this.getList();
    },

      /** 删除按钮 */
    handleDelete(row) {
      const contactIds = row.contactId || this.ids;
      this.$modal.confirm('是否确认删除所选数据？').then(function () {
        return del(contactIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('estate/phone/export', {
        ...this.queryParams
      }, `phone_${new Date().getTime()}.xlsx`)
    },
    /** 下载模板按钮操作 */
    importTemplate() {
      this.download('estate/phone/theImport', {
        ...this.queryParams
      }, `phone_${new Date().getTime()}.xlsx`)
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "常用电话导入";
      this.upload.open = true;
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(response.msg, "导入结果", { dangerouslyUseHTMLString: true });
      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    // 新增
    handleAdd(){
      this.add.title = '新增常用电话'
      this.add.centerDialogVisible = true
      this.add.form.communityId = this.queryParams.communityId
    },
    // 取消
    cancel(){
      this.add.form ={}
      this.add.centerDialogVisible = false
      this.getList()
    },
    // 提交
    submit(formName){
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.add.form.contactId == null){
            add(this.add.form).then(resp => {
              this.$modal.msgSuccess(resp.msg)
              this.getList()
              this.add.centerDialogVisible = false
              this.add.form = {}
            })
          }else {
            updateData(this.add.form).then(resp =>{
              this.$modal.msgSuccess(resp.msg)
              this.getList()
              this.add.centerDialogVisible = false
              this.add.form = {}
            })
          }
        } else {
          this.$modal.msgWarning('请填写完整')
          return false;
        }
      });
    }
  },
};
</script>

<style scoped>
/*设置高亮颜色*/
/deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #b3f1b6 !important;
}
</style>


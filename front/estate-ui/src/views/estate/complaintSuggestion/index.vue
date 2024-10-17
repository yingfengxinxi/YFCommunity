<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="92px"
    >
      <el-form-item label="关键字" prop="searchValue">
        <el-input
          v-model="queryParams.searchValue"
          placeholder="请输入问题描述/答复内容"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属小区" prop="communityId" >
        <el-select v-model="queryParams.communityId" placeholder="请选择" style="width: 240px">
          <el-option
            v-for="(item, index) in currentList"
              :key="index"
                          :label="item.communityName"
                          :value="item.communityId"
                        ></el-option>
                      </el-select>
      </el-form-item>
      <el-form-item label="投诉类型" prop="complaintSuggestionType" >
        <el-select v-model="queryParams.complaintSuggestionType" placeholder="请选择" style="width: 240px">
          <el-option
            v-for="(item, index) in complaintSuggestionTypeOptions"
              :key="index"
                          :label="item.dictLabel"
                          :value="item.dictValue"
                        ></el-option>
                      </el-select>
      </el-form-item>
      <el-form-item label="回复状态" prop="status" >
        <el-select v-model="queryParams.status" placeholder="请选择" >
          <el-option key="0" label="未回复" value="0" ></el-option>
          <el-option key="1" label="已回复" value="1" ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item>
        <el-button
          type="primary"
          icon="el-icon-search"
          size="mini"
          @click="handleQuery"
          >搜索</el-button
        >
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
          >重置</el-button
        >
      </el-form-item>
    </el-form>

    <el-row :gutter="10" class="mb8">
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="schoolList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="问题描述" :show-overflow-tooltip="true" align="center" prop="problemDescription"/>
      <el-table-column label="投诉类型" align="center" prop="complaintSuggestionType">
        <template slot-scope="scope">
          <dict-tag :options="dict.type.complaint_suggestion_type" :value="scope.row.complaintSuggestionType"/>
        </template>
      </el-table-column>
      <el-table-column label="所属小区" align="center" prop="communityId">
        <template slot-scope="scope">
          {{ getCommunityName(scope.row.communityId) }}
        </template>
      </el-table-column>
      <el-table-column label="发布时间" align="center" prop="createTime" />
      <el-table-column label="回复状态" align="center">
        <template slot-scope="scope">
          {{ scope.row.updateBy==null|| scope.row.updateBy==''?'未回复':'已回复' }}
        </template>
      </el-table-column>
      <el-table-column label="回复时间" align="center" prop="updateTime"/>
      <el-table-column label="回复人" align="center" prop="updateBy"/>
      
      
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-if="scope.row.updateBy==null|| scope.row.updateBy==''"
            >答复</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-if="scope.row.updateBy!=null && scope.row.updateBy!=''"
            >查看</el-button
          >
          
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

    <!-- 添加或修改活动对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="92px"
      > 
        <el-form-item label="所属小区：" prop="communityId">
          {{ getCommunityName(form.communityId) }}
        </el-form-item>
        <el-form-item label="投诉类型：" prop="complaintSuggestionType">
          {{ getComplaintSuggestionTypeName(form.complaintSuggestionType) }}
        </el-form-item>
        <el-form-item label="问题描述：" prop="problemDescription">
          {{ form.problemDescription }}
        </el-form-item>
        <el-form-item label="" prop="problemImage">
          <image-preview v-for="image in form.problemImageLists" :key="image" :src="image" :width="100" :height="100" style="margin: 0px 10px;"/>
        </el-form-item>
        <el-form-item label="提出时间：" prop="createTime">
          {{ form.createTime }}
        </el-form-item>
      <el-form-item label="回复内容：" prop="propertyResponse" v-if="(form.updateBy==null || form.updateBy=='')">
        <el-input
                
                type="textarea"
                :rows="3"
                placeholder="请输入回复内容"
                v-model="form.propertyResponse">
              </el-input>
      </el-form-item>
      <el-form-item label="回复内容：" v-else>
        {{ form.propertyResponse }}
      </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm" v-if="(form.updateBy==null || form.updateBy=='')">回 复</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>

    </el-dialog>
  </div>
</template>

<script>
import {
  getListPage,
  update,
  getById
} from "@/api/estate/complaintSuggestion/complaintSuggestion";
import {
  getCurrentList
} from "@/api/estate/community";
export default {
  name: "ComplaintSuggestion",
  dicts:['complaint_suggestion_type'],
  data() {
    return {
      complaintSuggestionTypeOptions:[],
      currentList:[],
      studentJobTotal:0,
      openloation:false,
      modalMap: false,
      // 遮罩层
      loading: true,
      keyUrl: "",
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
      // 学校表格数据
      schoolList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      studentLoading: true,
     
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        searchValue: null,
        communityId: undefined,
        status: undefined,
        complaintSuggestionType:undefined
      },
      // 表单参数
      form: {
        content:undefined
      },
      // 表单校验
      rules: {
        propertyResponse: [{ required: true, message: "请输入回复内容", trigger: "blur" }],
      },
    };
  },
  created() {
    //所属小区
    getCurrentList().then(res => { 
      this.currentList = res.data;
    })
    this.getDicts("complaint_suggestion_type").then(response => {
        this.complaintSuggestionTypeOptions = response.data;
    });
    this.getList();
  },

  methods: {
    getComplaintSuggestionTypeName(complaintSuggestionType) { 
      if (complaintSuggestionType) { 
        var obj = this.complaintSuggestionTypeOptions.filter(item => item.dictValue = complaintSuggestionType)[0];
        if (obj) { 
          return obj.dictLabel;
        }
      }
    },
    getCommunityName(communityId) { 
      var obj = this.currentList.filter(item => item.communityId == communityId)[0];
      if (obj) { 
        return obj.communityName;
      }
    },
    /** 列表 */
    getList() {
      this.loading = true;
      getListPage(this.queryParams).then((response) => {
        this.schoolList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        content: undefined,
        searchValue: null,
        activePicture: null,
        communityId:undefined,
        sortNumber:0,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
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
      this.queryParams.searchValue = undefined;
      this.queryParams.communityId = undefined;
      this.queryParams.status = undefined;
      this.queryParams.complaintSuggestionType = undefined;
      
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      console.log(">>>>",this.ids)
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "回复投诉建议";
    },
    
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getById(id).then((response) => {
        this.form = response.data;

        this.open = true;
        this.title = "回复投诉建议";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
            update(this.form).then((response) => {
              this.$modal.msgSuccess("回复成功");
              this.open = false;
              this.getList();
            });
         
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除编号为"' + ids + '"的数据项？')
        .then(function () {
          return del(ids,row.top);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    
  },
};
</script>
<style lang="scss">
.el-input-group__append,
.el-input-group__prepend {
  background-color: #1890ff !important;
  color: #fff !important;
  border-color: #1890ff !important;
  border-radius: 0 4px 4px 0;
}
.cascaderW {
  width: 350px;
}

</style>
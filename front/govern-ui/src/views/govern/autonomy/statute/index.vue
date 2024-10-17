<template>
  <div class="app-container">
    <!--综合页面信息-->
    <div v-show=checkView>
      <!--模糊查询-->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
        <el-form-item label="发布日期">
          <el-date-picker
            v-model="dateRange"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            @keyup.enter.native="handleQuery"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="标题" prop="statuteTitle">
          <el-input
            v-model="queryParams.statuteTitle"
            placeholder="请输入标题"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜 索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重 置</el-button>
        </el-form-item>
      </el-form>
      <!--按钮操作-->
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['autonomy:statute:add']"
          >新增
          </el-button>
        </el-col>

        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['autonomy:statute:delete']"
          >删除
          </el-button>
        </el-col>

        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
      <!--表单-->
        <el-table v-loading="loading" :data="statuteList" row-key="statuteId"  @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
        <el-table-column label="标题" align="center" key="statuteTitle" prop="statuteTitle" width="auto" :show-overflow-tooltip="true"/>
        <el-table-column label="封面" >
          <template  slot-scope="scope">
            <img :src="scope.row.coverUrl" class="avatar" style="width: 100px;height:50px">
          </template>
        </el-table-column>
        <el-table-column label="发布时间" align="center" key="createTime" prop="createTime"/>
        <el-table-column
          label="操作"
          align="center"
          width="160"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['autonomy:statute:edit']"
            >编辑</el-button>
            <el-button
              size="mini"
              type="text"
              style="color: #f56c6c"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['autonomy:statute:delete']"
            >删除</el-button>
          </template>

        </el-table-column>

      </el-table>
      <!--     分页操作-->
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
    <!--新增或修改对话框v-show="checkAddUpdateView"-->
    <el-dialog :title="title" :visible.sync="checkAddUpdateView" width="800px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="文章标题" prop="statuteTitle">
          <el-input v-model="form.statuteTitle" placeholder="请输入" />
        </el-form-item>
        <el-form-item  label="上传封面"  label-position="left" >
          <el-upload
            class="el-upload--picture-card avatar-uploader2"
            :action="url"
            :show-file-list="false"
            accept="doc/*"
            :before-upload="beforeUpload"
            :on-success="handleUploadPhoto">
            <img  v-if="this.options.statuteImg" :src="this.options.statuteImg" class="avatar">
            <i slot="default" class="el-icon-plus"></i>
          </el-upload>
          <span v-show="checkPhotoStatute" style="color: #C03639">请上传封面</span>
        </el-form-item>
        <el-form-item label="发布小区" prop="communityIds">
          <el-select v-model="form.communityIds" multiple placeholder="请选择小区" style="width: 100%"
                       >
            <el-option
              v-for="item in communityList"
              :key="item.communityId"
              :label="item.communityName"
              :value="item.communityId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发布内容" prop="content">
          <editor v-model="form.content" :min-height="192"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>



  </div>
</template>

<script>
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {getCurrentList} from '@/api/govern/community';
import {desensitization, equals} from "@/utils/common";
import {getStatuteList,uploadPicture,addStatute,getStatuteById,updateStatute,delStatute} from "@/api/govern/autonomy/statute/statute";
export default {
  name: "lease",
  data() {
    return {
      checkPhotoStatute:false,
      url: process.env.VUE_APP_BASE_API + "/estate/tenant/uploadPicture",
      //上传图片的信息
      options: {
        coverUrl: "",
        statuteImg: "",
      },
      // 表单校验
      rules: {
        statuteTitle: [
          {required: true, message: "文章标题不能为空", trigger: "blur"},
        ],
        communityIds: [
          {required: true, message: "请选择小区", trigger: "blur"},
        ],
        content: [
          {required: true, message: "内容不能为空", trigger: "blur"},
        ],
      },
      //物业数据
      estateList:[],
      checkAddUpdateView: false,
      //小区数据
      communityList: [],
      //详情页控制显隐
      checkInfo: false,
      // 选中数组
      ids: [],
      //选中名字数组
      names: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      //证件类型字典
      certificateOptions: [],
      // 日期范围
      dateRange: [],
      // 弹出层标题（用户导入）
      title: "",
      //模糊查询显隐
      showSearch: true,
      //查询到的列表参数
      statuteList: [],
      //新增提交按钮显隐
      close: true,
      //详情页显隐
      checkView: true,
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 模糊查询数据
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        statuteTitle: undefined,
        coverUrl: undefined,
      },
      // 表单参数
      form: {},
    };
  },
  created() {
    this.getList();
  },
  methods: {

    /** 手机号脱敏 */
    phoneFormatter(phone){
      if(!equals(phone,undefined)){
        return desensitization(phone,3,7);
      }
    },
    /** 身份证号脱敏 */
    cardFormatter(card){
      if(!equals(card,undefined)){
        return desensitization(card,6,14);
      }
    },


    //查询当前物业名下的小区
    getCommunitys() {
      getCurrentList().then(response => {
        this.communityList = response.data;
      })
    },
    /** 查询列表 */
    getList() {
      this.loading = true;
     getStatuteList(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.statuteList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const statuteId = row.statuteId || this.ids;
      this.$confirm('是否确认删除所选中的的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delStatute(statuteId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.statuteId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 取消按钮
    cancel() {
      this.checkView = true;
      this.checkAddUpdateView= false;
      this.checkInfo= false;
      this.form.coverUrl = "";
      this.options.statuteImg = "";
      this.reset();
    },

    /** 修改按钮操作(查询相应数据讲数据铺上) */
    handleUpdate(row) {
      this.getCommunitys();
      this.reset();
      const statuteId = row.statuteId;
      getStatuteById(statuteId).then(response => {
        this.checkAddUpdateView = true;
        this.form = response.data;
        this.options.statuteImg= this.form.coverUrl;
        this.form.communityIds=response.commIds;
        this.title = "修改规约";
      });
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.checkAddUpdateView= true;
      this.title = "新增规约";
      this.getCommunitys();
    },
    /** 添加按钮 */
    submitForm: function() {
      if(this.form.coverUrl===undefined){
        this.checkPhotoStatute=true;
        return;
      }
      this.$refs["form"].validate(valid => {
        if (valid) {
          if(this.form.statuteId != undefined){
            updateStatute(this.form).then(response => {
              this.$message.success("修改成功");
              this.checkView = true;
              this.checkAddUpdateView= false;
              this.form.coverUrl = "";
              this.options.statuteImg = "";
              this.getList();
            });
          }else{
            addStatute(this.form).then(response => {
              this.$message.success("新增成功");
              this.checkView = true;
              this.checkAddUpdateView= false;
              this.form.coverUrl = "";
              this.options.statuteImg = "";
              this.getList();
            });
          }





        }
      });
    },


    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 表单重置
    reset() {
      this.form = {
        statuteId:undefined,
        statuteTitle:undefined,
        content:undefined,
        communityIds:[],
      };
      this.options.statuteImg = "";
      this.resetForm("form");
    },
    // 上传预处理
    beforeUpload(file) {
      return true;
    },
    // 上传封面
    handleUploadPhoto(res, file) {
      let formData = new FormData();
      formData.append('file', file.raw)
      uploadPicture(formData).then(response => {
        this.form.coverUrl = response.imgPath;
        this.options.statuteImg = response.imgPath;
        this.$modal.msgSuccess("上传成功");
        this.checkPhotoStatute=false;
      });
    },



  }
};
</script>


<style>

.dev-col {
  height: 500px;
  overflow-y: scroll;
}

.dev-col::-webkit-scrollbar {
  display: none;
}

.el-table__body-wrapper {
  z-index: 2
}

.el-upload--picture-card {
  width: 100%
}

.el-upload--text {
  width: 100%;
  text-align: center;
}

.avatar .el-upload--picture-card {
  width: 50%
}

.owner-avatar .el-upload {
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader2 .el-upload {
  /*border: 1px dashed #bebfc3;
  border-radius: 6px;*/
  cursor: pointer;
  position: relative;
  overflow: hidden;
  height: 180px;
}

.owner-avatar .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 400px;
  height: 200px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  height: 200px;
  width: 110%
}

.avatar2 {
  height: 200px;
  width: 110%
}

.peopleImg {
  height: 180px;
  width: 100%;
}

.peopleImg2 {
  height: 180px;
  width: 45%;
}

.headCenter {
  position: relative;
  button: 300px;
}

.table-div {
  height: 30vh;
  overflow-y: scroll;
  box-shadow: 0 -5px 5px #EAEAEA;
  padding: 15px 20px 20px 20px;
}

.table-div::-webkit-scrollbar {
  display: none;
}

.coverPhoto{
  width: 100px;
  heght: 100px;
}


</style>

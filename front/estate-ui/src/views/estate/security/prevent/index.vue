<template>

  <!-- 主页面 -->
  <div class="app-container">
    <div v-show=checkView>
      <!--搜索条件-->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
        <el-form-item label="标题" prop="preventTitle">
        <el-input
          v-model="queryParams.preventTitle"
          placeholder="请输入标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属小区" prop="communityId">
        <el-select v-model="queryParams.communityId" placeholder="请选择">
          <el-option
            v-for="(item, index) in currentList"
              :key="index"
                          :label="item.communityName"
                          :value="item.communityId"
                        ></el-option>
                      </el-select>
      </el-form-item>
      <el-form-item label="文章类型" prop="preventTypeId">
        <el-select
          v-model="queryParams.preventTypeId"
          placeholder="请选择文章类型"
          clearable
          :style="{ width: '100%' }"
        >
          <el-option
            v-for="(item, index) in preventTypeOptions"
            :key="index"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini"
                     @click="handleQuery">搜 索
          </el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重 置</el-button>
        </el-form-item>
      </el-form>

      <!--功能 -->
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
          >新增
          </el-button>
        </el-col>

        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <!--  主页表单  -->
      <el-table v-loading="loading" :data="tableList" row-key="contractId">
        <el-table-column label="封面" align="center" key="imageUrl" prop="imageUrl">
          <template slot-scope="scope">
            <el-image
              style="width: 80px; height: 80px; margin-bottom:-4px"
              :src="`${scope.row.imageUrl}`"
            >
            </el-image>
          </template>
        </el-table-column>
        <el-table-column label="标题" align="center" prop="preventTitle"
                         show-overflow-tooltip/>
        
        <el-table-column label="所属小区" align="center" prop="communityId">
          <template slot-scope="scope">
            {{ getCommunityName(scope.row.communityId) }}
          </template>
        </el-table-column>
        <el-table-column label="简介" align="center" prop="briefIntroduction"
                         show-overflow-tooltip/>
        
        <el-table-column label="类型" align="center" key="preventTypeId" prop="preventTypeId">
          <template slot-scope="scope">
            {{ getpreventTypeOptions(scope.row.preventTypeId) }}
          </template>
        </el-table-column>
        <el-table-column label="发布时间" align="center" key="createTime" prop="createTime"></el-table-column>
        <el-table-column
          label="操作"
          align="center"
          width="160"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button
              size="small"
              type="text"
              icon="el-icon-edit"
             
              @click="handleEdit(scope.row.preventId)"
            >修改
            </el-button>

            <el-button
              type="text"
              icon="el-icon-delete"
              size="small"
              style="color: #f56c6c"
             
              @click="handleDelete(scope.row.preventId)"
            >删除
            </el-button>
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

      <!--  添加或修改弹框   -->
      <el-dialog ref="insertOrEditForm" :title="title" :visible.sync="dialogFormVisible" width="780px" @close="cancel">
        <el-form :model="insertOrEditForm" :rules="rules" ref="insertOrEditForm" label-width="100px">
          <el-row>
            
            <el-col :span="22">
              <el-form-item label="所属小区" prop="communityId">
                  <el-select v-model="insertOrEditForm.communityId" placeholder="请选择" style="width: 100%;">
                    <el-option
                      v-for="(item, index) in currentList"
                        :key="index"
                                    :label="item.communityName"
                                    :value="item.communityId"
                                  ></el-option>
                                </el-select>
                </el-form-item>
            </el-col>
            <el-col :span="22">
              <el-form-item label="文章封面: " prop="imageUrl">
                <ImageUpload @input="handleUpload" v-model="insertOrEditForm.imageUrl" :limit="1" ref="imgupload"/>
              </el-form-item>

            </el-col>
            <el-col :span="22">
              <el-form-item label="文章标题:" prop="preventTitle">
                <el-input
                  v-model="insertOrEditForm.preventTitle"
                  placeholder="请输入文章标题"
                ></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="22">
              <el-form-item label="文章类型:" prop="preventTitle">
                <el-select
                  v-model="insertOrEditForm.preventTypeId"
                  placeholder="请选择文章类型"
                  clearable
                  :style="{ width: '100%' }"
                >
                  <el-option
                    v-for="(item, index) in preventTypeOptions"
                    :key="index"
                    :label="item.name"
                    :value="item.id"
                  ></el-option>
                </el-select>
              </el-form-item>
              
            </el-col>

            
            
            <el-col :span="22">
              <el-form-item label="简介:" prop="briefIntroduction">
                <el-input v-model="insertOrEditForm.briefIntroduction" type="textarea" placeholder="请输入说明" />
              </el-form-item>
            </el-col>
            <el-col :span="22">
              <el-form-item label="发布内容:" prop="content">
                <editor v-model="insertOrEditForm.content" :min-height="192"/>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确认发布</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>

    </div>
  </div>
</template>

<script>
import {
  getList,
  getPreventDetail,
  updatePrevent,
  addPrevent,
  upload,
  delPrevent
} from "@/api/estate/security/prevent"
import {
  getCurrentList
} from "@/api/estate/community";
import {
  selectList
} from "@/api/estate/security/preventType"
import ImageUpload from "@/components/ImageUpload";

export default {
  name: "prevent",
  components: [ImageUpload],
  data() {
    return {
      currentList:[],
      //图片是否打开
      imgOpen: false,
      //主页面是否展示
      checkView: true,
      //搜索框是否展示
      showSearch: true,
      preventTypeOptions:[],
      //查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      //总条目数
      total: 0,
      // 日期范围
      dateRange: [],
      //主页面表单集合
      tableList: [],
      //加载状态
      loading: false,
      //添加表单是否展示
      dialogFormVisible: false,
      //添加或者修改的表单
      insertOrEditForm: {},
  
      //标题
      title: '新建信息',
      //校验规则
      rules: {
        imageUrl: [
          {required: true, message: '文章封面不可为空', trigger: 'change'},
        ],
        preventTitle: [
          {required: true, message: '文章标题不可为空', trigger: 'blur'},
        ],
        communityId: [
          {required: true, message: '请选择所属小区', trigger: 'blur'},
        ],
        briefIntroduction: [
          {required: true, message: '文章简介不可为空', trigger: 'blur'},
        ],
        
        communityIds: [
          {required: true, message: '请选择发布小区', trigger: 'blur'},
        ],
        content: [
          {required: true, message: '发布内容不可为空', trigger: 'blur'},
        ],
      },
    }
  },

  methods: {
    getCommunityName(communityId) { 
      var obj = this.currentList.filter(item => item.communityId == communityId)[0];
      if (obj) { 
        return obj.communityName;
      }
     
    },
    //条件查询
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 照片上传
    handleUpload(data) {
      this.$forceUpdate('imgupload')
      this.insertOrEditForm.imageUrl = data
    },
    getpreventTypeOptions(preventTypeId) { 
      if (preventTypeId) {
        return this.preventTypeOptions.filter(item => item.id == preventTypeId)[0].name;
      } else { 
        return "--";
      }
    },
    //类型
    getPreventTypeList() {
      selectList().then((res) => {
        if (res.code == 200) {
          this.preventTypeOptions = res.data;
        }
      });
    },
    //添加方法
    handleAdd() {
      this.title = '新建防控信息'
      this.insertOrEditForm = {}
      this.dialogFormVisible = true;
    },


    //提交表单
    submitForm() {
      this.$refs["insertOrEditForm"].validate(valid => {
        if (valid) {
          
          if (this.insertOrEditForm.preventId != undefined) {
            updatePrevent(this.insertOrEditForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.getList();
            });
          } else {
            addPrevent(this.insertOrEditForm).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.getList();
            });
          }
          this.imgOpen=false;
          this.insertOrEditForm = {};
          this.dialogFormVisible = false;

        }
      });
    },

    //修改回显
    handleEdit(preventId) {
      getPreventDetail(preventId).then(res => {
        this.insertOrEditForm = res.data;
 
        this.dialogFormVisible = true;
        this.imgOpen=true;
        this.title = "修改信息";
      })
    },


    //删除
    handleDelete(preventId) {
      this.$modal.confirm('是否确认删除数据项？').then(function () {
        return delPrevent(preventId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },

    //图片上传前校验格式
    beforeFileUpload(file) {
      //上传文件的格式
      var suffix = file.name.substring(file.name.lastIndexOf("."))
      const idJPG = (suffix === '.jpg') || (suffix === '.png') || (suffix === '.jpeg');
      //上传文件的大小
      const isLt2M = (file.size / 1024 / 1024) < 5;
      if (!idJPG) {
        this.$message.error("上传的图片只能是png 、jpg 、jpeg格式的图片");
        this.$refs.imageUrl.clearFiles()
        return false;
      }
      if (!isLt2M) {
        this.$message.error("上传的图片大小不能超过5MB");
        this.$refs.imageUrl.clearFiles()
        return false;
      }
      let formData = new FormData();
      formData.append('file', file.raw)
      upload(formData).then(res => {
        this.insertOrEditForm.imageUrl = res.data.url
        this.imgOpen = true;
      })
      return true;
    },

    //取消添加
    cancel() {
      this.dialogFormVisible = false;
      this.insertOrEditForm = {}
      this.imgOpen=false;
      this.$refs['insertOrEditForm'].resetFields();
    },

    //重置搜索项
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },

    /** 查询列表 */
    getList() {
      this.loading = true;
      this.tableList = [];
      getList(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.tableList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
  },

  created() {
     //所属小区
     getCurrentList().then(res => { 
      this.currentList = res.data;
    })
    this.getPreventTypeList();
    this.getList();
  }
}

</script>

<style scoped>

</style>

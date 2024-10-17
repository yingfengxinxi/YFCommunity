<template>
  <!-- 主页面 -->
  <div class="app-container">
    <div v-show="checkView">
      <!--搜索条件-->
      <el-form
        :model="queryParams"
        ref="queryForm"
        size="small"
        :inline="true"
        v-show="showSearch"
      >
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
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            v-hasPermi="['govern:prevent:list']"
            size="mini"
            @click="handleQuery"
            >搜 索
          </el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
            >重 置</el-button
          >
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
            v-hasPermi="['govern:prevent:add']"
            @click="handleAdd"
            >新增
          </el-button>
        </el-col>

        <right-toolbar
          :showSearch.sync="showSearch"
          @queryTable="getList"
        ></right-toolbar>
      </el-row>

      <!--  主页表单  -->
      <el-table v-loading="loading" :data="tableList" row-key="contractId">
        <el-table-column
          label="标题"
          align="center"
          key="prevent_title"
          prop="preventTitle"
          show-overflow-tooltip
        />
        <el-table-column
          label="封面"
          align="center"
          key="imageUrl"
          prop="imageUrl"
        >
          <template slot-scope="scope">
            <el-image
              style="width: 40px; height: 40px; margin-bottom: -4px"
              :src="`${scope.row.imageUrl}`"
            >
            </el-image>
          </template>
        </el-table-column>
        <el-table-column
          label="发布时间"
          align="center"
          key="createTime"
          prop="createTime"
        ></el-table-column>
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
              v-hasPermi="['govern:prevent:edit']"
              @click="handleEdit(scope.row.preventId)"
              >编辑
            </el-button>

            <el-button
              type="text"
              icon="el-icon-delete"
              size="small"
              style="color: #f56c6c"
              v-hasPermi="['govern:prevent:remove']"
              @click="handleDelete(scope.row.preventId)"
              >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--     分页操作-->
      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />

      <!--  添加或修改弹框   -->
      <el-dialog
        ref="insertOrEditForm"
        :title="title"
        :visible.sync="dialogFormVisible"
        width="780px"
        @close="cancel"
      >
        <el-form
          :model="insertOrEditForm"
          :rules="rules"
          ref="insertOrEditForm"
          label-width="100px"
        >
          <el-row>
            <el-col :span="22">
              <el-form-item label="文章标题:" prop="preventTitle">
                <el-input
                  v-model="insertOrEditForm.preventTitle"
                  placeholder="请输入文章标题"
                ></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="22">
              <el-form-item label="照片: " prop="imageUrl">
                <ImageUpload
                  @input="handleUpload"
                  v-model="insertOrEditForm.imageUrl"
                  :limit="1"
                  ref="imgupload"
                />
              </el-form-item>
              <!--              <el-form-item label="文章封面:" prop="imageUrl">
                <el-upload
                  class="upload-demo"
                  drag
                  v-show="!imgOpen"
                  ref="imageUrl"
                  :show-file-list="false"
                  :auto-upload="false"
                  action="#"
                  :on-change="beforeFileUpload">
                  <i class="el-icon-upload"></i>
                  <div class="el-upload__text">将图片拖到此处，或<em>点击上传</em></div>
                </el-upload>
                <div v-show="imgOpen">
                  <el-image :src="insertOrEditForm.imageUrl" fit="fit"
                            style="width: 178px;height: 178px;display: block;"/>
                  <el-upload
                    ref="imageUrl"
                    :show-file-list="false"
                    :auto-upload="false"
                    action="#"
                    :on-change="beforeFileUpload">
                    <el-button
                      style="padding-top: 30px"
                      type="text"
                      icon="el-icon-edit"
                    >更换
                    </el-button>
                  </el-upload>
                </div>
              </el-form-item>-->
            </el-col>

            <el-col :span="22">
              <el-form-item label="发布小区:" prop="communityIds">
                <el-select
                  v-model="insertOrEditForm.communityIds"
                  placeholder="请选择小区"
                  style="width: 500px"
                  multiple
                >
                  <el-option
                    v-for="item in communtiyList"
                    :key="item.communityId"
                    :label="item.communityName"
                    :value="item.communityId"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="22">
              <el-form-item label="发布内容:" prop="content">
                <editor v-model="insertOrEditForm.content" :min-height="192" />
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
  getCommuntiyList,
  getPreventDetail,
  updatePrevent,
  addPrevent,
  upload,
  delPrevent,
} from "@/api/govern/security/prevent";
import ImageUpload from "../../../../../../estate-ui/src/components/ImageUpload";

export default {
  name: "index",
  components: [ImageUpload],
  data() {
    return {
      //图片是否打开
      imgOpen: false,
      //主页面是否展示
      checkView: true,
      //搜索框是否展示
      showSearch: true,
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
      //可选小区集合
      communtiyList: [],
      //标题
      title: "新建信息",
      //校验规则
      rules: {
        preventTitle: [
          { required: true, message: "文章标题不可为空", trigger: "blur" },
          {
            min: 3,
            max: 50,
            message: "长度在 3 到 50 个字符",
            trigger: "blur",
          },
        ],
        imageUrl: [
          { required: true, message: "文章封面不可为空", trigger: "change" },
        ],
        communityIds: [
          { required: true, message: "请选择发布小区", trigger: "blur" },
        ],
        content: [
          { required: true, message: "发布内容不可为空", trigger: "blur" },
          {
            min: 3,
            max: 500,
            message: "长度在 3 到 500 个字符",
            trigger: "blur",
          },
        ],
      },
    };
  },

  methods: {
    //条件查询
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 照片上传
    handleUpload(data) {
      this.$forceUpdate("imgupload");
      this.insertOrEditForm.imageUrl = data;
    },
    //添加方法
    handleAdd() {
      this.title = "新建信息";
      this.insertOrEditForm = {};
      this.getCommuntiyList();
      this.dialogFormVisible = true;
    },

    //提交表单
    submitForm() {
      this.$refs["insertOrEditForm"].validate((valid) => {
        if (valid) {
          if (this.insertOrEditForm.imageUrl == null) {
            this.$modal.msgWarning("请上传图片");
            return;
          }
          if (this.insertOrEditForm.preventId != undefined) {
            updatePrevent(this.insertOrEditForm).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.getList();
            });
          } else {
            addPrevent(this.insertOrEditForm).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.getList();
            });
          }
          this.imgOpen = false;
          this.insertOrEditForm = {};
          this.dialogFormVisible = false;
        }
      });
    },

    //编辑回显
    handleEdit(preventId) {
      getPreventDetail(preventId).then((res) => {
        this.insertOrEditForm = res.data;
        this.getCommuntiyList();
        this.dialogFormVisible = true;
        this.imgOpen = true;
        this.title = "修改信息";
      });
    },

    //获取小区集合
    getCommuntiyList() {
      getCommuntiyList().then((res) => {
        this.communtiyList = res.data;
      });
    },

    //删除
    handleDelete(preventId) {
      this.$modal
        .confirm("是否确认删除数据项？")
        .then(function () {
          return delPrevent(preventId);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },

    //图片上传前校验格式
    beforeFileUpload(file) {
      //上传文件的格式
      var suffix = file.name.substring(file.name.lastIndexOf("."));
      const idJPG =
        suffix === ".jpg" || suffix === ".png" || suffix === ".jpeg";
      //上传文件的大小
      const isLt2M = file.size / 1024 / 1024 < 5;
      if (!idJPG) {
        this.$message.error("上传的图片只能是png 、jpg 、jpeg格式的图片");
        this.$refs.imageUrl.clearFiles();
        return false;
      }
      if (!isLt2M) {
        this.$message.error("上传的图片大小不能超过5MB");
        this.$refs.imageUrl.clearFiles();
        return false;
      }
      let formData = new FormData();
      formData.append("file", file.raw);
      upload(formData).then((res) => {
        this.insertOrEditForm.imageUrl = res.data.url;
        this.imgOpen = true;
      });
      return true;
    },

    //取消添加
    cancel() {
      this.dialogFormVisible = false;
      this.insertOrEditForm = {};
      this.imgOpen = false;
      this.$refs["insertOrEditForm"].resetFields();
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
      getList(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.tableList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
  },

  created() {
    this.getList();
  },
};
</script>

<style scoped>
</style>

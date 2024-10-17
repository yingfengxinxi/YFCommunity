<template>
  <!-- 主页面 -->
  <div class="app-container">
    <div v-show=checkView>
      <!--搜索条件-->
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
        <el-form-item label="标题" prop="donationTitle">
          <el-input
            v-model="queryParams.donationTitle"
            placeholder="请输入标题"
            clearable
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" v-hasPermi="['govern:donation:list']" size="mini"
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
            v-hasPermi="['govern:donation:add']"
            @click="handleAdd"
          >新增
          </el-button>
        </el-col>

        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <!--  主页表单  -->
      <el-table v-loading="loading" :data="tableList" row-key="contractId">
        <el-table-column label="标题" align="center" key="donationTitle" prop="donationTitle"
                         show-overflow-tooltip/>
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
              v-hasPermi="['govern:donation:edit']"
              @click="handleEdit(scope.row.donationId)"
            >编辑
            </el-button>

            <el-button
              type="text"
              icon="el-icon-delete"
              size="small"
              style="color: #f56c6c"
              v-hasPermi="['govern:donation:remove']"
              @click="handleDelete(scope.row.donationId)"
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
      <el-dialog ref="insertOrEditForm" :title="title" :visible.sync="dialogFormVisible" width="780px">
        <el-form :model="insertOrEditForm" :rules="rules" ref="insertOrEditForm" label-width="100px">
          <el-row>
            <el-col :span="22">
              <el-form-item label="文章标题:" prop="donationTitle">
                <el-input
                  v-model="insertOrEditForm.donationTitle"
                  placeholder="请输入文章标题"
                ></el-input>
              </el-form-item>
            </el-col>


            <el-col :span="22">
              <el-form-item label="发布范围:" prop="communityIds">
                <el-select
                  v-model="insertOrEditForm.communityIds"
                  placeholder="请选择小区"
                  style="width: 500px"
                  multiple>
                  <el-option
                    v-for="item in communtiyList"
                    :key="item.communityId"
                    :label="item.communityName"
                    :value="item.communityId">
                  </el-option>
                </el-select>
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
          <el-button type="primary" v-hasPermi="['govern:donation:add']" @click="submitForm">确认发布</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>

    </div>
  </div>
</template>

<script>
import {
  getList,
  addDonation,
  updateDonation,
  delDonation,
  getCommuntiyList,
  getDonationDetail,
} from "@/api/govern/association/donation";

export default {
  name: "donation",
  data() {
    return {
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
      title: '新建信息',
      //校验规则
      rules:{
        donationTitle: [
          {required: true, message: '请输入文章标题', trigger: ['blur','change']},
          {min: 2, max: 50, message: '长度在 2 到 50 个字符', trigger: ['blur','change']}
        ],
        communityIds: [
          {required: true, message: '请选择小区', trigger: ['blur','change']},
        ],
        content: [
          {required: true, message: '发布内容不可为空', trigger: ['blur','change']},
        ],
      },
    }
  },
  methods:{
    //条件查询
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },

    //添加方法
    handleAdd() {
      this.insertOrEditForm = {}
      this.getCommuntiyList()
      this.dialogFormVisible = true;
      this.$refs['insertOrEditForm'].resetFields();
    },


    //提交表单
    submitForm() {
      this.$refs["insertOrEditForm"].validate(valid => {
        if (valid) {
          if (this.insertOrEditForm.donationId != undefined) {
            updateDonation(this.insertOrEditForm).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.dialogFormVisible = false;
              this.getList();
            });
          } else {
            addDonation(this.insertOrEditForm).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.dialogFormVisible = false;
              this.getList();
            });
          }
          this.insertOrEditForm = {};
        }
      });
    },

    //编辑回显
    handleEdit(donationId) {
      getDonationDetail(donationId).then(res => {
        this.insertOrEditForm = res.data;
        this.getCommuntiyList()
        this.dialogFormVisible = true;
        this.title = "修改信息";
      })
    },

    //获取小区集合
    getCommuntiyList() {
      getCommuntiyList().then(res => {
        console.log("res",res)
        this.communtiyList = res.data
      })
    },


    //删除
    handleDelete(donationId) {
      this.$modal.confirm('是否确认删除数据项？').then(function () {
        return delDonation(donationId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },

    //取消添加
    cancel() {
      this.dialogFormVisible = false;
      this.insertOrEditForm = {}
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
      this.tableList=[];
      getList(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.tableList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
  },

  created() {
    this.getList();
  }

}
</script>

<style scoped>

</style>

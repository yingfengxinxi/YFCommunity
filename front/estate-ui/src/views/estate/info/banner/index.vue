<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col>
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">

          <el-form-item label="序号" prop="orderNum">
            <el-select  v-model="queryParams.orderNum" placeholder="请选择序号" prop="createBy">
              <el-option
                v-for="(item, index) in orderNumList"
                :key="index"
                :label="item.orderNum"
                :value="item.orderNum"
              ></el-option>
            </el-select>
          </el-form-item>

          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['estate:banner:add']">新增</el-button>

            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              style="color: #f56c6c"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['estate:banner:remove']">删除</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <!--数据列表-->
        <el-table v-loading="loading" :data="bannerList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="50" align="center" />
          <el-table-column label="序号" align="center" prop="orderNum"/>
          <el-table-column label="图片" align="center" prop="bannerUrl">
            <template slot-scope="scope">
              <el-image
                style="width: 80px; height: 50px"
                :src="scope.row.bannerUrl"
                fit="scale-down"></el-image>
            </template>
          </el-table-column>
          <el-table-column label="上传人员" align="center" prop="createBy" />
          <el-table-column label="上传时间" align="center" prop="createTime">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.createTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            width="160"
            class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['estate:banner:edit']">修改</el-button>

              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                style="color: #f56c6c"
                @click="handleDelete(scope.row)"
                v-hasPermi="['estate:banner:remove']">删除</el-button>
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

    <!-- 添加或修改用户配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" size="medium" label-width="150px">
        <el-form-item label="序号：" prop="orderNum">
<!--          <el-input v-model="form.orderNum" placeholder="请输入"
                    oninput="value=value.replace(/\D|^0/g,'')"
                    controls-position=right
                    style="width: 250px"
          ></el-input>-->
<!--          <input type='text' οninput="value=value.replace(/^(0+)|[^\d]+/g,'')">-->
          <el-input-number v-model="form.orderNum" :min="0" :max="9999999" :disabled="form.bannerId"></el-input-number>
        </el-form-item>

        <el-form-item label="照片: " prop="bannerUrl">
          <ImageUpload @input="handleUpload" v-model="form.bannerUrl" :limit="1" ref="imgupload"/>
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
  import {listBanner, addBanner, updateBanner, delBanner, getBanner,} from "@/api/estate/info/banner";
  import ImageUpload from "../../../../components/ImageUpload/index";
  export default {
    name: 'banner',
    components: [ImageUpload],
    data() {
      return {
        // 遮罩层
        loading: true,
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        //序号集合
        orderNumList: [],
        // 总条数
        total: 0,
        // 用户分类设置表格数据
        bannerList: null,
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 日期范围
        dateRange: [],
        // 表单参数
        form: {
          orderNum: undefined,
          bannerUrl: ''
        },
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          orderNum: undefined
        },
        // 表单校验
        rules: {
          orderNum: [
            { required: true, message: "序号不能为空", trigger: "blur" },
            { pattern: /^\+?[1-9]\d*$/, message: '请输入大于0的正整数'}
          ],
          bannerUrl: [
            { required: true, message: "图片不能为空", trigger: "blur" }
          ]
        }
      };
    },
    created() {
      this.getList();
      this.getOrderNumList();
    },
    methods: {
      /** 查询用户列表 */
      getList() {
        this.loading = true;
        listBanner(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
            this.bannerList = response.rows;
            this.total = response.total;
            this.loading = false;
          }
        );
      },
      getOrderNumList() {
        listBanner().then(response => {
            this.orderNumList = response.rows;
          }
        );
      },
      // 取消按钮
      cancel() {
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          bannerId: undefined,
          orderNum: undefined,
          createBy: null,
          status: "0",
          roleIds: []
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
        this.dateRange = [];
        this.resetForm("queryForm");
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.bannerId);
        this.single = selection.length != 1;
        this.multiple = !selection.length;
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "新增轮播图";
      },

      getCommunityList(){
        listBanner().then(res => {
          this.bannerList = res.data;
        })
      },

      /*查看按钮操作*/
      handleView(row) {
        getBanner(row.bannerId).then(response => {
          this.form = response.data;
        })
      },


      /** 修改按钮操作 */
      handleUpdate(row) {
        this.reset();
        const bannerId = row.bannerId || this.ids;
        getBanner(bannerId).then(response => {
          this.form = response.data;
          this.open = true;
          this.title = "修改轮播图";

        });
      },

      // 照片上传
      handleUpload(data) {
        this.$forceUpdate('imgupload')
        this.form.bannerUrl = data
      },

      /** 提交按钮 */
      submitForm: function() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.bannerUrl == null) {
              this.$modal.msgWarning("请上传图片");
              return
            }
            if (this.form.bannerId != undefined) {
              updateBanner(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addBanner(this.form).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.open = false;
                this.getList();
              });
            }
          }
        });
      },
      /** 删除按钮操作 */
      handleDelete(row) {
        const bannerIds = row.bannerId || this.ids;
        this.$modal.confirm('是否确认删除数据项').then(function() {
          return delBanner(bannerIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
    }
  };
</script>



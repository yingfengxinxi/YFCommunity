<template>
  <div class="app-container">
    <div v-show ="openView === 0">
      <el-row :gutter="20">
        <el-col>
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">

            <el-form-item label="所属小区" prop="communityId">
              <el-select v-model="queryParams.communityId" @keyup.enter.native="handleQuery"  placeholder="请选择">
                <el-option
                  v-for="(item, index) in communityList"
                  :key="index"
                  :label="item.communityName"
                  :value="item.communityId"
                ></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="头条标题" prop="classifyName" label-width="100px">
              <el-input
                style="width: 215px"
                v-model="queryParams.headlineTitle"
                placeholder="请输入标题"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            <el-form-item label="发布时间" prop="createTime" label-width="100px">
              <el-date-picker
                v-model="dateRange"
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
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button
                type="primary"
                plain
                icon="el-icon-plus"
                size="mini"
                @click="handleAdd"
                v-hasPermi="['estate:headline:add']">新增</el-button>

              <el-button
                type="danger"
                plain
                icon="el-icon-delete"
                style="color: #f56c6c"
                size="mini"
                :disabled="multiple"
                @click="handleDelete"
                v-hasPermi="['estate:headline:remove']">删除</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
          </el-row>

          <!--数据列表-->
          <el-table v-loading="loading" :data="headlineList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="50" align="center" />
            <el-table-column label="头条标题" align="center" prop="headlineTitle" :show-overflow-tooltip='true'/>
            <el-table-column label="发布人" align="center" prop="createBy" />
            <el-table-column label="发布范围" align="center" prop="communityName" :show-overflow-tooltip='true'/>
            <el-table-column label="执行开始时间" align="center" prop="startTime">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.startTime) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="执行结束时间" align="center" prop="endTime">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.endTime) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="发布时间" align="center" prop="createTime">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.createTime) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="修改时间" align="center" prop="updateTime">
              <template slot-scope="scope">
                <span>{{ parseTimeUpdate(scope.row) }}</span>
              </template>
            </el-table-column >

            <el-table-column label="照片" align="center" prop="headlineUrl">
              <template slot-scope="scope">
                <el-image
                  style="width: 80px; height: 50px"
                  :src="scope.row.headlineUrl"
                  fit="scale-down"></el-image>
              </template>
            </el-table-column>

            <el-table-column label="阅读数" align="center" prop="readNum"/>

            <el-table-column
              label="操作"
              align="center"
              width="160"
              class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button size="mini" type="text" icon="el-icon-view" @click="handleView(scope.row)"
                           v-hasPermi="['estate:headline:query']">查看
                </el-button>

                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['estate:headline:add']">修改</el-button>

                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  style="color: #f56c6c"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['estate:headline:remove']">删除</el-button>
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

    <!--查看详情页-->
    <div v-show="openView === 1">
      <h4 class="form-header" style="color: #0558e7">头条详情</h4>
      <div style="display: flex">
        <el-form ref="form" :model="form" :rules="rules" label-width="180px">
          <el-col :span="20">
            <el-row>
              <el-col :span="6">
                <el-form-item label="头条标题：">
                  <div style="width: 280px">{{ headlineObj.headlineTitle }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="10" :offset="7">
                <el-form-item label="发布范围：">
                  <div style="width: 280px">{{ headlineObj.communityName }}</div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item label="发布时间：">
                  <div style="width: 280px">{{ headlineObj.createTime }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="7">
                <el-form-item label="阅读量：">
                  <div style="width: 280px">{{ headlineObj.readNum }}</div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item label="执行开始时间：">
                  <div style="width: 280px">{{ headlineObj.startTime }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="10" :offset="7">
                <el-form-item label="执行结束时间：">{{headlineObj.endTime}}</el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-form-item label="发布人：">
                  <div style="width: 280px">{{ headlineObj.createBy }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="10" :offset="7">
                <el-form-item label="图片：">
                  <div style="width: 280px">
                    <el-image
                      style="width: 80px; height: 50px;vertical-align: middle"
                      :src="headlineObj.headlineUrl"
                      fit="scale-down"></el-image></div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col>
                <el-form-item label="内容：">
                  <editor class="editor-view" v-model="headlineObj.communityContent" read-only="read-only"/>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="cancel">关闭</el-button>
      </div>
    </div>

    <!-- 添加或修改用户配置对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="780px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" size="medium" label-width="150px">
        <el-form-item label="头条标题：" prop="headlineTitle">
          <el-input v-model="form.headlineTitle" placeholder="请输入"
                    controls-position=right
                    style="width: 400px;"
                    maxlength="50" ></el-input>
        </el-form-item>

        <el-form-item label="执行时间: " prop="timeRange">
        <el-date-picker v-model="timeRange" value-format="yyyy-MM-dd HH:mm:ss" format="yyyy-MM-dd HH:mm:ss" type="datetimerange" range-separator="~"
                        start-placeholder="开始日期" end-placeholder="结束日期" style="width: 400px;"
                        :picker-options="pickerOptions">
        </el-date-picker>
        </el-form-item>

        <!--<el-form-item label="执行开始时间: " prop="startTime">
          <el-date-picker v-model="form.startTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                          format="yyyy-MM-dd HH:mm:ss"  placeholder="开始时间" style="width: 250px" :picker-options="pickerOptions">
          </el-date-picker>
        </el-form-item>

        <el-form-item label="执行结束时间: " prop="endTime">
          <el-date-picker v-model="form.endTime" type="datetime" value-format="yyyy-MM-dd HH:mm:ss"
                          format="yyyy-MM-dd HH:mm:ss"  placeholder="结束时间" style="width: 250px">
          </el-date-picker>
        </el-form-item>-->

        <el-form-item label="发布范围" prop="communityIds">
          <el-select v-model="form.communityIds" multiple placeholder="请选择发布范围" style="width: 400px;">
            <el-option v-for="item in communityList" :key="item.communityId" :label="item.communityName"
                       :value="item.communityId +''"></el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="照片: " prop="headlineUrl">
          <ImageUpload  :limit="1" @input="handleUpload" :value="form.headlineUrl"  ref="imgupload"/>
        </el-form-item>

        <el-form-item label="活动内容" prop="communityContent">
          <editor v-model="form.communityContent" :min-height="192"/>
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
  import {listHeadline, addHeadline, updateHeadline, delHeadline, getHeadline,} from "@/api/estate/info/headline";
  import ImageUpload from "../../../../components/ImageUpload/index";
  import {getCurrentList} from "../../../../api/estate/community";
  import {parseTime} from "@/utils/global";
  export default {
    name: 'headline',
    components: [ImageUpload],
    data() {
      return {
        pickerOptions: {
          disabledDate(time) {
            return time.getTime() < Date.now() - 8.64e7;  //如果没有后面的-8.64e7就是不可以选择今天的
          }
        },
        openView: false,
        headlineObj: '',
        BASE_API: process.env.VUE_APP_BASE_API,
        // 遮罩层
        loading: true,
        //时间组
        timeRange: [],
        // 选中数组
        ids: [],
        // 非单个禁用
        single: true,
        // 非多个禁用
        multiple: true,
        // 显示搜索条件
        showSearch: true,
        //项目集合
        communityList: [],
        // 总条数
        total: 0,
        // 用户分类设置表格数据
        headlineList: null,
        // 弹出层标题
        title: "",
        // 是否显示弹出层
        open: false,
        // 日期范围
        dateRange: [],
        // 表单参数
        form: {},
        // 查询参数
        queryParams: {
          pageNum: 1,
          pageSize: 10,
          communityId : undefined,
          headlineTitle: undefined
        },
        // 表单校验
        rules: {
          headlineTitle: [
            { required: true, message: "标题不能为空", trigger: "blur" }
          ],
          startTime: [
            { required: true, message: "开始时间不能为空", trigger: "blur" }
          ],
          endTime: [
            { required: true, message: "结束时间不能为空", trigger: "blur" }
          ],
          communityIds: [
            { required: true, message: "发布范围不能为空", trigger: "blur" }
          ],
          communityContent: [{
            required: true,
            message: "内容详情不能为空",
            trigger: "change"
          }]
        }
      };
    },
    created() {
      this.openView = 0;
      this.getCommunityList();
      this.getList();
    },
    methods: {
      /** 查询用户列表 */
      getList() {
        this.loading = true;
        listHeadline(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
            this.headlineList = response.rows;
            this.total = response.total;
            this.loading = false;
          }
        );
      },
      // 取消按钮
      cancel() {
        this.openView = 0;
        this.open = false;
        this.reset();
      },
      // 表单重置
      reset() {
        this.form = {
          headlineId: undefined,
          headlineTitle: undefined,
          createBy: null,
          status: "0",
          communityIds: [],
          timeRange: []
        };
        this.resetForm("form");
      },
      parseTimeUpdate(row){
        if(row.createTime === row.updateTime){
          return "-------";
        }
        return  parseTime(row.updateTime);
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
        this.queryParams.headlineTitle = null ;
        this.handleQuery();
      },
      // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.headlineId);
        this.single = selection.length != 1;
        this.multiple = !selection.length;
      },
      /** 新增按钮操作 */
      handleAdd() {
        this.reset();
        this.open = true;
        this.title = "新增头条";
        this.timeRange = [];
      },

      getCommunityList(){
        getCurrentList().then(res => {
          this.communityList = res.data;
        })
      },

      /*查看按钮操作*/
      handleView(row) {
        this.openView = 1;
        getHeadline(row.headlineId).then(response => {
          this.headlineObj = response.data;
        })
      },


      /** 修改按钮操作 */
      handleUpdate(row) {
        const headlineId = row.headlineId || this.ids;
        getHeadline(headlineId).then(response => {
          this.form = response.data;
          this.timeRange = [];
          this.timeRange[0] = this.form.startTime;
          this.timeRange[1] = this.form.endTime;
          this.form.communityIds = response.data.communityId.split(",");
          this.open = true;
          this.title = "修改头条";

        });
      },

      // 照片上传
      handleUpload(data) {
        this.$forceUpdate('imgupload')
        this.form.headlineUrl = data
      },

      /** 提交按钮 */
      submitForm: function() {
        this.$refs["form"].validate(valid => {
          if (valid) {
            this.form.startTime = this.timeRange[0];
            this.form.endTime = this.timeRange[1];
            if (this.form.headlineUrl == null) {
              this.$modal.msgWarning("请上传图片");
              return
            }
            if (this.form.endTime < this.form.startTime) {
              this.$modal.msgWarning("结束时间不可以在开始时间之前");
              return
            }
              if (this.form.communityContent == null) {
                this.$modal.msgWarning("请输入内容");
                return
              }
            if (this.form.headlineId != undefined) {
              updateHeadline(this.form).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.open = false;
                this.getList();
              });
            } else {
              addHeadline(this.form).then(response => {
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
        const headlineIds = row.headlineId || this.ids;
        this.$modal.confirm('是否确认删除数据项').then(function() {
          return delHeadline(headlineIds);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
    }
  };
</script>
<style>
  .el-tooltip__popper{
    max-width:20%;
  }
  .el-tooltip__popper,.el-tooltip__popper.is-dark{
    background:rgb(48, 65, 86) !important;
    color: #fff !important;
    line-height: 24px;
  }
</style>

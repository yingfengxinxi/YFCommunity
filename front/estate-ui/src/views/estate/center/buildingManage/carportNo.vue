<template>
  <div class="app-container">
    
      <el-row :gutter="20">
        <!--部门数据-->
        <el-col :span="4" :xs="24">
          <div class="head-container">
            <el-input
              placeholder="请输入小区名称"
              clearable
              size="small"
              prefix-icon="el-icon-search"
              style="margin-bottom: 20px"
              v-model="search"
            />
          </div>
          <el-table
            ref="singleTable"
            type="index"
            highlight-current-row
            :data="viewDeptData"
            :show-header="false"
            @cell-click="listClick"
          >
            <el-table-column prop="communityName" />
          </el-table>
        </el-col>
        <el-col :span="20" :xs="24">
          <!-- 条件搜索 -->
          <el-form
            :model="queryParams"
            ref="queryForm"
            size="small"
            :inline="true"
          >
            <el-form-item label="车位编号" prop="carportNo">
              <el-input
                v-model="queryParams.carportNo"
                placeholder="请输入车位编号"
                @keyup.enter.native="handleQuery"
              />
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

          <div>
            <!-- 权限 v-hasPermi="['estate:carport:remove']" -->
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="addMethod"
              >新增</el-button
            >
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              style="color: #f56c6c"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              >删除</el-button
            >
            
              <el-button
                type="info"
                plain
                icon="el-icon-upload2"
                size="mini"
                @click="handleImport"
                >导入</el-button
              >
   
          </div>

          <!-- 列表展示 -->
          <el-table
            stripe
            v-loading="loading"
            :data="injList"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column
              label="车位编号"
              align="center"
              prop="carportNo"
              type="number"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="创建时间"
              align="center"
              prop="createTime"
              :show-overflow-tooltip="true"
            />
            
            <el-table-column
              label="操作"
              align="center"
              fixed="right"
              width="190"
              class-name="small-padding fixed-width"
            >
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  @click="update(scope.row)"
                  ><el-link
                    icon="el-icon-edit"
                    type="success"
                    :underline="false"
                    >修改</el-link
                  ></el-button
                >
              </template>
            </el-table-column>
          </el-table>

          <pagination
            v-show="total > 0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getBlackList"
        /></el-col>
      </el-row>
    

    <!-- 新增页面 -->
    <el-dialog
      :visible.sync="addOpen"
      @close="addClose"
      :title="title"
      :modal="false"
    >
      <div>
        <el-form
          ref="addForm"
          :rules="addRules"
          :model="addForm"
          label-width="100px"
        >
          <el-row>
            <el-col :offset="3" :span="8">
              <!-- 小区下拉框 -->
              <el-form-item label="所属小区: ">
                {{ communityName }}
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :offset="3" :span="10">
              <el-form-item label="车位编号: " prop="carportNo">
                <el-input maxlength="50"
                  clearable
                  v-model="addForm.carportNo"
                  placeholder="请输入车位编号"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :offset="10" :span="2">
              <div style="width: 280px">
                <el-button type="primary" @click="addSuccess()"
                  >确定</el-button
                >
                <el-button @click="addClose">取消</el-button>
              </div>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-dialog>
  
    <!-- 车辆信息导入对话框 -->
    <el-dialog
      :title="upload.title"
      :visible.sync="upload.open"
      width="400px"
      append-to-body
    >
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url+'?communityId='+this.queryParams.communityId"
        :disabled="upload.isUploading"
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
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getBuildingList, getCurrentList} from "@/api/estate/community";
import {
  listPage,
  getDetail,
  add,
  update,
  deleteById
} from "@/api/estate/center/buildingManage/carportNo/carportNo";
import {getToken} from '@/utils/auth'
export default {
  data() {
    return {
      disRentalType: 'disabled',
      disType: false,
      showDetailTime: true,
      // 搜索
      search: "",
      //车位ID- 判断增减
      carportIds: null,
      //新增页面显示手机号
      ownerPhones: null,
      // 楼栋下拉框
      buildingData: [],
      buildingValue: "",
      // 单元下拉框
      unitData: [],
      unitValue: "",
      // 房号下拉框
      roomData: [],
      roomValue: "",
      // 业主车辆下拉框
      ownerCarData: [],
      ownerCarValueId: "", // 业主车辆ID
      // 业户姓名
      // 临时假数据 { ownerId: 11, ownerName: "内容" }
      ownerData: [],
      ownerValue: "",
      //转出表单
      cedeForm: { ownerValue: "", ownerCarValueId: "" },
      // 详情表单
      detailForm: {},
      // 新增页面表单
      addForm: {
        rentalType: '',
      },
      // 小区ID
      communityId: undefined,
      // 小区ID 新增修改传参
      communityIds: null,
      // 详情内表格数据
      ownerListData: [],
      // 机构列表
      deptData: [],
      // 机构下拉框
      deptValue: "",
      // 新增弹窗
      addOpen: false,
      // 转出弹窗
      cedeOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        // 小区ID
        communityId: undefined,
        carportNo: "",
        carportStatus: null,
        carportNature: null,
      },
      // 遮罩层
      loading: true,
      // 表格数据
      injList: [],
      // 选中数组
      ids: [],
      // 详情页面默认关闭
      showDetail: false,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      communityName:"",
      // 车位编号导入参数
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
        url: process.env.VUE_APP_BASE_API + "/estate/carportNo/importData",
      },
      // 表单参数
      form: {},
      // 新增表单校验
      addRules: {
        carportNo: [
          { required: true, message: "车位编号不能为空", trigger: "blur" },
        ],
      },
    };
  },

  created() {
    // 机构下拉框
    this.getTypeList();
    // 列表查询
    // this.getBlackList();
  },

  computed: {
    viewDeptData: function () {
      let search = this.search;
      let viewDeptData = this.deptData;
      if (search) {
        viewDeptData = this.deptData.filter((dataNews) => {
          return dataNews.communityName.indexOf(search) !== -1;
        });
      }
      return viewDeptData;
    },
  },

  // watch: {
  //   carportNature() {
  //     console.log(1);
  //   },
  // },

  methods: {
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "车位编号导入";
      this.upload.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download(
        "estate/carportNo/importTemplate",
        {},
        `车位编号导入模板_${new Date().getTime()}.xlsx`
      );
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
      this.$alert(
        "<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" +
          response.msg +
          "</div>",
        "导入结果",
        { dangerouslyUseHTMLString: true }
      );
      this.getBlackList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getBlackList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      // this.queryParams.dropDownForm = null;
      this.searchTime = "";
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 查询车位编号列表 */
    getBlackList() {
      this.loading = false;
      listPage(this.queryParams).then((response) => {
        this.injList = response.rows;
        this.total = response.total;
      });
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.carportNoId);
      this.multiple = !selection.length;
    },

    // 表单重置
    reset() {
      this.form = {};
      this.resetForm("form");
    },


    // 机构下拉框
    getTypeList() {
      getCurrentList().then((response) => {
        this.deptData = response.data;
        this.queryParams.communityId = this.deptData[0].communityId;
        // this.queryParams.communityName = this.deptData[0].communityName;
        this.$refs.singleTable.setCurrentRow(this.deptData[0]);
        this.addForm.communityId = this.deptData[0].communityId;
        this.communityName = this.deptData[0].communityName;
        this.communityIds = this.deptData[0].communityId;
        this.getBlackList();
      });
    },

    // 单元格点击时查列表
    listClick(row) {
      this.queryParams.communityId = row.communityId;
      // // 点击单元格时赋值所属小区
      this.addForm.communityId = row.communityId;
      this.communityName = row.communityName;
      this.communityIds = row.communityId;
      this.getBlackList();
    },

    // 根据小区ID查楼栋
    selectFloor() {
      getBuildingList(this.communityIds).then((response) => {
        this.buildingData = response.data;
      });
    },

    //删除按钮
    handleDelete() {
      this.$modal
        .confirm("是否确认删除选中的数据项？")
        .then(() => {
          deleteById(this.ids).then(() => {});
        })
        .then(() => {
          this.getBlackList();
          this.$modal.msgSuccess("删除成功");
         
        })
        .catch(() => {});
    },

    // 打开新增弹窗
    addMethod() {
      this.carportIds = null;
      this.addOpen = true;
      this.title = "新增车位编号";
    },

    // 关闭新增弹窗
    addClose() {
      this.addOpen = false;
      this.getBlackList();
      this.addFormEmpty();
    },
  
    // 修改
    update(row) {
      this.addOpen = true;
      this.title = "修改车位编号";
      getDetail(row.carportNoId).then((response) => {
        this.addForm = response.data;
      });
    },

    // 提交
    addSuccess() {
      console.log("this.addForm>>", this.addForm);
      this.$refs.addForm.validate((valid) => {
        if (valid) {
         
          if (this.addForm.carportNoId) {
            update(this.addForm).then((response) => {
              this.$modal.msgSuccess("修改成功");
                this.addClose()
                this.getBlackList();
            });
          } else { 
            add(this.addForm).then((response) => {
                this.$modal.msgSuccess("添加成功");
                this.addClose()
                this.getBlackList();
            });
          }
        }
      });
    },


    // 清空addForm表单
    addFormEmpty() {
      this.addForm.carportNo = null;
    },
  },
};
</script>
<style scoped>
</style>

<template>
  <div class="app-container">
    <div v-show="view === 1">

      <el-row :gutter="20">

        <el-col :span="3" :xs="24">
          <div class="head-container">
            <el-input
              placeholder="请输入小区名称"
              clearable
              v-model="filterText">
            </el-input>
            <el-tree
              :data="currentList"
              :props="currentListProps1"
              :expand-on-click-node="false"
              ref="tree"
              default-expand-all
              highlight-current
              :filter-node-method="filterNode"
              @node-click="handleNodeClick"
            />
          </div>
        </el-col>

        <el-col :span="21" :xs="24">

          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                   label-width="68px">
            <el-form-item label="检测标题" prop="activityName">
              <el-input
                v-model="queryParams.activityName"
                placeholder="请输入检测标题"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            <el-form-item label="体检负责人" prop="headName" label-width="82px">
              <el-input
                v-model="queryParams.headName"
                placeholder="体检负责人"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            <el-form-item label="体检时间" prop="startTime">
              <el-date-picker
                v-model="dateRange"
                type="daterange"
                range-separator="至"
                start-placeholder="开始日期"
                end-placeholder="结束日期"
                value-format="yyyy-MM-dd"
                align="right">
              </el-date-picker>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>

          <el-table v-loading="loading" :data="medicalList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center"/>
            <!--      <el-table-column label="主键" align="center" prop="medicalId" />-->
            <el-table-column label="体检名称" align="center" prop="activityName"
                             min-width="120" :show-overflow-tooltip="true"/>
            <el-table-column label="体检类型" align="center" prop="medicalType" :formatter="TomedicalType"/>
            <el-table-column label="检测机构" align="center" prop="medicalOrg"
                             min-width="120" :show-overflow-tooltip="true"/>
            <el-table-column label="体检负责人" align="center" prop="headName"
                             min-width="120" :show-overflow-tooltip="true"/>
            <el-table-column label="参与人数" align="center" prop="joinNum"/>
            <el-table-column label="体检活动开始时间" align="center" prop="startTime" width="200">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.startTime, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="体检活动结束时间" align="center" prop="endTime" width="180">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.endTime, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="档案完成度" align="center" prop="fileCompletion"/>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="see(scope.row)"
                >查看
                </el-button>
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


        <!-- 添加或修改医疗检测通告对话框 -->
        <el-dialog :title="title" :visible.sync="open" width="600px" append-to-body>
          <el-form ref="form" :model="form" :rules="rules">
            <el-row type="flex" justify="space-between" :gutter="50">
              <el-col :span="12">
                <el-form-item label="活动名称" prop="activityName">
                  <el-input v-model="form.activityName" placeholder="请输入活动名称"/>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="活动类型" prop="medicalType">
                  <el-select v-model="form.medicalType" placeholder="请输入活动名称">
                    <el-option
                      v-for="dict in dict.type.sys_medical_type"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row type="flex" justify="space-between" :gutter="50">
              <el-col :span="12">
                <el-form-item label="检测机构" prop="medicalOrg">
                  <el-input v-model="form.medicalOrg" placeholder="请输入检测机构"/>
                </el-form-item>
              </el-col>

              <el-col :span="12">
                <el-form-item label="发布范围" prop="communityId">
                  <el-select v-model="form.communityId" placeholder="请选择">
                    <el-option
                      v-for="(item, index) in currentList"
                      :key="index"
                      :label="item.communityName"
                      :value="item.communityId"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row type="flex" justify="space-between" :gutter="50">
              <el-col :span="12">
                <el-form-item label="检测地点" prop="address">
                  <el-input v-model="form.address" type="textarea" placeholder="请输入检测地点"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">

                <el-form-item label="参检人群范围" prop="communityId">
                  <el-select v-model="form.ageLimit" placeholder="请选择参检人群范围">
                    <el-option
                      v-for="dict in dict.type.sys_older_ages"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row type="flex" justify="space-between" :gutter="50">
              <el-col :span="12">
                <el-form-item label="体检负责人" prop="headName">
                  <el-input v-model="form.headName" placeholder="请输入负责人姓名"/>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="负责人电话" prop="headPhone">
                  <el-input v-model="form.headPhone" placeholder="请输入负责人手机号"/>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row type="flex" justify="space-between" :gutter="50">
              <el-col :span="12">
                <el-form-item label="开始时间" prop="startTime">
                  <el-date-picker clearable v-model="form.startTime" type="date" value-format="yyyy-MM-dd"
                                  placeholder="请选择开始时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="结束时间" prop="endTime">
                  <el-date-picker clearable v-model="form.endTime" type="date" value-format="yyyy-MM-dd"
                                  placeholder="请选择结束时间">
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>

            <el-form-item label="注意事项" prop="remark">
              <el-input v-model="form.remark" type="textarea" placeholder="请输入注意事项"/>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm">确 定</el-button>
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>

        <!--   查看-->
        <el-dialog :title="title" :visible.sync="open1" width="1500px" append-to-body>
          <el-form ref="form" :model="seeList">
            <el-row type="flex" justify="space-between">
              <el-col :span="4">
                <el-form-item label="体检项目名称:">
                  <span>{{ seeList.activityName }}</span>
                </el-form-item>
              </el-col>

              <el-col :span="4">
                <el-form-item label="体检地点:">
                  <span>{{ seeList.address }}</span>
                </el-form-item>
              </el-col>

              <el-col :span="4">
                <el-form-item label="参检人员范围:">
                  <span>{{ seeList.address }}</span>
                </el-form-item>
              </el-col>

              <el-col :span="4">
                <el-form-item label="体检负责机构:">
                  <span>{{ seeList.medicalOrg }}</span>
                </el-form-item>
              </el-col>

            </el-row>

            <el-row type="flex" justify="space-between">
              <el-col :span="4">
                <el-form-item label="体检开始时间:">
                  <span>{{ seeList.startTime }}</span>
                </el-form-item>
              </el-col>

              <el-col :span="4">
                <el-form-item label="体检结束时间:">
                  <span>{{ seeList.endTime }}</span>
                </el-form-item>
              </el-col>

              <el-col :span="4">
                <el-form-item label="体检负责人:">
                  <span>{{ seeList.headName }}</span>
                </el-form-item>
              </el-col>

              <el-col :span="4">
                <el-form-item label="负责人手机号:">
                  <span>{{ seeList.headPhone }}</span>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row type="flex" justify="space-between">
              <el-col :span="4">
                <el-form-item label="体检注意事项:">
                  <span>{{ seeList.remark }}</span>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button @click="cancel">取 消</el-button>
          </div>
        </el-dialog>

      </el-row>

    </div>

    <div v-show="view === 2">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleImport()"
          >导入
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-upload2"
            size="mini"
            @click="handleExport()"
          >导出
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-download"
            size="mini"
            @click="importTemplate()"
          >下载模板
          </el-button>
        </el-col>
        <el-col :span="1.5">
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-form :model="form1" ref="queryForm1" size="small" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="体检人员" prop="ownerName">
          <el-input
            v-model="form1.ownerName"
            placeholder="请输入体检人员"
            clearable
            @keyup.enter.native="seeByName"
          />
        </el-form-item>

        <el-form-item label="体检时间" prop="examinerTime">
          <el-date-picker
            v-model="form1.examinerTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期">
          </el-date-picker>

        </el-form-item>

        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="seeByName">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery1">重置</el-button>
        </el-form-item>
      </el-form>


      <el-table v-loading="loading" :data="olderEList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"/>
        <!--        <el-table-column label="主键" align="center" prop="examinerId" />-->
        <el-table-column label="体检名称" align="center" prop="activityName"/>
        <el-table-column label="体检人员姓名" align="center" prop="ownerName"/>
        <el-table-column label="体检人员性别" align="center" prop="olderGender">
          <template #default="scope">
            <span v-if="scope.row.olderGender == '0'">男</span>
            <span v-if="scope.row.olderGender == '1'">女</span>
          </template>
        </el-table-column>
        <el-table-column label="体检人员年龄" align="center" prop="olderAge"/>
        <el-table-column label="体检时间" align="center" prop="examinerTime"/>
        <el-table-column label="体检办理机构" align="center" prop="medicalOrg"/>
        <el-table-column label="体检医生" align="center" prop="examinerDoctor"/>
        <el-table-column label="体检报告状态" align="center" prop="examinerImportStatus">
          <template #default="scope">
            <span v-if="scope.row.examinerImportStatus == '0'">已导入</span>
            <span v-if="scope.row.examinerImportStatus == '1'">未导入</span>
          </template>
        </el-table-column>
        <el-table-column label="报告发送状态" align="center" prop="examinerSendStatus">
          <template #default="scope">
            <span v-if="scope.row.examinerSendStatus == '0'">已发送</span>
            <span v-if="scope.row.examinerSendStatus == '1'">未发送</span>
          </template>
        </el-table-column>
        <el-table-column label="发送时间" align="center" prop="examinerReportTime"/>
        <!--        <el-table-column label="最后操作人" align="center" prop="updateBy"/>-->
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="see1(scope.row)"
            >查看
            </el-button>
            <span v-if="scope.row.examinerImportStatus == 1">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              style="color: #E6A23C"
              @click="handleImport1(scope.row)"
            >导入
            </el-button>
            </span>
          </template>
        </el-table-column>
      </el-table>

      <div slot="footer" class="dialog-footer">
        <el-button @click="reset">取 消</el-button>
      </div>
    </div>

    <!-- 导入对话框 -->
    <el-dialog :title="upload.title" :visible.sync="upload.open" width="400px" append-to-body>
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url + '?updateSupport=' + upload.updateSupport"
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
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>


    <!-- 上传提交报告 -->
    <el-dialog :visible.sync="upop" width="600px" append-to-body>
      <el-upload
        class="upload-demo"
        ref="importUpload"
        drag
        :headers="upload.headers"
        accept=".xlsx,.xls,.docx,.doc,.jpg,.png"
        :action="url1 + '?examinerId=' + TempexaminerId"
        :on-success="handleImportFileSuccess">
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip" slot="tip">只能上传excel/word/jpg/png文件,且不能超过5M</div>
      </el-upload>
    </el-dialog>


    <!--    查看-->
    <el-dialog :title="see2title" :visible.sync="see2" width="1500px">
      <el-form>
        <el-row type="flex" justify="space-between">
          <el-col :span="4">
            <el-form-item label="体检项目名称:">
              <span>{{ see2List.activityName }}</span>
            </el-form-item>
          </el-col>

          <el-col :span="4">
            <el-form-item label="体检人员姓名:">
              <span>{{ see2List.ownerName }}</span>
            </el-form-item>
          </el-col>

          <el-col :span="4">
            <el-form-item label="体检人员性别:">
              <span v-if="this.see2List.olderGender == '0'">男</span>
              <span v-if="this.see2List.olderGender == '1'">女</span>
              <span v-if="this.see2List.olderGender == '2'">未知</span>
            </el-form-item>
          </el-col>

          <el-col :span="4">
            <el-form-item label="体检人员年龄:">
              <span>{{ see2List.olderAge }}</span>
            </el-form-item>
          </el-col>

        </el-row>


        <el-row type="flex" justify="space-between">
          <el-col :span="4">
            <el-form-item label="体检时间:">
              <span>{{ see2List.examinerTime }}</span>
            </el-form-item>
          </el-col>

          <el-col :span="4">
            <el-form-item label="体检办理机构:">
              <span>{{ see2List.medicalOrg }}</span>
            </el-form-item>
          </el-col>

          <el-col :span="4">
            <el-form-item label="体检医生:">
              <span>{{ see2List.examinerDoctor }}</span>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row type="flex" justify="space-between">
          <span v-if="see2List.examinerReportPath != null">
          <el-link :href="see2List.examinerReportPath" style="color: blue" icon="el-icon-download">下载报告</el-link>
          </span>
          <span v-else style="color: blue">报告未查找到</span>
        </el-row>

      </el-form>
    </el-dialog>

  </div>
</template>

<script>
import {
  listMedical,
  getMedical,
  delMedical,
  addMedical,
  updateMedical,
  oldlistMedical,
  see1, addDateRange
} from "@/api/estate/customer/medical/medical";
import {getToken} from "@/utils/auth";
import {getCurrentList} from "@/api/estate/community";

export default {
  name: "Medical",
  dicts: ['sys_medical_type', 'sys_older_ages'],

  data() {
    return {
      dateRange: [],

      filterText: '',
      // 体检人员导入
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
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/estate/medical/medical/importData/",
      },

      //文件上传地址
      url1: process.env.VUE_APP_BASE_API + "/estate/medical/medical/upload/",

      see2title: "",
      see2: false,
      see2List: [],
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
      // 总条数
      total: 0,
      // 医疗检测通告表格数据
      medicalList: [],
      view: 1,
      //小区数据
      currentList: [],

      olderEList: [],

      currentListProps1: {
        children: "communityId",
        label: "communityName"
      },
      seeList: {},

      upop: false,

      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      open1: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: null,
        activityName: null,
        activityType: null,
        medicalType: null,
        address: null,
        medicalOrg: null,
        startTime: null,
        endTime: null,
      },
      // 表单参数
      form: {},
      form1: {
        ownerName: undefined,
        examinerTime: undefined
      },
      //临时id数据
      tempId: null,
      TempexaminerId: null,
      // 表单校验
      rules: {
        communityId: [
          {required: true, message: "发布范围不能为空", trigger: "blur"}
        ],
        activityName: [
          {required: true, message: "活动名称不能为空", trigger: "blur"}
        ],
        medicalType: [
          {required: true, message: "活动类型不能为空", trigger: "change"}
        ],
        address: [
          {required: true, message: "检测地点不能为空", trigger: "blur"}
        ],
        medicalOrg: [
          {required: true, message: "检测机构不能为空", trigger: "blur"}
        ],
        headName: [
          {required: true, message: "负责人姓名不能为空", trigger: "blur"}
        ],
        headPhone: [
          {required: true, message: "负责人手机号不能为空", trigger: "blur"}
        ],
        startTime: [
          {required: true, message: "开始时间不能为空", trigger: "blur"}
        ],
        endTime: [
          {required: true, message: "结束时间不能为空", trigger: "blur"}
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getCurrent();
    this.view = 1
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },

  methods: {


    // 过滤树
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },


    //———————上传报告—————————


    handleRemove(file, fileList) {
    },
    handlePreview(file) {
    },
    handleExceed(files, fileList) {
      this.$message.warning(`当前限制选择 3 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`);
    },
    beforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`);
    },

    //——————————————


    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },

    handleImport1(row) {
      this.upop = true;
      this.TempexaminerId = row.examinerId;
    },

    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },

    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upop = false;
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", {dangerouslyUseHTMLString: true});
      this.seeByName();
    },
    handleImportFileSuccess(response, file, fileList) {
      this.upop = false;
      this.$refs.importUpload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", {dangerouslyUseHTMLString: true});
      this.seeByName();
    },


    /** 下载模板操作 */
    importTemplate() {
      this.download('/estate/medical/medical/importTemplate', {}, `inspection_archives_template_${new Date().getTime()}.xlsx`)
    },


    handleNodeClick(ro) {
      this.queryParams.communityId = ro.communityId
      this.getList();
    },


    TomedicalType(row, column) {
      return this.selectDictLabel(this.dict.type.sys_medical_type, row.medicalType)
    },


    see(row) {
      this.reset();
      this.form1 = {}
      this.form1.medicalId = row.medicalId;
      oldlistMedical(this.form1).then(response => {
        this.olderEList = response.data;
        this.loading = false;
      });
      this.tempId = row.medicalId;
      this.view = 2;
    },

    seeByName() {
      oldlistMedical(this.form1).then(response => {
        this.olderEList = response.data;
      });
    },


    see1(row) {
      see1(row.examinerId).then(res => {
        this.see2List = res.data;
      })
      this.see2 = true;
    },

    getCurrent() {
      getCurrentList().then(res => {
        this.currentList = res.data
      })
    },
    /** 查询医疗检测通告列表 */
    getList() {
      this.loading = true;
      listMedical(addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.medicalList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.open1 = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.tempId = null,
        this.seeList = {
          medicalId: null,
          communityId: null,
          ageLimit: null,
          activityName: null,
          medicalType: null,
          address: null,
          medicalOrg: null,
          headName: null,
          headPhone: null,
          startTime: null,
          endTime: null,
          remark: null,
          totalNum: null,
          joinNum: null,
          createBy: null,
          createTime: null,
          updateBy: null,
          updateTime: null,
          delFlag: null
        }
      this.form = {
        medicalId: null,
        communityId: null,
        ageLimit: null,
        activityName: null,
        medicalType: null,
        address: null,
        medicalOrg: null,
        headName: null,
        headPhone: null,
        startTime: null,
        endTime: null,
        remark: null,
        totalNum: null,
        joinNum: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null
      };
      this.view = 1
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = []
      this.form1 = {}
      this.queryParams.communityId = null;
      this.queryParams.communityName = null;
      this.queryParams.activityName = null;
      this.queryParams.headName = null;
      this.queryParams.startTime = null;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    resetQuery1() {
      this.resetForm("queryForm1");
      this.seeByName();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.medicalId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加医疗检测通告";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const medicalId = row.medicalId || this.ids
      getMedical(medicalId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改医疗检测通告";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.medicalId != null) {
            updateMedical(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addMedical(this.form).then(response => {
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
      const medicalIds = row.medicalId || this.ids;
      this.$modal.confirm('是否确认删除选中的数据项？').then(function () {
        return delMedical(medicalIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.queryParams.medicalId = this.tempId
      this.download('/estate/medical/medical/export', {
        ...this.queryParams
      }, `medical_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

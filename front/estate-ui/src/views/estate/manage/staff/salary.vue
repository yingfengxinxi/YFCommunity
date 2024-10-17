<template>
  <div class="app-container">
    <div v-show="openView === 0">
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['manage:salary:add']"
          >新增
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="warning"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleImport()"
            v-hasPermi="['manage:salary:import']"
          >导入
          </el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="salaryList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column label="薪资总称" width="130" align="center" prop="salaryName"/>
        <el-table-column label="薪资时间" align="center" prop="salaryTime"/>
        <!--        <el-table-column label="出勤天数" align="center" prop="" />-->
        <el-table-column label="出勤人数" align="center" prop="attendance"/>
        <el-table-column label="薪资费用支出" align="center" prop="salaryExpenses"/>
        <el-table-column label="满勤天数" align="center" prop="days"/>
        <el-table-column label="出勤天数" align="center" prop="workDays"/>
        <el-table-column label="发放满勤奖" align="center" prop="fullSalary"/>
        <el-table-column label="发放工龄奖" align="center" prop="salarySeniority"/>
        <el-table-column label="代扣代缴社保费用"  width="130" align="center" prop="guarantee"/>
        <el-table-column label="迟到扣除" align="center" prop="salaryLate"/>
        <el-table-column label="个人所得税" align="center" prop="realitySalary"/>
        <el-table-column label="实发总工资" align="center" prop="wages"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['manage:salary:list']"
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
    </div>
    <div v-show="openView === 2" style="height: 100%;width: 100%">
      <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
        <el-form-item label="用户名称" prop="employeeName">
          <el-input
            v-emoji
            v-model="queryParams.staffName"
            placeholder="请输入用户名称"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="手机号码" prop="attendanceCountDay">
          <el-input
            oninput="value=value.replace(/[^0-9]+$/g,'')"
            v-emoji
            v-model.number="queryParams.staffPhone"
            placeholder="请输入手机号码"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
            @change="numberChange($event)"
          />
        </el-form-item>
        <el-form-item label="满勤天数" prop="fullDay">
          <el-input
            oninput="value=value.replace(/[^0-9]+$/g,'')"
            v-emoji
            v-model.number="queryParams.fullDay"
            placeholder="请输入满勤天数"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
            @change="numberChange($event)"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
        </el-form-item>
        <el-form-item style="float: right;">
          <el-button
            @click="closeInit"
          >关 闭
          </el-button>
        </el-form-item>
      </el-form>
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['manage:salary:delete']"
          >删除
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-upload2"
            size="mini"
            @click="handleExport"
            v-hasPermi="['manage:salary:export']"
          >导出
          </el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList2"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="salaryList1" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center"/>
        <el-table-column label="员工姓名" align="center" prop="staffName"/>
        <el-table-column label="手机号" align="center" prop="staffPhone">
          <template slot-scope="scope">
            <span>{{ phoneFormatter(scope.row.staffPhone) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="出勤天数" align="center" prop="attendDay"/>
        <el-table-column label="满勤天数" align="center" prop="fullDay"/>
        <el-table-column label="满勤奖" align="center" prop="fullAward"/>
        <el-table-column label="工龄奖" align="center" prop="ageAward"/>
        <el-table-column label="应发工资" align="center" prop="grossSalary"/>
        <el-table-column label="代扣代缴社保费用" width="130" align="center" prop="socialFee"/>
        <el-table-column label="迟到扣除" align="center" prop="lateFee"/>
        <el-table-column label="个人所得税" align="center" prop="taxFee"/>
        <el-table-column label="实发工资" align="center" prop="actuallySalary"/>
        <el-table-column label="备注" align="center" prop="remark"/>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="salaryUpdate(scope.row)"
              v-hasPermi="['manage:salary:update']"
            >修改
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList2"
      />
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
    </div>

    <!-- 添加或修改员工薪资对话框 -->
    <div v-show="openView === 1">
      <h4 class="form-header">基本信息</h4>
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-row>
          <el-col :span="6">
            <el-form-item label="员工" prop="staffId">
              <el-select v-model="form.staffId" placeholder="请选择员工姓名" filterable style="width: 100%"
                         v-if="!form.salaryId"
                         @change="emplistChange($event)">
                <el-option
                  v-for="item in employeeList"
                  :key="item.staffId"
                  :label="item.staffName"
                  :value="item.staffId">
                </el-option>
              </el-select>
              <el-input v-else v-model="form.staffName" disabled></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="出勤天数" prop="attendDay">
              <el-input-number style="width: 100%" :min="0" :max="31"  v-model="form.attendDay"
                               placeholder="请输入出勤天数"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="满勤天数" prop="fullDay">
              <el-input-number style="width: 100%" :min="0" :max="31"  v-model="form.fullDay"
                               placeholder="请输入满勤天数"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="满勤奖" prop="fullAward">
              <el-input-number v-emoji style="width: 100%" :min="0" v-model="form.fullAward" placeholder="请输入满勤奖"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="工龄奖" prop="ageAward">
              <el-input-number v-emoji style="width: 100%" :min="0" v-model="form.ageAward" placeholder="请输入工龄奖"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="代扣代缴社保费用" prop="socialFee">
              <el-input-number v-emoji style="width: 100%" :precision="2" :step="0.1" :min="0" v-model="form.socialFee"
                               placeholder="请输入代扣代缴社保费用"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="个人所得税" prop="taxFee">

              <el-input-number v-emoji style="width: 100%" :precision="2" :step="0.1" :min="0" v-model="form.taxFee"
                               placeholder="请输入个人所得税"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="迟到扣除" prop="lateFee">
              <el-input-number v-emoji style="width: 100%" :min="0" v-model="form.lateFee" placeholder="请输入迟到扣除"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="应发工资" prop="grossSalary">
              <el-input-number v-emoji style="width: 100%" :precision="2" :step="0.1" :min="0"
                               v-model="form.grossSalary" placeholder="请输入应发工资"/>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="实发工资" prop="actuallySalary">
              <el-input-number v-emoji style="width: 100%" :precision="2" :step="0.1" :min="0"
                               v-model="form.actuallySalary" placeholder="请输入实发工资"/>
            </el-form-item>
          </el-col>
          <el-col :span="6" >
            <el-form-item label="薪资月份" prop="yearMonth">
              <el-date-picker

                v-model="form.yearMonth"
                value-format="yyyy-MM"
                type="month"
                placeholder="请选择薪资月份">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="18">
            <el-form-item label="备注" prop="remark">
              <el-input type="textarea" rows="5" v-emoji v-model="form.remark" placeholder="请输入内容"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <h4 class="form-header"></h4>
      <div slot="footer" class="dialog-footer">
        <el-row>
          <el-col :offset="20" :span="2">
            <el-button @click="cancel">
              取 消
            </el-button>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" @click="submitForm">确 定</el-button>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>

</template>
<script>
import {getToken} from "@/utils/auth";
import {listSalary, listChange, listName, updateSalary, addSalary, delSalary} from "@/api/estate/manage/staff/salary";
import {desensitization, equals} from "@/utils/common";

export default {
  name: "salary",
  components: {},
  data() {
    const realityValidator = (rule, value, callback) => {
      if (value > this.form.salary) {
        callback(new Error('实发工资不能大于应发工资'));
      } else {
        callback();
      }
    };
    return {
      sommSalary: true,
      // 遮罩层
      loading: false,
      // 选中数组
      ids: [],
      // 子表选中数据
      checkedEmployee: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 员工薪资表格数据
      salaryList: [],
      // 员工薪资表格数据
      salaryList1: [],
      // 员工信息表格数据
      employeeList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      //open: false,
      openView: 0,
      // 查询参数
      queryParams: {
        lateFee:0,
        pageNum: 1,
        pageSize: 10,
        staffId: null,
        grossSalary:0,
        salaryId: null,
        staffPhone: null,
        staffName: null,
        yearMonth: null,
        employeeId: null,
        employeeName: null,
        employeePhone: null,
        attendanceId: null,
        attendanceCountDay: null,
        fullAward: null,
        seniorityPay: null,
        salary: null,
        securityContribution: null,
        lateToDeduct: null,
        incomeTax: null,
        realitySalary: null,
        monthTime: null,
        //ids:null,
        fullDay: null
      },
      // 表单参数
      form: {

      },

      // 用户导入参数
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
        url: process.env.VUE_APP_BASE_API + "/estate/salary/importData"
      },
      // 表单校验
      rules: {
        staffId: [
          {required: true, message: "请选择员工", trigger: "blur"}
        ],
        fullDay: [
          {required: false, message: "满勤天数不能为空", trigger: "blur"},
          {type: 'number', message: '满勤天数必须为数字值'}
        ],
        attendDay: [
          {required: true, message: "出勤天数不能为空", trigger: "blur"},
          {type: 'number', message: '出勤天数必须为数字值'}
        ],
        fullAward: [
          {required: false, message: "满勤奖不能为空", trigger: "blur"}
        ],
        ageAward: [
          {required: false, message: "工龄奖不能为空", trigger: "blur"}
        ],
        grossSalary: [
          {required: true, message: "应发工资不能为空", trigger: "blur"}
        ],
        socialFee: [
          {required: false, message: "代扣社保费用不能为空", trigger: "blur"}
        ],
        lateFee: [
          {required: false, message: "迟到扣除不能为空", trigger: "blur"}
        ],
        taxFee: [
          {required: false, message: "个人所得税不能为空", trigger: "blur"}
        ],
        /*actuallySalary: [
          {required: false, validator: realityValidator, trigger: "blur"}
        ],*/
        actuallySalary: [
          {required: true, message: "实发工资不能为空", trigger: "blur"}
        ],
        yearMonth: [
          {required: true, message: "薪资月份不能为空", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getStaffName();
  },

  methods:{
    /** 查询员工薪资列表 */
    getList() {
      this.loading = false;

      listSalary(this.queryParams).then(response => {
        this.salaryList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },


    getList2() {
      this.loading = false;
      listChange(this.queryParams).then(response => {
        this.salaryList1 = response.rows;
        this.total = response.total;
        this.loading = false;
      });

    },
    //查询员工姓名
    /*getEmpName(){
      listEmployee().then( response => {
        if (response.code == 200) {
          this.employeeList = response.rows;
        } else {
              this.msgError(response.msg);
        }
      }
      );
    },*/
    //获取员工id
    emplistChange(val) {
      this.form.employeeId = val;
    },
    //查询员工姓名
    getStaffName() {
      listName().then(response => {
        this.employeeList = response.rows;
      });
    },
    //员工薪资
    numberChange(val) {
      let reg = /^[0-9]\d*$/;
      if (!reg.test(val)) {
        this.msgError("不允许为负值请修改")
        this.queryParams.attendanceCountDay = null;
        return;
      }
    },

    // 取消按钮
    cancel() {
      this.openView = 0;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        salaryId: null,
        employeeId: null,
        attendanceId: null,
        staffId: null,
        attendanceCountDay: null,
        fullDay: 0,
        attendDay:0,
        fullAward: 0,
        lateFee:0,
        socialFee:0,
        taxFee:0,
        grossSalary:0,
        actuallySalary:0,
        ageAward:0,
        seniorityPay: null,
        salary: 0,
        securityContribution: null,
        lateToDeduct: null,
        incomeTax: null,
        realitySalary: 0,
        remark: null,
        updateBy: null,
        updateTime: null,
        createBy: null,
        createTime: null,
        monthTime: null,
        yearMonth: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList2();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.staffPhone = ''
      this.queryParams.staffName = ''
      this.queryParams.fullDay = ''
      this.queryParams.salaryId = ''
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.salaryId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.openView = 1;
      this.form.attendDay=undefined
      this.form.grossSalary=undefined
      this.form.actuallySalary=undefined
      this.title = "添加员工薪资";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      //this.reset();
      this.queryParams.yearMonth = row.salaryTime;
      this.openView = 2;
      this.title = "修改员工薪资";
      this.getList2();
    },
    closeInit() {
      this.openView = 0;
      this.getList()
    },
    /** 修改按钮操作 */
    salaryUpdate(row) {
      this.form = row
       this.openView = 1
    },

    /** 提交按钮 */
    submitForm() {
      /* this.$refs["form"].validate(valid => {
         if (valid) {*/
      //this.form.employeeList = this.employeeList;
      this.sommSalary = false;
      delete this.form.params
      if (this.form.salaryId != null) {
        this.$refs["form"].validate(valid => {
          if (valid != "") {
            updateSalary(this.form).then(response => {
          this.$modal.msgSuccess("修改成功");
          this.sommSalary = true;
          this.openView = 2;
          this.queryParams.salaryId = null;
          this.getList2();
        });
          }

        })

      } else {
        this.$refs["form"].validate(valid => {
          if (valid != "") {
            addSalary(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.sommSalary = true;
              this.openView = 0;
              this.getList();
            });
          }

        })

      }
      /*  }
      });*/
    },

    /** 删除按钮 */
    handleDelete(row) {
      const salaryIds = row.salaryId || this.ids;
      this.$modal.confirm('是否确认删除所选中的数据项？').then(function () {
        return delSalary(salaryIds);
      }).then(() => {
        this.getList2();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "薪资导入";
      this.upload.open = true;
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
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert("<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" + response.msg + "</div>", "导入结果", {dangerouslyUseHTMLString: true});
      this.getList();
    },


    /** 下载模板操作 */
    importTemplate() {
      this.download('/estate/salary/importTemplate', {}, `inspection_archives_template_${new Date().getTime()}.xlsx`)
    },

    /** 导出按钮操作 */
    handleExport() {
      this.download('estate/salary/export', {
        ...this.queryParams
      }, `薪资_${new Date().getTime()}.xlsx`)
    },

    /** 手机号脱敏 */
    phoneFormatter(phone) {
      if (!equals(phone, undefined)) {
        return desensitization(phone, 3, 7);
      }
    },

    /** 身份证号脱敏 */
    cardFormatter(card) {
      if (!equals(card, undefined)) {
        return desensitization(card, 6, 14);
      }
    },
  }
};
</script>
<style scoped>
/deep/.el-date-editor.el-input, .el-date-editor.el-input__inner{
  width: 100% !important;
  }
</style>

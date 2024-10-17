<template>
  <div class="app-container">
    <el-row :gutter="20">

      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            placeholder="请输入小区名称"
            clearable
            v-model="filterText">
          </el-input>
          <el-tree
            :data="currentList"
            :props="currentListProps"
            :expand-on-click-node="false"
            ref="tree"
            default-expand-all
            highlight-current
            :filter-node-method="filterNode"
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>

      <el-col :span="20" :xs="24">

        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="体检名称" prop="activityName">
            <el-input
              v-model="queryParams.activityName"
              placeholder="请输入体检名称"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>

          <el-form-item label="体检类型" prop="medicalType">
            <el-select v-model="queryParams.medicalType" placeholder="请输入体检类型">
              <el-option
                v-for="dict in dict.type.sys_medical_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>

          <el-form-item label="检测机构" prop="medicalOrg">
            <el-input
              v-model="queryParams.medicalOrg"
              placeholder="请输入检测机构"
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

        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['medical:medical:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['medical:medical:edit']"
            >修改</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              style="color: #f56c6c"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['medical:medical:remove']"
            >删除</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="warning"
              plain
              icon="el-icon-download"
              size="mini"
              @click="handleExport"
              v-hasPermi="['medical:medical:export']"
            >导出</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="medicalList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="体检名称" align="center" prop="activityName"
                           min-width="120" :show-overflow-tooltip="true"/>
          <el-table-column label="体检类型" align="center" prop="medicalType" :formatter="TomedicalType" />
          <el-table-column label="检测机构" align="center" prop="medicalOrg"
                           min-width="120" :show-overflow-tooltip="true"/>
          <el-table-column label="体检负责人" align="center" prop="headName"
                           min-width="120" :show-overflow-tooltip="true"/>
          <el-table-column label="最后操作时间" align="center" prop="updateTime" width="200"/>
          <el-table-column label="最后操作人" align="center" prop="updateBy"/>
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
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="see(scope.row)"
                v-hasPermi="['medical:medical:query']"
              >查看</el-button>

              <el-button
                size="mini"
                type="text"
                icon="el-icon-delete"
                style="color: #f56c6c"
                @click="handleDelete(scope.row)"
                v-hasPermi="['medical:medical:remove']"
              >删除</el-button>
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
            <el-col :span="12" >
              <el-form-item label="体检名称" prop="activityName">
                <el-input v-model="form.activityName" placeholder="请输入体检名称" />
              </el-form-item>
            </el-col>

            <el-col :span="12" >
              <el-form-item label="体检类型" prop="medicalType">
                <el-select v-model="form.medicalType" placeholder="请输入体检类型">
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
            <el-col :span="12" >
              <el-form-item label="检测机构" prop="medicalOrg">
                <el-input v-model="form.medicalOrg" placeholder="请输入检测机构" />
              </el-form-item>
            </el-col>

            <el-col :span="12" >
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
            <el-col :span="12" >
              <el-form-item label="检测地点" prop="address">
                <el-input v-model="form.address" placeholder="请输入检测地点" />
              </el-form-item>
            </el-col>
            <el-col :span="12" >

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
            <el-col :span="12" >
              <el-form-item label="体检负责人" prop="headName">
                <el-input v-model="form.headName" placeholder="请输入负责人姓名" />
              </el-form-item>
            </el-col>
            <el-col :span="12" >
              <el-form-item label="负责人电话" prop="headPhone">
                <el-input v-model="form.headPhone" placeholder="请输入负责人手机号" />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row type="flex" justify="space-between" :gutter="50">

            <el-col :span="12" >
              <el-form-item label="体检时间" prop="addTime">
                <el-date-picker
                  v-model="form.addTime"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
                  value-format="yyyy-MM-dd"
                  align="right">
                </el-date-picker>
              </el-form-item>
<!--              <el-form-item label="开始时间" prop="startTime">-->
<!--                <el-date-picker clearable v-model="form.startTime" type="date" value-format="yyyy-MM-dd" placeholder="请选择开始时间">-->
<!--                </el-date-picker>-->
<!--              </el-form-item>-->
<!--            </el-col>-->
<!--            <el-col :span="12" >-->
<!--              <el-form-item label="结束时间" prop="endTime">-->
<!--                <el-date-picker clearable v-model="form.endTime" type="date" value-format="yyyy-MM-dd" placeholder="请选择结束时间">-->
<!--                </el-date-picker>-->
<!--              </el-form-item>-->

            </el-col>
          </el-row>

          <el-form-item label="注意事项" prop="remark">
            <el-input v-model="form.remark" type="textarea" placeholder="请输入注意事项" />
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
              <el-form-item label="体检名称:" >
                <span>{{seeList.activityName}}</span>
              </el-form-item>
            </el-col>

            <el-col :span="4" >
              <el-form-item label="检测地点:" >
                <span>{{seeList.address}}</span>
              </el-form-item>
            </el-col>

            <el-col :span="4" >
              <el-form-item label="参检人群范围:" >
                <span>{{seeList.ageLimit}}岁以上</span>
              </el-form-item>
            </el-col>

            <el-col :span="4" >
              <el-form-item label="检测机构:" >
                <span>{{seeList.medicalOrg}}</span>
              </el-form-item>
            </el-col>

          </el-row>

          <el-row type="flex" justify="space-between">
            <el-col :span="4" >
              <el-form-item label="体检开始时间:" >
                <span>{{seeList.startTime}}</span>
              </el-form-item>
            </el-col>

            <el-col :span="4" >
              <el-form-item label="体检结束时间:" >
                <span>{{seeList.endTime}}</span>
              </el-form-item>
            </el-col>

            <el-col :span="4" >
              <el-form-item label="体检负责人:" >
                <span>{{seeList.headName}}</span>
              </el-form-item>
            </el-col>

            <el-col :span="4" >
              <el-form-item label="负责人手机号:" >
                <span>{{seeList.headPhone}}</span>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row type="flex" justify="space-between">
            <el-col :span="24" >
              <el-form-item label="注意事项:" >
                <span>{{seeList.remark}}</span>
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
</template>

<script>
import { listMedical, getMedical, delMedical, addMedical, updateMedical,addDateRange} from "@/api/estate/customer/medical/medical";
import {getCurrentList} from "@/api/estate/community";

export default {
  name: "Medical",
  dicts: ['sys_medical_type','sys_older_ages'],

  data() {
    return {

      filterText: '',



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

      //小区数据
      currentList:[],

      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      seeList:{},
      dateRange: [],
      addTime:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      open1:false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: null,
        activityName: null,
        activityType:null,
        medicalType: null,
        address: null,
        medicalOrg: null,
        startTime: null,
        endTime: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        communityId: [
          { required: true, message: "发布范围不能为空", trigger: "blur" }
        ],
        activityName: [
          { required: true, message: "体检名称不能为空", trigger: "blur" },
          {min: 1, max: 20, message: '最多只能输入20位', trigger: 'blur'}
        ],
        medicalType: [
          { required: true, message: "体检类型不能为空", trigger: "change" }
        ],
        address: [
          { required: true, message: "检测地点不能为空", trigger: "blur" },
          {min: 1, max: 20, message: '最多只能输入20位', trigger: 'blur'}
        ],
        medicalOrg: [
          { required: true, message: "检测机构不能为空", trigger: "blur" },
          {min: 1, max: 20, message: '最多只能输入20位', trigger: 'blur'}
        ],
        headName: [
          { required: true, message: "负责人姓名不能为空", trigger: "blur" },
          {min: 1, max: 10, message: '最多只能输入10位', trigger: 'blur'}
        ],
        addTime: [
          { required: true, message: "体检时间不能为空", trigger: "blur" }
        ],
        headPhone: [{
          required: true,
          message: "负责人手机号不能为空",
          trigger: "blur"
        }, {
          pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
          message: "请输入正确的手机号码",
          trigger: "blur"
        }],
        startTime: [
          { required: true, message: "开始时间不能为空", trigger: "blur" }
        ],
        endTime: [
          { required: true, message: "结束时间不能为空", trigger: "blur" }
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getCurrent();
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



    handleNodeClick(ro){
      this.queryParams.communityId = ro.communityId
      this.getList();
    },


    TomedicalType(row, column) {
      return this.selectDictLabel(this.dict.type.sys_medical_type, row.medicalType)
    },


    see(row){
      this.reset();
      const medicalId = row.medicalId
      getMedical(medicalId).then(response => {
        this.seeList = response.data;
        this.open = false;
        this.open1 = true;
        this.title = "查看";
      });
    },


    getCurrent(){
      getCurrentList().then(res =>{
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
      this.queryParams.communityId = null;
      this.queryParams.communityName = null;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.medicalId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.form.communityId = this.queryParams.communityId;
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
            if (this.form.addTime[0] == null && this.form.addTime[1] == null){
              this.$message.warning("体检时间不能为空")
              return
            }
            this.form.startTime = this.form.addTime[0]
            this.form.endTime = this.form.addTime[1]
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
      this.$modal.confirm('是否确认删除选中的数据项？').then(function() {
        return delMedical(medicalIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('estate/medical/medical/export1', {
        ...this.queryParams
      }, `medical_${new Date().getTime()}.xlsx`)
    }
  }
};
</script>

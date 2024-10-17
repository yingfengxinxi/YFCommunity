<template>
  <div class="app-container">
    <div v-show="openView">
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
        <el-form-item label="考核名称" prop="assessName">
          <el-input maxlength="64"
                    v-model="queryParams.assessName"
                    placeholder="请输入考核办法"
                    clearable
                    style="width: 240px"
                    @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="考核时间">
          <el-date-picker
            v-model="dateRange"
            style="width: 240px"
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

      <el-table v-loading="loading" :data="assessRecordVoList" @selection-change="handleSelectionChange">
        <el-table-column label="考核名称" prop="assessName" align="center" :show-overflow-tooltip="true"/>
        <el-table-column label="发布时间" prop="updateTime">
        </el-table-column>
        <el-table-column label="考核时间" prop="startTime">
        </el-table-column>
        <el-table-column label="截止时间" prop="endTime">
        </el-table-column>
        <el-table-column label="考核状态">
          <template slot-scope="scope">
            <span>{{ scope.row.schedule }}</span>
          </template>
        </el-table-column>
        <el-table-column label="创建时间" align="center" prop="createTime">
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini" type="text"
              class="text-info"
              icon="el-icon-edit"
              @click="handleDetail(scope.row)"
              v-hasPermi="['estate:assess:list']"
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

    <!--  详情页  -->
    <div v-show="detailShow">
      <el-form ref="detailForm" :model="detailForm" label-width="140px">
        <h4 class="form-header">考核记录详情</h4>

        <el-row>
          <el-col :span="5" :offset="2">
            <el-form-item label="考核名称:">{{ detailForm.assessName }}</el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="考核结果:">{{ assessResult(detailForm) }}</el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="创建时间:">{{ detailForm.createTime }}</el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="5" :offset="2">
            <el-form-item label="发布时间:">{{ detailForm.createTime }}</el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="考核时间:">{{ detailForm.startTime }}</el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="截止时间:">{{ detailForm.endTime }}</el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="12" :offset="2">
            <el-form-item label="考核说明:">{{ detailForm.remark }}</el-form-item>
          </el-col>
        </el-row>

        <h4 class="form-header">考核项目详情</h4>

        <el-row>
          <el-col :span="3" :offset="2" style="margin-top: 1%">
            <el-form-item label="考核总得分">{{ detailForm.sumCount + "分" }}</el-form-item>
          </el-col>
          <el-col :span="3" :offset="2" v-for="(key, value) in detailForm.resultMap" :key="key"
                  style="margin-top: 1%">
            <el-form-item :label="value">{{ key + "分" }}</el-form-item>
          </el-col>
        </el-row>

        <div style="float: right">
          <el-button @click="canceldetail">关 闭</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import {
  listassessRecordVo,
  getassessRecordVo,
  delassessRecord, addassessRecord, updateassessRecord, getRecordParam, changePublicity,
  changeAssessEnable,
  bulkPublicity,
  getassessRecordDetail
} from "@/api/estate/manage/assess/assess";


export default {
  name: "index",
  data() {
    return {
      startEndTime: [new Date(), new Date()],
      // 遮罩层
      loading: true,
      //主页面展示
      openView: true,
      // 选中数组
      ids: [],
      schedules: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 考核记录表格数据
      assessRecordVoList: [],
      // 弹出层标题
      title: "",
      //详情页是否展示
      detailShow: false,
      //详情表单数据
      detailForm: {},
      //启停状态
      assessEnableOptions: [
        {
          label: "启用",
          value: "0",
        },
        {
          label: "停用",
          value: "1",
        }
      ],
      //是否公示状态
      publicityOptions: [
        {
          label: "未公示",
          value: "0",
        },
        {
          label: "已公示",
          value: "1",
        }
      ],
      // 是否显示弹出层
      open: false,
      // 日期范围
      dateRange: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        assessName: undefined,
        assessEnable: undefined,
        publicity: undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        assessName: [
          {required: true, message: "考核名称不能为空", trigger: "blur"}
        ],
        methodId: [
          {required: true, message: "考核方法不能为空", trigger: "blur"}
        ],
        startEndTime: [
          {required: true, message: "请选择考核持续时间", trigger: "blur"}
        ],
        estateIds: [
          {required: true, message: "请选择考核项目", trigger: "blur"}
        ]
      },
      pickerBeginOption: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 24 * 60 * 60 * 1000
        }
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    /** 查询考核记录列表 */
    getList() {
      this.loading = true;
      listassessRecordVo(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.assessRecordVoList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },

    //考核结果有
    assessResult(resultMap) {
      if (this.detailForm.assessStatus==='未开始') {
          return '未开始'
      }else if(this.detailForm.assessStatus==='进行中'){
          return '未结束'
      }
      return resultMap.sumCount >= 60 ? '合格' : '不合格'
    },


    // 取消按钮
    cancel() {
      this.startEndTime = [];
      this.open = false;
      this.reset();
    },

    // 表单重置
    reset() {
      this.form = {
        assessId: undefined,
        assessName: undefined,
        estateIds: [],
        remark: undefined
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
      this.ids = selection.map(item => item.assessId)
      this.schedules = selection.map(item => item.schedule)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },

    /** 查看详情按钮操作 */
    handleDetail(row) {
      this.reset();
      const assessId = row.assessId || this.ids
      getassessRecordDetail(assessId).then(response => {
        this.detailForm = response.data;
        this.detailForm.resultMap = response.resultMap;
        this.detailForm.sumCount = response.sumCount.total;
        this.detailForm.assessStatus = response.assessStatus
      });
      this.openView = false
      this.detailShow = true
    },

    //关闭详情页
    canceldetail() {
      this.detailForm = {}
      this.detailShow = false
      this.openView = true;
    },
  }
};
</script>

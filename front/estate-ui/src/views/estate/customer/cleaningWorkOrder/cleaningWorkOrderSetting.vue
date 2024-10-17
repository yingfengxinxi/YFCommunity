<template>
  <div class="app-container">
    
      <el-row :gutter="20">
        <!--小区数据-->
        <el-col :span="4">
          <div class="head-container">
            <el-input
              placeholder="请输入小区名称"
              clearable
              v-model="filterText">
            </el-input>
            <el-tree
              class="filter-tree"
              :data="currentList"
              :props="currentListProps"
              :expand-on-click-node="false"
              default-expand-all
              :filter-node-method="filterNode"
              ref="tree"
              highlight-current
              node-key="communityId"
              @node-click="handleNodeClick"
              >
            </el-tree>
          </div>
        </el-col>
       
        <el-col :span="20" :xs="24">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="82px">
            <el-form-item label="清洁点名称" prop="cleaningName">
              <el-input
                v-model="queryParams.cleaningName"
                placeholder="请输入清洁点名称"
                clearable
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <!-- <el-form-item label="清洁点状态" prop="inspectStatus">
              <el-select
                v-model="queryParams.inspectStatus"
                placeholder="清洁点状态"
                clearable
                style="width: 160px"
              >
                <el-option
                  v-for="item in inspectStatusOptions"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item> -->
            
            <el-form-item label="执行人" prop="executorBy">
              <el-select
                v-model="queryParams.executorBy"
                placeholder="执行人"
                clearable
                style="width: 160px"
              >
                <el-option
                  v-for="item in executorByOptions"
                  :key="item.userId+''"
                  :label="item.staffName"
                  :value="item.userId+''"
                />
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
          >新增</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

          <el-table v-loading="loading" :data="reportingWarrantyList">
            <el-table-column label="清洁点名称" align="center" key="cleaningName" prop="cleaningName"/>
            <el-table-column label="清洁地址" align="center" key="cleaningAddress" prop="cleaningAddress"/>
            <el-table-column label="执行人" align="center" key="executorBy" prop="executorBy">
              <template slot-scope="scope">
                  {{ getExecutorByName(scope.row.executorBy) }}
              </template>
            </el-table-column>
            <el-table-column label="创建时间" align="center" key="createTime" prop="createTime"/>
          
            <el-table-column
              label="操作"
              align="center"
              class-name="small-padding fixed-width"
            >

              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleDetail(scope.row)"
                >修改</el-button>
                <el-button
                size="mini"
                type="text"
                style="color: #f56c6c"
                icon="el-icon-delete"
                  @click="handleDelete(scope.row)"
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
      </el-row>

    <el-dialog :title="title" :visible.sync="open" width="60%" append-to-body>
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="92px"
        label-position="left"
      >
        <el-row>
          <el-col :span="10">
            <el-form-item label="清洁点名称" prop="cleaningName">
            <el-input v-model="form.cleaningName" placeholder="请输入清洁点名称" :style="{ width: '240px' }"/>
          </el-form-item>
        </el-col>
        <el-col :span="10" :offset="1">
          <el-form-item label="执行人" prop="executorBy">
            <el-select v-model="form.executorBy" multiple placeholder="请选择执行人" style="width: 240px">
                <el-option
                  v-for="(item, index) in executorByOptions"
                    :key="index"
                    :label="item.staffName"
                    :value="item.userId+''"
                ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        </el-row>
        <el-row>
        <el-col :span="10">
              <el-form-item label="清洁频率" prop="frequency" >
                <el-select :disabled="disabled"   v-model="form.frequency" placeholder="请选择清洁频率" style="width: 240px" @change="getExecutionNumberMax(form.frequency)">
                <el-option
                  v-for="(item, index) in frequencyOptions"
                    :key="index"
                    :label="item.label"
                    :value="item.value"
                ></el-option>
            </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="10" :offset="1">
              <el-form-item label="执行次数" prop="executionNumber">
                <el-input-number :disabled="disabled"  @change="getFirstExecutionTime()" :min="1" :max="executionNumberMax" v-model="form.executionNumber" placeholder="请输入清洁点名称" :style="{ width: '240px' }"/>
              </el-form-item>
            </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="开始时间" prop="firstExecutionTime">
                <el-date-picker
                :disabled="disabled"
                  @change="getFirstExecutionTime()"
                  v-model="form.firstExecutionTime"
                  value-format="yyyy-MM-dd"
                  type="date"
                  placeholder="选择开始日期"
                  :style="{ width: '240px' }"
                  :picker-options="pickerOptions">
                </el-date-picker>
              </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="21">
          <el-form-item label="清洁地址" prop="cleaningAddress">
            <el-input  v-model="form.cleaningAddress" placeholder="请输入清洁地址"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="21">
          <el-form-item label="清洁内容" prop="content">
            <el-input type="textarea" v-model="form.content" placeholder="请输入清洁内容"/>
          </el-form-item>
        </el-col>
      </el-row>
      
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listCleaningWorkOrderSetting,
  getCleaningWorkOrderSettingDetail,
  add,
  update,
  removeById,
  updateCleaningWorkOrderSettingStatus,
  getTime
} from "@/api/estate/customer/cleaningWorkOrder/cleaningWorkOrderSetting";
import { getCurrentList } from "@/api/estate/community";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getCommunityIdStaffList } from "@/api/estate/manage/staff/staff";
export default {
  name: "cleaningWorkOrderSetting",
  components: { Treeselect },
  dicts: ['renovation_audit_status'],
  data() {
    return {
      inspectStatusOptions: [{ "value": "0", "label": "关闭" }, { "value": "1", "label": "开启" }],
      executorByOptions: [],
      frequencyOptions: [{ "value": "0", "label": "每日执行" }, { "value": "1", "label": "每半月执行" }, { "value": "2", "label": "每月执行" }],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      active: 1,
      executionNumberMax:1,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      //主页面展示
      openView: true,
      //详情页是否展示
      detailShow: false,
      //详情表单数据
      detailForm: {},
      violationList: [],
      dateRange:[],
      // 树筛选文本
      filterText:'',
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 装修表格数据
      reportingWarrantyList: null,
      //装修工信息
      worker: {},
      firstExecutionTimes:[],
      //小区数据集合
      currentList: [],
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      disabled:false,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      rules: {
        cleaningName:[
            { required: true, message: "清洁点名称不能为空", trigger: "blur" },
        ],
        frequency: [
            { required: true, message: "请选择清洁频率", trigger: "blur" },
        ],
        executorBy:[
            { required: true, message: "清洁点名称不能为空", trigger: "blur" },
        ],
        firstExecutionTime:[
            { required: true, message: "请选择工单第一次执行时间", trigger: "blur" },
        ],
        executionNumber:[
            { required: true, message: "请输入执行次数", trigger: "blur" },
        ],
        cleaningAddress:[
            { required: true, message: "请输入清洁地址", trigger: "blur" },
        ],
        content:[
            { required: true, message: "请输入清洁内容", trigger: "blur" },
        ],
      },
      pickerOptions: {
        disabledDate(time) {
          //return time.getTime() < Date.now() - 8.64e7;
          return time.getTime() < Date.now() ;
        }
      },
      // 表单参数
      form: {
        cleaningName: undefined,
        inspectStatus: undefined,
        executorBy: undefined,
        frequency: undefined,
        firstExecutionTime: undefined,
        executionNumber: 1,
        cleaningAddress:undefined,
        content:undefined,
      },
      reportRepairTypeOptions: [],
      reportRepairStatusOptions:[],
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        cleaningName: undefined,
        inspectStatus: undefined,
        executorBy:undefined
      },
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    
    this.getTree();
  },
  methods: {
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(row){
      this.queryParams.communityId = row.communityId
      this.form.communityId = row.communityId;
      this.getExecutorByOptions();
      this.getList();
    },
    getExecutionNumberMax(frequency) { 
      if (frequency) { 
        if (frequency == '0') { 
          this.executionNumberMax = 7;
        }
        if (frequency == '1') { 
          this.executionNumberMax = 105;
        }
        if (frequency == '2') { 
          this.executionNumberMax = 210;
        }

        this.getFirstExecutionTime();
      }
    },
    //计算执行时间
    getFirstExecutionTime() { 
      var firstExecutionTime = this.form.firstExecutionTime;
      var frequency = this.form.frequency;
      var executionNumber = this.form.executionNumber;
      console.log("form>>>", this.form);
      if (frequency && executionNumber && firstExecutionTime) { 
        firstExecutionTime=firstExecutionTime+ " 07:00:00"
        if (executionNumber <= this.executionNumberMax) { 
          // getTime(firstExecutionTime, frequency, executionNumber).then(res => {
          //   var dataTime=res.msg.substring(0, res.msg.length - 1);
          //   this.firstExecutionTimes = dataTime.split("&");
          // })
        }
      }
    },
     /** 删除按钮操作 */
     handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除当前清洁点吗？')
        .then(function () {
          return removeById(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.executorBy) { 
            this.form.executorBy = this.form.executorBy + "";
          }
          if (this.form.frequency=='0') { 
            if (this.form.executionNumber > this.executionNumberMax) { 
              this.$modal.msgWarning("请输入正确的执行频次【最大执行频次每日"+this.executionNumberMax+"次】");
            }
          }
          if (this.form.frequency=='1') { 
            if (this.form.executionNumber > this.executionNumberMax) { 
              this.$modal.msgWarning("请输入正确的执行频次【最大执行频次每半月"+this.executionNumberMax+"次】");
            }
          }
          if (this.form.frequency=='2') { 
            if (this.form.executionNumber > this.executionNumberMax) { 
              this.$modal.msgWarning("请输入正确的执行频次【最大执行频次每月"+this.executionNumberMax+"次】");
            }
          }
          this.form.communityId=this.queryParams.communityId;
          this.form.firstExecutionTime=this.form.firstExecutionTime + ' 07:00:00';
          if (this.form.id) {
            update(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            add(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    getExecutorByName(val) { 
      var executorByName = "";
      if (val) { 
        var executorBys = val.split(",");
        executorBys.forEach(executorBy => {
          var obj = this.executorByOptions.filter(item => item.userId+'' == executorBy)[0];
          if (obj) { 
            executorByName=executorByName+","+obj.staffName;  
          }
          
        });
      }
      if (executorByName) { 
        return executorByName.substring(1, executorByName.length);
      }
    },
    getCurrentList(val) { 
      if (val) { 
        var obj = this.currentList.filter(item=>item.communityId == val)[0];
        if (obj) { 
          return obj.communityName;
        }
      }
    },
    /** 查询装修列表 */
    getList() {
      this.loading = true;
      listCleaningWorkOrderSetting(this.queryParams).then(response => {
          this.reportingWarrantyList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
// 状态修改
  handleStatusChange(row) {
      console.log("row>>", row);
      let text = row.inspectStatus === "1" ? "开启" : "关闭";
      this.$modal.confirm('确认要"' + text + '"当前清洁点吗？').then(function() {
        return updateCleaningWorkOrderSettingStatus(row.id,row.inspectStatus);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.inspectStatus = row.inspectStatus === "0" ? "1" : "0";
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        cleaningName: undefined,
        inspectStatus: undefined,
        executorBy: undefined,
        frequency: undefined,
        firstExecutionTime: undefined,
        executionNumber: 1,
        content:undefined,
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
      this.resetForm("queryForm");
      this.handleQuery();
    },

    // 获取树数据
    getTree(){
      getCurrentList().then(res =>{
        this.currentList = res.data;
        this.queryParams.communityId = this.currentList[0].communityId;
        this.getExecutorByOptions();
        this.getList();

        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.currentList[0].communityId);
        });
      })
    },
    getExecutorByOptions() { 
        //清洁人员
        var workPostList = ['4'];
        getCommunityIdStaffList(this.queryParams.communityId,workPostList).then(res => {
          this.executorByOptions = res.data;
        });
    },
    /** 增加按钮操作 */
    handleAdd() {
      this.reset();
      this.title = "增加清洁设置";
      this.open = true
      this.disabled = false;
    },

    /** 修改按钮操作 */
    handleDetail(row) {
      const id = row.id || this.ids
      getCleaningWorkOrderSettingDetail(id).then(response => {
        this.form = response.data;
        if (this.form.executorBy) { 
          this.form.executorBy= this.form.executorBy.split(",");
        }
        this.disabled = true;
        this.form.firstExecutionTime = this.form.firstExecutionTime.substring(0, 10);
        this.getExecutionNumberMax(this.form.frequency);
        this.getFirstExecutionTime();
      });
      this.title = "修改清洁设置";
      this.open = true
    },

    //关闭详情页
    canceldetail() {
      this.detailForm = {}
      this.detailShow = false
      this.openView = true;
    }
  }
};
</script>

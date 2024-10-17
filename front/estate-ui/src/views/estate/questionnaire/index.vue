<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="标题" prop="title">
        <el-input
          v-model="queryParams.title"
          placeholder="请输入标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属小区" prop="communityId" label-width="80px">
        <el-select v-model="queryParams.communityId" placeholder="请选择" style="width: 240px">
          <el-option
            v-for="(item, index) in currentList"
              :key="index"
                          :label="item.communityName"
                          :value="item.communityId"
                        ></el-option>
                      </el-select>
          </el-form-item>
      <el-form-item  label="评测状态" prop="status">
        <el-select
                  v-model="queryParams.status"
                  placeholder="请选择评测状态"
                >
                  <el-option
                    v-for="dict in status"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  ></el-option>
                </el-select>
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

    <el-table
      v-loading="loading"
      :data="list"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="测评小区" align="center" prop="communityId">
        <template slot-scope="scope">
          {{ getCommunityName(scope.row.communityId) }}
        </template>
      </el-table-column>
      <el-table-column label="问卷标题" align="center" prop="title"/>
      <el-table-column label="发布范围" align="center" prop="actRang">
        <template slot-scope="scope">
          {{ scope.row.actRang==0?'全体业主':'部分楼栋' }}
        </template>
      </el-table-column>
      <el-table-column label="开始时间" align="center" prop="startTime" />
      <el-table-column label="结束时间" align="center" prop="deadline" />
      <!-- <el-table-column label="是否发布" align="center" prop="releaseStatus">
        <template slot-scope="scope">
              <el-switch
                v-model="scope.row.releaseStatus"
                active-value="1"
                inactive-value="0"
                :disabled="scope.row.status!='0'"
                @change="handleStatusChange(scope.row)"
              ></el-switch>
            </template>
      </el-table-column> -->
      <el-table-column label="评测状态" align="center" prop="status">
        <template slot-scope="scope">
          {{ status.filter(item=>item.value==scope.row.status)[0].label }}
        </template>
      </el-table-column>
      <el-table-column label="创建时间" align="center" prop="createTime" />
      
      <el-table-column label="参与人数" align="center" prop="num"></el-table-column>
      
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            >查看</el-button
          >
          <br v-if="scope.row.status=='0'">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.status=='0'"
            @click="handleUpdate(scope.row)"
            >修改</el-button
          >
          <br v-if="scope.row.status=='0'">
          <el-button
            size="mini"
            type="text"
            style="color: #f56c6c"
            icon="el-icon-delete"
            v-if="scope.row.status=='0'"
            @click="handleDelete(scope.row)"
            >删除</el-button
          >
          <br v-if="scope.row.status=='0'">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleFind(scope.row)"
            >查看投票人员</el-button
          >
        </template>
      </el-table-column>
    </el-table>

    <pagination
      v-show="total > 0"
      :total="total"
      :page.sync="queryParams.pageNum"
      :limit.sync="queryParams.pageSize"
      @pagination="getList"
    />

    <!-- 添加或修改问卷调查对话框 -->
    <el-dialog :title="title"  :visible.sync="open" width="90%" append-to-body>
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        label-position="left"
        :disabled="isDisabled"
      >
        <el-row>
          <el-col :span="10">
            <el-form-item label="标题" prop="title">
            <el-input v-model="form.title" placeholder="请输入标题" :style="{ width: '240px' }"/>
          </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="测评小区" prop="communityId">
            <el-select v-model="form.communityId" @change="getBuildingOptions(form.communityId)" placeholder="请选择测评小区" style="width: 240px">
                <el-option
                  v-for="(item, index) in currentList"
                    :key="index"
                    :label="item.communityName"
                    :value="item.communityId"
                ></el-option>
            </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="10">
              <el-form-item label="发布范围：" prop="actRang" label-width="100px">
                <el-radio-group v-model="form.actRang" @change="getActRang(form)">
                    <el-radio :label="0">全体业主</el-radio>
                    <el-radio :label="1">部分楼栋</el-radio>
                </el-radio-group>
              </el-form-item>
            </el-col>
        <el-col :span="10">
          <el-form-item label="选择楼栋" prop="buildingIds">
            <el-select v-model="form.buildingIds" :disabled="form.actRang!==1" multiple @change="updateBuildingId()" placeholder="请选择楼栋" style="width:240px">
                        <el-option v-for="(item,index) in buildingOptions" 
                        :key="index" 
                        :label="item.buildingName" 
                        :value="item.buildingId"
                        />
            </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="10">
          <el-form-item label="开始时间" prop="startTime">
                <el-date-picker
                  v-model="form.startTime"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  type="datetime"
                  placeholder="选择开始日期"
                  :style="{ width: '240px' }"
                  :picker-options="pickerOptions">
                </el-date-picker>
              </el-form-item>
        </el-col>
        <el-col :span="10">
          <el-form-item label="截止时间" prop="deadline">
                <el-date-picker
                  v-model="form.deadline"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  type="datetime"
                  placeholder="选择截止日期"
                  :style="{ width: '240px' }"
                  :picker-options="pickerOptions">
                </el-date-picker>
             
              </el-form-item>
        </el-col>
      </el-row>
        <el-form-item label="问卷内容" prop="teacherClassIds">
         
          <div v-for="(item, index) in testingStandards" :key="index">
            <el-divider style="font-size: 16px" plain content-position="left"
            >题目{{ index+1 }}</el-divider
          >
        
            <el-row :gutter="20" style="margin-bottom: 15px">
              <el-col :span="7">
                <el-input
                  v-model="item.problemName"
                  placeholder="题目(必填)"
                  
                />
              </el-col>
              <el-col :span="5">
                <el-select
                  v-model="item.type"
                  placeholder="请选择问题类型(必选)"
                >
                  <el-option
                    v-for="dict in typeOptions"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  ></el-option>
                </el-select>
              </el-col>
              
              <el-col :span="4">
                <el-input-number
                  style="width: 60%"
                  :min="1"
                  v-model="item.sort"
                  placeholder="请输入显示顺序"
                />
              </el-col>

              <el-col :span="2">
                
                <el-button
                  type="primary"
                  plain
                  style="border:1px solid red;color:red;background-color: #ffffff;"
                 
                  size="mini"
                  @click="removeRow(item)"
                  class="minusRowBtn"
                >删除题目</el-button>
              </el-col>
            </el-row>
            
            <el-row v-if="item.type!='2'" :gutter="20" style="margin-left: 35px;margin-bottom: 15px">
              <el-form-item label="" prop="datestingStandards">
                
                <div v-for="(item1, index) in item.datestingStandards" :key="index" style="margin-bottom: 5px;">
                  <el-row :gutter="20">
                    <el-col :span="7">
                      <el-input
                        v-model="item1.name"
                        :placeholder="'选项'+index+1+'(必填)'"
                        
                      />
                    </el-col>
                    <el-col :span="2">
                      <el-button
                        size="mini"
                        type="text"
                        style="color:red"
                        icon="el-icon-delete"
                        @click="removeRow1(item,item1)"
                        ></el-button
                      >
                    </el-col>
                  </el-row>
                </div>
                  <el-button
                    type="text"
                    icon="el-icon-plus"
                    size="mini"
                    @click="addRow1(item)"
                  >添加选项</el-button>  
              </el-form-item>
            </el-row>
          </div>
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="addRow()"
          >新增题目</el-button>
        </el-form-item>
        
        
        <!-- <el-form-item label="是否发布" prop="releaseStatus">
          <el-switch
            v-model="form.releaseStatus"
            active-value="1"
            inactive-value="0"
          ></el-switch>
        </el-form-item> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="参与人员列表" :visible.sync="openAccount" width="100%" append-to-body>
      <questionnaire-account-component  ref="indexAccountRef"/>
    </el-dialog>
  </div>
</template>

<script>
import {
  list,
  getById,
  delTestingStandard,
  insert,
  update,
  updateByIdStatus
} from "@/api/estate/questionnaire/questionnaire";
import QuestionnaireAccountComponent from './indexAccountList'; // 导入子组件
import {
  getCurrentList
} from "@/api/estate/community";
import { getCommunityIdBuildingName} from "@/api/estate/center/room";
import { getToken } from "@/utils/auth";
export default {
  name: "Questionnaire",
  components: {
    QuestionnaireAccountComponent ,
  },
  data() {
    return {
      currentList:[],
      openAccount:false,
      typeOptions: [
        { "value": "0", "label": "单选" },
        { "value": "1", "label": "多选" },
        { "value": "2", "label": "文本" }
      ],
      status: [
        { "value": "0", "label": "未开始" },
        { "value": "1", "label": "进行中" },
        { "value": "2", "label": "已提交" },
        {"value":"3","label":"已结束"}
      ],
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
      // 问卷调查表格数据
      list: [],
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
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/back/testingStandard/importData",
      },
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        }
      },
      isDisabled:false,
      buildingOptions:[],
      testingStandards: [
        {
          id: "",
          problemName:"",
          type: "",
          sort: "",
          datestingStandards: [{"id":"","name":"","flag":"0","text":""}]
        },
      ],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        status: null,
        communityId:"",
      },
      // 表单参数
      form: {
        "releaseStatus":"0"
      },
      // 表单校验
      rules: {
        communityId: [
          { required: true, message: "请选择评测小区", trigger: "blur" },
        ],
        actRang: [
          { required: true, message: "请选择发布范围", trigger: "blur" },
        ],
        title: [
          { required: true, message: "请输入调查问卷标题", trigger: "blur" },
        ],
        startTime: [
          { required: true, message: "请选择开始时间", trigger: "blur" },
        ],
        deadline: [
          { required: true, message: "请选择截止时间", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    //所属小区
    getCurrentList().then(res => { 
      this.currentList = res.data;
    })
    this.getList();
  },
  methods: {
    getActRang(row) {
      if (row.actRang == 0) {
        this.form.buildingIds = [];
      } else { 
        if (row.communityId == null) { 
          if (row.communityId == null) {
            this.$modal.msgWarning("请选择评测小区");
            this.form.actRang = 0;
            return;
          }
        }
      }
    },
    updateBuildingId() { 
      this.$forceUpdate();
    },
    //小区下所有楼栋
    getBuildingOptions(communityId) { 
      getCommunityIdBuildingName(communityId).then(res => { 
        this.buildingOptions = res.data;
      })
    },
    getCommunityName(communityId) { 
     return this.currentList.filter(item => item.communityId == communityId)[0].communityName;
    },
    handleFind(row) { 
      this.openAccount=true;
      this.$nextTick(() => {
        this.$refs.indexAccountRef.receiveValue(row.id)
      })     
    },
    //添加
    addRow() {
      var problemNameFlag = 0;
      var typeFlag = 0;
      var datestingStandardFlag = 0;
      this.testingStandards.forEach(item => { 
        if (!item.problemName) { 
          problemNameFlag = 1;
          return;
        }
        if (!item.type) { 
          typeFlag = 1;
          return;
        }
        if (item.type!='2') { 
          item.datestingStandards.forEach(item1 => { 
            if (!item1.name) { 
              datestingStandardFlag = 1;
              return;
            }
          })
        }
        
      })
      if (problemNameFlag >= 1) { 
        this.$modal.msgError("问题名称不能为空");
        return;
      }
      if (typeFlag >= 1) { 
        this.$modal.msgError("问题类型不能为空");
        return;
      }

      
      if (datestingStandardFlag >= 1) { 
        this.$modal.msgError("问题选项不能为空");
        return;
      }
      
      this.testingStandards.push({
          id: "",
          problemName:"",
          type: "",
        sort: "",
        datestingStandards: [{"id":"","name":"","flag":"0","text":""}]
      });
    },
    addRow1(item1) {

      item1.datestingStandards.push({"id":"","name":"","flag":"0","text":""})
    },
     // 移除某行
    removeRow1(k0,k) {
  
      if (k0.datestingStandards.length === 1) {
        return;
      }
      k0.datestingStandards = k0.datestingStandards.filter(
        (item) => item !== k
      );
    },
    // 移除某行
    removeRow(k) {
      if (this.testingStandards.length === 1) {
        return;
      }
      this.testingStandards = this.testingStandards.filter(
        (item) => item !== k
      );
    },
   
   // 状态修改
    handleStatusChange(row) {
      console.log("row>>", row);
      let text = row.releaseStatus === "1" ? "发布" : "撤销";
      this.$modal.confirm('确认要"' + text + '""' + row.title + '"问卷调查吗？').then(function() {
        return updateByIdStatus(row.releaseStatus,row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.releaseStatus = row.releaseStatus === "0" ? "1" : "0";
      });
    },
    /** 查询问卷调查列表 */
    getList() {
      
      this.loading = true;
      list(this.queryParams).then((response) => {
        this.list = response.rows;
        this.total = response.total;
        this.loading = false;
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
        releaseStatus: "0",
        actRang:0,
      };
      (this.testingStandards = [
        {
          id: "",
          problemName:"",
          type: "",
          sort: "",
          datestingStandards: [{"id":"","name":"","flag":"0","text":""}]
        },
      ]),
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
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加问卷调查";
    },
    handleView(row) { 
      this.title = "问卷调查详情";
      this.open = true;
      this.isDisabled = true;
      this.getInfo(row);
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.isDisabled=false;
      this.open = true;
      this.title = "修改问卷调查";
      this.getInfo(row);
    },
    getInfo(row) {
      this.reset();
      const id = row.id || this.ids;
      getById(id).then((response) => {
        this.form = response.data;
        this.getBuildingOptions(this.form.communityId);
        this.form.actRang = Number(this.form.actRang);
        if (this.form.content != null) {
          this.testingStandards = JSON.parse(this.form.content);
        } else {
          this.testingStandards = [
            {
              id: "",
              problemName:"",
              type: "",
              sort: "",
              datestingStandards: [{"id":"","name":"","flag":"0","text":""}]
            },
          ];
        }
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {

          var problemNameFlag = 0;
          var typeFlag = 0;
          var datestingStandardFlag = 0;
          var datestingStandardCount = 0;
          this.testingStandards.forEach(item => { 
            if (!item.problemName) { 
              problemNameFlag = 1;
              return;
            }
            if (!item.type) { 
              typeFlag = 1;
              return;
            }
            if (item.type!='2') { 
              item.datestingStandards.forEach(item1 => { 
                if (!item1.name) { 
                  datestingStandardFlag = 1;
                  return;
                }
              })
            }
            //多选
            if (item.type == '1') { 
              var count = item.datestingStandards.length;
              if (count<=1) { 
                datestingStandardCount = 1;
                return;
              }
            }
            
          })
          if (problemNameFlag >= 1) { 
            this.$modal.msgError("问卷题目不能为空");
            return;
          }
          if (typeFlag >= 1) { 
            this.$modal.msgError("问卷题目类型不能为空");
            return;
          }
          if (datestingStandardFlag >= 1) { 
            this.$modal.msgError("问卷题目选项不能为空");
            return;
          }
          if (datestingStandardCount >= 1) { 
            this.$modal.msgError("选项多选时请添加多个选项");
            return;
          }
          
          this.form.content = JSON.stringify(this.testingStandards);
          if (this.form.id) {
            update(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            insert(this.form).then((response) => {
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
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除问卷调查【"' + row.title + '"】吗？')
        .then(function () {
          return delTestingStandard(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
   
  },
};
</script>

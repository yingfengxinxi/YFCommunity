<template>
  <div class="app-container">
    <div v-show="!open">
      <!--   选择树   -->
      <el-row :span="24">
        <el-col :span="4">
          <div class="head-container">
            <el-input
                placeholder="请输入小区名称"
                v-model="filterText">
            </el-input>

            <el-tree
                class="filter-tree"
                :data="currentList"
                :props="currentListProps"
                default-expand-all
                :filter-node-method="filterNode"
                icon-class="el-icon-star-on"
                @node-click="handleNodeClick"
                ref="tree">
            </el-tree>
          </div>
        </el-col>
        <el-col :span="20">
          <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
            <el-form-item label="名称">
              <el-input
                  v-emoji
                  v-model.trim="queryParams.voteName"
                  placeholder="请输入名称搜索"
                  clearable
                  size="small"
                  @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="发布时间">
              <el-date-picker
                  v-model="dateRange"
                  size="small"
                  style="width: 240px"
                  value-format="yyyy-MM-dd"
                  type="daterange"
                  range-separator="-"
                  start-placeholder="开始日期"
                  end-placeholder="结束日期"
              ></el-date-picker>
            </el-form-item>
            <!--        <el-form-item label="发布人" prop="createBy">
                      <el-select v-model="queryParams.id" clearable placeholder="请选择发布人" style="width: 100%">
                        <el-option
                          v-for="item in surveyList"
                          :key="item.id"
                          :label="item.createBy"
                          :value="item.id">
                        </el-option>
                      </el-select>
                    </el-form-item>-->
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
                  v-hasPermi="['govern:busEstate:add']"
              >新增
              </el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                  type="danger"
                  plain
                  icon="el-icon-delete"
                  size="mini"
                  :disabled="multiple"
                  @click="handleDelete"
                  v-hasPermi="['govern:busEstate:delete']"
              >删除
              </el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
          </el-row>
          <el-table v-loading="loading" :data="questionList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column label="名称" align="center" prop="voteName" :show-overflow-tooltip="true"/>
            <el-table-column label="发布范围" align="center" prop="estateName" :show-overflow-tooltip="true"/>
            <el-table-column label="楼栋范围" align="center" prop="buildingNames" :show-overflow-tooltip="true"/>
            <el-table-column label="单元范围" align="center" prop="unitNames" :show-overflow-tooltip="true"/>
            <el-table-column label="发布时间" align="center" prop="startTime"/>
            <el-table-column label="发布人" align="center" prop="createBy"/>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-view"
                    @click="handleSurvey(scope.row)"
                    v-hasPermi="['govern:busEstate:query']"
                >查看
                </el-button>

                <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-view"
                    @click="handleBulletin(scope.row)"
                    v-if="scope.row.hasFinish"
                    v-hasPermi="['govern:busEstate:bulletin']"
                >发布
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
      </el-row>
    </div>
    <!--查看详情  -->
    <div v-show="showDetail">
      <el-form ref="form" :model="form" label-width="120px">
        <h4 class="form-header">
          <el-row>
            <el-col :span="22">投票详情</el-col>
            <el-col :span="2">
              <el-button @click="closeDetail">关 闭</el-button>
            </el-col>
          </el-row>
        </h4>
        <el-row>
          <el-col :span="4">
            <el-form-item label="名称：" prop="voteName">{{ form.voteName }}</el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="发布范围：" prop="estateName">{{ form.estateName }}</el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="楼栋范围：" prop="estateName">{{ form.buildingNames }}</el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="单元范围：" prop="estateName">{{ form.unitNames }}</el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="发布时间：" prop="startTime">{{ form.startTime }}</el-form-item>
          </el-col>
          <el-col :span="4">
            <el-form-item label="发布人：">{{ form.createBy }}</el-form-item>
          </el-col>
          <el-row>
            <el-col>
              <el-form-item label="内容：">
                <editor class="editor-view" v-model="form.content" read-only="read-only"/>
              </el-form-item>
            </el-col>
          </el-row>
        </el-row>
      </el-form>
      <el-table v-loading="loadingRecord" :data="recordList">

        <el-table-column label="业主姓名" align="center" prop="ownerName" :show-overflow-tooltip="true"/>
        <el-table-column label="业主手机号" align="center" prop="ownerPhone" :show-overflow-tooltip="true"/>
        <el-table-column label="投票结果" align="center" prop="itemName" :show-overflow-tooltip="true"/>
        <el-table-column label="投票时间" align="center" prop="voteTime">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.voteTime) }}</span>
          </template>
        </el-table-column>

      </el-table>
      <pagination
          v-show="totalRecord>0"
          :total="totalRecord"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getRecordList"
      />
    </div>
    <!--新增问卷调查-->
    <div v-show="!openView">
      <el-form ref="modelForm" :model="modelForm" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="6" :offset="1" v-show="commonMessage">
            <el-form-item label="投票名称" prop="voteName">
              <el-input onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
                        v-model="modelForm.voteName" style="width:100%;margin-right: 1%;" clearable
                        placeholder="投票调查名称"
                        v-emoji/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1" v-show="commonMessage">
            <el-form-item label="发布时间:" prop="startEndTime">
              <el-date-picker
                  v-model="startEndTime"
                  type="daterange"
                  range-separator="至"
                  start-placeholder="开始日期"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  :picker-options="pickerOptions"
                  end-placeholder="结束日期">
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-show="showComms">
          <el-col :span="6" :offset="1" v-show="commonMessage">
            <el-form-item label="发布范围" prop="communityIds">
              <el-select v-model="modelForm.communityIds" multiple placeholder="请选择发布范围" style="width: 250px"
                         @change="handleChangeCommunity">
                <el-option
                    v-for="item in communityList"
                    :key="item.communityId"
                    :label="item.communityName"
                    :value="item.communityId"
                ></el-option>
              </el-select>

            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-show="buildingShow">
          <el-col :span="6" :offset="1" v-show="commonMessage">
            <el-form-item label="楼栋范围" prop="buildingIds">
              <el-select v-model="modelForm.buildingIds" multiple placeholder="请选择楼栋范围" style="width: 250px" @change="handleChangeBuilding">
                <el-option
                    v-for="item in buildingList"
                    :key="item.buildingId"
                    :label="item.buildingName"
                    :value="item.buildingId"
                ></el-option>
              </el-select>

            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-show="unitShow">
          <el-col :span="6" :offset="1" v-show="commonMessage">
            <el-form-item label="单元范围" prop="unitIds">
              <el-select v-model="modelForm.unitIds" multiple placeholder="请选择单元范围" style="width: 250px">
                <el-option
                    v-for="item in unitList"
                    :key="item.unitId"
                    :label="item.unitName"
                    :value="item.unitId"
                ></el-option>
              </el-select>

            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :offset="1" v-show="commonMessage">
            <el-form-item label="内容" prop="content">
              <editor v-model="modelForm.content" :min-height="200"/>
            </el-form-item>
          </el-col>
        </el-row>
        <h4 class="form-header" v-show="commonMessage"></h4>
        <el-row>
          <el-col :offset="18" :span="2" v-show="commonMessage">
            <el-button type="primary" size="mini" style="margin-top: 10px" @click="cancel()">取消</el-button>
            <el-button type="success" size="mini" style="margin-top: 10px" @click="submitForm">保存</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>
  </div>
</template>
<script>
import {
  delQuestion,
  listQuestion,
  addQuestion,
  updateItem,
  getListRecord,
  bulletin
} from "@/api/govern/autonomy/vote/ownerVote";
import {getCurrentList, getBuildingList, getUnitList} from "@/api/govern/community";
import {equals, isEmpty} from "@/utils/common";

export default {
  data() {
    let validatorr = (rule, value, callback) => {
      if (isEmpty(this.startEndTime)) {
        callback(new Error("发布时间不能为空"))
      } else {
        callback();
      }
    }
    var isNumber = (rule, value, cb) => {
      let reg = /^[1-9]\d*$/;
      return reg.test(value) ? cb() : cb(new Error("请输入正整数"));
    };
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        }
      },
      filterText: '',
      //折叠面板
      activeNames: ['1'],
      // 遮罩层
      loading: true,
      // 投票业主遮罩层
      loadingRecord: true,
      //投票业主总条数
      totalRecord: 0,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      //显示详情
      showDetail: false,
      showMessage: true,
      showCommunitys: false,
      showComms: true,
      //是否展示列表和新增页面
      openView: true,
      //是否是问卷调查
      isYN: true,
      // 调查问卷表格数据
      questionList: [],
      // 投票业主
      recordList: [],
      //小区列表
      communityList: [],
      buildingList: [],
      unitList: [],
      currentList: [],
      //是否展示基本信息选项
      commonMessage: true,
      buildingShow: true,
      unitShow: true,
      // 总条数
      total: 0,
      // 弹出层标题
      title: "",
      // 日期范围
      dateRange: [],
      startEndTime: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
      },
      // 树结构
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      //题目
      modelForm: {
        //问卷调查名称
        voteName: null,
        //发布日期
        dateRange: null,
        //小区id
        communityIds: undefined,
        communityId: undefined,
        buildingId: undefined,
        buildingIds: [],
        unitIds: [],
        //类型
        questionType: undefined,
        //单选内容
        single: [{
          mustYN: '',
          title: '',
          itemValue: [{value: ''}]
        }],
        //多选内容
        multiple: [{
          mustYN: '',
          title: '',
          itemValue: [{value: ''}]
        }],
        //单行文本
        text: [{
          mustYN: '',
          title: '',
          itemValue: [{value: ''}]
        }],
      },
      // 表单参数
      form: {},
      rules: {
        startEndTime: [
          {trigger: "blur", validator: validatorr}
        ],
        voteName: [
          {required: true, message: "投票名称不可为空", trigger: "blur"},
          {max: 32, message: '投票名称不能超过32个字符', trigger: 'blur'}
        ],
        dateRange: [
          {required: true, message: "发布日期不可为空", trigger: "blur"}
        ],
        communityIds: [
          {required: true, message: "发布范围不可为空", trigger: "blur"}
        ],
        questionType: [
          {required: true, message: "调查类型不可为空", trigger: "blur"}
        ],
        surveyName: [
          {required: true, message: "投票名称不可为空", trigger: "blur"}
        ]
      },

      options: [{
        value: '1',
        label: '单选题'
      }, {
        value: '2',
        label: '多选题'
      }, {
        value: '3',
        label: '多行文本'
      }],
      value: '',
      open: undefined
    };
  },
  created() {
    this.getTree();
    this.getList();
    this.getProCommunity();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    // 树点击事件
    handleNodeClick(row) {
      this.queryParams.communityId = row.communityId
      this.getList();
    },

    // 过滤树
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 获取树数据
    getTree() {
      getCurrentList().then(res => {
        this.currentList = res.data;
      })
    },
    /**基本信息下一步*/
    nextTab() {
      this.$refs["modelForm"].validate(valid => {
        if (valid) {
          this.commonMessage = false;
        }
      });
    },
    /**返回基本信息*/
    upTab() {
      this.commonMessage = true;
    },
    /**单选删除选项*/
    removeDomainSingle(index, idx) { // 删除选项
      this.modelForm.single[index].itemValue.splice(idx, 1)
    },
    /**单选新增选项*/
    addDomainSingle(index) { // 新增选项
      this.modelForm.single[index].itemValue.push({value: ''})
    },
    /**单选新增题目*/
    addQuestionSingle() { // 新增题目
      this.modelForm.single.push({mustYN: '', title: '', itemValue: [{value: ''}]})
    },
    /**单选删除题目*/
    removeQuestionSingle(index) {//删除题目
      this.modelForm.single.splice(index, 1)
    },
    /**多选删除选项*/
    removeDomainMultiple(index, idx) { // 删除选项
      this.modelForm.multiple[index].itemValue.splice(idx, 1)
    },
    /**多选新增选项*/
    addDomainMultiple(index) { // 新增选项
      this.modelForm.multiple[index].itemValue.push({value: ''})
    },
    /**多选新增题目*/
    addQuestionMultiple() { // 新增题目
      this.modelForm.multiple.push({mustYN: '', title: '', itemValue: [{value: ''}]})
    },
    /**多选删除题目*/
    removeQuestionMultiple(index) {//删除题目
      this.modelForm.multiple.splice(index, 1);
    },

    /**单行文本新增题目*/
    addQuestionText() { // 新增题目
      this.modelForm.text.push({mustYN: '', title: '', itemValue: [{value: ''}]})
    },
    /**单行文本删除题目*/
    removeQuestionText(index) {//删除题目
      this.modelForm.text.splice(index, 1)
    },
    /** 查询问卷调查列表 */
    getList() {
      this.loading = true;
      listQuestion(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
            this.questionList = response.rows;
            this.total = response.total;
            this.loading = false;
          }
      );
    },
    /** 查询项目列表 */
    getProCommunity() {
      let param = {
        communityType: '1'
      }

      getCurrentList(param).then(response => {
        if (response.code == 200) {
          this.communityList = response.data;
        } else {
          this.msgError(response.msg);
        }
      });
    },
    // 取消按钮
    cancel() {
      this.open = !this.open
      this.openView = true;
      this.commonMessage = true;
      this.isYN = true;
      this.reset();
    },
    // 表单重置
    reset() {
      startEndTime:null,
          this.form = {};
      this.queryParams = {};
      this.resetForm("form");
      this.modelForm.single = [
        {
          mustYN: '',
          title: '',
          itemValue: [{value: ''}]
        }

      ];
      this.modelForm.multiple = [
        {
          mustYN: '',
          title: '',
          itemValue: [{value: ''}]
        }
      ];
      this.modelForm.text = [
        {
          mustYN: '',
          title: '',
          itemValue: [{value: ''}]
        }
      ];
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.reset();
      this.dateRange = null;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.voteId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /**
     * 选择小区
     * @param selection
     */
    handleChangeCommunity(selection) {
      this.buildingList = [];
      this.unitList = [];
      this.modelForm.buildingIds = [];
      this.modelForm.unitIds = [];
      if (selection.length === 1){
        getBuildingList(selection[0]).then(res => {
          this.buildingList = res.data;
        });
      }

      if (selection.length > 1) {
        this.buildingShow = false;
        this.unitShow = false;
      } else {
        this.buildingShow = true;
        this.unitShow = true;
      }
    },
    /**
     * 选择小区
     * @param selection
     */
    handleChangeBuilding(selection) {
      this.unitList = [];
      this.modelForm.unitIds = [];
      if (selection.length === 1){
        getUnitList(selection[0]).then(res => {
          this.unitList = res.data;
        });
      }

      if (selection.length > 1) {
        this.unitShow = false;
      } else {
        this.unitShow = true;
      }
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.startEndTime = null;
      this.modelForm.content = null;
      this.modelForm.voteName = null;
      this.modelForm.communityIds = undefined;
      this.modelForm.communityId = undefined;
      this.modelForm.questionType = null;
      this.modelForm.dateRange = null;
      this.reset();
      this.open = !this.open
      this.openView = false;
      this.title = "添加调查问卷";
      this.getProCommunity();
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.voteId || this.ids;
      // alert(ids)
      this.$confirm('是否确认删除所选中的的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        console.log(ids)
        return delQuestion(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      })
    },
    //查看详情
    handleSurvey(row) {
      this.showDetail = true;
      this.showMessage = false;
      this.form = row;
      this.getRecordList(row);
      this.open = true;
    },
    // 发布
    handleBulletin(row) {
      const voteId = row.voteId;
      this.$confirm('是否发布该社区投票?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(
          function () {
            const query = {
              voteId: voteId
            };
            return bulletin(query);
          }).then(() => {
        this.getList();
        this.$message({
          type: 'success',
          message: '发布成功!'
        });
      }).catch(() => {
      });
    },
    getRecordList(row) {
      console.log(row.voteId)
      let query = {
        'voteId': row.voteId
      };
      getListRecord(query).then(response => {
        console.log(response)
        this.recordList = response.rows;
        this.totalRecord = response.total;
        this.loadingRecord = false;
      });
    },
    //关闭详情
    closeDetail() {
      this.showDetail = false;
      this.showMessage = true;
      this.loadingRecord = true;
      this.getList();
      this.open = !this.open
    },
    /** 提交按钮 */
    submitForm() {
      this.modelForm.startEndTime = this.startEndTime;
      if (this.modelForm.content == null) {
        this.$modal.msgError("请补充内容");
        return;
      }
      this.$refs["modelForm"].validate(valid => {
        if (valid) {
          let params = {...this.modelForm}
          addQuestion(params).then(response => {
            this.$modal.msgSuccess("新增成功")
            this.single = null;
            this.openView = true;
            this.commonMessage = true;
            this.isYN = true;
            this.open = !this.open
            this.getList();
          });
        }
      });
    },

    submitItem: function (item) {
      let reg = /^[1-9]\d*$/;
      if (!reg.test(item.paperNum)) {
        this.$modal.msgError("选项“" + item.itemName + "”纸质人数不是正整数")
        return;
      }
      let param = {
        itemId: item.itemId,
        paperNum: item.paperNum,
        personNum: item.personNum
      }
      updateItem(param).then(res => {
        if (equals(res.code, 200)) {
          this.msgSuccess(res.msg);
        }
      })
    },
    selectSurveyType(value) {
      if ("0" === value) {
        this.modelForm.communityId = undefined;
        this.modelForm.buildingId = undefined;
        this.showComms = true;
        this.showCommunitys = false
      }
      if ("1" === value) {
        this.modelForm.communityId = undefined;
        this.modelForm.buildingId = undefined;
        this.showComms = true;
        this.showCommunitys = false
      }
      if ("2" === value) {
        this.modelForm.communityIds = undefined;
        this.showComms = false;
        this.showCommunitys = true
      }
    },
    /**根据选择的小区项目id查出所有子集楼栋*/
    handleSelectionChangeForBuilding(value) {
      listRoomNumberOfAddOwnerAccountForParent(value).then(response => {
        this.buildingList = response.data;
        this.showBuilding = true;
      });
    },
    paperNumPaperNum(item) {
      let reg = /^[1-9]\d*$/;
      if (!reg.test(item.paperNum)) {
        return;
      }
      item.personNum += parseInt(item.paperNum);
    },
    //问卷类型
    questionType(row) {
      if (row.questionType == "0") {
        return "问卷";
      } else if (row.questionType == "1") {
        return "投票";
      } else if (row.questionType == "2") {
        return "业主委员会";
      }
    },
  }
};

</script>

<style>
#box_relative {
  position: fixed;
  left: 1550px;
  top: 830px;
}

.el-date-editor, .el-select--medium {

  width: 100% !important;

}
</style>

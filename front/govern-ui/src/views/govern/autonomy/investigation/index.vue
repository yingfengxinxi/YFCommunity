<template>
  <div class="app-container">
    <div v-show="!open">
      <!--   选择树   -->
      <el-row :span="24">
        <el-col :span="4">
          <div class="head-container">
            <el-input placeholder="请输入小区名称" v-model="filterText">
            </el-input>

            <el-tree
              class="filter-tree"
              :data="currentList"
              :props="currentListProps"
              default-expand-all
              :filter-node-method="filterNode"
              icon-class="el-icon-star-on"
              @node-click="handleNodeClick"
              ref="tree"
            >
            </el-tree>
          </div>
        </el-col>
        <el-col :span="20">
          <el-form
            :model="queryParams"
            ref="queryForm"
            :inline="true"
            v-show="showSearch"
            label-width="68px"
          >
            <el-form-item label="问卷名称">
              <el-input
                v-emoji
                v-model.trim="queryParams.quesName"
                placeholder="请输入名称搜索"
                clearable
                size="small"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <!--        <el-form-item label="发布范围" prop="communityName">
          <el-select v-model="queryParams.communityId" clearable placeholder="请选择发布范围" style="width: 100%">
            <el-option
              v-for="item in communityList"
              :key="item.communityId"
              :label="item.communityName"
              :value="item.communityId">
            </el-option>
          </el-select>
        </el-form-item>-->
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
                v-hasPermi="['govern:investigation:add']"
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
                v-hasPermi="['govern:investigation:delete']"
                >删除
              </el-button>
            </el-col>
            <right-toolbar
              :showSearch.sync="showSearch"
              @queryTable="getList"
            ></right-toolbar>
          </el-row>
          <el-table
            v-loading="loading"
            :data="questionList"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column
              label="问卷名称"
              align="center"
              prop="quesName"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="发布范围"
              align="center"
              prop="estateName"
              :show-overflow-tooltip="true"
            />
            <el-table-column label="发布时间" align="center" prop="startDate" />
            <el-table-column label="发布人" align="center" prop="createBy" />
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
                  @click="handleSurvey(scope.row)"
                  v-hasPermi="['govern:investigation:query']"
                  >查看
                </el-button>
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
        </el-col>
      </el-row>
    </div>
    <!--查看详情  -->
    <div v-show="showDetail">
      <el-form ref="form" :model="form" label-width="120px">
        <h4 class="form-header">
          <el-row>
            <el-col :span="22">问卷调查详情</el-col>
            <el-col :span="2">
              <el-button @click="closeDetail">关 闭</el-button>
            </el-col>
          </el-row>
        </h4>
        <el-row>
          <el-col :span="3">
            <el-form-item label="问卷名称：" prop="quesName">{{
              form.quesName
            }}</el-form-item>
          </el-col>
          <el-col :span="3" :offset="1">
            <el-form-item label="发布范围：" prop="estateName">{{
              form.estateName
            }}</el-form-item>
          </el-col>
          <!--          <el-col :span="3" :offset="1">
            <el-form-item label="类型：" prop="questionType">
              <div v-if="form.questionType == 0">问卷调查</div>
              <div v-else-if="form.questionType == 1">投票调查</div>
              <div v-else-if="form.questionType == 2">业主委员会</div>
            </el-form-item>
          </el-col>-->
          <el-col :span="6" :offset="1">
            <el-form-item label="发布时间：" prop="startDate">{{
              form.startDate
            }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="结束时间：" prop="endDate">{{
              form.endDate
            }}</el-form-item>
          </el-col>
          <el-col :span="3" :offset="1">
            <el-form-item label="发布人：">{{ form.createBy }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-form ref="form1" :model="form" label-width="120px">
        <el-row>
          <el-col :span="8">
            <h4 class="form-header">单选题</h4>
            <el-row
              v-for="(heading, headIndex) in form.headingList"
              :key="'single' + headIndex"
              v-if="heading.questionType == 0"
              style="height: 650px; overflow-x: hidden"
            >
              <el-form-item label="题目名称：">{{
                heading.questionName
              }}</el-form-item>
              <el-row
                v-for="(item, itemIndex) in heading.itemList"
                :key="'singleItem' + itemIndex"
              >
                <el-col :span="12">
                  <el-form-item label="选项名称：">{{
                    item.itemName
                  }}</el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="选择人数：">{{
                    item.personNum
                  }}</el-form-item>
                </el-col>
                <!--                <el-col :span="12">
                  <el-form-item label="纸质人数:" prop="paperNum" >
                    <el-input onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" :maxlength="6" v-model="item.paperNum"
                              style="width: 120px" @change="paperNumPaperNum(item)" />
                  </el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-button type="primary" size="small" @click="submitItem(item)">确 定</el-button>
                </el-col>-->
              </el-row>
            </el-row>
          </el-col>
          <el-col :span="8">
            <h4 class="form-header">多选题</h4>
            <el-row
              v-for="(heading, headIndex) in form.headingList"
              :key="'multiple' + headIndex"
              v-if="heading.questionType == 1"
              style="height: 280px; overflow-x: hidden"
            >
              <el-form-item label="题目名称：">{{
                heading.questionName
              }}</el-form-item>
              <el-row
                v-for="(item, itemIndex) in heading.itemList"
                :key="'multipleItem' + itemIndex"
              >
                <el-col :span="12">
                  <el-form-item label="选项名称：">{{
                    item.itemName
                  }}</el-form-item>
                </el-col>
                <el-col :span="12">
                  <el-form-item label="选择人数：">{{
                    item.personNum
                  }}</el-form-item>
                </el-col>
              </el-row>
            </el-row>
          </el-col>
          <el-col :span="8">
            <h4 class="form-header">文本题</h4>
            <el-row
              v-for="(heading, index) in form.headingList"
              :key="'text' + index"
              v-if="heading.questionType == 2"
              style="height: 280px; overflow-x: hidden"
            >
              <el-form-item label="题目名称：">{{
                heading.questionName
              }}</el-form-item>
            </el-row>
          </el-col>
        </el-row>
      </el-form>
    </div>
    <!--新增问卷调查-->
    <div v-show="!openView">
      <el-form
        ref="modelForm"
        :model="modelForm"
        :rules="rules"
        label-width="80px"
      >
        <!--        <el-row>
          <el-col :span="6" :offset="1" v-show="commonMessage">
            <el-form-item label="调查类型" prop="questionType">
              <el-select v-model="modelForm.questionType" placeholder="请选择调查类型" style="width: 100%" @change="selectSurveyType">
                <el-option label="问卷调查" value="0"/>
                <el-option label="投票表决" value="1"/>
                <el-option label="业主委员会" value="2"/>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>-->
        <el-row>
          <el-col :span="6" :offset="1" v-show="commonMessage">
            <el-form-item label="问卷名称" prop="quesName">
              <el-input
                onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
                v-model="modelForm.quesName"
                style="width: 100%; margin-right: 1%"
                clearable
                placeholder="问卷调查名称"
                v-emoji
              />
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
                value-format="yyyy-MM-dd"
                :picker-options="pickerOptions"
                end-placeholder="结束日期"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-show="showComms">
          <el-col :span="6" :offset="1" v-show="commonMessage">
            <el-form-item label="发布范围" prop="communityIds">
              <el-select
                v-model="modelForm.communityIds"
                multiple
                placeholder="请选择发布范围"
                style="width: 250px"
              >
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
        <el-row v-show="showCommunitys">
          <el-col :span="6" :offset="1">
            <el-form-item label="所属小区" prop="communityId">
              <el-select
                v-model="modelForm.communityId"
                placeholder="请选择所属小区"
                style="width: 100%"
                @change="handleSelectionChangeForBuilding"
              >
                <el-option
                  v-for="item in communityList"
                  :key="item.communityId"
                  :label="item.communityName"
                  :value="item.communityId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row v-show="showCommunitys">
          <el-col :span="6" :offset="1">
            <el-form-item label="所属楼宇" prop="buildingId">
              <el-select
                v-model="modelForm.buildingId"
                placeholder="请选择所属楼宇"
                style="width: 100%"
              >
                <el-option
                  v-for="dict in buildingList"
                  :key="dict.communityId"
                  :label="dict.communityName"
                  :value="dict.communityId"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <h4 class="form-header" v-show="commonMessage"></h4>
        <el-row>
          <el-col :offset="18" :span="2" v-show="commonMessage">
            <el-button
              type="primary"
              size="mini"
              style="margin-top: 10px"
              @click="cancel()"
              >取消</el-button
            >
            <el-button type="success" size="mini" @click="nextTab()"
              >下一步</el-button
            >
          </el-col>
        </el-row>
        <el-row>
          <el-collapse v-model="activeNames" v-show="!commonMessage">
            <el-collapse-item title="单选题" name="1">
              <div v-for="(item, index) in modelForm.single" :key="index">
                <!--单选题题目-->
                <el-form-item :label="` ${index + 1}：`">
                  <el-input
                    onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
                    v-model.trim="item.title"
                    style="width: 230px"
                    clearable
                    placeholder="题目"
                  />
                  <el-button
                    style="margin-left: 20px"
                    @click="removeQuestionSingle(index)"
                    >删除题目</el-button
                  >
                </el-form-item>
                <!-- 单选题选项 -->
                <el-form-item
                  v-for="(opt, idx) in item.itemValue"
                  v-show="!commonMessage"
                  :key="idx"
                  :label="` 选项${idx + 1}`"
                >
                  <el-input
                    onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
                    v-model.trim="opt.value"
                    style="width: 258px"
                    clearable
                    placeholder="选项内容"
                  />
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    style="margin-left: 20px; color: red"
                    @click.prevent="removeDomainSingle(index, idx)"
                  ></el-button>
                </el-form-item>
                <el-form-item v-show="!commonMessage">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-plus"
                    style="margin-left: 20px; color: #092ee7"
                    @click.prevent="addDomainSingle(index)"
                    >添加选项</el-button
                  >
                </el-form-item>
              </div>
              <el-form-item v-show="isYN">
                <el-row>
                  <el-col :offset="6" :span="1">
                    <el-button
                      @click="addQuestionSingle"
                      style="margin-top: 20px"
                      >新增题目</el-button
                    >
                  </el-col>
                </el-row>
              </el-form-item>
            </el-collapse-item>
            <el-collapse-item title="多选题" name="2" v-show="isYN">
              <div v-for="(item, index) in modelForm.multiple" :key="index">
                <!--多选题题目-->
                <el-form-item :label="` ${index + 1}：`">
                  <el-input
                    onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
                    v-model.trim="item.title"
                    style="width: 230px"
                    clearable
                    placeholder="题目"
                  />
                  <el-button
                    style="margin-left: 20px"
                    @click="removeQuestionMultiple(index)"
                    >删除题目</el-button
                  >
                </el-form-item>
                <!-- 多选题选项 -->
                <el-form-item
                  v-for="(opt, idx) in item.itemValue"
                  v-show="!commonMessage"
                  :key="idx"
                  :label="` 选项${idx + 1}`"
                >
                  <el-input
                    onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
                    v-model.trim="opt.value"
                    style="width: 258px"
                    clearable
                    placeholder="选项内容"
                  />
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    style="margin-left: 20px; color: red"
                    @click.prevent="removeDomainMultiple(index, idx)"
                  ></el-button>
                </el-form-item>
                <el-form-item v-show="!commonMessage">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-plus"
                    style="margin-left: 20px; color: #092ee7"
                    @click.prevent="addDomainMultiple(index)"
                    >添加选项</el-button
                  >
                </el-form-item>
              </div>
              <el-form-item>
                <el-row>
                  <el-col :offset="6" :span="1">
                    <el-button
                      @click="addQuestionMultiple"
                      style="margin-top: 20px"
                      >新增题目</el-button
                    >
                  </el-col>
                  <!--                  <el-col :offset="2"  :span="1">
                    <el-button  style="margin-top: 20px" @click="removeQuestionMultiple(index)">删除题目</el-button>
                  </el-col>-->
                </el-row>
              </el-form-item>
            </el-collapse-item>
            <el-collapse-item title="单行文本题" name="3" v-show="isYN">
              <div v-for="(item, index) in modelForm.text" :key="index">
                <!--单行文本题题目-->
                <el-form-item :label="` ${index + 1}：`">
                  <el-input
                    onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
                    v-model.trim="item.title"
                    style="width: 230px"
                    clearable
                    placeholder="题目"
                  />
                  <el-button
                    style="margin-left: 20px"
                    @click="removeQuestionText(index)"
                    >删除题目</el-button
                  >
                </el-form-item>
              </div>
              <el-form-item>
                <el-row>
                  <el-col :offset="6" :span="1">
                    <el-button @click="addQuestionText" style="margin-top: 20px"
                      >新增题目</el-button
                    >
                  </el-col>
                  <!--                  <el-col :offset="2"  :span="1">
                    <el-button  style="margin-top: 20px" @click="removeQuestionText(index)">删除题目</el-button>
                  </el-col>-->
                </el-row>
              </el-form-item>
            </el-collapse-item>
          </el-collapse>
        </el-row>
        <el-row
          :offset="18"
          :span="2"
          v-show="!commonMessage"
          style="margin-left: 66%"
        >
          <!--          <el-col >-->
          <el-button type="success" size="mini" @click="upTab()"
            >上一步</el-button
          >
          <el-button
            type="primary"
            size="mini"
            style="margin-top: 10px"
            @click="cancel()"
            >取消</el-button
          >
          <el-button
            type="success"
            size="mini"
            style="margin-top: 10px"
            @click="submitForm"
            >保存</el-button
          >
          <!--          </el-col>-->
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
} from "@/api/govern/autonomy/investigation/investigation";
import { getCurrentList } from "@/api/govern/community";
import { getBuildingList } from "@/api/govern/community";
import { equals, isEmpty } from "@/utils/common";
export default {
  data() {
    let validatorr = (rule, value, callback) => {
      if (isEmpty(this.startEndTime)) {
        callback(new Error("发布时间不能为空"));
      } else {
        callback();
      }
    };
    var isNumber = (rule, value, cb) => {
      let reg = /^[1-9]\d*$/;
      return reg.test(value) ? cb() : cb(new Error("请输入正整数"));
    };
    return {
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        },
      },
      filterText: "",
      //折叠面板
      activeNames: ["1"],
      // 遮罩层
      loading: true,
      open: undefined,
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
      //小区列表
      communityList: [],
      currentList: [],
      buildingList: [],
      //是否展示基本信息选项
      commonMessage: true,
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
        label: "communityName",
      },
      //题目
      modelForm: {
        startEndTime: null,
        //问卷调查名称
        quesName: null,
        //发布日期
        dateRange: null,
        //小区id
        communityIds: undefined,
        communityId: undefined,
        buildingId: undefined,
        //类型
        questionType: undefined,
        //单选内容
        single: [
          {
            mustYN: "",
            title: "",
            itemValue: [{ value: "" }],
          },
        ],
        //多选内容
        multiple: [
          {
            mustYN: "",
            title: "",
            itemValue: [{ value: "" }],
          },
        ],
        //单行文本
        text: [
          {
            mustYN: "",
            title: "",
            itemValue: [{ value: "" }],
          },
        ],
      },
      // 表单参数
      form: {},
      rules: {
        startEndTime: [{ trigger: "blur", validator: validatorr }],
        quesName: [
          { required: true, message: "问卷名称不可为空", trigger: "blur" },
        ],
        dateRange: [
          { required: true, message: "发布日期不可为空", trigger: "blur" },
        ],
        communityIds: [
          { required: true, message: "发布范围不可为空", trigger: "blur" },
        ],
        questionType: [
          { required: true, message: "调查类型不可为空", trigger: "blur" },
        ],
        surveyName: [
          { required: true, message: "问卷调查名称不可为空", trigger: "blur" },
        ],
      },
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
    },
  },
  methods: {
    handleNodeClick(row) {
      this.queryParams.communityId = row.communityId;
      this.getList();
    },
    // 过滤树
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 获取树数据
    getTree() {
      getCurrentList().then((res) => {
        this.currentList = res.data;
      });
    },
    /**基本信息下一步*/
    nextTab() {
      this.$refs["modelForm"].validate((valid) => {
        if (valid) {
          //判断调查类型是否是问卷调查还是投票表决
          if (equals(this.modelForm.questionType, "2")) {
            this.isYN = false;
            this.showCommunitys = false;
          }
          this.commonMessage = false;
        }
      });
    },
    /**返回基本信息*/
    upTab() {
      this.commonMessage = true;
    },
    /**单选删除选项*/
    removeDomainSingle(index, idx) {
      // 删除选项
      this.modelForm.single[index].itemValue.splice(idx, 1);
    },
    /**单选新增选项*/
    addDomainSingle(index) {
      // 新增选项
      this.modelForm.single[index].itemValue.push({ value: "" });
    },
    /**单选新增题目*/
    addQuestionSingle() {
      // 新增题目
      this.modelForm.single.push({
        mustYN: "",
        title: "",
        itemValue: [{ value: "" }],
      });
    },
    /**单选删除题目*/
    removeQuestionSingle(index) {
      //删除题目
      this.modelForm.single.splice(index, 1);
    },
    /**多选删除选项*/
    removeDomainMultiple(index, idx) {
      // 删除选项
      this.modelForm.multiple[index].itemValue.splice(idx, 1);
    },
    /**多选新增选项*/
    addDomainMultiple(index) {
      // 新增选项
      this.modelForm.multiple[index].itemValue.push({ value: "" });
    },
    /**多选新增题目*/
    addQuestionMultiple() {
      // 新增题目
      this.modelForm.multiple.push({
        mustYN: "",
        title: "",
        itemValue: [{ value: "" }],
      });
    },
    /**多选删除题目*/
    removeQuestionMultiple(index) {
      //删除题目
      this.modelForm.multiple.splice(index, 1);
    },

    /**单行文本新增题目*/
    addQuestionText() {
      // 新增题目
      this.modelForm.text.push({
        mustYN: "",
        title: "",
        itemValue: [{ value: "" }],
      });
    },
    /**单行文本删除题目*/
    removeQuestionText(index) {
      //删除题目
      this.modelForm.text.splice(index, 1);
    },
    /** 查询问卷调查列表 */
    getList() {
      this.loading = true;
      listQuestion(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.questionList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 查询项目列表 */
    getProCommunity() {
      let param = {
        communityType: "1",
      };
      getCurrentList(param).then((response) => {
        if (response.code == 200) {
          this.communityList = response.data;
        } else {
          this.$modal.msgError(response.msg);
        }
      });
    },
    // 取消按钮
    cancel() {
      this.open = !this.open;
      this.openView = true;
      this.commonMessage = true;
      this.isYN = true;
      this.reset();
    },
    // 表单重置
    reset() {
      startEndTime: null, (this.form = {});
      this.queryParams = {};
      this.resetForm("form");
      this.modelForm.single = [
        {
          mustYN: "",
          title: "",
          itemValue: [{ value: "" }],
        },
      ];
      this.modelForm.multiple = [
        {
          mustYN: "",
          title: "",
          itemValue: [{ value: "" }],
        },
      ];
      this.modelForm.text = [
        {
          mustYN: "",
          title: "",
          itemValue: [{ value: "" }],
        },
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
      this.ids = selection.map((item) => item.questionnaireId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.startEndTime = null;
      this.modelForm.quesName = null;
      this.modelForm.communityIds = undefined;
      this.modelForm.communityId = undefined;
      this.modelForm.questionType = null;
      this.modelForm.dateRange = null;
      this.reset();
      this.open = !this.open;
      this.openView = false;
      this.title = "添加调查问卷";
      this.getProCommunity();
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.questionnaireId || this.ids;
      // alert(ids)
      this.$confirm("是否确认删除所选中的的数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delQuestion(ids);
        })
        .then(() => {
          this.getList();
          this.msgSuccess("删除成功");
        });
    },
    //查看详情
    handleSurvey(row) {
      this.showDetail = true;
      this.showMessage = false;
      this.form = row;
      this.open = true;
    },
    //关闭详情
    closeDetail() {
      this.showDetail = false;
      this.showMessage = true;
      this.getList();
      this.open = !this.open;
    },
    /** 提交按钮 */
    submitForm() {
      this.modelForm.startEndTime = this.startEndTime;
      let singleFlag = false;
      let single = this.modelForm.single;

      for (let i = 0; i < single.length; i++) {
        if (isEmpty(single[i].title)) {
          this.$modal.msgError("请补充单选题题目内容");
          return;
        }
        let item = single[i].itemValue;
        if (item.length > 0 && !equals(item, undefined)) {
          for (let j = 0; j < item.length; j++) {
            if (isEmpty(item[j].value)) {
              singleFlag = true;
              break;
            }
          }
        } else {
          singleFlag = true;
        }
        if (singleFlag) {
          this.$modal.msgError("请补充单选题选项内容");
          return;
        }
      }
      let multipleFlag = false;
      let multiple = this.modelForm.multiple;

      for (let i = 0; i < multiple.length; i++) {
        if (isEmpty(multiple[i].title)) {
          this.$modal.msgError("请补充多选题题目内容");
          return;
        }
        let item = multiple[i].itemValue;
        if (item.length > 0 && !equals(item, undefined)) {
          for (let j = 0; j < item.length; j++) {
            if (item.length <= 1) {
              this.$modal.msgError("多选题选项至少为两项");
              return;
            }
            if (isEmpty(item[j].value)) {
              multipleFlag = true;
              break;
            }
          }
        } else {
          multipleFlag = true;
        }
        if (multipleFlag) {
          this.$modal.msgError("请补充多选题选项内容");
          return;
        }
      }
      let text = this.modelForm.text;

      for (let i = 0; i < text.length; i++) {
        if (isEmpty(text[i].title)) {
          this.$modal.msgError("请补充文本题题目内容");
          return;
        }
      }

      if (single.length === 0 && multiple.length === 0 && text.length === 0) {
        this.$modal.msgError("请最少选择一种题目");
        return;
      }

      this.$refs["modelForm"].validate((valid) => {
        if (valid) {
          let params = { ...this.modelForm };
          addQuestion(params).then((response) => {
            this.$modal.msgSuccess("新增成功");
            this.single = null;
            this.openView = true;
            this.commonMessage = true;
            this.isYN = true;
            this.open = !this.open;
            this.getList();
          });
        }
      });
    },

    submitItem: function (item) {
      let reg = /^[1-9]\d*$/;
      if (!reg.test(item.paperNum)) {
        this.$modal.msgError("选项“" + item.itemName + "”纸质人数不是正整数");
        return;
      }
      let param = {
        itemId: item.itemId,
        paperNum: item.paperNum,
        personNum: item.personNum,
      };
      updateItem(param).then((res) => {
        if (equals(res.code, 200)) {
          this.msgSuccess(res.msg);
        }
      });
    },
    selectSurveyType(value) {
      if ("0" === value) {
        this.modelForm.communityId = undefined;
        this.modelForm.buildingId = undefined;
        this.showComms = true;
        this.showCommunitys = false;
      }
      if ("1" === value) {
        this.modelForm.communityId = undefined;
        this.modelForm.buildingId = undefined;
        this.showComms = true;
        this.showCommunitys = false;
      }
      if ("2" === value) {
        this.modelForm.communityIds = undefined;
        this.showComms = false;
        this.showCommunitys = true;
      }
    },
    /**根据选择的小区项目id查出所有子集楼栋*/
    handleSelectionChangeForBuilding(value) {
      getBuildingList(value).then((response) => {
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
  },
};
</script>

<style>
#box_relative {
  position: fixed;
  left: 1550px;
  top: 830px;
}
.el-date-editor,
.el-select--medium {
  width: 100% !important;
}
</style>

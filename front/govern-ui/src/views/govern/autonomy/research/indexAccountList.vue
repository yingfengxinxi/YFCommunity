<template>
  <div class="app-container">
    <el-row :gutter="10" class="mb8">
      <right-toolbar
        :search.sync="showSearch"
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="list"
      @selection-change="handleSelectionChange"
    >
      <el-table-column label="昵称" align="center" prop="accountNick" />
      <el-table-column label="姓名" align="center" prop="accountName" />
      <el-table-column label="手机号" align="center" prop="loginName" />
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
            @click="handleUpdate(scope.row)"
            >详情</el-button
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
    <el-dialog
      title="党建调研详情"
      :visible.sync="open"
      width="80%"
      append-to-body
    >
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        label-position="top"
      >
        <el-form-item label="问卷内容" prop="teacherClassIds">
          <div v-for="(item, index) in testingStandards" :key="index">
            <el-divider style="font-size: 16px" plain content-position="left"
              >题目{{ index + 1 }}</el-divider
            >

            <el-row :gutter="20" style="margin-bottom: 15px">
              <el-col :span="7">
                {{ item.questionName }}
                <template v-if="item.questionType == '0'">(单选)</template>
                <template v-if="item.questionType == '1'">(多选)</template>
                <template v-if="item.questionType == '2'">(文本)</template>
              </el-col>
            </el-row>
            <el-row :gutter="20" style="margin-left: 35px; margin-bottom: 15px">
              <el-form-item label="" prop="datestingStandards">
                <div
                  v-for="(item1, index1) in item.questionItems"
                  :key="index1"
                  style="margin-bottom: 5px"
                >
                  <template v-if="item.questionType == '0'">
                    <!-- 单选 -->
                    <el-radio
                      disabled
                      v-model="item.datestingStandards.userAnswer"
                      :label="String(item1.itemId)"
                      >{{ item1.itemName }}</el-radio
                    >
                  </template>
                </div>
                <template v-if="item.questionType == '1'">
                  <el-checkbox-group
                    v-model="item.datestingStandards.userAnswerArr"
                  >
                    <div
                      v-for="(item1, index1) in item.questionItems"
                      :key="index1"
                    >
                      <el-checkbox
                        disabled
                        :label="String(item1.itemId)"
                        :name="item1.itemName"
                        >{{ item1.itemName }}</el-checkbox
                      >
                    </div>
                  </el-checkbox-group>
                </template>
                <template v-if="item.questionType == '2'">
                  <!-- 文本 -->
                  <el-input
                    disabled
                    v-model="item.datestingStandards.userAnswer"
                    type="textarea"
                    style="width: 50%"
                  />
                </template>
              </el-form-item>
            </el-row>
          </div>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  findListAccoutByQuestionId,
  findListPartyMemberResearch,
} from "@/api/govern/autonomy/research/research";

import { getToken } from "@/utils/auth";
export default {
  name: "IndexAccountList",
  props: ["id"],
  data() {
    return {
      tableId: "",
      typeOptions: [
        { value: "0", label: "单选" },
        { value: "1", label: "多选" },
        { value: "2", label: "文本" },
      ],
      status: [
        { value: "0", label: "未发布" },
        { value: "1", label: "进行中" },
        { value: "2", label: "已提交" },
        { value: "3", label: "已结束" },
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
      showSearch: false,
      // 总条数
      total: 0,
      // 问卷调查表格数据
      list: [],
      // 表单校验
      rules: {},
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        },
      },
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        // title: null,
      },
      communityId: "",
      // 表单参数
      form: {
        releaseStatus: "0",
      },
      testingStandards: [
        {
          id: "",
          problemName: "",
          type: "",
          sort: "",
          datestingStandards: [{ id: "", name: "", flag: "0", text: "" }],
        },
      ],
    };
  },
  created() {},
  methods: {
    receiveValue(newVal) {
      console.log("newVal>>>", newVal);
      this.tableId = newVal;
      this.getList();
    },
    handleFind(row) {},
    //添加
    addRow() {
      this.testingStandards.push({
        id: "",
        problemName: "",
        type: "",
        sort: "",
        datestingStandards: [{ id: "", name: "", flag: "0", text: "" }],
      });
    },
    addRow1(item1) {
      item1.datestingStandards.push({ id: "", name: "", flag: "0", text: "" });
    },
    // 移除某行
    removeRow1(k0, k) {
      console.log("k0.datestingStandards", k0.datestingStandards);
      if (k0.datestingStandards.length === 0) {
        return;
      }
      k0.datestingStandards = k0.datestingStandards.filter(
        (item) => item !== k
      );
    },
    // 移除某行
    removeRow(k) {
      if (this.testingStandards.length === 0) {
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
      this.$modal
        .confirm('确认要"' + text + '""' + row.title + '"问卷调查吗？')
        .then(function () {
          return updateByIdStatus(row.releaseStatus, row.id);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess(text + "成功");
        })
        .catch(function () {
          row.releaseStatus = row.releaseStatus === "0" ? "1" : "0";
        });
    },
    /** 查询问卷调查列表 */
    getList() {
      this.loading = true;
      this.queryParams.tableId = this.tableId; // 更新props属性的值
      // let data = {
      //   tableId: this.tableId,
      // };
      findListAccoutByQuestionId(this.queryParams).then((response) => {
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
      };
      (this.testingStandards = [
        {
          id: "",
          problemName: "",
          type: "",
          sort: "",
          datestingStandards: [{ id: "", name: "", flag: "0", text: "" }],
        },
      ]),
        this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      console.log("搜索");
      this.queryParams.communityId = this.communityId;
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
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      let data = {
        accountId: row.accountId,
        researchId: this.tableId,
      };
      findListPartyMemberResearch(data).then((response) => {
        let data = response.research;
        data.forEach((element, index) => {
          element.datestingStandards = response.quesAnswers[index];
        });
        this.testingStandards = data;
        console.log(this.testingStandards, "this.testingStandards");
        // this.form = response.data;
        // if (this.form.content != null) {
        //   this.testingStandards = JSON.parse(this.form.content);
        // } else {
        //   this.testingStandards = [
        //     {
        //       id: "",
        //       problemName: "",
        //       type: "",
        //       sort: "",
        //       datestingStandards: [{ id: "", name: "", flag: "0", text: "" }],
        //     },
        //   ];
        // }

        this.open = true;
        this.title = "修改问卷调查";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.form.communityId = this.communityId;
      this.form.content = JSON.stringify(this.testingStandards);
      this.$refs["form"].validate((valid) => {
        if (valid) {
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

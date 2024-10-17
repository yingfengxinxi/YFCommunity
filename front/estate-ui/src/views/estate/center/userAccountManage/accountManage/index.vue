<template>
  <div class="app-container">
    <div v-show="isFlag">
      <el-row style="margin-top: 20px; margin-left: 15px">
        <el-col :span="5">
          <el-input placeholder="请输入小区名称" v-model="filterText">
          </el-input>
          <div class="head-container">
            <el-tree
              class="filter-tree hide-tree"
              :data="data"
              :props="{ children: '' }"
              style="margin-top: 10px"
              :highlight-current="true"
              node-key="id"
              :filter-node-method="filterNode"
              :expand-on-click-node="false"
              :current-node-key="default_id"
              @node-click="getCommunityId"
              ref="tree"
            >
              <!-- 插入楼栋图标 -->
              <span slot-scope="{ node }">
                <i class="el-icon-office-building" />
                <span style="padding-left: 5px; font-size: 14px">{{
                  node.label
                }}</span>
              </span>
            </el-tree>
          </div>
        </el-col>
        <el-col style="margin-left: 15px" :span="18">
          <el-form
            :model="queryParams"
            ref="queryForm"
            size="small"
            :inline="true"
          >
            <el-form-item label="用户角色" prop="roleId">
              <el-select
                v-model="queryParams.roleId"
                placeholder="请选择用户角色"
                clearable
              >
                <el-option
                  v-for="type in roleList"
                  :key="type.value"
                  :label="type.label"
                  :value="type.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="用户名称" prop="accountName">
              <el-input
                v-model="queryParams.accountName"
                placeholder="请输入用户名称"
                clearable
              />
            </el-form-item>
            <el-form-item label="用户账号" prop="loginName">
              <el-input
                v-model="queryParams.loginName"
                placeholder="请输入用户账号"
                clearable
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
          <el-table v-loading="loading" :data="dataList">
            <el-table-column label="头像" align="center" prop="avatar">
              <template slot-scope="scope">
                <image-preview
                  :src="scope.row.avatar"
                  :width="50"
                  :height="50"
                />
              </template>
            </el-table-column>

            <el-table-column label="账号" align="center" prop="loginName" />
            <!-- <el-table-column
              label="用户名称"
              align="center"
              prop="accountNick"
              show-overflow-tooltip
            /> -->
            <el-table-column
              label="用户姓名"
              align="center"
              prop="accountName"
              show-overflow-tooltip
            />
            <el-table-column label="用户角色" align="center">
              <template slot-scope="scope">
                {{
                  scope.row.accountType == "0"
                    ? "业主"
                    : scope.row.accountType == "1"
                    ? "租客"
                    : scope.row.accountType == "2"
                    ? "访客"
                    : ""
                }}
              </template>
            </el-table-column>
            <el-table-column label="是否是业委会成员" align="center">
              <template slot-scope="scope">
                <el-switch
                  v-model="scope.row.isPropertyCouncil"
                  active-value="1"
                  inactive-value="0"
                  @change="handleStatusChange(scope.row)"
                ></el-switch>
              </template>
            </el-table-column>
            <el-table-column
              label="注册时间"
              align="center"
              prop="createTime"
              show-overflow-tooltip
            >
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.createTime) }}</span>
              </template>
            </el-table-column>
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
                  @click="
                    isFlag = false;
                    info = scope.row;
                  "
                  >查看</el-button
                ><br />
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-refresh"
                  style="color: red; margin-left: 20px"
                  @click="
                    dialogVisible = true;
                    info = scope.row;
                  "
                  >重置密码</el-button
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
        </el-col>
      </el-row>
    </div>
    <Preview v-show="!isFlag" @close="isFlag = true" :info="info" />

    <el-dialog title="密码重置" :visible.sync="dialogVisible" width="25%">
      <el-form
        ref="ruleForm"
        :rules="rules"
        :model="ruleForm"
        class="demo-ruleForm"
      >
        <el-form-item
          label="新密码"
          :label-width="formLabelWidth"
          prop="newPwd"
        >
          <el-input
            type="password"
            v-model="ruleForm.newPwd"
            autocomplete="off"
            maxlength="200"
            show-password
          ></el-input>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="resetForm" style="margin-right: 20px"
          >关 闭</el-button
        >
        <el-button type="primary" slot="reference" @click="updatePwd"
          >修 改</el-button
        >
      </span>
    </el-dialog>
  </div>
</template>

<script>
import Preview from "./Preview";
import {
  query,
  updateIsPropertyCouncil,
} from "@/api/estate/center/userAccountManage/accountManage";
import { getCurrentList } from "@/api/estate/community";
import { desensitization } from "@/utils/common";
import { updatePwd } from "@/api/estate/center/userAccountManage/accountManage";
export default {
  name: "index",
  components: { Preview },
  data() {
    return {
      default_id: "",
      filterText: "",
      isFlag: true,
      //查找条件
      queryParams: {
        roleId: "",
        name: "",
        account: "",
        pageNum: 1,
        pageSize: 10,
        volunteer: "",
      },
      dialogVisible: false,
      formLabelWidth: "100px",
      ruleForm: {
        oldPwd: "",
        newPwd: "",
      },
      rules: {
        oldPwd: [
          {
            required: true,
            message: "请输入原密码",
            trigger: "blur",
          },
        ],
        newPwd: [
          {
            required: true,
            message: "请输入新密码",
            trigger: "blur",
          },
          {
            min: 6,
            max: 12,
            message: "长度在 6 到 12 个字符",
            trigger: "blur",
          },
          {
            required: true,
            pattern: /^[A-Za-z0-9]+$/,
            message: "密码的格式只能是字母、数字",
            trigger: "blur",
          },
        ],
      },
      // 总条数
      total: 0,
      //
      dataList: [],
      // 遮罩层
      loading: true,
      roleList: [
        {
          value: "0",
          label: "业主",
        },
        {
          value: "1",
          label: "租客",
        },
        {
          value: "2",
          label: "访客",
        },
      ],
      volunteerList: [
        { label: "是", value: "1" },
        { label: "否", value: "0" },
      ],
      //查看对象
      info: "",
      data: [],
    };
  },
  created() {
    this.initData();
  },
  watch: {
    //小区过滤
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },

  methods: {
    // 更改状态
    handleStatusChange(row) {
      var isPropertyCouncil = row.isPropertyCouncil;
      let text =
        isPropertyCouncil == "1"
          ? "设置为业委会成员吗？"
          : "从业委会成员移除吗？";
      console.log(text);
      this.$modal
        .confirm("确认要把用户 " + row.accountNick + " " + text)
        .then(function () {
          return updateIsPropertyCouncil(row.accountId, isPropertyCouncil);
        })
        .then(() => {
          let text =
            isPropertyCouncil == "1" ? "设置为业委会成员" : "从业委会成员移除";
          this.$modal.msgSuccess(text + "成功");
          this.getList();
        })
        .catch(() => {
          row.isPropertyCouncil = isPropertyCouncil === "0" ? "1" : "0";
        });
    },
    hide(str, begin, end) {
      if (str) {
        return desensitization(str, begin, end);
      }
      return "";
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    resetForm() {
      this.$refs["ruleForm"].resetFields();
      this.dialogVisible = false;
    },
    //密码修改
    updatePwd() {
      this.$refs["ruleForm"].validate((valid) => {
        if (valid) {
          this.$confirm("确定要修改该账号密码?", "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              this.ruleForm["id"] = this.info.accountId + "";
              return updatePwd(this.ruleForm).then((e) => {
                if (e.code == 200) {
                  this.$message({
                    message: "修改成功!!",
                    type: "success",
                  });
                } else {
                  this.$message({
                    message: "修改失败!!",
                    type: "error",
                  });
                }
              });
            })
            .then(() => {
              this.dialogVisible = false;
              this.resetForm("ruleForm");
            });
        }
      });
    },
    //初始化数据
    initData() {
      getCurrentList().then((e) => {
        const { data } = e;
        if (data) {
          let arr = new Array();
          data.forEach((item) => {
            const { communityId, communityName } = item;
            arr.push({
              id: communityId,
              label: communityName,
              children: [{}],
            });
          });
          this.data = arr;
          this.queryParams["communityId"] = data[0].communityId;
          this.default_id = data[0].communityId;

          this.$nextTick(() => {
            this.$refs["tree"].setCurrentKey(this.default_id);
          });
          this.getList();
        } else {
          this.$message.error("暂无小区");
        }
      });
    },
    //查询数据
    getList() {
      this.loading = true;
      query(this.queryParams).then((e) => {
        this.dataList = e.rows;
        this.total = e.total;
        this.loading = false;
      });
    },
    //获取点击小区id
    getCommunityId(e) {
      this.queryParams["communityId"] = e.id;
      this.getList();
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
  },
};
</script>

<style scoped>
/*设置高亮颜色*/
/deep/
  .el-tree--highlight-current
  .el-tree-node.is-current
  > .el-tree-node__content {
  background-color: #b3f1b6 !important;
}
</style>

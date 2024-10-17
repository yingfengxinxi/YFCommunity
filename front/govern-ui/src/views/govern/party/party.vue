<template>
  <div class="app-container">
    <div v-show="!open">
      <el-form
        :model="queryParams"
        ref="queryForm"
        v-show="showSearch"
        :inline="true"
        class="cus-fm"
      >
        <el-form-item label="所属小区：" prop="communityId">
          <el-select
            v-model="queryParams.communityId"
            placeholder="请选择所属小区"
            style="width: 100%"
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
        <el-form-item label="党组织名称" prop="partyName">
          <el-input
            v-emoji
            v-model.trim="queryParams.partyName"
            placeholder="请输入党组织名称"
            clearable
            size="small"
            style="width: 240px"
          />
        </el-form-item>
        <el-form-item label="党组织状态" prop="partyStatus">
          <el-select
            v-model="queryParams.partyStatus"
            placeholder="请选择党组织状态"
            style="width: 100%"
          >
            <el-option label="正常" value="0" />
            <el-option label="关闭" value="1" />
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
            v-hasPermi="['govern:party:add']"
            >新增
          </el-button>
        </el-col>
        <right-toolbar
          :showSearch.sync="showSearch"
          @queryTable="getList"
        ></right-toolbar>
      </el-row>
      <el-table v-loading="loading" :data="partyList" v-show="show">
        <el-table-column
          label="党组织名称"
          prop="partyName"
          align="center"
          show-overflow-tooltip
        />
        <el-table-column label="所属小区" prop="communityName" align="center" />
        <el-table-column label="党组织管理员" prop="adminName" align="center" />
        <el-table-column
          label="是否顶层党组织"
          prop="topParty"
          align="center"
          :formatter="topParty"
        />
        <el-table-column
          label="党组织状态"
          prop="partyStatus"
          align="center"
          :formatter="partyStatusFormat"
        />
        <el-table-column label="创建时间" prop="createTime" />
        <el-table-column label="最后操作时间" prop="updateTime" />
        <el-table-column label="最后操作人" prop="updateBy" align="center" />
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <!--            <el-button
                          size="mini"
                          type="text"
                          icon="el-icon-view"
                          @click="handleFind(scope.row,scope.index)"
                          v-hasPermi="['system:organization:query']"
                        >查看
                        </el-button>-->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['govern:party:edit']"
              >修改
            </el-button>
            <el-button
              type="text"
              plain
              icon="el-icon-delete"
              size="mini"
              style="color: red"
              @click="handleDelete(scope.row)"
              v-hasPermi="['govern:party:remove']"
              >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
      <pagination
        v-show="total > 0 && show"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
    <!-- 添加页面-->
    <div v-show="openParty">
      <el-form ref="forms" :model="forms" :rules="rules" label-width="150px">
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="是否顶层党组织:" prop="topParty">
              <el-select
                v-model="forms.topParty"
                placeholder="请选择是否顶层党组织"
                style="width: 100%"
              >
                <el-option label="是" value="0" />
                <el-option label="不是" value="1" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1" v-if="forms.topParty == 1">
            <el-form-item label="顶层党组织" prop="topId">
              <el-select
                v-model="forms.topId"
                placeholder="请选择顶层党组织"
                style="width: 100%"
              >
                <el-option
                  v-for="{ partyId, partyName } in topOrganizationList"
                  :key="partyId"
                  :label="partyName"
                  :value="partyId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <h4 class="form-header">基本信息</h4>
        <el-row>
          <el-col :span="6" :offset="1" v-if="forms.topParty == 1">
            <el-form-item label="上层党组织" prop="parentId">
              <el-select
                v-model="forms.parentId"
                placeholder="请选择上层党组织"
                style="width: 100%"
                clearable
              >
                <el-option
                  v-for="{ partyId, partyName } in parentOrganizationList"
                  :key="partyId"
                  :label="partyName"
                  :value="partyId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="党组织名称:" prop="partyName">
              <el-input
                onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
                v-model="forms.partyName"
                placeholder="请输入党组织名称"
                :maxlength="20"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="党组织状态:" prop="partyStatus">
              <el-select
                v-model="forms.partyStatus"
                placeholder="请选择党组织状态"
                style="width: 100%"
              >
                <el-option label="正常" value="0" />
                <el-option label="关闭" value="1" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="党组织管理员:" prop="administratorId">
              <el-select
                v-model="forms.adminId"
                placeholder="请选择党组织管理员"
                style="width: 100%"
              >
                <el-option
                  v-for="member in memberList"
                  :key="member.adminId"
                  :label="member.adminName"
                  :value="member.adminId"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="组织类型:" prop="partyType">
              <el-select
                v-model="forms.partyType"
                placeholder="请选择是否顶层党组织"
                style="width: 100%"
              >
                <el-option
                  v-for="dict in dict.type.party_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="所属小区" prop="communityId">
              <el-select
                v-model="forms.communityId"
                placeholder="请选择所属小区"
                style="width: 100%"
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
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="单位名称:" prop="unitName">
              <el-input
                onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
                v-model="forms.unitName"
                placeholder="请输入单位名称"
                v-emoji
                :maxlength="20"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="单位类别:" prop="unitType">
              <el-select
                v-model="forms.unitType"
                placeholder="请选择单位类别"
                style="width: 100%"
              >
                <el-option
                  v-for="dict in dict.type.unit_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="单位建档组织情况:" prop="unitCase">
              <el-select
                v-model="forms.unitCase"
                placeholder="请选择单位建档组织情况"
                style="width: 100%"
              >
                <el-option
                  v-for="dict in dict.type.unit_case"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                >
                </el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="14" :offset="1">
            <el-form-item label="党组织介绍:" prop="content">
              <el-input
                onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
                type="textarea"
                :rows="10"
                v-model="forms.content"
                placeholder="请输入党组织介绍（200字以内）"
                v-emoji
                :maxlength="200"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="党组织logo" prop="logoUrl">
              <el-upload
                class="avatar-uploader"
                action="#"
                :show-file-list="false"
                :on-change="handleImageChange"
                accept="image/*"
                :before-upload="beforeUpload"
              >
                <el-image
                  v-if="bannerUrl.url != undefined"
                  :src="forms.logoUrl"
                  class="avatar"
                ></el-image>
                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
              </el-upload>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-row>
          <el-col :offset="20" :span="2">
            <el-button @click="cancel">取 消</el-button>
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
import {
  partyList,
  uploadImg,
  listOrganizationForTop,
  getMemberList,
  addParty,
  updateParty,
  getParty,
  delParty,
  listOrganizationForParty,
} from "../../../api/govern/party/party";
import { getCurrentList } from "@/api/govern/community";
import { equals } from "@/utils/common";

export default {
  name: "party",
  dicts: ["party_type", "unit_type", "unit_case"],
  data() {
    return {
      BASE_API: process.env.VUE_APP_BASE_API,
      //图片
      bannerUrl: {
        url: undefined,
      },
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
      // 党组织表格数据
      partyList: [],
      // 项目列表
      communityList: [],
      //上层党组织列表
      parentOrganizationList: [],
      //顶层党组织列表
      topOrganizationList: [],
      //党员列表
      memberList: [],
      //删除判断
      getOrga: {},
      getOrganizationOne: true,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      disabledTop: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        partyName: null,
        communityId: null,
        partyStatus: null,
      },
      // 表单参数
      forms: {
        logoUrl: "",
      },
      // 表单校验
      rules: {
        topId: [
          { required: true, message: "请选择顶层党组织", trigger: "blur" },
        ],
        parentId: [
          { required: true, message: "请选择上层党组织", trigger: "blur" },
        ],
        partyName: [
          { required: true, message: "党组织名称不能为空", trigger: "blur" },
          {
            pattern:
              /^[\u4e00-\u9fa5_a-zA-Z0-9_(_（_)_）_,_，_:_：_—_\-_/_\n]+$/,
            message: "请正确输入文本框",
            trigger: "blur",
          },
        ],
        topParty: [
          {
            required: true,
            message: "请选择是否是顶层党组织",
            trigger: "blur",
          },
        ],
        communityId: [
          { required: true, message: "请选择所属小区", trigger: "blur" },
        ],
        partyStatus: [
          { required: true, message: "请选择党组织状态", trigger: "blur" },
        ],
        partyType: [
          { required: true, message: "请选择组织类型", trigger: "blur" },
        ],
        unitName: [
          { required: true, message: "单位名称不能为空", trigger: "blur" },
          {
            pattern:
              /^[\u4e00-\u9fa5_a-zA-Z0-9_(_（_)_）_,_，_:_：_—_\-_/_\n]+$/,
            message: "请正确输入文本框",
            trigger: "blur",
          },
        ],
        unitType: [
          { required: true, message: "单位类型不能为空", trigger: "blur" },
        ],
        unitCase: [
          {
            required: true,
            message: "请选择单位建党组织情况",
            trigger: "blur",
          },
        ],
        content: [
          { required: true, message: "请输入党组织介绍", trigger: "blur" },
        ],
        logoUrl: [
          { required: true, message: "党组织logo不能为空", trigger: "change" },
        ],
      },
      //详情页面
      showDetail: false,
      //显示页面
      show: true,
      //添加页面
      openParty: false,
    };
  },
  watch: {
    "forms.logoUrl": {
      handler(newVal, oldVal) {
        const url = this.forms?.logoUrl;
        if (url) {
          this.$refs["forms"].clearValidate(["logoUrl"]);
        }
      },
      deep: true,
    },
  },
  created() {
    this.getCurrentList();
  },
  methods: {
    /** 所属小区 */
    getCurrentList() {
      getCurrentList().then((response) => {
        this.communityList = response.data;
        // if (this.communityList.length > 0) {
        //   this.queryParams.communityId = this.communityList[0].communityId;
        // }
        this.getList();
      });
    },
    /** 列表 */
    getList() {
      this.loading = true;
      partyList(this.queryParams).then((response) => {
        this.partyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.bannerUrl = {
        url: undefined,
      };
      this.resetForm("queryForm");
      console.log(this.queryParams, "query");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.openParty = true;
      this.title = "添加党组织";
      // this.forms.communityId = this.queryParams.communityId;
      // 查询顶级党组织
      this.topOrganization();
      // 查询上一级党组织
      this.parentOrganization();
      // 查党员
      this.getMemberList();
    },
    // 取消按钮
    cancel() {
      this.showReason = false;
      this.showDetail = false;
      this.open = false;
      this.openParty = false;
      this.reset();
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["forms"].validate((valid) => {
        if (valid) {
          // if (this.forms.logoUrl === undefined) {
          //   this.$modal.msgError("图片不能为空!");
          //   return;
          // }
          if (this.forms.partyId != null) {
            updateParty(this.forms).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.openParty = false;
              this.getList();
            });
          } else {
            addParty(this.forms).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.openParty = false;
              this.getList();
            });
          }
        }
      });
    },
    // 表单重置
    reset() {
      this.forms = {};
      this.bannerUrl.url = undefined;
      this.resetForm("form");
    },
    /** 上传预处理 */
    beforeUpload(file) {
      return false;
    },
    // 上传图片
    handleImageChange(file, fileList) {
      let formData = new FormData();
      formData.append("file", file.raw);
      uploadImg(formData)
        .then((response) => {
          this.forms.logoUrl = response.imgUrl;
          this.bannerUrl.url = response.imgUrl;
          this.$modal.msgSuccess("上传成功");
        })
        .catch((err) => {
          this.$modal.msgError("上传失败");
        });
    },
    /** 查询顶层党组织 */
    topOrganization() {
      listOrganizationForTop().then((response) => {
        this.topOrganizationList = response.data;
      });
    },
    /** 查询上层党组织列表 */
    parentOrganization() {
      listOrganizationForParty().then((response) => {
        this.parentOrganizationList = response.data;
      });
    },
    /** 查询党员列表 */
    getMemberList() {
      getMemberList().then((response) => {
        this.memberList = response.data;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.open = true;
      this.openParty = true;
      this.reset();
      this.topOrganization();
      this.parentOrganization();
      getParty(row.partyId).then((response) => {
        this.forms = response.data;
        this.bannerUrl.url = this.forms.logoUrl;
        if (equals(response.data.topId, "0")) {
          this.forms.topId = undefined;
        }
        if (equals(response.data.parentId, "0")) {
          this.forms.parentId = undefined;
        }
        this.open = true;
        this.title = "修改党组织";
        // 查党员
        this.getMemberList();
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.partyId || this.ids;
      this.$confirm("是否确认删除所选中数据?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {})
        .then(() => {
          delParty(ids).then((re) => {
            this.getList();
            this.msgSuccess("删除成功");
          });
        })
        .catch(() => {});
    },
    topParty(row) {
      if (equals(row.topParty, "0")) {
        return "是";
      } else if (equals(row.topParty, "1")) {
        return "不是";
      }
      return "-";
    },
    partyStatusFormat(row) {
      if (equals(row.partyStatus, "0")) {
        return "正常";
      } else if (equals(row.partyStatus, "1")) {
        return "关闭";
      }
      return "-";
    },
  },
};
</script>

<style scoped>
::v-deep .avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.avatar-uploader .el-upload:hover {
  border-color: #409eff;
}
.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

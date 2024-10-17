<template>
  <div>
    <div v-show="checkView">
      <!--搜索条件-->
      <el-form
        :model="queryParams"
        ref="queryForm"
        size="small"
        :inline="true"
        v-show="showSearch"
      >
        <el-form-item label="创建日期">
          <el-date-picker
            v-model="dateRange"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            @keyup.enter.native="handleQuery"
          ></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            v-hasPermi="['govern:bulletin:list']"
            icon="el-icon-search"
            size="mini"
            @click="handleQuery"
            >搜 索
          </el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
            >重 置</el-button
          >
        </el-form-item>
      </el-form>

      <!--功能 -->
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['govern:bulletin:add']"
            >新增
          </el-button>
        </el-col>

        <right-toolbar
          :showSearch.sync="showSearch"
          @queryTable="getList"
        ></right-toolbar>
      </el-row>

      <!--  主页表单  -->
      <el-table
        v-loading="loading"
        :data="bulletinList"
        row-key="bulletinId"
        style="padding-top: 20px"
      >
        <el-table-column
          label="姓名"
          align="center"
          key="userName"
          prop="userName"
        >
          <template slot-scope="scope">
            {{ isManage ? scope.row.userName : handleName(scope.row.userName) }}
          </template>
        </el-table-column>
        <el-table-column label="头像" align="center" key="avatar" prop="avatar">
          <template slot-scope="scope">
            <el-image
              style="width: 80px; height: 80px; margin-bottom: -4px"
              :src="`${scope.row.avatar}`"
            >
            </el-image>
          </template>
        </el-table-column>
        <el-table-column label="性别" align="center" key="gender" prop="gender">
          <template slot-scope="scope">
            {{
              scope.row.gender == 0 ? "男" : scope.row.gender == 1 ? "女" : ""
            }}
          </template>
        </el-table-column>
        <el-table-column
          label="年龄"
          align="center"
          key="userAge"
          prop="userAge"
        >
          <template slot-scope="scope">
            {{ scope.row.userAge + "岁" }}
          </template>
        </el-table-column>
        <el-table-column
          label="犯罪级别"
          align="center"
          key="crimeLevel"
          prop="crimeLevel"
        >
          <template slot-scope="scope">
            {{ scope.row.crimeLevel + "级" }}
          </template>
        </el-table-column>
        <el-table-column
          label="证件类型"
          align="center"
          key="cardType"
          prop="cardType"
        >
          <template slot-scope="scope">
            {{ getDictLabel(scope.row.cardType) }}
          </template>
        </el-table-column>
        <el-table-column
          label="证件号码"
          align="center"
          key="cardNo"
          prop="cardNo"
        >
          <template slot-scope="scope">
            {{ handleCard(scope.row.cardNo) }}
          </template>
        </el-table-column>
        <el-table-column label="线索数" align="center" prop="clueNum" />
        <el-table-column
          label="发布时间"
          align="center"
          key="createTime"
          prop="createTime"
        />
        <el-table-column
          label="操作"
          align="center"
          width="220"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button
              size="small"
              type="text"
              icon="el-icon-edit"
              v-hasPermi="['govern:bulletin:list']"
              @click="handleThread(scope.row.bulletinId)"
              >线索
            </el-button>

            <el-button
              size="small"
              type="text"
              icon="el-icon-edit"
              @click="handleEdit(scope.row.bulletinId)"
              v-hasPermi="['govern:bulletin:edit']"
              >编辑
            </el-button>

            <el-button
              v-show="isManage"
              size="small"
              type="text"
              icon="el-icon-edit"
              v-hasPermi="['govern:bulletin:edit']"
              @click="handleArchive(scope.row.bulletinId)"
              >归档
            </el-button>

            <el-button
              type="text"
              icon="el-icon-delete"
              size="small"
              style="color: #f56c6c"
              v-hasPermi="['govern:bulletin:remove']"
              @click="handleDelete(scope.row.bulletinId)"
              >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--     分页操作-->
      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />

      <!--  添加或修改弹框   -->
      <el-dialog
        ref="insertOrEditForm"
        :title="title"
        :visible.sync="dialogFormVisible"
        width="700px"
      >
        <el-form
          :model="insertOrEditForm"
          ref="insertOrEditForm"
          :rules="rules"
          label-width="100px"
        >
          <el-row>
            <el-col :span="20">
              <el-form-item label="姓名:" prop="userName">
                <el-input
                  v-model="insertOrEditForm.userName"
                  placeholder="请输入姓名"
                ></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="22">
              <el-form-item label="人员照片" prop="avatar">
                <el-upload
                  class="avatar-uploader"
                  list-type="picture-card"
                  action
                  :show-file-list="false"
                  accept="image/jpeg, image/jpg, image/png"
                  :auto-upload="false"
                  :before-upload="handleBeforeUpload"
                  :on-change="handleAvatar"
                >
                  <img
                    v-if="this.imageUrl"
                    :src="this.imageUrl"
                    class="avatar"
                  />
                  <i
                    class="el-icon-plus avatar-uploader-icon"
                    v-show="this.imageUrl == null"
                  ></i>
                </el-upload>
              </el-form-item>
            </el-col>

            <el-col :span="10">
              <el-form-item label="性别:" prop="gender">
                <el-select
                  v-model="insertOrEditForm.gender"
                  placeholder="请选择性别"
                >
                  <el-option
                    v-for="dict in dict.type.sys_user_sex"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="11">
              <el-form-item label="年龄:" prop="userAge">
                <el-input
                  type="tel"
                  v-model.number="insertOrEditForm.userAge"
                  placeholder="请输入年龄"
                  maxlength="3"
                  onkeyup="this.value = this.value.replace(/\D+/g,'')"
                ></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="20">
              <el-form-item label="证件类型:" prop="cardType">
                <el-select
                  v-model="insertOrEditForm.cardType"
                  placeholder="请选择证件类型"
                >
                  <el-option
                    v-for="item in dict.type.sys_certificate"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="20">
              <el-form-item label="犯罪级别:" prop="crimeLevel">
                <el-select
                  v-model="insertOrEditForm.crimeLevel"
                  placeholder="请选择犯罪级别"
                >
                  <el-option key="A" label="A级" value="A"> </el-option>
                  <el-option key="B" label="B级" value="B"> </el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="20">
              <el-form-item label="证件号码:" prop="cardNo">
                <el-input
                  v-model="insertOrEditForm.cardNo"
                  placeholder="请输入证件号码"
                ></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="22">
              <el-form-item label="通报原因:" prop="crimeReason">
                <editor
                  type="base64"
                  v-model="insertOrEditForm.crimeReason"
                  :min-height="192"
                />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 认</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>

    <!-- 详情页 -->
    <div v-show="detailView">
      <el-form ref="detailForm" :model="detailForm" label-width="140px">
        <el-row :gutter="20">
          <el-col :span="3">
            <el-avatar
              shape="square"
              :size="200"
              fit="fit"
              :src="detailForm.avatar"
            ></el-avatar>
          </el-col>
          <el-col :span="5">
            <el-form-item label="姓 名:">
              {{ detailForm.userName }}
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="性 别:">
              {{ sex[detailForm.gender] }}
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="年 龄:">
              {{ detailForm.userAge + "岁" }}
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="证件类型:">
              {{ getDictLabel(detailForm.cardType) }}
            </el-form-item>
          </el-col>

          <el-col :span="5" style="padding-top: 20px">
            <el-form-item label="证件号码:">
              {{ detailForm.cardNo }}
            </el-form-item>
          </el-col>

          <el-col :span="5" style="padding-top: 20px">
            <el-form-item label="犯罪级别:">
              {{ detailForm.crimeLevel + "级" }}
            </el-form-item>
          </el-col>

          <el-col :span="5" style="padding-top: 20px">
            <el-form-item label="发布时间:">
              {{ detailForm.createTime }}
            </el-form-item>
          </el-col>

          <el-col :span="20" style="padding-top: 20px">
            <el-form-item label="通报原因:">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="handleCrimeReason"
                v-hasPermi="['govern:bulletin:list']"
                >查看
              </el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <el-table
        v-loading="loading"
        :data="detailForm.clueList"
        row-key="clueId"
        style="padding-top: 80px"
      >
        <el-table-column
          label="举报人员"
          align="center"
          key="accountName"
          prop="accountName"
        >
          <template slot-scope="scope">
            {{ handleName(scope.row.accountName) }}
          </template>
        </el-table-column>
        <el-table-column
          label="举报时间"
          align="center"
          key="createTime"
          prop="createTime"
        ></el-table-column>
        <el-table-column
          label="举报地点"
          align="center"
          key="address"
          prop="address"
        ></el-table-column>
        <el-table-column label="备注" align="center" key="remark" prop="remark">
          <template slot-scope="scope">
            <el-popover
              placement="top-start"
              title="备注内容"
              width="200"
              trigger="hover"
              :content="scope.row.remark"
            >
              <el-button type="text" slot="reference">查看</el-button>
            </el-popover>
          </template>
        </el-table-column>
      </el-table>

      <!--  通报内容   -->
      <el-dialog title="通报原因" :visible.sync="openContent" width="700px">
        <!-- <editor
          :readOnly="true"
          v-model="detailForm.crimeReason"
          :min-height="192"
        /> -->
        <div v-html="detailForm.crimeReason"></div>
        <div slot="footer" class="dialog-footer">
          <el-button @click="cancelDetail">取 消</el-button>
        </div>
      </el-dialog>

      <div style="margin-left: 95%; margin-top: 50px">
        <el-button @click="backHome">返回</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getList,
  getBulletinDetail,
  updateBulletin,
  addBulletin,
  upload,
  delBulletin,
  updateArchive,
  getArchiveDetail,
} from "@/api/govern/security/bulletin";
import { getToken } from "@/utils/auth";

import { desensitizationName, desensitization } from "@/utils/common";

export default {
  name: "bulletin",
  dicts: ["sys_user_sex", "sys_certificate"],
  data() {
    //验证年龄
    var validateAge = (rule, value, callback) => {
      if (value > 150 || value < 0) {
        callback(new Error("年龄不能大于150岁,也不能小于0"));
      }
      callback();
    };
    //验证证件号码
    var validateCard = (rule, value, callback) => {
      const pattern =
        /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/;
      if (value == "I" || value == "T" || value == "S") {
        if (!pattern.test(value)) {
          callback(new Error("身份证格式不正确"));
        }
        callback();
      } else {
        callback();
      }
    };
    return {
      imageUrl: null,
      //主页面是否展示
      checkView: true,
      //搜索框是否展示
      showSearch: true,
      //查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        archive: 0,
      },
      //总条目数
      total: 0,
      // 日期范围
      dateRange: [],
      //主页面表单集合
      bulletinList: [],
      //加载状态
      loading: false,
      //添加表单是否展示
      dialogFormVisible: false,
      //添加或者修改的表单
      insertOrEditForm: { avatar: undefined },
      //标题
      title: "新建信息",
      //是否是管理员
      isManage: false,
      //详情页是否展开
      detailView: false,
      //详情表单
      detailForm: {},
      uploadUrl: process.env.VUE_APP_BASE_API + "/file/upload", // 上传的图片服务器地址
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      sex: {
        0: "男",
        1: "女",
        // "2": '未知',
      },
      //通告原因是否打开
      openContent: false,
      //校验规则
      rules: {
        userName: [
          { required: true, message: "姓名不可为空", trigger: "blur" },
          {
            pattern: /^[\u4E00-\u9FA5]{2,8}$/,
            message: "请输入汉字,长度在 2 到 8 个字符",
            trigger: "blur",
          },
        ],
        gender: [{ required: true, message: "请选择性别", trigger: "blur" }],
        userAge: [
          { required: true, message: "请输入年龄", trigger: "blur" },
          { validator: validateAge },
        ],
        cardType: [
          { required: true, message: "请选择证件类型", trigger: "blur" },
        ],
        crimeLevel: [
          { required: true, message: "犯罪级别不可为空", trigger: "blur" },
        ],
        //普通居民身份证
        cardNo: [
          /*{required: true, message: '请输入证件号码', trigger: 'blur'},
          {min: 4, max: 18, message: '长度在 4 到 18 个字符', trigger: 'blur'},
          {validator: validateCard},*/
          { required: true, message: "请输入证件号码", trigger: "blur" },
          {
            pattern:
              /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
            message: "请输入正确的证件号码",
            trigger: "blur",
          },
        ],
        crimeReason: [
          { required: true, message: "通报原因不可为空", trigger: "blur" },
        ],
      },
    };
  },

  created() {
    this.getList();
  },

  methods: {
    //条件查询
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },

    /** 表单重置*/
    reset() {
      this.insertOrEditForm = {
        userName: undefined,
        avatar: undefined,
        gender: undefined,
        userAge: undefined,
        cardType: undefined,
        crimeLevel: undefined,
        cardNo: undefined,
        crimeReason: undefined,
      };
      this.resetForm("insertOrEditForm");
    },

    //添加方法
    handleAdd() {
      this.imageUrl = null;
      this.title = "新建信息";
      this.dialogFormVisible = true;
      this.reset();
    },

    //提交表单
    submitForm() {
      if (this.insertOrEditForm.avatar === undefined) {
        this.$modal.msgError("请上传物品照片");
        return;
      }
      this.$refs["insertOrEditForm"].validate((valid) => {
        if (valid) {
          if (this.insertOrEditForm.bulletinId != undefined) {
            updateBulletin(this.insertOrEditForm).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.getList();
            });
          } else {
            addBulletin(this.insertOrEditForm).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.getList();
            });
          }
          this.insertOrEditForm = {};
          this.dialogFormVisible = false;
        }
      });
    },

    //编辑回显
    handleEdit(bulletinId) {
      getBulletinDetail(bulletinId).then((res) => {
        this.insertOrEditForm = res.data;
        this.imageUrl = res.data.avatar;
        this.dialogFormVisible = true;
        this.title = "修改信息";
      });
    },

    //删除
    handleDelete(bulletinId) {
      this.$modal
        .confirm("是否确认删除数据项？")
        .then(function () {
          return delBulletin(bulletinId);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },

    //归档
    handleArchive(bulletinId) {
      //是否是管理员
      if (this.isManage) {
        this.$modal.confirm("是否确认归档？").then(() => {
          updateArchive(bulletinId).then(() => {
            this.$emit("toArchived");
            this.$modal.msgSuccess("成功归档");
          });
        });
      } else {
        this.$modal.msgError("非管理员无法操作");
      }
    },

    //查看线索
    handleThread(bulletinId) {
      this.detailView = true;
      this.checkView = false;
      getArchiveDetail(bulletinId).then((res) => {
        this.detailForm = res.data;
      });
    },

    //返回主界面
    backHome() {
      this.detailForm = {};
      this.detailView = false;
      this.checkView = true;
    },

    // 上传前校检格式和大小
    handleBeforeUpload(file) {
      return false;
    },

    //图片上传
    handleAvatar(file) {
      if (file) {
        let formData = new FormData();
        formData.append("file", file.raw);
        upload(formData).then((res) => {
          this.imageUrl = res.data.url;
          this.insertOrEditForm.avatar = res.data.url;
          this.$modal.msgSuccess("上传成功");
        });
      }
    },

    //查看通告内容
    handleCrimeReason() {
      this.openContent = true;
    },

    //取消添加
    cancel() {
      this.dialogFormVisible = false;
      this.insertOrEditForm = {};
      this.$refs["insertOrEditForm"].resetFields();
    },

    //取消详情页
    cancelDetail() {
      this.openContent = false;
    },

    //姓名脱敏
    handleName(name) {
      return desensitizationName(name);
    },

    //脱敏身份证号
    handleCard(card) {
      return desensitization(card, 4, 14);
    },

    //重置搜索项
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },

    /** 查询列表 */
    getList() {
      this.loading = true;
      this.bulletinList = [];
      getList(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          if (response.total > 0) {
            this.bulletinList = response.rows[0].resultList;
            this.isManage = response.rows[0].isManage;
            this.total = response.total;
          }
          this.loading = false;
        }
      );
    },

    //获取字典标签
    getDictLabel(cardType) {
      return this.selectDictLabel(this.dict.type.sys_certificate, cardType);
    },
  },
};
</script>

<style scoped>
.avatar {
  width: 100%;
  height: 100%;
}
</style>

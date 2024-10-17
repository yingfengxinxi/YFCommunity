<template>
  <div class="app-container">
    <div v-show="openView === 0">
      <el-form
        :model="queryParams"
        ref="queryForm"
        :inline="true"
        v-show="showSearch"
        label-width="68px"
        size="small"
      >
        <el-form-item label="所属小区" prop="communityId" label-width="80px">
          <el-select
            v-model="queryParams.communityId"
            placeholder="请选择"
            style="width: 240px"
          >
            <el-option
              v-for="(item, index) in currentList"
              :key="index"
              :label="item.communityName"
              :value="item.communityId"
            ></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="工作类型" prop="workPost" label-width="80px">
          <el-select
            v-model="queryParams.workPost"
            placeholder="请选择工作类型"
            clearable
          >
            <el-option
              v-for="(item, index) in workPostOptions"
              :key="index"
              :label="item.dictLabel"
              :value="item.dictValue"
            />
          </el-select>
        </el-form-item>
        <el-form-item
          label="姓名"
          prop="staffName"
          size="small"
          label-width="80px"
        >
          <el-input
            v-emoji
            v-model="queryParams.staffName"
            placeholder="请输入姓名"
            clearable
            type="text"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item
          label="手机号码"
          prop="staffPhone"
          size="small"
          label-width="80px"
        >
          <el-input
            v-emoji
            v-model="queryParams.staffPhone"
            placeholder="请输入手机号码"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item
          label="在职状态"
          prop="status"
          size="small"
          label-width="80px"
        >
          <el-select
            v-model="queryParams.jobStatus"
            placeholder="请选择状态"
            clearable
          >
            <el-option label="在职" value="0" />
            <el-option label="离职" value="1" />
          </el-select>
        </el-form-item>
        <el-form-item label-width="80px">
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="getList"
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
            >新增
          </el-button>
        </el-col>

        <el-col :span="1.5">
          <el-button
            type="info"
            plain
            icon="el-icon-upload2"
            size="mini"
            @click="handleImport"
            >导入</el-button
          >
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="success"
            plain
            icon="el-icon-download"
            size="mini"
            @click="handleExport"
            >导出
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleUpdateStatus"
            >离职
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
            >删除
          </el-button>
        </el-col>
        <right-toolbar
          :showSearch.sync="showSearch"
          @queryTable="getList"
        ></right-toolbar>
      </el-row>
      <el-table
        ref="multipleTable"
        v-loading="loading"
        :data="staffList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="姓名" align="center" prop="staffName" />
        <el-table-column label="所属小区" align="center" prop="age">
          <template slot-scope="scope">
            {{ getCommunityName(scope.row.communityId) }}
          </template>
        </el-table-column>
        <el-table-column label="手机号码" align="center" prop="staffPhone" />
        <el-table-column label="工作类型" align="center" prop="workPost">
          <template slot-scope="scope">
            {{ workPostFormat(scope.row.workPost) }}
          </template>
        </el-table-column>
        <el-table-column label="绑定楼号" align="center" prop="buildingName" />
        <el-table-column label="在职状态" align="center" prop="jobStatus" />
        <el-table-column label="入职时间" align="center" prop="entryTime" />
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
          width="200"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleEmp(scope.row)"
              >查看
            </el-button>
            <!-- <br> -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              >修改
            </el-button>
            <!-- <br> -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleAddRole(scope.row)"
              >授权角色
            </el-button>
            <!-- <br> -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="getShowpwd(scope.row)"
              >重置密码
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
    </div>

    <!-- 查看详情页面 -->
    <div v-show="openView === 2">
      <h4 class="form-header">基本信息</h4>
      <div slot="footer" class="dialog-footer">
        <el-row>
          <el-col :offset="22" :span="2">
            <el-button @click="cancel">关 闭</el-button>
          </el-col>
        </el-row>
      </div>
      <el-form ref="form1" :model="form1" :rules="rules" label-width="120px">
        <el-row>
          <el-col :span="5">
            <img :src="form1.avatar" class="showAvatar" />
          </el-col>
          <el-col :span="5">
            <img :src="form1.qrCodeBase64" class="showAvatar" />
          </el-col>
          <el-col
            :span="5"
            style="line-height: 170px; color: #0b57d0; font-size: 14px"
          >
            <a @click="downLoadImg(form1.qrCodeBase64, form1.staffName)"
              >下载</a
            >
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <el-form-item label="姓名:"> {{ form1.staffName }}</el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="手机号:">{{ form1.staffPhone }}</el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="性别:">
              <div v-if="form1.gender == 0">男</div>
              <div v-else-if="form1.gender == 1">女</div>
              <div v-else>未知</div>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <el-form-item label="身份证号码:">{{ form1.cardNo }}</el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="学历水平:">
              <div style="width: 280px">
                {{ educationFormat(form1.education) }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="个人健康信息:">
              <div v-if="form1.healthy == 0">健康</div>
              <div v-else-if="form1.healthy == 1">不健康</div>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="6">
            <el-form-item label="工作类型:">
              <div>{{ workPostFormat(form1.workPost) }}</div>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="工作资历:">{{ form1.workYear }}</el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="入职时间:">{{ form1.entryTime }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="负责楼栋:">{{
              form1.buildingName
            }}</el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="职业证书:">{{ form1.certUrl }}</el-form-item>
          </el-col>
          <el-col :span="20">
            <el-form-item label="家庭住址:">{{ form1.address }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="20">
            <el-form-item label="个人介绍:">{{
              form1.staffIntro
            }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <h4 class="form-header">员工评价列表</h4>
      <el-table
        ref="multipleTable"
        v-loading="loading1"
        :data="staffEvaluationList"
      >
        <el-table-column label="姓名" align="center" prop="ownerName" />
        <el-table-column label="手机号码" align="center" prop="ownerPhone" />
        <el-table-column
          label="评价等级"
          align="center"
          prop="satisfactionLevel"
        >
          <template slot-scope="scope">
            <el-rate disabled v-model="scope.row.satisfactionLevel"></el-rate>
          </template>
        </el-table-column>
        <el-table-column
          label="评价内容"
          align="center"
          prop="comments"
          :show-overflow-tooltip="true"
        />
        <el-table-column label="评价时间" align="center" prop="createTime" />
      </el-table>

      <pagination
        v-show="total1 > 0"
        :total="total1"
        :page.sync="queryParams1.pageNum"
        :limit.sync="queryParams1.pageSize"
        @pagination="getStaffEvaluation"
      />
    </div>

    <!--    新增或修改页面-->
    <div v-show="openView === 1">
      <h4 class="form-header">基本信息</h4>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="7">
            <el-form-item prop="avatar">
              <el-upload
                class="staff-avatar"
                action="#"
                :show-file-list="false"
                accept="image/*"
                :before-upload="beforeUpload"
                :on-change="uploadLogo"
              >
                <el-image
                  v-if="form.avatar"
                  :src="form.avatar"
                  class="avatar"
                />
                <i v-else class="el-icon-plus avatar-uploader-icon">头像</i>
              </el-upload>
            </el-form-item>
          </el-col>
          <el-col :span="20">
            <el-row>
              <el-col :span="6">
                <el-form-item label="所属小区" prop="communityId">
                  <el-select
                    v-model="form.communityId"
                    placeholder="请选择"
                    style="width: 195px"
                    @change="getBuildingOptions(form.communityId)"
                  >
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
            <el-row>
              <el-col :span="8" v-if="form.parentId !== 0">
                <el-form-item label="姓名" prop="staffName">
                  <el-input
                    onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"
                    v-emoji
                    v-model.trim="form.staffName"
                    placeholder="请输入姓名"
                    :maxlength="20"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8" :offset="1">
                <el-form-item label="手机号" prop="staffPhone">
                  <el-input
                    :disabled="isDisabled"
                    v-emoji
                    v-model.trim="form.staffPhone"
                    placeholder="请输入手机号"
                    :maxlength="11"
                  />
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="8">
                <el-form-item label="证件号码" prop="cardNo">
                  <el-input
                    v-emoji
                    v-model.trim="form.cardNo"
                    placeholder="请输入证件号码"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8" :offset="1">
                <el-form-item label="健康状态" prop="healthy">
                  <el-select
                    v-emoji
                    v-model="form.healthy"
                    placeholder="请选择健康状态"
                    style="width: 100%"
                  >
                    <el-option label="健康" value="0" />
                    <el-option label="不健康" value="1" />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="8">
                <el-form-item label="职业证书" prop="certUrl">
                  <el-input
                    v-emoji
                    v-model.trim="form.certUrl"
                    placeholder="请输入证书名称"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8" :offset="1">
                <el-form-item label="入职时间" prop="entryTime">
                  <el-date-picker
                    clearable
                    size="small"
                    v-model="form.entryTime"
                    type="date"
                    value-format="yyyy-MM-dd"
                    placeholder="选择入职时间"
                    :picker-options="expireTimeOPtion"
                    style="width: 100%"
                  >
                  </el-date-picker>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item
                  label="工作类型"
                  prop="workPost"
                  label-width="100px"
                >
                  <el-select
                    v-model="form.workPost"
                    multiple
                    placeholder="请选择"
                    style="width: 250px"
                  >
                    <!-- value是双向绑定的值 -->
                    <el-option
                      v-for="item in workPostOptions"
                      :key="item.dictValue"
                      :label="item.dictLabel"
                      :value="item.dictValue"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="8" :offset="1">
                <el-form-item label="学历水平" prop="education">
                  <el-select
                    v-model="form.education"
                    placeholder="请选择学历水平"
                    style="width: 250px"
                  >
                    <el-option
                      v-for="(item, index) in educationOptions"
                      :key="index"
                      :label="item.dictLabel"
                      :value="item.dictValue"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="工作资历" prop="workYear">
                  <el-input-number
                    :min="0"
                    :precision="0"
                    :step="1"
                    v-emoji
                    v-model.trim="form.workYear"
                    placeholder="请输入工作资历"
                    style="width: 250px"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8" :offset="1">
                <el-form-item label="绑定楼栋" prop="buildingId">
                  <el-select
                    v-model="form.buildingId"
                    multiple
                    placeholder="请选择楼栋"
                    style="width: 250px"
                  >
                    <el-option
                      v-for="(item, index) in buildingOptions"
                      :key="index"
                      :label="item.buildingName"
                      :value="item.buildingId + ''"
                      :disabled="item.flag == '1'"
                    />
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="20">
                <el-form-item label="家庭住址" prop="address">
                  <el-input
                    v-emoji
                    v-model.trim="form.address"
                    type="textarea"
                    placeholder="请输入家庭住址"
                    :rows="2"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="20">
                <el-form-item label="个人介绍" prop="staffIntro">
                  <el-input
                    v-emoji
                    v-model.trim="form.staffIntro"
                    type="textarea"
                    placeholder="请输入个人介绍"
                    :rows="10"
                    maxlength="5000"
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
        </el-row>
      </el-form>
      <h4 class="form-header"></h4>
      <div slot="footer" class="dialog-footer">
        <el-row>
          <el-col :offset="20" :span="2">
            <el-button @click="cancel"> 取 消 </el-button>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" @click="submitForm">确 定</el-button>
          </el-col>
        </el-row>
      </div>
    </div>
    <!-- 物业人员授权角色配置对话框 -->
    <el-dialog
      :title="authMenuTitle"
      :visible.sync="authMenuOpen"
      width="50%"
      center
    >
      <el-transfer
        filterable
        :filter-method="filterMethod"
        filter-placeholder="请输入角色名称"
        :titles="['可选角色', '授权角色']"
        v-model="yxRoleList"
        :data="roleList"
      >
      </el-transfer>

      <span slot="footer" class="dialog-footer">
        <el-button @click="reBack()" style="margin: 0px 30px 0px 30px"
          >返回</el-button
        >
        <el-button type="success" @click="saveChoose()">保存</el-button>
      </span>
    </el-dialog>

    <!-- 房屋导入对话框 -->
    <el-dialog
      :title="uploadImprot.title"
      :visible.sync="uploadImprot.open"
      width="400px"
      append-to-body
    >
      <el-upload
        ref="uploadImprot"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="uploadImprot.headers"
        :action="uploadImprot.url"
        :disabled="uploadImprot.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <!-- <div class="el-upload__tip" slot="tip">
                <el-checkbox v-model="upload.updateSupport" />
                是否更新已经存在的用户数据
              </div> -->
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link
            type="primary"
            :underline="false"
            style="font-size: 12px; vertical-align: baseline"
            @click="importTemplate"
            >下载模板</el-link
          >
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="uploadImprot.open = false">取 消</el-button>
      </div>
    </el-dialog>

    <el-dialog title="密码重置" :visible.sync="dialogVisible" width="25%">
      <el-form
        ref="ruleForm"
        :rules="pwdRules"
        :model="updatePwdParams"
        class="demo-ruleForm"
      >
        <el-form-item label="新密码" label-width="100px" prop="password">
          <el-input
            type="password"
            v-model="updatePwdParams.password"
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
import { getCurrentList } from "@/api/estate/community";
import {
  listStaff,
  getStaff,
  updateStaff,
  addStaff,
  delStaff,
  updateStaffStatus,
  uploadImg,
  resetPass,
  listEstateRole,
  insertEstateRole,
  getStaffEvaluationList,
} from "@/api/estate/manage/staff/staff";
import { listRole } from "@/api/estate/role/role";

import { desensitization, equals } from "@/utils/common";
import { getCommunityIdBuildingName } from "@/api/estate/center/room";
import { getToken } from "@/utils/auth";
export default {
  name: "Staff",
  dicts: ["sys_user_sex", "education", "work_post"],
  data() {
    return {
      dialogVisible: false,
      yxRoleList: [],
      filterMethod(query, item) {
        return item.pinyin.indexOf(query) > -1;
      },
      expireTimeOPtion: {
        disabledDate(time) {
          return time.getTime() > Date.now() - 8.64e6; //如果没有后面的-8.64e6就是不可以选择今天的
        },
      },
      // communityName: "",
      // communityId: "",
      sommempList: true,
      options: [
        { label: "1", value: "0" },
        { label: "2", value: "1" },
      ],
      // 遮罩层
      loading: false,
      loading1: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      showSearch1: true,
      // 总条数
      total: 0,
      total1: 0,
      authMenuTitle: "",
      // 员工信息表格数据
      staffList: [],
      staffEvaluationList: [],
      classifyList: [],
      //小区数据集合
      currentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      //open: false,
      openView: 0,
      // 性别：0=男，1=女字典
      genderOptions: [],
      //学历水平
      educationOptions: [],
      //工作类型
      workPostOptions: [],
      buildingOptions: [],
      //判断是否显示
      isShow: false,
      showRelation: false,
      isDisabled: false,
      // 树筛选文本
      filterText: "",
      //树结构
      comOptions: [],
      communityId: null,
      currentListProps: {
        children: "communityId",
        label: "communityName",
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        staffName: null,
        workPost: null,
        staffPhone: null,
        ids: null,
      },
      updatePwdParams: {
        password: undefined,
        staffId: undefined,
        userId: undefined,
        staffPhone: undefined,
      },
      // 查询参数
      queryParams1: {
        pageNum: 1,
        pageSize: 10,
        staffId: undefined,
      },
      authMenuOpen: false,
      // 表单参数
      form: {
        healthy: "0",
        education: "0",
        password: "1234abc",
      },
      // 车位信息导入参数
      uploadImprot: {
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
        url: process.env.VUE_APP_BASE_API + "/estate/staff/importData",
      },
      staffId: "",
      roleList: [],
      form1: {},
      data: [],
      pwdRules: {
        password: [
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
      // 表单校验
      rules: {
        communityId: [
          { required: true, message: "请选择所属小区", trigger: "blur" },
        ],
        staffName: [{ required: true, message: "请输入姓名", trigger: "blur" }],
        staffPhone: [
          {
            required: true,
            message: "请输入手机号",
            trigger: "blur",
          },
          {
            pattern: /^1[3|4|5|6|7|8|9][0-9]\d{8}$/,
            message: "请输入正确的手机号码",
            trigger: "blur",
          },
        ],
        /*
        gender: [
          {required: true, message: "请选择性别", trigger: "blur"}
        ],
*/

        /*age: [
          {required: true, message: "年龄不能为空", trigger: "blur"}
        ],*/
        cardNo: [
          { required: true, message: "请输入证件号码", trigger: "blur" },
          {
            pattern:
              /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
            message: "请输入正确的证件号码",
            trigger: "blur",
          },
        ],
        healthy: [
          {
            required: true,
            message: "个人健康状态不能为空",
            trigger: "change",
          },
        ],
        education: [
          { required: true, message: "请选择学历水平", trigger: "change" },
        ],
        /*employeeAccount: [
          {required: true, message: "员工账号不能为空", trigger: "blur"}
        ],*/
        workPost: [
          { required: true, message: "请选择工作类型", trigger: "change" },
        ],
        workYear: [
          { required: false, message: "工作资历不能为空", trigger: "blur" },
        ],
        entryTime: [
          { required: true, message: "请选择入职时间", trigger: "blur" },
        ],
        address: [
          { required: false, message: "家庭住址不能为空", trigger: "blur" },
        ],
        staffIntro: [
          { required: false, message: "个人介绍不能为空", trigger: "blur" },
        ],
        /*
        avatar: [
          {required: true, message: "请上传头像", trigger: "blur"}
        ],
*/
        password: [
          { required: true, message: "密码不能为空", trigger: "blur" },
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
    };
  },
  created() {
    this.getDicts("sys_user_sex").then((response) => {
      this.genderOptions = response.data;
    });
    this.getDicts("education").then((response) => {
      this.educationOptions = response.data;
    });
    this.getDicts("work_post").then((response) => {
      this.workPostOptions = response.data;
    });
    this.getTree();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
  methods: {
    /** 导入按钮操作 */
    handleImport() {
      this.uploadImprot.title = "员工信息导入";
      this.uploadImprot.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download(
        "estate/staff/importTemplate",
        {},
        `员工信息导入模板_${new Date().getTime()}.xlsx`
      );
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.uploadImprot.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.uploadImprot.open = false;
      this.uploadImprot.isUploading = false;
      this.$refs.uploadImprot.clearFiles();
      this.$alert(
        "<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" +
          response.msg +
          "</div>",
        "导入结果",
        { dangerouslyUseHTMLString: true }
      );

      this.getList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.uploadImprot.submit();
    },

    getCommunityName(communityId) {
      if (communityId) {
        var obj = this.currentList.filter(
          (item) => item.communityId == communityId
        )[0];
        if (obj != null) {
          return obj.communityName;
        }
      }
    },
    /** 查询员工信息列表 */
    getList() {
      this.openView = 0;
      this.loading = false;
      listStaff(this.queryParams).then((response) => {
        this.staffList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    // 学历水平字典翻译
    educationFormat(education) {
      if (education) {
        var obj = this.educationOptions.filter(
          (item) => item.dictValue == education
        )[0];
        if (obj) {
          return obj.dictLabel;
        }
      }
    },
    // 工作类型字典翻译
    workPostFormat(workPost) {
      console.log(workPost);
      var str = "";
      if (workPost) {
        console.log(workPost);
        var workPosts = workPost.split(",");
        workPosts.filter((wp) => {
          var obj = this.workPostOptions.filter(
            (item) => item.dictValue == wp
          )[0];
          if (obj) {
            str = str + "," + obj.dictLabel;
          }
        });
        if (str) {
          str = str.substring(1, str.length);
        }
        return str;
      }
    },
    //授权角色
    saveChoose() {
      console.log("this.yxRoleList>>", this.yxRoleList);
      if (this.yxRoleList.length == 0) {
        this.$modal.msgError("请选择授权角色");
        return;
      }
      var roleroleList = [];
      console.log("this.staffId>>", this.staffId);
      this.yxRoleList.forEach((item) => {
        roleroleList.push({
          staffId: parseFloat(this.staffId),
          estateRoleId: item,
        });
      });
      insertEstateRole(roleroleList).then((res) => {
        this.$modal.msgSuccess("授权成功");
        this.authMenuOpen = false;
        this.getList();
      });
    },
    reBack() {
      this.authMenuOpen = false;
      this.getList();
    },
    handleAddRole(row) {
      this.authMenuTitle = row.staffName + "授权角色";
      this.authMenuOpen = true;
      this.yxRoleList = [];
      this.staffId = parseFloat(row.staffId);
      //查询已授权角色
      listEstateRole(row.staffId).then((res) => {
        var yxRoleList = res.data;
        if (yxRoleList) {
          yxRoleList.forEach((item) => {
            this.yxRoleList.push(item.estateRoleId);
          });
        }
      });
      //查询全部角色
      listRole().then((res) => {
        this.roleList = res.data;
        if (this.roleList) {
          this.roleList.forEach((item) => {
            item.label = item.estateRoleName;
            item.key = item.estateRoleId;
            item.pinyin = item.estateRoleName;
          });
        }
      });
    },
    getBuildingName(buildingId) {
      if (buildingId) {
        var obj = this.buildingOptions.filter(
          (item) => item.buildingId == buildingId
        )[0];
        if (obj) {
          return obj.buildingName;
        }
      }
    },
    //小区下所有楼栋
    getBuildingOptions(communityId) {
      getCommunityIdBuildingName(communityId).then((res) => {
        this.buildingOptions = res.data;
      });
    },

    // 获取树数据
    getTree() {
      getCurrentList().then((res) => {
        this.currentList = res.data;
        this.getList();
      });
    },
    // 取消按钮
    cancel() {
      this.sommempList = true;
      this.openView = 0;
      this.getList();
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        staffId: null,
        communityId: null,
        staffName: null,
        staffPhone: null,
        gender: null,
        age: null,
        avatar: null,
        cardNo: null,
        idNumber: null,
        healthy: "0",
        education: "0",
        certificateName: null,
        employeeAccount: null,
        jobType: null,
        workPost: null,
        workYear: 0,
        entryTime: null,
        address: null,
        staffIntro: null,
        employeePhoto: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null,
        password: "1234abc",
      };
    },
    resetForm() {
      this.reset();
      this.dialogVisible = false;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.queryParams.workPost = null;
      this.queryParams.jobStatus = null;
      this.queryParams.communityId = null;
      this.resetForm("queryForm");
      this.handleQuery();
      /*this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        //communityId:undefined,
        communityName:undefined
      }*/
      /* this.resetForm("queryForm");
       this.handleQuery();*/
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.staffId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.openView = 1;
      this.isDisabled = false;
      this.title = "添加员工信息";
    },

    /** 修改按钮操作 */
    handleUpdate(row) {
      //this.reset();
      this.openView = 1;
      const params = {};
      this.isDisabled = true;
      params.staffId = row.staffId;
      getStaff(params).then((response) => {
        this.form = response.rows[0];
        if (this.form.workPost) {
          this.form.workPost = this.form.workPost.split(",");
        }
        if (this.form.buildingId) {
          this.form.buildingId = this.form.buildingId.split(",");
        }

        if (this.form.communityId) {
          this.getBuildingOptions(this.form.communityId);
        }
        this.openView = 1;
        this.title = "修改员工信息";
      });
    },

    //查看员工详情
    handleEmp(row) {
      this.openView = 2;
      //this.form = row;
      const params = {};
      this.staffEvaluationList = [];
      params.staffId = row.staffId;
      getStaff(params).then((response) => {
        this.form1 = response.rows[0];
        //this.form1.workPost = this.form.workPost + "";
        if (this.form1.communityId) {
          this.getBuildingOptions(this.form1.communityId);
        }
        this.queryParams1.staffId = row.staffId;
        this.getStaffEvaluation();
        this.openView = 2;
        this.showAdd = false;
        this.details = true;
        this.title = "详情员工";
      });
    },
    /** 查询员工评价信息列表 */
    getStaffEvaluation() {
      this.loading1 = false;
      getStaffEvaluationList(this.queryParams1).then((response) => {
        this.staffEvaluationList = response.rows;
        this.total1 = response.total;
        this.loading1 = false;
      });
    },
    getShowpwd(row) {
      this.dialogVisible = true;
      this.updatePwdParams = {
        password: undefined,
        staffId: row.staffId,
        userId: row.userId,
        staffPhone: row.staffPhone,
      };
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
              return resetPass(this.updatePwdParams).then((e) => {
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
    /** 重置密码 */
    // ResetPassword(row) {
    //   this.$prompt('请输入密码', '提示(6-12位包含数字和字母)', {
    //     confirmButtonText: '确定',
    //     cancelButtonText: '取消',
    //     inputPattern: /(?=.*\d)(?=.*[a-zA-Z]).{6,12}/,
    //     inputErrorMessage: '密码格式不正确'
    //   }).then(({ value }) => {

    //     let params = {
    //       password: value,
    //       staffId: row.staffId,
    //       userId: row.userId,
    //       staffPhone: row.staffPhone
    //     }
    //     resetPass(params).then(response => {
    //       this.$message({
    //         type: 'success',
    //         message: '密码修改成功！！'
    //       });
    //       this.getList()
    //     });
    //   });
    // },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.form.workPost = this.form.workPost + "";
          if (this.form.buildingId) {
            this.form.buildingId = this.form.buildingId + "";
          }

          this.sommempList = false;
          const params = {};
          delete this.form.params;
          params.staff = this.form;
          if (this.form.staffId != null) {
            updateStaff(params.staff).then((res) => {
              if (res.code == 200) {
                this.$modal.msgSuccess("修改成功");
                this.sommempList = true;
                this.openView = 0;
                this.getList();
              } else if (res.code == 510) {
                this.$modal.msgError(res.msg);
                this.form.workPost = this.form.workPost.split(",");
                if (this.form.buildingId) {
                  
                  this.form.buildingId = this.form.buildingId.split(",");
                }
              }
            });
          } else {
            addStaff(params.staff).then((response) => {
              if (response.code == 200) {
                this.$modal.msgSuccess("新增成功");
                this.sommempList = true;
                this.openView = 0;
                this.getList();
              } else if (response.code == 510) {
                this.$modal.msgError(response.msg);
                this.form.workPost = this.form.workPost.split(",");
                if (this.form.buildingId) {
                  this.form.buildingId = this.form.buildingId.split(",");
                }
              }
             
            });
          }
        }
      });
    },
    //图片下载
    downLoadImg(url, name) {
      const image = new Image();
      image.setAttribute("crossOrigin", "anonymous");
      image.src = url;
      image.onload = () => {
        const canvas = document.createElement("canvas");
        canvas.width = image.width;
        canvas.height = image.height;
        const context = canvas.getContext("2d");
        context.drawImage(image, 0, 0, image.width, image.height);
        canvas.toBlob((blob) => {
          const url = URL.createObjectURL(blob);
          const a = document.createElement("a");
          a.download = name || "photo";
          a.href = url;
          a.click();
          a.remove();
          URL.revokeObjectURL(url);
        });
      };
    },
    /** 删除按钮操作 */
    /*handleDelete(row) {
      const staffIds = row.staffId || this.ids;
      /!* this.$confirm('是否确认删除名称为"' + staffIds + '"的数据项?', "警告", {*!/
      this.$confirm('是否确认删除所选中的的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delStaff(staffIds);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功")
      })
    },*/
    /** 删除按钮 */
    handleDelete(row) {
      const staffIds = row.staffId || this.ids;
      this.$modal
        .confirm("是否确认删除所选数据？")
        .then(function () {
          return delStaff(staffIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 离职按钮操作 */
    handleUpdateStatus(row) {
      const staffIds = row.staffId || this.ids;
      this.$modal
        .confirm("是否确认离职所选数据")
        .then(function () {
          return updateStaffStatus(staffIds);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("离职成功");
        })
        .catch(() => {});
    },

    //在职状态
    workPostChange(row) {
      if (row.workPost === "0") {
        return "安防人员";
      }
      if (row.workPost === "1") {
        return "维修人员";
      }
      if (row.workPost === "2") {
        return "装修人员";
      }
      if (row.workPost === "3") {
        return "场馆人员";
      }
      if (row.workPost === "4") {
        return "保洁人员";
      }
      if (row.workPost === "5") {
        return "志愿者";
      }
    },

    /** 导出按钮操作 */
    handleExport() {
      //清空多选按钮
      this.$refs.multipleTable.clearSelection();
      this.queryParams.ids = this.ids;
      this.download(
        "estate/staff/export",
        {
          ...this.queryParams,
        },
        `员工列表.xlsx`
      );
    },

    //覆盖默认的上传行为
    requestUpload() {},
    // 上传预处理
    beforeUpload(file) {
      return false;
    },

    // 上传图片
    uploadLogo(file, fileList) {
      let formData = new FormData();
      formData.append("file", file.raw);
      uploadImg(formData).then((response) => {
        this.form.avatar = response.imgUrl;
        this.$modal.msgSuccess("上传成功");
      });
    },

    /** 手机号脱敏 */
    phoneFormatter(phone) {
      if (!equals(phone, undefined)) {
        return desensitization(phone, 3, 7);
      }
    },

    /** 身份证号脱敏 */
    cardFormatter(card) {
      if (!equals(card, undefined)) {
        return desensitization(card, 6, 14);
      }
    },
  },
};
</script>

<style lang="scss">
.staff-avatar .el-upload {
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 85%;
}

.staff-avatar .el-upload:hover {
  border-color: #409eff;
}

.staff-avatar .image-slot {
  justify-content: center;
}

.avatar-uploader-icon {
  font-size: 14px;
  color: #8c939d;
  /*width: 80%;*/
  width: 160px;
  height: 160px;
  line-height: 160px;
  text-align: center;
  border: silver 1px solid;
}

.avatar {
  width: 100%;
  height: 200px;
  display: block;
}

.showAvatar {
  width: 160;
  height: 160px;
  margin-left: 60px;
  display: block;
  margin-bottom: 10px;
  border: 1px solid #eceff7;
}

.el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #b3f1b6;
}
</style>

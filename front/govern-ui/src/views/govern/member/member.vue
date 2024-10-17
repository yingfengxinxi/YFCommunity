<template>
  <div class="app-container">
    <div v-show="!open">
      <el-form
        v-show="showSearch"
        ref="queryForm"
        :model="queryParams"
        :inline="true"
        label-width="90px"
      >
        <el-form-item label="党组织名称" prop="partyId">
          <el-select
            v-model="queryParams.partyId"
            placeholder="党组织名称"
            clearable
            size="small"
          >
            <el-option
              v-for="item in organizationLists"
              :key="item.partyId"
              :label="item.partyName"
              :value="item.partyId"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="入党时间" prop="joinTime">
          <el-date-picker
            v-model="dateRange"
            size="small"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
          />
        </el-form-item>
        <el-form-item label="姓名" prop="memberName">
          <el-input
            v-model.trim="queryParams.memberName"
            v-emoji
            placeholder="请输入党员姓名"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
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
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            v-hasPermi="['govern:member:add']"
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
            v-hasPermi="['system:member:remove']"
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            >删除
          </el-button>
        </el-col>
        <right-toolbar :show-search.sync="showSearch" @queryTable="getList" />
      </el-row>
      <el-table
        v-show="show"
        v-loading="loading"
        :data="memberList"
        @selection-change="handleSelectionChange"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column
          label="姓名"
          align="center"
          prop="memberName"
          show-overflow-tooltip
        />
        <el-table-column
          label="手机号"
          align="center"
          prop="memberPhone"
          show-overflow-tooltip
        />
        <el-table-column
          label="入党日期"
          align="center"
          prop="joinTime"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.joinTime, "{y}-{m}-{d}") }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="所属党组织"
          align="center"
          prop="partyName"
          show-overflow-tooltip
        />
        <el-table-column
          label="党内职务"
          align="center"
          prop="memberDuty"
          show-overflow-tooltip
        />
        <el-table-column
          label="党员资料提交日期"
          align="center"
          prop="createTime"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <span>{{ scope.row.createTime }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="审核通过日期"
          align="center"
          prop="auditTime"
          show-overflow-tooltip
        >
          <template slot-scope="scope">
            <span>{{
              scope.row.auditTime == null ? "-" : scope.row.auditTime
            }}</span>
          </template>
        </el-table-column>
        <el-table-column
          label="住址"
          align="center"
          prop="address"
          width="150px"
          show-overflow-tooltip
        />
        <el-table-column
          label="操作"
          align="center"
          width="150px"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button
              v-hasPermi="['govern:member:query']"
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleView(scope.row, scope.index)"
              >查看
            </el-button>
            <el-button
              v-hasPermi="['govern:member:edit']"
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              >修改
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              @click="handleRemove(scope.row, scope.index)"
              >删除
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
    <!-- 添加、修改页面-->
    <div v-show="showUpdate">
      <el-form ref="forms" :model="form" :rules="rules" label-width="150px">
        <h4 class="form-header">{{ title }}</h4>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="所属小区:" prop="communityId">
              <el-select
                v-model="form.communityId"
                placeholder="请选择所属小区"
                style="width: 100%"
              >
                <el-option
                  v-for="item in communityList"
                  :key="item.communityId"
                  :label="item.communityName"
                  :value="item.communityId"
                  @click.native="handleSelectionChangeForBuilding(item)"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col v-if="ownerOption" :span="6" :offset="1">
            <el-form-item label="住户类型:" prop="accountType">
              <el-select
                v-model="form.accountType"
                placeholder="请选择住户类型"
                style="width: 100%"
                @change="getOwnerList"
              >
                <el-option label="业主" value="0" />
                <el-option label="租客" value="1" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col v-if="ownerInput" :span="6" :offset="1">
            <el-form-item label="住户类型:">
              <span>{{ form.accountType === "0" ? "业主" : "租客" }}</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col v-if="ownerOption" :span="6" :offset="1">
            <el-form-item label="姓名:" prop="accountId">
              <el-select v-model="form.accountId" style="width: 100%">
                <el-option
                  v-for="item in ownerList"
                  :key="item.accountId"
                  :label="item.accountName"
                  :value="item.accountId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col v-if="ownerInput" :span="6" :offset="1">
            <el-form-item label="姓名:">
              <span>
                {{ form.memberName }}
              </span>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="所属党组织:" prop="partyId">
              <el-select v-model="form.partyId" style="width: 100%">
                <el-option
                  v-for="item in organizationList"
                  :key="item.partyId"
                  :label="item.partyName"
                  :value="item.partyId"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="入党时间:" prop="joinTime">
              <el-date-picker
                v-model="form.joinTime"
                style="width: 100%"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="选择入党时间"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="所属楼宇" prop="buildingId">
              <el-select
                v-model="form.buildingId"
                placeholder="请选择所属楼宇"
                style="width: 100%"
              >
                <el-option
                  v-for="item in buildingList"
                  :key="item.buildingId"
                  :label="item.buildingName"
                  :value="item.buildingId"
                  @click.native="handleSelectionChangeForUnit(item)"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="所属单元" prop="unitId">
              <el-select
                v-model="form.unitId"
                placeholder="请选择"
                style="width: 100%"
              >
                <el-option
                  v-for="item in unitList"
                  :key="item.unitId"
                  :label="item.unitName"
                  :value="item.unitId"
                  @click.native="handleSelectionChangeForRoom(item)"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="所属房间" prop="roomId">
              <el-select
                v-model="form.roomId"
                placeholder="请选择所属房间"
                style="width: 100%"
              >
                <el-option
                  v-for="item in roomList"
                  :key="item.roomId"
                  :label="item.roomName"
                  :value="item.roomId"
                  @click.native="handleSelectionChangeForRooms(item)"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="党内职务:" prop="memberDuty">
              <el-select
                v-model="form.memberDuty"
                placeholder="请选择党内职务"
                style="width: 100%"
              >
                <el-option label="党员" value="党员" />
                <el-option label="其他" value="其他" />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <!--        <el-row>
                  <el-col :span="10" :offset="1">
                    <el-form-item label="详细住址:" prop="address">
                      <span>{{ address }}</span>
                    </el-form-item>
                  </el-col>
                </el-row>-->
      </el-form>
      <h4 class="form-header" />
      <div slot="footer" class="dialog-footer">
        <el-row>
          <el-col :offset="20" :span="2">
            <el-button @click="cancel1">取 消</el-button>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" @click="submitForm(form)"
              >确 定</el-button
            >
          </el-col>
        </el-row>
      </div>
    </div>
    <!-- 查看党员列表-->
    <el-dialog
      title="党员列表详情"
      :visible.sync="showDetail"
      :before-close="closeDetail"
    >
      <el-form ref="form" :model="form" label-width="130px">
        <el-row>
          <el-col :span="6" :offset="3">
            <el-form-item label="姓名:">{{ form.memberName }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="3">
            <el-form-item label="联系电话:">{{
              form.memberPhone
            }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8" :offset="3">
            <el-form-item label="地址:">{{ form.address }}</el-form-item>
          </el-col>
          <el-col :span="8" :offset="1">
            <el-form-item label="入党时间:">{{ form.joinTime }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="3">
            <el-form-item label="党内职务：">{{
              form.memberDuty
            }}</el-form-item>
          </el-col>
          <el-col :span="8" :offset="3">
            <el-form-item label="申请时间:">{{ form.createTime }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="closeDetail()">关 闭</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import {
  memberList,
  partyAll,
  getOwnerAll,
  addMember,
  getMember,
  upMember,
  MemberRemove,
} from "@/api/govern/member/member";
import {
  getBuildingList,
  getCurrentList,
  getRoomList,
  getUnitList,
} from "@/api/govern/community";
import { uploadImg } from "@/api/govern/party/party";
import { equals, isEmpty } from "@/utils/common";
import { partyList } from "@/api/govern/partypay/partyMoney";

export default {
  name: "Member",
  components: {},
  data() {
    const validatorImage = (rule, value, callback) => {
      if (isEmpty(this.imageUrl)) {
        callback(new Error("活动图片不能为空"));
      } else {
        callback();
      }
    };
    return {
      // 住址
      address: "",
      submitFlag: true,
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
      dateRange: [],
      open1: false,
      // 列表数据
      memberList: [],
      organizationList: [],
      organizationLists: [],
      ownerList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 楼栋数据
      buildingList: [],
      // 单元数据
      unitList: [],
      // 房间数据
      roomList: [],
      communityList: [],
      // 选择下拉楼宇
      showBuilding: false,
      // 选择下拉单元
      showUnit: false,
      // 选择下拉房间
      showRoom: false,
      // $comment字典
      sexOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        partyId: undefined,
        memberName: undefined,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        accountType: [
          { required: true, message: "请选择入住户类型", trigger: "blur" },
        ],
        joinTime: [
          { required: true, message: "请选择入党时间", trigger: "blur" },
        ],
        memberDuty: [
          { required: true, message: "请选择党内职务", trigger: "blur" },
        ],
        communityId: [
          { required: true, message: "所属小区不能为空", trigger: "blur" },
        ],
        buildingId: [
          { required: true, message: "所属楼宇不能为空", trigger: "blur" },
        ],
        unitId: [{ required: true, message: "不能为空", trigger: "blur" }],
        roomId: [
          { required: true, message: "所属房间不能为空", trigger: "blur" },
        ],
        partyId: [
          { required: true, message: "所属党组织不能为空", trigger: "blur" },
        ],
        accountId: [
          { required: true, message: "姓名不能为空", trigger: "blur" },
        ],
      },
      imageUrl: "",
      // 是否可见新增时姓名下拉
      ownerOption: false,
      // 是否可见修改时姓名
      ownerInput: false,
      // 装修列表详情页面
      showDetail: false,
      // 显示页面
      show: true,
      // 显示添加或修改页面
      showAdd: false,
      // 是否弹出输入驳回理由
      showReason: false,
      // owner详情页面
      details: false,
      showUpdate: false,
      // 关闭按钮
      showClose: false,
      // 审核按钮显示
      showPass: false,
      // 隐藏文本框
      showInput: false,
      // 失败文本框
      errorInput: false,
      // 隐藏审核按钮
      showPassButton: false,
    };
  },

  created() {
    // 列表
    this.getList();
    // 查询党组织
    this.getOrgAll();
  },

  methods: {
    /** 查询装修列表列表 */
    getList() {
      this.loading = true;
      memberList(this.addDateRange(this.queryParams, this.dateRange)).then(
        (response) => {
          this.memberList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 所属小区 */
    getCurrentList() {
      getCurrentList().then((response) => {
        this.communityList = response.data;
      });
    },
    // 根据小区查询党组织
    getOrgList(communityId) {
      if (!equals(null, communityId)) {
        partyList(communityId).then((response) => {
          this.organizationList = response.data;
        });
      }
    },
    // 查询党组织列表
    getOrgAll() {
      partyAll().then((response) => {
        this.organizationLists = response.data;
      });
    },
    // 获取人员列表
    getOwnerList() {
      this.form.accountId != null
        ? (this.form.accountId = null)
        : this.form.accountId;
      if (equals(null, this.form.communityId)) {
        this.$message({
          message: "警告哦，请选择所属小区",
          type: "warning",
        });
        return;
      }
      const data = {
        accountType: this.form.accountType,
        communityId: this.form.communityId,
      };
      getOwnerAll(data).then((response) => {
        this.ownerList = response.data;
      });
    },

    handleOwnerPhone(ownerType) {
      this.getOwnerPhoneList(ownerType);
    },

    handleCommunityData(ownerType) {
      this.getCommunityNumberList(ownerType);
    },
    // 上传预处理
    beforeUpload(file) {
      return false;
    },
    // 上传图片
    uploadLogo(file, fileList) {
      const formData = new FormData();
      formData.append("file", file.raw);
      uploadImg(formData).then((response) => {
        this.form.pictureUrl = response.imgUrl;
        this.imageUrl = response.imgUrl;
        this.$modal.msgSuccess("上传成功");
      });
    },
    /** 查询项目列表 */
    getProCommunity() {
      const param = {
        communityType: "1",
      };
      listProCommunity(param).then((response) => {
        if (response.code == 200) {
          this.communityList = response.data;
        }
      });
    },
    /** 根据选择的小区项目id查出所有子集楼栋*/
    handleSelectionChangeForBuilding(item) {
      this.unitList = [];
      this.roomList = [];
      this.form.buildingId != null
        ? (this.form.buildingId = null)
        : this.form.buildingId;
      this.form.unitId != null ? (this.form.unitId = null) : this.form.unitId;
      this.form.roomId != null ? (this.form.roomId = null) : this.form.roomId;
      this.address = item.communityName + " ";
      this.form.partyId != null
        ? (this.form.partyId = null)
        : this.form.partyId;
      // 获取业主
      this.getOwnerList();
      // 获取党组织
      this.getOrgList(item.communityId);
      getBuildingList(item.communityId).then((response) => {
        this.buildingList = response.data;
        this.showBuilding = true;
      });
    },
    /** 根据选择的楼宇id查出所有子集单元*/
    handleSelectionChangeForUnit(item) {
      this.roomList = [];
      this.form.unitId != null ? (this.form.unitId = null) : this.form.unitId;
      this.address = this.address + item.buildingName + " ";
      getUnitList(item.buildingId).then((response) => {
        this.unitList = response.data;
        this.showUnit = true;
      });
    },
    /** 根据选择的单元id查出所有子集房间号*/
    handleSelectionChangeForRoom(item) {
      this.form.roomId != null ? (this.form.roomId = null) : this.form.roomId;
      this.address = this.address + item.unitName + " ";
      getRoomList(item.unitId).then((response) => {
        this.roomList = response.data;
        this.showRoom = true;
      });
    },
    /** 房间号*/
    handleSelectionChangeForRooms(item) {
      this.address = this.address + item.roomName;
      getRoomList(item.roomId).then((response) => {
        this.roomList = response.data;
        this.showRoom = true;
      });
    },
    /** 关闭查看*/
    closeDetail() {
      this.showDetail = false;
      this.open = false;
      this.cancel1();
      this.showPass = false;
      this.showPassButton = false;
      this.details = false;
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.submitFlag = true;
      this.reset();
      this.open = true;
      this.showUpdate = true;
      this.ownerInput = true;
      this.ownerOption = false;
      /** 清除修改时姓名的效验*/
      /* this.$refs["forms"].fields.map(i=>{
        if ("ownerId" === i.prop){
          i.clearValidate();
          return false;
        }
      })*/
      // 获取详情
      getMember(row.memberId).then((response) => {
        this.form = response.data;
        this.address = response.data.address;
        // 查党组织
        this.getOrgList(this.form.communityId);
        // 查楼栋
        getBuildingList(this.form.communityId).then((response) => {
          this.buildingList = response.data;
          this.showBuilding = true;
        });
        // 查单元
        getUnitList(this.form.buildingId).then((response) => {
          this.unitList = response.data;
          this.showUnit = true;
        });
        // 查房间
        getRoomList(this.form.unitId).then((response) => {
          this.roomList = response.data;
          this.showRoom = true;
        });
        this.title = "修改党员信息";
      });
      // 获取项目
      this.getCurrentList();
    },

    // 取消按钮
    cancel1() {
      this.showUpdate = false;
      this.open = false;
    },
    // 表单重置
    reset() {
      this.form = {};
      this.address = "";
      this.imageUrl = null;
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
      this.ids = selection.map((item) => item.memberId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.submitFlag = true;
      this.open = true;
      this.showUpdate = true;
      this.ownerInput = false;
      this.ownerOption = true;
      this.title = "新增党员信息";
      // 获取项目
      this.getCurrentList();
    },
    /** 驳回审核弹框*/
    checkNo(row) {
      this.showReason = true;
    },

    /** 审核按钮操作 */
    handlePass(row) {
      this.showPass = true;
      this.title = "审核列表弹框";
      this.open1 = true;
      this.form = row;
    },

    /** 驳回审核弹框*/
    /* checkDecorationNo() {
       this.showReason = true;
       for(const i in this.ids){
         const ids = this.ids[i];
         this.form.memberId = ids;
       }
     },*/

    /** 驳回审核*/
    /* handleCheckNo(row) {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateErrorOrganizeReject(row).then(response => {
            this.cancel();
            this.reset();
            this.open = false;
            this.showCheck = false;
            this.getList();
            this.msgSuccess("审核不通过");
          });
        }
      });
    },*/
    /** 查看按钮操作 */
    handleView(row) {
      this.showDetail = true;
      this.open = true;
      this.form = row;
    },

    /** 删除按钮操作 */
    handleRemove(row) {
      console.log(row, "删除");
      const memberId = row.memberId;
      this.$confirm("是否确认删除此党员?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return MemberRemove({ memberId: memberId });
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        });
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["forms"].validate((valid) => {
        if (valid) {
          if (this.form.memberId != null) {
            upMember(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.showUpdate = false;
              this.getList();
            });
          } else {
            addMember(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.showUpdate = false;
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },

    /** 删除按钮操作 */
    handleDelete(row) {
      const memberIds = row.memberId || this.ids;
      this.$confirm("是否确认删除数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return delMember(memberIds);
        })
        .then(() => {
          this.getList();
          this.ids = [];
          this.msgSuccess("删除成功");
        });
    },
  },
};
</script>

<style scoped>
.avatar {
  width: 400px;
  height: 200px;
  display: block;
}

.avatar2 {
  width: 400px;
  height: 200px;
  display: block;
}

.peopleImg {
  width: 120px;
  height: 160px;
  display: block;
}

.pass {
  margin-top: 30px;
  margin-right: 80px;
  background-color: #33cc66;
}

.reject {
  margin-top: 30px;
  margin-right: 120px;
  background-color: #ff3300;
}
</style>

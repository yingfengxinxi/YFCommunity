<template>
  <div class="app-container" label-width="58px">
    <!--        搜索        -->
    <div v-show="openView === 0">
        <!--   选择树   -->
        <el-col :span="4">
          <div class="head-container">
            <el-input
              placeholder="请输入小区名称"
              v-model="filterText">
            </el-input>

            <el-tree
              class="filter-tree hide-tree"
              :data="currentList"
              :props="currentListProps"
              default-expand-all
              :filter-node-method="filterNode"
              icon-class="el-icon-star-on"
              highlight-current
              node-key="communityId"
              @node-click="handleNodeClick"
              ref="tree">
            </el-tree>
          </div>
        </el-col>
        <!--   右侧   -->
        <el-col :span="20" :xs="24">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                   label-width="100px">
            <el-form-item label="活动名称" prop="actTheme" label-width="95px">
              <el-input
                v-model="queryParams.actTheme"
                placeholder="请输入活动名称"
                style="width: 215px"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="发布人" prop="createBy" label-width="95px">
              <el-input
                v-model="queryParams.createBy"
                placeholder="请输入发布人姓名"
                style="width: 215px"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="活动状态" prop="state">
              <el-select v-model="queryParams.state" placeholder="请选择活动状态" style="width: 180px">
                <el-option label="未开始" value="0"/>
                <el-option label="进行中" value="1"/>
                <el-option label="已结束" value="2"/>
              </el-select>
            </el-form-item>
            <el-form-item label="活动时间" prop="startTime">
              <div class="block">
                <el-date-picker
                  v-model="queryParams.startTime"
                  type="date"
                  placeholder="选择日期">
                </el-date-picker>
              </div>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" float=right @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-add"
              size="mini"
              @click="handleAdd"
             
            >新增
            </el-button>
          </el-col>

          <!--        表单        -->
          <el-table v-loading="loading" :data="manageList" @selection-change="handleSelectionChange"
                    style="width: 100%"
          >
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column label="活动名称" align="center" prop="actTheme" show-overflow-tooltip/>
            <el-table-column label="活动开始时间" align="center" prop="startTime"  width="155"/>
            <el-table-column label="活动结束时间" align="center" prop="endTime"  width="155"/>
            <el-table-column label="活动地点" align="center" prop="address" show-overflow-tooltip/>
            <el-table-column label="发布人" align="center" prop="createBy"/>
            <el-table-column label="状态" align="center" prop="state"/>
            <el-table-column label="报名人数" align="center" prop="toSignUpNumber"/>
            <el-table-column label="发布时间" align="center" prop="createTime"  width="155"/>
            <el-table-column label="操作" align="center">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="handleView(scope.row)"
                >查看
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleEdit(scope.row)"
                  
                  v-show="scope.row.state === '未开始'"
                >修改
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  style="color: #f56c6c"
                  @click="handleDelete(scope.row)"
                
                  v-show="scope.row.state === '未开始' || scope.row.state === '已结束'"
                >删除
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
    </div>
    <!--    新增页面  -->
    <div v-show="openView === 1">
        <el-form ref="form1" :model="form1" :rules="rules" label-width="180px">
          <el-row>
            <el-col :span="8">
              <el-form-item label="活动名称：" prop="actTheme">
                <el-input v-model="form1.actTheme" maxlength="20" placeholder="请输入活动名称"></el-input>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="活动地点：" prop="address">
                <el-input v-model="form1.address" maxlength="20" placeholder="请输入活动地点"></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="活动开始时间：" prop="startTime">
                <el-date-picker
                  v-model="form1.startTime"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  type="datetime"
                  placeholder="选择日期"
                  style="width: 100%;"
                  :picker-options="pickerOptions">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="活动结束时间：" prop="endTime">
                <el-date-picker
                  v-model="form1.endTime"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  type="datetime"
                  placeholder="选择日期"
                  style="width: 100%;"
                  :picker-options="pickerOptions">
                </el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="报名截止时间：" prop="closeTime">
                <el-date-picker
                  v-model="form1.closeTime"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  type="datetime"
                  style="width: 100%;"
                  placeholder="选择日期">
                </el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="活动头图：" prop="pictureUrl">
                <ImageUpload @input="handleUpload" :value="form1.pictureUrl" ref="imgupload" :limit="1"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="24">
              <el-form-item label="活动说明：" prop="content">
                <editor v-model="form1.content" :min-height="192" class="position"/>
              </el-form-item>
            </el-col>
          </el-row>
          <div style="float: right;">
            <el-button type="primary" size="middle" @click="submitForm">确定</el-button>
            <el-button size="middle" @click="before">返回</el-button>
          </div>
        </el-form>
    </div>
    <!--    查看详情页面  -->
    <div v-show="openView === 2">
      <el-tabs v-model="activeName">
        <el-tab-pane label="活动信息" name="first">
          <el-button size="middle" @click="before" style="float:right;">关 闭</el-button>
          <h4 class="form-header" style="color: #0558e7">活动基本信息</h4>
            <el-form ref="form" :model="form" label-width="180px">
                <el-row>
                  <el-col :span="6">
                    <el-form-item label="活动名称：">
                      {{ form.actTheme }}
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item label="活动头图：">
                      <img :src="this.form.pictureUrl" class="image"/>
                    </el-form-item>
                  </el-col>
                  
                </el-row>
                <el-row>
                  <el-col :span="10">
                    <el-form-item label="活动创建时间：">
                      {{ form.createTime }}
                    </el-form-item>
                  </el-col>
                  <el-col :span="10">
                    <el-form-item label="活动报名截止时间：">
                      {{ form.closeTime }}
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="10">
                    <el-form-item label="活动开始时间：">
                      {{ form.startTime }}
                    </el-form-item>
                  </el-col>
                  <el-col :span="10">
                    <el-form-item label="活动结束时间：">
                      {{ form.endTime }}
                    </el-form-item>
                  </el-col>
                  
                </el-row>
                <el-row>
                  <el-col :span="6">
                    <el-form-item label="活动发布时间：">
                      <div style="width: 280px">{{ form.createTime }}</div>
                    </el-form-item>
                  </el-col>
                  <el-col :span="10">
                    <el-form-item label="活动地点：">
                      {{ form.address }}
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item label="活动报名人数：">
                      {{ form.toSignUpNumber }}
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="6">
                    <el-form-item label="发布人：">
                      {{ form.createBy }}
                    </el-form-item>
                  </el-col>
                  <el-col :span="6">
                    <el-form-item label="活动状态：">
                      <div style="width: 280px">{{ form.state }}</div>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="活动说明：">
                      <div v-html="form.content">
                      </div>
                    </el-form-item>
                  </el-col>
                </el-row>
            </el-form>
        </el-tab-pane>
        <el-tab-pane label="参与人员信息" name="second">
          <el-button size="middle" @click="before" style="float:right;">关 闭</el-button>
          <h4 class="form-header" style="color: #0558e7">活动基本信息</h4>
          <div style="display: flex">
            <el-form ref="form" :model="form" label-width="180px">
              <el-col :span="24">
                <el-row>
                  <el-col :span="4" :offset="1">
                    <el-form-item label="已报名人数：">
                      <div style="width: 280px">{{ form.toSignUpNumber }}</div>
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-col>
            </el-form>
          </div>
          <h4 class="form-header" style="color: #0558e7">参与人员明细</h4>
          <div>
            <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                     label-width="100px">
              <el-form-item label="姓名" prop="ownerName" label-width="95px">
                <el-input
                  v-model="queryParams.ownerName"
                  placeholder="请输入姓名"
                  style="width: 215px"
                  clearable
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item label="联系电话" prop="ownerPhone" label-width="95px">
                <el-input
                  v-model="queryParams.ownerPhone"
                  placeholder="请输入联系电话"
                  style="width: 215px"
                  clearable
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery2">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" float=right @click="resetQuery2">重置</el-button>
              </el-form-item>
            </el-form>
            <el-table v-loading="loading" :data="joinPeopleList">
              <el-table-column label="姓名" align="center" prop="ownerName" width="280px" show-overflow-tooltip />
              <el-table-column label="手机号" align="center" prop="ownerPhone" width="280px"/>
              <el-table-column label="报名时间" align="center" prop="createTime"  width="280px"/>
              <el-table-column label="备注" align="center" prop="remark"/>
            </el-table>
            <pagination
              v-show="total > 0"
              :total="total"
              :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize"
              @pagination="getJoin"
            />
            <h4 class="form-header" style="color: #0558e7"></h4>
          </div>
        </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import {
  addMemberAct,
  getMemberActInfo,
  joinPeopleListManage,
  memberActList,
  BuildinglistManage,
  updateActivite, delActivite
} from "@/api/estate/memberact/memberAct";
import {getCurrentList} from "@/api/estate/community";
import ImageUpload from "@/components/ImageUpload";
import {parseTime} from "@/utils/global";
import {uploadImageUrl} from "@/api/estate/customer/goods/goods";
import {equals} from "@/utils/common";

export default {
  name: "activiteManage",
  components: {ImageUpload},
  data() {
    return {
      dataNum: 0,
      BASE_API: process.env.VUE_APP_BASE_API,
      actUrl: process.env.VUE_APP_BASE_API + "/information/activity/upload",
      loading: false,
      showSearch: true,
      openView: false,
      total: 0,
      totals: 0,
      isabled: true,
      imageUrl: null,
      fileList: [],
      // 树筛选文本
      filterText: '',
      //小区数据集合
      currentList: [],
      //树结构
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      joinform: {},
      distributionLists: [
        {
          supplyNum: "",
          supplyName: "",
        },
      ],
      form: {},
      form1: {
        buildingIds: [],
        actRang: undefined,
      },
      manageList: [],
      dateRange: [],
      actRangList: [],
      joinPeopleList: [],
      actTheme: "",
      comOptions: [],
      communtiyList: [],
      buildingList: [],
      defaultProps: {
        children: "communityId",
        label: "communityName"
      },
      oilStation: [],
      open: false,
      title: "",
      single: true,
      multiple: true,
      projectId: 10,
      communityId: null,
      activityId: null,
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        actTheme: null,
        typeId: null,
        typeName: null,
        createBy: null,
        state: null,
        startTime: null,
        signState: null,
        ownerPhone: null,
        ownerName: null,
      },
      activeName: 'first',

      // 表单校验
      rules: {
        pictureUrl: [
          {required: true, message: "活动主图不能为空", trigger: "blur"}
        ],
        actTheme: [
          {required: true, message: "活动名称不能为空", trigger: "blur"}
        ],
        typeId: [
          {required: true, message: "类型不能为空", trigger: "trigger"}
        ],
        address: [
          {required: true, message: "地址不能为空", trigger: "blur"}
        ],
        startTime: [
          {required: true, message: "开始时间不能为空", trigger: "trigger"}
        ],
        endTime: [
          {required: true, message: "活动结束时间不能为空", trigger: "trigger"}
        ],
        closeTime: [
          {required: true, message: "报名截至时间不能为空", trigger: "trigger"}
        ],
        content: [
          {required: true, message: "活动说明不能为空", trigger: "blur"}
        ],
      },
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        }
      }
    };
  },
  created() {
    this.openView = 0;
    this.getTree();
    this.getBuildingList();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    // 删除表单
    removeDistributionList(index) {
      this.distributionLists.splice(index, 1);
    },
    // 新增表单
    addDistributionList() {
      this.distributionLists.push({
        supplyNum: "",
        supplyName: "",
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(row) {
      this.queryParams.communityId = row.communityId
      this.communityId = row.communityId
      this.getList();
      this.getBuildingList();
    },
    // 获取树数据
    getTree() {
      getCurrentList().then(res => {
        this.currentList = res.data;
        this.queryParams.communityId = this.currentList[0].communityId;
        this.communityId = this.currentList[0].communityId;
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.currentList[0].communityId);
        });
        this.getList();
      })
    },
    // 查询列表
    getList() {
      // this.openView = 0;
      memberActList(this.queryParams).then(response => {
        this.manageList = response.rows;
        this.total = response.total;
        this.openView = 0;
      });
    },

    impose() {
      // this.dialogEditForm.name是input框绑定的值
      var str = this.form1.actAmount
      var len1 = str.substr(0, 1)
      var len2 = str.substr(1, 1)
      // 如果第一位是0，第二位不是点，就用数字把点替换掉
      if (str.length > 1 && len1 == 0 && len2 != ".") {
        str = str.substr(1, 1)
      }
      // 第一位不能是.
      if (len1 == ".") {
        str = "";
      }
      // 限制只能输入一个小数点
      if (str.indexOf(".") != -1) {
        var str_ = str.substr(str.indexOf(".") + 1)
        if (str_.indexOf(".") != -1) {
          str = str.substr(0, str.indexOf(".") + str_.indexOf(".") + 1)
        }
      }
      // 正则替换，保留数字和小数点
      str = str.match(/^\d*(\.?\d{0,1})/g)[0] || null
      this.form1.actAmount = str
    },

    // 新增按钮
    handleAdd() {
      if (this.communityId == null) {
        this.$modal.msgError("请选择小区");
      } else {
        this.form1.actRang = 0;
        this.queryParams.activityId = undefined;
        this.form.activityId = undefined;
        this.getBuildingList();
        this.imageUrl = null;
        // this.form.supplyName=undefined;
        // this.form.supplyNum=undefined;
        this.reset();
        this.openView = 1;
      }

    },
    // 删除按钮
    handleDelete(row) {
      this.$modal.confirm('是否确认删除选中的数据项？').then(function () {
        return delActivite(row.activityId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    // 取消按钮列表
    returnbefore() {
      this.open = false
    },
    // 返回
    before() {
      this.reset();
      this.openView = 0;
      this.getList()
    },
    /** 表单重置*/
    reset() {
      this.form1 = {
        actTheme: undefined,
        typeId: undefined,
        address: undefined,
        startTime: undefined,
        endTime: undefined,
        closeTime: undefined,
        supplyName: undefined,
        supplyNum: undefined,
        buildingIds: []
      }
      this.distributionLists = [
        {
          supplyNum: "",
          supplyName: "",
        },
      ]
    },
    // 搜索按钮
    handleQuery() {
      if (this.queryParams.startTime != null) {
        this.queryParams.startTime = parseTime(this.queryParams.startTime, '{y}-{m}-{d}');
      }
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 重置按钮
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.actTheme = null;
        this.queryParams.typeId = null;
        this.queryParams.typeName = null;
        this.queryParams.createBy = null;
        this.queryParams.state = null;
        this.queryParams.startTime = null;
        this.queryParams.supplyName = undefined;
        this.queryParams.supplyNum = undefined;
        this.state = undefined;
        this.handleQuery();
    },
    // 重置按钮2
    resetQuery2() {
      this.resetForm("queryForm");
      this.queryParams.actTheme = null,
        this.queryParams.typeId = null,
        this.queryParams.typeName = null,
        this.queryParams.createBy = null,
        this.queryParams.state = null,
        this.queryParams.startTime = null,
        this.queryParams.createBy = null,
        this.queryParams.signState = null,
        this.queryParams.ownerPhone = null,
        this.queryParams.ownerName = undefined,
        this.state = undefined,
        this.handleQuery2();
    },
    // 删除按钮
    handleDetele() {
    },
    // 图片上传
    handleUpload(data) {
      this.$forceUpdate('imgupload')
      this.form1.pictureUrl = data;

    },
    // 获取楼栋列表
    getBuildingList() {
      BuildinglistManage(this.queryParams).then(response => {
        this.buildingList = response.data;
      });
    },
    // 编辑按钮
    handleEdit(row) {
      this.reset();
      this.queryParams.communityId = row.communityId;
      this.queryParams.activityId = row.activityId;
      const methodId = row.activityId || this.ids
      this.getBuildingList();
      getMemberActInfo(methodId).then(resp => {
        this.form1 = resp.data;
        this.distributionLists = resp.data.distributionLists;
        this.form1.actRang = Number(resp.data.actRang);
        console.log(resp.data.actRang)
        console.log(this.form1)
      })
      this.openView = 1;
    },
    // 查看按钮
    handleView(row) {
      this.openView = 2;
      this.activeName = 'first';
      getMemberActInfo(row.activityId).then(resp => {
        this.form = resp.data
      })
      
      this.queryParams.activityId = row.activityId
     
      this.activityId = row.activityId
      this.getJoin();

    },
    // 上传前校检格式和大小
    handleBeforeUpload(file) {
      return false;
    },
    handleAvatar(file) {
      if (file) {
        let formData = new FormData();
        formData.append("file", file.raw);
        uploadImageUrl(formData).then((res) => {
          this.form1.pictureUrl = res.imgPath;
          this.imageUrl = res.imgPath;
          this.$modal.msgSuccess("上传成功");
        });
      }
    },
    // 参与人员列表
    getJoin() {
      joinPeopleListManage(this.queryParams).then(response => {
        this.joinPeopleList = response.rows;
        this.total = response.total;
      });
    },
    // 参与人员列表搜索
    handleQuery2() {
      this.queryParams.pageNum = 1;
      this.queryParams.activityId = this.activityId
      this.getJoin();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.activityId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    // 提交按钮
    submitForm() {
      this.$refs["form1"].validate(valid => {
        if (valid) {
          if (this.form1.pictureUrl== null) {
            this.$modal.msgError("请上传图片");
          } else {
            if (this.form1.startTime >= this.form1.endTime) {
              this.$modal.msgError("开始时间不能大于结束时间");
              return;
            }
            if (this.form1.closeTime >= this.form1.startTime) {
              this.$modal.msgError("报名截止时间在开始时间之前");
              return;
            }
            if (this.form1.activityId != null) {
              this.form1.distributionLists = this.distributionLists;
              this.form1.communityId = this.communityId;
              updateActivite(this.form1).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.getList();
                this.openView = 0;
              });
            } else {
              this.form1.communityId = this.communityId;
              this.form1.distributionLists = this.distributionLists;
              this.form1.communityId = this.communityId;
              addMemberAct(this.form1).then(response => {
                this.$modal.msgSuccess("新增成功");
                this.form1 = {};
                this.getList();
                this.openView = 0;
              });
            }
          }
        }
      });
    },

    standardChange(val) {
      if (equals(val.length, 0)) {
        this.form1.buildingList = [];
      } else {
        this.form1.buildingList = [];
        for (let i = 0; i < val.length; i++) {
          this.buildingList.find((item) => {
            if (item.buildingName === val[i]) {
              let assessItem = {};
              assessItem.buildingName = item.buildingName;
              assessItem.buildingId = item.buildingId;
              this.form1.buildingList.push(assessItem)
            }
          });
        }

      }
    },
  }
}
</script>

<style>
.margin > label {
  margin-top: 20px !important;
}

.editor-view .ql-toolbar {
  display: none;
}

.editor-view .ql-toolbar.ql-snow + .ql-container.ql-snow {
  border: 1px solid #ccc;
}

.ql-editor {
  overflow-y: scroll;
}

.ql-editor::-webkit-scrollbar {
  display: none;
}

.avatar {
  width: 100%;
  height: 100%;
}

.image {
  height: 200px;
  width: 200px
}

.el-form-item__error {
  width: 150px;
}

.position {
  width: 64%;
  z-index: 1;
}
</style>

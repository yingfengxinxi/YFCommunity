<template>
  <div class="app-container">
    <div v-show="view === 1">

      <el-row :gutter="20">
        <el-col :span="4" :xs="24">
          <div class="head-container">
            <el-input
              placeholder="请输入小区名称"
              clearable
              v-model="filterText">
            </el-input>
            <el-tree
              :data="currentList"
              :props="currentListProps"
              :expand-on-click-node="false"
              ref="tree"
              default-expand-all
              highlight-current
              :filter-node-method="filterNode"
              @node-click="handleNodeClick"
            />
          </div>
        </el-col>

        <el-col :span="20" :xs="24">
          <div class="tabble">
            <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                     label-width="110px">

              <el-form-item label="老人姓名" prop="older">
                <el-input v-model="queryParams.ownerName" placeholder="请输入老人姓名"/>
              </el-form-item>

              <el-form-item label="紧急联系人姓名" prop="contactName">
                <el-input v-model="queryParams.contactName" placeholder="请输入紧急联系人姓名"/>
              </el-form-item>

              <el-form-item label="紧急人联系方式" prop="contactPhone">
                <el-input v-model="queryParams.contactPhone" placeholder="请输入紧急人联系方式"/>
              </el-form-item>

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
                  v-hasPermi="['oldman:elderlyfile:add']"
                >新增
                </el-button>

                <el-button
                  type="danger"
                  plain
                  icon="el-icon-delete"
                  size="mini"
                  :disabled="multiple"
                  @click="handleDelete"
                  v-hasPermi="['oldman:elderlyfile:del']"
                >删除
                </el-button>
              </el-col>
              <el-col :span="1.5">
              </el-col>
              <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
            </el-row>

            <el-table v-loading="loading" :data="olderList" @selection-change="handleSelectionChange">
              <el-table-column type="selection" width="55" align="center"/>
              <el-table-column label="老人姓名" align="center" prop="ownerName"/>
              <el-table-column label="所属房号" align="center" prop="roomName"/>
              <el-table-column label="年龄" align="center" prop="age"/>
              <el-table-column label="性别" align="center" prop="oldGender" :formatter="Dgender"/>
              <el-table-column label="紧急联系人" align="center" prop="contactName"/>
              <el-table-column label="与老人关系" align="center" prop="relation" :formatter="gx"/>
              <el-table-column label="联系人手机号" align="center" prop="contactPhone"/>
              <el-table-column label="最后操作时间" align="center" prop="updateTime"/>
              <el-table-column label="最后操作人" align="center" prop="updateBy"/>
              <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                <template slot-scope="scope">
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-view"
                    @click="lookgg(scope.row)"
                  >查看
                  </el-button>

                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-delete"
                    style="color: #f56c6c"
                    @click="handleDelete(scope.row)"
                    v-hasPermi="['oldman:elderlyfile:del']"
                  >删除
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </div>
          <pagination
            v-show="total>0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
          />
        </el-col>

        <!--添加数据或修改-->
        <el-dialog :title="title" :visible.sync="open" width="1500px" append-to-body>
          <el-form ref="form" :model="form" :rules="rules" label-width="px">
            <el-row :gutter="20">
              <h2>房产信息</h2>
              <hr>
              <el-col :span="8">
                <el-form-item label="与户主关系:">
                  <span>{{ ownerRelation }}</span>
                </el-form-item>

                <el-form-item label="所属单元:">
                  <el-select v-model="form.unitId" @change="getroomN()" placeholder="请选择">
                    <el-option
                      v-for="(item, index) in unitList"
                      :key="index"
                      :label="item.unitName"
                      :value="item.unitId"
                    ></el-option>
                  </el-select>
                </el-form-item>


              </el-col>

              <el-col :span="8">
                <el-form-item label="所属小区:">
                  <el-select v-model="form.communityId" @change="getbuilding()" placeholder="请选择">
                    <el-option
                      v-for="(item, index) in currentList"
                      :key="index"
                      :label="item.communityName"
                      :value="item.communityId"
                    ></el-option>
                  </el-select>
                </el-form-item>

                <el-form-item label="所属房间:">
                  <el-select v-model="form.roomId" @change="GetroomStatus" placeholder="请选择">
                    <el-option
                      v-for="(item, index) in roomList"
                      :key="index"
                      :label="item.roomName"
                      :value="item.roomId"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="所属楼字:">
                  <el-select v-model="form.buildingId" @change="getunit()" placeholder="请选择">
                    <el-option
                      v-for="(item, index) in buildingList"
                      :key="index"
                      :label="item.buildingName"
                      :value="item.buildingId"
                    ></el-option>
                  </el-select>
                </el-form-item>

                <el-form-item label="房屋状态:">
                  <span v-if="roomStatus == 0">未售</span>
                  <span v-else-if="roomStatus == 1">已售</span>
                  <span v-else-if="roomStatus == 2">入住</span>
                  <span v-else-if="roomStatus == 3">装修</span>
                  <span v-else-if="roomStatus == 4">已拿房</span>
                  <span v-else-if="roomStatus == 5">整租</span>
                  <span v-else-if="roomStatus == 6">合租</span>
                  <span v-else-if="roomStatus == 7">群组</span>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row :gutter="20">
              <h2>业主基本信息</h2>
              <hr>
              <el-col :span="8">
                <el-form-item label="姓名:" prop="ownerId">
                  <el-select v-model="form.ownerId" @change="szjbxx()" placeholder="请选择">
                    <el-option
                      v-for="(item, index) in oldman"
                      :key="index"
                      :label="item.ownerName"
                      :value="item.ownerId"
                    ></el-option>
                  </el-select>
                </el-form-item>

                <el-form-item label="出生日期:">
                  <span>{{ oldDate }}</span>
                </el-form-item>


                <el-form-item label="身份证正面照片：">
                  <div style="width: 280px">
                    <el-image
                      style="width: 80px; height: 50px;vertical-align: middle"
                      :src="cardFont"
                      fit="scale-down"></el-image>
                  </div>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="手机号:">
                  <span>{{ ownerPhone }}</span>
                </el-form-item>

                <el-form-item label="身份证类型:">
                  <span>{{ cardType }}</span>
                </el-form-item>

                <el-form-item label="身份证反面：">
                  <div style="width: 280px">
                    <el-image
                      style="width: 80px; height: 50px;vertical-align: middle"
                      :src="cardBack"
                      fit="scale-down"></el-image>
                  </div>
                </el-form-item>

              </el-col>

              <el-col :span="8">
                <el-form-item label="性别:">
                  <span v-if="gender == 0">男</span>
                  <span v-else-if="gender == 1">女</span>
                  <span v-if="gender == 2">未知</span>
                </el-form-item>

                <el-form-item label="人脸图片：">
                  <div style="width: 280px">
                    <el-image
                      style="width: 80px; height: 50px;vertical-align: middle"
                      :src="faceUrl"
                      fit="scale-down"></el-image>
                  </div>
                </el-form-item>

              </el-col>
            </el-row>

            <el-row :gutter="20">
              <h2>紧急联系人信息</h2>
              <hr>
              <el-col :span="8">
                <el-form-item label="姓名:" prop="contactName">
                  <el-input v-model="form.contactName" placeholder="请输入姓名" class="weizhi80"/>
                </el-form-item>

                <el-form-item label="联系方式:" prop="contactPhone">
                  <el-input v-model="form.contactPhone" placeholder="请输入联系方式" class="weizhi80"/>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="性别:" prop="contactGender">
                  <el-select v-model="form.contactGender" placeholder="请选择" class="weizhi80">
                    <el-option
                      v-for="dict in dict.type.sys_user_sex"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>

                <el-form-item label="与老人关系:" prop="relation">
                  <el-select v-model="form.relation" placeholder="请选择" class="weizhi80">
                    <el-option
                      v-for="dict in dict.type.sys_oldman_gx"
                      :key="dict.value"
                      :label="dict.label"
                      :value="dict.value"
                    />
                  </el-select>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="身份证:" prop="contactId">
                  <el-input v-model="form.contactId" placeholder="请输入身份证号" class="weizhi80"/>
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitForm" v-hasPermi="['oldman:elderlyfile:add']">确 定</el-button>
            <el-button @click="reset">取 消</el-button>
          </div>
        </el-dialog>
      </el-row>
    </div>

    <div v-show="view === 2">

      <el-form ref="form" :model="form" :rules="rules" label-width="px">
        <el-row :gutter="20">
          <h2>房产信息</h2>
          <hr>
          <el-col :span="8">
            <el-form-item label="与户主关系:">
              <span>{{ form.ownerRelation }}</span>
            </el-form-item>

            <el-form-item label="所属单元:">
              <span>{{ form.unitName }}</span>
            </el-form-item>

          </el-col>
          <el-col :span="8">
            <el-form-item label="所属小区:">
              <span>{{ form.communityName }}</span>
            </el-form-item>

            <el-form-item label="所属房间:">
              <span>{{ form.roomName }}</span>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="所属楼字:">
              <span>{{ form.buildingName }}</span>
            </el-form-item>

            <el-form-item label="房屋状态:">
              <span v-if="form.roomStatus == 0">未售</span>
              <span v-else-if="form.roomStatus == 1">已售</span>
              <span v-else-if="form.roomStatus == 2">入住</span>
              <span v-else-if="form.roomStatus == 3">装修</span>
              <span v-else-if="form.roomStatus == 4">已拿房</span>
              <span v-else-if="form.roomStatus == 5">整租</span>
              <span v-else-if="form.roomStatus == 6">合租</span>
              <span v-else-if="form.roomStatus == 7">群组</span>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <h2>业主基本信息</h2>
          <hr>
          <el-col :span="8">
            <el-form-item label="姓名:">
              <span>{{ form.ownerName }}</span>
            </el-form-item>

            <el-form-item label="年龄:">
              <span>{{ form.age }}</span>
            </el-form-item>

            <el-form-item label="身份证正面照片：">
              <div style="width: 500px">
                <image-preview :src="form.cardFont"
                               style="width: 80px; height: 80px;vertical-align: middle"></image-preview>
              </div>
            </el-form-item>

          </el-col>

          <el-col :span="8">
            <el-form-item label="手机号:">
              <span>{{ form.ownerPhone }}</span>
            </el-form-item>

            <el-form-item label="身份证类型:">
              <span>{{ form.cardType }}</span>
            </el-form-item>

            <el-form-item label="身份证反面照片：">
              <div style="width: 500px">
                <image-preview :src="form.cardBack"
                               style="width: 80px; height: 80px;vertical-align: middle"></image-preview>

              </div>
            </el-form-item>

          </el-col>

          <el-col :span="8">
            <el-form-item label="性别:">
              <span v-if="form.oldGender == 0">男</span>
              <span v-else-if="form.oldGender == 1">女</span>
              <span v-if="form.oldGender == 2">未知</span>
            </el-form-item>

            <el-form-item label="人脸图片：">
              <div style="width: 280px">
                <image-preview :src="form.faceUrl"
                               style="width: 80px; height: 80px;vertical-align: middle"></image-preview>

              </div>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row :gutter="20">
          <h2>紧急联系人信息</h2>
          <hr>
          <el-col :span="8">
            <el-form-item label="姓名:">
              <span>{{ form.contactName }}</span>
            </el-form-item>

            <el-form-item label="联系方式:">
              <span>{{ form.contactPhone }}</span>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="性别:">
              <span>{{ form.contactGender }}</span>
            </el-form-item>

            <el-form-item label="与老人关系:">
              <span>{{ form.relation }}</span>
            </el-form-item>
          </el-col>

          <el-col :span="8">
            <el-form-item label="身份证:">
              <span>{{ form.contactId }}</span>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div class="table-div">
        <h2>体检记录</h2>
        <hr>
        <el-table :data="olderList1">
          <el-table-column label="活动名称" align="center" prop="activityName"/>
          <el-table-column label="活动类型" align="center" prop="medicalType">
            <template #default="scope">
              <span v-if="scope.row.medicalType == '0'">公益活动</span>
              <span v-if="scope.row.medicalType == '1'">其他</span>
            </template>
          </el-table-column>
          <el-table-column label="体检办理机构" align="center" prop="medicalOrg"/>
          <el-table-column label="体检医生" align="center" prop="examinerDoctor"/>
          <el-table-column label="体检报告状态" align="center" prop="examinerImportStatus">
            <template #default="scope">
              <span v-if="scope.row.examinerImportStatus == '0'">已导入</span>
              <span v-if="scope.row.examinerImportStatus == '1'">未导入</span>
            </template>
          </el-table-column>
          <el-table-column label="报告发送状态" align="center" prop="examinerSendStatus">
            <template #default="scope">
              <span v-if="scope.row.examinerSendStatus == '0'">已发送</span>
              <span v-if="scope.row.examinerSendStatus == '1'">未发送</span>
            </template>
          </el-table-column>
          <el-table-column label="发送时间" align="center" prop="examinerReportTime"/>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="lookgg1(scope.row)"
              >查看
              </el-button>
            </template>
          </el-table-column>
        </el-table>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-button @click="reset">取 消</el-button>
      </div>


      <!--    查看-->
      <el-dialog :title="see2title" :visible.sync="see2" width="1500px">
        <el-form>
          <el-row type="flex" justify="space-between">
            <el-col :span="4">
              <el-form-item label="体检项目名称:">
                <span>{{ see2List.activityName }}</span>
              </el-form-item>
            </el-col>

            <el-col :span="4">
              <el-form-item label="体检人员姓名:">
                <span>{{ see2List.ownerName }}</span>
              </el-form-item>
            </el-col>

            <el-col :span="4">
              <el-form-item label="体检人员性别:">
                <span v-if="this.see2List.olderGender == 0">男</span>
                <span v-if="this.see2List.olderGender == 1">女</span>
                <span v-if="this.see2List.olderGender == 2">未知</span>
              </el-form-item>
            </el-col>

            <el-col :span="4">
              <el-form-item label="体检人员年龄:">
                <span>{{ see2List.olderAge }}</span>
              </el-form-item>
            </el-col>

          </el-row>


          <el-row type="flex" justify="space-between">
            <el-col :span="4">
              <el-form-item label="体检时间:">
                <span>{{ see2List.examinerTime }}</span>
              </el-form-item>
            </el-col>

            <el-col :span="4">
              <el-form-item label="体检办理机构:">
                <span>{{ see2List.medicalOrg }}</span>
              </el-form-item>
            </el-col>

            <el-col :span="4">
              <el-form-item label="体检医生:">
                <span>{{ see2List.examinerDoctor }}</span>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row type="flex" justify="space-between">
          <span v-if="see2List.examinerReportPath != null">
          <el-link :href="see2List.examinerReportPath" style="color: blue" icon="el-icon-download">下载报告</el-link>
          </span>
            <span v-else style="color: blue">报告未查找到</span>
          </el-row>

        </el-form>
      </el-dialog>


    </div>
  </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {
  getbuildingList,
  getunit,
  getroom,
  getoldman,
  addoldfile,
  getList,
  del,
  getoldmsg
} from "@/api/estate/customer/oldman/oldmanfile";
import {selectDictLabels} from "@/utils/global";
import {see1} from "@/api/estate/customer/medical/medical";

export default {
  name: "elderlyfile",
  dicts: ['sys_user_sex', 'sys_oldman_gx', 'sys_certificate', 'sys_oldman_huzhu'],
  data() {
    return {
      filterText: '',

      //小区数据集合
      currentList: [],
      //楼字数据集合
      buildingList: [],
      //单元数据集合
      unitList: [],
      //房间数据集合
      roomList: [],
      //房屋类型
      roomStatus: null,
      //老人数据集合
      oldman: [],
      //老人数据列表
      olderList: [],

      //查看老人体检记录表列表
      olderList1: [],

      //老人基本信息
      //————————————————————————
      ownerRelation: null,
      cardBack: null,
      cardFont: null,
      cardNo: null,
      cardType: null,
      date: null,
      faceUrl: null,
      gender: null,
      ownerId: null,
      ownerName: null,
      ownerPhone: null,
      searchValue: null,
      oldDate: null,

      //————————————————————————
      currentListProps: {
        children: "communityId",
        label: "communityName"
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
      // 弹出层标题
      title: "",
      view: 1,
      // 是否显示弹出层
      open: false,
      // 是否显示详细弹出层
      openView1: false,
      // 是否显示Cron表达式弹出层
      openCron: false,
      // 传入的表达式
      expression: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: null,
        buildingId: null,
        unitId: null,
        roomId: null,
        ownerId: null,
        roomName: null,
        status: '1'
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        ownerId: [
          {required: true, message: "业主姓名不能为空", trigger: "blur"}
        ],
        contactName: [
          {required: true, message: "紧急联系人姓名不能为空", trigger: "blur"},
        ],
        contactPhone: [
          {required: true, message: "紧急人联系方式不能为空", trigger: "blur"},
          {pattern: /^((0\d{2,3}-\d{7,8})|(1[34578]\d{9}))$/, message: "请正确输入手机号"},
        ],
        contactGender: [
          {required: true, message: "联系人性别不能为空", trigger: "blur"},
        ],
        relation: [
          {required: true, message: "与老人关系不能为空", trigger: "blur"},
        ],
        contactId: [
          {required: true, message: "身份证不能为空", trigger: "blur"},
          {pattern: /(^\d{15}$)|(^\d{18}$)|(^\d{17}(\d|X|x)$)/, message: "请正确输入身份证号"},
        ],

      },
      see2title: "",
      see2: false,
      see2List: [],
    };
  },
  created() {
    this.view = 1;
    this.gettree();
  },

  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },


  methods: {


    // 过滤树
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },


    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },


    //字典过滤
    Dgender(row, column) {
      return this.selectDictLabel(this.dict.type.sys_user_sex, row.oldGender)
    },
    gx(row, column) {
      return this.selectDictLabel(this.dict.type.sys_oldman_gx, row.relation)
    },

    // dicts: ['sys_user_sex', 'sys_oldman_gx'],
    getbuilding() {
      getbuildingList(this.form).then(res => {
        this.form.buildingId = null;
        this.form.unitId = null;
        this.form.roomId = null;
        this.unitList = [];
        this.roomList = [];
        this.buildingList = res.data;
      })
    },
    getunit() {
      getunit(this.form).then(res => {
        this.form.unitId = null;
        this.form.roomId = null;
        this.roomList = [];
        this.unitList = res.data;
      })
    },
    getroomN() {
      getroom(this.form).then(res => {
        this.roomList = [];
        this.roomList = res.data;
      })
    },
    GetroomStatus(roomId) {
      let listtemp = this.roomList;
      for (let key in listtemp) {
        if (listtemp[key].roomId == this.form.roomId) {
          this.roomStatus = listtemp[key].roomStatus
        }
      }
      this.form.ownerId = undefined;
      this.ownerRelation = null;
      this.cardBack = null;
      this.cardFont = null;
      this.cardNo = null;
      this.cardType = null;
      this.date = null;
      this.faceUrl = null;
      this.gender = null;
      this.ownerId = null;
      this.ownerName = null;
      this.ownerPhone = null;
      this.searchValue = null;
      this.oldDate = null;
      getoldman({roomId: roomId}).then(res => {
        this.oldman = res.data
      })
    },
    szjbxx() {
      let id = this.form.ownerId;
      let oldmanlist = this.oldman
      for (let key in oldmanlist) {
        if (oldmanlist[key].ownerId == id) {
          this.cardBack = oldmanlist[key].cardBack;
          this.cardFont = oldmanlist[key].cardFont;
          this.cardNo = oldmanlist[key].cardNo;
          this.cardType = selectDictLabels(this.dict.type.sys_certificate, oldmanlist[key].cardType)
          // this.cardType = oldmanlist[key].cardType;
          this.date = oldmanlist[key].date;
          this.faceUrl = oldmanlist[key].faceUrl;
          this.gender = oldmanlist[key].gender;
          this.ownerId = oldmanlist[key].ownerId;
          this.ownerName = oldmanlist[key].ownerName;
          this.ownerPhone = oldmanlist[key].ownerPhone;
          this.searchValue = oldmanlist[key].searchValue;
          this.ownerRelation = selectDictLabels(this.dict.type.sys_oldman_huzhu, oldmanlist[key].ownerRelation.toString())
          this.oldDate = oldmanlist[key].oldDate;
        }
      }
    },


    getList() {
      this.queryParams.communityId = this.currentList[0].communityId;
      console.log(this.queryParams)
      getList(this.queryParams).then(res => {
        this.olderList = res.rows;
        this.total = res.total;
        this.loading = false;
      })
    },

    gettree() {
      getCurrentList().then(res => {
        this.currentList = res.data;
        this.getList();
      })
    },

    handleNodeClick(ro) {
      this.queryParams.communityId = ro.communityId;
      getList(this.queryParams).then(res => {
        this.olderList = res.rows;
        this.total = res.total;
        this.loading = false;
      })
    },

    handleQuery() {
      this.getList();
    },


    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        communityId: this.currentList[0].communityId,
        buildingId: null,
        unitId: null,
        roomId: null,
        ownerId: null,
        roomName: null,
        status: '1'
      }
      this.getList()
    },
    reset() {
      this.resetForm("form");
      this.resetQuery()
      this.form = {}

      this.ownerRelation = null
      this.cardBack = null
      this.cardFont = null
      this.cardNo = null
      this.cardType = null
      this.date = null
      this.faceUrl = null
      this.gender = null
      this.ownerId = null
      this.ownerName = null
      this.ownerPhone = null
      this.searchValue = null
      this.oldDate = null
      this.open = false
      this.view = 1
    },

    handleAdd() {
      this.reset()
      this.open = true
      this.title = '新增老人档案'
    },
    handleDelete(row) {
      const olderId = row.olderId || this.ids;
      this.$modal.confirm('是否确认删除所选中的数据项?').then(function () {
        return del(olderId);
      }).then(() => {
        this.$modal.msgSuccess('删除成功')
        this.$tab.refreshPage();
      }).catch(() => {
      });
    },


    lookgg(row) {
      this.reset()
      getoldmsg(row.olderId).then(res => {
        this.form = res.data[0];
        this.form.cardType = selectDictLabels(this.dict.type.sys_certificate, this.form.cardType)
        this.form.ownerRelation = selectDictLabels(this.dict.type.sys_oldman_huzhu, this.form.ownerRelation.toString())
        this.form.relation = selectDictLabels(this.dict.type.sys_oldman_gx, this.form.relation.toString())
        this.form.contactGender = selectDictLabels(this.dict.type.sys_user_sex, this.form.contactGender.toString())

        this.olderList1 = res.data[1];
      })
      this.view = 2
    },

    lookgg1(row) {
      see1(row.examinerId).then(res => {
        this.see2List = res.data;

      })
      this.see2 = true;
    },


    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.olderId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs['form'].validate(valid => {
        if (valid) {
          if (this.form.olderId != null) {
            this.$modal.msgSuccess('修改')
            // updateTask(this.form).then(response => {
            //   this.$modal.msgSuccess('修改成功')
            //   this.open = false
            //   this.getList()
            //   this.$tab.refreshPage();
            // })
          } else {
            addoldfile(this.form).then(response => {
              this.$modal.msgSuccess('新增成功')
              this.open = false
              this.$tab.refreshPage();
            })
          }
        }
      })
    },

  }
}

</script>

<style scoped>
.weizhi80 {
  width: 45%;
}
</style>

<template>
  <div class="app-container">
    <!--        搜索        -->
    <div v-show="openView === 0">
      <!--   右侧   -->
      <el-form
        :model="queryParams"
        ref="queryForm"
        size="small"
        :inline="true"
        v-show="showSearch"
        label-width="100px"
      >
        <el-form-item label="所属小区" prop="communityId">
          <el-select
            v-model="queryParams.communityId"
            placeholder="请选择所属小区"
            style="width: 150px"
          >
            <el-option
              v-for="community in allCommunityList"
              :key="community.communityId"
              :label="community.communityName"
              :value="community.communityId"
            />
          </el-select>
        </el-form-item>

        <el-form-item label="物品名称" prop="name" label-width="95px">
          <el-input
            v-model="queryParams.name"
            placeholder="请输入物品名称"
            style="width: 150px"
          />
          <el-form-item label="物品类型" prop="assetType">
            <el-select
              v-model="queryParams.assetType"
              placeholder="请选择物品类型"
              style="width: 150px"
            >
              <el-option
                v-for="(item, index) in assetTypeOptions"
                :key="index"
                :label="item.name"
                :value="item.id"
              />
            </el-select>
          </el-form-item>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="handleQuery"
            >搜索</el-button
          >
          <el-button
            icon="el-icon-refresh"
            size="mini"
            float="right"
            @click="resetQuery"
            >重置</el-button
          >
        </el-form-item>
      </el-form>
      <el-row style="margin-bottom: 10px">
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
      </el-row>

      <!--        表单        -->
      <el-table
        v-loading="loading"
        :data="manageList"
        @selection-change="handleSelectionChange"
        style="width: 100%"
      >
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="所属小区" align="center" prop="communityId">
          <template slot-scope="scope">
            {{ getCommunityName(scope.row.communityId) }}
          </template>
        </el-table-column>
        <el-table-column label="编号" align="center" prop="no" />
        <el-table-column label="物品类型" align="center" prop="assetType">
          <template slot-scope="scope">
            {{ getAssetTypeName(scope.row.assetType) }}
          </template>
        </el-table-column>
        <el-table-column label="物品名称" align="center" prop="name" />
        <el-table-column label="品牌" align="center" prop="specifications" />
        <el-table-column label="采购单价" align="center" prop="price" />
        <el-table-column label="采购人" align="center" prop="purchaser" />
        <el-table-column label="库存量" align="center" prop="number" />

        <el-table-column label="操作" align="center">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleRecordAdd(scope.row)"
              >物品领用
            </el-button>
            <!-- <br> -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handlePhView(scope.row)"
              >补货
            </el-button>
            <!-- <br> -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleGuiView(scope.row)"
              >归还
            </el-button>
            <!-- <br> -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-view"
              @click="handleView(scope.row)"
              >查看
            </el-button>
            <!-- <br> -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleEdit(scope.row)"
              >编辑
            </el-button>
            <!-- <br> -->
            <el-button
              size="mini"
              type="text"
              icon="el-icon-delete"
              style="color: #f56c6c"
              @click="handleDelete(scope.row)"
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
    <!--    新增页面  -->
    <div v-show="openView === 1">
      <el-form ref="form1" :model="form1" :rules="rules" label-width="100px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="所属小区" prop="communityId">
              <el-select
                v-model="form1.communityId"
                placeholder="请选择所属小区"
                style="width: 252px"
              >
                <el-option
                  v-for="community in allCommunityList"
                  :key="community.communityId"
                  :label="community.communityName"
                  :value="community.communityId"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="编号：" prop="no">
              <el-input
                v-model="form1.no"
                maxlength="20"
                placeholder="请输入编号"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="物品类型" prop="assetType">
              <el-select v-model="form1.assetType" placeholder="请选择物品类型">
                <el-option
                  v-for="(item, index) in assetTypeOptions"
                  :key="index"
                  :label="item.name"
                  :value="item.id"
                />
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="物品名称：" prop="name">
              <el-input
                v-model="form1.name"
                maxlength="20"
                placeholder="请输入物品名称"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="物品品牌：" prop="specifications">
              <el-input
                v-model="form1.specifications"
                maxlength="20"
                placeholder="请输入型号规格"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="采购单价：" prop="price">
              <el-input-number
                style="width: 220px"
                v-model="form1.price"
                :min="0"
                :max="99999"
                :precision="2"
                :step="0.1"
              ></el-input-number>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="库存数量：" prop="number">
              <el-input-number
                style="width: 254px"
                :min="0"
                v-model="form1.number"
                maxlength="20"
                placeholder="请输入物品数量"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="24">
            <el-form-item label="备注" prop="briefIntroduction">
              <el-input
                type="textarea"
                v-model="form1.briefIntroduction"
                :min-height="192"
                class="position"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <div style="float: right">
          <el-button type="primary" size="middle" @click="submitForm"
            >确定</el-button
          >
          <el-button size="middle" @click="before">返回</el-button>
        </div>
      </el-form>
    </div>
    <!--    查看详情页面  -->
    <div v-show="openView === 2">
      <el-tabs v-model="activeName">
        <el-tab-pane label="物品信息" name="first">
          <el-button size="middle" @click="before" style="float: right"
            >关 闭</el-button
          >
          <h4 class="form-header" style="color: #0558e7">物品基本信息</h4>
          <el-form ref="form" :model="form" label-width="180px">
            <!-- <el-row>
                  <el-col :span="5">
                    <el-form-item>
                      <img :src="form.assetImage" class="image"/>
                    </el-form-item>
                  </el-col>
                  <el-col :span="5" >
                    <el-form-item>
                      <img :src="form.qrCodeBase64" class="image"/>
                    </el-form-item>
                  </el-col>
                  <el-col :span="10" style="color: #0b57d0;margin-top: 10%;margin-left: 16%;">
                    <a @click="downLoadImg(form.qrCodeBase64,form.name)" >下载</a>
                  </el-col>
                </el-row> -->
            <el-row>
              <el-col :span="8">
                <el-form-item label="编号：">
                  {{ form.no }}
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="物品名称：">
                  {{ form.name }}
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="物品类型：">
                  {{ getAssetTypeName(form.assetType) }}
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="库存数量：">
                  {{ form.number }}
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="物品品牌：">
                  {{ form.specifications }}
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="采购单价：">
                  {{ form.price }}
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item label="采购人：">
                  {{ form.purchaser }}
                </el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="24">
                <el-form-item label="备注：">
                  {{ form.briefIntroduction }}
                </el-form-item>
              </el-col>
            </el-row>
          </el-form>
        </el-tab-pane>
        <el-tab-pane label="借用物品人员信息" name="second">
          <el-button size="middle" @click="before" style="float: right"
            >关 闭</el-button
          >
          <h4 class="form-header" style="color: #0558e7">人员明细</h4>
          <div>
            <el-form
              :model="queryParams"
              ref="queryForm"
              size="small"
              :inline="true"
              v-show="showSearch"
              label-width="100px"
            >
              <el-form-item label="姓名" prop="name" label-width="95px">
                <el-input
                  v-model="queryParams.name"
                  placeholder="请输入姓名"
                  style="width: 215px"
                  clearable
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item label="联系电话" prop="phone" label-width="95px">
                <el-input
                  v-model="queryParams.phone"
                  placeholder="请输入联系电话"
                  style="width: 215px"
                  clearable
                  @keyup.enter.native="handleQuery"
                />
              </el-form-item>
              <el-form-item>
                <el-button
                  type="primary"
                  icon="el-icon-search"
                  size="mini"
                  @click="handleQuery2"
                  >搜索</el-button
                >
                <el-button
                  icon="el-icon-refresh"
                  size="mini"
                  float="right"
                  @click="resetQuery2"
                  >重置</el-button
                >
              </el-form-item>
            </el-form>
            <el-table v-loading="loading" :data="joinPeopleList">
              <el-table-column
                label="领用人姓名"
                align="center"
                prop="name"
                width="120px"
                show-overflow-tooltip
              />
              <el-table-column
                label="联系方式"
                align="center"
                prop="phone"
                width="120px"
              />
              <el-table-column
                label="领用时间"
                align="center"
                prop="createTime"
                width="150px"
              />
              <el-table-column
                label="归还时间"
                align="center"
                prop="returnTime"
                width="150px"
              />
              <el-table-column
                label="归还状态"
                align="center"
                prop="status"
                width="80px"
              >
                <template slot-scope="scope">
                  {{ scope.row.status == "0" ? "未归还" : "归还" }}
                </template>
              </el-table-column>
              <el-table-column label="备注" align="center" prop="remarks" />
              <el-table-column label="操作" align="center" prop="remarks">
                <template slot-scope="scope">
                  <el-button
                    v-if="scope.row.status == '0'"
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="handleRecordUpdate(scope.row)"
                    >归还
                  </el-button>
                </template>
              </el-table-column>
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
    <!--    物品领用新增页面  -->
    <div v-show="openView === 3">
      <el-form ref="form2" :model="form2" :rules="rules2" label-width="180px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="物品类型：" prop="assetType">
              {{ getAssetTypeName(form2.assetType) }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="物品名称：" prop="assetName">
              {{ form2.assetName }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="资产规格：" prop="specifications">
              {{ form2.specifications }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="姓名：" prop="name">
              <el-input
                v-model="form2.name"
                maxlength="20"
                placeholder="请输入姓名"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="联系方式：" prop="phone">
              <el-input
                v-model="form2.phone"
                maxlength="11"
                placeholder="请输入联系方式"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="借用物品数量：" prop="quantityUsed">
              <el-input-number
                :min="0"
                v-model="form2.quantityUsed"
                maxlength="20"
                placeholder="请输入物品数量"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="归还时间：" prop="returnTime">
              <el-date-picker
                v-model="form2.returnTime"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="选择日期"
                style="width: 100%"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="领用说明：" prop="remarks">
            <el-input
              type="textarea"
              v-model="form2.remarks"
              :min-height="192"
              class="position"
            />
          </el-form-item>
        </el-row>
        <div style="float: right">
          <el-button type="primary" size="middle" @click="recordSubmitForm"
            >确定</el-button
          >
          <el-button size="middle" @click="recordBefore">返回</el-button>
        </div>
      </el-form>
    </div>
    <!--    物品补货新增页面  -->
    <div v-show="openView === 4">
      <el-form ref="form3" :model="form3" :rules="rules3" label-width="180px">
        <el-row>
          <el-col :span="8">
            <el-form-item label="资产编号：" prop="no">
              {{ form3.no }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="物品类型：" prop="assetType">
              {{ getAssetTypeName(form3.assetType) }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="物品名称：" prop="assetName">
              {{ form3.assetName }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="资产规格：" prop="specifications">
              {{ form3.specifications }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="采购单价：" prop="price">
              <el-input-number
                v-model="form3.price"
                :min="0"
                :max="99999"
                :precision="2"
                :step="0.1"
              ></el-input-number>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="库存数量：" prop="number">
              <el-input-number
                :min="0"
                v-model="form3.number"
                maxlength="20"
                placeholder="请输入物品数量"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="采购人：" prop="purchaser">
              <el-input
                v-model="form3.purchaser"
                maxlength="11"
                placeholder="请输入采购人"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="备注：" prop="remarks">
            <el-input
              type="textarea"
              v-model="form3.remarks"
              :min-height="192"
              class="position"
            />
          </el-form-item>
        </el-row>
        <div style="float: right">
          <el-button type="primary" size="middle" @click="recordSubmitForm3"
            >确定</el-button
          >
          <el-button size="middle" @click="recordBefore">返回</el-button>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import {
  addProperty,
  listProperty,
  getPropertyInfo,
  updateProperty,
  delProperty,
  joinPeopleListManage,
  addRecordProperty,
  updateStatus,
  propertyProcurementRecordAdd,
} from "@/api/estate/property/property";
import ImageUpload from "@/components/ImageUpload";
import { parseTime } from "@/utils/global";
import { uploadImageUrl } from "@/api/estate/customer/goods/goods";
import { equals } from "@/utils/common";
import { getBuildingList, getAllCommunityList } from "@/api/estate/community";
import { assetTypeList } from "@/api/estate/property/propertyType";
export default {
  name: "wastingAsset",
  components: { ImageUpload },
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
      filterText: "",
      //小区数据集合
      currentList: [],
      //树结构
      currentListProps: {
        children: "communityId",
        label: "communityName",
      },
      joinform: {},
      distributionLists: [
        {
          supplyNum: "",
          supplyName: "",
        },
      ],
      form: {},
      form3: {
        id: undefined,
        name: undefined,
        phone: undefined,
        quantityUsed: 0,
        remarks: undefined,
        status: "0",
        returnTime: undefined,
      },
      form1: {
        buildingIds: [],
        actRang: undefined,
      },
      form2: {
        id: undefined,
        name: undefined,
        phone: undefined,
        quantityUsed: 0,
        remarks: undefined,
        status: "0",
        returnTime: undefined,
      },
      manageList: [],
      dateRange: [],
      actRangList: [],
      joinPeopleList: [],
      activityName: "",
      comOptions: [],
      communtiyList: [],
      buildingList: [],
      supplyList: [
        {
          supplyName: "",
          supplyNum: "",
        },
      ],
      defaultProps: {
        children: "communityId",
        label: "communityName",
      },
      oilStation: [],
      record: false,
      open: false,
      title: "",
      single: true,
      multiple: true,
      projectId: 10,
      communityId: null,
      activityId: null,
      allCommunityList: [],
      propertyType: "1",
      assetTypeOptions: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        activityName: null,
        typeId: null,
        typeName: null,
        createBy: null,
        actState: null,
        startTime: null,
        signState: null,
        ownerPhone: null,
        ownerName: null,
      },
      activeName: "first",
      rules2: {
        name: [{ required: true, message: "姓名不能为空", trigger: "blur" }],
        phone: [
          { required: true, message: "联系方式不能为空", trigger: "blur" },
        ],
        quantityUsed: [
          { required: true, message: "物品借用数量不能为空", trigger: "blur" },
        ],
        returnTime: [
          { required: true, message: "物品归还时间不能为空", trigger: "blur" },
        ],
      },
      rules3: {
        price: [
          { required: true, message: "购入单价不能为空", trigger: "blur" },
        ],
        number: [
          { required: true, message: "库存数量不能为空", trigger: "blur" },
        ],
        purchaser: [
          { required: true, message: "采购人不能为空", trigger: "blur" },
        ],
      },
      // 表单校验
      rules: {
        communityId: [
          { required: true, message: "请选择所属小区", trigger: "trigger" },
        ],
        assetType: [
          { required: true, message: "请选择物品类型", trigger: "trigger" },
        ],
        name: [
          { required: true, message: "物品名称不能为空", trigger: "blur" },
        ],
        no: [{ required: true, message: "物品编号不能为空", trigger: "blur" }],
        price: [
          { required: true, message: "购入单价不能为空", trigger: "blur" },
        ],
      },
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        },
      },
    };
  },
  created() {
    this.openView = 0;
    this.getAssetTypeList();

    this.getAllCommunityList();
    this.getList();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
  methods: {
    getAssetTypeList() {
      assetTypeList(this.propertyType).then((response) => {
        this.assetTypeOptions = response.data;
      });
    },
    getAllCommunityList() {
      getAllCommunityList().then((response) => {
        this.allCommunityList = response.data;
      });
    },
    getCommunityName(communityId) {
      var obj = this.allCommunityList.filter(
        (item) => item.communityId == communityId
      )[0];
      if (obj != null) {
        return obj.communityName;
      }
    },
    getAssetTypeName(assetType) {
      if (assetType) {
        var obj = this.assetTypeOptions.filter(
          (item) => item.id == assetType
        )[0];
        if (obj != null) {
          return obj.name;
        }
      }
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
      this.queryParams.communityId = row.communityId;
      this.communityId = row.communityId;
      this.getList();
    },

    // 查询列表
    getList() {
      //固定资产
      this.queryParams.type = this.propertyType;
      listProperty(this.queryParams).then((response) => {
        this.manageList = response.rows;
        this.total = response.total;
        this.openView = 0;
      });
    },

    // 新增按钮
    handleAdd() {
      this.reset();
      this.openView = 1;
    },
    recordBefore() {
      this.openView = 0;
      this.getList();
    },
    recordSubmitForm3() {
      this.$refs["form3"].validate((valid) => {
        if (valid) {
          propertyProcurementRecordAdd(this.form3).then((response) => {
            this.$modal.msgSuccess("补货成功");
            this.form3 = {};
            this.getList();
            this.openView = 0;
          });
        }
      });
    },
    //出库
    recordSubmitForm() {
      this.form2.type = this.propertyType;
      this.$refs["form2"].validate((valid) => {
        if (valid) {
          addRecordProperty(this.form2).then((response) => {
            this.$modal.msgSuccess("领用成功");
            this.form2 = {};
            this.getList();
            this.openView = 0;
          });
        }
      });
    },
    handleRecordUpdate(row) {
      this.$modal
        .confirm("是否确认归还【" + row.name + "】借用的物品？")
        .then(function () {
          return updateStatus(row.id);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("归还成功");
        })
        .catch(() => {});
    },
    // 删除按钮
    handleDelete(row) {
      this.$modal
        .confirm("是否确认删除选中的数据项？")
        .then(function () {
          return delProperty(row.id);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    // 取消按钮列表
    returnbefore() {
      this.open = false;
    },
    // 返回
    before() {
      this.reset();
      this.openView = 0;
      this.getList();
    },
    /** 表单重置*/
    reset() {
      this.form1 = {
        name: undefined,
        number: 0,
        assetType: undefined,
        communityId: undefined,
        no: undefined,
        price: 0.0,
        specifications: undefined,
      };
      this.distributionLists = [
        {
          supplyNum: "",
          supplyName: "",
        },
      ];
    },
    // 搜索按钮
    handleQuery() {
      if (this.queryParams.startTime != null) {
        this.queryParams.startTime = parseTime(
          this.queryParams.startTime,
          "{y}-{m}-{d}"
        );
      }
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 重置按钮
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.name = null;
      this.queryParams.communityId = null;
      this.queryParams.assetType = null;
      this.handleQuery();
    },
    // 重置按钮2
    resetQuery2() {
      this.resetForm("queryForm");
      (this.queryParams.activityName = null),
        (this.queryParams.typeId = null),
        (this.queryParams.typeName = null),
        (this.queryParams.createBy = null),
        (this.queryParams.actState = null),
        (this.queryParams.startTime = null),
        (this.queryParams.createBy = null),
        (this.queryParams.signState = null),
        (this.queryParams.ownerPhone = null),
        (this.queryParams.ownerName = undefined),
        (this.actState = undefined),
        this.handleQuery2();
    },
    // 删除按钮
    handleDetele() {},
    // 图片上传
    handleUpload(data) {
      this.$forceUpdate("imgupload");
      this.form1.actUrl = data;
    },

    // 编辑按钮
    handleEdit(row) {
      this.reset();
      getPropertyInfo(row.id).then((resp) => {
        this.form1 = resp.data;
      });
      this.openView = 1;
    },
    handleRecordAdd(row) {
      this.form2 = {
        propertyId: row.id,
        assetType: row.assetType,
        assetName: row.name,
        specifications: row.specifications,
        number: row.number,
        queueMicrotask: 0,
      };
      this.openView = 3;
    },
    handlePhView(row) {
      this.form3 = {
        no: row.no,
        propertyId: row.id,
        type: this.propertyType,
        assetType: row.assetType,
        assetName: row.name,
        specifications: row.specifications,
        price: 0.0,
        number: 0,
        remarks: undefined,
      };
      this.openView = 4;
    },
    handleGuiView(row) {
      this.activeName = "second";
      this.view(row);
    },

    // 查看按钮
    handleView(row) {
      this.activeName = "first";
      this.view(row);
    },
    view(row) {
      this.openView = 2;
      getPropertyInfo(row.id).then((resp) => {
        this.form = resp.data;
      });
      this.queryParams.propertyId = row.id;
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
          this.form1.actUrl = res.imgPath;
          this.imageUrl = res.imgPath;
          this.$modal.msgSuccess("上传成功");
        });
      }
    },
    // 参与人员列表
    getJoin() {
      joinPeopleListManage(this.queryParams).then((response) => {
        this.joinPeopleList = response.rows;
        this.total = response.total;
      });
    },
    // 参与人员列表搜索
    handleQuery2() {
      this.queryParams.pageNum = 1;
      this.queryParams.activityId = this.activityId;
      this.getJoin();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.activityId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    // 提交按钮
    submitForm() {
      this.form1.type = this.propertyType;
      this.$refs["form1"].validate((valid) => {
        if (valid) {
          if (this.form1.id != null) {
            updateProperty(this.form1).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.getList();
              this.openView = 0;
            });
          } else {
            addProperty(this.form1).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.form1 = {};
              this.getList();
              this.openView = 0;
            });
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
              this.form1.buildingList.push(assessItem);
            }
          });
        }
      }
    },
  },
};
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
  width: 200px;
}

.el-form-item__error {
  width: 150px;
}

.position {
  width: 64%;
  z-index: 1;
}
</style>

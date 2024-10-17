<template>
  <div class="app-container">
    <div v-show="openView === 0">
      <el-row :gutter="20">
        <!-- 树结构     -->
        <el-col :span="4">
          <div class="head-container">
            <el-input
              v-model="filterText"
              placeholder="请输入小区名称"
              clearable
              size="small"
              prefix-icon="el-icon-search"
              style="margin-bottom: 20px"
            />
          </div>
          <div>
            <el-tree
              :data="comOptions"
              :props="defaultProps"
              :expand-on-click-node="false"
              :filter-node-method="filterNode"
              ref="tree"
              highlight-current
              node-key="communityId"
              @node-click="handleNodeClick"
            >
            </el-tree>
          </div>
        </el-col>

        <el-col :span="20" :xs="24">
          <el-form
            :model="queryParams"
            ref="queryForm"
            size="small"
            :inline="true"
            v-show="showSearch"
            label-width="110px"
          >
            <el-form-item label="房屋地址" prop="address">
              <el-input
                v-model="queryParams.address"
                placeholder="请输入房屋地址"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            <el-form-item label="发布人" prop="ownerName">
              <el-input
                v-model="queryParams.ownerName"
                placeholder="请输入发布人"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            <el-form-item label="业主联系电话" prop="ownerPhone">
              <el-input
                v-model="queryParams.ownerPhone"
                placeholder="请输入业主联系电话"
                clearable
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
          <div>
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              >新增</el-button
            >
          </div>

          <el-table v-loading="loading" :data="rentList">
            <el-table-column label="业主姓名" align="center" prop="ownerName">
              <template slot-scope="scope">
                {{ scope.row.ownerName == null ? "--" : scope.row.ownerName }}
              </template>
            </el-table-column>
            <el-table-column
              label="业主手机号"
              align="center"
              prop="ownerPhone"
            >
              <template slot-scope="scope">
                {{ scope.row.ownerPhone == null ? "--" : scope.row.ownerPhone }}
              </template>
            </el-table-column>
            <el-table-column label="证件号" align="center" prop="cardNo">
              <template slot-scope="scope">
                {{ scope.row.cardNo == null ? "--" : scope.row.cardNo }}
              </template>
            </el-table-column>
            <el-table-column
              label="所属楼栋"
              align="center"
              prop="buildingName"
            />
            <el-table-column label="所属单元" align="center" prop="unitName" />
            <el-table-column label="所属房间" align="center" prop="roomName" />
            <el-table-column
              label="地址"
              align="center"
              prop="address"
              :show-overflow-tooltip="true"
            />
            <el-table-column label="房屋面积" align="center" prop="totalArea" />
            <el-table-column
              label="发布时间"
              align="center"
              prop="publishTime"
            />
            <el-table-column label="状态" align="center" prop="rentStatus">
              <template slot-scope="scope">
                <div v-if="scope.row.rentStatus == '0'" type="success">
                  待出租
                </div>
                <div v-else-if="scope.row.rentStatus == '1'" type="整租">
                  已出租
                </div>
                <div v-else-if="scope.row.rentStatus == '2'" type="整租">
                  已下架
                </div>
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
                  @click="handleGoods(scope.row)"
                  >查看
                </el-button>
                <!-- <br v-if="scope.row.rentStatus!='1'"> -->

                <el-button
                  size="mini"
                  type="text"
                  style="color: #f56c6c"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row)"
                  v-if="scope.row.rentStatus != '1'"
                  >删除</el-button
                >
                <!-- <br /> -->
                <el-button
                  size="mini"
                  type="text"
                  style="color: #f56c6c"
                  icon="el-icon-delete"
                  @click="handleXJ(scope.row, '2')"
                  v-if="scope.row.rentStatus == '0'"
                  >下架</el-button
                >
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleXJ(scope.row, '0')"
                  v-if="scope.row.rentStatus == '2'"
                  >上架</el-button
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

    <!--  新增  -->
    <div v-show="openView === 2">
      <h4 class="form-header">所属业户信息</h4>
      <div style="display: flex">
        <el-form
          ref="form"
          :rules="rules"
          :disabled="isDisabled"
          :model="form"
          label-width="180px"
        >
          <el-row>
            <el-col :offset="0" :span="6">
              <el-form-item label="所属楼栋: " prop="ownBuilding">
                <el-select
                  v-model="form.ownBuilding"
                  clearable
                  placeholder="请选择楼栋"
                  @change="buildingChange(form.ownBuilding, '0')"
                  style="width: 180px"
                >
                  <el-option
                    v-for="item in buildingData"
                    :key="item.buildingId"
                    :label="item.buildingName"
                    :value="item.buildingId"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :offset="1" :span="6">
              <el-form-item label="所属单元: " prop="ownUnit">
                <el-select
                  v-model="form.ownUnit"
                  clearable
                  placeholder="请选择单元"
                  :disabled="isDisabled"
                  @change="unitChange(form.ownUnit, '0')"
                  style="width: 180px"
                >
                  <el-option
                    v-for="item in unitData"
                    :key="item.unitId"
                    :label="item.unitName"
                    :value="item.unitId"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :offset="1" :span="6">
              <el-form-item label="所属房号: " prop="roomNumber">
                <el-select
                  v-model="form.roomNumber"
                  clearable
                  placeholder="请选择房号"
                  :disabled="isDisabled"
                  @change="roomChange(form.roomNumber, '0')"
                  style="width: 180px"
                >
                  <el-option
                    v-for="item in roomData"
                    :key="item.roomId"
                    :label="item.roomName"
                    :value="item.roomId"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :offset="0" :span="6">
              <el-form-item label="业户姓名: " prop="ownerId">
                <el-select
                  v-model="form.ownerId"
                  clearable
                  placeholder="请选择业户"
                  @change="ownerChange(form.ownerId, '1')"
                  style="width: 180px"
                >
                  <el-option
                    v-for="item in ownerData"
                    :key="item.ownerId"
                    :label="item.ownerName"
                    :value="item.ownerId"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :offset="1" :span="6">
              <el-form-item label="联系方式: ">
                {{ ownerPhones }}
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="1">
              <el-form-item label="与户主关系：">
                <el-select
                  v-model="form.relationship"
                  clearable
                  placeholder="请选择与户主关系"
                  style="width: 180px"
                >
                  <el-option
                    v-for="d in dict.type.sys_oldman_huzhu"
                    :key="d.value"
                    :label="d.label"
                    :value="d.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6" :offset="0">
              <el-form-item label="房屋坐落：">
                {{ houseAddress }}
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="1">
              <el-form-item label="房屋面积：">
                {{ totalArea }}
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="1">
              <el-form-item label="房屋属性：">
                {{ getRoomTypeOptions(roomAttribute) }}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6" :offset="0">
              <el-form-item label="所在层：">
                {{ floor }}
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="1">
              <el-form-item label="联系人：" prop="contactName">
                <el-input
                  style="width: 180px"
                  v-model="form.contactName"
                  placeholder="请输入联系人"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="1">
              <el-form-item label="联系人手机号：" prop="contactPhone">
                <el-input
                  style="width: 180px"
                  v-model="form.contactPhone"
                  placeholder="请输入联系人手机号"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="20" :offset="0">
              <el-form-item label="房产证明：" prop="realEstate">
                <image-upload
                  :isShowTip="!isDisabled"
                  v-model="form.realEstate"
                  :limit="3"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="10" :offset="0">
              <el-form-item label="身份证正面：" prop="frontIdCard">
                <image-upload
                  :isShowTip="!isDisabled"
                  v-model="form.frontIdCard"
                  :limit="1"
                />
              </el-form-item>
            </el-col>
            <el-col :span="10" :offset="1">
              <el-form-item label="身份证反面：" prop="backIdCard">
                <image-upload
                  :isShowTip="!isDisabled"
                  v-model="form.backIdCard"
                  :limit="1"
                />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <el-row>
        <el-col :offset="20">
          <el-button v-if="!isDisabled" type="primary" @click="submit"
            >确 定</el-button
          >
          <el-button @click="cancel">取 消</el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import {
  getCurrentList,
  getBuildingList,
  getOwnerList,
  getRoomList,
  getUnitList,
} from "@/api/estate/community";
import {
  deleteRent,
  getRentList,
  updateByRentIdShelve,
  insertMyRental,
  getMyRental,
} from "@/api/estate/customer/rent/rent";
import { selectOwnerName } from "@/api/estate/center/buildingManage/vehicleManage/vehicleManage";
import { getRoomDetail } from "@/api/estate/center/room";
export default {
  name: "rent",
  dicts: ["property_of_house", "sys_oldman_huzhu"],
  data() {
    return {
      // 树筛选文本
      filterText: "",
      openView: false,
      isDisabled: false,
      // 楼栋下拉框
      buildingData: [],
      // 单元下拉框
      unitData: [],
      // 房号下拉框
      roomData: [],
      ownerData: [],
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
      // 表格数据
      rentList: [],
      houseAddress: "",
      totalArea: "",
      roomAttribute: "",
      floor: "",
      //树结构
      comOptions: [],
      defaultProps: {
        children: "communityId",
        label: "communityName",
      },
      roomTypeOptions: [
        // { "value": "1", label: "安置房" },
        // { "value": "2", label: "商品房" },
        // {"value":"3",label:"公租房"},
      ],
      rules: {
        ownBuilding: [
          { required: true, message: "请选择所属楼栋", trigger: "blur" },
        ],
        ownUnit: [
          { required: true, message: "请选择所属单元", trigger: "blur" },
        ],
        roomNumber: [
          { required: true, message: "请选择所属房号", trigger: "blur" },
        ],
        ownerId: [{ required: false, message: "请选择业户", trigger: "blur" }],
        contactName: [
          { required: true, message: "请输入联系人", trigger: "blur" },
        ],
        contactPhone: [
          { required: true, message: "请输入联系人手机号", trigger: "blur" },
        ],
        realEstate: [
          { required: true, message: "请上传房产证明", trigger: "blur" },
        ],
        frontIdCard: [
          { required: true, message: "请上传身份证正面", trigger: "blur" },
        ],
        backIdCard: [
          { required: true, message: "请上传身份证反面", trigger: "blur" },
        ],
      },
      communityId: null,
      ownerPhones: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: null,
        rentType: null,
        address: null,
        ownerName: null,
        ownerPhone: null,
      },
      // 表单参数
      form: {},
    };
  },
  created() {
    this.getTreeSelect();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
  methods: {
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.communityId = data.communityId;
      // this.handleQuery();
      this.communityId = data.communityId;
      this.selectFloor();
      this.getList();
    },
    getRoomTypeOptions(val) {
      return this.selectDictLabel(this.dict.type.property_of_house, val);
    },
    /** 树结构 */
    getTreeSelect() {
      getCurrentList().then((response) => {
        this.comOptions = response.data;
        this.queryParams.communityId = this.comOptions[0].communityId;
        this.communityId = this.comOptions[0].communityId;

        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.comOptions[0].communityId);
        });
        this.selectFloor();
        this.getList();
      });
    },
    // 取消按钮
    cancel() {
      this.openView = 0;
    },
    submit() {
      this.$refs["form"].validate((valid) => {
        if (!this.form.ownerId) {
          this.$modal.msgWarning("请选择业户姓名");
          return;
        }
        var obj = {
          rentId: this.form.rentId,
          communityId: this.queryParams.communityId,
          accountId: this.form.accountId,
          ownBuilding: this.form.ownBuilding,
          ownUnit: this.form.ownUnit,
          roomNumber: this.form.roomNumber,
          relationship: this.form.relationship,
          realEstate: this.form.realEstate,
          frontIdCard: this.form.frontIdCard,
          backIdCard: this.form.backIdCard,
          contactName: this.form.contactName,
          contactPhone: this.form.contactPhone,
        };
        if (valid) {
          if (this.form.rantId) {
            //修改
          } else {
            //添加
            insertMyRental(obj).then((res) => {
              this.$modal.msgSuccess("新增成功");
              this.form = {};
              this.getList();
              this.openView = 0;
            });
          }
        }
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
      // this.getTreeSelect();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 查询列表 */
    getList() {
      this.openView = 0;
      this.loading = false;
      getRentList(this.queryParams).then((response) => {
        this.rentList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /**新增 */
    handleAdd() {
      this.openView = 2;
      this.isDisabled = false;
      this.form = {};
      this.ownerPhones = "";
      this.houseAddress = "";
      this.totalArea = "";
      this.roomAttribute = "";
      this.floor = "";
    },
    /** 查看详情*/
    handleGoods(row) {
      this.openView = 2;
      this.isDisabled = true;
      this.ownerPhones = "";
      this.houseAddress = "";
      this.totalArea = "";
      this.roomAttribute = "";
      this.floor = "";
      this.form = {};
      getMyRental(row.rentId).then((res) => {
        this.form = res.data;
        this.form.ownBuilding = parseFloat(this.form.ownBuilding);
        this.buildingChange(this.form.ownBuilding, "1");
        this.form.ownUnit = parseFloat(this.form.ownUnit);
        this.unitChange(this.form.ownUnit, "1");
        this.form.roomNumber = parseFloat(this.form.roomNumber);
        this.roomChange(this.form.roomNumber, "1");
        this.form.ownerId = parseFloat(this.form.ownerId);
        this.ownerChange(this.form.ownerId, "1");
      });
    },
    // 根据小区ID查楼栋
    selectFloor() {
      getBuildingList(this.communityId).then((response) => {
        this.buildingData = response.data;
      });
    },

    // 根据楼栋查单元
    buildingChange(ownBuilding, flag) {
      if (ownBuilding != "") {
        if (flag == "0") {
          // 变化时先清空
          this.form.ownUnit = null;
          this.form.roomNumber = null;
          this.form.ownerId = "";
          this.ownerPhones = null;
        }

        // 给addForm赋值
        this.form.ownBuilding = ownBuilding;
        getUnitList(ownBuilding).then((response) => {
          this.unitData = response.data;
        });
      } else {
        this.form.ownBuilding = null;
        this.form.ownUnit = null;
        this.form.roomNumber = null;
        this.form.ownerId = "";
        this.ownerPhones = null;
      }
    },

    // 根据单元查房号
    unitChange(ownUnit, flag) {
      if (ownUnit != "") {
        if (flag == "0") {
          // 先清空
          this.form.roomNumber = null;
          this.form.ownerId = "";
          this.ownerPhones = null;
        }
        // 给addForm赋值
        this.form.ownUnit = ownUnit;
        getRoomList(ownUnit).then((response) => {
          this.roomData = response.data;
        });
      } else {
        this.form.ownUnit = null;
        this.form.roomNumber = null;
        this.form.ownerId = "";
        this.ownerPhones = null;
      }

      this.$forceUpdate();
    },

    // 根据房号查业户姓名
    roomChange(roomNumber, flag) {
      if (roomNumber != "") {
        if (flag == "0") {
          // 先清空
          this.form.ownerId = "";
          this.ownerPhones = null;
        }
        // 给addForm赋值
        //this.form.roomNumber = roomNumber;
        let params = {
          roomId: roomNumber,
        };
        getOwnerList(params).then((response) => {
          this.ownerData = response.data;
        });
        this.houseAddress = "";
        this.totalArea = "";
        this.roomAttribute = "";
        this.floor = "";
        getRoomDetail(roomNumber, this.queryParams.communityId).then(
          (response) => {
            this.houseAddress = response.data.houseAddress;
            this.totalArea = response.data.totalArea;
            this.roomAttribute = response.data.roomAttribute;
            this.floor = response.data.currentFloor;
          }
        );
      } else {
        this.form.ownerId = "";
        this.ownerPhones = null;
      }
      this.$forceUpdate();
    },

    // 改变业户姓名时
    ownerChange(ownerId, flag) {
      this.$refs["form"].validateField("ownerId", (errorMessage) => {
        let valid = errorMessage == "" ? true : false;
      });

      if (ownerId != "") {
        // 先清空
        this.ownerPhones = null;
        //查找业主手机号
        this.selectOwnerName(ownerId);
      } else {
        this.ownerPhones = null;
      }
    },

    //查找业主手机号
    selectOwnerName(ownerId) {
      // 查联系方式
      let params = {
        ownerId: ownerId,
      };
      selectOwnerName(params).then((response) => {
        this.ownerPhones = response.data.loginPhone;
        this.form.loginPhone = response.data.loginPhone;
        this.form.accountId = response.data.accountId;
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$modal
        .confirm("是否确认删除选中的数据项？")
        .then(function () {
          return deleteRent(row.rentId);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    // 下架
    handleXJ(row, rentStatus) {
      var text = "上架";
      if (rentStatus == "2") {
        text = "下架";
      }
      this.$modal
        .confirm("是否确认" + text + "选中的数据项？")
        .then(function () {
          return updateByRentIdShelve(row.rentId, rentStatus);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess(text + "成功");
        })
        .catch(() => {});
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
.content {
  font-size: 14px;
}
.thumbnail {
  height: 100px;
  width: 200px;
}
</style>

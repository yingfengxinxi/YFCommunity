<template>
  <div class="app-container">
    <div v-show="homeShow">
      <el-row :gutter="20">
        <!--部门数据-->
        <el-col :span="4" :xs="24">
          <div class="head-container">
            <el-input
              placeholder="请输入小区名称"
              clearable
              size="small"
              v-model="search"
              prefix-icon="el-icon-search"
              style="margin-bottom: 20px"
            />
          </div>
          <el-table
            ref="singleTable"
            type="index"
            highlight-current-row
            :data="viewDeptData"
            :show-header="false"
            @cell-click="listClick"
          >
            <el-table-column prop="communityName"/>
          </el-table>
        </el-col>

        <el-col :span="20" :xs="24">
          <!-- 条件搜索 -->
          <el-form
            :model="queryParams"
            ref="queryForm"
            size="small"
            :inline="true"
          >
            <el-form-item label="巡更路线名称" prop="routeName">
              <el-input
                v-model="queryParams.routeName"
                placeholder="请输入巡更路线名称"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="巡点数量" prop="pointNumber">
              <el-input
                v-model="queryParams.pointNumber"
                placeholder="请输入巡点数量"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="创建人" prop="createBy">
              <el-input
                v-model="queryParams.createBy"
                placeholder="请输入创建人名称"
                @keyup.enter.native="handleQuery" F
              />
            </el-form-item>
            <el-form-item label="巡更路线状态" prop="routeStatus">
              <el-select
                v-model="queryParams.routeStatus"
                placeholder="请选择巡更路线状态"
              >
                <el-option label="开启" value="0"/>
                <el-option label="关闭" value="1"/>
              </el-select>
            </el-form-item>
            <!--            <el-form-item label="车位属性" prop="carportNature">-->
            <!--              <el-select-->
            <!--                v-model="queryParams.carportNature"-->
            <!--                placeholder="请选择车位类型"-->
            <!--              >-->
            <!--                <el-option label="人防" value="0" />-->
            <!--                <el-option label="非人防" value="1" />-->
            <!--              </el-select>-->
            <!--            </el-form-item>-->
            <el-form-item>
              <el-button
                type="primary"
                icon="el-icon-search"
                size="mini"
                @click="handleQuery"
              >搜索
              </el-button
              >
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
              >重置
              </el-button
              >
            </el-form-item>
          </el-form>

          <div>
            <!-- 权限 v-hasPermi="['estate:carport:remove']" -->
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              v-hasPermi="['estate:route:add']"
              @click="addMethod"
            >新增
            </el-button
            >
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              v-hasPermi="['estate:route:remove']"
              @click="handleDelete"
            >删除
            </el-button
            >
          </div>

          <!-- 列表展示 -->
          <el-table
            stripe
            v-loading="loading"
            :data="pointList"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55" align="center" :selectable="selectEnable"/>
            <el-table-column
              label="巡更路线编号"
              align="center"
              prop="routeId"
              type="number"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="巡更路线名称"
              align="center"
              prop="routeName"
              :show-overflow-tooltip="true"
            >
            </el-table-column>
            <el-table-column
              label="巡点数量"
              align="center"
              prop="pointNumber"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="操作人"
              align="center"
              prop="updateBy"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="最后操作时间"
              align="center"
              prop="updateTime"
              width="200"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="状态"
              align="center"
              prop="routeStatus"
              :show-overflow-tooltip="true"
            >
              <template slot-scope="scope">
                <el-switch
                  v-model="scope.row.routeStatus"
                  active-value="0"
                  inactive-value="1"
                  @change="handleStatusChange(scope.row)"
                ></el-switch>
              </template>
            </el-table-column>


            <el-table-column
              label="操作"
              align="center"
              fixed="right"
              width="300"
              class-name="small-padding fixed-width"
            >
              <template slot-scope="scope">
                <el-button size="mini" type="text"  v-hasPermi="['estate:route:query']" @click="detail(scope.row)"
                >
                  <el-link icon="el-icon-view" type="brand" :underline="false"
                  >详情
                  </el-link
                  >
                </el-button
                >
                <el-button
                  size="mini"
                  type="text"
                  v-hasPermi="['estate:route:edit']"
                  @click="update(scope.row)"
                >
                  <el-link
                    icon="el-icon-edit"
                    type="success"
                    :underline="false"
                  >修改
                  </el-link
                  >
                </el-button
                >


                <el-button
                  size="mini"
                  type="text"
                  v-hasPermi="['estate:route:export']"
                  @click="downLoadImg(scope.row)"
                >
                  <el-link
                    icon="el-icon-edit"
                    type="success"
                    :underline="false"
                  >导出二维码
                  </el-link
                  >
                </el-button>

                <el-button
                  size="mini"
                  type="text"
                  v-hasPermi="['estate:route:remove']"
                  @click="remove(scope.row)"
                  v-if="scope.row.routeStatus === '1'"
                >
                  <el-link
                    icon="el-icon-refresh"
                    type="danger"
                    :underline="false"
                  >删除
                  </el-link
                  >
                </el-button
                >
              </template>
            </el-table-column>
          </el-table>

          <pagination
            v-show="total > 0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getBlackList"
          />
        </el-col>
      </el-row>
    </div>

    <!-- 新增页面 -->
    <el-dialog :visible.sync="addOpen" @close="addClose" :title="title" center>
      <div>
        <el-form
          ref="addForm"
          :rules="addRules"
          :model="addForm"
          label-width="150px"
        >
          <el-row>
            <el-col :offset="3" :span="8">
              <!-- 小区下拉框 -->
            </el-col>
          </el-row>

          <el-row>
            <el-col :offset="2" :span="8">
              <el-form-item label="巡更路线名称: " prop="routeName" style="width: 150%">
                <el-input
                  clearable
                  v-model="addForm.routeName"
                  placeholder="请输巡更路线名称"
                ></el-input>

              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :offset="2" :span="8">
              <el-form-item label="巡点选择: " prop="pointIdList">
                <el-select v-model="addForm.pointIdList" multiple placeholder="请选择巡点" style="width: 200%" clearable
                           @clear="removeTags"
                           @change="selectPoint"
                           @remove-tag="removeTag"
                           @visible-change="visChange"
                           :disabled="disab">
                  <el-option
                    v-for="(item, index) in pointNameList"
                    :key="index"
                    :label="item.pointName"
                    :value="item.pointId"
                  ></el-option>
                </el-select>

              </el-form-item>
            </el-col>
          </el-row>


          <!--          <el-table-->
          <!--            stripe-->
          <!--            v-loading="loading"-->
          <!--            :data="List"-->
          <!--          >-->
          <!--            <el-table type="selection" width="50" align="center" />-->
          <!--            <el-table-column-->
          <!--              label="巡更路线编号"-->
          <!--              align="center"-->
          <!--              prop="routeId"-->
          <!--              type="number"-->
          <!--              :show-overflow-tooltip="true"-->
          <!--            />-->
          <!--            <el-table-column-->
          <!--              label="巡点名称"-->
          <!--              align="center"-->
          <!--              prop="pointName"-->
          <!--              :show-overflow-tooltip="true"-->
          <!--            >-->
          <!--            </el-table-column>-->
          <!--            <el-table-column-->
          <!--              label="巡点地址"-->
          <!--              align="center"-->
          <!--              prop="pointSite"-->
          <!--              :show-overflow-tooltip="true"-->
          <!--            />-->


          <!---->
          <!--          </el-table>-->


          <div class="tab-add">
            <el-table :data="List"
                      border
                      row-key="routeId"
                      align="left"
            >
              <el-table-column v-for="(item, index) in col"
                               :key="`col_${index}`"
                               :prop="dropCol[index].prop"
                               :label="item.label"
              >
              </el-table-column>
              <el-table-column v-for="(item, index) in col1"
                               :key="`col1_${index}`"
                               :prop="dropCol1[index].prop"
                               :label="item.label"
                               align="center"
                               width="130">
                <span class="el-icon-sort"></span>
              </el-table-column>

            </el-table>
          </div>

          <!--          <pre style="text-align: left">-->
          <!--      {{dropCol}}-->
          <!--    </pre>-->
          <!--          <hr>-->
          <!--          <pre style="text-align: left">-->
          <!--      {{List}}-->
          <!--    </pre>-->


          <el-row>
            <el-col :offset="2" :span="8">
              <el-form-item label="路线状态:" style="width: 1000px" label-width="145px" prop="routeStatus">

                <el-switch
                  v-model="addForm.routeStatus"
                  active-value="0"
                  inactive-value="1"
                  active-text="启用巡更路线"
                  inactive-text="停用巡更路线"
                >
                </el-switch>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :offset="10" :span="2">
              <div style="width: 280px">
                <el-button type="primary" @click="addSuccess(addForm)"
                >确定
                </el-button
                >
                <el-button @click="addClose">取消</el-button>
              </div>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-dialog>

    <!-- 详情页面 -->
    <div v-show="showDetail">
      <el-form ref="form" :model="detailForm" label-width="180px">
        <h5><b>巡更路线详情</b></h5>
        <el-divider></el-divider>

        <!-- 第一行 -->
        <el-row>
          <el-col :offset="0" :span="4">
            <el-form-item label="巡更路线编号: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.routeId }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-form-item label="巡更路线名称: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.routeName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-form-item label="巡点数量: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.pointNumber }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第二行 -->
        <el-row>
          <el-col :offset="0" :span="4">
            <el-form-item label="巡点详情: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.pointName }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>


        <!-- 所属业户信息 -->
        <div>
          <h5><b>巡点详情</b></h5>
        </div>
        <el-divider></el-divider>
        <el-row>
          <el-col :span="20">
            <el-table
              stripe
              type="index"
              highlight-current-row
              :data="ownerListData"
            >
              <el-table-column
                label="巡点名称"
                align="center"
                :show-overflow-tooltip="true"
                prop="pointName"
              />
              <el-table-column
                label="巡点二维码"
                align="center"
                :show-overflow-tooltip="true"
                prop="sss"
              >
                <template slot-scope="scope">
                  <el-image
                    style="width: 80px; height: 50px"
                    :src="scope.row.pointUrl"
                    fit="scale-down"></el-image>
                </template>
              </el-table-column>
              <el-table-column
                label="巡点地址"
                align="center"
                :show-overflow-tooltip="true"
                prop="pointSite"
              ></el-table-column>
            </el-table>
<!--            <pagination
              v-show="total > 0"
              :total="total"
              :page.sync="queryParams.pageNum"
              :limit.sync="queryParams.pageSize"
              @pagination="getlistPoint"
            />-->
          </el-col>
        </el-row>
        <el-row>
          <el-col :offset="23" :span="24">
            <el-button @click="cancel">返回</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>

  </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {equals} from "@/utils/common";
import {
  getList,
  getListUrl,
  getRoute,
  getRouteList,
  Point,
  PointList,
  remove,
  removes,
  RouteAdd,
  updateStatus
} from "@/api/estate/work/patrol/route";

import Sortable from "sortablejs";

export default {

  data() {
    return {
      // 搜索
      search: '',
      // 详情表单
      detailForm: {},
      // 新增页面表单
      addForm: {},
      pointLength: undefined,
      // 小区ID
      communityId: undefined,

      // 详情内表格数据
      ownerListData: [],
      // 机构列表
      deptData: [],
      pointNameList: [],
      ownerListDataUrl: [],

      // 新增弹窗
      addOpen: false,

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        // 小区ID
        communityId: undefined,
        routeName: "",
        createBy: null,
        routeStatus: null,
        pointNumber: undefined,
        routeId: undefined
      },
      // 遮罩层
      loading: true,
      // 表格数据
      pointList: [],
      pointIdList: [],
      List: [],
      // 选中数组
      ids: [],
      // 详情页面默认关闭
      showDetail: false,
      homeShow: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 表单参数
      form: {},
      // 事件阻止标志
      flag: 0,
      disab: false,
      col: [
        {
          label: '巡点名称',
          prop: 'pointName'
        },
        {
          label: '巡点地址',
          prop: 'pointSite'
        }
      ],
      dropCol: [
        {
          label: '巡点名称',
          prop: 'pointName'
        },
        {
          label: '巡点地址',
          prop: 'pointSite'
        }
      ],

      col1: [
        {
          label: '排序',
          prop: 'sum'
        },
      ],

      dropCol1: [
        {
          label: '排序',
          prop: 'sum'
        },
      ],


      // 新增表单校验
      addRules: {
        routeName: [
          {required: true, message: "巡更名称不能为空", trigger: "blur"},
        ],
        pointIdList: [
          {required: true, message: "请选择巡点", trigger: "blur"},
        ],
        // routeStatus: [
        //   {required: true, message: "请选择巡更路线状态", trigger: "change"},
        // ],
      },

    };
  },
  computed: {
    viewDeptData: function () {
      let search = this.search;
      let viewDeptData = this.deptData;
      if (search) {
        viewDeptData = this.deptData.filter(dataNews => {
          return dataNews.communityName.indexOf(search) !== -1;
        })
      }
      return viewDeptData;
    }
  },
  created() {
    // 机构下拉框
    this.getTypeList();
    // 列表查询
    // this.getBlackList();
  },
  methods: {
    selectEnable(row) { // 复选框可选情况
     return row.routeStatus === '1';
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getBlackList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      // this.queryParams.dropDownForm = null;
      this.searchTime = "";
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 查询巡更列表 */
    getBlackList() {
      this.loading = false;
      this.pointList=[];
      getRouteList(this.queryParams).then((response) => {
        this.pointList = response.rows;
        this.total = response.total;
      });
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.routeId);
      this.multiple = !selection.length;
    },

    //行拖拽
    rowDrop() {
      const tbody = document.querySelector('.el-table--border tbody')
      const _this = this
      Sortable.create(tbody, {
        onEnd({newIndex, oldIndex}) {
          const currRow = _this.List.splice(oldIndex, 1)[0]
          _this.List.splice(newIndex, 0, currRow)
        }
      })
    },

    // 表单重置
    reset() {
      this.form = {};
      this.resetForm("form");
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.showDetail = false;
      this.homeShow = true;
      this.ownerListData = [];
      this.detailForm = [];
      this.detailForm.ownerListData = [];
      this.getBlackList();

      // this.reset();
    },

    selectView(row) {
      // let params = {
      //   pointId: row.pointId,
      // };
      let routeId = row.routeId;
      let params = {
        routeId: row.routeId,
      };
      getRoute(routeId).then((response) => {
        this.detailForm = response.data;
        this.open = true;
      });
      getList(params).then(resp => {
        this.ownerListData = resp.rows;
        this.total = resp.total;
        this.open = true;
      })

    },

    getlistPoint() {
      this.queryParams.routeId = this.detailForm.routeId;
      getList(this.queryParams).then(resp => {
        this.ownerListData = resp.rows;
        this.total = resp.total;
        this.open = true;
      })
    },

    /** 详情 */
    detail(row) {
      this.showDetail = true;
      this.homeShow = false;
      this.selectView(row);
    },


    // 机构下拉框
    getTypeList() {
      getCurrentList().then((response) => {
        this.deptData = response.data;
        this.queryParams.communityId = this.deptData[0].communityId;
        // this.queryParams.communityName = this.deptData[0].communityName;
        this.$refs.singleTable.setCurrentRow(this.deptData[0]);
        this.addForm.communityId = this.deptData[0].communityId;
        this.addForm.communityName = this.deptData[0].communityName;
        this.communityIds = this.deptData[0].communityId;
        this.getBlackList();
      });
    },

    // 单元格点击时查列表
    listClick(row) {
      this.queryParams.communityId = row.communityId;
      // // 点击单元格时赋值所属小区
      this.addForm.communityId = row.communityId;
      this.addForm.communityName = row.communityName;
      this.communityIds = row.communityId;
      this.getBlackList();
    },


    // 列表删除按钮
    remove(row) {
      let params = {
        routeId: row.routeId,
      };
      this.$confirm("此操作将删除该巡更路线信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          remove(params).then(() => {
            this.getBlackList();
          });
          this.$message({
            type: "success",
            message: "删除成功!",
          });
        })
        .catch(() => {
        });
    },


    //选择触发事件
    selectPoint(pointIdList) {
      if (this.flag == 1) {
        return
      }
      if (equals(pointIdList.length, 0)) {
        this.List = [];
      } else {
        this.List = [];
        for (let i = 0; i < pointIdList.length; i++) {
          Point(pointIdList[i]).then(resp => {
            resp.routeId = this.List.length + 1
            this.List.push(resp)
          })
        }
      }
    },

    //多选删除处罚事件
    removeTag(value) {
      this.flag = 1
      // this.pointLength=pointIdList.length;
      // return true;
      this.List = this.List.filter(item => {
        return item.pointId != value
      })
    },
    //多选删除处罚事件
    removeTags(){
      this.flag = 1
      // this.pointLength=pointIdList.length;
      // return true;
     /* this.List = this.List.filter(item =>{
        return item.pointId != value
      })*/
      this.List = []
    },

    visChange(value) {
      if (value == true) {
        this.disab = true
        this.flag = 0
      }
      if (value == false) {
        this.disab = false
        this.flag = 1
      }
    },
    //删除按钮
    handleDelete() {
      this.$modal
        .confirm("是否确认删除选中的数据项？")
        .then(() => {
          removes(this.ids).then(() => {
          });
        })
        .then(() => {
          this.getBlackList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {
        });
    },

    // 打开新增弹窗
    addMethod() {
      this.addOpen = true;
      PointList(this.addForm.communityId).then(response => {
        this.pointNameList = response
        this.rowDrop();
        this.open = true;
        this.title = "新增巡更路线";
      });
    },

    // 关闭新增弹窗
    addClose() {
      // this.$refs.addForm.resetFields();
      this.addFormEmpty();
      this.addOpen = false;
      this.getBlackList();
      this.addFormEmpty();
    },


    // 修改
    update(row) {
      this.routeId = row.routeId;
      this.addOpen = true;
      this.updateDetails(row.routeId);
      this.addForm.routeId = row.routeId;
      PointList(this.addForm.communityId).then(response => {
        this.pointNameList = response
        this.rowDrop();
      });
    },

    // 编辑页面详情展示
    updateDetails(routeId) {
      let params = {
        routeId: routeId,
      };
      getRoute(this.routeId).then((response) => {
        this.addForm = response.data;
      });
      getList(params).then(resp => {
        this.List = resp.rows;
        this.open = true;
        this.title = "修改巡更路线";
      })
    },

    // 提交
    addSuccess(addForm) {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          delete this.addForm["params"];
          this.addForm = addForm;
          this.addForm.routeId = this.routeId;
          this.addForm.pointIdList = this.List.map(item => {
            return item.pointId
          })

          this.$confirm("是否确定提交?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              RouteAdd(this.addForm).then((response) => {
                this.form = response.data;
                this.$modal.msgSuccess("操作成功");
                /*this.$message({
                  type: response.code,
                  message: response.msg,
                });*/
                this.getBlackList();
              });
              this.addFormEmpty();
              this.addOpen = false;
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消提交",
              });
            });
        }
      });
    },

    // 更改巡更路线状态
    handleStatusChange(row) {
      let params = {
        routeId: row.routeId,
        routeStatus: row.routeStatus,
      };
      let text = row.routeStatus === "0" ? "开启" : "关闭";
      this.$modal
        .confirm("确认要" + text + "名称为 " + row.routeName + " 巡更路线吗？")
        .then(function () {
          updateStatus(params).then(() => {
          });
        })
        .then(() => {
          this.$modal.msgSuccess(text + "成功");
          this.getBlackList();
        })
        .catch(function () {
          row.routeStatus = row.routeStatus === "0" ? "1" : "0";
        });
    },


    downLoadImg(row) {
      let routeId = row.routeId;
      getListUrl(routeId).then(resp => {
        this.ownerListDataUrl = resp;
        this.open = true;
        //图片下载
        this.ownerListDataUrl.forEach(item => {
          const image = new Image();
          image.setAttribute("crossOrigin", "anonymous");
          image.src = item.pointUrl;
          // image.src = "http://127.0.0.1:9302/statics/2022/11/22/qr1669090231360_20221122121031A002.jpg";
          image.onerror = () => {
            this.$modal.msgError("图片未找到");
          };
          image.onload = () => {
            const canvas = document.createElement("canvas");
            canvas.width = image.width;
            canvas.height = image.height;
            const context = canvas.getContext("2d");
            context.drawImage(image, 0, 0, image.width, image.height);
            canvas.toBlob((blob) => {
              const url = URL.createObjectURL(blob);
              const a = document.createElement("a");
              a.download = item.pointName || "photo";
              a.href = url;
              a.click();
              a.remove();
              URL.revokeObjectURL(url);
            });
            this.$modal.msgSuccess("导出成功");
          };
        })
      })
      this.ownerListDataUrl = [];

    },


    // 清空addForm表单
    addFormEmpty() {
      this.addForm.routeId = undefined;
      this.routeId = undefined;
      this.addForm.routeName = null;
      this.addForm.routeStatus = null;
      this.addForm.List = [];
      this.addForm.pointIdList = [];
      this.List = [];
    },


  },
};
</script>
<style scoped>
</style>

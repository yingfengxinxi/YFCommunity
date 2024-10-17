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
              prefix-icon="el-icon-search"
              v-model="filterText"
              style="margin-bottom: 20px"
            />
          </div>
          <!-- <el-table
            ref="singleTable"
            type="index"
            highlight-current-row
            :data="deptData"
            :show-header="false"
            @cell-click="listClick"
          >
            <el-table-column prop="communityName" />
          </el-table> -->
           <el-tree
              class="filter-tree"
              :data="deptData"
              :props="currentListProps"
              default-expand-all
              :filter-node-method="filterNode"
              icon-class="el-icon-star-on"
              @node-click="handleNodeClick"
              ref="tree"
             highlight-current
             node-key="communityId"
           >
              </el-tree>
        </el-col>
        <el-col :span="20" :xs="24">
          <!-- 条件搜索 -->
          <el-form
            :model="queryParams"
            ref="queryForm"
            size="small"
            :inline="true"
          >
            <el-form-item label="巡点名称" prop="pointName">
              <el-input
                v-model="queryParams.pointName"
                placeholder="请输入巡点名称"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="创建人" prop="createBy">
              <el-input
                v-model="queryParams.createBy"
                placeholder="请输入创建人名称"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="巡点状态" prop="pointStatus">
              <el-select
                v-model="queryParams.pointStatus"
                placeholder="请选择巡点状态"
              >
                <el-option label="开启" value="0" />
                <el-option label="关闭" value="1" />
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
              >搜索</el-button
              >
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
              >重置</el-button
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
              v-hasPermi="['estate:point:add']"
              @click="addMethod"
            >新增</el-button
            >
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              v-hasPermi="['estate:point:remove']"
              @click="handleDelete"
            >删除</el-button
            >
          </div>

          <!-- 列表展示 -->
          <el-table
            stripe
            v-loading="loading"
            :data="pointList"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column
              label="巡点编号"
              align="center"
              prop="pointId"
              type="number"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="巡点名称"
              align="center"
              prop="pointName"
              :show-overflow-tooltip="true"
            >
            </el-table-column>
            <el-table-column
              label="巡点功能"
              align="center"
              prop="pointType"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="巡点地址"
              align="center"
              prop="pointSite"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="创建人"
              align="center"
              prop="createBy"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="创建时间"
              align="center"
              prop="createTime"
              width="200"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="状态"
              align="center"
              prop="pointStatus"
              :show-overflow-tooltip="true"
            >
              <template slot-scope="scope">
                <el-switch
                  v-model="scope.row.pointStatus"
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
              width="280"
              class-name="small-padding fixed-width"
            >
              <template slot-scope="scope">
                <el-button size="mini" type="text"   v-hasPermi="['estate:point:query']" @click="detail(scope.row)"
                ><el-link icon="el-icon-view" type="brand" :underline="false"
                >详情</el-link
                ></el-button
                >
                <el-button
                  size="mini"
                  type="text"
                  v-hasPermi="['estate:point:edit']"
                  @click="update(scope.row)"
                ><el-link
                  icon="el-icon-edit"
                  type="success"
                  :underline="false"
                >修改</el-link
                ></el-button>

                <el-button
                  size="mini"
                  type="text"
                  v-hasPermi="['estate:point:export']"
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
                  v-hasPermi="['estate:point:remove']"
                  @click="remove(scope.row)"
                ><el-link
                  icon="el-icon-refresh"
                  type="danger"
                  :underline="false"
                >删除</el-link
                ></el-button
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
          /></el-col>
      </el-row>
    </div>

    <!-- 新增页面 -->
    <el-dialog :visible.sync="addOpen" @close="addClose" title="title">
      <div>
        <el-form
          ref="addForm"
          :rules="addRules"
          :model="addForm"
          label-width="100px"
        >
          <el-row>
            <el-col :offset="3" :span="8">
              <!-- 小区下拉框 -->
              <el-form-item label="所属小区: ">
                <div style="width: 280px; overflow: hidden">
                  {{ addForm.communityName }}
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :offset="3" :span="8">
              <el-form-item label="巡点名称: " prop="pointName" >
                <el-input
                  clearable
                  v-model="addForm.pointName"
                  placeholder="请输巡点名称" maxlength="10"
                ></el-input>

              </el-form-item>
            </el-col>
          </el-row>


          <el-row>
            <el-col :offset="3" :span="8">
              <el-form-item label="巡点地址: " prop="pointSite">
                <el-input
                  clearable
                  v-model="addForm.pointSite"
                  placeholder="请输巡点地址" maxlength="20"
                ></el-input>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :offset="3" :span="8">
              <el-form-item label="巡点功能: " prop="pointType">
                <el-select
                  clearable
                  v-model="addForm.pointType"
                  placeholder="请选择巡点功能"
                >
                  <el-option label="巡查点、巡更点" value="0"></el-option>
                  <el-option label="巡查点" value="1"></el-option>
                  <el-option label="巡更点" value="2"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

            <el-row>
              <el-col :offset="2" :span="8">
            <el-form-item label="巡点状态:" style="width: 1000px" label-width="145px" prop="pointStatus">
              <el-radio-group v-model="addForm.pointStatus" >
                <el-radio v-model="addForm.pointStatus" label="0">开启</el-radio>
                <el-radio v-model="addForm.pointStatus" label="1">关闭</el-radio>
              </el-radio-group>
            </el-form-item>
              </el-col>
          </el-row>

          <el-row>
            <el-col :offset="10" :span="2">
              <div style="width: 280px">
                <el-button type="primary" @click="addSuccess(addForm)"
                >确定</el-button
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
        <h5><b>巡点信息</b></h5>
        <el-divider></el-divider>

        <!-- 第一行 -->
        <el-row>
          <el-col :offset="0" :span="4">
            <el-form-item label="巡点编号: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.pointId }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-form-item label="巡点名称: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.pointName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-form-item label="巡点功能: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm. pointTypeStr}}
              </div>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第二行 -->
        <el-row>
          <el-col :offset="0" :span="4">
            <el-form-item label="巡更路线: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.pointSite }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-form-item label="二维码: ">
              <div style="width: 280px; overflow: hidden">
                <el-image
                  style="width: 70px; height: 70px"
                  :src="detailForm.pointUrl"
                  fit="fit"/>
<!--                {{ detailForm.pointUrl }}-->
              </div>
            </el-form-item>
          </el-col>
        </el-row>


        <!-- 所属业户信息 -->
<!--        <div>-->
<!--          <h5><b>扫码历史</b></h5>-->
<!--        </div>-->
<!--        <el-divider></el-divider>-->
<!--        <el-row>-->
<!--          <el-col :span="14">-->
<!--            <el-table-->
<!--              stripe-->
<!--              type="index"-->
<!--              highlight-current-row-->
<!--              :data="ownerListData"-->
<!--            >-->
<!--              <el-table-column-->
<!--                label="相关工单编号"-->
<!--                align="center"-->
<!--                :show-overflow-tooltip="true"-->
<!--                prop="carporNo"-->
<!--              />-->
<!--              <el-table-column-->
<!--                label="相关工单名称"-->
<!--                align="center"-->
<!--                :show-overflow-tooltip="true"-->
<!--                prop="userName"-->
<!--              />-->
<!--              <el-table-column-->
<!--                label="扫码原因"-->
<!--                align="center"-->
<!--                :show-overflow-tooltip="true"-->
<!--                prop="cardNo"-->
<!--              ></el-table-column>-->
<!--              <el-table-column-->
<!--                label="扫码人"-->
<!--                align="center"-->
<!--                :show-overflow-tooltip="true"-->
<!--                prop="userPhone"-->
<!--              ></el-table-column>-->
<!--              <el-table-column-->
<!--                label="扫码时间"-->
<!--                align="center"-->
<!--                :show-overflow-tooltip="true"-->
<!--                prop="rentalStart"-->
<!--              />-->

<!--            </el-table>-->
<!--          </el-col>-->
<!--        </el-row>-->
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
import {
  getCurrentList
} from "@/api/estate/community";
import {getpointList, getPoint, updateStatus, removes, remove, pointAdd} from "@/api/estate/work/patrol/point";



export default {
  data() {
    return {
      // 详情表单
      detailForm: {},
      // 新增页面表单
      addForm: {
        pointName: '',
        pointSite: '',
        pointType: undefined,
        pointStatus: undefined,
      },
      // 小区ID
      communityId: undefined,
      filterText:'',
      // 详情内表格数据
      ownerListData: [],
      // 机构列表
      deptData: [],
   currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      // 新增弹窗
      addOpen: false,

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        // 小区ID
        communityId: undefined,
        pointName: "",
        createBy: null,
        pointStatus: null,
      },
      // 遮罩层
      loading: true,
      // 表格数据
      pointList: [],
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
      // 新增表单校验
      addRules: {
        pointName: [
          { required: true, message: "巡点名称不能为空", trigger: "blur" },
        ],
        pointSite: [
          { required: true, message: "巡点地址不能为空", trigger: "blur" },
        ],
        pointType: [
          { required: true, message: "请选择巡点功能", trigger: "blur" },
        ],
        pointStatus: [
          { required: true, message: "请选择巡点状态", trigger: "blur" },
        ],
      },

    };
  },

  created() {
    // 机构下拉框
    this.getTypeList();
    // 列表查询
    // this.getBlackList();

  },
 watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
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
    /** 查询岗位列表 */
    getBlackList() {
      this.loading = false;
      getpointList(this.queryParams).then((response) => {
        this.pointList = response.rows;
        this.total = response.total;
      });
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.pointId);
      this.multiple = !selection.length;
    },

    // 表单重置
    reset() {
      this.form = {};
      this.resetForm("form");
    },
      // 过滤树
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.showDetail = false;
      this.homeShow = true;
      this.getBlackList();
      //this.reset();
    },

    selectView(row) {
      // let params = {
      //   pointId: row.pointId,
      // };
      let pointId=row.pointId;
      getPoint(pointId).then((response) => {
        this.detailForm = response.data;
        this.open = true;
      });
      // getDetailList(params).then((response) => {
      //   this.ownerListData = response.data;
      //   this.open = true;
      // });
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
        // this.$refs.singleTable.setCurrentRow(this.deptData[0]);
        this.addForm.communityId = this.deptData[0].communityId;
        this.addForm.communityName = this.deptData[0].communityName;
        this.communityIds = this.deptData[0].communityId;
        this.getBlackList();
        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.communityIds);
        });
      });
    },

    // 单元格点击时查列表
    handleNodeClick(row) {
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
        pointId: row.pointId,
      };
      this.$confirm("此操作将删除该巡点信息, 是否继续?", "提示", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          remove(params).then(() => {});
          this.$message({
            type: "success",
            message: "删除成功!",
          });
          this.getBlackList();
        })
        .catch(() => {});
    },

    //删除按钮
    handleDelete() {
      this.$modal
        .confirm("是否确认删除选中的数据项？")
        .then(() => {
          removes(this.ids).then(() => {});
        })
        .then(() => {
          this.getBlackList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },

    // 打开新增弹窗
    addMethod() {
      this.title = '巡点新增';
      this.addOpen = true;
    },

    // 关闭新增弹窗
    addClose() {
      // this.$refs.addForm.resetFields();
      this.addOpen = false;
      this.getBlackList();
      this.addFormEmpty();
      this.$refs["addForm" ].resetFields();
      this.pointId = null;
    },





    // 修改
    update(row) {
      this.pointId = row.pointId;
      this.addOpen = true;
      this.updateDetails(row.pointId);
      this.addForm.pointId = row.pointId;
      this.title = '巡点修改';
    },

    // 编辑页面详情展示
    updateDetails(pointId) {
      let params = {
        pointId: pointId,
      };
      getPoint(this.pointId).then((response) => {
        this.addForm = response.data;
      });
    },

    // 提交
    addSuccess(addForm) {
      this.$refs.addForm.validate((valid) => {
        if (valid) {
          delete this.addForm["params"];
          this.addForm = addForm;
          this.addForm.pointId = this.pointId;

          this.$confirm("是否确定提交?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              pointAdd(this.addForm).then((response) => {
                this.form = response.data;
                this.$modal.msgSuccess("操作成功");
                /*this.$message({
                  type: response.code,
                  message: response.msg,
                });*/
              });
              this.addFormEmpty();
              this.getBlackList();
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

    // 更改巡点状态
    handleStatusChange(row) {
      let params = {
        pointId: row.pointId,
        pointStatus: row.pointStatus,
      };
      let text = row.pointStatus === "0" ? "开启" : "关闭";
      this.$modal
        .confirm("确认要修改名称为 " + row.pointName + " 为 " + text + " 吗？")
        .then(function () {
          updateStatus(params).then(() => {});
        })
        .then(() => {
          this.$modal.msgSuccess(text + "成功");
          this.getBlackList();
        })
        .catch(function () {
          row.pointStatus = row.pointStatus === "0" ? "1" : "0";
        });
    },










    //图片下载
    downLoadImg(row) {
      const image = new Image();
      image.setAttribute("crossOrigin", "anonymous");
      image.src = row.pointUrl;
      image.onload = () => {
        const canvas = document.createElement("canvas");
        canvas.width = image.width;
        canvas.height = image.height;
        const context = canvas.getContext("2d");
        context.drawImage(image, 0, 0, image.width, image.height);
        canvas.toBlob((blob) => {
          const url = URL.createObjectURL(blob);
          const a = document.createElement("a");
          a.download = row.pointName || "photo";
          a.href = url;
          a.click();
          a.remove();
          URL.revokeObjectURL(url);
        });
      };
    },









    // 清空addForm表单
    addFormEmpty() {
      this.addForm.pointName = null;
      this.addForm.pointStatus = null;
      this.addForm.pointType = null;
      this.addForm.pointSite = null;
    },



  },
};
</script>
<style scoped>
  /*设置高亮颜色*/
/deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #b3f1b6 !important;
}
</style>

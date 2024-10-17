<template>
  <!-- 主页面 -->
  <div class="app-container">
    <div v-show=checkView>
      <!--搜索条件-->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
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
        <el-form-item label="所属小区" prop="communityIds">
          <el-select
            v-model="queryParams.communityIds"
            placeholder="请选择小区"
            style="width: 240px"
            multiple
            @keyup.enter.native="handleQuery">
            <el-option
              v-for="item in communtiyList"
              :key="item.communityId"
              :label="item.communityName"
              :value="item.communityId">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" v-hasPermi="['govern:grid:list']" size="mini"
                     @click="handleQuery">搜 索
          </el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重 置</el-button>
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
            v-hasPermi="['govern:grid:add']"
            @click="handleAdd"
          >新增
          </el-button>
        </el-col>

        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <!--  主页表单  -->
      <el-table v-loading="loading" :data="tableList" row-key="contractId">
        <el-table-column label="区域名称" align="center" key="gridName" prop="gridName"/>
        <el-table-column label="所属小区" align="center" key="communityName" prop="communityName"/>
        <el-table-column label="管理员" align="center" key="staffName" prop="staffName"/>
        <el-table-column label="联系电话" align="center" key="staffPhone" prop="staffPhone"/>
        <el-table-column label="创建时间" align="center" key="createTime" prop="createTime"></el-table-column>
        <el-table-column
          label="操作"
          align="center"
          width="160"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button
              size="small"
              type="text"
              icon="el-icon-edit"
              v-hasPermi="['govern:grid:edit']"
              @click="handleEdit(scope.row.gridId)"
            >编辑
            </el-button>

            <el-button
              size="small"
              type="text"
              icon="el-icon-edit"
              v-hasPermi="['govern:grid:list']"
              @click="handleEvent(scope.row.gridId)"
            >查看事件
            </el-button>

            <el-button
              type="text"
              icon="el-icon-delete"
              size="small"
              style="color: #f56c6c"
              v-hasPermi="['govern:grid:remove']"
              @click="handleDelete(scope.row.gridId)"
            >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--     分页操作-->
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />

      <!--  添加或修改弹框   -->
      <el-dialog ref="insertOrEditForm" :title="title" :visible.sync="dialogFormVisible" width="780px">
        <el-form :model="insertOrEditForm" :rules="rules" ref="insertOrEditForm" label-width="100px">

          <el-row>
            <el-col :span="22">
              <el-form-item label="所属小区:" prop="communityId">
                <el-select
                  :disabled="insertOrEditForm.gridId"
                  v-model="insertOrEditForm.communityId"
                  placeholder="请选择小区"
                  style="width: 500px"
                  @change="getStaff(insertOrEditForm.communityId)">
                  <el-option
                    v-for="item in communtiyList"
                    :key="item.communityId"
                    :label="item.communityName"
                    :value="item.communityId">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="22">
              <el-form-item label="区域名称:" prop="gridName">
                <el-input
                  v-model="insertOrEditForm.gridName"
                  placeholder="请输入区域名称"
                  style="width: 500px"
                ></el-input>
              </el-form-item>
            </el-col>


            <el-col :span="23">
              <el-form-item label="区域描述:" prop="remark">
                <el-input
                  v-model="insertOrEditForm.remark"
                  placeholder="请输入区域位置描述"
                  style="width: 500px"
                ></el-input>
              </el-form-item>
            </el-col>

            <el-col :span="22" v-show="insertOrEditForm.communityId">
              <el-form-item label="管理人员:" prop="staffId">
                <el-select
                  v-model="insertOrEditForm.staffId"
                  placeholder="请选择管理人员"
                  style="width: 500px">
                  <el-option
                    v-for="item in staffList"
                    :key="item.staffId"
                    :label="item.staffName"
                    :value="item.staffId">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" v-hasPermi="['govern:grid:add']" @click="submitForm">确定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>

    <div v-show=eventView>
      <event @goBack="goBack"></event>
    </div>
  </div>
</template>

<script>
import {
  getList,
  addGrid,
  updateGrid,
  delGrid,
  getCommuntiyList,
  getGridDetail,
  getStaff,
} from "@/api/govern/manage/grid";
import event from "@/views/govern/manage/grid/event";

export default {
  name: "index",
  components: {
    event,
  },
  data() {
    return {
      numberList: [{timestamp: '2018/4/12', label: "测试"}, {
        timestamp: '2019/4/12',
        label: "测试一下吗"
      }, {timestamp: '2018/8/10', label: "洗个手"}],
      //主页面是否展示
      checkView: true,
      //事件详情页
      eventView: false,
      //搜索框是否展示
      showSearch: true,
      //查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityIds: [],
      },
      //总条目数
      total: 0,
      // 日期范围
      dateRange: [],
      //主页面表单集合
      tableList: [],
      //加载状态
      loading: false,
      //添加表单是否展示
      dialogFormVisible: false,
      //添加或者修改的表单
      insertOrEditForm: {},
      //可选小区集合
      communtiyList: [],
      //管理员集合
      staffList: [],
      //标题
      title: '添加网格',
      //校验规则
      rules:{
        communityId: [
          {required: true, message: '请选择小区', trigger: ['blur']},
        ],
        gridName: [
          {required: true, message: '区域名称不可为空', trigger: ['blur']},
          {min: 2, max: 8, message: '长度在 2 到 8 个字符', trigger: ['blur']}
        ],
        remark: [
          {required: true, message: '请填写区域描写', trigger: ['blur']},
          {min: 5, max: 50, message: '长度在 5 到 50 个字符', trigger: ['blur']}
        ],
        staffId: [
          {required: true, message: '请选择管理人员', trigger: ['blur']},
        ],
      },
    }
  },
  methods: {
    //条件查询
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },

    //添加方法
    handleAdd() {
      this.insertOrEditForm = {}
      this.getCommuntiyList()
      this.dialogFormVisible = true;
      this.title='添加网格'
    },


    //提交表单
    submitForm() {
      this.$refs["insertOrEditForm"].validate(valid => {
        if (valid) {
          if (this.insertOrEditForm.gridId != undefined) {
            updateGrid(this.insertOrEditForm).then(response => {
              this.$modal.msgSuccess("修改成功");
            });
          } else {
            addGrid(this.insertOrEditForm).then(response => {
              this.$modal.msgSuccess("新增成功");
            });
          }
          this.dialogFormVisible = false;
          this.getList();
          this.insertOrEditForm = {};
        }
      });
    },

    //编辑回显
    handleEdit(gridId) {
      this.getCommuntiyList()
      getGridDetail(gridId).then(res => {
        console.log("res",res)
        this.getStaff(res.data.communityId);
        this.insertOrEditForm = res.data;
        this.dialogFormVisible = true;
        this.title = "修改网格";
      })
    },

    //查看事件
    handleEvent(gridId) {
       alert("功能待完善")
       // this.checkView = false;
       // this.eventView = true;
    },

    //查看事件
    goBack() {
      this.checkView=true;
      this.eventView=false;
    },

    //获取小区集合
    getCommuntiyList() {
      getCommuntiyList().then(res => {
        this.communtiyList = res.data
      })
    },

    //获取管理员集合
    getStaff(communtiyId) {
      getStaff(communtiyId).then(res => {
        this.staffList = res.data
      })
    },


    //删除
    handleDelete(GridId) {
      this.$modal.confirm('是否确认删除数据项？').then(function () {
        return delGrid(GridId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },

    //取消添加
    cancel() {
      this.dialogFormVisible = false;
      this.insertOrEditForm = {}
      this.$refs['insertOrEditForm'].resetFields();
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
      this.tableList=[];
      getList(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.tableList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
  },

  created() {
    this.getList();
    this.getCommuntiyList();
  }

}
</script>

<style scoped>

</style>


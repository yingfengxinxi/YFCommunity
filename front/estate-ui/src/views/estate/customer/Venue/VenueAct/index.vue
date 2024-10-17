<template>
  <div class="app-container">
    <el-row :gutter="20">

      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            placeholder="请输入小区名称"
            clearable
            v-model="filterText">
          </el-input>
          <el-tree
            class="filter-tree"
            :data="currentList"
            :props="currentListProps"
            node-key="communityId"
            default-expand-all
            highlight-current
            :filter-node-method="filterNode"
            icon-class="el-icon-star-on"
            @node-click="handleNodeClick"
            :current-node-key="queryParams.communityId"
            ref="tree">
          </el-tree>
        </div>
      </el-col>

      <el-col :span="20" :xs="24">

        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
          <el-form-item label="活动类型" prop="activityName">
            <el-input
              v-model="queryParams.activityName"
              placeholder="请输入活动类型"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="状态" prop="activityStatus">
            <el-select v-model="queryParams.activityStatus" clearable>
              <el-option key="0" label="开启" value="0"></el-option>
              <el-option key="1" label="关闭" value="1"></el-option>
            </el-select>
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
              v-hasPermi="['system:act:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
<!--            <el-button
              type="success"
              plain
              icon="el-icon-edit"
              size="mini"
              :disabled="single"
              @click="handleUpdate"
              v-hasPermi="['system:act:edit']"
            >修改</el-button>-->
          </el-col>
          <el-col :span="1.5">
<!--            <el-button-->
<!--              type="danger"-->
<!--              plain-->
<!--              icon="el-icon-delete"-->
<!--              size="mini"-->
<!--              :disabled="multiple"-->
<!--              @click="handleDelete"-->
<!--              v-hasPermi="['system:act:remove']"-->
<!--            >删除</el-button>-->
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="actList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <!--      <el-table-column label="主键" align="center" prop="activityId"/>-->
          <el-table-column label="活动类型" align="center" prop="activityName"/>
          <el-table-column label="状态" align="center" key="activityStatus">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.activityStatus"
                active-value= "0"
                inactive-value= "1"
                @change="handleStatusChange(scope.row)"
              ></el-switch>
            </template>
          </el-table-column>
          <el-table-column label="最后操作人" align="center" prop="updateBy"/>
          <el-table-column label="最后操作时间" align="center">
            <template slot-scope="scope">
              {{parseTime(scope.row.updateTime)}}
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['system:act:edit']"
              >修改</el-button>
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

    </el-row>

    <!-- 添加或修改场馆活动对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">

        <el-form-item label="活动名称" prop="activityName">
          <el-input v-model="form.activityName" placeholder="请输入活动名称" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>

import { listAct, getAct, delAct, addAct, updateAct } from "@/api/estate/customer/Venue/VenueAct";
import {getCurrentList} from "@/api/estate/community";

export default {
  name: "Act",
  data() {
    return {
      //小区数据集合
      currentList:[],

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
      // 场馆活动表格数据
      actList: [],
      // 弹出层标题
      title: "",
      filterText:'',
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        activityId: undefined,
        activityName: undefined,
        activityStatus: undefined
      },
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        communityId: [
          { required: true, message: "小区不能为空", trigger: "blur" }
        ],
        activityName: [
          { required: true, message: "活动名称不能为空", trigger: "blur" }
        ],
      },
    };
  },
  created() {
    this.getCurrent();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {

    getCurrent(){
      getCurrentList().then(res =>{
        this.currentList = res.data;
        this.queryParams.communityId = this.currentList[0].communityId;
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.currentList[0].communityId);
        });
        this.getList();
      })
    },


    /** 查询场馆活动列表 */
    getList() {
      this.loading = true;
      listAct(this.queryParams).then(response => {
        this.actList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },

    // 过滤树
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },


    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        activityId: null,
        communityId: null,
        activityName: null,
        activityStatus: "0",
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        delFlag: null
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.activityId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      if (this.queryParams.communityId ==null || this.queryParams.communityId == ""){
        this.$message.warning("请选择小区")
        return
      }
      this.reset();
      this.form.communityId = this.queryParams.communityId;
      this.open = true;
      this.title = "添加场馆活动";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const activityId = row.activityId || this.ids
      getAct(activityId).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改场馆活动";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.activityId != null) {
            updateAct(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addAct(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const activityIds = row.activityId || this.ids;
      this.$modal.confirm('是否确认删除此项场馆类型的数据项？').then(function() {
        return delAct(activityIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /**
     * 修改状态
     * @param row
     */
    handleStatusChange(row) {
      this.form.activityId = row.activityId
      this.form.activityStatus = row.activityStatus
      let text = row.activityStatus === "0" ? "启动" : "暂停";
        this.$confirm('确定要'+text+row.activityName+'吗?', '提示', {
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          updateAct(this.form).then(res =>{
            if (res.code == 200){
              this.$message.success("修改成功")
              this.getList();
            }
          });
        }).catch(() => {
          row.activityStatus = row.activityStatus === "0" ? "1" : "0";
        });
    },

    handleNodeClick(ro){
      this.queryParams.communityId = ro.communityId
      this.getList();
    },

  }
};
</script>

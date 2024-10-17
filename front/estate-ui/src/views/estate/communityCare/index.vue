<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="活动名称" prop="activityName">
        <el-input
          v-model="queryParams.activityName"
          placeholder="请输入活动名称"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属小区" prop="communityId" label-width="80px">
        <el-select v-model="queryParams.communityId" placeholder="请选择" style="width: 240px">
          <el-option
            v-for="(item, index) in currentList"
              :key="index"
                          :label="item.communityName"
                          :value="item.communityId"
                        ></el-option>
                      </el-select>
      </el-form-item>
      <el-form-item label="活动类型" prop="communityCareId">
        <el-select
          v-model="queryParams.communityCareId"
          placeholder="请选择活动类型"
          clearable
          :style="{ width: '100%' }"
        >
          <el-option
            v-for="(item, index) in communityCareOptions"
            :key="index"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          >新增</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="schoolList"
      @selection-change="handleSelectionChange"
    >
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="活动封面" align="center" prop="activePicture">
        <template slot-scope="scope">
          <image-preview :src="scope.row.activePicture" :width="50" :height="50" />
        </template>
      </el-table-column>
      
      <el-table-column label="所属小区" align="center" prop="communityId">
        <template slot-scope="scope">
          {{ getCommunityName(scope.row.communityId) }}
        </template>
      </el-table-column>
      <el-table-column label="活动名称" align="center" prop="activityName" />
      <el-table-column label="活动类型" align="center" prop="communityCareId">
        <template slot-scope="scope">
          {{ getCommunityCareTypeName(scope.row.communityCareId) }}
        </template>
      </el-table-column>
      <el-table-column label="活动介绍" align="center" prop="activeListContent" :show-overflow-tooltip="true"/>
      <el-table-column label="是否发布" align="center" prop="status">
        <template slot-scope="scope">
              <el-switch
                v-model="scope.row.status"
                active-value="1"
                inactive-value="0"
                @change="handleStatusChange(scope.row)"
              ></el-switch>
            </template>
      </el-table-column>
      <el-table-column label="发布时间" align="center" prop="eventReleaseTime" />
      <el-table-column label="发布人" align="center" prop="eventReleaseBy" />
      
      <el-table-column
        label="操作"
        align="center"
        class-name="small-padding fixed-width"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            >修改</el-button
          >
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            style="color: #f56c6c"
            @click="handleDelete(scope.row)"
            >删除</el-button
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

    <!-- 添加或修改活动对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="90px"
      >
        <el-form-item label="活动封面" prop="activePicture">
          <image-upload v-model="form.activePicture" :limit="1" />
          <div style="font-size: 12px;color: #f57272;margin-top: -19px;">需上传尺寸为：宽343px，高140px的图片</div>
        </el-form-item>
        <el-form-item label="所属小区" prop="communityId">
        <el-select v-model="form.communityId" placeholder="请选择" style="width: 100%;">
          <el-option
            v-for="(item, index) in currentList"
              :key="index"
                          :label="item.communityName"
                          :value="item.communityId"
                        ></el-option>
                      </el-select>
        </el-form-item>
        <el-form-item label="活动名称" prop="activityName">
          <el-input v-model="form.activityName" placeholder="请输入活动名称" />
        </el-form-item>
        <el-form-item label="活动类型" prop="communityCareId">
        <el-select
          v-model="form.communityCareId"
          placeholder="请选择活动类型"
          clearable
          :style="{ width: '100%' }"
        >
          <el-option
            v-for="(item, index) in communityCareOptions"
            :key="index"
            :label="item.name"
            :value="item.id"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="活动介绍" prop="activeListContent">
          <el-input v-model="form.activeListContent" type="textarea" placeholder="请输入活动介绍" />
      </el-form-item>
      <el-form-item label="活动内容" prop="content">
          <editor v-model="form.content" :min-height="192" />
      </el-form-item>
        <el-form-item label="是否发布" prop="status">
          <el-radio-group v-model="form.status" size="medium">
            <el-radio
              v-for="(item, index) in isPublishOptions"
              :key="index"
              :label="item.value"
              >{{ item.label }}</el-radio
            >
          </el-radio-group>
        </el-form-item>
        <el-form-item label="显示顺序" prop="sort">
          <el-input-number
            :min="1"
            :max="1000"
            v-model="form.sort"
            placeholder="请输入显示顺序"
          />
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
import {
  getListPage,
  insert,
  update,
  del,
  getById,
  updateByIdStatus
} from "@/api/estate/communityCare/communityCare";
import {
  getList
} from "@/api/estate/communityCare/communityCareType";
import {
  getCurrentList
} from "@/api/estate/community";
export default {
  name: "CommunityCare",
  data() {
    return {
      isPublishOptions: [
        { value: "0", label: "否" },
        { value: "1", label: "是" },
      ],
      communityCareOptions:[],
      studentJobTotal:0,
      openloation:false,
      modalMap: false,
      currentList:[],
      // 遮罩层
      loading: true,
      keyUrl: "",
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
      // 学校表格数据
      schoolList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      studentLoading: true,
     
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        activityName: null,
        communityCareId: undefined,
        communityId:undefined,
      },
      // 表单参数
      form: {
        content:undefined
      },
      // 表单校验
      rules: {
        activePicture: [
          { required: true, message: "请上传活动封面", trigger: "blur" },
        ],
        communityId: [
          { required: true, message: "请选择所属小区", trigger: "blur" },
        ],
        activityName: [
          { required: true, message: "活动名称不能为空", trigger: "blur" },
        ],
        communityCareId: [{ required: true, message: "请选择活动类型", trigger: "blur" }],
        activeListContent: [{ required: true, message: "请输入活动介绍", trigger: "blur" }],
        content: [{ required: true, message: "请输入活动内容", trigger: "blur" }],
        status: [{ required: true, message: "请选择是否发布活动", trigger: "blur" }],
      },
    };
  },
  created() {
    //所属小区
    getCurrentList().then(res => { 
      this.currentList = res.data;
    })
    this.getList();
    this.getCommunityCareTypeList();
  },

  methods: {
    getCommunityName(communityId) { 
      var obj = this.currentList.filter(item => item.communityId == communityId)[0];
      if (obj) { 
        return obj.communityName;
      }
    },
    // 状态修改
    handleStatusChange(row) {
      let text = row.status === "1" ? "发布" : "撤销";
      this.$modal.confirm('确认要"' + text + '""' + row.activityName + '"活动吗？').then(function() {
        return updateByIdStatus(row.id, row.status);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    //类型
    getCommunityCareTypeList() {
      getList().then((res) => {
        if (res.code == 200) {
          this.communityCareOptions = res.data;
        }
      });
    },
    /** 列表 */
    getList() {
      this.loading = true;
      getListPage(this.queryParams).then((response) => {
        this.schoolList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        id: null,
        communityId:undefined,
        content: undefined,
        activityName: null,
        activePicture: null,
        communityCareId: null,
        activeListContent: null,
        sort:0,
        delFlag: null,
        createBy: null,
        createTime: null,
        updateBy: null,
        updateTime: null,
        remark: null,
      };
      this.resetForm("form");
    },
    getCommunityCareTypeName(communityCareId) { 
     return this.communityCareOptions.filter(item => item.id == communityCareId)[0].name;
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.activityName = undefined;
      this.queryParams.communityId = undefined;
      this.queryParams.communityCareId = undefined;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加社区关怀";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getById(id).then((response) => {
        this.form = response.data;

        this.open = true;
        this.title = "修改社区关怀";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
      
          if (this.form.id != null) {
            update(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            insert(this.form).then((response) => {
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
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除编号为"' + ids + '"的数据项？')
        .then(function () {
          return del(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
 
  },
};
</script>
<style lang="scss">
.el-input-group__append,
.el-input-group__prepend {
  background-color: #1890ff !important;
  color: #fff !important;
  border-color: #1890ff !important;
  border-radius: 0 4px 4px 0;
}
.cascaderW {
  width: 350px;
}

</style>
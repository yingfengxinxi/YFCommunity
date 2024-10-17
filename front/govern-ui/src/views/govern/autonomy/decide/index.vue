<template>
  <div class="app-container">
    <!--综合页面信息-->
    <div v-show=checkView>
      <!--模糊查询-->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
        <el-form-item label="发布日期">
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
        <el-form-item label="标题" prop="decideTitle">
          <el-input
            v-model="queryParams.decideTitle"
            placeholder="请输入"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜 索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重 置</el-button>
        </el-form-item>
      </el-form>
      <!--按钮操作-->
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['autonomy:decide:add']"
          >新增
          </el-button>
        </el-col>

        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['autonomy:decide:delete']"
          >删除
          </el-button>
        </el-col>

        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
      <!--表单-->
      <el-table v-loading="loading" :data="decideList" row-key="decideId" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="50" align="center"/>
        <el-table-column label="标题" align="center" key="decideTitle" prop="decideTitle"/>
        <el-table-column label="内容" align="center" key="content" prop="content"/>
        <el-table-column label="开始时间" align="center" key="startTime" prop="startTime"/>
        <el-table-column label="结束时间" align="center" key="endTime" prop="endTime"/>
        <el-table-column label="发布时间" align="center" key="createTime" prop="createTime"/>
        <el-table-column
          label="操作"
          align="center"
          width="160"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleVote(scope.row)"
              v-hasPermi="['autonomy:decide:edit']"
            >投票
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['autonomy:decide:edit']"
            >编辑
            </el-button>
            <el-button
              v-if="scope.row.parentId != 0 && scope.row.deptId !=101"
              size="mini"
              type="text"
              style="color: #f56c6c"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['autonomy:decide:delete']"
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
    </div>
    <!--新增或修改对话框v-show="checkAddUpdateView"-->
    <el-dialog :title="title" :visible.sync="checkAddUpdateView" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="100px">
        <el-form-item label="发布小区" prop="communityIds">
          <el-select v-model="form.communityIds" multiple placeholder="请选择小区" style="width: 100%"
          >
            <el-option
              v-for="item in communityList"
              :key="item.communityId"
              :label="item.communityName"
              :value="item.communityId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="发布标题" prop="decideTitle">
          <el-input v-model="form.decideTitle" placeholder="请输入"/>
        </el-form-item>
        <el-form-item label="发布内容" prop="content">
          <el-input v-model="form.content" type="textarea" placeholder="请输入内容" prop="content"></el-input>
        </el-form-item>
        <el-form-item label="开始时间" prop="startTime">
          <el-date-picker
            v-model="form.startTime"
            style="width: 100%;"
            value-format="yyyy-MM-dd"
            type="date"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="结束时间" prop="endTime">
          <el-date-picker
            v-model="form.endTime"
            style="width: 100%;"
            value-format="yyyy-MM-dd"
            type="date"
          ></el-date-picker>
        </el-form-item>

        <el-form-item label="投票选项一" prop="itemTitleOne">
          <input type="text" v-model="form.itemTitleOne" placeholder="请输入" class="input_style"
                 style="width: 360px;height: 36px;border-radius: 4px;border: 1px solid #DCDFE6;">
        </el-form-item>

        <el-form-item label="投票选项二" prop="itemTitleTwo">
          <input type="text" v-model="form.itemTitleTwo" placeholder="请输入" class="input_style"
                 style="width: 360px;height: 36px;border-radius: 4px;border: 1px solid #DCDFE6;">
          <!--          <el-input v-model="form.itemTitleTwo" placeholder="请输入" />-->
        </el-form-item>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!--投票页面对话框-->
    <el-dialog :title="voteTitle" :visible.sync="voteView" width="500px" append-to-body>
      <el-form ref="form" :model="voteForm" :rules="rules" label-width="100px">
        <div :class="change == j?'List_two':''" class="List" v-for="(i,j) in this.DecideItemList" :key="j"
             @click="actionVoteOne(i.itemId,j)">
          <div class="List_left">{{ i.itemTitle }}</div>
          <div class="List_left"><span class="List_right">{{ i.voteNum }}</span>票</div>
        </div>
      </el-form>
    </el-dialog>


  </div>
</template>

<script>
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {getCurrentList} from '@/api/govern/community';
import {desensitization, equals} from "@/utils/common";
import {
  addDecide,
  delDecide,
  getDecideById,
  getDecideItemById,
  getDecideList,
  updateDecide, updateDecideItemById
} from "@/api/govern/autonomy/decide/decide";

export default {
  name: "lease",
  data() {
    return {
      change: -1,
      url: process.env.VUE_APP_BASE_API + "/estate/tenant/uploadPicture",
      //上传图片的信息
      options: {
        coverUrl: "",
        statuteImg: "",
      },
      // 表单校验
      rules: {
        communityIds: [
          {required: true, message: "请选择发布小区", trigger: "blur"},
        ],
        decideTitle: [
          {required: true, message: "发布标题不能为空", trigger: "blur"},
        ],
        startTime: [
          {required: true, message: "请选择开始时间", trigger: "blur"},
        ],
        endTime: [
          {required: true, message: "请选择结束时间", trigger: "blur"},
        ],
        itemTitleOne: [
          {required: true, message: "请输入投票选项一", trigger: "blur"},
        ],
        itemTitleTwo: [
          {required: true, message: "请输入投票选项二", trigger: "blur"},
        ],
      },
      //投票页面
      voteTitle: "",
      //投票页面显隐
      voteView: false,
      //form表单
      voteForm: {},
      //物业数据
      estateList: [],
      checkAddUpdateView: false,
      //小区数据
      communityList: [],
      //详情页控制显隐
      checkInfo: false,
      // 选中数组
      ids: [],
      //选中名字数组
      names: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      //选项容器
      DecideItemList: [],
      // 日期范围
      dateRange: [],
      // 弹出层标题（用户导入）
      title: "",
      //模糊查询显隐
      showSearch: true,
      //查询到的列表参数
      decideList: [],
      //新增提交按钮显隐
      close: true,
      //详情页显隐
      checkView: true,
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 模糊查询数据
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        decideTitle: undefined,
      },
      // 表单参数
      form: {
        itemTitleOne: "",
        itemTitleTwo: "",
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {


    /** 手机号脱敏 */
    phoneFormatter(phone) {
      if (!equals(phone, undefined)) {
        return desensitization(phone, 3, 7);
      }
    },
    /** 身份证号脱敏 */
    cardFormatter(card) {
      if (!equals(card, undefined)) {
        return desensitization(card, 6, 14);
      }
    },


    //查询当前物业名下的小区
    getCommunitys() {
      getCurrentList().then(response => {
        this.communityList = response.data;
      })
    },
    /** 查询列表 */
    getList() {
      this.loading = true;
      getDecideList(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.decideList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const decideId = row.decideId || this.ids;
      this.$confirm('是否确认删除所选中的的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delDecide(decideId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
    },
    /** 投票按钮*/
    handleVote(row) {
      const decideId = row.decideId || this.ids;
      this.voteTitle = "投票结果";
      this.voteView = true;
      this.reset();
      getDecideItemById(decideId).then(response => {
        this.DecideItemList = response.data;
        //遍历集合
        if (this.DecideItemList.length > 0) {
          let list = this.DecideItemList;
          for (let e in list) {
            e.itemTitle;
          }

        }
      })
    },

    actionVoteOne(e, j) {
      console.log(e)
      updateDecideItemById(e).then(response => {
        this.$message.success("投票成功！");
        this.voteView = false;

      });
      this.change = j;
    },


    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.decideId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 取消按钮
    cancel() {
      this.checkView = true;
      this.checkAddUpdateView = false;
      this.checkInfo = false;
      this.form.coverUrl = "";
      this.options.statuteImg = "";
      this.voteView = false;
      this.reset();
    },

    /** 修改按钮操作(查询相应数据讲数据铺上) */
    handleUpdate(row) {
      this.getCommunitys();
      this.reset();
      const decideId = row.decideId;
      getDecideById(decideId).then(response => {
        this.checkAddUpdateView = true;
        this.form = response.data;
        this.form.communityIds = response.commIds;
        this.form.itemTitleOne = response.itemTitleOne;
        this.form.itemTitleTwo = response.itemTitleTwo;
        this.title = "修改用户";
      });
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.checkAddUpdateView = true;
      this.title = "新增";
      this.getCommunitys();
    },

    /** 添加按钮 */
    submitForm: function () {
      this.$refs["form"].validate(valid => {
        const endTime = new Date(this.form.endTime);
        const startTime = new Date(this.form.startTime);
        if (endTime.getTime() < startTime.getTime()) {
          return this.$message({
            message: '结束时间必须大于开始时间！',
            type: 'warning'
          });
        }
        if (valid) {
          if (this.form.decideId != undefined) {
            updateDecide(this.form).then(response => {
              this.$message.success("修改成功");
              this.checkView = true;
              this.checkAddUpdateView = false;
              this.getList();
            });
          } else {
            addDecide(this.form).then(response => {
              this.$message.success("新增成功");
              this.checkView = true;
              this.checkAddUpdateView = false;
              this.getList();
            });
          }
        }
      });
    },

    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 表单重置
    reset() {
      this.form = {
        decideId: undefined,
        decideTitle: undefined,
        content: undefined,
        itemTitleOne: undefined,
        itemTitleTwo: undefined,
        communityIds: [],
        startTime: undefined,
        endTime: undefined,
      };
      this.voteForm = {
        itemTitleOne: undefined,
        itemTitleTwo: undefined,
      }
      this.resetForm("form");
    },
    // 上传预处理
    beforeUpload(file) {
      return true;
    },
    // 上传封面
    handleUploadPhoto(res, file) {
      let formData = new FormData();
      formData.append('file', file.raw)
      uploadPicture(formData).then(response => {
        this.form.coverUrl = response.imgPath;
        this.options.statuteImg = response.imgPath;
        this.$modal.msgSuccess("上传成功");
      });
    },


  }
};
</script>


<style>
.input_style {
  padding: 0 15px;
}

.List {
  width: 460px;
  height: 50px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0 20px;
  margin-bottom: 15px;
}

.List_two {
  width: 460px;
  height: 50px;
  display: flex;
  justify-content: space-between;
  background-color: pink;
  align-items: center;
  padding: 0 20px;
  margin-bottom: 15px;
}


.List_left {
  font-size: 18px;
  font-weight: 500;
}

.List_right {
  color: #0180f8;
  font-size: 18px;
  font-weight: 500;
}

::placeholder {
  color: #c8cbd2 !important;
}

.input_style:focus {
  outline: 1px solid #1890ff;
}

.dev-col {
  height: 500px;
  overflow-y: scroll;
}

.dev-col::-webkit-scrollbar {
  display: none;
}

.el-table__body-wrapper {
  z-index: 2
}

.el-upload--picture-card {
  width: 100%
}

.el-upload--text {
  width: 100%;
  text-align: center;
}

.avatar .el-upload--picture-card {
  width: 50%
}

.owner-avatar .el-upload {
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader2 .el-upload {
  /*border: 1px dashed #bebfc3;
  border-radius: 6px;*/
  cursor: pointer;
  position: relative;
  overflow: hidden;
  height: 180px;
}

.owner-avatar .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 400px;
  height: 200px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  height: 200px;
  width: 110%
}

.avatar2 {
  height: 200px;
  width: 110%
}

.peopleImg {
  height: 180px;
  width: 100%;
}

.peopleImg2 {
  height: 180px;
  width: 45%;
}

.headCenter {
  position: relative;
  button: 300px;
}

.table-div {
  height: 30vh;
  overflow-y: scroll;
  box-shadow: 0 -5px 5px #EAEAEA;
  padding: 15px 20px 20px 20px;
}

.table-div::-webkit-scrollbar {
  display: none;
}

.coverPhoto {
  width: 100px;
  heght: 100px;
}


</style>

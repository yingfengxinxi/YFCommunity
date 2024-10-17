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
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          v-if="total<=0"
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
      :data="list"
    >
      <el-table-column label="阿里云短信KEY" align="center" prop="accessKeyId"/>
      <el-table-column label="阿里云短信秘钥" align="center" prop="accessKeySecret"/>
      <el-table-column label="短信签名" align="center" prop="signName"/>
      <el-table-column label="短信签名" align="center" prop="region"/>
      <el-table-column label="是否使用" align="center" prop="status">
        <template slot-scope="scope">
              <el-switch
                v-model="scope.row.status"
                active-value="1"
                inactive-value="0"
                @change="handleStatusChange(scope.row)"
              ></el-switch>
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
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            >修改</el-button
          >
          <br>
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleFind(scope.row)"
            >设置短信模板</el-button
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

    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="80px"
        label-position="left"
      >
        <el-row>
          <el-col >
            <el-form-item label="KEY" prop="accessKeyId">
            <el-input v-model="form.accessKeyId" placeholder="请输入KEY" style="width:100%"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
          <el-col >
            <el-form-item label="秘钥" prop="accessKeySecret">
            <el-input v-model="form.accessKeySecret" placeholder="请输入秘钥" style="width:100%"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
          <el-col >
            <el-form-item label="签名" prop="signName">
            <el-input v-model="form.signName" placeholder="请输入签名" style="width:100%"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
          <el-col >
            <el-form-item label="地域" prop="region">
            <el-input v-model="form.region" placeholder="请输入地域" style="width:100%"/>
          </el-form-item>
        </el-col>
      </el-row>
      
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
    <el-dialog title="设置短信模板" :visible.sync="openSmsSend" width="90%" append-to-body>
      <send-sms  ref="sendSmsRef"/>
    </el-dialog>
  </div>
</template>

<script>
import {
  list,
  getById,
  insert,
  update
} from "@/api/estate/send/sendSetting";
import SendSms from './sendSms'; // 导入子组件
import {
  getCurrentList
} from "@/api/estate/community";
export default {
  name: "sendSetting",
  components: {
    SendSms ,
  },
  data() {
    return {
      currentList:[],
      openSmsSend:false,
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
      // 短信配置表格数据
      list: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        title: null,
        status: null,
        communityId:"",
      },
      // 表单参数
      form: {
        "releaseStatus":"0"
      },
      // 表单校验
      rules: {
        accessKeyId: [
          { required: true, message: "请输入阿里云短信KEY", trigger: "blur" },
        ],
        accessKeySecret: [
          { required: true, message: "请输入阿里云短信秘钥", trigger: "blur" },
        ],
        signName: [
          { required: true, message: "请输入阿里云短信签名", trigger: "blur" },
        ],
        region: [
          { required: true, message: "请输入阿里云地域", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    //所属小区
    getCurrentList().then(res => { 
      this.currentList = res.data;
    })
    this.getList();
  },
  methods: {
    handleFind(row) { 
      this.openSmsSend=true;
      this.$nextTick(() => {
        this.$refs.sendSmsRef.receiveValue(row.id)
      })     
    },
   
   // 状态修改
    handleStatusChange(row) {
      console.log("row>>", row);
      let text = row.status === "1" ? "使用" : "停用";
      this.$modal.confirm('确认要"' + text + '"当前发送短信配置吗？').then(function () {
        return update(row);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    /** 查询短信配置列表 */
    getList() {
      
      this.loading = true;
      list(this.queryParams).then((response) => {
        this.list = response.rows;
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
        accessKeyId:undefined,
        accessKeySecret:undefined,
        signName:undefined,
        status:undefined
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

    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.title = "添加短信配置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const id = row.id || this.ids;
      getById(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改短信配置";
      });
    },
    /** 提交按钮 */
    submitForm() {
     
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.id) {
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
  },
};
</script>

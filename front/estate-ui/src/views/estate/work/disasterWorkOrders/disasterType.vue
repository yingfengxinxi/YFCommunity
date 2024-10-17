<template>
  <div class="app-container">
    <div v-show="mainOpen">
      <el-form :model="queryParams" ref="queryForm" v-show="showSearch" :inline="true" class="cus-fm">
        <el-form-item label="灾害类型" prop="disasterName">
          <el-input
            v-model.trim="queryParams.disasterName"
            placeholder="请输入灾害类型"
            clearable
            size="small"
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="状态" prop="disasterStatus">
          <el-select
            v-model.trim="queryParams.disasterStatus"
            placeholder="状态"
            clearable
            size="small"
            style="width: 240px"
          >
<!--            <el-option
              v-for="dict in statusOptions"
              :key="dict.dictValue"
              :label="dict.dictLabel"
              :value="dict.dictValue"
            />-->
            <el-option value="0" label="启用"/>
            <el-option value="1" label="停用"/>
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
            v-hasPermi="['work:disasterLedger:add']"
          >新增</el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['work:disasterLedger:remove/{disasterId}']"
          >删除</el-button>
        </el-col>
            <!-- 用户导入对话框 -->
   <!-- <el-upload
            :show-file-list="false"
            :before-upload="beforeUpload"
            :on-success="onSuccess"
            :on-error="onError"
            :disabled="importDataDisabled"
            :headers="headers"
            action="/disasterLedger/importData"
          >
            <el-button
              :disabled="importDataDisabled"
              icon="el-icon-upload2"
              size="mini"
              type="success"
            >
              {{ importDataBtnText }}</el-button
            >
          </el-upload> -->

        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="disasterList" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="55" align="center" />
        <el-table-column label="灾害类型" prop="disasterName"/>
        <el-table-column label="状态" align="center" prop="disasterStatus" :formatter="statusFormatter">
        </el-table-column>
        <el-table-column show-overflow-tooltip label="说明" align="center" prop="remark"></el-table-column>
        <el-table-column label="最后操作人" align="center" prop="updateBy"></el-table-column>
        <el-table-column label="最后操作时间" align="center" prop="updateTime">
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['work:disasterLedger:update']"
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
    </div>
     <!-- 添加或修改灾害报事信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="600px" @close="cancel" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-row>
          <el-col :span="15" :offset="1">
            <el-form-item label="灾害类型" prop="disasterName">
              <el-input v-model="form.disasterName" style="width: 250px" placeholder="请输入灾害类型" maxlength="10" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="15" :offset="1">
            <el-form-item label="说明" style="margin-top:20px">
              <el-input v-model="form.remark" type="textarea" :rows="5" placeholder="请输入说明"></el-input>
            </el-form-item>
          </el-col>
        </el-row>
         <el-row>
          <el-col :span="15" :offset="1">
            <el-form-item label="状态" prop="disasterStatus">
          <el-switch
            v-model="form.disasterStatus"
            active-value='0'
            inactive-value='1'
            active-color="#13ce66"
            inactive-color="#ff4949">
          </el-switch>
        </el-form-item>
          </el-col>
        </el-row>

      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-row>
          <el-col :span="18">
            <el-button @click="cancel">取 消</el-button>
          </el-col>
          <el-col :span="4">
            <el-button type="primary" @click="submitForm"  :style="{ display: visibleLine }">确 定</el-button>
          </el-col>
        </el-row>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import {listDisasterLedger,updateBusDisaster,addBusDisaster,deleteBusDisaster} from "@/api/estate/work/orderDisaster/DisasterLedger";
export default {
  name: "PropRole",
  data() {
    return {
      visibleLine: '',  //隐藏
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
      mainOpen:true,
      // 总条数
      total: 0,
      // 角色表格数据
      disasterList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 状态数据字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        disasterName: undefined,
        disasterStatus: undefined
      },
      importDataBtnText:"导入",
      importDataDisabled: false,

      // 表单参数
      form: {},



      // 表单校验
      rules: {
        disasterName: [
          { required: true, message: "灾害类型不能为空", trigger: "blur" },
        ],
      }
    };
  },
  created() {
    this.getList();
     this.getDicts("sys_normal_disable").then(response => {
      this.statusOptions = response.data;
    });
  },
  methods: {
     /** 查询角色列表 */
    getList() {
      this.loading = true;
       listDisasterLedger(this.queryParams).then(response => {
        this.disasterList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /**
     * 新增
     */
    handleAdd(){
     this.reset();
      this.open = true;
      this.title = "新增物灾害报事类型";
    },

     /** 删除按钮操作 */
      handleDelete(row) {
        const disasterId = row.disasterId || this.ids;
        this.$modal.confirm('是否确认删除此灾害类型?(删除后此类型无法查询)').then(function() {
          return deleteBusDisaster(disasterId);
        }).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        }).catch(() => {});
      },
    /**
     * 修改
     */
    handleUpdate(row){
      this.reset();
      this.form = JSON.parse(JSON.stringify(row));
      this.open = true;
      this.title = "修改灾害报事类型";
    },
    /**
     * 导入
     */
      // handleImport(){},

      // /**
      //  * 查看
      //  */
      // handleInfo(data){
      //     this.form=data,
      //     this.open=true
      //     this.title ="灾害报事详情"
      //     this.visibleLine = 'none'
      // },
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        disasterId: undefined,
        estateId: undefined,
        disasterName: undefined,
        remark: undefined,
        disasterStatus: "0"
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
   /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.disasterId != undefined) {
            updateBusDisaster(this.form).then(response => {
               const {code,msg}=response
                if (code==200){
                   this.$modal.msgSuccess(msg);
              this.open = false;
              this.getList();
                }else {
                    this.$modal.msgError(msg)
                }

            });
          } else {
            addBusDisaster(this.form).then(response => {
              const {code,msg}=response
              if (code==200){
               this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
              }else {
                this.$modal.msgError(msg)
              }
            });
          }
        }
      });
    },
       // 多选框选中数据
      handleSelectionChange(selection) {
        this.ids = selection.map(item => item.disasterId);
        this.single = selection.length != 1;
        this.multiple = !selection.length;
      },
                /**
     * 后台返回的状态改变
     */
statusFormatter(row, column){
				const status = row.disasterStatus
      			if (status == 0) {
        			return '启用'
      			} else if (status == 1) {
        			return '停用'
            }
			}
    //    //处理导入事件
    // beforeUpload() {
    //   this.importDataBtnText = '正在导入';
    //   this.importDataDisabled = true;
    // },
    // //导入成功
    // onSuccess() {
    //   this.importDataBtnText = '导入数据';
    //   this.importDataDisabled = false;
    //   this.getList();
    // },
    // //导入失败
    // onError() {
    //   this.importDataBtnText = '导入数据';
    //   this.importDataDisabled = false;
    // },
  }
}
</script>

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
      <el-form-item label="短信类型" prop="templateType">
        <el-select v-model="queryParams.templateType" placeholder="请选择" style="width: 240px">
          <el-option 
            v-for="item in dict.type.template_type" 
            :key="item.value"
            :label="item.label" 
            :value="item.value">
          </el-option>
        </el-select>
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
      :data="list"
    >
      <el-table-column label="所属小区" align="center" prop="communityId">
        <template slot-scope="scope">
          {{ getCommunityName(scope.row.communityId) }}
        </template>
      </el-table-column>
      <el-table-column label="短信类型" align="center" prop="templateType" :formatter="templateTypeName"/>
      <el-table-column label="短信模板CODE" align="center" prop="templateCode"/>
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
            icon="el-icon-view"
            @click="handleUpdate(scope.row)"
            >修改</el-button
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
        label-width="110px"
        label-position="left"
      >
        <el-row>
          <el-col >
            <el-form-item label="所属小区" prop="communityId">
              <el-select :disabled="isDisabled" v-model="form.communityId" placeholder="请选择" style="width: 240px">
                <el-option
                  v-for="(item, index) in currentList"
                    :key="index"
                                :label="item.communityName"
                                :value="item.communityId"
                              ></el-option>
                            </el-select>
            </el-form-item>
        </el-col>
      </el-row>
      <el-row>
          <el-col >
            <el-form-item label="短信类型" prop="templateType">
              <el-select :disabled="isDisabled" v-model="form.templateType" placeholder="请选择" style="width: 240px">
                <el-option 
                  v-for="item in dict.type.template_type" 
                  :key="item.value"
                  :label="item.label" 
                  :value="item.value">
                </el-option>
              </el-select>
            </el-form-item>
        </el-col>
      </el-row>
      <el-row>
          <el-col >
            <el-form-item label="短信模板编号" prop="templateCode">
            <el-input v-model="form.templateCode"  style="width: 240px" placeholder="请输入短信模板编号"/>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
          <el-col >
            <el-form-item label="描述" prop="description">
            <el-input type="textarea" v-model="form.description" placeholder="请输入描述" style="width:240px"/>
          </el-form-item>
        </el-col>
      </el-row>
      
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
  list,
  getById,
  insert,
  update
} from "@/api/estate/send/sendSms";
import {
  getCurrentList
} from "@/api/estate/community";
export default {
  name: "sendSms",
  dicts:['template_type'],
  props: ['id'],
  data() {
    return {
      sendSettingId:"",
     
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      isDisabled:false,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 短信模板表格数据
      list: [],
      // 表单校验
      rules: {
        communityId: [
          { required: true, message: "请选择所属小区", trigger: "blur" },
        ],
        templateType: [
          { required: true, message: "请选择模板类型", trigger: "blur" },
        ],
        templateCode: [
          { required: true, message: "请输入模板编号", trigger: "blur" },
        ],
      },
     
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      currentList:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        templateType: undefined,
        communityId: undefined,
      },
      communityId:'',
      // 表单参数
      form: {
        "releaseStatus":"0"
      },
     
    };
  },
  created() {
    //所属小区
    getCurrentList().then(res => { 
      this.currentList = res.data;
    })
  },
  methods: {
    receiveValue(newVal) {
      console.log("newVal>>>", newVal);
      this.sendSettingId = newVal;
      this.getList();
    },
     
     templateTypeName(row, column){
      return this.selectDictLabel(this.dict.type.template_type,row.templateType);
    },
    getCommunityName(communityId) { 
     return this.currentList.filter(item => item.communityId == communityId)[0].communityName;
    },
   
   // 状态修改
    handleStatusChange(row) {
      console.log("row>>", row);
      let text = row.status === "1" ? "使用" : "停用";
      this.$modal.confirm('确认要"' + text + '"当前短信模板吗？').then(function() {
        return update(row);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
    /** 查询短信模板列表 */
    getList() {
      this.loading = true;
      this.queryParams.sendSettingId = this.sendSettingId;
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
        sendSettingId:undefined,
        communityId:undefined,
        templateType:undefined,
        templateCode:undefined,
        status:undefined,
        description:undefined

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
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.open = true;
      this.isDisabled = false;
      this.title = "添加短信模板";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.isDisabled = true;
      this.reset();
      const id = row.id || this.ids;
      getById(id).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改短信模板";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.form.sendSettingId = this.sendSettingId;
    
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
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除短信模板【"' + row.title + '"】吗？')
        .then(function () {
          return delTestingStandard(ids);
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

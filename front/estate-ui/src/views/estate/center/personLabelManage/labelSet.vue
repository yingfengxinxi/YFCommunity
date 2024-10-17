<template>
  <div class="app-container">
    <div v-show="openView === 0">
      <el-row :gutter="20">
        <!--   选择树   -->
        <el-col :span="4" :xs="24">
          <div class="head-container">
            <el-input
              placeholder="请输入小区名称"
              clearable
              size="small"
              prefix-icon="el-icon-search"
              v-model="filterText">
            </el-input>

            <el-tree
              class="filter-tree"
              :data="currentList"
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
          </div>
        </el-col>
        <!--   右侧   -->
        <el-col :span="20" :xs="24">
          <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="68px">
            <el-form-item label="人员姓名" prop="name">
              <el-input
                v-emoji
                placeholder="请输入姓名"
                clearable
                size="small"
                v-model="queryParams.name"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="手机号码" prop="phone">
              <el-input
                v-emoji
                placeholder="请输入手机号码"
                clearable
                size="small"
                v-model="queryParams.phone"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="身份证号" prop="cardNO">
              <el-input
                v-emoji
                placeholder="请输入身份证号"
                clearable
                size="small"
                v-model="queryParams.cardNo"
                @keyup.enter.native="handleQuery"
              />
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
                v-hasPermi="['center:personLabel:add']"
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
                v-hasPermi="['center:personLabel:delete']"
              >删除
              </el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
          </el-row>
          <el-table  ref="multipleTable"  :data="personLabelList" @selection-change="handleSelectionChange" show-overflow-tooltip>
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column label="姓名" align="center" prop="name"/>
            <el-table-column label="性别" align="center" prop="gender" >
              <template slot-scope="scope">
                <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.gender"/>
              </template>
            </el-table-column>
            <el-table-column label="手机号码" width="110"  align="center" prop="phone">
              <template slot-scope="scope">
                <span>{{ phoneFormatter(scope.row.phone) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="身份证号码" align="center"  width="160" prop="cardNo">
              <template slot-scope="scope">
                <span>{{ cardFormatter(scope.row.cardNo) }}</span>
              </template>
            </el-table-column>
            <!--        <el-table-column label="所属物业" align="center" prop="deptName"/>-->
            <el-table-column label="身份状态" align="center" prop="personType" >
              <template slot-scope="scope">
                <dict-tag :options="dict.type.log_user_type" :value="scope.row.personType"/>
              </template>
            </el-table-column>

            <el-table-column label="所属楼栋" align="center" prop="buildingName"/>
            <el-table-column label="户号" align="center" prop="unitName" />
            <el-table-column label="标签" align="center"  width="180" :show-overflow-tooltip="true" :formatter="labelFormat" />
            <!--<el-table-column label="标签" align="center" prop="" :show-overflow-tooltip="true"/>-->
            <el-table-column label="最后操作时间" align="center" prop="updateTime" width="180">
            </el-table-column>
            <el-table-column label="最后操作人" align="center" prop="updateBy"/>
            <el-table-column label="操作"  width="100" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['center:personLabel:update']"
                >修改</el-button>
                <el-button
                  class="text-danger"
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  style="color: #f56c6c"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['center:personLabel:delete']"
                >删除
                </el-button>
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
      <!-- 添加或修改变更申请对话框 -->
      <el-dialog :title="title" :visible.sync="open" width="500px" center append-to-body>
        <el-form ref="form" :model="form" style="padding: 0 50px"  label-width="140px" :rules="rules">
          <el-form-item v-if="isshow ==1" label="身份状态" prop="personType">
            <el-select v-model="form.personType" placeholder="请选择"  @change="getNameList()">
              <el-option
                v-for="dict in dict.type.log_user_type"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item v-if="isshow ==1"  label="姓名" prop="nameId">
            <el-select v-model="form.nameId" placeholder="请选择"  >
              <el-option
                v-for="item in nameList"
                :key="item.nameId"
                :label="item.name"
                :value="item.nameId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="年龄标签" prop="ageLabel">
            <el-select v-model="form.ageLabel" placeholder="请选择"  >
              <el-option
                v-for="dict in dict.type.sys_age_label"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="身份标签" prop="identityLabel">
            <el-select v-model="form.identityLabel" placeholder="请选择"  >
              <el-option
                v-for="dict in dict.type.sys_identity_label"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="身份健康标签" prop="healthyLabel">
            <el-select v-model="form.healthyLabel" placeholder="请选择"  >
              <el-option
                v-for="dict in dict.type.sys_healthy_label"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="新冠健康状态标签"  >
            <el-select v-model="form.coronavirus" placeholder="请选择"  >
              <el-option
                v-for="dict in dict.type.sys_coronavirus_label"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="安康码"  >
            <el-select v-model="form.healthyColor" placeholder="请选择"  >
              <el-option
                v-for="dict in dict.type.sys_healthy_color"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="行动标签"  >
            <el-select v-model="form.behaviorLabel" placeholder="请选择" >
              <el-option
                v-for="dict in dict.type.sys_behavior_label"
                :key="dict.value"
                :label="dict.label"
                :value="dict.value"
              />
            </el-select>
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>
  </div>

</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {addPersonLabel, getPersonLabelInfo, listPersonLabel, removePersonLabel, updatePersonLabel,listName} from
    "@/api/estate/center/personLabelManage/labelSet";
import {desensitization, equals} from "@/utils/common";

export default {
  name: "personLabel",
  dicts:['sys_user_sex','sys_age_label','sys_identity_label','sys_healthy_label','sys_coronavirus_label','sys_healthy_color','sys_behavior_label','log_user_type'],
  data(){
    return{
      openView:false,
      // 遮罩层
      loading: true,
      // 表单校验
      rules: {
        personType: [{required: true, message: '身份状态不能为空', trigger: 'blur'}],
        nameId: [{required: true, message: '姓名不能为空', trigger: 'blur'}],
        ageLabel: [{required: true, message: '年龄标签不能为空', trigger: 'blur'}],
        identityLabel: [{required: true, message: '身份标签不能为空', trigger: 'blur'}],
        healthyLabel: [{required: true, message: '身份健康标签不能为空', trigger: 'blur'}],


      },

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
      personLabelList: [],
      nameList: [],
      classifyList:[],
      //小区数据集合
      currentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 树筛选文本
      filterText:'',
      //树结构
      comOptions:[],
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        name:null,
        phone:null,
        cardNo:null,
        personType:null,
        //passageId:undefined,
      },
      // 表单参数
      form: {},
      isshow:1
    }
  },
  created() {
    this.getTree();
    //this.getList();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods:{
    labelFormat(row){
      let ageLabel = this.selectDictLabel(this.dict.type.sys_age_label, row.ageLabel);
      let identityLabel = this.selectDictLabel(this.dict.type.sys_identity_label, row.identityLabel);
      let healthyLabel = this.selectDictLabel(this.dict.type.sys_healthy_label, row.healthyLabel);
      let coronavirus = this.selectDictLabel(this.dict.type.sys_coronavirus_label, row.coronavirus);
      let healthyColor = this.selectDictLabel(this.dict.type.sys_healthy_color, row.healthyColor);
      let behaviorLabel = this.selectDictLabel(this.dict.type.sys_behavior_label, row.behaviorLabel);
      let array = [ageLabel,identityLabel,healthyLabel,coronavirus,healthyColor,behaviorLabel];
      return array.join(",");
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(row){
      this.queryParams.communityId = row.communityId
      this.communityId = row.communityId
      this.getList();
    },
    // 获取树数据
    getTree(){
      getCurrentList().then(res =>{
        this.currentList = res.data;
        this.queryParams.communityId = this.currentList[0].communityId;
        this.communityId = this.currentList[0].communityId;
        this.getList();
        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.communityId);
        });
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 新增按钮
    handleAdd(){
      this.reset();
      this.open = true;
      this.title = "新增个人标签"
      this.isshow = 1
    },
    /** 编辑按钮 */
    handleUpdate(row) {
      getPersonLabelInfo(row.labelId).then(resp => {
        this.form = resp.data
      })
      this.open = true;
      this.isshow = 0
      this.title = "编辑个人标签";
    },
    /** 删除按钮 */
    handleDelete(row) {
      const labelIds = row.labelId || this.ids;
      this.$modal.confirm('是否确认删除所选中的数据项?').then(function() {
        return removePersonLabel(labelIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 提交按钮 */
    submitForm: function () {
      //this.form.createBy = "admin";
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.labelId != null) {
            updatePersonLabel(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.communityId = this.communityId;
            addPersonLabel(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 表单重置*/
    reset() {
      this.form = {};
    },
    /** 搜索按钮操作 */
    handleQuery() {
      // this.getTree();
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      //this.handleQuery();
      this.resetForm("queryForm");
      this.queryParams.cardNo = null;
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.labelId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 查询列表 */
    getList() {
      this.openView = 0;
      this.loading = false;
      listPersonLabel(this.queryParams).then(response => {
        this.personLabelList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getNameList() {
      this.queryParams.personType = this.form.personType;
      listName(this.queryParams).then(response => {
        this.nameList = response.rows;
        //this.total = response.total;
        this.loading = false;
      });
    },

    /** 手机号脱敏 */
    phoneFormatter(phone){
      if(!equals(phone,undefined)){
        return desensitization(phone,3,7);
      }
    },

    /** 身份证号脱敏 */
    cardFormatter(card){
      if(!equals(card,undefined)){
        return desensitization(card,6,14);
      }
    },
  }
}
</script>

<style scoped>
/*设置高亮颜色*/
/deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #b3f1b6 !important;
}
</style>

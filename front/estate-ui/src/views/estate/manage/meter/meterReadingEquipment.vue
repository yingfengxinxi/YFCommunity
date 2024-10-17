<template>
  <div class="app-container">
    
      <el-row :gutter="20">
        <!--小区数据-->
        <el-col :span="4">
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
              :expand-on-click-node="false"
              default-expand-all
              :filter-node-method="filterNode"
              ref="tree"
              highlight-current
              node-key="communityId"
              @node-click="handleNodeClick"
              >
            </el-tree>
          </div>
        </el-col>
       
        <el-col :span="20" :xs="24">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="82px">
           
            <el-form-item label="能耗类型" prop="energyConsumptionType">
              <el-select
                v-model="queryParams.energyConsumptionType"
                placeholder="请选择能耗类型"
                clearable
                style="width: 160px"
                @change="getMeterReadingTypeOptions(queryParams.energyConsumptionType)"
              >
                <el-option
                  v-for="item in dict.type.energy_consumption_type"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value"
                />
              </el-select>
            </el-form-item>
            
            <el-form-item label="设备类型" prop="meterReadingType">
              <el-select
                v-model="queryParams.meterReadingType"
                placeholder="请选择设备类型"
                clearable
                style="width: 160px"
              >
                <el-option
                  v-for="item in meterReadingTypeOptions"
                  :key="item.dictValue"
                  :label="item.dictLabel"
                  :value="item.dictValue"
                />
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
          >新增</el-button
        >
      </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

          <el-table v-loading="loading" :data="list">
            <el-table-column label="设备编号" align="center" prop="equipmentNo"/>
            <el-table-column label="能耗类型" align="center" prop="energyConsumptionType" :formatter="ect"/>
            <el-table-column label="设备类型" align="center" prop="meterReadingType" :formatter="mrt"/>
            <el-table-column label="生成工单状态" align="center" prop="status">
              <template slot-scope="scope">
                <el-switch
                  v-model="scope.row.status"
                  active-value="1"
                  inactive-value="0"
                  @change="handleStatusChange(scope.row)"
                ></el-switch>
              </template>
            </el-table-column>
            <el-table-column label="创建时间" align="center" key="createTime" prop="createTime"/>
          
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
                  @click="handleDetail(scope.row)"
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

    <el-dialog :title="title" :visible.sync="open" width="40%" append-to-body>
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="110px"
        label-position="left"
      >
        <el-row>
          <el-col :span="10" :offset="4">
            <el-form-item label="设备编号" prop="equipmentNo">
              <el-input  style="width: 160px" v-model="form.equipmentNo" placeholder="请输入设备编号"/>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10" :offset="4">
            <el-form-item label="能耗类型" prop="energyConsumptionType">
              <el-select
                  v-model="form.energyConsumptionType"
                  placeholder="请选择能耗类型"
                  clearable
                  :disabled="isDisabled"
                  style="width: 160px"
                  @change="getMeterReadingTypeOptions(form.energyConsumptionType)"
                >
                  <el-option
                    v-for="item in dict.type.energy_consumption_type"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value"
                  />
                </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10" :offset="4">
            <el-form-item label="类型" prop="meterReadingType">
              <el-select
                  v-model="form.meterReadingType"
                  placeholder="请选择类型"
                  clearable
                  style="width: 160px"
                  :disabled="isDisabled"
                >
                  <el-option
                    v-for="item in meterReadingTypeOptions"
                    :key="item.dictValue"
                    :label="item.dictLabel"
                    :value="item.dictValue"
                  />
                </el-select>
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
  listMeterReadingEquipment,
  getMeterReadingEquipmentDetail,
  add,
  update,
  removeById,
  updateByIdStatus
} from "@/api/estate/manage/meter/meterReadingEquipment";
import { getCurrentList } from "@/api/estate/community";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
export default {
  name: "meterReadingEquipment",
  components: { Treeselect },
  dicts: ['energy_consumption_type','meter_reading_type'],
  data() {
    return {
      energyConsumptionTypeOptions: [],
      executorByOptions: [],
      frequencyOptions: [],
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      active: 1,
      executionNumberMax: 1,
      meterReadingTypeOptions:[],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      //主页面展示
      openView: true,
      //详情页是否展示
      detailShow: false,
      //详情表单数据
      detailForm: {},
      violationList: [],
      dateRange:[],
      // 树筛选文本
      filterText:'',
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 装修表格数据
      list: null,
      //装修工信息
      worker: {},
      firstExecutionTimes:[],
      //小区数据集合
      currentList: [],
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      isDisabled:false,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      rules: {
        equipmentNo:[
            { required: true, message: "设备编号不能为空", trigger: "blur" },
        ],
        energyConsumptionType: [
            { required: true, message: "请选择能耗类型", trigger: "blur" },
        ],
        meterReadingType:[
            { required: true, message: "请选择类型", trigger: "blur" },
        ]
      },
 
      // 表单参数
      form: {
        equipmentNo: undefined,
        energyConsumptionType: undefined,
        meterReadingType: undefined,
        communityId:undefined
      },

      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        equipmentNo: undefined,
        energyConsumptionType: undefined,
        meterReadingType:undefined
      },
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    this.getTree();
  },
  methods: {
    ect(row) { 
      return this.selectDictLabel(this.dict.type.energy_consumption_type, row.energyConsumptionType)
    },
    mrt(row) { 
      return this.selectDictLabel(this.dict.type.meter_reading_type, row.meterReadingType)
    },
    getMeterReadingTypeOptions(energyConsumptionType) {
      this.queryParams.meterReadingType = undefined;
      this.form.meterReadingType = undefined;
      this.getDicts("meter_reading_type").then(response => {
        var meterReadingTypeOptions = response.data;
        this.meterReadingTypeOptions=meterReadingTypeOptions.filter(item => item.remark == energyConsumptionType);
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(row){
      this.queryParams.communityId = row.communityId
      this.form.communityId = row.communityId
      this.getList();
    },
    
    handleStatusChange(row) {
      console.log("row>>", row);
      let text = row.status === "1" ? "启用" : "停用";
      this.$modal.confirm('确认要"' + text + '"能耗类型吗？').then(function() {
        return updateByIdStatus(row.id,row.status);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess(text + "成功");
      }).catch(function() {
        row.status = row.status === "0" ? "1" : "0";
      });
    },
     /** 删除按钮操作 */
     handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除当前抄表设备点吗？')
        .then(function () {
          return removeById(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.form.communityId=this.queryParams.communityId;
          if (this.form.id) {
            update(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            add(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    
    getCurrentList(val) { 
      if (val) { 
        var obj = this.currentList.filter(item=>item.communityId == val)[0];
        if (obj) { 
          return obj.communityName;
        }
      }
    },
    /** 查询装修列表 */
    getList() {
      this.loading = true;
      listMeterReadingEquipment(this.queryParams).then(response => {
          this.list = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        equipmentNo: undefined,
        energyConsumptionType: undefined,
        meterReadingType: undefined,
        communityId:undefined
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
      this.meterReadingTypeOptions = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },

    // 获取树数据
    getTree(){
      getCurrentList().then(res =>{
        this.currentList = res.data;
        this.queryParams.communityId = this.currentList[0].communityId;
        this.getList();
        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.currentList[0].communityId);
        });
      })
    },

    /** 增加按钮操作 */
    handleAdd() {
      this.reset();
      this.title = "增加抄表设备信息";
      this.open = true
      this.isDisabled = false;
    },

    /** 修改按钮操作 */
    handleDetail(row) {
      const id = row.id || this.ids
      getMeterReadingEquipmentDetail(id).then(response => {
        this.form = response.data;
        this.isDisabled = true;
      });
      this.title = "修改抄表设备信息";
      this.open = true
    },

    //关闭详情页
    canceldetail() {
      this.detailForm = {}
      this.detailShow = false
      this.openView = true;
    }
  }
};
</script>

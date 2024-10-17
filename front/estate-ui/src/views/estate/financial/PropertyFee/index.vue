<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">

      <el-form-item label="所属小区:" label-width="90px" prop="communityId">
        <el-select v-model="queryParams.communityId" placeholder="请选择">
          <el-option
            v-for="(item, index) in currentList"
            :key="index"
            :label="item.communityName"
            :value="item.communityId"
            @keyup.enter.native="handleQuery"
          ></el-option>
        </el-select>
      </el-form-item>

      <el-form-item label="收费状态" prop="feeStatus">
          <el-select v-model="queryParams.feeStatus" placeholder="请选择收费状态">
            <el-option label="开启" value="0"></el-option>
            <el-option label="暂停" value="1"></el-option>
          </el-select>
<!--        <el-input-->
<!--          v-model="queryParams.feeStatus"-->
<!--          placeholder="请输入收费状态"-->
<!--          clearable-->
<!--          @keyup.enter.native="handleQuery"-->
<!--        />-->
      </el-form-item>

      <el-form-item label="收费类型:" label-width="100px" prop="feeType">
        <el-select v-model="queryParams.feeType" placeholder="请选择">
          <el-option
            v-for="dict in dict.type.fee_type"
            :key="dict.value"
            :label="dict.label"
            :value="dict.value"
            @keyup.enter.native="handleQuery"
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
         
        >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="feeList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center"/>
      <el-table-column label="所属小区" align="center" prop="communityId">
        <template slot-scope="scope">
          {{ getCommunityName(scope.row.communityId) }}
        </template>
      </el-table-column>
      <el-table-column label="收费状态" align="center" key="feeStatus">
        <template slot-scope="scope">
          <el-switch
            v-model="scope.row.feeStatus"
            active-value= "0"
            inactive-value= "1"
            @change="handleStatusChange(scope.row)"
          ></el-switch>
        </template>
      </el-table-column>
      <el-table-column label="收费类型" align="center" prop="feeType" :formatter="lx"/>
      <el-table-column label="费用" align="center" prop="feeAmount"/>
      <!-- <el-table-column label="房屋属性" align="center" prop="roomType" :formatter="rt"/> -->
      <el-table-column label="缴费方式" align="center" prop="payMethod" :formatter="pm"/>
      <el-table-column label="适用开始时间" align="center" prop="startTime"/>
      <el-table-column label="适用结束时间" align="center" prop="endTime"/>
      <el-table-column label="创建人" align="center" prop="createBy"/>
      <el-table-column label="创建时间" align="center" prop="createTime"/>
      <el-table-column label="修改人" align="center" prop="updateBy"/>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width" fixed="right">
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            v-if="scope.row.isUpdate=='0'"
            @click="handleUpdate(scope.row)"
          >修改
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

    <!-- 添加或修改物业缴费设置对话框 -->
    <el-dialog :title="title" :visible.sync="opentj" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="134px">
            <el-form-item label="所属小区:"  prop="communityId">
              <el-select v-model="form.communityId" :disabled="isDisabled" placeholder="请选择" @change="selectFloor(form.communityId,'0')">
                <el-option
                  v-for="(item, index) in currentList"
                  :key="index"
                  :label="item.communityName"
                  :value="item.communityId"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="所属楼栋:"  prop="buildingId">
              <el-select
                        v-model="form.buildingId"
                        clearable
                        placeholder="请选择所属楼栋:"
                        multiple
                      >
                        <el-option
                          v-for="item in buildingData"
                          :key="item.buildingId+''"
                          :label="item.buildingName"
                          :value="item.buildingId+''"
                          :disabled="item.flag=='1'"
                        >
                        </el-option>
                      </el-select>
            </el-form-item>
            <el-form-item label="收费类型:" prop="feeType">
              <el-select v-model="form.feeType" placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.fee_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <!-- <el-form-item label="房屋属性:" prop="roomType">
              <el-select v-model="form.roomType" placeholder="请选择">
                <el-option
                  v-for="dict in dict.type.property_of_house"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item> -->
            <el-form-item label="费用:" prop="feeAmount">
              <el-input-number v-model="form.feeAmount" :min="0" :max="9999999" :precision="2" :step="0.1"></el-input-number>
            </el-form-item>
            <el-form-item label="缴费方式:" prop="payMethod">
              <el-select v-model="form.payMethod" placeholder="请选择">
                <el-option
                v-for="dict in dict.type.pay_method"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="缴费适用开始时间" prop="startTime">
            
              <el-date-picker
                  v-model="form.startTime"
                  type="date"
                  placeholder="选择日期">
                </el-date-picker>
            
            </el-form-item>
            <el-form-item label="缴费适用结束时间" prop="endTime">
                <el-date-picker
                  v-model="form.endTime"
                  type="date"
                  placeholder="选择日期">
                </el-date-picker>
            </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submittj">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import {addFee, updatePropertyFeeStatus, getFee, listFee, updateFee} from "@/api/estate/financial/PropertyFee";
import {getPropertyFeeBuilding, getCurrentList} from "@/api/estate/community";
export default {
  name: "Fee",
  dicts: ['fee_type','pay_method','property_of_house'],
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
      // 楼栋下拉框
      buildingData: [],
      // 物业缴费设置表格数据
      feeList: [],
      //1=安置房，2=商品房，3=公租房
      roomTypeOptions: [
        // { "value": "1", label: "安置房" },
        // { "value": "2", label: "商品房" },
        // {"value":"3",label:"公租房"},
      ],
      //缴费方式:0=按月缴费1=按季缴费2=按半年缴费3=按年缴费
      payMethodOptions: [
       
      ],
      isDisabled:false,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      opentj :false,
      openxg :false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: null,
        feeStatus: null,
        feeType: null,
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        communityId: [
          {required: true, message: "请选择所属小区", trigger: "blur"},
        ],
        buildingId: [
          {required: true, message: "请选择所属楼栋", trigger: "blur"},
        ],
        
        feeType: [
          {required: true, message: "请选择收费类型", trigger: "blur"},
        ],
        roomType: [
          {required: true, message: "请选择收费类型", trigger: "blur"},
        ],
        
        feeAmount:[
          {required: true,
            message: "请输入正确费用",
            trigger: "blur",
            pattern: /^([0-9]+|[0-9]{1,3}(,[0-9]{3})*)(.[0-9]{1,2})?$/
          },
        ],
        payMethod: [
          {required: true, message: "请选择缴费方式", trigger: "blur"},
        ],
        startTime: [
          {required: true, message: "请选择缴费适用开始时间", trigger: "blur"},
        ],
        endTime: [
          {required: true, message: "请选择缴费适用开始时间", trigger: "blur"},
        ],
      }
    };
  },
  created() {
    this.getList();
    this.getCurrent();
  },
  methods: {

    lx(row, column) {
      return this.selectDictLabel(this.dict.type.fee_type, row.feeType)
    },
    rt(row, column) { 
      return this.selectDictLabel(this.dict.type.property_of_house, row.roomType)
    },
    pm(row, column) {
      return this.selectDictLabel(this.dict.type.pay_method, row.payMethod)
    },
    getCurrent(){
      getCurrentList().then(res =>{
        this.currentList = res.data;
      })
    },
    getCommunityName(communityId) { 
      var obj = this.currentList.filter(item => item.communityId == communityId)[0];
      if(obj!= null){ 
        return obj.communityName;
      }
    },
    // 根据小区ID查楼栋
    selectFloor(communityId,flag) {
      getPropertyFeeBuilding(communityId).then((response) => {
        this.buildingData = response.data;
        if (flag=='0') {
          this.form.buildingId = undefined;
        }
      });
    },
    /** 查询物业缴费设置列表 */
    getList() {
      this.loading = true;
      listFee(this.queryParams).then(response => {
        this.feeList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.openxg = false
      this.opentj = false
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        communityId: undefined,
        buildingId:undefined,
        feeStatus: undefined,
        feeType: undefined,
        feeAmount: undefined,
        roomType: undefined,
        payMethod: undefined,
        startTime: undefined,
        endTime:undefined,
        createBy: undefined,
        createTime: undefined,
        updateBy: undefined,
        updateTime: undefined
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
      this.ids = selection.map(item => item.communityId)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.isDisabled = false;
      this.reset();
      this.opentj = true;
      this.title = "添加物业缴费设置";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      getFee(row.propertyFeeId).then(response => {
        if (response.code == 200) { 
            this.form = response.data;
            if (this.form.buildingId) { 
              this.form.buildingId = this.form.buildingId.split(",");
            }
            this.isDisabled = true;
            this.selectFloor(this.form.communityId,'1');
            this.opentj = true;
            this.title = "修改物业缴费设置";
        }
      });
    },
  

    submittj(){
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.buildingId) { 
            this.form.buildingId = this.form.buildingId + '';
          }
         
            if (this.form.propertyFeeId) {
              updateFee(this.form).then(response => {
                if (response.code == 200) {
                  this.$modal.msgSuccess("修改成功");
                  this.opentj = false;
                  this.getList();
                } else if (response.code == 510) { 
                  this.$modal.msgError(response.msg);
                  if (this.form.buildingId) { 
                    this.form.buildingId = this.form.buildingId.split(",");
                  }
                }
              });
            } else { 
              addFee(this.form).then(response => {
                if (response.code == 200) {
                  this.$modal.msgSuccess("新增成功");
                  this.opentj = false;
                  this.getList();
                } else if (response.code == 510) { 
                  this.$modal.msgError(response.msg);
                  if (this.form.buildingId) { 
                    this.form.buildingId = this.form.buildingId.split(",");
                  }
                }
              });
            }
          
        }
      })
    },


    handleStatusChange(row) {
      this.form.propertyFeeId = row.propertyFeeId
      this.form.feeStatus = row.feeStatus
      let text = row.feeStatus === "0" ? "启动" : "暂停";
      this.$confirm('确定要'+text+'当前类型的物业缴费吗?', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        updatePropertyFeeStatus(this.form).then(res =>{
          if (res.code == 200){
            this.$message.success("修改成功")
            this.getList();
          }
        });
      }).catch(() => {
        row.feeStatus = row.feeStatus === "0" ? "1" : "0";
      });
    },
  }
};
</script>

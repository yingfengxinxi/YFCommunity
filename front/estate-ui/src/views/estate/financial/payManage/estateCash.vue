<template>
  <div class="app-container">
    <div v-show="openView === 0">
      <el-row :span="24">
        <!--   选择树   -->
        <el-col :span="3">
          <div class="head-container">
            <el-input
              placeholder="请输入小区名称"
              v-model="filterText">
            </el-input>
            <el-tree
              class="filter-tree hide-tree"
              :data="currentList"
              :props="currentListProps"
              highlight-current
              node-key="communityId"
              default-expand-all
              :filter-node-method="filterNode"
              icon-class="el-icon-star-on"
              @node-click="handleNodeClick"
              ref="tree">
            </el-tree>
          </div>
        </el-col>
        <!--   右侧   -->
        <el-col :span="20" :xs="24" :offset="1">
          
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                   label-width="68px">
            <el-form-item label="所属楼栋" prop="buildingId">
                  <el-select v-model="queryParams.buildingId" placeholder="请选择所属楼宇" style="width: 180px"
                             @change="getUnits">
                    <el-option
                      v-for="dict in buildingList"
                      :key="dict.buildingId"
                      :label="dict.buildingName"
                      :value="dict.buildingId"
                    ></el-option>
                  </el-select>
          </el-form-item>
          <el-form-item label="所属单元" prop="unitId">
                  <el-select v-model="queryParams.unitId" placeholder="请选择所属单元" style="width: 180px"
                             @change="getRooms">
                    <el-option
                      v-for="dict in unitList"
                      :key="dict.unitId"
                      :label="dict.unitName"
                      :value="dict.unitId"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="所属房间" prop="roomId">
                  <el-select v-model="queryParams.roomId" placeholder="请选择所属房间" style="width: 180px" @change="getRoomId()">
                    <el-option
                      v-for="dict in roomList"
                      :key="dict.roomId"
                      :label="dict.roomName"
                      :value="dict.roomId"
                    ></el-option>
                  </el-select>
                </el-form-item>
            <el-form-item label="账单状态" prop="payStatus">
              <el-select v-model="queryParams.payStatus" placeholder="请选择账单状态" style="width: 180px">
                <el-option
                v-for="dict in payStatusOptions"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="支付方" prop="isUnpaidPropertyFee">
              <el-select v-model="queryParams.isUnpaidPropertyFee" placeholder="请选择支付方" style="width: 180px">
                <el-option
                v-for="dict in isUnpaidPropertyFeeOptions"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            
            <el-form-item label="收费类型" prop="feeType">
              <el-select v-model="queryParams.feeType" placeholder="请选择收费类型" style="width: 180px">
                <el-option
                v-for="dict in dict.type.fee_type"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="缴费方式" prop="payMethod">
              <el-select v-model="queryParams.payMethod" placeholder="请选择缴费方式" style="width: 180px">
                <el-option
                v-for="dict in dict.type.pay_method"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="缴费适用" prop="payYear">
              <el-date-picker
                style="width: 180px"
                v-model="queryParams.payYear"
                value-format="yyyy"
                type="year"
                placeholder="选择年">
              </el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" float=right @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button
                type="primary"
                plain
                icon="el-icon-plus"
                size="mini"
                :disabled="multiple"
                @click="bachUpdatePayStatus()"
                >批量发送账单</el-button
              >
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="primary"
                plain
                icon="el-icon-plus"
                size="mini"
                :disabled="multiple"
                @click="handleBachSms('0')"
                >批量发送站内消息</el-button
              >
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="primary"
                plain
                icon="el-icon-plus"
                size="mini"
                :disabled="multiple"
                @click="handleBachSms('1')"
                >批量发送短信消息</el-button
              >
            </el-col>
          </el-row>
          <el-table v-loading="loading" :data="list" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column label="楼栋" align="center" prop="buildingName"/>
            <el-table-column label="单元楼" align="center" prop="unitName"/>
            <el-table-column label="房号" align="center" prop="roomName"/>
            <el-table-column label="业主姓名" align="center" prop="ownerName">
              <template slot-scope="scope">
                {{ scope.row.isUnpaidPropertyFee=='1'?'-':scope.row.ownerName }}
              </template>
            </el-table-column>
            <el-table-column label="业主联系方式" align="center" prop="ownerPhone">
              <template slot-scope="scope">
                {{ scope.row.isUnpaidPropertyFee=='1'?'-':scope.row.ownerPhone }}
              </template>
            </el-table-column>
            <el-table-column label="房屋面积(㎡)" align="center" prop="roomArea"/>
            <el-table-column label="支付方" align="center" prop="isUnpaidPropertyFee">
              <template slot-scope="scope">
                {{ getIsUnpaidPropertyFeeName(scope.row.isUnpaidPropertyFee) }}
              </template>
            </el-table-column>
            
            <el-table-column label="收费类型" align="center" prop="feeType" :formatter="lx"/>
            <el-table-column label="缴费方式" align="center" prop="payMethod" :formatter="pm"/>
            <el-table-column label="收费标准(元)" align="center" prop="feeAmount"/>
            <el-table-column label="应缴金额(元)" align="center" prop="yjAmount"/>
            <el-table-column label="优惠金额(元)" align="center" prop="discountAmount"/>
            <el-table-column label="已缴金额(元)" align="center" prop="payAmount"/>
            <el-table-column label="账单状态" align="center" prop="payStatus">
              <template slot-scope="scope">
                {{ getPayStatusName(scope.row.payStatus) }}
              </template>
            </el-table-column>
            <el-table-column label="缴费周期" align="center" >
              <template slot-scope="scope">
                {{ scope.row.startTime }}至{{ scope.row.endTime }}
              </template>
            </el-table-column>
            <el-table-column label="付款时间" align="center" prop="payTime"/>
            <el-table-column label="通知类型" align="center" prop="sendType">
              <template slot-scope="scope">
                {{ scope.row.sendType==null?'--':scope.row.sendType=='0'?'站内消息':'短信通知' }}
              </template>
            </el-table-column>
            <el-table-column label="通知状态" align="center" prop="sendStatus">
              <template slot-scope="scope">
                {{ getStatusName(scope.row.sendStatus) }}
              </template>
            </el-table-column>
            <el-table-column label="短信失败原因" align="center" prop="sendErrorMsg" :show-overflow-tooltip="true"/>
            <el-table-column
              label="操作"
              align="center"
              class-name="small-padding fixed-width"
              fixed="right"
            >
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  v-if="scope.row.payStatus=='0'"
                  @click="handleUpdate(scope.row)"
                  >修改金额</el-button
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
        </el-col>
      </el-row>
    </div>
    <!-- 添加或修改活动对话框 -->
  <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="150px"
      >
      <el-form-item label="缴费金额" prop="yjAmount">
        <el-input-number disabled placeholder="请输入缴费金额" v-model="form.yjAmount" :min="0" :max="999999" :precision="2" :step="0.1"  style="width:217px" @input="getPaidAmount(form.yjAmount,form.discountAmount)"></el-input-number>
      </el-form-item>
      <el-form-item label="优惠金额" prop="discountAmount">
        <el-input-number placeholder="请输入优惠金额" v-model="form.discountAmount" :min="0" :max="999999" :precision="2" :step="0.1"  style="width:217px" @input="getPaidAmount(form.yjAmount,form.discountAmount)"></el-input-number>
      </el-form-item>
      <el-form-item label="实缴金额" prop="paidAmount">
        <el-input-number disabled placeholder="请输入实缴金额" v-model="form.paidAmount" :min="0" :max="999999" :precision="2" :step="0.1"  style="width:217px"></el-input-number>
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
  getCurrentList,
  getBuildingList,
  getUnitList,
  getRoomList
} from '@/api/estate/community'
import {
  propertyLogList,
  getInfoById,
  ownerList,
  payList,
  sendSms,
  bachUpdatePayStatus,
  updateByLogIdAmount
} from "@/api/estate/financial/estateCash";

export default {
  name: "estateCash",
  dicts: ['fee_type','pay_method','property_of_house'],
  data() {
    return {
      openView: false,
      // 遮罩层
      loading: true,
      // 表单校验
      rules: {discountAmount: [{required: true, message: '优惠金额不能为空', trigger: 'blur'}],},
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
      payStatusOptions: [
        { "value": "0", "label": "账单待发送" },
        { "value": "1", "label": "待支付" },
        {"value":"2","label":"已支付"},
      ],
      isUnpaidPropertyFeeOptions: [
        { "value": "0", "label": "业主" },
        { "value": "1", "label": "物业" }
      ],
      // 表格数据
      activiteList: [],
      classifyList: [],
      list: [],
      ownerList: [],
      ownerPayList: [],
      //小区数据集合
      currentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //楼栋数据
      buildingList: [],
      //单元
      unitList: [],
      //房间数据
      roomList: [],
      // 树筛选文本
      filterText: '',
      //树结构
      comOptions: [],
      communityId: undefined,
      buildingId: undefined,
      payYear: undefined,
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        buildingId: undefined,
        unitId: undefined,
        roomId: undefined,
        payYear: new Date().getFullYear()+'',
        typeId: undefined,
        typeName: undefined,
      },
      // 表单参数
      form: {},
      
    }
  },
  created() {
    this.getTree();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(row) {
      this.queryParams.communityId = row.communityId
      this.communityId = row.communityId
      this.getList();
    },
    lx(row, column) {
      return this.selectDictLabel(this.dict.type.fee_type, row.feeType)
    },
    rt(row, column) { 
      return this.selectDictLabel(this.dict.type.property_of_house, row.roomType)
    },
    pm(row, column) {
      return this.selectDictLabel(this.dict.type.pay_method, row.payMethod)
    },
    getStatusName(status) { 
      if (status == '0') { 
        return "待发送";
      }
      if (status == '1') { 
        return "发送成功";
      }
      if (status == '2') { 
        return "发送失败";
      }
    },
    // 获取树数据
    getTree() {
      getCurrentList().then(res => {
        this.currentList = res.data;
        this.currentList.length > 0 ? this.queryParams.communityId = this.currentList[0].communityId : false;
        this.communityId = this.queryParams.communityId;
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.currentList[0].communityId);
        });
        this.getBuildings(this.communityId);
        this.getList();
      })
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    getPaidAmount(payAmount,discountAmount){ 
      if (!payAmount) { 
        payAmount = parseFloat(0.00);
      }
      if (!discountAmount) { 
        discountAmount = parseFloat(0.00);
      }
      if (payAmount > 0) { 
        console.log("payAmount>>", payAmount);
        console.log("discountAmount>>", discountAmount);
        console.log(parseFloat(discountAmount) > parseFloat(payAmount))
        if (parseFloat(discountAmount) > parseFloat(payAmount)) {
          this.$modal.msgWarning("请输入正确的优惠金额");
          this.form.discountAmount = undefined;
        } else { 
          this.form.paidAmount = payAmount - discountAmount;
        }
        
        this.$forceUpdate();
      }
    },
    handleUp2(row) {
      this.openView = 1;
      this.queryParams.communityId = row.communityId;
      this.communityId = row.communityId;
      this.queryParams.buildingId = row.buildingId;
      this.buildingId = row.buildingId;
      this.queryParams.payYear = row.payYear;
      this.payYear = row.payYear;
      ownerList(this.queryParams).then(response => {
        this.ownerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    handlesee(row) {
      this.openView = 2;
      this.queryParams.communityId = row.communityId;
      this.queryParams.buildingId = row.buildingId;
      this.queryParams.roomId = row.roomId;
      this.queryParams.unitId = row.unitId;
      getInfoById(this.queryParams).then(response => {
        this.form = response.data;
      })
      payList(this.queryParams).then(response => {
        this.ownerPayList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    //楼栋(社区触发)
    getBuildings(communityId) {
      this.queryParams.buildingId = undefined;
      this.queryParams.unitId = undefined;
      this.queryParams.roomId = undefined;
     
      getBuildingList(communityId).then(response => {
        this.buildingList = response.data;
      })
    },
    //获取单元
    getUnits(buildingId) {
      this.queryParams.roomId = undefined;
      this.queryParams.unitId = undefined;
      getUnitList(buildingId).then(response => {
        this.unitList = response.data;
      })
    },
    //房号
    getRooms(unitId) {
      this.queryParams.roomId = undefined;
        getRoomList(unitId).then(response => {
          this.roomList = response.data;
        })
    },
    getRoomId() { 
      this.$forceUpdate();
    },
    getPayStatusName(payStatus) { 
     return this.payStatusOptions.filter(item => item.value == payStatus)[0].label;
    },
    getIsUnpaidPropertyFeeName(isUnpaidPropertyFee) { 
     return this.isUnpaidPropertyFeeOptions.filter(item => item.value == isUnpaidPropertyFee)[0].label;
    },
    
  
    /** 编辑按钮 */
    handleUpdate(row) {
      this.form.logId = row.logId;
      this.form.yjAmount = row.yjAmount;
      this.form.discountAmount = row.discountAmount;
      this.getPaidAmount(this.form.yjAmount,this.form.discountAmount);
      this.open = true;
      this.title = "修改支付金额";
    },
    
    /** 表单重置*/
    reset() {
      this.form = {
        typeId: undefined,
        typeName: undefined,
      };
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 提交按钮 */
    submitForm() {
      
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.getPaidAmount(this.form.payAmount, this.form.discountAmount);
          if (this.form.discountAmount == null || this.form.discountAmount <= 0) { 
            
            return;
          }
          updateByLogIdAmount(this.form.discountAmount,this.form.paidAmount,this.form.logId).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
        }
      });
    },
    /** 搜索按钮操作2222 */
    handleQuery2() {
      this.queryParams.pageNum = 1;
      this.openView = 1
      this.queryParams.communityId = this.communityId;
      this.queryParams.buildingId = this.buildingId;
      this.queryParams.payYear = this.payYear;
      ownerList(this.queryParams).then(response => {
        this.ownerList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.logId);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    //批量发送账单
    bachUpdatePayStatus() {
      const ids = this.ids;
      this.loading = true;
      this.$modal
        .confirm('是否确认批量发送选中的账单吗？')
        .then(function () {
         
          return bachUpdatePayStatus(ids + '');
        })
        .then(() => {
          this.loading = false;
          this.getList();
          this.$modal.msgSuccess("发送成功");
        })
        .catch(() => {
          this.loading = false;
        });
    },
    /** 重新发送短信 */
    handleBachSms(type) {
      const ids = this.ids;
      var text = "短信通知";
      if (type == '0') {
        text = "站内通知";
      }
      this.loading = true;
      this.$modal
        .confirm('是否确认批量发送'+text+'吗？')
        .then(function () {
         
          return sendSms(ids + '',type);
        })
        .then(() => {
          this.loading = false;
          this.getList();
          this.$modal.msgSuccess("发送成功");
        })
        .catch(() => {
          this.loading = false;
        });
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
      }
      this.resetForm("queryForm");
      this.queryParams.payYear = new Date().getFullYear()+'';
      this.queryParams.communityId = this.communityId;
      this.handleQuery();
    },
    /** 重置按钮操作222 */
    resetQuery2() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
      }
      this.resetForm("queryForm");
      this.handleQuery2();
    },
    /** 返回  */
    returnBefore() {
      this.getList();
      this.openView = 1;
    },
    returnBefore1() {
      this.openView = 0;
    },
    /** 导出按钮操作 */
    handleExport() {
      this.$confirm('是否确认导出数据?', "导出确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      }).then(() => {
        this.download('/estate/estateCash/export', {
          ...this.queryParams
        }, `物业费_${new Date().getTime()}.xlsx`)
      }).catch(function (err) {
      })
    },
    /** 查询列表 */
    getList() {
      this.openView = 0;
      this.loading = false;
      propertyLogList(this.queryParams).then(response => {
        this.list = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
  }
}
</script>

<style scoped>

</style>

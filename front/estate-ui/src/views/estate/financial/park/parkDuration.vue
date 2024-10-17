<template>
  <div class="app-container">
    <template>

      <div>
        <el-row :span="24">
         <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
           <el-form-item label="所属小区" prop="communityId">
              <el-select v-model="queryParams.communityId" placeholder="请选择">
                <el-option
                  v-for="(item, index) in currentList"
                  :key="index"
                  :label="item.communityName"
                  :value="item.communityId"
                ></el-option>
              </el-select>
            </el-form-item>
           <el-form-item>
             <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
             <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
           </el-form-item>
         </el-form>
       </el-row>
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="ParkpayAdd"
            >新增
            </el-button>
          </el-col>
        </el-row>

        <el-table v-loading="loading" :data="parkpayList">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="小区名称" align="center" prop="communityId">
            <template slot-scope="scope">
                {{ getCommunityName(scope.row.communityId) }}
            </template>
          </el-table-column>
          <el-table-column label="租赁购买" align="center" prop="leasePurchase">
            <template slot-scope="scope">
                {{ getLeasePurchaseName(scope.row.leasePurchase) }}
            </template>
          </el-table-column>
          <el-table-column label="套餐" align="center" prop="purchasePackage">
            <template slot-scope="scope">
                {{ getPurchasePackageName(scope.row.purchasePackage) }}
            </template>
          </el-table-column>
          <el-table-column label="费用" align="center" prop="hourFee"/>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="lpd(scope.row)"
                >修改缴费规则
              </el-button>
              <el-button
                  size="mini"
                  type="text"
                  class="text-danger"
                  icon="el-icon-delete"
                  style="color: #f56c6c"
                  @click="handleDelete(scope.row)"
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
          @pagination="getParkPayList"
        />
      </div>
      
      <el-dialog title="添加车位缴费规则" :visible.sync="ParkPayopen" width="500px">
        <el-form :model="ParkPayfrom" :rules="rules">
          <el-form-item label="所属小区:" prop="communityId"  label-width="121px">
            <el-select v-model="ParkPayfrom.communityId" placeholder="请选择"  style="    width: 201px;" :disabled="isDisabled">
              <el-option
                v-for="(item, index) in currentList"
                :key="index"
                :label="item.communityName"
                :value="item.communityId"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="租赁购买:" prop="leasePurchase"  label-width="121px">
              <el-select v-model="ParkPayfrom.leasePurchase" placeholder="请选择"  style="    width: 201px;" :disabled="isDisabled">
                <el-option
                  v-for="(item, index) in leasePurchaseOptions"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                  
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="套餐:" prop="purchasePackage" label-width="121px" v-if="ParkPayfrom.leasePurchase=='0'">
              <el-select v-model="ParkPayfrom.purchasePackage" placeholder="请选择" style="    width: 201px;" :disabled="isDisabled">
                <el-option
                  v-for="(item, index) in purchasePackageOptions"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                 
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="费用:" prop="hourFee" label-width="121px">
              <el-input-number v-model="ParkPayfrom.hourFee" :min="0" :max="99999" :precision="2" :step="0.1"></el-input-number>
            </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitParkPay">提 交</el-button>
          <el-button @click="reset">取 消</el-button>
        </div>
      </el-dialog>
    </template>

  </div>
</template>

<script>
import {
  insert,
  update,
  del,
  get,
  list
} from "@/api/estate/financial/parkDuration";
import {getCurrentList} from "@/api/estate/community";

export default {
  name: "park",
  data() {
    return {
      view: 1,
      isDisabled:false,
      currentList: [],
      parkpayList: [],
      parkdurationList: [],
      leasePurchaseOptions: [{ "label": "租", "value": "0" }, { "label": "买", "value": "1" }],
      purchasePackageOptions: [{ "label": "包年", "value": "0" }, { "label": "包月", "value": "1" }],
      ParkPayopen: false,
      ParkDurationOpen: false,
      addParkDurationopen: false,
      xgfyopen: false,
      ParkPayfrom: {

      },
      ParkDurationform: {},
      communityIdTemp: 0,
      // 树筛选文本
      filterText: '',
      // 显示搜索条件
      showSearch: true,
      openView: false,
      // 遮罩层
      loading: true,
      //树结构
      comOptions: [],
      total: 0,
      defaultProps: {
        children: "communityId",
        label: "communityName"
      },
      // 表单校验
      rules: {
        communityId: [
          { required: true, message: "请选择小区", trigger: "blur" }
        ],
        leasePurchase: [
          { required: true, message: "请选择租赁购买", trigger: "blur" }
        ],
        purchasePackage: [
          { required: true, message: "请选择套餐", trigger: "blur" }
        ],
        hourFee: [
          { required: true, message: "请输入费用", trigger: "blur" }
        ]
        
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: null,
      },
    }
  },
  created() {
    this.view = 0;
    this.getParkPayList();
    this.getCurrentList();
  },
  methods: {
     // 搜索按钮
     handleQuery(){
      this.getParkPayList();
    },
    // 重置按钮
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        communityId: null,
      },
      this.handleQuery();
    },
    getCommunityName(communityId) { 

      if (communityId) { 
        var obj = this.currentList.filter(item => item.communityId == communityId)[0];
        if (obj != null) { 
          return obj.communityName;
        }
      }
    },
    getLeasePurchaseName(leasePurchase) { 
      if (leasePurchase) { 
        return this.leasePurchaseOptions.filter(item=>item.value == leasePurchase)[0].label;
      }
    },
    //套餐
    getPurchasePackageName(purchasePackage) { 
      if (purchasePackage) {
        return this.purchasePackageOptions.filter(item => item.value == purchasePackage)[0].label;
      } else { 
        return "--";
      }
    },
    //删除按钮
    handleDelete(row) {
      this.$modal
        .confirm("是否确认删除选中的数据项？")
        .then(() => {
          del(row.id).then(() => {
            this.getParkPayList()
            this.$modal.msgSuccess("删除成功");
          });
        }).catch(() => {
      });
    },
    getCurrentList() {
      getCurrentList().then(res => {
        this.currentList = res.data
      })
    },

    lpd(row) {
      this.parkdurationList = []
      get(row.id).then(res => {
        this.ParkPayfrom = res.data
        this.isDisabled = true;
        this.ParkPayopen = true;
      })
      
    },

    getParkPayList() {
      list(this.queryParams).then(res => {
        this.parkpayList = res.rows
        this.total = res.total
        this.loading = false
      })
    },
    //新增
    ParkpayAdd() {
      this.ParkPayfrom = {};
      this.ParkPayopen = true;
      this.isDisabled = false;
    },
  
    submitParkPay() {
      if (this.ParkPayfrom.communityId != null) {
        if (this.ParkPayfrom.id==null || this.ParkPayfrom.id=='') {
          insert((this.ParkPayfrom)).then(res => {
            this.$message.success("新增成功")
            this.ParkPayfrom = {}
            this.ParkPayopen = false
            this.getParkPayList()
          })
        } else { 
          update((this.ParkPayfrom)).then(res => {
            this.$message.success("修改成功")
            this.ParkPayfrom = {}
            this.ParkPayopen = false
            this.getParkPayList()
          })
        }
      } else {
        this.$message.warning("请选择小区")
      }
    },


    reset() {
      this.ParkPayfrom = {}
      this.ParkDurationform = {}
      this.ParkPayopen = false
      this.ParkDurationOpen = false
      this.addParkDurationopen = false
    },
  }
}
</script>

<style scoped>

</style>

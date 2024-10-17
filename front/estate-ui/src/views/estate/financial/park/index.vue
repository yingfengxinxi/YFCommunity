<template>
  <div class="app-container">
    <template>

      <div>
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
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getParkPayList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="parkpayList">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="小区名称" align="center" prop="communityName"/>
          <el-table-column label="缴费开关" align="center" key="switchType">
            <template slot-scope="scope">
              <el-switch
                v-model="scope.row.switchType"
                active-value="0"
                inactive-value="1"
                @change="handleStatusChange(scope.row)"
              ></el-switch>
            </template>
          </el-table-column>

          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <span v-if="scope.row.switchType === '0'">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="lpd(scope.row)"
                >设置缴费规则
              </el-button>
              </span>
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
      <el-dialog title="设置缴费规则" :visible.sync="ParkDurationOpen" width="500px">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5">
            <el-button
              type="primary"
              icon="el-icon-plus"
              size="mini"
              @click="ParkDurationAdd"
            >新增
            </el-button>
          </el-col>
        </el-row>
        <el-table :data="parkdurationList">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="小区名称" align="center" prop="communityName"/>
          <el-table-column label="开始计时" align="center" prop="minHour"/>
          <el-table-column label="结束计时" align="center" prop="maxHour"/>
          <el-table-column label="计时费用" align="center" prop="hourFee"/>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="dpd(scope.row)"
              >删除
              </el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="fy(scope.row)"
              >修改费用
              </el-button>
            </template>
          </el-table-column>
        </el-table>
        <el-dialog title="添加缴费规则" :append-to-body="true" :visible.sync="addParkDurationopen" width="400px">
          <el-form ref="ParkDurationform" :model="ParkDurationform"  :rules="rules">
            <el-form-item label="所属小区:" prop="communityId">
              <el-select v-model="ParkDurationform.communityId" disabled placeholder="请选择">
                <el-option
                  v-for=" item in currentList"
                  :key="item.communityId"
                  :label="item.communityName"
                  :value="item.communityId*1"
                ></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="请输入开始计时时间:" prop="minHour">
              <el-input v-model="ParkDurationform.minHour" onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'')" maxlength="9"></el-input>
            </el-form-item>

            <el-form-item label="请输入结束计时时间:" prop="maxHour">
              <el-input v-model="ParkDurationform.maxHour" onkeyup="value=value.replace(/^(0+)|[^\d]+/g,'')" maxlength="9"></el-input>
            </el-form-item>

            <el-form-item label="请输入计时费用:" prop="hourFee">
<!--              <el-input v-model="ParkDurationform.hourFee"></el-input>-->
              <el-input-number v-model="ParkDurationform.hourFee" :min="0" :max="99999" :precision="2" :step="0.1"></el-input-number>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitParkDuration">提 交</el-button>
            <el-button @click="reset">取 消</el-button>
          </div>
        </el-dialog>

        <el-dialog title="修改费用"  :append-to-body="true" :visible.sync="xgfyopen" width="400px">
          <el-form :model="ParkDurationform">
            <el-form-item label="租赁购买:" prop="leasePurchase">
              <el-select v-model="ParkPayfrom.leasePurchase" placeholder="请选择">
                <el-option
                  v-for="(item, index) in leasePurchaseOptions"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>
            <el-form-item label="套餐:" prop="purchasePackage">
              <el-select v-model="ParkPayfrom.purchasePackage" placeholder="请选择">
                <el-option
                  v-for="(item, index) in purchasePackageOptions"
                  :key="index"
                  :label="item.label"
                  :value="item.value"
                ></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="请输入开始计时时间:" prop="minHour">
              <el-input :disabled="true" v-model="ParkDurationform.minHour"></el-input>
            </el-form-item>

            <el-form-item label="请输入结束计时时间:" prop="maxHour">
              <el-input :disabled="true" v-model="ParkDurationform.maxHour"></el-input>
            </el-form-item>

            <el-form-item label="请输入计时费用:" prop="hourFee">
<!--              <el-input v-model="ParkDurationform.hourFee"></el-input>-->
              <el-input-number v-model="ParkDurationform.hourFee" :min="0" :max="99999" :precision="2" :step="0.1"></el-input-number>
            </el-form-item>
          </el-form>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitfy">提 交</el-button>
          </div>
        </el-dialog>

      </el-dialog>


      <el-dialog title="添加小区开关" :visible.sync="ParkPayopen" width="500px">
        <el-form :model="ParkPayfrom">
          <el-form-item label="所属小区:" prop="communityId">
            <el-select v-model="ParkPayfrom.communityId" placeholder="请选择">
              <el-option
                v-for="(item, index) in currentList"
                :key="index"
                :label="item.communityName"
                :value="item.communityId"
              ></el-option>
            </el-select>
          </el-form-item>

          <el-form-item label="是否收费:" prop="switchType">
            <template>
              <el-radio v-model="ParkPayfrom.switchType" label="0">收费</el-radio>
              <el-radio v-model="ParkPayfrom.switchType" label="1">不收费</el-radio>
            </template>
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
  addParkDuration,
  AddParkPay,
  delPd,
  parkdurationlist,
  ParkPaylist,
  UpdateParkPay,
  xgfy
} from "@/api/estate/financial/Park";
import {getCurrentList} from "@/api/estate/community";

export default {
  name: "park",
  data() {
    return {
      view: 1,
      currentList: [],
      parkpayList: [],
      parkdurationList: [],
      leasePurchaseOptions: [{ "label": "租", "value": "0" }, { "label": "买", "value": "1" }],
      purchasePackageOptions: [{ "label": "包年", "value": "0" }, { "label": "包月", "value": "1" }],
      ParkPayopen: false,
      ParkDurationOpen: false,
      addParkDurationopen: false,
      xgfyopen: false,
      ParkPayfrom: {},
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
        minHour: [
          { required: true, message: "开始计时时间不能为空", trigger: "blur" }
        ],
        maxHour: [
          { required: true, message: "结束计时时间不能为空", trigger: "blur" }
        ],
        hourFee: [
          { required: true, message: "计时费用不能为空", trigger: "blur" }
        ]
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: null,
        switchType: null,
      },
    }
  },
  created() {
    this.view = 0;
    this.getParkPayList();
    this.getCurrentList();
  },
  methods: {

    dpd(row) {
      delPd(row.durationId).then(res => {
        this.$message.success("删除成功")
        this.getParkPayList()
        this.reset()
      })
    },
    fy(row) {
      this.ParkDurationform = {}
      this.ParkDurationform = row
      this.xgfyopen = true;
    },
    submitfy() {
      xgfy(this.ParkDurationform).then(res => {
        this.$message.success("修改成功")
        this.getParkPayList();
        this.getCurrentList();
        this.xgfyopen = false
      })
    },

    getCurrentList() {
      getCurrentList().then(res => {
        this.currentList = res.data
      })
    },

    lpd(row) {
      this.parkdurationList = []
      parkdurationlist(row.communityId).then(res => {
        this.parkdurationList = res.rows
        this.communityIdTemp = row.communityId
        this.ParkDurationOpen = true;
      })
    },

    getParkPayList() {
      ParkPaylist(this.queryParams).then(res => {
        this.parkpayList = res.rows
        this.total = res.total
        this.loading = false
      })
    },
    ParkpayAdd() {
      this.ParkPayopen = true;
    },
    ParkDurationAdd() {
      this.ParkDurationform = {}
      this.ParkDurationform.communityId = this.communityIdTemp
      this.addParkDurationopen = true;
    },
    submitParkPay() {
      if (this.ParkPayfrom.communityId != null) {
        AddParkPay((this.ParkPayfrom)).then(res => {
          this.$message.success("新增成功")
          this.ParkPayfrom = {}
          this.ParkPayopen = false
          this.getParkPayList()
        })
      } else {
        this.$message.warning("请选择小区")
      }
    },

    submitParkDuration() {
      this.$refs["ParkDurationform"].validate(valid => {
        if (valid) {
          if (this.ParkDurationform.communityId !== null && this.ParkDurationform.minHour !== null && this.ParkDurationform.maxHour !== null && this.ParkDurationform.hourFee !== null) {
            addParkDuration(this.ParkDurationform).then(res => {
              this.$message.success("新增成功")
              this.reset()
              this.getParkPayList()
            })
          } else {
            this.$message.warning("请填写完整")
          }
        }
      });

    },


    reset() {
      this.ParkPayfrom = {}
      this.ParkDurationform = {}
      this.ParkPayopen = false
      this.ParkDurationOpen = false
      this.addParkDurationopen = false
    },


    handleStatusChange(row) {
      this.ParkPayfrom.communityId = row.communityId
      this.ParkPayfrom.switchType = row.switchType
      let text = row.switchType === "0" ? "收费" : "不收费";
      this.$confirm('确定要' + text + row.communityName + '吗?', '提示', {
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        UpdateParkPay(this.ParkPayfrom).then(res => {
          if (res.code === 200) {
            this.$message.success("修改成功")
            this.getParkPayList();
            this.ParkPayfrom.communityId=undefined;
          }
        });
      }).catch(() => {
        row.switchType = row.switchType === "0" ? "1" : "0";
        this.ParkPayfrom.communityId=undefined;
      });
    },
  }
}
</script>

<style scoped>

</style>

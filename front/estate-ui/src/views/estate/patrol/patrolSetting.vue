<template>
  <div class="app-container">
    <div v-show="openView">
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
        <!--装修数据-->
        <el-col :span="20" :xs="24">
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>

          <el-table v-loading="loading" :data="list">
            <el-table-column label="设备类型" align="center" prop="facilitieEquipmentType" :formatter="facilitieEquipmentName"/>
            <el-table-column label="执行周期" align="center" prop="frequency">
              <template slot-scope="scope">
                <el-select
                v-model="scope.row.frequency"
                  clearable
                  placeholder="请选择执行周期"
                  style="width:200px"
                >
                  <el-option label="每天" value="0"></el-option>
                  <el-option label="每月" value="1"></el-option>
                </el-select>
              </template>
            </el-table-column>
            <el-table-column label="巡检状态" align="center" prop="patrolStatus">
              <template slot-scope="scope">
                <el-switch
                  v-model="scope.row.patrolStatus"
                  active-value="1"
                  inactive-value="0"
                ></el-switch>
              </template>
            </el-table-column>
            <el-table-column label="下次巡检工单生成时间" align="center" prop="orderExpirationTime"/>
          </el-table>
        </el-col>
      </el-row>
      <el-row>
              <el-col :span="4" :offset="20" style="margin-top: 5px;">
                <el-button type="primary" @click="submit">提 交</el-button>
              </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import {
  listPatrol,
  updatePatrolStatus,
  save,
} from "@/api/estate/patrol/patrolSetting";
import { getCurrentList } from "@/api/estate/community";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "patrolSetting",
  components: { Treeselect },
  dicts: ['facilitie_equipment_type'],
  data() {
    return {
      qrCodeOpen:false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      active:1,
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
      //小区数据集合
      currentList: [],
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      facilitieShow: false,
      isDisabled:false,
      // 弹出层标题
      title: "",
      activeName: 'house',
      // 是否显示弹出层
      open: false,
      // 默认密码
      initPassword: undefined,
      // 表单参数
      form: {
        id:undefined,
        communityId:undefined,
        equipmentName:undefined,
        bindName:undefined,
        facilitieEquipmentType:undefined,
        specificationsModel:undefined,
        equipmentStatus:undefined,
        unitSupply:undefined,
        installationTime:undefined,
        enableTime:undefined,
        warrantyExpireTime:undefined,
        originalUnit:undefined,
        businessLicense:undefined,
        chargeName:undefined,
        chargePhone:undefined,
        installationLocation:undefined,
        productNumber:undefined,
        registrationNumber:undefined,
        other:undefined,
        signs:undefined,
        instructions:undefined,
        qrCode:undefined
      },
      renovationAuditStatusOptions:[],
      defaultProps: {
        children: "children",
        label: "label"
      },
      // 表单校验
      rules: {
        equipmentName: [
          {required: true, message: "设备姓名不能为空", trigger: "blur"},
          {pattern: /[^ \x22]+/, message: "不能输入空格", trigger: "blur"},
        ],
        bindName: [
          {required: true, message: "设备品牌不能为空", trigger: "blur"},
          {pattern: /[^ \x22]+/, message: "不能输入空格", trigger: "blur"},
        ],
        facilitieEquipmentType: [
          {required: true, message: "请选择设备类型", trigger: "blur"}
        ],
        specificationsModel: [
          {required: true, message: "规格型号不能为空",  trigger: "blur"}
        ],
        unitSupply: [
          {required: true, message: "供货单位不能为空",  trigger: "blur"}
        ],
        installationTime: [
          {required: true, message: "安装时间不能为空",  trigger: "blur"}
        ],
        enableTime: [
          {required: true, message: "启用时间不能为空",  trigger: "blur"}
        ],
        warrantyExpireTime: [
          {required: true, message: "质保到期时间不能为空",  trigger: "blur"}
        ],
        originalUnit: [
          {required: true, message: "维保单位不能为空",  trigger: "blur"}
        ],
        businessLicense: [
          {required: true, message: "营业执照号码不能为空",  trigger: "blur"}
        ],
        chargeName: [
          {required: true, message: "承担管理维护负责人不能为空",  trigger: "blur"}
        ],
        chargePhone: [
          {required: true, message: "承担管理维护负责人联系方式不能为空",  trigger: "blur"}
        ],
        installationLocation: [
          {required: true, message: "安装位置不能为空",  trigger: "blur"}
        ],
        productNumber: [
          {required: true, message: "产品编号不能为空",  trigger: "blur"}
        ],
        registrationNumber: [
          {required: true, message: "使用登记证编号不能为空",  trigger: "blur"}
        ],
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        equipmentName:undefined,
        facilitieEquipmentType: undefined,
        communityId: undefined
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
  // 任务组名字典翻译
    facilitieEquipmentName(row, column) {
      return this.selectDictLabel(this.dict.type.facilitie_equipment_type, row.facilitieEquipmentType);
    },
    // 更改状态
    handleStatusChange(row) {
      
      let text = row.patrolStatus === "0" ? "启用" : "停止";
      this.$modal
        .confirm("确认要" + text + " 当前类型的巡检工单吗？")
        .then(function () {
           return updatePatrolStatus(row.id,row.patrolStatus);
        })
        .then(() => {
          this.$modal.msgSuccess(text + "成功");
          this.getList();
        })
        .catch(function () {
          row.patrolStatus = row.patrolStatus === "0" ? "1" : "0";
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
    //装修办理提交
    submit() { 

        var flag="0";
        this.list.forEach(item => {
          item.communityId=this.queryParams.communityId;
          if(!item.frequency){
            flag='1'
          }
        });
        if(flag=='1'){
          this.$modal.msgWarning("请选择执行周期");
          return;
        }
        this.loading=true;
        save(this.list).then(response => {
          this.$modal.msgSuccess("保存成功");
          this.loading=false;
          this.getList()
        });
         
        
    },
    //取消
    cancelFacilitle() { 
      this.reset();
      this.facilitieShow = false;
      this.openView = true
      this.getList();
    },
    /** 查询装修列表 */
    getList() {
     
      this.loading = true;
      listPatrol(this.queryParams).then(response => {
          this.list = response.data;
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
      this.activeName = 'house';
      this.form = {
        id:undefined,
        communityId:undefined,
        equipmentName:undefined,
        bindName:undefined,
        facilitieEquipmentType:undefined,
        specificationsModel:undefined,
        equipmentStatus:undefined,
        unitSupply:undefined,
        installationTime:undefined,
        enableTime:undefined,
        warrantyExpireTime:undefined,
        originalUnit:undefined,
        businessLicense:undefined,
        chargeName:undefined,
        chargePhone:undefined,
        installationLocation:undefined,
        productNumber:undefined,
        registrationNumber:undefined,
        other:undefined,
        signs:undefined,
        instructions:undefined,
        qrCode:undefined
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
      this.dateRange = [];
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

    //关闭详情页
    canceldetail() {
      this.detailForm = {}
      this.detailShow = false
      this.openView = true;
    }
  }
};
</script>

<style scoped>


/*设置高亮颜色*/
/deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #b3f1b6 !important;
}


   /deep/ .stepsStyle .el-step__title.is-process,.el-step__title.is-wait{
       color: #fff !important;
     }
     /* 这里设置的是下一步和等待状态里的文字颜色 */
     /deep/.stepsStyle .el-step__head.is-process,.el-step__heads.is-wait .el-step__icon.is-text{
       
         color: #a8a8a8 !important;
         border-color: #a8a8a8 !important;
       
     }
     /deep/.stepsStyle .el-step__head.is-finish{
       color: #2a82e4 !important;
     }
     /deep/.el-step__main .el-step__title.is-finish{
      padding-bottom:0px;
     }
     /* 这里设置的是已完成状态里的文字颜色 */
     /deep/.stepsStyle .el-step__head.is-finish .el-step__icon.is-text{
         color: #2a82e4 !important;
         border-color: #2a82e4 !important;
       }
     .el-step__title.is-finish{
       color: #2a82e4 !important;
     }
     
     /* 这里设置的是完成后过渡线条颜色 */
     .el-step__line-inner{
       border-color: #2a82e4 !important;
     }
     
     /* 这里设置的是完成后的颜色 */
     .el-step__head.is-success,.el-step__title.is-success{
       color: #2a82e4 !important;
     }

</style>

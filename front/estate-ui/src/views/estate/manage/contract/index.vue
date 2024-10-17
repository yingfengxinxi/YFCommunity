<template>
  <div class="app-container">
    <!--综合页面信息-->
    <div v-show=checkView>
      <!--模糊查询-->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
        <el-form-item label="合同名称" prop="contractName">
          <el-input
            v-model="queryParams.contractName"
            placeholder="请输入合同名称"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="合同编号" prop="contractNo">
          <el-input
            v-model="queryParams.contractNo"
            placeholder="请输入合同编号"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>

        <el-form-item label="甲方负责人" prop="personChargeParty1Name">
          <el-input
            v-model="queryParams.personChargeParty1Name"
            placeholder="请输入甲方负责人"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="甲方负责人联系方式" prop="personChargeParty1Phone">
          <el-input
            v-model="queryParams.personChargeParty1Phone"
            placeholder="请输入甲方负责人联系方式"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="乙方负责人" prop="personChargeParty2Name">
          <el-input
            v-model="queryParams.personChargeParty2Name"
            placeholder="请输入乙方负责人"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="乙方负责人联系方式" prop="personChargeParty2Phone">
          <el-input
            v-model="queryParams.personChargeParty2Phone"
            placeholder="请输入乙方负责人联系方式"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="所属小区" prop="communityId">
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
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜 索</el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重 置</el-button>
        </el-form-item>
      </el-form>
      <!--按钮操作-->
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
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
      <!--表单-->
      <el-table v-loading="loading" :data="contractManageList" row-key="procurementId" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="50" align="center"/>
        <el-table-column label="合同编号" align="center" key="contractNo" prop="contractNo"/>
        <el-table-column label="合同名称" align="center" key="contractName" prop="contractName" />
        <el-table-column label="所属小区" align="center" key="communityId" prop="communityId" >
          <template scope="scope">
            <span>{{ getCommunityName(scope.row.communityId) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="甲方负责人" align="center" key="personChargeParty1Name" prop="personChargeParty1Name" />
        <el-table-column label="甲方联系方式" align="center" key="personChargeParty1Phone" prop="personChargeParty1Phone"/>
        <el-table-column label="乙方负责人" align="center" key="personChargeParty2Name" prop="personChargeParty2Name" />
        <el-table-column label="乙方联系方式" align="center" key="personChargeParty2Phone" prop="personChargeParty2Phone"/>
        <el-table-column label="签约人" align="center" key="contractorName" prop="contractorName"/>
        <el-table-column label="签约时间" align="center" key="createTime" prop="createTime"/>
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
              @click="checkBuyerInfoView(scope.row)"
            >查询
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="checkBuyerInfo(scope.row,'1')"
            >修改
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

      <!--     分页操作-->
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
    <!--新增合同信息-->
    <div v-show=checkAddView>
      <!--添加-->
      <div >
        <el-form ref="form" :disabled="isDisabled" :model="form" :rules="rules" label-width="100px">
          <h4 class="form-header">合同信息</h4>
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="合同名称" prop="contractName">
                <el-input v-model="form.contractName" placeholder="请输入合同名称" maxlength="30"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="1">
              <el-form-item label="合同编号" prop="contractNo">
                <el-input :disabled="form.id" v-model="form.contractNo" placeholder="请输入合同编号" maxlength="30"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="所属小区" prop="communityId">
                <el-select v-model="form.communityId" placeholder="请选择" >
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
          <h4 class="form-header">甲方</h4>
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="负责人" prop="personChargeParty1Name">
                <el-input v-model="form.personChargeParty1Name" placeholder="请输入负责人" style="width: 100%"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="联系方式" prop="personChargeParty1Phone">
                <el-input v-model="form.personChargeParty1Phone" placeholder="请输入联系方式" style="width: 100%"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="身份证正面" prop="personChargeParty1CardFont"  >
                <image-upload :isShowTip="!isDisabled"  v-model="form.personChargeParty1CardFont" :limit="1"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="身份证反面" prop="personChargeParty1CardBack"  >
                <image-upload :isShowTip="!isDisabled" v-model="form.personChargeParty1CardBack" :limit="1"/>
              </el-form-item>
            </el-col>
          </el-row>
          <h4 class="form-header">乙方</h4>
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="负责人" prop="personChargeParty2Name">
                <el-input v-model="form.personChargeParty2Name" placeholder="请输入负责人" style="width: 100%"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="联系方式" prop="personChargeParty2Phone">
                <el-input v-model="form.personChargeParty2Phone" placeholder="请输入联系方式" style="width: 100%"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="身份证正面" prop="personChargeParty2CardFont"  >
                <image-upload :isShowTip="!isDisabled" v-model="form.personChargeParty2CardFont" :limit="1"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="身份证反面" prop="personChargeParty2CardBack" >
                <image-upload :isShowTip="!isDisabled" v-model="form.personChargeParty2CardBack" :limit="1"/>
              </el-form-item>
            </el-col>
          </el-row>
          <h4 class="form-header">签约信息</h4>
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="签约人" prop="contractorName">
                <el-input v-model="form.contractorName" placeholder="请输入签约人" maxlength="11"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="签约金额" prop="contractorMoney">
                <el-input-number v-model="form.contractorMoney" :min="0" :max="999999" :precision="2" :step="0.1"  style="width:200px"></el-input-number>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="服务期限" prop="dateRange">
                <el-date-picker
              v-model="dateRange"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>

          <h4 class="form-header">附件信息</h4>
          <el-row>
            <el-col :span="15" :offset="1">
              <el-form-item label="合同签字页" prop="signContract">
                <image-upload :isShowTip="!isDisabled" v-model="form.signContract" :limit="3"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="15" :offset="1">
              <el-form-item label="其他附件" prop="other">
                <image-upload :isShowTip="!isDisabled" v-model="form.other" :limit="3"/>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer" style="float: right;margin-bottom: 30px" v-show="close">
          <el-button type="primary" v-if="!isDisabled" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {getCurrentList} from '@/api/estate/community';
import {addContractManage,updateContractManage,listContractManage, delContractManage,getContractManageById} from "@/api/estate/manage/contract/contract";
export default {
  name: "contractIndex",
  props: {
  },
  data() {
    return {
      //文件名称
      fileName:"",
      number: 0,
      currentList:[],
      
      // 表单校验
      rules: {

        contractName: [
          {required: true, message: "合同名称不能为空", trigger: "blur"},
          {pattern: /[^ \x22]+/, message: "不能输入空格", trigger: "blur"},
        ],
        contractNo: [
          {required: true, message: "合同编号不能为空", trigger: "blur"},
          {pattern: /[^ \x22]+/, message: "不能输入空格", trigger: "blur"},
        ],
        communityId: [
          {required: true, message: "请选择小区", trigger: "change"},
        ],
        personChargeParty1Name: [
          {required: true, message: "请输入甲方负责人", trigger: "change"},
        ],
        personChargeParty1Phone: [
          //1、手机号码验证正则
          {required: true, message: "请输入甲方负责人电话", trigger: "blur"},
          {pattern: /^1[3456789]\d{9}$/, message: "手机号格式不对", trigger: "blur"},
        ],
        personChargeParty1CardFont: [
          {required: true, message: "甲方负责人身份证正面", trigger: "blur"},
        ],
        personChargeParty1CardBack: [
          {required: true, message: "甲方负责人身份证反面", trigger: "blur"},
        ],
        personChargeParty2Name: [
          {required: true, message: "请输入乙方负责人", trigger: "change"},
        ],
        personChargeParty2Phone: [
          //1、手机号码验证正则
          {required: true, message: "请输入乙方负责人电话", trigger: "blur"},
          {pattern: /^1[3456789]\d{9}$/, message: "手机号格式不对", trigger: "blur"},
        ],
        personChargeParty2CardFont: [
          {required: true, message: "乙方负责人身份证正面", trigger: "blur"},
        ],
        personChargeParty2CardBack: [
          {required: true, message: "乙方负责人身份证反面", trigger: "blur"},
        ],
        contractorName: [
          {required: true, message: "请输入签约人姓名", trigger: "blur"},
          {pattern: /[^ \x22]+/, message: "不能输入空格", trigger: "blur"},
          {pattern: /^[\u4e00-\u9fa5]{0,}$/, message: "格式错误", trigger: "blur"},
        ],
        contractorMoney: [
          {required: true, message: "请输入签约金额", trigger: "blur"},
        ],
      
        signContract: [
          {required: true, message: "请上传合同附件", trigger: "blur"},
        ]
      },
      checkAddView: false,
      //小区数据
      communityList: [],
  
      // 选中数组
      ids: [],
      //选中名字数组
      names: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      //证件类型字典
      certificateOptions: [],
      // 日期范围
      dateRange: [],
      //模糊查询显隐
      showSearch: true,
      //查询到的列表参数
      contractManageList: [],
      //新增提交按钮显隐
      close: true,
      //详情页显隐
      checkView: true,
      isDisabled: false,
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,

      personLegal: '',
      personHotline:'',
      // 模糊查询数据
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        estateId:undefined,
        communityId: undefined,
        contractName:undefined,
        contractNo: undefined,
        personChargeParty1Name: undefined,
        personChargeParty2Name: undefined,
        personChargeParty1Phone: undefined,
        personChargeParty2Phone: undefined,
      },
      resultArr:undefined,
      // 表单参数
      form: {
        totalAmount:0
      },
    };
  },

  created() {
    getCurrentList().then(res => {
        this.currentList = res.data;
        this.getList();
    })
  },
  watch: {
  
  },
  computed: {

  },

  methods: {
    getCommunityName(communityId) { 
      if (communityId) { 
        var obj=this.currentList.filter(item => item.communityId == communityId)[0];
        if (obj != null) { 
          return obj.communityName;
        }
      }
    },
    checkBuyerInfoView(row) { 
      this.checkBuyerInfo(row,"0")
    },
    //详情按钮
    checkBuyerInfo(row,flag){
      //打开详情页面，并置空reset
      this.checkView = false;
      this.checkAddView = true;
      this.isDisabled = false;
      if (flag=="0") { 
         //查询 
         this.isDisabled = true;
      }
      
      //请求接口数据，数据铺上
      getContractManageById(row.id).then(response => {
        this.form = response.data;
        this.dateRange=[this.form.serviceStartTime,this.form.serviceEndTime];
      })


    },
    /** 查询列表 */
    getList() {
      this.loading = true;
      listContractManage(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.contractManageList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('是否确认删除所选中的的数据项?', {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delContractManage(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.procurementId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 取消按钮
    cancel() {
      this.checkView = true;
      this.checkAddView= false;
      this.reset();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.checkView = false;
      this.checkAddView = true;
      this.isDisabled = false;
    },
    /** 添加按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.dateRange) { 
            this.form.serviceStartTime = this.dateRange[0];
            this.form.serviceEndTime = this.dateRange[1];
          }
          if (this.form.id) {
            updateContractManage(this.form).then(response => {
              this.$message.success("修改成功");
              this.checkView = true;
              this.checkAddView = false;
              this.getList();
            });
          } else { 
            addContractManage(this.form).then(response => {
              this.$message.success("新增成功");
              this.checkView = true;
              this.checkAddView = false;
              this.getList();
            });
          }
          
        }
      });
    },


    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 表单重置
    reset() {
      this.form = {
        //	主键
        id:undefined,
        estateId:undefined,
        communityId:undefined,
        contractName:undefined,
        contractNo:undefined,
        personChargeParty1Name:undefined,
        personChargeParty1Phone:undefined,
        personChargeParty1CardFont:undefined,
        personChargeParty1CardBack:undefined,
        personChargeParty2Name:undefined,
        personChargeParty2Phone:undefined,
        personChargeParty2CardFont:undefined,
        personChargeParty2CardBack:undefined,
        contractorName:undefined,
        contractorMoney:undefined,
        serviceStartTime:undefined,
        serviceEndTime:undefined,
        signContract:undefined,
        other:undefined
      };
      this.resetForm("form");
    },
  }
};
</script>


<style>

.dev-col {
  height: 500px;
  overflow-y: scroll;
}

.dev-col::-webkit-scrollbar {
  display: none;
}

.el-table__body-wrapper {
  z-index: 2
}

.el-upload--picture-card {
  width: 100%
}

.el-upload--text {
  width: 100%;
  text-align: center;
}

.avatar .el-upload--picture-card {
  width: 50%
}

.owner-avatar .el-upload {
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader2 .el-upload {
  /*border: 1px dashed #bebfc3;
  border-radius: 6px;*/
  cursor: pointer;
  position: relative;
  overflow: hidden;
  height: 180px;
}

.owner-avatar .el-upload:hover {
  border-color: #409EFF;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 400px;
  height: 200px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  height: 200px;
  width: 110%
}

.avatar2 {
  height: 200px;
  width: 110%
}

.peopleImg {
  height: 180px;
  width: 100%;
}

.peopleImg2 {
  height: 180px;
  width: 45%;
}

.headCenter {
  position: relative;
  button: 300px;
}

.table-div {
  height: 30vh;
  overflow-y: scroll;
  box-shadow: 0 -5px 5px #EAEAEA;
  padding: 15px 20px 20px 20px;
}

.table-div::-webkit-scrollbar {
  display: none;
}

.upload-file-uploader {
  margin-bottom: 5px;
}


.theme-demo a {
  color: #3975C6;
}


</style>

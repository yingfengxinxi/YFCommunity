<template>
  <div class="app-container">
    <!-- list-->
    <div v-show="isList">
      <el-row style="margin-top: 20px;margin-left: 15px">
        <el-col :span="4">
          <el-input
            placeholder="请输入小区名称"
            v-model="filterText">
          </el-input>
          <div class="head-container">
            <el-tree
              class="filter-tree hide-tree"
              :data="data"
              :props="{children:''}"
              style="margin-top: 10px"
              :highlight-current="true"
              node-key="id"
              :filter-node-method="filterNode"
              :expand-on-click-node="false"
              :current-node-key="default_id"
              @node-click="getCommunityId"
              ref="tree">
              <!-- 插入楼栋图标 -->
              <span slot-scope="{ node }">
	   		          <i class="el-icon-office-building" />
	   		         <span style="padding-left:5px;font-size: 14px">{{node.label}}</span>
		          </span>
            </el-tree>
          </div>
        </el-col>
        <el-col style="margin-left: 15px" :span="19">
          <el-row>
            <el-form :model="queryParams" ref="queryForm" size="small" :inline="true">
              <el-form-item label="租客姓名" prop="tenantName">
                <el-input
                  v-model="queryParams.tenantName"
                  placeholder="请输入租客姓名"
                  clearable
                />
              </el-form-item>
              <el-form-item label="手机号码" prop="tenantPhone">
                <el-input
                  v-model="queryParams.tenantPhone"
                  placeholder="请输入手机号码"
                  clearable
                />
              </el-form-item>
              <el-form-item label="合同状态" prop="state">
                <el-select v-model="queryParams.state" placeholder="请选择" clearable>
                  <el-option
                    v-for="i in stateList"
                    :key="i.value"
                    :value="i.value"
                    :label="i.label"
                  />
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
                @click="addAlterShow()"
              >新增</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="primary"
                plain
                icon="el-icon-plus"
                size="mini"
                :disabled="multiple"
                @click="dialogVisible = true"
              >续约</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="danger"
                plain
                icon="el-icon-close"
                size="mini"
                :disabled="multiple"
                v-hasPermi="['center:tenantfile:deleted']"
                @click="deleted"
              >解约</el-button>
            </el-col>
            <!-- <el-col :span="1.5">
              <el-button
                type="warning"
                plain
                icon="el-icon-download"
                size="mini"
                @click="handleExport"
                v-hasPermi="['center:tenantfile:export']"
              >导出</el-button>
            </el-col> -->
          </el-row>
          <el-table v-loading="loading" :data="dataList" @selection-change="handleSelectionChange" ref="multipleTable">
            <el-table-column type="selection" width="50" align="center" :selectable="selectEnable"/>
            <el-table-column label="租客姓名" align="center" prop="tenantName"/>
            <el-table-column label="手机号码" align="center" prop="tenantPhone"/>
            <el-table-column label="证件号码" align="center" prop="cardNo"/>
            <el-table-column label="租赁楼栋" align="center" prop="buildingName"/>
            <el-table-column label="租赁单元" align="center" prop="unitName"/>
            <el-table-column label="租赁房号" align="center" prop="roomName"/>
            <el-table-column label="业主姓名" align="center" prop="ownerName"/>
            <el-table-column label="业主联系方式" align="center" prop="ownerPhone"/>
            <el-table-column label="合同状态" align="center">
              <template slot-scope="scope">
                <el-tag type="success" v-show="scope.row.state=='进行中'">{{scope.row.state}}</el-tag>
                <el-tag type="danger" v-show="scope.row.state=='已结束'">{{scope.row.state}}</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="合同到期时间" align="center" prop="leaseEndTime"/>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                 
                  @click="alterShow(scope.row)"
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
    </div>
    <!-- 新增/修改-->
    <div  v-show="alter">
      <div class="add-view">
        <el-form ref="form" :model="form"  label-width="111px" :rules="rules">
          <el-row>
            <el-col>
              <span>业主基本信息</span>
              <el-divider/>
            </el-col>
            <el-col>
              <el-row>
                <el-col :offset="0" :span="6">
                  <el-form-item label="所属小区:" prop="communityName">
                    <el-input v-model="communityName" :disabled="true"/>
                  </el-form-item>
                </el-col>
                <el-col :offset="0" :span="6">
                  <el-form-item label="所属楼栋:" prop="buildingId">
                      <el-select :disabled="isDisabled" v-model="form.buildingId" placeholder="请选择所属楼宇" clearable @change="getUnitList(form.buildingId,'0')">
                            <el-option
                              v-for="i in BuildingList"
                              :key="i.buildingId"
                              :value="i.buildingId"
                              :label="i.buildingName"
                            />
                          </el-select>
                  </el-form-item>
                </el-col>
                <el-col :offset="0" :span="6">
                  <el-form-item label="所属单元:" prop="unitId">
                    <el-select :disabled="isDisabled" v-model="form.unitId" placeholder="请选择所属单元" clearable @change="getRoomList(form.unitId,'0')">
                          <el-option
                            v-for="i in unitList"
                            :key="i.unitId+''"
                            :value="i.unitId+''"
                            :label="i.unitName"
                          />
                        </el-select>
                  </el-form-item>
                </el-col>
                <el-col :offset="0" :span="6">
                  <el-form-item label="房号:" prop="roomId">
                    <el-select :disabled="isDisabled" v-model="form.roomId" placeholder="请选择所属房间" clearable @change="getRoomId(form.unitId,form.roomId,'0')">
                      <el-option
                        v-for="i in roomList"
                        :key="i.roomId"
                        :value="i.roomId"
                        :label="i.roomName"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :offset="0" :span="6">
                  <el-form-item label="姓名:" prop="accountName">
                    {{form.accountName}}
                  </el-form-item>
                  
                </el-col>
                <el-col :offset="0" :span="6">
                  <el-form-item label="手机号:" prop="ownerPhone">
                    {{form.ownerPhone}}
                  </el-form-item>
                </el-col>
                <el-col :offset="0" :span="6">
                  <el-form-item label="证件号码:" prop="ownerCardNo">
                    {{ form.ownerCardNo }}
                  </el-form-item>
                </el-col>
                <el-col :offset="0" :span="6">
                  <el-form-item label="房屋坐落:" prop="houseAddress">
                    {{ form.houseAddress }}
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :offset="0" :span="6">
                  <el-form-item label="房屋面积:" prop="totalArea">
                    {{ form.totalArea }}
                  </el-form-item>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <span>租客基本信息</span>
              <el-divider/>
            </el-col>
            <el-col>
              <el-row>
                <el-col :offset="0" :span="7">
                  <el-form-item label="姓名:" prop="tenantName">
                    <el-input
                      v-model="form.tenantName"
                      clearable
                      maxlength="50"
                      placeholder="请输入姓名"
                    />
                  </el-form-item>
                </el-col>
                <el-col :offset="0" :span="7">
                  <el-form-item label="手机号码:" prop="tenantPhone">
                    <el-input
                      placeholder="请输入手机号"
                      v-model="form.tenantPhone"
                      clearable
                      maxlength="480"
                    />
                  </el-form-item>
                </el-col>
                <el-col :offset="0" :span="7">
                  <el-form-item label="性别:" prop="gender">
                    <el-select v-model="form.gender" placeholder="请选择性别" clearable>
                      <el-option
                        v-for="i in gender"
                        :key="i.value"
                        :value="i.value"
                        :label="i.label"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                
              </el-row>
              <el-row>
                <el-col :offset="0" :span="7">
                  <el-form-item label="证件类型:" prop="cardType">
                    <el-select v-model="form.cardType" placeholder="请选择证件类型" clearable>
                      <el-option
                        v-for="dict in dict.type.sys_certificate"
                        :key="dict.value"
                        :label="dict.label"
                        :value="dict.value"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :offset="0" :span="7">
                  <el-form-item label="证件号码:"  prop="cardNo">
                    <el-input
                      placeholder="请输入证件号码"
                      v-model="form.cardNo"
                      clearable
                      maxlength="125"
                    />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :offset="0" :span="7">
                  <el-form-item label="身份证正面照:" prop="cardFont">
                    <image-upload   v-model="form.cardFont" :limit="1" />
                  </el-form-item>
                </el-col>
                <el-col :offset="0" :span="7">
                  <el-form-item label="身份证反面照:" prop="cardBack">
                    <image-upload   v-model="form.cardBack" :limit="1" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :offset="0" :span="7">
                  <el-form-item label="户籍详情地址:" prop="domicileAddress">
                    <el-input
                      placeholder="请输入户籍详细地址"
                      v-model="form.domicileAddress"
                      clearable
                      maxlength="480"
                    />
                  </el-form-item>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <span>交易信息</span>
              <el-divider/>
            </el-col>
            <el-col>
              <el-row>
                <el-col  :offset="0" :span="7">
                  <el-form-item label="租赁开始时间:" prop="leaseStartTime">
                    <el-date-picker
                      v-model="form.leaseStartTime"
                      value-format="yyyy-MM-dd"
                      placeholder="请选择开始时间"
                      style="width=190px"
                      type="date"
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col  :offset="1" :span="7">
                  <el-form-item label="租赁结束时间:" prop="leaseEndTime">
                    <el-date-picker
                      v-model="form.leaseEndTime"
                      value-format="yyyy-MM-dd"
                      placeholder="请选择结束时间"
                      style="width=190px"
                      type="date"
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
                <el-col  :offset="1" :span="7">
                  <el-form-item label="押金付款时间:" prop="depositPayTime">
                    <el-date-picker
                      v-model="form.depositPayTime"
                      value-format="yyyy-MM-dd"
                      placeholder="请选择押金付款时间"
                      style="width=190px"
                      type="date"
                    ></el-date-picker>
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :offset="0" :span="7">
                  <el-form-item label="租赁总价（元）" prop="totalMoney">
                    <el-input-number  v-model="form.totalMoney" :min="0" :max="999999" :precision="2" :step="0.1"  style="width:190px"></el-input-number>
                  </el-form-item>
                </el-col>
                <el-col :offset="1" :span="7">
                  <el-form-item label="押金金额（元）" prop="deposit">
                    <el-input-number  v-model="form.deposit" :min="0" :max="999999" :precision="2" :step="0.1"  style="width:190px"></el-input-number>
                  </el-form-item>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
          <el-row>
            <el-col>
              <span>要件信息</span>
              <el-divider/>
            </el-col>
            <el-col>
              <el-row>
                <el-col  :offset="0" :span="20">
                  <el-form-item label="房产证:" prop="licenseUrl">
                    <image-upload  :isShowTip="!isDisabled" v-model="form.licenseUrl" :limit="3" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col  :offset="0" :span="20">
                  <el-form-item label="租房合同:" prop="rentContract">
                    <image-upload  :isShowTip="!isDisabled" v-model="form.rentContract" :limit="3" />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col  :offset="0" :span="20">
                  <el-form-item label="其他:" prop="other">
                    <image-upload  :isShowTip="!isDisabled" v-model="form.other" :limit="3" />
                  </el-form-item>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
          <el-divider/>
          <el-row>
            <el-col :offset="20" :span="2">
              <div style="width: 280px">
                <el-button type="primary" @click="update"
                  >确定</el-button
                >
                <el-button @click="closer">取消</el-button>
              </div>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </div>
    <!-- 续约 msg -->
    <el-dialog
      title="续约时间"
      :visible.sync="dialogVisible"
      style="margin-top:10%"
      width="35%">
      <el-date-picker
        v-model="contractDate"
        type="daterange"
        unlink-panels
        value-format="yyyy-MM-dd"
        range-separator="至"
        start-placeholder="续约开始日期"
        end-placeholder="续约结束日期">
      </el-date-picker>
       <span slot="footer" class="dialog-footer">
          <el-button @click="dialogVisible = false;contractDate=''">关 闭</el-button>
          <el-button type="primary" @click="updateContract">续 约</el-button>
       </span>
    </el-dialog>
  </div>
</template>

<script>
import {desensitization} from '@/utils/common'
import {getCurrentList} from  '@/api/estate/community'
import {query,queryContract,save,update,deleted,updateContract, getById, authTenant} from '@/api/estate/center/personnelFileManage/tenantFile'
import { queryCarByOwnerId, queryCarPortByOwnerId, upload } from '@/api/estate/center/personnelFileManage/ownerFile'
import { getRoomDetail } from "@/api/estate/center/room";
import { getRentBuildingList,getRentUnitList,getRentRoomList,getByRoomIdRentStatusCount } from "@/api/estate/customer/rent/rent";

export default {
  name: "index",
  dicts: ['sys_certificate'],
  data() {
    return {
      default_id:'',
      dialogVisible:false,
      //查找条件
      queryParams:{
        tenantPhone:'',
        tenantName:'',
        state:'',
        pageNum: 1,
        pageSize: 10,
      },
      alter:false,
      unitList:[],
      roomList:[],
      BuildingList:[],
      politicsStatus:[
        {label:'党员',value:'0'},
        {label:'团员',value:'1'},
        {label:'其它',value:'2'}
      ],
      houseState:[
        {label:'在售',value:'0'},
        {label:'不可售',value:'1'},
        {label:'入住',value:'2'},
        {label:'装修',value:'3'},
        {label:'空关',value:'4'},
        {label:'整租',value:'5'},
        {label:'合租',value:'6'},
        // {label:'合租2',value:'7'}
      ],
      gender:[
        {label:'男',value:'0'},
        {label:'女',value:'1'},
      ],
      marriageStatus:[
        {label:'未婚',value:'0'},
        {label:'已婚',value:'1'},
      ],
      familyList:[],
      population:[
        {label:'户籍人口',value:'0'},
        {label:'常驻人口',value:'1'},
        {label:'暂住人口',value:'2'},
      ],
      isDisabled:false,
      activeName: 'car',
      ownerData: [],
      form:{
        //租客id
        tenantId:undefined,
        //小区Id
        communityId:undefined,
        //楼栋Id
        buildingId:undefined,
        //单元Id
        unitId:undefined,
        //房号Id
        roomId:undefined,
        //业主名称
        ownerId:undefined,
        //业主手机号
        ownerPhone:undefined,
        //业主证件号
        ownerCardNo:undefined,
        //房屋坐落
        houseAddress:undefined,
        //房屋面积
        totalArea:undefined,
        //租客姓名
        tenantName:undefined,
        //租客手机号
        tenantPhone:undefined,
        //租客性别
        gender:undefined,
        //租客证件类型
        cardType:undefined,
        //租客证件号码
        cardNo:undefined,
        //租客正面身份证图片
        cardFont:undefined,
        //租客反面身份证图片
        cardBack:undefined,
        //租客户籍详细地址
        domicileAddress:undefined,
        //租赁开始时间
        leaseStartTime:undefined,
        //租赁结束时间
        leaseEndTime:undefined,
        //押金付款时间
        depositPayTime:undefined,
        //租赁总价
        totalMoney:undefined,
        //押金金额
        deposit:undefined,
        //房产证
        licenseUrl:undefined,
        //租房合同
        rentContract:undefined,
        //其他
        other:undefined,
        //账号id
        accountId:undefined
      },
      // 表单校验
      rules: {
        buildingId:[
          { required: true, message: "请选择所属楼栋", trigger: "blur" }
        ],
        unitId:[
          { required: true, message: "请选择所属单元楼", trigger: "blur" }
        ],
        roomId:[
          { required: true, message: "请选择房间号", trigger: "blur" }
        ],
        ownerName:[
          { required: true, message: "请选择业主", trigger: "blur" }
        ],
        tenantName: [
          {required: true, message: "租客姓名不能为空", trigger: "blur" },
          {pattern:/[^ \x22]+/,message: "不能输入空格",trigger: "blur"},
        ],
        tenantPhone: [
          {required: true, message: "租客手机号不能为空", trigger: "blur" },
          {pattern:/[^ \x22]+/,message: "不能输入空格",trigger: "blur"},
        ],
        gender: [
          { required: true, message: "性别不能为空", trigger: "blur" }
        ],
        cardType: [
          { required: true, message: "证件类型不能为空", trigger: ["blur", "change"] }
        ],
        cardNo: [
          //身份证验证
          { required: true, message: '请输入证件号码', trigger: 'blur' },
        ],
        cardFont:[
          { required: true, message: "请选择证件照正面", trigger: "blur" }
        ],
        cardBack:[
          { required: true, message: "请选择证件照反面", trigger: ["blur", "change"] }
        ],
        leaseStartTime:[
          { required: true, message: "请选择租赁开始时间", trigger: ["blur", "change"] }
        ],
        leaseEndTime:[
          { required: true, message: "请选择租赁结束时间", trigger: ["blur", "change"] }
        ],
        depositPayTime:[
          { required: true, message: "请选择押金付款时间", trigger: ["blur", "change"] }
        ],
        totalMoney:[
          { required: true, message: "请输入租赁总价", trigger: ["blur", "change"] }
        ],
        deposit:[
          { required: true, message: "请输入押金金额", trigger: ["blur", "change"] }
        ],
        
        
        
      },
      contractDate: '',
      ownerPhone:undefined,
      isList:true,
      details:false,
      filterText: '',
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      ids:[],
      communityName:'',
      // 总条数
      total: 0,
      rentList:[],
      carPortList:[],
      carList:[],
      dataList:[],
      data: [],
      // 遮罩层
      loading: true,
      stateList:[
        {value:'0',label:'进行中'},
        {value:'1',label:'已结束'}
      ],
      //是否显示可用设备
      showDevice: false,
      tenantTempList: [],
      tenantIds:[],
      tenantNames: [],
      deviceList: [],
      indexCodes: [],
    };
  },

  created() {
    this.initData()
/*
    this.queryDict().then(e=>{
      this.cardTypeList=e.data
    })
*/
  },
  watch: {
    //小区过滤
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },

  methods: {
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    // 上传预处理
    beforeUpload(file) {

      return false
    },
    selectEnable(row) { // 复选框可选情况
      const {state}=row
      if (state=='进行中') { // 禁用没结束的合同
        return false;
      } else {
        return true;
      }

    },
    //大图显示
    openUrl(url){
      window.open(url)
    },
    //解约
    deleted(){
      this.$confirm('是否确认解约数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then( ()=>{
        return deleted(this.ids);
      }).then(() => {
        this.$message.success('解约成功!!')
        this.getList();
        this.ids = [];
      })
    },
    //续约
    updateContract(){
        if (!this.contractDate){
              this.$message.error('请选择续约日期!!')
        }else {
          let params={
            contractStart:this.contractDate[0],
            contractEnd:this.contractDate[1],
            ids:this.ids+""
          }
          updateContract(params).then(e=>{
            if (e.code==200){
              this.$message.success(e.msg)

            }else {
              this.$message.error(e.msg)
            }
            this.dialogVisible=false
            this.getList();
            this.ids = [];
          })
        }

    },
    isValidID(id) {
      const regex = /^[1-9]\d{5}(18|19|20|21|22)?\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}(\d|[Xx])$/;
      return regex.test(id);
    },
    //修改
    update(){
      this.$refs["form"].validate(valid => {
        if (valid) {
          var cardNo=this.form.cardNo;
          if (this.form.cardType=='I') {
            if (!this.isValidID(cardNo)) { 
              this.$modal.msgWarning("证件号码格式有误！");
              return;
            }
          }


          this.form.communityId = this.queryParams.communityId;
          if (this.form.tenantId) {
            update(this.form).then(e=>{
              if (e.code==200){
                this.$message.success(e.msg)
                this.closer()
              }else {
                this.$message.error(e.msg)
              }
            })
          } else { 
            save(this.form).then(e=>{
              if (e.code==200){
                this.$message.success(e.msg)
                this.closer()
              }else {
                this.$message.error(e.msg)
              }
            })
          }
        }
      })
    },
    //修改
    alterShow(row){
      this.communityName = row.communityName;
      getById(row.tenantId).then(res => { 
        this.form = res.data;
        this.form.ownerId = parseFloat(this.form.ownerId);
        this.form.unitId =this.form.unitId+'';
        this.getUnitList(this.form.buildingId,'1');
        this.getRoomList(this.form.unitId, '1');
        this.getRoomId(this.form.unitId,this.form.roomId,'1');
        //this.ownerChange(this.form.ownerId);
        this.alter=true
        this.isList = false
        this.isDisabled = true;
      })
      
    },
    //新增
    addAlterShow() { 
      this.form = {}
      this.alter=true
      this.isList = false
      this.isDisabled = false;
    },
    //关闭
    closer(){
        this.details=false
        this.isList=true
        this.alter=false
        this.getList()
    },
    //查看
    show(row){
      this.public(row).then(e=>{
        this.details=true;
        this.isList=false
      })
      queryCarByOwnerId({id:this.form.account_id}).then(e=>{
        this.carList=e.data
      })

      queryCarPortByOwnerId({id:this.form.account_id}).then(e=>{
        this.carPortList=e.data
      })
      queryContract({id:this.form.tenant_id}).then(e=>{
        this.rentList=e.data
      })
    },
    async public(row) {
      this.form = row
      this.communityName = row['community_name']
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      await getRentBuildingList(row.community_id).then(e => {
        this.BuildingList = e.data
      })
      await getRentUnitList(row.bid).then(e => {
        this.unitList = e.data
      })
      await getRentRoomList(row.uid).then(e => {

        this.roomList = e.data
      })
      loading.close()
      return true;
    },
    //获取楼栋
    getBuildingList(communityId) {
      getRentBuildingList(communityId).then(e=>{
         this.BuildingList=e.data
      })
    },
    //获取单元
    getUnitList(buildingId, flag) {
      if (flag=='0') { 
        this.form.unitId= undefined
        this.form.roomId = undefined
        this.form.ownerName = undefined
        this.form.ownerPhone = undefined
        this.form.ownerCardNo = undefined
        this.form.houseAddress = undefined
        this.form.totalArea=undefined
      }
      
      getRentUnitList(buildingId).then(e=>{
        this.unitList=e.data
      })
    },
    //获取房间
    getRoomList(unitId,flag) {
      if (flag=='0') { 
        this.form.roomId = undefined
        this.form.ownerName = undefined
        this.form.ownerPhone = undefined
        this.form.ownerCardNo = undefined
        this.form.houseAddress = undefined
        this.form.totalArea=undefined
      }
      
      getRentRoomList(unitId).then(e=>{
        this.roomList=e.data
      })
    },

    getRoomId(unitId,roomId,flag) { 
      if (roomId) {
        var count = 0;
        if (flag == '0') {
          getByRoomIdRentStatusCount(roomId).then(res => {
            count = parseFloat(res.data);
          })
        } else {
          count = 1;
        }
        console.log("count>>>", count);
        if (count <= 0) {
          this.$message.error('当前房屋已出租');
        }else { 
          getRentRoomList(unitId).then(e => {
                var obj = e.data.filter(item => item.roomId == roomId)[0];
                console.log("roomId>>>>", obj);
                this.form.ownerId = obj.ownerId;
                this.form.accountName = obj.accountName;
                this.form.ownerPhone = obj.contactPhone;
                this.form.accountId = obj.accountId;
                this.form.ownerCardNo = obj.cardNo;
                this.$forceUpdate();
                getRoomDetail(roomId,this.queryParams.communityId).then(response => {
                  this.form.houseAddress = response.data.houseAddress;
                  this.form.totalArea = response.data.totalArea;
                  this.$forceUpdate();
                })
          })
        }
        
      }
      this.$forceUpdate();
    },
    // 改变业户姓名时
    ownerChange(ownerValue) {
      if (ownerValue) {
        //查找业主手机号
        //this.selectOwnerName(ownerValue);
      } else {
        this.form.ownerPhone = null;
      }
    },
     //查找业主手机号
     selectOwnerName(ownerValue) {
        var obj = this.ownerData = this.roomList.filter(item => item.ownerId == ownerValue)[0];
        this.form.ownerPhone = obj.contactPhone;
        this.form.accountId = obj.accountId;
        this.form.ownerCardNo = obj.cardNo;
        this.$forceUpdate()
    },
    /** 导出按钮操作 */
    handleExport() {
      this.queryParams.ids = this.ids;
      this.download('/estate/tenantfile/export',{...this.queryParams}, `${this.parseTime(new Date())}租客档案.xlsx`)
      //清空多选按钮
      this.$refs.multipleTable.clearSelection();
    },
     // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.tenantId)
      this.multiple = !selection.length
    },
    //初始化数据
    initData(){
      getCurrentList().then(e => {
        const {data}=e
        if (data){
          let arr=new Array();
          data.forEach((item)=>{
            const {communityId,communityName}=item
            arr.push({
              id:communityId,
              label:communityName,
              children: [{}]
            });
          })
          this.data=arr;
          this.queryParams['communityId'] = data[0].communityId;
          this.communityName= data[0].communityName;
          this.default_id=data[0].communityId

          this.$nextTick(() => {
            this.$refs['tree'].setCurrentKey(this.default_id);
          });
          this.getBuildingList(this.queryParams['communityId']);
          this.getList();
        }else {
          this.$message.error('暂无小区')
        }
      })
    },
    hide(str,begin,end){
        if (str){
          return  desensitization(str,begin,end);
        }
      return  "";
    },
    //查询数据
    getList() {
      this.loading = false;

      query(this.queryParams).then((e) => {
        this.dataList=e.rows
        this.total=e.total
        this.loading=false
      })
    },
    //获取点击小区id
    getCommunityId(e){
      this.queryParams['communityId'] = e.id;
      this.getBuildingList(this.queryParams['communityId']);
      this.getList()
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum=1
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },



    /** 选择业主 */
    handleSelectionChangeTenant(selection){
      this.tenantIds = selection.map(item => item.ownerId)
      this.tenantNames = selection.map(item => item.ownerName)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 选择设备 */
    handleSelectionChangeDevice(selection) {
      this.indexCodes = selection.map(item => item.indexCode)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },

    /** 授权提交 */
    authSubmit(){
      if(this.ownerIds.length == 0){
        this.$message.error("至少选择一个业主");
        return false;
      }
      if(this.indexCodes.length == 0){
        this.$message.error("至少选择一个设备");
        return false;
      }
      let params ={
        communityId:this.queryParams.communityId,
        ids:this.tenantIds,
        indexCodes:this.indexCodes
      }
      authTenant(params).then(response => {
        this.msgSuccess(response.msg);
        this.cancel()
        this.getList();
      }).catch(err =>{
        this.$message.error(err);
      });
    },

    cancel(){
  
      this.tenantTempList = [];
      this.tenantIds = [];
      this.tenantNames = [];
      this.deviceList = [];
      this.indexCodes = [];
    },
  },

}
</script>
<style scoped>
  .add-view span{
    font-weight: bold;
    color: black;
  }
 .avatar-uploader .el-upload,.face-url .avatar-uploader{
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .face-url .avatar-uploader{
    width: 178px;
    height: 178px;
  }
  .avatar-uploader:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
 .avatar1 {
    width: 356px;
    height: 178px;
    display: block;
  }
  /*设置高亮颜色*/
  /deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
    background-color: #b3f1b6 !important;
  }
</style>

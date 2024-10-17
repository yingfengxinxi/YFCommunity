<template>
  <div class="aaaaa">
    <!-- list-->
        <div v-show="isList">
          <el-row style="margin-top: 20px;margin-left: 15px">
            <el-col style="margin-left: 15px" :span="19">
              <el-row>
                <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
                  <el-form-item label="业主姓名" prop="name">
                    <el-input
                      v-model="queryParams.name"
                      placeholder="请输入业主姓名"
                      clearable
                    />
                  </el-form-item>
                  <el-form-item label="手机号码" prop="phone">
                    <el-input
                      v-model="queryParams.phone"
                      placeholder="请输入手机号码"
                      clearable
                    />
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
                    @click="add"
                    v-hasPermi="['govern:ownerCommittee:bind']"
                  >绑定业主</el-button>
                </el-col>
                <el-col :span="1.5">
                  <el-button
                    type="danger"
                    plain
                    icon="el-icon-delete"
                    size="mini"
                    @click="handleDelete"
                    :disabled="multiple"
                    v-hasPermi="['govern:ownerCommittee:delete']"
                  >删除</el-button>
                </el-col>
                <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"/>
              </el-row>
              <el-table v-loading="loading" :data="dataList" row-key="owner_id"  @selection-change="handleSelectionChange">
                <el-table-column type="selection" width="50" align="center" />
                <el-table-column label="业主姓名" align="center" prop="owner_name" />
                <el-table-column label="手机号码 / 账号" align="center" prop="owner_phone">
                  <template slot-scope="scope">
                    {{
                      hide(scope.row.owner_phone,3,7)
                    }}
                  </template>
                </el-table-column>
                <el-table-column label="身份证号码" align="center">
                  <template slot-scope="scope">
                    {{
                      hide(scope.row.card_no,4,13)
                    }}
                  </template>
                </el-table-column>
                <el-table-column label="性别" align="center" width="50">
                  <template slot-scope="scope">
                    {{
                      scope.row.gender=='0'?'男':scope.row.gender=='1'?'女':''
                    }}
                  </template>
                </el-table-column>
                <el-table-column label="最后操作时间" align="center"  width="180">
                  <template slot-scope="scope">
                    <span>{{ parseTime(scope.row.update_time) }}</span>
                  </template>
                </el-table-column>
                <el-table-column label="最后操作人" align="center" prop="update_by"/>
                <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
                  <template slot-scope="scope">
                    <el-button
                      size="mini"
                      type="text"
                      icon="el-icon-view"
                      @click="show(scope.row)"
                      v-hasPermi="['govern:ownerCommittee:queryOwner']"
                    >查看</el-button>
                    <el-button
                      size="mini"
                      type="text"
                      style="color: #f56c6c"
                      icon="el-icon-delete"
                      @click="handleDelete(scope.row)"
                      v-hasPermi="['govern:ownerCommittee:delete']"
                    >删除</el-button>
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
        <!-- 新增 修改-->
        <div v-show="isAdd">
          <el-dialog :visible.sync="open" width="950px" :before-close="closer" append-to-body>
            <el-form ref="form" :model="form" :rules="rules" label-width="230px" style="margin: 30px">
              <el-row style="margin-top: 20px;margin-left: 15px">
                <el-col style="margin-left: 15px" :span="59">
                  <el-row>
                    <el-form :model="queryParamses" ref="queryForm" size="small" :inline="true" v-show="showSearch">
                      <el-form-item label="业主姓名" prop="name">
                        <el-input
                          v-model="queryParamses.name"
                          placeholder="请输入业主姓名"
                          clearable
                        />
                      </el-form-item>
                      <el-form-item label="手机号码" prop="phone">
                        <el-input
                          v-model="queryParamses.phone"
                          placeholder="请输入手机号码"
                          clearable
                        />
                      </el-form-item>
                      <el-form-item>
                        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery1">搜索</el-button>
                        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
                      </el-form-item>
                    </el-form>
                  </el-row>
                  <!--              <el-row :gutter="10" class="mb8">
                                  <el-col :span="1.5">
                                    <el-button
                                      type="primary"
                                      plain
                                      icon="el-icon-plus"
                                      size="mini"
                                      @click="add"
                                    >新增</el-button>
                                  </el-col>
                                  <el-col :span="1.5">
                                    <el-button
                                      type="danger"
                                      plain
                                      icon="el-icon-delete"
                                      size="mini"
                                      @click="handleDelete"
                                      :disabled="multiple"
                                    >删除</el-button>
                                  </el-col>
                                  <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"/>
                                </el-row>-->
                  <el-table v-loading="loading" :data="dataList1" @selection-change="handleSelectionChange" ref="multipleTable">
                    <el-table-column type="selection" width="50" align="center"/>
                    <el-table-column label="业主姓名" align="center" prop="owner_name" />
                    <el-table-column label="手机号码 / 账号" align="center" prop="owner_phone">
                      <template slot-scope="scope">
                        {{
                          hide(scope.row.owner_phone,3,7)
                        }}
                      </template>
                    </el-table-column>
                    <el-table-column label="身份证号码" align="center">
                      <template slot-scope="scope">
                        {{
                          hide(scope.row.card_no,4,13)
                        }}
                      </template>
                    </el-table-column>
                    <el-table-column label="性别" align="center" width="50">
                      <template slot-scope="scope">
                        {{
                          scope.row.gender=='0'?'男':scope.row.gender=='1'?'女':''
                        }}
                      </template>
                    </el-table-column>
                    <el-table-column label="最后操作时间" align="center"  width="180">
                      <template slot-scope="scope">
                        <span>{{ parseTime(scope.row.update_time) }}</span>
                      </template>
                    </el-table-column>
                    <el-table-column label="最后操作人" align="center" prop="update_by"/>
                  </el-table>

                  <pagination
                    v-show="ownerTotal>0"
                    :total="ownerTotal"
                    :page.sync="queryParamses.pageNum"
                    :limit.sync="queryParamses.pageSize"
                    @pagination="getPersonList()"
                  />
                </el-col>
              </el-row>
              <el-divider/>
              <el-row>
                <el-col :span="8" style="margin-left: 75%">
                  <el-button style="margin-right: 20px" @click="closer">取 消</el-button>
                  <el-button type="primary" @click="bind">确 定</el-button>
                </el-col>
              </el-row>
            </el-form>
          </el-dialog>
        </div>
        <!-- 查看详情-->
        <div  v-show="details">
          <div class="add-view" >
            <el-form ref="formShow" :model="form"  label-width="130px" style="margin: 30px" >
              <el-row>
                <el-col>
                  <span>业主信息</span>
                  <el-button @click="cancel" style="margin-top: 15px;margin-left: 90%">关 闭</el-button>
                  <el-divider/>
                </el-col>
                <el-col>
                  <el-row>
                    <el-col :span="5">
                      <el-form-item label="所属小区" prop="communityName" disabled>
                        <el-input
                          :value="communityName"
                          disabled
                          clearable
                        />
                      </el-form-item>
                    </el-col>
                    <el-col :span="5">
                      <el-form-item label="所属楼宇：" prop="building_id">
                        <el-select :value="form.building_id" placeholder="" clearable disabled>
                          <el-option
                            v-for="i in BuildingList"
                            :key="i.buildingId"
                            :value="i.buildingId"
                            :label="i.buildingName"
                          />
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :span="5">
                      <el-form-item label="所属单元：" prop="unit_id">
                        <el-select :value="form.unit_id"   clearable  placeholder=""  disabled>
                          <el-option
                            v-for="i in unitList"
                            :key="i.unitId"
                            :value="i.unitId"
                            :label="i.unitName"
                          />
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :span="5">
                      <el-form-item label="所属房间：" prop="room_id">
                        <el-select :value="form.room_id"   clearable  disabled>
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
                    <el-col :span="5">
                      <el-form-item label="姓名：" prop="owner_name">
                        <el-input
                          :value="form.owner_name"
                          clearable
                          disabled
                        />
                      </el-form-item>
                    </el-col>
                    <el-col :span="7">
                      <el-form-item label="手机号码：" prop="owner_phone">
                        <el-input
                          :value="form.owner_phone"
                          clearable
                          disabled
                        />
                      </el-form-item>
                    </el-col>
                    <el-col :span="5">
                      <el-form-item label="性别：" prop="gender">
                        <el-select :value="form.gender" placeholder="" clearable disabled>
                          <el-option
                            v-for="i in gender"
                            :key="i.value"
                            :value="i.value"
                            :label="i.label"
                          />
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :span="5">
                      <el-form-item label="证件类型：" prop="card_type">
                        <el-select :value="form.card_type" placeholder="" clearable disabled>
                          <el-option
                            v-for="i in cardTypeList"
                            :key="i.dict_value"
                            :value="i.dict_value"
                            :label="i.dict_label"
                          />
                        </el-select>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="5">
                  <el-form-item label="政治面貌" prop="politics_status">
                    <el-select :value="form.politics_status" clearable  placeholder="" disabled>
                      <el-option
                        v-for="i in politicsStatus"
                        :key="i.value"
                        :value="i.value"
                        :label="i.label"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item label="宗教信仰" prop="religion">
                    <el-input
                      :value="form.religion"
                      clearable
                      disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item label="籍贯" prop="native_place" >
                    <el-input
                      :value="form.native_place"
                      disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item label="民族" prop="nation">
                    <el-input
                      :value="form.nation"
                      clearable
                      disabled
                    />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="5">
                  <el-form-item label="婚姻情况" prop="marriage_status">
                    <el-select :value="form.marriage_status"  clearable disabled  placeholder="">
                      <el-option
                        v-for="i in marriageStatus"
                        :key="i.value"
                        :value="i.value"
                        :label="i.label"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item label="兵役情况" prop="military">
                    <el-input
                      :value="form.military"
                      clearable
                      disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item label="文化程度" prop="edu_level">
                    <el-input
                      :value="form.edu_level"
                      disabled

                    />
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item label="国籍" prop="nationality">
                    <el-input
                      :value="form.nationality"
                      clearable
                      disabled
                    />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="5">
                  <el-form-item label="人口类型" prop="population">
                    <el-select :value="form.population" placeholder="" clearable disabled>
                      <el-option
                        v-for="i in population"
                        :key="i.value"
                        :value="i.value"
                        :label="i.label"
                      />
                    </el-select>
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item label="职业" prop="career">
                    <el-input
                      :value="form.career"
                      clearable
                      disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item label="QQ号码" prop="qq_number">
                    <el-input
                      :value="form.qq_number"
                      clearable
                      disabled
                    />
                  </el-form-item>
                </el-col>
                <el-col :span="5">
                  <el-form-item label="邮箱地址" prop="email" >
                    <el-input
                      :value="form.email"
                      clearable
                      disabled
                    />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="20">
                  <el-form-item label="个人介绍 / 备注" prop="self_intro">
                    <el-input
                      :value="form.self_intro"
                      clearable
                      disabled
                    />
                  </el-form-item>
                </el-col>
              </el-row>
              <el-divider/>
              <el-row>
                <el-row>
                  <el-col :span="3" style="margin-left: 85%">
                    <div v-show="!details">
                      <el-button style="margin-right: 20px" @click="closer">取 消</el-button>
                      <el-button type="primary" @click="add1" v-if="!form.account_id">确 定</el-button>
                      <el-button type="primary" @click="update" v-else>修 改</el-button>
                    </div>
                  </el-col>
                </el-row>
              </el-row>
            </el-form>
          </div>
        </div>


  </div>
</template>

<script>
import {getCurrentList,getBuildingList,getUnitList,getRoomList} from  '@/api/govern/community'
import {desensitization} from '@/utils/common'
import {
  query, queryList,bind, queryByRoomId, queryRoomByOwnerId,
  deletedOwner,queryDict
} from '@/api/govern/autonomy/ownerPerson/ownercommit'
import {objectMerge} from "@/utils";
export default {
  name: "index",
  props:{
    handRowId:Object,
    required:true
  },
  data() {
    return {
      //默认第一个选项卡
      activeName: "family",
      form:{
        owner_relation:'',
        community_id:'',
        self_intro:'',
        house_state:'',
        building_id:'',
        qq_number:'',
        email:'',
        unit_id:'',
        room_id:'',
        owner_name:'',
        owner_phone:'',
        population:'',
        gender:'0',
        military:'',
        owner_id:'',
        career:'',
        card_type:'I',
        delivery_time:null,
        marriage_status:'',
        license_url:'',
        card_font:'',
        card_back:'',
        card_no:'',
        domicile_address:'',
        face_url:'',
        religion:'',
        nation:'',
        data_source:'0',
        native_place:'',
        politics_status:'',
        edu_level:'',
        nationality:'',
        account_id:''
      },
      // 表单校验
      rules: {
        owner_name: [
          {required: true, message: "业主姓名不能为空", trigger: "blur" },
          {pattern:/[^ \x22]+/,message: "不能输入空格",trigger: "blur"},
        ],
        owner_phone: [
          //1、手机号码验证正则
          { required: true, message: '请输入手机号', trigger: 'blur' },
          { pattern:/^1[3456789]\d{9}$/, message: "手机号格式不对", trigger: "blur" },
        ],
        gender: [
          { required: true, message: "性别不能为空", trigger: "blur" }
        ],
        card_type: [
          { required: true, message: "证件类型不能为空", trigger: ["blur", "change"] }
        ],
        card_no: [
          //身份证验证
          { required: true, message: '请输入证件号码', trigger: 'blur' },
          { pattern: /(^[1-9]\d{5}(18|19|([23]\d))\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{3}[0-9Xx]$)|(^[1-9]\d{5}\d{2}((0[1-9])|(10|11|12))(([0-2][1-9])|10|20|30|31)\d{2}$)/,
            message: '证件号码格式有误！',
            trigger: 'blur',
          },
        ],
        card_font:[
          { required: true, message: "请选择证件照正面", trigger: "blur" }
        ],
        card_back:[
          { required: true, message: "请选择证件照反面", trigger: ["blur", "change"] }
        ],
        face_url:[
          { required: true, message: "请选择照片", trigger: ["blur", "change"] }
        ],
        owner_relation: [
          { required: true, message: "请选择户主关系", trigger: ["blur", "change"] }
        ],
        building_id:[
          { required: true, message: "请选择所属楼宇", trigger: ["blur", "change"] }
        ],
        unit_id:[
          { required: true, message: "请选择所属单元", trigger: ["blur", "change"]}
        ],
        room_id:[
          { required: true, message: "请选择所属房间",trigger: ["blur", "change"] }
        ],
        email: [
          {
            type: "email",
            message: "'请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
        qq_number: [
          {
            pattern:'[1-9]([0-9]{5,11})',
            message: "'请输入正确的qq号码",
            trigger: "blur"
          }
        ],
      },
      relationList:[],
      showForm:'',
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
        // {label:'群租',value:'7'}
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
      oldRoomId:'',
      carList:[],
      unitList:[],
      roomList:[],
      tenantList:[],
      cardTypeList:[],
      hosingList:[],
      carPortList:[],
      filterText: '',
      details:false,
      isAddCommit:false,
      // 显示搜索条件
      showSearch: true,
      isAdd:false,
      open:false,
      isEdit:false,
      isopenAdd:false,
      //查找条件
      queryParams:{
        phone:'',
        name:'',
        isCat:'',
        isOutIn:'',
        pageNum: 1,
        pageSize: 10,
      },
      queryParamses:{
        phone:'',
        name:'',
        isCat:'',
        isOutIn:'',
        pageNum: 1,
        pageSize: 10,
      },
      // 非单个禁用
      single: true,
      isList:true,
      isShow:true,
      owner_relation:'',
      isopen:false,
      // 非多个禁用
      multiple: true,
      ids:[],
      communityId:'',
      communityName:'',
      // 总条数
      total: 0,
      ownerTotal: 0,
      //
      dataList:[],
      dataList1:[],
      BuildingList:[],
      // 遮罩层
      loading: true,
      //查看对象
      info:'',
      inout:[
        {value:'0',label:'是'},
        {value:'1',label:'否'}
      ],
      data: [],
    };
  },

  created() {
    this.initData()
    queryDict().then(e=>{
      this.cardTypeList=e.data
    })
  },
  watch: {
    //小区过滤
    filterText(val) {
      this.$refs.tree.filter(val);
    },
    handRowId:{
      handler(newVa){
        this.getPersonList()
        this.getList()
      },
      deep:true,
    }
  },

  methods: {
    hide(str,begin,end){
      if(str){
        return  desensitization(str,begin,end)
      }
      return  "";
    },
    openUrl(url){
      window.open(url)
    },
    cancel(){
      this.details=false
      this.isAdd=false
      this.isList=true
    },
    up(row){
      this.public(row).then(e=>{
        this.isAdd=true;
        this.owner_relation=row['owner_relation']
        this.isList=false
        this.details=false
        this.oldRoomId=row.room_id
      })

    },
    show(row){
      this.public(row).then(e=>{
        this.details=true;
        this.isAdd=false;
        this.isList=false
      })
      const queryForm = {
        community_id: this.form.community_id,
        building_id: this.form.building_id,
        unit_id: this.form.unit_id,
        room_id: this.form.room_id,
      }
      queryByRoomId(queryForm).then(e=>{
        this.familyList=e.data
      })
      queryRoomByOwnerId({id:this.form.owner_id}).then(e=>{
        this.hosingList=e.data
      })
/*
      queryCatByOwnerId({id:this.form.account_id}).then(e=>{
        this.carList=e.data
      })
*/
/*
      queryTenantByOwnerId({id:this.form.owner_id}).then(e=>{
        this.tenantList=e.data
      })
      queryCarPortByOwnerId({id:this.form.account_id}).then(e=>{
        this.carPortList=e.data
      })
*/
    },
    async public(row) {

      if (row.owner_relation=='0'){
        this.relationList=[
          {label:'本人',value:'0'},
        ]
      }else {
        this.relationList=[
          {label:'配偶',value:'1'},
          {label:'父母',value:'2'},
          {label:'子女',value:'3'},
          {label:'其它',value:'4'}
        ]
      }

      this.form = row
      this.communityName = row['community_name']
      let loading = this.$loading({
        lock: true,
        text: 'Loading',
        spinner: 'el-icon-loading',
        background: 'rgba(0, 0, 0, 0.7)'
      });
      await getBuildingList(row.community_id).then(e => {
        this.BuildingList = e.data
      })
      await getUnitList(row.building_id).then(e => {
        this.unitList = e.data
      })
      await getRoomList(row.unit_id).then(e => {
        this.roomList = e.data
      })
      this.form['house_state'] = row.room_status
      loading.close()
      return true;
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.owner_id)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    //获取单元
    getUnitList(e){
      this.form['owner_relation']=''
      this.relationList=''
      this.roomList=''
      const buildingId=e;
      this.form.unit_id=''
      this.form.room_id=''
      this.form['house_state']=''
      getUnitList(buildingId).then(e=>{
        this.unitList=e.data
      })
    },
    //获取房间
    getRoomList(e){
      this.form['owner_relation']=''
      const buildingId=e;
      this.relationList=''
      this.form.room_id=''
      this.form['house_state']=''
      getRoomList(buildingId).then(e=>{
        this.roomList=e.data
      })
    },
    closer(){
      this.isAddCommit=false;
      this.isopen=false;
      this.isEdit=false;
      this.open=false;
      this.isAdd=false;
      this.details=false
      this.isList=true
      this.getList()
      this.getCommitList()
      this.getPersonList()
    },
    //房屋状态
    getRoomState(e){
      let id;
      this.roomList.forEach((i,index)=>{
        const {roomStatus,roomId,ownerId}=i
        if (e==roomId){

          this.form['house_state']=roomStatus
          id=ownerId
          return;
        }
      })
      if (this.form['owner_id']==id){
        this.relationList=[
          {label:'本人',value:'0'}
        ]
      }else {

        if (id != 0) {//房间存在业主 移除 本人选项
          this.relationList = [
            {label: '配偶', value: '1'},
            {label: '父母', value: '2'},
            {label: '子女', value: '3'},
            {label: '其它', value: '4'}
          ]

        } else {
          this.relationList = [
            {label: '本人', value: '0'}
          ]
        }
      }
      this.form.owner_relation=''
    },
    bind(){
      let obj = {
        committeeId:'',
        ownerIds:'',
      }
      obj.committeeId = this.handRowId.committeeId
      obj.ownerIds= this.ids;
      bind(obj).then(e => {
        if (e.code == 200) {
          this.$message.success(e.msg)
          this.closer()
        } else {
          this.$message.error(e.msg)
        }
      })
    },

    add1(){
      this.form['community_id']=this.communityId
      this.$refs["form"].validate(valid => {
        if (valid) {
          add(this.form).then(e => {
            if (e.code == 200) {
              this.$message.success(e.msg)
              this.closer()
            } else {
              this.$message.error(e.msg)
            }
          })
        }
      })
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      console.log(row)
      const ownerId = row.owner_id || this.ids;
      this.$confirm('是否确认删除所选中的的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return deletedOwner(ownerId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
    },
    update(){
      this.form['oldRoomId']=this.oldRoomId+''
      if (this.form['owner_relation']!='0'){
        this.form['license_url']=''
      }
      if (this.form['owner_relation']=='0'&&!this.form['delivery_time']){
        this.$message.error('请选择交房日期!!')

        return;
      }
      this.$refs["form"].validate(valid => {
        if (valid){
          update(this.form).then(e=>{
            if (e.code==200){
              this.$message.success(e.msg)
              this.closer()
            }else {
              this.$message.error(e.msg)
            }
          })
        }
      })
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
        }
      })
      this.getCommitList();
      this.getList();
      this.getPersonList();
    },
    //查询数据
    getList() {
        this.loading = true;
      this.queryParams['communityId']=this.handRowId.communityId
        query(this.queryParams).then((e) => {
          this.dataList = e.rows
          this.total = e.total
          this.loading = false
        })
    },
    //查询数据
    getPersonList() {
      this.loading = true;
      this.queryParamses['communityId']=this.handRowId.communityId
      queryList(this.queryParamses).then((e) => {
        this.dataList1=e.rows
        this.ownerTotal=e.total
        this.loading=false
      })
    },
    getCommitList() {
      this.loading = true;
    },
    //获取点击小区id
    getCommunityId(e){
      this.queryParams['communityId']=e.id
      this.communityName=e.label
      this.communityId=e.id
      this.getList()
    },
    /** 搜索按钮操作 */
    handleQuery() {

      this.queryParams.pageNum=1
      this.getList();
      this.getPersonList();
    },
    /** 搜索按钮操作 */
    handleQuery1() {

      this.queryParams.pageNum=1
      this.getPersonList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    add(){
      this.isAdd=true
      this.open=true
    },
    edit(){
      this.isEdit=true
      this.isopen=true
    }
  },

}
</script>

<style>
.aaaaa{
  height:100%;
  position: relative;
}
.hshsh{
  height: 100px;
  display: flex;
  align-items: center;
  justify-content: center;
  /*position: absolute;*/
  /*left: 50%;*/
  /*top: 50%;*/
}
.butto{
  margin-top: 600px;
}
.add-view span{
  font-weight: bold;
  color: black;
}
.add-view .avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.add-view .avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}
.add-view .avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}
.add-view .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
.add-view .avatar1 {
  width: 356px;
  height: 178px;
  display: block;
}
</style>

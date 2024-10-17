<template>
  <div class="app-container">
    <div v-show="mainShow">
      <el-row :gutter="20">
        <!--部门数据-->
        <el-col :span="4" :xs="24">
          <div class="head-container">
            <el-input
              v-model="filterText"
              placeholder="请输入小区称"
              clearable
              size="small"
              prefix-icon="el-icon-search"
              style="margin-bottom: 20px"
            />
          </div>
          <div class="head-container">
            <el-tree
              :data="communityList"
              :props="defaultProps"
              :filter-node-method="filterNode"
              :indent=25
              ref="tree"
              highlight-current
              node-key="key"
              icon-class="el-icon-office-building"
              @node-click="pickTree"
            />
          </div>
        </el-col>

        <!--用户数据-->
        <el-col :span="20" :xs="24" v-show="listShow">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" label-width="68px">
            <el-form-item label="产权人" prop="userName">
              <el-input
                v-model="queryParams.ownerName"
                placeholder="请输入产权人"
                clearable
                style="width: 160px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="房屋状态" prop="status">
              <el-select
                v-model="queryParams.roomStatus"
                placeholder="房屋状态"
                clearable
                style="width: 160px"
              >
                <el-option
                  v-for="status in defaultRoomStatus"
                  :key="status.id"
                  :label="status.name"
                  :value="status.id"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="装修进度" prop="renovationAuditStatus">
              <el-select
                v-model="queryParams.renovationAuditStatus"
                placeholder="装修进度"
                clearable
                style="width: 160px"
              >
                <el-option
                  v-for="item in renovationAuditStatusOptions"
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
                type="success"
                plain
                icon="el-icon-edit"
                size="mini"
                :disabled="single"
                @click="handleAddRoom"
              >装修办理
              </el-button>
            </el-col>
          </el-row>

          <el-table v-loading="loading" :data="roomList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="50" align="center"/>
            <el-table-column label="所属楼栋" align="center" key="buildingName" prop="buildingName"/>
            <el-table-column label="所属单元" align="center" key="unitName" prop="unitName"/>
            <el-table-column label="房号" align="center" key="roomName" prop="roomName"/>
            <el-table-column label="装修进度" align="center" key="renovationAuditStatus" prop="roomStatus">
              <template slot-scope="scope">
                {{ getRenovationAuditStatusOptions(scope.row.renovationAuditStatus) }}
              </template>
            </el-table-column>
            <el-table-column label="房间总面积" align="center" key="totalArea" prop="totalArea">
              <template slot-scope="scope">
                {{ scope.row.totalArea }}<span>m<sup>2</sup></span>
              </template>
            </el-table-column>
            <el-table-column label="产权人" align="center" key="ownerName" prop="ownerName"/>
            <el-table-column label="产权人手机号" align="center" key="ownerPhone" prop="ownerPhone"/>
            <el-table-column label="居住人数" align="center" key="liveNum" prop="liveNum"/>
            <el-table-column label="房屋属性" align="center" key="roomType" prop="roomType">
              <template slot-scope="scope">
                {{ roomTypeStr(scope.row.roomType) }}
              </template>
            </el-table-column>
            <el-table-column label="最后操作时间" align="center" prop="updateTime">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.updateTime) }}</span>
              </template>
            </el-table-column>
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
                  v-if="scope.row.renovationAuditStatus=='0'"
                  @click="handleUpdateRoom(scope.row)"
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
        </el-col>

      </el-row>

     
    </div>

      <!--  办理装修  -->
      
      <div v-show="updateRoomOpen" v-cloak>
        <el-tabs v-model="activeName">
          <el-tab-pane label="房屋信息" name="house">
            <el-form ref="updateRoomForm" :model="updateRoomForm" label-width="140px">
              <h4 class="form-header">
                房屋信息
              </h4>
              <el-row>
                <el-col :span="20">
                  <el-row>
                    <el-col :span="6" :offset="0">
                      <el-form-item label="所属小区:" style="width: 271px;">
                          {{ updateRoomForm.communityName }}
                      </el-form-item>
                    </el-col>
                    <el-col :span="6" :offset="0">
                      <el-form-item label="楼栋号:" label-width="126px">{{ updateRoomForm.buildingName }}</el-form-item>
                    </el-col>
                    <el-col :span="6" :offset="0">
                      <el-form-item label="单元楼:" label-width="126px">{{ updateRoomForm.unitName }}</el-form-item>
                    </el-col>
                    <el-col :span="6" :offset="0">
                      <el-form-item label="房间号:">
                        {{ updateRoomForm.roomName }}
                      </el-form-item>
                    </el-col>
                    
                  </el-row>

                  <el-row>
                    <el-col :span="6" :offset="0">
                      <el-form-item label="房屋坐落:" style="width: 271px;">{{ updateRoomForm.houseAddress }}</el-form-item>
                    </el-col>
                    <el-col :span="6" :offset="0">
                      <el-form-item label="房屋属性:">{{ roomTypeStr(updateRoomForm.roomType) }}</el-form-item>
                    </el-col>
                    <el-col :span="6" :offset="0">
                      <el-form-item label="房屋面积:">{{ updateRoomForm.totalArea }}<span
                        style="line-height:30px;">m<sup>2</sup></span>
                      </el-form-item>
                    </el-col>
                    <el-col :span="6" :offset="0">
                      <el-form-item label="所在层:">{{ updateRoomForm.floor }}</el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="6" :offset="0">
                      <el-form-item label="房屋结构:" style="width: 271px;">{{ roomStructureStr(updateRoomForm.structure) }}</el-form-item>
                    </el-col>
                    <el-col :span="6" :offset="0">
                      <el-form-item label="交房时间:">{{ updateRoomForm.deliveryTime }}</el-form-item>
                    </el-col>
                    <el-col :span="6" :offset="0">
                      <el-form-item label="房屋状态:">{{ roomStatusStr(updateRoomForm.roomStatus) }}</el-form-item>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
              <h4 class="form-header">业主信息</h4>
              <el-row>
                <el-table :data="inhabitantList">
                  <el-table-column label="住户姓名" align="center" key="ownerName" prop="ownerName"/>
                  <el-table-column label="证件号码" align="center" key="cardNo" prop="cardNo"/>
                  <el-table-column label="手机号" align="center" key="ownerPhone" prop="ownerPhone"/>
                  <el-table-column label="与户主关系" align="center" key="ownerRelation" prop="ownerRelation">
                    <template slot-scope="scope">
                      <span>{{ relationStr(scope.row.ownerRelation) }}</span>
                    </template>
                  </el-table-column>
                  <el-table-column label="入住时间" align="center" key="auditTime" prop="auditTime"/>
                </el-table>
              </el-row>
            </el-form>
            <el-row>
              <el-col :span="4" :offset="20" style="margin-top: 5px;">
                <el-button @click="cancelProcessingDetail">取 消</el-button>
              </el-col>
            </el-row>
          </el-tab-pane>
          <el-tab-pane label="装修办理" name="handle">
            <el-form ref="updateRoomForm" :rules="processingRules"  label-width="140px">
              <h4 class="form-header">
                装修信息
              </h4>
              <el-row>
                <el-col :span="20">
                  <el-row>
                    <el-col :span="8" :offset="0">
                      <el-form-item label="装修时间:" prop="dateRange">
                        <el-date-picker
                          v-model="dateRange"
                          style="width: 240px"
                          value-format="yyyy-MM-dd"
                          type="daterange"
                          range-separator="-"
                          start-placeholder="开始日期"
                          end-placeholder="结束日期"
                        ></el-date-picker>  
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="8" :offset="0" >
                      
                      <el-form-item label="装修施工单位名称:" prop="companyName">
                        <el-input
                          v-model="companyName"
                          placeholder="请输入装修施工单位名称(必填)"
                          clearable
                          style="width:240px"
                        />
                      </el-form-item>
                    </el-col>
                    
                  </el-row>
                  <el-row>
                    <el-col :span="8" :offset="0">
                      <el-form-item label="负责人:"  prop="personName">
                        <el-input
                          v-model="personName"
                          placeholder="请输入负责人(必填)"
                          clearable
                          style="width:240px"
                        />  
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="8" :offset="0">
                      <el-form-item label="负责人联系方式:"  prop="personPhone">
                        <el-input
                          v-model="personPhone"
                          placeholder="请输入负责人联系方式(必填)"
                          maxlength="11"
                          clearable
                          style="width:240px"
                        /> 
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
              <h4 class="form-header">
                装修/安装项目
              </h4>
              <el-row style="margin-left: 30px">
                <el-col :span="20"> 
                 
                  <el-checkbox-group v-model="checkList">
                            <el-row class="checkrow">
                              <el-col :span="6" :offset="0">
                                <el-checkbox label="1" >
                                  1、改造部分线路
                                </el-checkbox>
                              </el-col>
                              <el-col :span="6" :offset="0">
                                <el-checkbox label="2">
                                  2、隔墙改动
                                </el-checkbox>
                              </el-col>
                            </el-row>
                            <el-row class="checkrow">
                              <el-col :span="6" :offset="0">
                                <el-checkbox label="3" >
                                  3、餐厅改动
                                </el-checkbox>
                              </el-col>
                              <el-col :span="6" :offset="0">
                                <el-checkbox label="4">
                                  4、阳台包封（按要求制作安装）
                                </el-checkbox>
                              </el-col>
                            </el-row>
                            <el-row class="checkrow">
                              <el-col :span="6" :offset="0">
                                <el-checkbox label="5" >
                                  5、水管改造
                                </el-checkbox>
                              </el-col>
                              <el-col :span="6" :offset="0">
                                <el-checkbox label="6">
                                  6、卫生间改造
                                </el-checkbox>
                              </el-col>
                            </el-row>
                            <el-row class="checkrow">
                              <el-col :span="6" :offset="0">
                                <el-checkbox label="7" >
                                  7、吊顶
                                </el-checkbox>
                              </el-col>
                              <el-col :span="6" :offset="0">
                                <el-checkbox label="8">
                                  8、空调（挂壁机、柜机、中央空调）
                                </el-checkbox>
                              </el-col>
                            </el-row>
                            <el-row class="checkrow">
                              <el-col :span="10" :offset="0">
                                <el-checkbox label="9" >
                                  9、其他
                                </el-checkbox>
                                <el-input
                                    v-if="checkList.includes('9')"
                                    v-model="remarks"
                                    placeholder="请输入"
                                    clearable
                                    style="width:240px;margin-left: 15px;"
                                  /> 
                              </el-col>
                            </el-row>
                  </el-checkbox-group>
                </el-col>
              </el-row>
              <h4 class="form-header" v-if="!isDisabled">
                办理装修信息
              </h4>
              <el-row v-if="!isDisabled">
                <el-col :span="20">
                  <el-row>
                    <el-col :span="8" :offset="0" >
                      <el-form-item label="审核备注:" prop="content">
                        <el-input
                          type="textarea"
                          v-model="content"
                          placeholder="请输入装修施工单位名称"
                          clearable
                          style="width:240px"
                        />
                      </el-form-item>
                    </el-col>
                    
                  </el-row>
                  <el-row>
                    <el-col >
                      <el-form-item label="房屋图片:" prop="image">
                        <image-upload v-model="image" :limit="3" />
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
            </el-form>
            <el-row>
              <el-col :span="4" :offset="20" style="margin-top: 5px;">
                <el-button type="primary" @click="submit">提 交</el-button>
                <el-button @click="cancelProcessingDetail">取 消</el-button>
              </el-col>
            </el-row>
          </el-tab-pane>
          <el-tab-pane label="要件信息" name="document">
            <el-form ref="updateRoomForm" :rules="processingRules" :model="updateRoomForm" label-width="140px">
              <h4 class="form-header">
                要件信息
              </h4>
              <el-row>
                <el-col :span="20">
                  <el-row>
                    <el-col>
                      <el-form-item label="房屋所有权（或证明其合法权益的有效证明）:" prop="warrant">
                        <image-upload v-model="warrant" :limit="3" />
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="8" :offset="0">
                      <el-form-item label="申请人身份证正面:" prop="cardFont">
                        <image-upload v-model="cardFont" :limit="1" />
                      </el-form-item>
                    </el-col>
                    <el-col :span="8" :offset="0">
                      <el-form-item label="申请人身份证反面:" prop="cardBack">
                        <image-upload v-model="cardBack" :limit="1" />
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col>
                      <el-form-item label="装修单位提交装修设计方案(最多上传三张):" prop="designScheme">
                        <image-upload v-model="designScheme" :limit="3" />
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col>
                      <el-form-item label="装修合同(最多上传三张):" prop="decorationContract">
                        <image-upload v-model="decorationContract" :limit="3" />
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col>
                      <el-form-item label="其他(最多上传三张):" prop="other">
                        <image-upload v-model="other" :limit="3" />
                      </el-form-item>
                    </el-col>
                  </el-row>
                  
                </el-col>
              </el-row>
            </el-form>
            <el-row>
              <el-col :span="4" :offset="20" style="margin-top: 5px;">
                <el-button type="primary" @click="submit">提 交</el-button>
                <el-button @click="cancelProcessingDetail">取 消</el-button>
              </el-col>
            </el-row>
          </el-tab-pane>
        </el-tabs>
        
      </div>

  </div>
</template>

<script>
import {
  deListPageRooms,
  treeRoom,
  getRoomDetail,
  getRoomGraphData,
  roomSearchListPage,
  transferOwnership,
  roomFamilyList,
} from "@/api/estate/center/room";
import {getAllCommunityList} from "@/api/estate/community"
import {getToken} from "@/utils/auth";
import { queryDict} from "@/api/estate/center/personnelFileManage/ownerFile";
import {addProcessing,updateProcessing,getProcessing } from "@/api/estate/customer/decorate/decorationProcessing";

export default {
  name: "decorationProcessing",
  dicts:['renovation_audit_status'],
  created() {
    this.getDicts("renovation_audit_status").then((response) => {
      this.renovationAuditStatusOptions = response.data;
    });
    this.getDicts("property_of_house").then(response => {
      this.defaultRoomType = response.data;
    });
    // 左侧树数据加载
    this.treeList();
    this.getAllCommunityList();
  },
  computed: {
    selectExcludeBuildingNums() {
      let nums = [];
      const startBuildingNum = this.insertBuildingBatchForm.startBuildingNum;
      const endBuildingNum = this.insertBuildingBatchForm.endBuildingNum;
      const length = endBuildingNum - startBuildingNum;
      if (startBuildingNum !== undefined && endBuildingNum !== undefined) {
        for (let i = 0; i <= length; i++) {
          nums[i] = startBuildingNum + i;
        }
      }
      return nums;
    },
    selectExcludeUnitNums() {
      let nums = [];
      const startUnitNum = this.insertUnitAndRoomBatchForm.startUnitNum;
      const endUnitNum = this.insertUnitAndRoomBatchForm.endUnitNum;
      const length = endUnitNum - startUnitNum;
      if (startUnitNum !== undefined && endUnitNum !== undefined) {
        for (let i = 0; i <= length; i++) {
          nums[i] = startUnitNum + i;
        }
      }
      return nums;
    }
  },
  data() {
   
    return {
      activeName: 'house',
      // 树筛选文本
      filterText: '',
      roomOptions:[],
      // 列表选中的ids
      ids: [],
      communityIds:[],
      renovationAuditStatusOptions:[],
      // 页面显示
      // 主页面显示
      mainShow: true,
      // 主页面内部列表显示
      listShow: true,
      // 主页面内部图形显示
      graphShow: false,
      isDisabled:false,
      // 详情显示
      detailShow: false,
      // 过户页面显示
      transferOwnershipShow: false,
      // 遮罩层
      loading: true,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,

      // tree
      currentNodeKey: undefined,
      // 图片
      // 用户导入参数
      upload: {
        // 设置上传的请求头部
        headers: {Authorization: "Bearer " + getToken()},
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/file/upload",
      },

      // 外来人员表单数据
      foreignForm: {
        ownerRelation: '',
        communityId: '',
        selfIntro: '',
        roomStatus: '',
        buildingId: '',
        qqNumber: '',
        email: '',
        unitId: '',
        roomId: '',
        ownerName: '',
        ownerPhone: '',
        population: '',
        gender: '',
        military: '',
        ownerId: '',
        career: '',
        cardType: '',
        deliveryTime: null,
        marriageStatus: '',
        licenseUrl: '',
        cardFont: '',
        cardBack: '',
        cardNo: '',
        domicileAddress: '',
        faceUrl: '',
        religion: '',
        nation: '',
        dataSource: '0',
        nativePlace: '',
        politicsStatus: '',
        eduLevel: '',
        nationality: '',
        accountId: ''
      },
      // 总条数
      total: 0,
      ownerTotal: 0,
      // 弹出层标题
      title: "",
      roomGraphData: {},
      selectData: {},
      // 查询参数
      queryParams: {
        id: undefined,
        pageNum: 1,
        pageSize: 10,
        level: 1,
        ownerName: undefined,
        roomStatus: undefined
      },
      ownerQueryParams: {
        communityId: undefined,
        pageNum: 1,
        pageSize: 10,
        ownerName: undefined,
        ownerPhone: undefined,
        cardNo: undefined
      },
      // 过户参数
      transferOwnershipParams: {
        roomId: undefined,
        ownerId: undefined,
        licenseUrl: ''
      },
      // 房号下拉数据
      selectRoomNumberList: [],
      // 身份类型下拉数据
      cardTypeList: [],
      // 楼号下拉数据
      selectFloorList: [],
      // 单元下拉数据
      selectRoomUnitList: [],
      defaultRoomStatus: [
        {id: 0, name: '未售', background: '#1b88e7'},
        {id: 1, name: '已售', background: '#e8e8e8'},
        {id: 2, name: '入住', background: '#1be751'},
        {id: 3, name: '装修', background: '#efa648'},
        {id: 4, name: '已拿房', background: '#e2cc00'},
        {id: 5, name: '整租', background: '#00d1ff'},
        {id: 6, name: '合租', background: '#00b4cd'},
        // {id: 7, name: '群租', background: '#ff4949'}
      ],
      // 房屋类型
      defaultRoomType: [
        // {id: 1, name: '安置房'},
        // {id: 2, name: '商品房'},
        // {id: 3, name: '公租房'}
      ],
      // 房屋结构 1=框架结构,2=钢结构,3=钢筋混泥土结构,4=混合结构,5=砖木结构,6=其他结构
      defaultRoomStructure: [
        {id: 1, name: '框架结构'},
        {id: 2, name: '钢结构'},
        {id: 3, name: '钢筋混泥土结构'},
        {id: 4, name: '混合结构'},
        {id: 5, name: '砖木结构'},
        {id: 6, name: '其他结构'}
      ],
      relationList: [
        {label: '本人', value: '0'},
        {label: '配偶', value: '1'},
        {label: '父母', value: '2'},
        {label: '子女', value: '3'},
        {label: '其它', value: '4'},
        {label: '租客', value: '5'}
      ],
      politicsStatus: [
        {label: '党员', value: '0'},
        {label: '团员', value: '1'},
        {label: '其它', value: '2'}
      ],
      marriageStatus: [
        {label: '未婚', value: '0'},
        {label: '已婚', value: '1'},
      ],
      population: [
        {label: '户籍人口', value: '0'},
        {label: '常驻人口', value: '1'},
        {label: '暂住人口', value: '2'},
      ],
      gender: [
        {label: '男', value: '0'},
        {label: '女', value: '1'},
      ],

      // 左侧tree数据
      communityList: [],
      allCommunityList: [],
      // tree默认props
      defaultProps: {
        children: "children",
        label: "name"
      },
      dateRange:[],
      // 列表数据
      roomList: [],
      ownerList: [],
      isPlain: true,
      defaultTab: 'family',
      // 单元房屋批量添加是否显示弹出层
      insertRoomBatchOpen: false,
      // 单元房屋批量添加表单参数
      insertRoomBatchForm: {},
      

      // 单元房屋批量添加是否显示弹出层
      insertUnitAndRoomBatchOpen: false,
      updateUnitOpen: false,
      // 单元房屋批量添加表单参数
      insertUnitAndRoomBatchForm: {},
      updateUnitForm: {unitName: ''},
      


      // 楼宇批量添加是否显示弹出层
      buildingBatchInsertOpen: false,
      // 楼宇修改是否显示弹出层
      updateBuildingOpen: false,
      // 楼宇批量添加表单参数
      insertBuildingBatchForm: {},
      // 楼宇修改表单参数
      updateBuildingForm: {buildingName: ''},
    
      processingRules: {
        dateRange: [
          {required: false, message: "请选择装修时间", trigger: "blur"}
        ],
        companyName: [
          {required: false, message: "请输入装修施工单位名称", trigger: "blur"}
        ],
        personName: [
          {required: false, message: "请输入负责人", trigger: "blur"}
        ],
        content: [
          {required: false, message: "请输入审核内容", trigger: "blur"}
        ],
        image: [
          {required: false, message: "请上传图片", trigger: "blur"}
        ],
        personPhone: [
          {required: false, message: '请输入负责人联系方式', trigger: 'blur'},
          {pattern: /^1[3456789]\d{9}$/, message: "负责人联系方式格式不对", trigger: "blur"},
        ]
      },

      // 表单校验
      rules: {
        ownerName: [
          {required: true, message: "业主姓名不能为空", trigger: "blur"},
          {pattern: /[^ \x22]+/, message: "不能输入空格", trigger: "blur"},
        ],
        ownerPhone: [
          //1、手机号码验证正则
          {required: true, message: '请输入手机号', trigger: 'blur'},
          {pattern: /^1[3456789]\d{9}$/, message: "手机号格式不对", trigger: "blur"},
        ],
        gender: [
          {required: true, message: "性别不能为空", trigger: "blur"}
        ],
        cardType: [
          {required: true, message: "证件类型不能为空", trigger: ["blur", "change"]}
        ],
        licenseUrl: [
          {required: true, message: "请上传房产证明", trigger: ["blur", "change"]}
        ],
        cardNo: [
          //证件验证
          {required: true, message: '请输入证件号码', trigger: 'blur'},
        ],
        cardFont: [
          {required: true, message: "请选择证件照正面", trigger: "blur"}
        ],
        cardBack: [
          {required: true, message: "请选择证件照反面", trigger: ["blur", "change"]}
        ],
        faceUrl: [
          {required: true, message: "请选择照片", trigger: ["blur", "change"]}
        ],
        owner_relation: [
          {required: true, message: "请选择户主关系", trigger: ["blur", "change"]}
        ],
        building_id: [
          {required: true, message: "请选择所属楼宇", trigger: ["blur", "change"]}
        ],
        unit_id: [
          {required: true, message: "请选择所属单元", trigger: ["blur", "change"]}
        ],
        room_id: [
          {required: true, message: "请选择所属房间", trigger: ["blur", "change"]}
        ],
        email: [
          {
            type: "email",
            message: "'请输入正确的邮箱地址",
            trigger: ["blur", "change"]
          }
        ],
      },
      renovationAuditStatusList:[],
      updateRoomBatchOpen: false,
      updateRoomBatchForm: {},
      updateRoomBatchRules: {},

      updateRoomOpen: false,
      updateRoomForm: {
          
      },
      id:undefined,
      companyName:undefined,
      personName:undefined,
      personPhone:undefined,
      remarks:undefined,
      content: undefined,
      image:undefined,
      warrant:undefined,
      cardFont:undefined,
      cardBack:undefined,
      designScheme:undefined,
      other:undefined,
      decorationContract:undefined,
      checkList:[''],
      updateRoomRules: {},
      detailForm: {},
      inhabitantList: [],


      imageUrl: '',
      cardFontImageUrl: '',
      licenseUrl: '',
      cardBackImageUrl: '',
      faceUrl: '',
    }
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    getAllCommunityList() {
      getAllCommunityList().then(response => {
        this.allCommunityList = response.data;
      });
    },
    // 筛选树节点
    filterNode(value, data) {
      if (!value) return true;
      return data.name.indexOf(value) !== -1;
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.single = selection.length !== 1;
      this.ids = selection.map(item => item.id);
      this.communityIds = selection.map(item => item.communityId);
      
      this.renovationAuditStatusList = selection.map(item => item.renovationAuditStatus);
      this.multiple = !selection.length;
    },
    // 获取分页列表数据
    getList() {
      deListPageRooms(this.queryParams).then(response => {
        this.roomList = response.rows;
        this.total = response.total;
        this.loading = false;
      }).catch(() => {
        this.loading = false;
      });
    },
    // 分页获取业主列表
    searchOwner() {
      this.ownerQueryParams.roomId = this.transferOwnershipParams.roomId;
      roomSearchListPage(this.ownerQueryParams).then(response => {
        this.ownerList = response.rows;
        this.ownerTotal = response.total;
      }).catch(() => {
      });
    },
    // 过户
    transferOwnership(ownerId, ownerName) {
      if (this.transferOwnershipParams.licenseUrl === '' || this.transferOwnershipParams.licenseUrl === undefined) {
        this.$modal.msgWarning("请上传房产证明！！！");
        return;
      }
      this.transferOwnershipParams.ownerId = ownerId;
      let $this = this;
      this.$modal.confirm('是否过户"' + ownerName + '"？').then(function () {
        return transferOwnership($this.transferOwnershipParams);
      }).then(() => {
        this.$modal.msgSuccess("过户成功");
        this.licenseUrl = '';
      
        this.getList();
      }).catch((err) => {
      });
    },
    // 获取证件类型
    getCardType() {
      if (this.cardTypeList.length == 0) {
        queryDict().then(e => {
          this.cardTypeList = e.data
        })
      }
    },
    treeList() {
      // 左侧树数据加载
      treeRoom().then(response => {
        this.communityList = response.data;
        this.queryParams.id = this.communityList[0].id
        this.ownerQueryParams.communityId = this.communityList[0].id
        this.currentNodeKey = this.communityList[0].key
        this.selectData = this.communityList[0];
        this.selectData.communityId = this.communityList[0].id
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.currentNodeKey); //一定要加这个选中了否则样式没有出来
        });
        this.getList();
      });
    },
    reloadTreeList() {
      // 左侧树数据加载
      treeRoom().then(response => {
        this.communityList = response.data;
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.currentNodeKey); //一定要加这个选中了否则样式没有出来
        });
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    handleQueryOwner() {
      this.ownerQueryParams.pageNum = 1;
      this.searchOwner();
    },

    /** 房间操作 */
    // 查看
    detail(row) {
      this.mainShow = false;
      this.detailShow = true;
      getRoomDetail(row.id,row.communityId).then(response => {
        this.detailForm = response.data;
      });
      roomFamilyList(id).then(response => {
        this.inhabitantList = response.data;
      });
    },
    //办理装修
    handleAddRoom() { 
     
      var renovationAuditStatus = this.renovationAuditStatusList[0];
      if (renovationAuditStatus) { 
        this.$modal.msgWarning("当前房间已办理装修");
        return; 
      }
      this.activeName = "house";
      this.updateRoomForm = {};
      this.imageUrl = '';
      this.mainShow = false;
      this.updateRoomOpen = true;
      this.isDisabled = false; 
      var roomId = this.ids;
      var communityId = this.communityIds;
      this.checkList = [],
        this.dateRange = [];
        this.companyName = undefined;
          this.personName = undefined;
          this.personPhone = undefined;
          this.remarks = undefined;
          this.warrant = undefined;
          this.cardFont = undefined;
          this.cardBack = undefined;
          this.designScheme = undefined;
          this.other = undefined;
          this.decorationContract = undefined;
      getRoomDetail(roomId,communityId).then(response => {
          this.updateRoomForm = response.data;
          this.imageUrl = response.data.roomUrl;
        });
        roomFamilyList(roomId).then(response => {
          this.inhabitantList = response.data;
        });
    },
      //修改办理装修
    handleUpdateRoom(row) {
      this.isDisabled = true; 
      var renovationAuditStatus = row.renovationAuditStatus;
      if (!renovationAuditStatus) { 
        this.$modal.msgWarning("当前房间未办理装修");
        return; 
      }
      this.imageUrl = '';
      this.mainShow = false;
      this.updateRoomOpen = true;
      this.activeName = "house";
      this.checkList = [],
      this.dateRange = [];
      var roomId = row.id;
      var communityId = row.communityId;
        getRoomDetail(roomId,communityId).then(response => {
          this.updateRoomForm = response.data;
          this.imageUrl = response.data.roomUrl;
        });
        roomFamilyList(roomId).then(response => {
          this.inhabitantList = response.data;
        });
      getProcessing(row.decorateId).then(res => { 
        if (res.data) { 
          this.dateRange = [res.data.planStart+'', res.data.planEnd+''];
          this.checkList = res.data.project.split(",");
          this.id = res.data.id;
          this.companyName = res.data.companyName;
          this.personName = res.data.personName;
          this.personPhone = res.data.personPhone;
          this.remarks = res.data.remarks;
          this.warrant = res.data.warrant;
          this.cardFont = res.data.cardFont;
          this.cardBack = res.data.cardBack;
          this.designScheme = res.data.designScheme;
          this.other = res.data.other;
          this.decorationContract = res.data.decorationContract;
        }
      })
    },
    //装修办理提交
    submit() { 
      this.$refs["updateRoomForm"].validate(valid => {
        if (this.dateRange) {
          this.updateRoomForm.planStart = this.dateRange[0];
          this.updateRoomForm.planEnd = this.dateRange[1];
        } else { 
          this.$modal.msgWarning("请选择装修时间");
          return;
        }
        console.log("this.checkList>>", this.checkList);
        if (this.checkList.length == 1) { 
          if (this.checkList.includes("")) { 
            this.$modal.msgWarning("请选择装修/安装项目");
            return;
          }
        }
        if (this.checkList.includes("9")) { 
          if (!this.remarks) { 
            this.$modal.msgWarning("请输入其他项目");
            return;
          }
        }
        var pro ="";
        this.checkList.forEach(project => {
          if (project) { 
            pro = pro + "," + project;
          }
        });
        if (pro) { 
          pro=pro.substring(1, pro.length);
        }
        var companyName=this.companyName;
        if (!companyName) { 
          this.$modal.msgWarning("请输入装修施工单位名称");
          return;
        }
        var personName=this.personName;
        if (!personName) { 
          this.$modal.msgWarning("请输入负责人姓名");
          return;
        }
        var personPhone=this.personPhone;
        if (!personPhone) { 
          this.$modal.msgWarning("请输入负责人手机号");
          return;
        }
        var flag = 0;
        this.updateRoomForm.project = pro;
        this.updateRoomForm.id=this.id;
        this.updateRoomForm.companyName=companyName;
        this.updateRoomForm.personName=personName;
        this.updateRoomForm.personPhone=personPhone;
        this.updateRoomForm.remarks = this.remarks;
        
        if (this.warrant) {
          this.updateRoomForm.warrant = this.warrant;
        } else { 
          flag = 1;
        
        }
        if (this.cardFont) {
          this.updateRoomForm.cardFont = this.cardFont;
        } else { 
          flag = 1;
         
        }
        if (this.cardBack) {
          this.updateRoomForm.cardBack=this.cardBack;
        } else { 
          flag = 1;
          
        }
        if (this.designScheme) {
          this.updateRoomForm.designScheme=this.designScheme;
        } else { 
          flag = 1;
        
        }
        if (this.other) {
          this.updateRoomForm.other=this.other;
        } else { 
          flag = 1;
         
        }
        if (this.decorationContract) {
          this.updateRoomForm.decorationContract = this.decorationContract;
        } else { 
          flag = 1;
          
        }
  
        if (valid) {
          if (this.id) {
            if (flag == 1) {
              this.$modal
                .confirm("要件信息还未填写是否提交装修办理")
                .then(() => {
                  updateProcessing(this.updateRoomForm);
                })
                .then(() => {
                  this.$modal.msgSuccess("修改成功");
                  this.updateRoomOpen = false;
                  this.cancelProcessingDetail()
                  this.getList();
                })
                .catch(() => { });
            } else { 
              updateProcessing(this.updateRoomForm).then(response => {
                this.$modal.msgSuccess("修改成功");
                this.updateRoomOpen = false;
                this.cancelProcessingDetail()
                this.getList();
              });
            }
            
            
            
          } else { 
            var content=this.content;
            if (!content) { 
              this.$modal.msgWarning("请输入审核备注");
              return;
            }
            var image=this.image;
            if (!image) { 
              this.$modal.msgWarning("请上传房屋图片");
              return;
            }
            this.updateRoomForm.content = this.content;
            this.updateRoomForm.image = this.image;
            if (flag == 1) {
              this.$modal
                .confirm("要件信息还未填写是否提交装修办理")
                .then(() => {
                  addProcessing(this.updateRoomForm);
                })
                .then(() => {
                  this.$modal.msgSuccess("办理成功");
                  this.cancelProcessingDetail()
                  this.getList();
                })
                .catch(() => { });
            } else { 
              addProcessing(this.updateRoomForm).then(response => {
                this.$modal.msgSuccess("办理成功");
                this.cancelProcessingDetail()
                this.getList();
              });
            }
            
          }
        }
      });
    },
    //取消
    cancelProcessingDetail() { 
      this.updateRoomForm = {};
      this.imageUrl = '';
      this.mainShow = true;
      this.updateRoomOpen = false;
      this.getList();
    },
    // 取消查看
    cancelDetail() {
      this.mainShow = true;
      this.detailShow = false;
      this.detailForm = {};
    },
    // 取消办理装修
    cancelProcessing() {
      this.mainShow = true;
      this.updateRoomOpen = false;
      this.detailForm = {};
      this.getList();
    },
  
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.roomStatus = undefined;
      this.queryParams.ownerName = undefined;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    resetOwnerQuery() {
      this.ownerQueryParams.communityId = this.selectData.communityId;
      this.ownerQueryParams.cardNo = undefined;
      this.ownerQueryParams.ownerPhone = undefined;
      this.ownerQueryParams.ownerName = undefined;
      this.resetForm("ownerQueryForm");
      this.handleQueryOwner();
    },

   
   
    listRoomGraphs() {
      getRoomGraphData(this.selectData).then(response => {
        this.roomGraphData = response.data;
      });
    },
    openUrl(url) {
      window.open(url)
    },
    // 树选中节点
    pickTree(data, node) {
     
      this.selectData = data;

      switch (data.level) {
        case 1:
          this.selectData.communityId = data.id;
          break;
        case 2:
          this.selectData.communityId = node.parent.data.id;
          break;
        case 3:
          this.selectData.communityId = node.parent.parent.data.id;
          break;
      }
      this.queryParams.id = data.id;
      this.ownerQueryParams.communityId = this.selectData.communityId;
      this.currentNodeKey = data.key;
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 10;
      this.queryParams.level = data.level;

      if (this.graphShow) {
        this.listRoomGraphs();
      } else {
        this.getList();
      }
    },

    // 重置
    reset() {
      this.insertBuildingBatchForm = {};
      this.insertUnitAndRoomBatchForm = {};
      this.insertRoomBatchForm = {};
      this.updateRoomBatchForm = {};
    },

  
   
    // 房间状态名称转换
    roomStatusStr(val) {
      for (const index in this.defaultRoomStatus) {
        if (this.defaultRoomStatus[index].id == val) {
          return this.defaultRoomStatus[index].name
        }
      }
      return '未知';
    },
    getRenovationAuditStatusOptions(val) { 
      if (val) {
        var obj = this.renovationAuditStatusOptions.filter(item => item.dictValue == val)[0];
        if (obj) {
          return obj.dictLabel;
        }
      } else { 
        return "未办理装修";
      }
    },
    // 与户主关系名称转换
    relationStr(val) {
      for (const index in this.relationList) {
        if (this.relationList[index].value == val) {
          return this.relationList[index].label
        }
      }
      return '未知';
    },
    // 房屋属性转换
    roomTypeStr(val) {
      for (const index in this.defaultRoomType) {
        if (this.defaultRoomType[index].dictValue == val) {
          return this.defaultRoomType[index].dictLabel
        }
      }
      return '未知';
    },
    // 房屋结构转换
    roomStructureStr(val) {
      for (const index in this.defaultRoomStructure) {
        if (this.defaultRoomStructure[index].id == val) {
          return this.defaultRoomStructure[index].name
        }
      }
      return '未知';
    }
  }
}
</script>

<style scoped>
.checkrow{
  margin-bottom: 12px
}
.el-checkbox__label{
  display: inline-block;
    padding-left: 10px;
    line-height: 19px;
    font-size: 15px;
}
aside {
  background: none;
  display: flex;
  justify-content: center;
  align-items: center;
}

.statisticsClass span {
  margin-left: 20px;
}

.colorStatisticsClass div {
  width: 18px;
  height: 18px;
  display: inline-block;
  margin-left: 6px;
}


.room-avatar-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  width: 178px;
  height: 178px;
}

.room-avatar-uploader .el-upload:hover {
  border-color: #409EFF;
}

.room-avatar-uploader-icon {
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
</style>

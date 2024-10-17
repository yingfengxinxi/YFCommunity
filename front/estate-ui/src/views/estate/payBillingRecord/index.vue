<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
     
    >
      <el-row>
        <el-col :span="8">
          <el-form-item label="所属小区" prop="communityId" label-width="70px">
            <el-select v-model="queryParams.communityId" placeholder="请选择" @change="getBuildingOptions(queryParams.communityId)" >
              <el-option
                v-for="(item, index) in currentList"
                  :key="index"
                              :label="item.communityName"
                              :value="item.communityId"
                            ></el-option>
                          </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="楼栋号" prop="buildingId" label-width="70px">
            <el-select v-model="queryParams.buildingId" placeholder="请选择" @change="getUnitOptions(queryParams.buildingId)">
              <el-option
                v-for="(item, index) in buildingOptions"
                  :key="index"
                              :label="item.buildingName"
                              :value="item.buildingId"
                            ></el-option>
                          </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="单元号" prop="unitId" label-width="70px">
            <el-select v-model="queryParams.unitId" placeholder="请选择" @change="getRoomOptions(queryParams.unitId)">
              <el-option
                v-for="(item, index) in unitOptions"
                  :key="index"
                              :label="item.unitName"
                              :value="item.unitId"
                            ></el-option>
                          </el-select>
          </el-form-item>
        </el-col>
        
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="房号" prop="roomId" label-width="70px">
            <el-select v-model="queryParams.roomId" placeholder="请选择">
              <el-option
                v-for="(item, index) in roomOptions"
                  :key="index"
                              :label="item.roomName"
                              :value="item.roomId"
                            ></el-option>
                          </el-select>
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="业主姓名" prop="ownerName" label-width="70px">
            <el-input
              v-model="queryParams.ownerName"
              placeholder="请输入业主姓名"
              clearable
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
        </el-col>
        <el-col :span="8">
          <el-form-item label="缴费类型" prop="payType" label-width="70px">
            <el-select v-model="queryParams.payType" placeholder="请选择">
              <el-option
                v-for="(item, index) in payTypeOptions"
                  :key="index"
                              :label="item.dictLabel"
                              :value="item.dictValue"
                            ></el-option>
                          </el-select>
          </el-form-item>
        </el-col>
      </el-row>
      <el-row>
        <el-col :span="8">
          <el-form-item label="缴费时间" prop="dateRange" label-width="70px">
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
        <el-col :span="8">
          <el-form-item>
            <el-button
              type="primary"
              icon="el-icon-search"
              size="mini"
              @click="handleQuery"
              >搜索</el-button
            >
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
              >重置</el-button
            >
          </el-form-item>
        </el-col>
      </el-row>
      
    </el-form>

    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
              type="primary"
              plain
              icon="el-icon-add"
              size="mini"
              @click="handleAdd"
            >新增
        </el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
                  type="info"
                  plain
                  icon="el-icon-upload2"
                  size="mini"
                  @click="handleImport1"
                  >垃圾清运费导入</el-button
                >
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
                  type="info"
                  plain
                  icon="el-icon-upload2"
                  size="mini"
                  @click="handleImport2"
                  >物业费导入</el-button
                >
      </el-col> -->
      
                
      <el-col :span="1.5">
                 <el-button
                   type="warning"
                   plain
                   icon="el-icon-download"
                   size="mini"
                   @click="handleExport"
                   
                 >导出</el-button>
               </el-col>
      <right-toolbar
        :showSearch.sync="showSearch"
        @queryTable="getList"
      ></right-toolbar>
    </el-row>

    <el-table
      v-loading="loading"
      :data="list"
      @selection-change="handleSelectionChange"
    >
     
      <el-table-column label="小区名称" align="center" prop="communityName"/>
      <el-table-column label="楼栋" align="center" prop="buildingName"/>
      <el-table-column label="单元号" align="center" prop="unitName"/>
      <el-table-column label="房号" align="center" prop="roomName"/>
      <el-table-column label="缴费渠道" align="center" prop="type">
        <template slot-scope="scope">
          {{ scope.row.type=='0'?'线下':'线上' }}
        </template>
      </el-table-column>
      <el-table-column label="业主姓名" align="center" prop="ownerName"/>
      <el-table-column label="房屋面积" align="center" prop="totalArea"/>
      <el-table-column label="缴费金额" align="center" prop="payAmount"/>
      <el-table-column label="优惠金额" align="center" prop="discountAmount"/>
      <el-table-column label="实缴金额" align="center" prop="paidAmount"/>
      <el-table-column label="缴费类型" align="center" prop="payTypeName"/>
      <el-table-column label="缴费周期" align="center" prop="activePicture">
        <template slot-scope="scope">
          <span v-if="scope.row.payType=='0' || scope.row.payType=='1'">
            <span  v-if="scope.row.startTime!=null">
              {{scope.row.startTime}}至{{scope.row.endTime}}
            </span>
            <span  v-else>
              永久
            </span>
          </span>
          <span v-else>
            --
          </span>
        </template>
      </el-table-column>
      <el-table-column label="缴费人" align="center" prop="payBy">
        <template slot-scope="scope">
          {{scope.row.isUnpaidPropertyFee=='1'?'物业支付':scope.row.payBy }}
        </template>
      </el-table-column>
      <el-table-column label="缴费时间" align="center" prop="payTime" />
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
            v-if="scope.row.type=='0'"
            @click="handleUpdate(scope.row)"
            >修改</el-button
          >
          <br v-if="scope.row.type=='0'">
          <el-button
            size="mini"
            type="text"
            icon="el-icon-delete"
            style="color: #f56c6c"
            v-if="scope.row.type!='0' && scope.row.payType!='0' && scope.row.payType!='1'"
            @click="handleDelete(scope.row)"
            >删除</el-button
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

  <!-- 添加或修改活动对话框 -->
  <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form
        ref="form"
        :model="form"
        :rules="rules"
        label-width="150px"
      >
        <el-form-item label="所属小区" prop="communityId">
          <el-select :disabled="isDisabled" v-model="form.communityId" placeholder="请选择所属小区" 
          @change="selectFloor(form.communityId,'0')">
            <el-option
              v-for="(item, index) in currentList"
                :key="index"
                            :label="item.communityName"
                            :value="item.communityId"
                          ></el-option>
                        </el-select>
          </el-form-item>
          <el-form-item label="缴费类型" prop="payType">
            <el-select v-model="form.payType" clearable
              :disabled="isDisabled"
              placeholder="请选择缴费类型" @change="getPayType(form.payType,form.communityId)">
              <el-option
                v-for="(item, index) in payTypeOptions"
                :key="index"
                :label="item.dictLabel"
                :value="item.dictValue"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item  label="支付方" prop="isUnpaidPropertyFee" v-if="form.payType=='0'">
            <el-radio-group  :disabled="isDisabled" v-model="form.isUnpaidPropertyFee" @change="getIsUnpaidPropertyFee(form.isUnpaidPropertyFee)">
                <el-radio v-model="form.isUnpaidPropertyFee" label="0">业主支付</el-radio>
                <el-radio v-model="form.isUnpaidPropertyFee" label="1">物业支付</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item  label="人员类型" prop="flag" v-if="form.payType=='1'">
            <el-radio-group   :disabled="isDisabled" v-model="form.flag" @change="getFlag(form.flag)">
                <el-radio v-model="form.flag" label="0">物业缴费</el-radio>
                <el-radio v-model="form.flag" label="1">业主缴费</el-radio>
            </el-radio-group>
          </el-form-item>
        
              <el-form-item label="所属楼栋: " prop="buildingId" v-if="form.flag=='1'">
                
                  <el-select
                  :disabled="isDisabled"
                    v-model="form.buildingId"
                    clearable
                    placeholder="请选择所属楼栋"
                    @change="buildingChange(form.buildingId,'0')"
                  >
                    <el-option
                      v-for="item in buildingData"
                      :key="item.buildingId"
                      :label="item.buildingName"
                      :value="item.buildingId"
                    >
                    </el-option>
                  </el-select>
                
              </el-form-item>
              <el-form-item label="所属单元: " prop="unitId" v-if="form.flag=='1'">
                
                  <el-select
                  :disabled="isDisabled"
                    v-model="form.unitId"
                    clearable
                    placeholder="请选择所属单元"
                    @change="unitChange(form.unitId,'0')"
                  >
                    <el-option
                      v-for="item in unitData"
                      :key="item.unitId"
                      :label="item.unitName"
                      :value="item.unitId"
                    >
                    </el-option>
                  </el-select>
                
              </el-form-item>
              <el-form-item label="所属房号: " prop="roomId" v-if="form.flag=='1'">
               
                  <el-select
                  :disabled="isDisabled"
                    v-model="form.roomId"
                    clearable
                    placeholder="请选择所属房号"
                    @change="roomChange(form.roomId,'0')"
                  >
                    <el-option
                      v-for="item in roomData"
                      :key="item.roomId"
                      :label="item.roomName"
                      :value="item.roomId"
                    >
                    </el-option>
                  </el-select>
                
              </el-form-item>
              <div v-if="form.payType=='0'">
                <!--物业缴费使用 -->
                <el-form-item label="业户姓名: " prop="ownerId" v-if="form.isUnpaidPropertyFee=='0'">
                
                    <el-select
                    :disabled="isDisabled"
                      v-model="form.ownerId"
                      clearable
                      placeholder="请选择业户"
                    @change="getOwner(form.ownerId,'0')"
                    >
                      <el-option
                        v-for="item in ownerData"
                        :key="item.ownerId"
                        :label="item.ownerName"
                        :value="item.ownerId"
                      >
                      </el-option>
                    </el-select>
                  
                </el-form-item>
              </div>
              <div v-else>
                <el-form-item label="业户姓名: " prop="ownerId" v-if="form.flag=='1'">
                
                    <el-select
                    :disabled="isDisabled"
                      v-model="form.ownerId"
                      clearable
                      placeholder="请选择业户"
                    @change="getOwner(form.ownerId,'0')"
                    >
                      <el-option
                        v-for="item in ownerData"
                        :key="item.ownerId"
                        :label="item.ownerName"
                        :value="item.ownerId"
                      >
                      </el-option>
                    </el-select>
                  
                </el-form-item>
              </div>
              <el-form-item label="物业人员姓名: " prop="payBy" v-if="form.flag=='0'">
                
                <el-select
                :disabled="isDisabled"
                  v-model="form.payBy"
                  clearable
                  placeholder="请选择物业人员"
                  @change="getPayBy(form.payBy,'0')"
                >
                  <el-option
                    v-for="item in executorByOptions"
                    :key="item.userId"
                    :label="item.staffName"
                    :value="item.userId"
                  >
                  </el-option>
                </el-select>
              
            </el-form-item>
            <div  v-if="form.payType=='1'">
              <el-form-item label="车位编号" prop="vehicleNo">
                  <el-select
                      :disabled="isDisabled"
                      v-model="form.vehicleNo"
                      clearable
                      placeholder="请选择车位编号"
                      @change="getVehicleNo(form.communityId,form.vehicleNo,form.flag)"
                    >
                      <el-option
                        v-for="item in vehicleNoOptions"
                        :key="item.carportNoId"
                        :label="item.carportNo"
                        :value="item.carportNoId"
                        :disabled="item.isCarport>=1"
                      >
                      </el-option>
                    </el-select>
                </el-form-item>
                <el-form-item label="车牌号" prop="plateNo" v-if="form.flag=='1'">
                  <el-select
                    :disabled="isVehicleNoDisabled"
                      v-model="form.plateNo"
                      clearable
                      placeholder="请选择车牌号"
                    >
                      <el-option
                        v-for="item in plateNoOptions"
                        :key="item.vehicleId"
                        :label="item.plateNo"
                        :value="item.vehicleId"
                      >
                      </el-option>
                    </el-select>
                </el-form-item>
              
                <el-form-item label="租/出售" prop="leasePurchase" v-if="form.payType=='1'">
                  <el-select
                    :disabled="isDisabled"
                      v-model="form.leasePurchase"
                      clearable
                      placeholder="请选择租/出售"
                      @change="getPurchasePackage(form.leasePurchase,'0')"
                    >
                      <el-option
                        v-for="item in leasePurchaseOptions"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                      >
                      </el-option>
                    </el-select>
          </el-form-item>  
          <el-form-item label="套餐" prop="purchasePackage" v-if="form.payType!='0' && form.leasePurchase!='1' ">
                  <el-select
                    :disabled="isDisabled"
                      v-model="form.purchasePackage"
                      clearable
                      placeholder="请选择套餐"
                      @change="getFee(form.purchasePackage)"

                    >
                      <el-option
                        v-for="item in purchasePackageOptions"
                        :key="item.id"
                        :label="item.name"
                        :value="item.id"
                      >
                      </el-option>
                    </el-select>
              </el-form-item>  
              <el-form-item  label="租赁天数" prop="yearMonth" v-if="form.leasePurchase=='0'">
                <el-input-number :disabled="isDisabled" placeholder="请输入租赁天数" v-model="form.yearMonth" :min="0" :max="999999" style="width:217px"  @input="getYearMonth(form.yearMonth,form.discountAmount)"></el-input-number>
              </el-form-item>
        </div>
            
      <el-form-item label="缴费金额" prop="payAmount">
        <el-input-number :disabled="form.payType=='1'" placeholder="请输入缴费金额" v-model="form.payAmount" :min="0" :max="999999" :precision="2" :step="0.1"  style="width:217px" @input="getPaidAmount(form.payAmount,form.discountAmount)"></el-input-number>
      </el-form-item>
      <el-form-item label="优惠金额" prop="discountAmount">
        <el-input-number placeholder="请输入优惠金额" v-model="form.discountAmount" :min="0" :max="999999" :precision="2" :step="0.1"  style="width:217px" @input="getPaidAmount(form.payAmount,form.discountAmount)"></el-input-number>
      </el-form-item>
      <el-form-item label="实缴金额" prop="paidAmount">
        <el-input-number disabled placeholder="请输入实缴金额" v-model="form.paidAmount" :min="0" :max="999999" :precision="2" :step="0.1"  style="width:217px"></el-input-number>
      </el-form-item>
      <div v-if="form.payType!='2' && form.payType!='3'">
        <!-- 物业费使用 -->
        <el-form-item label="缴费周期" v-if="form.payType != null && form.payType!='1'">
          <el-date-picker
            v-model="dateRange1"
            :disabled="disDateRange"
            size="small"
            style="width: 220px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
          ></el-date-picker>
        </el-form-item>
        <!-- 车位费使用 -->
        <el-form-item label="缴费周期" v-if="(form.payType!='0' && form.leasePurchase!='1') ">
          <el-date-picker
            v-model="dateRange1"
            size="small"
            style="width: 220px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始时间"
            end-placeholder="结束时间"
          ></el-date-picker>
        </el-form-item>
      </div>
      
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>

    </el-dialog>


    <!-- 垃圾清运费 -->
    <el-dialog
          :title="uploadImprot.title"
          :visible.sync="uploadImprot.open"
          width="400px"
          append-to-body
        >
          <el-upload
            ref="uploadImprot"
            :limit="1"
            accept=".xlsx, .xls"
            :headers="uploadImprot.headers"
            :action="uploadImprot.url"
            :disabled="uploadImprot.isUploading"
            :on-progress="handleFileUploadProgress1"
            :on-success="handleFileSuccess1"
            :auto-upload="false"
            drag
          >
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip text-center" slot="tip">
              <!-- <div class="el-upload__tip" slot="tip">
                <el-checkbox v-model="upload.updateSupport" />
                是否更新已经存在的用户数据
              </div> -->
              <span>仅允许导入xls、xlsx格式文件。</span>
              <el-link
                type="primary"
                :underline="false"
                style="font-size: 12px; vertical-align: baseline"
                @click="importTemplate1"
                >下载模板</el-link
              >
            </div>
          </el-upload>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitFileForm1">确 定</el-button>
            <el-button @click="uploadImprot.open = false">取 消</el-button>
          </div>
        </el-dialog>


        <!-- 物业费 -->
    <el-dialog
          :title="uploadImprot2.title"
          :visible.sync="uploadImprot2.open"
          width="400px"
          append-to-body
        >
          <el-upload
            ref="uploadImprot2"
            :limit="1"
            accept=".xlsx, .xls"
            :headers="uploadImprot2.headers"
            :action="uploadImprot2.url"
            :disabled="uploadImprot2.isUploading"
            :on-progress="handleFileUploadProgress2"
            :on-success="handleFileSuccess2"
            :auto-upload="false"
            drag
          >
            <i class="el-icon-upload"></i>
            <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
            <div class="el-upload__tip text-center" slot="tip">
              <!-- <div class="el-upload__tip" slot="tip">
                <el-checkbox v-model="upload.updateSupport" />
                是否更新已经存在的用户数据
              </div> -->
              <span>仅允许导入xls、xlsx格式文件。</span>
              <el-link
                type="primary"
                :underline="false"
                style="font-size: 12px; vertical-align: baseline"
                @click="importTemplate2"
                >下载模板</el-link
              >
            </div>
          </el-upload>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitFileForm2">确 定</el-button>
            <el-button @click="uploadImprot2.open = false">取 消</el-button>
          </div>
        </el-dialog>
  </div>
</template>

<script>
import {
  getListPage,
  deleteById,
  update,
  insert,
  getById,
  getLeasePurchaseList,
  getPurchasePackageList,
  getFee
} from "@/api/estate/payBillingRecord/payBillingRecord";
import {getToken} from "@/utils/auth";
import { getCommunityIdStaffList } from "@/api/estate/manage/staff/staff";
import { getUserIdVehicleNoList } from "@/api/estate/manage/staffProperty/staffProperty";
import { getByRoomIdAmount } from "@/api/estate/financial/estateCash";
import { getCommunityCarportList,getOwnerIdCarportList, getCarportNoCarportNature } from "@/api/estate/center/buildingManage/carPositionManage/carPositionManage";
import { getByOwnerIdVehicleList } from "@/api/estate/center/buildingManage/vehicleManage/vehicleManage";
import { getCommunityIdParkDurationCount } from "@/api/estate/financial/parkDuration";
import { getByBuildingIdPropertyFeeCount } from "@/api/estate/financial/PropertyFee";
import {
  getCurrentList,
  getBuildingList,
  getUnitList,
  getRoomList,
  getOwnerList
} from "@/api/estate/community";
export default {
  name: "PayBillingRecord",
  data() {
    return {
      payTypeOptions:[],
      dateRange: [],
      isDisabled:false,
      dateRange1: [],
      buildingOptions:[],
      currentList: [],
      unitOptions: [],
      roomOptions: [],
      vehicleNoOptions: [],
      plateNoOptions: [],
      isVehicleNoDisabled:false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      ownerData:[],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      roomData: [],
      unitData:[],
      buildingData: [],
      // 垃圾清运费导入参数
      uploadImprot: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/estate/payBillingRecord/importData",
      },

       // 物业费导入参数
       uploadImprot2: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/estate/payBillingRecord/importPropertyFeeData",
      },
      // 学校表格数据
      list: [],
      // 弹出层标题
      title: "",
      disDateRange:false,
      // 是否显示弹出层
      open: false,
      studentLoading: true,
      executorByOptions: [],
      leasePurchaseOptions: [],
      purchasePackageOptions:[],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        buildingId: undefined,
        unitId: undefined,
        roomId: undefined,
        ownerName: undefined,
        payType: undefined,
        dateRange:[],
      },
      ysMoney:0.00,
      // 表单参数
      form: {
        id:undefined,
        communityId:undefined,
        buildingId:undefined,
        unitId:undefined,
        roomId:undefined,
        ownerId:undefined,
        payType:undefined,
        payAmount: undefined,
        discountAmount: undefined,
        startTime: undefined,
        endTime:undefined,
        flag: '1',
        isUnpaidPropertyFee:'0',
        yearMonth:1
      },
      // 表单校验
      rules: {
        communityId: [
          { required: true, message: "请选择所属小区", trigger: "blur" }
        ],
        buildingId: [
          { required: true, message: "请选择所属楼栋", trigger: "blur" }
        ],
        unitId: [
          { required: true, message: "请选择所属单元", trigger: "blur" }
        ],
        roomId: [
          { required: true, message: "请选择所属房号", trigger: "blur" }
        ],
        ownerId: [
          { required: true, message: "请选择业主", trigger: "blur" }
        ],
        payBy: [
          { required: true, message: "请选择物业人员", trigger: "blur" }
        ],
        payType: [
          { required: true, message: "请选择缴费类型", trigger: "blur" }
        ],
        flag: [
          { required: true, message: "请选择人员类型", trigger: "blur" }
        ],
        // leasePurchase: [
        //   { required: true, message: "请选择租/售", trigger: "blur" }
        // ],
        // purchasePackage: [
        //   { required: true, message: "请选择套餐", trigger: "blur" }
        // ],
        vehicleNo: [
          { required: true, message: "请选择车位编号", trigger: "blur" }
        ],
        yearMonth: [
          { required: true, message: "请输入租赁天数", trigger: "blur" }
        ],
        payAmount: [
          { required: true, message: "请输入缴费金额", trigger: "blur" }
        ]
      },
    };
  },
  created() {
    this.getDicts("pay_type").then((response) => {
      this.payTypeOptions = response.data;
    });
    //所属小区
    getCurrentList().then(res => { 
      this.currentList = res.data;
    })
    this.getList();
  },

  methods: {
    getYearMonth(yearMonth,discountAmount) { 
      
      if (!discountAmount) { 
        discountAmount = parseFloat(0.00);
      }
      this.form.paidAmount = (parseInt(yearMonth) * this.ysMoney) - discountAmount;
      this.form.payAmount= (parseInt(yearMonth) * this.ysMoney)

       //包年计算周期
       const now = new Date();
      const dYear = now.getFullYear();  // 获取年份
      const dMonth = String(now.getMonth() + 1).padStart(2, '0');  // 获取月份，月份从0开始所以要+1
      const dDate = String(now.getDate()).padStart(2, '0');  // 获取日期
      var startTime = dYear + "-" + dMonth + "-" + dDate;
      if (this.form.purchasePackage=='0') { 
        var year=parseInt(yearMonth) * 12;
        this.dateRange1=[startTime,this.getDate(year)]
      }
      //包月计算周期
      if (this.form.purchasePackage=='1') { 
        
        this.dateRange1=[startTime,this.getDate(yearMonth)]
      }

    },
    getPaidAmount(payAmount, discountAmount) { 
      console.log("pay>>>",payAmount, discountAmount);
      if (!payAmount) { 
        payAmount = parseFloat(0.00);
      }
      if (!discountAmount) { 
        discountAmount = parseFloat(0.00);
      }
      if (payAmount > 0) { 
        if (discountAmount > payAmount) {
          discountAmount = undefined;
          this.$modal.msgWarning("请输入正确的优惠金额");
        } else { 
          this.form.paidAmount=payAmount - discountAmount;
        }
      }
      this.form.discountAmount = discountAmount;
      this.$forceUpdate();
    },
   //小区下所有楼栋
    getBuildingOptions(communityId) { 
      getBuildingList(communityId).then(res => { 
        this.buildingOptions = res.data;

        this.queryParams.buildingId = undefined;
        this.queryParams.unitId = undefined;
        this.queryParams.roomId = undefined;
        })
    },
   /** 导入按钮操作 */
   handleImport1() {
     
     this.uploadImprot.title = "垃圾清运费导入";
     this.uploadImprot.open = true;
   },
   /** 下载模板操作 */
   importTemplate1() {
     this.download(
       "estate/payBillingRecord/importTemplate",
       {},
       `垃圾清运费导入模板_${new Date().getTime()}.xlsx`
     );
    },
   // 文件上传中处理
   handleFileUploadProgress1(event, file, fileList) {
     this.uploadImprot.isUploading = true;
   },
   // 文件上传成功处理
   handleFileSuccess1(response, file, fileList) {
     this.uploadImprot.open = false;
     this.uploadImprot.isUploading = false;
     this.$refs.uploadImprot.clearFiles();
     this.$alert(
       "<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" +
         response.msg +
         "</div>",
       "导入结果",
       { dangerouslyUseHTMLString: true }
     );
     this.getList();
   },
   // 提交上传文件
   submitFileForm1() {
     this.$refs.uploadImprot.submit();
   },


   /** 导入按钮操作 */
   handleImport2() {
     
     this.uploadImprot2.title = "物业费导入";
     this.uploadImprot2.open = true;
   },
   /** 下载模板操作 */
   importTemplate2() {
     this.download(
       "estate/payBillingRecord/importPropertyFeeTemplate",
       {},
       `物业费导入模板_${new Date().getTime()}.xlsx`
     );
    },
   // 文件上传中处理
   handleFileUploadProgress2(event, file, fileList) {
     this.uploadImprot2.isUploading = true;
   },
   // 文件上传成功处理
   handleFileSuccess2(response, file, fileList) {
     this.uploadImprot2.open = false;
     this.uploadImprot2.isUploading = false;
     this.$refs.uploadImprot2.clearFiles();
     this.$alert(
       "<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" +
         response.msg +
         "</div>",
       "导入结果",
       { dangerouslyUseHTMLString: true }
     );
     this.getList();
   },
   // 提交上传文件
   submitFileForm2() {
     this.$refs.uploadImprot2.submit();
   },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.isDisabled = false;
      this.open = true;
      this.title = "添加线下缴费记录";
    },
    //单元楼
    getUnitOptions(buildingId) { 
      getUnitList(buildingId).then(res => { 
        this.unitOptions = res.data;

      this.queryParams.unitId = undefined;
      this.queryParams.roomId = undefined;
      })
    },
    //房间号
    getRoomOptions(unitId) { 
      getRoomList(unitId).then(res => { 
        this.roomOptions = res.data;
      })
    },
    getVehicleNo(communityId,vehicleNo,flag) {
      //
      if (flag == '1') { 
        //业主
        //查询当前车位的类型
        getCarportNoCarportNature(vehicleNo).then(res => {
        
          if (res.data) { 
            var carportNature = res.data.carportNature;
            getLeasePurchaseList(communityId,carportNature).then(res1 => { 
              this.leasePurchaseOptions = res1.data;
            })
          
          
          this.form.plateNo = res.data.vehicleId;
        }
          this.isVehicleNoDisabled = false;
          if(this.form.plateNo){
            this.isVehicleNoDisabled = true;
          }
        })
      } 
      this.$forceUpdate();
    },
    getOwner(ownerId,flag) { 
      if (ownerId) { 
        if (flag == '0') { 
          this.form.plateNo=undefined
        }
        getOwnerIdCarportList(ownerId, this.form.communityId).then(res => {
          if (res.data == null ||res.data.length==0) {
            getCommunityCarportList(this.form.communityId).then(re => { 
              console.log("AAAA")
              this.vehicleNoOptions = re.data;
            })
          } else { 
            console.log("BBBB")
            this.vehicleNoOptions = res.data;
          }
          this.$forceUpdate();
          
        });
        //查询车辆信息
        getByOwnerIdVehicleList(this.form.communityId, ownerId).then(res => { 
          this.plateNoOptions = res.data;
        })
      }
      
      this.$forceUpdate();
    },
    getPayBy(payBy,flag) {
      if (flag == '0') { 
        this.form.vehicleNo = undefined;
      }
      getUserIdVehicleNoList(payBy).then(res => {
        this.vehicleNoOptions = res.data;
      }); 
      this.$forceUpdate();
    },
    getFlag() { 
      this.form.buildingId = undefined;
      this.form.unitId = undefined;
      this.form.roomId = undefined;
      this.form.ownerId = undefined;
      this.form.payBy = undefined;
      this.form.vehicleNo = undefined;
    },
    getIsUnpaidPropertyFee(isUnpaidPropertyFee) { 
      if (this.form.roomId) {
        this.disDateRange = false;
        console.log("disDateRange1>>");
        getByRoomIdAmount(this.form.roomId, isUnpaidPropertyFee).then(res => {
          if (res.data) {
            this.form.payAmount = res.data.yjAmount;
            this.form.discountAmount = res.data.discountAmount;
            this.getPaidAmount(this.form.payAmount, this.form.discountAmount);
            this.dateRange1 = [res.data.startTime, res.data.endTime];
            if (this.dateRange1) { 
              this.disDateRange = true;
            }
            console.log("this.dateRange3>>", this.dateRange1);
          } else { 
            if (this.isDisabled) {
              this.disDateRange=true;
            }else{
              this.form.payAmount = undefined;
              this.form.discountAmount = undefined;
              this.form.paidAmount = undefined;
              this.dateRange1 = [];
              this.disDateRange=false;
            }
            
          }
        })
      } else { 
        this.form.payAmount = undefined;
        this.form.discountAmount = undefined;
        this.form.paidAmount = undefined;
        this.dateRange1 = [];
        this.disDateRange=false;
      }
      
      this.$forceUpdate();
    },
    /** 列表 */
    getList() {
      if (this.dateRange) { 
        this.queryParams.payStatTime = this.dateRange[0];
        this.queryParams.payEndTime=this.dateRange[1];
      }
      
      this.loading = true;
      getListPage(this.queryParams).then((response) => {
        this.list = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
     // 根据小区ID查楼栋
    selectFloor(communityId, flag) {
      if (flag=='0') { 
        this.form.buildingId = undefined;
        this.form.unitId = undefined;
        this.form.roomId = undefined;
        this.form.ownerId = undefined;
        this.form.payBy = undefined;
        this.form.leasePurchase = undefined;
        this.form.purchasePackage = undefined;
        this.form.yearMonth = "1";
      }
      if (communityId) { 
        getBuildingList(communityId).then((response) => {
          this.buildingData = response.data;
        });
        var workPostList = [];
        getCommunityIdStaffList(communityId,workPostList).then(res => {
            this.executorByOptions = res.data;
        });
        
      }
      
    },
    getPurchasePackage(leasePurchase, flag) { 
      if (flag == "0") { 
        this.form.purchasePackage = undefined;
        this.form.yearMonth = "1";
      }
      if (leasePurchase=='0') { 
        getPurchasePackageList(this.form.communityId, leasePurchase).then(res => { 
          this.purchasePackageOptions = res.data;
        })
      }
      if (leasePurchase == '1') { 
        this.getFee("");
      }
     
      this.$forceUpdate();
      
    },
    getFee(purchasePackage) { 
      console.log(purchasePackage)
      console.log("getFee>>1")
      getFee(this.form.communityId, this.form.leasePurchase, purchasePackage).then(res => { 
        this.form.payAmount = res.data[0].hourFee;
        this.form.paidAmount = res.data[0].hourFee;
        this.ysMoney = res.data[0].hourFee;
      })
      console.log("getFee>>2");
      this.dateRange1 = [];
      //包年计算周期
      const now = new Date();
      const dYear = now.getFullYear();  // 获取年份
      const dMonth = String(now.getMonth() + 1).padStart(2, '0');  // 获取月份，月份从0开始所以要+1
      const dDate = String(now.getDate()).padStart(2, '0');  // 获取日期
      var startTime = dYear + "-" + dMonth + "-" + dDate;
      if (purchasePackage=='0') { 
        var year=parseInt(this.form.yearMonth) * 12;
        this.dateRange1=[startTime,this.getDate(year)]
      }
      //包月计算周期
      if (purchasePackage=='1') { 
        
        this.dateRange1=[startTime,this.getDate(this.form.yearMonth)]
      }
    },
    getDate(inputMonths){
				// 获取当前日期对象
				let currentDate = new Date();
				 
				// 获取输入的月数（假设为3）
				//let inputMonths = 12;
				 
				// 计算新的日期
				currentDate.setMonth(currentDate.getMonth() + inputMonths);
				 
				// 将结果转换成指定格式的字符串
				let formattedDate = `${currentDate.getFullYear()}-${currentDate.getMonth()+1}-${currentDate.getDate()}`;
      console.log("增加后的日期：", formattedDate);
      return formattedDate;
		},
    // 根据楼栋查单元
    buildingChange(buildingId, flag) {
      if (flag == '0') { 
          this.form.unitId = undefined;
          this.form.roomId = undefined;
          this.form.ownerId = undefined;
      }
      if (buildingId) {
        this.form.buildingId = buildingId;
        getUnitList(buildingId).then((response) => {
          this.unitData = response.data;
        });
        if (this.form.payType == '0') { 
          if (this.isDisabled) { 
            return;
          }
          //查询物业费缴费规则是否配置
          getByBuildingIdPropertyFeeCount(buildingId, this.form.communityId).then(res => {
            if (res.data <= 0) { 
              this.$modal.msgError("当前楼栋未配置物业费缴费规则，请配置规则后进行线下缴费");
              this.form.buildingId = undefined;
              this.form.payAmount = undefined;
              this.form.discountAmount = undefined;
              this.form.paidAmount = undefined;
              this.dateRange1 = [];
              this.disDateRange=false;
              return;
            }
          })
        }
        
      
      }
    },

    // 根据单元查房号
    unitChange(unitId, flag) {
      if (flag == '0') { 
          this.form.roomId = undefined;
          this.form.ownerId = undefined;
      }
      if (unitId) {
        this.form.unitId = unitId;
        getRoomList(unitId).then((response) => {
          this.roomData = response.data;
        });
      }
    },
    getPayType(payType,communityId) { 
      if (!communityId) { 
        this.$modal.msgError("请选择所属小区!");
        return;
      }
      if (payType == '0') {
        this.form.isUnpaidPropertyFee = '0';
        if (this.form.roomId) { 
          this.disDateRange=false;
          console.log("disDateRange2>>");
          getByRoomIdAmount(this.form.roomId,this.form.isUnpaidPropertyFee).then(res => {
            if (res.data) {
                this.form.payAmount = res.data.yjAmount;
                this.form.discountAmount = res.data.discountAmount;
                this.getPaidAmount(form.payAmount, form.discountAmount);
                
              this.dateRange1 = [res.data.startTime, res.data.endTime];
              if (this.dateRange1) { 
                this.disDateRange=true;
              }
              console.log("this.dateRange1>>", this.dateRange1);
            } else {
              if (this.isDisabled) {
                this.disDateRange=true;
              } else {
                this.form.payAmount = undefined;
                this.form.discountAmount = undefined;
                this.form.paidAmount = undefined;
                this.dateRange1 = [];
                this.disDateRange=false;
              }
              
            }
          })
        }
        
      } else { 
        this.form.isUnpaidPropertyFee = undefined;
          if (payType == '1') { 
            //查询车位费规则是否配置
            getCommunityIdParkDurationCount(communityId).then(res => {
              if (res.data <= 0) {
                this.form.payType = undefined;
                this.$modal.msgError("请先配置车位缴费规则后进行线下缴费!");
                return;
              }
            })
          }
          this.form.payAmount = undefined;
          this.dateRange1 = undefined;
          this.form.discountAmount = undefined;
          this.form.paidAmount = undefined;
      }
    },
    // 根据房号查业户姓名
    roomChange(roomId, flag) {
      if (flag == '0') { 
        this.form.ownerId = undefined;
      }
      if (roomId) {
        // 给addForm赋值
        this.form.roomId = roomId;
        let params = {
          roomId: roomId,
        };
        getOwnerList(params).then((response) => {
          this.ownerData = response.data;
        });
        if (this.form.payType=='0') { 
          this.disDateRange = false;
          console.log("disDateRange3>>");
          getByRoomIdAmount(roomId,this.form.isUnpaidPropertyFee).then(res => { 
            if (res.data) { 
              
                this.form.payAmount = res.data.yjAmount;
                this.form.discountAmount = res.data.discountAmount;
                this.getPaidAmount(this.form.payAmount, this.form.discountAmount);
              
              
              this.dateRange1 =[res.data.startTime,res.data.endTime];
              if (this.dateRange1) { 
                this.disDateRange = true;
              }
              console.log("this.dateRange2>>", this.dateRange1);
            } else {
              if (this.isDisabled) {
               
                this.disDateRange = true;
              } else { 
                this.form.payAmount = undefined;
                this.dateRange1 = undefined;
                this.form.discountAmount = undefined;
                this.form.paidAmount = undefined;
                this.disDateRange = false;
              }
              
            }
          })
        } else { 
          if (flag == '0') { 
            this.form.payAmount = undefined;
            this.dateRange1 = undefined;
            this.form.discountAmount = undefined;
            this.form.paidAmount = undefined;
          }
         
        }
        
      }
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      //this.reset();
      this.isDisabled = true;
      const id = row.id || this.ids;
      getById(id).then((response) => {
        this.form = response.data;
        
        this.form.payBy = parseFloat(this.form.payBy);
        this.selectFloor(this.form.communityId, '1');
        this.buildingChange(this.form.buildingId, '1');
        this.unitChange(this.form.unitId, '1');
        this.roomChange(this.form.roomId, '1');
        if (this.form.payType == '1') {
          if (this.form.flag == '1') {
            this.getOwner(this.form.ownerId,'1');
          } else { 
            this.getPayBy(this.form.payBy, '1');
          }
          
        }
        
        
        this.getVehicleNo(this.form.communityId,this.form.vehicleNo,this.form.flag);
        this.form.vehicleNo = parseFloat(this.form.vehicleNo);
        this.open = true;
        this.title = "修改线下缴费记录";
        if (this.form.payType == '1') {
          if (this.form.leasePurchase == '0') { 
            this.getPurchasePackage(this.form.leasePurchase);
          } 
        }
        
        this.form.payAmount = response.data.payAmount;
        this.form.discountAmount = response.data.discountAmount;
        this.form.paidAmount = response.data.paidAmount;
        //this.getPaidAmount(this.form.payAmount, this.form.discountAmount);
        if (response.data.startTime) { 
          this.dateRange1 =[response.data.startTime,response.data.endTime];
        }
        this.$forceUpdate();
      });
    },
    /** 提交按钮 */
    submitForm() {
      
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.payType == '1') {
            if (this.form.leasePurchase == '0') {
              if (this.dateRange1==null || this.dateRange1.length==0) {
                this.$modal.msgError("请选择缴费周期");
                return;
              }
            }
          } else { 
            if (this.form.payType != '2' && this.form.payType != '3') {
              console.log("this.dateRange1>>", this.dateRange1);
              if (this.dateRange1==null || this.dateRange1.length==0) {
                this.$modal.msgError("请选择缴费周期");
                return;
              }
            }
            
          }
          if (this.dateRange1!=null) { 
            this.form.startTime = this.dateRange1[0];
            this.form.endTime=this.dateRange1[1];
          }
          console.log("AAA>", this.form.discountAmount);
          this.getPaidAmount(this.form.payAmount, this.form.discountAmount);
          console.log("BBB>", this.form.discountAmount);
          if (this.form.discountAmount==null) { 
            return;
          }
          if (this.form.id) {
            update(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            insert(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm('是否确认删除当前线下缴费记录吗？')
        .then(function () {
          return deleteById(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        // id: undefined,
         communityId: undefined,
         buildingId: undefined,
         unitId: undefined,
         roomId: undefined,
        // ownerId: undefined,
        // payType: undefined,
        // payAmount: undefined,
        // discountAmount: undefined,
        // startTime: undefined,
        // endTime: undefined,
        flag: "1",
        yearMonth: "1",
        // leasePurchase: undefined,
        // payBy: undefined,
        // paidAmount: undefined,
        // purchasePackage:undefined
      };
      this.dateRange1 = [];
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        buildingId: undefined,
        unitId: undefined,
        roomId: undefined,
        ownerName: undefined,
        payType: undefined,
      
      }
      this.dateRange=[],
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
   
    /** 导出按钮操作 */
    handleExport() {
      if (this.dateRange) { 
        this.queryParams.payStatTime = this.dateRange[0];
        this.queryParams.payEndTime=this.dateRange[1];
      }
      this.download(
        "/estate/payBillingRecord/export",
        {
          ...this.queryParams,
        },
        `缴费账单记录_${new Date().getTime()}.xlsx`
      );
    },
  },
};
</script>
<style lang="scss">
.el-input-group__append,
.el-input-group__prepend {
  background-color: #1890ff !important;
  color: #fff !important;
  border-color: #1890ff !important;
  border-radius: 0 4px 4px 0;
}
.cascaderW {
  width: 350px;
}

</style>
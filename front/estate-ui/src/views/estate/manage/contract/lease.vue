<template>
  <div class="app-container">
    <!--综合页面信息-->
    <div v-show=checkView>
      <!--模糊查询-->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
        <el-form-item label="所属小区" prop="communityId">
          <el-select v-model="queryParams.communityId" placeholder="请选择小区" style="width: 100%">
            <el-option
              v-for="item in communityList"
              :key="item.communityId"
              :label="item.communityName"
              :value="item.communityId"
              @keyup.enter.native="handleQuery"
            >
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="合同时间">
          <el-date-picker
            v-model="dateRange"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            @keyup.enter.native="handleQuery"
          ></el-date-picker>
        </el-form-item>
        <el-form-item label="租客姓名" prop="tenantName">
          <el-input
            v-model="queryParams.tenantName"
            placeholder="请输入租客姓名"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="手机号码" prop="tenantPhone">
          <el-input
            maxlength="64"
            v-model="queryParams.tenantPhone"
            placeholder="请输入手机号码"
            clearable
            style="width: 240px"
            size="small"
            @keyup.enter.native="handleQuery"
          />
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
            v-hasPermi="['manage:lease:add']"
          >新增
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['manage:lease:remove']"
          >删除
          </el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
      <!--表单-->
      <el-table v-loading="loading" :data="leaseList" row-key="contractId" @selection-change="handleSelectionChange"
                stripe>
        <el-table-column type="selection" width="50" align="center"/>
        <el-table-column label="合同编号" align="center" key="contractNo" prop="contractNo"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="租户姓名" align="center" key="tenantName" prop="tenantName"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="租户手机号" align="center" key="tenantPhone" prop="tenantPhone"
                         :show-overflow-tooltip="true">
          <template scope="scope">
            <span>{{ scope.row.tenantPhone }}</span>
          </template>
        </el-table-column>
        <el-table-column label="业主姓名" align="center" :show-overflow-tooltip="true">
          <template scope="scope">
            <span>{{ scope.row.ownerName === null ? '-' : scope.row.ownerName }}</span>
          </template>
        </el-table-column>
        <el-table-column label="业主手机号" align="center" :show-overflow-tooltip="true">
          <template scope="scope">
            <span>{{ scope.row.ownerPhone === null ? '-' : scope.row.ownerPhone }}</span>
          </template>
        </el-table-column>
        <el-table-column label="所属小区" align="center" key="communityName" prop="communityName"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="所属楼栋" align="center" key="buildingName" prop="buildingName"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="所属单元" align="center" key="unitName" prop="unitName" :show-overflow-tooltip="true"/>
        <el-table-column label="所属房间" align="center" key="roomName" prop="roomName" :show-overflow-tooltip="true"/>
        <el-table-column label="租金（元/月）" align="center" key="monthRent" prop="monthRent"
                         :show-overflow-tooltip="true"/>
        <el-table-column label="合同生效时间" align="contractStart" prop="contractStart" :show-overflow-tooltip="true"/>


        <el-table-column label="合同截止时间" align="contractEnd" prop="contractEnd"
                         :show-overflow-tooltip="true"></el-table-column>
        <el-table-column label="租赁方式" align="center" key="rentType" prop="rentType" :show-overflow-tooltip="true"/>
        <!-- <el-table-column label="审核状态" align="center" key="auditStatus" prop="auditStatus"
                         :show-overflow-tooltip="true">
          <template slot-scope="scope">
            <span v-if="scope.row.auditStatus === '0'">审核中</span>
            <span v-else-if="scope.row.auditStatus === '1'">通过</span>
            <span v-else>驳回</span>
          </template>
        </el-table-column> -->
        <el-table-column label="创建时间" align="center" key="createTime" prop="createTime"
                         :show-overflow-tooltip="true"></el-table-column>
        <el-table-column
          label="操作"
          align="center"
          width="100"
          class-name="small-padding fixed-width"
          :show-overflow-tooltip="true"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="checkLeaseInfo(scope.row)"
              v-hasPermi="['manage:lease:query']"
            >查看
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
      <!--用于切换：业主和物业-->
      <template>
        出租方：
        <el-radio v-model="radio" :label="true">业主</el-radio>
        <el-radio v-model="radio" :label="false">物业</el-radio>
      </template>
      <!--添加-->
      <div>
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
          <h4 class="form-header">租户基本信息</h4>
          <!--姓名手机号性别-->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="姓名" prop="tenantName">
                <el-input v-model="form.tenantName" placeholder="请输入租户姓名" maxlength="30"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="手机号码" prop="tenantPhone">
                <el-input v-model="form.tenantPhone" placeholder="请输入手机号码" maxlength="11"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="性别" prop="gender">
                <el-select v-model="form.gender" placeholder="请选择性别" style="width: 100%">
                  <el-option label="男" value="0"></el-option>
                  <el-option label="女" value="1"></el-option>
                  <el-option label="未知" value="2"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <!--出生日期 证件类型 证件号码-->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="证件类型" prop="cardType">
                <el-select v-model="form.cardType" placeholder="请选择证件类型" style="width: 100%;">
                  <el-option
                    v-for="dict in certificateOptions"
                    :key="dict.dictValue"
                    :label="dict.dictLabel"
                    :value="dict.dictValue"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="证件号码" prop="cardNo">
                <el-input v-model="form.cardNo" placeholder="请输入证件号码" maxlength="30"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="紧急联系人" prop="emergencyContact">
                <el-input v-model="form.emergencyContact" placeholder="请输入紧急联系人" maxlength="30"/>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="20">
              <el-form-item label="紧急联系人手机号" prop="emergencyContactPhone" label-width="150px">
                <el-input v-model="form.emergencyContactPhone" placeholder="请输入紧急联系人手机号" maxlength="11" style="width: 270px;"/>
              </el-form-item>
            </el-col>
          </el-row>

          <!--证件信息-->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="证件照正面" prop="cardFont" label-position="left">
                <el-upload
                  class="el-upload--picture-card avatar-uploader2"
                  :action="url"
                  :show-file-list="false"
                  accept="doc/*"
                  :headers="headers"
                  :before-upload="beforeUpload"
                  :on-success="handleUploadFont"
                  :on-error="uploadErrorHandler">
                  <img v-if="this.options.cardFontImg" :src="this.options.cardFontImg" class="avatar">
                  <i slot="default" class="el-icon-plus"></i>
                </el-upload>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="证件照反面" prop="cardBack" label-position="left">
                <el-upload
                  class="el-upload--picture-card avatar-uploader2"
                  :action="url"
                  :show-file-list="false"
                  accept="image/*"
                  :headers="headers"
                  :before-upload="beforeUpload"
                  :on-success="handleUploadBack"
                  :on-error="uploadErrorHandler">
                  <img v-if="this.options.cardBackImg" :src="this.options.cardBackImg" class="avatar">
                  <i slot="default" class="el-icon-plus"></i>
                </el-upload>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="个人照片" prop="faceUrl" label-position="left" class="avatar">
                <el-upload
                  class="el-upload--picture-card owner-avatar"
                  :action="url"
                  :show-file-list="false"
                  accept="image/*"
                  :headers="headers"
                  :before-upload="beforeUpload"
                  :on-success="handleUploadAvatar"
                  :on-error="uploadErrorHandler">
                  <img v-if="this.options.faceUrlImg" :src="this.options.faceUrlImg" class="avatar">
                  <i slot="default" class="el-icon-plus"></i>
                </el-upload>
              </el-form-item>
            </el-col>
          </el-row>
          <!--户籍地详址-->
          <!--          <el-row>
                      <el-col :span="6" :offset="1">
                        <el-form-item label="户籍地详址" prop="domicileAddress">
                          <el-input v-emoji v-model="form.domicileAddress" placeholder="请输入户籍地详址"
                                    onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"/>
                        </el-form-item>
                      </el-col>
                    </el-row>-->
          <h4 class="form-header">租户详细信息</h4>
          <!--政治面貌 宗教信仰 籍贯-->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="政治面貌" prop="politicsStatus">
                <el-select label="政治面貌" v-model="form.politicsStatus" prop="politicsStatus"
                           placeholder="请选择政治面貌" style="width: 100%;">
                  <el-option label="党员" value="1"></el-option>
                  <el-option label="团员" value="2"></el-option>
                  <el-option label="其他" value="3"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="宗教信仰" prop="religion">
                <el-input v-model="form.religion" placeholder="请输入宗教信仰" maxlength="11"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="3">
              <el-form-item label="籍贯" prop="nativePlace">
                <el-input v-model="form.nativePlace" placeholder="请输入籍贯" maxlength="11"/>
              </el-form-item>
            </el-col>
          </el-row>
          <!--民族 文化程度 兵役状况-->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="民族" prop="nation">
                <el-input v-model="form.nation" placeholder="请输入民族" maxlength="30"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="文化程度" prop="eduLevel">
                <el-input v-model="form.eduLevel" placeholder="请输入文化程度" maxlength="11"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="3">
              <el-form-item label="兵役状况" prop="military">
                <el-input v-model="form.military" placeholder="请输入兵役状况" maxlength="11"/>
              </el-form-item>
            </el-col>
          </el-row>
          <!--婚姻状况 国际 人口类型-->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="婚姻状况" prop="marriageStatus">
                <el-select v-model="form.marriageStatus" placeholder="请选择婚姻状况" style="width: 100%;">
                  <el-option label="未婚" value="0"></el-option>
                  <el-option label="已婚" value="1"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="国籍" prop="nationality">
                <el-input v-model="form.nationality" placeholder="请输入国籍" maxlength="11"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="3">
              <el-form-item label="人口类型" prop="population">
                <el-select v-model="form.population" placeholder="请选择人口类型" style="width: 100%;">
                  <el-option label="户籍人口" value="0"></el-option>
                  <el-option label="常驻人口" value="1"></el-option>
                  <el-option label="暂住人口" value="2"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <!--职业 qq号码  邮箱地址-->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="职业" prop="career">
                <el-input v-model="form.career" placeholder="请输入职业" maxlength="11"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="QQ号码" prop="qqNumber">
                <el-input v-emoji v-model="form.qqNumber" placeholder="请输入QQ号码"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="3">
              <el-form-item label="邮箱地址" prop="email">
                <el-input v-emoji v-model="form.email" placeholder="请输入邮箱"/>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 个人介绍  -->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="个人介绍" prop="selfIntro">
                <el-input v-emoji v-model="form.selfIntro" placeholder="请输入个人介绍"/>
              </el-form-item>
            </el-col>
          </el-row>

          <div v-if="radio">
            <h4 class="form-header">绑定房东信息</h4>
            <!--所属小区 楼栋  单元  -->
            <el-row>
              <el-col :span="6" :offset="1">
                <el-form-item label="所属小区" prop="communityId">
                  <el-select v-model="form.communityId" placeholder="请选择小区" style="width: 100%"
                             @change="getBuildings">
                    <el-option
                      v-for="item in communityList"
                      :key="item.communityId"
                      :label="item.communityName"
                      :value="item.communityId"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="2">
                <el-form-item label="所属楼栋" prop="buildingId">
                  <el-select v-model="form.buildingId" placeholder="请选择所属楼宇" style="width: 100%;"
                             @change="getUnits">
                    <el-option
                      v-for="dict in buildingList"
                      :key="dict.buildingId"
                      :label="dict.buildingName"
                      :value="dict.buildingId"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="2">
                <el-form-item label="所属单元" prop="unitId">
                  <el-select v-model="form.unitId" placeholder="请选择所属单元" style="width: 100%;"
                             @change="getRooms">
                    <el-option
                      v-for="dict in unitList"
                      :key="dict.unitId"
                      :label="dict.unitName"
                      :value="dict.unitId"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <!--租赁房号 业主姓名  手机号-->
            <el-row>
              <el-col :span="6" :offset="1">
                <el-form-item label="所属房间" prop="roomId">
                  <el-select v-model="form.roomId" placeholder="请选择所属房间" style="width: 100%;"
                             @change="getOwners">
                    <el-option
                      v-for="dict in roomList"
                      :key="dict.roomId"
                      :label="dict.roomName"
                      :value="dict.roomId"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="2">
                <el-form-item label="业主姓名" prop="ownerId">
                  <el-select v-model="form.ownerId" placeholder="请选择业主" style="width: 100%;"
                             @change="getOwnerPhone">
                    <el-option
                      v-for="dict in ownerNameList"
                      :key="dict.ownerId"
                      :label="dict.ownerName"
                      :value="dict.ownerId"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="2">
                <el-form-item label="手机号" prop="ownerPhone">
                  <el-select v-model="form.ownerPhone" placeholder="请选择业主手机号" style="width: 100%;">
                    <el-option
                      v-for="dict in ownerNameList"
                      :key="dict.ownerPhone"
                      :label="dict.ownerPhone"
                      :value="dict.ownerPhone"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </div>

          <div v-if="!radio">
            <h4 class="form-header">绑定物业信息</h4>
            <!--所属小区 楼栋  单元  -->
            <el-row>
              <el-col :span="6" :offset="1">
                <el-form-item label="所属小区" prop="communityId">
                  <el-select v-model="form.communityId" placeholder="请选择小区" style="width: 100%"
                             @change="getBuildings">
                    <el-option
                      v-for="item in communityList"
                      :key="item.communityId"
                      :label="item.communityName"
                      :value="item.communityId"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="2">
                <el-form-item label="所属楼栋" prop="buildingId">
                  <el-select v-model="form.buildingId" placeholder="请选择所属楼宇" style="width: 100%;"
                             @change="getUnits">
                    <el-option
                      v-for="dict in buildingList"
                      :key="dict.buildingId"
                      :label="dict.buildingName"
                      :value="dict.buildingId"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="2">
                <el-form-item label="所属单元" prop="unitId">
                  <el-select v-model="form.unitId" placeholder="请选择所属单元" style="width: 100%;"
                             @change="getRooms">
                    <el-option
                      v-for="dict in unitList"
                      :key="dict.unitId"
                      :label="dict.unitName"
                      :value="dict.unitId"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="6" :offset="1">
                <el-form-item label="所属房间" prop="roomId">
                  <el-select v-model="form.roomId" @change="updateFun" placeholder="请选择所属房间" style="width: 100%;">
                    <el-option
                      v-for="dict in roomList"
                      :key="dict.roomId"
                      :label="dict.roomName"
                      :value="dict.roomId"
                    ></el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </div>


          <h4 class="form-header">合同信息</h4>
          <!--合同生效时间  合同截止时间 租金-->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="合同生效时间" prop="contractStart" label-width="110px">
                <el-date-picker
                  v-model="form.contractStart"
                  style="width: 100%;"
                  value-format="yyyy-MM-dd"
                  type="date"
                  @change="getContractInfo(form.contractStart  , form.contractEnd)"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="合同截止时间" prop="contractEnd" label-width="110px">
                <el-date-picker
                  v-model="form.contractEnd"
                  style="width: 100%;"
                  value-format="yyyy-MM-dd"
                  type="date"
                  @change="getContractInfo(form.contractStart  , form.contractEnd)"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="租金（元/月）" prop="monthRent" label-width="110px">
                <el-input-number v-model="form.monthRent" :min="0" :max="99999999" :precision="2"
                                 :step="0.1" @input="getJsTotalMoney(form.monthRent)"></el-input-number>
              </el-form-item>
            </el-col>
          </el-row>
          <!--合同有效天数 租赁月数 合同来源-->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="合同有效天数" prop="contractDay" label-width="110px">
                <el-input
                  v-model="this.contractDay"
                  maxlength="11"
                  placeholder="请输入合同有效天数"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="租赁月数" prop="contractMoth" label-width="110px">
                <el-input
                  v-model="this.contractMoth"
                  maxlength="11"
                  placeholder="请输入租赁月数"
                  disabled
                />
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="押金" prop="deposit" label-width="110px">
                <el-input-number v-model="form.deposit" :min="0" :max="99999" :precision="2" :step="0.1"></el-input-number>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="押金付款时间" prop="depositPayTime" label-width="110px">
                <el-date-picker
                  v-model="form.depositPayTime"
                  style="width: 100%;"
                  value-format="yyyy-MM-dd"
                  type="date"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="总金额" prop="totalMoney" label-width="110px">
                <el-input-number disabled v-model="form.totalMoney" :min="0" :max="99999" :precision="2" :step="0.1"></el-input-number>
              </el-form-item>
            </el-col>
            
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <!--上传合同文件-->
              <el-form-item label="合同信息">
                <div class="upload-file">
                  <el-upload
                    multiple
                    :action="uploadFileUrl"
                    :before-upload="handleBeforeUpload"
                    :file-list="fileList"
                    :limit="limit"
                    :on-error="handleUploadError"
                    :on-exceed="handleExceed"
                    :on-success="handleUploadSuccess"
                    :show-file-list="false"
                    :headers="headers"
                    class="upload-file-uploader"
                    ref="lease"
                  >
                    <!-- 上传按钮 -->
                    <el-button size="mini" type="primary">+ 新增合同</el-button>
                    <!-- 上传提示 -->
                    <div class="el-upload__tip" slot="tip" v-if="showTip">
                      请上传
                      <template v-if="fileSize"> 大小不超过 <b style="color: #f56c6c">{{ fileSize }}MB</b></template>
                      <template v-if="fileType"> 格式为 <b style="color: #f56c6c">{{ fileType.join("/") }}</b>
                      </template>
                      的文件
                    </div>
                  </el-upload>

                  <!-- 文件列表 -->
                  <transition-group class="upload-file-list el-upload-list el-upload-list--text"
                                    name="el-fade-in-linear" tag="ul">
                    <li :key="file.url" class="el-upload-list__item ele-upload-list__item-content"
                        v-for="(file, index) in fileList">
                      <el-link :href="file.url" :underline="false" target="_blank">
                        <span class="el-icon-document"> {{ getFileName(file.name) }} </span>
                      </el-link>
                      <div class="ele-upload-list__item-content-action">
                        <el-link :underline="false" @click="handleDeleteFile(index)" type="danger">删除</el-link>
                      </div>
                    </li>
                  </transition-group>
                </div>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer" style="float: right;margin-bottom: 30px" v-show="close">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </div>
    </div>


    <!--详情页-->
    <div v-show=checkInfo>
      <!--详情页！-->
      <div>
        <el-form ref="forms" :model="form" label-width="100px">
          <h4 class="form-header">租户基本信息</h4>
          <!--姓名手机号性别-->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="姓名" prop="tenantName">
                <div>
                  {{ form.tenantName }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="手机号" prop="tenantName">
                <div>
                  {{ form.tenantPhone }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="性别">
                <div style="width: 400px; overflow: hidden">
                  <div v-if="form.gender==0">男</div>
                  <div v-if="form.gender==1">女</div>
                  <div v-if="form.gender==2">未知</div>
                </div>
              </el-form-item>
            </el-col>
          </el-row>
          <!--出生日期 证件类型 证件号码-->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="证件类型" prop="cardType">
                <div>{{ getCertificateOptions(form.cardType) }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="证件号码" prop="cardNo">
                <div>{{ form.cardNo }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="紧急联系人" prop="emergencyContact">
                {{ form.emergencyContact }}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="20">
              <el-form-item label="紧急联系人手机号" prop="emergencyContactPhone" label-width="150px">
                {{form.emergencyContactPhone}}
              </el-form-item>
            </el-col>
          </el-row>

          <!--证件信息-->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="证件正面" prop="cardFont">
                <img :src="this.form.cardFont" class="avatar">
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="证件反面" prop="cardBack">
                <img :src="this.form.cardBack" class="avatar">
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="个人照片" prop="faceUrl" label-position="left" class="avatar">
                <img :src="this.form.faceUrl" class="avatar">
              </el-form-item>
            </el-col>
          </el-row>
          <h4 class="form-header">租户详细信息</h4>
          <!--政治面貌 宗教信仰 籍贯-->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="政治面貌" prop="politicsStatus">
                <div v-if="this.form.politicsStatus != ''">
                  <div v-show="this.form.politicsStatus==1">党员</div>
                  <div v-show="this.form.politicsStatus==2">团员</div>
                  <div v-show="this.form.politicsStatus==3">其他</div>
                </div>

              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="宗教信仰" prop="religion">
                <div>{{ form.religion }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="3">
              <el-form-item label="籍贯" prop="nativePlace">
                <div>{{ form.nativePlace }}</div>
              </el-form-item>
            </el-col>
          </el-row>
          <!--民族 文化程度 兵役状况-->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="民族" prop="nation">
                <div>{{ form.nation }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="文化程度" prop="eduLevel">
                <div>{{ form.eduLevel }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="3">
              <el-form-item label="兵役状况" prop="military">
                <div>{{ form.military }}</div>
              </el-form-item>
            </el-col>
          </el-row>
          <!--婚姻状况 国际 人口类型-->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="婚姻状况" prop="marriageStatus">

                <div v-if="this.form.marriageStatus != ''">
                  <div v-if="form.marriageStatus==0">未婚</div>
                  <div v-if="form.marriageStatus==1">已婚</div>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="国籍" prop="nationality">
                <div>{{ form.nationality }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="3">
              <el-form-item label="人口类型" prop="population">
                <div v-if="this.form.population != ''" style="width: 400px; overflow: hidden">
                  <div v-if="form.population==0">户籍人口</div>
                  <div v-if="form.population==1">常驻人口</div>
                  <div v-if="form.population==2">暂住人口</div>
                </div>
              </el-form-item>
            </el-col>
          </el-row>
          <!--职业 qq号码  邮箱地址-->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="职业" prop="career">
                <div>{{ form.career }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="QQ号码" prop="qqNumber">
                <div>{{ form.qqNumber }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="3">
              <el-form-item label="邮箱地址" prop="email">
                <div>{{ form.email }}</div>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 个人介绍  -->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="个人介绍" prop="selfIntro">
                <div>{{ form.selfIntro }}</div>
              </el-form-item>
            </el-col>
          </el-row>

          <div v-show=radio>
            <h4 class="form-header">绑定房东信息</h4>
            <!--所属小区 楼栋  单元  -->
            <el-row>
              <el-col :span="6" :offset="1">
                <el-form-item label="所属小区" prop="communityId">
                  <div>{{ form.communityName }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="2">
                <el-form-item label="所属楼栋" prop="buildingId">
                  <div>{{ form.buildingName }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="2">
                <el-form-item label="所属单元" prop="unitId">
                  <div>{{ form.unitName }}</div>
                </el-form-item>
              </el-col>
            </el-row>
            <!--租赁房号 业主姓名  手机号-->
            <el-row>
              <el-col :span="6" :offset="1">
                <el-form-item label="所属房间" prop="roomId">
                  <div>{{ form.roomName }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="2">
                <el-form-item label="业主姓名" prop="ownerId">
                  <div>{{ form.ownerName }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="6" :offset="2">
                <el-form-item label="手机号" prop="ownerPhone">
                  <div>{{ form.ownerPhone }}</div>
                </el-form-item>
              </el-col>
            </el-row>
          </div>
          <h4 class="form-header">合同信息</h4>
          <!--编号 创建时间 合同这状态-->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="合同编号" prop="nickName">
                <div>{{ form.contractNo }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="创建时间" prop="phonenumber">
                <div>{{ form.createTime }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="合同生效时间">
                <div>{{ form.contractStart }}</div>
              </el-form-item>
            </el-col>
          </el-row>
          <!--合同生效时间  合同截止时间 租金-->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="合同截止时间">
                <div>{{ form.contractEnd }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="租金（元/月）">
                <div>{{ form.monthRent }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="合同有效天数">
                <div>{{ form.leaseDay }}</div>
              </el-form-item>
            </el-col>
          </el-row>
          <!--合同有效天数 租赁月数 合同来源-->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="租赁月数">
                <div>{{ this.contractMoth }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="押金" prop="deposit" label-width="110px">
                {{form.deposit}}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="押金付款时间" prop="depositPayTime" label-width="110px">
                {{ form.depositPayTime }}
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="总金额" prop="totalMoney" label-width="110px">
                {{form.totalMoney}}
              </el-form-item>
            </el-col>
          </el-row>
          <!--合同下载-->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="合同下载：" class="theme-demo">
                <div v-if="this.form.contractUrl!='' ">
                  <div>{{ this.fileName }}</div>
                  <a :href="this.form.contractUrl" target="_blank"> 合同文件下载 </a>
                </div>
              </el-form-item>
            </el-col>
          </el-row>


        </el-form>
      </div>
      <div slot="footer" class="dialog-footer" style="float: right;margin-bottom: 30px" v-show="close">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </div>


  </div>
</template>

<script>
import {listLease, addLease, delLeases, getLeaseById} from "@/api/estate/manage/contract/lease";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {OwnerList} from "@/api/estate/manage/contract/owner";
import {getCurrentList, getBuildingList, getUnitList, getRoomList, getOwnerList} from '@/api/estate/community'
import {desensitization, equals} from "@/utils/common";
import {getToken} from "@/utils/auth";
import {parseTime} from "@/utils/global";

export default {
  name: "lease",
  props: {
    // 值
    value: [String, Object, Array],
    // 数量限制
    limit: {
      type: Number,
      default: 1,
    },
    // 大小限制(MB)
    fileSize: {
      type: Number,
      default: 5,
    },
    // 文件类型, 例如['png', 'jpg', 'jpeg']
    fileType: {
      type: Array,
      default: () => ["doc", "xls", "ppt", "txt", "pdf", "jpg", "jpeg"],
    },
    // 是否显示提示
    isShowTip: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      //文件名称
      fileName: "",
      number: 0,
      //上传doc文件的接口路径
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/file/upload", // 上传文件服务器地址
      //头
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      //上传图片所需的接口路径
      url: process.env.VUE_APP_BASE_API + "/estate/tenant/uploadPicture",
      //上传成功回调函数
      uploadList: [],
      //上传图片的信息
      options: {
        avatar: "",
        cardBack: "",
        cardFont: "",
        cardFontImg: "",
        faceUrlImg: "",
        cardBackImg: "",
      },
      //文件名称
      name: '',
      //存储文件信息
      fileList: [],
      // 表单校验
      rules: {
        tenantName: [
          {required: true, message: "业主姓名不能为空", trigger: "blur"}
        ],
        tenantPhone: [
          //1、手机号码验证正则
          {required: true, message: '请输入手机号', trigger: 'blur'},
          {pattern: /^1[3456789]\d{9}$/, message: "手机号格式不对", trigger: "blur"},
        ],
        gender: [
          {required: true, message: "性别不能为空", trigger: "blur"}
        ],
        cardType: [
          {required: true, message: "证件类型不能为空", trigger: "blur"}
        ],
        cardNo: [
          //身份证验证
          {required: true, message: '请输入证件号码', trigger: 'blur'},
        ],
        cardFont: [
          {required: true, message: "请选择证件照正面", trigger: ["blur", "change"]}
        ],
        cardBack: [
          {required: true, message: "请选择证件照反面", trigger: ["blur", "change"]}
        ],
        faceUrl: [
          {required: true, message: "请选择个人照片", trigger: ["blur", "change"]}
        ],
        communityId: [
          {required: true, message: "所属小区不能为空", trigger: "blur"}
        ],
        buildingId: [
          {required: true, message: "楼栋不能为空", trigger: "blur"}
        ],
        unitId: [
          {required: true, message: "所属单元不能为空", trigger: "blur"}
        ],
        roomId: [
          {required: true, message: "所属房间不能为空", trigger: "blur"}
        ],
        ownerId: [
          {required: true, message: "业主姓名不能为空", trigger: "blur"}
        ],
        ownerPhone: [
          {required: true, message: "业主手机号不能为空", trigger: "blur"}
        ],
        contractStart: [
          {required: true, message: "租房合同开始日期不能为空", trigger: "blur"}
        ],
        contractEnd: [
          {required: true, message: "租房合同结束日期不能为空", trigger: "blur"}
        ],
        monthRent: [
          {required: true, message: "租金不能为空", trigger: "blur"}
        ],
        contractDay: [
          {required: true, message: "租赁天数不能为空", trigger: "blur"}
        ],
        contractMoth: [
          {required: true, message: "租赁月数不能为空", trigger: "blur"}
        ],
        deposit: [
          {required: true, message: "押金不能为空", trigger: "blur"}
        ]
      },
      checkAddView: false,
      //合同有效天数
      contractDay: 0,
      //租赁月数
      contractMoth: 0,
      //小区数据
      communityList: [],
      //楼栋数据
      buildingList: [],
      //单元
      unitList: [],
      //用户姓名
      ownerNameList: [],
      //房间数据
      roomList: [],
      roomValue: "",
      //详情页控制显隐
      checkInfo: false,
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
      leaseList: [],
      //新增提交按钮显隐
      close: true,
      //单选按钮
      radio: true,
      //详情页显隐
      checkView: true,
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 模糊查询数据
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        tenantName: undefined,
        tenantPhone: undefined,
      },
      // 表单参数
      form: {
        communityId: undefined,
        buildingId: undefined,
        unitId: undefined,
        roomId: undefined,
        ownerId: undefined
      },

      //获取业主信息的表单参数
      ownerInfoForm: {
        roomId: "",
      },
      //楼栋ID
      communityIds: 0,
    };
  },
  created() {
    this.getDicts("sys_certificate").then(response => {
        this.certificateOptions = response.data;
    });
    this.getList();
    this.getCommunitys();
  },
  watch: {
    value: {
      handler(val) {
        if (val) {
          let temp = 1;
          // 首先将值转为数组
          const list = Array.isArray(val) ? val : this.value.split(',');
          // 然后将数组转为对象数组
          this.fileList = list.map(item => {
            if (typeof item === "string") {
              item = {name: item, url: item};
            }
            item.uid = item.uid || new Date().getTime() + temp++;
            return item;
          });
        } else {
          this.fileList = [];
          return [];
        }
      },
      deep: true,
      immediate: true
    }
  },
  computed: {
    // 是否显示提示
    showTip() {
      return this.isShowTip && (this.fileType || this.fileSize);
    },
  },
  methods: {
    reduMonths(startMonth, endMonth) {
      let startY = startMonth.slice(0, 4),
        startM = startMonth.slice(4),
        endY = endMonth.slice(0, 4),
        endM = endMonth.slice(4);
      startMonth = startY + startM;
      endMonth = endY + endM;
      if (startMonth > endMonth) {
        let reduY = startY - endY,
          reduM = startM - endM;
        return reduY * 12 + reduM;
      } else if (startMonth < endMonth) {
        let reduY = endY - startY,
          reduM = endM - startM;
        return reduY * 12 + reduM;
      } else {
        return 0;
      }
    },
    //月份/金额
    getJsTotalMoney(monthRent) { 
      console.log(monthRent);
      var contractMoth=this.contractMoth;
      if (contractMoth && monthRent) {
        this.form.totalMoney = parseFloat(parseFloat(contractMoth) * parseFloat(monthRent));
      } else { 
        this.form.totalMoney = "0.00";
      }
    },
    // 上传前校检格式和大小
    handleBeforeUpload(file) {
      // 校检文件类型
      if (this.fileType) {
        let fileExtension = "";
        if (file.name.lastIndexOf(".") > -1) {
          fileExtension = file.name.slice(file.name.lastIndexOf(".") + 1);
        }
        const isTypeOk = this.fileType.some((type) => {
          if (file.type.indexOf(type) > -1) return true;
          if (fileExtension && fileExtension.indexOf(type) > -1) return true;
          return false;
        });
        if (!isTypeOk) {
          this.$modal.msgError(`文件格式不正确, 请上传${this.fileType.join("/")}格式文件!`);
          return false;
        }
      }
      // 校检文件大小
      if (this.fileSize) {
        const isLt = file.size / 1024 / 1024 < this.fileSize;
        if (!isLt) {
          this.$modal.msgError(`上传文件大小不能超过 ${this.fileSize} MB!`);
          return false;
        }
      }
      this.$modal.loading("正在上传文件，请稍候...");
      this.number++;
      return true;
    },
    // 文件个数超出
    handleExceed() {
      this.$modal.msgError(`上传文件数量不能超过 ${this.limit} 个!`);
    },
    // 上传失败
    handleUploadError(err) {
      this.$modal.msgError("上传文件失败，请重试");
      this.$modal.closeLoading()
    },
    // 上传成功回调
    handleUploadSuccess(res, file) {
      if (res.code === 200) {
        this.uploadList.push({name: res.data.url, url: res.data.url});
        this.uploadedSuccessfully();
      } else {
        this.number--;
        this.$modal.closeLoading();
        this.$modal.msgError(res.msg);
        this.$refs.lease.handleRemove(file);
        this.uploadedSuccessfully();
      }
    },
    // 删除文件
    handleDeleteFile(index) {
      this.fileList.splice(index, 1);
      this.$emit("input", this.listToString(this.fileList));
    },
    // 上传结束处理
    uploadedSuccessfully() {
      if (this.number > 0 && this.uploadList.length === this.number) {
        this.fileList = this.fileList.concat(this.uploadList);
        this.form.contractUrl = this.fileList[0].url;
        this.uploadList = [];
        this.number = 0;
        this.$emit("input", this.listToString(this.fileList));
        this.$modal.closeLoading();
      }
    },
    // 获取文件名称
    getFileName(name) {
      if (name.lastIndexOf("/") > -1) {
        return name.slice(name.lastIndexOf("/") + 1);
      } else {
        return "";
      }
    },
    // 对象转成指定字符串分隔
    listToString(list, separator) {
      let strs = "";
      separator = separator || ",";
      for (let i in list) {
        strs += list[i].url + separator;
      }
      return strs != '' ? strs.substr(0, strs.length - 1) : '';
    },


    /** 手机号脱敏 */
    phoneFormatter(phone) {
      if (!equals(phone, undefined)) {
        return desensitization(phone, 3, 7);
      }
    },
    /** 身份证号脱敏 */
    cardFormatter(card) {
      if (!equals(card, undefined)) {
        return desensitization(card, 6, 14);
      }
    },
    //详情按钮
    checkLeaseInfo(row) {
      //打开详情页面，并置空reset
      this.reset();
      this.checkView = false;
      this.checkInfo = true;
      //清空日期
      this.contractMoth = 0;
      this.contractDay = 0;
      //后去行id
      const contractId = row.contractId;
      //请求接口数据，数据铺上
      getLeaseById(contractId).then(response => {
        this.form = response.data;
        console.log(this.form);
        this.fileName = this.getLeaseFileName(this.form.contractUrl);
        this.getContractInfo(this.form.contractStart,this.form.contractEnd);
      })
    },

    //获取文件名称
    getLeaseFileName(leaseUrl) {
      var file = leaseUrl.split("/");
      return file[file.length - 1];
    },


    //小区
    getCommunitys() {
      getCurrentList().then(response => {
        this.communityList = response.data;
      })
    },
    //楼栋(社区触发)
    getBuildings(communityId) {
      let comm = this.communityList.filter((item) => {
        return item.communityId === communityId
      });
      this.form.communityName = comm[0].communityName;
      this.form.buildingId = undefined;
      this.form.unitId = undefined;
      this.form.roomId = undefined;
      this.form.ownerId = undefined;
      this.form.ownerPhone = '';
      this.buildingList = [];
      this.unitList = [];
      this.roomList = [];
      this.ownerNameList = [];
      getBuildingList(communityId).then(response => {
        this.buildingList = response.data;
      })
    },
    //获取单元
    getUnits(buildingId) {
      this.form.roomId = undefined;
      this.form.unitId = undefined;
      this.form.ownerId = undefined;
      this.form.ownerPhone = '';
      this.unitList = [];
      this.roomList = [];
      this.ownerNameList = [];
      getUnitList(buildingId).then(response => {
        this.unitList = response.data;
      })
    },
    //房号
    getRooms(unitId) {
        this.form.roomId = undefined;
        this.form.ownerId = undefined;
        this.form.ownerPhone = '';
        this.ownerNameList = [];
        getRoomList(unitId).then(response => {
          this.roomList = response.data;
        })
    },
    //根据房间id查业主
    getOwners(d) {
      if (d != '') {
        console.log(d, 'd');
        this.form.ownerId = '';
        this.form.ownerPhone = '';
        this.ownerNameList = [];
        this.ownerInfoForm.roomId = d;
        getOwnerList(this.addDateRange(this.ownerInfoForm)).then(response => {
          this.ownerNameList = response.data;
        })
      }
    },
    getOwnerPhone(e) {
      let owner = this.ownerNameList.filter((item) => {
        return item.ownerId === e
      });
      this.form.ownerPhone = owner[0].ownerPhone;
    },
    //自定义通过(暂时不用)
    getOwner(c) {
      OwnerList(c).then(response => {
          this.leaseList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    //计算日期间隔天数getContractInfo
    getContractInfo(sDate1, sDate2) {
      console.log(sDate1);
      //sDate1和sDate2是2006-12-18格式
      if (sDate1 === undefined || sDate2 === undefined) {
        this.contractDay = 0;
        this.contractMoth = 0;
        return
      }
      var d1 = new Date(sDate1);
      var d2 = new Date(sDate2);
      this.contractDay = (d2.getTime()-d1.getTime())/(1000*60*60*24);

      //月份计算
      this.contractMoth = this.reduMonths(parseTime(sDate1, '{y}{m}'), parseTime(sDate2, '{y}{m}')) + 1;
      this.getJsTotalMoney(this.form.monthRent);
    },

    /** 查询列表 */
    getList() {
      this.loading = true;
      listLease(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.leaseList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const leaseIds = row.contractId || this.ids;
      this.$confirm('是否确认删除所选中的的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delLeases(leaseIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
    },


    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.contractId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 取消按钮
    cancel() {
      this.contractMoth = 0;
      this.contractDay = 0;
      this.checkView = true;
      this.checkAddView = false;
      this.checkInfo = false;
      this.fileList = [];
      this.uploadList = [];
      this.reset();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getCommunitys();
      this.checkView = false;
      this.checkAddView = true;
      
    },
    getCertificateOptions(cardType) { 
      var obj = this.certificateOptions.filter(item => item.dictValue == cardType)[0];
      if (obj) { 
       return obj.dictLabel;
      }
    },
    /** 提交按钮 */
    submitForm: function () {
      if (this.form.contractStart === undefined) {
        this.$modal.msgError(`合同生效日期不能为空!`);
        return;
      }
      if (this.form.contractEnd === undefined) {
        this.$modal.msgError(`合同截止日期不能为空!`);
        return;
      }
      if (Date.parse(this.form.contractStart) >= Date.parse(this.form.contractEnd)) {
        this.$modal.msgError(`合同截止日期不能晚于生效日期!`);
        return;
      }
      if (this.form.cardFont === undefined) {
        this.$modal.msgError(`证件照正面不能为空!`);
        return;
      }
      if (this.form.cardBack === undefined) {
        this.$modal.msgError(`证件照反面不能为空!`);
        return;
      }
      if (this.form.faceUrl === undefined) {
        this.$modal.msgError(`个人照片不能为空!`);
        return;
      }
      console.log(this.form);
      this.$refs["form"].validate(valid => {
        if (valid) {
          addLease(this.form).then(response => {
            this.fileList = [];
            this.uploadList = [];
            this.contractMoth = 0;
            this.contractDay = 0;
            this.$message.success("新增成功");
            this.checkView = true;
            this.checkAddView = false;
            this.getList();
            this.reset();
          });
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
        contractId: undefined,
        tenantName: undefined,
        tenantPhone: undefined,
        ownerName: undefined,
        ownerPhone: undefined,
        communityName: undefined,
        buildingName: undefined,
        unitName: undefined,
        roomName: undefined,
        monthRent: 0.00,
        //押金
        deposit: "0.00",
        //租赁总金额
        totalMoney: "0.00",
        //押金支付时间
        depositPayTime:undefined,
        contractStart: undefined,
        contractEnd: undefined,
        ownerId: undefined,
        auditStatus: undefined,
        createTime: undefined,
        communityId: undefined,
        domicileAddress: undefined,
        eduLevel: undefined,
        contractNo: undefined,
        contractUrl: "",
        contractDay: 0,
        contractMoth: 0,
        leaseDay: 0,
        leaseMonth: 0
      };
      this.options = {
        avatar: "",
        cardBack: "",
        cardFont: "",
        cardFontImg: "",
        faceUrlImg: "",
        cardBackImg: "",
      };
      this.resetForm("form");
    },
    // 上传预处理
    beforeUpload(file) {
      return true;
    },
    // 上传正面图片
    handleUploadFont(res) {
      this.form.cardFont = res.imgPath;
      this.options.cardFontImg = res.imgPath;
      this.$modal.msgSuccess("上传成功");
    },
    // 上传身份证反面图片
    handleUploadBack(res) {
      this.form.cardBack = res.imgPath;
      this.options.cardBackImg = res.imgPath;
      this.$modal.msgSuccess("上传成功");
    },
    // 上传头像
    handleUploadAvatar(res) {
      this.form.faceUrl = res.imgPath;
      this.options.faceUrlImg = res.imgPath;
      this.$modal.msgSuccess("上传成功");
    },
    uploadErrorHandler() {
      this.$modal.msgSuccess("上传失败！");
    },
    updateFun() {
      this.$forceUpdate();
    }

  }
};
</script>


<style scoped>

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

>>> .el-upload {
  display: block;
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
  height: 200px !important;
  width: 110% !important;
}

.avatar2 {
  height: 200px;
  width: 100%
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

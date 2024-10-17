<template>
  <div class="app-container">
    <div v-show="homeShow">
      <el-row :gutter="20">
        <!--部门数据-->
        <el-col :span="4" :xs="24">
          <div class="head-container">
            <el-input
              placeholder="请输入小区名称"
              clearable
              size="small"
              prefix-icon="el-icon-search"
              style="margin-bottom: 20px"
              v-model="search"
            />
          </div>
          <el-table
            ref="singleTable"
            type="index"
            highlight-current-row
            :data="viewDeptData"
            :show-header="false"
            @cell-click="listClick"
          >
            <el-table-column prop="communityName" />
          </el-table>
        </el-col>
        <el-col :span="20" :xs="24">
          <!-- 条件搜索 -->
          <el-form
            :model="queryParams"
            ref="queryForm"
            size="small"
            :inline="true"
          >
            <el-form-item label="车位编号" prop="carportNo">
              <el-select filterable  v-model="queryParams.carportNo" placeholder="请选择车位编号" clearable>
                  <el-option
                    v-for="d in carportNoList"
                    :key="d.carportNoId+''"
                    :label="d.carportNo"
                    :value="d.carportNoId+''"
                  />
                </el-select>
            </el-form-item>
            <el-form-item label="车位状态" prop="allStatus">
              <el-select
                v-model="queryParams.allStatus"
                placeholder="请选择车位状态"
              >
                <el-option 
                    v-for="item in dict.type.carport_all_status" 
                    :key="item.value"
                    :label="item.label"
                    :value="item.value" 
                    />
                
              </el-select>
            </el-form-item>
            <!-- <el-form-item label="车位状态" prop="carportStatus">
              <el-select
                v-model="queryParams.carportStatus"
                placeholder="请选择车位状态"
              >
                <el-option label="开启" value="0" />
                <el-option label="关闭" value="1" />
              </el-select>
            </el-form-item> -->
            <el-form-item label="车位属性" prop="carportNature">
              <el-select
                v-model="queryParams.carportNature"
                placeholder="请选择车位类型"
              >
                <el-option label="人防" value="0" />
                <el-option label="非人防" value="1" />
              </el-select>
            </el-form-item>
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
          </el-form>

          <div>
            <!-- 权限 v-hasPermi="['estate:carport:remove']" -->
            <el-button
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="addMethod"
              >新增</el-button
            >
            <!-- <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              style="color: #f56c6c"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              >删除</el-button
            > -->
            <el-button
                  type="info"
                  plain
                  icon="el-icon-upload2"
                  size="mini"
                  @click="handleImport"
                  >导入</el-button
                >
          </div>

          <!-- 列表展示 -->
          <el-table
            stripe
            v-loading="loading"
            :data="injList"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column
              label="车位编号"
              align="center"
              prop="carportNo"
              type="number"
            >
            <template slot-scope="scope">
              {{ getCarportNo(scope.row.carportNo) }}
            </template>
          </el-table-column>

            <el-table-column
              label="车位属性"
              align="center"
              prop="carportNature"
              :show-overflow-tooltip="true"
            >
            </el-table-column>
            <el-table-column
              label="租售方式"
              align="center"
              prop="rentalTypeName"
              :show-overflow-tooltip="true"
            >
              <template slot-scope="scope">
                {{scope.row.allStatus=='0'?'-':scope.row.rentalTypeName==null?'-':scope.row.rentalTypeName }}
              </template>
            </el-table-column>
            <el-table-column
              label="车位出售金额"
              align="center"
              prop="carportFeeSell"
              :show-overflow-tooltip="true"
            > 
              <template slot-scope="scope">
                {{scope.row.allStatus=='0'?'-':scope.row.carportFeeSell==null?'-':scope.row.carportFeeSell }}
              </template>
            </el-table-column>
            <el-table-column
              label="车位月租金额"
              align="center"
              prop="carportFeeMonth"
              :show-overflow-tooltip="true"
            >  
              <template slot-scope="scope">
                {{scope.row.allStatus=='0'?'-':scope.row.carportFeeMonth}}
              </template>
            </el-table-column>
            <el-table-column
              label="车位年租金额"
              align="center"
              prop="carportFeeYear"
              :show-overflow-tooltip="true"
            >  
              <template slot-scope="scope">
                {{scope.row.allStatus=='0'?'-':scope.row.carportFeeYear}}
              </template>
            </el-table-column>
            <!-- <el-table-column
              label="车位状态"
              align="center"
              prop="carportStatus"
              :show-overflow-tooltip="true"
            >
              <template slot-scope="scope">
                <el-switch
                  v-model="scope.row.carportStatus"
                  active-value="0"
                  inactive-value="1"
                  @change="handleStatusChange(scope.row)"
                ></el-switch>
              </template>
            </el-table-column> -->
            <el-table-column
              label="售租状态"
              align="center"
              prop="allStatus"
              :formatter="as"
            />
            <el-table-column
              label="产权人"
              align="center"
              prop="accountName"
              :show-overflow-tooltip="true"
            >
            <template slot-scope="scope">
              {{scope.row.allStatus=='0'?'-':scope.row.accountName}}
            </template>
            </el-table-column>
            <el-table-column
              label="绑定车牌"
              align="center"
              prop="plateNo"
              :show-overflow-tooltip="true"
            >
              <template slot-scope="scope">
                {{scope.row.allStatus=='0'?'-':scope.row.plateNo}}
              </template>
            </el-table-column>
            <el-table-column
              label="租售开始时间"
              align="center"
              prop="rentalStart"
              :show-overflow-tooltip="true"
            >  
              <template slot-scope="scope">
                {{scope.row.allStatus=='0'?'-':scope.row.rentalStart}}
              </template>
            </el-table-column>
            <el-table-column
              label="租售结束时间"
              align="center"
              prop="rentalEnd"
              :show-overflow-tooltip="true"
            >
              <template slot-scope="scope">
                {{scope.row.allStatus=='0'?'-':scope.row.rentalEnd}}
              </template>
            </el-table-column>
            <el-table-column
              label="创建时间"
              align="center"
              prop="createTime"
              :show-overflow-tooltip="true"
            />
            <el-table-column
              label="操作"
              align="center"
              fixed="right"
              width="90"
              class-name="small-padding fixed-width"
            >
              <template slot-scope="scope">
                <el-button size="mini" type="text" v-if="scope.row.allStatus!='0'" @click="detail(scope.row)"
                  ><el-link style="font-size: 12px;" icon="el-icon-view" type="brand" :underline="false"
                    >详情</el-link
                  ></el-button
                >
                <el-button 
                  size="mini" 
                  type="text" 
                  v-if="scope.row.allStatus=='0'" 
                  @click="update(scope.row)"
                  >
                    <el-link 
                      style="font-size: 12px;" 
                      icon="el-icon-edit" 
                      type="success" 
                      :underline="false"
                    >修改</el-link>
                  </el-button>
                  <br v-if="scope.row.allStatus=='0'" >
                  <el-button 
                  size="mini" 
                  type="text" 
                  
                  style="color: #f56c6c"
                  v-if="scope.row.allStatus=='0'" 
                  @click="handleDelete(scope.row)"
                  >
                    <el-link 
                      icon="el-icon-delete"
                      type="success" 
                      style="color: #f56c6c;font-size: 12px;"
                      :underline="false"
                    >删除</el-link>
                  </el-button>
                  
                <br v-if="scope.row.allStatus=='2'">
                <el-button 
                  size="mini" 
                  type="text" 
                  v-if="scope.row.allStatus=='2'"
                  @click="cede(scope.row)"
                  >
                    <el-link 
                      style="font-size: 12px;" 
                      icon="el-icon-refresh" 
                      type="danger" 
                      :underline="false"
                    >转让</el-link>
                  </el-button>
                <br v-if="!scope.row.plateNo && scope.row.allStatus != '0'">
                <el-button
                v-if="!scope.row.plateNo && scope.row.allStatus != '0'"
                  size="mini"
                  type="text"
                  @click="cedeBind(scope.row)"
                  ><el-link
                    style="font-size: 12px;"
                    icon="el-icon-edit"
                    type="success"
                    :underline="false"
                    >绑定车辆</el-link
                  ></el-button>
                
              </template>
            </el-table-column>
          </el-table>

          <pagination
            v-show="total > 0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getBlackList"
        /></el-col>
      </el-row>
    </div>

    <!-- 新增页面 -->
    <el-dialog
      :visible.sync="addOpen"
      @close="addClose"
      :title="title"

    >
      <div>
        <el-form
          ref="addForm"
          :rules="addRules"
          :model="addForm"
          label-width="100px"
        >
          <el-row>
            <el-col :span="8">
              <!-- 小区下拉框 -->
              <el-form-item label="所属小区: ">
                <div style="width: 280px; overflow: hidden">
                  {{ addForm.communityName }}
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col  :span="10">
              <el-form-item label="车位编号: " prop="carportNo">
                <el-input
                v-model="addForm.carportNo"
                placeholder="请输入车位编号"
                style="width: 170px"
              />
                <!-- <el-select  v-model="addForm.carportNo" placeholder="请选择车位编号" clearable>
                  <el-option
                    v-for="d in carportNoList"
                    :key="d.carportNoId+''"
                    :label="d.carportNo"
                    :value="d.carportNoId+''"
                    :disabled="d.isCarport>=1"
                  />
                </el-select> -->
              </el-form-item>
            </el-col>
            <el-col :offset="2" :span="10">
               
              <el-form-item label="车位属性: " prop="carportNature">
                <el-select
                  v-model="addForm.carportNature"
                  placeholder="请选择车位属性"
                  @change="carportNatureChange(addForm.carportNature,'0')"
                 
                >
                  <el-option label="人防" value="0"></el-option>
                  <el-option label="非人防" value="1"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- <el-row>
            <el-col  :span="10">
              <el-form-item label="租售类型: " prop="rentalType">
                <el-select
                  v-model="addForm.rentalType"
                  placeholder="请选择租售类型"
                  :disabled="disRentalType"
                >
                  <el-option label="售" value="0" v-show="disType"></el-option>
                  <el-option label="年租" value="1" ></el-option>
                  <el-option label="月租" value="2"></el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :offset="2"  :span="10">
              <el-form-item label="金额: " prop="carportFee">
                <el-input-number
                  clearable
                  :min="0"
                  :max="10000000"
                  v-model="addForm.carportFee"
                  placeholder="请输入金额  ¥"
                ></el-input-number>
              </el-form-item>
            </el-col>
          </el-row> -->

          <el-row>
            <el-col :offset="10" :span="2">
              <div style="width: 280px">
                <el-button type="primary" @click="addSuccess"
                  >确定</el-button
                >
                <el-button @click="addClose">取消</el-button>
              </div>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-dialog>

    <!-- 详情页面 -->
    <div v-show="showDetail">
      <el-form ref="form" :model="detailForm" label-width="180px">
        <h5><b>车位所属信息</b></h5>
        <el-divider></el-divider>

        <!-- 第一行 -->
        <el-row>
          <el-col :offset="0" :span="4">
            <el-form-item label="所属小区: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.communityName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-form-item label="车位编号: ">
              <div style="width: 280px; overflow: hidden">
                {{ getCarportNo(detailForm.carportNo) }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-form-item label="车位属性: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.carportNature }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-form-item label="车位状态: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.carportStatus }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第二行 -->
        <el-row>
          <el-col :offset="0" :span="4">
            <el-form-item label="产权所属: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.accountName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-form-item label="联系方式: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.loginPhone }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-form-item label="绑定车位: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.plateNo }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-form-item label="当前租售方式: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.rentalMode }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>

        <!-- 第三行 -->
        <el-row>
          <el-col :offset="0" :span="4">
            <el-form-item label="租售开始时间: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.rentalStart }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-form-item label="租售结束时间: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.rentalEnd }}
              </div>
            </el-form-item>
          </el-col>
          <div v-show="showDetailTime">
            <el-col :offset="1" :span="4">
              <el-form-item label="租售时长: ">
                <div style="width: 280px; overflow: hidden">
                  {{ detailForm.dateInterval }}
                </div>
              </el-form-item>
            </el-col>
          </div>
        </el-row>

        <!-- 租售信息 -->
        <div>
          <h5><b>租售信息</b></h5>
        </div>
        <el-divider></el-divider>
        <el-row>
          <el-col :offset="0" :span="4">
            <el-form-item label="租售类型: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.rentalType }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :offset="1" :span="4">
            <el-form-item label="金额: ">
              <div style="width: 280px; overflow: hidden">
                {{ detailForm.carportFee }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 所属业户信息 -->
        <div>
          <h5><b>所属业户信息</b></h5>
        </div>
        <el-divider></el-divider>
        <el-row>
          <el-col :span="24">
            <el-table
              stripe
              type="index"
              highlight-current-row
              :data="ownerListData"
            >
              <el-table-column
                label="编号"
                align="center"
                :show-overflow-tooltip="true"
                prop="carporNo"
              />
              <el-table-column
                label="租售人员姓名"
                align="center"
                :show-overflow-tooltip="true"
                prop="userName"
              />
              <el-table-column
                label="身份证证件号"
                align="center"
                :show-overflow-tooltip="true"
                prop="cardNo"
              >
                <template scope="scope">
                  <span>{{scope.row.cardNo}}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="手机号"
                align="center"
                :show-overflow-tooltip="true"
                prop="userPhone"
              >
                <template scope="scope">
                  <span>{{ scope.row.userPhone }}</span>
                </template>
              </el-table-column>
              <el-table-column
                label="绑定车牌"
                align="center"
                :show-overflow-tooltip="true"
                prop="plantNo"
              />
              <el-table-column
                label="租售开始时间"
                align="center"
                :show-overflow-tooltip="true"
                prop="rentalStart"
              />
              <el-table-column
                label="租售结束时间"
                align="center"
                :show-overflow-tooltip="true"
                prop="rentalEnd"
              />
            </el-table>
          </el-col>
        </el-row>
        <el-row>
          <el-col :offset="21" :span="24">
            <el-button @click="cancel">返回</el-button>
          </el-col>
        </el-row>
      </el-form>
    </div>

    <!-- 车位转让 -->
    <el-dialog
      :visible.sync="cedeOpen"
      @close="cedeClose"
      title="车位转让"
      :modal="false"
      width="80%" 
    >
      <div>
        <el-form
          ref="cedeForm"
          :rules="cedeRules"
          :model="cedeForm"
          label-width="140px"
        >
          <el-row>
            <el-col :offset="3" :span="5">
              <el-form-item label="所属小区: ">
                <div style="width: 280px; overflow: hidden">
                  {{ cedeForm.communityName }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :offset="5" :span="5">
              <el-form-item label="车位编号: ">
                <div style="width: 280px; overflow: hidden">
                  {{ cedeForm.carportNo }}
                </div>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 第二行 -->
          <el-row>
            <el-col :offset="3" :span="5">
              <el-form-item label="租售方式: ">
                <div style="width: 280px; overflow: hidden">
                  {{ cedeForm.rentalTypeName }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :offset="5" :span="5">
              <el-form-item label="租售结束时间: ">
                <div style="width: 280px; overflow: hidden">
                  {{ cedeForm.rentalEnd }}
                </div>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 第三行 -->
          <el-row>
            <el-col :offset="3" :span="5">
              <el-form-item label="转让人姓名: ">
                <div style="width: 280px; overflow: hidden">
                  {{ cedeForm.accountNames }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :offset="5" :span="5">
              <el-form-item label="转让人号码: ">
                <div style="width: 280px; overflow: hidden">
                  {{ cedeForm.loginPhone }}
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-divider></el-divider>
          <!-- 第一行 -->
          <el-row>
            <el-col :offset="3" :span="5">
              <el-form-item label="所属小区: ">
                <div style="width: 280px; overflow: hidden">
                  {{ cedeForm.communityName }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :offset="5" :span="5">
              <el-form-item label="所属楼栋: ">
                <div style="width: 280px; overflow: hidden">
                  <el-select
                    v-model="buildingValue"
                    clearable
                    placeholder="请选择所属楼栋"
                    @change="buildingChange(buildingValue)"
                  >
                    <el-option
                      v-for="item in buildingData"
                      :key="item.buildingId"
                      :label="item.buildingName"
                      :value="item.buildingId"
                    >
                    </el-option>
                  </el-select>
                </div>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 第二行 -->
          <el-row>
            <el-col :offset="3" :span="5">
              <el-form-item label="所属单元: ">
                <div style="width: 280px; overflow: hidden">
                  <el-select
                    v-model="unitValue"
                    clearable
                    placeholder="请选择所属单元"
                    @change="unitChange(unitValue)"
                  >
                    <el-option
                      v-for="item in unitData"
                      :key="item.unitId"
                      :label="item.unitName"
                      :value="item.unitId"
                    >
                    </el-option>
                  </el-select>
                </div>
              </el-form-item>
            </el-col>
            <el-col :offset="5" :span="5">
              <el-form-item label="所属房号: ">
                <div style="width: 280px; overflow: hidden">
                  <el-select
                    v-model="roomValue"
                    clearable
                    placeholder="请选择所属房号"
                    @change="roomChange(roomValue)"
                  >
                    <el-option
                      v-for="item in roomData"
                      :key="item.roomId"
                      :label="item.roomName"
                      :value="item.roomId"
                    >
                    </el-option>
                  </el-select>
                </div>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 第三行 -->
          <el-row>
            <el-col :offset="3" :span="6">
              <el-form-item label="接受业户姓名: " prop="ownerValue">
                <div style="width: 280px; overflow: hidden">
                  <el-select
                    v-model="cedeForm.ownerValue"
                    clearable
                    placeholder="请选择业户"
                    @change="ownerChange(cedeForm.ownerValue)"
                  >
                    <el-option
                      v-for="item in ownerData"
                      :key="item.ownerId"
                      :label="item.ownerName"
                      :value="item.ownerId"
                    >
                    </el-option>
                  </el-select>
                </div>
              </el-form-item>
            </el-col>
            <el-col :offset="5" :span="5">
              <el-form-item label="接受业户联系方式: ">
                <div style="width: 280px; overflow: hidden">
                  {{ ownerPhones }}
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <!-- 第四行 -->
          <el-row>
            <el-col :offset="3" :span="6">
              <el-form-item label="接受业户车辆: " prop="ownerCarValueId">
                <div style="width: 280px; overflow: hidden">
                  <el-select
                    v-model="cedeForm.ownerCarValueId"
                    clearable
                    placeholder="请选择业主车辆:"
                    @change="ownerCarChange(cedeForm.ownerCarValueId)"
                  >
                    <el-option
                      v-for="item in ownerCarData"
                      :key="item.vehicleId"
                      :label="item.plateNo"
                      :value="item.vehicleId"
                    >
                    </el-option>
                  </el-select>
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :offset="10">
              <el-button
                size="small"
                type="primary"
                @click="cedeSuccess(cedeForm)"
                >确定</el-button
              >
              <el-button size="small" @click="cedeClose">取消</el-button>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-dialog>

    <!-- 车辆绑定 -->
    <el-dialog
      :visible.sync="cedeBindOpen"
      @close="cedeBindClose"
      title="车辆绑定"
      :modal="false"
      width="40%" 
    >
      <div>
        <el-form
          ref="cedeBindForm"
          :rules="cedeBindRules"
          :model="cedeBindForm"
          label-width="140px"
        >
        <!-- 车辆信息第一行 -->
        <el-row>
          <el-col :offset="2" :span="6">
            <el-form-item label="车牌号: " prop="plateNo">
              <el-select
                  v-model="cedeBindForm.plateNo"
                  clearable
                  placeholder="请选择车牌号"
                  style="width:200px"
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
          </el-col>
        </el-row>
          <el-row>
            <el-col :offset="18">
              <el-button
                size="small"
                type="primary"
                @click="cedeBindSuccess()"
                >确定</el-button
              >
              <el-button size="small" @click="cedeBindClose">取消</el-button>
            </el-col>
          </el-row>
        </el-form>
      </div>
    </el-dialog>
    
    <!-- 房屋导入对话框 -->
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
            :action="uploadImprot.url+'?communityId='+this.addForm.communityId"
            :disabled="uploadImprot.isUploading"
            :on-progress="handleFileUploadProgress"
            :on-success="handleFileSuccess"
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
                @click="importTemplate"
                >下载模板</el-link
              >
            </div>
          </el-upload>
          <div slot="footer" class="dialog-footer">
            <el-button type="primary" @click="submitFileForm">确 定</el-button>
            <el-button @click="uploadImprot.open = false">取 消</el-button>
          </div>
        </el-dialog>

  </div>
</template>

<script>
import {getBuildingList, getCurrentList, getOwnerList, getRoomList, getUnitList,} from "@/api/estate/community";
import {selectOwnerCar, selectOwnerName,getByAccountIdVehicleList} from "@/api/estate/center/buildingManage/vehicleManage/vehicleManage";
import {
  cedeSubmit,
  changeCarportStatus,
  getDetail,
  getDetailList,
  listPage,
  removes,
  saveOrUpdate,
  updateDetail,
  getCommunityCarportList,
  bindVehicle
} from "@/api/estate/center/buildingManage/carPositionManage/carPositionManage";
import { desensitization, equals, isEmpty } from "@/utils/common";
import {getToken} from "@/utils/auth";
export default {
  name: "carPositionManage",
  dicts:['carport_all_status'],
  data() {
    return {
      disRentalType: 'disabled',
      disType: false,
      showDetailTime: true,
      plateNoOptions:[],
      title:"",
      // 搜索
      search: "",
      //车位ID- 判断增减
      carportIds: null,
      //新增页面显示手机号
      ownerPhones: null,
      // 业主车辆下拉框
      ownerCarData: [],
      ownerCarValueId: "", // 业主车辆ID
      // 业户姓名
      // 临时假数据 { ownerId: 11, ownerName: "内容" }
      ownerData: [],
      ownerValue: "",
      //转出表单
      cedeForm: { ownerValue: "", ownerCarValueId: "" },
      cedeBindForm:{ },
      // 详情表单
      detailForm: {},
      // 新增页面表单
      addForm: {
        rentalType: '',
        carportNo:undefined
      },
      // 车位信息导入参数
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
        url: process.env.VUE_APP_BASE_API + "/estate/carport/importData",
      },
      // 小区ID
      communityId: undefined,
      // 小区ID 新增修改传参
      communityIds: null,
      // 详情内表格数据
      ownerListData: [],
      // 机构列表
      deptData: [],
      // 机构下拉框
      deptValue: "",
      // 新增弹窗
      addOpen: false,
      // 转出弹窗
      cedeOpen: false,
      //绑定车辆弹框
      cedeBindOpen: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        // 小区ID
        communityId: undefined,
        carportNo:undefined,
        allStatus: undefined,
        carportNature: undefined,
      },
      carportNoList:[],
      // 遮罩层
      loading: true,
      // 表格数据
      injList: [],
      // 选中数组
      ids: [],
      // 楼栋下拉框
      buildingData: [],
      buildingValue: "",
      // 单元下拉框
      unitData: [],
      unitValue: "",
      // 房号下拉框
      roomData: [],
      roomValue: "",
      // 业主车辆下拉框
      ownerCarData: [],
      // 详情页面默认关闭
      showDetail: false,
      homeShow: true,
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 表单参数
      form: {},
       // 车辆颜色下拉框
       carColour: [],
      // 车牌颜色下拉框
      plateColorList: [],
      // 车辆类型下拉框
      plateTypeList: [],
      // 新增表单校验
      addRules: {
        carportNo: [
          { required: true, message: "车位编号不能为空", trigger: "blur" },
        ],
        carportNature: [
          { required: true, message: "请选择车位属性", trigger: "change" },
        ],
        rentalType: [
          { required: true, message: "请选择租售类型", trigger: "change" },
        ],
        carportFee: [
          { required: true, message: "金额不能为空", trigger: "blur" },
        ],
      },
      // 转让表单校验
      cedeRules: {
        ownerValue: [
          { required: true, message: "请选择业户姓名", trigger: "change" },
        ],
        ownerCarValueId: [
          { required: true, message: "请选择业户车辆", trigger: "change" },
        ],
      },
      cedeBindRules: {
       
        ownerValue: [
          { required: true, message: "请选择业户姓名", trigger: "blur" },
        ],
        plateNo: [
          { required: true, message: "请选择车牌号", trigger: "blur" },
        ],
        vehicleBrand: [
          { required: true, message: "车辆品牌不为空", trigger: "blur" },
        ],
        vehicleType: [
          { required: true, message: "车辆类型不为空", trigger: "blur" },
        ],
        vehicleColor: [
          { required: true, message: "请选择车辆颜色", trigger: "blur" },
        ],
      },
    };
  },

  created() {
    this.getDict();
    // 机构下拉框
    this.getTypeList();
    // 列表查询
    // this.getBlackList();
  },

  computed: {
    viewDeptData: function () {
      let search = this.search;
      let viewDeptData = this.deptData;
      if (search) {
        viewDeptData = this.deptData.filter((dataNews) => {
          return dataNews.communityName.indexOf(search) !== -1;
        });
      }
      return viewDeptData;
    },
  },

  // watch: {
  //   carportNature() {
  //     console.log(1);
  //   },
  // },

  methods: {
    
    as(row){
      return this.selectDictLabel(this.dict.type.carport_all_status,row.allStatus);
    },
    /** 导入按钮操作 */
    handleImport() {
     
     this.uploadImprot.title = "车位信息导入";
     this.uploadImprot.open = true;
   },
   /** 下载模板操作 */
   importTemplate() {
     this.download(
       "estate/carport/importTemplate",
       {},
       `车位信息导入模板_${new Date().getTime()}.xlsx`
     );
    },
   // 文件上传中处理
   handleFileUploadProgress(event, file, fileList) {
     this.uploadImprot.isUploading = true;
   },
   // 文件上传成功处理
   handleFileSuccess(response, file, fileList) {
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
     this.getBlackList();
   },
   // 提交上传文件
   submitFileForm() {
     this.$refs.uploadImprot.submit();
   },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getBlackList();
    },
    // 查字典表数据
    getDict() {
      this.getDicts("sys_vehicle_color").then((response) => {
        this.carColour = response.data;
      });
      // 车牌颜色
      this.getDicts("sys_plate_color").then((response) => {
        this.plateColorList = response.data;
      });
      // 车牌类型
      this.getDicts("sys_plate_type").then((response) => {
        this.plateTypeList = response.data;
      });
    },
    /** 重置按钮操作 */
    resetQuery() {
      // this.queryParams.dropDownForm = null;
      this.searchTime = "";
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 查询岗位列表 */
    getBlackList() {
      this.loading = false;
      listPage(this.queryParams).then((response) => {
        this.injList = response.rows;
        this.total = response.total;
        this.getCarportList(this.queryParams.communityId)
      });
    },
    getCarportNo(carportNo) { 
      var obj = this.carportNoList.filter(item => item.carportNoId + '' == carportNo)[0];
      if (obj) { 
        return obj.carportNo;
      }
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.carportId);
      this.multiple = !selection.length;
    },

    // 表单重置
    reset() {
      this.form = {};
      this.resetForm("form");
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.showDetail = false;
      this.homeShow = true;
      this.getBlackList();
      //this.reset();
    },

    selectView(row) {
      let params = {
        carportId: row.carportId,
      };
      getDetail(params).then((response) => {
        this.detailForm = response.data;
        this.open = true;
        if (this.detailForm.rentalMode == "售") {
          this.showDetailTime = false;
        } else {
          this.showDetailTime = true;
        }
      });
      getDetailList(params).then((response) => {
        this.ownerListData = response.data;
        this.open = true;
      });
    },

    /** 详情 */
    detail(row) {
      // row.carportId
      this.showDetail = true;
      this.homeShow = false;
      this.selectView(row);
    },

    // 转让
    cede(row) {
      // 打开弹窗
      this.cedeOpen = true;
      this.cedeForm.communityName = row.communityName;
      this.cedeForm.carportNo =this.getCarportNo(row.carportNo);
      this.cedeForm.rentalTypeName = row.rentalTypeName;
      this.cedeForm.rentalEnd = row.rentalEnd;
      this.cedeForm.accountNames = row.accountNames;
      this.cedeForm.loginPhone = row.loginPhone;
      this.cedeForm.carportId = row.carportId;
      this.cedeForm.cardNos = row.cardNos;
      // 根据所属小区ID查所属楼栋
      this.selectFloor();
    },

    // 绑定车辆信息
    cedeBind(row) {
      // 打开弹窗
      this.cedeBindOpen = true;
      this.cedeBindForm.communityId = row.communityId;
      this.cedeBindForm.carportId =row.carportId;
      this.cedeBindForm.accountId =row.accountId;
      
      getByAccountIdVehicleList(row.communityId, row.accountId).then(res => { 
        this.plateNoOptions = res.data;
      })
    },
    

    // 机构下拉框
    getTypeList() {
      getCurrentList().then((response) => {
        this.deptData = response.data;
        this.queryParams.communityId = this.deptData[0].communityId;
        // this.queryParams.communityName = this.deptData[0].communityName;
        this.$refs.singleTable.setCurrentRow(this.deptData[0]);
        this.addForm.communityId = this.deptData[0].communityId;
        this.addForm.communityName = this.deptData[0].communityName;
        this.communityIds = this.deptData[0].communityId;
        //查询房屋信息信息
        this.getCarportList(this.queryParams.communityId);
        this.getBlackList();
      });
    },
    getCarportList(communityId) {
      getCommunityCarportList(communityId).then(res => {
          this.carportNoList=res.data;
      })
    },

    // 单元格点击时查列表
    listClick(row) {
      this.queryParams.communityId = row.communityId;
      // // 点击单元格时赋值所属小区
      this.addForm.communityId = row.communityId;
      this.addForm.communityName = row.communityName;
      this.communityIds = row.communityId;
      this.getBlackList();
    },

    // 根据小区ID查楼栋
    selectFloor() {
      getBuildingList(this.communityIds).then((response) => {
        this.buildingData = response.data;
      });
    },

    // 根据楼栋查单元
    buildingChange(buildingId) {
      if (buildingId != "") {
        this.unitValue = null;
        this.roomValue = null;
        this.ownerPhones = null;
        this.cedeForm.ownerCarValueId = null;
        this.cedeBindForm.ownerCarValueId = null;
        this.cedeForm.ownerValue = null;
        this.cedeBindForm.ownerValue = null;
        // 给addForm赋值
        this.cedeForm.buildingId = buildingId;
        this.cedeBindForm.buildingId = buildingId;
        getUnitList(buildingId).then((response) => {
          this.unitData = response.data;
        });
      } else {
        this.buildingValue = null;
        this.unitValue = null;
        this.roomValue = null;
        this.ownerPhones = null;
        this.cedeForm.ownerCarValueId = null;
        this.cedeForm.ownerValue = null;
        this.cedeBindForm.ownerCarValueId = null;
        this.cedeBindForm.ownerValue = null;
      }
    },

    // 根据单元查房号
    unitChange(unitId) {
      if (unitId != "") {
        this.roomValue = null;
        this.ownerPhones = null;
        this.cedeForm.ownerCarValueId = null;
        this.cedeForm.ownerValue = null;
        this.cedeBindForm.ownerCarValueId = null;
        this.cedeBindForm.ownerValue = null;
        // 给addForm赋值
        this.cedeForm.unitId = unitId;
        this.cedeBindForm.unitId = unitId;
        getRoomList(unitId).then((response) => {
          this.roomData = response.data;
        });
      } else {
        this.unitValue = null;
        this.roomValue = null;
        this.ownerPhones = null;
        this.cedeForm.ownerCarValueId = null;
        this.cedeForm.ownerValue = null;
        this.cedeBindForm.ownerCarValueId = null;
        this.cedeBindForm.ownerValue = null;
      }
    },

    // 根据房号查业户姓名
    roomChange(roomId) {
      if (roomId != "") {
        this.ownerPhones = null;
        this.cedeForm.ownerCarValueId = null;
        this.cedeForm.ownerValue = null;
        this.cedeBindForm.ownerCarValueId = null;
        this.cedeBindForm.ownerValue = null;
        // 给addForm赋值
        this.cedeForm.roomId = roomId;
        this.cedeBindForm.roomId = roomId;
        let params = {
          roomId: roomId,
        };
        getOwnerList(params).then((response) => {
          this.ownerData = response.data;
        });
      } else {
        this.roomValue = null;
        this.ownerPhones = null;
        this.cedeForm.ownerCarValueId = null;
        this.cedeForm.ownerValue = null;
        this.cedeBindForm.ownerCarValueId = null;
        this.cedeBindForm.ownerValue = null;
      }
    },

    // 改变业户姓名时
    ownerChange(ownerId) {
      if (ownerId != "") {
        this.ownerPhones = null;
        this.cedeForm.ownerCarValueId = null;
        this.cedeForm.ownerId = ownerId;
        //查找业主手机号
        this.selectOwnerName(ownerId);
        // 查找业主车辆
        this.selectOwnerCar(ownerId);
      } else {
        this.ownerPhones = null;
        this.cedeForm.ownerCarValueId = null;
        this.cedeForm.ownerValue = null;
      }
    },

    // 业户车辆改变事件
    ownerCarChange(vehicleId) {
      //车辆ID
      this.cedeForm.vehicleId = vehicleId;
    },

    //查找业主手机号
    selectOwnerName(ownerId) {
      // 查联系方式
      let params = {
        ownerId: ownerId,
      };
      selectOwnerName(params).then((response) => {
        this.ownerPhones = response.data.loginPhone;
        this.cedeForm.accountName = response.data.ownerName;

        this.cedeForm.cardNo = response.data.cardNo;
        this.cedeForm.loginPhone = this.ownerPhones;
      });
    },

    //查找业主车辆
    selectOwnerCar(ownerId) {
      // 查联系方式
      let params = {
        ownerId: ownerId,
      };
      selectOwnerCar(params).then((response) => {
        this.ownerCarData = response.data;
      });
    },

    // 新增按钮
    handleAdd() {},

    //删除按钮
    handleDelete(row) {
      this.$modal
        .confirm("是否确认删除选中的数据项？")
        .then(() => {
          removes(row.carportId).then(() => {
            this.getBlackList();
            this.$modal.msgSuccess("删除成功");
          });
        })
    },

    // 打开新增弹窗
    addMethod() {
      this.carportIds = null;
      this.addOpen = true;
      this.title = "新增车位";
    },

    // 关闭新增弹窗
    addClose() {
      this.$refs.addForm.resetFields();
      this.addOpen = false;
      this.getBlackList();
      this.addFormEmpty();
    },

    //关闭转让弹窗
    cedeClose() {
      this.$refs.cedeForm.resetFields();
      this.cedeOpen = false;
      this.getBlackList();
      this.buildingValue = "";
      this.unitValue = "";
      this.roomValue = "";
      this.ownerPhones = null;
    },
    
    //关闭绑定车辆信息弹窗
    cedeBindClose() {
      this.$refs.cedeBindForm.resetFields();
      this.cedeBindOpen = false;
      this.getBlackList();
      this.buildingValue = "";
      this.unitValue = "";
      this.roomValue = "";
      this.ownerPhones = null;
    },

    // 转出确定按钮
    cedeSuccess(cedeForm) {
      this.$refs.cedeForm.validate((valid) => {
        if (valid) {
          this.$modal
            .confirm("是否确认转让？")
            .then(() => {
              cedeSubmit(this.cedeForm).then(() => {});
            })
            .then(() => {
              this.getBlackList();
              this.cedeOpen = false;
              this.buildingValue = "";
              this.unitValue = "";
              this.roomValue = "";
              this.ownerPhones = null;
              this.$modal.msgSuccess("转让成功");
            })
            .catch(() => {});
        } else {
          return false;
        }
      });
    },
    // 绑定车辆确定按钮
    cedeBindSuccess() {
      this.$refs.cedeBindForm.validate((valid) => {
        if (valid) {
          bindVehicle(this.cedeBindForm).then(res => { 
              this.getBlackList();
              this.cedeBindOpen = false;
              this.buildingValue = "";
              this.unitValue = "";
              this.roomValue = "";
              this.ownerPhones = null;
              this.$modal.msgSuccess("绑定车辆信息成功");
          })
        } else {
          return false;
        }
      });
    },

    // 修改
    update(row) {
      this.carportIds = row.carportId;
      this.addOpen = true;
      this.updateDetails(row.carportId);
      this.addForm.carportId = row.carportId;
      this.title = "修改车位";
    },

    // 编辑页面详情展示
    updateDetails(carportId) {
      let params = {
        carportId: carportId,
      };
      updateDetail(params).then((response) => {
        this.addForm = response.data;
        this.addForm.carportNo = this.getCarportNo(this.addForm.carportNo);
        //this.carportNatureChange(this.addForm.carportNature,'1');
      });
    },

    // 提交
    addSuccess() {
      if (!this.addForm.carportNo) { 
        this.$modal.msgWarning("请输入车位编号");
        return;
      }
      if (!this.addForm.carportNature) {
        this.$modal.msgWarning("请选择车位属性");
        return;
      }
      
      this.addForm.carportId = this.carportIds;
          this.$confirm("是否确定提交?", "提示", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning",
          })
            .then(() => {
              saveOrUpdate(this.addForm).then((response) => {
                this.form = response.data;
                if (response.data.code != 200) {
                  this.$modal.msgError(response.data.msg);
                } else {
                  this.$modal.msgSuccess(response.data.msg);
                  this.addFormEmpty();
                  this.getBlackList();
                  this.addOpen = false;
                }
              });
             
            })
            .catch(() => {
              this.$message({
                type: "info",
                message: "已取消提交",
              });
            });
    },

    // 更改车位状态
    handleStatusChange(row) {
      let params = {
        carportId: row.carportId,
        carportStatus: row.carportStatus,
      };
      let text = row.carportStatus === "0" ? "开启" : "关闭";
      this.$modal
        .confirm("确认要修改车位 " + row.carportNo + " 为 " + text + " 吗？")
        .then(function () {
          changeCarportStatus(params).then(() => {});
        })
        .then(() => {
          this.$modal.msgSuccess(text + "成功");
          this.getBlackList();
        })
        .catch(function () {
          row.carportStatus = row.carportStatus === "0" ? "1" : "0";
        });
    },

    // 清空addForm表单
    addFormEmpty() {
      this.addForm.carportNo = null;
      this.addForm.carportNature = null;
      this.addForm.rentalType = null;
      this.addForm.carportFee = null;
      this.disRentalType = 'disabled';
    },

    /** 手机号脱敏 */
    phoneFormatter(phone) {
      if (!equals(phone + "", undefined)) {
        return desensitization(phone + "", 3, 7);
      }
    },

    /** 身份证号脱敏 */
    cardFormatter(card) {
      if (!equals(card, undefined)) {
        return desensitization(card, 6, 14);
      }
    },

    carportChange(row) {
      // console.log(row);
      // console.log(this.addForm.carportNature);
      /*if(!this.addForm.carportNature){
        this.$message({
          type: "info",
          message: "请先选择车位属性",
        });
        return;
      }*/

    },

    carportNatureChange(carportNature,flag) {
      // if (flag == '0') { 
      //   this.addForm.rentalType = "";
      // }
      
      // if(isEmpty(carportNature)){
      //   this.disRentalType = 'disabled';
      // }else {
      //   this.disRentalType = false;
      // }
      // this.disType = false;
      // if (carportNature == '1') {
      //   this.disType = true;
      // }
      this.$forceUpdate();
    },
  },
};
</script>
<style scoped>
</style>

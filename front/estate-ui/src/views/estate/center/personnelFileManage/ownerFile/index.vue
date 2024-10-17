<template>
  <div class="app-container">
    <!-- list-->
    <div v-show="isList">
      <el-row style="margin-top: 20px; margin-left: 15px">
        <el-col :span="4">
          <el-input placeholder="请输入小区名称" v-model="filterText">
          </el-input>
          <div class="head-container">
            <el-tree
              class="filter-tree hide-tree"
              :data="data"
              :props="{ children: '' }"
              style="margin-top: 10px"
              :highlight-current="true"
              node-key="id"
              :filter-node-method="filterNode"
              :expand-on-click-node="false"
              :current-node-key="defaultId"
              @node-click="getCommunityId"
              ref="tree"
            >
              <!-- 插入楼栋图标 -->
              <span slot-scope="{ node }">
                <i class="el-icon-office-building" />
                <span style="padding-left: 5px; font-size: 14px">{{
                  node.label
                }}</span>
              </span>
            </el-tree>
          </div>
        </el-col>
        <el-col style="margin-left: 15px" :span="19">
          <el-row>
            <el-form
              :model="queryParams"
              ref="queryForm"
              size="small"
              :inline="true"
              v-show="showSearch"
            >
              <el-form-item label="业主姓名" prop="name">
                <el-input
                  v-model="queryParams.name"
                  placeholder="请输入业主姓名"
                  style="width: 160px"
                  clearable
                />
              </el-form-item>
              <el-form-item label="手机号码" prop="phone">
                <el-input
                  v-model="queryParams.phone"
                  placeholder="请输入手机号码"
                  style="width: 160px"
                  clearable
                />
              </el-form-item>
              <el-form-item label="是否有车" prop="isCar">
                <el-select
                  v-model="queryParams.isCar"
                  placeholder="请选择"
                  style="width: 120px"
                  clearable
                >
                  <el-option
                    v-for="i in inout"
                    :key="i.value"
                    :value="i.value"
                    :label="i.label"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="是否出租" prop="isOutIn">
                <el-select
                  v-model="queryParams.isOutIn"
                  placeholder="请选择"
                  style="width: 120px"
                  clearable
                >
                  <el-option
                    v-for="i in inout"
                    :key="i.value"
                    :value="i.value"
                    :label="i.label"
                  />
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
                <el-button
                  icon="el-icon-refresh"
                  size="mini"
                  @click="resetQuery"
                  >重置</el-button
                >
                <el-button
                  icon="el-icon-check"
                  size="mini"
                  type="warning"
                  plain
                  v-hasPermi="['center:ownerfile:auth']"
                  @click="handleAuth"
                  >门禁授权</el-button
                >
                <!--                    <el-button icon="el-icon-check" size="mini" type="warning" plain-->
                <!--                               v-hasPermi="['center:ownerfile:auth']" @click="$message.success('演示模式')">门禁授权</el-button>-->
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
                >新增</el-button
              >
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="danger"
                plain
                icon="el-icon-delete"
                size="mini"
                @click="handleDeletes"
                :disabled="multiple"
                >删除</el-button
              >
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="info"
                plain
                icon="el-icon-upload2"
                size="mini"
                @click="handleImport"
                >导入</el-button
              >
            </el-col>
            <right-toolbar
              :showSearch.sync="showSearch"
              @queryTable="getList"
            />
          </el-row>
          <el-table
            v-loading="loading"
            :data="dataList"
            @selection-change="handleSelectionChange"
            ref="multipleTable"
          >
            <el-table-column
              type="selection"
              width="50"
              align="center"
              :selectable="selectEnable"
            />
            <el-table-column label="业主姓名" align="center" prop="ownerName" />
            <el-table-column
              label="手机号码/账号"
              align="center"
              prop="ownerPhone"
            />
            <el-table-column label="证件号码" align="center" prop="cardNo" />
            <el-table-column label="性别" align="center">
              <template slot-scope="scope">
                {{
                  scope.row.gender == "0"
                    ? "男"
                    : scope.row.gender == "1"
                    ? "女"
                    : ""
                }}
              </template>
            </el-table-column>
            <el-table-column label="提交方式" align="center">
              <template slot-scope="scope">
                {{
                  scope.row.dataSource == "0"
                    ? "物业注册"
                    : scope.row.dataSource == "1"
                    ? "手机注册"
                    : ""
                }}
              </template>
            </el-table-column>
            <el-table-column label="是否有车" align="center">
              <template slot-scope="scope">
                <el-tag type="success" v-show="scope.row.carNum > 0">是</el-tag>
                <el-tag type="danger" v-show="scope.row.carNum == 0">否</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="是否绑定房产" align="center">
              <template slot-scope="scope">
                <el-tag type="success" v-show="scope.row.roomSpace > 0"
                  >是</el-tag
                >
                <el-tag type="danger" v-show="scope.row.roomSpace == 0"
                  >否</el-tag
                >
              </template>
            </el-table-column>
            <el-table-column label="是否出租房屋" align="center">
              <template slot-scope="scope">
                <el-tag type="success" v-show="scope.row.space > 0">是</el-tag>
                <el-tag type="danger" v-show="scope.row.space == 0">否</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="最后操作时间" align="center">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.updateTime) }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="最后操作人"
              align="center"
              prop="updateBy"
            />
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
                  @click="view(scope.row)"
                  >查看</el-button
                >
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="up(scope.row)"
                  >修改</el-button
                >
                <br v-if="scope.row.isShowDel == 0" />
                <el-button
                  size="mini"
                  v-if="scope.row.isShowDel == 0"
                  type="text"
                  icon="el-icon-delete"
                  style="color: #f56c6c"
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
        </el-col>
      </el-row>
    </div>

    <!-- 新增/修改房屋 -->
    <el-dialog :title="title" :visible.sync="isAdd" width="90%" append-to-body>
      <el-form
        ref="updateRoomForm"
        :model="updateRoomForm"
        :rules="rules"
        label-width="110px"
      >
        <h4 class="form-header">房屋信息</h4>
        <el-row>
          <el-col :span="6">
            <el-form-item label="所属小区" prop="communityName">
              <el-input
                v-model="updateRoomForm.communityName"
                :disabled="true"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="所属楼栋" prop="buildingName">
              <el-select
                :disabled="isDisabled"
                v-model="updateRoomForm.buildingName"
                placeholder="请选择所属楼宇"
                clearable
                @change="getUnitList"
              >
                <el-option
                  v-for="i in BuildingList"
                  :key="i.buildingId"
                  :value="i.buildingId"
                  :label="i.buildingName"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="所属单元" prop="unitName">
              <el-select
                :disabled="isDisabled"
                v-model="updateRoomForm.unitName"
                placeholder="请选择所属单元"
                clearable
                @change="getRoomList"
              >
                <el-option
                  v-for="i in unitList"
                  :key="i.unitId"
                  :value="i.unitId"
                  :label="i.unitName"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="房号" prop="roomName">
              <el-select
                :disabled="isDisabled"
                v-model="updateRoomForm.roomName"
                placeholder="请选择所属房间"
                clearable
                @change="getRoomState"
              >
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
          <el-col :span="6">
            <el-form-item label="房屋坐落" prop="houseAddress">
              <el-input
                v-model="updateRoomForm.houseAddress"
                placeholder="请输入房屋坐落"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="房屋属性" prop="roomType">
              <el-select
                style="width: 100%"
                v-model="updateRoomForm.roomType"
                placeholder="房屋性质"
                clearable
              >
                <el-option
                  v-for="status in defaultRoomType"
                  :key="status.dictValue"
                  :label="status.dictLabel"
                  :value="status.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="交房时间" prop="deliveryTime">
              <el-date-picker
                v-model="updateRoomForm.deliveryTime"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="请选择交房时间"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="房间总面积" prop="totalArea">
              <el-input
                v-model="updateRoomForm.totalArea"
                placeholder="请输入房间总面积"
              >
                <i slot="suffix" class="el-input__icon">㎡</i>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="20">
            <el-form-item
              label="房产证明"
              prop="licenseUrl"
              label-width="110px"
            >
              <image-upload
                :isShowTip="!isDisabled"
                v-model="updateRoomForm.licenseUrl"
                :limit="3"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <h4 class="form-header">业主信息</h4>

        <el-row>
          <el-col :span="8">
            <el-form-item label="姓名" prop="ownerName" label-width="110px">
              <el-input
                @input="(val) => ownerNameInput(val)"
                v-model="updateRoomForm.ownerName"
                placeholder="请输入姓名"
                style="width: 216px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="性别" prop="gender" label-width="110px">
              <el-select
                @change="(val) => genderInput(val)"
                v-model="updateRoomForm.gender"
                placeholder="请选择性别"
                clearable
                style="width: 216px"
              >
                <el-option label="男" value="0" />
                <el-option label="女" value="1" />
                <el-option label="未知" value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="手机号" prop="ownerPhone" label-width="110px">
              <el-input
                @input="(val) => ownerPhoneInput(val)"
                :disabled="isDisabled"
                v-model="updateRoomForm.ownerPhone"
                placeholder="请输入手机号"
                style="width: 216px"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="证件类型" prop="cardType" label-width="110px">
              <el-select
                @change="(val) => cardTypeInput(val)"
                :disabled="isDisabled"
                v-model="updateRoomForm.cardType"
                placeholder="请选择证件类型"
                clearable
                style="width: 216px"
              >
                <el-option
                  v-for="d in cardTypeList"
                  :key="d.dictValue"
                  :label="d.dictLabel"
                  :value="d.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item label="证件号码" prop="cardNo" label-width="110px">
              <el-input
                @input="(val) => cardNoInput(val)"
                :disabled="isDisabled"
                v-model="updateRoomForm.cardNo"
                placeholder="请输入证件号码"
                style="width: 216px"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item
              label="身份证正面"
              prop="cardFont"
              label-width="110px"
            >
              <image-upload
                :isShowTip="!isDisabled"
                v-model="updateRoomForm.cardFont"
                :limit="1"
              />
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item
              label="身份证反面"
              prop="cardBack"
              label-width="110px"
            >
              <image-upload
                :isShowTip="!isDisabled"
                v-model="updateRoomForm.cardBack"
                :limit="1"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item
              label="与户主关系"
              prop="ownerRelation"
              label-width="110px"
            >
              <el-select
                @change="(val) => ownerRelationInput(val)"
                v-model="updateRoomForm.ownerRelation"
                placeholder="请选择与户主关系"
                clearable
              >
                <el-option
                  v-for="d in relationList"
                  :key="d.value"
                  :label="d.label"
                  :value="d.value"
                  :disable="d.isRead == '1'"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item
              label="政治面貌"
              prop="politicsStatus"
              label-width="110px"
            >
              <el-select
                @change="(val) => politicsStatusInput(val)"
                v-model="updateRoomForm.politicsStatus"
                placeholder="请选择政治面貌"
                clearable
              >
                <el-option
                  v-for="d in politicsStatus"
                  :key="d.value"
                  :label="d.label"
                  :value="d.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="宗教信仰" prop="religion" label-width="110px">
              <el-input
                style="width: 220px"
                v-model="updateRoomForm.religion"
                placeholder="请输入宗教信仰"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item
              label="紧急联系人"
              prop="emergencyContact"
              label-width="110px"
            >
              <el-input
                @input="(val) => emergencyContactInput(val)"
                v-model="updateRoomForm.emergencyContact"
                placeholder="请输入紧急联系人"
                style="width: 216px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item
              label="紧急联系人电话"
              prop="emergencyContactPhone"
              label-width="110px"
            >
              <el-input
                @input="(val) => emergencyContactPhoneInput(val)"
                v-model="updateRoomForm.emergencyContactPhone"
                placeholder="请输入紧急联系人电话"
                style="width: 216px"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="备注" prop="selfIntro" label-width="110px">
              <el-input
                @input="(val) => selfIntroInput(val)"
                type="textarea"
                v-model="updateRoomForm.selfIntro"
                placeholder="请输入备注"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitUpdateRoomForm"
          >确 定</el-button
        >
        <el-button @click="closer">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 查看房屋 -->
    <el-dialog :title="title" :visible.sync="isView" width="90%" append-to-body>
      <el-form disabled :model="updateRoomForm" label-width="110px">
        <h4 class="form-header">房屋信息</h4>
        <el-row>
          <el-col :span="6">
            <el-form-item label="所属小区" prop="communityName">
              <el-input
                v-model="updateRoomForm.communityName"
                :disabled="true"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="所属楼栋" prop="buildingName">
              <el-select
                :disabled="isDisabled"
                v-model="updateRoomForm.buildingName"
                placeholder="请选择所属楼宇"
                clearable
                @change="getUnitList"
              >
                <el-option
                  v-for="i in BuildingList"
                  :key="i.buildingId"
                  :value="i.buildingId"
                  :label="i.buildingName"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="所属单元" prop="unitName">
              <el-select
                :disabled="isDisabled"
                v-model="updateRoomForm.unitName"
                placeholder="请选择所属单元"
                clearable
                @change="getRoomList"
              >
                <el-option
                  v-for="i in unitList"
                  :key="i.unitId"
                  :value="i.unitId"
                  :label="i.unitName"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="房号" prop="roomName">
              <el-select
                :disabled="isDisabled"
                v-model="updateRoomForm.roomName"
                placeholder="请选择所属房间"
                clearable
                @change="getRoomState"
              >
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
          <el-col :span="6">
            <el-form-item label="房屋坐落" prop="houseAddress">
              <el-input
                v-model="updateRoomForm.houseAddress"
                placeholder="请输入房屋坐落"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="房屋属性" prop="roomType">
              <el-select
                style="width: 100%"
                v-model="updateRoomForm.roomType"
                placeholder="房屋性质"
                clearable
              >
                <el-option
                  v-for="status in defaultRoomType"
                  :key="status.dictValue"
                  :label="status.dictLabel"
                  :value="status.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="交房时间" prop="deliveryTime">
              <el-date-picker
                v-model="updateRoomForm.deliveryTime"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="请选择交房时间"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6">
            <el-form-item label="房间总面积" prop="totalArea">
              <el-input
                v-model="updateRoomForm.totalArea"
                placeholder="请输入房间总面积"
              >
                <i slot="suffix" class="el-input__icon">㎡</i>
              </el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="20">
            <el-form-item
              label="房产证明"
              prop="licenseUrl"
              label-width="110px"
            >
              <image-upload
                :isShowTip="!isDisabled"
                v-model="updateRoomForm.licenseUrl"
                :limit="3"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <h4 class="form-header">业主信息</h4>

        <el-row>
          <el-col :span="8">
            <el-form-item label="姓名" prop="ownerName" label-width="110px">
              <el-input
                @input="(val) => ownerNameInput(val)"
                v-model="updateRoomForm.ownerName"
                placeholder="请输入姓名"
                style="width: 216px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="性别" prop="gender" label-width="110px">
              <el-select
                @change="(val) => genderInput(val)"
                v-model="updateRoomForm.gender"
                placeholder="请选择性别"
                clearable
                style="width: 216px"
              >
                <el-option label="男" value="0" />
                <el-option label="女" value="1" />
                <el-option label="未知" value="2" />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="手机号" prop="ownerPhone" label-width="110px">
              <el-input
                @input="(val) => ownerPhoneInput(val)"
                :disabled="isDisabled"
                v-model="updateRoomForm.ownerPhone"
                placeholder="请输入手机号"
                style="width: 216px"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="证件类型" prop="cardType" label-width="110px">
              <el-select
                @change="(val) => cardTypeInput(val)"
                :disabled="isDisabled"
                v-model="updateRoomForm.cardType"
                placeholder="请选择证件类型"
                clearable
                style="width: 216px"
              >
                <el-option
                  v-for="d in cardTypeList"
                  :key="d.dictValue"
                  :label="d.dictLabel"
                  :value="d.dictValue"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item label="证件号码" prop="cardNo" label-width="110px">
              <el-input
                @input="(val) => cardNoInput(val)"
                :disabled="isDisabled"
                v-model="updateRoomForm.cardNo"
                placeholder="请输入证件号码"
                style="width: 216px"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item
              label="身份证正面"
              prop="cardFont"
              label-width="110px"
            >
              <image-upload
                :isShowTip="!isDisabled"
                v-model="updateRoomForm.cardFont"
                :limit="1"
              />
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item
              label="身份证反面"
              prop="cardBack"
              label-width="110px"
            >
              <image-upload
                :isShowTip="!isDisabled"
                v-model="updateRoomForm.cardBack"
                :limit="1"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item
              label="与户主关系"
              prop="ownerRelation"
              label-width="110px"
            >
              <el-select
                @change="(val) => ownerRelationInput(val)"
                v-model="updateRoomForm.ownerRelation"
                placeholder="请选择与户主关系"
                clearable
              >
                <el-option
                  v-for="d in relationList"
                  :key="d.value"
                  :label="d.label"
                  :value="d.value"
                  :disable="d.isRead == '1'"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item
              label="政治面貌"
              prop="politicsStatus"
              label-width="110px"
            >
              <el-select
                @change="(val) => politicsStatusInput(val)"
                v-model="updateRoomForm.politicsStatus"
                placeholder="请选择政治面貌"
                clearable
              >
                <el-option
                  v-for="d in politicsStatus"
                  :key="d.value"
                  :label="d.label"
                  :value="d.value"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="宗教信仰" prop="religion" label-width="110px">
              <el-input
                style="width: 220px"
                v-model="updateRoomForm.religion"
                placeholder="请输入宗教信仰"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item
              label="紧急联系人"
              prop="emergencyContact"
              label-width="110px"
            >
              <el-input
                @input="(val) => emergencyContactInput(val)"
                v-model="updateRoomForm.emergencyContact"
                placeholder="请输入紧急联系人"
                style="width: 216px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="14">
            <el-form-item
              label="紧急联系人电话"
              prop="emergencyContactPhone"
              label-width="110px"
            >
              <el-input
                @input="(val) => emergencyContactPhoneInput(val)"
                v-model="updateRoomForm.emergencyContactPhone"
                placeholder="请输入紧急联系人电话"
                style="width: 216px"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="10">
            <el-form-item label="备注" prop="selfIntro" label-width="110px">
              <el-input
                @input="(val) => selfIntroInput(val)"
                type="textarea"
                v-model="updateRoomForm.selfIntro"
                placeholder="请输入备注"
              />
            </el-form-item>
          </el-col>
        </el-row>

        <h4 class="form-header">车位信息</h4>
        <div v-for="(item1, index1) in spaceVehicleList" :key="index1">
          <h4 class="form-header">第{{ index1 + 1 }}条车辆/车位信息</h4>

          <el-row>
            <el-col :span="8">
              <el-form-item
                label="车位编号"
                prop="carportNo"
                label-width="110px"
              >
                <el-select
                  filterable
                  v-model="item1.carportNo"
                  placeholder="请选择车位编号"
                  clearable
                  style="width: 216px"
                  @change="getCarport(item1)"
                >
                  <el-option
                    v-for="d in carportNoList"
                    :key="d.carportNoId + ''"
                    :label="d.carportNo"
                    :value="d.carportNoId + ''"
                    :disabled="d.isCarport >= 1"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                label="车位属性"
                prop="carportNature"
                label-width="110px"
              >
                {{ getCarportNature(item1.carportNature) }}
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                label="租售类型"
                prop="rentalType"
                label-width="110px"
              >
                {{ getRentalTypeName(item1.rentalType) }}
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="8">
              <el-form-item label="金额" prop="carportFee" label-width="110px">
                <el-input-number
                  v-model="item1.carportFee"
                  :min="0"
                  :max="999999"
                  :precision="2"
                  :step="0.1"
                  style="width: 216px"
                ></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="8" v-if="item1.rentalType != '0'">
              <el-form-item
                label="租赁开始时间"
                prop="rentalStart"
                label-width="110px"
              >
                <el-date-picker
                  v-model="item1.rentalStart"
                  value-format="yyyy-MM-dd"
                  placeholder="请选择开始时间"
                  type="date"
                ></el-date-picker>
              </el-form-item>
            </el-col>
            <el-col :span="8" v-if="item1.rentalType != '0'">
              <el-form-item
                label="租赁结束时间"
                prop="rentalEnd"
                label-width="110px"
              >
                <el-date-picker
                  v-model="item1.rentalEnd"
                  value-format="yyyy-MM-dd"
                  placeholder="请选择结束时间"
                  type="date"
                ></el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <h4 class="form-header">车辆信息</h4>
          <el-row>
            <el-col :span="8">
              <el-form-item label="车牌号" prop="plateNo" label-width="110px">
                <el-input
                  v-model="item1.plateNo"
                  placeholder="请输入车牌号"
                  style="width: 216px"
                />
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item
                label="车辆颜色"
                prop="plateColor"
                label-width="110px"
              >
                <el-select
                  v-model="item1.plateColor"
                  placeholder="请选择车辆颜色"
                  clearable
                >
                  <el-option
                    v-for="d in plateColorList"
                    :key="d.dictValue"
                    :label="d.dictLabel"
                    :value="d.dictValue"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                label="车辆类型"
                prop="vehicleType"
                label-width="110px"
              >
                <el-select
                  v-model="item1.vehicleType"
                  placeholder="请选择车辆颜色"
                  clearable
                >
                  <el-option
                    v-for="d in vehicleTypeList"
                    :key="d.dictValue"
                    :label="d.dictLabel"
                    :value="d.dictValue"
                  />
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item
                label="车辆品牌"
                prop="vehicleBrand"
                label-width="110px"
              >
                <el-input
                  v-model="item1.vehicleBrand"
                  placeholder="请输入车辆品牌"
                  style="width: 216px"
                />
              </el-form-item>
            </el-col>
          </el-row>
        </div>
        <el-row> </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="viewCloser">取 消</el-button>
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
        :action="uploadImprot.url + '?communityId=' + this.communityId"
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
    <!-- 可用设备 -->
    <el-dialog
      title="人员授权"
      :visible.sync="showDevice"
      width="1000px"
      append-to-body
    >
      <el-row>
        <el-col :span="8" class="dev-col">
          <h4 class="form-header">业主信息</h4>
          <el-table
            :data="ownerTempList"
            @selection-change="handleSelectionChangeOwner"
          >
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="业主姓名" align="center" prop="ownerName" />
            <el-table-column label="手机号" align="center" prop="ownerPhone" />
          </el-table>
        </el-col>
        <el-col :span="15" :offset="1" class="dev-col">
          <h4 class="form-header">设备信息</h4>
          <el-table
            :data="deviceList"
            @selection-change="handleSelectionChangeDevice"
          >
            <el-table-column type="selection" width="55" align="center" />
            <el-table-column label="可用设备名称" align="center" prop="name" />
            <!--        <el-table-column label="设备类型" align="center" prop="resourceType"/>-->
            <el-table-column
              label="设备唯一编码"
              align="center"
              prop="indexCode"
              :show-overflow-tooltip="true"
            />
            <el-table-column label="设备状态" align="center" prop="online">
              <template slot-scope="scope">
                <span v-if="scope.row.online == 0">离线</span>
                <span v-else-if="scope.row.online == 1">在线</span>
                <span v-else>未检测</span>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
      <div slot="footer" class="dialog-footer">
        <el-row :gutter="10" class="mb8">
          <el-col :span="1.5" style="float: right">
            <el-button type="primary" plain @click="authSubmit"
              >确 定</el-button
            >
          </el-col>
          <el-col :span="1.5" style="float: right">
            <el-button @click="cancel">取 消 </el-button>
          </el-col>
        </el-row>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  getCurrentList,
  getBuildingList,
  getUnitList,
  getRoomList,
} from "@/api/estate/community";
import { desensitization } from "@/utils/common";
import { getDicts } from "@/api/system/dict/data";
import {
  query,
  add,
  update,
  deleted,
  deleteds,
  upload,
  queryByRoomId,
  queryRoomByOwnerId,
  queryCarByOwnerId,
  queryTenantByOwnerId,
  queryCarPortByOwnerId,
  getDevice,
  authOwner,
  getDetailByOwnerId,
  getByRoomIdOwnerRelationCount,
} from "@/api/estate/center/personnelFileManage/ownerFile";
import {
  getCommunityCarportList,
  updateDetail,
} from "@/api/estate/center/buildingManage/carPositionManage/carPositionManage";
import { getToken } from "@/utils/auth";
import { getRoomDetail, updateRoom } from "@/api/estate/center/room";
export default {
  name: "index",
  dicts: ["sys_oldman_huzhu"],
  data() {
    return {
      vehicleType: [
        { label: "其他车", value: 0 },
        { label: "小型车", value: 1 },
        { label: "大型车", value: 2 },
        { label: "摩托车", value: 3 },
      ],
      title: "",
      //默认第一个选项卡
      activeName: "family",
      form: {
        ownerRelation: "",
        communityId: "",
        selfIntro: "",
        roomStatus: "",
        buildingId: "",
        qqNumber: "",
        email: "",
        unitId: "",
        roomId: "",
        ownerName: "",
        ownerPhone: "",
        population: "",
        gender: "",
        military: "",
        ownerId: "",
        career: "",
        cardType: "",
        deliveryTime: null,
        marriageStatus: "",
        licenseUrl: "",
        cardFont: "",
        cardBack: "",
        cardNo: "",
        domicileAddress: "",
        faceUrl: "",
        religion: "",
        nation: "",
        dataSource: "0",
        nativePlace: "",
        politicsStatus: "",
        emergencyContact: "",
        eduLevel: "",
        nationality: "",
        accountId: "",
      },
      carportNoList: [],
      // 表单校验
      rules: {
        buildingName: [
          {
            required: true,
            message: "请选择所属楼宇",
            trigger: ["blur", "change"],
          },
        ],
        unitName: [
          {
            required: true,
            message: "请选择所属单元",
            trigger: ["blur", "change"],
          },
        ],
        roomName: [
          {
            required: true,
            message: "请选择所属房间",
            trigger: ["blur", "change"],
          },
        ],
        ownerName: [
          { required: true, message: "业主姓名不能为空", trigger: "blur" },
          { pattern: /[^ \x22]+/, message: "不能输入空格", trigger: "blur" },
        ],
        ownerPhone: [
          //1、手机号码验证正则
          { required: true, message: "请输入手机号", trigger: "blur" },
          {
            pattern: /^1[3456789]\d{9}$/,
            message: "手机号格式不对",
            trigger: "blur",
          },
        ],
        gender: [
          {
            required: true,
            message: "性别不能为空",
            trigger: ["blur", "change"],
          },
        ],
        cardType: [
          {
            required: true,
            message: "证件类型不能为空",
            trigger: ["blur", "change"],
          },
        ],
        cardNo: [
          //身份证验证
          { required: true, message: "请输入证件号码", trigger: "blur" },
        ],
        deliveryTime: [
          {
            required: true,
            message: "请输入交房时间",
            trigger: ["blur", "change"],
          },
        ],
        totalArea: [
          {
            required: true,
            message: "请输入房屋面积",
            trigger: ["blur", "change"],
          },
        ],

        cardFont: [
          {
            required: true,
            message: "请选择证件照正面",
            trigger: ["blur", "change"],
          },
        ],
        cardBack: [
          {
            required: true,
            message: "请选择证件照反面",
            trigger: ["blur", "change"],
          },
        ],
        faceUrl: [
          {
            required: true,
            message: "请选择照片",
            trigger: ["blur", "change"],
          },
        ],
        ownerRelation: [
          {
            required: true,
            message: "请选择户主关系",
            trigger: ["blur", "change"],
          },
        ],

        email: [
          {
            type: "email",
            message: "'请输入正确的邮箱地址",
            trigger: ["blur", "change"],
          },
        ],
        qqNumber: [
          {
            pattern: "[1-9]([0-9]{5,11})",
            message: "'请输入正确的qq号码",
            trigger: "blur",
          },
        ],
      },
      relationList: [
        { label: "本人", value: "0", isRead: "0" },
        { label: "配偶", value: "1", isRead: "0" },
        { label: "父母", value: "2", isRead: "0" },
        { label: "子女", value: "3", isRead: "0" },
        { label: "其它", value: "4", isRead: "0" },
        { label: "租客", value: "5", isRead: "0" },
      ],
      showForm: "",
      politicsStatus: [
        { label: "党员", value: "1" },
        { label: "团员", value: "2" },
        { label: "其它", value: "3" },
      ],
      // 房屋类型
      defaultRoomType: [
        // {id: 1, name: '安置房'},
        // {id: 2, name: '商品房'},
        // {id: 3, name: '公租房'}
      ],
      roomStatus: [
        { label: "未售", value: "0" },
        { label: "已售", value: "1" },
        { label: "入住", value: "2" },
        { label: "装修", value: "3" },
        { label: "已拿房", value: "4" },
        { label: "整租", value: "5" },
        { label: "合租", value: "6" },
        { label: "群租", value: "7" },
      ],
      gender: [
        { label: "男", value: "0" },
        { label: "女", value: "1" },
      ],
      marriageStatus: [
        { label: "未婚", value: "0" },
        { label: "已婚", value: "1" },
      ],
      familyList: [],
      population: [
        { label: "户籍人口", value: "0" },
        { label: "常驻人口", value: "1" },
        { label: "暂住人口", value: "2" },
      ],
      oldRoomId: "",
      carList: [],
      unitList: [],
      roomList: [],
      tenantList: [],
      cardTypeList: [],
      hosingList: [],
      carPortList: [],
      defaultId: "", //默认选中的节点树
      filterText: "",
      details: false,
      // 业主信息导入参数
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
        url: process.env.VUE_APP_BASE_API + "/estate/file/owner/importData",
      },
      // 显示搜索条件
      showSearch: true,
      isAdd: false,
      isView: false,
      updateRoomForm: {
        communityName: undefined,
        buildingName: undefined,
        unitName: undefined,
        roomName: undefined,
        currentFloor: undefined,
        totalFloor: undefined,
        roomType: undefined,
        totalArea: undefined,
        shareArea: undefined,
        roomArea: undefined,
        deliveryTime: undefined,
        id: undefined,
        ownerName: undefined,
        ownerPhone: undefined,
        cardType: undefined,
        cardNo: undefined,
        cardFont: undefined,
        cardBack: undefined,
        gender: undefined,
        ownerRelation: undefined,
        politicsStatus: undefined,
        emergencyContact: undefined,
        emergencyContactPhone: undefined,
        selfIntro: undefined,
        ownerList: [],
        spaceVehicleList: [],
      },
      ownerList: [
        {
          id: undefined,
          ownerName: undefined,
          ownerPhone: undefined,
          cardType: undefined,
          cardNo: undefined,
          cardFont: undefined,
          cardBack: undefined,
          gender: undefined,
          ownerRelation: undefined,
          politicsStatus: undefined,
          emergencyContact: undefined,
          emergencyContactPhone: undefined,
          selfIntro: undefined,
        },
      ],
      spaceVehicleList: [
        {
          id: undefined,
          carportNo: undefined,
          carportNature: undefined,
          rentalType: undefined,
          carportFee: undefined,
          rentalStart: undefined,
          rentalEnd: undefined,
          plateNo: undefined,
          plateColor: undefined,
          vehicleType: undefined,
          vehicleBrand: undefined,
        },
      ],
      //查找条件
      queryParams: {
        phone: "",
        name: "",
        isCar: "",
        isOutIn: "",
        pageNum: 1,
        pageSize: 10,
      },
      // 非单个禁用
      single: true,
      isList: true,
      ownerRelation: "",
      // 非多个禁用
      multiple: true,
      ids: [],
      communityId: "",
      communityName: "",
      // 总条数
      total: 0,
      //
      dataList: [],
      BuildingList: [],
      // 遮罩层
      loading: true,
      //查看对象
      info: "",
      inout: [
        { value: "0", label: "是" },
        { value: "1", label: "否" },
      ],
      isDisabled: false,
      data: [],
      //是否显示可用设备
      showDevice: false,
      ownerTempList: [],
      ownerIds: [],
      ownerNames: [],
      deviceList: [],
      indexCodes: [],
      vehicleTypeList: [],
      plateColorList: [],
    };
  },

  created() {
    this.initData();
    getDicts("sys_certificate").then((e) => {
      this.cardTypeList = e.data;
    });
    getDicts("sys_plate_color").then((response) => {
      this.plateColorList = response.data;
    });
    getDicts("vehicle_type").then((response) => {
      this.vehicleTypeList = response.data;
    });
    getDicts("property_of_house").then((response) => {
      this.defaultRoomType = response.data;
    });
  },
  watch: {
    //小区过滤
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },

  methods: {
    /** 导入按钮操作 */
    handleImport() {
      this.uploadImprot.title = "业主信息导入";
      this.uploadImprot.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download(
        "estate/file/owner/importTemplate",
        {},
        `业主信息导入模板_${new Date().getTime()}.xlsx`
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
      this.initData();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.uploadImprot.submit();
    },
    isValidID(id) {
      const regex =
        /^[1-9]\d{5}(18|19|20|21|22)?\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}(\d|[Xx])$/;
      return regex.test(id);
    },
    add() {
      this.isAdd = true;
      this.isDisabled = false;
      this.updateRoomForm = {};
      this.updateRoomForm.communityName = this.communityName;
      getBuildingList(this.communityId).then((e) => {
        this.BuildingList = e.data;
      });
      this.init();
    },
    ownerNameInput(val) {
      this.updateRoomForm.ownerName = val;
      this.$forceUpdate();
    },
    genderInput(val) {
      this.updateRoomForm.gender = val;
      this.$forceUpdate();
    },
    ownerPhoneInput(val) {
      this.updateRoomForm.ownerPhone = val;
      this.$forceUpdate(val);
    },
    cardTypeInput(val) {
      this.updateRoomForm.cardType = val;
      this.$forceUpdate();
    },
    cardNoInput(val) {
      this.updateRoomForm.cardNo = val;
      this.$forceUpdate();
    },
    ownerRelationInput(val) {
      this.updateRoomForm.ownerRelation = val;
      this.$forceUpdate();
    },
    politicsStatusInput(val) {
      this.updateRoomForm.politicsStatus = val;
      this.$forceUpdate();
    },
    emergencyContactInput(val) {
      this.updateRoomForm.emergencyContact = val;
      this.$forceUpdate();
    },
    emergencyContactPhoneInput(val) {
      this.updateRoomForm.emergencyContactPhone = val;
      this.$forceUpdate();
    },
    selfIntroInput(val) {
      this.updateRoomForm.selfIntro = val;
      this.$forceUpdate();
    },
    view(row) {
      this.loading = true;
      this.updateRoomForm = {};
      this.updateRoomForm.communityName = this.communityName;
      getBuildingList(this.communityId).then((e) => {
        this.BuildingList = e.data;
      });
      getDetailByOwnerId(row.ownerId, row.communityId).then((response) => {
        this.updateRoomForm = response.data;
        if (response.data.buildingId) {
          //单元
          getUnitList(response.data.buildingId).then((e) => {
            this.unitList = e.data;
          });
        }
        if (response.data.unitId) {
          //获取房间
          getRoomList(response.data.unitId).then((e) => {
            this.roomList = e.data;
          });
        }
        this.getCarportList(row.communityId);
        //房屋信息
        if (response.data.ownerList) {
          if (response.data.ownerList[0].id) {
            this.updateRoomForm.ownerId = response.data.ownerList[0].id;
          }
          if (response.data.ownerList[0].ownerName) {
            this.updateRoomForm.ownerName =
              response.data.ownerList[0].ownerName;
          }
          if (response.data.ownerList[0].ownerPhone) {
            this.updateRoomForm.ownerPhone =
              response.data.ownerList[0].ownerPhone;
          }
          if (response.data.ownerList[0].cardType) {
            this.updateRoomForm.cardType = response.data.ownerList[0].cardType;
          }
          if (response.data.ownerList[0].cardNo) {
            this.updateRoomForm.cardNo = response.data.ownerList[0].cardNo;
          }
          if (response.data.ownerList[0].cardFont) {
            this.updateRoomForm.cardFont = response.data.ownerList[0].cardFont;
          }
          if (response.data.ownerList[0].cardBack) {
            this.updateRoomForm.cardBack = response.data.ownerList[0].cardBack;
          }
          if (response.data.ownerList[0].gender) {
            this.updateRoomForm.gender = response.data.ownerList[0].gender;
          }
          if (response.data.ownerList[0].ownerRelation) {
            this.updateRoomForm.ownerRelation =
              response.data.ownerList[0].ownerRelation;
          }
          if (response.data.ownerList[0].politicsStatus) {
            this.updateRoomForm.politicsStatus =
              response.data.ownerList[0].politicsStatus;
          }
          if (response.data.ownerList[0].emergencyContact) {
            this.updateRoomForm.emergencyContact =
              response.data.ownerList[0].emergencyContact;
          }
          if (response.data.ownerList[0].emergencyContactPhone) {
            this.updateRoomForm.emergencyContactPhone =
              response.data.ownerList[0].emergencyContactPhone;
          }
          if (response.data.ownerList[0].selfIntro) {
            this.updateRoomForm.selfIntro =
              response.data.ownerList[0].selfIntro;
          }
        }
        if (response.data.spaceVehicleList) {
          this.spaceVehicleList = response.data.spaceVehicleList;
        } else {
          this.spaceVehicleList = [
            {
              id: undefined,
              carportNo: undefined,
              carportNature: undefined,
              rentalType: undefined,
              carportFee: undefined,
              rentalStart: undefined,
              rentalEnd: undefined,
              plateNo: undefined,
              plateColor: undefined,
              vehicleType: undefined,
              vehicleBrand: undefined,
            },
          ];
        }

        this.updateRoomForm.roomType = this.updateRoomForm.roomType + "";

        this.loading = false;
        this.isView = true;
        this.isDisabled = true;
      });
    },
    up(row) {
      this.loading = true;
      this.updateRoomForm = {};
      this.updateRoomForm.communityName = this.communityName;
      getBuildingList(this.communityId).then((e) => {
        this.BuildingList = e.data;
      });
      getDetailByOwnerId(row.ownerId, row.communityId).then((response) => {
        this.updateRoomForm = response.data;
        if (response.data.buildingId) {
          //单元
          getUnitList(response.data.buildingId).then((e) => {
            this.unitList = e.data;
          });
        }
        if (response.data.unitId) {
          //获取房间
          getRoomList(response.data.unitId).then((e) => {
            this.roomList = e.data;
          });
        }
        this.getCarportList(row.communityId);
        //房屋信息
        if (response.data.ownerList) {
          if (response.data.ownerList[0].id) {
            this.updateRoomForm.ownerId = response.data.ownerList[0].id;
          }
          if (response.data.ownerList[0].ownerName) {
            this.updateRoomForm.ownerName =
              response.data.ownerList[0].ownerName;
          }
          if (response.data.ownerList[0].ownerPhone) {
            this.updateRoomForm.ownerPhone =
              response.data.ownerList[0].ownerPhone;
          }
          if (response.data.ownerList[0].cardType) {
            this.updateRoomForm.cardType = response.data.ownerList[0].cardType;
          }
          if (response.data.ownerList[0].cardNo) {
            this.updateRoomForm.cardNo = response.data.ownerList[0].cardNo;
          }
          if (response.data.ownerList[0].cardFont) {
            this.updateRoomForm.cardFont = response.data.ownerList[0].cardFont;
          }
          if (response.data.ownerList[0].cardBack) {
            this.updateRoomForm.cardBack = response.data.ownerList[0].cardBack;
          }
          if (response.data.ownerList[0].gender) {
            this.updateRoomForm.gender = response.data.ownerList[0].gender;
          }
          if (response.data.ownerList[0].ownerRelation) {
            this.updateRoomForm.ownerRelation =
              response.data.ownerList[0].ownerRelation;
          }
          if (response.data.ownerList[0].politicsStatus) {
            this.updateRoomForm.politicsStatus =
              response.data.ownerList[0].politicsStatus;
          }
          if (response.data.ownerList[0].emergencyContact) {
            this.updateRoomForm.emergencyContact =
              response.data.ownerList[0].emergencyContact;
          }
          if (response.data.ownerList[0].emergencyContactPhone) {
            this.updateRoomForm.emergencyContactPhone =
              response.data.ownerList[0].emergencyContactPhone;
          }
          if (response.data.ownerList[0].selfIntro) {
            this.updateRoomForm.selfIntro =
              response.data.ownerList[0].selfIntro;
          }
          if (response.data.ownerList[0].ownerId) {
            this.updateRoomForm.ownerId = response.data.ownerList[0].ownerId;
          }
        }
        // if (response.data.spaceVehicleList) {
        //   this.spaceVehicleList = response.data.spaceVehicleList;
        // } else {
        //   this.spaceVehicleList = [{
        //     id: undefined,
        //     carportNo: undefined,
        //     carportNature: undefined,
        //     rentalType: undefined,
        //     carportFee: undefined,
        //     rentalStart: undefined,
        //     rentalEnd: undefined,
        //     plateNo: undefined,
        //     plateColor: undefined,
        //     vehicleType: undefined,
        //     vehicleBrand: undefined,
        //   }]
        // }

        this.updateRoomForm.roomType = this.updateRoomForm.roomType + "";

        this.loading = false;
        this.isAdd = true;
        this.isDisabled = true;
      });
    },
    // 修改房屋
    submitUpdateRoomForm() {
      this.$refs["updateRoomForm"].validate((valid) => {
        if (valid) {
          if (this.updateRoomForm.ownerName) {
            this.ownerList[0].ownerName = this.updateRoomForm.ownerName;
          }
          if (this.updateRoomForm.ownerPhone) {
            this.ownerList[0].ownerPhone = this.updateRoomForm.ownerPhone;
          }
          if (this.updateRoomForm.cardType) {
            this.ownerList[0].cardType = this.updateRoomForm.cardType;
          }

          var cardNo = this.updateRoomForm.cardNo;
          if (this.updateRoomForm.cardType == "I") {
            if (!this.isValidID(cardNo)) {
              this.$modal.msgWarning("证件号码格式有误！");
              return;
            }
          }

          if (cardNo) {
            this.ownerList[0].cardNo = cardNo;
          }
          if (this.updateRoomForm.cardFont) {
            this.ownerList[0].cardFont = this.updateRoomForm.cardFont;
          }
          if (this.updateRoomForm.cardBack) {
            this.ownerList[0].cardBack = this.updateRoomForm.cardBack;
          }
          if (this.updateRoomForm.gender) {
            this.ownerList[0].gender = this.updateRoomForm.gender;
          }
          if (this.updateRoomForm.ownerRelation) {
            this.ownerList[0].ownerRelation = this.updateRoomForm.ownerRelation;
          }
          if (this.updateRoomForm.politicsStatus) {
            this.ownerList[0].politicsStatus =
              this.updateRoomForm.politicsStatus;
          }
          if (this.updateRoomForm.emergencyContact) {
            this.ownerList[0].emergencyContact =
              this.updateRoomForm.emergencyContact;
          }
          if (this.updateRoomForm.emergencyContactPhone) {
            this.ownerList[0].emergencyContactPhone =
              this.updateRoomForm.emergencyContactPhone;
          }
          if (this.updateRoomForm.selfIntro) {
            this.ownerList[0].selfIntro = this.updateRoomForm.selfIntro;
          }
          getByRoomIdOwnerRelationCount(
            this.updateRoomForm.roomName,
            this.updateRoomForm.ownerRelation,
            this.updateRoomForm.ownerId
          ).then((res) => {
            if (res.data == 0) {
              this.$modal.msgWarning("当前房间下业主关系已存在本人");
              return;
            }
          });
          if (this.spaceVehicleList.length >= 1) {
            this.spaceVehicleList.forEach((item) => {
              if (item.carportNo) {
                if (!item.plateNo) {
                  this.$modal.msgWarning("请输入车辆信息");
                  return;
                }
              }
            });
            this.updateRoomForm.spaceVehicleList = this.spaceVehicleList;
          }
          this.updateRoomForm.ownerList = this.ownerList;
          updateRoom(this.updateRoomForm).then((response) => {
            this.$modal.msgSuccess("修改成功");
            this.closer();
          });
        }
      });
    },
    carportNatureChange(item1) {
      item1.disType = item1.carportNature != "0";
    },
    getRentalType(item1) {
      if (item1.rentalType == "0") {
        item1.rentalStart = "";
        item1.rentalEnd = "";
      }
    },
    getCarport(item) {
      var obj = { carportNo: item.carportNo };
      updateDetail(obj).then((res) => {
        item.carportNature = res.data.carportNature;
        item.rentalType = res.data.rentalType;
        item.disType = item.carportNature != "0";
        this.getRentalType(item);
      });
    },
    getCarportNature(carportNature) {
      if (carportNature == "0") {
        return "人防";
      }
      if (carportNature == "1") {
        return "非人防";
      }
    },
    getRentalTypeName(rentalType) {
      if (rentalType == "0") {
        return "售";
      }
      if (rentalType == "1") {
        return "年租";
      }
      if (rentalType == "2") {
        return "月租";
      }
    },
    hide(str, begin, end) {
      if (str) {
        return desensitization(str, begin, end);
      }
      return "";
    },
    getVehicleType(type) {
      let filter = this.vehicleType.filter((e) => e.value == type);

      return filter[0]["label"];
    },
    clearOption() {
      this.BuildingList = [];
      this.unitList = [];
      this.roomList = [];
      this.form["ownerId"] = "";
    },
    openUrl(url) {
      window.open(url);
    },
    selectEnable(row) {
      // 复选框可选情况
      const { isShowDel } = row;

      if (isShowDel == 1) {
        // 禁用有人租的房子
        return false;
      } else {
        return true;
      }
    },
    cancel() {
      this.details = false;
      this.isAdd = false;
      this.isList = true;
      this.clearOption();
      this.showDevice = false;
      this.ownerTempList = [];
      this.ownerIds = [];
      this.ownerNames = [];
      this.deviceList = [];
      this.indexCodes = [];
    },
    // 上传预处理
    beforeUpload(file) {
      return false;
    },
    //上传产权
    uploadLicenseUrl(file) {
      const isJPG =
        file.raw.type === "image/jpeg" || file.raw.type === "image/png";

      if (!isJPG) {
        this.$message.error("上传图片只能是 JPG/PNG 格式!");
        return isJPG;
      }
      let formData = new FormData();
      const { raw } = file;
      formData.append("file", raw);
      upload(formData).then((e) => {
        const { code } = e;
        if (code == 200) {
          const {
            data: { url },
          } = e;
          this.form.licenseUrl = url;
        } else {
          this.$message.error("上传失败!!");
        }
      });
    },

    //上传正面
    uploadCardFont(file) {
      const isJPG =
        file.raw.type === "image/jpeg" || file.raw.type === "image/png";

      if (!isJPG) {
        this.$message.error("上传图片只能是 JPG/PNG 格式!");
        return isJPG;
      }
      let formData = new FormData();
      const { raw } = file;
      formData.append("file", raw);
      upload(formData).then((e) => {
        const { code } = e;
        if (code == 200) {
          const {
            data: { url },
          } = e;
          this.form.cardFont = url;
        } else {
          this.$message.error("上传失败!!");
        }
      });
    },
    //上传反面
    uploadCardBack(file) {
      const isJPG =
        file.raw.type === "image/jpeg" || file.raw.type === "image/png";

      if (!isJPG) {
        this.$message.error("上传图片只能是 JPG/PNG 格式!");
        return isJPG;
      }
      let formData = new FormData();
      const { raw } = file;
      formData.append("file", raw);
      upload(formData).then((e) => {
        const { code } = e;
        if (code == 200) {
          const {
            data: { url },
          } = e;
          this.form.cardBack = url;
        } else {
          this.$message.error("上传失败!!");
        }
      });
    },
    //上传人脸
    uploadFaceUrl(file) {
      const isJPG =
        file.raw.type === "image/jpeg" || file.raw.type === "image/png";

      if (!isJPG) {
        this.$message.error("上传图片只能是 JPG/PNG 格式!");
        return isJPG;
      }
      let formData = new FormData();
      const { raw } = file;
      formData.append("file", raw);
      upload(formData).then((e) => {
        const { code } = e;
        if (code == 200) {
          const {
            data: { url },
          } = e;
          this.form.faceUrl = url;
        } else {
          this.$message.error("上传失败!!");
        }
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.queryParams["ids"] = this.ids;
      this.download(
        "/estate/file/owner/export",
        { ...this.queryParams },
        `${this.parseTime(new Date())}业主档案.xlsx`
      );
      //清空多选按钮
      this.$refs.multipleTable.clearSelection();
    },

    show(row) {
      //对象的深度拷贝
      this.form = JSON.parse(JSON.stringify(row));
      this.public(row).then((e) => {
        this.details = true;
        this.isAdd = false;
        this.isList = false;
      });
      queryByRoomId({
        communityId: row.communityId,
        buildingId: row.buildingId,
        unitId: row.unitId,
        roomId: row.roomId,
      }).then((e) => {
        this.familyList = e.data;
      });
      queryRoomByOwnerId({ id: row.ownerId }).then((e) => {
        this.hosingList = e.data;
      });
      queryCarByOwnerId({ id: row.accountId }).then((e) => {
        this.carList = e.data;
      });
      queryTenantByOwnerId({ id: row.ownerId }).then((e) => {
        this.tenantList = e.data;
      });
      queryCarPortByOwnerId({ id: row.accountId }).then((e) => {
        this.carPortList = e.data;
      });
    },
    async public(row) {
      if (row.ownerRelation == "0") {
        this.relationList = [{ label: "本人", value: "0" }];
      } else {
        this.relationList = [
          { label: "配偶", value: "1" },
          { label: "父母", value: "2" },
          { label: "子女", value: "3" },
          { label: "其它", value: "4" },
        ];
      }
      this.communityName = row["communityName"];
      let loading = this.$loading({
        lock: true,
        text: "Loading",
        spinner: "el-icon-loading",
        background: "rgba(0, 0, 0, 0.7)",
      });
      await getBuildingList(row.communityId).then((e) => {
        this.BuildingList = e.data;
      });
      await getUnitList(row.buildingId).then((e) => {
        this.unitList = e.data;
      });
      await getRoomList(row.unitId).then((e) => {
        this.roomList = e.data;
      });
      this.form["roomStatus"] = row.roomStatus;
      loading.close();
      return true;
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.ownerId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    //获取单元
    getUnitList(e) {
      this.updateRoomForm["ownerRelation"] = "";
      this.roomList = "";
      const buildingId = e;
      this.updateRoomForm.unitName = "";
      this.updateRoomForm.roomName = "";
      this.updateRoomForm["roomStatus"] = "";
      this.init();
      getUnitList(buildingId).then((e) => {
        this.unitList = e.data;
      });
    },
    //获取房间
    getRoomList(unitId) {
      this.updateRoomForm["ownerRelation"] = "";
      this.updateRoomForm.roomName = "";
      this.init();
      this.updateRoomForm["roomStatus"] = "";
      getRoomList(unitId).then((e) => {
        this.roomList = e.data;
      });
    },
    closer() {
      this.isAdd = false;
      this.details = false;
      this.isList = true;
      this.clearOption();
      this.getList();
    },
    viewCloser() {
      this.isView = false;
      this.details = false;
      this.isList = true;
      this.clearOption();
      this.getList();
    },
    //房屋状态
    getRoomState(e) {
      //查询房屋信息
      this.getRoomInfo(e);
    },
    getRoomInfo(id) {
      if (id) {
        getRoomDetail(id, this.communityId).then((response) => {
          this.updateRoomForm = response.data;
          //房屋信息
          this.getCarportList(this.communityId);

          this.updateRoomForm.roomType = this.updateRoomForm.roomType + "";
        });
      } else {
        this.init();
      }
    },
    init() {
      this.updateRoomForm.houseAddress = undefined;
      this.updateRoomForm.roomType = undefined;
      this.updateRoomForm.deliveryTime = undefined;
      this.updateRoomForm.totalArea = undefined;
      this.updateRoomForm.shareArea = undefined;
      this.updateRoomForm.roomArea = undefined;
      (this.spaceVehicleList = [
        {
          id: undefined,
          carportNo: undefined,
          carportNature: undefined,
          rentalType: undefined,
          carportFee: undefined,
          rentalStart: undefined,
          rentalEnd: undefined,
          plateNo: undefined,
          plateColor: undefined,
          vehicleType: undefined,
          vehicleBrand: undefined,
        },
      ]),
        (this.ownerList = [
          {
            id: undefined,
            ownerName: undefined,
            ownerPhone: undefined,
            cardType: undefined,
            cardNo: undefined,
            cardFont: undefined,
            cardBack: undefined,
            gender: undefined,
            ownerRelation: undefined,
            politicsStatus: undefined,
            emergencyContact: undefined,
            emergencyContactPhone: undefined,
            selfIntro: undefined,
          },
        ]);
    },
    //查询房屋信息信息
    getCarportList(communityId) {
      getCommunityCarportList(communityId).then((res) => {
        this.carportNoList = res.data;
      });
    },

    //增加车辆车位信息
    saveSpaceVehicle() {
      this.spaceVehicleList.push({
        id: undefined,
        carportNo: undefined,
        carportNature: undefined,
        rentalType: undefined,
        carportFee: undefined,
        rentalStart: undefined,
        rentalEnd: undefined,
        plateNo: undefined,
        plateColor: undefined,
        vehicleType: undefined,
        vehicleBrand: undefined,
      });
    },
    //删除车辆车位信息
    deleteSpaceVehicle(item1, index1) {
      this.spaceVehicleList = this.spaceVehicleList.filter(
        (item) => item != item1
      );

      // this.spaceVehicleList = this.spaceVehicleList.filter(function (v, i, ar) {
      //   return i!==ar.length-1;
      // });
    },
    filterNode(value, data) {
      if (!value) return true;
      return data.label.indexOf(value) !== -1;
    },
    add1() {
      this.form["communityId"] = this.communityId;
      this.$refs["form"].validate((valid) => {
        if (valid) {
          add(this.form).then((e) => {
            if (e.code == 200) {
              this.$message.success(e.msg);
              this.closer();
            } else {
              this.$message.error(e.msg);
            }
          });
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm("是否确认删除该数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return deleted(row.ownerId);
        })
        .then(() => {
          this.$message.success("删除成功");
          this.getList();
          this.ids = [];
        });
    },
    /** 删除按钮操作 */
    handleDeletes() {
      this.$confirm("是否确认删除数据项?", "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(() => {
          return deleteds(this.ids);
        })
        .then(() => {
          this.$message.success("删除成功");
          this.getList();
          this.ids = [];
        });
    },
    update() {
      this.form["oldRoomId"] = this.oldRoomId + "";
      if (this.form["ownerRelation"] != "0") {
        this.form["licenseUrl"] = "";
      }
      if (this.form["ownerRelation"] == "0" && !this.form["deliveryTime"]) {
        this.$message.error("请选择交房日期!!");

        return;
      }
      this.$refs["form"].validate((valid) => {
        if (valid) {
          update(this.form).then((e) => {
            if (e.code == 200) {
              this.$message.success(e.msg);
              this.closer();
            } else {
              this.$message.error(e.msg);
            }
          });
        }
      });
    },
    //初始化数据
    initData() {
      getCurrentList().then((e) => {
        const { data } = e;
        if (data) {
          let arr = new Array();
          data.forEach((item) => {
            const { communityId, communityName } = item;
            arr.push({
              id: communityId,
              label: communityName,
              children: [{}],
            });
          });
          this.data = arr;
          this.queryParams["communityId"] = data[0].communityId;
          this.defaultId = data[0].communityId;
          this.communityId = data[0].communityId;
          this.communityName = data[0].communityName;

          this.$nextTick(() => {
            this.$refs["tree"].setCurrentKey(this.defaultId);
          });
          this.getList();
        } else {
          this.$message.error("暂无小区");
        }
      });
    },
    //查询数据
    getList() {
      this.loading = true;

      query(this.queryParams).then((e) => {
        this.dataList = e.rows;
        this.total = e.total;
        this.loading = false;
      });
    },
    //获取点击小区id
    getCommunityId(e) {
      this.queryParams["communityId"] = e.id;
      this.communityName = e.label;
      this.communityId = e.id;
      this.getList();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },

    /** 门禁授权按钮 */
    handleAuth() {
      if (this.queryParams.communityId == undefined) {
        this.$message.error("请选择小区！");
        return false;
      }
      getDevice(this.queryParams.communityId).then((response) => {
        this.showDevice = true;
        this.ownerTempList = response.ownerList;
        this.deviceList = response.deviceList;
      });
    },

    /** 选择业主 */
    handleSelectionChangeOwner(selection) {
      this.ownerIds = selection.map((item) => item.ownerId);
      this.ownerNames = selection.map((item) => item.ownerName);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 选择设备 */
    handleSelectionChangeDevice(selection) {
      this.indexCodes = selection.map((item) => item.indexCode);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },

    /** 授权提交 */
    authSubmit() {
      if (this.ownerIds.length == 0) {
        this.$message.error("至少选择一个业主");
        return false;
      }
      if (this.indexCodes.length == 0) {
        this.$message.error("至少选择一个设备");
        return false;
      }
      let params = {
        communityId: this.queryParams.communityId,
        ids: this.ownerIds,
        indexCodes: this.indexCodes,
      };
      authOwner(params)
        .then((response) => {
          this.msgSuccess(response.msg);
          this.showDevice = false;
          this.ownerTempList = [];
          this.ownerIds = [];
          this.ownerNames = [];
          this.deviceList = [];
          this.indexCodes = [];
          this.getList();
        })
        .catch((err) => {
          this.$message.error(err);
        });
    },
  },
};
</script>

<style scoped>
.add-view span {
  font-weight: bold;
  color: black;
}
.avatar-uploader .el-upload,
.face-url .avatar-uploader {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.face-url .avatar-uploader {
  width: 178px;
  height: 178px;
}
.avatar-uploader:hover {
  border-color: #409eff;
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
/deep/
  .el-tree--highlight-current
  .el-tree-node.is-current
  > .el-tree-node__content {
  background-color: #b3f1b6 !important;
}
</style>

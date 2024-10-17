<template>
  <div class="app-container">
    <div v-show="mainShow">
      <el-row :gutter="20">
        <!--部门数据-->
        <el-col :span="4" :xs="24">
          <div
            class="head-container"
            style="margin-bottom: 10px; text-align: center"
          >
            <el-button
              size="small"
              type="info"
              :plain="!isPlain"
              @click="switchTable"
              style="width: 70px; padding-left: 11px"
              >表格视图</el-button
            >
            <el-button
              size="small"
              type="info"
              :plain="isPlain"
              @click="switchGraph"
              style="width: 70px; padding-left: 11px"
              >图形视图</el-button
            >
          </div>
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
          <div class="head-container" style="margin-bottom: 10px">
            <el-button-group>
              <el-button
                size="small"
                @click="insert"
                v-hasPermi="[
                  'manager:room:addBuildingBatch',
                  'manager:room:addUnitAndRoomBatch',
                  'manager:room:addRoomBatch',
                ]"
              >
                新增
              </el-button>
              <!--              <el-button size="small" v-show="queryParams.level != 1" @click="update">修改</el-button>-->
              <el-button
                size="small"
                v-show="queryParams.level != 1"
                @click="remove"
                v-hasPermi="[
                  'manager:room:deleteBuilding',
                  'manager:room:deleteUnit',
                ]"
                >删除
              </el-button>
            </el-button-group>
          </div>
          <div class="head-container">
            <el-tree
              :data="communityList"
              :props="defaultProps"
              :filter-node-method="filterNode"
              :indent="25"
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
          <el-form
            :model="queryParams"
            ref="queryForm"
            size="small"
            :inline="true"
            label-width="68px"
          >
            <el-form-item label="产权人" prop="userName">
              <el-input
                v-model="queryParams.ownerName"
                placeholder="请输入产权人"
                clearable
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="房屋状态" prop="status">
              <el-select
                v-model="queryParams.roomStatus"
                placeholder="房屋状态"
                clearable
                style="width: 240px"
              >
                <el-option
                  v-for="status in defaultRoomStatus"
                  :key="status.id"
                  :label="status.name"
                  :value="status.id"
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
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
                >重置</el-button
              >
            </el-form-item>
          </el-form>

          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button
                type="success"
                plain
                icon="el-icon-edit"
                size="mini"
                :disabled="multiple"
                @click="handleUpdateRoomStatus()"
                >修改房屋状态
              </el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="danger"
                plain
                icon="el-icon-delete"
                size="mini"
                v-hasPermi="['manager:room:deleteRoomBatch']"
                :disabled="multiple"
                @click="deleteRoomBatch"
                >删除
              </el-button>
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
          </el-row>

          <el-table
            v-loading="loading"
            :data="roomList"
            @selection-change="handleSelectionChange"
          >
            <el-table-column type="selection" width="50" align="center" />
            <el-table-column
              label="所属楼栋"
              align="center"
              key="buildingName"
              prop="buildingName"
            />
            <el-table-column
              label="所属单元"
              align="center"
              key="unitName"
              prop="unitName"
            />
            <el-table-column
              label="房号"
              align="center"
              key="roomName"
              prop="roomName"
            />
            <el-table-column
              label="房间状态"
              align="center"
              key="roomStatus"
              prop="roomStatus"
            >
              <template slot-scope="scope">
                {{ roomStatusStr(scope.row.roomStatus) }}
              </template>
            </el-table-column>
            <el-table-column
              label="房间面积"
              align="center"
              key="totalArea"
              prop="totalArea"
            >
              <template slot-scope="scope">
                {{ scope.row.totalArea }}<span>m<sup>2</sup></span>
              </template>
            </el-table-column>
            <el-table-column
              label="产权人"
              align="center"
              key="ownerName"
              prop="ownerName"
            />
            <el-table-column
              label="产权人手机号"
              align="center"
              key="ownerPhone"
              prop="ownerPhone"
            />
            <el-table-column
              label="居住人数"
              align="center"
              key="liveNum"
              prop="liveNum"
            />
            <el-table-column
              label="房屋属性"
              align="center"
              key="roomType"
              prop="roomType"
            >
              <template slot-scope="scope">
                {{ roomTypeStr(scope.row.roomType) }}
              </template>
            </el-table-column>
            <el-table-column
              label="最后操作时间"
              align="center"
              prop="updateTime"
            >
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.updateTime) }}</span>
              </template>
            </el-table-column>
            <el-table-column
              label="操作"
              align="center"
              fixed="right"
              width="120"
              class-name="small-padding fixed-width"
            >
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="handleViewRoom(scope.row)"
                  >查看
                </el-button>
                <br>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-school"
                  @click="handleTransferOwnership(scope.row)"
                  v-if="scope.row.ownerId !== 0"
                  style="color: #ed5565"
                  >过户
                </el-button>
                <br />
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-setting"
                  @click="handleConfirmRoom(scope.row.id)"
                  v-if="scope.row.ownerId !== 0 && scope.row.roomStatus == 1"
                  style="color: #070707"
                  >收房
                </el-button>
                <br v-if="scope.row.ownerId !== 0 && scope.row.roomStatus == 1">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdateRoom(scope.row)"
                  >添加/修改信息
                </el-button>
                <br
                  v-if="scope.row.roomStatus != '3' && scope.row.roomStatus != '5'"
                />
                <el-button
                  v-if="scope.row.roomStatus != '3' && scope.row.roomStatus != '5'"
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdateRoomStatus(scope.row)"
                  >修改房屋状态
                </el-button>
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

        <el-col :span="20" v-if="graphShow" style="font-size: 18px">
          <el-row style="margin-bottom: 30px">
            <el-col :span="2" :offset="0">{{ this.selectData.name }}</el-col>
            <el-col :span="22">
              <el-row style="margin-bottom: 30px" class="statisticsClass">
                <el-col :span="24"
                  >统计
                  <span v-for="roomStatus in this.defaultRoomStatus">
                    {{ roomStatus.name }}：{{
                      handlerRoomStatusStatistics(roomStatus.id)
                    }}
                  </span>
                </el-col>
                <el-col :span="24" class="colorStatisticsClass">
                  图例
                  <span v-for="roomStatus in this.defaultRoomStatus">
                    <div :style="{ background: roomStatus.background }"></div>
                    {{ roomStatus.name }}
                  </span>
                </el-col>
              </el-row>
            </el-col>
          </el-row>

          <el-row style="margin-bottom: 30px">
            <el-col :span="3">楼层</el-col>
            <el-col
              :span="3"
              style="text-align: center"
              v-for="roomNumber in this.roomGraphData.roomNumbers"
            >
              {{ roomNumber }}
            </el-col>
          </el-row>
          <el-row
            style="margin-top: 5px"
            v-for="roomGraph in this.roomGraphData.buildingGraphList"
          >
            <el-col :span="3">{{ roomGraph.floor }}层</el-col>
            <el-col
              :span="3"
              v-for="roomDetail in roomGraph.roomDetailList"
              style="text-align: center"
            >
              <el-button
                style="width: 115px; height: 60px"
                :style="getBackgroundColor(roomDetail.roomStatus)"
                v-if="roomDetail !== null"
                @click="handleUpdateRoom(roomDetail)"
                >{{ roomDetail.roomName }}
              </el-button>
              <div style="width: 115px; height: 60px" v-else></div>
            </el-col>
          </el-row>
        </el-col>
      </el-row>

      <!-- 批量添加楼宇 -->
      <el-dialog
        :title="title"
        :visible.sync="buildingBatchInsertOpen"
        width="800px"
        append-to-body
      >
        <el-form
          ref="insertBuildingBatchForm"
          :model="insertBuildingBatchForm"
          :rules="buildingBatchInsertRules"
          label-width="100px"
        >
          <el-row>
            <el-col :span="12">
              <el-form-item label="楼宇首名称" prop="buildingName">
                <el-input
                  v-model="insertBuildingBatchForm.buildingName"
                  placeholder="请输入楼宇首名称"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="起始楼宇" prop="startBuildingNum">
                <el-input-number
                  v-model="insertBuildingBatchForm.startBuildingNum"
                  controls-position="right"
                  :min="1"
                  style="width: 100%"
                />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item label="结尾楼宇" prop="endBuildingNum">
                <el-input-number
                  v-model="insertBuildingBatchForm.endBuildingNum"
                  controls-position="right"
                  :min="1"
                  style="width: 100%"
                />
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="排除楼宇">
                <el-select
                  v-model="insertBuildingBatchForm.excludeBuildingNums"
                  placeholder="请选择排除楼宇"
                  style="width: 100%"
                  multiple
                >
                  <el-option
                    v-for="item in selectExcludeBuildingNums"
                    :label="item"
                    :value="item"
                  ></el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitInsertBuildingBatchForm"
            >确 定</el-button
          >
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>

      <!-- 批量添加单元与房间 -->
      <el-dialog
        :title="title"
        :visible.sync="insertUnitAndRoomBatchOpen"
        width="550px"
        append-to-body
      >
        <el-form
          ref="insertUnitAndRoomBatchForm"
          :model="insertUnitAndRoomBatchForm"
          :rules="insertUnitAndRoomBatchRules"
          label-width="120px"
        >
          <el-form-item label="起始单元" prop="startUnitNum">
            <el-input-number
              v-model="insertUnitAndRoomBatchForm.startUnitNum"
              controls-position="right"
              :min="1"
              style="width: 365px"
            />
          </el-form-item>
          <el-form-item label="结尾单元" prop="endUnitNum">
            <el-input-number
              v-model="insertUnitAndRoomBatchForm.endUnitNum"
              controls-position="right"
              :min="1"
              style="width: 365px"
            />
          </el-form-item>
          <el-form-item label="排除单元">
            <el-select
              v-model="insertUnitAndRoomBatchForm.excludeUnitNums"
              placeholder="请选择排除楼宇"
              style="width: 365px"
              multiple
            >
              <el-option
                v-for="item in selectExcludeUnitNums"
                :label="item"
                :value="item"
              ></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="楼层数/单元" prop="floorsNum">
            <el-input-number
              v-model="insertUnitAndRoomBatchForm.floorsNum"
              controls-position="right"
              :min="1"
              style="width: 365px"
            />
          </el-form-item>
          <el-form-item label="每层户数/单元" prop="roomNum">
            <el-input-number
              v-model="insertUnitAndRoomBatchForm.roomNum"
              controls-position="right"
              :min="0"
              style="width: 365px"
            />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitInsertUnitAndRoomBatchForm"
            >确 定</el-button
          >
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>

      <!-- 批量添加房间 -->
      <el-dialog
        :title="title"
        :visible.sync="insertRoomBatchOpen"
        width="550px"
        append-to-body
      >
        <el-form
          ref="insertRoomBatchForm"
          :model="insertRoomBatchForm"
          :rules="insertRoomBatchRules"
          label-width="120px"
        >
          <el-form-item label="楼层数/单元" prop="floorsNum">
            <el-input-number
              v-model="insertRoomBatchForm.floorsNum"
              controls-position="right"
              :min="1"
              style="width: 365px"
            />
          </el-form-item>
          <el-form-item label="每层户数/单元" prop="roomNum">
            <el-input-number
              v-model="insertRoomBatchForm.roomNum"
              controls-position="right"
              :min="1"
              style="width: 365px"
            />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitInsertRoomBatchForm"
            >确 定</el-button
          >
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>

      <!-- 修改楼宇 -->
      <el-dialog
        :title="title"
        :visible.sync="updateBuildingOpen"
        width="500px"
        append-to-body
      >
        <el-form
          ref="updateBuildingForm"
          :model="updateBuildingForm"
          :rules="buildingBatchInsertRules"
          label-width="100px"
        >
          <el-form-item label="楼宇首名称" prop="buildingName">
            <el-input
              v-model="updateBuildingForm.buildingName"
              placeholder="请输入楼宇首名称"
              style="width: 315px"
            />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitUpdateBuildingForm"
            >确 定</el-button
          >
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>

      <!-- 修改单元 -->
      <el-dialog
        :title="title"
        :visible.sync="updateUnitOpen"
        width="500px"
        append-to-body
      >
        <el-form
          ref="updateUnitForm"
          :model="updateUnitForm"
          :rules="insertUnitAndRoomBatchRules"
          label-width="100px"
        >
          <el-form-item label="楼宇首名称" prop="unitName">
            <el-input
              v-model="updateUnitForm.unitName"
              placeholder="请输入楼宇首名称"
              style="width: 315px"
            />
          </el-form-item>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitUpdateUnitForm"
            >确 定</el-button
          >
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>

      <!-- 批量修改房屋 -->
      <el-dialog
        :title="title"
        :visible.sync="updateRoomBatchOpen"
        width="1000px"
        append-to-body
      >
        <el-form
          ref="updateRoomBatchForm"
          :model="updateRoomBatchForm"
          :rules="updateRoomBatchRules"
          label-width="100px"
        >
          <div>
            <span>批量数据</span>
            <el-divider></el-divider>
          </div>
          <el-row>
            <el-col :span="12">
              <el-form-item label="房号" prop="roomNumbers">
                <el-select
                  v-model="updateRoomBatchForm.roomNumbers"
                  placeholder="房号"
                  clearable
                  style="width: 100%"
                  :multiple="true"
                >
                  <el-option
                    v-for="roomNumber in selectRoomNumberList"
                    :key="roomNumber"
                    :label="roomNumber"
                    :value="roomNumber"
                  />
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="12">
              <el-form-item
                label="楼宇"
                prop="floors"
                v-if="this.selectData.level === 1"
              >
                <el-select
                  v-model="updateRoomBatchForm.buildingIds"
                  placeholder="楼宇"
                  clearable
                  style="width: 100%"
                  :multiple="true"
                >
                  <el-option
                    v-for="floor in selectFloorList"
                    :key="floor.buildingId"
                    :label="floor.buildingName"
                    :value="floor.buildingId"
                    :multiple="true"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item
                label="单元号"
                prop="roomUnits"
                v-if="
                  this.selectData.level === 1 || this.selectData.level === 2
                "
              >
                <el-select
                  v-model="updateRoomBatchForm.unitIds"
                  placeholder="单元号"
                  clearable
                  style="width: 100%"
                  :multiple="true"
                >
                  <el-option
                    v-for="unit in selectRoomUnitList"
                    :key="unit.unitId"
                    :label="unit.unitName"
                    :value="unit.unitId"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <div>
            <span>房屋基本信息</span>
            <el-divider></el-divider>
          </div>
          <el-row>
            <el-col :span="12">
              <el-form-item label="房屋性质" prop="roomType">
                <el-select
                  v-model="updateRoomBatchForm.roomType"
                  placeholder="房屋性质"
                  clearable
                  style="width: 100%"
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

            <el-col :span="12">
              <el-form-item label="房屋结构" prop="structure">
                <el-select
                  v-model="updateRoomBatchForm.structure"
                  placeholder="房屋结构"
                  clearable
                  style="width: 100%"
                >
                  <el-option
                    v-for="status in defaultRoomStructure"
                    :key="status.id"
                    :label="status.name"
                    :value="status.id"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item label="房间面积" prop="totalArea">
                <el-input
                  v-model="updateRoomBatchForm.totalArea"
                  placeholder="请输入房间总面积"
                >
                  <i slot="suffix" class="el-input__icon">㎡</i>
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="12">
              <el-form-item label="房屋格局" prop="roomPattern">
                <el-input
                  v-model="updateRoomBatchForm.roomPattern"
                  placeholder="请输入房屋格局"
                />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="12">
              <el-form-item label="设计图1" prop="roomUrl">
                <image-upload v-model="imageUrl" :limit="1" />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitUpdateRoomBatchForm"
            >确 定</el-button
          >
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>

      <!-- 新增/修改房屋 -->
      <el-dialog
        :title="title"
        :visible.sync="updateRoomOpen"
        width="90%"
        append-to-body
      >
        <el-form
          ref="updateRoomForm"
          :model="updateRoomForm"
          :rules="updateRoomRules"
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
                <el-input
                  v-model="updateRoomForm.buildingName"
                  :disabled="true"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="所属单元" prop="unitName">
                <el-input v-model="updateRoomForm.unitName" :disabled="true" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="房号" prop="roomName">
                <el-input v-model="updateRoomForm.roomName" :disabled="true" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="所在层" prop="currentFloor">
                <el-input
                  v-model="updateRoomForm.currentFloor"
                  :disabled="true"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="房屋坐落" prop="houseAddress">
                <el-input
                  :disabled="isDisabled"
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
                  :disabled="isDisabled"
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
                  style="width: 100%"
                  v-model="updateRoomForm.deliveryTime"
                  value-format="yyyy-MM-dd"
                  type="date"
                  placeholder="请选择交房时间"
                  :disabled="isDisabled"
                ></el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="房间面积" prop="totalArea">
                <el-input
                  :disabled="isDisabled"
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
                  :disabled="isDisabled"
                  :isShowTip="!isDisabled"
                  v-model="updateRoomForm.licenseUrl"
                  :limit="3"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <h4 class="form-header">业主信息</h4>
          <div v-for="(item, index) in ownerList" :key="index">
            <h4 class="form-header">第{{ index + 1 }}条业主信息</h4>
            <el-row>
              <el-col :span="8">
                <el-form-item label="姓名" prop="ownerName" label-width="110px">
                  <el-input
                    :disabled="isDisabled"
                    v-model="item.ownerName"
                    placeholder="请输入姓名"
                    style="width: 216px"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="性别" prop="gender" label-width="110px">
                  <el-select
                    :disabled="isDisabled"
                    v-model="item.gender"
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
                <el-form-item
                  label="手机号"
                  prop="ownerPhone"
                  label-width="110px"
                >
                  <el-input
                    :disabled="item.id || isDisabled"
                    v-model="item.ownerPhone"
                    placeholder="请输入手机号"
                    style="width: 216px"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item
                  label="证件类型"
                  prop="cardType"
                  label-width="110px"
                >
                  <el-select
                    :disabled="item.id || isDisabled"
                    v-model="item.cardType"
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
                <el-form-item
                  label="证件号码"
                  prop="cardNo"
                  label-width="110px"
                >
                  <el-input
                    :disabled="item.id || isDisabled"
                    v-model="item.cardNo"
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
                    :disabled="isDisabled"
                    :isShowTip="!isDisabled"
                    v-model="item.cardFont"
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
                    :disabled="isDisabled"
                    :isShowTip="!isDisabled"
                    v-model="item.cardBack"
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
                    :disabled="isDisabled"
                    v-model="item.ownerRelation"
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
                    :disabled="isDisabled"
                    v-model="item.politicsStatus"
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
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item
                  label="紧急联系人"
                  prop="emergencyContact"
                  label-width="110px"
                >
                  <el-input
                    :disabled="isDisabled"
                    v-model="item.emergencyContact"
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
                    :disabled="isDisabled"
                    v-model="item.emergencyContactPhone"
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
                    :disabled="isDisabled"
                    type="textarea"
                    v-model="item.selfIntro"
                    placeholder="请输入备注"
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </div>
          <el-row>
            <el-col :span="4">
              <el-button
                type="primary"
                plain
                icon="el-icon-plus"
                size="mini"
                @click="saveOwner"
                >新增业主信息
              </el-button>
            </el-col>
            <el-col :span="4">
              <el-button
                type="danger"
                plain
                icon="el-icon-delete"
                size="mini"
                @click="deleteOwner"
                >删除业主信息
              </el-button>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="submitUpdateRoomForm"
            >确 定</el-button
          >
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>

      <!-- 查看房屋 -->
      <el-dialog
        :title="title"
        :visible.sync="updateRoomViewOpen"
        width="90%"
        append-to-body
      >
        <el-form
          ref="updateRoomForm"
          disabled
          :model="updateRoomForm"
          :rules="updateRoomRules"
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
                <el-input
                  v-model="updateRoomForm.buildingName"
                  :disabled="true"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="所属单元" prop="unitName">
                <el-input v-model="updateRoomForm.unitName" :disabled="true" />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="房号" prop="roomName">
                <el-input v-model="updateRoomForm.roomName" :disabled="true" />
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="所在层" prop="currentFloor">
                <el-input
                  v-model="updateRoomForm.currentFloor"
                  :disabled="true"
                />
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="房屋坐落" prop="houseAddress">
                <el-input
                  :disabled="isDisabled"
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
                  :disabled="isDisabled"
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
                  style="width: 100%"
                  v-model="updateRoomForm.deliveryTime"
                  value-format="yyyy-MM-dd"
                  type="date"
                  placeholder="请选择交房时间"
                  :disabled="isDisabled"
                ></el-date-picker>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6">
              <el-form-item label="房间面积" prop="totalArea">
                <el-input
                  :disabled="isDisabled"
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
                  :disabled="isDisabled"
                  :isShowTip="!isDisabled"
                  v-model="updateRoomForm.licenseUrl"
                  :limit="3"
                />
              </el-form-item>
            </el-col>
          </el-row>
          <h4 class="form-header">业主信息</h4>
          <div v-for="(item, index) in ownerList" :key="index">
            <h4 class="form-header">第{{ index + 1 }}条业主信息</h4>
            <el-row>
              <el-col :span="8">
                <el-form-item label="姓名" prop="ownerName" label-width="110px">
                  <el-input
                    :disabled="isDisabled"
                    v-model="item.ownerName"
                    placeholder="请输入姓名"
                    style="width: 216px"
                  />
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="性别" prop="gender" label-width="110px">
                  <el-select
                    :disabled="isDisabled"
                    v-model="item.gender"
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
                <el-form-item
                  label="手机号"
                  prop="ownerPhone"
                  label-width="110px"
                >
                  <el-input
                    :disabled="item.id || isDisabled"
                    v-model="item.ownerPhone"
                    placeholder="请输入手机号"
                    style="width: 216px"
                  />
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item
                  label="证件类型"
                  prop="cardType"
                  label-width="110px"
                >
                  <el-select
                    :disabled="item.id || isDisabled"
                    v-model="item.cardType"
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
                <el-form-item
                  label="证件号码"
                  prop="cardNo"
                  label-width="110px"
                >
                  <el-input
                    :disabled="item.id || isDisabled"
                    v-model="item.cardNo"
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
                    :disabled="isDisabled"
                    :isShowTip="false"
                    v-model="item.cardFont"
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
                    :disabled="isDisabled"
                    :isShowTip="false"
                    v-model="item.cardBack"
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
                    :disabled="isDisabled"
                    v-model="item.ownerRelation"
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
                    :disabled="isDisabled"
                    v-model="item.politicsStatus"
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
            </el-row>
            <el-row>
              <el-col :span="8">
                <el-form-item
                  label="紧急联系人"
                  prop="emergencyContact"
                  label-width="110px"
                >
                  <el-input
                    :disabled="isDisabled"
                    v-model="item.emergencyContact"
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
                    :disabled="isDisabled"
                    v-model="item.emergencyContactPhone"
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
                    :disabled="isDisabled"
                    type="textarea"
                    v-model="item.selfIntro"
                    placeholder="请输入备注"
                  />
                </el-form-item>
              </el-col>
            </el-row>
          </div>

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
                    :disabled="isDisabled"
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
                <el-form-item
                  label="金额"
                  prop="carportFee"
                  label-width="110px"
                >
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
                    :disabled="isDisabled"
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
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button @click="viewCancel">取 消</el-button>
        </div>
      </el-dialog>
      <!-- 新增/修改房屋 -->
      <el-dialog
        title="修改房屋状态"
        :visible.sync="updateRoomStatusOpen"
        width="40%"
        append-to-body
      >
        <el-form label-width="110px">
          <el-row>
            <el-col :span="10">
              <el-form-item label="房屋状态" prop="roomStatus">
                <el-select
                  v-model="roomStatus"
                  placeholder="房屋状态"
                  clearable
                  style="width: 240px"
                >
                  <el-option
                    v-for="status in roomStatusOptions"
                    :key="status.value"
                    :label="status.label"
                    :value="status.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer">
          <el-button type="primary" @click="updateRoomStatus">确 定</el-button>
          <el-button @click="roomStatusCancel">取 消</el-button>
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
          :action="
            uploadImprot.url + '?communityId=' + this.selectData.communityId
          "
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

    <!--  过户页面  -->
    <div v-show="transferOwnershipShow">
      <el-form ref="detailForm" :model="detailForm" label-width="140px">
        <el-tabs
          type="border-card"
          v-model="defaultTab"
          @tab-click="handleClick"
        >
          <el-tab-pane name="family">
            <span slot="label"><i class="el-icon-date"></i> 家庭成员</span>
            <h4 class="form-header">房产信息</h4>
            <el-row>
              <el-col :span="6" :offset="2">
                <el-form-item label="所属小区:">{{
                  detailForm.communityName
                }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="2">
                <el-form-item label="所属楼宇:">{{
                  detailForm.buildingName
                }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="2">
                <el-form-item label="所属单元:">{{
                  detailForm.unitName
                }}</el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="6" :offset="2">
                <el-form-item label="所属房间:">{{
                  detailForm.roomName
                }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="2">
                <el-form-item label="房屋状态:">{{
                  roomStatusStr(detailForm.roomStatus)
                }}</el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="20" :offset="2">
                <el-form-item label="房产证明:">
                  <image-upload v-model="detailForm.licenseUrl" :limit="3" />
                </el-form-item>
              </el-col>
            </el-row>

            <h4 class="form-header">家庭成员信息</h4>
            <el-row>
              <el-table :data="inhabitantList">
                <el-table-column
                  label="人员姓名"
                  align="center"
                  key="ownerName"
                  prop="ownerName"
                />
                <el-table-column
                  label="手机号"
                  align="center"
                  key="phone"
                  prop="ownerPhone"
                />
                <el-table-column
                  label="证件号码"
                  align="center"
                  key="cardNo"
                  prop="cardNo"
                />
                <el-table-column
                  label="操作"
                  align="center"
                  key="checkDate"
                  prop="auditTime"
                >
                  <template slot-scope="scope">
                    <el-button
                      size="mini"
                      type="text"
                      icon="el-icon-edit"
                      @click="
                        transferOwnership(
                          scope.row.ownerId,
                          scope.row.ownerName
                        )
                      "
                      v-if="scope.row.ownerRelation !== 0"
                      >过户
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
            </el-row>
          </el-tab-pane>
          <el-tab-pane name="other">
            <span slot="label"><i class="el-icon-date"></i> 其他业主</span>
            <h4 class="form-header">房产信息</h4>
            <el-row>
              <el-col :span="6" :offset="2">
                <el-form-item label="所属小区:">{{
                  detailForm.communityName
                }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="2">
                <el-form-item label="所属楼宇:">{{
                  detailForm.buildingName
                }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="2">
                <el-form-item label="所属单元:">{{
                  detailForm.unitName
                }}</el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="6" :offset="2">
                <el-form-item label="所属房间:">{{
                  detailForm.roomName
                }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="2">
                <el-form-item label="房屋状态:">{{
                  roomStatusStr(detailForm.roomStatus)
                }}</el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="20" :offset="2">
                <el-form-item label="房产证明:" prop="licenseUrl">
                  <image-upload v-model="detailForm.licenseUrl" :limit="3" />
                </el-form-item>
              </el-col>
            </el-row>

            <h4 class="form-header">其他业主信息</h4>
            <el-row>
              <el-form
                :model="ownerQueryParams"
                ref="ownerQueryForm"
                size="small"
                :inline="true"
                label-width="110px"
              >
                <el-form-item label="人员姓名" prop="userName">
                  <el-input
                    v-model="ownerQueryParams.ownerName"
                    placeholder="请输入人员姓名"
                    clearable
                    style="width: 240px"
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>
                <el-form-item label="手机号" prop="userName">
                  <el-input
                    v-model="ownerQueryParams.ownerPhone"
                    placeholder="请输入手机号"
                    clearable
                    style="width: 240px"
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>
                <el-form-item label="证件号码" prop="cardNo">
                  <el-input
                    v-model="ownerQueryParams.cardNo"
                    placeholder="请输入证件号码"
                    clearable
                    style="width: 240px"
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>
                <el-form-item label="所属小区" prop="communityId">
                  <el-select
                    v-model="ownerQueryParams.communityId"
                    placeholder="请选择所属小区"
                    clearable
                    style="width: 315px"
                  >
                    <el-option
                      v-for="community in allCommunityList"
                      :key="community.communityId"
                      :label="community.communityName"
                      :value="community.communityId"
                    />
                  </el-select>
                </el-form-item>
                <el-form-item>
                  <el-button
                    type="primary"
                    icon="el-icon-search"
                    size="mini"
                    @click="handleQueryOwner"
                    >搜索</el-button
                  >
                  <el-button
                    icon="el-icon-refresh"
                    size="mini"
                    @click="resetOwnerQuery"
                    >重置</el-button
                  >
                </el-form-item>
              </el-form>

              <el-table :data="ownerList" v-if="defaultTab === 'other'">
                <el-table-column
                  label="人员姓名"
                  align="center"
                  key="ownerName"
                  prop="ownerName"
                />
                <el-table-column
                  label="手机号"
                  align="center"
                  key="ownerPhone"
                  prop="ownerPhone"
                />
                <el-table-column
                  label="证件号码"
                  align="center"
                  key="cardNo"
                  prop="cardNo"
                />
                <el-table-column
                  label="所属小区"
                  align="center"
                  key="communityName"
                  prop="communityName"
                />
                <el-table-column label="操作" align="center">
                  <template slot-scope="scope">
                    <el-button
                      size="mini"
                      type="text"
                      icon="el-icon-edit"
                      @click="
                        transferOwnership(
                          scope.row.ownerId,
                          scope.row.ownerName
                        )
                      "
                      >过户
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>
              <pagination
                v-show="ownerTotal > 0"
                :total="ownerTotal"
                :page.sync="ownerQueryParams.pageNum"
                :limit.sync="ownerQueryParams.pageSize"
                @pagination="searchOwner"
              />
            </el-row>
          </el-tab-pane>
          <el-tab-pane name="foreign">
            <span slot="label"><i class="el-icon-date"></i> 外来人员</span>
            <h4 class="form-header">房产信息</h4>
            <el-row>
              <el-col :span="6" :offset="2">
                <el-form-item label="所属小区:">{{
                  detailForm.communityName
                }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="2">
                <el-form-item label="所属楼宇:">{{
                  detailForm.buildingName
                }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="2">
                <el-form-item label="所属单元:">{{
                  detailForm.unitName
                }}</el-form-item>
              </el-col>
            </el-row>

            <el-row>
              <el-col :span="6" :offset="2">
                <el-form-item label="所属房间:">{{
                  detailForm.roomName
                }}</el-form-item>
              </el-col>
              <el-col :span="6" :offset="2">
                <el-form-item label="房屋状态:">{{
                  roomStatusStr(detailForm.roomStatus)
                }}</el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="20" :offset="2">
                <el-form-item label="房产证明:" prop="licenseUrl">
                  <image-upload v-model="detailForm.licenseUrl" :limit="3" />
                </el-form-item>
              </el-col>
            </el-row>
            <el-form
              ref="foreignForm"
              :model="foreignForm"
              label-width="130px"
              style="margin: 30px"
              :rules="rules"
            >
              <el-row>
                <h4 class="form-header">业主基本信息</h4>
                <el-col style="margin-left: 6%">
                  <el-row>
                    <el-col :span="6">
                      <el-form-item label="姓名：" prop="ownerName">
                        <el-input
                          v-model="foreignForm.ownerName"
                          clearable
                          placeholder="请输入姓名"
                        />
                      </el-form-item>
                    </el-col>
                    <el-col :span="6">
                      <el-form-item label="手机号码：" prop="ownerPhone">
                        <el-input
                          v-model="foreignForm.ownerPhone"
                          clearable
                          v-show="!foreignForm.accountId"
                          placeholder="请输入手机号码"
                        />
                        <el-input
                          v-model="foreignForm.ownerPhone"
                          clearable
                          v-show="foreignForm.accountId"
                          disabled
                        />
                      </el-form-item>
                    </el-col>
                    <el-col :span="6">
                      <el-form-item label="性别：" prop="gender">
                        <el-select
                          v-model="foreignForm.gender"
                          placeholder="请选择性别"
                          clearable
                        >
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
                    <el-col :span="6">
                      <el-form-item label="证件类型：" prop="cardType">
                        <el-select
                          v-model="foreignForm.cardType"
                          placeholder="请选择证件类型"
                          clearable
                        >
                          <el-option
                            v-for="i in cardTypeList"
                            :key="i.dictValue"
                            :value="i.dictValue"
                            :label="i.dictLabel"
                          />
                        </el-select>
                      </el-form-item>
                    </el-col>
                    <el-col :span="6">
                      <el-form-item label="证件号码" prop="cardNo">
                        <el-input
                          v-model="foreignForm.cardNo"
                          clearable
                          placeholder="请输入证件号码"
                        />
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="8">
                      <el-form-item label="证件正面照：" prop="cardFont">
                        <image-upload
                          v-model="foreignForm.cardFont"
                          :limit="1"
                        />
                      </el-form-item>
                    </el-col>
                    <el-col :span="8">
                      <el-form-item label="证件反面照：" prop="cardBack">
                        <image-upload
                          v-model="foreignForm.cardBack"
                          :limit="1"
                        />
                      </el-form-item>
                    </el-col>
                    <el-col :span="6">
                      <el-form-item label="人脸照片：" prop="faceUrl">
                        <image-upload
                          v-model="foreignForm.faceUrl"
                          :limit="1"
                        />
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="6">
                      <el-form-item
                        label="户籍详情地址："
                        prop="domicileAddress"
                      >
                        <el-input
                          v-model="foreignForm.domicileAddress"
                          clearable
                          placeholder="请输入户籍详情地址"
                        />
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>

              <el-row>
                <h4 class="form-header">业主详情信息</h4>
                <el-col style="margin-left: 6%">
                  <el-row>
                    <el-col :span="5">
                      <el-form-item label="政治面貌" prop="politicsStatus">
                        <el-select
                          v-model="foreignForm.politicsStatus"
                          placeholder="请选择政治面貌"
                          clearable
                        >
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
                          v-model="foreignForm.religion"
                          clearable
                          placeholder="请输入宗教信仰"
                        />
                      </el-form-item>
                    </el-col>
                    <el-col :span="5">
                      <el-form-item label="籍贯" prop="nativePlace">
                        <el-input
                          v-model="foreignForm.nativePlace"
                          placeholder="请输入籍贯"
                        />
                      </el-form-item>
                    </el-col>
                    <el-col :span="5">
                      <el-form-item label="民族" prop="nation">
                        <el-input
                          v-model="foreignForm.nation"
                          clearable
                          placeholder="请输入民族"
                        />
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="5">
                      <el-form-item label="婚姻情况" prop="marriageStatus">
                        <el-select
                          v-model="foreignForm.marriageStatus"
                          placeholder="请选择婚姻情况"
                          clearable
                        >
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
                          v-model="foreignForm.military"
                          clearable
                          placeholder="请输入兵役情况"
                        />
                      </el-form-item>
                    </el-col>
                    <el-col :span="5">
                      <el-form-item label="文化程度" prop="eduLevel">
                        <el-input
                          v-model="foreignForm.edu_level"
                          placeholder="请输入文化程度"
                        />
                      </el-form-item>
                    </el-col>
                    <el-col :span="5">
                      <el-form-item label="国籍" prop="nationality">
                        <el-input
                          v-model="foreignForm.nationality"
                          clearable
                          placeholder="请输入国籍"
                        />
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="5">
                      <el-form-item label="人口类型" prop="population">
                        <el-select
                          v-model="foreignForm.population"
                          placeholder="请选择"
                          clearable
                        >
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
                          v-model="foreignForm.career"
                          clearable
                          placeholder="请输入职业"
                        />
                      </el-form-item>
                    </el-col>
                    <el-col :span="5">
                      <el-form-item label="QQ号码" prop="qqNumber">
                        <el-input
                          v-model="foreignForm.qqNumber"
                          clearable
                          placeholder="请输入QQ号码"
                        />
                      </el-form-item>
                    </el-col>
                    <el-col :span="5">
                      <el-form-item label="邮箱地址" prop="email">
                        <el-input
                          v-model="foreignForm.email"
                          clearable
                          placeholder="请输入邮箱地址"
                        />
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="20">
                      <el-form-item label="个人介绍 / 备注" prop="selfIntro">
                        <el-input
                          v-model="foreignForm.selfIntro"
                          clearable
                          placeholder="请输入个人介绍"
                        />
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-col>
              </el-row>
              <el-divider />
            </el-form>
          </el-tab-pane>
        </el-tabs>
      </el-form>
      <el-row>
        <el-col :span="4" style="margin-top: 5px; float: right">
          <el-button
            type="primary"
            @click="addOwner"
            v-show="defaultTab === 'foreign'"
            >确 定</el-button
          >
          <el-button @click="cancelTransferOwnership">取 消</el-button>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import {
  listPageRooms,
  treeRoom,
  insertBuildingBatch,
  insertUnitAndRoomBatch,
  updateBuilding,
  updateUnit,
  deleteBuilding,
  deleteUnit,
  insertRoomBatch,
  listRoomNamesByCondition,
  listUnitsByCommunityId,
  updateRoomBatch,
  deleteRoomBatch,
  getRoomDetail,
  updateRoom,
  getRoomGraphData,
  roomSearchListPage,
  transferOwnership,
  roomFamilyList,
  confirmRoom,
  bachUpdateRoomStatus,
} from "@/api/estate/center/room";
import { getBuildingList, getAllCommunityList } from "@/api/estate/community";
import { getToken } from "@/utils/auth";
import { add } from "@/api/estate/center/personnelFileManage/ownerFile";
import {
  getCommunityCarportList,
  updateDetail,
} from "@/api/estate/center/buildingManage/carPositionManage/carPositionManage";
export default {
  name: "index",
  created() {
    // 左侧树数据加载
    this.treeList();

    this.getDicts("sys_plate_color").then((response) => {
      this.plateColorList = response.data;
    });
    this.getDicts("vehicle_type").then((response) => {
      this.vehicleTypeList = response.data;
    });
    this.getDicts("property_of_house").then((response) => {
      this.defaultRoomType = response.data;
    });

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
    },
  },
  data() {
    // 自定义校验
    const insertUnitAndRoomFloorNumRule = (rule, value, callback) => {
      if (
        this.insertUnitAndRoomBatchForm.roomNum !== undefined &&
        this.insertUnitAndRoomBatchForm.floorsNum === undefined
      ) {
        callback(new Error("楼层数/单元不能为空"));
      } else {
        callback();
      }
    };
    const insertUnitAndRoomRoomNumRule = (rule, value, callback) => {
      if (
        this.insertUnitAndRoomBatchForm.floorsNum !== undefined &&
        this.insertUnitAndRoomBatchForm.roomNum === undefined
      ) {
        callback(new Error("每层户数/单元不能为空"));
      } else {
        callback();
      }
    };
    return {
      // 树筛选文本
      filterText: "",
      // 列表选中的ids
      ids: [],
      isAdd: false,
      ownerRelationOptions: [],
      cardTypeOptions: [],
      politicsStatusOptions: [],
      roomStatus: undefined,
      roomStatusOptions: [
        { value: "1", label: "已售未拿房" },
        { value: "2", label: "入住" },
        { value: "4", label: "已售已拿房" },
      ],
      // 页面显示
      // 主页面显示
      mainShow: true,
      // 房屋信息导入参数
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
        url: process.env.VUE_APP_BASE_API + "/estate/room/importData",
      },
      // 主页面内部列表显示
      listShow: true,
      // 主页面内部图形显示
      graphShow: false,
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
      // rentalTypeList: [
      //   { "dictValue": "0", "dictLabel": "售" },
      //   { "dictValue": "1", "dictLabel": "年租" },
      //   {"dictValue":"2","dictLabel":"月租"}
      // ],
      carportNoList: [],
      // tree
      currentNodeKey: undefined,

      // 图片
      // 用户导入参数
      upload: {
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/file/upload",
      },

      // 外来人员表单数据
      foreignForm: {
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
        eduLevel: "",
        nationality: "",
        accountId: "",
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
        roomStatus: undefined,
      },
      ownerQueryParams: {
        communityId: undefined,
        pageNum: 1,
        pageSize: 10,
        ownerName: undefined,
        ownerPhone: undefined,
        cardNo: undefined,
      },
      // 过户参数
      transferOwnershipParams: {
        roomId: undefined,
        ownerId: undefined,
        licenseUrl: "",
      },
      // 房号下拉数据
      selectRoomNumberList: [],
      // 身份类型下拉数据
      cardTypeList: [],
      // 楼号下拉数据
      selectFloorList: [],
      // 单元下拉数据
      selectRoomUnitList: [],
      vehicleTypeList: [],
      plateColorList: [],
      defaultRoomStatus: [
        { id: 0, name: "未售", background: "#1b88e7" },
        { id: 1, name: "已售未拿房", background: "#e8e8e8" },
        { id: 2, name: "入住", background: "#1be751" },
        { id: 3, name: "装修", background: "#efa648" },
        { id: 4, name: "已售已拿房", background: "#e2cc00" },
        { id: 5, name: "出租", background: "#00d1ff" },
        // {id: 6, name: '合租', background: '#00b4cd'},
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
        { id: 1, name: "框架结构" },
        { id: 2, name: "钢结构" },
        { id: 3, name: "钢筋混泥土结构" },
        { id: 4, name: "混合结构" },
        { id: 5, name: "砖木结构" },
        { id: 6, name: "其他结构" },
      ],
      relationList: [
        { label: "本人", value: "0", isRead: "0" },
        { label: "配偶", value: "1", isRead: "0" },
        { label: "父母", value: "2", isRead: "0" },
        { label: "子女", value: "3", isRead: "0" },
        { label: "其它", value: "4", isRead: "0" },
        { label: "租客", value: "5", isRead: "0" },
      ],
      politicsStatus: [
        { label: "党员", value: "1" },
        { label: "团员", value: "2" },
        { label: "其它", value: "3" },
      ],
      marriageStatus: [
        { label: "未婚", value: "0" },
        { label: "已婚", value: "1" },
      ],
      population: [
        { label: "户籍人口", value: "0" },
        { label: "常驻人口", value: "1" },
        { label: "暂住人口", value: "2" },
      ],
      gender: [
        { label: "男", value: "0" },
        { label: "女", value: "1" },
      ],

      // 左侧tree数据
      communityList: [],
      allCommunityList: [],
      // tree默认props
      defaultProps: {
        children: "children",
        label: "name",
      },
      // 列表数据
      roomList: [],
      isPlain: true,
      defaultTab: "family",
      // 单元房屋批量添加是否显示弹出层
      insertRoomBatchOpen: false,
      // 单元房屋批量添加表单参数
      insertRoomBatchForm: {},
      // 表单校验
      insertRoomBatchRules: {
        roomNum: [
          { required: true, message: "每层户数不能为空", trigger: "blur" },
        ],
        floorsNum: [
          { required: true, message: "楼层数不能为空", trigger: "blur" },
        ],
        deliveryTime: [
          { required: true, message: "请输入交房时间", trigger: "blur" },
        ],
        totalArea: [
          {
            required: true,
            message: "请输入房屋面积",
            trigger: ["blur", "change"],
          },
        ],
      },

      // 单元房屋批量添加是否显示弹出层
      insertUnitAndRoomBatchOpen: false,
      updateUnitOpen: false,
      // 单元房屋批量添加表单参数
      insertUnitAndRoomBatchForm: {},
      updateUnitForm: { unitName: "" },
      // 表单校验
      insertUnitAndRoomBatchRules: {
        startUnitNum: [
          { required: true, message: "起始单元不能为空", trigger: "blur" },
        ],
        endUnitNum: [
          { required: true, message: "结尾单元不能为空", trigger: "blur" },
        ],
        floorsNum: [
          { validator: insertUnitAndRoomFloorNumRule, trigger: "blur" },
        ],
        roomNum: [{ validator: insertUnitAndRoomRoomNumRule, trigger: "blur" }],
      },

      // 楼宇批量添加是否显示弹出层
      buildingBatchInsertOpen: false,
      // 楼宇修改是否显示弹出层
      updateBuildingOpen: false,
      // 楼宇批量添加表单参数
      insertBuildingBatchForm: {},
      // 楼宇修改表单参数
      updateBuildingForm: { buildingName: "" },
      // 表单校验
      buildingBatchInsertRules: {
        buildingName: [
          { required: true, message: "楼宇首名称不能为空", trigger: "blur" },
        ],
        startBuildingNum: [
          { required: true, message: "起始楼宇不能为空", trigger: "blur" },
        ],
        endBuildingNum: [
          { required: true, message: "结尾楼宇不能为空", trigger: "blur" },
        ],
      },

      // 表单校验
      rules: {
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
        gender: [{ required: true, message: "性别不能为空", trigger: "blur" }],
        cardType: [
          {
            required: true,
            message: "证件类型不能为空",
            trigger: ["blur", "change"],
          },
        ],
        licenseUrl: [
          {
            required: true,
            message: "请上传房产证明",
            trigger: ["blur", "change"],
          },
        ],
        cardNo: [
          //证件验证
          { required: true, message: "请输入证件号码", trigger: "blur" },
        ],
        cardFont: [
          { required: true, message: "请选择证件照正面", trigger: "blur" },
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
        owner_relation: [
          {
            required: true,
            message: "请选择户主关系",
            trigger: ["blur", "change"],
          },
        ],
        building_id: [
          {
            required: true,
            message: "请选择所属楼宇",
            trigger: ["blur", "change"],
          },
        ],
        unit_id: [
          {
            required: true,
            message: "请选择所属单元",
            trigger: ["blur", "change"],
          },
        ],
        room_id: [
          {
            required: true,
            message: "请选择所属房间",
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
        deliveryTime: [
          { required: true, message: "请输入交房时间", trigger: "blur" },
        ],
        totalArea: [
          {
            required: true,
            message: "请输入房屋面积",
            trigger: ["blur", "change"],
          },
        ],
      },
      isDisabled: false,
      updateRoomBatchOpen: false,
      updateRoomBatchForm: {},
      updateRoomBatchRules: {
        deliveryTime: [
          { required: true, message: "请输入交房时间", trigger: "blur" },
        ],
        totalArea: [
          {
            required: true,
            message: "请输入房屋面积",
            trigger: ["blur", "change"],
          },
        ],
      },
      updateRoomStatusOpen: false,
      updateRoomOpen: false,
      updateRoomViewOpen: false,
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
      updateRoomRules: {
        deliveryTime: [
          { required: true, message: "请输入交房时间", trigger: "blur" },
        ],
        totalArea: [
          {
            required: true,
            message: "请输入房屋面积",
            trigger: ["blur", "change"],
          },
        ],
      },
      detailForm: {},
      inhabitantList: [],

      imageUrl: "",
      cardFontImageUrl: "",
      licenseUrl: "",
      cardBackImageUrl: "",
      faceUrl: "",
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
  methods: {
    /** 导入按钮操作 */
    handleImport() {
      this.uploadImprot.title = "房屋信息导入";
      this.uploadImprot.open = true;
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download(
        "estate/room/importTemplate",
        {},
        `房屋管理导入模板_${new Date().getTime()}.xlsx`
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
      this.treeList();
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.uploadImprot.submit();
    },
    getAllCommunityList() {
      getAllCommunityList().then((response) => {
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
      this.ids = selection.map((item) => item.id);
      this.multiple = !selection.length;
    },
    // 获取分页列表数据
    getList() {
      listPageRooms(this.queryParams)
        .then((response) => {
          this.roomList = response.rows;
          this.total = response.total;
          this.loading = false;
        })
        .catch(() => {
          this.loading = false;
        });
    },

    // 分页获取业主列表
    searchOwner() {
      this.ownerQueryParams.roomId = this.transferOwnershipParams.roomId;
      roomSearchListPage(this.ownerQueryParams)
        .then((response) => {
          this.ownerList = response.rows;
          this.ownerTotal = response.total;
        })
        .catch(() => {});
    },
    // 过户
    transferOwnership(ownerId, ownerName) {
      var licenseUrl = this.detailForm.licenseUrl;
      if (licenseUrl === "" || licenseUrl === undefined) {
        this.$modal.msgWarning("请上传房产证明！！！");
        return;
      }

      this.transferOwnershipParams.licenseUrl = licenseUrl;
      this.transferOwnershipParams.ownerId = ownerId;
      let $this = this;
      this.$modal
        .confirm('是否过户"' + ownerName + '"？')
        .then(function () {
          return transferOwnership($this.transferOwnershipParams);
        })
        .then(() => {
          this.$modal.msgSuccess("过户成功");
          this.licenseUrl = "";
          this.cancelTransferOwnership();
          this.getList();
        })
        .catch((err) => {});
    },
    // 获取证件类型
    getCardType() {
      this.getDicts("sys_certificate").then((e) => {
        this.cardTypeList = e.data;
      });
    },
    treeList() {
      // 左侧树数据加载
      treeRoom().then((response) => {
        this.communityList = response.data;
        this.queryParams.id = this.communityList[0].id;
        this.ownerQueryParams.communityId = this.communityList[0].id;
        this.currentNodeKey = this.communityList[0].key;
        this.selectData = this.communityList[0];
        this.selectData.communityId = this.communityList[0].id;
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.currentNodeKey); //一定要加这个选中了否则样式没有出来
        });
        this.getCardType();
        this.getList();
      });
    },
    reloadTreeList() {
      // 左侧树数据加载
      treeRoom().then((response) => {
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
    /** 楼宇操作 */
    handleUpdateBuilding() {
      // this.reset();
      this.updateBuildingOpen = true;
      this.title = "修改楼栋";
      this.updateBuildingForm.buildingId = this.queryParams.id;
    },
    handleBuildingBatchInsert() {
      // this.reset();
      this.buildingBatchInsertOpen = true;
      this.title = "批量添加楼宇";
      this.insertBuildingBatchForm.communityId = this.queryParams.id;
    },
    submitInsertBuildingBatchForm() {
      this.$refs["insertBuildingBatchForm"].validate((valid) => {
        if (valid) {
          insertBuildingBatch(this.insertBuildingBatchForm).then((response) => {
            this.$modal.msgSuccess("添加成功");
            this.buildingBatchInsertOpen = false;
            this.reloadTreeList();
          });
        }
      });
    },
    submitUpdateBuildingForm() {
      this.$refs["updateBuildingForm"].validate((valid) => {
        if (valid) {
          updateBuilding(this.updateBuildingForm).then((response) => {
            this.$modal.msgSuccess("修改成功");
            this.updateBuildingOpen = false;
            this.reloadTreeList();
          });
        }
      });
    },

    // 删除操作
    handleDeleteBuilding() {
      let $this = this;
      this.$modal
        .confirm('是否确认删除楼栋"' + this.selectData.name + '"？')
        .then(function () {
          return deleteBuilding($this.selectData.id);
        })
        .then(() => {
          this.reloadTreeList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch((err) => {});
    },
    /** 楼宇操作 */

    /** 单元操作 */
    handleUpdateUnit() {
      // this.reset();
      this.updateUnitOpen = true;
      this.title = "修改单元信息";
      this.updateUnitForm.unitId = this.queryParams.id;
    },
    isValidID(id) {
      const regex =
        /^[1-9]\d{5}(18|19|20|21|22)?\d{2}(0[1-9]|1[0-2])(0[1-9]|[12]\d|3[01])\d{3}(\d|[Xx])$/;
      return regex.test(id);
    },
    // 新增外来人员
    addOwner() {
      this.foreignForm["communityId"] = this.detailForm.communityId;
      this.foreignForm["roomId"] = this.detailForm.roomId;
      this.foreignForm["unitId"] = this.detailForm.unitId;
      this.foreignForm["buildingId"] = this.detailForm.buildingId;
      this.foreignForm["dataSource"] = "0";
      this.foreignForm["ownerRelation"] = "0";
      const $this = this;

      var cardNo = this.foreignForm.cardNo;
      if (this.foreignForm.cardType == "I") {
        if (!this.isValidID(cardNo)) {
          this.$modal.msgWarning("证件号码格式有误！");
          return;
        }
      }

      this.$refs["foreignForm"].validate((valid) => {
        if (valid) {
          this.$modal
            .confirm("是否确认添加业主，并过户给他？")
            .then(function () {
              return add($this.foreignForm);
            })
            .then(() => {
              this.$modal.msgSuccess("过户成功");
              this.foreignForm = {
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
                eduLevel: "",
                nationality: "",
                accountId: "",
              };
              this.faceUrl = "";
              this.cardFontImageUrl = "";
              this.cardBackImageUrl = "";
              this.licenseUrl = "";
              this.cancelTransferOwnership();
              this.getList();
            })
            .catch((err) => {});
        }
      });
    },
    carportNatureChange(item1) {
      item1.disType = item1.carportNature != "0";
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
    getRentalType(item1) {
      if (item1.rentalType == "0") {
        item1.rentalStart = "";
        item1.rentalEnd = "";
      }
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
    handleInsertUnitAndRoomBatch() {
      this.insertUnitAndRoomBatchOpen = true;
      this.title = "批量添加单元与房间";
      this.insertUnitAndRoomBatchForm.buildingId = this.queryParams.id;
    },
    submitInsertUnitAndRoomBatchForm() {
      this.$refs["insertUnitAndRoomBatchForm"].validate((valid) => {
        if (valid) {
          insertUnitAndRoomBatch(this.insertUnitAndRoomBatchForm).then(
            (response) => {
              this.$modal.msgSuccess("添加成功");
              this.insertUnitAndRoomBatchOpen = false;
              this.reloadTreeList();
            }
          );
        }
      });
    },
    submitUpdateUnitForm() {
      this.$refs["updateUnitForm"].validate((valid) => {
        if (valid) {
          updateUnit(this.updateUnitForm).then((response) => {
            this.$modal.msgSuccess("修改成功");
            this.updateUnitOpen = false;
            this.reloadTreeList();
          });
        }
      });
    },
    // 删除操作
    handleDeleteUnit() {
      const $this = this;
      this.$modal
        .confirm('是否确认删除单元"' + this.selectData.name + '"？')
        .then(function () {
          return deleteUnit($this.selectData.id);
        })
        .then(() => {
          this.reloadTreeList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 单元操作 */

    /** 房间操作 */
    handleInsertRoomBatch() {
      // this.reset();
      this.insertRoomBatchOpen = true;
      this.title = "批量添加房间";
      this.insertRoomBatchForm.unitId = this.queryParams.id;
    },
    submitInsertRoomBatchForm() {
      this.$refs["insertRoomBatchForm"].validate((valid) => {
        if (valid) {
          insertRoomBatch(this.insertRoomBatchForm).then((response) => {
            this.$modal.msgSuccess("添加成功");
            this.insertRoomBatchOpen = false;
            this.getList();
          });
        }
      });
    },
    submitUpdateRoomBatchForm() {
      this.updateRoomBatchForm.communityId = this.selectData.communityId;
      switch (this.selectData.level) {
        case 2:
          this.updateRoomBatchForm.roomNumbers = [this.selectData.id];
          break;
        case 3:
          this.updateRoomBatchForm.unitIds = [this.selectData.id];
          break;
      }

      this.$refs["updateRoomBatchForm"].validate((valid) => {
        if (valid) {
          this.updateRoomBatchForm.roomUrl = this.imageUrl;
          updateRoomBatch(this.updateRoomBatchForm).then((response) => {
            this.$modal.msgSuccess("批量修改成功");
            this.updateRoomBatchOpen = false;
            this.reloadTreeList();
          });
        }
      });
    },
    //增加业主
    saveOwner() {
      this.ownerList.push({
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
      });
    },
    //删除业主
    deleteOwner() {
      if (this.ownerList.length == 1) {
        this.$modal.msgWarning("请保留至少一条业主信息");
        return;
      }

      this.ownerList = this.ownerList.filter(function (v, i, ar) {
        return i !== ar.length - 1;
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
    deleteSpaceVehicle() {
      this.spaceVehicleList = this.spaceVehicleList.filter(function (v, i, ar) {
        return i !== ar.length - 1;
      });
    },

    // 修改房屋
    submitUpdateRoomForm() {
      this.$refs["updateRoomForm"].validate((valid) => {
        if (valid) {
          if (this.ownerList) {
            var ownerList = this.ownerList.filter(
              (item) => item.ownerRelation == "0"
            );
            if (ownerList.length > 1) {
              this.$modal.msgWarning("户主关系【本人】只能选择一次");
              return;
            }
            if (ownerList.length == 0) {
              this.$modal.msgWarning("户主关系中至少要有信息是【本人】");
              return;
            }

            this.updateRoomForm.ownerList = this.ownerList;
          }
          console.log(
            "this.spaceVehicleList.length>>",
            this.spaceVehicleList.length
          );
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
          var totalArea = this.updateRoomForm.totalArea;
          if (totalArea == null || totalArea <= 0) {
            this.$modal.msgError("房屋面积不能为空");
            return;
          }
          updateRoom(this.updateRoomForm).then((response) => {
            this.$modal.msgSuccess("修改成功");
            this.updateRoomOpen = false;
            this.getList();
          });
        }
      });
    },
    handleUpdateRoomStatus(row) {
      this.roomStatus = undefined;
      this.updateRoomStatusOpen = true;
      var ids = row.id || this.ids;
      this.ids = ids;
    },
    roomStatusCancel() {
      this.updateRoomStatusOpen = false;
      this.getList();
    },
    updateRoomStatus() {
      var roomStatus = this.roomStatus;
      if (!roomStatus) {
        this.$modal.msgWarning("请选择房屋状态");
        return;
      }

      bachUpdateRoomStatus(this.ids, roomStatus).then((res) => {
        this.getList();
        this.$modal.msgSuccess("修改房屋状态成功");
        this.updateRoomStatusOpen = false;
      });
    },
    handleViewRoom(row) {
      this.title = "房屋信息";

      getRoomDetail(row.id, row.communityId).then((response) => {
        this.updateRoomForm = response.data;
        //房屋信息
        this.getCarportList(row.communityId);
        if (response.data.ownerList) {
          this.ownerList = response.data.ownerList;
        } else {
          this.ownerList = [
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
          ];
        }
        if (response.data.spaceVehicleList) {
          this.spaceVehicleList = response.data.spaceVehicleList;
          // this.spaceVehicleList.forEach(item => {
          //   item.carportNo=parseFloat(item.carportNo)
          //  })
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
        this.updateRoomViewOpen = true;
      });
    },
    //修改房屋信息
    handleUpdateRoom(row) {
      this.updateRoomForm = {};
      this.imageUrl = "";
      this.updateRoomOpen = true;
      this.title = "修改房屋信息";
      this.isDisabled = false;
      console.log("XXX:", row);

      getRoomDetail(row.roomId, row.communityId).then((response) => {
        this.updateRoomForm = response.data;
        //房屋信息
        this.getCarportList(this.updateRoomForm.communityId);
        console.log("response.data.ownerList>>>", response.data.ownerList);
        if (
          response.data.ownerList != null &&
          response.data.ownerList.length >= 1
        ) {
          this.ownerList = response.data.ownerList;
        } else {
          this.ownerList = [
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
          ];
        }
        // if (response.data.spaceVehicleList) {
        //   this.spaceVehicleList = response.data.spaceVehicleList;
        //   // this.spaceVehicleList.forEach(item => {
        //   //   item.carportNo=parseFloat(item.carportNo)
        //   //  })
        // } else {
        //   this.spaceVehicleList =[{
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
        //     vehicleBrand:undefined,
        // }]
        // }

        this.updateRoomForm.roomType = this.updateRoomForm.roomType + "";
      });
    },
    //查询房屋信息信息
    getCarportList(communityId) {
      getCommunityCarportList(communityId).then((res) => {
        this.carportNoList = res.data;
      });
    },

    cancelTransferOwnership() {
      this.mainShow = true;
      this.transferOwnershipShow = false;
      this.detailForm = {};
      this.ownerList = [];
      this.resetForm("ownerQueryForm");
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
    /**
     * 表格视图切换
     */
    switchTable() {
      this.isPlain = true;
      this.listShow = true;
      this.graphShow = false;
      this.getList();
    },
    /**
     * 图形视图切换
     */
    switchGraph() {
      const level = this.selectData.level;
      if (level === 1) {
        this.$modal.msg("图形视图只能在楼栋和单元下查看！");
        return;
      }
      this.isPlain = false;
      this.listShow = false;
      this.graphShow = true;

      this.listRoomGraphs();
    },
    listRoomGraphs() {
      getRoomGraphData(this.selectData).then((response) => {
        this.roomGraphData = response.data;
      });
    },
    openUrl(url) {
      window.open(url);
    },
    // 树选中节点
    pickTree(data, node) {
      if (data.level === 1 && this.graphShow) {
        this.$modal.msg("图形视图只能在楼栋和单元下查看！");
        return;
      }

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
    /**
     * 新增
     */
    insert() {
      switch (this.queryParams.level) {
        // 新增楼宇
        case 1:
          this.insertBuildingBatchForm = {};
          this.handleBuildingBatchInsert();
          break;
        // 新增单元与房屋
        case 2:
          this.insertUnitAndRoomBatchForm = {};
          this.handleInsertUnitAndRoomBatch();
          break;
        case 3:
          this.insertRoomBatchForm = {};
          this.handleInsertRoomBatch();
          break;
      }
    },
    /**
     * 批量删除房屋
     */
    deleteRoomBatch() {
      const $this = this;
      console.log(this);
      this.$modal
        .confirm("是否确认删除所选房屋？")
        .then(function () {
          return deleteRoomBatch($this.ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch((err) => {});
    },
    /**
     * 批量修改房屋
     */
    updateRoomBatch() {
      this.imageUrl = "";
      this.title = "批量添加房间";
      this.updateRoomBatchOpen = true;
      this.updateRoomBatchForm.communityId = this.selectData.communityId;
      this.updateRoomBatchForm = {};
      switch (this.selectData.level) {
        case 1:
          getBuildingList(this.selectData.id).then((response) => {
            this.selectFloorList = response.data;
          });
          listRoomNamesByCondition({
            level: this.selectData.level,
            id: this.selectData.id,
          }).then((response) => {
            this.selectRoomNumberList = response.data;
          });
          listUnitsByCommunityId(this.selectData.communityId).then(
            (response) => {
              this.selectRoomUnitList = response.data;
            }
          );
          break;
        case 2:
          listRoomNamesByCondition({
            level: this.selectData.level,
            id: this.selectData.id,
          }).then((response) => {
            this.selectRoomNumberList = response.data;
          });
          listUnitsByCommunityId(this.selectData.communityId).then(
            (response) => {
              this.selectRoomUnitList = response.data;
            }
          );
        case 3:
          listRoomNamesByCondition({
            level: this.selectData.level,
            id: this.selectData.id,
          }).then((response) => {
            this.selectRoomNumberList = response.data;
          });
          break;
      }
    },
    /**
     * 修改
     */
    update() {
      switch (this.selectData.level) {
        // 修改楼栋
        case 2:
          this.handleUpdateBuilding();
          this.updateBuildingForm.buildingName = this.selectData.name;

          break;
        // 修改单元
        case 3:
          this.handleUpdateUnit();
          this.updateUnitForm.unitName = this.selectData.name;
          break;
      }
    },
    remove() {
      switch (this.selectData.level) {
        // 删除楼栋
        case 2:
          this.handleDeleteBuilding();
          break;
        // 删除单元
        case 3:
          this.handleDeleteUnit();
          break;
      }
    },
    viewCancel() {
      this.updateRoomViewOpen = false;
      this.reset();
    },
    // 取消按钮
    cancel() {
      this.buildingBatchInsertOpen = false;
      this.insertUnitAndRoomBatchOpen = false;
      this.insertRoomBatchOpen = false;
      this.updateBuildingOpen = false;
      this.updateUnitOpen = false;
      this.updateRoomBatchOpen = false;
      this.updateRoomOpen = false;
      this.reset();
    },

    // 重置
    reset() {
      this.insertBuildingBatchForm = {};
      this.insertUnitAndRoomBatchForm = {};
      this.insertRoomBatchForm = {};
      this.updateRoomBatchForm = {};
    },

    handleAvatarSuccess(res, file) {
      if (res.code !== 200) {
        this.$message.error("上传图片失败!");
        return;
      }
      this.updateRoomForm.roomUrl = res.data.url;
      this.updateRoomBatchForm.roomUrl = res.data.url;
      this.imageUrl = URL.createObjectURL(file.raw);
    },
    // 上传证件正面照
    cardFontUpload(res, file) {
      this.foreignForm.cardFont = res.data.url;
      this.cardFontImageUrl = res.data.url;
    },
    // 上传证件反面照
    cardBackUpload(res, file) {
      this.foreignForm.cardBack = res.data.url;
      this.cardBackImageUrl = res.data.url;
    },
    // 人脸照片
    faceUpload(res, file) {
      this.foreignForm.faceUrl = res.data.url;
      this.faceUrl = res.data.url;
    },
    beforeAvatarUpload(file) {
      //const isJPG = file.type === 'image/jpeg';
      const isLt2M = file.size / 1024 / 1024 < 15;

      // if (!isJPG) {
      //   this.$message.error('请上传 JPG 格式!');
      // }
      if (!isLt2M) {
        this.$message.error("图片大小不能超过 15MB!");
      }
      return isJPG && isLt2M;
    },
    // 房间状态名称转换
    roomStatusStr(val) {
      for (const index in this.defaultRoomStatus) {
        if (this.defaultRoomStatus[index].id == val) {
          return this.defaultRoomStatus[index].name;
        }
      }
      return "未知";
    },
    // 与户主关系名称转换
    relationStr(val) {
      for (const index in this.relationList) {
        if (this.relationList[index].value == val) {
          return this.relationList[index].label;
        }
      }
      return "未知";
    },
    // 房屋属性转换
    roomTypeStr(val) {
      for (const index in this.defaultRoomType) {
        if (this.defaultRoomType[index].dictValue == val) {
          return this.defaultRoomType[index].dictLabel;
        }
      }
      return "未知";
    },
    // 房屋结构转换
    roomStructureStr(val) {
      for (const index in this.defaultRoomStructure) {
        if (this.defaultRoomStructure[index].id == val) {
          return this.defaultRoomStructure[index].name;
        }
      }
      return "未知";
    },
    // 根据房屋状态获取背景颜色
    getBackgroundColor(roomStatus) {
      for (const index in this.defaultRoomStatus) {
        if (this.defaultRoomStatus[index].id == roomStatus) {
          const { background } = this.defaultRoomStatus[index];
          return { background: background };
        }
      }
    },
    // 房屋状态统计
    handlerRoomStatusStatistics(id) {
      if (this.roomGraphData.hasOwnProperty("roomStatusStatistics")) {
        return this.roomGraphData.roomStatusStatistics[id] === undefined
          ? 0
          : this.roomGraphData.roomStatusStatistics[id];
      }
      return 0;
    },
    // 打开过户页面
    handleTransferOwnership(row) {
      this.transferOwnershipShow = true;
      this.transferOwnershipParams.roomId = row.id;
      this.mainShow = false;
      this.defaultTab = "family";
      getRoomDetail(row.id, row.communityId).then((response) => {
        this.detailForm = response.data;
      });
      roomFamilyList(row.id).then((response) => {
        this.inhabitantList = response.data;
      });
      this.getCardType();
      this.searchOwner();
    },
    //收房
    handleConfirmRoom(id) {
      this.$modal
        .confirm("是否确认收房？")
        .then(function () {
          return confirmRoom(id);
        })
        .then(() => {
          this.$modal.msgSuccess("收房成功");
          this.getList();
        })
        .catch((err) => {});
    },
    //上传产权
    uploadLicenseUrl(res, file) {
      this.foreignForm.licenseUrl = res.data.url;
      this.licenseUrl = res.data.url;
    },
    otherLicenseUrl(res, file) {
      this.transferOwnershipParams.licenseUrl = res.data.url;
      this.licenseUrl = res.data.url;
    },
    handleClick() {
      this.transferOwnershipParams.licenseUrl = "";
      this.licenseUrl = "";
    },
  },
};
</script>

<style scoped>
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
  border-color: #409eff;
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

<template>
  <div class="app-container">
    <el-form
      :model="queryParams"
      ref="queryForm"
      size="small"
      :inline="true"
      v-show="showSearch"
      label-width="68px"
    >
      <el-form-item label="投票标题" prop="voteName">
        <el-input
          v-model="queryParams.voteName"
          placeholder="请输入投票标题"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="投票单号" prop="voteNo">
        <el-input
          v-model="queryParams.voteNo"
          placeholder="请输入投票单号"
          clearable
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="所属小区" prop="communityId" label-width="80px">
        <el-select
          v-model="queryParams.communityId"
          placeholder="请选择"
          style="width: 240px"
        >
          <el-option
            v-for="(item, index) in currentList"
            :key="index"
            :label="item.communityName"
            :value="item.communityId"
          ></el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="投票状态" prop="state">
        <el-select v-model="queryParams.state" placeholder="请选择投票状态">
          <el-option label="未开始" value="未开始"></el-option>
          <el-option label="投票中" value="投票中"></el-option>
          <el-option label="已结束" value="已结束"></el-option>
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
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          @click="handleAdd"
          >新增</el-button
        >
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
      <el-table-column label="投票单号" align="center" prop="voteNo" />
      <el-table-column label="所属小区" align="center" prop="communityId">
        <template slot-scope="scope">
          {{ getCommunityName(scope.row.communityId) }}
        </template>
      </el-table-column>
      <el-table-column label="投票标题" align="center" prop="voteName" />
      <el-table-column label="投票开始时间" align="center" prop="startTime" />
      <el-table-column label="投票结束时间" align="center" prop="endTime" />
      <el-table-column label="投票状态" align="center" prop="state" />
      <el-table-column label="是否延期" align="center" prop="isYq">
        <template slot-scope="scope">
          {{ scope.row.isYq == "0" ? "否" : "是" }}
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
            icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
            v-if="scope.row.state == '未开始'"
            >修改</el-button
          >
          <!-- <br v-if="scope.row.state=='未开始'"> -->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleUpdateVote(scope.row)"
            v-if="scope.row.state == '投票中'"
            >修改投票人</el-button
          >
          <!-- <br  v-if="scope.row.state=='投票中'"> -->
          <el-button
            size="mini"
            type="text"
            style="color: #f56c6c"
            icon="el-icon-delete"
            @click="handleDelete(scope.row)"
            v-if="scope.row.state != '投票中'"
            >删除</el-button
          >
          <!-- <br v-if="scope.row.state!='投票中'"> -->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-edit"
            @click="handleYq(scope.row)"
            v-if="scope.row.state == '投票中'"
            >延期</el-button
          >
          <!-- <br  v-if="scope.row.state=='投票中'"> -->
          <el-button
            size="mini"
            type="text"
            icon="el-icon-view"
            @click="handleView(scope.row)"
            v-if="scope.row.state != '未开始'"
            >查看结果</el-button
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
    <!-- 查看投票结果 -->
    <el-dialog
      title="投票结果"
      :visible.sync="viewOpen"
      width="90%"
      append-to-body
    >
      <el-form ref="formref" :model="form" label-width="140px" :rules="rules">
        <el-row>
          <el-col :span="7">
            <el-form-item label="投票单号:">
              {{ form.voteNo }}
            </el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="投票标题:">
              {{ form.voteName }}
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="议题名称:">
              {{ form.issuesName }}
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="投票结果:">
              {{ form.state == "已结束" ? form.result : "--" }}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <el-form
        ref="formref"
        label-position="top"
        :model="form"
        label-width="140px"
        :rules="rules"
      >
        <el-row style="width: 100%; background: #fafafa; height: 90px">
          <el-col :span="4" :offset="0.5" style="text-align: center">
            <el-form-item label="应投票数">
              <span style="font-size: 22px">{{ form.voteTotal }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="4" style="text-align: center">
            <el-form-item label="已投票数">
              <span style="font-size: 22px">{{ form.yvoteTotal }}</span>
              <span style="font-size: 12px">{{ form.yvotePercentage }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="4" style="text-align: center">
            <el-form-item label="未反馈">
              <span style="font-size: 22px">{{ form.wvoteTotal }}</span>
              <span style="font-size: 12px">{{ form.wvotePercentage }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="4" style="text-align: center">
            <el-form-item label="总面积">
              <span style="font-size: 22px">{{ form.totalArea }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="4" style="text-align: center">
            <el-form-item label="已投面积">
              <span style="font-size: 22px">{{ form.ytotalArea }}</span>
              <span style="font-size: 12px">{{
                form.ytotalAreaPercentage
              }}</span>
            </el-form-item>
          </el-col>
          <el-col :span="4" style="text-align: center">
            <el-form-item label="未反馈面积">
              <span style="font-size: 22px">{{ form.wtotalArea }}</span>
              <span style="font-size: 12px">{{
                form.wtotalAreaPercentage
              }}</span>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-table :data="form.voteResultList" style="width: 100%">
            <el-table-column align="center" prop="name" label="投票结果">
            </el-table-column>
            <el-table-column
              align="center"
              prop="number"
              label="投票票数"
            ></el-table-column>
            <el-table-column
              align="center"
              prop="percentage"
              label="票数占比(%)"
            >
              <template slot-scope="scope">
                <el-progress
                  v-if="scope.row.ownerStatus == '1'"
                  :text-inside="true"
                  :stroke-width="15"
                  status="success"
                  :percentage="scope.row.percentage"
                ></el-progress>
                <el-progress
                  v-if="scope.row.ownerStatus == '2'"
                  :text-inside="true"
                  :stroke-width="15"
                  status="exception"
                  :percentage="scope.row.percentage"
                ></el-progress>
                <el-progress
                  v-if="scope.row.ownerStatus == '3'"
                  :text-inside="true"
                  :stroke-width="15"
                  :percentage="scope.row.percentage"
                ></el-progress>
              </template>
            </el-table-column>
          </el-table>
        </el-row>

        <el-row style="margin-top: 15px">
          <el-table v-loading="loading2" :data="list2">
            <el-table-column label="楼栋" align="center" prop="buildingName" />
            <el-table-column label="单元楼" align="center" prop="unitName" />
            <el-table-column label="房号" align="center" prop="roomName" />
            <el-table-column label="投票人" align="center" prop="ownerName" />
          </el-table>

          <pagination
            style="margin-bottom: 30px"
            v-show="total2 > 0"
            :total="total2"
            :page.sync="queryParams2.pageNum"
            :limit.sync="queryParams2.pageSize"
            @pagination="getList2"
          />
        </el-row>
      </el-form>
      <el-row>
        <el-col :span="4" :offset="20" style="margin-top: 5px">
          <el-button @click="viewCancel">返 回</el-button>
        </el-col>
      </el-row>
    </el-dialog>

    <!-- 延期 -->
    <el-dialog
      title="投票表决延期"
      :visible.sync="yqOpen"
      width="50%"
      append-to-body
    >
      <el-form ref="yqFormref" :model="form" label-width="140px">
        <el-row v-if="form.id">
          <el-col :span="10">
            <el-form-item label="投票单号:">
              {{ form.voteNo }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="11">
            <el-form-item label="投票时间范围:">
              {{ form.startTime }}
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <!--
              <el-date-picker
                v-model="form.endTime"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="结束日期"
                :picker-options="pickerOptions"
              ></el-date-picker> -->

            <el-date-picker
              v-model="form.endTime"
              value-format="yyyy-MM-dd HH:mm:ss"
              type="datetime"
              placeholder="请选择投票结束时间"
              :picker-options="pickerOptions"
            ></el-date-picker>
          </el-col>
        </el-row>
      </el-form>
      <el-row>
        <el-col :span="6" :offset="17" style="margin-top: 5px">
          <el-button type="primary" @click="yqSubmitForm">提 交</el-button>
          <el-button @click="yqCancel">取 消</el-button>
        </el-col>
      </el-row>
    </el-dialog>

    <!-- 添加/修改投票 -->
    <el-dialog :title="title" :visible.sync="open" width="90%" append-to-body>
      <el-form ref="formref" :model="form" label-width="140px" :rules="rules">
        <el-tabs v-model="activeName">
          <el-tab-pane label="投票信息" name="house">
            <el-row>
              <el-col :span="20">
                <el-row v-if="form.id">
                  <el-col :span="10" :offset="8">
                    <el-form-item label="投票单号:">
                      {{ form.voteNo }}
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="10" :offset="8">
                    <el-form-item label="投票标题:" prop="voteName">
                      <el-input
                        v-model="form.voteName"
                        placeholder="请输入投票标题"
                        clearable
                        style="width: 267px"
                      />
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="10" :offset="8">
                    <el-form-item label="议题名称:" prop="issuesName">
                      <el-input
                        v-model="form.issuesName"
                        placeholder="请输入议题名称"
                        clearable
                        style="width: 267px"
                      />
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="10" :offset="8">
                    <el-form-item label="所属小区" prop="communityId">
                      <el-select
                        v-model="form.communityId"
                        placeholder="请选择所属小区"
                        @change="getBuildingOptions(form.communityId, '0')"
                        style="width: 267px"
                      >
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
                <el-row>
                  <el-col :span="10" :offset="8">
                    <el-form-item label="投票开始时间:" prop="startTime">
                      <el-date-picker
                        v-model="form.startTime"
                        style="width: 267px"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        type="datetime"
                        placeholder="请选择投票开始时间"
                        :picker-options="pickerOptions"
                      ></el-date-picker>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="10" :offset="8">
                    <el-form-item label="投票结束时间:" prop="endTime">
                      <el-date-picker
                        v-model="form.endTime"
                        style="width: 267px"
                        value-format="yyyy-MM-dd HH:mm:ss"
                        type="datetime"
                        placeholder="请选择投票开始时间"
                        :picker-options="pickerOptions"
                      ></el-date-picker>
                    </el-form-item>
                  </el-col>
                </el-row>

                <el-row>
                  <el-col :span="10" :offset="8">
                    <el-form-item label="投票描述:" prop="content">
                      <el-input
                        v-model="form.content"
                        type="textarea"
                        placeholder="请输入投票描述"
                        style="width: 267px"
                      />
                    </el-form-item>
                  </el-col>
                </el-row>
              </el-col>
            </el-row>
          </el-tab-pane>
          <el-tab-pane label="投票范围" name="handle">
            <el-row>
              <el-col :span="20">
                <el-row>
                  <el-col :span="8" :offset="0">
                    <el-form-item label="楼栋:" prop="buildingId">
                      <el-select
                        v-model="form.buildingId"
                        placeholder="请选择"
                        @change="getUnitOptions(form.buildingId, '0')"
                      >
                        <el-option
                          v-for="(item, index) in buildingOptions"
                          :key="index"
                          :label="item.buildingName"
                          :value="item.buildingId"
                        ></el-option>
                      </el-select>
                    </el-form-item>
                  </el-col>
                  <el-col :span="8" :offset="0">
                    <el-form-item label="单元楼:" prop="unitId">
                      <el-select
                        v-model="form.unitId"
                        placeholder="请选择"
                        @change="getBuildingValue(form.unitId)"
                      >
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
                <h4 class="form-header">业主信息</h4>
                <el-row style="margin-left: 93px">
                  <el-form
                    :model="queryParams1"
                    ref="queryForm1"
                    size="small"
                    :inline="true"
                    v-show="showSearch1"
                    label-width="68px"
                  >
                    <el-form-item label="业主名称" prop="ownerName">
                      <el-input
                        v-model="queryParams1.ownerName"
                        placeholder="请输入业主名称"
                        clearable
                      />
                    </el-form-item>
                    <el-form-item>
                      <el-button
                        type="primary"
                        icon="el-icon-search"
                        size="mini"
                        @click="handleQuery1"
                        >搜索</el-button
                      >
                      <el-button
                        icon="el-icon-refresh"
                        size="mini"
                        @click="resetQuery1"
                        >重置</el-button
                      >
                    </el-form-item>
                  </el-form>
                  <el-table v-loading="loading1" :data="list1">
                    <el-table-column
                      label="楼栋"
                      align="center"
                      prop="buildingName"
                    />
                    <el-table-column
                      label="单元楼"
                      align="center"
                      prop="unitName"
                    />
                    <el-table-column
                      label="房号"
                      align="center"
                      prop="roomName"
                    />
                    <el-table-column
                      label="业主名称"
                      align="center"
                      prop="ownerName"
                    />
                    <el-table-column
                      label="业主联系方式"
                      align="center"
                      prop="ownerPhone"
                    />
                    <el-table-column
                      label="与业主关系"
                      align="center"
                      prop="ownerRelation"
                    >
                      <template slot-scope="scope">
                        {{ scope.row.ownerRelation == "0" ? "本人" : "配偶" }}
                      </template>
                    </el-table-column>
                    <el-table-column
                      label="操作"
                      align="center"
                      class-name="small-padding fixed-width"
                    >
                      <template slot-scope="scope">
                        <!-- 删除状态:{{ scope.row.delFlag }} -->
                        <el-button
                          size="mini"
                          type="text"
                          icon="el-icon-edit"
                          @click="handleDelete1(scope.row, '1')"
                          v-if="scope.row.delFlag == '0'"
                          >恢复</el-button
                        >

                        <el-button
                          size="mini"
                          type="text"
                          icon="el-icon-delete"
                          style="color: #f56c6c"
                          @click="handleDelete1(scope.row, '0')"
                          v-if="scope.row.delFlag >= '1'"
                          >删除</el-button
                        >
                      </template>
                    </el-table-column>
                  </el-table>

                  <pagination
                    style="margin-bottom: 30px"
                    v-show="total1 > 0"
                    :total="total1"
                    :page.sync="queryParams1.pageNum"
                    :limit.sync="queryParams1.pageSize"
                    @pagination="getList1"
                  />
                </el-row>
              </el-col>
            </el-row>
          </el-tab-pane>
        </el-tabs>
      </el-form>
      <el-row>
        <el-col :span="4" :offset="20" style="margin-top: 5px">
          <el-button type="primary" @click="submitForm">提 交</el-button>
          <el-button @click="cancel">取 消</el-button>
        </el-col>
      </el-row>
    </el-dialog>

    <!-- 修改投票人 -->
    <el-dialog
      title="修改投票人"
      :visible.sync="voteOpen"
      width="90%"
      append-to-body
    >
      <el-form ref="formref" :model="form" label-width="140px" :rules="rules">
        <el-row>
          <el-col :span="20">
            <el-row style="margin-left: 93px">
              <el-form
                :model="queryParams2"
                ref="queryForm2"
                size="small"
                :inline="true"
                v-show="showSearch2"
                label-width="110px"
              >
                <el-form-item label="楼栋:" prop="buildingId">
                  <el-select
                    v-model="queryParams2.buildingId"
                    placeholder="请选择"
                    @change="getUnitOptions2(queryParams2.buildingId, '0')"
                  >
                    <el-option
                      v-for="(item, index) in buildingOptions"
                      :key="index"
                      :label="item.buildingName"
                      :value="item.buildingId"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="单元楼:" prop="unitId">
                  <el-select
                    v-model="queryParams2.unitId"
                    placeholder="请选择"
                    @change="getRoomOptions2(queryParams2.unitId)"
                  >
                    <el-option
                      v-for="(item, index) in unitOptions"
                      :key="index"
                      :label="item.unitName"
                      :value="item.unitId"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="房号:" prop="roomId">
                  <el-select v-model="queryParams2.roomId" placeholder="请选择">
                    <el-option
                      v-for="(item, index) in roomOptions"
                      :key="index"
                      :label="item.roomName"
                      :value="item.roomId"
                    ></el-option>
                  </el-select>
                </el-form-item>
                <el-form-item label="业主名称:" prop="ownerName">
                  <el-input
                    v-model="queryParams2.ownerName"
                    placeholder="请输入业主名称"
                    clearable
                  />
                </el-form-item>
                <el-form-item>
                  <el-button
                    type="primary"
                    icon="el-icon-search"
                    size="mini"
                    @click="handleQuery2"
                    >搜索</el-button
                  >
                  <el-button
                    icon="el-icon-refresh"
                    size="mini"
                    @click="resetQuery2"
                    >重置</el-button
                  >
                </el-form-item>
              </el-form>
              <el-table v-loading="loading2" :data="list2">
                <el-table-column
                  label="楼栋"
                  align="center"
                  prop="buildingName"
                />
                <el-table-column
                  label="单元楼"
                  align="center"
                  prop="unitName"
                />
                <el-table-column label="房号" align="center" prop="roomName" />
                <el-table-column
                  label="业主名称"
                  align="center"
                  prop="ownerName"
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
                      icon="el-icon-edit"
                      @click="handleSetVote(scope.row)"
                      >设置投票人</el-button
                    >
                  </template>
                </el-table-column>
              </el-table>

              <pagination
                style="margin-bottom: 30px"
                v-show="total2 > 0"
                :total="total2"
                :page.sync="queryParams2.pageNum"
                :limit.sync="queryParams2.pageSize"
                @pagination="getList2"
              />
            </el-row>
          </el-col>
        </el-row>
      </el-form>
    </el-dialog>

    <!-- 房间投票人列表 -->
    <el-dialog
      title="设置投票人"
      :visible.sync="setVoteOpen"
      width="70%"
      append-to-body
    >
      <el-form
        ref="setVoteFormref"
        :model="form"
        label-width="140px"
        :rules="setVoteRules"
      >
        <el-row>
          <el-col :span="10">
            <el-form-item label="房屋坐落:">
              {{ houseAddress }}
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="业主名称:">
              {{ ownerName }}
            </el-form-item>
          </el-col>
        </el-row>
        <h4 class="form-header" style="color: #0558e7">增加投票人</h4>
        <el-row>
          <el-col :span="10">
            <el-form-item label="投票人:" prop="accountId">
              <el-select
                v-model="form.accountId"
                placeholder="请选择"
                style="width: 290px"
              >
                <el-option
                  v-for="(item, index) in ownerOptions"
                  :key="index"
                  :label="item.ownerName"
                  :value="item.accountId"
                  :disabled="item.count == 1"
                ></el-option>
              </el-select>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="40">
            <el-form-item label="添加原因:" prop="setReason">
              <el-input
                type="textarea"
                style="width: 290px"
                v-model="form.setReason"
                placeholder="请输入添加原因"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="20" style="margin-top: 10px">
            <el-button type="primary" @click="handleSetVoteAdd(form)"
              >添 加</el-button
            >
          </el-col>
        </el-row>
        <h4 class="form-header" style="color: #0558e7">投票人信息</h4>
        <el-row>
          <right-toolbar
            :showSearch.sync="showSearch3"
            @queryTable="getList3"
          ></right-toolbar>
        </el-row>
        <el-row>
          <el-table v-loading="loading3" :data="setVoteOwnerList">
            <el-table-column
              label="投票人姓名"
              align="center"
              prop="ownerName"
            />
            <el-table-column
              label="投票人联系方式"
              align="center"
              prop="ownerPhone"
            />
            <el-table-column label="证件类型" align="center" prop="cardType" />
            <el-table-column label="证件号" align="center" prop="cardNo" />
            <el-table-column
              label="是否已投票"
              align="center"
              prop="ownerStatus"
            >
              <template slot-scope="scope">
                {{ getOwnerStatusName(scope.row.ownerStatus) }}
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
                  style="color: #f56c6c"
                  icon="el-icon-delete"
                  @click="handleSetVoteDelete(scope.row)"
                  >删除</el-button
                >
              </template>
            </el-table-column>
          </el-table>
        </el-row>
      </el-form>
      <el-row>
        <el-col :span="6" :offset="20" style="margin-top: 10px">
          <el-button @click="setVoteCancel">取 消</el-button>
        </el-col>
      </el-row>
    </el-dialog>
  </div>
</template>

<script>
import {
  list,
  getById,
  del,
  insert,
  update,
  yqUpdate,
  voteScopeList,
  voteScopeOwnerSave,
  voteScopeOwnerList,
  voteScopeOwnerRemove,
  getAllOwnerList,
  getResult,
} from "@/api/estate/vote/vote";

import { getCurrentList, getUnitList } from "@/api/estate/community";
import {
  getCommunityIdBuildingName,
  getVoteListRooms,
} from "@/api/estate/center/room";
import { getRoomList } from "../../../api/estate/community";
export default {
  name: "vote",
  components: {},
  data() {
    return {
      activeName: "house",
      currentList: [],
      openAccount: false,
      ownerOptions: [],
      // 遮罩层
      loading: true,
      loading1: false,
      loading2: false,
      loading3: false,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      showSearch1: true,
      showSearch2: true,
      showSearch3: true,
      buildingOptions: [],
      unitOptions: [],
      roomOptions: [],
      // 总条数
      total: 0,
      // 问卷调查表格数据
      list: [],
      //总条数
      total1: 0,
      //业主表格数据
      list1: [],
      //总条数
      total2: 0,
      //业主表格数据
      list2: [],
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;
        },
      },
      resultList: [
        { name: "赞成", number: 10, percentage: 10, flag: "0" },
        { name: "反对", number: 30, percentage: 20, flag: "1" },
        { name: "弃权", number: 30, percentage: 30, flag: "2" },
      ],

      dateRange: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      yqOpen: false,
      voteOpen: false,
      setVoteOpen: false,
      viewOpen: false,
      setVoteOwnerList: [],
      houseAddress: undefined,
      ownerName: undefined,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        voteName: undefined,
        voteNo: undefined,
        state: undefined,
        communityId: undefined,
      },
      queryParams1: {
        pageNum: 1,
        pageSize: 10,
        ownerName: undefined,
        id: undefined,
      },
      communityId: undefined,
      queryParams2: {
        pageNum: 1,
        pageSize: 10,
        ownerName: undefined,
        voteId: undefined,
        buildingId: undefined,
        unitId: undefined,
        roomId: undefined,
      },
      queryParams3: {
        id: undefined,
        roomId: undefined,
      },
      // 表单参数
      form: {
        id: undefined,
        buildingId: undefined,
        unitId: undefined,
        roomId: undefined,
        estateId: undefined,
        communityId: undefined,
        voteNo: undefined,
        voteName: undefined,
        content: undefined,
        startTime: undefined,
        endTime: undefined,
        issuesName: undefined,
        voteScopeList: [
          {
            id: undefined,
            estateId: undefined,
            communityId: undefined,
            buildingId: undefined,
            unitId: undefined,
            roomId: undefined,
            voteId: undefined,
            voteOwnerList: [
              {
                ownerPhone: undefined,
                ownerName: undefined,
                ownerCarNo: undefined,
              },
            ],
          },
        ],
      },
      voteScopeList: [
        {
          id: undefined,
          estateId: undefined,
          communityId: undefined,
          buildingId: undefined,
          unitId: undefined,
          roomId: undefined,
          voteId: undefined,
        },
      ],
      voteOwnerList: [
        {
          ownerPhone: undefined,
          ownerName: undefined,
          ownerCarNo: undefined,
        },
      ],
      setVoteRules: {
        accountId: [
          { required: true, message: "请选择业主信息", trigger: "blur" },
        ],
        setReason: [
          { required: true, message: "请输入添加原因", trigger: "blur" },
        ],
      },
      // 表单校验
      rules: {
        communityId: [
          { required: true, message: "请选择评测小区", trigger: "blur" },
        ],
        voteName: [
          { required: true, message: "请输入投票标题", trigger: "blur" },
        ],
        content: [
          { required: true, message: "请输入投票描述", trigger: "blur" },
        ],
        issuesName: [
          { required: true, message: "请输入议题名称", trigger: "blur" },
        ],
        startTime: [
          { required: true, message: "请选择投票开始时间", trigger: "blur" },
        ],
        endTime: [
          { required: true, message: "请选择投票结束时间", trigger: "blur" },
        ],
      },
    };
  },
  created() {
    //所属小区
    getCurrentList().then((res) => {
      this.currentList = res.data;
    });
    this.getList();
  },
  methods: {
    getCommunityName(communityId) {
      return this.currentList.filter(
        (item) => item.communityId == communityId
      )[0].communityName;
    },
    //业主下拉
    getAllOwnerList(communityId, voteScopeId) {
      getAllOwnerList(communityId, voteScopeId).then((res) => {
        this.ownerOptions = res.data;
      });
    },
    handleFind(row) {
      this.openAccount = true;
      this.$nextTick(() => {
        this.$refs.indexAccountRef.receiveValue(row.id);
      });
    },
    //小区下所有楼栋
    getBuildingOptions(communityId, flag) {
      getCommunityIdBuildingName(communityId).then((res) => {
        this.buildingOptions = res.data;
        if (flag == "0") {
          this.form.buildingId = undefined;
          this.form.unitId = undefined;
          this.form.roomId = undefined;
        }
      });
      this.form.communityId = communityId;
      if (flag == "0") {
        //this.getList1();
        this.list1 = [];
        this.total1=0;
      }
    },
    //单元楼
    getUnitOptions(buildingId, flag) {
      getUnitList(buildingId).then((res) => {
        this.unitOptions = res.data;
        if (flag == "0") {
          this.form.unitId = undefined;
          this.form.roomId = undefined;
        }
      });
      if (flag == "0") {
        this.getList1();
      }
    },
    //单元楼
    getUnitOptions2(buildingId, flag) {
      getUnitList(buildingId).then((res) => {
        this.unitOptions = res.data;
        if (flag == "0") {
          this.form.unitId = undefined;
          this.form.roomId = undefined;
        }
      });
    },
    //房间号
    getRoomOptions2(unitId) {
      getRoomList(unitId).then((res) => {
        this.roomOptions = res.data;
      });
    },
    getBuildingValue(unitId) {
      this.form.unitId = unitId;
      this.$forceUpdate();
    },

    /** 查询问卷调查列表 */
    getList() {
      this.loading = true;
      list(this.queryParams).then((response) => {
        this.list = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    getList1() {
      if (this.form.communityId) {
        this.queryParams1.id = this.form.communityId;
        this.queryParams1.level = 1;
      } else {
        this.$modal.msgWarning("请选择所属小区");
        return;
      }
      if (this.form.buildingId) {
        this.queryParams1.id = this.form.buildingId;
        this.queryParams1.level = 2;
      }
      if (this.form.unitId) {
        this.queryParams1.id = this.form.unitId;
        this.queryParams1.level = 3;
      }
      this.loading1 = true;
      getVoteListRooms(this.queryParams1).then((res) => {
        this.list1 = res.rows;
        this.total1 = res.total;
        this.loading1 = false;
      });
    },

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
      this.getList();
    },
    yqCancel() {
      this.yqOpen = false;
      this.reset();
      this.getList();
    },
    viewCancel() {
      this.viewOpen = false;
      this.getList();
    },
    voteCancel() {
      this.voteOpen = false;
      this.reset();
      this.getList();
    },
    setVoteCancel() {
      this.setVoteOpen = false;
      this.getList2();
    },

    // 表单重置
    reset() {
      this.dateRange = [];
      this.activeName = "house";
      // 表单参数
      (this.form = {
        id: undefined,
        estateId: undefined,
        communityId: undefined,
        voteNo: undefined,
        voteName: undefined,
        content: undefined,
        startTime: undefined,
        endTime: undefined,
        issuesName: undefined,
        voteScopeList: [
          {
            id: undefined,
            estateId: undefined,
            communityId: undefined,
            buildingId: undefined,
            unitId: undefined,
            roomId: undefined,
            voteId: undefined,
            voteOwnerList: [
              {
                ownerPhone: undefined,
                ownerName: undefined,
                ownerCarNo: undefined,
              },
            ],
          },
        ],
      }),
        (this.voteScopeList = [
          {
            id: undefined,
            estateId: undefined,
            communityId: undefined,
            buildingId: undefined,
            unitId: undefined,
            roomId: undefined,
            voteId: undefined,
          },
        ]),
        (this.voteOwnerList = [
          {
            ownerPhone: undefined,
            ownerName: undefined,
            ownerCarNo: undefined,
          },
        ]),
        this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    handleQuery1() {
      this.queryParams1.pageNum = 1;
      this.getList1();
    },
    handleQuery2() {
      this.queryParams2.pageNum = 1;
      this.getList2();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 重置按钮操作 */
    resetQuery1() {
      this.resetForm("queryForm1");
      this.form.buildingId = undefined;
      this.form.unitId = undefined;
      this.handleQuery1();
    },
    /** 重置按钮操作 */
    resetQuery2() {
      this.resetForm("queryForm2");

      this.handleQuery2();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.id);
      this.single = selection.length !== 1;
      this.multiple = !selection.length;
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.queryParams1.flag = 0;
      this.queryParams1.voteId = "";
      this.reset();
      this.open = true;
      this.title = "添加投票表决";
    },
    //延期
    handleYq(row) {
      this.form.id = row.id;
      this.form.voteNo = row.voteNo;
      this.form.startTime = row.startTime;
      this.form.endTime = row.endTime;
      this.yqOpen = true;
    },
    //查看投票结果
    handleView(row) {
      getResult(row.id).then((res) => {
        this.form = res.data;
        this.form.state = row.state;
      });
      this.queryParams2.voteId = row.id;
      this.queryParams2.il = "INNER";
      this.getList2();
      this.viewOpen = true;
    },
    //修改投票人
    handleUpdateVote(row) {
      this.queryParams2.voteId = row.id;
      this.queryParams2.il = "LEFT";
      this.communityId = row.communityId;
      this.getBuildingOptions(row.communityId, "0");
      this.getList2();

      this.voteOpen = true;
    },
    //设置投票人
    handleSetVote(row) {
      this.getAllOwnerList(this.communityId, row.id);
      this.queryParams3.id = row.id;
      this.queryParams3.voteId = row.voteId;
      this.queryParams3.roomId = row.roomId;
      this.getList3();
      this.setVoteOpen = true;
    },
    getList2() {
      this.loading2 = true;
      voteScopeList(this.queryParams2).then((res) => {
        this.list2 = res.rows;
        this.total2 = res.total;
        this.loading2 = false;
      });
    },
    getList3() {
      this.loading3 = true;
      voteScopeOwnerList(
        this.queryParams3.voteId,
        this.queryParams3.roomId
      ).then((res) => {
        this.setVoteOwnerList = res.list;
        this.houseAddress = res.houseAddress;
        this.ownerName = res.ownerName;
        this.loading3 = false;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();

      const id = row.id || this.ids;
      getById(id).then((response) => {
        this.form = response.data;
        this.getBuildingOptions(this.form.communityId, "1");
        if (this.form.buildingId) {
          this.getUnitOptions(this.form.buildingId, "1");
        }
        this.queryParams1.flag = 1;
        this.queryParams1.voteId = row.id;
        this.getList1();
        this.open = true;
        this.title = "修改投票表决";
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["formref"].validate((valid) => {
        if (valid) {
          this.form.voteScopeList = [];

          var voteName = this.form.voteName;
          if (!voteName) {
            this.$modal.msgWarning("请输入投票标题");
            return;
          }
          var content = this.form.content;
          if (!content) {
            this.$modal.msgWarning("请输入投票描述");
            return;
          }
          var issuesName = this.form.issuesName;
          if (!issuesName) {
            this.$modal.msgWarning("请输入议题名称");
            return;
          }
          var startTime = this.form.startTime;
          if (!startTime) {
            this.$modal.msgWarning("请选择投票开始时间");
            return;
          }

          var endTime = this.form.endTime;
          if (!endTime) {
            this.$modal.msgWarning("请选择投票结束时间");
            return;
          }

          var communityId = this.form.communityId;
          if (!communityId) {
            this.$modal.msgWarning("请选择评测小区");
            return;
          }

          if (this.list1 != null) {
            this.form.voteScopeList = this.list1.filter(
              (item) => item.delFlag == "1"
            );
            if (this.form.voteScopeList.length == 0) {
              this.$modal.msgWarning("请选择发布群体范围");
              return;
            }
          } else { 
             this.$modal.msgWarning("请选择发布群体范围");
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
    /** 提交按钮 */
    yqSubmitForm() {
      this.$refs["yqFormref"].validate((valid) => {
        if (valid) {
          if (!this.form.endTime) {
            this.$modal.msgWarning("请选择投票结束时间");
            return;
          }

          yqUpdate(this.form).then((response) => {
            this.$modal.msgSuccess("延期成功");
            this.yqOpen = false;
            this.getList();
          });
        }
      });
    },
    getOwnerStatusName(ownerStatus) {
      //投票状态0=未投票1=支持2=反对3=弃权
      if (ownerStatus == "0") {
        return "未投票";
      }
      if (ownerStatus == "1") {
        return "赞同";
      }
      if (ownerStatus == "2") {
        return "反对";
      }
      if (ownerStatus == "3") {
        return "弃权";
      }
    },
    handleSetVoteAdd(form) {
      this.$refs["setVoteFormref"].validate((valid) => {
        if (valid) {
          form.voteScopeId = this.queryParams3.id;
          voteScopeOwnerSave(form).then(() => {
            this.getAllOwnerList(this.communityId, this.queryParams3.id);
            this.getList3();
            this.form.accountId = undefined;
            this.form.setReason = undefined;
            this.$modal.msgSuccess("添加成功");
          });
        }
      });
    },
    handleSetVoteDelete(row) {
      this.$modal
        .confirm("是否确认删除" + row.ownerName + "投票人吗?")
        .then(function () {
          return voteScopeOwnerRemove(row.id);
        })
        .then(() => {
          this.getAllOwnerList(this.communityId, this.queryParams3.id);
          this.getList3();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.id || this.ids;
      this.$modal
        .confirm("是否确认删除当前数据吗？")
        .then(function () {
          return del(ids);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },

    /** 删除按钮操作 */
    handleDelete1(row, flag) {
      this.list1.forEach((item) => {
        if (item.ownerId == row.ownerId) {
          item.delFlag = flag;
        }
      });
    },
  },
};
</script>

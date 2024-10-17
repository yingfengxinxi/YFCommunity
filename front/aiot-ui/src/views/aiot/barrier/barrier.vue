<template>
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" :inline="true" class="cus-fm">
      <el-form-item label="小区名称">
        <el-select v-model="queryParams.communityId" placeholder="请选择小区">
          <el-option
            v-for="item in communityList"
            :key="item.communityId"
            :label="item.communityName"
            :value="item.communityId">
          </el-option>
        </el-select>
      </el-form-item>
      <el-form-item label="道闸名称" prop="barrierName">
        <el-input
          v-emoji
          v-model.trim="queryParams.barrierName"
          placeholder="请输入道闸名称"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="唯一标识" prop="indexCode">
        <el-input
          v-emoji
          v-model.trim="queryParams.indexCode"
          placeholder="请输入唯一标识"
          clearable
          size="small"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
      </el-form-item>
    </el-form>
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-refresh"
          size="mini"
          :loading = "loading"
          @click="syncBarrier"
        >同步设备信息</el-button>
      </el-col>
      <!-- <el-col :span="1.5">
        <el-button
          type="primary" plain
          icon="el-icon-menu"
          :disabled="multiple"
          size="mini" @click="handleBatchQr"
        >设置设备二维码</el-button>
      </el-col> -->
      <right-toolbar  @queryTable="getBarrierList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="barrierList"  width="60px" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="道闸名称" prop="barrierName" :show-overflow-tooltip="true"/>
      <el-table-column label="唯一标识" prop="indexCode" :show-overflow-tooltip="true"/>
      <!-- <el-table-column label="开闸输出口" prop="openOutput">
        <template slot-scope="scope">{{ outputFormatter(scope.row.openOutput) }}</template>
      </el-table-column> -->
      <!-- <el-table-column label="关闸输出口" prop="closeOutput">
        <template slot-scope="scope">{{ outputFormatter(scope.row.closeOutput) }}</template>
      </el-table-column> -->
      <el-table-column label="创建时间" prop="createTime" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ momentFormat(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="更新时间" prop="updateTime" :show-overflow-tooltip="true">
        <template slot-scope="scope">
          <span>{{ momentFormat(scope.row.updateTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="数据同步时间" prop="renewalTime" :show-overflow-tooltip="true"/>
      <el-table-column label="操作" align="center" width="150px" class-name="small-padding fixed-width" >
        <template slot-scope="scope">
          <el-button
            size="mini" type="text" icon="el-icon-view"
            class="text-info"
            @click="handleView(scope.row)"
          >查看</el-button>
          <el-button
            size="mini" type="text" icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getBarrierList"/>
    <!--  查看  -->
    <el-dialog :title="title" :visible.sync="showDetail" width="60%">
      <el-form :model="form" label-width="140px">
        <el-row>
          <el-col :span="20">
            <el-form-item label="资源唯一编码：">{{form.indexCode}}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="资源名称：">{{form.barrierName}}</el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="组件标志：">{{ form.comId }}</el-form-item>
          </el-col>
          </el-row>
        <el-row>
          <el-col :span="28">
            <!-- <el-row>
              <el-col :span="12">
                <el-form-item label="开闸输出口：">{{ outputFormatter(form.openOutput) }}</el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="关闸输出口：">{{ outputFormatter(form.closeOutput) }}</el-form-item>
              </el-col>
            </el-row> -->
            <el-row >
              <el-col :span="12">
                <el-form-item label="创建时间：">{{momentFormat(form.createTime)}}</el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="更新时间：">{{momentFormat(form.updateTime)}}</el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="12">
                <el-form-item label="数据同步人：">{{form.renewalBy}}</el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="数据同步时间：">{{form.renewalTime}}</el-form-item>
              </el-col>
            </el-row>
          </el-col>
          <!-- <el-col :span="8"> -->
            <!-- <el-form-item label="设备二维码：" v-if="form.deviceQr">
              <el-image :src="form.deviceQr"/>
            </el-form-item> -->
            <!-- <el-form-item label="设备二维码：" v-else>未设置</el-form-item> -->
          <!-- </el-col> -->
        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="所属小区：">{{form.communityName}}</el-form-item>
          </el-col>
          <el-col :span="16">
            <el-form-item label="所在位置：">{{form.address}}</el-form-item>

          </el-col>
        </el-row>
        <el-row>
          <el-form-item label="关联停车场：">{{form.parkName}} </el-form-item>
          <el-form-item label="关联出入口：">{{form.entranceName}} </el-form-item>
          <el-form-item label="关联车道：">{{form.roadwayName}}</el-form-item>

        </el-row>
        <!-- <el-row>
          <el-form-item label="关联出入口：">{{form.entranceName}} </el-form-item>
        </el-row>
        <el-row>
          <el-form-item label="关联车道：">{{form.roadwayName}}</el-form-item>
        </el-row> -->
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>
    <!--  修改  -->
    <el-dialog :title="title" :visible.sync="updateShow" width="30%">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="资源唯一编码：">{{form.indexCode}}</el-form-item>
        <el-form-item label="资源名称：">{{form.barrierName}}</el-form-item>
        <el-form-item label="所属小区：">{{form.communityName}}</el-form-item>
        <el-form-item label="停车场：">
          <el-select v-model="form.parkIndex" placeholder="请选择停车场" style="width: 100%" @change="getEntrances">
            <el-option
              v-for="item in parkList"
              :key="item.indexCode"
              :label="item.parkName"
              :value="item.indexCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="出入口：">
          <el-select v-model="form.entranceIndex" placeholder="请选择出入口" style="width: 100%" @change="getRoadways">
            <el-option
              v-for="item in entranceList"
              :key="item.indexCode"
              :label="item.entranceName"
              :value="item.indexCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="车道：">
          <el-select v-model="form.roadwayIndex" placeholder="请选择车道" style="width: 100%">
            <el-option
              v-for="item in roadwayList"
              :key="item.indexCode"
              :label="item.roadwayName"
              :value="item.indexCode">
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="所在位置：" prop="address">
          <el-input v-model="form.address" placeholder="请输入所在位置" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
        <el-button type="primary" @click="submitForm">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getCurrentList } from "@/api/aiot/community"
import {
  selectBarrierList,syncBarrierData,selectBarrierById,updateBarrier,batchUpdateDeviceQr, selectUnbindRoadway
} from "@/api/aiot/barrier"
import {selectParkAll,selectEntranceAll} from "@/api/aiot/park"
import { isEmpty,equals } from "@/utils/common"
export default {
  name: "Barrier",
  data() {
    return {
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        barrierName: undefined,
        indexCode: undefined
      },
      //按钮加载
      loading: false,
      //小区列表
      communityList: [],
      // 遮罩层
      loading: true,
      // 道闸列表
      barrierList: [],
      // 停车场列表
      parkList: [],
      // 出入口列表
      entranceList: [],
      // 车道列表
      roadwayList: [],
      // 非多个禁用
      multiple: true,
      // 总条数
      total: 0,
      //id
      ids: [],
      indexCodeList: [],
      form: {},
      showDetail: false,
      title:'',
      updateShow: false,
      // 表单校验
      rules: {
        address: [
          { required: true, message: "请输入所在位置", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.listCommunity();
  },
  methods: {
    outputFormatter(output){
      if(equals(output, "2")){
        return "C1-N1"
      }
      if(equals(output, "3")){
        return "C2-N2"
      }
      if(equals(output, "4")){
        return "C3-N3"
      }
      if(equals(output, "5")){
        return "C4-N4"
      }
      return "未知"
    },
    // 获取小区数据
    listCommunity() {
      getCurrentList().then(response => {
        this.communityList = response.data;
        if (this.communityList.length > 0) {
          this.queryParams.communityId = this.communityList[0].communityId
          this.getBarrierList();
        }
      });
    },
    /** 查询列表 */
    getBarrierList() {
      this.loading = true;
      selectBarrierList(this.queryParams).then(response => {
          this.barrierList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 10;
      this.getBarrierList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      // this.queryParams = {};
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /**时间转换*/
    momentFormat(time) {
      if (isEmpty(time)) {
        return "";
      }
      return this.$moment(time).format("YYYY-MM-DD HH:mm:ss");
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.barrierId)
      this.indexCodeList = selection.map(item => item.indexCode)
      this.multiple = !selection.length
    },
    // 设备同步
    syncBarrier() {
      this.loading = true;
      syncBarrierData(this.queryParams.communityId).then(response => {
        this.$modal.msgSuccess(response.msg);
        this.getBarrierList();
        this.loading = false;
      }).catch(err => {
        this.loading = false;
      })
    },

    /**查看*/
    handleView(row) {
      const barrierId = row.barrierId
      selectBarrierById(barrierId).then(response => {
        this.form = response.data;
        console.log(this.form)
        this.title = "道闸详情";
        this.showDetail = true;
      });
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const barrierId = row.barrierId
      this.selectParkAll(row.communityId);
      selectBarrierById(barrierId).then(response => {
        this.form = response.data;
        this.getEntrances();
        this.getRoadways();
        this.showDetail = false;
        this.title = "修改道闸";
        this.updateShow = true;
      });
    },
    selectParkAll(communityId){
      selectParkAll(communityId).then(response => {
        this.parkList = response.data;
      });
    },
    getEntrances(){
      if( '' == this.form.parkIndex || null == this.form.parkIndex){
        return;
      }
      selectEntranceAll(this.form.parkIndex).then(response => {
        this.entranceList = response.data;
      });
    },
    getRoadways(){
      let data = {
        barrierId: this.form.barrierId,
        entranceIndex: this.form.entranceIndex
      }
      selectUnbindRoadway(data).then(response => {
        this.roadwayList = response.data;
        console.log(this.roadwayList)
      });
    },

    // 表单重置
    reset() {
      this.resetForm("form");
    },
    // 取消按钮
    cancel() {
      this.showDetail =false;
      this.updateShow =false;
      this.reset();
    },
    /** 提交按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          updateBarrier(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.updateShow = false;
            this.getBarrierList();
          });
        }
      });
    },
    // 设置设备二维码
    handleBatchQr(){
      batchUpdateDeviceQr(this.ids).then(response => {
        this.$modal.msgSuccess(response.msg);
        this.getBarrierList();
      });
    },
  }
};
</script>

<style scoped>

</style>

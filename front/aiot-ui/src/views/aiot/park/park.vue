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
      <el-form-item label="停车场名称" prop="parkName">
        <el-input
          v-emoji
          v-model.trim="queryParams.parkName"
          placeholder="请输入停车场名称"
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
          @click="syncPark"
        >同步设备信息</el-button>
      </el-col>
      <!-- <el-col :span="1.5"> -->
        <!-- <el-button
          type="primary" plain
          icon="el-icon-menu"
          :disabled="multiple"
          size="mini" @click="handleBatchQr"
        >设置支付二维码</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning" plain
          icon="el-icon-s-promotion"
          size="mini" @click="handleSubscribe"
        >出入事件订阅</el-button>
      </el-col>
      <el-col :span="1.5">
        <el-button
          type="warning" plain
          icon="el-icon-s-promotion"
          size="mini" @click="handleUnsubscribe"
        >出入事件取消订阅</el-button>
      </el-col> -->
      <right-toolbar  @queryTable="getParkList"></right-toolbar>
    </el-row>
    <el-table v-loading="loading" :data="parkList" width="60px" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <!--        <el-table-column label="所属小区" align="center" prop="communityName"/>-->
      <el-table-column label="停车场名称" prop="parkName" :show-overflow-tooltip="true"/>
      <el-table-column label="唯一标识" prop="indexCode" :show-overflow-tooltip="true"/>
      <el-table-column label="停车场类型" prop="parkType">
        <template slot-scope="scope">
          <span v-if="scope.row.parkType === '0'">车库</span>
          <span v-else-if="scope.row.parkType === '1'">大门</span>
          <span v-else>未设置</span>
        </template>
      </el-table-column>
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
            size="mini" type="text" icon="el-icon-view"
            class="text-info"
            @click="handleCxView(scope.row)"
          >查看车辆出入数据</el-button>
          <el-button
            size="mini" type="text" icon="el-icon-edit"
            @click="handleUpdate(scope.row)"
          >修改</el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="queryParams.pageNum" :limit.sync="queryParams.pageSize" @pagination="getParkList"/>

    <!--  查看  -->
    <el-dialog :title="title" :visible.sync="showDetail" width="65%">
      <el-form :model="form" label-width="140px">
        <el-row>
          <el-col :span="20">
            <el-form-item label="资源唯一编码：">{{form.indexCode}}</el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="停车场名称：">{{form.parkName}}</el-form-item>
          </el-col>
          <el-col :span="6">
            <el-form-item label="停车场类型：">
              <span v-if="form.parkType === '0'">车库</span>
              <span v-else-if="form.parkType === '1'">大门</span>
              <span v-else>未设置</span>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="16">
            <el-row>
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
            <el-row>
              <el-col :span="12">
                <el-form-item label="所属小区：">{{form.communityName}}</el-form-item>
              </el-col>
              <el-col :span="12">
                <el-form-item label="所在位置：">{{form.address}}</el-form-item>
              </el-col>
            </el-row>
          </el-col>
          <!-- <el-col :span="8">
            <el-form-item label="支付二维码：" v-if="form.payCode">
              <el-image :src="form.payCode" style="width: 60%"/>
            </el-form-item>
            <el-form-item label="支付二维码：" v-else>未设置</el-form-item>
          </el-col> -->
        </el-row>
        <el-row v-for="entrance in form.entranceList">
          <h4 class="form-header">{{ entrance.entranceName }} ( {{ entrance.indexCode }} )</h4>
          <el-row v-for="roadway in entrance.roadwayList">
            <el-col :span="8" >
              <el-form-item label="车道名称：">{{roadway.roadwayName}}</el-form-item>
            </el-col>
            <el-col :span="14" >
              <el-form-item label="车道唯一标识：">{{roadway.indexCode}}</el-form-item>
            </el-col>
            <el-row >
            <el-col :span="14" >
              <el-form-item label="关联设备：">
                <span  v-for="barrier in roadway.barrierList" >
                  {{barrier.barrierName + '  '}}
                </span>
              </el-form-item>
            </el-col>
          </el-row>
          </el-row>

        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">关 闭</el-button>
      </div>
    </el-dialog>

    <!--  修改  -->
    <el-dialog :title="title" :visible.sync="updateShow" width="30%">
      <el-form ref="form" :model="form" :rules="rules" label-width="120px">
        <el-form-item label="资源唯一编码：">{{form.indexCode}}</el-form-item>
        <el-form-item label="停车场名称：">{{form.parkName}}</el-form-item>
        <el-form-item label="所属小区：">{{form.communityName}}</el-form-item>
        <el-form-item label="所在位置：">
          <el-input v-model="form.address" placeholder="请输入所在位置" />
        </el-form-item>
        <el-form-item label="停车场类型：" prop="parkType">
          <el-select v-model="form.parkType" placeholder="请选择停车场类型" style="width: 100%">
            <el-option label="车库" value="0" selected="selected"></el-option>
            <el-option label="大门" value="1"></el-option>
          </el-select>
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
import { selectParkList, syncParkData, selectParkById, updatePark, batchUpdatePayQr, subscribeEvent, unsubscribeLock } from "@/api/aiot/park"
import { isEmpty } from "@/utils/common"
export default {
  name: "Park",
  data() {
    return {
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        parkName: undefined,
        indexCode: undefined
      },
      //按钮加载
      loading: false,
      //小区列表
      communityList: [],
      // 遮罩层
      loading: true,
      // 是否展开，默认全部折叠
      isExpandAll: false,
      //小区列表
      parkList: [],
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
        parkType: [
          { required: true, message: "请选择停车场类型", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.listCommunity();
  },
  methods: {
    /** 展开/折叠操作 */
    toggleExpandAll() {
      this.isExpandAll = !this.isExpandAll;
    },
    // 获取小区数据
    listCommunity() {
      getCurrentList().then(response => {
        this.communityList = response.data;
        if (this.communityList.length > 0) {
          this.queryParams.communityId = this.communityList[0].communityId
          this.getParkList();
        }
      });
    },
    /** 查询列表 */
    getParkList() {
      this.loading = true;
      selectParkList(this.queryParams).then(response => {
        this.parkList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.queryParams.pageSize = 10;
      this.getParkList();
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
      this.ids = selection.map(item => item.parkId)
      this.indexCodeList = selection.map(item => item.indexCode)
      this.multiple = !selection.length
    },
    // 设备同步
    syncPark() {
      this.loading = true;
      syncParkData(this.queryParams.communityId).then(response => {
        this.$modal.msgSuccess(response.msg);
        this.getParkList();
        this.loading = false;
      }).catch(err => {
        this.loading = false;
      })
    },

    /**查看*/
    handleView(row) {
      const parkId = row.parkId
      selectParkById(parkId).then(response => {
        this.form = response.data;
        console.log(response)
        this.title = "停车场详情";
        this.showDetail = true;
      });
    },
    handleCxView(row) {
      let query = {
        parkSyscode: row.indexCode,
      };
      this.$router.replace({ path: '/pk/crossRecord', query })
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      const parkId = row.parkId
      selectParkById(parkId).then(response => {
        this.form = response.data;
        this.showDetail = false;
        this.title = "修改停车场";
        this.updateShow = true;
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
          updatePark(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.updateShow = false;
            this.getParkList();
          });
        }
      });
    },
    // 设置支付二维码
    handleBatchQr(){
      batchUpdatePayQr(this.ids).then(response => {
        this.$modal.msgSuccess(response.msg);
        this.getParkList();
      });
    },
    // 停车场出入事件订阅
    handleSubscribe(){
      let communityId = this.queryParams.communityId;
      if(isEmpty(communityId)){
        this.$modal.msgWarning("请选择要订阅事件的小区！！！");
        return
      }
      this.$modal.confirm('是否订阅该小区“停车场出入事件”？"').then(function() {
        return subscribeEvent(communityId);
      }).then(() => {
        this.$modal.msgSuccess("订阅成功");
      }).catch(() => {});
    },
    // 停车场出入事件取消订阅
    handleUnsubscribe(){
      let communityId = this.queryParams.communityId;
      if(isEmpty(communityId)){
        this.$modal.msgWarning("请选择要取消订阅的小区！！！");
        return
      }
      this.$modal.confirm('是否订阅该小区“停车场出入事件”？"').then(function() {
        return unsubscribeLock(communityId);
      }).then(() => {
        this.$modal.msgSuccess("取消成功");
      }).catch(() => {});
    },
  }
};
</script>

<style scoped>

</style>

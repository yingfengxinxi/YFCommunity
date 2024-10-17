<template>
  <div class="app-container home">
    <el-row>
      <!--  左边    -->
      <el-col style="width: 79%">
        <!--  上边    -->
        <el-row>
          <router-link to="/audit/owmer">
            <el-col style="width: 24%">
              <el-card
                class="box-card card-radius bg-img audit-house"
                shadow="always"
              >
                <el-row style="margin-top: 4%">
                  <el-col>
                    <span style="font-size: 30px">{{ houseNum }}</span>
                    <span style="font-size: 20px; font-weight: bold"> 套</span>
                  </el-col>
                </el-row>
                <el-row style="margin-top: 4%; font-weight: bold"
                  >业主/房产待审核</el-row
                >
              </el-card>
            </el-col>
          </router-link>
          <router-link to="/audit/carAudit">
            <el-col style="width: 24%; margin-left: 1%">
              <el-card
                class="box-card card-radius bg-img audit-vehicle"
                shadow="always"
              >
                <el-row style="margin-top: 4%">
                  <el-col>
                    <span style="font-size: 30px">{{ vehicleNum }}</span>
                    <span style="font-size: 20px; font-weight: bold"> 辆</span>
                  </el-col>
                </el-row>
                <el-row style="margin-top: 4%; font-weight: bold"
                  >车辆待审核</el-row
                >
              </el-card>
            </el-col>
          </router-link>
          <router-link to="/information/send/sendSmsRecord">
            <el-col style="width: 24%; margin-left: 1%">
              <el-card
                class="box-card card-radius bg-img audit-owner"
                shadow="always"
              >
                <el-row style="margin-top: 4%">
                  <el-col>
                    <span style="font-size: 30px">{{ sendNum }}</span>
                    <span style="font-size: 20px; font-weight: bold"> 条</span>
                  </el-col>
                </el-row>
                <el-row style="margin-top: 4%; font-weight: bold"
                  >生日/节日消息待发送</el-row
                >
              </el-card>
            </el-col>
          </router-link>
          <router-link to="/customer/complaintSuggestion">
            <el-col style="width: 24%; margin-left: 1%">
              <el-card
                class="box-card card-radius bg-img audit-tenant"
                shadow="always"
              >
                <el-row style="margin-top: 4%">
                  <el-col>
                    <span style="font-size: 30px">{{
                      complaintSuggestionNum
                    }}</span>
                    <span style="font-size: 20px; font-weight: bold"> 条</span>
                  </el-col>
                </el-row>
                <el-row style="margin-top: 4%; font-weight: bold"
                  >投诉建议待回复</el-row
                >
              </el-card>
            </el-col>
          </router-link>
        </el-row>
        <!--  中间    -->
        <el-row style="margin-top: 2%; font-size: 14px">
          <!--  每日人行/车行    -->
          <el-col style="width: 65%; font-weight: bold">
            <el-card class="box-card card-radius" shadow="always">
              <el-row style="margin-bottom: 4%">
                <el-col :span="5">
                  <span>今日</span><span class="text-blue">人行/车行</span>
                </el-col>
                <el-col :span="8">
                  <el-select
                    v-model="logQuery.communityId"
                    placeholder="请选择小区"
                    size="mini"
                    @change="getLogList"
                  >
                    <el-option
                      v-for="item in communityList"
                      :key="item.communityId"
                      :label="item.communityName"
                      :value="item.communityId"
                    >
                    </el-option>
                  </el-select>
                </el-col>
              </el-row>

              <div ref="logRef" style="height: 250px"></div>
            </el-card>
          </el-col>
          <!--  房屋状态    -->
          <el-col style="width: 34%; margin-left: 1%; font-weight: bold">
            <el-card class="box-card card-radius" shadow="always">
              <el-row style="margin-bottom: 4%">
                <el-col :span="6" style="margin-top: 5px"
                  ><span>房屋</span><span class="text-blue">状态</span></el-col
                >
                <el-col :span="10">
                  <el-select
                    style="width: 150px"
                    v-model="houseQuery.communityId"
                    placeholder="请选择小区"
                    size="mini"
                    @change="selectRoomStatus"
                  >
                    <el-option
                      v-for="item in communityList"
                      :key="item.communityId"
                      :label="item.communityName"
                      :value="item.communityId"
                    >
                    </el-option>
                  </el-select>
                </el-col>
              </el-row>
              <div ref="roomStatusRef" style="height: 260px"></div>
            </el-card>
          </el-col>
        </el-row>
        <!--  下边    -->
        <el-row style="margin-top: 1%; font-size: 14px">
          <!--  能耗统计    -->
          <el-col style="width: 65%; font-weight: bold">
            <el-card class="box-card card-radius" shadow="always">
              <el-row style="margin-bottom: 4%">
                <el-col :span="3" style="margin-top: 5px"
                  ><span>能耗</span><span class="text-blue">统计</span></el-col
                >
                <el-col :span="8">
                  <el-select
                    v-model="energyQuery.communityId"
                    placeholder="请选择小区"
                    size="mini"
                    @change="selectEnergyData"
                  >
                    <el-option
                      v-for="item in communityList"
                      :key="item.communityId"
                      :label="item.communityName"
                      :value="item.communityId"
                    >
                    </el-option>
                  </el-select>
                </el-col>
                <el-col :span="8" :offset="2" style="margin-top: 5px">
                  <el-radio
                    @input="selectEnergyData"
                    v-model="energyType"
                    :label="0"
                    >水量</el-radio
                  >
                  <el-radio
                    @input="selectEnergyData"
                    v-model="energyType"
                    :label="1"
                    >电量</el-radio
                  >
                </el-col>
              </el-row>
              <div ref="energyRef" style="height: 250px"></div>
            </el-card>
          </el-col>
          <!--  基金统计    -->
          <el-col style="width: 34%; margin-left: 1%; font-weight: bold">
            <el-card class="box-card card-radius" shadow="always">
              <el-row style="margin-bottom: 4%">
                <el-col :span="8" style="margin-top: 5px"
                  ><span>小区</span><span class="text-blue">收益</span></el-col
                >
                <el-col :span="10">
                  <el-select
                    style="width: 150px"
                    v-model="foundQuery.communityId"
                    placeholder="请选择小区"
                    size="mini"
                    @change="selectFoundData"
                  >
                    <el-option
                      v-for="item in communityList"
                      :key="item.communityId"
                      :label="item.communityName"
                      :value="item.communityId"
                    >
                    </el-option>
                  </el-select>
                </el-col>
              </el-row>
              <div ref="foundRef" style="height: 260px"></div>
            </el-card>
          </el-col>
        </el-row>
      </el-col>
      <!--  右边    -->
      <el-col style="width: 20%; margin-left: 1%">
        <!--  快捷菜单    -->
        <el-row style="font-size: 14px">
          <el-card class="box-card card-radius" shadow="always">
            <el-row style="font-weight: bold">
              <span>快捷</span><span class="text-blue">菜单</span>
            </el-row>
            <el-row style="margin-top: 4px; line-height: 20px">
              <router-link to="/file/owner">
                <el-col :span="6">
                  <el-row>
                    <el-col
                      :span="14"
                      :offset="4"
                      class="bg-img quick-venue"
                    ></el-col>
                  </el-row>
                  <el-row class="quick-text">
                    <el-col :span="18" :offset="2">业主档案</el-col>
                  </el-row>
                </el-col>
              </router-link>
              <router-link to="/file/tenant">
                <el-col :span="6">
                  <el-row>
                    <el-col
                      :span="14"
                      :offset="4"
                      class="bg-img quick-borrow"
                    ></el-col>
                  </el-row>
                  <el-row class="quick-text">
                    <el-col :span="18" :offset="2">租客档案</el-col>
                  </el-row>
                </el-col>
              </router-link>
              <router-link to="/buildingManage/room">
                <el-col :span="6">
                  <el-row>
                    <el-col
                      :span="14"
                      :offset="4"
                      class="bg-img quick-activity"
                    ></el-col>
                  </el-row>
                  <el-row class="quick-text">
                    <el-col :span="18" :offset="2">房号管理</el-col>
                  </el-row>
                </el-col>
              </router-link>
              <router-link to="/buildingManage/carPositionManage">
                <el-col :span="6">
                  <el-row>
                    <el-col
                      :span="14"
                      :offset="4"
                      class="bg-img quick-older"
                    ></el-col>
                  </el-row>
                  <el-row class="quick-text">
                    <el-col :span="18" :offset="2">车位管理</el-col>
                  </el-row>
                </el-col>
              </router-link>
              <router-link to="/buildingManage/vehicle">
                <el-col :span="6">
                  <el-row>
                    <el-col
                      :span="14"
                      :offset="4"
                      class="bg-img quick-complaint"
                    ></el-col>
                  </el-row>
                  <el-row class="quick-text">
                    <el-col :span="18" :offset="2">车辆管理</el-col>
                  </el-row>
                </el-col>
              </router-link>
              <router-link
                to="/information/Community%20activities/activitemanage"
              >
                <el-col :span="6">
                  <el-row>
                    <el-col
                      :span="14"
                      :offset="4"
                      class="bg-img quick-notice"
                    ></el-col>
                  </el-row>
                  <el-row class="quick-text">
                    <el-col :span="18" :offset="2">社区活动</el-col>
                  </el-row>
                </el-col>
              </router-link>
              <router-link to="/information/communityNotice">
                <el-col :span="6">
                  <el-row>
                    <el-col
                      :span="14"
                      :offset="4"
                      class="bg-img quick-illegal"
                    ></el-col>
                  </el-row>
                  <el-row class="quick-text">
                    <el-col :span="18" :offset="2">通知公告</el-col>
                  </el-row>
                </el-col>
              </router-link>
              <router-link to="/workOrder/decorate/decorationProcessing">
                <el-col :span="6">
                  <el-row>
                    <el-col
                      :span="14"
                      :offset="4"
                      class="bg-img quick-decorate"
                    ></el-col>
                  </el-row>
                  <el-row class="quick-text">
                    <el-col :span="18" :offset="2">装修办理</el-col>
                  </el-row>
                </el-col>
              </router-link>
              <router-link to="/workOrder/inspection/inspectionOrder">
                <el-col :span="6">
                  <el-row>
                    <el-col
                      :span="14"
                      :offset="4"
                      class="bg-img quick-xcgd"
                    ></el-col>
                  </el-row>
                  <el-row class="quick-text">
                    <el-col :span="18" :offset="2">巡查工单</el-col>
                  </el-row>
                </el-col>
              </router-link>
              <router-link to="/workOrder/cleaning/cleaningWorkOrder">
                <el-col :span="6">
                  <el-row>
                    <el-col
                      :span="14"
                      :offset="4"
                      class="bg-img quick-found"
                    ></el-col>
                  </el-row>
                  <el-row class="quick-text">
                    <el-col :span="18" :offset="2">清洁工单</el-col>
                  </el-row>
                </el-col>
              </router-link>
              <router-link to="/workOrder/xgpatrol/patrol">
                <el-col :span="6">
                  <el-row>
                    <el-col
                      :span="14"
                      :offset="4"
                      class="bg-img quick-found2"
                    ></el-col>
                  </el-row>
                  <el-row class="quick-text">
                    <el-col :span="18" :offset="2">巡更工单</el-col>
                  </el-row>
                </el-col>
              </router-link>
              <router-link to="/facilitieeq/facilitie">
                <el-col :span="6">
                  <el-row>
                    <el-col
                      :span="14"
                      :offset="4"
                      class="bg-img quick-property"
                    ></el-col>
                  </el-row>
                  <el-row class="quick-text">
                    <el-col :span="18" :offset="2">设施设备</el-col>
                  </el-row>
                </el-col>
              </router-link>
              <router-link to="/workOrder/patrol/patrolWorkOrder">
                <el-col :span="6">
                  <el-row>
                    <el-col
                      :span="14"
                      :offset="4"
                      class="bg-img quick-property"
                    ></el-col>
                  </el-row>
                  <el-row class="quick-text">
                    <el-col :span="18" :offset="2">巡检工单</el-col>
                  </el-row>
                </el-col>
              </router-link>
              <router-link to="/workOrder/clood/meterReadingEquipment">
                <el-col :span="6">
                  <el-row>
                    <el-col
                      :span="14"
                      :offset="4"
                      class="bg-img quick-cbsb"
                    ></el-col>
                  </el-row>
                  <el-row class="quick-text">
                    <el-col :span="18" :offset="2">抄表设备</el-col>
                  </el-row>
                </el-col>
              </router-link>
              <router-link to="/workOrder/clood/meterReadingWorkOrder">
                <el-col :span="6">
                  <el-row>
                    <el-col
                      :span="14"
                      :offset="4"
                      class="bg-img quick-cbgd"
                    ></el-col>
                  </el-row>
                  <el-row class="quick-text">
                    <el-col :span="18" :offset="2">抄表工单</el-col>
                  </el-row>
                </el-col>
              </router-link>
              <router-link to="/workOrder/reportingWarranty">
                <el-col :span="6">
                  <el-row>
                    <el-col
                      :span="14"
                      :offset="4"
                      class="bg-img quick-bsbx"
                    ></el-col>
                  </el-row>
                  <el-row class="quick-text">
                    <el-col :span="18" :offset="2">报事报修</el-col>
                  </el-row>
                </el-col>
              </router-link>
              <router-link to="/manage/staff/staffList">
                <el-col :span="6">
                  <el-row>
                    <el-col
                      :span="14"
                      :offset="4"
                      class="bg-img quick-xzyg"
                    ></el-col>
                  </el-row>
                  <el-row class="quick-text">
                    <el-col :span="18" :offset="2">新增员工</el-col>
                  </el-row>
                </el-col>
              </router-link>
              <router-link to="/moneyManage/cashManage/cashRecode/estateMoney">
                <el-col :span="6">
                  <el-row>
                    <el-col
                      :span="14"
                      :offset="4"
                      class="bg-img quick-wyf"
                    ></el-col>
                  </el-row>
                  <el-row class="quick-text">
                    <el-col :span="18" :offset="2">物业费</el-col>
                  </el-row>
                </el-col>
              </router-link>
              <router-link to="/phone">
                <el-col :span="6">
                  <el-row>
                    <el-col
                      :span="14"
                      :offset="4"
                      class="bg-img quick-contact"
                    ></el-col>
                  </el-row>
                  <el-row class="quick-text">
                    <el-col :span="18" :offset="2">常用电话</el-col>
                  </el-row>
                </el-col>
              </router-link>
            </el-row>
          </el-card>
        </el-row>
        <!--  工单统计    -->
        <el-row style="font-weight: bold; font-size: 14px; margin-top: 2%">
          <el-card class="box-card card-radius" shadow="always">
            <el-row>
              <span>工单</span><span class="text-blue">统计</span>
            </el-row>
            <el-row style="font-size: 12px">
              <el-table :data="orderList" :show-header="false" size="mini">
                <el-table-column
                  label="工单名称"
                  width="94"
                  align="left"
                  prop="orderName"
                ></el-table-column>
                <el-table-column
                  label="工单数量"
                  align="right"
                  prop="orderNum"
                ></el-table-column>
              </el-table>
            </el-row>
          </el-card>
        </el-row>
        <!--  社区公告    -->
        <router-link to="/information/communityNotice">
          <el-row style="font-size: 14px; margin-top: 3%">
            <el-card class="box-card card-radius" shadow="always">
              <el-row style="font-weight: bold">
                <span>社区</span><span class="text-blue">公告</span>
              </el-row>
              <div class="list-scroll">
                <el-row v-for="notice in noticeList">
                  <el-col :span="12" class="list-content">{{
                    notice.noticeTitle
                  }}</el-col>
                  <el-col
                    :span="12"
                    class="list-content"
                    style="color: #adadad"
                    >{{ notice.updateTime }}</el-col
                  >
                </el-row>
              </div>
            </el-card>
          </el-row>
        </router-link>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import echarts from "echarts";
import {
  auditNum,
  communityIdList,
  energyData,
  getFoundData,
  getNoticeLists,
  getOrderLists,
  getRoomStatusData,
  logList,
} from "@/api/estate/index";

export default {
  name: "Index",
  data() {
    return {
      //房产审核数量
      houseNum: 0,
      //车辆审核数量
      vehicleNum: 0,
      //生日节日待发送数量
      sendNum: 0,
      //业主审核数量
      ownerNum: 0,
      //租客审核数量
      tenantNum: 0,
      //投诉建议待回复
      complaintSuggestionNum: 0,
      // 人行/车行chart
      logChart: null,
      // 人行数据
      userNumList: [],
      // 车行数据
      vehicleNumList: [],
      // 当前物业小区数据
      communityList: [],
      // 房屋状态查询条件
      houseQuery: {
        communityId: undefined,
      },
      // 人行/车行查询条件
      logQuery: {
        communityId: undefined,
      },
      // 房屋状态chart
      roomStatusChart: null,
      // 房屋状态数据
      roomStatusData: {
        // 未售
        saleNum: 0,
        // 已售
        unSaleNum: 0,
        // 入住
        checkInNum: 0,
        // 装修
        decorateNum: 0,
        // 已拿房
        emptyNum: 0,
        // 整租
        wholeNum: 0,
        // 合租
        jointNum: 0,
        // 群租
        groupNum: 0,
      },
      // 能耗统计查询条件
      energyQuery: {
        communityId: undefined,
      },
      // 能耗类型
      energyType: 0,
      // 能耗统计echart
      energyChart: null,
      // 用水量数据
      waterData: {
        thisYear: [],
        lastYear: [],
      },
      // 用电量数据
      eleData: {
        thisYear: [],
        lastYear: [],
      },
      foundDataList: [],
      // 燃气量数据
      gasData: {
        thisYear: [],
        lastYear: [],
      },
      // 基金统计查询条件
      foundQuery: {
        communityId: undefined,
      },
      // 基金统计chart
      foundChart: null,
      // 物业费
      houseAmount: 0,
      // 车位费
      specialAmount: 0,
      // 工单数据
      orderList: [],
      // 公告数据
      noticeList: [],
    };
  },
  mounted() {
    this.$nextTick(() => {
      this.getAuditNum();
      this.initChart();
      this.getCommunityList();
      this.getOrderList();
      this.getNoticeList();
    });
  },
  methods: {
    // 查询审核数量
    async getAuditNum() {
      await auditNum().then((res) => {
        this.houseNum = Math.round(res.data.houseNum);
        this.vehicleNum = Math.round(res.data.vehicleNum);
        this.sendNum = Math.round(res.data.sendNum);
        this.complaintSuggestionNum = Math.round(
          res.data.complaintSuggestionNum
        );
      });
    },
    // 初始化echarts
    initChart() {
      let that = this;
      that.logChart = echarts.init(that.$refs.logRef, "macarons");
      that.roomStatusChart = echarts.init(that.$refs.roomStatusRef, "macarons");
      that.energyChart = echarts.init(that.$refs.energyRef, "macarons");
      that.foundChart = echarts.init(that.$refs.foundRef, "foundRef");
      //窗口监听事件
      window.addEventListener("resize", function () {
        that.logChart.resize();
        that.roomStatusChart.resize();
        that.energyChart.resize();
        that.foundChart.resize();
      });
    },
    // 查询车行/人行数据
    getLogList() {
      let query = {
        communityId: Number(this.logQuery.communityId),
      };
      logList(query).then((res) => {
        this.userNumList = res.data.userNumList;
        this.vehicleNumList = res.data.vehicleNumList;
        this.setLogOption();
      });
    },
    // 设置人行/车行图表
    setLogOption() {
      this.logChart.setOption({
        tooltip: {
          trigger: "axis",
        },
        legend: {
          top: 0,
          right: 10,
          //itemGap: 37,
          itemWidth: 20,
          itemHeight: 20,
          icon: "roundRect",
          textStyle: {
            color: "#666666",
            fontSize: 14,
            padding: [2, 0, 0, 0],
          },
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: {
          type: "category",
          boundaryGap: true, //坐标轴两边留白
          data: [
            "00:00~01:59",
            "02:00~03:59",
            "04:00~05:59",
            "06:00~07:59",
            "08:00~09:59",
            "10:00~11:59",
            "12:00~13:59",
            "14:00~15:59",
            "16:00~17:59",
            "18:00~19:59",
            "20:00~21:59",
            "22:00~23:59",
          ],
          axisLabel: {
            interval: 0,
            rotate: 25,
            textStyle: {
              color: "#999999",
              fontStyle: "normal",
              fontSize: 10,
            },
          },
          axisTick: {
            show: false,
          },
          axisLine: {
            show: false,
          },
        },
        yAxis: [
          {
            type: "value",
            splitLine: {
              show: false,
            },
            axisLabel: {
              textStyle: {
                color: "#999999",
                fontSize: 14,
              },
            },
            axisLine: {
              show: false,
            },
            axisTick: {
              show: false,
            },
          },
        ],
        series: [
          {
            name: "人行",
            type: "line",
            data: this.userNumList,
            color: "#FF6D41",
            smooth: true,
            symbol: "circle",
            symbolSize: 0,
            lineStyle: {
              width: 4,
            },
          },
          {
            name: "车行",
            type: "line",
            data: this.vehicleNumList,
            color: "#816AF8",
            smooth: true,
            symbol: "circle",
            symbolSize: 0,
            lineStyle: {
              width: 4,
            },
          },
        ],
      });
    },

    // 查询所有小区
    async getCommunityList() {
      await communityIdList().then((res) => {
        this.communityList = res.data;
        this.houseQuery.communityId = this.communityList[0].communityId;
        this.energyQuery.communityId = this.communityList[0].communityId;
        this.foundQuery.communityId = this.communityList[0].communityId;
        this.logQuery.communityId = this.communityList[0].communityId;
      });
      this.selectRoomStatus();
      this.selectEnergyData();
      this.selectFoundData();
      this.getLogList();
    },

    // 查询房屋状态
    selectRoomStatus() {
      let query = {
        communityId: Number(this.houseQuery.communityId),
      };
      getRoomStatusData(query).then((res) => {
        this.roomStatusData = {
          saleNum: res.data.saleNum,
          unSaleNum: res.data.unSaleNum,
          checkInNum: res.data.checkInNum,
          decorateNum: res.data.decorateNum,
          emptyNum: res.data.emptyNum,
          wholeNum: res.data.wholeNum,
          jointNum: res.data.jointNum,
          groupNum: res.data.groupNum,
        };
        this.setRoomStatusOption();
      });
    },
    // 设置房屋状态图表
    setRoomStatusOption() {
      let roomTotal =
        this.roomStatusData.saleNum +
        this.roomStatusData.unSaleNum +
        this.roomStatusData.checkInNum +
        this.roomStatusData.decorateNum +
        this.roomStatusData.emptyNum +
        this.roomStatusData.wholeNum +
        this.roomStatusData.jointNum +
        this.roomStatusData.groupNum;

      this.roomStatusChart.setOption(
        {
          color: [
            "#1A88E7",
            "#E8E8E8",
            "#1AE750",
            "#EFA648",
            "#E2CC00",
            "#00D1FE",
            "#00B4CE",
            "#FF4946",
          ],
          tooltip: {
            trigger: "item",
          },
          legend: {
            left: "right",
            orient: "vertical",
            y: "center",
          },
          title: {
            zlevel: 0,
            text: [
              "{value|" + JSON.stringify(roomTotal) + "}",
              "{name|房屋总数}",
            ].join("\n"),
            top: "center",
            left: "33%",
            textAlign: "center",
            textStyle: {
              rich: {
                value: {
                  color: "#353535",
                  fontSize: 28,
                  fontWeight: "bold",
                  lineHeight: 40,
                },
                name: {
                  color: "#353535",
                  fontSize: 14,
                  fontWeight: "bold",
                  lineHeight: 18,
                },
              },
            },
          },
          series: [
            {
              name: "",
              type: "pie",
              radius: ["50%", "85%"],
              center: ["35%", "50%"],
              avoidLabelOverlap: false,
              itemStyle: {
                borderColor: "#fff",
                borderWidth: 2,
              },
              label: {
                show: false,
              },
              emphasis: {
                label: {
                  show: false,
                },
              },
              //0=未售,1=已售未拿房,2=入住,3=装修,4=已售已拿房,5=出租
              data: [
                { name: "未售", value: this.roomStatusData.saleNum },
                { name: "已售未拿房", value: this.roomStatusData.unSaleNum },
                { name: "入住", value: this.roomStatusData.checkInNum },
                { name: "装修", value: this.roomStatusData.decorateNum },
                { name: "已售已拿房", value: this.roomStatusData.emptyNum },
                { name: "出租", value: this.roomStatusData.wholeNum },
              ],
            },
          ],
        },
        true
      );
    },

    // 查询能耗数据
    selectEnergyData() {
      let query = {
        communityId: Number(this.energyQuery.communityId),
        energyType: Number(this.energyType),
      };

      energyData(query).then((res) => {
        // 用水量
        if (0 === this.energyType) {
          this.waterData.thisYear = res.data.thisYear;
          this.waterData.lastYear = res.data.lastYear;

          this.setEnergyOption(
            "吨",
            this.waterData.thisYear,
            this.waterData.lastYear
          );
        }
        // 用水量
        else if (1 === this.energyType) {
          this.eleData.thisYear = res.data.thisYear;
          this.eleData.lastYear = res.data.lastYear;

          this.setEnergyOption(
            "度",
            this.eleData.thisYear,
            this.eleData.lastYear
          );
        }
        // 燃气量
        else {
          this.gasData.thisYear = res.data.thisYear;
          this.gasData.lastYear = res.data.lastYear;

          this.setEnergyOption(
            "立方",
            this.gasData.thisYear,
            this.gasData.lastYear
          );
        }
      });
    },
    // 设置能耗图表
    setEnergyOption(unit, thisYearData, lastYearData) {
      let thisYear = new Date().getFullYear();
      let lastYear = thisYear - 1;
      this.energyChart.setOption(
        {
          tooltip: {
            trigger: "axis",
          },
          grid: {
            left: "3%",
            right: "4%",
            bottom: "3%",
            top: 40,
            containLabel: true,
          },
          legend: {
            icon: "circle",
            right: "4%",
            itemWidth: 20,
            itemHeight: 10,
            data: [JSON.stringify(thisYear), JSON.stringify(lastYear)],
            textStyle: {
              color: "#555",
            },
          },
          xAxis: {
            type: "category",
            data: [
              "1月",
              "2月",
              "3月",
              "4月",
              "5月",
              "6月",
              "7月",
              "8月",
              "9月",
              "10月",
              "11月",
              "12月",
            ],
            axisLabel: {
              //坐标轴字体颜色
              textStyle: {
                color: "#9eaaba",
              },
            },
            axisLine: {
              show: false,
            },
            axisTick: {
              //y轴刻度线
              show: false,
            },
            splitLine: {
              //网格
              show: false,
            },
            boundaryGap: false,
          },
          yAxis: {
            type: "value",
            name: "单位：" + unit,
            axisLabel: {
              //坐标轴字体颜色
              textStyle: {
                color: "#9eaaba",
              },
            },
            axisLine: {
              show: false,
            },
            axisTick: {
              //y轴刻度线
              show: false,
            },
            splitLine: {
              //网格
              show: false,
            },
          },
          series: [
            {
              name: JSON.stringify(thisYear),
              type: "line",
              smooth: true,
              symbol: "circle",
              symbolSize: 0,
              itemStyle: {
                color: "#2D9AFF",
                borderWidth: 0,
              },
              areaStyle: {
                color: "#2D9AFF",
              },
              data: thisYearData,
            },
            {
              name: JSON.stringify(lastYear),
              type: "line",
              smooth: true,
              symbol: "circle",
              symbolSize: 0,
              itemStyle: {
                color: "#83CFFE",
                borderWidth: 0,
              },
              areaStyle: {
                color: "#83CFFE",
              },
              data: lastYearData,
            },
          ],
        },
        true
      );
    },

    // 查询基金投票金额
    selectFoundData() {
      let query = {
        communityId: Number(this.foundQuery.communityId),
      };
      getFoundData(query).then((res) => {
        this.foundDataList = res;
        this.setFoundOption();
      });
    },
    // 设置基金图表
    setFoundOption() {
      var foundDataList = this.foundDataList.data;
      var totalAmount = this.foundDataList.totalAmount;
      //let totalAmount = this.houseAmount + this.specialAmount;
      this.foundChart.setOption(
        {
          color: ["#31BAFF", "#B6AEFF", "#1ae750", "#e2cc00"],
          tooltip: {
            trigger: "item",
          },
          legend: {
            left: "right",
            orient: "vertical",
            y: "center",
          },
          title: {
            zlevel: 0,
            text: [
              "{value|" + JSON.stringify(totalAmount) + "}",
              "{name|年累计金额}",
            ].join("\n"),
            top: "center",
            left: "33%",
            textAlign: "center",
            textStyle: {
              rich: {
                value: {
                  color: "#353535",
                  fontSize: 28,
                  fontWeight: "bold",
                  lineHeight: 40,
                },
                name: {
                  color: "#BFBFBF",
                  fontSize: 14,
                  fontWeight: "bold",
                  lineHeight: 18,
                },
              },
            },
          },
          series: [
            {
              name: "",
              type: "pie",
              radius: ["50%", "85%"],
              center: ["35%", "50%"],
              avoidLabelOverlap: false,
              itemStyle: {
                borderColor: "#fff",
                borderWidth: 2,
              },
              label: {
                show: false,
              },
              emphasis: {
                label: {
                  show: false,
                },
              },
              data: foundDataList,
            },
          ],
        },
        true
      );
    },

    // 获取工单数量
    getOrderList() {
      getOrderLists().then((res) => {
        this.orderList = res.data;
      });
    },

    // 查询社区公告
    getNoticeList() {
      getNoticeLists().then((res) => {
        this.noticeList = res.data;
      });
    },
  },
};
</script>

<style scoped lang="scss">
.card-radius {
  border-radius: 15px;
}

.text-blue {
  color: #3ba0f4;
}

.bg-img {
  background-size: 100% 100%;
  background-repeat: no-repeat;
}

.audit-house {
  background-image: url("../assets/images/index/audit-house.png");
  color: #333;
  height: 120px;
}

.audit-vehicle {
  background-image: url("../assets/images/index/audit-vehicle.png");
  color: #333;
  height: 120px;
}

.audit-owner {
  background-image: url("../assets/images/index/audit-owner.png");
  color: #333;
  height: 120px;
}

.audit-tenant {
  background-image: url("../assets/images/index/audit-tenant.png");
  color: #333;
  height: 120px;
}

.quick-text {
  text-align: center;
  font-size: 12px;
  margin-top: 4px;
}

.quick-venue {
  background-image: url("../assets/images/index/quick-venue.png");
  height: 35px;
  background-position-x: center;
  background-size: contain;
}

.quick-borrow {
  background-image: url("../assets/images/index/quick-borrow.png");
  height: 35px;
  background-position-x: center;
  background-size: contain;
}

.quick-activity {
  background-image: url("../assets/images/index/quick-activity.png");
  height: 35px;
  background-position-x: center;
  background-size: contain;
}

.quick-older {
  background-image: url("../assets/images/index/quick-older.png");
  height: 35px;
  background-position-x: center;
  background-size: contain;
}

.quick-complaint {
  background-image: url("../assets/images/index/quick-complaint.png");
  height: 35px;
  background-position-x: center;
  background-size: contain;
}

.quick-notice {
  background-image: url("../assets/images/index/quick-notice.png");
  height: 35px;
  background-position-x: center;
  background-size: contain;
}

.quick-illegal {
  background-image: url("../assets/images/index/quick-illegal.png");
  height: 35px;
  background-position-x: center;
  background-size: contain;
}

.quick-decorate {
  background-image: url("../assets/images/index/quick-decorate.png");
  height: 35px;
  background-position-x: center;
  background-size: contain;
}

.quick-income {
  background-image: url("../assets/images/index/quick-income.png");
  height: 35px;
  background-position-x: center;
  background-size: contain;
}

.quick-xcgd {
  background-image: url("../assets/images/index/quick-xcgd.png");
  height: 35px;
  background-position-x: center;
  background-size: contain;
}

.quick-bsbx {
  background-image: url("../assets/images/index/quick-bsbx.png");
  height: 35px;
  background-position-x: center;
  background-size: contain;
}

.quick-found {
  background-image: url("../assets/images/index/quick-found.png");
  height: 35px;
  background-position-x: center;
  background-size: contain;
}
.quick-found2 {
  background-image: url("../assets/images/index/quick-found2.png");
  height: 35px;
  background-position-x: center;
  background-size: contain;
}

.quick-property {
  background-image: url("../assets/images/index/quick-property.png");
  height: 35px;
  background-position-x: center;
  background-size: contain;
}

.quick-wyf {
  background-image: url("../assets/images/index/quick-wyf.png");
  height: 35px;
  background-position-x: center;
  background-size: contain;
}

.quick-xzyg {
  background-image: url("../assets/images/index/quick-xzyg.png");
  height: 35px;
  background-position-x: center;
  background-size: contain;
}

.quick-cbgd {
  background-image: url("../assets/images/index/quick-cbgd.png");
  height: 35px;
  background-position-x: center;
  background-size: contain;
}

.quick-cbsb {
  background-image: url("../assets/images/index/quick-cbsb.png");
  height: 35px;
  background-position-x: center;
  background-size: contain;
}

.quick-contact {
  background-image: url("../assets/images/index/quick-contact.png");
  height: 35px;
  background-position-x: center;
  background-size: contain;
}

.list-scroll {
  line-height: 30px;
  overflow-y: scroll;
  height: 150px;
}

.list-scroll::-webkit-scrollbar {
  display: none;
}

.list-content {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
}
</style>


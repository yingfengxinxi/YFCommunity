<template>
  <div class="app-container home">
    <el-row :gutter="20" type="flex" class="row-bg" justify="space-between">
      <el-col :span="13" class="numberBox">
        <el-row
          :gutter="20"
          type="flex"
          justify="space-between"
          style="font-size: 14px"
        >
          <el-col :span="8">
            <el-card class="box-card card-radius" shadow="always">
              <el-row
                type="flex"
                justify="space-between"
                style="margin-top: 5%; margin-bottom: 5%"
              >
                <el-col :span="6" class="bg-img property"></el-col>
                <el-col :span="9" style="line-height: 25px">
                  <el-col>物业数量</el-col>
                  <el-col style="font-weight: bold">{{ propertyNum }}</el-col>
                </el-col>
                <el-col :span="6" class="bg-img property-bar"></el-col>
              </el-row>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card class="box-card card-radius" shadow="always">
              <el-row
                type="flex"
                justify="space-between"
                style="margin-top: 5%; margin-bottom: 5%"
              >
                <el-col :span="6" class="bg-img staff"></el-col>
                <el-col :span="9" style="line-height: 25px">
                  <el-col>员工数量</el-col>
                  <el-col style="font-weight: bold">{{ staffNum }}</el-col>
                </el-col>
                <el-col :span="6" class="bg-img staff-bar"></el-col>
              </el-row>
            </el-card>
          </el-col>
          <el-col :span="8">
            <el-card class="box-card card-radius" shadow="always">
              <el-row
                type="flex"
                justify="space-between"
                style="margin-top: 5%; margin-bottom: 5%"
              >
                <el-col :span="6" class="bg-img guest"></el-col>
                <el-col :span="9" style="line-height: 25px">
                  <el-col>外来人员数量</el-col>
                  <el-col style="font-weight: bold">{{ guestNum }}</el-col>
                </el-col>
                <el-col :span="6" class="bg-img guest-bar"></el-col>
              </el-row>
            </el-card>
          </el-col>
        </el-row>
      </el-col>
      <!--  快捷菜单    -->
      <el-col :span="11">
        <el-row style="font-size: 14px">
          <el-card class="box-card card-radius" shadow="always">
            <el-row style="font-weight: bold">
              <span>快捷</span><span class="text-blue">菜单</span>
            </el-row>
            <el-row style="margin-top: 4px; line-height: 20px">
              <router-link to="/system/dept">
                <el-col :span="4">
                  <div class="bg-img quick-dept"></div>
                  <el-row class="quick-text">部门管理</el-row>
                </el-col>
              </router-link>
              <router-link to="/property/estate">
                <el-col :span="4">
                  <div class="bg-img quick-property"></div>
                  <el-row class="quick-text">物业信息管理</el-row>
                </el-col>
              </router-link>
              <router-link to="/system/log/operlog">
                <el-col :span="4">
                  <div class="bg-img quick-log"></div>
                  <el-row class="quick-text">日志管理</el-row>
                </el-col>
              </router-link>
              <router-link to="/system/sensitive">
                <el-col :span="4">
                  <div class="bg-img quick-sensitive"></div>
                  <el-row class="quick-text">敏感词管理</el-row>
                </el-col>
              </router-link>
              <router-link to="/community/area">
                <el-col :span="4">
                  <div class="bg-img quick-area"></div>
                  <el-row class="quick-text">地区管理</el-row>
                </el-col>
              </router-link>
              <router-link to="/community/community">
                <el-col :span="4">
                  <div class="bg-img quick-community"></div>
                  <el-row class="quick-text">社区信息管理</el-row>
                </el-col>
              </router-link>
            </el-row>
          </el-card>
        </el-row>
      </el-col>
    </el-row>
    <el-row
      :gutter="20"
      type="flex"
      class="row-bg"
      justify="space-between"
      style="margin-top: 1%"
    >
      <el-col :span="13">
        <el-row
          :gutter="20"
          style="font-weight: bold"
          type="flex"
          justify="space-between"
        >
          <!--   工单评分统计     -->
          <el-col :span="24">
            <el-card class="box-card card-radius" shadow="always">
              <el-row style="font-size: 14px">
                <el-col :span="4" style="margin-top: 5px"
                  ><span>工单</span><span class="text-blue">评分统计</span>
                </el-col>
                <el-col :span="6">
                  <el-select
                    v-model="communityId"
                    placeholder="请选择小区"
                    size="mini"
                    @change="selectOrder"
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
              <el-row>
                <div ref="orderStarRef" style="height: 270px"></div>
              </el-row>
            </el-card>
          </el-col>
        </el-row>
      </el-col>
      <el-col :span="11">
        <!--  物业工单数量排名    -->
        <el-row
          :gutter="20"
          type="flex"
          justify="space-between"
          style="font-size: 14px; height: 100%"
        >
          <!--   物业信息统计     -->
          <el-col :span="12">
            <el-card class="box-card card-radius" shadow="always">
              <el-row style="font-size: 14px; font-weight: bold">
                <span>物业</span><span class="text-blue">信息统计</span>
              </el-row>
              <el-row>
                <div ref="propertyRef" style="height: 280px"></div>
              </el-row>
            </el-card>
          </el-col>
          <el-col :span="12">
            <el-card
              class="box-card card-radius"
              shadow="always"
              style="height: 100%"
            >
              <el-row style="font-weight: bold">
                <span>平台</span><span class="text-blue">工单数量排名</span>
              </el-row>
              <div class="list-scroll">
                <el-row
                  v-for="(estate, index) in estateList"
                  style="height: 50px"
                  v-if="index < 5"
                >
                  <el-col
                    :span="4"
                    class="bg-img point-img point-red"
                    v-if="index === 0"
                    >{{ index + 1 }}</el-col
                  >
                  <el-col
                    :span="4"
                    class="bg-img point-img point-org"
                    v-else-if="index === 1"
                    >{{ index + 1 }}</el-col
                  >
                  <el-col
                    :span="4"
                    class="bg-img point-img point-yellow"
                    v-else-if="index === 1"
                    >{{ index + 1 }}</el-col
                  >
                  <el-col
                    :span="4"
                    class="bg-img point-img point-gray"
                    v-else
                    >{{ index + 1 }}</el-col
                  >
                  <el-col
                    :span="12"
                    :offset="2"
                    class="content-omit"
                    style="color: #353535; font-weight: bold"
                  >
                    {{ estate.estateName }}
                  </el-col>
                  <el-col
                    :span="4"
                    :offset="2"
                    class="content-omit"
                    style="color: #dadada"
                    >{{ estate.orderTotal }}
                  </el-col>
                </el-row>
              </div>
            </el-card>
          </el-col>
        </el-row>
      </el-col>
    </el-row>
    <!--   车流人流量统计     -->
    <el-row
      :gutter="20"
      style="margin-top: 1%"
      type="flex"
      class="row-bg"
      justify="space-between"
    >
      <el-col :span="13">
        <el-card class="box-card card-radius" shadow="always">
          <el-row style="font-size: 14px">
            <el-col :span="4" style="margin-top: 5px; font-weight: bold">
              <span>车流</span><span class="text-blue">人流量统计</span>
            </el-col>
            <el-col :span="6">
              <el-select
                v-model="communityId1"
                placeholder="请选择小区"
                size="mini"
                @change="getContractData"
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
            <el-col :span="6">
              <el-select
                v-model="selectDay"
                placeholder="请选择方式"
                size="mini"
                @change="selectDM"
              >
                <el-option
                  v-for="item in daysData"
                  :key="item"
                  :label="item"
                  :value="item"
                >
                </el-option>
              </el-select>
            </el-col>
          </el-row>
          <el-row>
            <div ref="contractRef" style="width: 100%; height: 270px"></div>
          </el-row>
        </el-card>
      </el-col>
      <el-col :span="11">
        <el-card class="box-card card-radius flex" shadow="always">
          <el-row style="font-size: 14px">
            <el-col :span="4" style="margin-top: 5px; font-weight: bold"
              ><span>设备</span><span class="text-blue">信息</span>
            </el-col>
            <el-col :span="6">
              <el-select
                v-model="communityId2"
                placeholder="请选择小区"
                size="mini"
                @change="findListBusDoor"
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
          <el-table :data="tableData" style="width: 100%">
            <el-table-column
              align="center"
              prop="doorName"
              label="资源名称"
              width="180"
            >
            </el-table-column>
            <el-table-column width="90" align="center" prop="type" label="类型">
            </el-table-column>
            <el-table-column
              align="center"
              prop="manufacturer"
              label="厂商"
              width="160"
            >
            </el-table-column>
            <el-table-column align="center" prop="online" label="状态">
            </el-table-column>
            <el-table-column align="center" prop="inoutType" label="进出状态">
            </el-table-column>
            <el-table-column fixed="right" label="操作" width="100">
              <template slot-scope="scope">
                <el-button
                  @click="handleClick(scope.row)"
                  type="text"
                  size="small"
                  >查看</el-button
                >
              </template>
            </el-table-column>
          </el-table>

          <el-pagination
            hide-on-single-page
            @current-change="handleCurrentChange"
            :current-page="page.pageNum"
            :page-size="page.pageSize"
            layout="total, prev, pager, next, jumper"
            :total="page.total"
          >
          </el-pagination>
        </el-card>
      </el-col>
    </el-row>
    <el-dialog title="" :visible.sync="dialogTableVisible">
      <el-descriptions title="设备信息">
        <el-descriptions-item label="资源名称">{{
          detail.doorName
        }}</el-descriptions-item>
        <el-descriptions-item label="类型">{{
          detail.type
        }}</el-descriptions-item>
        <el-descriptions-item label="通道类型">{{
          detail.channelType
        }}</el-descriptions-item>
        <el-descriptions-item label="厂商">{{
          detail.manufacturer
        }}</el-descriptions-item>
        <el-descriptions-item label="设备所在地址">
          {{ detail.address }}
        </el-descriptions-item>
        <el-descriptions-item label="在线状态">{{
          detail.online
        }}</el-descriptions-item>
        <el-descriptions-item label="编号">{{
          detail.doorNo
        }}</el-descriptions-item>
        <el-descriptions-item label="进出类型">{{
          detail.inoutType
        }}</el-descriptions-item>
      </el-descriptions>
    </el-dialog>
  </div>
</template>

<script>
import { mapGetters, mapState } from "vuex";
import * as echarts from "echarts";
import * as home from "@/api/home";

export default {
  name: "Index",
  data() {
    return {
      // 物业数量
      propertyNum: 0,
      // 员工数量
      staffNum: 0,
      // 外来人员数量
      guestNum: 0,
      // 小区数据
      communityList: [],
      // 小区id
      communityId: 0,
      // 人流车流量小区
      communityId1: 0,
      // 设备信息小区
      communityId2: 0,
      // 工单chart
      orderStarChart: null,
      // 居家维修
      homeOrder: {},
      // 园区报事
      parkOrder: {},
      // 灾害报修
      disasterOrder: {},
      // 物业信息chart
      propertyChart: null,
      // 物业数量
      propertyData: {},
      // 合同echart
      contractChart: null,
      // 租房合同
      tenancy: {},
      // 车流人流量
      userNumBig: {},
      userNumBigMonth: {},
      vehicleNumBig: {},
      vehicleNumBigMonth: {},
      days: [],
      selectDay: "年",
      daysData: ["年", "月"],
      maxRen: 0,
      maxChe: 0,
      // 采购合同
      purchase: {},
      // 服务合同
      server: {},
      // 物业数据
      estateList: [],
      tableData: [],
      dialogTableVisible: false,
      page: {
        pageNum: 1,
        pageSize: 5,
        total: null,
      },
      detail: {},
    };
  },
  computed: {
    ...mapGetters(["sidebar"]),
  },
  watch: {
    "sidebar.opened": {
      deep: true,
      handler: function (newVal, oldVal) {
        setTimeout(() => {
          this.orderStarChart.resize();
          this.propertyChart.resize();
          this.contractChart.resize();
        }, 100);
      },
    },
  },
  mounted() {
    let that = this;
    this.getCommunityList();
    this.$nextTick(() => {
      that.getContractData();
      that.getUserNum();
      that.getPropertyData();
      that.getEstateList();
      that.initChart();
    });
  },
  methods: {
    handleCurrentChange(val) {
      console.log(`当前页: ${val}`);
      this.page.pageNum = val;
      this.findListBusDoor();
    },
    handleClick(row) {
      console.log(row);
      this.dialogTableVisible = true;
      this.detail = row;
    },
    // 初始化echarts
    initChart() {
      let that = this;
      that.orderStarChart = echarts.init(that.$refs.orderStarRef, "macarons");
      that.propertyChart = echarts.init(that.$refs.propertyRef, "macarons");
      that.contractChart = echarts.init(that.$refs.contractRef, "macarons");
      //窗口监听事件
      window.addEventListener("resize", function () {
        that.orderStarChart.resize();
        that.propertyChart.resize();
        that.contractChart.resize();
      });
    },
    selectDM(e) {
      this.selectDay = e;
      this.setContractOption();
    },
    // 查询物业数量
    getUserNum() {
      home.count().then((res) => {
        this.propertyNum = res.data.estateCount;
        this.staffNum = res.data.staffCount;
        this.guestNum = res.data.foreignStaffCount;
      });
    },

    // 查询所有小区
    async getCommunityList() {
      await home.listCommunities().then((res) => {
        console.log("查询所有小区");
        this.communityList = res.data;
        if (this.communityList.length > 0) {
          this.communityId = this.communityList[0].communityId;
          this.communityId1 = this.communityList[0].communityId;
          this.communityId2 = this.communityList[0].communityId;
          console.log(this.communityId, this.communityId1, this.communityId2);
        }
      });
      await this.selectOrder();
      await this.findListBusDoor();
      await this.getContractData();
    },
    // 查询设备信息
    async findListBusDoor() {
      await home
        .findListBusDoor({
          communityId: this.communityId2,
          pageSize: this.page.pageSize,
          pageNum: this.page.pageNum,
        })
        .then((res) => {
          console.log(res, "resss");
          this.tableData = res.rows;
          this.page.total = res.total;
          // if (this.tableData.length < this.page.total) {
          //   this.page.pageSize++;
          // }
          // this.communityList = res.data;
          // if (this.communityList.length > 0) {
          //   this.communityId = this.communityList[0].communityId;
          // }
        });
      // await this.selectOrder();
    },

    // 查询工单
    async selectOrder() {
      await home.scoreStatistics(this.communityId).then((res) => {
        this.homeOrder = res.data;
        // this.parkOrder = res.data.parkOrder;
        // this.disasterOrder = res.data.disasterOrder;
      });
      this.setStarOption();
    },
    // 设置工单星级图表
    setStarOption() {
      console.log("设置工单星级图表");
      this.orderStarChart.setOption({
        tooltip: {
          trigger: "axis",
          padding: [8, 10],
          backgroundColor: "rgba(0,0,0,0.5)",
          axisPointer: {
            type: "shadow",
            textStyle: {
              color: "#fff",
            },
          },
        },
        legend: {
          // data: ["居家维修工单"],
          align: "left",
          right: 0,
          textStyle: {
            color: "#969696",
            fontSize: 14,
            fontWeight: 200,
          },
          itemWidth: 14,
          itemHeight: 14,
        },
        grid: {
          left: "20",
          right: "0",
          bottom: "8%",
          top: "15%",
          containLabel: true,
        },
        label: {
          show: true,
          position: "top",
          color: "#969696",
          fontSize: 14,
          fontWeight: 700,
        },
        xAxis: [
          {
            type: "category",
            offset: 10,
            data: ["一星", "二星", "三星", "四星", "五星"],
            axisLine: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            axisLabel: {
              show: true,
              textStyle: {
                color: "#969696",
                fontSize: 16,
                fontWeight: 200,
              },
            },
          },
        ],
        yAxis: [
          {
            type: "value",
            name: "单位：个",
            axisLine: {
              show: false,
            },
            splitLine: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            minInterval: 1,
          },
        ],
        series: [
          {
            name: "报事报修工单",
            type: "bar",
            data: [
              this.homeOrder.oneStar,
              this.homeOrder.twoStar,
              this.homeOrder.threeStar,
              this.homeOrder.fourStar,
              this.homeOrder.fiveStar,
            ],
            barWidth: 22, //柱子宽度
            barGap: 0.2, //柱子之间间距
            label: {
              show: false,
            },
            itemStyle: {
              normal: {
                color: "#2F9EFF",
                opacity: 1,
              },
            },
          },
        ],
      });
    },

    // 查询物业数量
    async getPropertyData() {
      await home.estateStatistics().then((res) => {
        this.propertyData = res.data;
      });

      this.setPropertyOption();
    },
    // 设置物业数量图表
    setPropertyOption() {
      let total =
        this.propertyData.standNum +
        this.propertyData.employedNum +
        this.propertyData.revokeNum +
        this.propertyData.removeNum +
        this.propertyData.moveInNum +
        this.propertyData.moveOutNum +
        this.propertyData.closeNum +
        this.propertyData.clearNum;
      this.propertyChart.setOption({
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
            "{value|" + JSON.stringify(total) + "}",
            "{name|物业总数}",
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
            radius: ["40%", "60%"],
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
            data: [
              { name: "存续", value: this.propertyData.standNum },
              { name: "在业", value: this.propertyData.employedNum },
              { name: "吊销", value: this.propertyData.revokeNum },
              { name: "注销", value: this.propertyData.removeNum },
              { name: "迁入", value: this.propertyData.moveInNum },
              { name: "迁出", value: this.propertyData.moveOutNum },
              { name: "停业", value: this.propertyData.closeNum },
              { name: "清算", value: this.propertyData.clearNum },
            ],
          },
        ],
      });
    },

    // 查询合同数据
    async getContractData() {
      await home
        .getLogListMonthDay({
          communityId: this.communityId1,
          year: new Date().getFullYear(),
          month: new Date().getMonth() + 1,
        })
        .then((res) => {
          this.userNumBig = res.data.userNumBig;
          this.userNumBigMonth = res.data.userNumBigMonth;
          this.vehicleNumBig = res.data.vehicleNumBig;
          this.vehicleNumBigMonth = res.data.vehicleNumBigMonth;
          this.days = [];
          this.userNumBig.forEach((item) => {
            this.days.push(item.day);
          });
          // this.tenancy = res.data.tenancy;
          // this.purchase = res.data.purchase;
          // this.server = res.data.server;
        });

      this.setContractOption();
    },
    // 设置车流人流量统计
    setContractOption() {
      let option8 = {
        tooltip: {
          trigger: "axis",
        },
        legend: {
          icon: "rect",
          itemWidth: 14,
          itemHeight: 5,
          itemGap: 13,
          data: ["人行", "车行"],
          top: 30,
          right: "center",
          textStyle: {
            fontSize: 12,
          },
        },
        grid: {
          left: "3%",
          right: "4%",
          bottom: "3%",
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            boundaryGap: false,
            data: [],
          },
        ],
        yAxis: [
          {
            type: "value",
            name: "单位：次",
            min: 0,
            max: 2000,
            nameTextStyle: {
              padding: [0, 44, -1, 0],
            },
            axisTick: {
              show: false,
            },
            axisLine: {
              show: false,
            },

            axisLabel: {
              margin: 10,
              textStyle: {
                fontSize: 14,
              },
            },
          },
        ],
        series: [
          {
            name: "人流",
            type: "line",
            smooth: false,
            symbol: "circle",
            symbolSize: 5,
            showSymbol: false,
            lineStyle: {
              normal: {
                width: 1,
              },
            },
            data: [
              0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
              0, 0, 0, 0, 0, 0, 0, 0, 0,
            ],
          },
          {
            name: "车流",
            type: "line",
            smooth: false,
            symbol: "circle",
            symbolSize: 5,
            showSymbol: false,
            lineStyle: {
              normal: {
                width: 1,
              },
            },
            data: [
              0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
              0, 0, 0, 0, 0, 0, 0, 0, 0,
            ],
          },
        ],
      };

      if (this.selectDay == "年") {
        option8.series[0].data = [
          "0",
          "0",
          "0",
          "0",
          "0",
          "0",
          "0",
          "0",
          "0",
          "0",
          "0",
          "0",
        ];
        option8.series[1].data = [
          "0",
          "0",
          "0",
          "0",
          "0",
          "0",
          "0",
          "0",
          "0",
          "0",
          "0",
          "0",
        ];
        this.maxChe = "";
        this.maxRen = "";
        this.userNumBigMonth.forEach((item, index) => {
          option8.series[0].data[index] = item.monthly_traffic;
          if (this.maxRen < item.monthly_traffic) {
            this.maxRen = item.monthly_traffic;
          }
        });
        this.vehicleNumBigMonth.forEach((item, index) => {
          // option8.series[1].data.push(item.daily_traffic);
          option8.series[1].data[index] = item.daily_traffic;
          if (this.maxChe < item.daily_traffic) {
            this.maxChe = item.daily_traffic;
          }
        });
        option8.xAxis[0].data = [
          "1",
          "2",
          "3",
          "4",
          "5",
          "6",
          "7",
          "8",
          "9",
          "10",
          "11",
          "12",
        ];
      } else if (this.selectDay == "月") {
        option8.xAxis[0].data = this.days;
        option8.series[0].data = [];
        this.maxChe = "";
        this.maxRen = "";
        this.userNumBig.forEach((item) => {
          option8.series[0].data.push(item.daily_traffic);
          if (this.maxRen < item.daily_traffic) {
            this.maxRen = item.daily_traffic;
          }
        });
        option8.series[1].data = [];
        this.vehicleNumBig.forEach((item) => {
          option8.series[1].data.push(item.daily_traffic);
          if (this.maxChe < item.daily_traffic) {
            this.maxChe = item.daily_traffic;
          }
        });
      } else {
        option8.series[0].data = [
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0,
        ];
        option8.series[1].data = [
          0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0,
          0, 0, 0, 0, 0, 0, 0, 0,
        ];
      }
      if (Number(this.maxChe) > Number(this.maxRen)) {
        option8.yAxis[0].max = this.maxChe;
      } else {
        option8.yAxis[0].max = this.maxRen;
      }
      this.contractChart.setOption(option8);
    },

    // 查询物业数据
    getEstateList() {
      // 排名
      home.orderRank().then((res) => {
        this.estateList = res.data;
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
  width: 50px;
  height: 50px;
}

.property {
  background-image: url("../assets/images/index/property.png");
  height: 50px;
  width: 50px;
}

.property-bar {
  background-image: url("../assets/images/index/property-bar.png");
  height: 50px;
}

.staff {
  background-image: url("../assets/images/index/staff.png");
  height: 50px;
  width: 50px;
}

.staff-bar {
  background-image: url("../assets/images/index/staff-bar.png");
  height: 50px;
}

.guest {
  background-image: url("../assets/images/index/guest.png");
  height: 50px;
  width: 50px;
}

.guest-bar {
  background-image: url("../assets/images/index/guest-bar.png");
  height: 50px;
}

.quick-text {
  text-align: center;
  font-size: 12px;
  margin-top: 4px;
}

.quick-dept {
  background-image: url("../assets/images/index/quick-dept.png");
  height: 50px;
  margin: 0 auto;
}

.quick-property {
  background-image: url("../assets/images/index/quick-property.png");
  height: 50px;
  margin: 0 auto;
}

.quick-log {
  background-image: url("../assets/images/index/quick-log.png");
  height: 50px;
  margin: 0 auto;
}

.quick-sensitive {
  background-image: url("../assets/images/index/quick-sensitive.png");
  height: 50px;
  margin: 0 auto;
}

.quick-area {
  background-image: url("../assets/images/index/quick-area.png");
  height: 50px;
  margin: 0 auto;
}

.quick-community {
  background-image: url("../assets/images/index/quick-community.png");
  height: 50px;
  margin: 0 auto;
}

::v-deep .el-card {
  height: 100%;
}

.list-scroll {
  line-height: 30px;
  overflow-y: scroll;
  height: auto;
  margin-top: 10px;
}

.list-scroll::-webkit-scrollbar {
  display: none;
}

.content-omit {
  text-overflow: ellipsis;
  overflow: hidden;
  white-space: nowrap;
  font-size: 12px;
}

.point-img {
  height: 30px;
  width: 30px;
  text-align: center;
  color: #ffffff;
}

.point-red {
  background-image: url("../assets/images/index/point-red.png");
}

.point-org {
  background-image: url("../assets/images/index/point-org.png");
}

.point-yellow {
  background-image: url("../assets/images/index/point-yellow.png");
}

.point-gray {
  background-image: url("../assets/images/index/point-gray.png");
}

.el-table ::v-deep th.el-table__cell.is-leaf,
.el-table ::v-deep td.el-table__cell {
  border: none;
}
::v-deep .el-table::before,
::v-deep .el-table__fixed::before,
::v-deep .el-table__fixed-right::before {
  content: "";
  display: none;
}
::v-deep .el-table--medium .el-table__cell {
  padding: 3px 0px;
}

.el-table ::v-deep .el-table__header-wrapper th,
.el-table ::v-deep .el-table__fixed-header-wrapper th {
  background: none;
}
.el-card__body {
  height: 100%;
}
.numberBox {
  ::v-deep .el-card__body {
    padding: 26px 20px;
  }
}

.flex ::v-deep .el-card__body {
  display: flex;
  justify-content: space-between;
  flex-direction: column;
  height: 100%;
}
</style>


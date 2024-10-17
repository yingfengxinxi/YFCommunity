<template>
  <div class="app-container home">
    <el-row>
      <!--  左边    -->
      <el-col>
        <!--  上边    -->
        <el-row style="font-size: 14px">
          <el-col style="width: 32%">
            <el-card
              class="box-card card-radius bg-img party-audit"
              shadow="always"
            >
              <el-row style="font-weight: bold">入党审核</el-row>
              <el-row style="font-size: 30px; text-align: center">
                <el-col :span="4" :offset="6">
                  <el-col style="">{{ this.form.memberAudit }}</el-col>
                  <el-col style="font-size: 14px">待审核</el-col>
                </el-col>
                <el-col :span="6" style="font-size: 40px"> | </el-col>
                <el-col :span="4">
                  <el-col style="">{{ this.form.memberRebut }}</el-col>
                  <el-col style="font-size: 14px">已驳回</el-col>
                </el-col>
              </el-row>
            </el-card>
          </el-col>
          <el-col style="width: 32%; margin-left: 2%">
            <el-card class="box-card card-radius bg-img rescue" shadow="always">
              <el-row style="font-weight: bold">党员活动</el-row>
              <el-row style="font-size: 30px; text-align: center">
                <el-col :span="4" :offset="6">
                  <el-col style="">{{ this.form.rescueNotStart }}</el-col>
                  <el-col style="font-size: 14px">未开始</el-col>
                </el-col>
                <el-col :span="6" style="font-size: 40px"> | </el-col>
                <el-col :span="4">
                  <el-col style="">{{ this.form.rescueStart }}</el-col>
                  <el-col style="font-size: 14px">进行中</el-col>
                </el-col>
              </el-row>
            </el-card>
          </el-col>
          <el-col style="width: 32%; margin-left: 2%">
            <el-card
              class="box-card card-radius bg-img bulletin"
              shadow="always"
            >
              <el-row style="font-weight: bold">警民互动</el-row>
              <el-row style="font-size: 30px; text-align: center">
                <el-col :span="4" :offset="6">
                  <el-col style="">{{ this.form.bulletinNum }}</el-col>
                  <el-col style="font-size: 14px">通报人数</el-col>
                </el-col>
                <el-col :span="6" style="font-size: 40px"> | </el-col>
                <el-col :span="4">
                  <el-col style="">{{ this.form.fileNum }}</el-col>
                  <el-col style="font-size: 14px">归档人数</el-col>
                </el-col>
              </el-row>
            </el-card>
          </el-col>
        </el-row>
        <!--  党费统计    -->
        <!-- <el-row style="margin-top: 1%;">
          <el-card class="box-card card-radius" shadow="always">
            <el-row style="margin-bottom: 2%;font-weight: bold;font-size: 14px;">
              <el-col :span="2" style="margin-top: 5px;"><span>党费</span><span class="text-blue">统计</span></el-col>
              <el-col :span="8">
                  <el-date-picker v-model="payMonth" :clearable="false" @change="getPartyFeeData"
                  type="month" size="mini" placeholder="请选择年月">
                </el-date-picker>
              </el-col>
            </el-row>
            <div ref="partyFeeRef" style="height: 300px;"></div>
          </el-card>
        </el-row> -->
        <el-row style="margin-top: 2%">
          <el-col style="width: calc(100% - 6% - 800px)">
            <el-card class="box-card card-radius" shadow="always">
              <el-row style="font-weight: bold">
                <span>快捷</span><span class="text-blue">菜单</span>
              </el-row>
              <div
                style="
                  margin-top: 4px;
                  line-height: 30px;
                  height: 230px;
                  display: flex;
                  justify-content: space-around;
                  flex-direction: column;
                "
              >
                <el-row>
                  <router-link to="/party/member/audit">
                    <el-col :span="8">
                      <el-row>
                        <el-col
                          :span="18"
                          :offset="2"
                          class="bg-img quick-audit"
                        ></el-col>
                      </el-row>
                      <el-row class="quick-text"
                        ><el-col :span="18" :offset="2"
                          >入党审核</el-col
                        ></el-row
                      >
                    </el-col>
                  </router-link>
                  <router-link to="/party/research">
                    <el-col :span="8">
                      <el-row>
                        <el-col
                          :span="14"
                          :offset="4"
                          class="bg-img quick-research"
                        ></el-col>
                      </el-row>
                      <el-row class="quick-text"
                        ><el-col :span="18" :offset="2"
                          >党建调研</el-col
                        ></el-row
                      >
                    </el-col>
                  </router-link>
                  <router-link to="/party/memberAct">
                    <el-col :span="8">
                      <el-row>
                        <el-col
                          :span="14"
                          :offset="4"
                          class="bg-img quick-activity"
                        ></el-col>
                      </el-row>
                      <el-row class="quick-text"
                        ><el-col :span="18" :offset="2"
                          >党员活动</el-col
                        ></el-row
                      >
                    </el-col>
                  </router-link>
                </el-row>
                <el-row>
                  <router-link to="/prevent">
                    <el-col :span="8">
                      <el-row>
                        <el-col
                          :span="14"
                          :offset="4"
                          class="bg-img quick-prevent"
                        ></el-col>
                      </el-row>
                      <el-row class="quick-text"
                        ><el-col :span="18" :offset="2"
                          >防控信息</el-col
                        ></el-row
                      >
                    </el-col>
                  </router-link>
                  <router-link to="/party/message">
                    <el-col :span="8">
                      <el-row>
                        <el-col
                          :span="14"
                          :offset="4"
                          class="bg-img quick-procedure"
                        ></el-col>
                      </el-row>
                      <el-row class="quick-text"
                        ><el-col :span="18" :offset="2"
                          >党建资讯</el-col
                        ></el-row
                      >
                    </el-col>
                  </router-link>
                  <router-link to="/bulletin">
                    <el-col :span="8">
                      <el-row>
                        <el-col
                          :span="14"
                          :offset="4"
                          class="bg-img quick-red-black"
                        ></el-col>
                      </el-row>
                      <el-row class="quick-text"
                        ><el-col :span="18" :offset="2"
                          >警民互动</el-col
                        ></el-row
                      >
                    </el-col>
                  </router-link>
                </el-row>
              </div>
            </el-card>
          </el-col>
          <!--  线索反馈次数    -->
          <el-col style="width: 400px; margin-left: 3%">
            <el-card class="box-card card-radius" shadow="always">
              <el-row style="margin-bottom: 2%; font-weight: bold">
                <span>线索反馈</span><span class="text-blue">次数</span>
              </el-row>
              <div ref="clueCountRef" style="height: 228px"></div>
            </el-card>
          </el-col>
          <!--  业主委员会成员统计    -->
          <el-col style="width: 400px; margin-left: 3%">
            <el-card class="box-card card-radius" shadow="always">
              <el-row style="margin-bottom: 2%; font-weight: bold">
                <span>业主委员会</span><span class="text-blue">成员统计</span>
              </el-row>
              <div ref="committeeRef" style="height: 230px"></div>
            </el-card>
          </el-col>
        </el-row>
        <!--  党员统计    -->
        <el-row style="margin-top: 2%">
          <el-card class="box-card card-radius" shadow="always">
            <el-row
              style="margin-bottom: 2%; font-weight: bold; font-size: 14px"
            >
              <el-col :span="2" style="margin-top: 5px"
                ><span>党员</span><span class="text-blue">统计</span></el-col
              >
            </el-row>
            <div ref="memberRef" style="height: 220px"></div>
          </el-card>
        </el-row>
      </el-col>
      <!--  右边    -->
      <el-col style="font-size: 14px; width: 21%; margin-left: 1%">
        <!--  快捷菜单    -->
      </el-col>
    </el-row>
  </div>
</template>

<script>
import echarts from "echarts";
import { getParams } from "@/api/govern/home/homePage";
import { equals, isEmpty } from "@/utils/common";
export default {
  name: "Index",
  data() {
    return {
      // 入党审核数据
      memberAudit: 0,
      memberRebut: 0,
      // 社区救助
      rescueNotStart: 0,
      rescueStart: 0,
      // 警民互动
      bulletinNum: 0,
      fileNum: 0,
      // 党费统计echart
      partyFeeChart: null,
      // 查询条件
      payMonth: new Date(),
      // 党费数据
      partyFeeList: [],
      // 党员统计echart
      memberChart: null,
      // 党员数据
      memberList: [],
      form: {},
      // 线索反馈次数统计echart
      clueChart: null,
      // 线索反馈次数数据
      clueList: [],
      // 业主委员会echart
      committeeChart: null,
      // 业主委员会人员数量
      committeeList: [],
    };
  },
  created() {
    this.getList();
  },
  mounted() {
    this.$nextTick(() => {
      this.initChart();
      //this.getPartyFeeData();
      // this.getMemberList();
      //this.getClueList();
      //this.getCommitteeList();
    });
  },
  methods: {
    getList() {
      getParams(this.momentFormat(this.payMonth)).then((response) => {
        this.form = response.data;
        //党员统计
        this.memberList = this.form.memberList;
        this.setMemberOption();
        //线索反馈次数
        this.clueList = this.form.clueList;
        this.setClueOption();

        //业主委员会人数
        this.committeeList = this.form.committeeList;
        this.setCommitteeOption();

        //党费统计
        this.partyFeeList = this.form.partyFeeList;
        this.setPartyFeeOption();
      });
    },
    /**时间转换*/
    momentFormat(time) {
      if (isEmpty(time)) {
        return "";
      }
      return this.$moment(time).format("YYYY-MM-DD HH:mm:ss");
    },

    // 查询上部份数据
    getTopNum() {
      this.rescueData = {
        unBegin: Math.round(Math.random() * 1001),
        underway: Math.round(Math.random() * 1001),
      };
      this.bulletinData = {
        bulletin: Math.round(Math.random() * 1001),
        archive: Math.round(Math.random() * 1001),
      };
    },
    // 初始化echarts
    initChart() {
      let that = this;
      // that.partyFeeChart = echarts.init(that.$refs.partyFeeRef, "macarons");
      that.memberChart = echarts.init(that.$refs.memberRef, "macarons");
      that.clueChart = echarts.init(that.$refs.clueCountRef, "macarons");
      that.committeeChart = echarts.init(that.$refs.committeeRef, "foundRef");
      //窗口监听事件
      window.addEventListener("resize", function () {
        that.partyFeeChart.resize();
        that.memberChart.resize();
        that.clueChart.resize();
        that.committeeChart.resize();
      });
    },
    // 查询党费数据
    getPartyFeeData() {
      this.getList();
    },
    // 设置党费统计图表
    setPartyFeeOption() {
      let communityData = [];
      let normalData = [];
      let aheadData = [];
      let backData = [];
      let offlineData = [];
      for (let i in this.partyFeeList) {
        communityData.push(this.partyFeeList[i].communityName);
        normalData.push(this.partyFeeList[i].normalAmount);
        aheadData.push(this.partyFeeList[i].aheadAmount);
        backData.push(this.partyFeeList[i].backAmount);
        offlineData.push(this.partyFeeList[i].offlineAmount);
      }
      this.partyFeeChart.setOption(
        {
          color: ["#2F9FFF", "#BBB2FF", "#8AE6A7", "#FF926C"],
          tooltip: {
            trigger: "axis",
            axisPointer: {
              type: "shadow",
            },
          },
          legend: {
            //bottom: 0,
            data: ["正常缴费", "预缴", "补缴", "线下缴费"],
          },
          grid: {
            left: "2%",
            right: "2%",
            bottom: "3%",
            containLabel: true,
          },
          xAxis: {
            type: "category",
            axisLine: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            data: communityData,
          },
          yAxis: {
            type: "value",
            name: "单位：元",
            axisLabel: {
              show: true,
            },
            axisLine: {
              show: false,
            },
            axisTick: {
              show: false,
            },
            splitLine: {
              show: false,
            },
          },
          series: [
            {
              type: "bar",
              stack: "one",
              align: "inside",
              name: "正常缴费",
              data: normalData,
              barWidth: "25%",
              label: {
                show: false,
              },
            },
            {
              type: "bar",
              stack: "one",
              align: "inside",
              name: "预缴",
              data: aheadData,
              barWidth: "25%",
              label: {
                show: false,
              },
            },
            {
              type: "bar",
              stack: "one",
              align: "inside",
              name: "补缴",
              data: backData,
              barWidth: "25%",
              label: {
                show: false,
              },
            },
            {
              type: "bar",
              stack: "one",
              align: "inside",
              name: "线下缴费",
              data: offlineData,
              barWidth: "25%",
              label: {
                show: false,
              },
              /*itemStyle: {
              barBorderRadius: [15, 15, 0, 0],
            }*/
            },
          ],
        },
        true
      );
    },

    // 查询党员数据
    /*    getMemberList(){
      this.memberList = [];
      for (let i = 0; i < 10; i++) {
        this.memberList.push({
          communityName:'小区'+i,
          memberNum: Math.round(Math.random() * 501)
        })
      }
      this.setMemberOption();
    },*/
    // 设置党员图表
    setMemberOption() {
      let communityData = [];
      let memberData = [];
      for (let i in this.memberList) {
        communityData.push(this.memberList[i].communityName);
        memberData.push(this.memberList[i].memberNum);
      }

      this.memberChart.setOption({
        title: {
          top: 20,
          text: "单位：人",
          textStyle: {
            color: "#808080",
            fontSize: 12,
          },
        },
        color: ["#004558"],
        tooltip: {
          trigger: "axis",
          axisPointer: {
            // 坐标轴指示器，坐标轴触发有效
            type: "shadow", // 默认为直线，可选为：'line' | 'shadow'
          },
          formatter: "{b}：{c}人",
        },
        grid: {
          left: "5px",
          right: "5px",
          bottom: 0,
          containLabel: true,
        },
        xAxis: [
          {
            type: "category",
            data: communityData,
            axisTick: {
              alignWithLabel: true,
              show: false,
            },
            axisLine: {
              show: false,
            },
            axisLabel: {
              interval: 0, // 解决x轴名称过长问题
              textStyle: {
                color: "#808080",
              },
            },
          },
        ],
        yAxis: {
          minInterval: 1,
          axisLine: {
            show: false,
          },
          axisTick: {
            show: false,
          },
          axisLabel: {
            textStyle: {
              color: "#808080",
            },
          },
          splitLine: {
            //保留网格线
            show: false,
          },
        },
        series: [
          {
            name: "接口调用次数",
            type: "bar",
            barWidth: "25%", //去掉这个则会显示柱状阴影
            itemStyle: {
              normal: {
                // 圆角
                barBorderRadius: [50, 50, 0, 0],
                color: "#2F9FFF",
              },
            },
            data: memberData,
          },
        ],
      });
    },

    // 查询线索反馈数据
    /*    getClueList(){
      for (let i = 0; i < 5; i++) {
        this.clueList.push({
          communityName:'小区'+i,
          clueNum: Math.round(Math.random() * 501)
        })
      }

      this.setClueOption();
    },*/
    // 设置线索反馈图表
    setClueOption() {
      let totalNum = 0;
      let clueData = [];
      this.clueList.forEach((clue) => {
        totalNum += clue.clueNum;
        clueData.push({ name: clue.communityName, value: clue.clueNum });
      });

      this.clueChart.setOption({
        color: ["#2F9FFF", "#BBB2FF", "#8AE7A5", "#63C8FE", "#FFBF67"],
        title: {
          zlevel: 0,
          text: [
            "{value|" + JSON.stringify(totalNum) + "}",
            "{name|反馈次数}",
          ].join("\n"),
          top: "center",
          left: "48%",
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
                lineHeight: 18,
              },
            },
          },
        },
        tooltip: {
          trigger: "item",
        },
        series: [
          {
            name: "标题",
            type: "pie",
            center: ["50%", "50%"],
            radius: ["50%", "75%"],
            clockwise: false, //饼图的扇区是否是顺时针排布
            avoidLabelOverlap: false,
            label: {
              normal: {
                show: true,
                position: "outter",
                color: "#5C5C5C",
                formatter: function (parms) {
                  return parms.data.name + "\n" + parms.data.value + "次";
                },
              },
            },
            labelLine: {
              show: false,
              normal: {
                length: 5,
                length2: 3,
                smooth: false,
              },
            },
            data: clueData,
          },
        ],
      });
    },

    // 查询业主委员会数据
    /*getCommitteeList(){
      for (let i = 0; i < 5; i++) {
        this.committeeList.push({
          communityName:'小区'+i,
          committeeNum: Math.round(Math.random() * 501)
        })
      }

      this.setCommitteeOption();
    },*/
    // 设置业主委员会数据图表
    setCommitteeOption() {
      let totalNum = 0;
      let committeeData = [];
      this.committeeList.forEach((committee) => {
        totalNum += committee.committeeNum;
        committeeData.push({
          name: committee.communityName,
          value: committee.committeeNum,
        });
      });

      this.committeeChart.setOption({
        color: ["#2F9FFF", "#BBB2FF", "#8AE7A5", "#63C8FE", "#FFBF67"],
        title: {
          zlevel: 0,
          text: [
            "{value|" + JSON.stringify(totalNum) + "}",
            "{name|总人数}",
          ].join("\n"),
          top: "40%",
          left: "48%",
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
                lineHeight: 18,
              },
            },
          },
        },
        tooltip: {
          trigger: "item",
        },
        series: [
          {
            name: "标题",
            type: "pie",
            center: ["50%", "52%"],
            radius: ["50%", "75%"],
            clockwise: false, //饼图的扇区是否是顺时针排布
            avoidLabelOverlap: false,
            label: {
              normal: {
                show: true,
                position: "outter",
                color: "#5C5C5C",
                formatter: function (parms) {
                  return (
                    (parms.data.name || "") +
                    "\n" +
                    (parms.data.value || 0) +
                    "人"
                  );
                },
              },
            },
            labelLine: {
              show: false,
              normal: {
                length: 5,
                length2: 3,
                smooth: false,
              },
            },
            data: committeeData,
          },
        ],
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

.party-audit {
  background-image: url("../assets/images/index/party-audit.png");
  color: #ffffff;
  height: 120px;
}

.rescue {
  background-image: url("../assets/images/index/rescue.png");
  color: #ffffff;
  height: 120px;
}
.bulletin {
  background-image: url("../assets/images/index/bulletin.png");
  color: #ffffff;
  height: 120px;
}

.quick-text {
  text-align: center;
  font-size: 12px;
  margin-top: 4px;
}

.quick-audit {
  background-image: url("../assets/images/index/quick-audit.png");
  height: 45px;
  background-size: 45px;
  background-position: center;
}

.quick-research {
  background-image: url("../assets/images/index/quick-research.png");
  height: 45px;
  background-size: 45px;
  background-position: center;
}

.quick-activity {
  background-image: url("../assets/images/index/quick-activity.png");
  height: 45px;
  background-size: 45px;
  background-position: center;
}

.quick-prevent {
  background-image: url("../assets/images/index/quick-prevent.png");
  height: 45px;
  background-size: 45px;
  background-position: center;
}

.quick-vote {
  background-image: url("../assets/images/index/quick-vote.png");
  height: 45px;
  background-size: 45px;
  background-position: center;
}

.quick-questionnaire {
  background-image: url("../assets/images/index/quick-questionnaire.png");
  height: 45px;
  background-size: 45px;
  background-position: center;
}

.quick-procedure {
  background-image: url("../assets/images/index/quick-procedure.png");
  height: 45px;
  background-size: 45px;
  background-position: center;
}

.quick-red-black {
  background-image: url("../assets/images/index/quick-red-black.png");
  height: 45px;
  background-size: 45px;
  background-position: center;
}
</style>


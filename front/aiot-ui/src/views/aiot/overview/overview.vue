<template>
  <div class="app-container">
    <el-row>
      <!--  左侧    -->
      <el-col :span="16">
        <!--  月度停车场收费视图    -->
        <el-row>
          <el-card class="box-card round-corner">
            <el-row>
              <label class="el-form-item__label">
                <svg-icon icon-class="vertical"/>
                月度停车场收费视图</label>
            </el-row>
            <el-row>
              <el-col :span="6">
                <el-row style="background-color: #D9FFFF;line-height: 30px;">
                  <el-col :offset="1" style="font-weight: bold;">
                    <span style="color: #448FF1;font-size: 14px;">第一季度收款金额（元）：</span>
                    <span style="color: #0DCB0F;font-size: 18px;">{{ thisFirstAmount }}</span>
                  </el-col>
                  <el-col :offset="1">
                    <span style="color: #A5C2C2;font-size: 12px;">较上年比：</span>
                    <svg-icon :icon-class="thisFirstAmount>=lastFirstAmount ? 'green-up' : 'orange-down'"/>
                    <span style="font-size: 10px;">{{ firstRate }}</span>
                  </el-col>
                </el-row>
                <h4></h4>
                <el-row style="background-color: #D9FFFF;line-height: 30px;">
                  <el-col :offset="1" style="font-weight: bold;">
                    <span style="color: #448FF1;font-size: 14px;">第二季度收款金额（元）：</span>
                    <span style="color: #0DCB0F;font-size: 18px;">{{ thisSecAmount }}</span>
                  </el-col>
                  <el-col :offset="1">
                    <span style="color: #A5C2C2;font-size: 12px;">较上年比：</span>
                    <svg-icon :icon-class="thisSecAmount>=lastSecAmount ? 'green-up' : 'orange-down'"/>
                    <span style="font-size: 10px;">{{ secRate }}</span>
                  </el-col>
                </el-row>
                <h4></h4>
                <el-row style="background-color: #D9FFFF;line-height: 30px;">
                  <el-col :offset="1" style="font-weight: bold;">
                    <span style="color: #448FF1;font-size: 14px;">第三季度收款金额（元）：</span>
                    <span style="color: #0DCB0F;font-size: 18px;">{{ thisThirdAmount }}</span>
                  </el-col>
                  <el-col :offset="1">
                    <span style="color: #A5C2C2;font-size: 12px;">较上年比：</span>
                    <svg-icon :icon-class="thisThirdAmount>=lastThirdAmount ? 'green-up' : 'orange-down'"/>
                    <span style="font-size: 10px;">{{ thirdRate }}</span>
                  </el-col>
                </el-row>
                <h4></h4>
                <el-row style="background-color: #D9FFFF;line-height: 30px;">
                  <el-col :offset="1" style="font-weight: bold;">
                    <span style="color: #448FF1;font-size: 14px;">第四季度收款金额（元）：</span>
                    <span style="color: #0DCB0F;font-size: 18px;">{{ thisFourthAmount }}</span>
                  </el-col>
                  <el-col :offset="1">
                    <span style="color: #A5C2C2;font-size: 12px;">较上年比：</span>
                    <svg-icon :icon-class="thisFourthAmount>=lastFourthAmount ? 'green-up' : 'orange-down'"/>
                    <span style="font-size: 10px;">{{ fourthRate }}</span>
                  </el-col>
                </el-row>
              </el-col>
              <el-col :span="18">
                <div ref="parkingFeeRef" style="height: 300px;width: 98%;"></div>
              </el-col>
            </el-row>
          </el-card>
        </el-row>
        <!--  日车辆出入统计    -->
        <h4></h4>
        <el-row>
          <el-card class="box-card round-corner">
            <el-row>
              <el-col :span="12">
                <label class="el-form-item__label">
                  <svg-icon icon-class="vertical"/>
                  日车辆出 / 入统计</label>
              </el-col>
              <el-col :span="12" style="float: right">
                <el-form :model="queryParams" ref="queryForm">
                  <el-row>
                    <el-col :span="6" :offset="10">
                      <el-form-item prop="queryData">
                        <el-date-picker
                          v-model="queryParams.inoutTime" type="month"
                          :editable="false" size="small" style="width: 100%"
                          value-format="yyyy-MM"
                          @change="selectParkMonth"
                          :default-value="defaultDate" :clearable="false"
                          placeholder="请选择">
                        </el-date-picker>
                      </el-form-item>
                    </el-col>
                    <el-col :span="6" :offset="2">
                      <el-form-item prop="monthType">
                        <el-select v-model="queryParams.monthType" size="small"
                                   style="width: 100%" @change="selectParkMonth">
                          <el-option value="0" label="上半月"/>
                          <el-option value="1" label="下半月"/>
                        </el-select>
                      </el-form-item>
                    </el-col>
                  </el-row>
                </el-form>
              </el-col>
            </el-row>
            <el-row>
              <div ref="parkingMonthRef" style="height: 300px;width: 98%;"></div>
            </el-row>
          </el-card>
        </el-row>
      </el-col>
      <!--  右侧    -->
      <el-col :span="7" :offset="1">
        <el-card class="box-card round-corner">
          <el-row>
            <label class="el-form-item__label">
              <svg-icon icon-class="vertical"/>
              车位占用统计</label>
          </el-row>
          <el-row>
            <div ref="parkingUseRef" style="height: 300px;width: 98%;"></div>
          </el-row>
          <el-row>
            <el-col :offset="1" :span="22" style="font-weight: bold;line-height: 40px;">
              <el-card class="box-card" style="border-radius: 10px;background-color: #EFF6FF" shadow="never">
                <el-row>
                  <el-col :span="12">
                    <span style="color: #2D80EF;font-size: 14px;">实际车位（位）：</span>
                    <span style="color: #2DAE66;font-size: 18px;">{{ carportTotal }}</span>
                  </el-col>
                  <el-col :span="12">
                    <span style="color: #2D80EF;font-size: 14px;">今日入园车辆（辆）：</span>
                    <span style="color: #2DAE66;font-size: 18px;">{{ vehicleLogToday }}</span>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="12">
                    <span style="color: #2D80EF;font-size: 14px;">注册车辆（辆）：</span>
                    <span style="color: #FB4C18;font-size: 18px;">{{ vehicleTotal }}</span>
                  </el-col>
                  <el-col :span="12">
                    <span style="color: #2D80EF;font-size: 14px;">今日外来车辆（辆）：</span>
                    <span style="color: #2DAE66;font-size: 18px;">{{ tempLogToday }}</span>
                  </el-col>
                </el-row>
              </el-card>
            </el-col>
          </el-row>
          <h4></h4>
          <el-row>
            <label class="el-form-item__label">
              <svg-icon icon-class="vertical"/>
              今日注册车辆 / 外来车辆统计</label>
          </el-row>
          <el-row>
            <el-col :span="16">
              <div ref="vehicleLogTodayRef" style="height: 250px;width: 98%;"></div>
            </el-col>
            <el-col :span="8">
              <el-row style="background-color: #D9FFFF;line-height: 80px;margin-top: 30%">
                <el-col :offset="1" style="font-weight: bold;">
                  <span style="color: #448FF1;font-size: 14px;">注册车辆（辆）：</span>
                  <span style="color: #0DCB0F;font-size: 18px;">{{ vehicleToday }}</span>
                </el-col>
              </el-row>
              <h4></h4>
              <el-row style="background-color: #D9FFFF;line-height: 80px;">
                <el-col :offset="1" style="font-weight: bold;">
                  <span style="color: #448FF1;font-size: 14px;">外来车辆（辆）：</span>
                  <span style="color: #FB4C18;font-size: 18px;">{{ tempLogToday }}</span>
                </el-col>
              </el-row>
            </el-col>
          </el-row>
        </el-card>
      </el-col>
    </el-row>
  </div>
</template>
<script>
import echarts from 'echarts'
import {getMonthPark, getVehicleLog, getCarportUse} from "@/api/aiot/overview";
import {equals} from "@/utils/common";

export default {
  name: 'ParkView',
  data() {
    return {
      //月度停车场收费视图chart
      parkingFeeChart: null,
      //今年第一季度金额
      thisFirstAmount: 0,
      firstRate: 0,
      //今年第二季度金额
      thisSecAmount: 0,
      secRate: 0,
      //今年第三季度金额
      thisThirdAmount: 0,
      thirdRate: 0,
      //今年第四季度金额
      thisFourthAmount: 0,
      fourthRate: 0,
      //去年第一季度金额
      lastFirstAmount: 0,
      //去年第二季度金额
      lastSecAmount: 0,
      //去年第三季度金额
      lastThirdAmount: 0,
      //去年第四季度金额
      lastFourthAmount: 0,
      //今年12个月的金额
      thisTotalAmount: [],
      //日车辆出入统计chart
      parkingMonthChart: null,
      //查询条件
      queryParams: {
        inoutTime:undefined,
        monthType: '0'
      },
      defaultDate: new Date(),
      //出场数据
      outParkArray: [],
      //入场数据
      inParkArray: [],
      //天数(上下半月)
      dayArray: [],
      //车位占用chart
      parkingUseChart: null,
      //当前物业总车位数
      carportTotal: 0,
      //今日入园车辆数
      vehicleLogToday: 0,
      //注册车辆数
      vehicleTotal: 0,
      //今日外来车辆
      tempLogToday: 0,
      //注册车辆/外来车辆统计
      vehicleLogTodayChart: null,
      //今日注册车辆数
      vehicleToday: 0,
    }
  },
  mounted() {
    let now = new Date();
    let year = now.getFullYear();
    let month = now.getMonth() + 1;
    if (month<10) {
      this.queryParams.inoutTime = year+"-0"+month;
    }else{
      this.queryParams.inoutTime = year+"-"+month;
    }
    this.initChart();
    this.$nextTick(() =>{
      this.initPageData();
    })
  },
  methods: {
    //初始化echarts数据
    initChart() {
      let that = this;
      that.parkingFeeChart = echarts.init(that.$refs.parkingFeeRef);
      that.parkingMonthChart = echarts.init(that.$refs.parkingMonthRef);
      that.parkingUseChart = echarts.init(that.$refs.parkingUseRef);
      that.vehicleLogTodayChart = echarts.init(that.$refs.vehicleLogTodayRef);
      //窗口监听事件
      window.addEventListener('resize', function () {
        that.parkingFeeChart.resize();
        that.parkingMonthChart.resize();
        that.parkingUseChart.resize();
        that.vehicleLogTodayChart.resize();
      })
    },
    //初始化数据
    initPageData() {
      //月度停车场收费
      this.selectParkFee();
      //日车辆出入统计
      this.selectParkMonth();
      //车位占用统计--注册车辆/外来车辆统计
      this.selectVehicleUsed();
    },
    //获取百分比
    percentage(num, total) {
      if (equals(num,0)) {
        return 0;
      }
      if(equals(total,0)){
        return "100%"
      }
      // 小数点后两位百分比
      return(Math.round(num / total * 100)) + "%";
    },
    //月度停车场收费数据
    selectParkFee() {
      getMonthPark().then(response => {
        let thisData = response.data.thisData;
        //今年第一季度
        this.thisFirstAmount = thisData.janData + thisData.febData + thisData.marData;
        //今年第二季度
        this.thisSecAmount = thisData.aprData + thisData.mayData + thisData.junData;
        //今年第三季度
        this.thisThirdAmount = thisData.julData + thisData.augData + thisData.sepData;
        //今年第四季度
        this.thisFourthAmount = thisData.octData + thisData.novData + thisData.decData;

        let lastData = response.data.lastData;
        //去年第一季度
        this.lastFirstAmount = lastData.janData + lastData.febData + lastData.marData;
        //去年第二季度
        this.lastSecAmount = lastData.aprData + lastData.mayData + lastData.junData;
        //去年第三季度
        this.lastThirdAmount = lastData.julData + lastData.augData + lastData.sepData;
        //去年第四季度
        this.lastFourthAmount = lastData.octData + lastData.novData + lastData.decData;
        //今年跟去年对比
        this.firstRate = this.percentage(Math.abs(this.thisFirstAmount-this.lastFirstAmount),this.lastFirstAmount);
        this.secRate = this.percentage(Math.abs(this.thisSecAmount-this.lastSecAmount),this.lastSecAmount);
        this.thirdRate = this.percentage(Math.abs(this.thisThirdAmount-this.lastThirdAmount),this.lastThirdAmount);
        this.fourthRate = this.percentage(Math.abs(this.thisFourthAmount-this.lastFourthAmount),this.lastFourthAmount);

        this.thisTotalAmount=[
          thisData.janData,thisData.febData,thisData.marData,thisData.aprData,thisData.mayData,thisData.junData,
          thisData.julData,thisData.augData,thisData.sepData,thisData.octData,thisData.novData,thisData.decData
        ];

        //渲染chart
        this.setParkFeeOption();
      })
    },
    //渲染月度停车场收费数据chart
    setParkFeeOption() {
      let xData = ["1月", "2月", "3月", "4月", "5月", "6月", "7月", "8月", "9月", "10月", "11月", "12月"];
      this.parkingFeeChart.setOption({
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            crossStyle: {
              color: '#999'
            },
            lineStyle: {
              type: 'dashed'
            }
          }
        },
        grid: {
          top: '15%',
          right: 0,
          left: '15%',
          bottom: '12%'
        },
        /*legend: {
          top:'15%',
          itemGap: 50,
          data: ['金额'],
          textStyle: {
            color: '#363636'
          },
        },*/
        xAxis: [{
          type: 'category',
          data: xData,
          axisLine: {
            lineStyle: {
              color: 'rgba(255,255,255,0.12)'
            }
          },
          axisLabel: {
            margin: 10,
            color: '#363636',
            textStyle: {
              fontSize: 14
            },
          },
        }],
        yAxis: [{
          name: "单位/元",
          axisLabel: {
            formatter: '{value}',
            color: '#363636',
          },
          axisLine: {
            show: true
          },
          splitLine: {
            lineStyle: {
              color: 'rgba(255,255,255,0.12)'
            }
          }
        }],
        series: [{
          type: 'bar',
          data: this.thisTotalAmount,
          barWidth: '16px',
          itemStyle: {
            normal: {
              color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                {offset: 0, color: '#3EA1FF'},
                {offset: 0.25, color: '#6DB8FF'},
                {offset: 0.5, color: '#9ACEFF'},
                {offset: 0.75, color: '#C4E2FF'},
                {offset: 1, color: '#F3F9FF'}
              ], false),
              barBorderRadius: [25, 25, 0, 0],
              shadowColor: '#ffffff',
              shadowBlur: 4,
            }
          },
          label: {
            normal: {
              show: false
            }
          }
        }]
      });
    },

    //日车辆出入统计
    selectParkMonth() {
      getVehicleLog(this.queryParams).then(response => {
        this.dayArray = response.data.dayArray
        this.inParkArray = response.data.inParkArray
        this.outParkArray = response.data.outParkArray
        let max = 0;
        for (let i in this.dayArray) {
          max = this.inParkArray[i] > max ? this.inParkArray[i] : max;
          max = this.outParkArray[i] > max ? this.outParkArray[i] : max;
        }
        //渲染chart
        this.setParkMonthOption(max);
      })
    },
    //日车辆出入统计chart
    setParkMonthOption(max) {
      var legendData = ['出场车辆', '入场车辆'];
      var color = ['#94d96c', '#0bbcb7', '#1a9bfc', '#7049f0'];

      var xAxisData = [];
      var data1 = [],
        data2 = [];
      let bottomData = [], zero = [];
      let outShadow1 = [], outShadow2 = [];
      let inShadow1 = [], inShadow2 = [];
      for (var i in this.dayArray) {
        xAxisData.push(this.dayArray[i]);
        outShadow1.push(max+10);
        outShadow2.push(max+20);
        inShadow1.push(max+10);
        inShadow2.push(max+20);
        data1.push(this.outParkArray[i]);
        data2.push(this.inParkArray[i]);
        bottomData.push(1.2);
        zero.push(0);
      }
      let shadowData1 = [], shadowData2 = [];
      let symbolOffset = [], barGap1 = '0%', barGap2 = '0%';
      let dataArr = [];
      let series = [];
      for (var j = 1; j < 3; j++) {
        if (equals(j,1)) {
          symbolOffset = [-11, 0];
          barGap1 = '0%';
          barGap2 = '0%';
          shadowData1 = outShadow1;
          shadowData2 = outShadow2;
          dataArr = data1
        } else if (equals(j,2)) {
          symbolOffset = [11, 0];
          barGap1 = '50%';
          barGap2 = '20%';
          shadowData1 = inShadow1;
          shadowData2 = inShadow2;
          dataArr = data2
        }
        series.push(
          {
            name: legendData[j - 1],
            type: 'bar',
            stack: j,
            xAxisIndex: 0,
            data: dataArr,
            barWidth: 6,
            barGap: '260%',
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  {offset: 0, color: color[j * 2 - 2]},
                  {offset: 1, color: color[j * 2 - 1]}
                ]),
              }
            },
            z: 2
          },
          {
            name: legendData[j - 1],
            type: 'scatter',
            stack: j,
            symbolOffset: symbolOffset, //相对于原本位置的偏移量
            data: zero,
            label: {
              normal: {
                show: false,
              }
            },
            xAxisIndex: 0,
            symbolSize: 12,
            itemStyle: {
              normal: {
                borderColor: '#fff',
                borderWidth: 1,
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  {offset: 0, color: color[j * 2 - 2]},
                  {offset: 1, color: color[j * 2 - 1]}
                ]),
                opacity: 1,
              }
            },
            z: 2
          },
          {
            name: legendData[j - 1],
            type: 'bar',
            xAxisIndex: 1,
            barGap: barGap1,
            data: shadowData1,
            barWidth: 14,
            itemStyle: {
              normal: {
                color: '#FFFFFF',
                barBorderRadius: 5,
              }
            },
            z: 1
          },
          {
            name: legendData[j - 1],
            type: 'bar',
            xAxisIndex: 2,
            data: shadowData2,
            barWidth: 18,
            barGap: barGap2,
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  {offset: 0, color: color[j * 2 - 2]},
                  {offset: 1, color: color[j * 2 - 1]}
                ]),
                barBorderRadius: 5,
              }
            },
            z: 0
          },
          //下圆片
          {
            name: legendData[j - 1],
            type: 'scatter',
            hoverAnimation: false,
            data: bottomData,
            xAxisIndex: 2,
            symbolOffset: symbolOffset, //相对于原本位置的偏移量
            symbolSize: 18,
            itemStyle: {
              normal: {
                color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                  {offset: 0, color: color[j * 2 - 2]},
                  {offset: 1, color: color[j * 2 - 1]}
                ]),
                opacity: 1,
              }
            },
            z: 2
          }
        )
      }
      var option = {
        grid: {
          left: 0,
          right: 0,
          bottom: 5,
          top: '10%',
          containLabel: true
        },
        tooltip: {
          show: "true",
          trigger: 'axis',
          axisPointer: { // 坐标轴指示器，坐标轴触发有效
            type: 'shadow', // 默认为直线，可选为：'line' | 'shadow'
            shadowStyle: {
              color: 'rgba(112,112,112,0)',
            },
          },
          formatter: function (params) {
            return params[0].name + ' ：<br />'
              + params[0].seriesName + ' ：' + params[0].data + '次<br />'
              + params[2].seriesName + ' ：' + params[2].data + '次';
          },
          backgroundColor: 'rgba(0,0,0,0.7)', // 背景
          padding: [8, 10], //内边距
          extraCssText: 'box-shadow: 0 0 3px rgba(255, 255, 255, 0.4);', //添加阴影
        },
        legend: {
          top: 0,
          icon: 'circle',
          textStyle: {
            color: '#363636',
          },
          data: legendData,
        },
        xAxis: [{
          type: 'category',
          axisTick: {
            show: false
          },
          axisLine: {
            show: true,
            lineStyle: {
              color: '#B3DBFD',
            }
          },
          axisLabel: {
            inside: false,
            textStyle: {
              color: '#363636',
              fontWeight: 'normal',
              fontSize: '14',
            },
          },
          data: xAxisData
        }, {
          type: 'category',
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          axisLabel: {
            show: false
          },
          splitArea: {
            show: false
          },
          splitLine: {
            show: false
          },
          data: xAxisData
        }, {
          type: 'category',
          axisLine: {
            show: false
          },
          axisTick: {
            show: false
          },
          axisLabel: {
            show: false
          },
          splitArea: {
            show: false
          },
          splitLine: {
            show: false
          },
          data: xAxisData
        }],
        yAxis: {
          name: '单位/次',
          type: 'value',
          axisTick: {
            show: false
          },
          axisLine: {
            show: false
          },
          splitLine: {
            show: true,
            lineStyle: {
              color: '#D9EEFF',
            }
          },
          axisLabel: {
            textStyle: {
              color: '#363636',
              fontWeight: 'normal',
              fontSize: '12',
            },
          },
        },
        series: series
      };
      this.parkingMonthChart.setOption(option)
    },

    //车位占用统计
    selectVehicleUsed() {
      getCarportUse().then(response => {
        this.carportTotal = response.data.carportTotal;
        this.vehicleTotal = response.data.vehicleTotal;
        this.vehicleToday = response.data.vehicleToday;
        this.vehicleLogToday = response.data.vehicleLogToday;
        this.tempLogToday = response.data.tempLogToday;
        //渲染chart
        this.setParkUsedOption();
        this.setvehicleLogTodayOption();
      })
    },
    //车位占用统计chart
    setParkUsedOption() {
      // 数据进度值
      let dataArr = ((this.vehicleLogToday / this.carportTotal) * 100).toFixed(0);
      dataArr = dataArr >= 100 ? 100 : dataArr;
      this.parkingUseChart.setOption({
        title: {
          show: true,
          text: '今日车位占用情况',
          x: '49%',
          y: '58%',
          z: 8,
          textAlign: 'center',
          textStyle: {
            color: '#f1f7fe',
            fontSize: 16,
            fontWeight: 'normal'
          },

        },
        series: [{
          name: "内部（环形）进度条",
          type: "gauge",
          center: ['50%', '55%'],
          radius: '80%',
          splitNumber: 10,
          axisLine: {
            lineStyle: {
              color: [
                [dataArr / 100, '#458EFD'],
                [1, "#FFFFFF"]
              ],
              width: 14
            }
          },
          axisLabel: {
            show: false,
          },
          axisTick: {
            show: false,

          },
          splitLine: {
            show: false,
          },
          pointer: {
            show: false,
          },
        },
          {
            name: '外部刻度',
            type: 'gauge',
            center: ['50%', '55%'],
            radius: '100%',
            min: 0, //最小刻度
            max: 100, //最大刻度
            splitNumber: 10, //刻度数量
            startAngle: 225,
            endAngle: -45,
            axisLine: {
              show: true,
              // 仪表盘刻度线
              lineStyle: {
                width: 2,
                color: [
                  [1, '#FFFFFF']
                ]
              }
            },
            //仪表盘文字
            axisLabel: {
              show: true,
              color: '#868FDF',
              distance: 25,
              formatter: function (v) {
                switch (v + '') {
                  case '0':
                    return '0';
                  case '10':
                    return '10';
                  case '20':
                    return '20';
                  case '30':
                    return '30';
                  case '40':
                    return '40';
                  case '50':
                    return '50';
                  case '60':
                    return '60';
                  case '70':
                    return '70';
                  case '80':
                    return '80';
                  case '90':
                    return '90';
                  case '100':
                    return '100';
                }
              }
            },
            //刻度标签。
            axisTick: {
              show: true,
              splitNumber: 7,
              lineStyle: {
                color: '#3082FE', //用颜色渐变函数不起作用
                width: 2,
              },
              length: -8
            },
            //刻度样式
            splitLine: {
              show: true,
              length: -20,
              lineStyle: {
                color: '#458EFD', //用颜色渐变函数不起作用
              }
            },
            //分隔线样式
            detail: {
              show: false
            },
            pointer: {
              show: false
            }
          },
          /*内部*/
          {
            type: 'pie',
            radius: '50%',
            center: ['50%', '55%'],
            z: 8,
            hoverAnimation: false,
            data: [{
              //name: '检查进度',
              value: dataArr,
              itemStyle: {
                normal: {
                  color: new echarts.graphic.LinearGradient(0, 0, 0, 1, [
                    {offset: 0, color: '#3398ff'},
                    {offset: 1, color: '#7db0fd'}
                  ])
                }

              },
              label: {
                normal: {
                  formatter: function (params) {
                    return params.value + '%';
                  },
                  color: '#FFFFFF',
                  fontSize: 36,
                  fontWeight: "bold",
                  position: 'center',
                  show: true
                }
              },
              labelLine: {
                show: false
              }
            }],
          },
          /*外一层*/
          {
            type: "pie",
            radius: "58%",
            startAngle: 220,
            endAngle: -40,
            hoverAnimation: false,
            center: ["50%", "55%"],
            avoidLabelOverlap: false,
            label: {
              show: false
            },
            labelLine: {
              show: false
            },
            data: [{
              value: 1,
              itemStyle: {
                normal: {
                  color: '#8DC4FD'
                }
              }
            }],
          },
          //外二层圈
          {
            type: "pie",
            radius: "65%",
            center: ["50%", "55%"],
            avoidLabelOverlap: false,
            z: 0,
            hoverAnimation: false,
            label: {
              show: false
            },
            labelLine: {
              show: false
            },
            data: [{
              value: 1,
              itemStyle: {
                normal: {
                  color: '#e3edf8'
                }
              }
            }]
          }
        ]
      });
    },
    //注册车辆/外来车辆统计chart
    setvehicleLogTodayOption() {
      let img = 'data:image/png;base64,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';
      let vehicle = [{name: '注册车辆', value: this.vehicleToday}, {name: '外来车辆', value: this.tempLogToday}];
      let data = [];
      var color = ['#00ffff', '#006ced']
      for (let i = 0; i < vehicle.length; i++) {
        data.push({
          value: vehicle[i].value,
          name: vehicle[i].name,
          itemStyle: {
            normal: {
              borderWidth: 5,
              shadowBlur: 20,
              borderColor: color[i],
              shadowColor: color[i]
            }
          }
        }, {
          value: 2,
          name: '',
          itemStyle: {
            normal: {
              label: {
                show: false
              },
              labelLine: {
                show: false
              },
              color: 'rgba(0, 0, 0, 0)',
              borderColor: 'rgba(0, 0, 0, 0)',
              borderWidth: 0
            }
          }
        });
      }
      let total = this.vehicleToday + this.tempLogToday;
      let seriesOption = [{
        name: '',
        type: 'pie',
        clockWise: false,
        radius: [80, 80],
        hoverAnimation: false,
        itemStyle: {
          normal: {
            label: {
              show: true,
              position: 'outsider',
              color: '#141414',
              fontWeight: 'bold',
              padding: [0, -60],
              alignTo: 'labelLine',
              formatter: function (params) {
                let percent = 0;
                if (!equals(0,total)){
                  percent = ((params.value / total) * 100).toFixed(0);
                }
                if (params.name !== '') {
                  return params.name + '\n' + '\n' + '占比：' + percent + '%';
                } else {
                  return '';
                }
              },
            },
            labelLine: {
              length: 20,
              length2: 50,
              show: true,
              color: '#00ffff'
            }
          }
        },
        data: data
      }];
      this.vehicleLogTodayChart.setOption({
        color: color,
        title: {
          text: total,
          top: '45%',
          textAlign: "center",
          left: "48%",
          textStyle: {
            color: '#141414',
            fontSize: 30,
            fontWeight: 'bold'
          }
        },
        graphic: {
          elements: [{
            type: "image",
            z: 3,
            style: {
              image: img,
              width: 130,
              height: 130
            },
            left: 'center',
            top: 'center',
            position: [90, 90]
          }]
        },
        tooltip: {
          show: false
        },
        toolbox: {
          show: false
        },
        series: seriesOption
      })
    }
  },
}
</script>
<style scoped>
.round-corner {
  border-radius: 10px;
}
</style>

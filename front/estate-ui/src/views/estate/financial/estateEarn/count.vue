<template>
  <div class="app-container">
    <div v-show="openView === 0">
      <el-row :gutter="20">
        <!-- 树结构     -->
        <el-col :span="3" :xs="24">
          <div class="head-container">
            <el-input v-model="filterText" placeholder="请输入小区名称" clearable size="small"
                      prefix-icon="el-icon-search" style="margin-bottom: 20px"/>
          </div>
          <div >
            <el-tree
              :data="comOptions"
              :props="defaultProps"
              :expand-on-click-node="false"
              :filter-node-method="filterNode"
              ref="tree"
              highlight-current
              node-key="communityId"
              @node-click="handleNodeClick"
            >
              <!-- 插入楼栋图标 -->
              <span slot-scope="{ node }">
	   		          <i class="el-icon-office-building" />
	   		          <span class="content"  style="padding-left:5px;">{{node.label}}</span>
		          </span>
            </el-tree>
          </div>
        </el-col>

        <el-col :span="21" :xs="24">
          <br/>
          <br/>
          <br/>
          <el-row>
            <el-col :span="11" :offset="0" >
              <div class="echart" id="mychart" :style="myChartStyle" ></div>
            </el-col>
            <el-col :span="11" :offset="0" >
              <div class="echart" id="mychart2" :style="myChartStyle" ></div>
            </el-col>
          </el-row>

          <br/>
          <br/>
          <br/>
          <br/>

          <br/>
          <br/>
          <el-row>
            <el-col :span="11" :offset="0" >
              <div class="echart" id="mychart3" :style="myChartStyle" ></div>
            </el-col>
            <el-col :span="11" :offset="0" >
              <div class="echart" id="mychart4" :style="myChartStyle2" ></div>
            </el-col>
          </el-row>
        </el-col>
      </el-row>
    </div>

  </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import * as echarts from "echarts";
import {getRecordSum} from "@/api/estate/manage/meter/record";
import {countList} from "@/api/estate/financial/estateEarn";

export default {
  name: "count",
  data(){
    return{
      water:[],
      electric:[],
      gas:[],
      lastWater:[],
      lastElectric:[],
      lastGas:[],
      // 树筛选文本
      filterText:'',
      openView:false,
      //树结构
      comOptions:[],
      communityId: null,
      communityName : null,
      defaultProps: {
        children: "communityId",
        label: "communityName"
      },
      // 查询参数
      queryParams: {
        communityId: null,
      },
      myChartStyle: { float: "left", width: "100%", height: "300px" }, //图表样式
      myChartStyle2: { float: "left", width: "100%", height: "335px" } //图表样式
    }
  },
  created() {
    this.getTreeSelect();

  },
  mounted() {
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods:{
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.communityId = data.communityId;
      this.communityName = data.communityName;
      this.getList();
      this.getList2();
    },
    /** 树结构 */
    getTreeSelect() {
      this.openView = 0;
      getCurrentList().then(response => {
        this.comOptions = response.data;
        this.communityId = this.comOptions[0].communityId;

        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.comOptions[0].communityId);
        });
        this.getList();
      });
    },
    getList2(){
      this.queryParams.communityId = this.communityId;
      countList(this.queryParams).then(response => {
        this.water = response.water;
        this.electric = response.electric;
        this.gas = response.gas;

        this.lastWater = response.lastWater;
        this.lastElectric = response.lastElectric;
        this.lastGas = response.lastGas;

        this.$nextTick(() => {
          this.initEcharts();
        })
      });
    },
    getList() {
      this.queryParams.communityId = this.communityId;
      getRecordSum(this.queryParams).then(response => {

        this.water = response.water;
        this.electric = response.electric;
        this.gas = response.gas;

        this.lastWater = response.lastWater;
        this.lastElectric = response.lastElectric;
        this.lastGas = response.lastGas;

        this.$nextTick(() => {
          this.initEcharts();
        })
      });
    },

    initEcharts() {
      const option = {
        title: {
          text: '水量趋势图'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        legend: {
          // data: ['2022', '2021']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月','8月','9月','10月','11月','12月']
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: new Date().getFullYear(),
            type: 'line',
            stack: 'Total',
            areaStyle: {},
            emphasis: {
              focus: 'series'
            },
            // data: [120, 132, 101, 134, 90, 230, 210, 101, 134, 90, 230, 210]
            data: this.water
          },
          {
            name: new Date().getFullYear() - 1,
            type: 'line',
            stack: 'Total',
            areaStyle: {},
            emphasis: {
              focus: 'series'
            },
            // data: [220, 182, 191, 234, 290, 330, 310, 191, 234, 290, 330, 310]
            data: this.lastWater
          },

        ]
      };
      const option2 = {
        title: {
          text: '电量趋势图'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        legend: {
          // data: ['2022', '2021']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月','8月','9月','10月','11月','12月']
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: new Date().getFullYear(),
            type: 'line',
            stack: 'Total',
            areaStyle: {},
            emphasis: {
              focus: 'series'
            },
            // data: [120, 132, 101, 134, 90, 230, 210, 101, 134, 90, 230, 210]
            data : this.electric
          },

          {
            name: new Date().getFullYear() - 1,
            type: 'line',
            stack: 'Total',
            areaStyle: {},
            emphasis: {
              focus: 'series'
            },
            // data: [220, 182, 191, 234, 290, 330, 310, 191, 234, 290, 330, 310]
            data: this.lastElectric
          },


        ]
      };
      const option3 = {
        title: {
          text: '燃气使用量趋势图'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross',
            label: {
              backgroundColor: '#6a7985'
            }
          }
        },
        legend: {
          // data: ['2022', '2021']
        },
        grid: {
          left: '3%',
          right: '4%',
          bottom: '3%',
          containLabel: true
        },
        xAxis: [
          {
            type: 'category',
            boundaryGap: false,
            data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月','8月','9月','10月','11月','12月']
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: new Date().getFullYear(),
            type: 'line',
            stack: 'Total',
            areaStyle: {},
            emphasis: {
              focus: 'series'
            },
            // data: [120, 132, 101, 134, 90, 230, 210, 101, 134, 90, 230, 210]
            data: this.gas
          },
          {
            name: new Date().getFullYear() - 1,
            type: 'line',
            stack: 'Total',
            areaStyle: {},
            emphasis: {
              focus: 'series'
            },
            // data: [220, 182, 191, 234, 290, 330, 310, 191, 234, 290, 330, 310]
            data: this.lastGas
          },

        ]
      };
      const posList = [
        'left',
        'right',
        'top',
        'bottom',
        'inside',
        'insideTop',
        'insideLeft',
        'insideRight',
        'insideBottom',
        'insideTopLeft',
        'insideTopRight',
        'insideBottomLeft',
        'insideBottomRight'
      ];
      app.configParameters = {
        rotate: {
          min: -90,
          max: 90
        },
        align: {
          options: {
            left: 'left',
            center: 'center',
            right: 'right'
          }
        },
        verticalAlign: {
          options: {
            top: 'top',
            middle: 'middle',
            bottom: 'bottom'
          }
        },
        position: {
          options: posList.reduce(function (map, pos) {
            map[pos] = pos;
            return map;
          }, {})
        },
        distance: {
          min: 0,
          max: 100
        }
      };
      app.config = {
        rotate: 90,
        align: 'left',
        verticalAlign: 'middle',
        position: 'insideBottom',
        distance: 15,
        onChange: function () {
          const labelOption = {
            rotate: app.config.rotate,
            align: app.config.align,
            verticalAlign: app.config.verticalAlign,
            position: app.config.position,
            distance: app.config.distance
          };
          myChart.setOption({
            series: [
              {
                label: labelOption
              },
              {
                label: labelOption
              },
              {
                label: labelOption
              },
              {
                label: labelOption
              }
            ]
          });
        }
      };
      const labelOption = {
        show: false,
        position: app.config.position,
        distance: app.config.distance,
        align: app.config.align,
        verticalAlign: app.config.verticalAlign,
        rotate: app.config.rotate,
        formatter: '{c}  {name|{a}}',
        fontSize: 8,
        rich: {
          name: {}
        }
      };
      const option4 = {
        title: {
          text: new Date().getFullYear() + '能耗支出图'
        },
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
        },
        legend: {
          data: ['水量', '电量', '燃气使用量']
        },
        toolbox: {
          show: false,
          orient: 'vertical',
          left: 'right',
          top: 'center',
          feature: {
            mark: { show: false },
            dataView: { show: false, readOnly: false },
            magicType: { show: false, type: ['line', 'bar', 'stack'] },
            restore: { show: false },
            saveAsImage: { show: false }
          }
        },
        xAxis: [
          {
            type: 'category',
            axisTick: { show: false },
            data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月','8月','9月','10月','11月','12月']
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
            name: '水量',
            type: 'bar',
            barGap: 0,
            label: labelOption,
            emphasis: {
              focus: 'series'
            },
            data: [320, 332, 301, 334, 390,320, 332, 301, 334, 390, 320, 332],
            // data: this.water
          },
        ]
      };

      this.myChart = echarts.init(document.getElementById("mychart"));
      this.myChart.setOption(option);

      this.myChart = echarts.init(document.getElementById("mychart2"));
      this.myChart.setOption(option2);

      this.myChart = echarts.init(document.getElementById("mychart3"));
      this.myChart.setOption(option3);

      this.myChart = echarts.init(document.getElementById("mychart4"));
      this.myChart.setOption(option4);

      //随着屏幕大小调节图表
      window.addEventListener("resize", () => {
        this.myChart.resize();
      });
    },
  }
}
</script>

<style scoped>
/*设置高亮颜色*/
/deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #00FF99 !important;
}
.content {
  font-size: 14px;
}
</style>

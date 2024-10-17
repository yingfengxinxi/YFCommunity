<template>
  <div class="app-container">
    <div v-show="openView === 0">
      <el-row :span="24">
        <!--   选择树   -->
        <el-col :span="4">
          <div class="head-container">
            <el-input
                placeholder="请输入小区名称"
                v-model="filterText">
            </el-input>

            <el-tree
                class="filter-tree hide-tree"
                :data="currentList"
                :props="currentListProps"
                node-key="communityId"
                default-expand-all
                :filter-node-method="filterNode"
                icon-class="el-icon-star-on"
                @node-click="handleNodeClick"
                highlight-current
                ref="tree">
            </el-tree>
          </div>
        </el-col>
        <el-col :span="20">
          <el-tabs v-model="activeName" @tab-click="tabClick">
            <el-tab-pane label="收益" name="first">
<!--              <el-row>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="seeDetile" v-if="isshow">查看详情
                </el-button>
              </el-row>-->
              <el-row>
                <el-col :span="12">
                  <div>
                    <div id="main" class="echarts" style="width: auto;height: 400px"></div>
                  </div>
                </el-col>
                <el-col :span="12">
                  <div>
                    <div id="main2" class="echarts2" style="width: auto;height: 400px"></div>
                  </div>
                </el-col>
              </el-row>
              <el-row>
                <el-col :span="12">
                  <div>
                    <div id="main3" class="echarts3" style="width: auto;height: 400px"></div>
                  </div>
                </el-col>
                <el-col :span="12">
                  <div>
                    <div id="main4" class="echarts4" style="width: auto;height: 400px"></div>
                  </div>
                </el-col>
              </el-row>
            </el-tab-pane>

            <el-tab-pane label="支出" name="second">
<!--              <el-row>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="seeDetile2" v-if="isshow">查看详情
                </el-button>
              </el-row>-->
              <el-col :span="12">
                <div>
                  <div id="main5" class="echarts5" style="width: auto;height: 400px"></div>
                </div>
              </el-col>
            </el-tab-pane>
          </el-tabs>
        </el-col>
      </el-row>
    </div>
    <div v-show="openView === 1">
<!--      <el-row>
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                 label-width="68px">
          <el-form-item label="创建时间" prop="applyTime">
            <el-date-picker
                v-model="queryParams.yearVo"
                value-format="yyyy"
                type="year"
                placeholder="选择日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="selectyear">搜索</el-button>
          </el-form-item>
        </el-form>
      </el-row>-->
      <h4 class="form-header" style="color: #0558e7"></h4>
      <div>
        <el-form ref="incomeDetailForm" :model="incomeDetailForm" label-width="180px">
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="收益总金额：">
                <div style="width: 180px">{{ incomeDetailForm.totalMoney }}元</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="同比增长率：">
                <div style="width: 180px">{{ incomeDetailForm.totalRate }}%</div>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="物业收益：">
                <div style="width: 180px">{{ incomeDetailForm.estateMoney }}元</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="同比增长率：">
                <div style="width: 180px">{{ incomeDetailForm.estateRate }}%</div>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="车位收益：">
                <div style="width: 180px">{{ incomeDetailForm.parkingMoney }}元</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="同比增长率：">
                <div style="width: 180px">{{ incomeDetailForm.parkingRate }}%</div>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="场馆收益：">
                <div style="width: 180px">{{ incomeDetailForm.venueMoney }}元</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="同比增长率：">
                <div style="width: 180px">{{ incomeDetailForm.venueRate }}%</div>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="其它收益：">
                <div style="width: 180px">{{ incomeDetailForm.serverMoney }}元</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="同比增长率：">
                <div style="width: 180px">{{ incomeDetailForm.serverRate }}%</div>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="returnbefore">返回</el-button>
              </el-form-item>
            </el-col>
          </el-row>

        </el-form>
      </div>
    </div>
    <div v-show="openView === 2">
<!--      <el-row>
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                 label-width="68px">
          <el-form-item label="创建时间" prop="applyTime">
            <el-date-picker
                v-model="queryParams.yearVo"
                value-format="yyyy"
                type="year"
                placeholder="选择日期">
            </el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="selectyear2">搜索</el-button>
          </el-form-item>
        </el-form>
      </el-row>-->
      <h4 class="form-header" style="color: #0558e7"></h4>
      <div>
        <el-form ref="form" :model="form" label-width="180px">
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="支出总金额：">
                <div style="width: 180px">{{ form.expendMoney }}元</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="同比增长率：">
                <div style="width: 180px">{{ form.expendMoney }}%</div>
              </el-form-item>
            </el-col>
          </el-row>
<!--
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="其它支出：">
                <div style="width: 180px">{{ form.buymoney }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="同比增长率：">
                <div style="width: 180px">{{ form.buymoneyrate }}</div>
              </el-form-item>
            </el-col>
          </el-row>
-->
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="returnbefore">返回</el-button>
              </el-form-item>
            </el-col>
          </el-row>

        </el-form>
      </div>
    </div>
  </div>
</template>

<script>
import * as echarts from 'echarts';
import {getCurrentList} from "@/api/estate/community";
import {buylist, countList, detile, threeList, buydetile} from "@/api/estate/financial/estateEarn";

export default {
  name: "count1",
  data() {
    return {
      // 树筛选文本
      filterText: '',
      isshow: false,
      showSearch: true,
      //小区数据集合
      currentList: [],
      buildingList: [
        {alwaysrate: "同比增长率："}
      ],
      //树结构
      comOptions: [],
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      openView: true,
      // 小区Id
      communityId: null,
      parkingMoney: [],
      parkingRate: [],
      estatemoney: [],
      venuemoney: [],
      servermoney: [],
      buymoney: [],
      estatemoneyrate: [],
      venuemoneyrate: [],
      servermoneyrate: [],
      buymoneyrate: [],
      yeasVo: "",
      activeName: 'first',
      incomeDetailForm: {
        estateRate: undefined,
        estateMoney: undefined,
        parkingRate: undefined,
        parkingMoney: undefined,
        serverRate: undefined,
        serverMoney: undefined,
        totalRate: undefined,
        totalMoney: undefined,
        venueMoney: undefined,
        venueRate: undefined
      },
      form: {
        expendMoney: undefined,
        expendRate: undefined,
      },
      // 查询参数
      queryParams: {
        communityId: null,
        yearVo: undefined,
      },
    }
  },
  created() {
    this.openView = 0;
    this.getTree();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  mounted() {
  },
  methods: {
    tabClick(e) {
      this.activeName = e.name;

      if (null == this.communityId) {
        this.$modal.msgError("请选择小区")
        return
      }

      if (this.activeName === 'first') {
        this.getList();
        this.getthreeList();
      } else {
        this.getbuymoneyList();
      }
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(row) {
      this.queryParams.communityId = row.communityId
      this.communityId = row.communityId
      if (this.activeName === 'first') {
        this.getList();
        this.getthreeList();
      } else {
        this.getbuymoneyList();
      }
      this.isshow = true;
    },
    // 获取树数据
    getTree() {
      getCurrentList().then(res => {
        this.currentList = res.data;
        this.queryParams.communityId = res.data[0].communityId;
        this.communityId = res.data[0].communityId;
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.queryParams.communityId); //一定要加这个选中了否则样式没有出来
        });
        this.getList();
        this.getthreeList()
      })
    },
    // 获取停车费
    getList() {
      this.queryParams.communityId = this.communityId;
      countList(this.queryParams).then(response => {
        this.parkingMoney = response.parkingMoney;
        this.parkingRate = response.parkingRate;
        this.$nextTick(() => {
          this.initIncomeEcharts();
        })
      });
    },
    // 获取停车费
    getthreeList() {
      this.queryParams.communityId = this.communityId;
      threeList(this.queryParams).then(response => {
        this.estatemoney = response.estatemoney;
        this.venuemoney = response.venuemoney;
        this.servermoney = response.servermoney;
        this.estatemoneyrate = response.estatemoneyrate;
        this.venuemoneyrate = response.venuemoneyrate;
        this.servermoneyrate = response.servermoneyrate;
        this.$nextTick(() => {
          this.initIncomeEcharts();
        })
      });
    },
    // 获取支出
    getbuymoneyList() {
      this.queryParams.communityId = this.communityId;
      buylist(this.queryParams).then(response => {
        this.buymoney = response.buymoney;
        this.buymoneyrate = response.buymoneyrate;
        this.$nextTick(() => {
          this.initExpendEcharts();
        });
      });
    },
    // 年搜索按钮
    selectyear() {
      this.seeDetile();
    },
    // 年搜索按钮
    selectyear2() {
      this.seeDetile2();
    },
    // 返回
    returnbefore() {
      this.openView = 0;
    },
    // 查看详情
    seeDetile() {
      this.openView = 1;
      this.queryParams.communityId = this.communityId;
      detile(this.queryParams).then(response => {
        this.incomeDetailForm.estateRate = response.estateRate;
        this.incomeDetailForm.estateMoney = response.estateMoney;
        this.incomeDetailForm.parkingRate = response.parkingRate;
        this.incomeDetailForm.parkingMoney = response.parkingMoney;
        this.incomeDetailForm.serverRate = response.serverRate;
        this.incomeDetailForm.serverMoney = response.serverMoney;
        this.incomeDetailForm.venueMoney = response.venueMoney;
        this.incomeDetailForm.venueRate = response.venueRate;
        this.incomeDetailForm.totalRate = response.totalRate;
        this.incomeDetailForm.totalMoney = response.totalMoney;
      })
    },
    // 查看详情
    seeDetile2() {
      this.openView = 2;
      this.queryParams.communityId = this.communityId;
      buydetile(this.queryParams).then(response => {
        this.form.expendMoney = response.expendMoney;
        this.form.expendRate = response.expendRate;
      })
    },
    /**
     * 初始化收益Echarts
     */
    initIncomeEcharts() {
      var chartDom = document.getElementById('main');
      var myChart = echarts.init(chartDom);
      var option;
      // 第一个颜色就是柱状图的颜色
      const colors = ['#B9DFEE', '#EB582A', '#CCCCCC', '#F4CCE9'];
      option = {
        color: colors,
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          },
          formatter: '{a0}: {c0}元<br />{a1}: {c1}%'
        },
        grid: {
          right: '20%'
        },
        toolbox: {
          feature: {
            dataView: {show: false, readOnly: false},
            restore: {show: false},
            saveAsImage: {show: false}
          }
        },
        legend: {
          data: ['物业费收益', 'Precipitation', '同比']
        },
        xAxis: [
          {
            type: 'category',
            axisTick: {
              alignWithLabel: true
            },
            // prettier-ignore
            data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '单位(元)',
            position: 'left',
            alignTicks: true,
            axisLine: {
              show: true,
              lineStyle: {
                //左边的线的颜色
                color: colors[2]
              }
            },
            axisLabel: {
              formatter: '{value}'
            }
          },
          {
            type: 'value',
            name: '',
            position: 'left',
            alignTicks: true,
            offset: 80,
            axisLine: {
              show: true,
              lineStyle: {
                // 没有变化
                color: colors[2]
              }
            },
            axisLabel: {
              formatter: '{value}'
            }
          },
          {
            type: 'value',
            name: '同比',
            position: 'right',
            alignTicks: true,
            axisLine: {
              show: true,
              lineStyle: {
                // 右边的线的颜色
                color: colors[2]
              }
            },
            axisLabel: {
              formatter: '{value}%'
            }
          }
        ],
        series: [
          {
            name: '物业费收益',
            type: 'bar',
            data: this.estatemoney
          },
          {
            name: '同比',
            type: 'line',
            yAxisIndex: 2,
            data: this.estatemoneyrate
          }
        ]
      };
      option && myChart.setOption(option);
      // -----------------------------------------------------------------------------
      var chartDom2 = document.getElementById('main2');
      var myChart2 = echarts.init(chartDom2);
      var option2;

      const colors2 = ['#B9BEEE', '#CB2C2C', '#CCCCCC', '#B9EEC8'];
      option2 = {
        color: colors2,
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          },
          formatter: '{a0}: {c0}元<br />{a1}: {c1}%'
        },
        grid: {
          right: '20%'
        },
        toolbox: {
          feature: {
            dataView: {show: false, readOnly: false},
            restore: {show: false},
            saveAsImage: {show: false}
          }
        },
        legend: {
          data: ['车位费收益', 'Precipitation', '同比']
        },
        xAxis: [
          {
            type: 'category',
            axisTick: {
              alignWithLabel: true
            },
            // prettier-ignore
            data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '收益(元)',
            position: 'left',
            alignTicks: true,
            axisLine: {
              show: true,
              lineStyle: {
                color: colors[2]
              }
            },
            axisLabel: {
              formatter: '{value}'
            }
          },
          {
            type: 'value',
            name: '',
            position: 'right',
            alignTicks: true,
            offset: 80,
            axisLine: {
              show: false,
              lineStyle: {
                color: colors[1]
              }
            },
            axisLabel: {
              formatter: '{value}%'
            }
          },
          {
            type: 'value',
            name: '同比',
            position: 'right',
            alignTicks: true,
            axisLine: {
              show: true,
              lineStyle: {
                color: colors[2]
              }
            },
            axisLabel: {
              formatter: '{value}'
            }
          }
        ],
        series: [
          {
            name: '车位费收益',
            type: 'bar',
            // data: [20, 0, 0, 0, 0, 0, 0, 0, 32.6, 20.0, 6.4, 3.3]
            data: this.parkingMoney
          },
          {
            name: '同比',
            type: 'line',
            yAxisIndex: 2,
            // data: [20, 0, 0, 0, 0, 0, 0, 0, 32.6, 20.0, 6.4, 3.3]
            data: this.parkingRate
          }
        ]
      };
      option2 && myChart2.setOption(option2);
      // -----------------------------------------------------------------------------
      var chartDom3 = document.getElementById('main3');
      var myChart3 = echarts.init(chartDom3);
      var option3;
      const colors3 = ['#B9EEC8', '#2A83EB', '#CCCCCC', '#B9EEC8'];

      option3 = {
        color: colors3,
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          },
          formatter: '{a0}: {c0}元<br />{a1}: {c1}%'
        },
        grid: {
          right: '20%'
        },
        toolbox: {
          feature: {
            dataView: {show: false, readOnly: false},
            restore: {show: false},
            saveAsImage: {show: false}
          }
        },
        legend: {
          data: ['场馆收益', 'Precipitation', '同比']
        },
        xAxis: [
          {
            type: 'category',
            axisTick: {
              alignWithLabel: true
            },
            // prettier-ignore
            data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '收益(元)',
            position: 'left',
            alignTicks: true,
            axisLine: {
              show: true,
              lineStyle: {
                color: colors[2]
              }
            },
            axisLabel: {
              formatter: '{value}'
            }
          },
          {
            type: 'value',
            name: '',
            position: 'right',
            alignTicks: true,
            offset: 80,
            axisLine: {
              show: false,
              lineStyle: {
                color: colors[1]
              }
            },
            axisLabel: {
              formatter: '{value}%'
            }
          },
          {
            type: 'value',
            name: '同比',
            position: 'right',
            alignTicks: true,
            axisLine: {
              show: true,
              lineStyle: {
                color: colors[2]
              }
            },
            axisLabel: {
              formatter: '{value}'
            }
          }
        ],
        series: [
          {
            name: '场馆收益',
            type: 'bar',
            data: this.venuemoney
          },
          {
            name: '同比',
            type: 'line',
            yAxisIndex: 2,
            data: this.venuemoneyrate
          }
        ]
      };
      option3 && myChart3.setOption(option3);
      // -----------------------------------------------------------------------------
      var chartDom4 = document.getElementById('main4');
      var myChart4 = echarts.init(chartDom4);
      var option4;
      const colors4 = ['#F4CCE9', '#2CC1CB', '#CCCCCC', '#B9EEC8'];

      option4 = {
        color: colors4,
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          },
          formatter: '{a0}: {c0}元<br />{a1}: {c1}%'
        },
        grid: {
          right: '20%'
        },
        toolbox: {
          feature: {
            dataView: {show: false, readOnly: false},
            restore: {show: false},
            saveAsImage: {show: false}
          }
        },
        legend: {
          data: ['其他收益', 'Precipitation', '同比']
        },
        xAxis: [
          {
            type: 'category',
            axisTick: {
              alignWithLabel: true
            },
            // prettier-ignore
            data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '收益(元)',
            position: 'left',
            alignTicks: true,
            axisLine: {
              show: true,
              lineStyle: {
                color: colors[2]
              }
            },
            axisLabel: {
              formatter: '{value}'
            }
          },
          {
            type: 'value',
            name: '',
            position: 'right',
            alignTicks: true,
            offset: 80,
            axisLine: {
              show: false,
              lineStyle: {
                color: colors[0]
              }
            },
            axisLabel: {
              formatter: '{value}'
            }
          },
          {
            type: 'value',
            name: '同比',
            position: 'right',
            alignTicks: true,
            axisLine: {
              show: true,
              lineStyle: {
                color: colors[2]
              }
            },
            axisLabel: {
              formatter: '{value}%'
            }
          }
        ],
        series: [
          {
            name: '其他收益',
            type: 'bar',
            data: this.servermoney
          },
          {
            name: '同比',
            type: 'line',
            yAxisIndex: 2,
            data: this.servermoneyrate
          }
        ]
      };
      option4 && myChart4.setOption(option4);
    },

    /**
     * 初始化支出Echarts
     */
    initExpendEcharts() {
      var chartDom5 = document.getElementById('main5');
      var myChart5 = echarts.init(chartDom5);
      var option5;
      const colors = ['#B9DFEE', '#EB582A', '#CCCCCC', '#F4CCE9'];

      option5 = {
        color: colors,
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'cross'
          },
          formatter: '{a0}: {c0}元<br />{a1}: {c1}%'
        },
        grid: {
          right: '20%'
        },
        toolbox: {
          feature: {
            dataView: {show: false, readOnly: false},
            restore: {show: false},
            saveAsImage: {show: false}
          }
        },
        legend: {
          data: ['支出', 'Precipitation', '同比']
        },
        xAxis: [
          {
            type: 'category',
            axisTick: {
              alignWithLabel: true
            },
            // prettier-ignore
            data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月', '8月', '9月', '10月', '11月', '12月']
          }
        ],
        yAxis: [
          {
            type: 'value',
            name: '支出(元)',
            position: 'left',
            alignTicks: true,
            axisLine: {
              show: true,
              lineStyle: {
                color: colors[2]
              }
            },
            axisLabel: {
              formatter: '{value}'
            }
          },
          {
            type: 'value',
            name: '',
            position: 'right',
            alignTicks: true,
            offset: 80,
            axisLine: {
              show: false,
              lineStyle: {
                color: colors[0]
              }
            },
            axisLabel: {
              formatter: '{value}'
            }
          },
          {
            type: 'value',
            name: '同比',
            position: 'right',
            alignTicks: true,
            axisLine: {
              show: true,
              lineStyle: {
                color: colors[2]
              }
            },
            axisLabel: {
              formatter: '{value}%'
            }
          }
        ],
        series: [
          {
            name: '支出',
            type: 'bar',
            data: this.buymoney
          },
          {
            name: '同比',
            type: 'line',
            yAxisIndex: 2,
            data: this.buymoneyrate
          }
        ]
      };
      option5 && myChart5.setOption(option5);
    }
  }

}
</script>

<style scoped>

</style>

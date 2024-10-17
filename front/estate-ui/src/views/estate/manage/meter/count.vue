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
              
            </el-tree>
          </div>
        </el-col>

        <el-col :span="21" :xs="24">
          <br/>
          <br/>
          <br/>
          <el-row>
            <el-col :offset="2">
              <el-form :model="queryParams2" ref="queryForm2" size="small" :inline="true" v-show="showSearch" label-width="68px">
              <el-form-item label="设备类型" prop="meterReadingType">
                <el-select
                  v-model="queryParams2.meterReadingType"
                  placeholder="请选择设备类型"
                  clearable
                  style="width: 160px"
                >
                  <el-option
                    v-for="item in meterReadingTypeOptions1"
                    :key="item.dictValue"
                    :label="item.dictLabel"
                    :value="item.dictValue"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="年份" prop="selectDate2">
                <el-date-picker
                    v-model="selectDate2"
                    type="year"
                    value-format="yyyy"
                    placeholder="选择年">
                  </el-date-picker>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery2">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery2">重置</el-button>
              </el-form-item>
            </el-form>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="21" :offset="0" >
              <div class="echart" id="mychart" :style="myChartStyle" ></div>
            </el-col>
          </el-row>

          <br/>
          <br/>
          <br/>
          <br/>

          <br/>
          <br/>
          <el-row>
            <el-col :offset="2">
              <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
              <el-form-item label="设备类型" prop="meterReadingType">
                <el-select
                  v-model="queryParams.meterReadingType"
                  placeholder="请选择设备类型"
                  clearable
                  style="width: 160px"
                >
                  <el-option
                    v-for="item in meterReadingTypeOptions0"
                    :key="item.dictValue"
                    :label="item.dictLabel"
                    :value="item.dictValue"
                  />
                </el-select>
              </el-form-item>
              <el-form-item label="年份" prop="selectDate2">
                <el-date-picker
                    v-model="selectDate"
                    type="year"
                    value-format="yyyy"
                    placeholder="选择年">
                  </el-date-picker>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
              </el-form-item>
            </el-form>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="21" :offset="0" >
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

export default {
  name: "count",
  data(){
    return {
      meterReadingTypeOptions0: [],
      meterReadingTypeOptions1: [],
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
      selectDate:new Date().getFullYear()+'',
      selectDate2: new Date().getFullYear()+'',
      // 显示搜索条件
      showSearch: true,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: null,
        meterReadingTime:null,
        selectDate: '',
        meterReadingType:null,
        energyConsumptionType : null
      },
      queryParams2: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        meterReadingTime: undefined,
        meterReadingType: undefined,
        energyConsumptionType:undefined,
      },
      
      myChartStyle: { float: "left", width: "100%", height: "300px" }, //图表样式
      myChartStyle2: { float: "left", width: "100%", height: "335px" } //图表样式
    }
  },
  created() {
    
    this.getMeterReadingTypeOptions0("0");
    this.getMeterReadingTypeOptions1("1");
    this.getTreeSelect();

  },
  mounted() {
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    //水表
    getMeterReadingTypeOptions0(energyConsumptionType) {
      this.queryParams.meterReadingType = undefined;
      
      this.getDicts("meter_reading_type").then(response => {
        var meterReadingTypeOptions = response.data;
        this.meterReadingTypeOptions0=meterReadingTypeOptions.filter(item => item.remark == energyConsumptionType);
      });
    },
    //电表
    getMeterReadingTypeOptions1(energyConsumptionType) {
      this.queryParams.meterReadingType = undefined;
      
      this.getDicts("meter_reading_type").then(response => {
        var meterReadingTypeOptions = response.data;
        this.meterReadingTypeOptions1=meterReadingTypeOptions.filter(item => item.remark == energyConsumptionType);
      });
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    handleQuery() {
      this.getList();
    },
    resetQuery() { 
      this.selectDate = new Date().getFullYear()+'';
      this.resetForm("queryForm");
      this.getList();
    },
    handleQuery2() {
      this.getList2();
    },
    resetQuery2() { 
      this.selectDate2 = new Date().getFullYear()+'';
      this.resetForm("queryForm2");
      this.getList2();
    },
     //水表统计
     getList() {
      console.log(">>>>", this.selectDate);
      if (this.selectDate){
        var year = this.selectDate;
        this.queryParams.meterReadingTime = year;
      }
      else {
        this.queryParams.meterReadingTime = new Date().getFullYear();
       }
       this.queryParams.communityId = this.communityId;
      this.queryParams.energyConsumptionType='0'
      getRecordSum(this.queryParams).then(response => {
        this.water = response.data;
        this.$nextTick(() => {
          this.initEcharts(this.queryParams.meterReadingTime);
        })
      });
      
     
    },
    //电量统计
    getList2() {
      console.log(">>>>", this.selectDate2);
      if (this.selectDate2){
        var year = this.selectDate2;
        this.queryParams2.meterReadingTime = year;
      }
      else {
        this.queryParams2.meterReadingTime = new Date().getFullYear();
      }
      this.queryParams2.energyConsumptionType = '1'
      this.queryParams2.communityId = this.communityId;
      getRecordSum(this.queryParams2).then(response => {
        this.electric = response.data;
        this.$nextTick(() => {
          this.initEcharts(this.queryParams2.meterReadingTime);
        })
      });
     
     
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
        this.queryParams2.communityId =this.communityId;
        this.queryParams.communityId = this.communityId;

        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.comOptions[0].communityId);
        });
        this.getList();
        this.getList2();
      });
    },

   

    initEcharts(meterReadingTime) {
      const option = {
        title:{
          text: meterReadingTime+"年度电量趋势图(千瓦)"
        },
        color:["#748ede"],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
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
            data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月','8月','9月','10月','11月','12月'],
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
          
            type: 'bar',
            barWidth: '30%',
            data: this.electric
          }
        ]
      };
     
      const option4 = {
        title:{
          text: meterReadingTime+"年度水量趋势图(吨)"
        },
        color:["#748ede"],
        tooltip: {
          trigger: 'axis',
          axisPointer: {
            type: 'shadow'
          }
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
            data: ['1月', '2月', '3月', '4月', '5月', '6月', '7月','8月','9月','10月','11月','12月'],
            axisTick: {
              alignWithLabel: true
            }
          }
        ],
        yAxis: [
          {
            type: 'value'
          }
        ],
        series: [
          {
           
            type: 'bar',
            barWidth: '30%',
            data: this.water
          }
        ]
      };

      this.myChart = echarts.init(document.getElementById("mychart"));
      this.myChart.setOption(option);

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
  background-color: #b3f1b6 !important;
}
.content {
  font-size: 14px;
}
</style>

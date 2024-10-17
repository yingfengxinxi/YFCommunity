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
        <el-tabs v-model="activeName">
         
          <el-tab-pane label="电表" name="second">
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
              <el-form-item label="年份" prop="year">
                <el-select
                  v-model="queryParams2.year"
                  placeholder="请选择设备类型"
                  clearable
                  style="width: 160px"
                >
                  <el-option
                    v-for="item in yearOptions"
                    :key="item.year"
                    :label="item.year"
                    :value="item.year"
                  />
                </el-select>
              </el-form-item>
              <el-form-item>
                <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery2">搜索</el-button>
                <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
              </el-form-item>
            </el-form>

            <el-row :gutter="10" class="mb8">
              <el-col :span="1.5">
                <el-button
                  type="warning"
                  plain
                  icon="el-icon-download"
                  size="mini"
                  @click="handleExport2"
                  v-hasPermi="['business:record:export2']"
                >导出</el-button>
              </el-col>
<!--              <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
            </el-row>


            <el-table :data="recordList2" >
              <el-table-column prop="houseName" label="门牌号" align="center" width="120px"/>
              <el-table-column prop="ownerName" label="业主姓名" align="center">
                <template slot-scope="scope">
                  <div v-if="scope.row.ownerName == null" type="primary">-</div>
                  <div v-else >{{scope.row.ownerName}}</div>
                </template>
              </el-table-column>
              <el-table-column label="1月" align="center">
                <el-table-column prop="one" label="耗电量(千瓦时)" align="center">
                  <template slot-scope="scope">
                    <div v-if="scope.row.one == null" >-</div>
                    <div v-else >{{scope.row.one}}</div>
                  </template>
                </el-table-column>
              </el-table-column>
              <el-table-column label="2月" align="center">
                <el-table-column prop="two" label="耗电量(千瓦时)" align="center">
                  <template slot-scope="scope">
                    <div v-if="scope.row.two == null" >-</div>
                    <div v-else >{{scope.row.two}}</div>
                  </template>
                </el-table-column>
              </el-table-column>
              <el-table-column label="3月" align="center">
                <el-table-column prop="three" label="耗电量(千瓦时)" align="center">
                  <template slot-scope="scope">
                    <div v-if="scope.row.three == null" >-</div>
                    <div v-else >{{scope.row.three}}</div>
                  </template>
                </el-table-column>
              </el-table-column>
              <el-table-column label="4月" align="center">
                <el-table-column prop="four" label="耗电量(千瓦时)" align="center">
                  <template slot-scope="scope">
                    <div v-if="scope.row.four == null" >-</div>
                    <div v-else >{{scope.row.four}}</div>
                  </template>
                </el-table-column>
              </el-table-column>
              <el-table-column label="5月" align="center">
                <el-table-column prop="five" label="耗电量(千瓦时)" align="center">
                  <template slot-scope="scope">
                    <div v-if="scope.row.five == null" >-</div>
                    <div v-else >{{scope.row.five}}</div>
                  </template>
                </el-table-column>
              </el-table-column>
              <el-table-column label="6月" align="center">
                <el-table-column prop="six" label="耗电量(千瓦时)" align="center">
                  <template slot-scope="scope">
                    <div v-if="scope.row.six == null" >-</div>
                    <div v-else >{{scope.row.six}}</div>
                  </template>
                </el-table-column>
              </el-table-column>
              <el-table-column label="7月" align="center">
                <el-table-column prop="seven" label="耗电量(千瓦时)" align="center">
                  <template slot-scope="scope">
                    <div v-if="scope.row.seven == null" >-</div>
                    <div v-else >{{scope.row.seven}}</div>
                  </template>
                </el-table-column>
              </el-table-column>
              <el-table-column label="8月" align="center">
                <el-table-column prop="eight" label="耗电量(千瓦时)" align="center">
                  <template slot-scope="scope">
                    <div v-if="scope.row.eight == null" >-</div>
                    <div v-else >{{scope.row.eight}}</div>
                  </template>
                </el-table-column>
              </el-table-column>
              <el-table-column label="9月" align="center">
                <el-table-column prop="nine" label="耗电量(千瓦时)" align="center">
                  <template slot-scope="scope">
                    <div v-if="scope.row.nine == null" >-</div>
                    <div v-else >{{scope.row.nine}}</div>
                  </template>
                </el-table-column>
              </el-table-column>
              <el-table-column label="10月" align="center">
                <el-table-column prop="ten" label="耗电量(千瓦时)" align="center">
                  <template slot-scope="scope">
                    <div v-if="scope.row.ten == null" >-</div>
                    <div v-else >{{scope.row.ten}}</div>
                  </template>
                </el-table-column>
              </el-table-column>
              <el-table-column label="11月" align="center">
                <el-table-column prop="eleven" label="耗电量(千瓦时)" align="center">
                  <template slot-scope="scope">
                    <div v-if="scope.row.eleven == null" >-</div>
                    <div v-else >{{scope.row.eleven}}</div>
                  </template>
                </el-table-column>
              </el-table-column>
              <el-table-column label="12月" align="center">
                <el-table-column prop="twelve" label="耗电量(千瓦时)" align="center">
                  <template slot-scope="scope">
                    <div v-if="scope.row.twelve == null" >-</div>
                    <div v-else >{{scope.row.twelve}}</div>
                  </template>
                </el-table-column>
              </el-table-column>
            </el-table>

            <pagination
              v-show="total2>0"
              :total="total2"
              :page.sync="queryParams2.pageNum"
              :limit.sync="queryParams2.pageSize"
              @pagination="getList2"
            />
            <br/>
            <br/>
          </el-tab-pane>
          <el-tab-pane label="水表" name="first">
              <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
                <el-form-item label="门牌号" prop="roomId">
                  <treeselect v-model="queryParams.roomId"
                              :options="treeOptions"
                              id="deptId-tree"
                              :searchable="false"
                              style="width: 200px;height: 0px;"
                              :default-expand-level="Infinity"
                              placeholder="请选择门牌号"
                              @select="handleSelect" />
                </el-form-item>
                <el-form-item label="业主姓名" prop="ownerName">
                  <el-input
                    v-model="queryParams.ownerName"
                    placeholder="请输入业主姓名"
                    clearable
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>

                <el-form-item label="选择时间" prop="selectDate">
                  <el-date-picker
                    v-model="selectDate"
                    type="year"
                    placeholder="选择年">
                  </el-date-picker>
                </el-form-item>

                <el-form-item>
                  <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
                  <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
                </el-form-item>
              </el-form>

              <el-row :gutter="10" class="mb8">
                <el-col :span="1.5">
                  <el-button
                    type="warning"
                    plain
                    icon="el-icon-download"
                    size="mini"
                    @click="handleExport"
                    v-hasPermi="['business:record:export']"
                  >导出</el-button>
                </el-col>
              <!--                <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>-->
              </el-row>


              <el-table :data="recordList" >
                <el-table-column prop="houseName" label="门牌号" align="center" width="120px"/>
                <el-table-column prop="ownerName" label="业主姓名" align="center">
                  <template slot-scope="scope">
                    <div v-if="scope.row.ownerName == null" type="primary">-</div>
                    <div v-else >{{scope.row.ownerName}}</div>
                  </template>
                </el-table-column>
                <el-table-column label="1月" align="center">
                  <el-table-column prop="one" label="耗水量(吨)" align="center">
                    <template slot-scope="scope">
                      <div v-if="scope.row.one == null" >-</div>
                      <div v-else >{{scope.row.one}}</div>
                    </template>
                  </el-table-column>
                </el-table-column>
                <el-table-column label="2月" align="center">
                  <el-table-column prop="two" label="耗水量(吨)" align="center">
                    <template slot-scope="scope">
                      <div v-if="scope.row.two == null" >-</div>
                      <div v-else >{{scope.row.two}}</div>
                    </template>
                  </el-table-column>
                </el-table-column>
                <el-table-column label="3月" align="center">
                  <el-table-column prop="three" label="耗水量(吨)" align="center">
                    <template slot-scope="scope">
                      <div v-if="scope.row.three == null" >-</div>
                      <div v-else >{{scope.row.three}}</div>
                    </template>
                  </el-table-column>
                </el-table-column>
                <el-table-column label="4月" align="center">
                  <el-table-column prop="four" label="耗水量(吨)" align="center">
                    <template slot-scope="scope">
                      <div v-if="scope.row.four == null" >-</div>
                      <div v-else >{{scope.row.four}}</div>
                    </template>
                  </el-table-column>
                </el-table-column>
                <el-table-column label="5月" align="center">
                  <el-table-column prop="five" label="耗水量(吨)" align="center">
                    <template slot-scope="scope">
                      <div v-if="scope.row.five == null" >-</div>
                      <div v-else >{{scope.row.five}}</div>
                    </template>
                  </el-table-column>
                </el-table-column>
                <el-table-column label="6月" align="center">
                  <el-table-column prop="six" label="耗水量(吨)" align="center">
                    <template slot-scope="scope">
                      <div v-if="scope.row.six == null" >-</div>
                      <div v-else >{{scope.row.six}}</div>
                    </template>
                  </el-table-column>
                </el-table-column>
                <el-table-column label="7月" align="center">
                  <el-table-column prop="seven" label="耗水量(吨)" align="center">
                    <template slot-scope="scope">
                      <div v-if="scope.row.seven == null" >-</div>
                      <div v-else >{{scope.row.seven}}</div>
                    </template>
                  </el-table-column>
                </el-table-column>
                <el-table-column label="8月" align="center">
                  <el-table-column prop="eight" label="耗水量(吨)" align="center">
                    <template slot-scope="scope">
                      <div v-if="scope.row.eight == null" >-</div>
                      <div v-else >{{scope.row.eight}}</div>
                    </template>
                  </el-table-column>
                </el-table-column>
                <el-table-column label="9月" align="center">
                  <el-table-column prop="nine" label="耗水量(吨)" align="center">
                    <template slot-scope="scope">
                      <div v-if="scope.row.nine == null" >-</div>
                      <div v-else >{{scope.row.nine}}</div>
                    </template>
                  </el-table-column>
                </el-table-column>
                <el-table-column label="10月" align="center">
                  <el-table-column prop="ten" label="耗水量(吨)" align="center">
                    <template slot-scope="scope">
                      <div v-if="scope.row.ten == null" >-</div>
                      <div v-else >{{scope.row.ten}}</div>
                    </template>
                  </el-table-column>
                </el-table-column>
                <el-table-column label="11月" align="center">
                  <el-table-column prop="eleven" label="耗水量(吨)" align="center">
                    <template slot-scope="scope">
                      <div v-if="scope.row.eleven == null" >-</div>
                      <div v-else >{{scope.row.eleven}}</div>
                    </template>
                  </el-table-column>
                </el-table-column>
                <el-table-column label="12月" align="center">
                  <el-table-column prop="twelve" label="耗水量(吨)" align="center">
                    <template slot-scope="scope">
                      <div v-if="scope.row.twelve == null" >-</div>
                      <div v-else >{{scope.row.twelve}}</div>
                    </template>
                  </el-table-column>
                </el-table-column>
              </el-table>

              <pagination
                v-show="total>0"
                :total="total"
                :page.sync="queryParams.pageNum"
                :limit.sync="queryParams.pageSize"
                @pagination="getList"
              />
              <br/>
              <br/>

              </el-tab-pane>
        </el-tabs>
        </el-col>


      </el-row>
    </div>

  </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {getRecordList, getRecordList2, getRecordList3, getTree} from "@/api/estate/manage/meter/record";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "record",
  components: {Treeselect},
  data(){
    return{
      selectDate:'',
      selectDate2:'',
      selectDate3:'',
      activeName: 'first',
      // 树筛选文本
      filterText:'',
      openView:false,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      meterReadingTypeOptions0: [],
      meterReadingTypeOptions1: [],
      yearOptions:[],
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      total2: 0,
      total3: 0,
      // 表格数据
      recordList: [],
      recordList2: [],
      recordList3: [],
      treeOptions: [],
      //树结构
      comOptions:[],
      defaultProps: {
        children: "communityId",
        label: "communityName"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: null,
        selectDate: '',
        address:null,
        ownerName : null,
        roomId : null,
        flag : null
      },
      queryParams2: {
        pageNum: 1,
        pageSize: 10,
        communityId: null,
        selectDate: '',
        ownerName : null,
        roomId : null,
        flag : null
      },
      queryParams3: {
        pageNum: 1,
        pageSize: 10,
        communityId: null,
        selectDate: '',
        address:null,
        ownerName : null,
        roomId : null,
        flag : null
      },
      treeQueryParams:{
        communityId :null
      },
      // 表单参数
      form: {},
    }
  },

  created() {
    this.getMeterReadingTypeOptions0("0");
    this.getMeterReadingTypeOptions1("1");
    this.getTreeSelect();
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
      this.form.meterReadingType = undefined;
      this.getDicts("meter_reading_type").then(response => {
        var meterReadingTypeOptions = response.data;
        this.meterReadingTypeOptions0=meterReadingTypeOptions.filter(item => item.remark == energyConsumptionType);
      });
    },
    //电表
    getMeterReadingTypeOptions1(energyConsumptionType) {
      this.queryParams.meterReadingType = undefined;
      this.form.meterReadingType = undefined;
      this.getDicts("meter_reading_type").then(response => {
        var meterReadingTypeOptions = response.data;
        this.meterReadingTypeOptions1=meterReadingTypeOptions.filter(item => item.remark == energyConsumptionType);
      });
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    handleQuery2() {
      this.getList2();
    },
    handleQuery3() {
      this.getList3();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams.ownerName = null;
      this.queryParams2.ownerName = null;
      this.queryParams3.ownerName = null;
      this.selectDate = '';
      this.selectDate = '';
      this.selectDate = '';
      this.queryParams.roomId = null;
      this.queryParams.flag = null;
      this.queryParams2.roomId = null;
      this.queryParams2.flag = null;
      this.queryParams3.roomId = null;
      this.queryParams3.flag = null;
      this.resetForm("queryForm");
      this.handleQuery();
      this.handleQuery2();
      this.handleQuery3();
      this.getList();
      this.getList2();
      this.getList3()
    },

    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.communityId = data.communityId;
      this.queryParams2.communityId = data.communityId;
      this.queryParams3.communityId = data.communityId;
      this.treeQueryParams.communityId = data.communityId;
      this.getList();
      this.getList2();
      this.getList3();
      this.getTree();
    },
    /** 树结构 */
    getTreeSelect() {

      getCurrentList().then(response => {
        this.comOptions = response.data;
        this.queryParams.communityId = this.comOptions[0].communityId;
        this.queryParams2.communityId = this.comOptions[0].communityId;
        this.queryParams3.communityId = this.comOptions[0].communityId;
        this.treeQueryParams.communityId = this.comOptions[0].communityId;
        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.comOptions[0].communityId);
        });

        this.getList();
        this.getList2();
        this.getList3();
        this.getTree();
      });
    },

    /** 查询列表 */
    getList() {
      this.openView = 0;
      if (this.selectDate != ''){
        var year = this.selectDate.getFullYear();
        this.queryParams.selectDate = year;
      }else {
        this.queryParams.selectDate = new Date().getFullYear();
      }
      getRecordList(this.queryParams).then(response => {
        this.recordList = response.rows;
        this.total = response.total;
        this.loading = false;
      });

    },
    getList2(){
      if (this.selectDate2 != ''){
        var year = this.selectDate2.getFullYear();
        this.queryParams2.selectDate = year;
      }
      else {
        this.queryParams2.selectDate = new Date().getFullYear();
      }
      getRecordList2(this.queryParams2).then(response => {
        this.recordList2 = response.rows;
        this.total2 = response.total;
      });
    },

    getList3(){
      if (this.selectDate3 != ''){
        var year = this.selectDate3.getFullYear();
        this.queryParams3.selectDate = year;
      }else {
        this.queryParams3.selectDate = new Date().getFullYear();
      }
      getRecordList3(this.queryParams3).then(response => {
        this.recordList3 = response.rows;
        this.total3 = response.total;
      });
    },
    /** 导出按钮操作 */
    handleExport() {
      this.download('estate/record/export', {
        ...this.queryParams
      }, `导出数据.xlsx`)
    },
    /** 导出按钮操作 */
    handleExport2() {
      this.download('estate/record/export2', {
        ...this.queryParams2
      }, `导出数据.xlsx`)
    },
    /** 导出按钮操作 */
    handleExport3() {
      this.download('estate/record/export3', {
        ...this.queryParams3
      }, `导出数据.xlsx`)
    },

    getTree(){
      getTree(this.treeQueryParams).then(response => {
        this.treeOptions = response.data;
      });
    },
    handleSelect(node) {
      if (node.flag == 0){
        this.queryParams.flag = 0;
      }else if (node.flag == 1){
        this.queryParams.flag = 1;
      }else if(node.flag == 2){
        this.queryParams.flag = 2;
      }
    },
    handleSelect2(node) {
      if (node.flag == 0){
        this.queryParams2.flag = 0;
      }else if (node.flag == 1){
        this.queryParams2.flag = 1;
      }else if(node.flag == 2){
        this.queryParams2.flag = 2;
      }
    },
    handleSelect3(node) {
      if (node.flag == 0){
        this.queryParams3.flag = 0;
      }else if (node.flag == 1){
        this.queryParams3.flag = 1;
      }else if(node.flag == 2){
        this.queryParams3.flag = 2;
      }
    }
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

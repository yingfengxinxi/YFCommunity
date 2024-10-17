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
              default-expand-all
              :filter-node-method="filterNode"
              icon-class="el-icon-star-on"
              @node-click="handleNodeClick"
              ref="tree">
            </el-tree>
          </div>
        </el-col>
        <!--   右侧日历   -->
        <el-col :span="8">
        <div class="con">
          <div class="now-data-myself">
            <div class="now-data-myself-time">{{ date }}</div>
            <div class="now-data-myself-week">{{ week }}</div>
          </div>
          <Calendar
            v-on:choseDay="clickDay"
            v-on:changeMonth="changeDate"
            v-on:isToday="clickToday"
          ></Calendar>
        </div>
        </el-col>
        <!--   右侧卡片   -->
        <el-col :span="7" class="c_view">
          <div v-if="manageList.length > 0">
            <el-card  class="box-card right-card" shadow="always" :class="item.actState=='未开始'?'state1':item.actState=='已结束'?'state2':'state3'" v-for="item in manageList" >
              <div class="text item" @click="handleSwitch(item.activityId)" >
                <div>活动标题：{{ item.activityName }}</div>
                <div>活动状态：{{ item.actState }}</div>
                <div>活动时间：{{ item.startTime}}</div>
                <div v-if="item.endTime != null">结束时间：{{ item.endTime }}</div>
                <div>活动地点：{{ item.address }}</div>
              </div>
            </el-card>
          </div>
          <div v-else>
            <el-result  icon="info" subTitle="当日暂无活动！"/>
          </div>
        </el-col>
      </el-row>
    </div>
    <div v-show="openView === 1">
      <el-tabs v-model="activeName">
      <el-tab-pane label="活动信息" name="first">
        <el-button size="middle" @click="returnbefore" style="float:right;">关 闭</el-button>
        <h4 class="form-header" style="color: #0558e7">活动基本信息</h4>
        <div style="display: flex">
          <el-form ref="form" :model="form" label-width="180px">
                <el-row>
                  <el-col :span="6">
                    <el-form-item label="活动头图：">
                      <img :src="this.form.actUrl" class="image"/>
                    </el-form-item>
                  </el-col>
                  
                </el-row>
                <el-row>
                  <el-col :span="10">
                    <el-form-item label="活动名称：">
                      {{ form.activityName }}
                    </el-form-item>
                  </el-col>
                  <el-col :span="10">
                    <el-form-item label="所属小区：">
                      {{ form.communityName }}
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="10">
                    <el-form-item label="活动开始时间：">
                      {{ form.startTime }}
                    </el-form-item>
                  </el-col>
                  <el-col :span="10">
                    <el-form-item label="活动结束时间：">
                      {{ form.endTime }}
                    </el-form-item>
                  </el-col>
                  
                </el-row>
                <el-row>
                  <el-col :span="10">
                    <el-form-item label="活动创建时间：">
                      {{ form.createTime }}
                    </el-form-item>
                  </el-col>
                  <el-col :span="10">
                    <el-form-item label="活动报名截止时间：">
                      {{ form.closeTime }}
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="10">
                    <el-form-item label="活动范围：">
                      {{ form.actRang==0?'全体业主':'部分栋楼' }}
                    </el-form-item>
                  </el-col>
                  <el-col :span="10">
                    <el-form-item label="选择楼栋：" v-if="form.actRang==1">
                      {{ getBuildingName(form.communityId,form.buildingIds) }}
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="10">
                    <el-form-item label="活动地点：">
                      {{ form.address }}
                    </el-form-item>
                  </el-col>
                  <el-col :span="10">
                    <el-form-item label="活动报名人数：">
                      {{ form.joinNum }}
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="7">
                    <el-form-item label="发布人：">
                      {{ form.createBy }}
                    </el-form-item>
                  </el-col>
                  <el-col :span="7">
                    <el-form-item label="活动状态：">
                      <div >{{ form.actState }}</div>
                    </el-form-item>
                  </el-col>
                  <el-col :span="7">
                    <el-form-item label="活动费用：">
                      <div >{{ form.actAmount }}</div>
                    </el-form-item>
                  </el-col>
                </el-row>
                <el-row>
                  <el-col :span="24">
                    <el-form-item label="活动说明：">
                      <div v-html="form.actIntro">
                      </div>
                    </el-form-item>
                  </el-col>
                </el-row>
            </el-form>
        </div>
      </el-tab-pane>
      </el-tabs>
    </div>
  </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {getActiviteInfo, listAllManage} from "@/api/estate/activity/activiteManage";
import Calendar from 'vue-calendar-component';
import {parseTime} from "@/utils/global";
import ImageUpload from "@/components/ImageUpload";

export default {
  name: "activiteView",
  components: {
    Calendar,
    ImageUpload
  },
  data(){
    return{
      BASE_API: process.env.VUE_APP_BASE_API,
      date: "",
      state:0,
      state1:1,
      week: "",
      openView:false,
      // 遮罩层
      loading: true,
      activeName: '',
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 表格数据
      activiteList: [],
      classifyList:[],
      manageList:[],
      //小区数据集合
      currentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 树筛选文本
      filterText:'',
      //树结构
      comOptions:[],
      activityId:null,
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        activityName:null,
        typeId: undefined,
        typeName: undefined,
        activityId:null,
      },
      // 表单参数
      form: {},
      supplyList:[],
    }
  },
  created() {
    this.openView = 0 ;
    var now = new Date();
    this.date = now.getDate();//得到日期
    var day = now.getDay();//得到周几
    var arr_week = new Array("星期日", "星期一", "星期二", "星期三", "星期四", "星期五", "星期六");
    this.week = arr_week[day];
    this.getTree();
    this.queryParams.startTime = parseTime(now,'{y}-{m}-{d}');
    this.getList();
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
    handleNodeClick(row){
      this.queryParams.communityId = row.communityId
      this.form.communityId = row.communityId
      this.getList();
    },
    // 获取树数据
    getTree(){
      getCurrentList().then(res =>{
        this.currentList = res.data;
      })
    },
    // 查询列表
    /*getList() {
      // this.openView = 0;
      listManage(this.queryParams).then(response => {
        this.manageList = response.rows;
        this.total = response.total;
        this.openView = 0;
      });
    },*/
    getList() {
      // this.openView = 0;
      listAllManage(this.queryParams).then(response => {
        this.manageList = response.data;
        this.total = response.total;
        this.openView = 0;
      });
    },
    // 日历
    clickDay(data) {
      this.queryParams.startTime = parseTime(data,'{y}-{m}-{d}'); //选中某天
      this.getList();
    },
    changeDate(data) {
    },
    clickToday(data) {
    },
    // 返回
    returnbefore(){
      this.openView = 0;
    },
    // 卡片点击
    handleSwitch(activityId){
      this.openView = 1;
      this.activeName = 'first';
      getActiviteInfo(activityId).then(resp => {
        this.form = resp.data
      });
      this.queryParams.activityId=activityId;

    },
  }
}
</script>

<style scoped>
.c_view{
  height: 800px;
  overflow-y: auto;
  border: 1px solid #ccc;
  padding: 20px;
  margin-left: 100px;
  width: 330px;
}
！.el-card > .box-card{
  display: none;
  magin:30px  !important;

}
.state1{
  border-left: 6px solid #66ff00;
}
.state2{
  border-left: 6px solid #ff0000;

}
.state3{
  border-left: 6px solid #ff9900;
}

.right-card {
  margin-bottom: 10px;
}

.box-card{
  box-shadow: 2px 2px 2px #ccc;
}
.now-data-myself {
  width: 40%;
  position: absolute;
  border-right: 1px solid rgba(227, 227, 227, 0.6);
}
.con {
  position: relative;
  max-width: 280px;
  margin:0 30px;


}

.con >>> .wh_content_all {
  background: transparent !important;
  width: 350px;
}
>>> .wh_top_changge li {
  color: #F56C6C !important;
  font-size: 15px !important;
}
>>> .wh_content_item, .wh_content_item_tag {
  color: #303133 !important;
}
>>> .wh_content_item .wh_isToday {
  background: #00d985  !important;
  color: #fff  !important;
}
>>> .wh_content_item .wh_chose_day {
  background: #409EFF  !important;
  color: #ffff  !important;
}
>>> .wh_item_date:hover {
  background: rgb(217, 236, 255) !important;
  border-radius: 100px !important;
  color: rgb(102, 177, 255)  !important;
}
>>> .wh_jiantou1[data-v-2ebcbc83] {
  border-top: 2px solid #909399;
  border-left: 2px solid #909399;
  width: 7px;
  height: 7px;
}
>>> .wh_jiantou2[data-v-2ebcbc83] {
  border-top: 2px solid #909399;
  border-right: 2px solid #909399;
  width: 7px;
  height: 7px;
}
>>> .wh_top_tag[data-v-2ebcbc83] {
  color: #409EFF;
  border-top: 1px solid rgba(227, 227, 227, 0.6);
  border-bottom: 1px solid rgba(227, 227, 227, 0.6);
}
>>> .wh_container[data-v-2ebcbc83] {
  max-width: 280px;
}
>>> .wh_top_changge[data-v-2ebcbc83] {
  display: flex;
  width: 50%;
  margin-left: 43%;
}
.now-data-myself-time {
  color: #F56C6C;
  font-size: 28px;
  height: 30px;
  font-family: "Helvetica Neue";
}
.now-data-myself-week {
  font-size: 10px;
  color: #909399;
}
>>> .wh_top_changge .wh_content_li[data-v-2ebcbc83] {
  font-family: Helvetica;
}
>>> .now-data-myself[data-v-05c11b57] {
  width: 40%;
  position: absolute;
  border-right: none;
}
.image {
  height:200px;
  width: 200px
}
>>> .avatar {
  width: 100%;
  height: 100%;
}


</style>

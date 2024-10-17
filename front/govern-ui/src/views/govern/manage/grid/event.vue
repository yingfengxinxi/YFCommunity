<template>
  <div>
    <!--搜索条件-->
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
      <el-form-item label="发布日期">
        <el-date-picker
          v-model="dateRange"
          style="width: 240px"
          value-format="yyyy-MM-dd"
          type="daterange"
          range-separator="-"
          start-placeholder="开始日期"
          end-placeholder="结束日期"
          @keyup.enter.native="getEventList"
        ></el-date-picker>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" icon="el-icon-search" v-hasPermi="['govern:grid:list']" size="mini"
                   @click="getEventList">搜 索
        </el-button>
        <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重 置</el-button>
      </el-form-item>
    </el-form>

    <!--功能 -->
    <el-row :gutter="10" class="mb8">
      <el-col :span="1.5">
        <el-button
          type="primary"
          plain
          icon="el-icon-plus"
          size="mini"
          v-hasPermi="['govern:grid:add']"
          @click="handleEventAdd"
        >新增
        </el-button>
      </el-col>

      <div style="margin-left: 92%">
        <el-button  @click="goBack">返回</el-button>
      </div>
    </el-row>

    <!--  事件列表  -->
    <div class="block" style="margin-top: 50px">
      <el-timeline>
        <el-timeline-item
          placement="top"
          v-for="(item,index) in eventList"
          :timestamp="item.timestamp">
          <el-card>
            <el-button
              size="small"
              type="text"
              icon="el-icon-delete-solid"
              style="float: right;padding: 20px 0;color: rgba(175,35,55,0.72)"
              v-hasPermi="['govern:grid:edit']"
              @click="handleDetail(index)"
            >删除
            </el-button>

            <el-button
              size="small"
              type="text"
              icon="el-icon-view"
              style="float: right;padding: 20px 5px"
              v-hasPermi="['govern:grid:edit']"
              @click="handleDetail(index)"
            >编辑
            </el-button>

           <h4>{{ item.label }}</h4>

            <el-button
              size="small"
              type="text"
              icon="el-icon-view"
              style="display: block"
              v-hasPermi="['govern:grid:edit']"
              @click="handleDetail(index)"
            >查看详情
            </el-button>
          </el-card>
        </el-timeline-item>
      </el-timeline>
    </div>

    <!--  添加   -->
    <el-dialog :title="title" :visible.sync="insertEventOpen" width="780px">
      <el-form :model="insertEventForm" :rules="rules" ref="insertOrEditForm" label-width="100px">
        <el-row>
          <el-col :span="22">
            <el-form-item label="事件标题:" prop="label">
              <el-input
                v-model="insertEventForm.label"
                placeholder="请输入事件标题"
                style="width: 500px"
              ></el-input>
            </el-form-item>
          </el-col>

          <el-col :span="22">
            <el-form-item label="事件内容:" prop="content">
              <editor v-model="insertEventForm.content" :min-height="192"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" v-hasPermi="['govern:grid:add']" @click="submitForm">确认</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>


  </div>
</template>

<script>
import { getEventList } from "@/api/govern/manage/grid";

export default {
  name: "event",
  data() {
    return {
      //是否打开添加页
      insertEventOpen: false,
      //事件添加表单
      insertEventForm: {},
      //搜索框是否展示
      showSearch: true,
      //查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityIds: [],
      },
      //弹框标题
      title:"添加事件",
      //事件列表
      eventList:[{timestamp:'2022-01-02',label:"关于加强瑶海疫情防控"},{timestamp:'2022-09-10',label:"八荒六合唯我独尊功"}],
      // 日期范围
      dateRange: [],
      //校验规则
      rules:{},
    }
  },
  methods: {
    //获取事件集合
    getEventList() {
      this.eventList=[];
      getEventList(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.eventList = response.rows;
        }
      );
    },

    //新增
    handleEventAdd(){
      this.insertEventForm={};
      this.insertEventOpen=true;
    },

    //查看详情
    handleDetail(index){
      this.title='详情展示'
      this.insertEventOpen=true;
      this.insertEventForm=this.eventList[index]
    },

    //提交
    submitForm(){

    },

    //取消新增
    cancel(){
      this.insertEventForm={};
        this.insertEventOpen=false;
        this.title='添加事件'
    },

    //回到主页
    goBack(){
      this.$emit("goBack")
    },

    resetQuery(){
      this.queryParams={};
      this.getEventList();
    },

  }
}
</script>

<style scoped>

</style>

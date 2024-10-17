<template>
  <div>
    <div v-show="checkView">
      <!--搜索条件-->
      <el-form
        :model="queryParams"
        ref="queryForm"
        size="small"
        :inline="true"
        v-show="showSearch"
      >
        <el-form-item label="选择日期">
          <el-date-picker
            v-model="queryParams.queryTime"
            type="date"
            value-format="yyyy-MM-dd"
            placeholder="选择日期"
          >
          </el-date-picker>
        </el-form-item>
        <el-form-item label="人员姓名" prop="userName">
          <el-input
            v-model="queryParams.userName"
            placeholder="请输入人员姓名"
            clearable
          />
        </el-form-item>
        <el-form-item label="小区名称:" prop="communityName">
          <el-select
            v-model="queryParams.communityId"
            placeholder="请选择要查询的小区名称"
          >
            <el-option
              v-for="item in communityList"
              :key="item.communityId"
              :label="item.communityName"
              :value="item.communityId"
            >
            </el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            v-hasPermi="['govern:path:list']"
            icon="el-icon-search"
            size="mini"
            @click="handleQuery"
            >搜 索
          </el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
            >重 置</el-button
          >
        </el-form-item>
      </el-form>

      <!--  主页表单  -->
      <el-table
        v-loading="loading"
        :data="tableList"
        row-key="bulletinId"
        style="padding-top: 20px"
      >
        <el-table-column
          label="姓名"
          align="center"
          key="userName"
          prop="userName"
        >
          <template slot-scope="scope">
            {{ handleName(scope.row.userName) }}
          </template>
        </el-table-column>
        <el-table-column label="头像" align="center" key="avatar" prop="avatar">
          <template slot-scope="scope">
            <el-image
              style="width: 80px; height: 80px; margin-bottom: -4px"
              :src="`${scope.row.avatar}`"
            >
            </el-image>
          </template>
        </el-table-column>
        <el-table-column
          label="所属小区"
          align="center"
          key="communityName"
          prop="communityName"
        />
        <el-table-column label="性别" align="center" key="gender" prop="gender">
          <template slot-scope="scope">
            {{
              scope.row.gender == 0 ? "男" : scope.row.gender == 1 ? "女" : ""
            }}
          </template>
        </el-table-column>
        <el-table-column label="年龄" align="center" key="age" prop="age">
          <template slot-scope="scope">
            {{ scope.row.age + "岁" }}
          </template>
        </el-table-column>
        <el-table-column
          label="识别方式"
          align="center"
          key="ioType"
          prop="ioType"
        >
          <template slot-scope="scope">
            {{ ioTypeList[scope.row.ioType] }}
          </template>
        </el-table-column>
        <el-table-column
          label="进入时间"
          align="center"
          key="discernTime"
          prop="discernTime"
        ></el-table-column>
        <el-table-column
          label="操作"
          align="center"
          width="160"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button
              size="small"
              type="text"
              icon="el-icon-edit"
              @click="handlePath(scope.row)"
              v-hasPermi="['govern:path:list']"
              >历史轨迹
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!--     分页操作-->
      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>

    <!-- 历史轨迹页 -->
    <div v-show="detailView">
      <el-form ref="detailForm" :model="detailForm" label-width="140px">
        <el-row :gutter="20">
          <el-col :span="3">
            <el-avatar
              shape="square"
              :size="200"
              fit="fit"
              :src="detailForm.avatar"
            ></el-avatar>
          </el-col>
          <el-col :span="5">
            <el-form-item label="姓 名:" style="padding-top: 20px">
              {{ detailForm.userName }}
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="性 别:" style="padding-top: 20px">
              {{ sex[detailForm.gender] }}
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="年 龄:" style="padding-top: 20px">
              {{ detailForm.age + "岁" }}
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="证件类型:" style="padding-top: 20px">
              {{ getDictLabel(detailForm.cardType) }}
            </el-form-item>
          </el-col>

          <el-col :span="5" style="padding-top: 20px">
            <el-form-item label="证件号码:">
              {{ detailForm.idCard }}
            </el-form-item>
          </el-col>

          <el-col :span="5" style="padding-top: 20px">
            <el-form-item label="联系电话:">
              {{ detailForm.phoneNumber }}
            </el-form-item>
          </el-col>

          <el-col :span="5" style="padding-top: 20px">
            <el-form-item label="所属小区:">
              {{ detailForm.communityName }}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <el-table
        v-loading="loading"
        :data="detailForm.userLogVOs"
        row-key="clueId"
        style="padding-top: 30px"
      >
        <el-table-column
          label="小区名称"
          align="center"
          key="communityName"
          prop="communityName"
        />
        <el-table-column
          label="出入时间"
          align="center"
          key="discernTime"
          prop="discernTime"
        ></el-table-column>
        <el-table-column
          label="设备名称"
          align="center"
          key="deviceName"
          prop="deviceName"
        ></el-table-column>
      </el-table>

      <div style="margin-left: 95%; margin-top: 50px">
        <el-button @click="backHome">返回</el-button>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getCommuntiyList,
  getList,
  getUserLogDetail,
} from "@/api/govern/manage/path";
import { desensitization, desensitizationName } from "@/utils/common";

export default {
  name: "user",
  dicts: ["sys_user_sex", "sys_certificate"],
  data() {
    return {
      //标签页名
      tableName: "first",
      //当前查询的表名
      currentTableName: "",
      //主页面是否展示
      checkView: true,
      //搜索框是否展示
      showSearch: true,
      //查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        tableIndex: -1,
        queryTime: "",
      },
      //总条目数
      total: 0,
      //主页面表单集合
      tableList: [],
      //加载状态
      loading: false,
      sex: {
        0: "男",
        1: "女",
        // "2": '未知',
      },
      //识别方式
      ioTypeList: {
        1: "人脸识别",
        2: "蓝牙开门",
        3: "安康码",
        4: "二维码",
        5: "社保卡",
      },
      //小区集合
      communityList: [],
      //历史轨迹页是否打开
      detailView: false,
      //历史轨迹表单
      detailForm: {},
    };
  },
  created() {
    this.getList();
    this.getCommuntiyList();
  },
  methods: {
    //条件查询
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },

    //查看历史轨迹
    handlePath(row) {
      this.checkView = false;
      this.detailView = true;
      let query = {
        idCard: row.idCard,
        logId: row.logId,
        currentTableName: this.currentTableName,
      };
      getUserLogDetail(query).then((res) => {
        this.detailForm = res.data;
      });
    },

    //回到主页
    backHome() {
      this.detailView = false;
      this.checkView = true;
    },

    //获取字典标签
    getDictLabel(cardType) {
      return this.selectDictLabel(this.dict.type.sys_certificate, cardType);
    },

    //获取小区集合
    getCommuntiyList() {
      getCommuntiyList().then((res) => {
        this.communityList = res.data;
      });
    },

    //姓名脱敏
    handleName(name) {
      return desensitizationName(name);
    },

    //脱敏身份证号
    handleCard(card) {
      return desensitization(card, 4, 14);
    },

    //重置搜索项
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams = {};
      this.handleQuery();
    },

    /** 查询列表 */
    getList() {
      this.loading = true;
      this.tableList = [];
      getList(this.queryParams).then((response) => {
        if (response.rows.length > 0) {
          this.tableList = response.rows[0].resultList;
          this.currentTableName = response.rows[0].tableName;
        }
        this.total = response.total;
        this.loading = false;
      });
    },
  },
};
</script>

<style scoped>
</style>

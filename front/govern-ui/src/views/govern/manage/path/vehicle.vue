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
        <el-form-item label="车牌号码" prop="plateNo">
          <el-input
            v-model="queryParams.plateNo"
            placeholder="请输入车牌号码"
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
          label="车辆号码"
          align="center"
          key="plateNo"
          prop="plateNo"
        />
        <el-table-column
          label="姓名"
          align="center"
          key="userName"
          prop="userName"
        >
          <template slot-scope="scope">
            {{
              scope.row.userName == "" ? "未知" : handleName(scope.row.userName)
            }}
          </template>
        </el-table-column>
        <el-table-column
          label="出入口名称"
          align="center"
          key="gateName"
          prop="gateName"
        />
        <el-table-column
          label="身份类型"
          align="center"
          key="userType"
          prop="userType"
        >
          <template slot-scope="scope">
            {{
              scope.row.userType == 0
                ? "业主"
                : scope.row.userType == 1
                ? "租客"
                : "游客"
            }}
          </template>
        </el-table-column>
        <el-table-column
          label="进出场发生时间"
          align="center"
          key="inoutTime"
          prop="inoutTime"
        ></el-table-column>
        <el-table-column
          label="进出场开门时间"
          align="center"
          key="inoutOpen"
          prop="inoutOpen"
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
              @click="handlePath(scope.row.plateNo)"
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
              v-show="detailForm.avatar"
              shape="square"
              :size="200"
              fit="fill"
              :src="detailForm.avatar"
            ></el-avatar>
            <el-image
              v-show="!detailForm.avatar"
              style="width: 180px; height: 180px"
            ></el-image>
          </el-col>
          <el-col :span="5">
            <el-form-item label="车牌号码:" style="padding-top: 20px">
              {{
                detailForm.plateNo == undefined ? "暂无" : detailForm.plateNo
              }}
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="车主姓名:" style="padding-top: 20px">
              {{
                detailForm.userName == undefined ? "暂无" : detailForm.userName
              }}
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="年 龄:" style="padding-top: 20px">
              {{ detailForm.age == undefined ? "暂无" : detailForm.age + "岁" }}
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="性 别:" style="padding-top: 20px">
              {{
                detailForm.gender == undefined ? "暂无" : sex[detailForm.gender]
              }}
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="证件类型:" style="padding-top: 20px">
              {{
                detailForm.cardType == undefined
                  ? "暂无"
                  : getDictLabel(detailForm.cardType)
              }}
            </el-form-item>
          </el-col>

          <el-col :span="5" style="padding-top: 20px">
            <el-form-item label="证件号码:">
              {{
                detailForm.idCardNo == undefined ? "暂无" : detailForm.idCardNo
              }}
            </el-form-item>
          </el-col>

          <el-col :span="5" style="padding-top: 20px">
            <el-form-item label="联系电话:">
              {{
                detailForm.phoneNumber == undefined
                  ? "暂无"
                  : detailForm.phoneNumber
              }}
            </el-form-item>
          </el-col>

          <el-col :span="5" style="padding-top: 20px">
            <el-form-item label="所属小区:">
              {{
                detailForm.communityName == undefined
                  ? "暂无"
                  : detailForm.communityName
              }}
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <el-table
        v-loading="loading"
        :data="detailForm.vehicleLogVOs"
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
          key="inoutTime"
          prop="inoutTime"
        ></el-table-column>
        <el-table-column
          label="出入口名称"
          align="center"
          key="gateName"
          prop="gateName"
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
  getVehicleList,
  getVehicleDetail,
} from "@/api/govern/manage/path";
import { desensitization, desensitizationName } from "@/utils/common";

export default {
  name: "vehicle",
  dicts: ["sys_certificate"],
  data() {
    return {
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
      //主页表单数据
      tableList: [],
      //加载状态
      loading: false,
      sex: {
        0: "男",
        1: "女",
        // "2": '未知',
      },
      //小区集合
      communityList: [],
      //历史轨迹页是否打开
      detailView: false,
      //历史轨迹表单
      detailForm: {},
      //身份证字典集合
      certificateOptions: [],
    };
  },
  methods: {
    //条件查询
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },

    //车辆进出记录历史轨迹初始化
    handlePath(plateNo) {
      this.checkView = false;
      this.detailView = true;
      getVehicleDetail({
        plateNo,
        currentTableName: this.currentTableName,
      }).then((res) => {
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
      getVehicleList(this.queryParams).then((response) => {
        if (response.rows.length != 0) {
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

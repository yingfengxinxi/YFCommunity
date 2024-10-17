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
        <el-form-item label="归档日期">
          <el-date-picker
            v-model="dateRange"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            @keyup.enter.native="handleQuery"
          ></el-date-picker>
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="handleQuery"
            >搜 索</el-button
          >
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
            >重 置</el-button
          >
        </el-form-item>

        <right-toolbar
          :showSearch.sync="showSearch"
          @queryTable="getList"
        ></right-toolbar>
      </el-form>

      <!--  主页表单  -->
      <el-table v-loading="loading" :data="archivedList" row-key="bulletinId">
        <el-table-column
          label="被举报人员"
          align="center"
          key="userName"
          prop="userName"
        >
          <template slot-scope="scope">
            {{ isManage ? scope.row.userName : handleName(scope.row.userName) }}
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
          label="犯罪级别"
          align="center"
          key="crimeLevel"
          prop="crimeLevel"
        >
          <template slot-scope="scope">
            {{ scope.row.crimeLevel + "级" }}
          </template>
        </el-table-column>
        <el-table-column
          label="证件类型"
          align="center"
          key="cardType"
          prop="cardType"
        >
          <template slot-scope="scope">
            {{ getDictLabel(scope.row.cardType) }}
          </template>
        </el-table-column>
        <el-table-column
          label="证件号码"
          align="center"
          key="cardNo"
          prop="cardNo"
        >
          <template slot-scope="scope">
            {{ handleCard(scope.row.cardNo) }}
          </template>
        </el-table-column>
        <el-table-column
          label="归档时间"
          align="center"
          key="updateTime"
          prop="updateTime"
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
              icon="el-icon-view"
              v-hasPermi="['govern:bulletin:list']"
              @click="handleDetail(scope.row.bulletinId)"
              >查看
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
    <!-- 详情页 -->
    <div v-show="detailView">
      <el-form ref="detailForm" :model="detailForm" label-width="140px">
        <el-row :gutter="20">
          <el-col :span="3">
            <el-image
              fit="fill"
              style="height: 200px; width: 200px"
              :src="detailForm.avatar"
            ></el-image>
          </el-col>
          <el-col :span="5">
            <el-form-item label="姓 名:">
              {{ detailForm.userName }}
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="性 别:">
              {{ sex[detailForm.gender] }}
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="年 龄:">
              {{ detailForm.userAge + "岁" }}
            </el-form-item>
          </el-col>
          <el-col :span="5">
            <el-form-item label="证件类型:">
              {{ getDictLabel(detailForm.cardType) }}
            </el-form-item>
          </el-col>

          <el-col :span="5" style="padding-top: 20px">
            <el-form-item label="证件号码:">
              {{ detailForm.cardNo }}
            </el-form-item>
          </el-col>

          <el-col :span="5" style="padding-top: 20px">
            <el-form-item label="犯罪级别:">
              {{ detailForm.crimeLevel + "级" }}
            </el-form-item>
          </el-col>

          <el-col :span="5" style="padding-top: 20px">
            <el-form-item label="发布时间:">
              {{ detailForm.createTime }}
            </el-form-item>
          </el-col>

          <el-col :span="5" style="padding-top: 20px">
            <el-form-item label="举报时间:">
              {{ detailForm.createTime }}
            </el-form-item>
          </el-col>

          <el-col :span="20" style="padding-top: 20px">
            <el-form-item label="通报原因:">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="handleCrimeReason"
                v-hasPermi="['govern:bulletin:list']"
                >查看
              </el-button>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>

      <el-table
        v-loading="loading"
        :data="detailForm.clueList"
        row-key="clueId"
        style="padding-top: 80px"
      >
        <el-table-column
          label="举报人员"
          align="center"
          key="accountName"
          prop="accountName"
        >
          <template slot-scope="scope">
            {{ handleName(scope.row.accountName) }}
          </template>
        </el-table-column>
        <el-table-column
          label="举报时间"
          align="center"
          key="createTime"
          prop="createTime"
        ></el-table-column>
        <el-table-column
          label="举报地点"
          align="center"
          key="address"
          prop="address"
        ></el-table-column>
        <el-table-column label="备注" align="center" key="remark" prop="remark">
          <template slot-scope="scope">
            <el-popover
              placement="top-start"
              title="备注内容"
              width="200"
              trigger="hover"
              :content="scope.row.remark"
            >
              <el-button type="text" slot="reference">查看</el-button>
            </el-popover>
          </template>
        </el-table-column>
      </el-table>

      <div style="margin-left: 95%; margin-top: 50px">
        <el-button @click="backHome">返回</el-button>
      </div>
    </div>

    <!--  通报内容   -->
    <el-dialog title="通报内容" :visible.sync="dialogFormVisible" width="700px">
      <editor v-model="detailForm.crimeReason" :min-height="192" />
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getArchiveDetail, getList } from "@/api/govern/security/bulletin";
import { desensitization, desensitizationName } from "@/utils/common";

export default {
  name: "archived",
  dicts: ["sys_certificate"],
  data() {
    return {
      //主页面是否展示
      checkView: true,
      //搜索框是否展示
      showSearch: true,
      //详情页是否展示
      detailView: false,
      //查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        archive: 1,
      },
      //总条目数
      total: 0,
      // 日期范围
      dateRange: [],
      //主页面表单集合
      archivedList: [],
      //加载状态
      loading: false,
      //添加表单是否展示
      dialogFormVisible: false,
      //添加或者修改的表单
      insertOrEditForm: {},
      //标题
      title: "新建信息",
      //详情表单
      detailForm: {},
      //是否是管理员
      isManage: false,
      sex: {
        0: "男",
        1: "女",
        2: "未知",
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    //条件查询
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },

    //查看方法
    handleDetail(bulletinId) {
      this.checkView = false;
      this.detailView = true;
      getArchiveDetail(bulletinId).then((res) => {
        this.detailForm = res.data;
      });
    },

    //取消添加
    cancel() {
      this.dialogFormVisible = false;
      this.insertOrEditForm = {};
    },

    //返回主界面
    backHome() {
      this.detailForm = {};
      this.detailView = false;
      this.checkView = true;
    },

    //重置搜索项
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },

    /** 查询列表 */
    getList() {
      this.loading = true;
      this.archivedList = [];
      getList(this.addDateRange(this.queryParams, this.dateRange)).then(
        (res) => {
          if (res.rows.length > 0) {
            this.archivedList = res.rows[0].resultList;
            this.isManage = res.rows[0].isManage;
            this.total = res.total;
          }
          this.loading = false;
        }
      );
    },

    //查看通告内容
    handleCrimeReason() {
      this.dialogFormVisible = true;
    },

    //获取字典标签
    getDictLabel(cardType) {
      return this.selectDictLabel(this.dict.type.sys_certificate, cardType);
    },

    //姓名脱敏
    handleName(name) {
      return desensitizationName(name);
    },

    //脱敏身份证号
    handleCard(card) {
      return desensitization(card, 4, 14);
    },
  },
};
</script>

<style scoped>
</style>


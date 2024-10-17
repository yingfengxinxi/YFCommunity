<template>
  <div class="app-container">
    <!-- 救助 list -->
    <el-row style="margin-left: 2%">
      <el-row>
        <el-form
          :model="queryParams"
          ref="queryForm"
          size="small"
          :inline="true"
        >
          <el-form-item label="发布日期" prop="time">
            <el-date-picker
              v-model="queryParams.time"
              type="daterange"
              range-separator="至"
              start-placeholder="开始日期"
              value-format="yyyy-MM-dd"
              end-placeholder="结束日期"
            >
            </el-date-picker>
          </el-form-item>
          <el-form-item label="发布人" prop="name">
            <el-input
              v-model="queryParams.name"
              placeholder="请输入"
              clearable
            />
          </el-form-item>
          <el-form-item label="小区：" prop="">
            <el-select
              v-model="queryParams.community_id"
              clearable
              @change="getList"
            >
              <el-option
                v-for="item in currentList"
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
              icon="el-icon-search"
              size="mini"
              @click="handleQuery"
              >搜索</el-button
            >
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
              >重置</el-button
            >
          </el-form-item>
        </el-form>
      </el-row>
      <el-row>
        <el-table v-loading="loading" :data="dataList" ref="multipleTable">
          <el-table-column
            label="标题"
            align="center"
            prop="rescue_title"
            show-overflow-tooltip
          />
          <el-table-column
            label="发布人"
            align="center"
            prop="user_name"
            show-overflow-tooltip
          />
          <el-table-column label="年龄" align="center" prop="age" />
          <el-table-column label="手机号" align="center" show-overflow-tooltip>
            <template slot-scope="scope">
              {{ hide(scope.row.phone, 3, 7) }}
            </template>
          </el-table-column>
          <el-table-column
            label="所在位置"
            align="center"
            prop="address"
            show-overflow-tooltip
          />
          <el-table-column label="是否有志愿者求助" align="center">
            <template v-slot="scope">
              <el-tag v-show="scope.row.num == '是'">{{
                scope.row.num
              }}</el-tag>
              <el-tag v-show="scope.row.num == '否'" type="danger">{{
                scope.row.num
              }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="状态" align="center">
            <template v-slot="scope">
              <el-tag v-show="scope.row.state == '进行中'">{{
                scope.row.state
              }}</el-tag>
              <el-tag v-show="scope.row.state == '未开始'" type="success">{{
                scope.row.state
              }}</el-tag>
              <el-tag v-show="scope.row.state == '已结束'" type="info">{{
                scope.row.state
              }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column
            label="需要人数"
            align="center"
            prop="need_num"
            show-overflow-tooltip
          />
          <el-table-column
            label="发布时间"
            align="center"
            width="180"
            show-overflow-tooltip
          >
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.release_time) }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="操作"
            align="center"
            class-name="small-padding fixed-width"
          >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-view"
                @click="
                  form = scope.row;
                  flag_view = true;
                "
                >查看</el-button
              >
            </template>
          </el-table-column>
        </el-table>

        <pagination
          v-show="total > 0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-row>
    </el-row>
    <!--  preview -->
    <div>
      <el-dialog title="" :visible.sync="flag_view" width="50%">
        <el-descriptions
          title="基本信息"
          :column="4"
          border
          direction="vertical"
          :label-style="{ 'line-height': '40px' }"
        >
          <el-descriptions-item label="标题" :span="4">{{
            form.rescue_title
          }}</el-descriptions-item>
          <el-descriptions-item label="所在位置" :span="4">
            {{ form.address }}
          </el-descriptions-item>
          <el-descriptions-item label="发布人">
            {{ form.user_name }}
          </el-descriptions-item>
          <el-descriptions-item label="性别">
            {{ form.gender == "0" ? "男" : form.gender == "1" ? "女" : "" }}
          </el-descriptions-item>
          <el-descriptions-item label="年龄">{{
            form.age
          }}</el-descriptions-item>
          <el-descriptions-item label="手机号">{{
            form.phone
          }}</el-descriptions-item>
          <el-descriptions-item label="证件号码">{{
            form.card_no
          }}</el-descriptions-item>
          <el-descriptions-item label="发布时间">{{
            parseTime(form.release_time)
          }}</el-descriptions-item>
          <el-descriptions-item label="需要人数">{{
            form.need_num
          }}</el-descriptions-item>
          <el-descriptions-item label="紧急联系人">{{
            form.contact_name
          }}</el-descriptions-item>
          <el-descriptions-item label="联系电话">{{
            form.contact_phone
          }}</el-descriptions-item>
          <el-descriptions-item label="状态">
            <el-tag v-show="form.state == '进行中'">{{ form.state }}</el-tag>
            <el-tag v-show="form.state == '未开始'" type="success">{{
              form.state
            }}</el-tag>
            <el-tag v-show="form.state == '已结束'" type="info">{{
              form.state
            }}</el-tag>
          </el-descriptions-item>
          <el-descriptions-item label="救助开始时间">{{
            parseTime(form.start_time)
          }}</el-descriptions-item>
          <el-descriptions-item label="救助结束时间">{{
            parseTime(form.end_time)
          }}</el-descriptions-item>
          <el-descriptions-item label="救助内容" :span="4">{{
            form.content
          }}</el-descriptions-item>
        </el-descriptions>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { getCurrentList } from "@/api/govern/community";
import { searchList } from "@/api/govern/voluntary/rescue";
import { desensitization } from "../../../../../estate-ui/src/utils/common";
export default {
  name: "rescue",
  data() {
    return {
      flag_view: false,
      dataList: [],
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        time: "",
        name: "",
        community_id: "",
      },
      currentList: [],
      // 总条数
      total: 0,
      form: {},
      // 遮罩层
      loading: true,
    };
  },
  mounted() {
    getCurrentList().then((e) => {
      const { data } = e;
      if (data) {
        this.currentList = data;
        this.queryParams["community_id"] = data[0].communityId;
        this.getList();
      }
    });
  },
  methods: {
    hide(str, begin, end) {
      if (str) {
        return desensitization(str, begin, end);
      }
      return "";
    },
    getList() {
      this.queryParams["dateTime"] = this.queryParams["time"] + "";
      searchList(this.queryParams).then((e) => {
        this.dataList = e.rows;
        this.total = e.total;
        this.loading = false;
      });
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
  },
};
</script>

<style scoped>
</style>

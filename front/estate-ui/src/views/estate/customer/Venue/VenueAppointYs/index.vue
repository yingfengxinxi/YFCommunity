<template>
  <div class="app-container">
    <el-row  :gutter="20">
      <el-col :span="3" :xs="24">
        <div class="head-container">
          <el-input v-model="filterText" placeholder="请输入小区名称" clearable size="small"
                    prefix-icon="el-icon-search" style="margin-bottom: 20px"/>
        </div>
        <div>
          <el-tree
            :data="deptData"
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
	   		          <i class="el-icon-office-building"/>
	   		          <span class="content" style="padding-left:5px;">{{ node.label }}</span>
		          </span>
          </el-tree>
        </div>
      </el-col>
      <el-col :span="20" :xs="24">
        <div v-show="openView === 0">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                   label-width="120px">
            <el-form-item label="预约人姓名" prop="userName">
              <el-input
                v-model="queryParams.userName"
                placeholder="请输入预约人姓名"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="预约人手机号" prop="userPhone">
              <el-input
                v-model="queryParams.userPhone"
                placeholder="请输入预约人手机号"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            <el-form-item label="场馆名称" prop="venueId">
              <el-select v-model="queryParams.venueId" placeholder="请选择场馆名称">
                <el-option
                  v-for="(item, index) in venueList"
                  :key="item.venueId"
                  :label="item.venueName"
                  :value="item.venueId"
                  @keyup.enter.native="handleQuery"
                ></el-option>
              </el-select>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>

          <el-table v-loading="loading" :data="appointList">
            <el-table-column type="selection" width="55" align="center"/>
<!--            <el-table-column label="主键" align="center" prop="appointId"/>-->
            <el-table-column label="场馆名称" align="center" prop="venueName"
                             min-width="120" :show-overflow-tooltip="true"/>
            <el-table-column label="预约人姓名" align="center" prop="userName"/>
            <el-table-column label="预约人手机号" align="center" prop="userPhone"
                             min-width="120" :show-overflow-tooltip="true"/>
            <!--            <el-table-column label="活动" align="center" prop="actName" />-->
            <el-table-column label="活动名称" align="center" prop="activityName"
                             min-width="120" :show-overflow-tooltip="true"/>
            <el-table-column label="终止时间" align="center" prop="updateTime" width="180"></el-table-column>
            <el-table-column label="是否退款" align="center" prop="appointStatus">
              <template slot-scope="scope">
                <span v-if="scope.row.appointStatus == '0'">未支付</span>
                <span v-else-if="scope.row.appointStatus == '1'">已支付</span>
                <span v-else-if="scope.row.appointStatus == '2'">已退款</span>
                <span v-else-if="scope.row.appointStatus == '3'">无需退款</span>
                <span v-else>其他</span>
              </template>
            </el-table-column>
            <el-table-column label="退款金额" align="center" prop="refundAmount"/>
            <el-table-column label="操作时间" align="center" prop="updateTime" width="180"></el-table-column>
            <el-table-column label="操作人" align="center" prop="updateBy"/>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="look(scope.row.appointId)"
                >查看
                </el-button>
              </template>
            </el-table-column>
          </el-table>

          <pagination
            v-show="total>0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
          />

        </div>

        <div v-show="openView === 1">
          <h1>申请详情</h1>
          <hr>
          <el-form>

            <el-row type="flex" class="row-bg" justify="space-around">
              <el-col :span="8">
                <el-form-item label="预约场馆">
                  <span>{{ lsjlList.venueName }}</span>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="预约人">
                  <span>{{ lsjlList.userName }}</span>
                </el-form-item>
              </el-col>
              <el-col :span="8">
                <el-form-item label="联系方式">
                  <span>{{ lsjlList.userPhone }}</span>
                </el-form-item>
              </el-col>
            </el-row>

            <el-row justify="space-around">
              <el-col :span="8">
                <el-form-item label="身份证">
                  <span>{{ lsjlList.cardNo }}</span>
                </el-form-item>
              </el-col>

              <el-col :span="8">
                <el-form-item label="申请时间">
                  <span>{{ lsjlList.updateTime }}</span>
                </el-form-item>
              </el-col>

              <el-col :span="8">
              <el-form-item label="退款原因">
                <span>{{ lsjlList.refundReason }}</span>
              </el-form-item>
              </el-col>

            </el-row>


            <el-row justify="space-around">
              <el-col :span="8">
                <el-form-item label="活动名称">
                  <span>{{ lsjlList.activityName }}</span>
                </el-form-item>
              </el-col>
            </el-row>


            <el-row type="flex" class="row-bg" justify="space-around">
              <el-col :span="24">
                <el-form-item label="备注">
                  <span>{{ lsjlList.remark }}</span>
                </el-form-item>
              </el-col>
            </el-row>

          </el-form>

          <div>
            <el-button type="primary" @click="cancel">返 回</el-button>
          </div>
        </div>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {
  listAppoint,
  venueaz, venueazById
} from "@/api/estate/customer/Venue/VenueAppoint";
import {addrule, gl, updaterule} from "@/api/estate/customer/Venue/VenueRule";
import {getCurrentList} from "@/api/estate/community";
import {listVenue, venueList} from "@/api/estate/customer/Venue/Venue";

export default {
  name: "Appoint",
  dicts: ['pay_way'],
  data() {
    return {
      defaultProps: {
        children: "communityId",
        label: "communityName"
      },
      //小区数据集合
      currentList: [],
      //场馆集合
      venueList: [],
      //活动集合
      venueactList: [],
      lsjlList: [],
      // 遮罩层
      loading: true,
      // 小区筛选条件
      filterText: '',
      openView: 0,

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
      // 场馆活动表格数据
      appointList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      deptData: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        userName: null,
        userPhone: null,
        venueId: null,
      },
      currentListProps1: {
        children: "communityId",
        label: "communityName"
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {}
    };
  },
  created() {
    this.getList();
    this.getCurrent();
    this.getTypeList();

  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.communityId = data.communityId;
      this.communityId = data.communityId;
      this.getList();
    },
    // 机构下拉框
    getTypeList() {
      getCurrentList().then((response) => {
        this.deptData = response.data;
        this.queryParams.communityId = this.deptData[0].communityId;
        this.communityIds = this.deptData[0].communityId;
        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.deptData[0].communityId);
        });
        this.getList();
      });
    },
    getVenueList() {

      venueList(this.queryParams).then(response => {
        this.venueList = response.data;
      });
    },
    getList() {
      this.loading = true;
      venueaz(this.queryParams).then(response => {
        this.appointList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
      this.getVenueList();
    },
    look(row) {
      venueazById(row).then(res => {
        this.lsjlList = res.data
      })
      this.openView = 1
    },
    cancel() {
      this.openView = 0
      this.form = {}
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    getCurrent() {
      getCurrentList().then(res => {
        this.currentList = res.data;
      })
    },

    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },

    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
  }
}
</script>

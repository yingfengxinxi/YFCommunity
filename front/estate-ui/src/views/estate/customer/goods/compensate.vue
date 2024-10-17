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
          <div class="head-container">
            <el-tree
              :data="comOptions"
              :props="defaultProps"
              :expand-on-click-node="false"
              :filter-node-method="filterNode"
              ref="tree"
              highlight-current
              node-key="communityId"
              accordion
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
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
            <el-form-item label="物品编号" prop="goodsNo">
              <el-input
                v-model="queryParams.goodsNo"
                placeholder="请输入物品编号"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="物品名称" prop="goodsName">
              <el-input
                v-model="queryParams.goodsName"
                placeholder="请输入物品名称"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            <el-form-item label="赔偿方式" prop="compensateWay">
              <el-select v-model="queryParams.compensateWay" clearable>
                <el-option key="0" label="现金" value="0"></el-option>
                <el-option key="1" label="物品" value="1"></el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="赔偿人" prop="accountName">
              <el-input
                v-model="queryParams.accountName"
                placeholder="请输入赔偿人姓名"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>

          <el-table v-loading="loading" :data="compensateList" >
            <el-table-column label="物品编号" align="center" prop="goodsNo" />
            <el-table-column label="物品名称" align="center" prop="goodsName" />
            <el-table-column label="物品价格" align="center" prop="goodsPrice" />
            <el-table-column label="赔偿人" align="center" prop="accountName" />
            <el-table-column label="物品价格" align="center" prop="goodsPrice" />
            <el-table-column label="赔偿方式" align="center" prop="compensateWay" >
              <template slot-scope="scope">
                <div v-if="scope.row.compensateWay == '0'">现金</div>
                <div v-else-if="scope.row.compensateWay == '1'">物品</div>
              </template>
            </el-table-column>
            <el-table-column label="赔偿时间" align="center" prop="compensateTime" />
          </el-table>

          <pagination
            v-show="total>0"
            :total="total"
            :page.sync="queryParams.pageNum"
            :limit.sync="queryParams.pageSize"
            @pagination="getList"
          />
        </el-col>
      </el-row>
    </div>

  </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {getGoodsInfo,} from "@/api/estate/customer/goods/goods";
import {getAllClassifyList} from "@/api/estate/customer/goods/classify";
import {getCompensateList} from "@/api/estate/customer/goods/compensate";

export default {
  name: "compensate",
  data(){
    return{
      // 树筛选文本
      filterText:'',
      openView:false,
      // 遮罩层
      loading: true,
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
      goodsList: [],
      classifyList:[],
      compensateList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //树结构
      comOptions:[],
      borrowVos:[],
      defaultProps: {
        children: "communityId",
        label: "communityName"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: null,
        goodsName:null,
        goodsNo:null,
        accountName:null,
        compensateWay: null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        classifyName:[
          { required: true, message: "分类信息不能为空", trigger: "blur" }
        ],
        remark: [
          { required: true, message: "说明不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getTreeSelect();
    // this.getList();
    this.getAllClassifyList();
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
    handleNodeClick(data) {
      this.queryParams.communityId = data.communityId;
      // this.handleQuery();
      this.getList();
    },
    /** 树结构 */
    getTreeSelect() {
      getCurrentList().then(response => {
        this.comOptions = response.data;
        this.queryParams.communityId = this.comOptions[0].communityId;

        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.comOptions[0].communityId);
        });

        this.getList();
      });
    },
    // 取消按钮
    cancel() {
      this.openView = 0;
      this.reset();
    },

    /** 表单重置*/
    reset() {
      this.form = {
        goodsName: undefined,
        address: undefined,
        classifyId: undefined,
        borrowStatus: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
      // this.getTreeSelect();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 查询机构列表 */
    getAllClassifyList() {
      getAllClassifyList(this.queryParams).then(response => {
          this.classifyList = response.data;
        }
      );
    },
    /** 查询列表 */
    getList() {
      this.openView = 0;
      this.loading = false;
      getCompensateList(this.queryParams).then(response => {
        this.compensateList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查看详情*/
    handleGoods(row){
      const goodsId = row.goodsId;
      getGoodsInfo(goodsId).then(response => {
        this.form = response.data;
        this.borrowVos = this.form.borrowVos;
        this.openView = 1;
      });
    }
  }
}
</script>

<style scoped>
.image {
  height:200px;
  width: 200px
}
/*设置高亮颜色*/
/deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #b3f1b6 !important;
}
.content {
  font-size: 14px;
}
</style>

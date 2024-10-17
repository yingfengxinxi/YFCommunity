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
            <el-form-item label="出门标题" prop="outTitle">
              <el-input
                v-model="queryParams.outTitle"
                placeholder="请输入出门标题"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="提交人" prop="accountName">
              <el-input
                v-model="queryParams.accountName"
                placeholder="请输入提交人"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            <el-form-item label="手机号" prop="loginName">
              <el-input
                v-model="queryParams.loginName"
                placeholder="请输入手机号"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            <el-form-item label="出门状态" prop="outStatus">
              <el-select v-model="queryParams.outStatus" placeholder="请选择出门状态" style="width: 180px">
                <el-option label="未出门" value="0"/>
                <el-option label="已出门" value="1"/>
              </el-select>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>

          <el-table v-loading="loading" :data="goodsOutList" >
            <el-table-column label="出门标题" align="center" prop="outTitle" show-overflow-tooltip/>
            <el-table-column label="提交人" align="center" prop="accountName" />
            <el-table-column label="手机号" align="center" :formatter="loginNameFormat"/>
            <el-table-column label="出门地点" align="center" prop="passageName" show-overflow-tooltip>
              <template slot-scope="scope">
                <span>{{ scope.row.passageName === null ? '-' : scope.row.passageName }}</span>
              </template>
            </el-table-column>
            <el-table-column label="提交时间" align="center" prop="submitTime" />
            <el-table-column label="出门时间" align="center">
              <template slot-scope="scope">
                <span>{{ scope.row.outTime === null ? '-' : scope.row.outTime }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作人" align="center" prop="staffName" />
            <el-table-column label="出门状态" align="center" prop="outStatus" >
              <template slot-scope="scope">
                <el-tag v-if="scope.row.outStatus == '0'" type="warning">未出门</el-tag>
                <el-tag v-else-if="scope.row.outStatus == '1'" type="success">已出门</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180px">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="handleGoodsOut(scope.row)"
                >查看
                </el-button>

                <el-button
                  v-if="scope.row.outStatus === '0'"
                  size="mini"
                  type="text"
                  icon="el-icon-key"
                  style="color: green"
                  @click="handleOut(scope.row)"
                  v-hasPermi="['business:goodsOut:update']"
                >出门</el-button>

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
        </el-col>
      </el-row>
    </div>

    <!--  查看  -->
    <div v-show="openView === 1">
      <h4 class="form-header" style="color: #0558e7">物品详情</h4>
      <div style="display: flex">
        <el-form ref="form" :model="form" label-width="180px">
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="出门标题：" >
                <div style="width: 180px">{{ form.outTitle}}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="提交人：">
                <div style="width: 180px">{{ form.accountName }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="手机号：">
                <div style="width: 180px">{{ form.loginName }}</div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="出门状态：" prop="doctorName">
                <div style="width: 180px">
                  <div v-if="form.outStatus == '0'">未出门</div>
                  <div v-if="form.outStatus == '1'">已出门</div>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="出门地点：">
                <div style="width: 180px">{{ form.passageName }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="提交时间：">
                <div style="width: 180px">{{ form.submitTime }}</div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="出门时间：" >
                <div style="width: 180px">{{ form.outTime}}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="出门说明：" >
                <div style="width: 180px">{{ form.remark}}</div>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>

      </div>

      <h4 class="form-header" style="color: #0558e7">物品图片</h4>
      <div style="display: flex">
        <el-form ref="form" :model="form" label-width="180px">
          <el-row>
            <el-col :span="24" :offset="4">
              <div class="images">
                <div v-for="(item, index) in goodsOutImages" :key="index" class="image-middle">
                  <el-popover placement="top-start" trigger="focus">
                    <img :src="goodsOutImages[index].imageUrl" slot="reference" class="image"/>
                    <div style="width: 250px; height: 250px">
                      <img :src="goodsOutImages[index].imageUrl" class="imagePreview"/>
                    </div>
                  </el-popover>
                </div>
              </div>
            </el-col>

          </el-row>
        </el-form>

      </div>
      <h4 class="form-header" style="color: #0558e7"/>
      <br/>
      <br/>
      <div slot="footer" class="dialog-footer">
        <el-row>
          <el-col :offset="20" :span="2">
            <el-button @click="cancel">取消</el-button>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" @click="cancel">确 定</el-button>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {getGoodsOutList, getInfo, getPassageList, updateGoodsOutStatus} from "@/api/estate/customer/goods/goodsOut";
import {desensitization} from "../../../../../../govern-ui/src/utils/common";

export default {
  name: "goodsOut",
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
      goodsOutList:[],
      goodsOutImages:[],
      passageList:[],
      // 弹出层标题
      title: "",
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
        outTitle:null,
        accountName:null,
        loginName:null,
        outStatus:null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        passageId:[
          { required: true, message: "出入口不能为空", trigger: "blur" }
        ],
      }
    }
  },
  created() {
    this.getTreeSelect();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods:{
    loginNameFormat(row){
      return desensitization(row.loginName, 3, 7);
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.communityId = data.communityId;
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
    /** 查询列表 */
    getList() {
      this.openView = 0;
      this.loading = false;
      getGoodsOutList(this.queryParams).then(response => {
        this.goodsOutList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查看详情*/
    handleGoodsOut(row){
      getInfo(row.outId).then(response => {
        this.form = response.data;
        this.goodsOutImages = response.data.images;
        this.openView = 1;
      });
    },

    /** 出门*/
    handleOut(row) {
      this.$modal.confirm('是否确认出门?').then(function() {
        return updateGoodsOutStatus(row.outId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("出门成功");
      }).catch(() => {});
    },

  }
}
</script>

<style scoped>
.image {
  height:200px;
  width: 200px
}

/* 图片总布局，样式 */
.images {
  display: flex;
  margin-top: 20px;
  margin-left: 20px;
  margin-right: 20px;
  flex-wrap: wrap;
}

/* 图片之间 */
.image-middle {
  margin-right: 15px;
  margin-bottom: 15px;
}
/*设置高亮颜色*/
/deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #b3f1b6 !important;
}
.content {
  font-size: 14px;
}
</style>

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

            <el-form-item label="审批状态" prop="auditStatus">
              <el-select
                v-model="queryParams.auditStatus"
                placeholder="请选择审批状态"
                clearable
                size="small"
                style="width: 240px">
                <el-option label="待审核" value="0"></el-option>
                <el-option label="已驳回" value="1"></el-option>
                <el-option label="已通过" value="2"></el-option>
              </el-select>
            </el-form-item>

<!--            <el-form-item label="存放地址" prop="address">
              <el-input
                v-model="queryParams.address"
                placeholder="请输入存放地址"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>-->

            <el-form-item label="分类信息" prop="classifyId">
              <el-select v-model="queryParams.classifyId" placeholder="请选择分类信息">
                <el-option
                  v-for="item in classifyList"
                  :key="item.classifyId"
                  :label="item.classifyName"
                  :value="item.classifyId">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>

          <el-table v-loading="loading" :data="borrowList" >
            <el-table-column label="物品编号" align="center" prop="goodsNo" />
            <el-table-column label="物品名称" align="center" prop="goodsName" show-overflow-tooltip/>
            <el-table-column label="存放地址" align="center" prop="address" show-overflow-tooltip/>
            <el-table-column label="分类信息" align="center" prop="classifyName" show-overflow-tooltip/>
            <el-table-column label="借用人" align="center" prop="accountName" />
            <el-table-column label="申请时间" align="center" prop="applyTime" width="180px"/>
            <el-table-column label="预计归还时间" align="center" prop="returnTime" width="180px"/>
            <el-table-column label="借用时长(天)" align="center" prop="borrowDays" />
            <el-table-column label="审批状态" align="center" prop="auditStatus">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.auditStatus == '0'" type="primary">待审核</el-tag>
                <el-tag v-else-if="scope.row.auditStatus == '1'" type="danger">已驳回</el-tag>
                <el-tag v-else-if="scope.row.auditStatus == '2'" type="success">已通过</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width" width="180px">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="handleBorrow(scope.row)"
                >查看
                </el-button>

                <el-button
                  v-if="scope.row.auditStatus == '0'"
                  size="mini"
                  type="text"
                  icon="el-icon-check"
                  style="color: green"
                  @click="handlePass(scope.row)"
                  v-hasPermi="['business:borrow:update']"
                >通过</el-button>

                <el-button
                  v-if="scope.row.auditStatus == '0'"
                  size="mini"
                  type="text"
                  icon="el-icon-close"
                  style="color:red"
                  @click="checkNo(scope.row)"
                  v-hasPermi="['business:borrow:update']"
                >驳回</el-button>

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
      <el-form ref="form" :model="form" label-width="180px">
        <el-row>
          <el-col :span="12">
            <el-row>
              <el-col :span="11" :offset="1">
                <el-form-item label="物品编号：" prop="goodsNo">
                  <div style="width: 180px">{{ form.goodsNo}}</div>
                </el-form-item>
              </el-col>
              <el-col :span="11" :offset="1">
                <el-form-item label="物品名称：">
                  <div style="width: 180px">{{ form.goodsName }}</div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="11" :offset="1">
                <el-form-item label="分类信息：">
                  <div style="width: 180px">{{ form.classifyName }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="11" :offset="1">
                <el-form-item label="物品价格：" prop="goodsPrice">
                  <div style="width: 180px">{{ form.goodsPrice}}</div>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="11" :offset="1">
                <el-form-item label="所属小区：">
                  <div style="width: 180px">{{ form.communityName }}</div>
                </el-form-item>
              </el-col>
              <el-col :span="11" :offset="1">
                <el-form-item label="存放地址：">
                  <div style="width: 180px">{{ form.address }}</div>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
          <el-col :span="12">
            <el-form-item label="" prop="goodsUrl">
              <img :src="this.form.goodsUrl" class="image"/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <h4 class="form-header" style="color: #0558e7" >借用详情</h4>
      <div style="display: flex" >
        <el-form ref="form" :model="form" label-width="180px">
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="借用人：" >
                <div style="width: 180px">{{ form.ownerName}}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="手机号：">
                <div style="width: 180px">{{ form.ownerPhone }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="所属楼栋：">
                <div style="width: 180px">{{ form.buildingName }}</div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="单元号：" >
                <div style="width: 180px">{{ form.unitName}}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="门牌号：">
                <div style="width: 180px">{{ form.roomName }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="历史借用次数：">
                <div style="width: 180px">{{ form.borrowCount }}</div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>

            <el-col :span="6" :offset="1">
              <el-form-item label="借用开始时间：">
                <div style="width: 180px">{{ form.applyTime }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="预计归还时间：">
                <div style="width: 180px">{{ form.returnTime }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="实际归还时间：">
                <div style="width: 180px">{{ form.actuallyTime }}</div>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <h4 class="form-header" style="color: #0558e7">审批详情</h4>
      <div style="display: flex">
        <el-form ref="form" :model="form" label-width="180px">
          <el-row>
            <el-col :span="5" :offset="1">
              <el-form-item label="申请类别：" >
                <div style="width: 180px">物品借用</div>
              </el-form-item>
            </el-col>
            <el-col :span="5" :offset="1">
              <el-form-item label="审批状态：">
                <div style="width: 180px">
                  <div v-if="form.auditStatus == '0'">待审核</div>
                  <div v-if="form.auditStatus == '1'">已驳回</div>
                  <div v-if="form.auditStatus == '2'">已通过</div>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="5" :offset="1">
              <el-form-item label="审批人：">
                <div style="width: 180px">
                  <div v-if="form.auditStatus == '0'">-</div>
                  <div v-else> {{ form.auditBy }}</div>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="5" :offset="1">
              <el-form-item label="审批时间：">
                <div style="width: 180px">
                  <div v-if="form.auditStatus == '0'">-</div>
                  <div v-if="form.auditStatus != '0'"> {{ form.auditTime}}</div>
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row v-show="form.auditStatus == '1'">
            <el-col :offset="1">
              <el-form-item label="驳回原因：" >{{ form.rejectReason}}</el-form-item>
            </el-col>
          </el-row>
        </el-form>

      </div>

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

    <!-- 驳回理由 -->
    <el-dialog :title="title" :visible.sync="showReason" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="驳回理由" prop="rejectReason">
          <el-input onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" type="textarea" :rows="5" v-model="form.rejectReason" placeholder="请输入驳回理由"
                    style="width: 100%;"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel">取 消</el-button>
        <el-button type="primary" @click="handleCheckNo(form)">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {aduitBorrow, getBorrowList, getInfo} from "@/api/estate/customer/goods/borrow";
import {getAllClassifyList} from "@/api/estate/customer/goods/classify";
import {getGoodsInfo} from "@/api/estate/customer/goods/goods";

export default {
  name: "borrow",
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
      borrowList: [],
      classifyList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      //树结构
      comOptions:[],
      borrowVos:[],
      //是否弹出输入驳回理由
      showReason: false,
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
        address:null,
        classifyId:null,
        borrowStatus:null,
        goodsNo:null,
        auditStatus: null
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
      this.showReason = false;
      this.reset();
    },

    /** 表单重置*/
    reset() {
      this.form = {
        goodsName: undefined,
        address: undefined,
        classifyId: undefined,
        borrowStatus: undefined,
        goodsNo:undefined
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
      getBorrowList(this.queryParams).then(response => {
        this.borrowList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查看详情*/
    handleBorrow(row){
      getInfo(row.borrowId).then(response => {
        this.form = response.data;
        this.openView = 1;
      });
    },

    /** 通过按钮操作 */
    handlePass(row) {
      getGoodsInfo(row.goodsId).then(response => {
        // 通过前校验物品是否已被借出
        if (1 == response.data.borrowStatus){
          this.$modal.msgError("物品已被借出,通过失败");
        }else {
          const borrowId = row.borrowId;
          this.$confirm('是否确认通过物品编号为"' + row.goodsNo + '"的数据项?', "警告", {
            confirmButtonText: "确定",
            cancelButtonText: "取消",
            type: "warning"
          }).then(function() {
            const data = {
              borrowId : borrowId,
              auditStatus : 2,
              communityId : row.communityId,
              goodsId : row.goodsId
            };
            return aduitBorrow(data);
          }).then(() => {
            this.getList();
            this.msgSuccess("通过成功");
          }).catch(() => {});
        }
      });
    },

    /**驳回审核弹框*/
    checkNo(row) {
      this.showReason = true;
      this.form = row;
      this.title = "填写驳回理由";
    },

    /**驳回审核*/
    handleCheckNo(row) {
      if (null == this.form.rejectReason){
        this.$modal.msgError("不通过理由不能为空");
      }else {
        const data = {
          borrowId : row.borrowId,
          auditStatus : 1,
          rejectReason: this.form.rejectReason
        };
        aduitBorrow(data).then(response => {
          this.cancel();
          this.showReason = false;
          this.getList();
          this.msgSuccess("驳回成功");
        });
      }

    },
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

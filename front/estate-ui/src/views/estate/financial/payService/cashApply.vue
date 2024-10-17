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
        <!--   右侧   -->
        <el-col :span="20" :xs="24">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                   label-width="68px">
            <el-form-item label="问卷名称" prop="voteTitle">
              <el-input
                  v-model="queryParams.voteTitle"
                  placeholder="请输入问卷名称"
                  clearable
                  @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="发起人" prop="ownerName">
              <el-input
                  v-model="queryParams.ownerName"
                  placeholder="请输入发起人"
                  clearable
                  @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="申请结果" prop="auditStatus">
              <el-select v-model="queryParams.auditStatus" placeholder="请选择申请结果" style="width: 180px">
                <el-option label="审核中" value="0"/>
                <el-option label="通过" value="1"/>
                <el-option label="驳回" value="2"/>
              </el-select>
            </el-form-item>
            <el-form-item label="创建时间" prop="applyTime">
              <el-date-picker
                  v-model="queryParams.applyTime"
                  value-format="yyyy-MM-dd HH:mm:ss"
                  type="date"
                  placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item label="类型" prop="voteType">
              <el-select v-model="queryParams.voteType" placeholder="请选择类型" style="width: 180px">
                <el-option label="住宅专项维修基金" value="0"/>
                <el-option label="公共维修基金" value="1"/>
              </el-select>
            </el-form-item>
            <el-form-item label="年份" prop="yearVo">
              <el-date-picker
                  v-model="queryParams.yearVo"
                  value-format="yyyy"
                  type="year"
                  placeholder="选择日期">
              </el-date-picker>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" float=right @click="resetQuery">重置</el-button>
            </el-form-item>
            <el-row :span="24">
              <el-col :span="8">
                <div class="first" style="">{{ form.cashCountNow }}
                  <div style="color: #2a6496">年累计金额(元)</div>
                </div>
              </el-col>
              <el-col :span="8">
                <div class="second" style="">{{ form.cashCountLast }}
                  <div style="color: #2a6496">去年累计金额(元)</div>
                </div>
              </el-col>
              <el-col :span="8">
                <div class="third" style="">{{ form.cashCount }}
                  <div style="color: #2a6496">总累计金额(元)</div>
                </div>
              </el-col>
            </el-row>

          </el-form>
          <el-table v-loading="loading" :data="cashApplyList">
            <el-table-column label="投票名称" align="center" prop="voteTitle" show-overflow-tooltip/>
            <el-table-column label="发起人" align="center" prop="ownerName"/>
            <el-table-column label="同意率" align="center" prop="agreeRate"/>
            <el-table-column label="类型" align="center" prop="voteType">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.voteType == '0'">住宅专项维修基金</el-tag>
                <el-tag v-else-if="scope.row.voteType == '1'">公共维修基金</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="投票开始时间" align="center" prop="startTime"/>
            <el-table-column label="投票结束时间" align="center" prop="entTime"/>
            <el-table-column label="申请结果" align="center" prop="auditStatus">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.auditStatus == '0'" type="primary">待审核</el-tag>
                <el-tag v-else-if="scope.row.auditStatus == '2'" type="danger">已驳回</el-tag>
                <el-tag v-else-if="scope.row.auditStatus == '1'" type="success">已通过</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="创建时间" align="center" prop="applyTime"/>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="handleUp2(scope.row)"
                    v-hasPermi="['business:cashApply:check']"
                >查看
                </el-button>
                <el-button
                    v-if="scope.row.hasFinish"
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="handleBulletin(scope.row)"
                    v-hasPermi="['business:cashApply:bulletin']"
                >发布
                </el-button>
                <el-button
                  :disabled="scope.row.finish=='1'"
                    v-if="scope.row.auditStatus == '0'"
                    size="mini"
                    type="text"
                    icon="el-icon-check"
                    style="color: green"
                    @click="handlePass(scope.row)"
                    v-hasPermi="['business:cashApply:pass']"
                >通过
                </el-button>

                <el-button
                  :disabled="scope.row.finish=='1'"
                    v-if="scope.row.auditStatus == '0'"
                    size="mini"
                    type="text"
                    icon="el-icon-close"
                    style="color:red"
                    @click="checkNo(scope.row)"
                    v-hasPermi="['business:cashApply:pass']"
                >驳回
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
        </el-col>
      </el-row>
    </div>
    <!-- 第一次查看跳转 -->
    <div v-show="openView === 1">
      <h4 class="form-header" style="color: #0558e7">投票信息</h4>
      <div>
        <el-form ref="form1" :model="form1" label-width="180px">
          <el-row>
            <el-col :span="6">
              <el-form-item label="问卷名称：">
                {{ form1.voteTitle }}
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="发起人：">
                {{ form1.ownerName }}
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="楼栋范围：">
                {{ form1.buildingName }}
              </el-form-item>
            </el-col>

            <el-col :span="6">
              <el-form-item label="单元范围：">
                {{ form1.unitName }}
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6">
              <el-form-item label="申请结果：">
                <div style="width: 180px">
                  <div v-if="form1.auditStatus == '0'">待审核</div>
                  <div v-if="form1.auditStatus == '2'">已驳回</div>
                  <div v-if="form1.auditStatus == '1'">已通过</div>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="投票开始时间：" prop="doctorName">
                <div style="width: 180px">{{ form1.startTime }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="投票结束时间：">
                <div style="width: 180px">{{ form1.entTime }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6">
              <el-form-item label="问卷内容：">
                <div style="width: 180px">{{ form1.content }}</div>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>

      </div>
      <el-row>
        <h4 class="form-header" style="color: #0558e7">业主详情</h4>
        <el-col :span="20" :xs="24">
          <el-form :model="queryParams1" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                   label-width="68px">
            <el-form-item label="业主姓名" prop="ownerName">
              <el-input
                  v-model="queryParams1.ownerName"
                  placeholder="请输入业主姓名"
                  clearable
                  @keyup.enter.native="handleQuery1"
              />
            </el-form-item>
            <el-form-item label="手机号" prop="ownerPhone">
              <el-input
                  v-model="queryParams1.ownerPhone"
                  placeholder="请输入手机号"
                  clearable
                  @keyup.enter.native="handleQuery1"
              />
            </el-form-item>
            <el-form-item label="投票结果" prop="voteStatus">
              <el-select v-model="queryParams1.voteStatus" placeholder="请选择投票结果" style="width: 180px">
                <el-option label="同意" value="0"/>
                <el-option label="不同意" value="1"/>
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery1">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" float=right @click="resetQuery1">重置</el-button>
            </el-form-item>
          </el-form>
          <el-col :span="1.5">
            <el-button
                type="warning"
                plain
                icon="el-icon-download"
                size="mini"
                @click="handleExport"
                v-hasPermi="['business:cashApply:upload']"
            >导出
            </el-button>
          </el-col>
          <el-table v-loading="loading" :data="ownerVoteList">
            <el-table-column label="业主名称" align="center" prop="ownerName"/>
            <el-table-column label="手机号" align="center" prop="ownerPhone"/>
            <el-table-column label="投票结果" align="center" prop="voteStatus">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.voteStatus == '0'" type="primary">同意</el-tag>
                <el-tag v-else-if="scope.row.voteStatus == '1'" type="danger">不同意</el-tag>

              </template>
            </el-table-column>
            <el-table-column label="投票时间" align="center" prop="voteTime"/>
          </el-table>
        </el-col>

        <el-col>
          <div>
            <el-button
                type="primary"
                plain
                icon="el-icon-add"
                size="mini"
                @click="returnBefore"
            >返回
            </el-button>
          </div>
        </el-col>
      </el-row>

    </div>
    <!-- 驳回理由 -->
    <el-dialog :title="title" :visible.sync="showReason" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="50px">
        <el-form-item label="驳回理由" prop="rejectReason">
          <el-input onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" type="textarea" :rows="5"
                    v-model="form.rejectReason" placeholder="请输入驳回理由"
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
import {addActivite, listManage, removeActivite, updateActivite} from "@/api/estate/activity/activiteType";
import {
  aduitUpdata,
  bulletin,
  cashApplyList,
  getInfoById,
  ownerVoteList,
  voteInfoById
} from "@/api/estate/financial/cashApply";
import {aduitBorrow} from "@/api/estate/customer/goods/borrow";
import {getGoodsInfo} from "@/api/estate/customer/goods/goods";

export default {
  name: "cashApply",
  data() {
    return {
      showReason: false,
      openView: false,
      // 遮罩层
      loading: true,
      // 表单校验
      rules: {typeName: [{required: true, message: '类别名不能为空', trigger: 'blur'}],},
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
      cashApplyList: [],
      ownerVoteList: [],
      //小区数据集合
      currentList: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 树筛选文本
      filterText: '',
      voteId: '',
      //树结构
      comOptions: [],
      communityId: null,
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        activityName: null,
        typeId: undefined,
        typeName: undefined,
        applyTime: undefined
      },
      queryParams1: {
        ownerName: undefined,
        ownerPhone: undefined,
        voteStatus: undefined,
      },
      // 表单参数
      form: {},
      form1: {},
    }
  },
  created() {
    // this.getList();
    this.getTree();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(row) {
      this.queryParams.communityId = row.communityId
      this.communityId = row.communityId
      this.getList();
    },
    // 获取树数据
    getTree() {
      getCurrentList().then(res => {
        this.currentList = res.data;
        this.queryParams.communityId = this.currentList[0].communityId;
        this.communityId = this.currentList[0].communityId;
        this.getList();
      })
    },
    // 取消按钮
    cancel() {
      this.openView = 0;
      this.showReason = false;
      this.reset();
    },
    //返回
    returnBefore() {
      this.openView = 0;
      this.getList();
    },
    // 查看详情
    handleUp2(row) {
      this.openView = 1;
      this.queryParams.voteId = row.voteId;
      ownerVoteList(this.queryParams).then(response => {
        this.ownerVoteList = response.list;
      });
      voteInfoById(row.voteId).then(response => {
        this.form1 = response.data
      })
      this.voteId = row.voteId;
    },
    /**驳回审核弹框*/
    checkNo(row) {
      this.showReason = true;
      this.form = row;
      this.title = "填写驳回理由";
    },
    /**驳回审核*/
    handleCheckNo(row) {
      if (null == this.form.rejectReason) {
        this.$modal.msgError("不通过理由不能为空");
      } else {
        const data = {
          voteId: row.voteId,
          auditStatus: 2,
          rejectReason: this.form.rejectReason
        };
        aduitUpdata(data).then(response => {
          this.cancel();
          this.showReason = false;
          this.getList();
          this.$modal.msgSuccess("驳回成功");
        });
      }
    },
    /** 通过按钮操作 */
    handlePass(row) {
      const voteId = row.voteId;
      this.$confirm('是否确认通过该条申请?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(
          function () {
            const data = {
              voteId: voteId,
              auditStatus: 1,
            };
            return aduitUpdata(data);
          }).then(() => {
        this.getList();
        this.$modal.msgSuccess("通过成功");
      }).catch(() => {
      });
    },
    // 发布
    handleBulletin(row) {
      const voteId = row.voteId;
      this.$confirm('是否发布该基金投票?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(
          function () {
            const query = {
              voteId: voteId
            };
            return bulletin(query);
          }).then(() => {
            this.getList();
        this.$message({
          type: 'success',
          message: '发布成功!'
        });
      }).catch(() => {
      });
    },
    // 新增按钮
    handleAdd() {
      if (this.communityId == null) {
        this.$modal.msgError("请选择小区");
      } else {
        this.reset();
        this.open = true;
        this.title = "新增活动类型"
      }
    },
    /** 编辑按钮 */
    handleUpdate(row) {
      this.openView = 2;
    },
    /** 删除按钮 */
    handleDelete(row) {
      this.$modal.confirm('是否确认删除该数据项？').then(function () {
        return removeActivite(row.typeId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    /** 表单重置*/
    reset() {
      this.form = {
        typeId: undefined,
        typeName: undefined,
      };
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getTree();
    },
    /** 里面搜索按钮操作 */
    handleQuery1() {
      this.openView = 1;
      this.queryParams1.voteId = this.voteId;
      ownerVoteList(this.queryParams1).then(response => {
        this.ownerVoteList = response.list;
      });
      voteInfoById(this.voteId).then(response => {
        this.form1 = response.data
      })
    },
    /** 导出按钮操作 */
    handleExport() {
      this.$confirm('是否确认导出数据?', "导出确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "info"
      }).then(() => {
        this.download('/estate/cashapply/export', {
          ...this.queryParams
        }, `投票表_${new Date().getTime()}.xlsx`)
      }).catch(function (err) {
      })
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.queryParams = {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        communityName: undefined,
        activityName: null,
        typeId: undefined,
        typeName: undefined,
        submitDate: []
      }
      this.communityId = undefined;
      this.resetForm("queryForm");
      this.handleQuery();
    },
    resetQuery1() {
      this.queryParams1 = {
        ownerName: undefined,
        ownerPhone: undefined,
        voteStatus: undefined,
      }
      this.resetForm("queryForm1");
      this.handleQuery1();
    },
    /** 查询列表 */
    getList() {
      this.openView = 0;
      this.loading = false;
      this.queryParams.communityId = this.communityId;
      console.log(this.queryParams.communityId)
      getInfoById(this.queryParams).then(response => {
        this.form = response.data;
      });
      cashApplyList(this.queryParams).then(response => {
        this.cashApplyList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
  }
}
</script>

<style scoped>
.first {
  /*background: -webkit-linear-gradient(left,hsla(0,0%,59%,.15),hsla(0,0%,59%,0),white) no-repeat;*/
  background: linear-gradient(to top right, #16FFC7, #00A3FF);
  border-radius: 4px;
  height: 100px;
  width: 90%;
  font-size: 30px;
  color: #fff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.second {
  background: linear-gradient(to top right, #16FF8F, #FFE600);
  border-radius: 4px;
  height: 100px;
  width: 90%;
  font-size: 30px;
  color: #fff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

.third {
  background: linear-gradient(to top right, #FFE816, #FF7A00);
  border-radius: 4px;
  height: 100px;
  width: 90%;
  font-size: 30px;
  color: #fff;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
}

</style>

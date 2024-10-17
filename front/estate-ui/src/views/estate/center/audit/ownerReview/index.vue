<template>
  <div>
    <div v-show="zhuye == 0">
      <div class="app-container">
        <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                 label-width="68px">
          <el-form-item label="姓名" prop="ownerName">
            <el-input
              v-model="queryParams.ownerName"
              placeholder="请输入姓名"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="手机号码" prop="ownerPhone">
            <el-input
              v-model="queryParams.ownerPhone"
              placeholder="请输入手机号码"
              clearable
              style="width: 240px"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="小区名称" prop="communityId">
            <el-select v-model="queryParams.communityId" placeholder="请选择小区" clearable>
              <el-option
                v-for="d in communityList"
                :key="d.communityId"
                :label="d.communityName"
                :value="d.communityId"
              />
            </el-select>
          </el-form-item>
          <el-form-item label="审核状态" prop="auditStatus">
            <el-select v-model="queryParams.auditStatus" placeholder="审核状态" clearable>
              <el-option label="审核中" value="0"/>
              <!--          <el-option label="通过" value="1"/>-->
              <el-option label="驳回" value="2"/>
            </el-select>
          </el-form-item>
          <el-form-item label="审核时间">
            <el-date-picker
              v-model="dateRange"
              style="width: 240px"
              value-format="yyyy-MM-dd"
              type="daterange"
              range-separator="-"
              start-placeholder="开始日期"
              end-placeholder="结束日期"
            ></el-date-picker>
          </el-form-item>
          <el-form-item>
            <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
            <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
          </el-form-item>
        </el-form>

        <el-row :gutter="10" class="mb8">
          <!-- <el-col :span="1.5">
            <el-button
              type="danger"
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
            >删除
            </el-button>
          </el-col> -->
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>

        <el-table v-loading="loading" :data="configList" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center"/>
          <el-table-column label="姓名" align="center" prop="ownerName"/>
          <el-table-column label="手机号码" align="center" prop="ownerPhone"/>
          <el-table-column label="身份证号码" align="center" prop="cardNo"/>
          <el-table-column label="所属小区" align="center" prop="communityName"/>
          <el-table-column label="所属楼栋" align="center" prop="buildingName"/>
          <el-table-column label="所属单元" align="center" prop="unitName"/>
          <el-table-column label="所属房号" align="center" prop="roomName"/>
          <el-table-column label="与户主关系" align="center" prop="ownerRelation">
            <template slot-scope="scope">
              {{ getSysOldmanHuzhu(scope.row.ownerRelation) }}
              <!-- <div v-if="scope.row.ownerRelation==0">本人</div>
              <div v-if="scope.row.ownerRelation==1">配偶</div>
              <div v-if="scope.row.ownerRelation==2">父母</div>
              <div v-if="scope.row.ownerRelation==3">子女</div>
              <div v-if="scope.row.ownerRelation==4">其他</div>
              <div v-if="scope.row.ownerRelation==5">租客</div> -->
            </template>
          </el-table-column>
          <el-table-column label="提交时间" align="center" prop="createTime">
            <template slot-scope="scope">
              <span>{{ parseTime(scope.row.updateTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="审核时间" align="center" prop="auditTime">
            <template slot-scope="scope">
              <span v-if="scope.row.auditTime == null">-</span>
              <span else>{{ parseTime(scope.row.auditTime) }}</span>
            </template>
          </el-table-column>
          <el-table-column label="审核状态" align="center" prop="auditStatus">
            <template slot-scope="scope">
              <div v-if="scope.row.auditStatus==0">-</div>
              <div v-if="scope.row.auditStatus==2 || scope.row.auditStatus==3" style="color: red">驳回</div>
            </template>
          </el-table-column>
          <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
            <template slot-scope="scope" v-show="0">
              <div v-if="scope.row.auditStatus=='0'">
                <el-button
                  type="text"
                  icon="el-icon-check"
                  @click="handleUpdate(scope.row)"
                  style="color: lawngreen; font-size: 12px"
                >审核
                </el-button>
              </div>
              <div  style="color: red">
                <el-button
                  type="text"
                  icon="el-icon-view"
                  v-if="scope.row.auditStatus != '0'"
                  @click="handleUpdate(scope.row)"
                  style="font-size: 12px"
                >查看
                </el-button>
              </div>
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
    </div>
    <div v-if="zhuye==1">
      <el-form ref="form" :model="form" label-width="200px">
        <div>
          <h5 style="display: inline-block;margin-left:20px;font-size: 17px"><b>房产信息</b></h5>
          <el-button style="float: right; padding: 3px 25px;font-size: 15px;margin-right:10px;margin-top:20px"
                     type="primary" @click="back" plain>返回
          </el-button>
          <el-button
            style="float: right; padding: 3px 25px;font-size: 15px;color: lawngreen;margin-right:10px;margin-top:20px"
            type="success" plain v-show="chakanyushenhe" @click="pass(form)">通过
          </el-button>
          <el-button
            style="float: right; padding: 3px 25px;font-size: 15px;color: red;margin-right:10px;margin-top:20px"
            type="danger" plain v-show="chakanyushenhe" @click="repulse(form.ownerId)">驳回
          </el-button>
        </div>
        <el-divider></el-divider>
        <!-- 第一行 -->
        <el-row>

          <el-col :span="8">
            <el-form-item label="所属小区: ">
              <div style="width: 400px; overflow: hidden">
                {{ form.communityName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="所属楼栋: ">
              <div style="width: 400px; overflow: hidden">
                {{ form.buildingName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="所属单元: ">
              <div style="width: 280px; overflow: hidden">
                {{ form.unitName }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第二行 -->
        <el-row>
          <el-col :span="8">
            <el-form-item label="所属房间: ">
              <div style="width: 280px; overflow: hidden">
                {{ form.roomName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="房屋状态: ">
              <div style="width: 280px; overflow: hidden">
                <div v-if="form.roomStatus==0">未售</div>
                <div v-if="form.roomStatus==1">已售</div>
                <div v-if="form.roomStatus==2">入住</div>
                <div v-if="form.roomStatus==3">装修</div>
                <div v-if="form.roomStatus==4">已拿房</div>
                <div v-if="form.roomStatus==5">整租</div>
                <div v-if="form.roomStatus==6">合租</div>
                
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="户主姓名: " v-if="form.ownerRelation==0">
              {{ form.ownerName }}
            </el-form-item>
          </el-col>

        </el-row>
        <!-- 第三行 -->
        <el-row>
          <el-col :span="8">
            <el-form-item label="房屋坐落: ">
              <div style="width: 280px; overflow: hidden">
                {{ form.houseAddress }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="房屋属性: ">
              <div style="width: 280px; overflow: hidden">
                {{ getRoomTypeOptions(form.roomType) }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="房屋面积: ">
              {{ form.totalArea }}
            </el-form-item>
          </el-col>

        </el-row>
        <el-row>
          <el-col :span="8">
            <el-form-item label="所在层: ">
              {{ form.floor }}
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="交房时间: ">
              {{ form.deliveryTime }}
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
        <el-col :span="8">
            <el-form-item label="房产证明: ">
              <image-preview v-if="form.licenseUrl" v-for="image in form.licenseUrl.split(',')" :src="image" :width="50" :height="50" style="margin: 0 5px;"/>
              <!-- <el-image
                style="width: 100px; height: 100px"
                :src=""
                :preview-src-list="[form.licenseUrl]">
              </el-image> -->
            </el-form-item>
          </el-col>

          <el-col :span="8" v-if="form.auditStatus == 2 || form.auditStatus == 3">
            <el-form-item label="驳回原因: ">
              {{ form.rejectReason }}
            </el-form-item>
          </el-col>
        </el-row>


        <!-- 业主信息 -->
        <div>
          <h5 style="margin-left:20px;font-size: 17px"><b>业主基本信息</b></h5>
        </div>
        <el-divider></el-divider>
        <!-- 第一行 -->
        <el-row>
          <el-col :span="8">
            <el-form-item label="姓名: ">
              <div style="width: 400px; overflow: hidden">
                {{ form.ownerName }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="手机号: ">
              <div style="width: 400px; overflow: hidden">
                {{ form.ownerPhone }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="性别: ">
              <div style="width: 400px; overflow: hidden">
                <div v-if="form.gender==0">男</div>
                <div v-if="form.gender==1">女</div>
                <div v-if="form.gender==2">未知</div>
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <!-- 第二行 -->
        <el-row>
          <el-col :span="8">
            <el-form-item label="证件类型: ">
              {{ cartTypeFormat(form.cardType) }}
              <!--                <dict-tag :options="dict.type.sys_certificate" :value="form.cardType"/>-->
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="证件号码: ">
              <div style="width: 400px; overflow: hidden">
                {{ form.cardNo }}
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="与户主关系: ">
              <div style="width: 400px; overflow: hidden">
                {{ getSysOldmanHuzhu(form.ownerRelation) }}
                <!-- <div v-if="form.ownerRelation==0">本人</div>
                <div v-if="form.ownerRelation==1">配偶</div>
                <div v-if="form.ownerRelation==2">父母</div>
                <div v-if="form.ownerRelation==3">子女</div>
                <div v-if="form.ownerRelation==4">其他</div>
                <div v-if="form.ownerRelation==5">租客</div> -->
              </div>
            </el-form-item>
          </el-col>
        </el-row>


        <el-row>
          <el-col :span="8">
            <el-form-item label="证件正面: ">
              <image-preview :src="form.cardFont" :width="100" :height="100" />
              
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="证件反面: ">
              <image-preview :src="form.cardBack" :width="100" :height="100" />
            </el-form-item>
          </el-col>

        </el-row>

        <el-row>
          <el-col :span="8">
            <el-form-item label="政治面貌: ">
              <div style="width: 400px; overflow: hidden">
                  <div v-if="form.politicsStatus==1">党员</div>
                  <div v-if="form.politicsStatus==2">团员</div>
                  <div v-if="form.politicsStatus==3">其他</div>
              </div>
            </el-form-item>
          </el-col>
          <el-col :span="8">
            <el-form-item label="宗教信仰: ">
              <div style="width: 400px; overflow: hidden">
                {{ form.religion }}
              </div>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col >
            <el-form-item label="备注: ">
              
                {{ form.ownRemarks }}
              
            </el-form-item>
          </el-col>
          
        </el-row>
      </el-form>
    </div>

    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="原由：" prop="rejectReason">
          <el-input v-model="form.RejectReason" type="textarea" placeholder="请输入内容"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm(form)">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import {
  listConfig,
  listdict,
  getConfig,
  delConfig,
  passAndRepulse
} from "@/api/estate/center/audit/ownerReview/ownerReview";

export default {
  name: "OwnerReview",
  dicts: ['sys_yes_no', 'sys_certificate', 'sys_normal_disable','property_of_house','sys_oldman_huzhu'],
  data() {
    return {
      //驳回信息框
      open: false,
      //主页
      zhuye: 0,
      //查看与审核按钮开关
      chakanyushenhe: true,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      //下拉列表
      communityList: [],
      // 表单参数
      form: {},
      //业主信息
      zhuren: {},
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 参数表格数据
      configList: [],
      // 弹出层标题
      title: "",
      // 日期范围
      dateRange: [],
      //审核参数
      audit: [],
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        ownerName: undefined,
        ownerPhone: undefined,
        communityId: undefined,
        auditStatus: undefined
      },
      // 表单校验
      rules: {
        RejectReason: [
          {required: true, message: "驳回原由不能为空！", trigger: "blur"}
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getdictList();
  },
  methods: {
    cartTypeFormat(cardType) {
      return this.selectDictLabel(this.dict.type.sys_certificate, cardType);
    },
    getRoomTypeOptions(val) { 
      return this.selectDictLabel(this.dict.type.property_of_house, val);
    },
    getSysOldmanHuzhu(val) { 
      return this.selectDictLabel(this.dict.type.sys_oldman_huzhu, val);
    },
    /** 查询参数列表 */
    getList() {
      
      this.loading = true;
      listConfig(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.configList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },

    /** 查询下拉列表列表 */
    getdictList() {
      listdict().then(response => {
          this.communityList = response.data;
        }
      );
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.audit = [];
    },
    // 表单重置
    reset() {
      this.form = {
        configId: undefined,
        configName: undefined,
        configKey: undefined,
        configValue: undefined,
        configType: "Y",
        remark: undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.ownerId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ownerIds = row.ownerId || this.ids;
      this.$modal.confirm('是否确认删除参数编号为"' + ownerIds + '"的数据项？').then(function () {
        return delConfig(ownerIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
    //返回主页
    back() {
      this.zhuye = 0;
      this.chakanyushenhe = true;
    },
    //通过审核
    pass(form) {
      this.$confirm('是否通过该数据的审核!', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const auditStatus = '1';
        this.audit = {"auditStatus":auditStatus,"ownerId":form.ownerId,"roomId":form.roomId},
        passAndRepulse(this.audit).then(response => {
            this.zhuye = 0;
            this.audit = [];
            this.getList();
            this.$message({
              type: 'success',
              message: '审核通过!'
            });
          }
        )
      }).catch(() => {
      });
    },
    //驳回
    repulse(ownerId) {
      this.open = true;
      this.title = "驳回原由";
    },
    submitForm(form) {
      this.$refs["form"].validate(valid => {
        if (valid) {
          this.$confirm('是否确认驳回？', '提示', {
            confirmButtonText: '确定',
            cancelButtonText: '取消',
            type: 'warning'
          }).then(() => {
            const auditStatus = '2';
            const rejectReason = this.form.RejectReason;
            this.audit = {"auditStatus":auditStatus,"ownerId":form.ownerId,"rejectReason":rejectReason,"roomId":form.roomId},
              passAndRepulse(this.audit).then(res => { 
                this.form = {};
                this.open = false;
                this.zhuye = 0;
                this.getList();
                this.$message({
                  type: 'success',
                  message: '驳回成功!'
                });
              });
            
          }).catch(() => {
          });
        }
      });
    },
    //审核与查看
    handleUpdate(row) {
      this.reset();
      const ownerId = row.ownerId;
      this.chakanyushenhe = true;
      getConfig(ownerId,row.roomId+'').then(response => {
        this.form = response.data;
        if (this.form) { 
         
          if (response.data.auditStatus != '0') {
            //查看
            this.chakanyushenhe = false;
          }
          this.zhuye = 1;
        }
        
      });
    },
  }
};
</script>
<style scoped>
/deep/ .el-divider--horizontal {
  margin: -20px 0 30px 0 !important;
}
</style>

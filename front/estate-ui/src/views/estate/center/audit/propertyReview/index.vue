<template>
  <div>
  <div v-show="zhuye == 0">
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
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
      <el-form-item label="所属小区" prop="communityId">
        <el-select v-model="queryParams.communityId" placeholder="请选择所属小区" clearable>
          <el-option
            v-for="d in communityIds"
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
      <el-col :span="1.5">
        <el-button
          type="danger"
          icon="el-icon-delete"
          size="mini"
          :disabled="multiple"
          @click="handleDelete"
        >删除</el-button>
      </el-col>
      <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
    </el-row>

    <el-table v-loading="loading" :data="configList" @selection-change="handleSelectionChange">
      <el-table-column type="selection" width="55" align="center" />
      <el-table-column label="所属小区" align="center" prop="communityName" />
      <el-table-column label="所属楼栋" align="center" prop="buildingName"/>
      <el-table-column label="所属单元" align="center" prop="unitName"/>
      <el-table-column label="所属房号" align="center" prop="roomName"/>
      <el-table-column label="姓名" align="center" prop="ownerName" />
      <el-table-column label="手机号码" align="center" prop="ownerPhone" :show-overflow-tooltip="true" />
      <el-table-column label="身份证号码" align="center" prop="cardNo" :show-overflow-tooltip="true" />
      <el-table-column label="提交方式" align="center" prop="dataSource">
        <template slot-scope="scope">
          <div v-if="scope.row.dataSource==0">物业注册</div>
          <div v-if="scope.row.dataSource==1">手机注册</div>
        </template>
      </el-table-column>
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
          <span>{{ parseTime(scope.row.createTime) }}</span>
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
            <div v-if="scope.row.auditStatus==0" >-</div>
            <div v-if="scope.row.auditStatus==2" style="color: red">驳回</div>
          </template>
        </el-table-column>
      <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
        <template slot-scope="scope" v-show="0">
          <div v-show="scope.row.auditStatus==0" >
            <el-button
              type="text"
              icon="el-icon-check"
              @click="handleUpdate(scope.row)"
              style="color: lawngreen; font-size: 15px"
            >审核</el-button>
          </div>
          <div v-if="scope.row.auditStatus==2" style="color: red" v-show="scope.row.auditStatus==2">
            <el-button
              type="text"
              icon="el-icon-view"
              @click="handleUpdate(scope.row)"
              style="font-size: 15px"
            >查看</el-button>
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
            <el-button  style="float: right; padding: 3px 25px;font-size: 15px;margin-right:10px;margin-top:20px" type="primary" @click="back" plain>返回</el-button>
            <el-button  style="float: right; padding: 3px 25px;font-size: 15px;color: lawngreen;margin-right:10px;margin-top:20px" type="success" plain v-show="chakanyushenhe"  @click="pass(form)">通过</el-button>
            <el-button  style="float: right; padding: 3px 25px;font-size: 15px;color: red;margin-right:10px;margin-top:20px"  type="danger" plain  v-show="chakanyushenhe" @click="repulse">驳回</el-button>
          </div>
          <el-divider></el-divider>
          <!-- 第一行 -->
          <el-row>
            <el-col :span="8">
              <el-form-item label="与户主关系: ">
                <div>
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
            <el-col :span="8">
              <el-form-item label="所属小区: ">
                <div>
                  {{ form.communityName }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="所属楼栋: ">
                <div>
                  {{ form.buildingName }}
                </div>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 第二行 -->
          <el-row>
            <el-col :span="8">
              <el-form-item label="所属单元: ">
                <div>
                  {{ form.unitName }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="所属房间: ">
                <div>
                  {{ form.roomName }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="房屋状态: ">
                <div>
                  <div v-if="form.ownerRelation==0">在售</div>
                  <div v-if="form.ownerRelation==1">不可售</div>
                  <div v-if="form.ownerRelation==2">入住</div>
                  <div v-if="form.ownerRelation==3">装修</div>
                  <div v-if="form.ownerRelation==4">空关</div>
                  <div v-if="form.ownerRelation==5">整租</div>
                  <div v-if="form.ownerRelation==6">合租</div>
<!--                  <div v-if="form.ownerRelation==7">合租2</div>-->
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="房产证明: ">
                <el-image
                  :src="form.licenseUrl"
                  :preview-src-list="[form.licenseUrl]">
                </el-image>
              </el-form-item>
            </el-col>

            <el-col :span="8" v-if="form.auditStatus == 2">
              <el-form-item label="驳回原因: ">
                <el-input
                  type="textarea"
                  :rows="3"
                  disabled
                  placeholder="请输入内容"
                  v-model="form.rejectReason">
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>



          <!-- 车辆信息 -->
          <div>
            <h5 style="margin-left:20px;font-size: 17px"><b>业主基本信息</b></h5>
          </div>
          <el-divider></el-divider>
          <!-- 第一行 -->
          <el-row>
            <el-col :span="8">
              <el-form-item label="姓名: ">
                <div>
                  {{ form.ownerName }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="手机号: ">
                <div>
                  {{ form.ownerPhone }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="性别: ">
                <div>
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
<!--                {{form.cardType}}-->
                {{cartTypeFormat(form.cardType)}}
<!--                <dict-tag :options="dict.type.sys_certificate" :value="form.cardType"/>-->
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="证件号码: ">
                <div>
                  {{ form.cardNo }}
                </div>
              </el-form-item>
            </el-col>
          </el-row>


          <el-row>
            <el-col :span="8">
              <el-form-item label="证件正面: ">
                <div>
                  <div class="block">
                    <el-image :src="form.cardFont"></el-image>
                  </div>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="证件反面: ">
                <div>
                  <div class="block">
                    <el-image :src="form.cardBack"></el-image>
                  </div>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="个人照片: ">
                <div>
                  <div class="block">
                    <el-image :src="form.faceUrl"></el-image>
                  </div>
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
<!--
            <el-col :span="8">
              <el-form-item label="证件地祥址: ">
                <div style="width: 400px; overflow: hidden">
                  {{ form.domicileAddress }}
                </div>
              </el-form-item>
            </el-col>
-->
            <el-col :span="8">
              <el-form-item label="户籍详细地址: ">
                <div>
                  {{ form.domicileAddress }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="与户主关系: ">
                <div>
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


          <!-- 业主详细信息 -->
          <div>
            <h5 style="margin-left:20px;font-size: 17px"><b>业主详细信息</b></h5>
          </div>
          <el-divider></el-divider>
          <!-- 第一行 -->
          <el-row>
            <el-col :span="8">
              <el-form-item label="政治面貌: ">
                <div>
                  <div v-if="form.politicsStatus==1">党员</div>
                  <div v-if="form.politicsStatus==2">团员</div>
                  <div v-if="form.politicsStatus==3">其他</div>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="宗教信仰: ">
                <div>
                  {{ form.religion }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="籍贯: ">
                <div>
                  {{ form.nativePlace }}
                </div>
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 第二行 -->
          <el-row>
            <el-col :span="8">
              <el-form-item label="民族: ">
                <div>
                  {{ form.nation }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="文化程度: ">
                <div>
                  {{ form.eduLevel }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="兵役状况 ">
                <div>
                  {{ form.military }}
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="婚姻状况: ">
                <div>
                  <div v-if="form.marriageStatus==0">未婚</div>
                  <div v-if="form.marriageStatus==1">已婚</div>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="国籍: ">
                <div>
                  {{ form.nationality }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="人口类型: ">
                <div>
                  <div v-if="form.population==0">户籍人口</div>
                  <div v-if="form.population==1">常驻人口</div>
                  <div v-if="form.population==2">暂住人口</div>
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="职业: ">
                <div>
                  {{ form.career }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="数据来源: ">
                <div>
                  <div v-if="form.dataSource==0">物业注册</div>
                  <div v-if="form.dataSource==1">手机注册</div>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="QQ号: ">
                <div>
                  {{ form.qqNumber }}
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="邮箱: ">
                <div>
                  {{ form.email }}
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="个人介绍: ">
                <div>
                  <el-input
                    type="textarea"
                    placeholder="请输入内容"
                    v-model="form.selfIntro"
                    maxlength="200"
                    show-word-limit
                    disabled
                  >
                  </el-input>
                </div>
              </el-form-item>
            </el-col>
          </el-row>

        </el-form>
      </div>

    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form  ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="原由：" prop="RejectReason">
          <el-input v-model="form.RejectReason" type="textarea" placeholder="请输入内容" />
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
} from "@/api/estate/center/audit/propertyReview/propertyReview";

export default {
  name: "propertyReview",
  dicts: ['sys_yes_no','sys_certificate','sys_normal_disable','sys_oldman_huzhu'],
  data() {
    return {
      //驳回信息框
      open : false,
      //主页
      zhuye : 0,
      //查看与审核按钮开关
      chakanyushenhe:true,
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      //下拉列表
      communityIds:[],
      // 表单参数
      form: {},
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
      audit:[],
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
          { required: true, message: "驳回原由不能为空！", trigger: "blur" }
        ]
      }
    };
  },
  created() {
    this.getList();
    this.getdictList();
  },
  methods: {
    cartTypeFormat(cardType){
      return this.selectDictLabel(this.dict.type.sys_certificate,cardType);
    },
    getSysOldmanHuzhu(val){
      return this.selectDictLabel(this.dict.type.sys_oldman_huzhu,val);
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
      this.loading = true;
      listdict().then(response => {
          this.communityIds = response.data;
          this.loading = false;
        }
      );
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.audit=[];
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
      this.ids = selection.map(item => item.houseId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const houseIds = row.houseId || this.ids;
      this.$modal.confirm('是否确认删除参数编号为"' + houseIds + '"的数据项？').then(function() {
        return delConfig(houseIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    //返回主页
    back(){
      this.zhuye = 0;
      this.chakanyushenhe = true;
    },
    //通过审核
    pass(row){
      this.$confirm('是否通过该数据的审核!!', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const auditStatus = '1';
        const houseId = row.houseId;
        const roomId = row.roomId;
        this.audit = {houseId, auditStatus, roomId}
        passAndRepulse(this.audit).then(response => {
          this.zhuye = 0;
          this.audit=[];
          this.getList();
          this.$message({
            type: 'success',
            message: '审核成功'
          });
        }).catch(()=>{
        });
      }).catch(() => {});
    },
    //驳回
    repulse(){
      this.open = true;
      this.title = "驳回原由";
    },
 submitForm(row){
  this.$refs["form"].validate(valid => {
   if (valid) {
      this.$confirm('是否确认驳回？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const auditStatus = '2';
        const rejectReason = this.form.RejectReason;
        const houseId = row.houseId;
        const roomId = row.roomId;
        this.audit = {auditStatus,houseId,rejectReason, roomId},
        passAndRepulse(this.audit);
        this.form={};
        this.open = false;
        this.zhuye = 0;
        this.getList();
        this.$message({
          type: 'success',
          message: '驳回成功!'
        });
      }).catch(() => {});
        }
      });
    },
    //审核与查看
    handleUpdate(row) {
      this.reset();
      const houseId = row.houseId
      getConfig(houseId).then(response => {
        this.form = response.data;
        if(response.data.auditStatus==2){
          this.chakanyushenhe = false;
        }
        this.zhuye = 1;
      });
    },
    }
};
</script>
<style  scoped>
/deep/.el-divider--horizontal{
  margin: -20px 0 30px 0 !important;
}
</style>

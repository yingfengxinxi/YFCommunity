<template>
  <div>
  <div v-show="zhuye == 0">
  <div class="app-container">
    <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="100px">
      <el-form-item label="租客姓名" prop="tenantName">
        <el-input
          v-model="queryParams.tenantName"
          placeholder="请输入姓名"
          clearable
          style="width: 240px"
          @keyup.enter.native="handleQuery"
        />
      </el-form-item>
      <el-form-item label="租客手机号码" prop="tenantPhone">
        <el-input
          v-model="queryParams.tenantPhone"
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
      <el-table-column type="selection" width="60" align="center" />
      <el-table-column label="租客姓名" align="center" prop="tenantName" />
      <el-table-column label="手机号" align="center" prop="tenantPhone" show-overflow-tooltip/>
      <el-table-column label="身份证号" align="center" prop="cardNo" show-overflow-tooltip/>
      <el-table-column label="所属小区" align="center" prop="communityName" />
      <el-table-column label="所属楼栋" align="center" prop="buildingName"/>
      <el-table-column label="所属单元" align="center" prop="unitName"/>
      <el-table-column label="所属房号" align="center" prop="roomName"/>
      <el-table-column label="业主姓名" align="center" prop="ownerName" />
      <el-table-column label="业主联系方式" align="center" prop="ownerPhone" :show-overflow-tooltip="true" width="100"/>
      <el-table-column label="提交方式" align="center" prop="dataSource">
        <template slot-scope="scope">
          <div v-if="scope.row.dataSource==0">物业注册</div>
          <div v-if="scope.row.dataSource==1">手机注册</div>
        </template>
      </el-table-column>
      <el-table-column label="合同编号" align="center" prop="contractNo" show-overflow-tooltip/>
      <el-table-column label="申请时间" align="center" prop="createTime" width="180">
        <template slot-scope="scope">
          <span>{{ parseTime(scope.row.createTime) }}</span>
        </template>
      </el-table-column>
      <el-table-column label="审核时间" align="center" prop="auditTime" width="180">
        <template slot-scope="scope">
          <span v-if="scope.row.auditTime == null">-</span>
          <span else>{{ parseTime(scope.row.auditTime) }}</span>
        </template>
      </el-table-column>
        <el-table-column label="审核状态" align="center" prop="auditStatus">
          <template slot-scope="scope">
            <div v-if="scope.row.auditStatus==0" >审核中</div>
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
            <el-button  style="float: right; padding: 3px 25px;font-size: 15px;color: lawngreen;margin-right:10px;margin-top:20px" type="success" plain v-show="chakanyushenhe"  @click="pass(form.tenantId)">通过</el-button>
            <el-button  style="float: right; padding: 3px 25px;font-size: 15px;color: red;margin-right:10px;margin-top:20px"  type="danger" plain  v-show="chakanyushenhe" @click="repulse()">驳回</el-button>
          </div>
          <el-divider></el-divider>
          <!-- 第一行 -->
          <el-row>
            <el-col :span="8">
              <el-form-item label="户主姓名: ">
                  {{form.ownerName}}
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="联系电话: ">
                  {{ form.ownerPhone }}
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="所属小区: ">
                  {{ form.communityName }}
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 第二行 -->
          <el-row>
            <el-col :span="8">
              <el-form-item label="所属楼栋: ">
                  {{ form.buildingName }}
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="所属单元: ">
                  {{ form.unitName }}
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="所属房间: ">
                  {{ form.roomName }}
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="房屋状态: ">
                  <div v-if="form.roomStatus==0">未售</div>
                  <div v-if="form.roomStatus==1">已售</div>
                  <div v-if="form.roomStatus==2">入住</div>
                  <div v-if="form.roomStatus==3">装修</div>
                  <div v-if="form.roomStatus==4">已拿房</div>
                  <div v-if="form.roomStatus==5">整租</div>
                  <div v-if="form.roomStatus==6">合租</div>
<!--                  <div v-if="form.roomStatus==7">合租2</div>-->
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="合同编号: ">
               {{ form.contractNo }}
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="合同生效时间: ">
                {{ parseTime(form.contractStart) }}
              </el-form-item>
            </el-col>


          </el-row>
          <el-row>
          <el-col :span="8">
            <el-form-item label="合同失效时间: ">
              {{ parseTime(form.contractEnd) }}
            </el-form-item>
          </el-col>

            <el-col :span="8">
              <el-form-item label="房产证明: ">
                 <a href="form.licenseUrl">{{ form.licenseUrl }}</a>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="租房合同: ">
                  <a href="form.contractUrl">{{ form.contractUrl }}</a>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
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
            <h5 style="margin-left:20px;font-size: 17px"><b>租客基本信息</b></h5>
          </div>
          <el-divider></el-divider>
          <!-- 第一行 -->
          <el-row>
            <el-col :span="8">
              <el-form-item label="姓名: ">
                  {{ form.tenantName }}
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="手机号: ">
                  {{ form.tenantPhone }}
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="性别: ">
                  <div v-if="form.gender==0">男</div>
                  <div v-if="form.gender==1">女</div>
                  <div v-if="form.gender==2">未知</div>
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
                  {{ form.cardNo }}
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="户籍地详细地址: ">
                  {{ form.domicileAddress }}
              </el-form-item>
            </el-col>
          </el-row>


          <el-row>
            <el-col :span="8">
              <el-form-item label="证件正面: ">
                  <div class="block">
                    <el-image :src="form.cardFont"></el-image>
                  </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="证件反面: ">
                  <div class="block">
                    <el-image :src="form.cardBack"></el-image>
                  </div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="个人照片: ">
                  <div class="block">
                    <el-image :src="form.faceUrl"></el-image>
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
                  <div v-if="form.politicsStatus==1">党员</div>
                  <div v-if="form.politicsStatus==2">团员</div>
                  <div v-if="form.politicsStatus==3">其他</div>
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="宗教信仰: ">
                  {{ form.religion }}
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="籍贯: ">
                  {{ form.nativePlace }}
              </el-form-item>
            </el-col>
          </el-row>
          <!-- 第二行 -->
          <el-row>
            <el-col :span="8">
              <el-form-item label="民族: ">
                  {{ form.nation }}
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="文化程度: ">
                  {{ form.eduLevel }}
              </el-form-item>
            </el-col>
            <el-col :span="8">
              <el-form-item label="兵役状况 ">
                  {{ form.military }}
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
                  {{ form.nationality }}
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
                  {{ form.career }}
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
                  {{ form.qqNumber }}
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="邮箱: ">
                  {{ form.email }}
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="8">
              <el-form-item label="个人介绍: ">
                  <el-input
                    type="textarea"
                    placeholder="请输入内容"
                    v-model="form.selfIntro"
                    disabled
                    maxlength="200"
                    show-word-limit
                  >
                  </el-input>
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
        <el-button type="primary" @click="submitForm(form.tenantId)">确 定</el-button>
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
} from "@/api/estate/center/audit/tenantAudit/tenantAudit";
import log from "@/views/monitor/job/log";

export default {
  name: "tenantAudit",
  dicts: ['sys_yes_no','sys_certificate','sys_normal_disable'],
  data() {
    return {
      roomId: null,
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
        tenantName: undefined,
        tenantPhone: undefined,
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
      this.ids = selection.map(item => item.tenantId)
      this.single = selection.length!=1
      this.multiple = !selection.length
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const tenantIds = row.tenantId || this.ids;
      this.$modal.confirm('是否确认删除参数编号为"' + tenantIds + '"的数据项？').then(function() {
        return delConfig(tenantIds);
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
    pass(tenantId){
      this.$confirm('是否通过该数据的审核!!', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const auditStatus = '1';
        const roomId =  this.roomId;
        this.audit = {
          tenantId,
          roomId,
          auditStatus
        }
        passAndRepulse(this.audit).then(response => {
          this.getList();
        });
        this.zhuye = 0;
        this.audit=[];
        this.$message({
          type: 'success',
          message: '审核通过!'
        });
      }).catch(() => {});
    },
    //驳回
    repulse(){
      this.open = true;
      this.title = "驳回原由";
    },
 submitForm(tenantId){
  this.$refs["form"].validate(valid => {
   if (valid) {
      this.$confirm('是否确认驳回？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const auditStatus = '2';
        const rejectReason = this.form.RejectReason;
        this.audit = {auditStatus,tenantId,rejectReason},
        passAndRepulse(this.audit).then(response => {
          this.open = false;
          this.getList();
        });
        this.form={};
        this.zhuye = 0;
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
      const tenantId = row.tenantId;
      this.roomId = row.roomId;
      getConfig(tenantId).then(response => {
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

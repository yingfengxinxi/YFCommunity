<template>
  <div class="app-container">
    <div v-show="openView">
      <el-row :gutter="20">
        <!--小区数据-->
        <el-col :span="4">
          <div class="head-container">
            <el-input
              placeholder="请输入小区名称"
              clearable
              v-model="filterText">
            </el-input>
            <el-tree
              class="filter-tree"
              :data="currentList"
              :props="currentListProps"
              :expand-on-click-node="false"
              default-expand-all
              :filter-node-method="filterNode"
              ref="tree"
              highlight-current
              node-key="communityId"
              @node-click="handleNodeClick"
              >
            </el-tree>
          </div>
        </el-col>
      
        <el-col :span="20" :xs="24">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
            <el-form-item label="评测编号" prop="satisfactionNo">
              <el-input
                v-model="queryParams.satisfactionNo"
                placeholder="请输入评测编号"
                clearable
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            
            <el-form-item label="问卷标题" prop="title">
              <el-input
                v-model="queryParams.title"
                placeholder="请输入问卷标题"
                clearable
                style="width: 240px"
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="评测状态" prop="state">
              <el-select
                v-model="queryParams.state"
                placeholder="请选择评测状态"
                clearable
                style="width: 160px"
              >
                <el-option  label="未开始" value="未开始" />
                <el-option  label="进行中" value="进行中" />
                <el-option  label="已结束" value="已结束" />
                
              </el-select>
            </el-form-item>
            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>
          
          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
              <el-button
                type="primary"
                plain
                icon="el-icon-plus"
                size="mini"
                @click="handleAdd"
              
              >新增
              </el-button>
            </el-col>
            
          </el-row>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>

          <el-table v-loading="loading" :data="decorationList">
            <el-table-column label="评测编号" align="center" prop="satisfactionNo"/>
            <el-table-column label="问卷标题" align="center" prop="title"/>
            <el-table-column label="开始时间" align="center" prop="startTime" :show-overflow-tooltip="true"/>
            <el-table-column label="结束时间" align="center" prop="endTime" :show-overflow-tooltip="true"/>
            <el-table-column label="评测状态" align="center" prop="state"/>
            <el-table-column label="创建时间" align="center" key="createTime" prop="createTime" :show-overflow-tooltip="true"/>
          
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
                  @click="handleDetail(scope.row)"
                  v-if="scope.row.state!='未开始'"
                >查看</el-button>
                <br  v-if="scope.row.state!='未开始'">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  v-if="scope.row.state=='未开始'"
                >修改</el-button>
                <br  v-if="scope.row.state=='未开始'">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  style="color: #f56c6c"
                  @click="handleDelete(scope.row)"
                  v-if="scope.row.state=='未开始'"
                >删除
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

    

     <!--  添加/修改  -->
      
     <div v-show="facilitieShow" v-cloak>
      <el-form ref="form"  :model="form" :rules="rules" label-width="110px">
              <h4 class="form-header">
                问卷信息
              </h4>
              <el-row>
                <el-col >
                  <el-row>
                    <el-col :span="8" :offset="2">
                      <el-form-item label="评测编号:" prop="satisfactionNo">
                        <el-input
                          v-model="form.satisfactionNo"
                          placeholder="请输入评测编号"
                          clearable
                          style="width: 200px"
                        />
                      </el-form-item>
                    </el-col>
                    <el-col :span="8" :offset="2">
                      <el-form-item label="问卷标题:" prop="title">
                        <el-input
                          v-model="form.title"
                          placeholder="请输入问卷标题"
                          clearable
                          style="width: 200px"
                        />
                      </el-form-item>
                    </el-col>
                    </el-row>
                    <el-row>
                    <el-col :span="8" :offset="2">
                      <el-form-item label="开始时间:" prop="startTime">
                        
                        <el-date-picker
                          :disabled="isDisabled"
                          v-model="form.startTime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          type="datetime"
                          style="width: 200px"
                          placeholder="请选择开始时间"
                          :picker-options="expireTimeOPtion"
                        ></el-date-picker>  
                      </el-form-item>
                    </el-col>
                    <el-col :span="8" :offset="2">
                      <el-form-item label="结束时间:" prop="endTime">
                        <el-date-picker
                          v-model="form.endTime"
                          value-format="yyyy-MM-dd HH:mm:ss"
                          type="datetime"
                          style="width: 200px"
                          placeholder="请选择结束时间"
                          :picker-options="expireTimeOPtion"
                        ></el-date-picker>  
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="17" :offset="2">
                      <el-form-item label="参与范围:" prop="buildingIds">
                        <el-select
                          v-model="form.buildingIds"
                          clearable
                          multiple
                          placeholder="请选择参与范围"
                          style="width:100%"
                        >
                          <el-option
                            v-for="item in buildingData"
                            :key="item.buildingId+''"
                            :label="item.buildingName"
                            :value="item.buildingId+''"
                          >
                          </el-option>
                        </el-select>
                      </el-form-item>
                    </el-col>
                    
                  </el-row>
                  <el-row>
                    <el-col :span="17" :offset="2">
                      <el-form-item label="问卷描述:" prop="description">
                        <el-input
                            type="textarea"
                            placeholder="请输入问卷描述"
                            v-model="form.description"
                            maxlength="400"
                            show-word-limit
                          >
                          </el-input>
                      </el-form-item>
                    </el-col>
                    
                  </el-row>
                  
                  
                </el-col>
              </el-row>
              <h4 class="form-header">
                问题信息
              </h4>
              <el-row>
                <el-col >
                  <div v-for="(item,index) in satisfactionSurveyProblemList" :key="index">
                    <el-row>
                      <el-col :span="8" :offset="2">
                        <el-form-item label="问题:" prop="problemName">
                          <el-input
                            v-model="item.problemName"
                            placeholder="请输入问题"
                            clearable
                            style="width: 200px"
                          />
                        </el-form-item>
                      </el-col>
                      <el-col :span="2">
                        <el-button
                          size="mini"
                          type="text"
                          style="color:red"
                          icon="el-icon-delete"
                          @click="removeRow(item,item)"
                          ></el-button
                        >
                    </el-col>
                    </el-row>
                  </div>
                  <el-button
                    type="text"
                    icon="el-icon-plus"
                    size="mini"
                    style="padding-left: 141px;"
                    @click="addRow"
                  >添加问题</el-button>  
                </el-col>
              </el-row>
            </el-form>
        <el-row>
              <el-col :span="4" :offset="20" style="margin-top: 5px;">
                <el-button type="primary" @click="submit">提 交</el-button>
                <el-button @click="cancelFacilitle">取 消</el-button>
              </el-col>
            </el-row>
     </div>

     <!-- 详情 -->
     <div v-show="viewShow" v-cloak>
        <el-tabs v-model="activeName">
          <el-tab-pane label="满意度调查问卷" name="house">
            <el-form ref="form"  :model="form"  label-width="110px">
              <h4 class="form-header">
                问卷信息
              </h4>
              <el-row>
                <el-col >
                  <el-row>
                    <el-col :span="8" :offset="2">
                      <el-form-item label="评测编号:" prop="satisfactionNo">
                       {{ form.satisfactionNo }}
                      </el-form-item>
                    </el-col>
                    <el-col :span="8" :offset="2">
                      <el-form-item label="问卷标题:" prop="title">
                        {{ form.title }}
                      </el-form-item>
                    </el-col>
                    </el-row>
                    <el-row>
                    <el-col :span="8" :offset="2">
                      <el-form-item label="开始时间:" prop="startTime">
                        {{ form.startTime }}
                      </el-form-item>
                    </el-col>
                    <el-col :span="8" :offset="2">
                      <el-form-item label="结束时间:" prop="endTime">
                        {{ form.endTime }}
                      </el-form-item>
                    </el-col>
                  </el-row>
                  <el-row>
                    <el-col :span="17" :offset="2">
                      <el-form-item label="参与范围:" prop="buildingIds">
                        {{form.buildingIds}}
                      </el-form-item>
                    </el-col>
                    
                  </el-row>
                  <el-row>
                    <el-col :span="17" :offset="2">
                      <el-form-item label="问卷描述:" prop="description">
                       {{ form.description }}
                      </el-form-item>
                    </el-col>
                    
                  </el-row>
                  
                  
                </el-col>
              </el-row>
              <h4 class="form-header">
                问题信息
              </h4>
              <el-row>
                <el-col >
                  <div v-for="(item,index) in satisfactionSurveyProblemList" :key="index">
                    <el-row>
                      <el-col :span="8" :offset="2">
                        <el-form-item label="问题:" prop="problemName">
                          {{ item.problemName }}
                        </el-form-item>
                      </el-col>
                      
                    </el-row>
                  </div>
                 
                </el-col>
              </el-row>
            </el-form>
            
          </el-tab-pane>
          <el-tab-pane label="满意度调查结果" name="document">
            <el-form :model="form"  label-width="140px">
              <h4 class="form-header">
                按照问题类型统计
              </h4>
              <el-row>
                
                  <el-table  :data="problemList">
                    <el-table-column label="问题类型" align="center" prop="problemName"/>
                    <el-table-column label="“非常满意”占比" align="center" prop="answer1Percentage"/>
                    <el-table-column label="“满意”占比" align="center" prop="answer2Percentage" />
                    <el-table-column label="“一般”占比" align="center" prop="answer3Percentage" />
                    <el-table-column label="“不满意”占比" align="center" prop="answer4Percentage"/>
                    <el-table-column label="“未投票”占比" align="center" prop="answer0Percentage"/>
                  </el-table>
                
              </el-row>
            </el-form>
          </el-tab-pane>
         
        </el-tabs>
        <el-row>
              <el-col :span="4" :offset="22" style="margin-top: 20px;">
                <el-button @click="canceldetail">取 消</el-button>
              </el-col>
            </el-row>
     </div>
  </div>
</template>

<script>
import { listSatisfactionSurvey, getSatisfactionSurveyDetail,del,add,update } from "@/api/estate/satisfaction/satisfaction";
import { getCurrentList,getBuildingList } from "@/api/estate/community";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";

export default {
  name: "facilitie",
  components: { Treeselect },
  dicts: ['facilitie_equipment_type'],
  data() {
    return {
      viewShow:false,
      // 遮罩层
      loading: true,
      loading1: true,
      satisfactionSurveyProblemList:[],
      // 选中数组
      ids: [],
      active:1,
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      //主页面展示
      openView: true,
      //详情页是否展示
      detailShow: false,
      //详情表单数据
      detailForm: {},
      violationList: [],
      dateRange:[],
      // 树筛选文本
      filterText:'',
      // 显示搜索条件
      showSearch: true,
      showSearch1:true,
      // 总条数
      total: 0,
      total1: 0,
      // 装修表格数据
      decorationList: null,
      problemList:[],
      //装修工信息
      worker: {},
      //小区数据集合
      currentList: [],
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      facilitieShow: false,
      isDisabled:false,
      // 弹出层标题
      title: "",
      activeName: 'house',
      // 是否显示弹出层
      open: false,
      // 默认密码
      initPassword: undefined,
      // 表单参数
      form: {
        id:undefined,
        communityId:undefined,
        equipmentName:undefined,
        bindName:undefined,
        facilitieEquipmentType:undefined,
        specificationsModel:undefined,
        equipmentStatus:undefined,
        unitSupply:undefined,
        installationTime:undefined,
        enableTime:undefined,
        warrantyExpireTime:undefined,
        originalUnit:undefined,
        businessLicense:undefined,
        chargeName:undefined,
        chargePhone:undefined,
        installationLocation:undefined,
        productNumber:undefined,
        registrationNumber:undefined,
        other:undefined,
        signs:undefined,
        instructions:undefined,
        qrCode:undefined
      },
      renovationAuditStatusOptions:[],
      defaultProps: {
        children: "children",
        label: "label"
      },
      expireTimeOPtion: {
        disabledDate(time) {
          return time.getTime() < Date.now() - 8.64e7;  //如果没有后面的-8.64e6就是不可以选择今天的
        }
      },
      showBut: true,
      buildingData:[],
      // 表单校验
      rules: {
        satisfactionNo: [
          {required: true, message: "评测编号不能为空", trigger: "blur"}
        ],
        title: [
          {required: true, message: "问卷标题不能为空", trigger: "blur"},
        ],
        startTime: [
          {required: true, message: "开始时间不能为空",  trigger: "blur"}
        ],
        endTime: [
          {required: true, message: "结束时间不能为空",  trigger: "blur"}
        ],
        buildingIds: [
          {required: true, message: "请选择参与范围", trigger: "blur"}
        ]
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        satisfactionNo:undefined,
        title: undefined,
        state: undefined,
        communityId: undefined
      },
      // 查询参数
      queryParams1: {
        pageNum: 1,
        pageSize: 10,
        satisfactionId:undefined
      },
    };
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  created() {
    
    this.getTree();
  },
  methods: {
    addRow() {
      this.satisfactionSurveyProblemList.push({"problemName":"","satisfactionSurveyId":""});
    },
     // 移除某行
    removeRow(k) {
      if (this.satisfactionSurveyProblemList.length == 1) {
        return;
      }
      this.satisfactionSurveyProblemList = this.satisfactionSurveyProblemList.filter(
        (item) => item !== k
      );
    },
     // 根据小区ID查楼栋
     selectFloor(communityId) {
      getBuildingList(communityId).then((response) => {
        this.buildingData = response.data;
      });
    },
    getBuildingNames(form) { 
      var name = "";
      console.log("form.buildingIds>>", form.buildingIds);
      var buildingList=form.buildingIds.split(",");
      buildingList.forEach(buildingId => {
        var obj = this.buildingData.filter( item=> buildingId == item.buildingId + '')[0];
        if (obj != null) { 
          name = name + obj.buildingName + ",";
        }
      });
      if (name) { 
        return name.substring(0, name.length - 1);
      }
    },
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(row){
      this.queryParams.communityId = row.communityId
      this.form.communityId = row.communityId
      this.selectFloor(this.queryParams.communityId);
      this.getList();
    },
    //提交
    submit() { 
      this.$refs["form"].validate(valid => {
        this.form.communityId = this.queryParams.communityId;
        var flag = 0;
        if (valid) {
          this.satisfactionSurveyProblemList.forEach(item => {
            if (!item.problemName) { 
             
              flag = 1;
              return;
            }
          });
          if (flag == 1) { 
            this.$modal.msgError("请填写问题项");
            return;
          }
          
          this.form.satisfactionSurveyProblemList = this.satisfactionSurveyProblemList;
          this.form.buildingIds = this.form.buildingIds + '';
          this.showBut = false;
          if (this.form.id) {
            update(this.form).then(response => {
              if (response.code == 200) {
                this.$modal.msgSuccess("修改成功");
                this.cancelFacilitle()
                this.showBut = true;
              } else { 
                this.$modal.msgError(response.msg);
                this.form.buildingIds = this.form.buildingIds.split(",");
                this.showBut = true;
                return;
              }
          });
          } else { 
            add(this.form).then(response => {
              if (response.code == 200) {
                this.$modal.msgSuccess("添加成功");
                this.cancelFacilitle();
                this.showBut = true;
              } else { 
                this.$modal.msgError(response.msg);
                this.form.buildingIds = this.form.buildingIds.split(",");
                return;
              }
            });
          }
        }
      });
    },
    //取消
    cancelFacilitle() { 
     
      this.facilitieShow = false;
      this.openView = true
      this.getList();
    },
    /** 查询装修列表 */
    getList() {
      if (this.dateRange) { 
        this.queryParams.startTime = this.dateRange[0];
        this.queryParams.endTime = this.dateRange[1];
      }
      this.loading = true;
      listSatisfactionSurvey(this.queryParams).then(response => {
          this.decorationList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
   

    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      
      this.form = {
        id:undefined,
        communityId:undefined,
        
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

    // 获取树数据
    getTree(){
      getCurrentList().then(res =>{
        this.currentList = res.data;
        this.queryParams.communityId = this.currentList[0].communityId;
        this.selectFloor(this.queryParams.communityId);
        this.getList();

        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.currentList[0].communityId);
        });
      })
    },

     // 任务组名字典翻译
    facilitieEquipmentName(row, column) {
      return this.selectDictLabel(this.dict.type.facilitie_equipment_type, row.facilitieEquipmentType);
    },

      // 任务组名字典翻译
    auditStatusName(row, column) {
      return this.selectDictLabel(this.dict.type.renovation_audit_status, row.renovationAuditStatus);
    },

    handleExport() { 
      this.download('estate/facilitieEquipment/export', {
        ...this.queryParams
      }, `设施设备_${new Date().getTime()}.xlsx`)
    },

    /** 新增按钮操作 */
    handleAdd() {
      this.form = {};
      this.satisfactionSurveyProblemList = [{ "problemName": "", "satisfactionSurveyId": "" }];
      this.isDisabled = false;
      this.facilitieShow = true;
      this.openView = false;
    },

    /** 查看详情按钮操作 */
    handleDetail(row) {
      this.activeName = "house"
      this.isDisabled = true;
      const id = row.id;
      this.form = {};
      getSatisfactionSurveyDetail(id, "1").then(response => {
        if (response.code == 200) { 
          this.form = response.data;
          if (this.form.problemList) { 
            this.problemList = this.form.problemList;
          }
          
          if (this.form.satisfactionSurveyProblemList) {
            this.satisfactionSurveyProblemList = this.form.satisfactionSurveyProblemList;
          } else { 
            this.satisfactionSurveyProblemList=[{ "problemName": "", "satisfactionSurveyId": "" }];
          }        
          this.form.buildingIds=this.getBuildingNames(this.form);
          
          this.selectFloor(this.form.communityId);
          this.openView = false
          this.viewShow = true
        }
        
      });
      
    },
    //修改
    handleUpdate(row) { 
      this.isDisabled = true;
      const id = row.id
      getSatisfactionSurveyDetail(id,"0").then(response => {
        if (response.code == 200) { 
          this.form = response.data;

          if (this.form.satisfactionSurveyProblemList) {
            this.satisfactionSurveyProblemList = this.form.satisfactionSurveyProblemList;
          } else { 
            this.satisfactionSurveyProblemList=[{ "problemName": "", "satisfactionSurveyId": "" }];
          }        
          if (this.form.buildingIds) { 
            this.form.buildingIds = this.form.buildingIds.split(",");
          }
          
          this.selectFloor(this.form.communityId);
          this.openView = false
          this.facilitieShow = true
        }
      });
    
    },
    // 删除按钮
    handleDelete(row) {
      this.$modal.confirm('是否确认删除选中的数据项？').then(function () {
        return del(row.id);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {
      });
    },
     //图片下载
     downLoadImg(url, name) {
      const image = new Image();
      image.setAttribute("crossOrigin", "anonymous");
      image.src = url;
      image.onload = () => {
        const canvas = document.createElement("canvas");
        canvas.width = image.width;
        canvas.height = image.height;
        const context = canvas.getContext("2d");
        context.drawImage(image, 0, 0, image.width, image.height);
        canvas.toBlob((blob) => {
          const url = URL.createObjectURL(blob);
          const a = document.createElement("a");
          a.download = name || "photo";
          a.href = url;
          a.click();
          a.remove();
          URL.revokeObjectURL(url);
        });
      };
    },
    //关闭详情页
    canceldetail() {
      this.form = {}
      this.viewShow = false
      this.openView = true;
      this.getList();
    }
  }
};
</script>

<style scoped>


/*设置高亮颜色*/
/deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #b3f1b6 !important;
}


   /deep/ .stepsStyle .el-step__title.is-process,.el-step__title.is-wait{
      font-weight: 400 !important;
        color: #ccc4cf !important;
         border-color: #a8a8a8 !important;
     }
     /* 这里设置的是下一步和等待状态里的文字颜色 */
     /deep/.stepsStyle .el-step__head.is-process,.el-step__heads.is-wait .el-step__icon.is-text{
       
         color: #a8a8a8 !important;
         border-color: #a8a8a8 !important;
       
     }
     /deep/.stepsStyle .el-step__head.is-finish{
       color: #2a82e4 !important;
     }
     /deep/.el-step__main .el-step__title.is-finish{
      padding-bottom:0px;
     }
     /* 这里设置的是已完成状态里的文字颜色 */
     /deep/.stepsStyle .el-step__head.is-finish .el-step__icon.is-text{
         color: #2a82e4 !important;
         border-color: #2a82e4 !important;
       }
     .el-step__title.is-finish{
       color: #2a82e4 !important;
     }
     
     /* 这里设置的是完成后过渡线条颜色 */
     .el-step__line-inner{
       border-color: #2a82e4 !important;
     }
     
     /* 这里设置的是完成后的颜色 */
     .el-step__head.is-success,.el-step__title.is-success{
       color: #2a82e4 !important;
     }

</style>

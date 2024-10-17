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
              class="filter-tree"
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


        <el-col :span="20">
          <el-row :gutter="10" class="mb8" style="margin-left: 2%">
            <el-col :span="1.5">
              <el-button
                type="primary"
                plain
                icon="el-icon-plus"
                size="mini"
                @click="handleAdd"
                v-hasPermi="['estate:voteCommittee:add']"
              >新增</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
          </el-row>
          <el-table v-loading="loading" :data="allotList" v-show="show" >
            <el-table-column label="任务配置" align="center" prop="allotType" >
              <template slot-scope="scope">
                <dict-tag :options="dict.type.allot_type" :value="scope.row.allotType"/>
              </template>
            </el-table-column>
            <el-table-column label="协助人数量" align="center" prop="assistNum" />
            <el-table-column label="分配方式" align="center" prop="allotWay" >
              <template slot-scope="scope">
                <dict-tag :options="dict.type.allot_way" :value="scope.row.allotWay"/>
              </template>
            </el-table-column>
            <el-table-column label="人员角色" align="center" prop="userTypes" :show-overflow-tooltip="true" :formatter="userTypeFormatter">
              <template slot-scope="scope">
                <span>{{ userTypeFormatter(scope.row.userTypes) }}</span>
              </template>
            </el-table-column>
            <el-table-column label="间隔时长" align="center" prop="gapHour" />
            <el-table-column label="最后操作人" align="center" prop="updateBy" />
            <el-table-column label="最后操作时间" align="center" prop="updateTime" />
            <el-table-column label="操作" align="center" width="200px" class-name="small-padding fixed-width" >
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['estate:voteCommittee:edit']"
                >修改</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </div>
    <!-- 添加或修改业主委员会 -->
    <div v-show="showAdd">
      <el-dialog :title="title" :visible.sync="open" width="950px"  center  >


        <el-form ref="form" :model="form" :rules="rules" size="medium" label-width="150px">
          <el-row :span="25">
            <el-col :span="10">
              <el-form-item label="所属小区:">
                {{form.communityName}}
              </el-form-item>
            </el-col>
            <el-col :span="10">
              <el-form-item label="协助人数量：" prop="assistNum">
                <!--                onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;"-->
                <el-input  v-model="form.assistNum" placeholder="请输入协助人数量" v-emoji/>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :span="25">
            <el-col :span="10">
              <el-form-item label="任务配置:" prop="allotType">
                <el-select v-model="form.allotType" placeholder="请选择任务配置"  @change="getAllot" clearable>
                  <el-option
                    v-for="dict in dict.type.allot_type"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  />
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="10">
              <el-form-item label="分配方式:" prop="allotWay">
                <el-select v-model="form.allotWay" placeholder="请选择分配方式"   clearable>
                  <el-option
                    v-for="dict in dict.type.allot_way"
                    :key="dict.value"
                    :label="dict.label"
                    :value="dict.value"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :span="25">
            <el-col :span="10">
              <el-form-item label="人员角色:"  prop="userTypes">
                <el-select v-model="form.userTypes" multiple placeholder="请选择人员角色" clearable>
                  <el-option
                    v-for="item in dict.type.work_post"
                    :key="item.value"
                    :label="item.label"
                    :value="item.value">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>

            <el-col :span="10">
              <el-form-item label="间隔时长:"  prop="gapHour">
                <el-input oninput="value=value.replace(/^(0+)|[^\d]+/g,'')" maxlength="5"
                          v-model="form.gapHour" placeholder="请输入间隔时长"><template slot="append">小时</template></el-input>

                <!--                <el-select v-model="form.ownerId" placeholder="请选择人员" @change="handleOwnerChange">-->
                <!--                  <el-option-->
                <!--                    v-for="item in ownerList"-->
                <!--                    :key="item.ownerId"-->
                <!--                    :label="item.ownerName"-->
                <!--                    :value="item.ownerId">-->
                <!--                  </el-option>-->
                <!--                </el-select>-->
              </el-form-item>
            </el-col>
          </el-row>

          <!--          <el-row :span="25">-->
          <!--            <el-col :span="10">-->
          <!--              <el-form-item label="身份证号码:">-->
          <!--                {{ peopleObj.cardNo }}-->
          <!--              </el-form-item>-->
          <!--            </el-col>-->

          <!--            <el-col :span="8">-->
          <!--              <el-form-item label="手机号码:" >-->
          <!--                {{ peopleObj.ownerPhone }}-->
          <!--              </el-form-item>-->
          <!--            </el-col>-->
          <!--          </el-row>-->


        </el-form>
        <div slot="footer" class="dialog-footer" style="float: right;margin-top: -30px" >
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </el-dialog>
    </div>
  </div>
</template>
<script>
import {listAllot,addAllot,updateAllot,getAllot,getAll} from "@/api/estate/work/allot/allot"
import {getCurrentList,  getPersonList} from "@/api/estate/community";
import {desensitization} from "@/utils/common";

export default {
  name: "committee",

  dicts: ['allot_type','allot_way','work_post'],
  components: {
  },
  data() {
    return {
      openView: false,
      filterText:'',
      // 遮罩层
      loading: true,
      // 选中数组
      ids: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,

      showSearch:true,

      letcommunityId:undefined,
      letcommunityName:undefined,
      hasAllowUpdate: true,
      // 总条数
      total: 0,
      // 物品列表表格数据
      communityList: [],
      allotList: [],
      // 弹出层标题
      title: "",
      communityId:undefined,
      // 是否显示弹出层
      open: false,

      intRow:undefined,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId:undefined,
        committeeName:null,
        communityName:null,
        ownerPhone:undefined
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        assistNum: [
          { required: true, message: "协助人数量不能为空", trigger: "blur" },
          {pattern: /^[+]{0,1}(\d+)$/, message: "协助人数量格式错误！", trigger: "blur"}
        ],
        allotType: [
          { required: true, message: "任务配置不能为空", trigger: "blur" }
        ],
        allotWay: [
          { required: true, message: "请选择分配方式", trigger: "blur" }
        ],
        userTypes: [
          { required: true, message: "请选择人员角色", trigger: "blur" }
        ],
        gapHour: [
          { required: true, message: "间隔时长不能为空", trigger: "blur" }
        ],

      },
      //物品出门列表详情页面
      //显示页面
      show:true,
      //显示添加或修改页面
      showAdd:false,
      oSList: {},
      // 树结构
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      //小区数据集合
      currentList:[],
      modelForm:{}
    };
  },
  created() {
    this.openView = 0;
    this.getTree();
    this.getDicts("sys_user_sex").then(response => {
      this.sexOptions = response.data;
    });
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    userTypeFormatter(userTypes){
      let types = [];
      for (let i in userTypes) {
        types.push(this.selectDictLabel(this.dict.type.work_post, userTypes[i]))
      }
      return types.join();
    },
    // 过滤树
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 获取树数据
    getTree(){
      getCurrentList().then(res =>{
        this.queryParams.communityId=res.data[0].communityId;
        this.letcommunityId=res.data[0].communityId;
        this.letcommunityName=res.data[0].communityName;
        this.currentList = res.data;
        this.getList();
      })
    },

    // 树点击事件
    handleNodeClick(row){
      this.form.communityId = row.communityId;
      this.form.communityName = row.communityName;
      this.queryParams.communityId = row.communityId
      this.getList();
    },
    /** 查询所有业主委员会列表 */
    getList() {
      this.loading = true;
      listAllot(this.queryParams).then(response => {
        this.allotList = response;
        this.loading = false;
      });
    },



    // 取消按钮
    cancel() {
      this.openView = 0;
      this.showAdd = false;
      this.open = false;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {};
      this.resetForm("form");
    },
    /** 新增按钮操作 */
    handleAdd() {

      if(this.form.communityId==null){
        this.form.communityId=this.letcommunityId;
        this.form.communityName=this.letcommunityName;
        this.open = true;
        this.showAdd = true;
        this.title = "添加分配";
      } else {
        this.open = true;
        this.showAdd = true;
        this.title = "添加分配";

      }
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      getAll(row.allotId).then(res =>{
        this.form=res.data;
        this.showAdd = true;
        this.open = true;
        this.hasAllowUpdate = true;
        this.title = "修改当前分配设置";
      });

    },

    getAllot(){
      // this.form.allotType = this.form.allotType;
      let data = JSON.stringify(this.form)
      getAllot(data).then(res => {
        this.intRow = res;
        if (this.intRow>0){
          this.hasAllowUpdate = false;
          this.$modal.msgError("该任务已存在不允许新建")
        }
      })
    },

    submitForm(){
      if (!this.hasAllowUpdate){
        this.$modal.msgError("该任务已存在不允许新建")
        return;
      }
      this.$refs["form"].validate(valid => {
        if(valid) {
          if (this.form.allotId == undefined) {
            addAllot(this.form).then(response => {
              this.$modal.msgSuccess("添加成功");
              // this.getList();
              // this.reset();
              // this.show=true;

              this.cancel();
              this.getList();
            }).catch((error) => {
              this.$modal.alertError("添加失败");
            });
          } else {
            updateAllot(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.getList();
            }).catch((error) => {
              this.$modal.alertError("修改失败");
            })
          }

          this.cancel();
          this.getList();
          // this.showAdd = false;
          // this.show=true;
          // this.reset();
        }
      })
    },






    // 人员下拉信息
    handleOwnerChange(data){
      let params = {
        ownerId:data
      }
      getPersonList(params).then(resp => {
        this.peopleObj = resp.data
        this.peopleObj.cardNo = desensitization(this.peopleObj.cardNo,6,15)
        this.peopleObj.ownerPhone = desensitization(this.peopleObj.ownerPhone,3,7)
      })
    },

  }
};
</script>
<style scoped>

</style>

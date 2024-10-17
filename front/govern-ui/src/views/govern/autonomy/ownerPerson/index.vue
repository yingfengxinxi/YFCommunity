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
            highlight-current
            node-key="communityId"
            ref="tree">
          </el-tree>
        </div>
      </el-col>


      <el-col :span="20">
        <el-form :model="queryParams" ref="queryForm" :inline="true" v-show="showSearch" label-width="90px">
          <el-form-item label="委员会名称">
            <el-input
              v-emoji
              v-model.trim="queryParams.committeeName"
              placeholder="请输入业主委员会名称"
              clearable
              size="small"
              @keyup.enter.native="handleQuery"
            />
          </el-form-item>
          <el-form-item label="创建时间">
            <el-date-picker
              v-model="dateRange"
              size="small"
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
              type="primary"
              plain
              icon="el-icon-plus"
              size="mini"
              @click="handleAdd"
              v-hasPermi="['govern:ownerCommittee:add']"
            >新增</el-button>
          </el-col>
          <el-col :span="1.5">
            <el-button
              type="danger"
              plain
              icon="el-icon-delete"
              size="mini"
              :disabled="multiple"
              @click="handleDelete"
              v-hasPermi="['govern:ownerCommittee:remove']"
            >删除</el-button>
          </el-col>
          <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
        </el-row>
        <el-table v-loading="loading" :data="selectCommittee" v-show="show" @selection-change="handleSelectionChange">
          <el-table-column type="selection" width="55" align="center" />
          <el-table-column label="委员会名称" align="center" prop="committeeName" />
          <el-table-column label="所属小区" align="center" prop="communityName" />
          <el-table-column label="创建人" align="center" prop="createBy" />
          <el-table-column label="创建时间" align="center" prop="createTime" />
          <el-table-column label="修改人" align="center" prop="updateBy" />
          <el-table-column label="修改时间" align="center" prop="updateTime" />
          <el-table-column label="操作" align="center" width="200px" class-name="small-padding fixed-width" >
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="text"
                icon="el-icon-s-fold"
                @click="handleFind(scope.row)"
                v-hasPermi="['govern:ownerCommittee:query']"
              >查看</el-button>
              <el-button
                size="mini"
                type="text"
                style="color: #f56c6c"
                icon="el-icon-delete"
                @click="handleDelete(scope.row)"
                v-hasPermi="['govern:ownerCommittee:remove']"
              >删除</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-edit"
                @click="handleUpdate(scope.row)"
                v-hasPermi="['govern:ownerCommittee:edit']"
              >修改</el-button>
              <el-button
                size="mini"
                type="text"
                icon="el-icon-s-fold"
                @click="handMoreFind(scope.row)"
              >查看业主</el-button>
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
      <el-dialog
        title="业主信息"
        :visible.sync="dialogVisible"
        width="70%"
        :before-close="handleClose">
        <owner :handRowId="handRowId"></owner>
        <span slot="footer" class="dialog-footer">
<!--    <el-button @click="dialogVisibleCancle">取 消</el-button>-->
<!--    <el-button type="primary" @click="dialogVisibleCancle">确 定</el-button>-->
  </span>
      </el-dialog>
    </div>
    <!-- 添加或修改业主委员会 -->
    <div v-show="showAdd">
      <el-dialog :title="title" :visible.sync="open" width="950px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules" size="medium" label-width="150px">
        <el-row :span="25">
          <el-col :span="10">
            <el-form-item label="所属小区:">
                {{form.communityName}}
            </el-form-item>
          </el-col>
          <el-col :span="10">
            <el-form-item label="委员会名称：" prop="committeeName">
              <el-input onKeypress="javascript:if(event.keyCode == 32)event.returnValue = false;" v-model="form.committeeName" placeholder="请输入业主委员会名称" v-emoji/>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer" style="float: right;margin-top: -30px" >
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
      </el-dialog>
    </div>
    <!-- 查看业主委员会列表-->
    <div v-show="openView === 1">
      <h4 class="form-header">业主委员会列表的基本信息</h4>
      <el-form ref="form" :model="form" label-width="120px">
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="委员会名称：">{{ form.committeeName }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="所属小区：">{{ form.communityName }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="创建人：">{{ form.createBy }}</el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="创建时间：">{{ form.createTime }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="修改人：">{{ form.updateBy }}</el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="修改时间：">{{ form.updateTime }}</el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="cancel" style="float: right" class="footer-top">关 闭</el-button>
      </div>
    </div>
  </div>
</template>
<script>
import {listCommittee, addCommittee, removeCommittee, updateCommittee, tableCommittee} from "@/api/govern/autonomy/ownerPerson/ownercommit";
import {getCurrentList, getBuildingList, getUnitList, getRoomList, getOwnerList} from "@/api/govern/community";
import {desensitization, equals} from "@/utils/common";
import owner from "@/views/govern/autonomy/ownerPerson/owner";
export default {
  name: "committee",
  components: {
    owner,
  },
  data() {
    return {
      dialogVisible: false,
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
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      // 物品列表表格数据
      communityList: [],
      selectCommittee: [],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // $comment字典
      sexOptions: [],
      //小区列表
      buildingList:[],
      //小区列表
      unitList:[],
      roomList:[],
      ownerList:[],
      peopleObj:{},
      // 日期范围
      dateRange: [],
      handRowId: {},
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
        committeeName: [
          { required: true, message: "委员会名称不能为空", trigger: "blur" }
        ],
        communityId: [
          { required: true, message: "所属小区不能为空", trigger: "blur" }
        ],
      },
      //物品出门列表详情页面
      showGoods:false,
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
    // this.getList();
    this.getTree();
   // this.getProCommunity();
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
    handleClose(done) {
     this.dialogVisible=false;
    },
    // 过滤树
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 获取树数据
    getTree(){
      getCurrentList().then(res =>{
        this.currentList = res.data;
        this.queryParams.communityId = this.currentList[0].communityId;
        this.getList();
        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.currentList[0].communityId);
        });
      })
    },
    /** 查询所有业主委员会列表 */
    getList() {
      this.loading = true;
      listCommittee(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
        this.selectCommittee = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },


    /** 查询业主项目列表 */
    getProCommunity() {
      let param={
        communityType: '1'
      }
      listProCommunity(param).then(response => {
        if (response.code == 200) {
          this.communityList = response.data;
        } else {
          this.msgError(response.msg);
        }
      });
    },
    // $comment字典翻译
    sexFormat(row, column) {
      return this.selectDictLabel(this.sexOptions, row.sex);
    },
    // 取消按钮
    cancel() {
      this.openView = 0;
      this.showAdd = false;
      this.open = false;
      this.reset();
      this.peopleObj={
        cardNo:null,
        ownerPhone:null
      };
    },
    // 表单重置
    reset() {
      this.form = {
        committeeId:null,
        buildingId:null,
        committeeName:null,
        communityName:null,
        createBy:null,
        updateBy:null,
        ownerName: null,
        updateTime:null,
        createTime:null,
        communityId: this.currentList[0].communityId
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
      this.reset();
      this.dateRange = null;
      this.resetForm("queryForm");
      this.handleQuery();

    },
    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.committeeId)
      this.single = selection.length!==1
      this.multiple = !selection.length
    },
    /** 新增按钮操作 */
    handleAdd() {
      // this.reset();
      if(this.form.communityId==null){
        this.$modal.msgError("请选择小区")
      } else {
        this.open = true;
        this.showAdd = true;
        this.title = "添加业主委员会列表";
        // this.getProCommunity();
        getBuildingList(this.queryParams.communityId).then(resp => {
          this.buildingList = resp.data
        })
      }
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      // console.log(row)
      const id = row.committeeId || this.ids
      tableCommittee(id).then(response => {
        // this.getProCommunity();
        getBuildingList(row.communityId).then(resp => {
          this.buildingList = resp.data
        })
        this.form = response.data;
        this.showAdd = true;
        this.open = true;
        this.title = "修改业主委员会列表";

      });
    },
    /** 通过按钮操作 */
    handlePass(row) {
      const id = row.committeeId || this.ids
      checkPass(id).then(response => {
        this.form = response.data;
        this.open = true;
        this.title = "修改业主委员会列表";
      });
    },
    /** 查看按钮操作 */
    handleFind(row) {
      this.openView = 1;
      this.open =false;
      const id = row.committeeId || this.ids
      tableCommittee(id).then(response => {
        this.form = response.data;
        this.title = "查看业主委员会列表";
      });
    },
    /** 提交按钮 */
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.committeeId != null) {
            updateCommittee(this.form).then(response => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            addCommittee(this.form).then(response => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    handMoreFind(row){
      this.handRowId ={
        committeeId:row.committeeId,
        communityId:row.communityId
      }
     this.dialogVisible = true
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const ids = row.committeeId || this.ids;
      this.$confirm('是否确认删除所选数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function() {
        return removeCommittee(ids);
      }).then(() => {
        this.getList();
        this.msgSuccess("删除成功");
      }).catch(() => {});
    },
    // 树点击事件
    handleNodeClick(row){
      this.form.communityId = row.communityId
      this.form.communityName = row.communityName
      this.queryParams.communityId = row.communityId
      this.getList();
    },
    // 楼栋下拉框改变
    handleBuildChange(data){
      console.log(data)
      getUnitList(data).then(resp => {
        console.log(resp)
        this.unitList = resp.data
      })
    },
    // 单元楼下拉框改变
    handleUnitChange(data){
      console.log(data)
      getRoomList(data).then(resp => {
        console.log(resp)
        this.roomList = resp.data
      })
    },
    // 所屬房号下拉框改变
    handleRoomChange(data){
      console.log(data)
      let params = {
        roomId:data
      }
      getOwnerList(params).then(resp => {
        console.log(resp)
        this.ownerList = resp.data
      })
    },
    // 人员下拉信息
    handleOwnerChange(data){
      console.log(data)
      let params = {
        ownerId:data
      }
      getPersonList(params).then(resp => {
        console.log(111122)
        console.log(resp.data)
        this.peopleObj = resp.data
        this.peopleObj.cardNo = desensitization(this.peopleObj.cardNo,6,15)
        this.peopleObj.ownerPhone = desensitization(this.peopleObj.ownerPhone,3,7)
      })
    },

  }
};
</script>
<style scoped>
.footer-top{
  position: absolute !important;
  top:10px;
  right: 100px;
}
</style>

<template>
  <div class="app-container">

    <el-row :gutter="20">
      <el-col :span="4" :xs="24">
        <div class="head-container">
          <el-input
            placeholder="请输入小区名称"
            clearable
            v-model="filterText">
          </el-input>
<!--          <el-tree
            :data="currentList"
            :props="currentListProps"
            :expand-on-click-node="false"
            ref="tree"
            default-expand-all
            highlight-current
            node-key="communityId"
            :filter-node-method="filterNode"
            @node-click="handleNodeClick"
          />-->
          <el-tree
            class="filter-tree"
            :data="currentList"
            :props="currentListProps"
            default-expand-all
            :filter-node-method="filterNode"
            icon-class="el-icon-star-on"
            @node-click="handleNodeClick"
            :expand-on-click-node="false"
            highlight-current
            node-key="communityId"
            ref="tree">
          </el-tree>
        </div>
      </el-col>

      <el-col :span="20" :xs="24">
        <div class="tabble">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="110px">

            <el-form-item label="老人姓名">
              <el-input v-model="queryParams.ownerName" placeholder="请输入老人姓名"/>
            </el-form-item>

            <el-form-item label="紧急联系人姓名">
              <el-input v-model="queryParams.contactName" placeholder="请输入紧急联系人姓名"/>
            </el-form-item>

            <el-form-item label="紧急人联系方式">
              <el-input v-model="queryParams.contactPhone" placeholder="请输入紧急人联系方式"/>
            </el-form-item>

            <el-form-item label="审核状态" label-width="70px">
              <el-select v-model="queryParams.auditStatus" placeholder="请选择状态">
                <el-option
                  v-for="dict in dict.type.sys_oldman_auditstatus"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>

            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>

          </el-form>

          <el-row :gutter="10" class="mb8">
            <el-col :span="1.5">
<!--              <el-button-->
<!--                type="danger"-->
<!--                plain-->
<!--                icon="el-icon-delete"-->
<!--                size="mini"-->
<!--                :disabled="multiple"-->
<!--                @click="handleDelete(scope.row)"-->
<!--              >删除</el-button>   &lt;!&ndash; v-hasPermi="['system:car:remove']" &ndash;&gt;-->
            </el-col>
            <el-col :span="1.5">
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
          </el-row>

          <el-table v-loading="loading" :data="olderList"  @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center"/>
            <el-table-column label="老人姓名" align="center" prop="ownerName" :show-overflow-tooltip="true"/>
            <el-table-column label="所属房号" align="center" prop="homeName" :show-overflow-tooltip="true"/>
            <el-table-column label="年龄" align="center" prop="age"/>
            <el-table-column label="性别" align="center" prop="oldGender" :formatter="Dgender" />
            <el-table-column label="申请人" align="center" prop="createBy" :show-overflow-tooltip="true"/>
            <el-table-column label="与老人关系" align="center" prop="relation" :formatter="gx" />
            <el-table-column label="申请人手机号" align="center" prop="contactPhone" width="110px"/>
            <el-table-column label="提交时间" align="center" prop="createTime" :show-overflow-tooltip="true"/>
            <el-table-column label="审核状态" align="center" prop="auditStatus" :formatter="os"/>
            <el-table-column label="最后操作时间" align="center" prop="updateTime" :show-overflow-tooltip="true"/>
            <el-table-column label="最后操作人" align="center" prop="updateBy"/>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="lookgg(scope.row)"
                >查看
                </el-button>
              </template>
            </el-table-column>
          </el-table>
        </div>
        <pagination
          v-show="total>0"
          :total="total"
          :page.sync="queryParams.pageNum"
          :limit.sync="queryParams.pageSize"
          @pagination="getList"
        />
      </el-col>


      <!--查看审核-->
      <el-dialog :title="title" :visible.sync="open" width="800px" append-to-body>
        <el-form ref="form" :model="form" :rules="rules">
          <el-row :gutter="20">
            <h2>房产信息</h2>
            <hr>
            <el-col :span="8" >
              <el-form-item label="与户主关系:" >
                <span>{{form.ownerRelation}}</span>
              </el-form-item>

              <el-form-item label="所属单元:">
                <span>{{form.unitName}}</span>
              </el-form-item>

            </el-col>
            <el-col :span="8" >
              <el-form-item label="所属小区:">
                <span>{{form.communityName}}</span>
              </el-form-item>

              <el-form-item label="所属房间:">
                <span>{{form.roomName}}</span>
              </el-form-item>
            </el-col>

            <el-col :span="8" >
              <el-form-item label="所属楼字:" >
                <span>{{form.buildingName}}</span>
              </el-form-item>

              <el-form-item label="房屋状态:">
                <span v-if="form.roomStatus == 0">未售</span>
                <span v-else-if="form.roomStatus == 1">已售</span>
                <span v-else-if="form.roomStatus == 2">入住</span>
                <span v-else-if="form.roomStatus == 3">装修</span>
                <span v-else-if="form.roomStatus == 4">已拿房</span>
                <span v-else-if="form.roomStatus == 5">整租</span>
                <span v-else-if="form.roomStatus == 6">合租</span>
                <span v-else-if="form.roomStatus == 7">群组</span>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <h2>业主基本信息</h2>
            <hr>
            <el-col :span="8" >
              <el-form-item label="姓名:" >
                <span>{{form.ownerName}}</span>
              </el-form-item>

              <el-form-item label="年龄:" >
                <span>{{form.age}}</span>
              </el-form-item>


              <el-form-item label="身份证正面照片:" >
                <div style="width: 280px">
                  <el-image
                    style="width: 100px; height: 100px;vertical-align: middle"
                    :src="form.cardFont"
                    fit="scale-down"></el-image>
                </div>
              </el-form-item>
            </el-col>

            <el-col :span="8" >
              <el-form-item label="手机号:" >
                <span>{{form.ownerPhone}}</span>
              </el-form-item>

              <el-form-item label="身份证类型:" >
                <span>{{form.cardType}}</span>
              </el-form-item>

              <el-form-item label="身份证反面:" >
                <div style="width: 280px">
                  <el-image
                    style="width: 100px; height: 100px;vertical-align: middle"
                    :src="form.cardBack"
                    fit="scale-down"></el-image>
                </div>
              </el-form-item>

            </el-col>

            <el-col :span="8" >
              <el-form-item label="性别:" >
                <span>{{form.oldGender}}</span>
              </el-form-item>

              <el-form-item label="人脸图片:" >
                <div style="width: 280px">
                  <el-image
                    style="width: 100px; height: 100px;vertical-align: middle"
                    :src="form.faceUrl"
                    fit="scale-down"></el-image>
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row :gutter="20">
            <h2>紧急联系人信息</h2>
            <hr>
            <el-col :span="8">
              <el-form-item label="姓名:">
                <span>{{form.contactName}}</span>
              </el-form-item>

              <el-form-item label="联系方式:">
                <span>{{form.contactPhone}}</span>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="性别:">
                <span>{{form.contactGender}}</span>
              </el-form-item>

              <el-form-item label="与老人关系:">
                <span>{{form.relation}}</span>
              </el-form-item>
            </el-col>

            <el-col :span="8">
              <el-form-item label="身份证:">
                <span>{{form.contactId}}</span>
              </el-form-item>
            </el-col>
            <span v-if="form.auditStatus == 2">
              <el-col :span="8">
              <el-form-item label="驳回原因:">
                <span>{{form.rejectReason}}</span>
              </el-form-item>
            </el-col>
            </span>


          </el-row>
        </el-form>

        <div slot="footer" class="dialog-footer">
          <el-button type="success" @click="tg" v-if="form.auditStatus == 0"v-hasPermi="['oldman:elderlyfile:tg']">通过</el-button>
          <el-button type="danger" @click="bohui" v-if="form.auditStatus == 0" v-hasPermi="['oldman:elderlyfile:bh']">驳回</el-button>
          <el-button @click="reset">取 消</el-button>
        </div>
      </el-dialog>

    </el-row>
  </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {
  getbuildingList,
  getunit,
  getroom,
  getoldman,
  addoldfile,
  getList,
  getoldmsg, shtg, shbh
} from "@/api/estate/customer/oldman/oldmanfile";
import {selectDictLabels} from "@/utils/global";

export default {
  name: "elderlyfile",
  dicts: ['sys_user_sex', 'sys_oldman_gx','sys_oldman_auditstatus','sys_certificate','sys_user_sex','sys_oldman_huzhu'],
  data() {
    return {

      filterText: '',

      //小区数据集合
      currentList:[],
      //楼字数据集合
      buildingList:[],
      //单元数据集合
      unitList:[],
      //房间数据集合
      roomList:[],
      //房屋类型
      roomStatus:null,
      //老人数据集合
      oldman:[],
      //老人数据列表
      olderList:[],

      //老人基本信息
      //————————————————————————
      ownerRelation:null,
      cardBack:null,
      cardFont:null,
      cardNo:null,
      cardType:null,
      date:null,
      faceUrl: null,
      gender: null,
      ownerId: null,
      ownerName:null,
      ownerPhone:null,
      searchValue:null,

      //————————————————————————
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },

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
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 是否显示详细弹出层
      openView: false,
      // 是否显示Cron表达式弹出层
      openCron: false,
      // 传入的表达式
      expression: "",
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId:null,
        buildingId:null,
        unitId:null,
        roomId:null,
        ownerId:null,
        roomName:null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
      }
    };
  },
  created(){
    this.gettree();
    // this.getList();
  },

  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },



  methods: {


    // 过滤树
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },


    //字典过滤
    Dgender(row, column) {
      return this.selectDictLabel(this.dict.type.sys_user_sex, row.oldGender)
    },
    gx(row, column) {
      return this.selectDictLabel(this.dict.type.sys_oldman_gx, row.relation)
    },
    os(row, column) {
      return this.selectDictLabel(this.dict.type.sys_oldman_auditstatus, row.auditStatus)
    },

    // dicts: ['sys_user_sex', 'sys_oldman_gx'],

    getList(){
      getList(this.queryParams).then(res =>{
        this.olderList = res.rows;
        this.total = res.total;
        this.loading = false;
      })
    },

    gettree(){
      getCurrentList().then(res =>{
        this.currentList = res.data;
        this.queryParams.communityId = this.currentList[0].communityId;
        this.communityId = this.currentList[0].communityId;
        this.getList();
        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.currentList[0].communityId);
        });
      })
    },

    handleNodeClick(ro){
      this.queryParams.communityId = ro.communityId;
      this.communityId = ro.communityId
      this.getList();
    },

    handleQuery(){
      this.getList();
    },


    resetQuery(){
      this.reset()
      this.gettree()
      // this.getList()
    },
    reset(){
      this.open = false;
      this.queryParams={
        communityId:null,
        buildingId:null,
        unitId:null,
        roomId:null,
        ownerId:null,
        roomName:null
      }
      this.form={}
    },
    handleDelete(){

    },
    lookgg(row){
      this.reset()
      getoldmsg(row.olderId).then(res=>{
        this.form = res.data[0];
        this.form.cardType = selectDictLabels(this.dict.type.sys_certificate, this.form.cardType.toString())
        this.form.oldGender = selectDictLabels(this.dict.type.sys_user_sex, this.form.oldGender.toString())
        this.form.contactGender = selectDictLabels(this.dict.type.sys_user_sex, this.form.contactGender.toString())
        this.form.relation = selectDictLabels(this.dict.type.sys_oldman_gx, this.form.relation.toString())
        this.form.ownerRelation = selectDictLabels(this.dict.type.sys_oldman_huzhu, this.form.ownerRelation.toString())
      })
      this.open = true
      this.title = '养老审核'
    },

    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.id)
      this.single = selection.length !== 1
      this.multiple = !selection.length
    },

    /** 提交按钮 */
    tg() {
      if (this.form.auditStatus == 0){
        shtg(this.form.olderId).then(res =>{
          this.$modal.msgSuccess('审核成功')
          this.open = false
          this.reset();
          this.$tab.refreshPage();
        })
      }else{
        this.$modal.msgError('改审核人已被审核无法再次审核')
        return
      }
    },
    bohui(){
      if (this.form.auditStatus == 0){
        this.$prompt('请输入', '驳回原因', {
          confirmButtonText: '提交',
          cancelButtonText: '取消',
          inputPattern:/^.{1,16}$/,
          inputErrorMessage: '驳回原因限制在 1 至 16 字符以内， 请重新输入'
        }).then(async ({ value }) => {
          let rejectReason = value
          shbh(this.form.olderId,rejectReason).then(res=>{
            this.open = false
            this.reset();
            this.$tab.refreshPage();
          })
        });
      }else {
        this.$modal.msgError('改审核人已被审核无法再次审核')
        return
      }
    },
  }
}

</script>

<style lang="scss">
//<style scoped>
.weizhi80{
  width: 45%;
}
.el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
  background-color: #b3f1b6;
}
</style>

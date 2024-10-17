<template>
  <div class="app-container">
    <div v-show="view === 1">
      <el-row :span="24">
        <!--   选择树   -->
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
              node-key="communityId"
              default-expand-all
              :filter-node-method="filterNode"
              icon-class="el-icon-star-on"
              @node-click="handleNodeClick"
              :current-node-key="queryParams.communityId"
              ref="tree">
            </el-tree>
          </div>
        </el-col>
        <!--   左侧   -->
        <el-col :span="20">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch"
                   :rules="formRules">
            <el-form-item label="工作类型" prop="postId">
              <el-select v-model="queryParams.postId" placeholder="工作类型" clearable>
                <el-option
                  v-for="dict in dict.type.work_post"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>

            <el-form-item label="员工姓名" prop="staffName">
              <el-input
                v-model="queryParams.staffName"
                placeholder="请输入员工姓名"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            <el-form-item label="手机号">
              <el-input
                v-model="queryParams.staffPhone"
                placeholder="请输入手机号"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            <el-form-item>
              <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜索</el-button>
              <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重置</el-button>
            </el-form-item>
          </el-form>
          <el-row :span="10">
            <el-col :span="1.5" :offset="0.5">
              <el-button
                type="primary"
                plain
                icon="el-icon-plus"
                size="mini"
                @click="handleAdd"
                v-hasPermi="['bussiness:phone:add']"
              >新增
              </el-button>
            </el-col>
          </el-row>
          <el-table
            v-if="refreshTable"
            v-loading="loading"
            :data="houseKeepList"
          >
            <el-table-column prop="buildingName" label="楼栋" width="150" align="center"></el-table-column>
            <el-table-column prop="staffName" label="员工姓名" width="150" align="center"></el-table-column>
            <el-table-column prop="age" label="年龄" width="100" align="center"></el-table-column>
            <el-table-column prop="orderId" label="性别" width="50" align="center">
              <template slot-scope="scope">
                <dict-tag :options="dict.type.sys_user_sex" :value="scope.row.gender"/>
              </template>
            </el-table-column>
            <el-table-column prop="staffPhone" label="手机号" width="300" align="center"></el-table-column>
            <el-table-column prop="postId" label="工作类型" width="100" align="center">
              <template slot-scope="scope">
                <span>{{ parsePostId(scope.row.postId) }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="updateTime" label="最后操作时间" width="200" align="center">
              <template slot-scope="scope">
                {{ parseTime(scope.row.updateTime) }}
              </template>
            </el-table-column>
            <el-table-column prop="updateBy" label="最后操作人" width="150" align="center"></el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="success"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['bussiness:phone:edit']"
                >修改
                </el-button>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  style="color: #f56c6c"
                  @click="handleDel(scope.row)"
                  v-hasPermi="['bussiness:phone:remove']"
                >删除
                </el-button>
              </template>
            </el-table-column>
          </el-table>
          <!-- 分页  -->
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


    <!--  新增,修改弹窗  -->
    <el-dialog :title="title"
      :visible.sync="addcenterDialogVisible"
      width="30%"
      :show-close="showClose"
      center>
      <el-row :span="24">
        <el-col :span="12" :offset="5">
          <el-form ref="form" :model="form" label-width="90px" :rules="rules">
            <el-form-item label="管理楼栋:" prop="buildingId">
              <el-select v-model="form.buildingId" placeholder="请选择楼栋" >
                <el-option
                  v-for="item in buildList"
                  :key="item.buildingId"
                  :label="item.buildingName"
                  :value="item.buildingId"/>
              </el-select>
            </el-form-item>
            <el-form-item label="工作类型:" prop="postId">
              <el-select v-model="form.postId" placeholder="请选择工作类型" @change="handPost(form.postId)">
                <el-option
                  v-for="dict in dict.type.work_post"
                  :key="dict.value"
                  :label="dict.label"
                  :value="dict.value"
                />
              </el-select>
            </el-form-item>
            <el-form-item label="管家姓名:" prop="staffId">
              <el-select v-model="form.staffId" placeholder="请选择管家姓名" @change="handPhone">
                <el-option
                  v-for="item in staffList"
                  :key="item.staffId"
                  :label="item.staffName"
                  :value="item.staffId"/>
              </el-select>
              <!--              <div v-model="form2.staffPhone">{{ form2.staffPhone }}</div>-->

              <!--              <el-input-->
              <!--                v-model="queryParams.staffName"-->
              <!--                placeholder="请输入管家姓名"-->
              <!--                clearable-->
              <!--                @keyup.enter.native="handleQuery"-->
              <!--              />-->
            </el-form-item>

            <el-form-item label="管家手机号:" >
              <el-input v-model="form.staffPhone" :disabled="true"></el-input>
            </el-form-item>
          </el-form>
        </el-col>
      </el-row>
      <span slot="footer" class="dialog-footer">
    <el-button @click="addCancel">取 消</el-button>
    <el-button type="primary" @click="submitForm">确 定</el-button>
  </span>
    </el-dialog>
  </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {
  getTableList,
  del,
  postSelect,
  updateDetail,
  updateData,
  getBuildList,
  getStaffList,
  getPhone,
  insertData
} from "@/api/estate/customer/housekeep/housekeep";
import {desensitization} from "@/utils/common";
import {addDeviceOrder, updateDeviceOrder} from "@/api/estate/work/deviceorder/deviceOrder";

export default {
  name: "Praise",
  dicts: ['sys_user_sex', 'work_post'],
  data() {
    /**
     * 校验手机号
     */
    const validatePhone = (rule, value, callback) => {
      const regExp = /^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$/
      if (!regExp.test(value)) {
        callback(new Error('请输入正确的手机号'))
      } else {
        callback()
      }
    }
    return {
      showClose: false,
      // 新增弹窗
      addcenterDialogVisible: false,
      total: 0,
      filterText: '',
      view: 1,
      letcommunityId: undefined,
      // 遮罩层
      loading: false,
      // 重新渲染表格状态
      refreshTable: true,
      // 搜索显示
      showSearch: true,
      // 查询条件
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        staffName: undefined,
        staffPhone: undefined,
        postId: undefined
      },
      // 管家管理列表
      houseKeepList: [],
      // 树结构
      currentListProps: {
        children: "communityId",
        label: "communityName"
      },
      //小区数据集合
      currentList: [],
      // 验证
      formRules: {
        staffPhone: [{
          validator: validatePhone,
          trigger: 'change',
        }]
      },
      // 弹窗form
      form: {
        buildingId: undefined,
        postId: undefined,
        staffId: undefined,
        staffPhone: undefined
      },
      buildList: [],
      staffList: [],
      // 岗位数据
      postList: [],
      title:"",
      // 表单校验
      rules: {
        //管理楼栋
        buildingId: [
          {required: true, message: "管理楼栋不能为空", trigger: ["blur","change"]}
        ],
        //工作类型
        postId: [
          {required: true, message: "工作类型不能为空", trigger: ["blur","change"]}
        ],
        //管家姓名
        staffId: [
          {required: true, message: "管家姓名不能为空", trigger: ["blur","change"]}
        ],
      }
    }
  },
  created() {
    this.getTree();
    this.postSelectOption();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: {
    //岗位下拉框
    postSelectOption() {
      postSelect().then(resp => {
        this.postList = resp.data
      })
    },
    // 过滤树
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 获取树数据
    getTree() {
      getCurrentList().then(res => {
        this.queryParams.communityId = res.data[0].communityId;
        this.currentList = res.data;
        this.getList();
      })
    },
    //设备类型
    parsePostId(postId) {
      return this.selectDictLabel(this.dict.type.work_post, postId);
    },
    // 列表
    getList() {
      this.loading = true;
      getTableList(this.queryParams).then(resp => {
        this.total = resp.total;
        this.houseKeepList = resp.rows;
        this.loading = false;
        this.houseKeepList.forEach(item => {
          if (item.staffPhone) {
            item.staffPhone = desensitization(item.staffPhone, 3, 7)
          }
          return item;
        });
      })
    },
    // 搜索按钮
    handleQuery() {
      this.getList();
    },
    //修改手机号
    handPhone() {
      getPhone(this.form.staffId).then(resp => {
        this.form.staffPhone = resp.staffPhone;
        if (this.form.staffPhone) {
          this.form.staffPhone = desensitization(this.form.staffPhone, 3, 7)
        }
      });

    },
    //改变工作类型
    handPost(postId) {
      this.form.staffId != null ? this.form.staffId = null : this.form.staffId;
      this.form.staffPhone != null ? this.form.staffPhone = null : this.form.staffPhone;
      this.staffList = [];
      let communityId = this.letcommunityId;
      let params = {
        'communityId': communityId,
        'postId': postId
      }
      getStaffList(params).then(resp1 => {
        this.staffList = resp1;
      })
    },
    // 重置按钮
    resetQuery() {
      this.resetForm("queryForm");
      this.queryParams.staffPhone = undefined;
      this.queryParams.staffName = undefined;
      this.queryParams.postId = undefined;
      this.handleQuery();
    },
    // 表格修改按钮
    handleUpdate(row) {
      this.title="修改专属管家信息";
      updateDetail(row.majordomoId).then(resp => {
        this.form = resp.data;
        if (this.form.staffPhone) {
          this.form.staffPhone = desensitization(this.form.staffPhone, 3, 7)
        }
        let communityId = this.letcommunityId;
        if (communityId !== undefined) {
          getBuildList(communityId).then(resp => {
            this.buildList = resp;
            this.addcenterDialogVisible = true;
          });
          let params = {
            'communityId': communityId,
            'postId': this.form.postId
          };
          getStaffList(params).then(resp1 => {
            this.staffList = resp1;
          });
          this.form.postId = this.form.postId+"";
        } else {
          this.$modal.alertError("请选择小区");
        }
      });



    },
    // 树点击事件
    handleNodeClick(row) {
      this.queryParams.communityId = row.communityId
      this.letcommunityId = row.communityId;
      this.getList();
    },
    //删除
    handleDel(row) {
      del(row.majordomoId).then(resp => {
        this.$modal.msgSuccess(resp.msg);
        this.getList();
      })
    },
    // 取消
    cancel() {
      this.form = {}
      this.addcenterDialogVisible = false
    },


    /** 提交按钮 */
    submitForm() {
      this.form.communityId = this.letcommunityId;
      this.$refs["form"].validate(valid => {
        if (valid) {
          if (this.form.majordomoId != null) {
            updateData(this.form).then(resp => {
              this.$modal.msgSuccess(resp.msg);
              this.addCancel();
              this.getList();
            })
          } else {
            insertData(this.form).then(resp => {
              this.$modal.msgSuccess(resp.msg);
              this.addCancel();
              this.getList();
            })
          }
        }
      });
    },
    // 新增按钮
    handleAdd() {
      this.title="新增专属管家信息";
      let communityId = this.letcommunityId;
      if (communityId !== undefined) {
        getBuildList(communityId).then(resp => {
          this.buildList = resp;
          this.addcenterDialogVisible = true
        })

      } else {
        this.$modal.alertError("请选择小区");
      }

    },
    //新增取消
    addCancel() {
      this.form = {};
      this.resetForm("form");
      this.addcenterDialogVisible = false
    },


  },
};
</script>

<style scoped>
/deep/ .el-dialog__header {
  background-color: #409EFF;
  color: white;
  font-size: 20px;
}

/deep/ .el-dialog__title {
  color: white;
  font-size: 22px;
}

</style>


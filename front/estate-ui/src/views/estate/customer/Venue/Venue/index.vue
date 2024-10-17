<template>
  <div class="app-container">
    <el-row :gutter="20">
      <el-col :span="3" :xs="24">
        <div class="head-container">
          <div class="tag-group">
            <el-tag type="success"  @click.native="l">列表界面</el-tag>
            <el-tag type="success"  @click.native="t">图形界面</el-tag>
            <hr>
          </div>
          <el-input
            placeholder="请输入小区名称"
            clearable
            v-model="filterText">
          </el-input>
          <el-tree
            :data="currentList"
            :props="currentListProps1"
            :expand-on-click-node="false"
            ref="tree"
            default-expand-all
            highlight-current
            node-key="communityId"
            :filter-node-method="filterNode"
            @node-click="handleNodeClick"
          />
        </div>
      </el-col>

      <el-col :span="21" :xs="24">
        <div v-show="openView === 0">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
            <el-form-item label="场馆名称" prop="venueName">
              <el-input
                v-model="queryParams.venueName"
                placeholder="请输入场馆名称"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>
            <el-form-item label="场馆地址" prop="address">
              <el-input
                v-model="queryParams.address"
                placeholder="请输入场馆地址"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            <el-form-item label="场馆状态" prop="venueStatus">
              <el-select v-model="queryParams.venueStatus" placeholder="请选择状态">
                <el-option
                  v-for="item in venueStatusList"
                  :key="item.value"
                  :label="item.label"
                  :value="item.value">
                </el-option>
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
                v-hasPermi="['system:venue:add']"
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
                v-hasPermi="['system:venue:remove']"
              >删除</el-button>
            </el-col>
            <el-col :span="1.5">
              <el-button
                type="warning"
                plain
                icon="el-icon-download"
                size="mini"
                @click="handleExport"
                v-hasPermi="['system:venue:export']"
              >导出</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
          </el-row>

          <el-table v-loading="loading" :data="venueList" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55" align="center" />
<!--            <el-table-column label="主键" align="center" prop="venueId" />-->
            <el-table-column label="场馆名称" align="center" prop="venueName"
                             min-width="120" :show-overflow-tooltip="true"/>
            <el-table-column label="场馆地址" align="center" prop="address"
                             min-width="120" :show-overflow-tooltip="true" />
            <el-table-column label="押金"align="center" prop="deposit" />
            <el-table-column label="每小时费用" align="center" prop="hourFee" />
            <el-table-column label="管理员" align="center" prop="staffName" />
            <el-table-column label="场馆图片" align="center" prop="venueImage" width="100">
              <template slot-scope="scope">
                <image-preview :src="scope.row.venueImage" :width="50" :height="50"/>
              </template>
            </el-table-column>
            <!--      <el-table-column label="状态" align="center" prop="venueStatus" />-->
            <el-table-column label="状态" align="center" key="venueStatus">
              <template slot-scope="scope">
                <el-switch
                  v-model="scope.row.venueStatus"
                  active-value= "0"
                  inactive-value= "1"
                  @change="handleStatusChange(scope.row)"
                ></el-switch>
              </template>
            </el-table-column>



            <el-table-column label="修改人" align="center" prop="updateBy" />
            <el-table-column label="修改时间" align="center" prop="updateTime" width="180">
              <template slot-scope="scope">
                <span>{{ parseTime(scope.row.updateTime, '{y}-{m}-{d}') }}</span>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['system:venue:edit']"
                >修改</el-button>
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-delete"
                  style="color: #f56c6c"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['system:venue:remove']"
                >删除</el-button>
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
        <div v-show="openView === 1">
          <div>
            <el-row :gutter="20" >
              <span>可用场馆</span>
              <hr>
              <div style="display: flex;flex-wrap: wrap;">
                <el-col :span="6" v-for="(item,index) in tlistdata" :key="index">
                  <el-card v-if="tlistdata[index].venueStatus === '0'">
                    <div slot="header" class="clearfix">
                      <span style="color: green">{{tlistdata[index].venueName}}</span>
                      <br>
                      <span style="color: #8c939d;font-size: 1px">{{tlistdata[index].address}}</span>
                      <span v-if="tlistdata[index].user === null">
                        <el-button style="float: right; paduserding: 3px 0" type="text" @click="closeVenue(tlistdata[index].venueId,1)">关闭</el-button>
                      </span>
                      <span v-else>
                        <el-button style="float: right; paduserding: 3px 0" type="text" @click="closeVenue(tlistdata[index].venueId,0)">关闭</el-button>
                      </span>
                    </div>
                    <br>
                  </el-card>
                  <br>
                </el-col>
              </div>
            </el-row>
            <span>不可用场馆</span>
            <hr>
            <el-row :gutter="20">
              <el-col :span="6" v-for="(item,index1) in tlistdata1" >
                <el-card v-if="tlistdata1[index1].venueStatus === '1'">
                  <div slot="header" class="clearfix">
                    <span style="color: green">{{tlistdata1[index1].venueName}}</span>
                    <br>
                    <span style="color: #8c939d;font-size: 1px">{{tlistdata1[index1].address}}</span>
                    <el-button style="float: right; padding: 3px 0" type="text" @click="openVenue(tlistdata1[index1].venueId)">开启</el-button>
                  </div>
                </el-card>
                <br>
              </el-col>
            </el-row>
          </div>
        </div>

      </el-col>

    </el-row>




    <!-- 添加或修改场馆信息对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
<!--        <el-form-item label="所属小区:">-->
<!--          <el-select v-model="form.communityId" placeholder="请选择">-->
<!--            <el-option-->
<!--              v-for="(item, index) in currentList"-->
<!--              :key="index"-->
<!--              :label="item.communityName"-->
<!--              :value="item.communityId"-->
<!--            ></el-option>-->
<!--          </el-select>-->
<!--        </el-form-item>-->
        <el-form-item label="场馆名称" prop="venueName">
          <el-input v-model="form.venueName" placeholder="请输入场馆名称" />
        </el-form-item>
        <el-form-item label="场馆地址" prop="address">
          <el-input v-model="form.address" type="textarea" placeholder="请输入内容" />
        </el-form-item>
        <el-form-item label="押金" prop="deposit">
          <el-input v-model="form.deposit" placeholder="请输入押金" maxlength="5"/>
        </el-form-item>
        <el-form-item label="每小时费用" prop="hourFee" label-width="100px">
          <el-input v-model="form.hourFee" placeholder="请输入每小时费用" maxlength="5"/>
        </el-form-item>
        <el-form-item label="管理员" prop="staffId">
            <el-select v-model="form.staffId" placeholder="请选择">
            <el-option
              v-for="(item, index) in gList"
              :key="index"
              :label="item.staffName"
              :value="item.staffId"
            ></el-option>
            </el-select>
        </el-form-item>
        <el-form-item label="场馆图片">
          <image-upload :limit="1" v-model="form.venueImage"/>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { listVenue, getVenue, delVenue, addVenue, updateVenue,getg} from "@/api/estate/customer/Venue/Venue";
import {getCurrentList} from "@/api/estate/community";
import {tlist} from "@/api/estate/customer/Venue/VenueAppoint";

let methods = {

  openVenue(id) {
    this.from = {};
    this.form.venueId = id
    this.form.venueStatus = 0;
    updateVenue(this.form).then(res => {
      if (res.code == 200) {
        this.$message.success("修改成功")
        this.getList();
        this.tuxing();
      }
    });
  },
  closeVenue(id, num) {
    if (num === 0) {
      this.$message.warning("该场馆当日有预约无法取消")
      return
    }
    this.from = {};
    this.form.venueId = id
    this.form.venueStatus = 1;
    updateVenue(this.form).then(res => {
      if (res.code == 200) {
        this.$message.success("修改成功")
        this.getList();
        this.tuxing();
      }
    });
  },


  tuxing() {
    if (this.queryParams.communityId !== null || this.queryParams.communityId !== '' || this.queryParams.communityId !== undefined) {
      this.tdata.communityId = this.queryParams.communityId
    }
    tlist(this.tdata).then(res => {
      this.tlistdata = res.data[0];
      this.tlistdata1 = res.data[1];
    })
  },

  //图形界面
  t() {
    this.openView = 1;
  },
  //列表界面
  l() {
    this.openView = 0;
  },

  adminList() {
    getg(this.form.communityId).then(res => {
      this.gList = res.data
    })
  },

  getCurrent() {
    getCurrentList().then(res => {
      this.currentList = res.data;
      this.queryParams.communityId = this.currentList[0].communityId;
      this.$nextTick(() => {
        this.$refs.tree.setCurrentKey(this.currentList[0].communityId);
      });
      this.tuxing();
      this.getList();
    })
  },

  // 过滤树
  filterNode(value, data) {
    if (!value) return true;
    return data.communityName.indexOf(value) !== -1;
  },

  /** 查询场馆信息列表 */
  getList() {
    this.loading = true;
    listVenue(this.queryParams).then(response => {
      this.venueList = response.rows;
      this.total = response.total;
      this.loading = false;
    });
  },
  // 取消按钮
  cancel() {
    this.open = false;
    this.reset();
  },
  // 表单重置
  reset() {
    this.form = {
      venueId: null,
      communityId: null,
      venueName: null,
      address: null,
      deposit: null,
      hourFee: null,
      staffId: null,
      venueImage: null,
      venueStatus: "0",
      createBy: null,
      createTime: null,
      updateBy: null,
      updateTime: null,
      delFlag: null
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
    this.resetForm("queryForm");
    this.handleQuery();
  },
  // 多选框选中数据
  handleSelectionChange(selection) {
    this.ids = selection.map(item => item.venueId)
    this.single = selection.length !== 1
    this.multiple = !selection.length
  },
  /** 新增按钮操作 */
  handleAdd() {
    if (this.queryParams.communityId == null) {
      this.$modal.msgWarning("请选择小区后新增");
      return
    }
    this.reset();
    this.form.communityId = this.queryParams.communityId;
    this.adminList();
    this.open = true;
    this.title = "添加场馆信息";
  },
  /** 修改按钮操作 */
  handleUpdate(row) {
    this.reset();
    const venueId = row.venueId || this.ids
    getVenue(venueId).then(response => {
      this.form = response.data;
      this.adminList();
      this.open = true;
      this.title = "修改场馆信息";
    });
  },
  /** 提交按钮 */
  submitForm() {
    this.$refs["form"].validate(valid => {
      if (valid) {
        if (this.form.venueId != null) {
          updateVenue(this.form).then(response => {
            this.$modal.msgSuccess("修改成功");
            this.open = false;
            this.getList();
          });
        } else {
          addVenue(this.form).then(response => {
            this.$modal.msgSuccess("新增成功");
            this.open = false;
            this.getList();
          });
        }
      }
    });
  },
  /** 删除按钮操作 */
  handleDelete(row) {
    const venueIds = row.venueId || this.ids;
    this.$modal.confirm('是否确认删除 "' + row.venueName + '" 吗？').then(function () {
      return delVenue(venueIds);
    }).then(() => {
      this.getList();
      this.$modal.msgSuccess("删除成功");
    }).catch(() => {
    });
  },
  /** 导出按钮操作 */
  handleExport() {
    this.download('system/venue/export', {
      ...this.queryParams
    }, `venue_${new Date().getTime()}.xlsx`)
  },

  /**
   * 修改状态
   * @param row
   */
  handleStatusChange(row) {
    this.form.venueId = row.venueId
    this.form.venueStatus = row.venueStatus
    let text = row.venueStatus === "0" ? "启动" : "暂停";
    this.$confirm('确定要' + text + row.venueName + '吗?', '提示', {
      confirmButtonText: '确认',
      cancelButtonText: '取消',
      type: 'warning'
    }).then(() => {
      updateVenue(this.form).then(res => {
        if (res.code == 200) {
          this.$message.success("修改成功")
          this.getList();
        }
      });
    }).catch(() => {
      row.venueStatus = row.venueStatus === "0" ? "1" : "0";
    });
  },
  handleNodeClick(ro) {
    this.queryParams.communityId = ro.communityId
    this.tuxing();
    this.getList();
  },
};
export default {
  name: "Venue",
  data() {
    return {
      openView:0,
      //小区数据集合
      currentList:[],
      tlistdata:{},
      tlistdata1:{},
      //管理员集合
      gList:[],
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
      // 场馆信息表格数据
      venueList: [],
      filterText:'',
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: undefined,
        venueName: undefined,
        address: undefined,
        venueStatus: undefined
      },
      currentListProps1: {
        children: "communityId",
        label: "communityName"
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        venueName: [
          { min: 1, max: 20, required: true, message: "场馆地址不能为空，且长度限制 1 至 20个字 以内", trigger: "blur" }
        ],
        address: [
          { min: 1, max: 20, required: true, message: "场馆地址不能为空，且长度限制 1 至 20个字 以内", trigger: "blur" }
        ],
        deposit: [
          { required: true, message: '价格不能为空', trigger: 'blur' },
          { pattern:  /(^[0]{1}$)|(^[1-9]{1}[0-9]*$)|(^[0-9]*\.[0-9]{1}$)/, message: '金额必须为整数或最多只有一位小数', trigger: ['change', 'blur'] }
        ],
        hourFee: [
          { required: true, message: '价格不能为空', trigger: 'blur' },
          //{ pattern: /^[0-9]*$/, message: '请填写正确的金额', trigger: ['change', 'blur'] }
          { pattern: /(^[0]{1}$)|(^[1-9]{1}[0-9]*$)|(^[0-9]*\.[0-9]{1}$)/, message: '金额必须为整数或最多只有一位小数', trigger: ['change', 'blur'] }
        ],
        staffId: [
          { required: true, message: "管理员id", trigger: "blur" }
        ],
        venueImage: [
          { required: true, message: "场馆图片不能为空", trigger: "blur" }
        ],
        venueStatus: [
          { required: true, message: "状态：0=可用，1=关闭不能为空", trigger: "blur" }
        ],
      },
      venueStatusList: [{
        value: '0',
        label: '开启'
      }, {
        value: '1',
        label: '关闭'
      }],
      tdata:{},
    };
  },
  created() {
    this.getCurrent();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods: methods
};
</script>

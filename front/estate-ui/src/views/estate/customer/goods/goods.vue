<template>
  <div class="app-container">
    <div v-show="openView === 0">
      <el-row :gutter="20">
        <!-- 树结构     -->
        <el-col :span="3" :xs="24">
          <div class="head-container">
            <el-input v-model="filterText" placeholder="请输入小区名称" clearable size="small"
                      prefix-icon="el-icon-search" style="margin-bottom: 20px"/>
          </div>
          <div >
            <el-tree
              :data="comOptions"
              :props="defaultProps"
              :expand-on-click-node="false"
              :filter-node-method="filterNode"
              ref="tree"
              highlight-current
              node-key="communityId"
              @node-click="handleNodeClick"
            >
              <!-- 插入楼栋图标 -->
              <span slot-scope="{ node }">
	   		          <i class="el-icon-office-building" />
	   		          <span class="content"  style="padding-left:5px;">{{node.label}}</span>
		          </span>
            </el-tree>
          </div>
        </el-col>

        <el-col :span="21" :xs="24">
          <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch" label-width="68px">
            <el-form-item label="物品名称" prop="goodsName">
              <el-input
                v-model="queryParams.goodsName"
                placeholder="请输入物品名称"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            <el-form-item label="存放地址" prop="address">
              <el-input
                v-model="queryParams.address"
                placeholder="请输入存放地址"
                clearable
                @keyup.enter.native="handleQuery"
              />
            </el-form-item>

            <el-form-item label="分类信息" prop="classifyId">
              <el-select v-model="queryParams.classifyId" placeholder="请选择分类信息">
                <el-option
                  v-for="item in classifyList"
                  :key="item.classifyId"
                  :label="item.classifyName"
                  :value="item.classifyId">
                </el-option>
              </el-select>
            </el-form-item>

            <el-form-item label="借用状态" prop="borrowStatus">
              <el-select v-model="queryParams.borrowStatus" placeholder="请选择借用状态" style="width: 180px">
                <el-option label="空闲中" value="0"/>
                <el-option label="借用中" value="1"/>
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
                v-hasPermi="['business:goods:insert']"
              >新增</el-button>
            </el-col>

            <el-col :span="1.5">
              <el-button
                type="warning"
                plain
                icon="el-icon-download"
                size="mini"
                @click="handleExport"
                v-hasPermi="['business:goods:export']"
              >导出</el-button>
            </el-col>

            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
          </el-row>

          <el-table v-loading="loading" :data="goodsList" >
            <el-table-column label="物品编号" align="center" prop="goodsNo" />
            <el-table-column label="物品名称" align="center" prop="goodsName" show-overflow-tooltip/>
            <el-table-column label="存放地址" align="center" prop="address" show-overflow-tooltip/>
            <el-table-column label="分类信息" align="center" prop="classifyName" show-overflow-tooltip/>
            <el-table-column label="物品价格" align="center" prop="goodsPrice" />
            <el-table-column label="借用时长(天)" align="center" prop="borrowDays" >
              <template slot-scope="scope">
                <div v-if="scope.row.borrowStatus == '0'">-</div>
                <div v-else-if="scope.row.borrowStatus == '1'">{{scope.row.borrowDays}}</div>
              </template>
            </el-table-column>
            <el-table-column label="历史借用次数" align="center" prop="borrowCount" />
            <el-table-column label="借用状态" align="center" prop="status">
              <template slot-scope="scope">
                <el-tag v-if="scope.row.borrowStatus == '0'" type="success">空闲中</el-tag>
                <el-tag v-else-if="scope.row.borrowStatus == '1'" type="warning">借用中</el-tag>
              </template>
            </el-table-column>
            <el-table-column label="操作" align="center" class-name="small-padding fixed-width" >
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-view"
                  @click="handleGoods(scope.row)"
                >查看
                </el-button>

                <el-button
                  size="mini"
                  type="text"
                  icon="el-icon-edit"
                  @click="handleUpdate(scope.row)"
                  v-hasPermi="['business:goods:update']"
                  v-show="scope.row.borrowStatus == '0'"
                >修改</el-button>

                <el-button
                  v-if="scope.row.borrowStatus == '1'"
                  size="mini"
                  type="text"
                  style="color: #2ec431"
                  icon="el-icon-top-right"
                  @click="handleRemind(scope.row)"
                  v-hasPermi="['business:goods:remind']"
                >提醒</el-button>

                <el-button
                  v-if="scope.row.borrowStatus == '1'"
                  size="mini"
                  type="text"
                  style="color: #f56c6c"
                  icon="el-icon-suitcase"
                  @click="handleCompensate(scope.row)"
                  v-hasPermi="['business:compensate:insert']"
                >赔偿</el-button>

                <el-button
                  size="mini"
                  type="text"
                  style="color: #f56c6c"
                  icon="el-icon-delete"
                  @click="handleDelete(scope.row)"
                  v-hasPermi="['business:goods:delete']"
                  v-show="scope.row.borrowStatus == '0'"
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
        </el-col>
      </el-row>
    </div>

    <!--  查看  -->
    <div v-show="openView === 1">
      <h4 class="form-header" style="color: #0558e7">物品详情</h4>
      <div style="display: flex">
        <el-form ref="form" :model="form" label-width="180px">
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="物品编号：" prop="doctorName">
                <div style="width: 180px">{{ form.goodsNo}}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="物品名称：">
                <div style="width: 180px">{{ form.goodsName }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="所属小区：">
                <div style="width: 180px">{{ form.communityName }}</div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="物品价格：" prop="doctorName">
                <div style="width: 180px">{{ form.goodsPrice}}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="存放地址：">
                <div style="width: 180px">{{ form.address }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="分类信息：">
                <div style="width: 180px">{{ form.classifyName }}</div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="物品照片：" prop="doctorName">
                <img :src="this.form.goodsUrl" class="image"/>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>

      </div>
      <h4 class="form-header" v-if="this.form.borrowStatus == '1'" style="color: #0558e7" >借用详情</h4>
      <div style="display: flex" v-if="this.form.borrowStatus == '1'">
        <el-form ref="form" :model="form" label-width="180px">
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="借用人：" >
                <div style="width: 180px">{{ form.ownerName}}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="手机号：">
                <div style="width: 180px">{{ form.ownerPhone }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="所属楼栋：">
                <div style="width: 180px">{{ form.buildingName }}</div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="单元号：" >
                <div style="width: 180px">{{ form.unitName}}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="门牌号：">
                <div style="width: 180px">{{ form.roomName }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="历史借用次数：">
                <div style="width: 180px">{{ form.borrowCount }}</div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="借用状态：" >
                <div style="width: 180px">
                  <div v-if="form.borrowStatus == '0'">空闲中</div>
                  <div v-if="form.borrowStatus == '1'">借用中</div>
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="借用开始时间：">
                <div style="width: 180px">{{ form.applyTime }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="预计归还时间：">
                <div style="width: 180px">{{ form.returnTime }}</div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="借用时长(天)：" >
                <div style="width: 180px">{{ form.borrowDays}}</div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="备注：" >
                <div style="width: 180px">{{ form.remark}}</div>
              </el-form-item>
            </el-col>
          </el-row>

        </el-form>
      </div>
      <h4 class="form-header" style="color: #0558e7">借用历史</h4>
      <div style="display: flex">
        <el-table v-loading="loading" :data="borrowVos" >
          <el-table-column label="借用时间" align="center" prop="applyTime" />
          <el-table-column label="归还时间" align="center" prop="returnTime" />
          <el-table-column label="借用人" align="center" prop="accountName" />
          <el-table-column label="联系方式" align="center" prop="loginName" />
        </el-table>
      </div>

      <br/>
      <br/>
      <div slot="footer" class="dialog-footer">
        <el-row>
          <el-col :offset="20" :span="2">
            <el-button @click="cancel">取消</el-button>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" @click="cancel">确 定</el-button>
          </el-col>
        </el-row>
      </div>
    </div>

    <!-- 添加或修改对话框 -->
    <el-dialog :title="title" :visible.sync="open" width="500px" append-to-body>
      <el-form ref="form" :model="form" :rules="rules" label-width="80px">
        <el-form-item label="分类信息" prop="classifyId">
          <el-select v-model="form.classifyId" placeholder="请选择分类" style="width: 100%">
            <el-option
              v-for="item in classifyList"
              :key="item.classifyId"
              :label="item.classifyName"
              :value="item.classifyId">
            </el-option>
          </el-select>
        </el-form-item>

        <el-form-item label="物品编号" prop="goodsNo">
          <el-input v-model="form.goodsNo"  placeholder="请输入物品编号" />
        </el-form-item>
        <el-form-item label="物品名称" prop="goodsName">
          <el-input v-model="form.goodsName"  placeholder="请输入物品名称" :maxlength = "20"/>
        </el-form-item>
        <el-form-item label="物品价格" prop="goodsPrice">
          <el-input v-model="form.goodsPrice"
                    placeholder="请输入物品价格"
                    oninput="if(isNaN(value)) { value = null } if(value.indexOf('.')>0){value=value.slice(0,value.indexOf('.')+3)}"/>
        </el-form-item>
        <el-form-item label="存放地址" prop="address">
          <el-input v-model="form.address"  placeholder="请输入存放地址" :maxlength = "20"/>
        </el-form-item>
        <el-form-item label="物品照片" prop="goodsUrl" >
          <el-upload
            class="avatar-uploader"
            list-type="picture-card"
            action
            :show-file-list="false"
            accept="image/jpeg, image/jpg, image/png"
            :auto-upload="false"
            :before-upload="handleBeforeUpload"
            :on-change="handleAvatar"
          >
            <img v-if="this.imageUrl" :src="this.imageUrl" class="avatar">
            <i class="el-icon-plus avatar-uploader-icon" v-show="this.imageUrl == null" ></i>
          </el-upload>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 赔偿 -->
    <el-dialog :title="title" :visible.sync="open2" width="500px" append-to-body>
      <el-form ref="form" :model="form" label-width="150px">
        <el-form-item label="物品编号：" >
          <div style="width: 180px">{{ form.goodsNo}}</div>
        </el-form-item>
        <el-form-item label="物品名称：" >
          <div style="width: 180px">{{ form.goodsName}}</div>
        </el-form-item>
        <el-form-item label="物品价格：" >
          <div style="width: 180px">{{ form.goodsPrice}}</div>
        </el-form-item>
        <el-form-item label="赔偿方式：" >
          <el-radio v-model="form.compensateWay" label="0">现金</el-radio>
          <el-radio v-model="form.compensateWay" label="1">物品</el-radio>
        </el-form-item>
        <el-form-item label="赔偿时间：">
          <el-date-picker
            v-model="form.compensateTime"
            type="date"
            placeholder="选择日期">
          </el-date-picker>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFormCompensate">确 定</el-button>
        <el-button @click="cancel">取 消</el-button>
      </div>
    </el-dialog>

  </div>
</template>

<script>
import {getCurrentList} from "@/api/estate/community";
import {
  addGoods,
  addRemind,
  delGoods,
  getGoodsInfo,
  getGoodsList,
  updateGoods,
  uploadImageUrl
} from "@/api/estate/customer/goods/goods";
import {getAllClassifyList} from "@/api/estate/customer/goods/classify";
import {addCompensate} from "@/api/estate/customer/goods/compensate";

export default {
  name: "goods",
  data(){
    return{
      // 树筛选文本
      filterText:'',
      openView:false,
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
      // 表格数据
      goodsList: [],
      classifyList:[],
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      open2: false,
      //树结构
      comOptions:[],
      borrowVos:[],
      defaultProps: {
        children: "communityId",
        label: "communityName"
      },
      imageUrl: null,
      communityId: null,
      communityName : null,
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: null,
        goodsName:null,
        address:null,
        classifyId:null,
        borrowStatus:null
      },
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        classifyId:[
          { required: true, message: "分类信息不能为空", trigger: "blur" }
        ],
        goodsNo: [
          { required: true, message: "物品编号不能为空", trigger: "blur" },
          { pattern:/^[0-9a-zA-Z]{1,}$/, message: "物品编号格式不正确", trigger: "blur" },
        ],
        goodsName: [
          { required: true, message: "物品名称不能为空", trigger: "blur" }
        ],
        goodsPrice: [
          { required: true, message: "物品价格不能为空", trigger: "blur" }
        ],
        address: [
          { required: true, message: "存放地址不能为空", trigger: "blur" }
        ],
        goodsUrl:[
          { required: true, message: "物品照片不能为空", trigger: "blur"}
        ],
      }
    }
  },
  created() {
    this.getTreeSelect();
    this.getAllClassifyList();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    }
  },
  methods:{
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.communityId = data.communityId;
      // this.handleQuery();
      this.communityId = data.communityId;
      this.communityName = data.communityName;
      this.getList();
    },
    /** 树结构 */
    getTreeSelect() {
      getCurrentList().then(response => {
        this.comOptions = response.data;
        this.queryParams.communityId = this.comOptions[0].communityId;
        this.communityId = this.comOptions[0].communityId;
        this.communityName = this.comOptions[0].communityName;

        // 默认第一个高亮
        this.$nextTick(() => {
          this.$refs.tree.setCurrentKey(this.comOptions[0].communityId);
        });
        this.getList();
      });
    },
    // 取消按钮
    cancel() {
      this.openView = 0;
      this.open = false;
      this.open2 = false;
      this.reset();
    },

    /** 表单重置*/
    reset() {
      this.form = {
        goodsName: undefined,
        address: undefined,
        classifyId: undefined,
        borrowStatus: undefined,
        goodsNo: undefined,
        goodsPrice:undefined,
        imageUrl:undefined,
        remark:undefined
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
      // this.getTreeSelect();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 查询分类列表 */
    getAllClassifyList() {
      getAllClassifyList(this.queryParams).then(response => {
          this.classifyList = response.data;
        }
      );
    },
    /** 查询列表 */
    getList() {
      this.openView = 0;
      this.loading = false;
      getGoodsList(this.queryParams).then(response => {
        this.goodsList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    /** 查看详情*/
    handleGoods(row){
      const goodsId = row.goodsId;
      getGoodsInfo(goodsId).then(response => {
        this.form = response.data;
        this.form.borrowDays = row.borrowDays;
        this.borrowVos = this.form.borrowVos;
        this.openView = 1;
      });
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.imageUrl = null;
      this.reset();
      this.open = true;
      this.title = "新增" + this.communityName + "物品";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      getGoodsInfo(row.goodsId).then(response => {
        this.form = response.data;
        this.imageUrl = response.data.goodsUrl;
        this.open = true;
        this.title = "修改物品";
      });
    },
    /** 提醒*/
    handleRemind(row){
      this.$modal.confirm('是否确认提醒？').then(function() {
      }).then(() => {
        getGoodsInfo(row.goodsId).then(response => {
          this.form = response.data;
          addRemind(this.form);
        });
        this.$modal.msgSuccess("提醒成功");
      }).catch(() => {});
    },
    /** 赔偿页面*/
    handleCompensate(row){
      getGoodsInfo(row.goodsId).then(response => {
        this.form = response.data;
        this.open2 = true;
        this.title = "物品赔偿";
      });

    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$modal.confirm('是否确认删除选中的数据项？').then(function() {
        return delGoods(row.goodsId);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      }).catch(() => {});
    },
    /** 提交按钮 */
    submitForm(){
        this.$refs["form"].validate(valid => {
          if (valid) {
            if (this.form.goodsUrl == null){
              this.$modal.msgError("请上传物品照片");
            }else {
              if (this.form.goodsId != undefined) {
                updateGoods(this.form).then(response => {
                  this.$modal.msgSuccess("修改成功");
                  this.open = false;
                  this.getList();
                });
              } else {
                this.form.communityId = this.communityId;
                addGoods(this.form).then(response => {
                  this.$modal.msgSuccess("新增成功");
                  this.open = false;
                  this.getList();
                });
              }
            }
          }
        });
    },
    /** 赔偿提交*/
    submitFormCompensate(){
      if (null == this.form.compensateWay || "" == this.form.compensateWay){
        this.$modal.msgError("赔偿方式不能为空");
      }else if (null == this.form.compensateTime){
        this.$modal.msgError("赔偿时间不能为空");
      } else {
        addCompensate(this.form).then(response => {
          this.$modal.msgSuccess("赔偿成功");
          this.open2 = false;
          this.getList();
        });
      }
    },
    //图片上传
    handleAvatar(file){
      if (file) {
        let formData = new FormData();
        formData.append("file", file.raw);
        uploadImageUrl(formData).then((res) => {
          this.form.goodsUrl = res.imgPath;
          this.rules.goodsUrl = res.imgPath;
          this.imageUrl = res.imgPath;
          this.$modal.msgSuccess("上传成功");
        });
      }
    },
    // 上传前校检格式和大小
    handleBeforeUpload(file) {
      return false;
    },

    /** 导出按钮操作 */
    handleExport() {
      this.$confirm('导出数据为未归还物品人员名单,是否确认导出？', "导出确认", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(()=> {
        this.download('estate/goods/export', {
          ...this.queryParams
        }, `未归还物品人员名单.xlsx`)
      }).catch(function (err){
      })
    },
  }
}
</script>

<style scoped>
.image {
  height:200px;
  width: 200px
}
.avatar {
  width: 100%;
  height: 100%;
}
/*设置高亮颜色*/
/deep/ .el-tree--highlight-current .el-tree-node.is-current > .el-tree-node__content {
 background-color: #b3f1b6 !important;
}
.content {
  font-size: 14px;
}
</style>

<template>
  <div class="app-container">
    <!--综合页面信息-->
    <div v-show=checkView>
      <!--模糊查询-->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
        <el-form-item label="乙方公司" prop="companyName">
          <el-input
            v-model="queryParams.companyName"
            placeholder="请输入乙方公司"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="合同名称" prop="contractName">
          <el-input
            v-model="queryParams.contractName"
            placeholder="请输入合同名称"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item label="签约人" prop="nickName">
          <el-input
            v-model="queryParams.nickName"
            placeholder="请输入签约人姓名"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery"
                     v-hasPermi="['manage:serve:list']">搜 索
          </el-button>
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery">重 置</el-button>
        </el-form-item>
      </el-form>
      <!--按钮操作-->
      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['manage:serve:add']"
          >新增
          </el-button>
        </el-col>
        <el-col :span="1.5">
          <el-button
            type="danger"
            plain
            icon="el-icon-delete"
            size="mini"
            :disabled="multiple"
            @click="handleDelete"
            v-hasPermi="['manage:serve:remove']"
          >删除
          </el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
      <!--表单-->
      <el-table v-loading="loading" :data="tableList" row-key="contractId" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="50" align="center"/>
        <el-table-column label="合同名称" align="center" key="contractName" prop="contractName">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" :content="scope.row.contractName" placement="top">
              <span>{{ hanldeOmit(scope.row.contractName) }}</span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column label="甲方公司" align="center" key="estateName" prop="estateName"/>
        <el-table-column label="甲方负责人" align="center" key="legal" prop="legal">
          <template slot-scope="scope">
            {{ handleName(scope.row.legal) }}
          </template>
        </el-table-column>
        <el-table-column label="甲方联系方式" align="center" key="hotline" prop="hotline">
          <template slot-scope="scope">
            {{ handlePhone(scope.row.hotline) }}
          </template>
        </el-table-column>
        <el-table-column label="乙方公司" align="center" key="companyName" prop="companyName">
          <template slot-scope="scope">
            <el-tooltip class="item" effect="dark" :content="scope.row.companyName" placement="top">
              <span>{{ hanldeOmit(scope.row.companyName) }}</span>
            </el-tooltip>
          </template>
        </el-table-column>
        <el-table-column label="乙方负责人" align="center" key="companyHead" prop="companyHead">
          <template slot-scope="scope">
            {{ handleName(scope.row.companyHead) }}
          </template>
        </el-table-column>
        <el-table-column label="乙方联系方式" align="center" key="companyPhone" prop="companyPhone">
          <template slot-scope="scope">
            {{ handlePhone(scope.row.companyPhone) }}
          </template>
        </el-table-column>
        <el-table-column label="签约人" align="center" key="nickName" prop="nickName">
          <template slot-scope="scope">
            {{ handleName(scope.row.nickName) }}
          </template>
        </el-table-column>
        <el-table-column label="签约金额" align="center" key="serverAmount" prop="serverAmount">
          <template slot-scope="scope">
            {{ parseInt(scope.row.serverAmount) + "元" }}
          </template>
        </el-table-column>
        <el-table-column label="操作时间" align="center" key="createTime" prop="createTime"/>
        <el-table-column
          label="操作"
          align="center"
          width="160"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              v-hasPermi="['manage:serve:list']"
              @click="getDetailInfo(scope.row.serverId)"
            >查看
            </el-button>
<!--            <el-button-->
<!--              size="mini"-->
<!--              type="text"-->
<!--              icon="el-icon-edit"-->
<!--              v-hasPermi="['manage:serve:upload']"-->
<!--              ><a :href="scope.row.annexUrl" target="view_window">下载</a>-->
<!--            </el-button>-->
          </template>
        </el-table-column>

      </el-table>

      <!--     分页操作-->
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>

    <!--详情页-->
    <div v-show=checkInfo>
      <div>
        <el-form ref="form" :model="form" label-width="140px">
          <h4 class="form-header">合同信息</h4>
          <el-row>
            <el-col :span="6" :offset="2">
              <el-form-item label="合同名称:" prop="contractName">
                <div>
                  {{ form.contractName }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="所属物业:" prop="estateName">
                <div>
                  {{ form.estateName }}
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="2">
              <el-form-item label="签约人:" prop="nickName">
                <div>
                  {{ form.nickName }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="签约人联系方式:" prop="phonenumber">
                <div>
                  {{ form.phonenumber }}
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="2">
              <el-form-item label="甲方公司:" prop="estateName">
                <div>
                  {{ form.estateName }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="乙方公司:" prop="companyName">
                <div>
                  {{ form.companyName }}
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="2">
              <el-form-item label="甲方负责人:" prop="legal">
                <div>
                  {{ form.legal }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="乙方负责人:" prop="companyHead">
                <div>
                  {{ form.companyHead }}
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="2">
              <el-form-item label="甲方联系方式:" prop="hotline">
                <div>
                  {{ form.hotline }}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="乙方联系方式:" prop="companyPhone">
                <div>
                  {{ form.companyPhone }}
                </div>
              </el-form-item>
            </el-col>
          </el-row>


          <h4 class="form-header">合同附件</h4>
          <el-row>
            <el-col :span="6" :offset="1.5">
              <el-form-item label="合同图片:" prop="annexUrl">
                <el-image
                  style="width: 200px; height: 300px"
                  :src="form.imageUrl"
                  fit="fit"/>
                <el-button
                  style="margin-top: 20px"
                  size="small"
                  type="text"
                  icon="el-icon-edit"
                  v-hasPermi="['manage:serve:upload']"
                ><a :href="form.annexUrl" target="view_window">附件下载</a>
                </el-button>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <div slot="footer" class="dialog-footer" style="float: right;margin-bottom: 30px" v-show="close">
        <el-button type="primary" @click="cancel">确定</el-button>
        <el-button style="margin-left: 40px" @click="cancel">取 消</el-button>
      </div>
    </div>

    <!--新增合同信息-->
    <div v-show=checkAddView>
      <!--添加-->
      <div>
        <el-form ref="insertform" :model="insertform" :rules="rules" label-width="150px">
          <h4 class="form-header"><b>合同信息</b></h4>
          <el-row>
            <el-col :span="6" :offset="2">
              <el-form-item label="合同名称:" prop="contractName">
                <el-input
                  v-model="insertform.contractName"
                  placeholder="请输入合同名"
                  style="width: 250px">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="合同编号:" prop="contractNo">
                <el-input
                  v-model="insertform.contractNo"
                  placeholder="请输入合同编号"
                  style="width: 250px">
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="2">
              <el-form-item label="乙方公司:" prop="companyName">
                <el-input
                  v-model="insertform.companyName"
                  placeholder="请输入乙方公司名"
                  style="width: 250px">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="乙方负责人:" prop="companyHead">
                <el-input
                  v-model="insertform.companyHead"
                  placeholder="请输入乙方负责人姓名"
                  style="width: 250px">
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="2">
              <el-form-item label="乙方联系方式:" prop="companyPhone">
                <el-input
                  v-model="insertform.companyPhone"
                  placeholder="请输入乙方联系方式"
                  style="width: 250px">
                </el-input>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="签约金额:" prop="serverAmount">
                <el-input
                  v-model="insertform.serverAmount"
                  placeholder="请输入签约金额"
                  style="width: 250px">
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="2">
              <el-form-item label="甲方公司:" prop="estateId">
                <el-select
                  v-model="insertform.estateId"
                  placeholder="请选择所在物业"
                  style="width: 250px"
                  @change="changeEstate">
                  <el-option
                    v-for="item in estateList"
                    :key="item.estateId"
                    :label="item.estateName"
                    :value="item.estateId">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>


            <el-col :span="6" :offset="2">
              <el-form-item label="签约人:" prop="userId" v-show="insertform.estateId!==undefined">
                <el-select
                  v-model="insertform.userId"
                  placeholder="请选择签约人"
                  style="width: 250px">
                  <el-option
                    v-for="item in userIdList"
                    :key="item.userId"
                    :label="item.nickName"
                    :value="item.userId">
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="2">
              <el-form-item label="甲方负责人:" v-show="insertform.estateId!==undefined">
                <el-input
                  v-model="company.legal"
                  style="width: 250px"
                  disabled>
                </el-input>
              </el-form-item>
            </el-col>


            <el-col :span="6" :offset="2">
              <el-form-item label="甲方联系方式:" v-show="insertform.estateId!==undefined">
                <el-input
                  v-model="company.hotline"
                  style="width: 250px"
                  disabled>
                </el-input>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="2">
              <el-form-item label="合同图片:" prop="imageUrl">
                <el-upload
                  class="avatar-uploader"
                  ref="imageUrl"
                  :action="imgUrl"
                  :show-file-list="true"
                  :on-success="(res,file)=>{handleFileSuccess(res,file,0)}"
                  :on-change="beforeImgUpload"
                  :auto-upload="true"
                  :headers="headers"
                  :limit="1">
                  <i class="el-icon-plus avatar-uploader-icons">点我上传</i>
                </el-upload>
              </el-form-item>
            </el-col>
          </el-row>

          <h4 class="form-header">合同附件</h4>

          <el-row>
            <el-col :span="6" :offset="2">
              <el-form-item label="合同附件:" prop="annexUrl">
                <el-upload
                  class="upload-file-uploader"
                  ref="annexUrl"
                  :action="imgUrl"
                  :show-file-list="true"
                  :on-success="(res,file)=>{handleFileSuccess(res,file,1)} "
                  :on-change="beforeFileUpload"
                  :auto-upload="true"
                  :headers="headers"
                  :limit="1">
                  <!-- 上传按钮 -->
                  <el-button size="mini" type="primary" style="margin-right: 88%">+ 新增合同</el-button>
                  <!-- 上传提示 -->
                  <div class="el-upload__tip" slot="tip">
                    请上传
                    <template > 大小不超过 <b style="color: #f56c6c">50MB</b> </template>
                    <template > 格式为 <b style="color: #f56c6c">doc 、docx 、wps 、pdf 、xls 、xlsx 或者zip rar格式的压缩文件 </b> </template>
                    的文件
                  </div>
                </el-upload>
              </el-form-item>
            </el-col>
          </el-row>

        </el-form>
        <div slot="footer" class="dialog-footer" style="float: right;margin-bottom: 30px" v-show="close">
          <el-button type="primary" @click="submitForm" v-hasPermi="['manage:serve:add']">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import {
  getServeList,
  getDetailInfo,
  getServeSelect,
  addServe,
  getUserInfo,
  delServe
} from "@/api/estate/manage/contract/serve";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {getToken} from "@/utils/auth";
import {desensitizationName, desensitization} from '@/utils/common'

export default {
  name: "lease",
  data() {
    return {
      imgUrl: process.env.VUE_APP_BASE_API + "/file/upload",
      //上传图片的信息
      options: {
        avatar: "",
        cardBack: "",
        cardFont: "",
        cardFontImg: "",
        faceUrlImg: "",
        cardBackImg: "",
      },
      checkAddView: false,
      //详情页控制显隐
      checkInfo: false,
      // 选中数组
      ids: [],
      //选中名字数组
      names: [],
      // 非单个禁用
      single: true,
      // 非多个禁用
      multiple: true,
      //证件类型字典
      certificateOptions: [],
      // 日期范围
      dateRange: [],
      //模糊查询显隐
      showSearch: true,
      //查询到的列表参数
      tableList: [],
      //新增提交按钮显隐
      close: true,
      //详情页显隐
      checkView: true,
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      open: false,
      // 甲方公司信息
      company:{},
      //token头
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      // 模糊查询数据
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        //乙方公司名
        companyName: undefined,
        //合同名
        contractName: undefined,
        //签约人名
        nickName: undefined,
        dateRange: undefined
      },
      // 表单参数
      form: {},
      //新增页面数据
      insertform: {
        userId: undefined,
      },
      //物业集合
      estateList: [],
      //签约人集合
      userIdList: [],
      //表单校验规则
      rules: {
        contractName: [
          {required: true, message: '合同名称不可为空', trigger: 'blur'},
        ],
        contractNo: [
          {required: true, message: '合同编号不可为空', trigger: 'blur'},
        ],
        companyName: [
          {required: true, message: '请选择乙方公司', trigger: 'blur'},
        ],
        companyHead: [
          {required: true, message: '请输入乙方公司负责人', trigger: 'blur'},
          {pattern: /^[\u4E00-\u9FFF\u3400-\u4DBF\uF900-\uFAFF\·]{2,20}$/, message: "姓名格式不对", trigger: "blur"}
        ],
        companyPhone: [
          {required: true, message: '请输入乙方联系方式', trigger: ['blur','change'] },
          {pattern: /^1[3456789]\d{9}$/, message: "手机号格式不对", trigger: "blur"},
        ],
        serverAmount: [
          {required: true, message: '请输入签约金额', trigger: 'blur'},
          {
            pattern: /^(([1-9]{1}\d{0,7})|(0{1}))(\.\d{1,2})?$/,
            message: "请输入合法的金额数字，最多两位小数",
            trigger: "blur"
          }
        ],
        estateId: [
          {required: true, message: '请选择甲方公司', trigger: 'blur'},
        ],
        userId: [
          {required: true, message: '请选择签约人', trigger: 'blur'},
        ],
        annexUrl: [
          {required: true, message: '附件不能为空', trigger: 'blur'},
        ],
        imageUrl: [
          {required: true, message: '合同图片不能为空', trigger: 'blur'},
        ],
      },
    };
  },
  created() {
    this.getList();
  },
  methods: {
    //详情按钮
    getDetailInfo(serverId) {
      //打开详情页面，并置空reset
      this.reset();
      this.checkView = false;
      this.checkInfo = true;
      this.queryParams = {};
      //请求接口数据，数据铺上
      getDetailInfo(serverId).then(response => {
        this.form = response.data;
      })
    },

    //下拉框改变
    changeEstate(value) {
      getUserInfo(value).then(res => {
        this.userIdList = res.data.userList
        this.company=res.data.estateInfo
      })
    },

    /** 查询列表 */
    getList() {
      this.loading = true;
      getServeList(this.queryParams).then(response => {
          this.tableList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },

    //姓名脱敏
    handleName(name) {
      return desensitizationName(name)
    },

    //处理超长变为省略号
    hanldeOmit(data) {
      if (data.length > 9) {
        return data.substring(0, 8) + "..."
      }
      return data;
    },

    //脱敏手机号
    handlePhone(phone) {
      return desensitization(phone, 3, 7)
    },

    /** 删除按钮操作 */
    handleDelete() {
      this.$confirm('是否确认删除所选中的的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(() => {
        delServe(this.ids).then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
      })
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.serverId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 取消按钮
    cancel() {
      this.checkView = true;
      this.checkAddView = false;
      this.checkInfo = false;
      this.insertform = {},
        this.$refs.annexUrl.clearFiles()
      this.$refs.imageUrl.clearFiles()
      this.reset();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.checkView = false;
      this.checkAddView = true;
      getServeSelect().then((res) => {
        this.estateList = res.data
      })
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["insertform"].validate(valid => {
        if (valid) {
          addServe(this.insertform).then(response => {
            this.$message.success("新增成功");
            this.checkView = true;
            this.checkAddView = false;
            this.$refs.annexUrl.clearFiles()
            this.$refs.imageUrl.clearFiles()
            this.getList();
          });
          this.resetForm("insertform");
        } else {
          return false;
        }
      })

    },
    /** 重置按钮操作 */
    resetQuery() {
      this.dateRange = [];
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.queryParams.pageNum = 1;
      this.getList();
    },
    // 表单重置
    reset() {
      this.form = {};
      this.resetForm("form");
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

    //文件上传前校验格式
    beforeFileUpload(file) {
      //上传文件的格式
      var suffix = file.name.substring(file.name.lastIndexOf("."))
      const idJPG = (suffix === '.doc') || (suffix === '.docx') || (suffix === '.wps')
        || (suffix === '.pdf') || (suffix === '.xls') || (suffix === '.xlsx') || (suffix === '.zip') || (suffix === '.rar')
      //上传文件的大小
      const isLt = (file.size / 1024 / 1024) < 50;
      this.fileSize=isLt
      this.fileType=idJPG
      if (!idJPG) {
        this.$message.error("上传的文件只能是.doc 、.docx 、.wps 、.pdf 、.xls 、.xlsx 或者.zip .rar格式的压缩文件");
        this.$refs.annexUrl.clearFiles()
        return false;
      }
      if (!isLt) {
        this.$message.error("上传的文件大小不能超过50MB");
        this.$refs.annexUrl.clearFiles()
        return false;
      }
      return true;
    },

    //图片上传前校验格式
    beforeImgUpload(file) {
      //上传文件的格式
      var suffix = file.name.substring(file.name.lastIndexOf("."))
      const idJPG = (suffix === '.jpg') || (suffix === '.png');
      //上传文件的大小
      const isLt2M = (file.size / 1024 / 1024) < 5;
      if (!idJPG) {
        this.$message.error("上传的图片只能是png ,jpg 格式的图片");
        this.$refs.annexUrl.clearFiles()
        return false;
      }
      if (!isLt2M) {
        this.$message.error("上传的图片大小不能超过5MB");
        this.$refs.annexUrl.clearFiles()
        return false;
      }
      return true;
    },

    //文件上传成功方法
    handleFileSuccess(res, file, index) {
      if (res.code === 200) {
        if (index == 0) {
          this.insertform.imageUrl = res.data.url
        } else if (index == 1) {
          this.insertform.annexUrl = res.data.url
        }
      }
    }
  }
};
</script>


<style>

.dev-col {
  height: 500px;
  overflow-y: scroll;
}

.dev-col::-webkit-scrollbar {
  display: none;
}

.el-table__body-wrapper {
  z-index: 2
}

.el-upload--picture-card {
  width: 100%
}

.el-upload--text {
  width: 100%;
  text-align: center;
}

.avatar .el-upload--picture-card {
  width: 50%
}

.owner-avatar .el-upload {
  cursor: pointer;
  position: relative;
  overflow: hidden;
}

.avatar-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  height: 180px;
  width: 250px;
}


.avatar-uploader2 .el-upload {
  /*border: 1px dashed #bebfc3;
  border-radius: 6px;*/
  cursor: pointer;
  position: relative;
  overflow: hidden;
  height: 180px;
}

.owner-avatar .el-upload:hover {
  border-color: #409EFF;
}

.avatar {
  width: 178px;
  height: 178px;
  display: block;
}

.avatar-uploader-icons {
  font-size: 15px;
  color: #8c939d;
  width: 250px;
  height: 178px;
  line-height: 178px;
  text-align: center;
}

.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 400px;
  height: 200px;
  line-height: 178px;
  text-align: center;
}

.avatar {
  height: 200px;
  width: 110%
}

.avatar2 {
  height: 200px;
  width: 110%
}

.avatar {
  height: 200px;
  width: 110%
}

.peopleImg {
  height: 180px;
  width: 100%;
}

.peopleImg2 {
  height: 180px;
  width: 45%;
}

.headCenter {
  position: relative;
  button: 300px;
}

.table-div {
  height: 30vh;
  overflow-y: scroll;
  box-shadow: 0 -5px 5px #EAEAEA;
  padding: 15px 20px 20px 20px;
}

.table-div::-webkit-scrollbar {
  display: none;
}

</style>

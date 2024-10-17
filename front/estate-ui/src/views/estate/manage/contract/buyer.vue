<template>
  <div class="app-container">
    <!--综合页面信息-->
    <div v-show=checkView>
      <!--模糊查询-->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
        <el-form-item label="合同名称" prop="contractName">
          <el-input
            v-model="queryParams.contractName"
            placeholder="请输入合同名称"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>

        <el-form-item label="公司名称" prop="companyName">
          <el-input
            v-model="queryParams.companyName"
            placeholder="请输入甲方/乙方公司名称"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>

        <el-form-item label="负责人" prop="companyPerson">
          <el-input
            v-model="queryParams.companyPerson"
            placeholder="请输入甲方/乙方负责人"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>

        <el-form-item label="手机号码" prop="companyPhone">
          <el-input
            v-model="queryParams.companyPhone"
            placeholder="请输入甲方/乙方手机号码"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>

<!--        <el-form-item label="签约时间">
          <el-date-picker
            v-model="dateRange"
            style="width: 240px"
            value-format="yyyy-MM-dd"
            type="daterange"
            range-separator="-"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            @keyup.enter.native="handleQuery"
          ></el-date-picker>
        </el-form-item>-->

        <el-form-item label="采购人" prop="staffName">
          <el-input
            v-model="queryParams.staffName"
            placeholder="请输入采购人姓名"
            clearable
            style="width: 240px"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>



        <el-form-item>
          <el-button type="primary" icon="el-icon-search" size="mini" @click="handleQuery">搜 索</el-button>
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
            v-hasPermi="['manage:buyer:add']"
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
            v-hasPermi="['manage:buyer:remove']"
          >删除
          </el-button>
        </el-col>
        <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
      </el-row>
      <!--表单-->
      <el-table v-loading="loading" :data="buyerList" row-key="procurementId" @selection-change="handleSelectionChange">
        <el-table-column type="selection" width="50" align="center"/>
        <el-table-column label="合同编号" align="center" key="contractNo" prop="contractNo" :show-overflow-tooltip="true"/>
        <el-table-column label="所属物业" align="center" key="estateName" prop="estateName" :show-overflow-tooltip="true"/>
        <el-table-column label="所属小区" align="center" key="communityName" prop="communityName" :show-overflow-tooltip="true"/>
        <el-table-column label="甲方负责人" align="center" key="legal" prop="legal" />
        <el-table-column label="甲方联系方式" align="center" key="hotline" prop="hotline" >
          <template scope="scope">
            <span>{{ phoneFormatter(scope.row.hotline) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="乙方公司" align="center" key="companyName" prop="companyName" :show-overflow-tooltip="true" />
        <el-table-column label="乙方负责人" align="center" key="companyHead" prop="companyHead" />
        <el-table-column label="乙方联系方式" align="center" key="companyPhone" prop="companyPhone">
          <template scope="scope">
            <span>{{ phoneFormatter(scope.row.companyPhone) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="采购人" align="center" key="staffName" prop="staffName"/>
        <el-table-column label="采购设备" align="center" key="materialName" prop="materialName" :show-overflow-tooltip="true"/>
        <el-table-column label="实际金额" align="center" key="actuallyAmount" prop="actuallyAmount"/>
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
              @click="checkBuyerInfo(scope.row)"
              v-hasPermi="['manage:buyer:query']"
            >查看
            </el-button>
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
    <!--新增合同信息-->
    <div v-show=checkAddView>
      <!--添加-->
      <div >
        <el-form ref="form" :model="form" :rules="rules" label-width="100px">
          <h4 class="form-header">合同信息</h4>
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="合同名称" prop="contractName">
                <el-input v-model="form.contractName" placeholder="请输入合同名称" maxlength="30"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="所属物业" prop="estateId">
                <el-select v-model="form.estateId" placeholder="请选择物业" style="width: 100%"
                value-key="estateId"   @change="estDopt(form.estateId)" >
                  <el-option
                    v-for="item in estateList"
                    :key="item.estateId"
                    :label="item.estateName"
                    :value="item.estateId"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="所属小区" prop="communityId">
                <el-select v-model="form.communityId"
                           placeholder="请选择所属小区"
                           style="width: 100%"
                >
                  <el-option
                    v-for="item in communityList"
                    :key="item.communityId"
                    :label="item.communityName"
                    :value="item.communityId"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="采购人" prop="staffId">
                <el-select v-model="form.staffId"
                           placeholder="请选择采购人"
                           style="width: 100%"
                           @change="getStaffPhoneInfo(form.staffId)"
                >
                  <el-option
                    v-for="item in staffList"
                    :key="item.staffId"
                    :label="item.staffName"
                    :value="item.staffId"
                  >
                  </el-option>
                </el-select>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="采购人电话" prop="staffPhone">
                <el-input v-model="form.staffPhone" placeholder="采购人电话" style="width: 100%"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="甲方负责人" prop="legal">
                <el-input v-model="form.legal" placeholder="请选择物业" style="width: 100%"/>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="甲方电话" prop="hotline">
                <el-input v-model="form.hotline" placeholder="请选择业主手机号" style="width: 100%"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="乙方公司" prop="companyName">
                <el-input v-model="form.companyName" placeholder="请输入公司名称" maxlength="11"/>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="乙方负责人" prop="companyHead">
                <el-input v-model="form.companyHead" placeholder="请输入负责人" maxlength="11"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="乙方电话" prop="companyPhone">
                <el-input v-model="form.companyPhone" placeholder="请输入乙方电话" maxlength="11"/>
              </el-form-item>
            </el-col>
          </el-row>

          <h4 class="form-header">采购信息</h4>
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="名称" prop="materialName">
                <el-input v-model="form.materialName" placeholder="请输入物品名称" maxlength="11"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="数量" prop="materialNum" >
                <el-input v-model="form.materialNum" placeholder="请输入数量" maxlength="11" @change="getSum(form.materialNum,form.unitPrice)"/>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="单价" prop="unitPrice">
<!--                <el-input v-model="form.unitPrice" placeholder="请输入物品单价" maxlength="11" @change="getSum(form.materialNum,form.unitPrice)"/>-->
                <el-input-number v-model="form.unitPrice" :min="0" :max="99999999" :precision="2" :step="1" @change="getSum(form.materialNum,form.unitPrice)"></el-input-number>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="总价" prop="totalAmount">
                <el-input
                  v-model="form.totalAmount"
                  placeholder=""
                  maxlength="11"
                  readonly
                />
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="规格" prop="specification">
                <el-input v-model="form.specification" placeholder="请输规格" maxlength="11"/>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="实际金额" prop="actuallyAmount">
<!--                <el-input v-model="form.actuallyAmount" placeholder="请输实际金额" maxlength="8"/>-->
                <el-input-number v-model="form.actuallyAmount" :min="0" :max="99999999" :precision="2" :step="1"></el-input-number>
              </el-form-item>
            </el-col>
          </el-row>
          <h4 class="form-header">合同附件</h4>
          <el-row>
            <el-col :span="6" :offset="1">
<!--              <el-form-item label="合同附件" prop="annexUrl" label-position="left">
                <el-upload
                  class="el-upload&#45;&#45;picture-card avatar-uploader2"
                  :action="url"
                  :show-file-list="false"
                  accept="image/*"
                  :before-upload="beforeUpload"
                  :on-success="handleUploadPhoto">
                  <img v-if="this.options.buyerImg" :src="this.options.buyerImg" class="avatar">
                  <i slot="default" class="el-icon-plus" v-else></i>
                </el-upload>
              </el-form-item>-->
              <el-form-item label="合同附件" prop="annexUrl">
                <div class="upload-file">
                  <el-upload
                    multiple
                    :action="uploadFileUrl"
                    :before-upload="handleBeforeUpload"
                    :file-list="fileList"
                    :limit="limit"
                    :on-error="handleUploadError"
                    :on-exceed="handleExceed"
                    :on-success="handleUploadSuccess"
                    :show-file-list="false"
                    :headers="headers"
                    class="upload-file-uploader"
                    ref="lease"
                  >
                    <!-- 上传按钮 -->
                    <el-button size="mini" type="primary">+ 新增合同</el-button>
                    <!-- 上传提示 -->
                    <div class="el-upload__tip" slot="tip" v-if="showTip">
                      请上传
                      <template v-if="fileSize"> 大小不超过 <b style="color: #f56c6c">{{ fileSize }}MB</b> </template>
                      <template v-if="fileType"> 格式为 <b style="color: #f56c6c">{{ fileType.join("/") }}</b> </template>
                      的文件
                    </div>
                  </el-upload>

                  <!-- 文件列表 -->
                  <transition-group class="upload-file-list el-upload-list el-upload-list--text" name="el-fade-in-linear" tag="ul">
                    <li :key="file.url" class="el-upload-list__item ele-upload-list__item-content" v-for="(file, index) in fileList">
                      <el-link :href="file.url" :underline="false" target="_blank">
                        <span class="el-icon-document"> {{ getFileName(file.name) }} </span>
                      </el-link>
                      <div class="ele-upload-list__item-content-action">
                        <el-link :underline="false" @click="handleDeleteFile(index)" type="danger">删除</el-link>
                      </div>
                    </li>
                  </transition-group>
                </div>
              </el-form-item>

            </el-col>
            </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer" style="float: right;margin-bottom: 30px" v-show="close">
          <el-button type="primary" @click="submitForm">确 定</el-button>
          <el-button @click="cancel">取 消</el-button>
        </div>
      </div>
    </div>

    <!--详情页-->
    <div v-show=checkInfo>
      <div >
        <el-form ref="forms" :model="form" label-width="100px">
          <h4 class="form-header">合同信息</h4>
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="合同名称" >
                <div>
                  {{form.contractName}}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="所属物业">
                <div>
                  {{form.estateName}}
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="所属小区">
                <div>
                  {{form.communityName}}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="采购人">
                <div>
                  {{form.staffName}}
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="采购人电话">
                <div>
                  {{form.staffPhone}}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="甲方负责人">
                <div>
                  {{form.legal}}
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="甲方电话">
                <div>
                  {{form.hotline}}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="乙方公司">
                <div>
                  {{form.companyName}}
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="乙方负责人">
                <div>
                  {{form.companyHead}}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="乙方电话">
                <div>
                  {{form.companyPhone}}
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <h4 class="form-header">采购信息</h4>
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="名称" prop="materialName">
                <div>
                  {{form.materialName}}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="数量" prop="materialNum">
                <div>
                  {{form.materialNum}}
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="单价" prop="unitPrice">
                <div>
                  {{form.unitPrice}}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="总价" prop="totalAmount">
                <div>
                  {{form.totalAmount}}
                </div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="规格" prop="specification">
                <div>
                  {{form.specification}}
                </div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="实际金额" prop="actuallyAmount">
                <div>
                  {{form.actuallyAmount}}
                </div>
              </el-form-item>
            </el-col>
          </el-row>
          <h4 class="form-header">合同附件</h4>
          <el-row>
            <el-col :span="6" :offset="1">
<!--              <el-form-item label="合同附件" prop="annexUrl" >
                <img :src="this.form.annexUrl" class="avatar">
              </el-form-item>-->
              <el-form-item label="合同下载：" class="theme-demo">
                <div>{{this.fileName}}</div>
                <a :href="this.form.annexUrl"  target="_blank">  合同文件下载 </a>
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
        <div slot="footer" class="dialog-footer" style="float: right;margin-bottom: 30px" v-show="close">
<!--          <el-button type="primary" @click="submitForm">确 定</el-button>-->
          <el-button @click="cancel">取 消</el-button>
        </div>
      </div>
    </div>

  </div>
</template>

<script>
import {uploadPicture} from "@/api/estate/manage/contract/tenant";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import {getCurrentList, getCurrentLists} from '@/api/estate/community';
import {addBuyer, listBuyer,getEstateList  ,getStaffList, delBuyer,getBuyerById} from "@/api/estate/manage/contract/buyer";
import {desensitization, equals} from "@/utils/common";
import {getToken} from "@/utils/auth";
export default {
  name: "buyer",
  props: {
    // 值
    value: [String, Object, Array],
    // 数量限制
    limit: {
      type: Number,
      default: 1,
    },
    // 大小限制(MB)
    fileSize: {
      type: Number,
      default: 5,
    },
    // 文件类型, 例如['png', 'jpg', 'jpeg']
    fileType: {
      type: Array,
      default: () => ["doc", "xls", "ppt", "txt", "pdf","jpg","jpeg"],
    },
    // 是否显示提示
    isShowTip: {
      type: Boolean,
      default: true
    }
  },
  data() {
    return {
      //文件名称
      fileName:"",
      number: 0,
      //头
      headers: {
        Authorization: "Bearer " + getToken(),
      },
      //上传doc文件的接口路径
      uploadFileUrl: process.env.VUE_APP_BASE_API + "/file/upload", // 上传文件服务器地址
      //上传成功回调函数
      uploadList: [],
      //文件名称
      name:'',
      //存储文件信息
      fileList:[],

      url: process.env.VUE_APP_BASE_API + "/estate/tenant/uploadPicture",
      //上传图片的信息
      options: {
        avatar: "",
        cardBack: "",
        annexUrl: "",
        buyerImg: "",
        faceUrlImg: "",
        cardBackImg: "",
      },
      // 表单校验
      rules: {

        contractName: [
          {required: true, message: "合同名称不能为空", trigger: "blur"},
          {pattern: /[^ \x22]+/, message: "不能输入空格", trigger: "blur"},
          {pattern: /^[\u4e00-\u9fa5]{0,}$/, message: "格式错误", trigger: "blur"},
        ],
        estateId: [
          {required: true, message: "请选择物业", trigger: "change"},
        ],
        communityId: [
          {required: true, message: "请选择小区", trigger: "change"},
        ],
        staffId: [
          {required: true, message: "请选择采购人", trigger: "change"},
        ],
        staffPhone: [
          //1、手机号码验证正则
          {required: true, message: "请选择采购人电话", trigger: "blur"},
          {pattern: /^1[3456789]\d{9}$/, message: "手机号格式不对", trigger: "blur"},
        ],
        legal: [
          {required: true, message: "请输入甲方负责人", trigger: "blur"},
          {pattern: /[^ \x22]+/, message: "不能输入空格", trigger: "blur"},
          {pattern: /^[\u4e00-\u9fa5]{0,}$/, message: "格式错误", trigger: "blur"},
        ],
        hotline: [
          //1、手机号码验证正则
          {required: true, message: "请输入甲方负责人电话", trigger: "blur"},
          {pattern: /^1[3456789]\d{9}$/, message: "手机号格式不对", trigger: "blur"},
        ],
        companyName: [
          {required: true, message: "乙方公司名称不能为空", trigger: "blur"},
        ],
        companyHead: [
          {required: true, message: "乙方负责人不能为空", trigger: "blur"},
          {pattern: /[^ \x22]+/, message: "不能输入空格", trigger: "blur"},
          {pattern: /^[\u4e00-\u9fa5]{0,}$/, message: "格式错误", trigger: "blur"},
        ],
        companyPhone: [
          //1、手机号码验证正则
          {required: true, message: "请输入乙方电话", trigger: "blur"},
          {pattern: /^1[3456789]\d{9}$/, message: "手机号格式不对", trigger: "blur"},
        ],
        materialName: [
          {required: true, message: "采购名称不能为空", trigger: "blur"},
          {pattern: /[^ \x22]+/, message: "不能输入空格", trigger: "blur"},
          {pattern: /^[\u4e00-\u9fa5]{0,}$/, message: "格式错误", trigger: "blur"},
        ],
        materialNum: [
          {required: true, message: "数量不能为空", trigger: "blur"},
          {pattern: /^[0-9]+$/, message: "书写不正确", trigger: "blur"},
        ],
        unitPrice: [
          {required: true, message: "请输入单价", trigger: "blur"},
          {pattern: /^[-\+]?\d+(\.\d+)?$/, message: "请输入数字", trigger: "blur"},
        ],
        specification: [
          {required: true, message: "规格不能为空", trigger: "blur"},
   /*       {pattern: /^[0-9]+$/, message: "书写不正确", trigger: "blur"},*/
        ],
        actuallyAmount: [
          {required: true, message: "请输入实际金额", trigger: "blur"},
          {pattern: /^[-\+]?\d+(\.\d+)?$/, message: "请输入实际金额", trigger: "blur"},
        ],
        annexUrl: [
          {required: true, message: "请上传合同附件", trigger: "blur"},

        ]
      },
      //员工数据
      staffList:[],
      //物业数据
      estateList:[],
      checkAddView: false,
      //小区数据
      communityList: [],
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
      buyerList: [],
      //新增提交按钮显隐
      close: true,
      //详情页显隐
      checkView: true,
      // 遮罩层
      loading: true,
      // 总条数
      total: 0,

      personLegal: '',
      personHotline:'',
      // 模糊查询数据
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        estateId:undefined,
        communityId: undefined,
        tenantName: undefined,
        tenantPhone: undefined,
      },
      resultArr:undefined,
      // 表单参数
      form: {
        totalAmount:0
      },
    };
  },

  created() {
    this.getList();
  },
  watch: {
    value: {
      handler(val) {
        if (val) {
          let temp = 1;
          // 首先将值转为数组
          const list = Array.isArray(val) ? val : this.value.split(',');
          // 然后将数组转为对象数组
          this.fileList = list.map(item => {
            if (typeof item === "string") {
              item = { name: item, url: item };
            }
            item.uid = item.uid || new Date().getTime() + temp++;
            return item;
          });
        } else {
          this.fileList = [];
          return [];
        }
      },
      deep: true,
      immediate: true
    }
  },
  computed: {
    // 是否显示提示
    showTip() {
      return this.isShowTip && (this.fileType || this.fileSize);
    },
  },

  methods: {
    // 上传前校检格式和大小
    handleBeforeUpload(file) {
      // 校检文件类型
      if (this.fileType) {
        let fileExtension = "";
        if (file.name.lastIndexOf(".") > -1) {
          fileExtension = file.name.slice(file.name.lastIndexOf(".") + 1);
        }
        const isTypeOk = this.fileType.some((type) => {
          if (file.type.indexOf(type) > -1) return true;
          if (fileExtension && fileExtension.indexOf(type) > -1) return true;
          return false;
        });
        if (!isTypeOk) {
          this.$modal.msgError(`文件格式不正确, 请上传${this.fileType.join("/")}格式文件!`);
          return false;
        }
      }
      // 校检文件大小
      if (this.fileSize) {
        const isLt = file.size / 1024 / 1024 < this.fileSize;
        if (!isLt) {
          this.$modal.msgError(`上传文件大小不能超过 ${this.fileSize} MB!`);
          return false;
        }
      }
      this.$modal.loading("正在上传文件，请稍候...");
      this.number++;
      return true;
    },
    // 文件个数超出
    handleExceed() {
      this.$modal.msgError(`上传文件数量不能超过 ${this.limit} 个!`);
    },
    // 上传失败
    handleUploadError(err) {
      this.$modal.msgError("上传文件失败，请重试");
      this.$modal.closeLoading()
    },
    // 上传成功回调
    handleUploadSuccess(res, file) {
      if (res.code === 200) {
        this.uploadList.push({ name: res.data.url, url: res.data.url });
        this.uploadedSuccessfully();
      } else {
        this.number--;
        this.$modal.closeLoading();
        this.$modal.msgError(res.msg);
        this.$refs.lease.handleRemove(file);
        this.uploadedSuccessfully();
      }
    },
    // 删除文件
    handleDeleteFile(index) {
      this.fileList.splice(index, 1);
      this.$emit("input", this.listToString(this.fileList));
    },
    // 上传结束处理
    uploadedSuccessfully() {
      if (this.number > 0 && this.uploadList.length === this.number) {
        this.fileList = this.fileList.concat(this.uploadList);
        this.form.annexUrl=this.fileList[0].url;
        this.uploadList = [];
        this.number = 0;
        this.$emit("input", this.listToString(this.fileList));
        this.$modal.closeLoading();
      }
    },
    // 获取文件名称
    getFileName(name) {
      if (name.lastIndexOf("/") > -1) {
        return name.slice(name.lastIndexOf("/") + 1);
      } else {
        return "";
      }
    },
    // 对象转成指定字符串分隔
    listToString(list, separator) {
      let strs = "";
      separator = separator || ",";
      for (let i in list) {
        strs += list[i].url + separator;
      }
      return strs != '' ? strs.substr(0, strs.length - 1) : '';
    },
    //获取文件名称
    getLeaseFileName(leaseUrl){
      var file = leaseUrl.split("/");
      return file[file.length - 1];
    },

    /** 手机号脱敏 */
    phoneFormatter(phone){
      if(!equals(phone,undefined)){
        return desensitization(phone,3,7);
      }
    },
    /** 身份证号脱敏 */
    cardFormatter(card){
      if(!equals(card,undefined)){
        return desensitization(card,6,14);
      }
    },


    //查询当前物业名下的小区
    getCommunitys(deptId) {
      getCurrentLists(deptId).then(response => {
        this.communityList = response.data;
      })
    },
    /** 查询物业信息 */
    getEstate() {
      getEstateList().then(response => {
        this.estateList = response.data;
      })
    },
    //采购人电话获取
    getStaffPhoneInfo(val){
      //通过员工id，找出指定员工的电话
      let staff =this.staffList.filter((item)=>{
        return item.staffId === val
      });
      this.form.staffPhone=staff[0].staffPhone;
    },
    //通过物业id，获取甲方信息
    estDopt(val){
      //通过物业id去除物业对象
      let busEstate =this.estateList.filter((item)=>{
        return item.estateId === val
      });
      this.form.legal=busEstate[0].legal;
      this.form.hotline=busEstate[0].hotline;
      this.form.estateName=busEstate[0].estateName;
      this.getStaff(val);
      // 获取当前物业下的小区
      this.getCommunitys(val);
    },
          /** 根据物业id，查询员工表信息 */
    getStaff(id) {
      getStaffList(id).then(response => {
        this.staffList = response.data;

      })
    },
    //详情按钮
    checkBuyerInfo(row){
      //打开详情页面，并置空reset
      this.reset();
      this.checkView= false;
      this.checkInfo= true;
      //获取行id
      const buyerId = row.procurementId;
      //请求接口数据，数据铺上
      getBuyerById(buyerId).then(response => {
        this.form = response.data;
        this.fileName=this.getLeaseFileName(this.form.annexUrl);
      })


    },
    /** 查询列表 */
    getList() {
      this.loading = true;
      listBuyer(this.addDateRange(this.queryParams, this.dateRange)).then(response => {
          this.buyerList = response.rows;
          this.total = response.total;
          this.loading = false;
        }
      );
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      const buyerIds = row.procurementId || this.ids;
      this.$confirm('是否确认删除所选中的的数据项?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning"
      }).then(function () {
        return delBuyer(buyerIds);
      }).then(() => {
        this.getList();
        this.$modal.msgSuccess("删除成功");
      })
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map(item => item.procurementId)
      this.single = selection.length != 1
      this.multiple = !selection.length
    },
    // 取消按钮
    cancel() {
      this.checkView = true;
      this.checkAddView= false;
      this.checkInfo= false;
      this.form.annexUrl = "";
      this.options.buyerImg = "";
      this.reset();
    },
    /** 新增按钮操作 */
    handleAdd() {
      this.reset();
      this.getEstate();
      this.handleDeleteFile(0);//点击新增按钮删除文件
      this.form.totalAmount=0;
      this.checkView = false;
      this.checkAddView= true;
    },
    /** 添加按钮 */
    submitForm: function() {
      this.$refs["form"].validate(valid => {
        if (valid) {
          addBuyer(this.form).then(response => {
            this.$message.success("新增成功");
            this.checkView = true;
            this.checkAddView= false;
            this.form.annexUrl = "";
            this.options.buyerImg = "";
            this.getList();
          });
        }
      });
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
      this.form = {
        procurementId: undefined,
        contractName: undefined,
        estateId: undefined,
        staffId: undefined,
        staffPhone: undefined,
        legal: undefined,
        hotline: undefined,
        companyName: undefined,
        companyHead: undefined,
        companyPhone: undefined,
        materialName: undefined,
        materialNum: undefined,
        unitPrice: undefined,
        totalAmount: undefined,
        specification: undefined,
        actuallyAmount: undefined,
        annexUrl:undefined
      };
      this.resetForm("form");
    },
    // 上传预处理
    beforeUpload(file) {
      return true;
    },
    // 上传合同
    handleUploadPhoto(res, file) {
      let formData = new FormData();
      formData.append('file', file.raw)
      uploadPicture(formData).then(response => {
        this.form.annexUrl = response.imgPath;
        this.options.buyerImg = response.imgPath;
        this.$modal.msgSuccess("上传成功");
      });
    },
    //计算总价
    getSum(Num,Price) {

      this.form.totalAmount=0;
      if(Num==undefined || Price==undefined){
       return  this.form.totalAmount=0;
      }
      this.form.totalAmount=(Num*Price);
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

.upload-file-uploader {
  margin-bottom: 5px;
}


.theme-demo a {
  color: #3975C6;
}


</style>

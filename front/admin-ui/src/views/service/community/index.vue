<template>
  <div class="app-container">
    <div v-show="!open">
      <el-form
        :model="queryParams"
        ref="queryForm"
        :inline="true"
        v-show="showSearch"
        label-width="68px"
        class="cus-fm"
      >
        <el-form-item
          label="所属物业"
          prop="estateId"
          style="margin-top: 2.5px"
        >
          <treeselect
            v-model="queryParams.estateId"
            :options="deptOptions"
            id="estateId-tree"
            :searchable="false"
            style="width: 240px; height: 32px"
            :default-expand-level="Infinity"
            placeholder="请选择所属物业"
          />
        </el-form-item>
        <el-form-item label="社区名称" prop="communityName">
          <el-input
            v-model="queryParams.communityName"
            placeholder="请输入社区名称"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
          />
        </el-form-item>
        <el-form-item>
          <el-button
            type="primary"
            icon="el-icon-search"
            size="mini"
            @click="handleQuery"
            >搜索</el-button
          >
          <el-button icon="el-icon-refresh" size="mini" @click="resetQuery"
            >重置</el-button
          >
        </el-form-item>
      </el-form>

      <el-row :gutter="10" class="mb8 cus-fm">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['system:community:add']"
            >新增
          </el-button>
        </el-col>
        <right-toolbar
          :showSearch.sync="showSearch"
          @queryTable="getList"
        ></right-toolbar>
      </el-row>

      <el-table v-loading="loading" :data="communityList">
        <el-table-column
          label="所属物业"
          align="center"
          prop="estateName"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="社区名称"
          align="center"
          prop="communityName"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="地址"
          align="center"
          prop="address"
          :show-overflow-tooltip="true"
        />
        <el-table-column label="绿化率" align="center" prop="greenRatio">
          <template slot-scope="scope">
            <span>{{ scope.row.greenRatio }}%</span>
          </template>
        </el-table-column>
        <!--        <el-table-column label="总住户" align="center" prop="residentNum" />-->
        <el-table-column
          label="开发商"
          align="center"
          prop="developersName"
          :show-overflow-tooltip="true"
        />
        <el-table-column
          label="操作"
          align="center"
          class-name="small-padding fixed-width"
        >
          <template slot-scope="scope">
            <el-button
              size=":mini"
              type="text"
              icon="el-icon-edit"
              class="text-info"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:community:edit']"
              >修改</el-button
            >
            <el-button
              size=":mini"
              type="text"
              icon="el-icon-delete"
              class="text-danger"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:community:remove']"
              >删除</el-button
            >
            <el-button
              size=":mini"
              type="text"
              icon="el-icon-edit"
              class="text-warning"
              @click="handleSetHik(scope.row)"
              v-hasPermi="['system:community:addHik']"
              >海康配置</el-button
            >
            <!--            <el-dropdown size="mini" @command="(command) => handleCommandAdd(command, scope.row)" v-hasPermi="['system:community:addAbc',
             'system:community:addAli','system:community:addHik','system:community:addWechat']">
                <span class="el-dropdown-link" style="font-size: 14px">
                  <i class="el-icon-d-arrow-right el-icon&#45;&#45;right"></i>更多新增操作
                </span>
              <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="handleAddHik" icon="el-icon-plus"
                                  v-hasPermi="['system:community:addHik']">新增海康配置
                </el-dropdown-item>
                <el-dropdown-item command="handleAddWechat" icon="el-icon-plus"
                                  v-hasPermi="['system:community:addWechat']">新增微信配置
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>-->

            <!--            <el-dropdown size="mini" @command="(command) => handleCommand(command, scope.row)" v-hasPermi="['system:community:editAbc',
             'system:community:editAli','system:community:editHik','system:community:editWechat']">
                <span class="el-dropdown-link" style="font-size: 14px">
                  <i class="el-icon-d-arrow-right el-icon&#45;&#45;right"></i>更多修改操作
                </span>
              <el-dropdown-menu slot="dropdown">
              <el-dropdown-item command="handleEditHik" icon="el-icon-edit"
                                  v-hasPermi="['system:community:editHik']">修改海康配置
                </el-dropdown-item>
                <el-dropdown-item command="handleEditWechat" icon="el-icon-edit"
                                  v-hasPermi="['system:community:editWechat']">修改微信配置
                </el-dropdown-item>
              </el-dropdown-menu>
            </el-dropdown>-->
          </template>
        </el-table-column>
      </el-table>

      <pagination
        v-show="total > 0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
    <!-- 添加或修改海康信息对话框 -->
    <el-dialog
      :title="titleHik"
      :visible.sync="openHik"
      width="500px"
      append-to-body
    >
      <el-form
        ref="formHik"
        :model="formHik"
        :rules="rulesHik"
        label-width="80px"
      >
        <el-form-item label="IP" prop="hikIp">
          <el-input v-model="formHik.hikIp" />
        </el-form-item>
        <el-form-item label="端口" prop="hikPort">
          <el-input v-model="formHik.hikPort" />
        </el-form-item>
        <el-form-item label="密钥ID" prop="hikAk">
          <el-input v-model="formHik.hikAk" />
        </el-form-item>
        <el-form-item label="密钥" prop="hikSk">
          <el-input v-model="formHik.hikSk" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-row>
          <el-col :span="18">
            <el-button @click="cancelHik">取 消</el-button>
          </el-col>
          <el-col :span="4" :offset="2">
            <el-button type="primary" @click="submitFormHik">确 定</el-button>
          </el-col>
        </el-row>
      </div>
    </el-dialog>

    <!-- 添加或修改微信相关信息对话框 -->
    <el-dialog
      :title="titleWechat"
      :visible.sync="openWechat"
      width="600px"
      append-to-body
    >
      <h4 class="form-header">微信相关信息</h4>
      <el-form
        ref="formWechat"
        :model="formWechat"
        :rules="rulesWechat"
        label-width="130px"
      >
        <el-form-item label="APPID" prop="appId">
          <el-input v-model="formWechat.appId" placeholder="请输入appid" />
        </el-form-item>

        <el-form-item label="小程序密钥" prop="appSecret">
          <el-input
            v-model="formWechat.appSecret"
            placeholder="请输入小程序密钥"
          />
        </el-form-item>

        <el-form-item label="商户号" prop="mchId">
          <el-input v-model="formWechat.mchId" placeholder="请输入商户号" />
        </el-form-item>

        <el-form-item label="证书序列号" prop="serialNumber">
          <el-input
            v-model="formWechat.serialNumber"
            placeholder="请输入证书序列号"
          />
        </el-form-item>

        <el-form-item label="APIv3密钥" prop="apiKey">
          <el-input v-model="formWechat.apiKey" placeholder="请输入APIv3密钥" />
        </el-form-item>

        <el-form-item label="微信公众号appid" prop="publicAppId">
          <el-input
            v-model="formWechat.publicAppId"
            placeholder="请输入微信公众号appid"
          />
        </el-form-item>

        <el-form-item label="微信公众号密钥" prop="publicSecret">
          <el-input
            v-model="formWechat.publicSecret"
            placeholder="请输入微信公众号密钥"
          />
        </el-form-item>

        <el-form-item prop="privateUrl" label="商户私钥">
          <el-upload
            accept=".pem,.p12,.pdf, .doc, .docx"
            class="upload-demo"
            action="#"
            :show-file-list="false"
            :before-upload="beforeUpload"
            :on-change="uploadWechatFile"
            :auto-upload="false"
          >
            <el-button slot="trigger" size="small" type="primary"
              >选取文件</el-button
            >
          </el-upload>
        </el-form-item>

        <el-form-item>
          <span v-if="formWechat.privateUrl">{{ formWechat.privateUrl }}</span>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-row>
          <el-col :span="18">
            <el-button @click="cancelWechat">取 消</el-button>
          </el-col>
          <el-col :span="4">
            <el-button type="primary" @click="submitFormWechat"
              >确 定</el-button
            >
          </el-col>
        </el-row>
      </div>
    </el-dialog>

    <!-- 添加或修改小区管理对话框 -->
    <div v-show="open">
      <el-form ref="form" :model="form" :rules="rules" label-width="130px">
        <el-row>
          <el-col :span="6" :offset="5">
            <el-form-item label="所属物业" prop="estateId">
              <treeselect
                v-model="form.estateId"
                :options="deptOptions"
                :show-count="true"
                :default-expand-level="Infinity"
                @input="changeEstateValid"
                placeholder="请选择所属物业"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="所属地区" prop="areaId">
              <treeselect
                v-model="form.areaId"
                :options="areaOptions"
                :show-count="true"
                :default-expand-level="Infinity"
                @input="changeAreaValid"
                :disable-branch-nodes="true"
                placeholder="请选择所属地区"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <h4 class="form-header">基本信息</h4>
        <el-row>
          <el-col :span="6" :offset="0">
            <el-form-item label="社区名称" prop="communityName">
              <el-input
                v-model="form.communityName"
                placeholder="请输入社区名称"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="社区别名" prop="communityAlias">
              <el-input
                v-model="form.communityAlias"
                placeholder="请输入社区别名"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="容积率(%)" prop="plotRatio">
              <el-input-number
                v-model="form.plotRatio"
                placeholder="请输入容积率"
                :min="0"
                :max="100"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="0">
            <el-form-item label="开发商" prop="developersName">
              <el-input
                v-model="form.developersName"
                placeholder="请输入开发商"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="总户数" prop="residentNum">
              <el-input-number
                v-model="form.residentNum"
                placeholder="请输入总户数"
                :min="0"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="登记户数" prop="registerNum">
              <el-input-number
                v-model="form.registerNum"
                placeholder="请输入登记户数"
                :min="0"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="0">
            <el-form-item label="绿化率(%)" prop="greenRatio">
              <el-input
                v-model="form.greenRatio"
                @input="handleInput"
                placeholder="请输入绿化率"
                :min="0"
                :max="30"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="纬度" prop="latitude">
              <el-input
                v-model="form.latitude"
                placeholder="纬度"
                id="lat"
                name="lat"
                :min="0"
                :max="30"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="经度" prop="longitude">
              <el-input
                v-model="form.longitude"
                placeholder="经度"
                id="lng"
                name="lng"
                :min="0"
                :max="30"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <!--地图选点-->
        </el-row>
        <el-row>
          <div style="height: 350px; width: auto" class="map" id="map1"></div>
          <el-form-item />
        </el-row>
        <el-row>
          <el-col :span="6" :offset="0">
            <el-form-item label="地理板块" prop="plateName">
              <el-input v-model="form.plateName" placeholder="请输入地理板块" />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="楼盘地址" prop="address">
              <el-input v-model="form.address" placeholder="请输入楼盘地址" />
            </el-form-item>
          </el-col>
          <el-col :span="9" :offset="1">
            <el-form-item label="与社区合同日期" prop="contractBegin">
              <el-date-picker
                v-model="form.contractBegin"
                placeholder="开始日期"
                clearable
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                style="width: 50%"
                :picker-options="startOptions"
              ></el-date-picker>
              <el-date-picker
                v-model="form.contractEnd"
                placeholder="结束日期"
                clearable
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                style="width: 50%"
                :picker-options="endOptions"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="0">
            <el-form-item label="户型面积范围" prop="doorArea">
              <el-input
                v-model="form.doorArea"
                placeholder="请输入户型面积范围"
                oninput="value=value.replace(/^(0+)|[^\d]+/g,'')"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="占地面积" prop="landSpace">
              <el-input-number
                v-model="form.landSpace"
                placeholder="请输入占地面积（单位/平方米）"
                :min="0"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1">
            <el-form-item label="建筑面积" prop="coveredArea">
              <el-input-number
                v-model="form.coveredArea"
                placeholder="请输入建筑面积（单位/平方米）"
                :min="0"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-row>
          <el-col :offset="20" :span="2">
            <el-button @click="cancel">取 消</el-button>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" @click="submitForm">确 定</el-button>
          </el-col>
        </el-row>
      </div>
    </div>
  </div>
</template>

<script>
import * as COMMUNITY from "@/api/system/community/community";
import { treeProSelect } from "@/api/system/property/estate";
import { areaTreeSelect } from "@/api/system/community/area";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { equals, isNotEmpty } from "@/utils/common";

export default {
  name: "Estate",
  components: {
    Treeselect,
  },
  data() {
    return {
      // 非多个禁用
      multiple: true,
      submitFlag: true,
      // 遮罩层
      loading: true,
      total: 0,
      // 选中数组
      ids: [],
      // 显示搜索条件
      showSearch: true,
      // 小区管理表格数据
      communityList: [],
      // 弹出层标题
      title: "",
      // 物业树选项
      deptOptions: [],
      //地区树选项
      areaOptions: [],
      // 是否显示弹出层
      open: false,

      //显示弹出层 海康信息
      openHik: false,
      //显示弹出层 微信相关信息
      openWechat: false,
      //地图标记点
      marker: [],
      // 弹出层标题 海康信息
      titleHik: "",
      // 弹出层标题 微信相关信息
      titleWechat: "",

      startOptions: this.startDate(),
      endOptions: this.endDate(),
      // 查询参数
      queryParams: {
        estateId: null,
        communityName: null,
        areaId: null,
      },
      // 表单参数
      form: {
        estateId: undefined,
      },
      //表单参数 海康信息
      formHik: {
        communityIds: undefined,
        communityId: undefined,
      },
      //表单参数 微信相关信息
      formWechat: {
        communityIds: undefined,
        communityId: undefined,
      },
      // 表单校验
      rules: {
        estateId: [
          { required: true, message: "请选择所属物业", trigger: "input" },
        ],
        areaId: [{ required: true, message: "请选择地区", trigger: "input" }],
        communityName: [
          {
            required: true,
            message: "社区名称不能为空",
            trigger: ["blur", "change"],
          },
          { max: 30, message: "文本输入过长", trigger: ["blur", "change"] },
        ],
        communityAlias: [
          {
            required: true,
            message: "社区别名不能为空",
            trigger: ["blur", "change"],
          },
          { max: 30, message: "文本输入过长", trigger: ["blur", "change"] },
        ],
        developersName: [
          {
            required: true,
            message: "开发商不能为空",
            trigger: ["blur", "change"],
          },
          { max: 30, message: "文本输入过长", trigger: ["blur", "change"] },
        ],
        plateName: [
          {
            required: true,
            message: "地理板块不能为空",
            trigger: ["blur", "change"],
          },
          { max: 30, message: "文本输入过长", trigger: ["blur", "change"] },
        ],
        address: [
          {
            required: true,
            message: "楼盘地址不能为空",
            trigger: ["blur", "change"],
          },
          { max: 30, message: "文本输入过长", trigger: ["blur", "change"] },
        ],
        doorArea: [
          {
            required: true,
            message: "户型面积范围不能为空",
            trigger: ["blur", "change"],
          },
          { message: "文本输入过长", trigger: ["blur", "change"] },
        ],
        residentNum: [
          {
            required: true,
            message: "总户数不能为空",
            trigger: ["blur", "change"],
          },
        ],
        registerNum: [
          {
            required: true,
            message: "登记户数不能为空",
            trigger: ["blur", "change"],
          },
        ],
        plotRatio: [
          {
            required: true,
            message: "容积率不能为空",
            trigger: ["blur", "change"],
          },
        ],
        latitude: [
          {
            required: true,
            message: "纬度不能为空",
            trigger: ["blur", "change"],
          },
        ],

        longitude: [
          {
            required: true,
            message: "经度不能为空",
            trigger: ["blur", "change"],
          },
        ],
        greenRatio: [
          {
            required: true,
            message: "绿化率不能为空",
            trigger: ["blur", "change"],
          },
        ],
        landSpace: [
          {
            required: true,
            message: "占地面积不能为空",
            trigger: ["blur", "change"],
          },
        ],
        coveredArea: [
          {
            required: true,
            message: "建筑面积不能为空",
            trigger: ["blur", "change"],
          },
        ],
        buildingType: [
          { required: true, message: "请选择建筑结构", trigger: "change" },
        ],
        contractTime: [
          {
            required: true,
            message: "请选择合同起始日期",
            trigger: ["blur", "change"],
          },
        ],
        contractBegin: [
          {
            required: true,
            message: "社区合同日期不能为空",
            trigger: ["blur", "change"],
          },
        ],
      },
      rulesHik: {
        hikIp: [
          {
            required: true,
            message: "请输入IP地址",
            trigger: ["blur", "change"],
          },
          { max: 30, message: "文本输入过长", trigger: ["blur", "change"] },
        ],
        hikPort: [
          {
            required: true,
            message: "请输入端口",
            trigger: ["blur", "change"],
          },
          { max: 30, message: "文本输入过长", trigger: ["blur", "change"] },
        ],
        hikAk: [
          {
            required: true,
            message: "请输入海康AK",
            trigger: ["blur", "change"],
          },
          { max: 30, message: "文本输入过长", trigger: ["blur", "change"] },
        ],

        hikSk: [
          {
            required: true,
            message: "请输入密钥",
            trigger: ["blur", "change"],
          },
          { max: 30, message: "文本输入过长", trigger: ["blur", "change"] },
        ],
      },
      rulesWechat: {
        appId: [
          {
            required: true,
            message: "APPID不能为空",
            trigger: ["blur", "change"],
          },
        ],
        appSecret: [
          {
            required: true,
            message: "小程序密钥不能为空",
            trigger: ["blur", "change"],
          },
        ],
        publicAppId: [
          {
            required: true,
            message: "微信公众号APPID不能为空",
            trigger: ["blur", "change"],
          },
        ],
        publicSecret: [
          {
            required: true,
            message: "微信公众号密钥不能为空",
            trigger: ["blur", "change"],
          },
        ],
        mchId: [
          {
            required: true,
            message: "商户号不能为空",
            trigger: ["blur", "change"],
          },
        ],
        serialNumber: [
          {
            required: true,
            message: "证书序列号不能为空",
            trigger: ["blur", "change"],
          },
        ],
        apiKey: [
          {
            required: true,
            message: "APIv3密钥不能为空",
            trigger: ["blur", "change"],
          },
        ],
        privateUrl: [
          {
            required: true,
            message: "商户私钥不能为空",
            trigger: ["blur", "change"],
          },
        ],
      },
    };
  },
  created() {
    this.getTreeSelect();
    this.getList();
    this.loading = false;
  },
  methods: {
    // 保证小数点只出现一次，而不能出现两次以上.replace(".", "$#$").replace(/\./g, "").replace("$#$", ".");
    //  数字 小数点.replace(/[^\d.]/g, "")
    handleInput(val) {
      this.form.greenRatio = val
        .replace(/[^\d.]/g, "")
        .replace(".", "$#$")
        .replace(/\./g, "")
        .replace("$#$", ".");
    },
    changeEstateValid() {
      this.$nextTick(() => {
        this.$refs.form.validateField("estateId");
      });
    },
    changeAreaValid() {
      this.$nextTick(() => {
        this.$refs.form.validateField("areaId");
      });
    },
    startDate() {
      const self = this;
      return {
        disabledDate(time) {
          //如果结束时间不为空，则小于结束时间
          if (self.form.contractEnd) {
            return new Date(self.form.contractEnd).getTime() < time.getTime();
          }
        },
      };
    },
    endDate() {
      const self = this;
      return {
        disabledDate(time) {
          //如果开始时间不为空，则结束时间大于开始时间
          if (self.form.contractBegin) {
            return new Date(self.form.contractBegin).getTime() > time.getTime();
          }
        },
      };
    },
    periodTypeFormat(row) {
      if (equals(row.periodType, "1")) {
        return "商品房";
      }
      if (equals(row.periodType, "2")) {
        return "经济适用房";
      }
      if (equals(row.periodType, "3")) {
        return "央产房";
      }
      if (equals(row.periodType, "4")) {
        return "军产房";
      }
      return "公房";
    },
    //覆盖默认的上传行为
    requestUpload() {},
    // 上传预处理
    beforeUpload(file) {
      return false;
    },

    // 上传支付宝商户私匙文件
    uploadAliFile(file, fileList) {
      let formData = new FormData();
      formData.append("file", file.raw);
      COMMUNITY.uploadPrivateKey(formData).then((response) => {
        console.log(response.imgUrlName);
        this.formAli.privateKey = response.imgUrlName;
        this.$modal.msgSuccess("上传成功");
      });
    },

    // 上传微信私匙文件
    uploadWechatFile(file, fileList) {
      let formData = new FormData();
      formData.append("file", file.raw);
      COMMUNITY.uploadPrivateKey(formData).then((response) => {
        console.log(response.imgUrlName);
        this.formWechat.privateUrl = response.imgUrlName;
        this.$modal.msgSuccess("上传成功");
      });
    },

    // 上传支付宝商户私匙文件
    uploadMerchantFile(file, fileList) {
      let formData = new FormData();
      formData.append("file", file.raw);
      COMMUNITY.uploadPrivateKey(formData).then((response) => {
        this.formAbc.privateUrl = response.imgUrlName;
        this.$modal.msgSuccess("上传成功");
      });
    },

    // 多选框选中数据
    handleSelectionChange(selection) {
      this.ids = selection.map((item) => item.communityId);
      this.single = selection.length != 1;
      this.multiple = !selection.length;
    },
    /** 设置海康配置 */
    handleSetHik(row) {
      let query = {
        communityTable: "bus_community_hik",
        communityId: row.communityId,
      };
      COMMUNITY.getCommunityTable(query).then((response) => {
        if (response.data > 0) {
          this.resetHik();
          this.openHik = true;
          this.titleHik = "修改海康配置";
          this.formHik = row.communityHik;
        } else {
          this.resetHik();
          this.openHik = true;
          this.titleHik = "新增海康配置";
          this.formHik.communityIds = row.communityId;
        }
      });
    },
    /**  新增微信配置 */
    handleAddWechat(row) {
      let query = {
        communityTable: "bus_community_wechat",
        communityId: row.communityId,
      };
      COMMUNITY.getCommunityTable(query).then((response) => {
        if (response.data > 0) {
          this.$modal.msgWarning("已有相关微信配置,请勿重复提交微信配置！！！");
        } else {
          this.resetWechat();
          this.openWechat = true;
          this.titleWechat = "新增微信配置";
          this.formWechat.communityIds = row.communityId;
        }
      });
    },
    /**  修改微信配置 */
    handleEditWechat(row) {
      let query = {
        communityTable: "bus_community_wechat",
        communityId: row.communityId,
      };
      console.log(row);
      COMMUNITY.getCommunityTable(query).then((response) => {
        console.log(response);
        if (response.data > 0) {
          this.resetWechat();
          this.openWechat = true;
          this.titleWechat = "修改微信配置";
          this.formWechat = row.communityWechat;
        } else {
          this.$modal.msgWarning("没有相关微信配置,请先进行微信配置！！！");
        }
      });
    },
    /** 查询下拉树结构 */
    getTreeSelect() {
      let dept = {
        deptType: "10",
      };
      //物业树
      treeProSelect(dept).then((response) => {
        this.deptOptions = response.data;
      });
      //地区树
      areaTreeSelect().then((response) => {
        this.areaOptions = response.data;
      });
    },
    /** 查询小区管理列表 */
    getList() {
      this.loading = true;
      COMMUNITY.listCommunity(this.queryParams).then((response) => {
        this.communityList = response.rows;
        this.total = response.total;
        this.loading = false;
      });
    },
    // 取消按钮
    cancel() {
      this.open = false;
      this.reset();
    },
    // 海康取消按钮
    cancelHik() {
      this.openHik = false;
      this.resetHik();
    },
    // 微信取消按钮
    cancelWechat() {
      this.openWechat = false;
      this.resetWechat();
    },
    // 海康表单重置
    resetHik() {
      this.formHik = {
        hikIp: null,
        communityId: null,
        hikPort: null,
        hikAk: null,
        hikSk: null,
      };
      this.resetForm("formHik");
    },
    // 微信表单重置
    resetWechat() {
      this.formWechat = {
        appId: null,
        communityId: null,
        appSecret: null,
        publicAppId: null,
        publicSecret: null,
        mchId: null,
        serialNumber: null,
        apiKey: null,
        privateUrl: null,
      };
      this.resetForm("formWechat");
    },
    // 表单重置
    reset() {
      this.form = {
        estateId: null,
        communityName: null,
        areaId: null,
      };
      this.resetForm("form");
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.handleQuery();
    },
    /** 新增按钮操作 */
    handleAdd(row) {
      this.reset();
      this.mapInit(null, null);
      this.submitFlag = true;
      if (row != null && row.communityId) {
        this.form.parentId = row.communityId;
      } else {
        this.form.parentId = 0;
      }

      this.open = true;
      this.title = "添加小区管理";
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.getTreeSelect();
      if (row != null) {
        this.form.parentId = row.communityId;
      }
      COMMUNITY.getCommunity(row.communityId).then((response) => {
        this.form = response.data;
        this.open = true;
        this.title = "修改小区管理";
        this.mapInit(response.data.latitude, response.data.longitude);
      });
    },
    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          if (this.form.communityId != null) {
            COMMUNITY.updateCommunity(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.open = false;
              this.getList();
            });
          } else {
            this.form.parentId = 0;
            this.form.ancestors = "0";
            COMMUNITY.addCommunity(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.open = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 提交海康信息按钮 */
    submitFormHik() {
      this.$refs["formHik"].validate((valid) => {
        if (valid) {
          if (this.formHik.communityId != null) {
            COMMUNITY.updateCommunityHik(this.formHik).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.openHik = false;
              this.getList();
            });
          } else {
            console.log(this.formHik);
            this.formHik.communityId = this.formHik.communityIds;
            COMMUNITY.addCommunityHik(this.formHik).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.openHik = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 提交支付宝信息按钮 */
    submitFormWechat() {
      this.$refs["formWechat"].validate((valid) => {
        if (valid) {
          if (this.formWechat.communityId != null) {
            COMMUNITY.updateCommunityWechat(this.formWechat).then(
              (response) => {
                this.$modal.msgSuccess("修改成功");
                this.openWechat = false;
                this.getList();
              }
            );
          } else {
            console.log(this.formWechat);
            this.formWechat.communityId = this.formWechat.communityIds;
            COMMUNITY.addCommunityWechat(this.formWechat).then((response) => {
              this.$modal.msgSuccess("新增成功");
              this.openWechat = false;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm('是否确认删除"' + row.communityName + '"?', "警告", {
        confirmButtonText: "确定",
        cancelButtonText: "取消",
        type: "warning",
      })
        .then(function () {
          return COMMUNITY.delCommunity(row.communityId);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        });
    },

    /** 地图初始化 */
    mapInit(lat, lng) {
      lat = isNotEmpty(lat) ? lat : 30.958093;
      lng = isNotEmpty(lng) ? lng : 118.763997;
      let zoom = 14;
      var that = this;
      let url =
        "https://webapi.amap.com/maps?v=1.4.15&key=3a8830e86df34d0a7142d852e26dd47a";
      this.loadScript(url, () => {
        this.$nextTick(() => {
          let map = new AMap.Map("map1", {
            center: [lng, lat],
            zoom: zoom,
          });
          that.marker = new AMap.Marker();
          that.marker.setMap(map);
          map.on("click", function (e) {
            document.getElementById("lat").value = e.lnglat.getLat();
            document.getElementById("lng").value = e.lnglat.getLng();
            that.form.latitude = e.lnglat.getLat();
            that.form.longitude = e.lnglat.getLng();
            that.setMarker(that.form.longitude, that.form.latitude, map);
          });
        });
      });
    },
    /** 设置标记点 */
    setMarker(lng, lat, map) {
      this.marker.setMap();
      this.marker = new AMap.Marker({
        position: [lng, lat],
        map: map,
      });
    },
    loadScript(url, callback) {
      let script = document.createElement("script");
      script.type = "text/javascript";
      script.async = "async";
      script.src = url;
      document.body.appendChild(script);
      if (script.readyState) {
        script.onreadystatechange = function () {
          if (
            script.readyState === "complete" ||
            script.readyState == "loaded"
          ) {
            script.onreadystatechange = null;
            callback();
          }
        };
      } else {
        script.onload = function () {
          callback();
        };
      }
    },
  },
};
</script>
<style rel="stylesheet/scss" lang="scss">
#estateId-tree .vue-treeselect__control {
  height: 32px;
  line-height: 32px;
}
</style>

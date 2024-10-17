<template>
  <div class="app-container">
    <!--列表-->
    <div v-show="openView === 0">
      <el-form
        :model="queryParams"
        ref="queryForm"
        :inline="true"
        v-show="showSearch"
        class="cus-fm"
      >
        <el-form-item label="物业名称" prop="estateName">
          <el-input
            v-model="queryParams.estateName"
            placeholder="请输入物业名称"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
            oninput="value=value.replace(/[`~!@#$%^&*()_=+-<>?:{}/,.\/;·！#￥（——）：；“”‘、，|《。》？、【】……]+$/g,'')"
          />
        </el-form-item>
        <el-form-item label="法人代表" prop="legal">
          <el-input
            v-model="queryParams.legal"
            placeholder="请输入法人代表"
            clearable
            size="small"
            @keyup.enter.native="handleQuery"
            oninput="value=value.replace(/[`~!@#$%^&*()_=+-<>?:{}/,.\/;·！#￥（——）：；“”‘、，|《。》？、【】……]+$/g,'')"
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

      <el-row :gutter="10" class="mb8">
        <el-col :span="1.5">
          <el-button
            type="primary"
            plain
            icon="el-icon-plus"
            size="mini"
            @click="handleAdd"
            v-hasPermi="['system:property:add']"
            >新增
          </el-button>
          <el-button
            type="info"
            plain
            icon="el-icon-upload2"
            size="mini"
            @click="handleImport"
            v-hasPermi="['system:property:import']"
            >导入
          </el-button>
        </el-col>
        <right-toolbar
          :showSearch.sync="showSearch"
          @queryTable="getList"
        ></right-toolbar>
      </el-row>

      <el-table
        v-loading="loading"
        :data="deptList"
        row-key="deptId"
        default-expand-all
        :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
      >
        <!--        <el-table-column prop="deptId" label="物业名称" width="200"></el-table-column>-->
        <el-table-column
          prop="estateName"
          label="物业名称"
          width="200"
          :show-overflow-tooltip="true"
        ></el-table-column>
        <el-table-column
          prop="estateCode"
          label="机构编码"
          align="center"
          width="150"
        ></el-table-column>
        <el-table-column
          prop="legal"
          label="法人代表"
          align="center"
          width="150"
        ></el-table-column>
        <el-table-column label="成立日期" prop="createTime" width="200">
          <template slot-scope="scope">
            <span v-if="scope.row.foundDate != null">{{
              parseTime(scope.row.foundDate, "{y}-{m}-{d}")
            }}</span>
            <span v-else></span>
          </template>
        </el-table-column>
        <el-table-column
          prop="creditCode"
          label="统一社会信用代码"
          width="250"
        ></el-table-column>
        <el-table-column label="登记状态" align="center">
          <template slot-scope="scope">
            {{ handRegisterStatus(scope.row.registerStatus) }}
          </template>
        </el-table-column>
        <el-table-column label="物业公司状态" align="center">
          <template slot-scope="scope">
            <el-switch
              v-model="scope.row.sysDept.status"
              active-value="0"
              inactive-value="1"
              @change="handleStatusChange(scope.row)"
            ></el-switch>
          </template>
        </el-table-column>
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
              @click="handleView(scope.row)"
              v-hasPermi="['system:property:query']"
              >查看
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-edit"
              @click="handleUpdate(scope.row)"
              v-hasPermi="['system:property:edit']"
              >修改
            </el-button>
            <el-button
              size="mini"
              type="text"
              icon="el-icon-plus"
              @click="handleAdd(scope.row)"
              v-hasPermi="['system:property:add']"
              >新增
            </el-button>
            <el-button
              v-if="scope.row.parentId != 0"
              size="mini"
              type="text"
              style="color: #f56c6c"
              icon="el-icon-delete"
              @click="handleDelete(scope.row)"
              v-hasPermi="['system:property:remove']"
              >删除
            </el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>
    <!--添加或修改-->
    <div v-show="openView === 1">
      <h4 class="form-header">基本信息</h4>
      <el-form ref="form" :model="form" :rules="rules" label-width="135px">
        <el-row>
          <el-col :span="6" :offset="1" v-if="form.parentId !== 0">
            <el-form-item label="上级公司" prop="parentId">
              <treeselect
                v-model="form.parentId"
                :options="deptOptions"
                :normalizer="normalizer"
                :default-expand-level="Infinity"
                placeholder="选择上级公司"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2" v-if="form.parentId !== 0">
            <el-form-item label="统一社会信用代码" prop="creditCode">
              <el-input
                v-model="form.creditCode"
                placeholder="请输入统一社会信用代码"
                maxlength="18"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="1" v-if="form.parentId === 0">
            <el-form-item label="统一社会信用代码" prop="creditCode">
              <el-input
                v-model="form.creditCode"
                placeholder="请输入统一社会信用代码"
                maxlength="18"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="机构编码" prop="estateCode">
              <el-input
                v-model="form.estateCode"
                placeholder="请输入机构编码"
                maxlength="18"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="集团/公司名称" prop="estateName">
              <el-input
                v-model="form.estateName"
                placeholder="请输入机构名称"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="曾用名">
              <el-input
                v-model="form.formerName"
                placeholder="请输入曾用名"
                maxlength="255"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="服务热线" prop="hotline">
              <el-input
                v-model="form.hotline"
                placeholder="请输入服务热线xxxx-xxxxx-xxx"
                maxlength="11"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="法人代表" prop="legal">
              <el-input
                v-model="form.legal"
                placeholder="请输入法人代表"
                maxlength="255"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="注册资本（万）" prop="registerCapital">
              <el-input-number
                v-model="form.registerCapital"
                placeholder="请输入注册资本"
                controls-position="right"
                :min="0"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="实缴资本（万）">
              <el-input-number
                v-model="form.actualCapital"
                placeholder="请输入实缴资本"
                controls-position="right"
                :min="0"
                style="width: 100%"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="登记状态" prop="registerStatus">
              <el-select
                v-model="form.registerStatus"
                placeholder="请选择"
                style="width: 100%"
              >
                <el-option
                  label="存续（在营、开业、在册）"
                  value="0"
                ></el-option>
                <el-option label="在业" value="1"></el-option>
                <el-option label="吊销" value="2"></el-option>
                <el-option label="注销" value="3"></el-option>
                <el-option label="迁入" value="4"></el-option>
                <el-option label="迁出" value="5"></el-option>
                <el-option label="停业" value="6"></el-option>
                <el-option label="清算" value="7"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="成立日期" prop="foundDate">
              <el-date-picker
                v-model="form.foundDate"
                placeholder="请选择成立日期"
                clearable
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                style="width: 100%"
                :picker-options="pickerOptions"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="核准日期" prop="approveDate">
              <el-date-picker
                v-model="form.approveDate"
                placeholder="请选择核准日期"
                clearable
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                style="width: 100%"
                :picker-options="pickerOptions"
              ></el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="工商注册号" prop="businessCode">
              <el-input
                v-model="form.businessCode"
                placeholder="请输入工商注册号"
                maxlength="18"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="纳税人识别号" prop="taxpayerCode">
              <el-input
                v-model="form.taxpayerCode"
                placeholder="请输入纳税人识别号"
                maxlength="18"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="纳税人资质">
              <el-input
                v-model="form.taxpayerQualify"
                placeholder="请输入纳税人资质"
                maxlength="255"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="营业期限" prop="periodBegin">
              <el-date-picker
                v-model="form.periodBegin"
                placeholder="开始日期"
                clearable
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                style="width: 50%"
                :picker-options="startOptions"
              ></el-date-picker>
              <el-date-picker
                v-model="form.periodEnd"
                placeholder="结束日期"
                clearable
                format="yyyy-MM-dd"
                value-format="yyyy-MM-dd"
                style="width: 50%"
                :picker-options="endOptions"
              ></el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="企业类型" prop="firmType">
              <el-select
                v-model="form.firmType"
                placeholder="请选择"
                style="width: 100%"
              >
                <el-option label="有限责任公司" value="0"></el-option>
                <el-option label="无限责任公司" value="1"></el-option>
                <el-option label="两合公司" value="2"></el-option>
                <el-option label="股份有限公司" value="3"></el-option>
                <el-option label="股份两合公司" value="4"></el-option>
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="显示排序">
              <el-input-number
                v-model="form.sysDept.orderNum"
                controls-position="right"
                :min="0"
                style="width: 100%"
                oninput="value=value.replace(/^(0+)|[^\d]+/g,'')"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="所属地区" prop="areaName">
              <el-input
                v-model="form.areaName"
                placeholder="请输入所属地区"
                maxlength="100"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="登记机关" prop="registerOffice">
              <el-input
                v-model="form.registerOffice"
                placeholder="请输入登记机关"
                maxlength="255"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="注册地址" prop="registerAddress">
              <el-input
                v-model="form.registerAddress"
                placeholder="请输入注册地址"
                maxlength="255"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <el-form-item label="人员规模" prop="staffSize">
              <el-input
                v-model="form.staffSize"
                type="number"
                placeholder="请输入人员规模"
                maxlength="20"
                oninput="value=value.replace(/^(0+)|[^\d]+/g,'')"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="参保人数">
              <el-input-number
                v-model="form.insuredNum"
                placeholder="请输入参保人数"
                controls-position="right"
                :min="0"
                style="width: 100%"
                oninput="value=value.replace(/^(0+)|[^\d]+/g,'')"
              />
            </el-form-item>
          </el-col>
          <el-col :span="6" :offset="2">
            <el-form-item label="物业公司状态">
              <el-radio-group v-model="form.sysDept.status">
                <el-radio
                  v-for="dict in statusOptions"
                  :key="dict.dictValue"
                  :label="dict.dictValue"
                  >{{ dict.dictLabel }}
                </el-radio>
              </el-radio-group>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="11" :offset="1">
            <el-form-item label="历史服务">
              <el-input
                v-model="form.historyServer"
                type="textarea"
                :rows="3"
                placeholder="请输入历史服务,最多输入500字"
                maxlength="500"
              ></el-input>
            </el-form-item>
          </el-col>
          <el-col :span="11">
            <el-form-item label="简介" prop="brief">
              <el-input
                v-model="form.brief"
                type="textarea"
                :rows="3"
                placeholder="请输入简介,最多输入500字"
                maxlength="500"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="22" :offset="1">
            <el-form-item label="经营范围" prop="businessScope">
              <el-input
                v-model="form.businessScope"
                type="textarea"
                :rows="5"
                placeholder="请输入经营范围,最多输入500字"
                maxlength="500"
              ></el-input>
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <h4 class="form-header"></h4>
      <div slot="footer" class="dialog-footer">
        <el-row>
          <el-col :offset="20" :span="2">
            <el-button @click="cancel">取 消</el-button>
          </el-col>
          <el-col :span="2">
            <el-button type="primary" @click.prevent="submitForm"
              >确 定</el-button
            >
          </el-col>
        </el-row>
      </div>
    </div>
    <!--详情-->
    <div v-show="openView === 2">
      <h4 class="form-header">基本信息</h4>
      <div id="content-div">
        <el-row>
          <el-col :span="6" :offset="1">
            <label class="el-form-item__label">上级公司：</label>
            <div class="el-form-item__content">{{ form.parentName }}</div>
          </el-col>
          <el-col :span="6" :offset="2">
            <label class="el-form-item__label">统一社会信用代码：</label>
            <div class="el-form-item__content">{{ form.creditCode }}</div>
          </el-col>
          <el-col :span="6" :offset="2">
            <label class="el-form-item__label">机构编码：</label>
            <div class="el-form-item__content">{{ form.estateCode }}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <label class="el-form-item__label">集团/公司名称：</label>
            <div class="el-form-item__content">{{ form.estateName }}</div>
          </el-col>
          <el-col :span="6" :offset="2">
            <label class="el-form-item__label">曾用名：</label>
            <div class="el-form-item__content">{{ form.formerName }}</div>
          </el-col>
          <el-col :span="6" :offset="2">
            <label class="el-form-item__label">服务热线：</label>
            <div class="el-form-item__content">{{ form.hotline }}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <label class="el-form-item__label">法人代表：</label>
            <div class="el-form-item__content">{{ form.legal }}</div>
          </el-col>
          <el-col :span="6" :offset="2">
            <label class="el-form-item__label">注册资本（万）：</label>
            <div class="el-form-item__content">{{ form.registerCapital }}</div>
          </el-col>
          <el-col :span="6" :offset="2">
            <label class="el-form-item__label">实缴资本（万）：</label>
            <div class="el-form-item__content">{{ form.actualCapital }}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <label class="el-form-item__label">登记状态：</label>
            <div class="el-form-item__content">
              <div v-if="form.registerStatus == '0'">
                存续（在营、开业、在册）
              </div>
              <div v-else-if="form.registerStatus == '1'">在业</div>
              <div v-else-if="form.registerStatus == '2'">吊销</div>
              <div v-else-if="form.registerStatus == '3'">注销</div>
              <div v-else-if="form.registerStatus == '4'">迁入</div>
              <div v-else-if="form.registerStatus == '5'">迁出</div>
              <div v-else-if="form.registerStatus == '6'">停业</div>
              <div v-else-if="form.registerStatus == '7'">清算</div>
            </div>
          </el-col>
          <el-col :span="6" :offset="2">
            <label class="el-form-item__label">成立日期：</label>
            <div class="el-form-item__content">{{ form.foundDate }}</div>
          </el-col>
          <el-col :span="6" :offset="2">
            <label class="el-form-item__label">核准日期：</label>
            <div class="el-form-item__content">{{ form.approveDate }}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <label class="el-form-item__label">工商注册号：</label>
            <div class="el-form-item__content">{{ form.businessCode }}</div>
          </el-col>
          <el-col :span="6" :offset="2">
            <label class="el-form-item__label">纳税人识别号：</label>
            <div class="el-form-item__content">{{ form.taxpayerCode }}</div>
          </el-col>
          <el-col :span="6" :offset="2">
            <label class="el-form-item__label">纳税人资质：</label>
            <div class="el-form-item__content">{{ form.taxpayerQualify }}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <label class="el-form-item__label">营业期限：</label>
            <div class="el-form-item__content">
              <div v-if="form.periodEnd != null">
                {{ form.periodBegin }} 至 {{ form.periodEnd }}
              </div>
              <div v-else>{{ form.periodBegin }} 至 无固定限期</div>
            </div>
          </el-col>
          <el-col :span="6" :offset="2">
            <label class="el-form-item__label">企业类型：</label>
            <div class="el-form-item__content">
              <div v-if="form.firmType == '0'">有限责任公司</div>
              <div v-else-if="form.firmType == '1'">无限责任公司</div>
              <div v-else-if="form.firmType == '2'">两合公司</div>
              <div v-else-if="form.firmType == '3'">股份有限公司</div>
              <div v-else-if="form.firmType == '4'">股份两合公司</div>
            </div>
          </el-col>
          <el-col :span="6" :offset="2">
            <label class="el-form-item__label">物业公司状态：</label>
            <div class="el-form-item__content">
              <div v-if="form.sysDept.status == 0">启用</div>
              <div v-else-if="form.sysDept.status == 1">停用</div>
            </div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <label class="el-form-item__label">所属地区：</label>
            <div class="el-form-item__content">{{ form.areaName }}</div>
          </el-col>
          <el-col :span="6" :offset="2">
            <label class="el-form-item__label">登记机关：</label>
            <div class="el-form-item__content">{{ form.registerOffice }}</div>
          </el-col>
          <el-col :span="6" :offset="2">
            <label class="el-form-item__label">注册地址：</label>
            <div class="el-form-item__content">{{ form.registerAddress }}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="6" :offset="1">
            <label class="el-form-item__label">人员规模：</label>
            <div class="el-form-item__content">{{ form.staffSize }}</div>
          </el-col>
          <el-col :span="6" :offset="2">
            <label class="el-form-item__label">参保人数：</label>
            <div class="el-form-item__content">{{ form.insuredNum }}</div>
          </el-col>
          <el-col :span="6" :offset="2">
            <label class="el-form-item__label">历史服务：</label>
            <div class="el-form-item__content">{{ form.historyServer }}</div>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="7" :offset="1">
            <label class="el-form-item__label">简介：</label>
            <div class="el-form-item__content">{{ form.brief }}</div>
          </el-col>
          <el-col :span="7" :offset="1">
            <label class="el-form-item__label">经营范围：</label>
            <div class="el-form-item__content">{{ form.businessScope }}</div>
          </el-col>
        </el-row>
      </div>
      <div class="table-div">
        <el-tabs v-model="activeTab">
          <el-tab-pane label="下属物业" name="childData">
            <el-table
              v-loading="loading"
              :data="childList"
              row-key="deptId"
              default-expand-all
              :tree-props="{ children: 'children', hasChildren: 'hasChildren' }"
            >
              <el-table-column
                prop="estateName"
                label="物业名称"
                width="200"
              ></el-table-column>
              <el-table-column
                prop="legal"
                label="法人代表"
                width="200"
              ></el-table-column>
              <el-table-column label="成立日期" prop="createTime" width="200">
                <template slot-scope="scope">
                  <span>{{
                    parseTime(scope.row.foundDate, "{y}-{m}-{d}")
                  }}</span>
                </template>
              </el-table-column>
              <el-table-column
                prop="creditCode"
                label="统一社会信用代码"
                width="250"
              ></el-table-column>
              <el-table-column
                prop="registerAddress"
                label="注册地址"
                width="250"
              ></el-table-column>

              <el-table-column
                prop="brief"
                label="简介"
                :show-overflow-tooltip="true"
                width="250"
              ></el-table-column>
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
                    @click="handleView(scope.row)"
                    v-hasPermi="['system:property:query']"
                    >查看
                  </el-button>
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-edit"
                    @click="handleUpdate(scope.row)"
                    v-hasPermi="['system:property:edit']"
                    >修改
                  </el-button>
                </template>
              </el-table-column>
            </el-table>
          </el-tab-pane>
          <el-tab-pane label="相关社区" name="community">
            <el-table v-loading="loading" :data="communityList">
              <el-table-column
                prop="communityName"
                label="社区名称"
                align="center"
              ></el-table-column>
              <el-table-column label="社区合同日期" align="center">
                <template slot-scope="scope">
                  <span>
                    {{ parseTime(scope.row.contractBegin, "{y}-{m}-{d}") }}
                    至
                    {{ parseTime(scope.row.contractEnd, "{y}-{m}-{d}") }}
                  </span>
                </template>
              </el-table-column>
              <el-table-column prop="greenRatio" label="绿化率" align="center">
                <template slot-scope="scope">
                  <span>{{ scope.row.greenRatio }} %</span>
                </template>
              </el-table-column>
              <el-table-column
                prop="address"
                label="地址"
                :show-overflow-tooltip="true"
                align="center"
              ></el-table-column>
              <el-table-column
                prop="coveredArea"
                label="建筑面积"
                align="center"
              ></el-table-column>
            </el-table>
          </el-tab-pane>
        </el-tabs>
      </div>
      <div slot="footer" class="dialog-footer">
        <el-row>
          <el-col :offset="22" :span="2">
            <el-button @click="cancel">关 闭</el-button>
          </el-col>
        </el-row>
      </div>
    </div>
    <!-- 用户导入对话框 -->
    <el-dialog
      :title="upload.title"
      :visible.sync="upload.open"
      width="400px"
      append-to-body
    >
      <el-upload
        ref="upload"
        :limit="1"
        accept=".xlsx, .xls"
        :headers="upload.headers"
        :action="upload.url"
        :disabled="upload.isUploading"
        :on-progress="handleFileUploadProgress"
        :on-success="handleFileSuccess"
        :auto-upload="false"
        drag
      >
        <i class="el-icon-upload"></i>
        <div class="el-upload__text">将文件拖到此处，或<em>点击上传</em></div>
        <div class="el-upload__tip text-center" slot="tip">
          <span>仅允许导入xls、xlsx格式文件。</span>
          <el-link
            type="primary"
            :underline="false"
            style="font-size: 12px; vertical-align: baseline"
            @click="importTemplate"
            >下载模板</el-link
          >
        </div>
      </el-upload>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitFileForm">确 定</el-button>
        <el-button @click="upload.open = false">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>
<script>
import * as DEPT_INFO from "@/api/system/property/estate.js";
import { listCommunity } from "@/api/system/community/community";
import { isEmpty, equals } from "@/utils/common";
import Treeselect from "@riophae/vue-treeselect";
import "@riophae/vue-treeselect/dist/vue-treeselect.css";
import { getToken } from "@/utils/auth";
export default {
  name: "Property",
  components: { Treeselect },
  data() {
    /**
     * 校验手机号
     */
    const validatePhone = (rule, value, callback) => {
      const regExp =
        /^(0|86|17951)?(13[0-9]|15[012356789]|166|17[3678]|18[0-9]|14[57])[0-9]{8}$/;
      if (!regExp.test(value)) {
        callback(new Error("请输入正确的服务热线"));
      } else {
        callback();
      }
    };
    return {
      activeTab: "childData",
      pickerOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now() - 8.64e6;
        },
      },
      /*禁止用户选择未来时间。最晚在当日 */
      startOptions: {
        disabledDate(time) {
          return time.getTime() > Date.now() - 8.64e6; //如果没有后面的-8.64e6就是不可以选择今天的
        },
      },
      /*startOptions:this.startDate(),*/
      endOptions: this.endDate(),
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 表格树数据
      deptList: [],
      // 部门树选项
      deptOptions: [],
      //下属物业
      childList: [],
      //相关社区
      communityList: [],
      submitFlag: true,
      // 弹出层标题
      title: "",
      // 是否显示弹出层
      //open: false,
      openView: 0,
      // 状态数据字典
      statusOptions: [],
      // 查询参数
      queryParams: {
        deptType: "10",
        estateName: undefined,
        legal: undefined,
      },
      // 表单参数
      form: {
        parentId: "",
        estateName: "",
        orderNum: "",
        creditCode: "",
        hotline: "",
        legal: "",
        registerCapital: "",
        registerStatus: "",
        foundDate: "",
        approveDate: "",
        businessCode: "",
        taxpayerCode: "",
        periodBegin: "",
        periodEnd: "",
        firmType: "",
        areaName: "",
        registerOffice: "",
        registerAddress: "",
        brief: "",
        businessScope: "",

        sysDept: {
          status: "0",
        },
      },
      // 表单校验
      rules: {
        creditCode: [
          {
            required: true,
            message: "统一社会信用代码不能为空",
            trigger: ["blur", "change"],
          },
          {
            pattern:
              /^([0-9A-HJ-NPQRTUWXY]{2}\d{6}[0-9A-HJ-NPQRTUWXY]{10}|[1-9]\d{14})$/,
            message: "格式错误",
            trigger: ["blur", "change"],
          },
        ],
        estateCode: [
          {
            required: true,
            message: "机构编码不能为空",
            trigger: ["blur", "change"],
          },
          {
            pattern: "[^ \x22]+",
            message: "格式错误",
            trigger: ["blur", "change"],
          },
        ],
        estateName: [
          {
            required: true,
            message: "公司名称不能为空",
            trigger: ["blur", "change"],
          },
          {
            pattern: "[^ \x22]+",
            message: "格式错误",
            trigger: ["blur", "change"],
          },
        ],
        hotline: [
          {
            required: true,
            message: "服务热线不能为空",
            trigger: ["blur", "change"],
          },
          {
            validator: validatePhone,
            trigger: "change",
          },
        ],
        legal: [
          {
            required: true,
            message: "法人代表不能为空",
            trigger: ["blur", "change"],
          },
          {
            pattern: "[^ \x22]+",
            message: "格式错误",
            trigger: ["blur", "change"],
          },
        ],
        registerCapital: [
          {
            required: true,
            message: "注册资本不能为空",
            trigger: ["blur", "change"],
          },
        ],
        registerStatus: [
          {
            required: true,
            message: "请选择登记状态",
            trigger: ["blur", "change"],
          },
          {
            pattern: "[^ \x22]+",
            message: "格式错误",
            trigger: ["blur", "change"],
          },
        ],
        foundDate: [
          {
            required: true,
            message: "成立日期不能为空",
            trigger: ["blur", "change"],
          },
        ],
        approveDate: [
          {
            required: true,
            message: "核准日期不能为空",
            trigger: ["blur", "change"],
          },
        ],
        businessCode: [
          {
            required: true,
            message: "工商注册号不能为空",
            trigger: ["blur", "change"],
          },
          {
            pattern: "[^ \x22]+",
            message: "格式错误",
            trigger: ["blur", "change"],
          },
        ],
        taxpayerCode: [
          {
            required: true,
            message: "纳税人识别号不能为空",
            trigger: ["blur", "change"],
          },
          {
            pattern: "[^ \x22]+",
            message: "格式错误",
            trigger: ["blur", "change"],
          },
        ],
        periodBegin: [
          {
            required: true,
            message: "营业期限开始日期不能为空",
            trigger: ["blur"],
          },
        ],
        firmType: [
          {
            required: true,
            message: "请选择企业类型",
            trigger: ["blur", "change"],
          },
        ],
        orderNum: [
          {
            required: true,
            message: "显示顺序不能为空",
            trigger: ["blur", "change"],
          },
        ],
        areaName: [
          {
            required: true,
            message: "所属地区不能为空",
            trigger: ["blur", "change"],
          },
          {
            pattern: "[^ \x22]+",
            message: "格式错误",
            trigger: ["blur", "change"],
          },
        ],
        registerOffice: [
          {
            required: true,
            message: "登记机关不能为空",
            trigger: ["blur", "change"],
          },
          {
            pattern: "[^ \x22]+",
            message: "格式错误",
            trigger: ["blur", "change"],
          },
        ],
        registerAddress: [
          {
            required: true,
            message: "注册地址不能为空",
            trigger: ["blur", "change"],
          },
          {
            pattern: "[^ \x22]+",
            message: "格式错误",
            trigger: ["blur", "change"],
          },
        ],
        brief: [
          {
            required: true,
            message: "简介不能为空",
            trigger: ["blur", "change"],
          },
          {
            pattern: "[^ \x22]+",
            message: "格式错误",
            trigger: ["blur", "change"],
          },
        ],
        businessScope: [
          {
            required: true,
            message: "经营范围不能为空",
            trigger: ["blur", "change"],
          },
          {
            pattern: "[^ \x22]+",
            message: "格式错误",
            trigger: ["blur", "change"],
          },
        ],
        staffSize: [
          {
            required: false,
            message: "人员规模不能为空",
            trigger: ["blur", "change"],
          },
          {
            pattern: "[^ \x22]+",
            message: "格式错误",
            trigger: ["blur", "change"],
          },
        ],
      },
      // 用户导入参数
      upload: {
        // 是否显示弹出层（用户导入）
        open: false,
        // 弹出层标题（用户导入）
        title: "",
        // 是否禁用上传
        isUploading: false,
        // 是否更新已经存在的用户数据
        // updateSupport: 0,
        // 设置上传的请求头部
        headers: { Authorization: "Bearer " + getToken() },
        // 上传的地址
        url: process.env.VUE_APP_BASE_API + "/system/estate/import",
      },
    };
  },
  created() {
    this.getList();
    this.getDicts("sys_normal_disable").then((response) => {
      this.statusOptions = response.data;
    });
  },
  methods: {
    /** 导入按钮操作 */
    handleImport() {
      this.upload.title = "用户导入";
      this.upload.open = true;
    },
    // 文件上传中处理
    handleFileUploadProgress(event, file, fileList) {
      this.upload.isUploading = true;
    },
    // 文件上传成功处理
    handleFileSuccess(response, file, fileList) {
      this.upload.open = false;
      this.upload.isUploading = false;
      this.$refs.upload.clearFiles();
      this.$alert(
        "<div style='overflow: auto;overflow-x: hidden;max-height: 70vh;padding: 10px 20px 0;'>" +
          response.msg +
          "</div>",
        "导入结果",
        { dangerouslyUseHTMLString: true }
      );
      this.getList();
    },
    /** 下载模板操作 */
    importTemplate() {
      this.download(
        "system/estate/template",
        {},
        `user_template_${new Date().getTime()}.xlsx`
      );
    },
    // 提交上传文件
    submitFileForm() {
      this.$refs.upload.submit();
    },
    startDate() {
      //const self = this;
      let self = this;
      return {
        disabledDate: (time) => {
          //如果结束时间不为空，则小于结束时间
          if (self.form.periodEnd) {
            let periodEnd = self.form.periodEnd.replace(/-/g, "/");
            return time.getTime() > new Date(periodEnd);
          }
        },
      };
    },
    endDate() {
      const self = this;
      return {
        disabledDate: (time) => {
          if (self.form.periodBegin) {
            let periodBegin = self.form.periodBegin.replace(/-/g, "/");
            return time.getTime() < new Date(periodBegin);
          }
        },
      };
    },
    // 物业公司状态
    handleStatusChange(row) {
      let text = row.sysDept.status === "0" ? "启用" : "停用";
      this.$confirm(
        '确认要"' + text + '""' + row.estateName + '"角色吗?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return DEPT_INFO.changeStatus(row.sysDept);
        })
        .then(() => {
          this.$modal.msgSuccess(text + "成功");
        })
        .catch(function () {
          row.sysDept.status = row.sysDept.status === "0" ? "1" : "0";
        });
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
    /** 查询部门列表 */
    getList() {
      this.loading = true;
      DEPT_INFO.listestate(this.queryParams).then((response) => {
        this.deptList = this.handleTree(response.data, "estateId");
        this.loading = false;
      });
    },
    /** 转换部门数据结构 */
    normalizer(node) {
      if (node.children && !node.children.length) {
        delete node.children;
      }
      return {
        id: node.estateId,
        label: node.estateName,
        children: node.children,
      };
    },
    // 字典状态字典翻译
    statusFormat(row, column) {
      return this.selectDictLabel(this.statusOptions, row.status);
    },
    // 取消按钮
    cancel() {
      //this.open = false;
      this.openView = 0;
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {
        deptId: undefined,
        parentId: undefined,
        estateName: undefined,
        deptType: "10",
        orderNum: undefined,
        leader: undefined,
        phone: undefined,
        email: undefined,

        sysDept: { status: "0" },
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
      this.submitFlag = true;
      if (row != undefined) {
        this.form.parentId = row.estateId;
      }
      //this.open = true;
      this.openView = 1;
      this.title = "添加部门";
      this.queryParams = { deptType: "10" };
      DEPT_INFO.listestate(this.queryParams).then((response) => {
        let deptData = response.data;
        if (isEmpty(deptData)) {
          this.form.parentId = undefined;
        } else {
          this.deptOptions = this.handleTree(deptData, "estateId");
        }
      });
    },
    /** 查看*/
    handleView(row) {
      this.reset();
      this.childList = [];
      this.communityList = [];
      this.activeTab = "childData";

      DEPT_INFO.getestate(row.estateId).then((response) => {
        this.form = response.data;
        if (equals(response.data.parentId, 100)) {
          this.form.parentId = undefined;
          this.form.parentName = "";
        }
        //this.open = true;
        this.openView = 2;
      });
      DEPT_INFO.getChildList(row.deptId).then((response) => {
        this.childList = this.handleTree(response.data, "estateId");
        this.loading = false;
      });
      let data = {
        estateId: row.estateId,
      };
      listCommunity(data).then((response) => {
        console.log(response);
        this.communityList = response.rows;
        this.loading = false;
      });
    },
    handRegisterStatus(value) {
      if (value === "0") {
        return "存续（在营、开业、在册）";
      } else if (value === "1") {
        return "在业";
      } else if (value === "2") {
        return "吊销";
      } else if (value === "3") {
        return "注销";
      } else if (value === "4") {
        return "迁入";
      } else if (value === "5") {
        return "迁出";
      } else if (value === "6") {
        return "停业";
      } else if (value === "7") {
        return "清算";
      }
    },
    /** 修改按钮操作 */
    handleUpdate(row) {
      this.reset();
      this.submitFlag = true;
      DEPT_INFO.getestate(row.estateId).then((response) => {
        this.form = response.data;
        if (equals(response.data.parentId, 100)) {
          this.form.parentId = undefined;
        }
        //this.open = true;
        this.openView = 1;
        this.title = "修改部门";
      });
      DEPT_INFO.listestateExcludeChild(row.estateId).then((response) => {
        this.deptOptions = this.handleTree(response.data, "estateId");
      });
    },
    /** 提交按钮 */
    submitForm: function () {
      this.$refs["form"].validate((valid) => {
        if (valid && this.submitFlag) {
          //this.submitFlag = false;
          if (isEmpty(this.form.parentId)) {
            this.form.parentId = 100;
          }
          if (this.form.deptId != undefined) {
            DEPT_INFO.updateestate(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              //this.open = false;
              this.submitFlag = true;
              this.openView = 0;
              this.getList();
            });
          } else {
            this.form.deptType = "10";
            DEPT_INFO.addestate(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");
              //this.open = false;
              this.submitFlag = true;
              this.openView = 0;
              this.getList();
            });
          }
        }
      });
    },
    /** 删除按钮操作 */
    handleDelete(row) {
      this.$confirm(
        '是否确认删除名称为"' + row.estateName + '"的数据项?',
        "警告",
        {
          confirmButtonText: "确定",
          cancelButtonText: "取消",
          type: "warning",
        }
      )
        .then(function () {
          return DEPT_INFO.delestate(row.deptId);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        });
    },
  },
};
</script>
<style rel="stylesheet/scss" lang="scss">
#content-div {
  height: 48vh;
  overflow-y: scroll;
}

#content-div::-webkit-scrollbar {
  display: none;
}

.table-div {
  height: 30vh;
  overflow-y: scroll;
  box-shadow: 0 -5px 5px #eaeaea;
  padding: 15px 20px 20px 20px;
}

.table-div::-webkit-scrollbar {
  display: none;
}
</style>

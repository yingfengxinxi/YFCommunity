<template>
  <div class="app-container">
    <div v-show="openView === 0">
      <el-row :gutter="20">
        <!-- 树结构     -->
        <el-col :span="4" :xs="24">
          <div class="head-container">
            <el-input
              v-model="filterText"
              placeholder="请输入小区名称"
              clearable
              size="small"
              prefix-icon="el-icon-search"
              style="margin-bottom: 20px"
            />
          </div>
          <div>
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
                <span class="content">{{ node.label }}</span>
              </span>
            </el-tree>
          </div>
        </el-col>

        <el-col :span="20" :xs="24">
          <el-tabs v-model="activeName">
            <el-tab-pane label="寻人启事" name="first">
              <el-form
                :model="queryParams"
                ref="queryForm"
                size="small"
                :inline="true"
                v-show="showSearch"
                label-width="68px"
              >
                <el-form-item label="姓名" prop="lostName">
                  <el-input
                    v-model="queryParams.lostName"
                    placeholder="请输入姓名"
                    clearable
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>

                <el-form-item label="联系人" prop="contactName">
                  <el-input
                    v-model="queryParams.contactName"
                    placeholder="请输入联系人"
                    clearable
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>

                <el-form-item label="联系电话" prop="contactPhone">
                  <el-input
                    v-model="queryParams.contactPhone"
                    placeholder="请输入联系电话"
                    clearable
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>

                <el-form-item label="目前状态" prop="lostStatus">
                  <el-select
                    v-model="queryParams.lostStatus"
                    placeholder="请选择目前状态"
                    style="width: 180px"
                  >
                    <el-option label="寻找中" value="0" />
                    <el-option label="已找回" value="1" />
                    <el-option label="已撤回" value="2" />
                  </el-select>
                </el-form-item>

                <el-form-item>
                  <el-button
                    type="primary"
                    icon="el-icon-search"
                    size="mini"
                    @click="handleQuery"
                    >搜索</el-button
                  >
                  <el-button
                    icon="el-icon-refresh"
                    size="mini"
                    @click="resetQuery"
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
                    @click="handleAdd1"
                    >新增</el-button
                  >
                </el-col>
                <right-toolbar
                  :showSearch.sync="showSearch"
                  @queryTable="getList"
                ></right-toolbar>
              </el-row>

              <el-table v-loading="loading" :data="peopleLostList">
                <el-table-column label="姓名" align="center" prop="lostName" />
                <el-table-column label="年龄" align="center" prop="lostAge" />
                <el-table-column
                  label="失踪日期"
                  align="center"
                  prop="lostDate"
                />
                <el-table-column
                  label="联系人"
                  align="center"
                  prop="contactName"
                />
                <el-table-column
                  label="联系电话"
                  align="center"
                  prop="contactPhone"
                />
                <el-table-column
                  label="目前状态"
                  align="center"
                  prop="lostStatus"
                >
                  <template slot-scope="scope">
                    <div v-if="scope.row.lostStatus == '0'" type="success">
                      寻找中
                    </div>
                    <div v-else-if="scope.row.lostStatus == '1'" type="warning">
                      已找回
                    </div>
                    <div v-else-if="scope.row.lostStatus == '2'" type="warning">
                      已撤回
                    </div>
                  </template>
                </el-table-column>
                <el-table-column label="图片" align="center" prop="thumbnail">
                  <template slot-scope="scope">
                    <image-preview
                      :src="scope.row.thumbnail"
                      :width="50"
                      :height="50"
                    />
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
                      @click="handlePeopleLost(scope.row)"
                      >查看
                    </el-button>
                    <!-- <br> -->
                    <el-button
                      size="mini"
                      type="text"
                      icon="el-icon-edit"
                      @click="handlePeopleLostUpdate(scope.row)"
                      >修改
                    </el-button>
                    <!-- <br> -->
                    <el-button
                      size="mini"
                      type="text"
                      style="color: #f56c6c"
                      icon="el-icon-delete"
                      @click="handlePeopleDelete(scope.row)"
                      >删除</el-button
                    >
                    <!-- <br v-if="scope.row.lostStatus == '0'"> -->
                    <el-button
                      v-if="scope.row.lostStatus == '0'"
                      size="mini"
                      type="text"
                      icon="el-icon-edit"
                      @click="handlePeopleLostUpdateStatus(scope.row, '1')"
                      >已找回
                    </el-button>
                    <!-- <br v-if="scope.row.lostStatus == '0'"> -->
                    <el-button
                      v-if="scope.row.lostStatus == '0'"
                      size="mini"
                      type="text"
                      icon="el-icon-edit"
                      @click="handlePeopleLostUpdateStatus(scope.row, '2')"
                      >撤销
                    </el-button>
                    <!-- <br v-if="scope.row.lostStatus == '2'"> -->
                    <el-button
                      v-if="scope.row.lostStatus == '2'"
                      size="mini"
                      type="text"
                      icon="el-icon-edit"
                      @click="handlePeopleLostUpdateStatus(scope.row, '0')"
                      >发布
                    </el-button>
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
              <br />
            </el-tab-pane>
            <el-tab-pane label="寻物启事" name="second">
              <el-form
                :model="queryParams2"
                ref="queryForm2"
                size="small"
                :inline="true"
                v-show="showSearch"
                label-width="68px"
              >
                <el-form-item label="物品名称" prop="lostName">
                  <el-input
                    v-model="queryParams2.lostName"
                    placeholder="请输入物品名称"
                    clearable
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>

                <el-form-item label="联系人" prop="contactName">
                  <el-input
                    v-model="queryParams2.contactName"
                    placeholder="请输入联系人"
                    clearable
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>

                <el-form-item label="联系电话" prop="contactPhone">
                  <el-input
                    v-model="queryParams2.contactPhone"
                    placeholder="请输入联系电话"
                    clearable
                    @keyup.enter.native="handleQuery"
                  />
                </el-form-item>

                <el-form-item label="目前状态" prop="lostStatus">
                  <el-select
                    v-model="queryParams2.lostStatus"
                    placeholder="请选择目前状态"
                    style="width: 180px"
                  >
                    <el-option label="寻找中" value="0" />
                    <el-option label="已找回" value="1" />
                    <el-option label="已撤回" value="2" />
                  </el-select>
                </el-form-item>

                <el-form-item>
                  <el-button
                    type="primary"
                    icon="el-icon-search"
                    size="mini"
                    @click="handleQuery"
                    >搜索</el-button
                  >
                  <el-button
                    icon="el-icon-refresh"
                    size="mini"
                    @click="resetQuery"
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
                    @click="handleAdd2"
                    >新增</el-button
                  >
                </el-col>
                <right-toolbar
                  :showSearch.sync="showSearch"
                  @queryTable="getList"
                ></right-toolbar>
              </el-row>
              <el-table v-loading="loading" :data="thingLostList">
                <el-table-column
                  label="物品名称"
                  align="center"
                  prop="lostName"
                  show-overflow-tooltip
                />
                <el-table-column
                  label="丢失时间"
                  align="center"
                  prop="lostDate"
                />
                <el-table-column
                  label="联系人"
                  align="center"
                  prop="contactName"
                />
                <el-table-column
                  label="联系电话"
                  align="center"
                  prop="contactPhone"
                />
                <el-table-column
                  label="目前状态"
                  align="center"
                  prop="lostStatus"
                >
                  <template slot-scope="scope">
                    <div v-if="scope.row.lostStatus == '0'" type="success">
                      寻找中
                    </div>
                    <div v-else-if="scope.row.lostStatus == '1'" type="warning">
                      已找到
                    </div>
                    <div v-else-if="scope.row.lostStatus == '2'" type="warning">
                      已撤回
                    </div>
                  </template>
                </el-table-column>
                <el-table-column label="图片" align="center" prop="thumbnail">
                  <template slot-scope="scope">
                    <el-image
                      v-if="scope.row.thumbnail"
                      :src="scope.row.thumbnail"
                      style="width: 80px; height: 50px"
                    >
                      <div slot="error" class="image-slot">
                        <i class="el-icon-picture-outline"></i>
                      </div>
                    </el-image>
                    <span v-else>-</span>
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
                      @click="handleThingLost(scope.row)"
                      >查看
                    </el-button>
                    <br />
                    <el-button
                      size="mini"
                      type="text"
                      icon="el-icon-edit"
                      @click="handleThingLostUpdate(scope.row)"
                      >修改
                    </el-button>
                    <br />
                    <el-button
                      size="mini"
                      type="text"
                      style="color: #f56c6c"
                      icon="el-icon-delete"
                      @click="handleThingDelete(scope.row)"
                      >删除</el-button
                    >
                    <br v-if="scope.row.lostStatus == '0'" />
                    <el-button
                      v-if="scope.row.lostStatus == '0'"
                      size="mini"
                      type="text"
                      icon="el-icon-edit"
                      @click="handleThingLostUpdateStatus(scope.row, '1')"
                      >已找回
                    </el-button>
                    <br v-if="scope.row.lostStatus == '0'" />
                    <el-button
                      v-if="scope.row.lostStatus == '0'"
                      size="mini"
                      type="text"
                      icon="el-icon-edit"
                      @click="handleThingLostUpdateStatus(scope.row, '2')"
                      >撤销
                    </el-button>
                    <br v-if="scope.row.lostStatus == '2'" />
                    <el-button
                      v-if="scope.row.lostStatus == '2'"
                      size="mini"
                      type="text"
                      icon="el-icon-edit"
                      @click="handleThingLostUpdateStatus(scope.row, '0')"
                      >发布
                    </el-button>
                  </template>
                </el-table-column>
              </el-table>

              <pagination
                v-show="total2 > 0"
                :total="total2"
                :page.sync="queryParams2.pageNum"
                :limit.sync="queryParams2.pageSize"
                @pagination="getList"
              />
              <br />
            </el-tab-pane>
          </el-tabs>
        </el-col>
      </el-row>
    </div>

    <!--  查看  -->
    <div v-show="openView === 1">
      <h4 class="form-header" style="color: #0558e7">寻人详情</h4>
      <div style="display: flex">
        <el-form ref="form" :model="form" label-width="180px">
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="姓名:">
                <div style="width: 180px">{{ form.lostName }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="性别:">
                <div v-if="form.gender == 0">男</div>
                <div v-else-if="form.gender == 1">女</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="身高:">
                <div style="width: 180px">{{ form.lostHeight }}</div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="年龄:">
                <div style="width: 180px">{{ form.lostAge }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="籍贯:">
                <div style="width: 180px">{{ form.hometown }}</div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="失踪日期:">
                <div style="width: 180px">{{ form.lostDate }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="失踪地点:">
                <div style="width: 180px">{{ form.lostAddress }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="状态:">
                <div v-if="form.lostStatus == 0">寻找中</div>
                <div v-else-if="form.lostStatus == 1">已找回</div>
                <div v-else-if="form.lostStatus == 2">已撤销</div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="寻人类别:">
                <div style="width: 180px">{{ form.peopleType }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="联系人:">
                <div style="width: 180px">{{ form.contactName }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="联系电话:">
                <div style="width: 180px">{{ form.contactPhone }}</div>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="21" :offset="1">
              <el-form-item label="简述:">
                <div style="width: 180px">{{ form.lostInfo }}</div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="寻人图片:">
                <image-preview
                  :src="form.thumbnail"
                  :width="100"
                  :height="100"
                />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <h4 class="form-header" style="color: #0558e7" />
      <div slot="footer" class="dialog-footer">
        <el-row>
          <el-col :offset="20" :span="2">
            <el-button size="small" @click="cancel">返回</el-button>
          </el-col>
        </el-row>
      </div>
    </div>
    <div v-show="openView === 2">
      <h4 class="form-header" style="color: #0558e7">寻物详情</h4>
      <div style="display: flex">
        <el-form ref="form" :model="form2" label-width="180px">
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="物品名称:">
                <div style="width: 180px">{{ form2.lostName }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="丢失时间:">
                <div style="width: 180px">{{ form2.lostDate }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="丢失地点:">
                <div style="width: 180px">{{ form2.lostAddress }}</div>
              </el-form-item>
            </el-col>
          </el-row>

          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="联系人:">
                <div style="width: 180px">{{ form2.contactName }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="联系电话:">
                <div style="width: 180px">{{ form2.contactPhone }}</div>
              </el-form-item>
            </el-col>
            <el-col :span="6" :offset="2">
              <el-form-item label="目前状态:">
                <div v-if="form2.lostStatus == 0">寻找中</div>
                <div v-else-if="form2.lostStatus == 1">已找回</div>
                <div v-else-if="form2.lostStatus == 2">已撤销</div>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="简述:">
                <div style="width: 180px">{{ form2.lostInfo }}</div>
              </el-form-item>
            </el-col>
          </el-row>
          <el-row>
            <el-col :span="6" :offset="1">
              <el-form-item label="物品图片:">
                <image-preview
                  :src="form2.thumbnail"
                  :width="100"
                  :height="100"
                />
              </el-form-item>
            </el-col>
          </el-row>
        </el-form>
      </div>
      <h4 class="form-header" style="color: #0558e7" />
      <div slot="footer" class="dialog-footer">
        <el-row>
          <el-col :offset="20" :span="2">
            <el-button size="small" @click="cancel">返回</el-button>
          </el-col>
        </el-row>
      </div>
    </div>

    <!-- 寻人启事添加或修改对话框 -->
    <el-dialog
      :title="formTitle"
      :visible.sync="formOpen"
      width="80%"
      append-to-body
    >
      <el-form ref="form" :model="form" :rules="rules1" label-width="90px">
        <el-row>
          <el-col :span="7" :offset="1">
            <el-form-item label="寻人照片:" prop="thumbnail">
              <image-upload v-model="form.thumbnail" :limit="1" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="5" :offset="1">
            <el-form-item label="姓名:" prop="lostName">
              <el-input
                v-model="form.lostName"
                placeholder="请输入姓名"
                style="width: 195px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="性别:" prop="gender">
              <el-select
                v-model="form.gender"
                placeholder="请选择性别"
                clearable
                style="width: 195px"
              >
                <el-option
                  v-for="i in gender"
                  :key="i.value"
                  :value="i.value"
                  :label="i.label"
                />
              </el-select>
            </el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="年龄:" prop="lostAge">
              <el-input-number
                :min="0"
                :max="200"
                v-model="form.lostAge"
                placeholder="请输入年龄"
                style="width: 195px"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="5" :offset="1">
            <el-form-item label="出生日期:" prop="birthday">
              <el-date-picker
                v-model="form.birthday"
                value-format="yyyy-MM-dd"
                type="date"
                placeholder="选择出生日期"
                style="width: 195px"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="身高:" prop="lostHeight">
              <el-input
                v-model="form.lostHeight"
                placeholder="请输入身高"
                style="width: 195px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="籍贯:" prop="hometown">
              <el-input
                v-model="form.hometown"
                placeholder="请输入籍贯"
                style="width: 195px"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="5" :offset="1">
            <el-form-item label="失踪地点:" prop="lostAddress">
              <el-input
                v-model="form.lostAddress"
                placeholder="请输入失踪地点"
                style="width: 195px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="失踪日期:" prop="lostDate">
              <el-date-picker
                v-model="form.lostDate"
                value-format="yyyy-MM-dd HH:mm:ss"
                type="datetime"
                placeholder="选择日期"
                style="width: 195px"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="寻人类别:" prop="peopleType">
              <el-input
                v-model="form.peopleType"
                placeholder="请输入寻人类别"
                style="width: 195px"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="5" :offset="1">
            <el-form-item label="联系人:" prop="contactName">
              <el-input
                v-model="form.contactName"
                placeholder="请输入联系人"
                style="width: 195px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="联系电话:" prop="contactPhone">
              <el-input
                v-model="form.contactPhone"
                placeholder="请输入联系电话"
                style="width: 195px"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="21" :offset="1">
            <el-form-item label="简述:" prop="lostInfo">
              <el-input
                type="textarea"
                v-model="form.lostInfo"
                placeholder="请输入简述"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm1">确 定</el-button>
        <el-button @click="cancel1">取 消</el-button>
      </div>
    </el-dialog>

    <!-- 寻物启事添加或修改对话框 -->
    <el-dialog
      :title="form2Title"
      :visible.sync="form2Open"
      width="80%"
      append-to-body
    >
      <el-form ref="form2" :model="form2" :rules="rules2" label-width="90px">
        <el-row>
          <el-col :span="7" :offset="1">
            <el-form-item label="物品照片:" prop="thumbnail">
              <image-upload v-model="form2.thumbnail" :limit="1" />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="5" :offset="1">
            <el-form-item label="物品名称:" prop="lostName">
              <el-input
                v-model="form2.lostName"
                placeholder="请输入物品名称"
                style="width: 195px"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="5" :offset="1">
            <el-form-item label="失踪地点:" prop="lostAddress">
              <el-input
                v-model="form2.lostAddress"
                placeholder="请输入失踪地点"
                style="width: 195px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="丢失日期:" prop="lostDate">
              <el-date-picker
                v-model="form2.lostDate"
                value-format="yyyy-MM-dd HH:mm:ss"
                type="datetime"
                placeholder="选择日期"
                style="width: 195px"
              >
              </el-date-picker>
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="5" :offset="1">
            <el-form-item label="联系人:" prop="contactName">
              <el-input
                v-model="form2.contactName"
                placeholder="请输入联系人"
                style="width: 195px"
              />
            </el-form-item>
          </el-col>
          <el-col :span="5" :offset="2">
            <el-form-item label="联系电话:" prop="contactPhone">
              <el-input
                v-model="form2.contactPhone"
                placeholder="请输入联系电话"
                style="width: 195px"
              />
            </el-form-item>
          </el-col>
        </el-row>
        <el-row>
          <el-col :span="21" :offset="1">
            <el-form-item label="简述:" prop="lostInfo">
              <el-input
                type="textarea"
                v-model="form2.lostInfo"
                placeholder="请输入简述"
              />
            </el-form-item>
          </el-col>
        </el-row>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button type="primary" @click="submitForm2">确 定</el-button>
        <el-button @click="cancel2">取 消</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { getCurrentList } from "@/api/estate/community";
import {
  delPeopleLost,
  delThingLost,
  getPeopleLostList,
  peopleLostUpdate,
  peopleLostSave,
  getPeopleLostInfo,
  getThingLostList,
  updatePeoLostStatus,
  updateThingLostStatus,
  thingLostSave,
  thingLostUpdate,
  getThingLostInfo,
} from "@/api/estate/customer/lost/lost";

export default {
  name: "lost",
  data() {
    return {
      activeName: "first",
      // 树筛选文本
      filterText: "",
      openView: false,
      // 遮罩层
      loading: true,
      // 显示搜索条件
      showSearch: true,
      // 总条数
      total: 0,
      total2: 0,
      // 表格数据
      peopleLostList: [],
      thingLostList: [],
      //树结构
      comOptions: [],
      defaultProps: {
        children: "communityId",
        label: "communityName",
      },
      gender: [
        { label: "男", value: "0" },
        { label: "女", value: "1" },
      ],
      // 表单校验
      lostId: undefined,

      rules1: {
        thumbnail: [
          { required: true, message: "图片不能为空", trigger: "blur" },
        ],
        lostName: [
          { required: true, message: "姓名不能为空", trigger: "blur" },
        ],
        gender: [{ required: true, message: "性别不能为空", trigger: "blur" }],
        lostHeight: [
          { required: true, message: "身高不能为空", trigger: "blur" },
        ],
        lostAge: [
          { required: true, message: "年龄不能为空", trigger: "trigger" },
        ],
        birthday: [
          { required: true, message: "出生日期不能为空", trigger: "blur" },
        ],
        hometown: [
          { required: true, message: "籍贯不能为空", trigger: "trigger" },
        ],
        lostDate: [
          { required: true, message: "失踪时间不能为空", trigger: "trigger" },
        ],
        lostAddress: [
          { required: true, message: "失踪地址不能为空", trigger: "trigger" },
        ],
        contactName: [
          { required: true, message: "联系人不能为空", trigger: "trigger" },
        ],
        peopleType: [
          { required: true, message: "寻人类别不能为空", trigger: "blur" },
        ],
        contactPhone: [
          { required: true, message: "联系人电话不能为空", trigger: "blur" },
        ],
      },
      rules2: {
        thumbnail: [
          { required: true, message: "图片不能为空", trigger: "blur" },
        ],
        lostName: [
          { required: true, message: "物品名称不能为空", trigger: "blur" },
        ],
        lostDate: [
          { required: true, message: "丢失时间不能为空", trigger: "trigger" },
        ],
        lostAddress: [
          { required: true, message: "丢失地址不能为空", trigger: "trigger" },
        ],
        contactName: [
          { required: true, message: "联系人不能为空", trigger: "trigger" },
        ],
        contactPhone: [
          { required: true, message: "联系人电话不能为空", trigger: "blur" },
        ],
      },
      // 查询参数
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        communityId: null,
        lostName: null,
        lostStatus: null,
        contactName: null,
        contactPhone: null,
      },
      queryParams2: {
        pageNum: 1,
        pageSize: 10,
        communityId: null,
        lostName: null,
        lostStatus: null,
        contactName: null,
        contactPhone: null,
      },
      // 表单参数
      form: {},
      formTitle: "",
      formOpen: false,
      form2: {},
      form2Title: "",
      form2Open: false,
    };
  },
  created() {
    this.getTreeSelect();
  },
  watch: {
    filterText(val) {
      this.$refs.tree.filter(val);
    },
  },
  methods: {
    // 筛选节点
    filterNode(value, data) {
      if (!value) return true;
      return data.communityName.indexOf(value) !== -1;
    },
    // 节点单击事件
    handleNodeClick(data) {
      this.queryParams.communityId = data.communityId;
      this.queryParams2.communityId = data.communityId;
      this.communityId = data.communityId;
      this.getList();
    },
    /** 树结构 */
    getTreeSelect() {
      getCurrentList().then((response) => {
        this.comOptions = response.data;
        this.queryParams.communityId = this.comOptions[0].communityId;
        this.queryParams2.communityId = this.comOptions[0].communityId;

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
    },
    /** 搜索按钮操作 */
    handleQuery() {
      this.getList();
    },
    /** 重置按钮操作 */
    resetQuery() {
      this.resetForm("queryForm");
      this.resetForm("queryForm2");
      this.handleQuery();
    },
    handleAdd1() {
      this.formTitle = "添加寻人启事";
      this.formOpen = true;
      this.reset1();
    },
    handlePeopleLostUpdate(item) {
      this.reset1();
      var lostId = item.lostId;
      getPeopleLostInfo(lostId).then((res) => {
        this.form = res.data;
        this.formTitle = "修改寻人启事";
        this.formOpen = true;
      });
    },
    handleThingLostUpdate(item) {
      this.reset2();
      var lostId = item.lostId;
      getThingLostInfo(lostId).then((res) => {
        this.form2 = res.data;
        this.form2Title = "修改寻物启事";
        this.form2Open = true;
      });
    },
    cancel1() {
      this.formOpen = false;
      this.reset1();
      this.getList();
    },
    submitForm1() {
      this.$refs["form"].validate((valid) => {
        if (valid) {
          this.form.communityId = this.queryParams.communityId;
          if (this.form.lostId) {
            peopleLostUpdate(this.form).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.cancel1();
            });
          } else {
            peopleLostSave(this.form).then((response) => {
              this.$modal.msgSuccess("新增成功");

              this.cancel1();
            });
          }
        }
      });
    },

    submitForm2() {
      this.$refs["form2"].validate((valid) => {
        if (valid) {
          this.form2.communityId = this.queryParams.communityId;
          if (this.form2.lostId) {
            thingLostUpdate(this.form2).then((response) => {
              this.$modal.msgSuccess("修改成功");
              this.cancel2();
            });
          } else {
            thingLostSave(this.form2).then((response) => {
              this.$modal.msgSuccess("新增成功");

              this.cancel2();
            });
          }
        }
      });
    },
    handlePeopleLostUpdateStatus(item, status) {
      var msg = "";
      if (status == "1") {
        msg = "已找回";
      }
      if (status == "2") {
        msg = "撤回";
      }
      if (status == "0") {
        msg = "发布";
      }
      this.$modal
        .confirm("是否确认" + msg + "选中的数据项？")
        .then(function () {
          return updatePeoLostStatus(item.lostId, status);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess(msg + "成功");
        })
        .catch(() => {});
    },
    handleThingLostUpdateStatus(item, status) {
      var msg = "";
      if (status == "1") {
        msg = "已找回";
      }
      if (status == "2") {
        msg = "撤回";
      }
      if (status == "0") {
        msg = "发布";
      }
      this.$modal
        .confirm("是否确认" + msg + "选中的数据项？")
        .then(function () {
          return updateThingLostStatus(item.lostId, status);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess(msg + "成功");
        })
        .catch(() => {});
    },
    handleAdd2() {
      this.res;
      this.form2Title = "添加寻物启事";
      this.form2Open = true;
    },

    cancel2() {
      this.form2Open = false;
      this.reset2();
      this.getList();
    },
    reset1() {
      this.form = {
        lostId: undefined,
        communityId: undefined,
        thumbnail: undefined,
        lostName: undefined,
        gender: undefined,
        lostHeight: undefined,
        lostAge: undefined,
        birthday: undefined,
        hometown: undefined,
        lostDate: undefined,
        lostAddress: undefined,
        contactName: undefined,
        peopleType: undefined,
        contactPhone: undefined,
        lostInfo: undefined,
      };
    },
    reset2() {
      this.form2 = {
        lostId: undefined,
        communityId: undefined,
        thumbnail: undefined,
        lostName: undefined,
        lostDate: undefined,
        lostAddress: undefined,
        contactName: undefined,
        contactPhone: undefined,
        lostInfo: undefined,
      };
    },

    /** 查询列表 */
    getList() {
      this.openView = 0;
      this.loading = true;
      //寻人列表
      getPeopleLostList(this.queryParams).then((response) => {
        this.peopleLostList = response.rows;
        this.total = response.total;
      });
      //寻物列表
      getThingLostList(this.queryParams2).then((response) => {
        this.thingLostList = response.rows;
        this.total2 = response.total;
      });

      this.loading = false;
    },
    /** 查看详情*/
    handlePeopleLost(row) {
      this.openView = 1;
      this.form = row;
    },
    handleThingLost(row) {
      this.openView = 2;
      this.form2 = row;
    },
    /** 删除按钮操作 */
    handlePeopleDelete(row) {
      this.$modal
        .confirm("是否确认删除选中的数据项？")
        .then(function () {
          return delPeopleLost(row.lostId);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
    /** 删除按钮操作 */
    handleThingDelete(row) {
      this.$modal
        .confirm("是否确认删除选中的数据项？")
        .then(function () {
          return delThingLost(row.lostId);
        })
        .then(() => {
          this.getList();
          this.$modal.msgSuccess("删除成功");
        })
        .catch(() => {});
    },
  },
};
</script>

<style scoped>
/*设置高亮颜色*/
/deep/
  .el-tree--highlight-current
  .el-tree-node.is-current
  > .el-tree-node__content {
  background-color: #b3f1b6 !important;
}
.content {
  font-size: 14px;
}
.thumbnail {
  height: 100px;
  width: 200px;
}
</style>

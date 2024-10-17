<template>
  <div class="app-container">
    <div v-show="openView">
      <!--  搜索栏  -->
      <el-form :model="queryParams" ref="queryForm" size="small" :inline="true" v-show="showSearch">
        <el-form-item label="公示标题" prop="bulletinTitle">
          <el-input
            v-model="queryParams.bulletinTitle"
            placeholder="请输入"
            clearable
            prefix-icon="el-icon-search"
            @keyup.enter.native="handleQuery"
          />
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
                >新增</el-button>

              <el-button
                type="danger"
                plain
                icon="el-icon-delete"
                style="color: #f56c6c"
                size="mini"
                :disabled="multiple"
                @click="handleDelete"
                >删除</el-button>
            </el-col>
            <right-toolbar :showSearch.sync="showSearch" @queryTable="getList"></right-toolbar>
          </el-row>

      <!-- 展示表格   -->
      <el-table v-loading="loading" :data="formList" row-key="bulletinId">
        <el-table-column type="selection" width="50" align="center"/>
        <el-table-column label="公示标题" align="center" prop="bulletinTitle"/>
        <el-table-column label="公示发布时间" align="center" prop="releaseTime">
          <template slot-scope="scope">
            {{ scope.row.status == '0' ? '-' : scope.row.releaseTime }}
          </template>
        </el-table-column>
        <el-table-column label="创建人" align="center" prop="createBy"/>
        <el-table-column label="创建时间" align="center" prop="createTime" width="180">
          <template slot-scope="scope">
            <span>{{ parseTime(scope.row.createTime) }}</span>
          </template>
        </el-table-column>
        <el-table-column label="操作" align="center" class-name="small-padding fixed-width">
          <template slot-scope="scope">
            <el-button
              size="mini" type="text"
              class="text-success"
              icon="el-icon-view"
              @click="handlerDetail(scope.row)"
            >查看
            </el-button>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页   -->
      <pagination
        v-show="total>0"
        :total="total"
        :page.sync="queryParams.pageNum"
        :limit.sync="queryParams.pageSize"
        @pagination="getList"
      />
    </div>
    <!--  新增  -->
    <div v-show="detailShow">
      <el-form ref="detailForm" :model="detailForm" label-width="140px">
        <div style="margin: auto 92%">
          <el-button @click="cancel">关 闭</el-button>
        </div>

        <h4 class="form-header">物业通报公示详情</h4>

        <el-row>
          <el-col :span="4" :offset="2">
            <el-form-item label="公示标题:">{{ detailForm.bulletinTitle }}</el-form-item>
          </el-col>

          <el-col :span="4" :offset="2">
            <el-form-item label="创建人:">{{ detailForm.createBy }}</el-form-item>
          </el-col>

          <el-col :span="4" :offset="2">
            <el-form-item label="发布状态:">
              <el-tag>
                {{ detailForm.status === "0" ? "未发布" : detailForm.status === "1" ? "已发布" : "已撤回" }}
              </el-tag>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="4" :offset="2">
            <el-form-item label="公示发布时间:">{{ detailForm.status === "0" ? "-" : detailForm.releaseTime }}</el-form-item>
          </el-col>

          <el-col :span="4" :offset="2">
            <el-form-item label="创建时间:">{{ detailForm.createTime }}</el-form-item>
          </el-col>

        </el-row>

        <el-row>
          <el-col :span="16" :offset="2">
            <el-form-item label="通报物业:">{{ detailForm.estateName }}</el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="14">
            <el-form-item label="公示内容" prop="content">
              <editor :type="base64" v-model="detailForm.content" :min-height="192"/>
            </el-form-item>
          </el-col>
        </el-row>

        <h4 class="form-header"></h4>
      </el-form>
    </div>
    <!--  详情页  -->
    <div v-show="detailShow">
      <el-form ref="detailForm" :model="detailForm" label-width="140px">
        <div style="margin: auto 92%">
          <el-button @click="cancel">关 闭</el-button>
        </div>

        <h4 class="form-header">物业通报公示详情</h4>

        <el-row>
          <el-col :span="4" :offset="2">
            <el-form-item label="公示标题:">{{ detailForm.bulletinTitle }}</el-form-item>
          </el-col>

          <el-col :span="4" :offset="2">
            <el-form-item label="创建人:">{{ detailForm.createBy }}</el-form-item>
          </el-col>

          <el-col :span="4" :offset="2">
            <el-form-item label="发布状态:">
              <el-tag>
                {{ detailForm.status === "0" ? "未发布" : detailForm.status === "1" ? "已发布" : "已撤回" }}
              </el-tag>
            </el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="4" :offset="2">
            <el-form-item label="公示发布时间:">{{ detailForm.status === "0" ? "-" : detailForm.releaseTime }}</el-form-item>
          </el-col>

          <el-col :span="4" :offset="2">
            <el-form-item label="创建时间:">{{ detailForm.createTime }}</el-form-item>
          </el-col>

        </el-row>

        <el-row>
          <el-col :span="16" :offset="2">
            <el-form-item label="通报物业:">{{ detailForm.estateName }}</el-form-item>
          </el-col>
        </el-row>

        <el-row>
          <el-col :span="14">
            <el-form-item label="公示内容" prop="content">
              <editor :type="base64" v-model="detailForm.content" :min-height="192"/>
            </el-form-item>
          </el-col>
        </el-row>

        <h4 class="form-header"></h4>
      </el-form>
    </div>

  </div>
</template>

<script>
import {selectBulletInList,getDetail} from "@/api/estate/info/bulletin";

export default {
  name: "index",
  data() {
    return {
      //总条数
      total: 0,
      //主页面展示
      openView: true,
      //下拉框选项集
      selectOptions: [
        {
          label: "未发布",
          value: "0",
        },
        {
          label: "已发布",
          value: "1",
        },
        {
          label: "已撤回",
          value: "2",
        }
      ],
      //表单是否打开
      dialogFormVisible: false,
      //参数集合
      queryParams: {
        pageNum: 1,
        pageSize: 10,
        bulletinTitle: '',
      },
      showSearch: true,
      loading: false,
      formList: [],
      //详情页是否展示
      detailShow: false,
      //详情表单数据
      detailForm: {},
      //物业选项集
      estateList: [],
    }
  },
  created() {
    this.getList()
  },
  methods: {
    //显示条数变化
    handleSizeChange(val) {
      this.queryParams.pageSize = val
      this.getList()
    },

    //页数变化
    handleCurrentChange(val) {
      this.queryParams.pageNum = val
      this.getList()
    },

    //获取主页面数据
    getList() {
      this.loading = true;
      selectBulletInList(this.queryParams).then((res) => {
        this.formList = res.rows
        this.total = res.total;
      })
      this.loading = false;
    },

    //根据搜索条件查询列表
    handleQuery() {
      this.getList();
    },

    //详情页展示
    handlerDetail(obj) {
      getDetail({bulletinId: obj.bulletinId}).then((res) => {
        this.detailForm = res.data
        this.getEstateNames(res.data.estateList)
      })
      this.openView = false
      this.detailShow = true
    },

    //编排物业名称
    getEstateNames(estateList) {
      let estateName = '';
      estateList.forEach((entry, index) => {
        if (index === estateList.length - 1) {
          estateName += entry.estateName;
        } else {
          estateName += entry.estateName + ","
        }
      })
      this.detailForm.estateName = estateName
    },

    //关闭详情页
    cancel() {
      this.detailForm = {}
      this.form = {}
      this.detailShow = false
      this.openView = true;
    },

    resetQuery() {
      this.$refs["queryForm"].resetFields();
      this.getList();
    },
  },
}
</script>

<style scoped>

</style>

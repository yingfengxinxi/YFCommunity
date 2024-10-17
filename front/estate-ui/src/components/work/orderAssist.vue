<template>
  <!--工单协助人信息 通用 -->
  <div>
    <div>
      <h4 class="form-header">工单协助人信息</h4>
    </div>
    <el-row>
<!--    <el-divider></el-divider>-->
      <el-col :span="24">
        <el-table
          border
          type="index"
          highlight-current-row
          :data="detailListGenerate"
        >
          <el-table-column
            label="人员姓名"
            align="center"
            :show-overflow-tooltip="true"
            prop="staffName"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.staffName }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="手机号"
            align="center"
            :show-overflow-tooltip="true"
            prop="staffPhone"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.staffPhone }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="身份证号"
            align="center"
            :show-overflow-tooltip="true"
            prop="cardNo"
          >
            <template slot-scope="scope">
              <span>{{ scope.row.cardNo }}</span>
            </template>
          </el-table-column>
          <el-table-column
            label="人员类别"
            align="center"
            :show-overflow-tooltip="true"
            prop="personnelType"
          >
            <template slot-scope="scope">
              <span>{{ parseWorkPost(scope.row.workPost) }}</span>
            </template>
          </el-table-column>
        </el-table>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import {assistList} from "@/api/estate/work/deviceorder/deviceOrder";
import {desensitizationName, desensitization} from "@/utils/common";

export default {
  name: "orderAssist",
  dicts: ['work_post'],

  data() {
    return {
      //执行情况详情信息
      detailListGenerate: [],
      query: this.father
    }
  },

  props: {
    father: {
      //属性
      type: Object,
      required: true,
      default: {},
    },
  },
  created() {
    assistList(this.query).then(res => {
      this.detailListGenerate = res.data;

    });
  },
  methods: {
    //设备类型
    parseWorkPost(workPost) {
      return this.selectDictLabel(this.dict.type.work_post, workPost);
    },

//姓名脱敏
    handleName(name) {
      return desensitizationName(name)
    },

    //脱敏身份证号
    handleCard(card) {
      return desensitization(card, 4, 14)
    },
    //脱敏身份证号
    handlePhone(card) {
      return desensitization(card, 3, 7)
    },
  }

}
</script>

<style scoped>

</style>

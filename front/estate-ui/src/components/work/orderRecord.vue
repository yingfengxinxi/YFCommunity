<template>
  <!--执行详情 通用 -->
  <div>
    <div>
      <h4 class="form-header">执行详情</h4>
    </div>
    <el-row>
      <div v-for="(item, index) in detailListThreeList" :key="index">

        <el-row :gutter="12">
          <el-col>
            <el-card shadow="always">
              <el-descriptions>
                <el-descriptions-item label="操作时间"
                >{{ item.createTime }}
                </el-descriptions-item
                >
                <el-descriptions-item label="操作"
                >{{ item.orderResult }}
                </el-descriptions-item
                >
                <el-descriptions-item label="操作人"
                >{{ item.userName }}
                </el-descriptions-item
                >
                <el-descriptions-item v-if="item.relevant != ''" label="相关人"
                >{{ item.relevant }}
                </el-descriptions-item
                >
              </el-descriptions>
            </el-card>
          </el-col>

        </el-row>
      </div>
    </el-row>
  </div>
</template>

<script>

import {detailRecordGeneral} from "@/api/estate/work/orderDisaster/DisasterOrder";
import {desensitizationName, desensitization} from "@/utils/common";

export default {
  name: "orderRecord",
  data() {
    return {
      //执行情况详情信息
      detailListThreeList: [],
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
    detailRecordGeneral(this.query).then(res => {
      this.detailListThreeList = res.data;
    });
  },

  methods: {
    //姓名脱敏
    handleName(name) {
      return desensitizationName(name)
    },

  }
}
</script>

<style scoped>

</style>

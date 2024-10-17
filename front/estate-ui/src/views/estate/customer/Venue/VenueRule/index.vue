<template>

  <div class="app-container">
    <el-form ref="form" :model="form" label-width="150px">
      <el-row>
        <el-col :span="8" :offset="5">
          <el-form-item label="可使用时间段">
            <el-time-picker
              is-range style="width: 100%"
              v-model="datetime"
              range-separator="至"
              start-placeholder="开始时间"
              end-placeholder="结束时间"
              placeholder="选择时间范围"
              value-format="HH:mm"
              format="HH:mm"
            >
            </el-time-picker>

          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8" :offset="5">
          <el-form-item label="与下一次间隔(时)">
            <el-input-number v-model="form.gapHour" :max="12" :min="0" style="width: 100%"/>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="8" :offset="5">
          <el-form-item label="结束生成保洁工单">
            <el-radio v-model="form.createOrder" label="0">是</el-radio>
            <el-radio v-model="form.createOrder" label="1">否</el-radio>
          </el-form-item>
        </el-col>
      </el-row>

      <el-row>
        <el-col :span="12" :offset="5">
          <el-form-item label="预约声明">
            <el-input
              type="textarea"
              placeholder="请输入内容"
              rows="10"
              v-model="form.statement">
            </el-input>
          </el-form-item>
        </el-col>

      </el-row>
      <el-row>
        <el-col :span="12" :offset="5">
          <el-form-item label="场馆使用规则">
            <el-input
              type="textarea"
              placeholder="请输入内容"
              rows="10"
              v-model="form.useRule">
            </el-input>
          </el-form-item>
        </el-col>
      </el-row>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-row>
        <el-col :offset="16">
          <el-button type="primary" @click="submitForm">修 改</el-button>
        </el-col>
      </el-row>

    </div>
  </div>

</template>

<script>
import {addrule, gl, updaterule} from "@/api/estate/customer/Venue/VenueRule";

export default {
  name: "index",
  data() {
    return {
      datetime: [],

      // 表单参数
      form: {
        gapHour: null,
        createOrder: null,
        statement: null,
        useRule: null,
        startTime: null,
        endTime: null
      },

    }
  },

  /* watch(date){
     this.$set(this.form, "date", response.data.openDaysList)
 },*/

  created() {
    this.getList();
  },
  filterText(val) {
    this;
  },

  methods: {

    // 取消按钮
    cancel() {
      this.reset();
    },
    // 表单重置
    reset() {
      this.form = {};
    },

    getList() {
      gl().then(res => {
        if (res.code == 200) {
          if (null == res.data){
            this.$message.warning("开始初始化")
            addrule().then(res =>{
              if (res.code == 200){
                this.$message.success("初始化成功")
                this.form = res.data;
                this.form.datetime = [
                  this.form.startTime,
                  this.form.endTime
                ]
                this.datetime = this.form.datetime
                this.getList();
              }
            })
          }
          this.form = res.data;
          this.form.datetime = [
            this.form.startTime,
            this.form.endTime
          ]
          this.datetime = this.form.datetime
        }
      })
    },

    /** 提交按钮 */
    submitForm() {
      if (this.form.gapHour > 12) {
        this.$message.error("时间不能大于12小时")
        return false
      }
      this.form.datetime = this.datetime
      this.form.startTime = this.form.datetime[0]
      this.form.endTime = this.form.datetime[1]
      updaterule(this.form).then(res => {
        if (res.code == 200) {
          this.$message.success("修改成功")
          this.getList();
        }
      })
    },
  }

}
</script>

<style scoped>

</style>

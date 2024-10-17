<template>
  <div class="app-container">

    <div v-show="openView === 0">
      <div style="display: flex">
        <el-form ref="form" :model="form" :rules="rules" label-width="120px">
          <el-col :span="20">
            <el-row>
              <el-col :span="10" :offset="3">
                <el-form-item label="抄表频率  1月/次" style="width: 280px"></el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="5" :offset="1">
                <el-form-item label="水表设置：" prop="waterDay" style="width: 500px">
                  <el-select v-model="form.waterDay" placeholder="请选择" style="width: 200px">
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>

            </el-row>
            <el-row>
              <el-col :span="5" :offset="1">
                <el-form-item label="电表设置：" prop="eleDay" style="width: 500px">
                  <el-select v-model="form.eleDay" placeholder="请选择" style="width: 200px">
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>

            </el-row>
            <el-row>
              <el-col :span="5" :offset="1" >
                <el-form-item label="燃气表设置：" prop="gasDay" style="width: 500px">
                  <el-select v-model="form.gasDay" placeholder="请选择" style="width: 200px">
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row >
              <el-col :span="20" :offset="1" >
                <el-form-item label="是否每月推送至用户手机：" prop="meterPush" style="width: 500px">
                  <el-radio v-model="form.meterPush" label="1">是</el-radio>
                  <el-radio v-model="form.meterPush" label="0">否</el-radio>
                </el-form-item>
              </el-col>
            </el-row>
            <el-row>
              <el-col :span="5" :offset="1" >
                <el-form-item label="推送时间：" prop="pushDay" style="width: 500px" v-if="this.form.meterPush == 1">
                  <el-select v-model="form.pushDay" placeholder="请选择" style="width: 200px">
                    <el-option
                      v-for="item in options"
                      :key="item.value"
                      :label="item.label"
                      :value="item.value"
                    >
                    </el-option>
                  </el-select>
                </el-form-item>
              </el-col>
            </el-row>
          </el-col>
        </el-form>
      </div>
      <h4 class="form-header"></h4>
      <div slot="footer" class="dialog-footer">
        <el-row>
          <el-col :offset="4" :span="2">
            <el-button type="primary" @click="submitForm">确 定</el-button>
          </el-col>
        </el-row>
      </div>
    </div>

  </div>
</template>

<script>

import {getRuleInfo, settingRule} from "@/api/estate/manage/meter/rule";

export default {
  name: "setting",
  data() {
    return {
      options: [],
      openView :false,
      // 表单参数
      form: {},
      // 表单校验
      rules: {
        waterDay: [
          { required: true, message: "水表抄表日期不能为空", trigger: "blur" }
        ],
        eleDay: [
          { required: true, message: "电表抄表日期不能为空", trigger: "blur" }
        ],
        gasDay: [
          { required: true, message: "燃气表抄表日期不能为空", trigger: "blur" }
        ],
        meterPush: [
          { required: true, message: "能耗推送不能为空", trigger: "blur" }
        ],
        pushDay: [
          { required: true, message: "能耗推送日期不能为空", trigger: "blur" }
        ],

      },
    };
  },
  created() {
    this.getDay();
    this.getList();
  },
  methods: {

    getDay(){
      var arr = [];
      for (let i = 1; i <= 28; i++) {
        var obj = new Object();
        obj.value = i;
        obj.label = "每月" + i + "号";
        arr.push(obj);
      }
      this.options = arr;
    },

    getList() {
      this.openView = 0;
      getRuleInfo().then( response => {
        this.form = response.data;
        }
      );
    },

    /** 提交按钮 */
    submitForm() {
      this.$refs["form"].validate(valid => {
        if (valid){
          settingRule(this.form).then(response => {
              if (response.code == 200){
                this.$modal.msgSuccess("设置成功");
              }
            }
          );
        }
      });
    },
  }
}
</script>
<style scoped>
</style>

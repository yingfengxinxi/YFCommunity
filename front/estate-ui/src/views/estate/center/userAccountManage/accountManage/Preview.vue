<template>
    <div>
      <el-row style="margin-top: 25px;margin-left: 20px">
          <el-col>
            <span>用户基本信息</span>
            <el-button plain style="margin-left: 80%" @click="$emit('close')">返回</el-button>
            <el-divider/>
            <el-descriptions title="" style="margin-left: 8%" :column="4">
              
              <el-descriptions-item label="姓名"><span>{{info.accountName}}</span></el-descriptions-item>
              <el-descriptions-item label="手机号"><span>{{info.loginName}}</span></el-descriptions-item>
              <el-descriptions-item label="证件类型"><span>{{getCartTypeFormat(info.cardType)}}</span></el-descriptions-item>
              <el-descriptions-item label="证件号码"><span>{{info.cardNo}}</span></el-descriptions-item>
              <el-descriptions-item label="性别"><span>
                {{info.sex=='0'?'男':info.sex=='1'?'女':''}}
              </span></el-descriptions-item>
              <el-descriptions-item label="用户角色"><span>
                  {{
                  info.accountType=='0'?'业主':info.accountType=='1'?'租客':info.accountType=='2'?'访客':''
                }}
              </span></el-descriptions-item>
            </el-descriptions>
          </el-col>
      </el-row>
      <el-row style="margin-top: 25px;margin-left: 20px">
        <el-col>
          <span>账号信息</span>
          <el-divider/>
          <el-col :span="7" style="margin-left: 4%">
            <el-descriptions title="" style="margin-left: 10%">
              <el-descriptions-item label="头像">
                <image-preview :src="info.avatar" :width="100" :height="100" />
              </el-descriptions-item>
            </el-descriptions>
          </el-col>
          <el-col :span="15" style="margin-left: 1%" >
            <el-row>
              <el-descriptions title="" :column="4">
                <el-descriptions-item label="昵称"><span>{{info.accountNick}}</span></el-descriptions-item>
                <el-descriptions-item label="ID"><span>{{info.accountId}}</span></el-descriptions-item>
                <el-descriptions-item label="账号"><span>{{info.loginName}}</span></el-descriptions-item>
                <el-descriptions-item label="密码">
                  <span>**********</span>
                  <el-button
                    size="mini"
                    type="text"
                    icon="el-icon-refresh"
                    style="color: red;margin-left: 20px"
                    @click="dialogVisible = true"
                    v-hasPermi="['center:accountManage:updatePwd']"
                  >重置密码</el-button>
                </el-descriptions-item>
              </el-descriptions>
            </el-row>
          <el-row style="margin-top: 5%">
            <el-descriptions>
              <el-descriptions-item label="注册时间"><span>
                {{ parseTime(info.createTime) }}
              </span></el-descriptions-item>
              <el-descriptions-item label="绑定微信"><span>
                {{info.wechatId}}
              </span></el-descriptions-item>
              <el-descriptions-item label="数据来源"><span>
                {{info.dataSource=='0'?'物业注册':info.dataSource=='1'?'手机注册':''}}
              </span></el-descriptions-item>
            </el-descriptions>
          </el-row>
          </el-col>
        </el-col>
      </el-row>
      <el-dialog
        title="密码重置"
        :visible.sync="dialogVisible"
        width="25%"
    >
        <el-form ref="ruleForm" :rules="rules"  :model="ruleForm" class="demo-ruleForm">
<!--
          <el-form-item label="原密码" :label-width="formLabelWidth" prop="oldPwd">
            <el-input type="password" v-model="ruleForm.oldPwd" autocomplete="off"   maxlength="200"></el-input>
          </el-form-item>
-->
          <el-form-item label="新密码" :label-width="formLabelWidth" prop="newPwd">
            <el-input type="password" v-model="ruleForm.newPwd" autocomplete="off" maxlength="200" show-password></el-input>
          </el-form-item>
        </el-form>
        <span slot="footer" class="dialog-footer">
        <el-button @click="resetForm" style="margin-right: 20px">关 闭</el-button>
        <el-button type="primary" slot="reference" @click="updatePwd">修 改</el-button>
  </span>
      </el-dialog>
    </div>
</template>

<script>
import {updatePwd} from '@/api/estate/center/userAccountManage/accountManage'
  export default {
    name: "Preview",
  props: ['info'],
  dicts: ['sys_certificate'],
    data(){
      return{
        dialogVisible:false,
        formLabelWidth: '100px',
        ruleForm:{
          oldPwd:'',
          newPwd:'',
        },
        rules:{
          oldPwd:[
            {
              required: true, message: '请输入原密码', trigger: 'blur',

            }
          ],
          newPwd:[
            {
              required: true, message: '请输入新密码', trigger: 'blur',

            },
            {
              min: 6,
              max: 12,
              message: "长度在 6 到 12 个字符",
              trigger: "blur",
            },
            {
              required: true,
              pattern: /^[A-Za-z0-9]+$/,
              message: "密码的格式只能是字母、数字",
              trigger: "blur",
            },
          ],
        }
      }
    },
  methods: {
    getCartTypeFormat(cardType){
      return this.selectDictLabel(this.dict.type.sys_certificate,cardType);
    },
      //密码修改
        updatePwd(){
          this.$refs['ruleForm'].validate((valid) => {
            if (valid) {
              this.$confirm('确定要修改该账号密码?', "警告", {
                confirmButtonText: "确定",
                cancelButtonText: "取消",
                type: "warning"
              }).then(()=>{
                this.ruleForm['id']=this.info.accountId+''
                return updatePwd(this.ruleForm).then(e=>{

                  if (e.code==200){
                    this.$message({
                      message: '修改成功!!',
                      type: 'success'
                    });
                  }else {
                    this.$message({
                      message: '修改失败!!',
                      type: 'error'
                    });
                  }

                })
              }).then(() => {
                this.dialogVisible=false
                this.resetForm("ruleForm")
              })
            }
          });

        },
      resetForm() {
        this.$refs['ruleForm'].resetFields();
        this.dialogVisible=false
      },
    }
  }
</script>

<style scoped>
    span{
        font-weight: bold;
      color: black;
    }
</style>

<template>
  <div class="login" id="login">
    <el-row>
      <el-col :offset="3" :span="18" class="form-bg" id="form-bg">
        <el-col :span="12" class="inset-bg"></el-col>
        <el-col :span="9" :offset="1">
          <el-form ref="loginForm" :model="loginForm" :rules="loginRules" class="login-form">
            <h3 class="title">智慧物管平台</h3>
            <el-form-item prop="username">
              <el-input
                v-model="loginForm.username"
                type="text"
                auto-complete="off"
                placeholder="账号"
              >
                <svg-icon slot="prefix" icon-class="user" class="el-input__icon input-icon" />
              </el-input>
            </el-form-item>
            <el-form-item prop="password">
              <el-input
                v-model="loginForm.password"
                :type="pwdType"
                auto-complete="off"
                placeholder="密码"
                @keyup.enter.native="handleLogin"
              >
                <svg-icon slot="prefix" icon-class="password" class="el-input__icon input-icon" />
                <svg-icon slot="suffix" icon-class="eye" class="el-input__icon input-icon" style="cursor: pointer;"
                          v-show="eyeClose" @click="changePasswordType"/>
                <svg-icon slot="suffix" icon-class="eye-open" class="el-input__icon input-icon" style="cursor: pointer;"
                          v-show="!eyeClose" @click="changePasswordType"/>
              </el-input>
            </el-form-item>
            <el-form-item prop="code" v-if="captchaEnabled">
              <el-input
                v-model="loginForm.code"
                auto-complete="off"
                placeholder="验证码"
                style="width: 63%"
                @keyup.enter.native="handleLogin"
              >
                <svg-icon slot="prefix" icon-class="validCode" class="el-input__icon input-icon" />
              </el-input>
              <div class="login-code">
                <img :src="codeUrl" @click="getCode" class="login-code-img"/>
              </div>
            </el-form-item>
            <el-checkbox v-model="loginForm.rememberMe" style="margin:0px 0px 25px 0px;">记住密码</el-checkbox>
            <el-form-item style="width:100%;">
              <el-button
                :loading="loading"
                size="medium"
                type="primary"
                style="width:100%;"
                @click.native.prevent="handleLogin"
              >
                <span v-if="!loading">登 录</span>
                <span v-else>登 录 中...</span>
              </el-button>
              <div style="float: right;" v-if="register">
                <router-link class="link-type" :to="'/register'">立即注册</router-link>
              </div>
            </el-form-item>
          </el-form>
        </el-col>
      </el-col>
    </el-row>
    <!--  底部  -->
    <div class="el-login-footer">
      <span>Copyright © 2022-now All Rights Reserved.</span>
    </div>
  </div>
</template>

<script>
import { getCodeImg } from "@/api/login";
import Cookies from "js-cookie";
import { encrypt, decrypt } from '@/utils/jsencrypt'

export default {
  name: "Login",
  data() {
    return {
      eyeClose: true,
      pwdType: "password",
      codeUrl: "",
      loginForm: {
        username: "",
        password: "",
        userType: "ESTATE_USER",
        rememberMe: false,
        code: "",
        uuid: ""
      },
      loginRules: {
        username: [
          { required: true, trigger: "blur", message: "请输入您的账号" }
        ],
        password: [
          { required: true, trigger: "blur", message: "请输入您的密码" }
        ],
        code: [{ required: true, trigger: "change", message: "请输入验证码" }]
      },
      loading: false,
      // 验证码开关
      captchaEnabled: false,
      // 注册开关
      register: false,
      redirect: undefined
    };
  },
  watch: {
    $route: {
      handler: function(route) {
        this.redirect = route.query && route.query.redirect;
      },
      immediate: true
    }
  },
  mounted() {
    let rate = 750 / 937;
    //窗口监听事件
    window.addEventListener('resize', function () {
      let loginEle = document.getElementById("login");
      let formBgEle = document.getElementById("form-bg");
      formBgEle.style.height = loginEle.offsetHeight * rate +'px'
    });
  },
  created() {
    //this.getCode();
    this.getCookie();
  },
  methods: {
    changePasswordType(){
      this.eyeClose = !this.eyeClose
      this.pwdType = this.eyeClose ? "password" : "";
    },
    getCode() {
      getCodeImg().then(res => {
        this.captchaEnabled = res.captchaEnabled === undefined ? true : res.captchaEnabled;
        if (this.captchaEnabled) {
          this.codeUrl = "data:image/gif;base64," + res.img;
          this.loginForm.uuid = res.uuid;
        }
      });
    },
    getCookie() {
      const username = Cookies.get("estateUsername");
      const password = Cookies.get("estatePassword");
      const userType = Cookies.get("estateUserType");
      const rememberMe = Cookies.get('estateRememberMe')
      this.loginForm = {
        username: username === undefined ? this.loginForm.username : username,
        password: password === undefined ? this.loginForm.password : decrypt(password),
        userType: userType === undefined ? this.loginForm.userType : userType,
        rememberMe: rememberMe === undefined ? false : Boolean(rememberMe)
      };
    },
    handleLogin() {
      this.$refs.loginForm.validate(valid => {
        if (valid) {
          this.loading = true;
          if (this.loginForm.rememberMe) {
            Cookies.set("estateUsername", this.loginForm.username, { expires: 30 });
            Cookies.set("estatePassword", encrypt(this.loginForm.password), { expires: 30 });
            Cookies.set("estateUserType", this.loginForm.userType, { expires: 30 });
            Cookies.set('estateRememberMe', this.loginForm.rememberMe, { expires: 30 });
          } else {
            Cookies.remove("estateUsername");
            Cookies.remove("estatePassword");
            Cookies.remove("estateUserType");
            Cookies.remove('estateRememberMe');
          }
          console.log("-------------------------->>")
          this.$store.dispatch("EstateLogin", this.loginForm).then(() => {
            this.$router.push({ path: this.redirect || "/" }).catch(()=>{});
          }).catch(() => {
            this.loading = false;
            if (this.captchaEnabled) {
              this.getCode();
            }
          });
        }
      });
    }
  }
};
</script>

<style rel="stylesheet/scss" lang="scss">
.login {
  /*display: flex;
  justify-content: center;
  align-items: center;*/
  height: 100%;
  background-image: url("../assets/images/login-background.jpg");
  background-size: 100% 100%;
  background-repeat: no-repeat;
}
.title {
  margin: 0 auto 30px auto;
  text-align: center;
  color: #707070;
  letter-spacing: 3px;
}

.form-bg {
  display: flex;
  justify-content: center;
  align-items: center;
  margin-top: 5%;
  border-radius: 6px;
  background: #ffffff;
  height: 750px;
}

.inset-bg{
  height: 65%;
  background-image: url("../assets/images/inset-bgi.png");
  background-size: 100% 100%;
  background-repeat: no-repeat;
}

.login-form {
  width: 80%;
  padding: 25px 25px 5px 25px;
  .el-input {
    height: 38px;
    input {
      height: 38px;
    }
  }
  .input-icon {
    height: 39px;
    width: 14px;
    margin-left: 2px;
  }
}
.login-tip {
  font-size: 13px;
  text-align: center;
  color: #bfbfbf;
}
.login-code {
  width: 33%;
  height: 38px;
  float: right;
  img {
    cursor: pointer;
    vertical-align: middle;
  }
}
.el-login-footer {
  height: 40px;
  line-height: 40px;
  position: fixed;
  bottom: 0;
  width: 100%;
  text-align: center;
  color: #fff;
  font-family: Arial;
  font-size: 12px;
  letter-spacing: 1px;
}
.login-code-img {
  height: 38px;
}
</style>

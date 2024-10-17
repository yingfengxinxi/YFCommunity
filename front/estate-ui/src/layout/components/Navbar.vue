<template>
  <div class="navbar">
    <hamburger id="hamburger-container" :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />

    <breadcrumb id="breadcrumb-container" class="breadcrumb-container" v-if="!topNav"/>
    <top-nav id="topmenu-container" class="topmenu-container" v-if="topNav"/>

    <div class="right-menu">
      <el-row style="height: 50px;">
        <el-col :span="20">
          <template v-if="device!=='mobile'">
            <el-row style="height: 50px;">
              <el-col :span="13" :offset="4" style="height: 50px;">
                <router-link class="right-menu-item hover-effect" tag="a" target="_blank" to="/screen">数字城建-驾驶舱</router-link>
              </el-col>
              <el-col :span="4" style="height: 50px;">
                <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
                  <el-badge :value="noticeList.length" :max="99" class="notice-badge" type="success">
                    <svg-icon icon-class="red-bell" class="notice-bell"/>
                  </el-badge>
                  <el-dropdown-menu slot="dropdown" class="notice-dropdown-menu">
                    <el-row>
                      <label class="el-form-item__label" style="line-height: 30px;margin-left: 10px;">
                        <svg-icon icon-class="vertical"/>平台公告
                      </label>
                    </el-row>
                    <h4 class="form-header" style="margin: 0 10px 10px 10px;"></h4>
                    <el-row>
                      <ul class="notice-list">
                        <li v-for="(notice, index) in noticeList" style="margin-bottom: 15px;cursor:pointer;" @click="linkToNotice(notice)">
                          <el-row style="font-size: 14px;" >
                            <el-col :span="1" :offset="1" style="margin-top: 3px;">
                              <svg-icon icon-class="point-yellow"/>
                            </el-col>
                            <el-col :span="13" style="white-space: nowrap;overflow: hidden;text-overflow: ellipsis;font-size: 14px;font-weight: bold;">
                              <span>{{ notice.noticeTitle }}</span>
                            </el-col>
                            <el-col :span="8">
                              <span style="color: #ABA8A8">{{ notice.createTime }}</span>
                            </el-col>
                          </el-row>
                        </li>
                      </ul>
                    </el-row>

                  </el-dropdown-menu>
                </el-dropdown>
              </el-col>
              <el-col :span="3" style="height: 50px;">
                <screenfull id="screenfull" class="right-menu-item hover-effect"/>
              </el-col>
            </el-row>
          </template>
        </el-col>
        <el-col :span="4" style="height: 50px;">
          <el-dropdown class="avatar-container right-menu-item hover-effect" trigger="click">
            <div class="avatar-wrapper"><img :src="avatar" class="user-avatar"><i class="el-icon-caret-bottom"/></div>
            <el-dropdown-menu slot="dropdown">
              <router-link to="/user/profile">
                <el-dropdown-item>个人中心</el-dropdown-item>
              </router-link>
              <!--          <el-dropdown-item @click.native="setting = true"><span>布局设置</span></el-dropdown-item>-->
              <el-dropdown-item divided @click.native="logout"><span>退出登录</span></el-dropdown-item>
            </el-dropdown-menu>
          </el-dropdown>
        </el-col>
      </el-row>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import TopNav from '@/components/TopNav'
import Hamburger from '@/components/Hamburger'
import Screenfull from '@/components/Screenfull'
import SizeSelect from '@/components/SizeSelect'
import Search from '@/components/HeaderSearch';
import { listNoticeTitle } from "@/api/screen/screen";

export default {
  components: {
    Breadcrumb,
    TopNav,
    Hamburger,
    Screenfull,
    SizeSelect,
    Search,
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar',
      'device'
    ]),
    setting: {
      get() {
        return this.$store.state.settings.showSettings
      },
      set(val) {
        this.$store.dispatch('settings/changeSetting', {
          key: 'showSettings',
          value: val
        })
      }
    },
    topNav: {
      get() {
        return this.$store.state.settings.topNav
      }
    }
  },
  data(){
    return {
      noticeList:[],
    };
  },
  created() {
    this.viewNoticeList();
  },
  methods: {
  
    viewNoticeList(){
      listNoticeTitle().then(response=>{
        this.noticeList = response.data;
      });
    },
    linkToNotice(row) {
      if (row.type == '0') {
        this.$router.push('/information/communityNotice');
      }
      if (row.type == '1') {
        this.$router.push('/information/send/sendSmsRecord');
      }
      if (row.type == '2') {
        this.$router.push('/moneyManage/cashManage/cashRecode/estateMoney');
      }
    },
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      this.$confirm('确定注销并退出系统吗？', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        this.$store.dispatch('EstateLogOut').then(() => {
          location.href = '/index';
        })
      }).catch(() => {});
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .topmenu-container {
    position: absolute;
    left: 50px;
  }

  .errLog-container {
    display: inline-block;
    vertical-align: top;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 10px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
    .notice-badge{
      margin-top: 12px;
      font-size: 26px;
    }
    .notice-bell{
      margin-bottom: 15px;
    }
  }
}
.notice-dropdown-menu{
  width: 400px;
}

.notice-dropdown-menu:hover{
  background-color: #FFFFFF !important;
}

.notice-list{
  padding: 0;
  margin: 0;
  max-height: 250px;
  list-style-type: none;
  color: #676a6c;
  overflow-y:scroll;
}

.notice-list::-webkit-scrollbar{
  display: none;
}
</style>

/*
 * @Author: 自由命名
 * @Date: 2023-11-29 14:06:45
 * @LastEditors: 自由命名
 * @LastEditTime: 2024-04-19 13:56:28
 * @FilePath: \admin-ui\src\store\modules\app.js
 * @filePurpose: 
 */
import Cookies from 'js-cookie'

const state = {
  sidebar: {
    opened: Cookies.get('adminSidebarStatus') ? !!+Cookies.get('adminSidebarStatus') : true,
    withoutAnimation: false,
    hide: false
  },
  device: 'desktop',
  size: Cookies.get('adminSize') || 'medium'
}

const mutations = {
  TOGGLE_SIDEBAR: state => {
    if (state.sidebar.hide) {
      return false;
    }
    state.sidebar.opened = !state.sidebar.opened
    console.log('状态', state.sidebar.opened)
    state.sidebar.withoutAnimation = false
    if (state.sidebar.opened) {
      Cookies.set('adminSidebarStatus', 1)
    } else {
      Cookies.set('adminSidebarStatus', 0)
    }
  },
  CLOSE_SIDEBAR: (state, withoutAnimation) => {
    Cookies.set('adminSidebarStatus', 0)
    state.sidebar.opened = false
    state.sidebar.withoutAnimation = withoutAnimation
  },
  TOGGLE_DEVICE: (state, device) => {
    state.device = device
  },
  SET_SIZE: (state, size) => {
    state.size = size
    Cookies.set('adminSize', size)
  },
  SET_SIDEBAR_HIDE: (state, status) => {
    state.sidebar.hide = status
  }
}

const actions = {
  toggleSideBar({
    commit
  }) {
    commit('TOGGLE_SIDEBAR')
  },
  closeSideBar({
    commit
  }, {
    withoutAnimation
  }) {
    commit('CLOSE_SIDEBAR', withoutAnimation)
  },
  toggleDevice({
    commit
  }, device) {
    commit('TOGGLE_DEVICE', device)
  },
  setSize({
    commit
  }, size) {
    commit('SET_SIZE', size)
  },
  toggleSideBarHide({
    commit
  }, status) {
    commit('SET_SIDEBAR_HIDE', status)
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}

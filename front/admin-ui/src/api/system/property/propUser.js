import request from '@/utils/request'
import {praseStrEmpty} from "@/utils/ruoyi";

// 查询用户列表
export function listPropUser(query) {
  return request({
    url: '/system/prop_user/list',
    method: 'get',
    params: query
  })
}

// 查询用户详细
export function getPropUser(userId) {
  return request({
    url: '/system/prop_user/' + praseStrEmpty(userId),
    method: 'get'
  })
}

// 新增用户
export function addPropUser(data) {
  return request({
    url: '/system/prop_user',
    method: 'post',
    data: data
  })
}

// 修改用户
export function updatePropUser(data) {
  return request({
    url: '/system/prop_user',
    method: 'put',
    data: data
  })
}

// 删除用户
export function delPropUser(userId) {
  return request({
    url: '/system/prop_user/' + userId,
    method: 'delete'
  })
}

// 用户密码重置
export function resetUserPwd(userId, password) {
  const data = {
    userId,
    password
  }
  return request({
    url: '/system/prop_user/resetPwd',
    method: 'put',
    data: data
  })
}

// 用户状态修改
export function changeUserStatus(userId, status) {
  const data = {
    userId,
    status
  }
  return request({
    url: '/system/prop_user/changeStatus',
    method: 'put',
    data: data
  })
}

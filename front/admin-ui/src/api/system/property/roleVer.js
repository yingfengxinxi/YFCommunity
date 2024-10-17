import request from '@/utils/request'

// 查询列表
export function listPropRole(query) {
  return request({
    url: '/system/role_ver/list',
    method: 'get',
    params: query
  })
}

//查询服务版本
export function listRoleVer() {
  return request({
    url: '/system/role_ver/ver_list',
    method: 'get'
  })
}

// 新增角色
export function addPropRole(data) {
  return request({
    url: '/system/role_ver',
    method: 'post',
    data: data
  })
}

// 角色状态修改
export function changeRoleStatus(roleId, status) {
  const data = {
    roleId,
    status
  }
  return request({
    url: '/system/role_ver/changeStatus',
    method: 'put',
    data: data
  })
}

// 查询角色详细
export function getPropRole(roleId) {
  return request({
    url: '/system/role_ver/' + roleId,
    method: 'get'
  })
}

// 修改角色
export function updatePropRole(data) {
  return request({
    url: '/system/role_ver',
    method: 'put',
    data: data
  })
}

// 删除角色
export function delPropRole(roleId) {
  return request({
    url: '/system/role_ver/' + roleId,
    method: 'delete'
  })
}

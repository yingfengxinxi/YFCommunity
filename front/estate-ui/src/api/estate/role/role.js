import request from '@/utils/request'

// 查询角色列表
export function list(query) {
  return request({
    url: '/estate/estateRole/list',
    method: 'post',
    data: query
  })
}

// 查询角色详细
export function getById(estateRoleId) {
  return request({
    url: '/estate/estateRole/' + estateRoleId,
    method: 'get'
  })
}

// 新增角色
export function add(data) {
  return request({
    url: '/estate/estateRole/insert',
    method: 'post',
    data: data
  })
}

// 修改角色
export function update(data) {
  return request({
    url: '/estate/estateRole/update',
    method: 'put',
    data: data
  })
}


// 删除角色
export function deleById(estateRoleId) {
  return request({
    url: '/estate/estateRole/delete?estateRoleId=' + estateRoleId,
    method: 'delete'
  })
}


// 查询已授权菜单
export function listRoleMenu(estateRoleId) {
  return request({
    url: '/estate/estateMobileRoleMenu/list?estateRoleId=' + estateRoleId,
    method: 'get'
  })
}

// 新增已授权菜单
export function insertRoleMenu(data) {
  return request({
    url: '/estate/estateMobileRoleMenu/insert',
    method: 'post',
    data: data
  })
}


// 查询菜单列表
export function listRole() {
  return request({
    url: '/estate/estateRole/listRole',
    method: 'get'
  })
}
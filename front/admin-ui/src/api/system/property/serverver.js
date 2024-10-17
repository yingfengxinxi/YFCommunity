import request from '@/utils/request'

// 查询列表
export function listRoleVersion(query) {
  return request({
    url: '/system/role_version/list',
    method: 'get',
    params: query
  })
}

// 新增
export function addRoleVersion(data) {
  return request({
    url: '/system/role_version',
    method: 'post',
    data: data
  })
}

// 根据角色ID查询菜单下拉树结构
export function roleVerMenuTree(versionId) {
  return request({
    url: '/system/role_version/verMenuTree/' + versionId,
    method: 'get'
  })
}

// 查询详细
export function getRoleVersion(versionId) {
  return request({
    url: '/system/role_version/' + versionId,
    method: 'get'
  })
}

// 修改
export function updateRoleVersion(data) {
  return request({
    url: '/system/role_version',
    method: 'put',
    data: data
  })
}

// 删除
export function delRoleVersion(versionId) {
  return request({
    url: '/system/role_version/' + versionId,
    method: 'delete'
  })
}

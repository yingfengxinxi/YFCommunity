import request from '@/utils/request'

// 查询物业公司信息列表
export function listestate(query) {
  return request({
    url: '/system/estate/list',
    method: 'get',
    params: query
  })
}

// 新增物业公司信息
export function addestate(data) {
  return request({
    url: '/system/estate',
    method: 'post',
    data: data
  })
}

// 查询物业公司详细信息
export function getestate(estateId) {
  return request({
    url: '/system/estate/' + estateId,
    method: 'get'
  })
}

// 查询列表（排除节点）
export function listestateExcludeChild(estateId) {
  return request({
    url: '/system/estate/list/exclude/' + estateId,
    method: 'get'
  })
}

// 修改物业公司
export function updateestate(data) {
  return request({
    url: '/system/estate',
    method: 'put',
    data: data
  })
}

// 删除物业公司
export function delestate(deptId) {
  return request({
    url: '/system/estate/' + deptId,
    method: 'delete'
  })
}

// 查询物业公司子集
export function getChildList(deptId) {
  return request({
    url: '/system/estate/child_list/' + deptId,
    method: 'get'
  })
}

// 查询部门下拉树结构
export function treeSelect(query) {
  return request({
    url: '/system/estate/tree',
    method: 'get',
    params: query
  })
}

// 查询物业下拉树结构
export function treeProSelect(query) {
  return request({
    url: '/system/estate/pro_tree',
    method: 'get',
    params: query
  })
}

export function changeStatus( sysDept){
  const data = {
    sysDept
  }
  return request({
    url: '/system/estate/changeStatus',
    method: 'put',
    data: data
  })
}

import request from '@/utils/request'

// 查询数据列表
export function listArea(query) {
  return request({
    url: '/system/area/list',
    method: 'get',
    params: query
  })
}

// 查询列表（排除节点）
export function listAreaExcludeDistrict() {
  return request({
    url: '/system/area/exclude/district',
    method: 'get'
  })
}

// 新增数据
export function addArea(data) {
  return request({
    url: '/system/area',
    method: 'post',
    data: data
  })
}

// 查询部门详细
export function getArea(areaId) {
  return request({
    url: '/system/area/' + areaId,
    method: 'get'
  })
}

// 查询列表（排除节点）
export function listAreaExcludeChild(areaId) {
  return request({
    url: '/system/area/list/exclude/' + areaId,
    method: 'get'
  })
}

// 修改部门
export function updateArea(data) {
  return request({
    url: '/system/area',
    method: 'put',
    data: data
  })
}

// 删除部门
export function delArea(areaId) {
  return request({
    url: '/system/area/' + areaId,
    method: 'delete'
  })
}

// 查询部门下拉树结构
export function areaTreeSelect() {
  return request({
    url: '/system/area/tree',
    method: 'get'
  })
}

import request from '@/utils/request'

// 查询列表
export function listSensitive(query) {
  return request({
    url: '/system/sensitive/list',
    method: 'get',
    params: query
  })
}

// 新增
export function addSensitive(data) {
  return request({
    url: '/system/sensitive',
    method: 'post',
    data: data
  })
}

// 查询详细
export function getSensitive(sensitiveId) {
  return request({
    url: '/system/sensitive/' + sensitiveId,
    method: 'get'
  })
}

// 修改
export function updateSensitive(data) {
  return request({
    url: '/system/sensitive',
    method: 'put',
    data: data
  })
}

// 删除
export function delSensitive(sensitiveIds) {
  return request({
    url: '/system/sensitive/' + sensitiveIds,
    method: 'delete'
  })
}

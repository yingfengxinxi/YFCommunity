import request from '@/utils/request'

// 查询居家维修类型列表
export function listHomeRepair(query) {
  return request({
    url: '/estate/work/homeRepair/list',
    method: 'get',
    params: query
  })
}

// 查询详情
export function getInfo(repairId) {
  return request({
    url: '/estate/work/homeRepair/' + repairId,
    method: 'get'
  })
}

// 添加类型
export function addHomeRepair(data) {
  return request({
    url: '/estate/work/homeRepair/add',
    method: 'post',
    data: data
  })
}

// 修改类型
export function upHomeRepair(data) {
  return request({
    url: '/estate/work/homeRepair/edit',
    method: 'put',
    data: data
  })
}

// 删除
export function delHomeRepair(repairIds) {
  return request({
    url: '/estate/work/homeRepair/remove/' + repairIds,
    method: 'delete'
  })
}

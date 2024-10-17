import request from '@/utils/request'

// 查询清洁工单列表
export function listCleaningWorkOrder(query) {
  return request({
    url: '/estate/cleaningWorkOrder/list',
    method: 'get',
    params: query
  })
}

// 查询清洁工单详情
export function getCleaningWorkOrderDetail(id) {
  return request({
    url: '/estate/cleaningWorkOrder/' + id,
    method: 'get'
  })
}

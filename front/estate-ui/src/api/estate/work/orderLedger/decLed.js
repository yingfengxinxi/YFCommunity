import request from '@/utils/request'
// 查询装修评定工单列表
export function listDecorateOrder(query) {
  return request({
    url: '/estate/decLed/list',
    method: 'get',
    params: query
  })
}

// 查询装修评定工单详情
export function getDecorateOrderDetail(orderId) {
  return request({
    url: '/estate/decLed/' + orderId,
    method: 'get'
  })
}

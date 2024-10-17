import request from '@/utils/request'

// 查询居家维修工单列表
export function listHomeOrder(query) {
  return request({
    url: '/estate/work/homeOrder/list',
    method: 'get',
    params: query
  })
}

// 查询居家维修类型
export function getHomeRepair(query) {
  return request({
    url: '/estate/work/homeOrder/getHomeRepair',
    method: 'get',
    params: query
  })
}

// 查询居家维修类型
export function getInfo(orderId) {
  return request({
    url: '/estate/work/homeOrder/' + orderId,
    method: 'get'
  })
}

// 分配工单
export function distribution(data) {
  return request({
    url: '/estate/work/homeOrder/distribution',
    method: 'put',
    data: data
  })
}

import request from '@/utils/request'



// 车位费
export function countList(query) {
  return request({
    url: '/estate/estateEarn/list',
    method: 'get',
    params: query
  })
}
// 其他三个费用
export function threeList(query) {
  return request({
    url: '/estate/estateEarn/threeCash',
    method: 'get',
    params: query
  })
}
// 支出
export function buylist(query) {
  return request({
    url: '/estate/estateEarn/buylist',
    method: 'get',
    params: query
  })
}

// 详情页收益数据
export function detile(query) {
  return request({
    url: '/estate/estateEarn/detile',
    method: 'get',
    params: query
  })
}

// 详情页支出数据
export function buydetile(query) {
  return request({
    url: '/estate/estateEarn/buydetile',
    method: 'get',
    params: query
  })
}

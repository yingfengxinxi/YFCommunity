import request from '@/utils/request'

export function query(query) {
  return request({
    url: '/estate/parkorder/query',
    method: 'get',
    params: query
  })
}

export function updateOrder(data) {
  return request({
    url: '/estate/parkorder/updateOrder',
    method: 'post',
    data: data
  })
}
export function remove(data) {
  return request({
    url: '/estate/parkorder/remove',
    method: 'post',
    data: data
  })
}
export function update(data) {
  return request({
    url: '/estate/parkorder/update',
    method: 'post',
    data: data
  })
}

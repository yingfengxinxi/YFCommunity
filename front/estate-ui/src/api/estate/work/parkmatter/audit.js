import request from '@/utils/request'

export function search(query) {

  return request({
    url: '/estate/parkaudit/search',
    method: 'get',
    params: query
  })
}
export function detail(query) {

  return request({
    url: '/estate/parkaudit/detail',
    method: 'get',
    params: query
  })
}
// 通过
export function passState(data) {
  return request({
    url: '/estate/parkaudit/pass',
    method: 'post',
    data: data
  })
}
//驳回
export function rebutState(data) {
  return request({
    url: '/estate/parkaudit/rebut',
    method: 'post',
    data: data
  })
}

import request from '@/utils/request'

// search
export function query(query) {
  return request({
    url: '/estate/disasterledgerbill/query',
    method: 'get',
    params: query
  })
}
// search 灾害类型
export function queryDisaster() {
  return request({
    url: '/estate/disasterledgerbill/queryDisaster',
    method: 'get',
  })
}

// search 协助人员
export function queryAssist(query) {
  return request({
    url: '/estate/disasterledgerbill/queryAssist',
    method: 'get',
    params: query
  })
}

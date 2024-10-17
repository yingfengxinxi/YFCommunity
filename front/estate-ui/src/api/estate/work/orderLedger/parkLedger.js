import request from '@/utils/request'

// search
export function query(query) {
  return request({
    url: '/estate/parkledgerbill/query',
    method: 'get',
    params: query
  })
}
// search 报事类型
export function queryRepair() {
  return request({
    url: '/estate/parkledgerbill/queryRepair',
    method: 'get',
  })
}

// search 协助人员
export function queryAssist(query) {
  return request({
    url: '/estate/parkledgerbill/queryAssist',
    method: 'get',
    params: query
  })
}

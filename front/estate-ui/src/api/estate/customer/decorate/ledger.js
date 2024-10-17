import request from '@/utils/request'

// 查询装修列表
export function listLedger(query) {
  return request({
    url: '/estate/ledger/list',
    method: 'get',
    params: query
  })
}

// 查询装修详情
export function getLedgerDetail(decorateId) {
  return request({
    url: '/estate/ledger/' + decorateId,
    method: 'get'
  })
}

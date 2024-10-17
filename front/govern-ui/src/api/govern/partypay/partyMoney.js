import request from '@/utils/request'

// 查激费列表
export function listPartyMoney(query) {
  return request({
    url: '/govern/partyPay/list',
    method: 'get',
    params: query
  })
}

// 查激费统计
export function queryStatistics(query) {
  return request({
    url: '/govern/partyPay/statistics',
    method: 'get',
    params: query
  })
}

// 查党组织
export function partyList(communityId) {
  return request({
    url: '/govern/partyPay/partyList/' + communityId,
    method: 'get'
  })
}

// 查党员
export function queryMember(partyId) {
  return request({
    url: '/govern/partyPay/memberList/' + partyId,
    method: 'get'
  })
}

// 新增
export function addPartyMoney(data) {
  return request({
    url: '/govern/partyPay/add',
    method: 'post',
    data: data
  })
}

// 归集
export function updatePayStatus(logIds) {
  return request({
    url: '/govern/partyPay/' + logIds,
    method: 'put'
  })
}


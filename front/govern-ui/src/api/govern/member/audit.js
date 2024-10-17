import request from '@/utils/request'

// 查询入党审核
export function auditList(query) {
  return request({
    url: '/govern/audit/list',
    method: 'get',
    params: query
  })
}

// 审核详细
export function getAudit(memberId) {
  return request({
    url: '/govern/audit/' + memberId,
    method: 'get'
  })
}

// 审核通过
export function checkOk(memberId) {
  return request({
    url: '/govern/audit/through/' + memberId,
    method: 'put',
  })
}

// 驳回审核
export function checkNo(data) {
  return request({
    url: '/govern/audit/rejected',
    method: 'put',
    data: data
  })
}

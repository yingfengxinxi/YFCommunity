import request from '@/utils/request'

// 查询参数列表
export function listConfig(query) {
  return request({
    url: '/estate/tenant/list',
    method: 'get',
    params: query
  })
}

// 下拉列表
export function listdict() {
  return request({
    url: '/estate/community/current',
    method: 'get'
  })
}

// 删除参数配置
export function delConfig(tenantIds) {
  return request({
    url: '/estate/tenant/'+tenantIds,
    method: 'delete'
  })
}


// 业主审核详细信息
export function getConfig(tenantId) {
  return request({
    url: '/estate/tenant/' + tenantId,
    method: 'get'
  })
}


//审核通过和驳回
export function passAndRepulse(data) {
  return request({
    url: '/estate/tenant/audit',
    method: 'put',
    data:data
  })
}

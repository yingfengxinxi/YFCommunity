import request from '@/utils/request'

// 查询参数列表
export function listConfig(query) {
  return request({
    url: '/estate/owner/list',
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
export function delConfig(ownerIds) {
  return request({
    url: '/estate/owner/' + ownerIds,
    method: 'delete'
  })
}


// 业主审核详细信息
export function getConfig(ownerId, roomId) {
  return request({
    url: '/estate/owner/' + ownerId + "/" + roomId,
    method: 'get'
  })
}


//审核通过和驳回
export function passAndRepulse(data) {
  return request({
    url: '/estate/owner/audit',
    method: 'put',
    data: data
  })
}

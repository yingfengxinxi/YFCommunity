import request from '@/utils/request'

// 查询巡查列表
export function listPatrol(query) {
  return request({
    url: '/estate/patrol/list',
    method: 'get',
    params: query
  })
}

// 查询巡查详情
export function getPatrolDetail(id) {
  return request({
    url: '/estate/patrol/' + id,
    method: 'get'
  })
}

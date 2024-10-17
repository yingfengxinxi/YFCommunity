import request from '@/utils/request'

// 查询巡查列表
export function listInspection(query) {
  return request({
    url: '/estate/inspection/list',
    method: 'get',
    params: query
  })
}

// 查询巡查详情
export function getInspectionDetail(id) {
  return request({
    url: '/estate/inspection/' + id,
    method: 'get'
  })
}

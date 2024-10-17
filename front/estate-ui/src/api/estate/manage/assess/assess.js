import request from '@/utils/request'

// 查询考核记录列表
export function listassessRecordVo(query) {
  return request({
    url: '/estate/assess/list',
    method: 'get',
    params: query
  })
}

// 查询考核记录详细
export function getassessRecordDetail(assessId) {
  return request({
    url: '/estate/assess/detail/' + assessId,
    method: 'get'
  })
}

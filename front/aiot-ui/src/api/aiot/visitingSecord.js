import request from '@/utils/request'

// 查询列表数据
export function selectVisitingRecordList(query) {
  return request({
    url: '/ai_iot/visitingRecord/list',
    method: 'get',
    params: query
  })
}


// 根据id查询数据
export function selectVisitingRecordById(visitingRecordId) {
  return request({
    url: '/ai_iot/visitingRecord/get/' + visitingRecordId,
    method: 'get'
  })
}
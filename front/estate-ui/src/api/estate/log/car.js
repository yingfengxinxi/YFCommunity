import request from '@/utils/request'

// 查询列表数据
export function selectCrossRecordList(query) {
  return request({
    url: '/estate/crossRecord/list',
    method: 'get',
    params: query
  })
}


// 根据id查询数据
export function selectcrossRecordById(crossRecordId) {
  return request({
    url: '/estate/crossRecord/get/' + crossRecordId,
    method: 'get'
  })
}
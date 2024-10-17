import request from '@/utils/request'

// 查询列表数据
export function selectDoorEventList(query) {
  return request({
    url: '/ai_iot/doorEvent/list',
    method: 'get',
    params: query
  })
}


// 根据id查询数据
export function selectDoorEventById(doorEventId) {
  return request({
    url: '/ai_iot/doorEvent/get/' + doorEventId,
    method: 'get'
  })
}
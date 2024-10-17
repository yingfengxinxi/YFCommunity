import request from '@/utils/request'

// 查询列表数据
export function selectDoorEventList(query) {
  return request({
    url: '/estate/doorEvent/list',
    method: 'get',
    params: query
  })
}


// 根据id查询数据
export function selectDoorEventById(doorEventId) {
  return request({
    url: '/estate/doorEvent/get/' + doorEventId,
    method: 'get'
  })
}

// 根据id查询数据
export function getAllDoorList() {
  return request({
    url: '/estate/doorEvent/getAllDoorList',
    method: 'get'
  })
}
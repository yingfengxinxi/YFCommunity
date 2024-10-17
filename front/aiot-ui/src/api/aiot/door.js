import request from '@/utils/request'

// 查询列表数据
export function selectDoorList(query) {
  return request({
    url: '/ai_iot/door/list',
    method: 'get',
    params: query
  })
}

// 设备同步
export function syncDoorData(communityId) {
  return request({
    url: '/ai_iot/door/sync_data/' + communityId,
    method: 'post'
  })
}

// 更新设备状态
export function checkOnline(data) {
  return request({
    url: '/ai_iot/door/online',
    method: 'post',
    data: data
  })
}

// 根据id查询数据
export function selectDoorById(doorId) {
  return request({
    url: '/ai_iot/door/'+doorId,
    method: 'get'
  })
}

// 更新门禁点
export function updateDoor(data) {
  return request({
    url: '/ai_iot/door',
    method: 'put',
    data: data
  })
}

export function selectAllDoorList(communityId) {
  return request({
    url: '/ai_iot/door/all/'+communityId,
    method: 'get'
  })
}

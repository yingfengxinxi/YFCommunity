import request from '@/utils/request'

// 查询列表数据
export function selectParkList(query) {
  return request({
    url: '/ai_iot/park/list',
    method: 'get',
    params: query
  })
}

// 设备同步
export function syncParkData(communityId) {
  return request({
    url: '/ai_iot/park/sync_data/' + communityId,
    method: 'post'
  })
}

// 根据id查询数据
export function selectParkById(parkId) {
  return request({
    url: '/ai_iot/park/'+parkId,
    method: 'get'
  })
}

// 更新停车场信息
export function updatePark(data) {
  return request({
    url: '/ai_iot/park',
    method: 'put',
    data: data
  })
}

// 订阅停车场出入事件
export function subscribeEvent(communityId) {
  return request({
    url: '/ai_iot/park/subscribe/' + communityId,
    method: 'post'
  })
}

// 停车场出入事件取消订阅
export function unsubscribeLock(communityId) {
  return request({
    url: '/ai_iot/park/unsubscribe/' + communityId,
    method: 'post'
  })
}

// 批量设置停车场支付二维码
export function batchUpdatePayQr(ids) {
  return request({
    url: '/ai_iot/park/qr',
    method: 'put',
    data: {ids:ids}
  })
}

// 查询所有停车场数据
export function selectParkAll(communityId) {
  return request({
    url: '/ai_iot/park/all_park/' + communityId,
    method: 'get'
  })
}

// 查询所有停车场出入口数据
export function selectEntranceAll(parkIndex) {
  return request({
    url: '/ai_iot/park/all_entrance/' + parkIndex,
    method: 'get'
  })
}

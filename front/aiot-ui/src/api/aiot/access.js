import request from '@/utils/request'

// 查询列表数据
export function selectAccessList(query) {
  return request({
    url: '/ai_iot/access/list',
    method: 'get',
    params: query
  })
}

// 设备同步
export function syncAccessData(communityId) {
  return request({
    url: '/ai_iot/access/sync_data/'+ communityId,
    method: 'post'
  })
}

// 更新设备状态
export function checkOnline(data) {
  return request({
    url: '/ai_iot/access/online',
    method: 'post',
    data: data
  })
}

// 根据id查询数据
export function selectAccessById(accessId) {
  return request({
    url: '/ai_iot/access/'+accessId,
    method: 'get'
  })
}

// 查询小区下未绑定门禁的门禁点
export function selectUnbindDoors(query) {
  return request({
    url: '/ai_iot/access/door',
    method: 'get',
    params: query
  })
}

// 更新门禁
export function updateAccess(data) {
  return request({
    url: '/ai_iot/access',
    method: 'put',
    data: data
  })
}

// 批量更新门禁
export function batchUpdateAccess(data) {
  return request({
    url: '/ai_iot/access/batch',
    method: 'put',
    data: data
  })
}

// 批量设置门禁设备二维码
export function batchUpdateDeviceQr(ids) {
  return request({
    url: '/ai_iot/access/qr',
    method: 'put',
    data: {ids:ids}
  })
}

// 合法卡比对事件订阅
export function subscribeLegalCard(communityId) {
  return request({
    url: '/ai_iot/access/subscribe/'+communityId,
    method: 'post'
  })
}

// 合法卡比对事件订阅
export function unsubscribeLegalCard(communityId) {
  return request({
    url: '/ai_iot/access/unsubscribe/'+communityId,
    method: 'post'
  })
}

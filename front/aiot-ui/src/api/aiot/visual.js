import request from '@/utils/request'

// 查询列表数据
export function selectVisualList(query) {
  return request({
    url: '/ai_iot/visual/list',
    method: 'get',
    params: query
  })
}

// 设备同步
export function syncVisualData(communityId) {
  return request({
    url: '/ai_iot/visual/sync_data/'+communityId,
    method: 'post'
  })
}

// 更新设备状态
export function checkOnline(data) {
  return request({
    url: '/ai_iot/visual/online',
    method: 'post',
    data: data
  })
}

// 根据id查询数据
export function selectVisualById(visualId) {
  return request({
    url: '/ai_iot/visual/'+visualId,
    method: 'get'
  })
}

// 更新可视对讲
export function updateVisual(data) {
  return request({
    url: '/ai_iot/visual',
    method: 'put',
    data: data
  })
}

// 批量设置可视对讲设备二维码
export function batchUpdateDeviceQr(ids) {
  return request({
    url: '/ai_iot/visual/qr',
    method: 'put',
    data: {ids:ids}
  })
}

// 可视对讲开锁事件订阅
export function subscribeLock(communityId) {
  return request({
    url: '/ai_iot/visual/subscribe/'+ communityId,
    method: 'post'
  })
}

// 可视对讲开锁事件取消订阅
export function unsubscribeLock(communityId) {
  return request({
    url: '/ai_iot/visual/unsubscribe/'+ communityId,
    method: 'post'
  })
}

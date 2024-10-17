import request from '@/utils/request'

// 查询列表数据
export function selectCameraList(query) {
  return request({
    url: '/ai_iot/camera/list',
    method: 'get',
    params: query
  })
}

// 设备同步
export function syncCameraData(communityId) {
  return request({
    url: '/ai_iot/camera/sync_data/' +  communityId,
    method: 'post'
  })
}

// 更新设备状态
export function checkOnline(data) {
  return request({
    url: '/ai_iot/camera/online',
    method: 'post',
    data: data
  })
}

// 根据id查询数据
export function selectCameraById(cameraId) {
  return request({
    url: '/ai_iot/camera/'+cameraId,
    method: 'get'
  })
}

// 更新监控点
export function updateCamera(data) {
  return request({
    url: '/ai_iot/camera',
    method: 'put',
    data: data
  })
}

// 批量设置监控点设备二维码
export function batchUpdateDeviceQr(data) {
  return request({
    url: '/ai_iot/camera/qr',
    method: 'put',
    data: data
  })
}

// 预览,获取视频流
export function cameraUrl(query) {
  return request({
    url: '/ai_iot/camera/play',
    method: 'get',
    params: query
  })
}

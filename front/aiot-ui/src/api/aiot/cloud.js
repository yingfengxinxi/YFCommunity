import request from '@/utils/request'

// 查询列表数据
export function selectCloudList(query) {
  return request({
    url: '/ai_iot/cloud/list',
    method: 'get',
    params: query
  })
}

// 设备添加
export function insertCloud(communityId) {
  return request({
    url: '/ai_iot/cloud',
    method: 'post',
    data: communityId
  })
}

// 根据id查询数据
export function selectCloudById(accessId) {
  return request({
    url: '/ai_iot/cloud/'+accessId,
    method: 'get'
  })
}

// 更新门禁
export function updateCloud(data) {
  return request({
    url: '/ai_iot/cloud',
    method: 'put',
    data: data
  })
}

// 批量更新二维码
export function batchUpdateDeviceQr(ids) {
  return request({
    url: '/ai_iot/cloud/qr',
    method: 'put',
    data: {ids:ids}
  })
}

// 批量删除门禁
export function removeCloud(ids) {
  return request({
    url: '/ai_iot/cloud/'+ids,
    method: 'delete'
  })
}

// 查询小区下未绑定门禁的门禁点
export function selectUnbindDoors(query) {
  return request({
    url: '/ai_iot/cloud/door',
    method: 'get',
    params: query
  })
}


import request from '@/utils/request'

// 查询列表数据
export function selectBarrierList(query) {
  return request({
    url: '/ai_iot/barrier/list',
    method: 'get',
    params: query
  })
}

// 设备同步
export function syncBarrierData(communityId) {
  return request({
    url: '/ai_iot/barrier/sync_data/' + communityId,
    method: 'post'
  })
}

// 根据id查询数据
export function selectBarrierById(barrierId) {
  return request({
    url: '/ai_iot/barrier/'+barrierId,
    method: 'get'
  })
}

// 更新道闸
export function updateBarrier(data) {
  return request({
    url: '/ai_iot/barrier',
    method: 'put',
    data: data
  })
}

// 批量设置道闸设备二维码
export function batchUpdateDeviceQr(ids) {
  return request({
    url: '/ai_iot/barrier/qr',
    method: 'put',
    data:{ids:ids}
  })
}

// 查询所有未绑定的车道数据
export function selectUnbindRoadway(query) {
  return request({
    url: '/ai_iot/barrier/unbid_roadway',
    method: 'get',
    params: query
  })
}

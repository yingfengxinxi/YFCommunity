import request from '@/utils/request'

// 查询设备维修工单列表
export function listDeviceOrder(query) {
  return request({
    url: '/estate/work/deviceOrder/list',
    method: 'get',
    params: query
  })
}
// 查询设备维修工单列表
export function getDeviceOrderById(orderId) {
  return request({
    url: '/estate/work/deviceOrder/' + orderId,
    method: 'get'
  })
}
// 查询设备维修工单列表
export function listDeviceOrderCode(query) {
  return request({
    url: '/estate/work/deviceOrder/codeList',
    method: 'get',
    params: query
  })
}
// 查询设备维修工单详细
export function getDeviceOrder(orderId) {
  return request({
    url: '/estate/work/deviceOrder/' + orderId,
    method: 'get'
  })
}

// 新增设备维修工单
export function addDeviceOrder(data) {
  return request({
    url: '/estate/work/deviceOrder',
    method: 'post',
    data: data
  })
}

// 修改设备维修工单
export function updateDeviceOrder(data) {
  return request({
    url: '/estate/work/deviceOrder',
    method: 'put',
    data: data
  })
}

// 删除设备维修工单
export function delDeviceOrder(orderId) {
  return request({
    url: '/estate/work/deviceOrder/' + orderId,
    method: 'delete'
  })
}
// 查询工单协助人信息 通用
export function assistList(query) {
  return request({
    url: '/estate/work/deviceOrder/assistList',
    method: 'get',
    params: query
  })
}

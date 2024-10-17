import request from '@/utils/request'

// 查询装修评定工单列表
export function listDecorateOrder(query) {
  return request({
    url: '/estate/decorateOrder/list',
    method: 'get',
    params: query
  })
}

// 查询装修评定工单详情
export function getDecorateOrderDetail(orderId) {
  return request({
    url: '/estate/decorateOrder/order/' + orderId,
    method: 'get'
  })
}

// 装修评定工单修改页面查询数据
export function getUpdateDetail(orderId,communityId) {
  return request({
    url: '/estate/decorateOrder/update/' + orderId + '/' + communityId,
    method: 'get'
  })
}

// 查询装修详情
export function getDecorateDetail(decorateId) {
  return request({
    url: '/estate/decorateOrder/' + decorateId,
    method: 'get'
  })
}

// 查询装修列表
export function getDecorateList(communityId) {
  return request({
    url: '/estate/decorateOrder/getDecorateList/' + communityId,
    method: 'get'
  })
}

// 查询分配人员信息
export function getShareNameList(communityId) {
  return request({
    url: '/estate/decorateOrder/share/' + communityId,
    method: 'get'
  })
}

// 装修评定工单删除
export function deleteDecorateOrder(orderIds) {
  return request({
    url: '/estate/decorateOrder/' + orderIds,
    method: 'delete'
  })
}

// 装修评定工单整改
export function rectificationDecorateOrder(orderId) {
  return request({
    url: '/estate/decorateOrder/' + orderId,
    method: 'put'
  })
}

// 装修评定工单分配
export function decorateOrderDistribution(data) {
  return request({
    url: '/estate/decorateOrder/distribution',
    method: 'put',
    data: data
  })
}

// 装修评定工单新增
export function addDecorateOrder(data) {
  return request({
    url: '/estate/decorateOrder',
    method: 'post',
    data: data
  })
}

// 装修评定工单修改
export function updateDecorateOrder(data) {
  return request({
    url: '/estate/decorateOrder',
    method: 'put',
    data: data
  })
}

// 审核单个驳回
export function workerNewRejectOne(data) {
  return request({
    url: '/estate/workerNew/one',
    method: 'post',
    data: data
  })
}

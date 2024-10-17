import request from '@/utils/request'

// 查询物业缴费设置列表
export function listFee(query) {
  return request({
    url: '/estate/pfe/list',
    method: 'post',
    data: query
  })
}

// 查询物业缴费设置详细
export function getFee(propertyFeeId) {
  return request({
    url: '/estate/pfe/' + propertyFeeId,
    method: 'get'
  })
}

// 新增物业缴费设置
export function addFee(data) {
  return request({
    url: '/estate/pfe',
    method: 'post',
    data: data
  })
}

// 修改物业缴费设置
export function updateFee(data) {
  return request({
    url: '/estate/pfe',
    method: 'put',
    data: data
  })
}

// 修改物业缴费状态
export function updatePropertyFeeStatus(data) {
  return request({
    url: '/estate/pfe/updatePropertyFeeStatus',
    method: 'put',
    data: data
  })
}

// 删除物业缴费设置
export function delFee(communityId) {
  return request({
    url: '/estate/pfe/' + communityId,
    method: 'delete'
  })
}

// 根据楼栋查询是否配置物业缴费规则
export function getByBuildingIdPropertyFeeCount(buildingId, communityId) {
  return request({
    url: '/estate/pfe/getByBuildingIdPropertyFeeCount?buildingId=' + buildingId + "&communityId=" + communityId,
    method: 'get'
  })
}

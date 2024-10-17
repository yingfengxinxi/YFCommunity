import request from '@/utils/request'

/**
 * 小区列表
 */
export function communityIdList(query) {
  return request({
    url: '/estate/index/getCommunityIdList',
    method: 'get',
    params: query
  })
}

/**
 * 审核数量 房产审核 车辆审核 业主审核 租客审核
 */
export function auditNum(query) {
  return request({
    url: '/estate/index/getAuditNum',
    method: 'get',
    params: query
  })
}

/**
 * 审核数量 房产审核 车辆审核 业主审核 租客审核
 */
export function logList(query) {
  return request({
    url: '/estate/index/getLogList',
    method: 'get',
    params: query
  })
}

/**
 * 审核数量 房产审核 车辆审核 业主审核 租客审核
 */
export function energyData(query) {
  return request({
    url: '/estate/index/getEnergyData',
    method: 'get',
    params: query
  })
}
/**
 * 查询房屋状态
 */
export function getRoomStatusData(query) {
  return request({
    url: '/estate/index/getRoomStatusData',
    method: 'get',
    params: query
  })
}
/**
 * 获取工单数量
 */
export function getOrderLists(query) {
  return request({
    url: '/estate/index/getOrderList',
    method: 'get',
    params: query
  })
}

/**
 * 查询社区公告
 */
export function getNoticeLists(query) {
  return request({
    url: '/estate/index/getNoticeList',
    method: 'get',
    params: query
  })
}

/**
 * 查询小区收益
 */
export function getFoundData(query) {
  return request({
    url: '/estate/index/getFoundData',
    method: 'get',
    params: query
  })
}

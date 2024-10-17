import request from '@/utils/request'

// 分页查询
export function listWatLed(query) {
  return request({
    url: '/estate/watLed/list',
    method: 'get',
    params: query
  })
}
// 查询详细
export function getWatLedInfo(watchmanId) {
  return request({
    url: '/estate/watLed/getWatLed/' + watchmanId,
    method: 'get'
  })
}
// 协助人员列表
export function getWatStaList(watchmanId) {
  return request({
    url: '/estate/watLed/staList/' + watchmanId,
    method: 'get'
  })
}

// 详情内容列表
/*export function detailList(planId) {
  return request({
    url: '/estate/inspectLedger/detailList/' + planId,
    method: 'get',
  })
}*/
// 巡更类型列表
export function getTourNameList(communityId) {
  return request({
    url: '/estate/watLed/nameList/' + communityId,
    method: 'get'
  })
}
// 巡更路线列表
export function getRouteNameList(communityId) {
  return request({
    url: '/estate/watLed/routeList/' + communityId,
    method: 'get'
  })
}

// 详情 执行详情
export function deatilListThree(watchmanId) {
  return request({
    url: '/estate/watLed/deatilListThree/' + watchmanId,
    method: 'get',
  })
}

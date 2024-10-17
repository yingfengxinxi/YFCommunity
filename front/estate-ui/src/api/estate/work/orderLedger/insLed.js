import request from '@/utils/request'

// 分页查询
export function listInsLed(query) {
  return request({
    url: '/estate/inspectLedger/list',
    method: 'get',
    params: query
  })
}
// 查询详细
export function getInspectInfo(inspectId) {
  return request({
    url: '/estate/inspectLedger/getInspect/' + inspectId,
    method: 'get'
  })
}
// 协助人员列表
export function getInsStaList(inspectId) {
  return request({
    url: '/estate/inspectLedger/staList/' + inspectId,
    method: 'get'
  })
}

// 详情内容列表
export function detailList(inspectId) {
  return request({
    url: '/estate/inspectLedger/detailList/' + inspectId,
    method: 'get',
  })
}
// 巡检类型列表
export function getTourNameList(communityId) {
  return request({
    url: '/estate/inspectLedger/nameList/' + communityId,
    method: 'get'
  })
}

// 详情 执行详情
export function deatilListThree(inspectId) {
  return request({
    url: '/estate/inspectLedger/deatilListThree/' + inspectId,
    method: 'get',
  })
}

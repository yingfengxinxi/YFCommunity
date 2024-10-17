import request from '@/utils/request'

// 分页查询
export function listPatLed(query) {
  return request({
    url: '/estate/patLed/list',
    method: 'get',
    params: query
  })
}
// 查询详细
export function getPatLedInfo(patrolId) {
  return request({
    url: '/estate/patLed/getPatLed/' + patrolId,
    method: 'get'
  })
}
// 协助人员列表
export function getPatStaList(patrolId) {
  return request({
    url: '/estate/patLed/staList/' + patrolId,
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
// 巡检类型列表
export function getTourNameList(patrolId) {
  return request({
    url: '/estate/patLed/nameList/' + patrolId,
    method: 'get'
  })
}

// 详情 执行详情
export function deatilListThree(cleanId) {
  return request({
    url: '/estate/patLed/deatilListThree/' + cleanId,
    method: 'get',
  })
}

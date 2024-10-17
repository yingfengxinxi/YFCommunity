import request from '@/utils/request'

// 分页查询
export function listCleLed(query) {
  return request({
    url: '/estate/cleLed/list',
    method: 'get',
    params: query
  })
}
// 查询详细
export function getCleLedInfo(cleanId) {
  return request({
    url: '/estate/cleLed/getCleLed/' + cleanId,
    method: 'get'
  })
}
// 协助人员列表
export function getCleStaList(cleanId) {
  return request({
    url: '/estate/cleLed/staList/' + cleanId,
    method: 'get'
  })
}

// 详情 执行详情
export function deatilListThree(cleanId) {
  return request({
    url: '/estate/cleLed/deatilListThree/' + cleanId,
    method: 'get',
  })
}

import request from '@/utils/request'

// 查询场馆活动列表
export function listAct(query) {
  return request({
    url: '/estate/venueact/list',
    method: 'get',
    params: query
  })
}

// 查询场馆活动详细
export function getAct(activityId) {
  return request({
    url: '/estate/venueact/' + activityId,
    method: 'get'
  })
}

// 新增场馆活动
export function addAct(data) {
  return request({
    url: '/estate/venueact',
    method: 'post',
    data: data
  })
}

// 修改场馆活动
export function updateAct(data) {
  return request({
    url: '/estate/venueact',
    method: 'put',
    data: data
  })
}

// 删除场馆活动
export function delAct(activityId) {
  return request({
    url: '/estate/venueact/' + activityId,
    method: 'delete'
  })
}

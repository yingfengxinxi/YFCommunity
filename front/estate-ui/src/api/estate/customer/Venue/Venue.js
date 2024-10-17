import request from '@/utils/request'

// 查询场馆信息列表
export function listVenue(query) {
  return request({
    url: '/estate/venue/list',
    method: 'get',
    params: query
  })
}

// 查询场馆信息详细
export function getVenue(venueId) {
  return request({
    url: '/estate/venue/' + venueId,
    method: 'get'
  })
}

// 新增场馆信息
export function addVenue(data) {
  return request({
    url: '/estate/venue',
    method: 'post',
    data: data
  })
}

// 修改场馆信息
export function updateVenue(data) {
  return request({
    url: '/estate/venue',
    method: 'put',
    data: data
  })
}

// 删除场馆信息
export function delVenue(venueId) {
  return request({
    url: '/estate/venue/' + venueId,
    method: 'delete'
  })
}


//获取管理员
export function getg(communityId) {
  return request({
    url: '/estate/venue/g/'+ communityId,
    method: 'get',
  })
}

// 查询场馆信息列表
export function venueList(query) {
  return request({
    url: '/estate/venue/venueList',
    method: 'get',
    params: query
  })
}

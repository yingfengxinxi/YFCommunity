import request from '@/utils/request'

// 查询小区管理列表
export function listCommunity(query) {
  return request({
    url: '/system/community/list',
    method: 'get',
    params: query
  })
}

// 查询小区管理详细
export function getCommunity(communityId) {
  return request({
    url: '/system/community/' + communityId,
    method: 'get'
  })
}
// 根据表名和小区id查询 通用查询
export function getCommunityTable(query) {
  return request({
    url: '/system/community/table',
    method: 'get',
    params: query
  })
}
// 新增小区管理
export function addCommunity(data) {
  return request({
    url: '/system/community',
    method: 'post',
    data: data
  })
}

// 新增海康配置管理
export function addCommunityHik(data) {
  return request({
    url: '/system/community/hik',
    method: 'post',
    data: data
  })
}
// 新增微信配置管理
export function addCommunityWechat(data) {
  return request({
    url: '/system/community/wechat',
    method: 'post',
    data: data
  })
}
// 修改小区管理
export function updateCommunity(data) {
  return request({
    url: '/system/community',
    method: 'put',
    data: data
  })
}

// 修改海康配置管理
export function updateCommunityHik(data) {
  return request({
    url: '/system/community/hik',
    method: 'put',
    data: data
  })
}
// 修改微信配置管理
export function updateCommunityWechat(data) {
  return request({
    url: '/system/community/wechat',
    method: 'put',
    data: data
  })
}
// 删除小区管理
export function delCommunity(communityId) {
  return request({
    url: '/system/community/' + communityId,
    method: 'delete'
  })
}

//上传图片
export function uploadPrivateKey(file) {
  return request({
    url: '/system/community/upload',
    method: 'post',
    data: file
  })
}

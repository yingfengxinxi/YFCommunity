import request from '@/utils/request'

// 查询社交列表查询
export function getSocialList(query) {
  return request({
    url: '/estate/social/messageList',
    method: 'post',
    data: query
  })
}

//新增社交信息
export function addSocial(data) {
  return request({
    url: '/estate/social',
    method: 'post',
    data: data
  })
}


// 查询系统将信息列表
export function getSystemList(query) {
  return request({
    url: '/estate/social/systemList',
    method: 'get',
    params: query
  })
}








//删除信息管理
export function delMessageSocial(socialId) {
  return request({
    url: '/estate/social/delMessage/' + socialId,
    method: 'delete'
  })
}

//删除系统信息管理
export function delSystemSocial(id) {
  return request({
    url: '/estate/social/delSystem/' + id,
    method: 'delete'
  })
}

//修改
export function updateSocial(data) {
  return request({
    url: '/estate/social',
    method: 'put',
    data: data
  })
}

//根据id查询详情
export function querySocialById(socialId) {
  return request({
    url: '/estate/social/' + socialId,
    method: 'get'
  })
}

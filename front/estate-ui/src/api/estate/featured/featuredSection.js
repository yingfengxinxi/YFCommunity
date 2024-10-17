import request from '@/utils/request'

// 查询社交列表查询
export function getFeaturedSectionList(query) {
  return request({
    url: '/estate/featuredSection/messageList',
    method: 'post',
    data: query
  })
}

//新增社交信息
export function addFeaturedSection(data) {
  return request({
    url: '/estate/featuredSection',
    method: 'post',
    data: data
  })
}


// 查询系统将信息列表
export function getSystemList(query) {
  return request({
    url: '/estate/featuredSection/systemList',
    method: 'get',
    params: query
  })
}








//删除信息管理
export function delMessageFeaturedSection(featuredId) {
  return request({
    url: '/estate/featuredSection/delMessage/' + featuredId,
    method: 'delete'
  })
}

//删除系统信息管理
export function delSystemFeaturedSection(id) {
  return request({
    url: '/estate/featuredSection/delSystem/' + id,
    method: 'delete'
  })
}

//修改
export function updateFeaturedSection(data) {
  return request({
    url: '/estate/featuredSection',
    method: 'put',
    data: data
  })
}

//根据id查询详情
export function queryFeaturedSectionById(featuredId) {
  return request({
    url: '/estate/featuredSection/' + featuredId,
    method: 'get'
  })
}

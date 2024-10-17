import request from '@/utils/request'

// 查询党组织管理
export function partyList(query) {
  return request({
    url: '/govern/party/list',
    method: 'get',
    params: query
  })
}

//上传图片
export function uploadImg(file) {
  return request({
    url: '/govern/party/upload',
    method: 'post',
    data: file
  })
}

//查询顶层党组织列表
export function listOrganizationForTop(){
  return request({
    url: '/govern/party/topList',
    method: 'get',
  })
}

//查询上级党组织列表
export function listOrganizationForParty(){
  return request({
    url: '/govern/party/partyList',
    method: 'get',
  })
}

// 查询审核通过的党员
export function getMemberList(query) {
  return request({
    url: '/govern/party/memberAll',
    method: 'get',
    params: query
  })
}

// 新增
export function addParty(data) {
  return request({
    url: '/govern/party/add',
    method: 'post',
    data: data
  })
}

//查看党组织
export function getParty(partyId) {
  return request({
    url: '/govern/party/' + partyId,
    method: 'get'
  })
}

//修改
export function updateParty(data) {
  return request({
    url: '/govern/party/edit',
    method: 'put',
    data: data
  })
}

//删除党组织管理
export function delParty(partyId) {
  return request({
    url: '/govern/party/' + partyId,
    method: 'delete'
  })
}


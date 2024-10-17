
import request from '@/utils/request'

// 查询员工列表
export function listStaff(query) {
  return request({
    url: '/estate/staff/list',
    method: 'get',
    params: query
  })
}

// 查询员工评价列表
export function getStaffEvaluationList(query) {
  return request({
    url: '/estate/staffEvaluation/getList',
    method: 'post',
    data: query
  })
}


// 详情 updateEmployee
export function getStaff(query) {
  return request({
    url: '/estate/staff/id',
    method: 'get',
    params: query
  })
}

// 查询小区列表 updateEmployee
export function updateStaff(query) {
  return request({
    url: '/estate/staff/update',
    method: 'put',
    data: query
  })
}
// 查询小区列表 updateEmployee
export function addStaff(query) {
  return request({
    url: '/estate/staff/add',
    method: 'post',
    data: query
  })
}

// 删除
export function delStaff(staffId) {
  return request({
    url: '/estate/staff/delete/' + staffId,
    method: 'delete',
  })
}

// 离职
export function updateStaffStatus(staffId) {
  return request({
    url: '/estate/staff/updateStatus/' + staffId,
    method: 'delete',
  })
}
//上传图片
export function uploadImg(file) {
  return request({
    url: '/estate/staff/upload',
    method: 'post',
    data: file
  })
}
// 查询员工列表
export function listLeisureBusyStaffs(query) {
  return request({
    url: '/estate/staff/listLeisureBusyStaffs',
    method: 'get',
    params: query
  })
}

// 修改密码
export function resetPass(query) {
  return request({
    url: '/estate/staff/resetPass',
    method: 'put',
    params: query
  })
}

// 查询员工列表
export function getCommunityIdStaffList(communityId, workPostList) {
  return request({
    url: '/estate/staff/getCommunityIdStaffList?communityId=' + communityId + "&workPostList=" + workPostList,
    method: 'get'
  })
}


// 查询员工授权角色列表
export function listEstateRole(staffId) {
  return request({
    url: '/estate/estateStaffRole/list?staffId=' + staffId,
    method: 'get'
  })
}


// 员工授权角色
export function insertEstateRole(data) {
  return request({
    url: '/estate/estateStaffRole/insert',
    method: 'post',
    data: data
  })
}
import request from '@/utils/request'

// 查询党员管理
export function memberList(query) {
  return request({
    url: '/govern/member/list',
    method: 'get',
    params: query
  })
}

// 查询党员列表详细
export function getMember(memberId) {
  return request({
    url: '/govern/member/' + memberId,
    method: 'get'
  })
}

// 查询党员列表删除
export function MemberRemove(data) {
  return request({
    url: '/govern/member/deleteMember',
    method: 'post',
    data: data
  })
}

// 查询党组织
export function partyAll() {
  return request({
    url: '/govern/member/partyAll',
    method: 'get'
  })
}

// 查询用户
export function getOwnerAll(data) {
  return request({
    url: '/govern/member/getOwnerAll',
    method: 'get',
    params: data
  })
}

// 新增
export function addMember(data) {
  return request({
    url: '/govern/member/add',
    method: 'post',
    data: data
  })
}

// 修改
export function upMember(data) {
  return request({
    url: '/govern/member/edit',
    method: 'put',
    data: data
  })
}

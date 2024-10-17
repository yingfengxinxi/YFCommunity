import request from '@/utils/request'

// 查询列表
export function list(query) {
  return request({
    url: '/estate/vote/getList',
    method: 'post',
    data: query
  })
}

// 查询详细
export function getById(id) {
  return request({
    url: '/estate/vote/' + id,
    method: 'get'
  })
}

// 新增
export function insert(data) {
  return request({
    url: '/estate/vote/saveVote',
    method: 'post',
    data: data
  })
}

// 修改
export function update(data) {
  return request({
    url: '/estate/vote/updateVote',
    method: 'put',
    data: data
  })
}
export function yqUpdate(data) {
  return request({
    url: '/estate/vote/yqUpdate',
    method: 'put',
    data: data
  })
}

// 删除
export function del(id) {
  return request({
    url: '/estate/vote/delete?id=' + id,
    method: 'delete'
  })
}

// 投票范围列表
export function voteScopeList(query) {
  return request({
    url: '/estate/voteScope/getList',
    method: 'post',
    data: query
  })
}

// 投票范围人员列表
export function voteScopeOwnerList(voteId, roomId) {
  return request({
    url: '/estate/voteScopeOwner/getList?voteId=' + voteId + "&roomId=" + roomId,
    method: 'get'
  })
}

// 投票范围人员保存列表
export function voteScopeOwnerSave(data) {
  return request({
    url: '/estate/voteScopeOwner/saveVoteOwner',
    method: 'post',
    data: data
  })
}

// 删除投票范围人员
export function voteScopeOwnerRemove(id) {
  return request({
    url: '/estate/voteScopeOwner/removeById?id=' + id,
    method: 'delete'
  })
}

// 业主下拉
export function getAllOwnerList(communityId, voteScopeId) {
  return request({
    url: '/estate/file/owner/getAllOwnerList?communityId=' + communityId + "&voteScopeId=" + voteScopeId,
    method: 'get'
  })
}

// 查看结果
export function getResult(id) {
  return request({
    url: '/estate/vote/getResult?id=' + id,
    method: 'get'
  })
}

import request from '@/utils/request'



// 第一个LIST
export function cashApplyList(query) {
  return request({
    url: '/estate/cashapply/list',
    method: 'get',
    params: query
  })
}
// 首页三个图片
export function getInfoById(query) {
  return request({
    url: '/estate/cashapply/getInfoById',
    method: 'post',
    data: query
  })
}

export function ownerVoteList(query) {
  return request({
    url: '/estate/cashapply/ownerVoteList',
    method: 'get',
    params: query
  })
}

// 查询详细列表
export function voteInfoById(voteId) {
  return request({
    url: '/estate/cashapply/voteInfoById/' + voteId,
    method: 'get'
  })
}

// 审核
export function aduitUpdata(data) {
  return request({
    url: '/estate/cashapply/update',
    method: 'put',
    data: data
  })
}
// 发布
export function bulletin(data) {
  return request({
    url: '/estate/cashapply/bulletin',
    method: 'get',
    params: data
  })
}

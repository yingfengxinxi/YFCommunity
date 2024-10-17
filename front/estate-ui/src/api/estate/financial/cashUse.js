import request from '@/utils/request'



// 第一个LIST
export function cashUseList(query) {
  return request({
    url: '/estate/cashUse/list',
    method: 'get',
    params: query
  })
}

// 查询详细
export function infoByVoteId(voteId) {
  return request({
    url: '/estate/cashUse/infoByVoteId/' + voteId,
    method: 'get'
  })
}

// 上传
export function cashUseUpdata(data) {
  return request({
    url: '/estate/cashUse/update',
    method: 'put',
    data: data
  })
}


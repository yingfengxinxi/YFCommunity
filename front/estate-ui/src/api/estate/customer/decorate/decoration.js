import request from '@/utils/request'

// 查询装修列表
export function listDecoration(query) {
  return request({
    url: '/estate/decoration/list',
    method: 'get',
    params: query
  })
}

// 查询装修详情
export function getDecorationDetail(decorateId) {
  return request({
    url: '/estate/decoration/' + decorateId,
    method: 'get'
  })
}

// 审核通过
export function decorateAdopt(decorateId) {
  return request({
    url: '/estate/decoration/' + decorateId,
    method: 'post'
  })
}

// 审核驳回
export function decorateReject(data) {
  return request({
    url: '/estate/decoration',
    method: 'post',
    data: data
  })
}

// 延期
export function decorateDelay(decorateId) {
  return request({
    url: '/estate/decoration/delay/' + decorateId,
    method: 'post'
  })
}

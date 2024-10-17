import request from '@/utils/request'

// 查询列表
export function list(query) {
  return request({
    url: '/estate/busQuestionnaireAccount/list',
    method: 'get',
    params: query
  })
}

// 查询详细
export function getById(id) {
  return request({
    url: '/estate/busQuestionnaireAccount/' + id,
    method: 'get'
  })
}
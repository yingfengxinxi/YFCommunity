import request from '@/utils/request'

// 查询审核未通过列表
export function rejectedList(query) {
  return request({
    url: '/govern/rejected/list',
    method: 'get',
    params: query
  })
}

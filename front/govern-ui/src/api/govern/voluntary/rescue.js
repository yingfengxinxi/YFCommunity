import request from '@/utils/request'

//查询列表
export function searchList(query) {
  return request({
    url: '/govern/rescue/searchList',
    method: 'get',
    params: query
  })
}

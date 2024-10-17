import request from '@/utils/request'

// 查询表格
export function getTable(params) {
  return request({
    url: '/estate/suggest/getList',
    method: 'get',
    params: params
  })
}

// 根据ID查询详情
export function getTableListById(id) {
  return request({
    url: `/estate/suggest/getDetail/${id}`,
    method: 'get'
  })
}

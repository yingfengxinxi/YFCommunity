import request from '@/utils/request'

// 查询表格
export function getTableList(params) {
  return request({
    url: '/estate/Illegal/getList',
    method: 'get',
    params: params
  })
}

// 根据ID查询表格
export function getTableListById(communityId) {
  return request({
    url: `/estate/Illegal/get/${communityId}`,
    method: 'get'
  })
}

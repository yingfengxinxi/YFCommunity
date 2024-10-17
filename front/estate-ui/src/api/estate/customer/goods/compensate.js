import request from '@/utils/request'

// 查询列表
export function getCompensateList(query) {
  return request({
    url: '/estate/compensate/list',
    method: 'get',
    params: query
  })
}

// 新增赔偿
export function addCompensate(data) {
  return request({
    url: '/estate/compensate/insert',
    method: 'post',
    data: data
  })
}

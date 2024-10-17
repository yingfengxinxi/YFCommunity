import request from '@/utils/request'

// 查询列表
export function list(query) {
  return request({
    url: '/estate/sendSms/list',
    method: 'post',
    data: query
  })
}

// 查询详细
export function getById(id) {
  return request({
    url: '/estate/sendSms/' + id,
    method: 'get'
  })
}

// 新增
export function insert(data) {
  return request({
    url: '/estate/sendSms/add',
    method: 'post',
    data: data
  })
}

// 修改
export function update(data) {
  return request({
    url: '/estate/sendSms/update',
    method: 'put',
    data: data
  })
}
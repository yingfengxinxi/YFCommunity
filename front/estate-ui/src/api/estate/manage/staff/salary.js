import request from '@/utils/request'

// 查询小区列表
export function listSalary(query) {
  return request({
    url: '/estate/salary/salaryList',
    method: 'get',
    params: query
  })
}
export function listChange(query) {
  return request({
    url: '/estate/salary/list',
    method: 'get',
    params: query
  })
}
export function listName(query) {
  return request({
    url: '/estate/salary/listName',
    method: 'get',
    params: query
  })
}
export function updateSalary(query) {
  return request({
    url: '/estate/salary/update',
    method: 'put',
    params: query
  })
}
export function addSalary(query) {
  return request({
    url: '/estate/salary/add',
    method: 'post',
    params: query
  })
}
// 删除
export function delSalary(salaryId) {
  return request({
    url: '/estate/salary/delete/' + salaryId,
    method: 'delete',
  })
}

import request from '@/utils/request'

// 分页查询
export function getListPage(query) {
  return request({
    url: '/estate/work/patrolType/listPage',
    method: 'get',
    params: query
  })
}

// 查询列表
export function getList(param) {
  return request({
    url: '/estate/work/patrolType/list',
    method: 'get',
    params: param
  })
}

// 新增巡查类型
export function add(data) {
  return request({
    url: '/estate/work/patrolType',
    method: 'post',
    data: data
  })
}

// 修改巡查类型
export function edit(data) {
  return request({
    url: '/estate/work/patrolType',
    method: 'put',
    data: data
  })
}

// 修改巡查类型
export function remove(ids) {
  return request({
    url: '/estate/work/patrolType/'+ids,
    method: 'delete'
  })
}

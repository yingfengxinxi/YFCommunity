import request from '@/utils/request'

// 分页查询
export function getList(query) {
  return request({
    url: '/estate/work/nightPatrolType/list',
    method: 'get',
    params: query
  })
}

export function getListPage(query) {
  return request({
    url: '/estate/work/nightPatrolType/listPage',
    method: 'get',
    params: query
  })
}

// 新增巡查类型
export function add(data) {
  return request({
    url: '/estate/work/nightPatrolType',
    method: 'post',
    data: data
  })
}

// 修改巡查类型
export function edit(data) {
  return request({
    url: '/estate/work/nightPatrolType',
    method: 'put',
    data: data
  })
}

// 修改巡查类型
export function remove(ids) {
  return request({
    url: '/estate/work/nightPatrolType/'+ids,
    method: 'delete'
  })
}

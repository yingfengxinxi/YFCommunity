import request from '@/utils/request'

// 分页查询
export function getListPage(query) {
  return request({
    url: '/estate/work/nightPatrolOrder/listPage',
    method: 'get',
    params: query
  })
}

// 巡查计划编辑详情
export function getInfo(id) {
  return request({
    url: '/estate/work/nightPatrolOrder/' + id,
    method: 'get'
  })
}

// 巡查工单查看详情
export function getDetail(patrolId) {
  return request({
    url: '/estate/work/nightPatrolOrder/detail/' + patrolId,
    method: 'get'
  })
}

// 新增巡查类型
export function add(data) {
  return request({
    url: '/estate/work/nightPatrolOrder',
    method: 'post',
    data: data
  })
}

// 修改巡查类型
export function edit(data) {
  return request({
    url: '/estate/work/nightPatrolOrder',
    method: 'put',
    data: data
  })
}

// 分配巡查负责人
export function allot(data) {
  return request({
    url: '/estate/work/nightPatrolOrder/allot',
    method: 'put',
    data: data
  })
}

// 修改巡查类型
export function remove(ids) {
  return request({
    url: '/estate/work/nightPatrolOrder/'+ids,
    method: 'delete'
  })
}

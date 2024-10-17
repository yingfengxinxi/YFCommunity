import request from '@/utils/request'

// 分页查询
export function getListPage(query) {
  return request({
    url: '/estate/work/patrolOrder/listPage',
    method: 'get',
    params: query
  })
}

// 巡查计划编辑详情
export function getInfo(patrolId) {
  return request({
    url: '/estate/work/patrolOrder/' + patrolId,
    method: 'get'
  })
}

// 巡查工单查看详情
export function getDetail(patrolId) {
  return request({
    url: '/estate/work/patrolOrder/detail/' + patrolId,
    method: 'get'
  })
}

// 新增巡查工单
export function add(data) {
  return request({
    url: '/estate/work/patrolOrder',
    method: 'post',
    data: data
  })
}

// 修改巡查工单
export function edit(data) {
  return request({
    url: '/estate/work/patrolOrder',
    method: 'put',
    data: data
  })
}

// 分配巡查负责人
export function allot(data) {
  return request({
    url: '/estate/work/patrolOrder/allot',
    method: 'put',
    data: data
  })
}

// 删除巡查工单
export function remove(orderId) {
  return request({
    url: '/estate/work/patrolOrder/' + orderId,
    method: 'delete'
  })
}

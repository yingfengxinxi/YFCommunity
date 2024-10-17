import request from '@/utils/request'

// 查询设施设备列表
export function listPatrolWorkOrder(query) {
  return request({
    url: '/estate/patrolWorkOrder/list',
    method: 'post',
    data: query
  })
}

// 查询设施设备详情
export function getPatrolWorkOrderDetail(id) {
  return request({
    url: '/estate/patrolWorkOrder/' + id,
    method: 'get'
  })
}


// 新增
export function save(data) {
  return request({
    url: '/estate/patrolWorkOrder/save',
    method: 'post',
    data: data
  })
}

// 修改
export function update(data) {
  return request({
    url: '/estate/patrolWorkOrder/update',
    method: 'put',
    data: data
  })
}

import request from '@/utils/request'

// 查询设施设备列表
export function listPatrol(query) {
  return request({
    url: '/estate/patrolWorkOrderSetting/list',
    method: 'post',
    data: query
  })
}

// 查询设施设备详情
export function getPatrolDetail(id) {
  return request({
    url: '/estate/patrolWorkOrderSetting/' + id,
    method: 'get'
  })
}


// 新增
export function save(data) {
  return request({
    url: '/estate/patrolWorkOrderSetting/save',
    method: 'post',
    data: data
  })
}

// 修改
export function update(data) {
  return request({
    url: '/estate/patrolWorkOrderSetting/update',
    method: 'put',
    data: data
  })
}
//修改状态
export function updatePatrolStatus(id, patrolStatus) {
  return request({
    url: '/estate/patrolWorkOrderSetting/updatePatrolStatus?id=' + id + "&patrolStatus=" + patrolStatus,
    method: 'get'
  })
}

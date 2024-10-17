import request from '@/utils/request'

// 查询巡更设置列表
export function listPatrolSetting(query) {
  return request({
    url: '/estate/patrolSetting/list',
    method: 'get',
    params: query
  })
}

// 查询巡更设置详情
export function getPatrolSettingDetail(id) {
  return request({
    url: '/estate/patrolSetting/' + id,
    method: 'get'
  })
}


// 增加巡更设置
export function add(data) {
  return request({
    url: '/estate/patrolSetting/save',
    method: 'post',
    data: data
  })
}


// 修改巡更设置
export function update(data) {
  return request({
    url: '/estate/patrolSetting/update',
    method: 'put',
    data: data
  })
}


// 删除巡更设置
export function removeById(id) {
  return request({
    url: '/estate/patrolSetting/removeById?id=' + id,
    method: 'delete'
  })
}

// 修改巡更设置状态详情
export function updatePatrolStatus(id, patrolStatus) {
  return request({
    url: '/estate/patrolSetting/updatePatrolStatus?id=' + id + "&patrolStatus=" + patrolStatus,
    method: 'get'
  })
}

// 获取下一次执行点
export function getTime(firstExecutionTime, frequency, executionNumber) {
  return request({
    url: '/estate/patrolSetting/getTime?firstExecutionTime=' + firstExecutionTime + "&frequency=" + frequency + "&executionNumber=" + executionNumber,
    method: 'get'
  })
}


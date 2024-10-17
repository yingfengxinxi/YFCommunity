import request from '@/utils/request'

// 查询清洁工单设置列表
export function listCleaningWorkOrderSetting(query) {
  return request({
    url: '/estate/cleaningWorkOrderSetting/list',
    method: 'get',
    params: query
  })
}

// 查询清洁工单设置详情
export function getCleaningWorkOrderSettingDetail(id) {
  return request({
    url: '/estate/cleaningWorkOrderSetting/' + id,
    method: 'get'
  })
}


// 增加清洁工单设置
export function add(data) {
  return request({
    url: '/estate/cleaningWorkOrderSetting/save',
    method: 'post',
    data: data
  })
}


// 修改清洁工单设置
export function update(data) {
  return request({
    url: '/estate/cleaningWorkOrderSetting/update',
    method: 'put',
    data: data
  })
}


// 删除清洁工单设置
export function removeById(id) {
  return request({
    url: '/estate/cleaningWorkOrderSetting/removeById?id=' + id,
    method: 'delete'
  })
}

// 修改清洁工单设置状态详情
export function updateCleaningWorkOrderSettingStatus(id, inspectStatus) {
  return request({
    url: '/estate/cleaningWorkOrderSetting/updateInspectStatus?id=' + id + "&inspectStatus=" + inspectStatus,
    method: 'get'
  })
}

// 获取下一次执行点
export function getTime(firstExecutionTime, frequency, executionNumber) {
  return request({
    url: '/estate/cleaningWorkOrderSetting/getTime?firstExecutionTime=' + firstExecutionTime + "&frequency=" + frequency + "&executionNumber=" + executionNumber,
    method: 'get'
  })
}


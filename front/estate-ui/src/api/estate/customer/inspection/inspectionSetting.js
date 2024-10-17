import request from '@/utils/request'

// 查询巡查设置列表
export function listInspectionSetting(query) {
  return request({
    url: '/estate/inspectionSetting/list',
    method: 'get',
    params: query
  })
}

// 查询巡查设置详情
export function getInspectionSettingDetail(id) {
  return request({
    url: '/estate/inspectionSetting/' + id,
    method: 'get'
  })
}


// 增加巡查设置
export function add(data) {
  return request({
    url: '/estate/inspectionSetting/save',
    method: 'post',
    data: data
  })
}


// 修改巡查设置
export function update(data) {
  return request({
    url: '/estate/inspectionSetting/update',
    method: 'put',
    data: data
  })
}


// 删除巡查设置
export function removeById(id) {
  return request({
    url: '/estate/inspectionSetting/removeById?id=' + id,
    method: 'delete'
  })
}

// 修改巡查设置状态详情
export function updateInspectStatus(id, inspectStatus) {
  return request({
    url: '/estate/inspectionSetting/updateInspectStatus?id=' + id + "&inspectStatus=" + inspectStatus,
    method: 'get'
  })
}

// 获取下一次执行点
export function getTime(firstExecutionTime, frequency, executionNumber) {
  return request({
    url: '/estate/inspectionSetting/getTime?firstExecutionTime=' + firstExecutionTime + "&frequency=" + frequency + "&executionNumber=" + executionNumber,
    method: 'get'
  })
}


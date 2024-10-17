import request from '@/utils/request'

// 分页查询
export function getRouteList(query) {
  return request({
    url: '/estate/route/list',
    method: 'get',
    params: query
  })
}

// 详情查询
export function getRoute(routeId) {
  return request({
    url: '/estate/route/'+routeId,
    method: 'get',
  })
}


//修改状态
export function updateStatus(query) {
  return request({
    url: '/estate/route/status',
    method: 'post',
    data:query
  })
}

// 批量删除
export function removes(query) {
  return request({
    url: '/estate/route/removes',
    method: 'post',
    data: query
  })
}

// 逻辑删除
export function remove(query) {
  return request({
    url: '/estate/route/remove',
    method: 'post',
    params: query
  })
}

// 新增
export function RouteAdd(query) {
  return request({
    url: '/estate/route/addUpdate',
    method: 'post',
    data: query
  })
}

// 选择框内容
export function PointList(communityId) {
  return request({
    url: '/estate/route/point1/'+communityId,
    method: 'get',
  })
}

// 选择框点击事件
export function Point(pointId) {
  return request({
    url: '/estate/route/point/'+pointId,
    method: 'get',
  })
}


// 详情表格查询
export function getList(query) {
  return request({
    url: '/estate/route/pointlist',
    method: 'get',
    params: query
  })
}

// 详情表格查询
export function getListUrl(routeId) {
  return request({
    url: '/estate/route/pointlist/'+routeId,
    method: 'get',
  })
}

import request from '@/utils/request'

//查询网格管理列表
export function getList(query) {
  return request({
    url: '/govern/grid/list',
    method: 'get',
    params: query
  })
}

//添加网格管理
export function addGrid(data) {
  return request({
    url: '/govern/grid',
    method: 'post',
    data: data
  })
}

//网格管理详情
export function getGridDetail(query) {
  return request({
    url: '/govern/grid/'+query,
    method: 'get',
  })
}

//修改网格管理
export function updateGrid(data) {
  return request({
    url: '/govern/grid',
    method: 'put',
    data: data
  })
}

//删除网格管理
export function delGrid(query) {
  return request({
    url: '/govern/grid/'+query,
    method: 'delete',
  })
}

//获取当前小区的员工集合
export function getStaff(query) {
  return request({
    url: '/govern/grid/staffList/'+query,
    method: 'get'
  })
}


//获取事件
export function getEventList(query) {
  return request({
    url: '/govern/grid/staffList/'+query,
    method: 'get'
  })
}


//获取当前登录人所在物业下的所有小区集合
export function getCommuntiyList() {
  return request({
    url: '/govern/community/current',
    method: 'get'
  })
}


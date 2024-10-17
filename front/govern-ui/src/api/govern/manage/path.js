import request from '@/utils/request'

//查询人员轨迹列表
export function getList(query) {
  return request({
    url: '/govern/path/list',
    method: 'get',
    params: query
  })
}

//人员轨迹详情
export function getUserLogDetail(query) {
  return request({
    url: '/govern/path',
    method: 'get',
    params: query,
  })
}

//查询车辆轨迹列表
export function getVehicleList(query) {
  return request({
    url: '/govern/path/vehicleList',
    method: 'get',
    params: query
  })
}
//人员轨迹详情
export function getVehicleDetail(query) {
  return request({
    url: '/govern/path/getVehicleDetail',
    method: 'get',
    params: query,
  })
}

//获取当前登录人所在物业下的所有小区集合
export function getCommuntiyList() {
  return request({
    url: '/govern/community/current',
    method: 'get'
  })
}

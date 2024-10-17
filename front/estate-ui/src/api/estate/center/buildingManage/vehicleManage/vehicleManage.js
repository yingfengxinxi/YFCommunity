import request from '@/utils/request'

// 分页查询
export function listPage(query) {
  return request({
    url: '/estate/carVehicle/listPage',
    method: 'get',
    params: query
  })
}

// 新增车辆信息
export function saveOrUpdate(query) {
  return request({
    url: '/estate/carVehicle/saveOrUpdate',
    method: 'post',
    data: query
  })
}

// 详情
export function getDetail(query) {
  return request({
    url: '/estate/carVehicle/detail',
    method: 'get',
    params: query
  })
}

// 逻辑删除
export function remove(query) {
  return request({
    url: '/estate/carVehicle/remove',
    method: 'post',
    params: query
  })
}

// 批量删除
export function removes(query) {
  return request({
    url: '/estate/carVehicle/removes',
    method: 'post',
    data: query
  })
}

// 根据业户ID查找手机号
export function selectOwnerName(query) {
  return request({
    url: '/estate/carVehicle/selectOwnerName',
    method: 'get',
    params: query
  })
}


// 根据业户ID查找联系方式
export function selectOwnerCar(query) {
  return request({
    url: '/estate/carVehicle/selectOwnerCar',
    method: 'get',
    params: query
  })
}

// 编辑页面详情
export function updateDetailInfo(query) {
  return request({
    url: '/estate/carVehicle/updateDetail',
    method: 'get',
    params: query
  })
}


// 查询当前业主下车辆信息
export function getByOwnerIdVehicleList(communityId, ownerId) {
  return request({
    url: '/estate/carVehicle/getByOwnerIdVehicleList?communityId=' + communityId + "&ownerId=" + ownerId,
    method: 'get',
  })
}


// 查询当前业主下可绑定车辆信息
export function getByAccountIdVehicleList(communityId, accountId) {
  return request({
    url: '/estate/carVehicle/getByAccountIdVehicleList?communityId=' + communityId + "&accountId=" + accountId,
    method: 'get',
  })
}

import request from '@/utils/request'

// 分页查询
export function listPage(query) {
  return request({
    url: '/estate/carport/listPage',
    method: 'get',
    params: query
  })
}

// 详情查看
export function getDetail(query) {
  return request({
    url: '/estate/carport/detail',
    method: 'get',
    params: query
  })
}

// 详情下列表
export function getDetailList(query) {
  return request({
    url: '/estate/carport/detailList',
    method: 'get',
    params: query
  })
}

// 批量删除
export function removes(id) {
  return request({
    url: '/estate/carport/removes?id=' + id,
    method: 'get'
  })
}

// 转让
export function cedeSubmit(query) {
  return request({
    url: '/estate/carport/cede',
    method: 'post',
    params: query
  })
}

// 新增
export function saveOrUpdate(query) {
  return request({
    url: '/estate/carport/saveOrUpdate',
    method: 'post',
    params: query
  })
}

// 修改页详情查看
export function updateDetail(query) {
  return request({
    url: '/estate/carport/updateDetail',
    method: 'get',
    params: query
  })
}

// 修改车位状态
export function changeCarportStatus(query) {
  return request({
    url: '/estate/carport/changeCarportStatus',
    method: 'post',
    params: query
  })
}

// 查询所有车位
export function getCommunityCarportList(communityId) {
  return request({
    url: '/estate/carportNo/getAllCarportNo?communityId=' + communityId,
    method: 'get',
  })
}


// 绑定车辆信息
export function bindVehicle(data) {
  return request({
    url: '/estate/carport/bindVehicle',
    method: 'post',
    data: data
  })
}



// 查询指定业主下绑定车位信息
export function getOwnerIdCarportList(ownerId, communityId) {
  return request({
    url: '/estate/carport/getOwnerIdCarportList?ownerId=' + ownerId + "&communityId=" + communityId,
    method: 'get'
  })
}


// 根据车位查询车位类型
export function getCarportNoCarportNature(carportNo) {
  return request({
    url: '/estate/carport/getCarportNoCarportNature?carportNo=' + carportNo,
    method: 'get'
  })
}

import request from '@/utils/request'
//查询
export function query(query) {
  return request({
    url: '/estate/file/owner/query',
    method: 'get',
    params: query
  })
}
//查询  房间下的人员
export function queryByRoomId(query) {
  return request({
    url: '/estate/file/owner/person',
    method: 'get',
    params: query
  })
}
//查询 业主房产
export function queryRoomByOwnerId(query) {
  return request({
    url: '/estate/file/owner/room',
    method: 'get',
    params: query
  })
}
//查询 业主车辆
export function queryCarByOwnerId(query) {
  return request({
    url: '/estate/file/owner/car',
    method: 'get',
    params: query
  })
}
//查询 业主的租客
export function queryTenantByOwnerId(query) {
  return request({
    url: '/estate/file/owner/tenant',
    method: 'get',
    params: query
  })
}
//查询 业主车位
export function queryCarPortByOwnerId(query) {
  return request({
    url: '/estate/file/owner/car_port',
    method: 'get',
    params: query
  })
}
//添加
export function add(data) {
  return request({
    url: '/estate/file/owner/add',
    method: 'post',
    data: data
  })
}
//更新
export function update(data) {
  return request({
    url: '/estate/file/owner/update',
    method: 'post',
    data: data
  })
}
//单个删除
export function deleted(id) {
  return request({
    url: `/estate/file/owner/deleted/${id}`,
    method: 'post',
  })
}
//多个删除
export function deleteds(ids) {
  return request({
    url: '/estate/file/owner/deleteds',
    method: 'post',
    data: ids
  })
}
//文件上传
export function upload(data) {
  return request({
    url: '/file/upload',
    method: 'post',
    data: data
  })
}
//证件类型
export function queryDict() {
  return request({
    url: '/estate/file/owner/queryDict',
    method: 'get',
  })
}

export function getDevice(communityId) {
  return request({
    url: '/estate/file/owner/getDevice/' + communityId,
    method: 'get',
  })
}
// 人员信息授权
export function authOwner(params) {
  return request({
    url: '/estate/file/owner/auth',
    method: 'post',
    data: params
  })
}

// 业主详情信息
export function getDetailByOwnerId(ownerId, communityId) {
  return request({
    url: '/estate/file/owner/getDetailByOwnerId?ownerId=' + ownerId + "&communityId=" + communityId,
    method: 'get'
  })
}

// 查询当前房间下是否存在业主本人信息
export function getByRoomIdOwnerRelationCount(roomId, ownerRelation, ownerId) {
  return request({
    url: '/estate/file/owner/getByRoomIdOwnerRelationCount?roomId=' + roomId + "&ownerRelation=" + ownerRelation + "&ownerId=" + ownerId,
    method: 'get'
  })
}


import request from '@/utils/request'

// 房间管理树形结构数据
export function treeRoom() {
  return request({
    url: '/estate/room/treeRoom',
    method: 'get'
  })
}

// 房屋管理列表
export function listPageRooms(parameter) {
  return request({
    url: '/estate/room/listPageRooms',
    method: 'get',
    params: parameter
  })
}

// 房屋管理列表
export function getVoteListRooms(parameter) {
  return request({
    url: '/estate/room/getVoteListRooms',
    method: 'get',
    params: parameter
  })
}

// 房屋管理列表
export function deListPageRooms(parameter) {
  return request({
    url: '/estate/room/deListPageRooms',
    method: 'get',
    params: parameter
  })
}

// 过户查询业主信息
export function roomSearchListPage(parameter) {
  return request({
    url: '/estate/room/roomSearchListPage',
    method: 'get',
    params: parameter
  })
}

// 根据条件查询所有房号
export function listRoomNamesByCondition(parameter) {
  return request({
    url: '/estate/room/listRoomNamesByCondition',
    method: 'get',
    params: parameter
  })
}

// 根据小区id查询所有单元
export function listUnitsByCommunityId(communityId) {
  return request({
    url: '/estate/room/listUnitsByCommunityId/' + communityId,
    method: 'get'
  })
}

// 查询业主信息以及家庭成员列表
export function roomFamilyList(roomId) {
  return request({
    url: '/estate/room/roomFamilyList/' + roomId,
    method: 'get'
  })
}

// 根据条件查询图标数据
export function getRoomGraphData(params) {
  return request({
    url: '/estate/room/roomGraphData',
    method: 'get',
    params: params
  })
}

// 根据房屋id查询详情
export function getRoomDetail(roomId, communityId) {
  return request({
    url: '/estate/room/getRoomDetail/' + roomId + '/' + communityId,
    method: 'get'
  })
}


// 批量新增楼宇
export function insertBuildingBatch(data) {
  return request({
    url: '/estate/room/insertBuildingBatch',
    method: 'post',
    data: data
  })
}

// 批量新增单元和房屋
export function insertUnitAndRoomBatch(data) {
  return request({
    url: '/estate/room/insertUnitAndRoomBatch',
    method: 'post',
    data: data
  })
}

// 批量新增房屋
export function insertRoomBatch(data) {
  return request({
    url: '/estate/room/insertRoomBatch',
    method: 'post',
    data: data
  })
}

// 批量删除房屋
export function deleteRoomBatch(ids) {
  return request({
    url: '/estate/room/deleteRoomBatch/' + ids,
    method: 'delete'
  })
}

// 批量更新房屋信息
export function updateRoomBatch(data) {
  return request({
    url: '/estate/room/updateRoomBatch',
    method: 'post',
    data: data
  })
}

// 修改房屋信息
export function updateRoom(data) {
  return request({
    url: '/estate/room/updateRoom',
    method: 'post',
    data: data
  })
}

// 修改楼栋信息
export function updateBuilding(data) {
  return request({
    url: '/estate/room/updateBuilding',
    method: 'post',
    data: data
  })
}

// 修改单元信息
export function updateUnit(data) {
  return request({
    url: '/estate/room/updateUnit',
    method: 'post',
    data: data
  })
}

// 修改单元信息
export function deleteBuilding(buildingId) {
  return request({
    url: '/estate/room/deleteBuilding/' + buildingId,
    method: 'delete'
  })
}

// 修改单元信息
export function deleteUnit(unitId) {
  return request({
    url: '/estate/room/deleteUnit/' + unitId,
    method: 'delete'
  })
}

// 过户
export function transferOwnership(params) {
  return request({
    url: '/estate/room/transferOwnership',
    method: 'get',
    params: params
  })
}

// 根据房屋id收房
export function confirmRoom(roomId) {
  return request({
    url: '/estate/room/confirmRoom/' + roomId,
    method: 'get'
  })
}

// 查询指定小区下所有楼栋
export function getCommunityIdBuildingName(communityId) {
  return request({
    url: '/estate/room/getCommunityIdBuildingName?communityId=' + communityId,
    method: 'get',
  })
}

// 查询指定楼栋下所有单元楼
export function getBuildingIdUnitIdName(buildingId) {
  return request({
    url: '/estate/room/getBuildingIdUnitIdName?buildingId=' + buildingId,
    method: 'get',
  })
}

// 查询指定单元楼信息
export function getByUnitIdDetail(unitId) {
  return request({
    url: '/estate/room/getByUnitIdDetail?unitId=' + unitId,
    method: 'get',
  })
}

// 修改房屋状态
export function bachUpdateRoomStatus(roomIds, roomStatus) {
  return request({
    url: '/estate/room/bachUpdateRoomStatus?roomIds=' + roomIds + "&roomStatus=" + roomStatus,
    method: 'get',
  })
}


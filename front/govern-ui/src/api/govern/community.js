import request from '@/utils/request'

// 查询当前登录人所在物业下的所有小区
export function getCurrentList() {
  return request({
    url: '/govern/community/current',
    method: 'get'
  })
}
// 查询当前登录人所在物业下的所有党组织
export function getpartyAllList() {
  return request({
    url: '/govern/member/partyAll',
    method: 'get'
  })
}

// 根据小区id查询楼栋
export function getBuildingList(communityId) {
  return request({
    url: '/govern/community/building/'+communityId,
    method: 'get'
  })
}

// 根据楼栋id查询单元
export function getUnitList(buildingId) {
  return request({
    url: '/govern/community/unit/'+buildingId,
    method: 'get'
  })
}

// 根据单元id查询房间
export function getRoomList(unitId) {
  return request({
    url: '/govern/community/room/'+unitId,
    method: 'get'
  })
}

/**
 * 根据小区id/楼栋id/单元id/房间id 查询业主
 * communityId 小区id
 * buildingId 楼栋id
 * unitId 单元id
 * roomId 房间id
 * 至少一个
 */
export function getOwnerList(query) {
  return request({
    url: '/govern/community/owner',
    method: 'get',
    params: query
  })
}

/**
 * 根据小区id/楼栋id/单元id/房间id 查询租客
 * communityId 小区id
 * buildingId 楼栋id
 * unitId 单元id
 * roomId 房间id
 * 至少一个
 */
export function getTenantList(query) {
  return request({
    url: '/govern/community/tenant',
    method: 'get',
    params: query
  })
}


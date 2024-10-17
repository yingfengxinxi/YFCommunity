import request from '@/utils/request'

// 查询当前登录人所在物业下的所有小区
export function getCurrentList() {
  return request({
    url: '/estate/community/current',
    method: 'get'
  })
}

// 查询当前物业下的所有小区
export function getCurrentLists(estateId) {
  return request({
    url: '/estate/community/currents/' + estateId,
    method: 'get'
  })
}

// 查询所有小区
export function getAllCommunityList() {
  return request({
    url: '/estate/community/getAllCommunityList',
    method: 'get'
  })
}

// 根据小区id查询楼栋
export function getBuildingList(communityId) {
  return request({
    url: '/estate/community/building/' + communityId,
    method: 'get'
  })
}

// 根据小区id查询楼栋【物业缴费楼栋下拉】
export function getPropertyFeeBuilding(communityId) {
  return request({
    url: '/estate/community/getPropertyFeeBuilding/' + communityId,
    method: 'get'
  })
}

// 根据楼栋id查询单元
export function getUnitList(buildingId) {
  return request({
    url: '/estate/community/unit/' + buildingId,
    method: 'get'
  })
}

// 根据单元id查询房间
export function getRoomList(unitId) {
  return request({
    url: '/estate/community/room/' + unitId,
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
    url: '/estate/community/owner',
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
    url: '/estate/community/tenant',
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
export function getPersonList(query) {
  return request({
    url: '/estate/community/person',
    method: 'get',
    params: query
  })
}


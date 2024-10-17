import request from '@/utils/request'

// 查询列表
export function getRentList(query) {
  return request({
    url: '/estate/rent/list',
    method: 'post',
    data: query
  })
}

// 上下架
export function updateByRentIdShelve(rentId, rentStatus) {
  return request({
    url: '/estate/rent/updateByRentIdShelve?rentId=' + rentId + '&rentStatus=' + rentStatus,
    method: 'get'
  })
}

// 详情
export function getMyRental(rentId) {
  return request({
    url: '/estate/rent/getMyRental?rentId=' + rentId,
    method: 'get'
  })
}


// 删除
export function deleteRent(rentId) {
  return request({
    url: '/estate/rent/delete/' + rentId,
    method: 'delete'
  })
}


// 出租楼栋下拉
export function getRentBuildingList(communityId) {
  return request({
    url: '/estate/rent/getRentBuildingList?communityId=' + communityId,
    method: 'get'
  })
}


// 出租单元楼下拉
export function getRentUnitList(buildingId) {
  return request({
    url: '/estate/rent/getRentUnitList?buildingId=' + buildingId,
    method: 'get'
  })
}


// 出租单元楼下拉
export function getRentRoomList(unitId) {
  return request({
    url: '/estate/rent/getRentRoomList?unitId=' + unitId,
    method: 'get'
  })
}

// 房号查询当前房屋是否出租
export function getByRoomIdRentStatusCount(roomId) {
  return request({
    url: '/estate/rent/getByRoomIdRentStatusCount?roomId=' + roomId,
    method: 'get'
  })
}


//新增我要出租
export function insertMyRental(data) {
  return request({
    url: '/estate/rent/insertMyRental',
    method: 'post',
    data: data
  })
}

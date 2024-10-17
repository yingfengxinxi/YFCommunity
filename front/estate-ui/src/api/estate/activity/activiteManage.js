import request from '@/utils/request'

export function listManage(query) {
  return request({
    url: '/estate/activiteCom/manage/list',
    method: 'get',
    params: query
  })
}
//不分页列表
export function listAllManage(query) {
  return request({
    url: '/estate/activiteCom/manage/allList',
    method: 'get',
    params: query
  })
}
// 类型列表
export function TypelistManage(query) {
  return request({
    url: '/estate/activiteCom/manage/typelist',
    method: 'get',
    params: query
  })
}

// 楼栋列表
export function BuildinglistManage(query) {
  return request({
    url: '/estate/activiteCom/manage/Buildinglist',
    method: 'get',
    params: query
  })
}


// 新增
export function addActivite(data) {
  return request({
    url: '/estate/activiteCom/manage/insert',
    method: 'post',
    data: data
  })
}

// 修改
export function updateActivite(data) {
  return request({
    url: '/estate/activiteCom/manage/update',
    method: 'put',
    data: data
  })
}

// 删除
export function delActivite(activityId) {
  return request({
    url: '/estate/activiteCom/manage/delete/' + activityId,
    method: 'delete'
  })
}

// 查询详细
export function getActiviteInfo(activityId) {
  return request({
    url: '/estate/activiteCom/manage/getInfoById/' + activityId,
    method: 'get'
  })
}

// 查询参与人数详细
export function getJoinPeoPleInfo(activityId) {
  return request({
    url: '/estate/activiteCom/manage/selectJoinPeople/' + activityId,
    method: 'get'
  })
}

// 参与人数列表
export function joinPeopleListManage(query) {
  return request({
    url: '/estate/activiteCom/manage/joinPeopleList',
    method: 'get',
    params: query
  })
}

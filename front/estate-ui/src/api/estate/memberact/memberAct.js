import request from '@/utils/request'

// 查询列表
export function memberActList(query) {
  return request({
    url: '/estate/memberAct/list',
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
export function addMemberAct(data) {
  return request({
    url: '/estate/memberAct/add',
    method: 'post',
    data: data
  })
}

// 修改
export function updateActivite(data) {
  return request({
    url: '/estate/memberAct/update',
    method: 'put',
    data: data
  })
}

//删除
export function delActivite(activityIds) {
  return request({
    url: '/estate/memberAct/remove/' + activityIds,
    method: 'delete'
  })
}

// 详情
export function getMemberActInfo(activityId) {
  return request({
    url: '/estate/memberAct/getInfoById/' + activityId,
    method: 'get'
  })
}


// 查询报名人员列表
export function joinPeopleListManage(query) {
  return request({
    url: '/estate/memberAct/joinPeopleList',
    method: 'get',
    params: query
  })
}






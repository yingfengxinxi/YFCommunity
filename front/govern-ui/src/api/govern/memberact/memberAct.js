import request from '@/utils/request'

// 查询列表
export function memberActList(query) {
  return request({
    url: '/govern/memberAct/list',
    method: 'get',
    params: query
  })
}

// 新增
export function addMemberAct(data) {
  return request({
    url: '/govern/memberAct/add',
    method: 'post',
    data: data
  })
}

// 删除
export function delMemberAct(activityIds) {
  return request({
    url: '/govern/memberAct/remove/' + activityIds,
    method: 'delete'
  })
}

// 参与人数列表 /memberAct/findListMemberPeople
export function joinPeopleListManage(data) {
  return request({
    url: '/govern/memberAct/findListMemberPeople',
    method: 'post',
    data: data
  })
}

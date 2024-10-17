import request from '@/utils/request'

// 查询问卷调查基础信息列表
export function listSurvey(query) {
  return request({
    url: '/govern/busEstate',
    method: 'get',
    params: query
  })
}

// 删除问卷调查
export function delSurvey(id) {
  return request({
    url: '/govern/busEstate/' + id,
    method: 'delete'
  })
}

// 新增问卷调查
export function addSurvey(data) {
  return request({
    url: '/govern/busEstate/add',
    method: 'post',
    data: data
  })
}


// 查询问卷调查基础信息列表
export function listQuestion(query) {
  return request({
    url: '/govern/busEstate/list',
    method: 'get',
    params: query
  })
}
// 查询问卷调查基础信息列表
export function getListRecord(query) {
  return request({
    url: '/govern/busEstate/listRecord',
    method: 'get',
    params: query
  })
}
export function bulletin(query) {
  return request({
    url: '/govern/busEstate/bulletin',
    method: 'get',
    params: query
  })
}
// 新增问卷调查
export function addQuestion(data) {
  return request({
    url: '/govern/busEstate/add',
    method: 'post',
    data: data
  })
}

// 删除问卷调查
export function delQuestion(voteId) {
  return request({
    url: '/govern/busEstate/' + voteId,
    method: 'delete'
  })
}

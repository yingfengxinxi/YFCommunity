import request from '@/utils/request'

// 查询问卷调查基础信息列表
export function listSurvey(query) {
  return request({
    url: '/govern/investigation',
    method: 'get',
    params: query
  })
}

// 删除问卷调查
export function delSurvey(id) {
  return request({
    url: '/govern/investigation/' + id,
    method: 'delete'
  })
}

// 新增问卷调查
export function addSurvey(data) {
  return request({
    url: '/govern/investigation/add',
    method: 'post',
    data: data
  })
}


// 查询问卷调查基础信息列表
export function listQuestion(query) {
  return request({
    url: '/govern/investigation/list',
    method: 'get',
    params: query
  })
}

// 新增问卷调查
export function addQuestion(data) {
  return request({
    url: '/govern/investigation/add',
    method: 'post',
    data: data
  })
}

// 删除问卷调查
export function delQuestion(voteId) {
  return request({
    url: '/govern/investigation/' + voteId,
    method: 'delete'
  })
}


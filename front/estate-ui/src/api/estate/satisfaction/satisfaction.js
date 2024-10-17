import request from '@/utils/request'

// 列表
export function listSatisfactionSurvey(query) {
  return request({
    url: '/estate/satisfactionSurvey/list',
    method: 'post',
    data: query
  })
}

// 详情
export function getSatisfactionSurveyDetail(id, flag) {
  return request({
    url: '/estate/satisfactionSurvey/' + id + "/" + flag,
    method: 'get'
  })
}


// 新增
export function add(data) {
  return request({
    url: '/estate/satisfactionSurvey/save',
    method: 'post',
    data: data
  })
}

// 修改
export function update(data) {
  return request({
    url: '/estate/satisfactionSurvey/update',
    method: 'put',
    data: data
  })
}
//删除
export function del(id) {
  return request({
    url: '/estate/satisfactionSurvey/removeById?id=' + id,
    method: 'delete'
  })
}

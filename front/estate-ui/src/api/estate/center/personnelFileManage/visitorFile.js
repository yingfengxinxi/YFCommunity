//查询 合同
import request from "@/utils/request";
//分页
export function query(query) {
  return request({
    url: '/estate/interview/query',
    method: 'post',
    data: query
  })
}
//详情
export function getVisitorById(visitorId) {
  return request({
    url: '/estate/interview/' + visitorId,
    method: 'get'
  })
}
// 访客 拜访记录
export function queryLiaison(query) {
  return request({
    url: '/estate/interview/queryLiaison',
    method: 'get',
    params: query
  })
}
//修改
export function update(data) {
  return request({
    url: '/estate/interview/update',
    method: 'post',
    data: data
  })
}

//删除
export function deleted(data) {
  debugger
  return request({
    url: '/estate/interview/deleted',
    method: 'post',
    data: data
  })
}

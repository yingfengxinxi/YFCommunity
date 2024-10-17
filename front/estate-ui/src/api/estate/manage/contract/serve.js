import request from '@/utils/request'

// 查询服务合同的列表
export function getServeList(query) {
  return request({
    url: '/estate/serve/list',
    method: 'get',
    params: query
  })
}

// 查询服务合同的详情
export function getDetailInfo(query) {
  return request({
    url: '/estate/serve/getDetailInfo/'+query,
    method: 'get',
  })
}


// 获取服务合同下拉框
export function getServeSelect() {
  return request({
    url: '/estate/serve/getServeSelect',
    method: 'get',
  })
}
// 获取服务合同签约人下拉框
export function getUserInfo(query) {
  return request({
    url: '/estate/serve/getUserInfo/'+query,
    method: 'get',
  })
}

// 服务合同新增
export function addServe(data) {
  return request({
    url: '/estate/serve/addServe',
    method: 'post',
    data:data
  })
}

// 服务合同删除
export function delServe(query) {
  return request({
    url: '/estate/serve/delServe/'+query,
    method: 'get',
  })
}

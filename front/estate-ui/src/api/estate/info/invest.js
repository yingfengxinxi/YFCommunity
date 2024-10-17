import request from '@/utils/request'


// 查询所有项目文档
export function listInvest(query) {
  return request({
    method: 'post',
    url: '/estate/invest/list',
    data: query
  })
}

// 根据invest_id查询
export function getInvest(investId) {
  return request({
    url: '/estate/invest/' + investId,
    method: 'get'
  })
}

// 新增项目文档
export function addInvest(data) {
  return request({
    url: '/estate/invest',
    method: 'post',
    data: data
  })
}

//撤回公示
export function backInvest(investId) {
  return request({
    url: '/estate/invest/back/' + investId,
    method: 'get',
  })
}

//发布公示
export function upInvest(investId) {
  return request({
    url: '/estate/invest/up/' + investId,
    method: 'get',
  })
}

// 修改项目文档
export function updateInvest(data) {
  return request({
    url: '/estate/invest',
    method: 'put',
    data: data
  })
}

// 删除项目文档
export function delInvest(investId) {
  return request({
    url: '/estate/invest/' + investId,
    method: 'delete'
  })
}

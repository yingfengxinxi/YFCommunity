import request from '@/utils/request'

export function listManage(query) {
  return request({
    url: '/estate/activiteType/list',
    method: 'get',
    params: query
  })
}

// 新增
export function addActivite(data) {
  return request({
    url: '/estate/activiteType/insert',
    method: 'post',
    data: data
  })
}

// 修改
export function updateActivite(data) {
  return request({
    url: '/estate/activiteType/update',
    method: 'put',
    data: data
  })
}

// 删除 
export function removeActivite(query) {
  return request({
    url: '/estate/activiteType/delete/',
    method: 'post',
    data: query
  })
}

// 查询详细
export function getActiviteInfo(typeId) {
  return request({
    url: '/estate/activiteType/getInfoById/' + typeId,
    method: 'get'
  })
}

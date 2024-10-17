import request from '@/utils/request'

//查询列表
export function listPassage(query) {
  return request({
    url: '/estate/passage/list',
    method: 'get',
    params: query
  })
}

// 新增
export function addPassage(data) {
  return request({
    url: '/estate/passage/insert',
    method: 'post',
    data: data
  })
}

// 修改
export function updatePassage(data) {
  return request({
    url: '/estate/passage/update',
    method: 'put',
    data: data
  })
}

// 删除
export function removePassage(passageId) {
  return request({
    url: '/estate/passage/delete/' + passageId,
    method: 'delete',
  })
}

// 查询详细
export function getPassageInfo(passageId) {
  return request({
    url: '/estate/passage/getInfoById/' + passageId,
    method: 'get'
  })
}

import request from '@/utils/request'

//查询列表
export function listPersonLabel(query) {
  return request({
    url: '/estate/personLabel/list',
    method: 'get',
    params: query
  })
}
//查询人员列表
export function listName(query) {
  return request({
    url: '/estate/personLabel/nameList',
    method: 'get',
    params: query
  })
}


// 新增
export function addPersonLabel(data) {
  return request({
    url: '/estate/personLabel/insert',
    method: 'post',
    data: data
  })
}

// 修改
export function updatePersonLabel(data) {
  return request({
    url: '/estate/personLabel/update',
    method: 'put',
    data: data
  })
}

// 删除
export function removePersonLabel(labelId) {
  return request({
    url: '/estate/personLabel/delete/' + labelId,
    method: 'delete',
  })
}

// 查询详细
export function getPersonLabelInfo(labelId) {
  return request({
    url: '/estate/personLabel/getInfoById/' + labelId,
    method: 'get'
  })
}

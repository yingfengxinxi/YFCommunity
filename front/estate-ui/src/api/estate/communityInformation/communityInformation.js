import request from '@/utils/request'

export function getListPage(query) {
  return request({
    url: '/estate/communityInformation/list',
    method: 'post',
    data: query
  })
}
// 新增
export function insert(data) {
  return request({
    url: '/estate/communityInformation/insert',
    method: 'post',
    data: data
  })
}

// 修改
export function update(data) {
  return request({
    url: '/estate/communityInformation/update',
    method: 'put',
    data: data
  })
}

// 删除
export function del(id, top) {
  return request({
    url: '/estate/communityInformation/delete?id=' + id + "&top=" + top,
    method: 'delete'
  })
}

// 查询详细
export function getById(id) {
  return request({
    url: '/estate/communityInformation/detail?id=' + id,
    method: 'get'
  })
}
//置顶
export function updateTop(id) {
  return request({
    url: '/estate/communityInformation/updateTop?id=' + id,
    method: 'get'
  })
}

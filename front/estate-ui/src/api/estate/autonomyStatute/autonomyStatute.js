import request from '@/utils/request'

export function getListPage(query) {
  return request({
    url: '/estate/autonomyStatute/list',
    method: 'post',
    data: query
  })
}
// 新增
export function insert(data) {
  return request({
    url: '/estate/autonomyStatute/insert',
    method: 'post',
    data: data
  })
}

// 修改
export function update(data) {
  return request({
    url: '/estate/autonomyStatute/update',
    method: 'put',
    data: data
  })
}

// 删除
export function del(id) {
  return request({
    url: '/estate/autonomyStatute/delete?id=' + id,
    method: 'delete'
  })
}

// 查询详细
export function getById(id) {
  return request({
    url: '/estate/autonomyStatute/' + id,
    method: 'get'
  })
}
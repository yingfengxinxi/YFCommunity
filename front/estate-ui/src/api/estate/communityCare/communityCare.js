import request from '@/utils/request'

export function getListPage(query) {
  return request({
    url: '/estate/communityCare/list',
    method: 'get',
    params: query
  })
}
// 新增
export function insert(data) {
  return request({
    url: '/estate/communityCare/insert',
    method: 'post',
    data: data
  })
}

// 修改
export function update(data) {
  return request({
    url: '/estate/communityCare/update',
    method: 'put',
    data: data
  })
}

// 删除
export function del(id) {
  return request({
    url: '/estate/communityCare/delete?id=' + id,
    method: 'delete'
  })
}

// 查询详细
export function getById(id) {
  return request({
    url: '/estate/communityCare/' + id,
    method: 'get'
  })
}

// 发布活动
export function updateByIdStatus(id, status) {
  return request({
    url: '/estate/communityCare/updateByIdStatus?id=' + id + '&status=' + status,
    method: 'get'
  })
}
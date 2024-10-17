import request from '@/utils/request'

export function getListPage(query) {
  return request({
    url: '/estate/communityCareType/getListPage',
    method: 'get',
    params: query
  })
}

export function getList(query) {
  return request({
    url: '/estate/communityCareType/list',
    method: 'get',
    params: query
  })
}

// 新增
export function insert(data) {
  return request({
    url: '/estate/communityCareType/insert',
    method: 'post',
    data: data
  })
}

// 修改
export function update(data) {
  return request({
    url: '/estate/communityCareType/update',
    method: 'put',
    data: data
  })
}

// 删除
export function del(id) {
  return request({
    url: '/estate/communityCareType/delete?id=' + id,
    method: 'delete'
  })
}

// 查询详细
export function getById(id) {
  return request({
    url: '/estate/communityCareType/' + id,
    method: 'get'
  })
}
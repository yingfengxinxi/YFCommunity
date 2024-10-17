import request from '@/utils/request'

export function getListPage(query) {
  return request({
    url: '/estate/preventType/getListPage',
    method: 'get',
    params: query
  })
}

export function selectList(query) {
  return request({
    url: '/estate/preventType/list',
    method: 'get',
    params: query
  })
}

// 新增
export function insert(data) {
  return request({
    url: '/estate/preventType/insert',
    method: 'post',
    data: data
  })
}

// 修改
export function update(data) {
  return request({
    url: '/estate/preventType/update',
    method: 'put',
    data: data
  })
}

// 删除
export function del(id) {
  return request({
    url: '/estate/preventType/delete?id=' + id,
    method: 'delete'
  })
}

// 查询详细
export function getById(id) {
  return request({
    url: '/estate/preventType/' + id,
    method: 'get'
  })
}
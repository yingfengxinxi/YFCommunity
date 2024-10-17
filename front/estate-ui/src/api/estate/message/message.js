import request from '@/utils/request'

export function getListPage(query) {
  return request({
    url: '/estate/message/list',
    method: 'get',
    params: query
  })
}
// 新增
export function insert(data) {
  return request({
    url: '/estate/message/insert',
    method: 'post',
    data: data
  })
}

// 修改
export function update(data) {
  return request({
    url: '/estate/message/update',
    method: 'put',
    data: data
  })
}

// 删除
export function del(id, top) {
  return request({
    url: '/estate/message/delete?messageId=' + id + "&top=" + top,
    method: 'delete'
  })
}

// 查询详细
export function getById(messageId) {
  return request({
    url: '/estate/message/detail?messageId=' + messageId,
    method: 'get'
  })
}
//置顶
export function updateTop(messageId) {
  return request({
    url: '/estate/message/updateTop?messageId=' + messageId,
    method: 'get'
  })
}

import request from '@/utils/request'

export function getListPage(query) {
  return request({
    url: '/estate/complaintSuggestion/list',
    method: 'get',
    params: query
  })
}


// 修改
export function update(data) {
  return request({
    url: '/estate/complaintSuggestion/update',
    method: 'put',
    data: data
  })
}



// 查询详细
export function getById(id) {
  return request({
    url: '/estate/complaintSuggestion/' + id,
    method: 'get'
  })
}

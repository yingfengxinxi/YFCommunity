import request from '@/utils/request'


// 查询所有项目文档
export function listHeadline(query) {
  return request({
    method: 'get',
    url: '/estate/headline/list',
    params: query
  })
}

// 根据headline_id查询
export function getHeadline(headlineId) {
  return request({
    url: '/estate/headline/' + headlineId,
    method: 'get'
  })
}

// 新增项目文档
export function addHeadline(data) {
  return request({
    url: '/estate/headline',
    method: 'post',
    data: data
  })
}

// 修改项目文档
export function updateHeadline(data) {
  return request({
    url: '/estate/headline',
    method: 'put',
    data: data
  })
}

// 删除项目文档
export function delHeadline(headlineId) {
  return request({
    url: '/estate/headline/' + headlineId,
    method: 'delete'
  })
}


import request from '@/utils/request'

// 查询列表
export function list(query) {
  return request({
    url: '/estate/busQuestionnaire/list',
    method: 'get',
    params: query
  })
}

// 查询详细
export function getById(id) {
  return request({
    url: '/estate/busQuestionnaire/' + id,
    method: 'get'
  })
}

// 新增
export function insert(data) {
  return request({
    url: '/estate/busQuestionnaire/insert',
    method: 'post',
    data: data
  })
}

// 修改
export function update(data) {
  return request({
    url: '/estate/busQuestionnaire/update',
    method: 'put',
    data: data
  })
}

// 删除
export function delTestingStandard(id) {
  return request({
    url: '/estate/busQuestionnaire/delete?id=' + id,
    method: 'delete'
  })
}

// 修改状态
export function updateByIdStatus(releaseStatus, id) {
  return request({
    url: '/estate/busQuestionnaire/updateByIdStatus?releaseStatus=' + releaseStatus + '&id=' + id,
    method: 'get'
  })
}

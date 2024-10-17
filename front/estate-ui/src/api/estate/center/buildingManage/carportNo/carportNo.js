import request from '@/utils/request'

// 分页查询
export function listPage(query) {
  return request({
    url: '/estate/carportNo/list',
    method: 'get',
    params: query
  })
}

// 详情查看
export function getDetail(carportNoId) {
  return request({
    url: '/estate/carportNo/' + carportNoId,
    method: 'get',
  })
}

// 删除
export function deleteById(carportNoIds) {
  return request({
    url: '/estate/carportNo/deleteById?carportNoIds=' + carportNoIds,
    method: 'post'
  })
}


// 新增
export function add(data) {
  return request({
    url: '/estate/carportNo/add',
    method: 'post',
    data: data
  })
}

// 修改
export function update(data) {
  return request({
    url: '/estate/carportNo/update',
    method: 'put',
    data: data
  })
}


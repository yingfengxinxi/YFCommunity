import request from '@/utils/request'
//列表查询
export function getListPage(query) {
  return request({
    url: '/estate/propertyType/list',
    method: 'get',
    params: query
  })
}
//下拉查询
export function assetTypeList(propertyType) {
  return request({
    url: '/estate/propertyType/getByPropertyTypelist?propertyType=' + propertyType,
    method: 'get',
  })
}

// 新增
export function insert(data) {
  return request({
    url: '/estate/propertyType/add',
    method: 'post',
    data: data
  })
}

// 修改
export function update(data) {
  return request({
    url: '/estate/propertyType/update',
    method: 'put',
    data: data
  })
}

// 删除
export function del(id) {
  return request({
    url: '/estate/propertyType/remove?id=' + id,
    method: 'delete'
  })
}

// 查询详细
export function getById(id) {
  return request({
    url: '/estate/propertyType/' + id,
    method: 'get'
  })
}
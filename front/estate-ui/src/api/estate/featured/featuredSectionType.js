import request from '@/utils/request'

export function getListPage(query) {
  return request({
    url: '/estate/featuredSectionType/getListPage',
    method: 'post',
    data: query
  })
}

export function getFeaturedSectionTypeList() {
  return request({
    url: '/estate/featuredSectionType/list',
    method: 'get'
  })
}



// 新增
export function insert(data) {
  return request({
    url: '/estate/featuredSectionType/insert',
    method: 'post',
    data: data
  })
}

// 修改
export function update(data) {
  return request({
    url: '/estate/featuredSectionType/update',
    method: 'put',
    data: data
  })
}

// 删除
export function del(id) {
  return request({
    url: '/estate/featuredSectionType/delete?id=' + id,
    method: 'delete'
  })
}

// 查询详细
export function getById(id) {
  return request({
    url: '/estate/featuredSectionType/' + id,
    method: 'get'
  })
}
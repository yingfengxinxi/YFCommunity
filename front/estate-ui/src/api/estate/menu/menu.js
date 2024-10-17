import request from '@/utils/request'

// 查询菜单列表
export function list(query) {
  return request({
    url: '/estate/estateMobileMenu/list',
    method: 'post',
    data: query
  })
}

// 查询菜单列表
export function listMenu() {
  return request({
    url: '/estate/estateMobileMenu/listMenu',
    method: 'get'
  })
}

// 查询菜单详细
export function getById(estateMenuId) {
  return request({
    url: '/estate/estateMobileMenu/' + estateMenuId,
    method: 'get'
  })
}

// 新增菜单
export function add(data) {
  return request({
    url: '/estate/estateMobileMenu/insert',
    method: 'post',
    data: data
  })
}

// 修改菜单
export function update(data) {
  return request({
    url: '/estate/estateMobileMenu/update',
    method: 'put',
    data: data
  })
}


// 删除菜单
export function deleById(estateMenuId) {
  return request({
    url: '/estate/estateMobileMenu/delete?estateMenuId=' + estateMenuId,
    method: 'delete'
  })
}
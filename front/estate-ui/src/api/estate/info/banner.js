import request from '@/utils/request'


// 查询所有轮播图
export function listBanner(query) {
  return request({
    method: 'get',
    url: '/estate/banner/list',
    params: query
  })
}

// 根据banner_id查询
export function getBanner(bannerId) {
  return request({
    url: '/estate/banner/' + bannerId,
    method: 'get'
  })
}

// 新增轮播图
export function addBanner(data) {
  return request({
    url: '/estate/banner',
    method: 'post',
    data: data
  })
}

// 修改轮播图
export function updateBanner(data) {
  return request({
    url: '/estate/banner',
    method: 'put',
    data: data
  })
}

// 删除轮播图
export function delBanner(bannerId) {
  return request({
    url: '/estate/banner/' + bannerId,
    method: 'delete'
  })
}

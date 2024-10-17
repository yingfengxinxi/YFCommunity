import request from '@/utils/request'

// 查询表格(表扬)
export function getTableListWithPraise(params) {
  return request({
    url: '/estate/complanint/praise',
    method: 'get',
    params: params
  })
}

// 根据ID查询表格(表扬)
export function getTableListById(id) {
  return request({
    url: `/estate/complanint/praiseDetail/${id}`,
    method: 'get'
  })
}

// 新增
export function add(complaintId) {
  return request({
    url: `/estate/complanint/add/` + complaintId,
    method: 'post',
  })
}

// 根据ID查询已公告
export function publicDetail(id) {
  return request({
    url: `/estate/complanint/publicDetail/${id}`,
    method: 'get'
  })
}

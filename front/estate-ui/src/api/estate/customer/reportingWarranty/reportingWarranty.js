import request from '@/utils/request'

// 查询报事保修列表
export function listReportingWarranty(query) {
  return request({
    url: '/estate/reportingWarranty/list',
    method: 'get',
    params: query
  })
}

// 查询报事报修详情
export function getReportingWarrantyDetail(id) {
  return request({
    url: '/estate/reportingWarranty/' + id,
    method: 'get'
  })
}

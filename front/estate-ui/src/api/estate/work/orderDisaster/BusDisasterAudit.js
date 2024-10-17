import request from '@/utils/request'


// 分页查询用户申请
export function listDisasterAudit(query) {
    return request({
      url: '/estate/disasterAudit/list',
      method: 'get',
      params: query
    })
  }

  // 分页查询员工申请
export function ListEmp(query) {
  return request({
    url: '/estate/disasterAudit/listPage',
    method: 'get',
    params: query
  })
}

  // 通过
export function passBusDisaster(data) {
  return request({
    url: '/estate/disasterAudit/pass',
    method: 'post',
    data: data
  })
}

//驳回
export function rejectBusDisaster(data) {
  return request({
    url: '/estate/disasterAudit/reject',
    method: 'post',
    data: data
  })
}

export function queryError(query) {
  return request({
    url: '/estate/disasterAudit/queryError',
    method: 'get',
    params: query
  })
}
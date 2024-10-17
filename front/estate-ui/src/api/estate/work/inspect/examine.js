import request from '@/utils/request'

// 分页查询
export function listPage(query) {
  return request({
    url: '/estate/order/audit/listPage',
    method: 'get',
    params: query
  })
}

/**
 * 详情查看
 * @param {*} query
 * @returns
 */
export function detail(query) {
  return request({
    url: '/estate/order/audit/getDetail',
    method: 'get',
    params: query
  })
}

/**
 * 驳回
 * @param {*} data
 * @returns
 */
export function reject(data) {
  return request({
    url: '/estate/order/audit/reject',
    method: 'post',
    data: data
  })
}

/**
 * 通过
 * @param {*} data
 * @returns
 */
export function adopt(data) {
  return request({
    url: '/estate/order/audit/adopt',
    method: 'post',
    data: data
  })
}

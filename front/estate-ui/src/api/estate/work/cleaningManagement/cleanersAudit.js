import request from '@/utils/request'

// 分页查询
export function listPage(query) {
  return request({
    url: '/estate/cleaningManagement/audit/listPage',
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
    url: '/estate/cleaningManagement/audit/detail',
    method: 'get',
    params: query
  })
}

/**
 * 驳回
 * @param {*} query
 * @returns
 */
export function share(query) {
  return request({
    url: '/estate/cleaningManagement/audit/share',
    method: 'post',
    data: query
  })
}

/**
 * 通过
 * @param {*} query
 * @returns
 */
export function adopt(query) {
  return request({
    url: '/estate/cleaningManagement/audit/adopt',
    method: 'post',
    data: query
  })
}

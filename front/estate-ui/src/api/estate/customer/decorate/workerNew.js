import request from '@/utils/request'

// 查询新增装修工人列表
export function listWorkerNew(query) {
  return request({
    url: '/estate/workerNew/list',
    method: 'get',
    params: query
  })
}

// 查询新增装修工人详情
export function getWorkerNewDetail(workerId) {
  return request({
    url: '/estate/workerNew/' + workerId,
    method: 'get'
  })
}

// 审核通过
export function workerNewAdopt(workerIds) {
  return request({
    url: '/estate/workerNew/' + workerIds,
    method: 'post'
  })
}

// 审核驳回
export function workerNewReject(data) {
  return request({
    url: '/estate/workerNew',
    method: 'post',
    data: data
  })
}

// 审核单个驳回
export function workerNewRejectOne(data) {
  return request({
    url: '/estate/workerNew/one',
    method: 'post',
    data: data
  })
}

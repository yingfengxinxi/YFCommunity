
import request from '@/utils/request'

// 查询维修工种列表
export function listMainTain(query) {
  return request({
    url: '/estate/basic/mainTain/list',
    method: 'get',
    params: query
  })
}

// 查询维修工种详细
export function getMainTain(workId) {
  return request({
    url: '/estate/basic/mainTain/' + workId,
    method: 'get'
  })
}

// 新增维修工种
export function addMainTain(data) {
  return request({
    url: '/estate/basic/mainTain',
    method: 'post',
    data: data
  })
}

// 修改维修工种
export function updateMainTain(data) {
  return request({
    url: '/estate/basic/mainTain',
    method: 'put',
    data: data
  })
}

// 删除维修工种
export function delMainTain(workId) {
  return request({
    url: '/estate/basic/mainTain/' + workId,
    method: 'delete'
  })
}

// 任务状态修改
export function changeWorkStatus(workId, workStatus) {
  const data = {
    workId,
    workStatus
  }
  return request({
    url: '/estate/basic/mainTain/workStatus',
    method: 'put',
    data: data
  })
}

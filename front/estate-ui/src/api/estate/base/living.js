
import request from '@/utils/request'

// 查询居家维修列表
export function listLiving(query) {
  return request({
    url: '/estate/basic/living/list',
    method: 'get',
    params: query
  })
}

// 查询居家维修详细
export function getLiving(repairId) {
  return request({
    url: '/estate/basic/living/' + repairId,
    method: 'get'
  })
}

// 新增居家维修
export function addLiving(data) {
  return request({
    url: '/estate/basic/living',
    method: 'post',
    data: data
  })
}

// 修改居家维修
export function updateLiving(data) {
  return request({
    url: '/estate/basic/living',
    method: 'put',
    data: data
  })
}

// 删除居家维修
export function delLiving(repairId) {
  return request({
    url: '/estate/basic/living/' + repairId,
    method: 'delete'
  })
}

// 任务状态修改
export function changeRepairStatus(repairId, repairStatus) {
  const data = {
    repairId,
    repairStatus
  }
  return request({
    url: '/estate/basic/living/repairStatus',
    method: 'put',
    data: data
  })
}

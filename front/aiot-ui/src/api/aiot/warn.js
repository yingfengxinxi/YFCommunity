import request from '@/utils/request'

// 查询列表数据
export function selectEventList(query) {
  return request({
    url: '/ai_iot/warn/list',
    method: 'get',
    params: query
  })
}

// 根据id查询数据
export function selectEventById(warnId) {
  return request({
    url: '/ai_iot/warn/'+warnId,
    method: 'get'
  })
}

// 删除数据
export function removeEventByIds(ids) {
  return request({
    url: '/ai_iot/warn/'+ids,
    method: 'delete'
  })
}

// 预警事件订阅
export function subscribeEvent(communityId) {
  return request({
    url: '/ai_iot/warn/subscribe/' + communityId,
    method: 'post'
  })
}

// 预警事件取消订阅
export function unsubscribeEvent(communityId) {
  return request({
    url: '/ai_iot/warn/unsubscribe/' + communityId,
    method: 'post'
  })
}

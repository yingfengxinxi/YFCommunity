import request from '@/utils/request'

// 查询列表数据
export function selectEventList(query) {
  return request({
    url: '/govern/warn/list',
    method: 'get',
    params: query
  })
}

// 根据id查询数据
export function selectEventById(warnId) {
  return request({
    url: '/govern/warn/'+warnId,
    method: 'get'
  })
}

// 删除数据
export function removeEventByIds(ids) {
  return request({
    url: '/govern/warn/'+ids,
    method: 'delete'
  })
}


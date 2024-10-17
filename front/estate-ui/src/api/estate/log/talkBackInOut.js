import request from '@/utils/request'

// 查出单元门禁出入记录列表
export function listTalkBackInOut(query) {
  return request({
    url: '/estate/visualSecord/list',
    method: 'get',
    params: query
  })
}

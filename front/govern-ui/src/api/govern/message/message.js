import request from '@/utils/request'

// 查询列表
export function messageList(query) {
  return request({
    url: '/govern/message/list',
    method: 'get',
    params: query
  })
}

// 新增
export function addMessage(data) {
  return request({
    url: '/govern/message/add',
    method: 'post',
    data: data
  })
}

// 删除
export function delMessage(messageId) {
  return request({
    url: '/govern/message/remove/' + messageId,
    method: 'delete'
  })
}

// 置顶 /message/topMessage
export function topMessage(data) {
  return request({
    url: '/govern/message/topMessage',
    method: 'post',
    data: data
  })
}

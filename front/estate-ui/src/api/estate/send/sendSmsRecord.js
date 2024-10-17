import request from '@/utils/request'

// 查询业主列表
export function getSendSmsOwnerList(query) {
  return request({
    url: '/estate/sendSmsRecord/getSendSmsOwnerList',
    method: 'post',
    data: query
  })
}

// 查询物业列表
export function getSendSmsEstateList(query) {
  return request({
    url: '/estate/sendSmsRecord/getSendSmsEstateList',
    method: 'post',
    data: query
  })
}



// 批量发送短信
export function sendSms(ids, type) {
  return request({
    url: '/estate/sendSmsRecord/sendSms?ids=' + ids + "&type=" + type,
    method: 'get'
  })
}

import request from '@/utils/request'

// 分页查询
export function listHomLed(query) {
  return request({
    url: '/estate/homLed/list',
    method: 'get',
    params: query
  })
}
// 查询详细
export function getHomLedInfo(orderId) {
  return request({
    url: '/estate/homLed/getHomLed/' + orderId,
    method: 'get'
  })
}
// 协助人员列表
export function getHomStaList(orderId) {
  return request({
    url: '/estate/homLed/staList/' + orderId,
    method: 'get'
  })
}

// 图片列表
export function getOrderImgList(orderId) {
  return request({
    url: '/estate/homLed/imgList/' + orderId,
    method: 'get'
  })
}


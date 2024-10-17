import request from '@/utils/request'

//新增采购信息
export function addBuyer(data) {
  return request({
    url: '/estate/material',
    method: 'post',
    params: data
  })
}

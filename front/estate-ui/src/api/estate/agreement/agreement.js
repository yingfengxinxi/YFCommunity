import request from '@/utils/request'

//协议
export function getAgreement() {
  return request({
    url: '/estate/agreementApp/getAgreement',
    method: 'get'
  })
}


// 新增
export function saveAgreement(data) {
  return request({
    url: '/estate/agreementApp/saveAgreement',
    method: 'post',
    data: data
  })
}
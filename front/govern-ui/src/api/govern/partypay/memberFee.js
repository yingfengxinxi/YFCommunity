import request from '@/utils/request'

// 党费设置
export function partyFeeEdit(data) {
  return request({
    url: '/govern/memberFee/edit',
    method: 'put',
    data: data
  })
}

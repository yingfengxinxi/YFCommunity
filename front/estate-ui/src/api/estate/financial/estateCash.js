import request from '@/utils/request'
// 账单列表
export function propertyLogList(query) {
  return request({
    url: '/estate/propertyLog/list',
    method: 'post',
    data: query
  })
}

// 批量发送短信
export function sendSms(ids, type) {
  return request({
    url: '/estate/propertyLog/sendSms?ids=' + ids + "&type=" + type,
    method: 'get'
  })
}



// 批量发送账单
export function bachUpdatePayStatus(ids) {
  return request({
    url: '/estate/propertyLog/bachUpdatePayStatus?logIds=' + ids,
    method: 'get'
  })
}

// 根据房屋id查询物业费
export function getByRoomIdAmount(roomId, isUnpaidPropertyFee) {
  return request({
    url: '/estate/propertyLog/getByRoomIdAmount?roomId=' + roomId + "&isUnpaidPropertyFee=" + isUnpaidPropertyFee,
    method: 'get'
  })
}

// 修改优惠金额
export function updateByLogIdAmount(discountAmount, paidAmount, logId) {
  return request({
    url: '/estate/propertyLog/updateByLogIdAmount?discountAmount=' + discountAmount + "&paidAmount=" + paidAmount + "&logId=" + logId,
    method: 'get'
  })
}


// 第二个LIST
export function ownerList(query) {
  return request({
    url: '/estate/propertyLog/ownerList',
    method: 'get',
    params: query
  })
}
// 第三个LIST
export function payList(query) {
  return request({
    url: '/estate/propertyLog/payList',
    method: 'get',
    params: query
  })
}

export function getInfoById(query) {
  return request({
    url: '/estate/propertyLog/getInfoById',
    method: 'post',
    data: query
  })
}


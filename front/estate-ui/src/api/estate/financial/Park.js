import request from '@/utils/request'

export function ParkPaylist(query) {
  return request({
    url: '/estate/park/parkpaylist',
    method: 'get',
    params: query
  })
}

export function parkdurationlist(communityId) {
  return request({
    url: '/estate/park/parkdurationlist/' + communityId,
    method: 'get',
  })
}


export function AddParkPay(data) {
  return request({
    url: '/estate/park/addparkpay',
    method: 'post',
    data: data
  })
}


export function UpdateParkPay(data) {
  return request({
    url: '/estate/park/updateparkpay',
    method: 'put',
    data: data
  })
}

export function addParkDuration(data) {
  return request({
    url: '/estate/park/addParkDuration',
    method: 'post',
    data: data
  })
}

export function delPd(durationId) {
  return request({
    url: '/estate/park/delPd/' + durationId,
    method: 'delete',
  })
}


export function xgfy(data) {
  return request({
    url: '/estate/park/xgfy',
    method: 'put',
    data: data
  })
}



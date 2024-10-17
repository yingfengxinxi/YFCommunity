import request from '@/utils/request'


// 分页查询
export function listDisasterLedger(query) {
  return request({
    url: '/estate/disasterLedger/list',
    method: 'get',
    params: query
  })
}
//删除
export function deleteBusDisaster(disasterId){
  return request({
    url: '/estate/disasterLedger/remove/'+disasterId,
    method: 'delete',
  })
}

// 新增
export function addBusDisaster(data) {
  return request({
    url: '/estate/disasterLedger/add',
    method: 'post',
    data: data
  })
}

// 修改
export function updateBusDisaster(data) {
  return request({
    url: '/estate/disasterLedger/update',
    method: 'put',
    data: data
  })
}

// 分页查询
export function getOrderType() {
  return request({
    url: '/estate/disasterLedger/getOrderType',
    method: 'get'
  })
}

import request from '@/utils/request'

// 查询场馆活动列表
export function listAppoint(query) {
  return request({
    url: '/estate/appoint/list',
    method: 'get',
    params: query
  })
}

// 查询场馆活动图形界面
export function tlist(data) {
  return request({
    url: '/estate/appoint/tlist',
    method: 'post',
    data: data
  })
}




// 查询场馆活动详细
export function getAppoint(appointId) {
  return request({
    url: '/estate/appoint/' + appointId,
    method: 'get'
  })
}

// 查询场馆活动详细
export function getAppoint1(appointId) {
  return request({
    url: '/estate/appoint/getInfo/' + appointId,
    method: 'get'
  })
}

// 新增场馆活动
export function addAppoint(data) {
  return request({
    url: '/estate/appoint',
    method: 'post',
    data: data
  })
}

// 修改场馆活动
export function updateAppoint(data) {
  return request({
    url: '/estate/appoint',
    method: 'put',
    data: data
  })
}

// 删除场馆活动
export function delAppoint(appointId) {
  return request({
    url: '/estate/appoint/' + appointId,
    method: 'delete'
  })
}

//获取场馆
export function getvenue(communityId) {
  return request({
    url: '/estate/appoint/getvenue/' + communityId,
    method: 'get'
  })
}
//获取场馆 无参
export function getvenue1() {
  return request({
    url: '/estate/appoint/getvenue1/',
    method: 'get'
  })
}


//获取活动类型
export function getvenueact(communityId) {
  return request({
    url: '/estate/appoint/getvenueact/' + communityId,
    method: 'get'
  })
}


// 终止
export function zz(data) {
  return request({
    url: '/estate/appoint/zz',
    method: 'post',
    data: data
  })
}

//结账
export function jz(data) {
  return request({
    url: '/estate/appoint/jz',
    method: 'post',
    data: data
  })
}

//结账回显
export function jzc(appointId){
  return request({
    url: '/estate/appoint/jzc/'+appointId,
    method:'get'
  })
}

//查看场馆预约信息
export function getvenueByvenueId(venueId){
  return request({
    url: '/estate/appoint/getvenueByvenueId/'+venueId,
    method:'get'
  })
}


//场馆终止申请列表
export function venueaz(query) {
  return request({
    url: '/estate/appoint/venueaz',
    method: 'get',
    params: query
  })
}

//场馆终止申请列表Id
export function venueazById(appointId){
  return request({
    url: '/estate/appoint/venueazById/'+appointId,
    method:'get'
  })
}

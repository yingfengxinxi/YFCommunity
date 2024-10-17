import request from '@/utils/request'
//查询
export function query(query){
  return request({
    url:'/govern/ownerCommittee/queryOwner',
    method:'get',
    params:query
  })
}

export function queryList(query){
  return request({
    url:'/govern/ownerCommittee/queryList',
    method:'get',
    params:query
  })
}
//查询  房间下的人员
export function queryByRoomId(query){
  return request({
    url:'/govern/ownerCommittee/queryByRoomId',
    method:'get',
    params:query
  })
}
//查询 业主房产
export function queryRoomByOwnerId(query){
  return request({
    url:'/govern/ownerCommittee/queryRoomByOwnerId',
    method:'get',
    params:query
  })
}
//证件类型
export function queryDict(){
  return request({
    url:'/govern/ownerCommittee/queryDict',
    method:'get',
  })
}
//绑定
export function bind(data){
  return request({
    url:'/govern/ownerCommittee/bind',
    method:'post',
    data:data
  })
}

//多个删除
export function deletedOwner(ownerId){
  return request({
    url:'/govern/ownerCommittee/remove/'+ownerId,
    method:'delete'
  })
}
// 查询业主委员会信息列表
export function listCommittee(query) {
  return request({
    url: '/govern/ownerCommittee/list',
    method: 'get',
    params: query
  })
}

// 查询业主委员会信息
export function tableCommittee(committeeId) {
  return request({
    url: '/govern/ownerCommittee/queryCommit/' + committeeId,
    method: 'get'
  })
}

// 新增业主委员会信息
export function addCommittee(params) {
  return request({
    url: '/govern/ownerCommittee/add',
    method: 'post',
    data: params
  })
}
// 修改业主委员会信息
export function updateCommittee(data) {
  return request({
    url: '/govern/ownerCommittee/edit',
    method: 'put',
    data: data
  })
}
// 删除业主委员会信息
export function removeCommittee(committeeId) {
  return request({
    url: '/govern/ownerCommittee/removeCommittee/' + committeeId,
    method: 'delete'
  })
}

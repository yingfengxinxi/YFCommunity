import request from '@/utils/request'


export function getbuildingList(data) {
  return request({
    url: '/estate/oldman/oldfile/getbuilding',
    method: 'post',
    data: data
  })
}

export function getunit(data) {
  return request({
    url: '/estate/oldman/oldfile/getunit',
    method: 'post',
    data: data
  })
}


export function getroom(data) {
  return request({
    url: '/estate/oldman/oldfile/getroom',
    method: 'post',
    data: data
  })
}

//获取老人信息

export function getoldman(data) {
  return request({
    url: '/estate/oldman/oldfile/getoldman',
    method: 'post',
    data: data
  })
}

//新增
export function addoldfile(data) {
  return request({
    url: '/estate/oldman/oldfile/add',
    method: 'post',
    data: data
  })
}


//查询
export function getList(query) {
  return request({
    url: '/estate/oldman/oldfile/list',
    method: 'get',
    params: query
  })
}


//查看老人信息
export function getoldmsg(olderId) {
  return request({
    url: '/estate/oldman/oldfile/getoldmsg/'+olderId,
    method: 'get',
  })
}

//审核通过
export function shtg(olderId) {
  return request({
    url: '/estate/oldman/oldfile/getoldmsg/auditstatus/'+olderId,
    method: 'put',
  })
}


//驳回
export function shbh(olderId,rejectReason) {
  return request({
    url: '/estate/oldman/oldfile/getoldmsg/noauditstatus/'+olderId+"/"+rejectReason,
    method: 'put',
  })
}

export function del(olderId) {
  return request({
    url: '/estate/oldman/oldfile/del/'+olderId,
    method: 'delete',
  })
}




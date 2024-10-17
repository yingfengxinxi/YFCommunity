import request from '@/utils/request'

//查询警民互动列表
export function getList(query) {
  return request({
    url: '/govern/bulletin/list',
    method: 'get',
    params: query
  })
}


//添加警民互动
export function addBulletin(data) {
  return request({
    url: '/govern/bulletin',
    method: 'post',
    data: data
  })
}

//警民互动详情
export function getBulletinDetail(query) {
  return request({
    url: '/govern/bulletin/'+query,
    method: 'get',
  })
}

//修改警民互动
export function updateBulletin(data) {
  return request({
    url: '/govern/bulletin',
    method: 'put',
    data: data
  })
}

//删除警民互动
export function delBulletin(query) {
  return request({
    url: '/govern/bulletin/'+query,
    method: 'delete',
  })
}

//修改成归档
export function updateArchive(query) {
  return request({
    url: '/govern/bulletin/updateArchive/'+query,
    method: 'get',
  })
}

//获取归档详情
export function getArchiveDetail(query) {
  return request({
    url: '/govern/bulletin/getArchiveDetail/'+query,
    method: 'get',
  })
}

//文件上传
export function upload(data){
  return request({
    url:'/file/upload',
    method:'post',
    data:data
  })
}


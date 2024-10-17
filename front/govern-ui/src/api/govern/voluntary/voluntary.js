import request from '@/utils/request'

//查询列表
export function searchList(query) {
  return request({
    url: '/govern/voluntary/searchList',
    method: 'get',
    params: query
  })
}
//查询列表人员
export function searchListUser(query) {
  return request({
    url: '/govern/voluntary/searchListUser',
    method: 'get',
    params: query
  })
}
// 新增
export function saveVoluntary(data) {
  return request({
    url: '/govern/voluntary/saveVoluntary',
    method: 'post',
    data:data
  })
}

// 修改
export function updateVoluntary(data) {

  return request({
    url: '/govern/voluntary/updateVoluntary',
    method: 'post',
    data: data
  })
}

// 删除
export function removeVoluntary(data) {
  return request({
    url: '/govern/voluntary/removeVoluntary',
    method: 'post',
    data: data
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

import request from '@/utils/request'

//查询列表
export function getList(query) {
  return request({
    url: '/govern/prevent/getList',
    method: 'get',
    params: query
  })
}

//添加防控信息
export function addPrevent(data) {
  return request({
    url: '/govern/prevent',
    method: 'post',
    data: data
  })
}

//防空详情
export function getPreventDetail(query) {
  return request({
    url: '/govern/prevent/getPreventDetail/'+query,
    method: 'get',
  })
}

//修改防控信息
export function updatePrevent(data) {
  return request({
    url: '/govern/prevent',
    method: 'put',
    data: data
  })
}

//删除防控信息
export function delPrevent(query) {
  return request({
    url: '/govern/prevent/delPrevent/'+query,
    method: 'get',
  })
}


//获取当前登录人所在物业下的所有小区集合
export function getCommuntiyList() {
  return request({
    url: '/govern/community/current',
    method: 'get'
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


import request from '@/utils/request'
import {getToken} from "@/utils/auth";
import {parseStrEmpty} from "@/utils/global";

// 查询信息列表查询
export function getSocialList(query) {
  return request({
    url: '/govern/exposal/messageList',
    method: 'get',
    params: query
  })
}

// 查询系统将信息列表
export function getSystemList(query) {
  return request({
    url: '/govern/exposal/systemList',
    method: 'get',
    params: query
  })
}




//新增系统信息
export function addSystemSocial(data) {
  return request({
    url: '/govern/exposal',
    method: 'post',
    data: data
  })
}



//删除信息管理
export function delMessageSocial(socialId) {
  return request({
    url: '/govern/exposal/delMessage/' + socialId,
    method: 'delete'
  })
}

//删除系统信息管理
export function delSystemSocial(id) {
  return request({
    url: '/govern/exposal/delSystem/' + id,
    method: 'delete'
  })
}


// 上传图片
export function uploadPicture(data) {
  return request({
    headers: {
      Authorization: "Bearer " + getToken(),
    },
    url: '/govern/exposal/uploadPicture',
    method: 'post',
    data: data
  })
}

//修改
export function updateSysSocial(data) {
  return request({
    url: '/govern/exposal',
    method: 'put',
    data: data
  })
}

//根据id查询详情
export function querySocialById(socialId) {
  return request({
    url: '/govern/exposal/' + parseStrEmpty(socialId),
    method: 'get'
  })
}

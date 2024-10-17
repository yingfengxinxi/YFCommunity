import request from '@/utils/request'
import {getToken} from "@/utils/auth";
import {parseStrEmpty} from "@/utils/global";

// 查询信息列表查询
export function getSocialList(query) {
  return request({
    url: '/govern/social/messageList',
    method: 'get',
    params: query
  })
}

// 查询系统将信息列表
export function getSystemList(query) {
  return request({
    url: '/govern/social/systemList',
    method: 'get',
    params: query
  })
}




//新增系统信息
export function addSystemSocial(data) {
  return request({
    url: '/govern/social',
    method: 'post',
    data: data
  })
}



//删除信息管理
export function delMessageSocial(socialId) {
  return request({
    url: '/govern/social/delMessage/' + socialId,
    method: 'delete'
  })
}

//删除系统信息管理
export function delSystemSocial(id) {
  return request({
    url: '/govern/social/delSystem/' + id,
    method: 'delete'
  })
}


// 上传图片
export function uploadPicture(data) {
  return request({
    headers: {
      Authorization: "Bearer " + getToken(),
    },
    url: '/govern/social/uploadPicture',
    method: 'post',
    data: data
  })
}

//修改
export function updateSysSocial(data) {
  return request({
    url: '/govern/social',
    method: 'put',
    data: data
  })
}

//根据id查询详情
export function querySocialById(socialId) {
  return request({
    url: '/govern/social/' + parseStrEmpty(socialId),
    method: 'get'
  })
}

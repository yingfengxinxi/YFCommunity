import request from '@/utils/request'
import {getToken} from "@/utils/auth";
import {parseStrEmpty} from "@/utils/global";

// 查询列表
export function getStatuteList(query) {
  return request({
    url: '/govern/statute/list',
    method: 'get',
    params: query
  })
}


// 上传图片
export function uploadPicture(data) {
  return request({
    headers: {
      Authorization: "Bearer " + getToken(),
    },
    url: '/govern/statute/uploadPicture',
    method: 'post',
    data: data
  })
}

//新增
export function addStatute(data) {
  return request({
    url: '/govern/statute',
    method: 'post',
    data: data
  })
}


//修改
export function updateStatute(data) {
  return request({
    url: '/govern/statute',
    method: 'put',
    data: data
  })
}

//根据id查询详情
export function getStatuteById(statuteId) {
  return request({
    url: '/govern/statute/' + parseStrEmpty(statuteId),
    method: 'get'
  })
}

//删除
export function delStatute(statuteId) {
  return request({
    url: '/govern/statute/' + statuteId,
    method: 'delete'
  })
}

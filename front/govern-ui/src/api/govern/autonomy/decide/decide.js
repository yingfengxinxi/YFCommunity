import request from '@/utils/request'
import {getToken} from "@/utils/auth";
import {parseStrEmpty} from "@/utils/global";

// 查询列表查询
export function getDecideList(query) {
  return request({
    url: '/govern/decide/list',
    method: 'get',
    params: query
  })
}


//新增
export function addDecide(data) {
  return request({
    url: '/govern/decide',
    method: 'post',
    data: data
  })
}


//修改
export function updateDecide(data) {
  return request({
    url: '/govern/decide',
    method: 'put',
    data: data
  })
}

//根据id查询详情
export function getDecideById(decideId) {
  return request({
    url: '/govern/decide/' + parseStrEmpty(decideId),
    method: 'get'
  })
}


//删除
export function delDecide(decideId) {
  return request({
    url: '/govern/decide/' + decideId,
    method: 'delete'
  })
}



//查询选项详情， 根据表决表id查询
export function getDecideItemById(statuteId) {
  return request({
    url: '/govern/decide/getDecideItem/' + parseStrEmpty(statuteId),
    method: 'get'
  })
}



//修改选项表中的投票信息
export function updateDecideItemById(itemId) {
  return request({
    url: '/govern/decide/updateDecideItem',
    method: 'put',
    data: itemId
  })
}

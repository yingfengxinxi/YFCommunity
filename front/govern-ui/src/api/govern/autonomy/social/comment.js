import request from '@/utils/request'
import {getToken} from "@/utils/auth";
import {parseStrEmpty} from "@/utils/global";



//通过社交id查询点赞数量
export function queryLikeAmount(socialId) {
  return request({
    url: '/govern/comment/' + parseStrEmpty(socialId),
    method: 'get'
  })
}

//用户点赞后， 新增点赞记录表
export function addLike(data) {
  return request({
    url: '/govern/comment',
    method: 'post',
    data: data
  })
}


export function addComment(data) {
  return request({
    url: '/govern/comment/addComment',
    method: 'post',
    data: data
  })
}



// 查询系统将信息列表
export function getCommentList(socialId) {
  return request({
    url: '/govern/comment/commentList/'+socialId,
    method: 'get'
  })
}



//撤回功能
export function revokeMsg(data) {
  return request({
    url: '/govern/comment/revokeMsg',
    method: 'put',
    data: data
  })
}





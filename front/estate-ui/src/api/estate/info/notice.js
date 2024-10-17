import request from '@/utils/request'


// 查询所有项目文档
export function listNotice(query) {
  return request({
    method: 'post',
    url: '/estate/notice/list',
    data: query
  })
}

// 根据notice_id查询
export function getNotice(noticeId) {
  return request({
    url: '/estate/notice/' + noticeId,
    method: 'get'
  })
}

// 新增项目文档
export function addNotice(data) {
  return request({
    url: '/estate/notice',
    method: 'post',
    data: data
  })
}

//撤回公示
export function backNotice(noticeId) {
  return request({
    url: '/estate/notice/back/' + noticeId,
    method: 'get',
  })
}

//发布公示
export function upNotice(noticeId) {
  return request({
    url: '/estate/notice/up/' + noticeId,
    method: 'get',
  })
}

// 修改项目文档
export function updateNotice(data) {
  return request({
    url: '/estate/notice',
    method: 'put',
    data: data
  })
}

// 删除项目文档
export function delNotice(noticeId) {
  return request({
    url: '/estate/notice/' + noticeId,
    method: 'delete'
  })
}

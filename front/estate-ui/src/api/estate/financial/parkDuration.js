import request from '@/utils/request'

//列表
export function list(query) {
  return request({
    url: '/estate/parkDuration/list',
    method: 'get',
    params: query
  })
}
//详情
export function get(id) {
  return request({
    url: '/estate/parkDuration/' + id,
    method: 'get',
  })
}

//添加
export function insert(data) {
  return request({
    url: '/estate/parkDuration/insert',
    method: 'post',
    data: data
  })
}
//修改
export function update(data) {
  return request({
    url: '/estate/parkDuration/update',
    method: 'put',
    data: data
  })
}

//删除
export function del(id) {
  return request({
    url: '/estate/parkDuration/delete?id=' + id,
    method: 'delete'
  })
}


//查询是否配置车位费缴费规则
export function getCommunityIdParkDurationCount(communityId) {
  return request({
    url: '/estate/parkDuration/getCommunityIdParkDurationCount?communityId=' + communityId,
    method: 'get'
  })
}



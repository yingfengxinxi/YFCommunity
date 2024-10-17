// 查询业主委员会信息列表
import request from "@/utils/request";


export function listAllot(query) {
  return request({
    url: '/estate/work/distribution/list',
    method: 'get',
    params: query
  })
}

// 新增分配设置
export function addAllot(data) {
  return request({
    url: '/estate/work/distribution/add',
    method: 'post',
    data: data
  })
}


// 修改分配设置
export function updateAllot(data) {
  return request({
    url: '/estate/work/distribution',
    method: 'put',
    data: data
  })
}


//查询是否存在
export function getAllot(data) {
  return request({
    url: '/estate/work/distribution/allot',
    method: 'post',
    data: data
  })
}


export function getAll(allotId) {
  return request({
    url: '/estate/work/distribution/'+allotId,
    method: 'get',
  })
}


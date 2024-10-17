import request from '@/utils/request'

// 分页查询
export function getpointList(query) {
  return request({
    url: '/estate/point/list',
    method: 'get',
    params: query
  })
}

// 详情查询
export function getPoint(piontId) {
  return request({
    url: '/estate/point/'+piontId,
    method: 'get',
  })
}


//修改状态
export function updateStatus(query) {
  return request({
    url: '/estate/point/status',
    method: 'post',
    data:query
  })
}

// 批量删除
export function removes(query) {
  return request({
    url: '/estate/point/removes',
    method: 'post',
    data: query
  })
}

// 逻辑删除
export function remove(query) {
  return request({
    url: '/estate/point/remove',
    method: 'post',
    params: query
  })
}

// 新增
export function pointAdd(query) {
  return request({
    url: '/estate/point/addUpdate',
    method: 'post',
    data: query
  })
}





import request from '@/utils/request'

// 查询列表
export function getClassifyList(query) {
  return request({
    url: '/estate/classify/list',
    method: 'get',
    params: query
  })
}

// 查询列表
export function getAllClassifyList() {
  return request({
    url: '/estate/classify/listAll',
    method: 'get',
  })
}

// 新增分类信息
export function addClassify(data) {
  return request({
    url: '/estate/classify/insert',
    method: 'post',
    data: data
  })
}

// 删除
export function delClassify(deptId) {
  return request({
    url: '/estate/classify/delete/' + deptId,
    method: 'delete'
  })
}

// 查询详细
export function getInfo(classifyId) {
  return request({
    url: '/estate/classify/getInfoById/' + classifyId,
    method: 'get'
  })
}

// 修改
export function updateClassify(data) {
  return request({
    url: '/estate/classify/update',
    method: 'put',
    data: data
  })
}

//修改状态
export function changeUserStatus(classifyId, classifyStatus) {
  const data = {
    classifyId,
    classifyStatus
  }
  return request({
    url: '/estate/classify/updateClassifyStatus',
    method: 'put',
    data: data
  })
}

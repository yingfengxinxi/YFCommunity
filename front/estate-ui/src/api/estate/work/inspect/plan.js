import request from '@/utils/request'

// 分页查询
export function listPage(query) {
  return request({
    url: '/estate/inspect/plan/listPage',
    method: 'get',
    params: query
  })
}

// 新增或修改
export function saveOrUpdate(query) {
  return request({
    url: '/estate/inspect/plan/saveOrUpdate',
    method: 'post',
    data: query
  })
}

// 修改页详情查看
export function updateDetail(query) {
  return request({
    url: '/estate/inspect/plan/updateDetail',
    method: 'get',
    params: query
  })
}


// 详情内容展示
export function detail(query) {
  return request({
    url: '/estate/inspect/plan/detail',
    method: 'get',
    params: query
  })
}

// 详情内容列表
export function detailList(query) {
  return request({
    url: '/estate/inspect/plan/detailList',
    method: 'get',
    params: query
  })
}

// 详情 工单生成历史列表
export function deatilListTwo(query) {
  return request({
    url: '/estate/inspect/plan/generateList',
    method: 'get',
    params: query
  })
}

// 批量删除
export function removes(query) {
  return request({
    url: '/estate/inspect/plan/removes',
    method: 'post',
    data: query
  })
}

// 修改车位状态
export function changeStatus(query) {
  return request({
    url: '/estate/inspect/plan/changeStatus',
    method: 'post',
    params: query
  })
}


// 查找巡查类型
export function getTourNameList(query) {
  return request({
    url: '/estate/inspect/plan/getTourNameList',
    method: 'get',
    params: query
  })
}

export function getDeviceNoList(communityId) {
  return request({
    url: '/estate/inspect/plan/deviceNo/'+communityId,
    method: 'get',
  })
}

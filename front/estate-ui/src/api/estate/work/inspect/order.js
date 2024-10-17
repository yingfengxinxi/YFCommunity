import request from '@/utils/request'

// 分页查询
export function listPage(query) {
  return request({
    url: '/estate/inspect/ins/listPage',
    method: 'get',
    params: query
  })
}

// 获取设备类型
export function getDeviceTypeList() {
  return request({
    url: '/estate/inspect/ins/deviceType',
    method: 'get',
  })
}

// 获取设备编号
export function getDeviceNoList(communityId) {
  return request({
    url: '/estate/inspect/plan/deviceNo/'+communityId,
    method: 'get',
  })
}

// 新增或修改
export function saveOrUpdate(query) {
  return request({
    url: '/estate/inspect/ins/saveOrUpdate',
    method: 'post',
    data: query
  })
}

// 详情内容列表
export function detailList(query) {
  return request({
    url: '/estate/inspect/ins/detailList',
    method: 'get',
    params: query
  })
}

// 详情 工单执行人信息
export function deatilListTwo(query) {
  return request({
    url: '/estate/work/deviceOrder/assistList',
    method: 'get',
    params: query
  })
}

// 详情 执行详情
export function deatilListThree(query) {
  return request({
    // url: '/estate/inspect/ins/deatilListThree',
    url: '/estate/disasterOrder/detailRecordGeneral',
    method: 'get',
    params: query
  })
}

/**
 * 详情查看
 * @param {*} query
 * @returns
 */
export function updateDetail(query) {
  return request({
    url: '/estate/inspect/ins/updateDetail',
    method: 'get',
    params: query
  })
}

/**
 * 删除
 * @param {*} query
 * @returns
 */
export function removes(query) {
  return request({
    url: '/estate/inspect/ins/removes',
    method: 'post',
    data: query
  })
}

/**
 * 获取转让人列表
 * @param {*} query
 * @returns
 */
export function getShareName(query) {
  return request({
    url: '/estate/staff/listLeisureBusyStaffs',
    method: 'get',
    params: query
  })
}

/**
 * 分配弹窗确定
 * @param {*} query
 * @returns
 */
export function shareUpdate(query) {
  return request({
    url: '/estate/inspect/ins/shareUpdate',
    method: 'post',
    data: query
  })
}

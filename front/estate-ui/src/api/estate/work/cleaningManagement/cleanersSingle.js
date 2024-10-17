import request from '@/utils/request'

// 分页查询
export function listPage(query) {
  return request({
    url: '/estate/cleaningManagement/HealthWork/list',
    method: 'get',
    params: query
  })
}

// 新增或修改
export function saveOrUpdate(data) {
  return request({
    url: '/estate/cleaningManagement/HealthWork/updataAndinsert',
    method: 'put',
    data:data
  })
}

// 修改页详情查看
export function updateDetail(cleanId) {
  return request({
    url: '/estate/cleaningManagement/HealthWork/One/'+cleanId,
    method: 'get'
  })
}

//执行流程列表
export function getorderRecordList(cleanId) {
  return request({
    url: '/estate/cleaningManagement/HealthWork/OrderRecordList/'+cleanId,
    method: 'get'
  })
}


// 工单执行人列表
export function getDetailList(cleanId) {
  return request({
    url: '/estate/cleaningManagement/HealthWork/executorList/'+cleanId,
    method: 'get'
  })
}

// 批量删除
export function removes(cleanIds) {
  return request({
    url: '/estate/cleaningManagement/HealthWork/removes/'+cleanIds,
    method: 'delete'
  })
}

// 查询可以用的员工
export function getsafftName(query) {
  return request({
    url: '/estate/cleaningManagement/HealthWork/getsafftName',
    method: 'get',
    params:query
  })
}

/**
 * 分配弹窗确定
 * @param {*} query
 * @returns
 */
export function shareUpdate(query) {
  return request({
    url: '/estate/cleaningManagement/HealthWork/UpdatestaffIdAndexecuteId',
    method: 'put',
    params: query
  })
}




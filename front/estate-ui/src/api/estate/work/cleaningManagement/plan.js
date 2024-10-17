import request from '@/utils/request'

// 分页查询
export function listPage(query) {
  return request({
    url: '/estate/cleaningManagement/hygiene/list',
    method: 'get',
    params: query
  })
}

// 新增或修改
export function saveOrUpdate(data) {
  return request({
    url: '/estate/cleaningManagement/hygiene/updataAndinsert',
    method: 'put',
    data:data
  })
}

// 批量删除
export function removes(planIds) {
  return request({
    url: '/estate/cleaningManagement/hygiene/'+planIds,
    method: 'delete'
  })
}

// 修改状态
export function changeCarportStatus(planId) {
  return request({
    url: '/estate/cleaningManagement/hygiene/changeStatus/'+planId,
    method: 'get'
  })
}


// 修改页详情查看
export function updateDetail(planId) {
  return request({
    url: '/estate/cleaningManagement/hygiene/One/'+planId,
    method: 'get'
  })
}


// 工单生成详细
export function detailList(query) {
  return request({
    url: '/estate/cleaningManagement/HealthWork/ByPlanId',
    method: 'get',
    params: query
  })
}




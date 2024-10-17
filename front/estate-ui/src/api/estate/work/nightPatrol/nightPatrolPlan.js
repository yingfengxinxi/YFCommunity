import request from '@/utils/request'

// 分页查询
export function getListPage(query) {
  return request({
    url: '/estate/work/nightPatrolPlan/listPage',
    method: 'get',
    params: query
  })
}

// 查询巡查路线列表
export function selectRouteList(communityId) {
  return request({
    url: '/estate/route/listRoutes/' + communityId,
    method: 'get'
  })
}

// 新增巡查计划
export function add(data) {
  return request({
    url: '/estate/work/nightPatrolPlan',
    method: 'post',
    data: data
  })
}

// 修改巡查计划
export function edit(data) {
  return request({
    url: '/estate/work/nightPatrolPlan',
    method: 'put',
    data: data
  })
}

// 修改巡查计划生成订单状态
export function updatePatrolPlanOrder(data) {
  return request({
    url: '/estate/work/nightPatrolPlan/updatePatrolPlanOrder',
    method: 'put',
    data: data
  })
}

// 巡查计划编辑详情
export function getInfo(planId) {
  return request({
    url: '/estate/work/nightPatrolPlan/' + planId,
    method: 'get'
  })
}

// 巡查计划查看详情
export function getDetail(planId) {
  return request({
    url: '/estate/work/nightPatrolPlan/detail/' + planId,
    method: 'get'
  })
}

// 删除巡查计划
export function remove(planId) {
  return request({
    url: '/estate/work/nightPatrolPlan/' + planId,
    method: 'delete'
  })
}

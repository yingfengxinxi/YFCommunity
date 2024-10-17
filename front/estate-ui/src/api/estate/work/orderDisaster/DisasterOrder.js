import request from '@/utils/request'


// 分页查询
export function listDisasterOrder(query) {
  return request({
    url: '/estate/disasterOrder/list',
    method: 'get',
    params: query
  })
}

// 详情
export function detailList(query) {
  return request({
    url: '/estate/disasterOrder/listInfo',
    method: 'get',
    params: query
  })
}

// 新增或修改
export function saveOrUpdate(query) {
  return request({
    url: '/estate/disasterOrder/updateOrAdd',
    method: 'post',
    data: query
  })
}

//分配
export function updateBusDisaster(communityId) {
  return request({
    url: '/estate/disasterOrder/nameList/' + communityId,
    method: 'get',
  })
}

/**
 * 分配弹窗确定
 */
export function shareUpdate(query) {
  return request({
    url: '/estate/disasterOrder/shareUpdate',
    method: 'post',
    data: query
  })
}

//逻辑删除
export function deleteDisasterId(orderId) {
  return request({
    url: '/estate/disasterOrder/deleteByOrder/' + orderId,
    method: 'delete',
  })
}

// 详情
export function recordInfo(orderId) {
  return request({
    url: '/estate/disasterOrder/deatilRecord/' + orderId,
    method: 'get',
  })
}

// 执行情况详情信息 通用查询
export function detailRecordGeneral(query) {
  return request({
    url: '/estate/disasterOrder/detailRecordGeneral',
    method: 'get',
    params: query
  })
}

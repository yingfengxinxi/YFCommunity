
import request from '@/utils/request'

// 查询员工提交审核列表
export function listStaff(query) {
  return request({
    url: '/estate/staffProperty/list',
    method: 'get',
    params: query
  })
}
// 审核
export function updateStatusPass(id) {
  return request({
    url: '/estate/staffProperty/updateStatusPass?id=' + id,
    method: 'get'
  })
}

//驳回
export function updateStatusReject(query) {
  return request({
    url: '/estate/staffProperty/updateStatusReject',
    method: 'post',
    data: query
  })
}


//根据物业人员userId查询认证车位
export function getUserIdVehicleNoList(userId) {
  return request({
    url: '/estate/staffProperty/getUserIdVehicleNoList?userId=' + userId,
    method: 'get'
  })
}

//新增
export function add(query) {
  return request({
    url: '/estate/staffProperty/add',
    method: 'post',
    data: query
  })
}

//修改
export function update(query) {
  return request({
    url: '/estate/staffProperty/update',
    method: 'post',
    data: query
  })
}


//详情
export function get(id) {
  return request({
    url: '/estate/staffProperty/' + id,
    method: 'get'
  })
}

//删除
export function deleteById(id) {
  return request({
    url: '/estate/staffProperty/deleteById?id=' + id,
    method: 'delete'
  })
}
import request from '@/utils/request'

// 查询租赁合同的列表
export function listBuyer(query) {
  return request({
    url: '/estate/buyer/list',
    method: 'get',
    params: query
  })
}

//新增合同
export function addBuyer(data) {
  return request({
    url: '/estate/buyer',
    method: 'post',
    params: data
  })
}

//获取物业列表
export function getEstateList() {
  return request({
    url: '/estate/buyer/estateList',
    method: 'get'
  })
}

//获取员工列表
export function getStaffList(estateId) {
  return request({
    url: '/estate/buyer/staffList/'+estateId,
    method: 'get'
  })
}






//删除采购合同
export function delBuyer(procurementId) {
  return request({
    url: '/estate/buyer/' + procurementId,
    method: 'delete'
  })
}

//根据id查询合同详情
export function getBuyerById(procurementId) {
  return request({
    url: '/estate/buyer/' + procurementId,
    method: 'get'
  })
}

import request from '@/utils/request'

// 查询合同列表
export function listContractManage(query) {
  return request({
    url: '/estate/contractManage/list',
    method: 'post',
    data: query
  })
}

//新增合同
export function addContractManage(data) {
  return request({
    url: '/estate/contractManage',
    method: 'post',
    data: data
  })
}



//修改合同
export function updateContractManage(data) {
  return request({
    url: '/estate/contractManage',
    method: 'put',
    data: data
  })
}

//获取物业列表
export function getEstateList() {
  return request({
    url: '/estate/contractManage/estateList',
    method: 'get'
  })
}

//获取员工列表
export function getStaffList(estateId) {
  return request({
    url: '/estate/contractManage/staffList/' + estateId,
    method: 'get'
  })
}


//删除合同
export function delContractManage(id) {
  return request({
    url: '/estate/contractManage/delete?id=' + id,
    method: 'delete'
  })
}

//根据id查询合同详情
export function getContractManageById(id) {
  return request({
    url: '/estate/contractManage/' + id,
    method: 'get'
  })
}

import request from '@/utils/request'

// 查询租赁合同的列表
export function listLease(query) {
  return request({
    url: '/estate/lease/list',
    method: 'get',
    params: query
  })
}


//新增租赁合同
export function addLease(data) {
  return request({
    url: '/estate/lease',
    method: 'post',
    params: data
  })
}

//delLeases
// 删除租房合同
export function delLeases(leaseId) {
  return request({
    url: '/estate/lease/' + leaseId,
    method: 'delete'
  })
}

//根据合同的id，查询详情
export function getLeaseById(contractId) {
  return request({
    url: '/estate/lease/' + contractId,
    method: 'get',
    params: contractId
  })
}


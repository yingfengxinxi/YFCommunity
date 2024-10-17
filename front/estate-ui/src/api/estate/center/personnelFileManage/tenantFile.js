import request from '@/utils/request'
//查询
export function query(query) {
  return request({
    url: '/estate/tenantfile/query',
    method: 'post',
    data: query
  })
}

//查询 合同
export function queryContract(query) {
  return request({
    url: '/estate/tenantfile/queryContract',
    method: 'get',
    params: query
  })
}
export function getById(tenantId) {
  return request({
    url: '/estate/tenantfile/getById?tenantId=' + tenantId,
    method: 'get'
  })
}

//添加
export function save(data) {
  return request({
    url: '/estate/tenantfile/save',
    method: 'post',
    data: data
  })
}
//修改
export function update(data) {
  return request({
    url: '/estate/tenantfile/update',
    method: 'post',
    data: data
  })
}
//解约
export function deleted(data) {
  return request({
    url: '/estate/tenantfile/deleted',
    method: 'post',
    data: data
  })
}
//续约
export function updateContract(data) {
  return request({
    url: '/estate/tenantfile/updateContract',
    method: 'get',
    params: data
  })
}

export function getDevice(communityId) {
  return request({
    url: '/estate/tenantfile/getDevice/' + communityId,
    method: 'get',
  })
}
// 人员信息授权
export function authTenant(params) {
  return request({
    url: '/estate/tenantfile/auth',
    method: 'post',
    data: params
  })
}

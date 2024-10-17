import request from '@/utils/request'

//分页列表
export function getListPage(query) {
  return request({
    url: '/estate/payBillingRecord/getList',
    method: 'get',
    params: query
  })
}


//详情
export function getById(id) {
  return request({
    url: '/estate/payBillingRecord/' + id,
    method: 'get'
  })
}

//新增线下记录
export function insert(data) {
  return request({
    url: '/estate/payBillingRecord/insert',
    method: 'post',
    data: data
  })
}

//修改线下记录
export function update(data) {
  return request({
    url: '/estate/payBillingRecord/update',
    method: 'post',
    data: data
  })
}

//删除线下记录
export function deleteById(id) {
  return request({
    url: '/estate/payBillingRecord/deleteById?id=' + id,
    method: 'delete'
  })
}


//租赁购买下拉
export function getLeasePurchaseList(communityId, carportNature) {
  return request({
    url: '/estate/parkDuration/getLeasePurchaseList?communityId=' + communityId + "&carportNature=" + carportNature,
    method: 'get'
  })
}

//选择套餐下拉
export function getPurchasePackageList(communityId, leasePurchase) {
  return request({
    url: '/estate/parkDuration/getPurchasePackageList?communityId=' + communityId + "&leasePurchase=" + leasePurchase,
    method: 'get'
  })
}

//查询费用
export function getFee(communityId, leasePurchase, purchasePackage) {
  return request({
    url: '/estate/parkDuration/getFee?communityId=' + communityId + "&leasePurchase=" + leasePurchase + "&purchasePackage=" + purchasePackage,
    method: 'get'
  })
}


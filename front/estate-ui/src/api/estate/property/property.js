import request from '@/utils/request'

export function listProperty(query) {
  return request({
    url: '/estate/property/list',
    method: 'get',
    params: query
  })
}

//采购
export function propertyProcurementRecordAdd(data) {
  return request({
    url: '/estate/propertyProcurementRecord/add',
    method: 'post',
    data: data
  })
}
// 新增
export function addProperty(data) {
  return request({
    url: '/estate/property/add',
    method: 'post',
    data: data
  })
}
// 出库
export function addRecordProperty(data) {
  return request({
    url: '/estate/property/recordAdd',
    method: 'post',
    data: data
  })
}
//入库
export function updateStatus(id) {
  return request({
    url: '/estate/property/updateStatus?id=' + id,
    method: 'get'
  })
}





// 修改
export function updateProperty(data) {
  return request({
    url: '/estate/property/update',
    method: 'put',
    data: data
  })
}

// 删除
export function delProperty(id) {
  return request({
    url: '/estate/property/remove?id=' + id,
    method: 'delete'
  })
}

// 查询详细
export function getPropertyInfo(id) {
  return request({
    url: '/estate/property/' + id,
    method: 'get'
  })
}


// 参与人数列表
export function joinPeopleListManage(query) {
  return request({
    url: '/estate/property/recordlist',
    method: 'get',
    params: query
  })
}

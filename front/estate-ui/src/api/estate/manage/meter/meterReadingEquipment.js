import request from '@/utils/request'

// 查询抄表设施列表
export function listMeterReadingEquipment(query) {
  return request({
    url: '/estate/meterReadingEquipment/list',
    method: 'POST',
    data: query
  })
}

// 查询抄表设施详情
export function getMeterReadingEquipmentDetail(id) {
  return request({
    url: '/estate/meterReadingEquipment/' + id,
    method: 'get'
  })
}

// 修改抄表设施状态
export function updateByIdStatus(id, status) {
  return request({
    url: '/estate/meterReadingEquipment/updateByIdStatus?id=' + id + "&status=" + status,
    method: 'get'
  })
}


// 增加抄表设施
export function add(data) {
  return request({
    url: '/estate/meterReadingEquipment/save',
    method: 'post',
    data: data
  })
}


// 修改抄表设施
export function update(data) {
  return request({
    url: '/estate/meterReadingEquipment/update',
    method: 'put',
    data: data
  })
}


// 删除抄表设施
export function removeById(id) {
  return request({
    url: '/estate/meterReadingEquipment/removeById?id=' + id,
    method: 'delete'
  })
}
import request from '@/utils/request'

// 查询设施设备列表
export function listFacilitieEquipment(query) {
  return request({
    url: '/estate/facilitieEquipment/list',
    method: 'post',
    data: query
  })
}

// 查询设施设备详情
export function getFacilitieEquipmentDetail(id) {
  return request({
    url: '/estate/facilitieEquipment/' + id,
    method: 'get'
  })
}


// 新增
export function add(data) {
  return request({
    url: '/estate/facilitieEquipment/insert',
    method: 'post',
    data: data
  })
}

// 修改
export function update(data) {
  return request({
    url: '/estate/facilitieEquipment/update',
    method: 'put',
    data: data
  })
}
// 修改
export function updateEquipmentStatus(id, equipmentStatus) {
  return request({
    url: '/estate/facilitieEquipment/updateEquipmentStatus?id=' + id + "&equipmentStatus=" + equipmentStatus,
    method: 'get'
  })
}

//删除
export function del(id) {
  return request({
    url: '/estate/facilitieEquipment/delete?id=' + id,
    method: 'delete'
  })
}

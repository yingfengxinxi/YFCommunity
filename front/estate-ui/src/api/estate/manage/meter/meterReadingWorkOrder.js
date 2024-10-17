import request from '@/utils/request'

// 查询抄表列表
export function listMeterReading(query) {
  return request({
    url: '/estate/meterReading/list',
    method: 'post',
    data: query
  })
}

// 查询抄表详情
export function getMeterReadingDetail(id) {
  return request({
    url: '/estate/meterReading/get?id=' + id,
    method: 'get'
  })
}
//查询统计年份
export function getYearList(communityId) {
  return request({
    url: '/estate/meterReading/getYearList?communityId=' + communityId,
    method: 'get'
  })
}

//电表能耗表统计
export function getElectricityMeterReadingList(data) {
  return request({
    url: '/estate/meterReading/getElectricityMeterReadingCountList',
    method: 'post',
    data: data
  })
}

//水表能耗表统计
export function getWaterMeterMeterReadingList(data) {
  return request({
    url: '/estate/meterReading/getWaterMeterMeterReadingCountList',
    method: 'post',
    data: data
  })
}

//查看能耗表统计
export function getMeterReadingEquipmentCountList(data) {
  return request({
    url: '/estate/meterReading/getMeterReadingEquipmentCountList',
    method: 'post',
    data: data
  })
}



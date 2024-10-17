import request from '@/utils/request'

// 查询详情
export function getRecordList(query) {
  return request({
    url: '/estate/record/list',
    method: 'get',
    params: query
  })
}

export function getRecordList2(query) {
  return request({
    url: '/estate/record/list2',
    method: 'get',
    params: query
  })
}

export function getRecordList3(query) {
  return request({
    url: '/estate/record/list3',
    method: 'get',
    params: query
  })
}

export function getTree(query) {
  return request({
    url: '/estate/record/tree',
    method: 'get',
    params: query
  })
}

export function getRecordSum(query) {
  return request({
    url: '/estate/meterReading/getMeterReadingWorkOrderCount',
    method: 'post',
    data: query
  })
}

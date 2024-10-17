/*
 * @Author: 自由命名
 * @Date: 2023-11-29 14:06:42
 * @LastEditors: 自由命名
 * @LastEditTime: 2024-04-22 10:27:48
 * @FilePath: \admin-ui\src\api\home.js
 * @filePurpose: 
 */
import request from '@/utils/request'

// 首页总数
export function count() {
  return request({
    url: '/system/home/count',
    method: 'get'
  })
}

// 工单评分统计  http://localhost:9303/estateapi/reportingWarranty/getCount?communityId=1
export function scoreStatistics(communityId) {
  return request({
    url: '/system/estateapi/reportingWarranty/getCount?communityId=' + communityId,
    method: 'get'
  })
}

// 小区列表
export function listCommunities() {
  return request({
    url: '/system/home/listCommunities',
    method: 'get'
  })
}

// 物业统计
export function estateStatistics() {
  return request({
    url: '/system/home/estateStatistics',
    method: 'get'
  })
}

// 合同统计
export function contractStatistics() {
  return request({
    url: '/system/home/contractStatistics',
    method: 'get'
  })
}

// 车流人流量统计  localhost:9303/estateapi/reportingWarranty/getLogListMonthDay
export function getLogListMonthDay(data) {
  console.log(data, 'query')
  return request({
    url: '/system/estateapi/reportingWarranty/getLogListMonthDay',
    method: 'post',
    data: data
  })
}


// 工单排名
export function orderRank() {
  return request({
    url: '/system/home/orderRank',
    method: 'get'
  })
}

// 查询设备信息分页查询 localhost:9303/estateapi/reportingWarranty/findListBusDoor?communityId=1&pageSize=1&pageNum=1
export function findListBusDoor(query) {
  return request({
    url: '/system/estateapi/reportingWarranty/findListBusDoor',
    method: 'get',
    params: query
  })
}

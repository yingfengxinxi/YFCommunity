import request from '@/utils/request'

// 访客统计
export function getVisitLogCount(query) {
  return request({
    url: '/estate/screen/getVisitLogCount',
    method: 'get',
    params: query
  })
}

export function getDmyVisitLogCount(query) {
  return request({
    url: '/estate/screen/getDmyVisitLogCount',
    method: 'get',
    params: query
  })
}

//出入详情数据饼状图
export function getPeopleInAndOutChart(query) {
  return request({
    url: '/estate/screen/getPeopleInAndOutChart',
    method: 'get',
    params: query
  })
}

//出入详情数据人行折线图
export function getVisitCharts(query) {
  return request({
    url: '/estate/screen/getVisitCharts',
    method: 'get',
    params: query
  })
}

// 设备在线离线数量统计
export function getEquipmentCount(query) {
  return request({
    url: '/estate/screen/getEquipmentCount',
    method: 'get',
    params: query
  })
}
// 查询业主年龄
export function getOwnerAge(query) {
  return request({
    url: '/estate/screen/getOwnerAge',
    method: 'get',
    params: query
  })
}
// 员工数据统计
export function getStaffCount(query) {
  return request({
    url: '/estate/screen/getStaffCount',
    method: 'get',
    params: query
  })
}
// 小区工单数据统计
export function getPatrolOrder(query) {
  return request({
    url: '/estate/screen/getPatrolOrder',
    method: 'get',
    params: query
  })
}
//
export function getInAndOut(query) {
  return request({
    url: '/estate/screen/getInAndOut',
    method: 'get',
    params: query
  })
}
export function getEvaluate(query) {
  return request({
    url: '/estate/screen/getEvaluate',
    method: 'get',
    params: query
  })
}
export function listWaring() {
  return request({
    url: '/estate/screen/listWaring',
    method: 'get',
  })
}

//通知管理
export function listNoticeTitle() {
  return request({
    url: '/estate/screen/noticeTitle',
    method: 'get',
  });
}
// 字典 /system/screenData/dictData
export function getdictData(query) {
  return request({
    url: "/system/screenData/dictData",
    method: "get",
    params: query
  });
}

// 水电表 接口 /system/screenData/selectWaterNum
export function selectWaterNum(query) {
  return request({
    url: "/system/screenData/selectWaterNum",
    method: "get",
    params: query
  });
}

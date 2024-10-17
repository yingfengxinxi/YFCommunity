import request from "@/utils/request";

// 物业服务人员
export function getService(query) {
  return request({
    url: "/system/screenData/staffData",
    method: "get",
    params: query
  });
}

// 查询用户个人信息
export function getRoomStatus(query) {
  return request({
    url: "/system/screenData/housingStatusData",
    method: "get",
    params: query
  });
}

// 大屏业主年龄分布数据
export function getDistribution() {
  return request({
    url: "/system/screenData/ownerAgeData",
    method: "get"
  });
}

// 小区情况统计   /system/screenData/communityCount
export function getCommunitySituation(query) {
  return request({
    url: "/system/screenData/communityCount",
    method: "get",
    params: query
  });
}

// 居住舒适质量  /system/screenData/communityDetail
export function getCommunitycomfort(query) {
  return request({
    url: "/system/screenData/communityDetail",
    method: "get",
    params: query
  });
}

// 招商公示  /system/screenData/selectInvest
export function getselectInvest(query) {
  return request({
    url: "/system/screenData/selectInvest",
    method: "get",
    params: query
  });
}

// 通知公告  /system/screenData/selectNotice
export function getselectNotice(query) {
  return request({
    url: "/system/screenData/selectNotice",
    method: "get",
    params: query
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

// 社区人员结构
export function getCommunitys(query) {
  return request({
    url: "/system/screenData/communityPersonnelData",
    method: "get",
    params: query
  });
}

// 工单统计
export function getWorkOrder() {
  return request({
    url: "/system/screenData/workOrderData",
    method: "get"
  });
}

// 设备统计
export function getEquipment(query) {
  return request({
    url: "/system/screenData/equipmentStatisticsData",
    method: "get",
    params: query
  });
}
// 设备在线状态统计
export function getOnlineStatus(query) {
  return request({
    url: "/system/screenData/equipmentStatusData",
    method: "get",
    params: query
  });
}
// 按月查询每天访问次数
export function getQueryDaily(query) {
  return request({
    url: "/system/screenData/getMonthVisit",
    method: "get",
    params: query
  });
}
// 按年查询每天访问次数
export function getQueryYear(query) {
  return request({
    url: "/system/screenData/getYearVisit",
    method: "get",
    params: query
  });
}

// 巡查巡更巡检列表
export function getPatrolLists(query) {
  return request({
    url: "/system/screenData/getPatrolOrder",
    method: "get",
    params: query
  });
}

// 社区人流量统计
export function getCommunityTraffic(query) {
  return request({
    url: "/system/screenData/getPeopleVisit",
    method: "get",
    params: query
  });
}

// 年度报事报修
export function getReportRepair(query) {
  return request({
    url: "/system/screenData/getReportRepair",
    method: "get",
    params: query
  });
}

// 根据区域查询小区
export function getProject(query) {
  return request({
    url: "/system/screenData/selectCommunityByAreaId",
    method: "get",
    params: query

  });
}

// 通过小区id查询设备信息
export function getEquipmentInformation(query) {
  return request({
    url: "/system/screenData/selectCameraVo",
    method: "get",
    params: query
  });
}

// 实时入园人员展示
export function admissionPersonnel(query) {
  return request({
    url: "/system/screenData/selectUserList",
    method: "get",
    params: query
  });
}

// 通过小区id查询设备信息
export function getCameraById(query) {
  return request({
    url: "/system/screenData/getCameraById",
    method: "get",
    params: query
  });
}

// 物业排行
export function propertyRanking(query) {
  return request({
    url: "/system/screenData/selectSuggestScore",
    method: "get",
    params: query
  });
}

// 小区设备告警统计
export function equipmentAlarm() {
  return request({
    url: "/system/screenData/getAllNum",
    method: "get"
  });
}

// 小区人行/车行进出流量走势图
export function inOutHuman(query) {
  return request({
    url: "/system/screenData/user_vehicle",
    method: "get",
    params: query
  });
}

// 小区人行/车行进出流量走势图
export function inOutCar(query) {
  return request({
    url: "/system/screenData/user_vehicle",
    method: "get",
    params: query
  });
}

// 年度维修设备统计
export function annualMaintenance(query) {
  return request({
    url: "/system/screenData/getMaintenance",
    method: "get",
    params: query
  });
}

// 7日巡查状况
export function patrolStatus(query) {
  return request({
    url: "/system/screenData/selectInformation",
    method: "get",
    params: query
  });
}

// 物业收费展示
export function feeDisplay(query) {
  return request({
    url: "/system/screenData/getAmountFee",
    method: "get",
    params: query
  });
}

// 设备异常列表
export function exceptionList(query) {
  return request({
    url: "/system/screenData/device_abnormal",
    method: "get",
    params: query
  });
}

// 一键处理申请
export function getProcessing() {
  return request({
    url: "/system/screenData/selectCount",
    method: "get"
  });
}

// 一键处理申请
export function getTwoProcessing() {
  return request({
    url: "/system/screenData/selectDealCount",
    method: "get"
  });
}

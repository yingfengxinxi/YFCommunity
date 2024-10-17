import request from '@/utils/request'

// 区县
export function getTreeArea() {
  return request({
    url: "/system/indexWork/treeArea",
    method: "get"
  });
}

//小区
export function getAreaCommunity(areaId){
  return request({
    url: '/system/indexWork/areaCommunity/' + areaId,
    method: 'get',
  });
}

//满意度
export function getSatisfaction(communityId){
  return request({
    url: '/system/indexWork/satisfaction/' + communityId,
    method: 'get',
  });
}

//住户统计
export function getCommunityHead(communityId){
  return request({
    url: '/system/indexWork/communityHead/' + communityId,
    method: 'get',
  });
}

//物业员工
export function getTotalStaff(){
  return request({
    url: '/system/indexWork/totalStaff',
    method: 'get',
  });
}

//工单统计
export function getTotalQuality(communityId){
  return request({
    url: '/system/indexWork/totalQuality/' + communityId,
    method: 'get',
  });
}

//通知管理
export function getNoticeTitle(){
  return request({
    url: '/system/indexWork/noticeTitle',
    method: 'get',
  });
}

//查询社区业主流量
export function getCommunityTraffic(query) {
  return request({
    url: '/system/indexWork/communityTraffic',
    method: 'post',
    data: query
  })
}

//查询社区租客流量
export function getTenantTraffic(query) {
  return request({
    url: '/system/indexWork/tenantTraffic',
    method: 'post',
    data: query
  })
}

//查询社区车辆流量
export function getVehicleTraffic(query){
  return request({
    url: '/system/indexWork/vehicleTraffic',
    method: 'post',
    data: query
  })
}

export function getTotalSuggest(communityId){
  return request({
    url: '/system/indexWork/getTotalSuggest/' + communityId,
    method: 'get',
  });
}

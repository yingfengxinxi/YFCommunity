import request from "@/utils/request";

//月度停车收费
export function getMonthPark(){
  return request({
    url: '/ai_iot/overview/park/month',
    method: 'get'
  })
}

//日车辆进出数据
export function getVehicleLog(query){
  return request({
    url: '/ai_iot/overview/vehicle',
    method: 'get',
    params: query
  })
}

// 车位占用统计
export function getCarportUse(){
  return request({
    url: '/ai_iot/overview/carport',
    method: 'get'
  })
}





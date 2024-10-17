import request from '@/utils/request'

// 查询医疗检测通告列表
export function listMedical(query) {
  return request({
    url: '/estate/medical/medical/list',
    method: 'get',
    params: query
  })
}

//医疗档案查看体检人
export function oldlistMedical(data) {
  return request({
    url: '/estate/medical/medical/getMedicalList/',
    method: 'post',
    data: data
  })
}


// 查询医疗检测通告详细
export function getMedical(medicalId) {
  return request({
    url: '/estate/medical/medical/' + medicalId,
    method: 'get'
  })
}

// 新增医疗检测通告
export function addMedical(data) {
  return request({
    url: '/estate/medical/medical',
    method: 'post',
    data: data
  })
}

// 修改医疗检测通告
export function updateMedical(data) {
  return request({
    url: '/estate/medical/medical',
    method: 'put',
    data: data
  })
}

// 删除医疗检测通告
export function delMedical(medicalId) {
  return request({
    url: '/estate/medical/medical/' + medicalId,
    method: 'delete'
  })
}

//查看老人体检报告信息
export function see1(examinerId) {
  return request({
    url: '/estate/medical/medical/lookoldere1/' + examinerId,
    method: 'post'
  })
}
// 添加日期范围
export function addDateRange(params, dateRange) {
  console.log(dateRange)
  var search = params;
  search.startTime = "";
  search.endTime = "";
  if (null != dateRange && '' != dateRange) {
    search.startTime = dateRange[0];
    search.endTime = dateRange[1];
  }
  return search;
}

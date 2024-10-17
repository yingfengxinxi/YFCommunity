import request from '@/utils/request'

// 查询表格
export function getTableList(params) {
  return request({
    url: '/estate/housekeep/getList',
    method: 'get',
    params: params
  })
}

// 根据ID查询表格
export function getTableListById(id) {
  return request({
    url: `/estate/complanint/praiseDetail/${id}`,
    method: 'get'
  })
}

// 根据ID删除
export function del(majordomoId) {
  return request({
    url: `/estate/housekeep/del/${majordomoId}`,
    method: 'post'
  })
}


// 岗位下拉
export function postSelect() {
  return request({
    url: `/estate/housekeep/postSelect/`,
    method: 'get'
  })
}

// 修改带回数据
export function updateDetail(majordomoId) {
  return request({
    url: `/estate/housekeep/updateDetail/${majordomoId}`,
    method: 'post'
  })
}


// 修改
export function updateData(data) {
  return request({
    url: `/estate/housekeep/update`,
    method: 'post',
    data: data
  })
}


export function getBuildList(communityId) {
  return request({
    url: `/estate/housekeep/build/`+communityId,
    method: 'get',
  })
}
export function getStaffList(params) {
  return request({
    url: `/estate/housekeep/staff`,
    method: 'get',
    params: params

  })
}

export function getPhone(staffId) {
  return request({
    url: `/estate/housekeep/phone/`+staffId,
    method: 'get',
  })
}



// 修改
export function addData(data) {
  return request({
    url: `/estate/housekeep/updateBuild`,
    method: 'post',
    data: data
  })
}


// 修改
export function insertData(data) {
  return request({
    url: `/estate/housekeep/insert`,
    method: 'post',
    data: data
  })
}


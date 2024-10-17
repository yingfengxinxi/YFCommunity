import request from '@/utils/request'

// 寻人查询列表
export function getPeopleLostList(query) {
  return request({
    url: '/estate/peopleLost/list',
    method: 'post',
    data: query
  })
}


// 寻人详情
export function getPeopleLostInfo(lostId) {
  return request({
    url: '/estate/peopleLost/' + lostId,
    method: 'get'
  })
}



// 寻人修改
export function peopleLostUpdate(data) {
  return request({
    url: '/estate/peopleLost/updatePeoLost',
    method: 'post',
    data: data
  })
}


// 寻人添加
export function peopleLostSave(data) {
  return request({
    url: '/estate/peopleLost/addPeoLost',
    method: 'post',
    data: data
  })
}

// 寻人删除
export function delPeopleLost(lostId) {
  return request({
    url: '/estate/peopleLost/delete/' + lostId,
    method: 'delete'
  })
}


// 寻人修改状态
export function updatePeoLostStatus(lostId, lostStatus) {
  return request({
    url: '/estate/peopleLost/updatePeoLostStatus?lostId=' + lostId + '&lostStatus=' + lostStatus,
    method: 'get'
  })
}




// 寻物查询列表
export function getThingLostList(query) {
  return request({
    url: '/estate/thingLost/list',
    method: 'post',
    data: query
  })
}



// 寻物详情
export function getThingLostInfo(lostId) {
  return request({
    url: '/estate/thingLost/' + lostId,
    method: 'get'
  })
}



// 寻物修改
export function thingLostUpdate(data) {
  return request({
    url: '/estate/thingLost/updateThingLost',
    method: 'post',
    data: data
  })
}


// 寻物添加
export function thingLostSave(data) {
  return request({
    url: '/estate/thingLost/addThingLost',
    method: 'post',
    data: data
  })
}


// 寻物修改状态
export function updateThingLostStatus(lostId, lostStatus) {
  return request({
    url: '/estate/thingLost/updateThingLostStatus?lostId=' + lostId + '&lostStatus=' + lostStatus,
    method: 'get'
  })
}


// 寻物删除
export function delThingLost(lostId) {
  return request({
    url: '/estate/thingLost/delete/' + lostId,
    method: 'delete'
  })
}


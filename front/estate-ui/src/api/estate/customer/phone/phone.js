import request from '@/utils/request'

// 查询表格
export function getTableList(params) {
  return request({
    url: '/estate/phone/getList',
    method: 'get',
    params: params
  })
}


// 根据ID删除
export function del(ids) {
  return request({
    url: `/estate/phone/del/${ids}`,
    method: 'post'
  })
}

// 新增
export function add(data) {
  return request({
    url: `/estate/phone/add`,
    method: 'post',
    data: data
  })
}



// 修改带回数据
export function queryDeatil(contactId) {
  return request({
    url: `/estate/phone/query/${contactId}`,
    method: 'post'
  })
}


// 修改
export function updateData(data) {
  return request({
    url: `/estate/phone/update`,
    method: 'post',
    data: data
  })
}

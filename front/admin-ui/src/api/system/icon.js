/*
 * @Author: 自由命名
 * @Date: 2024-03-21 09:26:58
 * @LastEditors: 自由命名
 * @LastEditTime: 2024-03-29 18:51:12
 * @FilePath: \admin-ui\src\api\system\icon.js
 * @filePurpose: 
 */
import request from '@/utils/request'

// 查询分类管理列表   无分页  localhost:9404/icon/type/list/all?status=&isHot=&name=
export function typeListAll(query) {
  return request({
    url: '/system/icon/type/list/all',
    method: 'get',
    params: query
  })
}

// 查询分类管理列表   分页  localhost:9404/icon/type/list?status=&isHot=&name=&pageNum=1&pageSize=10
export function typeList(query) {
  return request({
    url: '/system/icon/type/list',
    method: 'get',
    params: query
  })
}

// 新增icon分类   localhost:9404/icon/type/saveIconType
export function saveIconType(data) {
  return request({
    url: '/system/icon/type/saveIconType',
    method: 'post',
    data: data
  })
}

// 修改icon分类   localhost:9404/icon/type/updateIconType
export function updateIconType(data) {
  return request({
    url: '/system/icon/type/updateIconType',
    method: 'post',
    data: data
  })
}

// 删除icon分类   localhost:9404/icon/type/removeIds?ids=1,2,3
export function typeRemoveIds(id) {
  console.log(id, 'i')
  return request({
    url: '/system/icon/type/removeIds',
    method: 'get',
    params: {
      ids: id
    }
  })
}

// 获取icon分类详细信息   localhost:9404/icon/type/3
export function iconTypeDetail(id) {
  return request({
    url: '/system/icon/type/' + id,
    method: 'get'
  })
}

// 获取icon列表   localhost:9404/icon/list?isHot=&classify=&name=&pageNum=1&pageSize=10
export function iconList(query) {
  return request({
    url: '/system/icon/list',
    method: 'get',
    params: query
  })
}

// 新增icon   localhost:9404/icon/add
export function iconAdd(data) {
  return request({
    url: '/system/icon/add',
    method: 'post',
    data: data
  })
}

// 修改icon   localhost:9404/icon/edit
export function iconEdit(data) {
  return request({
    url: '/system/icon/edit',
    method: 'post',
    data: data
  })
}

// 删除icon   localhost:9404/icon/removeIcon?ids=35
export function removeIcon(id) {
  return request({
    url: '/system/icon/removeIcon',
    method: 'get',
    params: {
      ids: id
    }
  })
}

// icon启用  localhost:9404/icon/switchStatus
export function switchStatusIcon(data) {
  console.log(data, 'data')
  return request({
    url: '/system/icon/switchStatus',
    method: 'post',
    data: data
  })
}

// icon置顶  localhost:9404/icon/switchStatus/top
export function switchStatusIcontop(data) {
  return request({
    url: '/system/icon/switchStatus/top',
    method: 'post',
    data: data
  })
}


// 获取物业服务icon列表   http://localhost:9303/homeMenu/getPage?estateId=1&type=0&pageSize=1&pageNum=1
export function estateIconList(query) {
  return request({
    url: '/system/homeMenu/getPage',
    method: 'get',
    params: query
  })
}

// 新增物业服务icon   http://localhost:9303/homeMenu/add
export function estateIconAdd(data) {
  return request({
    url: '/system/homeMenu/add',
    method: 'post',
    data: data
  })
}

// 修改物业服务icon   http://localhost:9303/homeMenu/edit
export function estateIconEdit(data) {
  return request({
    url: '/system/homeMenu/edit',
    method: 'post',
    data: data
  })
}

// 删除物业服务icon   http://localhost:9303/homeMenu/del?homeMenuIds=1
export function removeEstateIcon(id) {
  return request({
    url: '/system/homeMenu/del?homeMenuIds=' + id,
    method: 'post'
  })
}

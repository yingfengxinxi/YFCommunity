/*
 * @Author: 自由命名
 * @Date: 2024-03-28 15:06:38
 * @LastEditors: 自由命名
 * @LastEditTime: 2024-03-28 18:49:17
 * @FilePath: \admin-ui\src\api\system\banner.js
 * @filePurpose: 
 */
import request from '@/utils/request'


// 查询所有轮播图 http://localhost:9303/system/banner/getPage?estateId=1&type=0&pageSize=10&pageNum=1
export function listBanner(query) {
  return request({
    method: 'get',
    url: '/system/banner/getPage',
    params: query
  })
}

// 新增轮播图 http://localhost:9303/system/banner/add
export function addBanner(data) {
  return request({
    url: '/system/banner/add',
    method: 'post',
    data: data
  })
}

// 修改轮播图  http://localhost:9303/system/banner/edit
export function updateBanner(data) {
  return request({
    url: '/system/banner/edit',
    method: 'post',
    data: data
  })
}

// 删除轮播图 http://localhost:9303/system/banner/del?bannerIds=1
export function delBanner(bannerId) {
  return request({
    url: '/system/banner/del?bannerIds=' + bannerId,
    method: 'post'
  })
}

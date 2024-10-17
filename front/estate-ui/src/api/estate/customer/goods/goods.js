import request from '@/utils/request'

export function uploadImageUrl(data) {
  return request({
    url: '/estate/goods/upload',
    method: 'post',
    data: data
  })
}

// 查询列表
export function getGoodsList(query) {
  return request({
    url: '/estate/goods/list',
    method: 'get',
    params: query
  })
}


// 查询详细
export function getGoodsInfo(goodsId) {
  return request({
    url: '/estate/goods/getInfoById/' + goodsId,
    method: 'get'
  })
}

// 修改
export function updateGoods(data) {
  return request({
    url: '/estate/goods/update',
    method: 'put',
    data: data
  })
}

// 新增信息
export function addGoods(data) {
  return request({
    url: '/estate/goods/insert',
    method: 'post',
    data: data
  })
}

// 新增提醒
export function addRemind(data) {
  return request({
    url: '/estate/goods/remind',
    method: 'post',
    data: data
  })
}

// 删除
export function delGoods(goodsId) {
  return request({
    url: '/estate/goods/delete/' + goodsId,
    method: 'delete'
  })
}

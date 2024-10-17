import request from '@/utils/request'

// 查询列表
export function getGoodsOutList(query) {
  return request({
    url: '/estate/goodsOut/list',
    method: 'get',
    params: query
  })
}

// 查询详细
export function getInfo(outId) {
  return request({
    url: '/estate/goodsOut/getInfoById/' + outId,
    method: 'get'
  })
}

// 修改
export function updateGoodsOutStatus(outId) {
  const data = {
    outId
  }
  return request({
    url: '/estate/goodsOut/update',
    method: 'put',
    data: data
  })
}

// 查询列表
export function getPassageList(query) {
  return request({
    url: '/estate/passage/allList',
    method: 'get',
    params: query
  })
}


import request from '@/utils/request'

// 查询列表
export function getBorrowList(query) {
  return request({
    url: '/estate/borrow/list',
    method: 'get',
    params: query
  })
}

// 查询详细
export function getInfo(borrowId) {
  return request({
    url: '/estate/borrow/getInfoById/' + borrowId,
    method: 'get'
  })
}


// 审核
export function aduitBorrow(data) {
  return request({
    url: '/estate/borrow/update',
    method: 'put',
    data: data
  })
}


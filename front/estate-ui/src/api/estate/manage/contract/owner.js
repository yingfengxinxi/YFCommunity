import request from '@/utils/request'

// 查询租赁合同的列表
export function OwnerList(query) {
  return request({
    url: '/estate/ownerLeaseInfo/ownerInfo',
    method: 'get',
    params: query
  })
}





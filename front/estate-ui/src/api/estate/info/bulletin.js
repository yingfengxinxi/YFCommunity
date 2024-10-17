import request from '@/utils/request'


// 查询物业通告集合
export function selectBulletInList(query) {
  return request({
    url: '/estate/bulletin/list',
    method: 'get',
    params: query
  })
}

// 查看物业通告详情
export function getDetail(query) {
  return request({
    url: '/estate/bulletin/detail',
    method: 'get',
    params: query
  })
}

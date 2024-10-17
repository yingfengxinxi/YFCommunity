import request from '@/utils/request'

// 查询列表
export function getSecondHandList(communityId) {
  return request({
    url: '/estate/secondHand/' + communityId,
    method: 'get'
  })
}

// 保存
export function saveSecondHand(communityId, thumbnail) {
  return request({
    url: '/estate/secondHand/save?communityId=' + communityId + "&thumbnail=" + thumbnail,
    method: 'get'
  })
}

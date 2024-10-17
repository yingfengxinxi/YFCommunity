import request from '@/utils/request'

// 查询列表数据
export function camerasPreviewURLs() {
  return request({
    url: '/ai_iot/haiK/camerasPreviewURLs',
    method: 'get'
  })
}
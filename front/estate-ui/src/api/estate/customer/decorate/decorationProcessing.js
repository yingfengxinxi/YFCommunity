import request from '@/utils/request'

// 新增
export function addProcessing(data) {
  return request({
    url: '/estate/decoration/add',
    method: 'post',
    data: data
  })
}

// 修改
export function updateProcessing(data) {
  return request({
    url: '/estate/decoration/update',
    method: 'post',
    data: data
  })
}

// 详情
export function getProcessing(id) {
  return request({
    url: '/estate/decoration/get/' + id,
    method: 'get'
  })
}
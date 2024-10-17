import request from '@/utils/request'

//初始化

export function gl() {
  return request({
    url: '/estate/rule/list',
    method: 'get',
  })
}



//新增
export function addrule() {
  return request({
    url: '/estate/rule',
    method: 'post'
  })
}



//修改
export function updaterule(data) {
  return request({
    url: '/estate/rule',
    method: 'put',
    data: data
  })
}



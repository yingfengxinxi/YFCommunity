import request from '@/utils/request'

// 查询首页数据
export function getParams(payMonth) {
  const data = {
    payMonth
  }
  return request({
    url: '/govern/homePage/getParams',
    method: 'post',
    data: data
  })
}

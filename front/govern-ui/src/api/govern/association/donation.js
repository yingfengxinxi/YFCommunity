import request from '@/utils/request'

//查询列表
export function getList(query) {
  return request({
    url: '/govern/donation/list',
    method: 'get',
    params: query
  })
}

//添加防控信息
export function addDonation(data) {
  return request({
    url: '/govern/donation',
    method: 'post',
    data: data
  })
}

//防空详情
export function getDonationDetail(query) {
  return request({
    url: '/govern/donation/getDonationDetail/'+query,
    method: 'get',
  })
}

//修改防控信息
export function updateDonation(data) {
  return request({
    url: '/govern/donation',
    method: 'put',
    data: data
  })
}

//删除业主捐赠信息
export function delDonation(query) {
  return request({
    url: '/govern/donation/'+query,
    method: 'delete',
  })
}


//获取当前登录人所在物业下的所有小区集合
export function getCommuntiyList() {
  return request({
    url: '/govern/community/current',
    method: 'get'
  })
}

import request from '@/utils/request'
//查询
export function query(query) {
  return request({
    url: '/estate/accountManage/query',
    method: 'get',
    params: query
  })
}

//修改密码
export function updatePwd(data) {
  return request({
    url: '/estate/accountManage/updatePwd',
    method: 'post',
    data: data
  })
}


//修改管委会
export function updateIsPropertyCouncil(accountId, isPropertyCouncil) {
  return request({
    url: '/estate/accountManage/updateIsPropertyCouncil?accountId=' + accountId + "&isPropertyCouncil=" + isPropertyCouncil,
    method: 'get'
  })
}

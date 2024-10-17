import request from '@/utils/request'

// 查询详情
export function getRuleInfo(){
  return request({
    url: '/estate/rule/getInfo',
    method: 'get',
  })
}

// 提交设置
export function settingRule(data){
  return request({
    url: '/estate/rule/setting',
    method: 'post',
    data: data
  })
}

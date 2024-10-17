import request from '@/utils/request'

export function aliList(query) {
  return request({
    url: '/estate/actm/ali_list',
    method: 'get',
    params: query
  })
}

export function alipayAdd(data) {
  return request({
    url: '/estate/actm/add_alipay',
    method: 'post',
    data: data
  })
}

export function alipayUpdate(data) {
  return request({
    url: '/estate/actm/update_alipay',
    method: 'put',
    data: data
  })
}

export function delAlipay(alipayId) {
  return request({
    url: '/estate/actm/del_alipay/' + alipayId,
    method: 'delete'
  })
}

export function wechatList(query) {
    return request({
        url: '/estate/actm/wechat_list',
        method: 'get',
        params: query
    })
}

export function addWechat(data) {
    return request({
        url: '/estate/actm/add_wechat',
        method: 'post',
        data: data
    })
}

export function updateWechat(data) {
    return request({
        url: '/estate/actm/update_wechat',
        method: 'put',
        data: data
    })
}

export function delWechat(wechatId) {
    return request({
        url: '/estate/actm/del_wechat/' + wechatId,
        method: 'delete'
    })
}

//上传证书文件
export function uploadPrivateKey(file) {
    return request({
        url: '/estate/actm/upload',
        method: 'post',
        data: file
    })
}

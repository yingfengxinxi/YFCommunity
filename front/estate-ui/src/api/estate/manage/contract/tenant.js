import request from '@/utils/request'
import {getToken} from '@/utils/auth'


// 上传图片
export function uploadPicture(data) {
  return request({
    headers: {
      Authorization: "Bearer " + getToken(),
    },
    url: '/estate/tenant/uploadPicture',
    method: 'post',
    data: data
  })
}

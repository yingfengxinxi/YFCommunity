import request from '@/utils/request'

/**
 *
 * @param query
 * @returns {AxiosPromise}
 */
export function search(query) {
  return request({
    url: '/estate/parkrepair/search',
    method: 'get',
    params: query
  })
}

export function update(data) {
  return request({
    url: '/estate/parkrepair/update',
    method: 'post',
    data: data
  })
}

export function save(data) {
  return request({
    url: '/estate/parkrepair/save',
    method: 'post',
    data: data
  })
}

export function removes(data) {
  return request({
    url: '/estate/parkrepair/removes',
    method: 'post',
    data: data
  })
}

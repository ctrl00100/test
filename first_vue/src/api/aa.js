import request from '@/utils/request'
const group_name = 'findAll'
const api_name = 'findAllByPage'
export default {
  findAll() {
    return request({
      url: `/${group_name}`,
      method: 'get'
    })
  },
  findAllByPage(page, size) {
    return request({
      url: `/${api_name}/${page}/${size}`,
      method: 'get',
      // data: searchMap
    })
  }
}

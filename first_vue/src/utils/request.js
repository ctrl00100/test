import axios from 'axios'

// 创建axios实例
const service = axios.create({
    baseURL: 'http://127.0.0.1:9002/user' // api的base_url

  })
export default service

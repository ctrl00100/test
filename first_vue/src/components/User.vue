<template>

  <div>
<!--面包屑-->

    <el-breadcrumb separator="/">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item><a href="/">用户信息</a></el-breadcrumb-item>
      <el-breadcrumb-item>活动列表</el-breadcrumb-item>
      <el-breadcrumb-item>活动详情</el-breadcrumb-item>
    </el-breadcrumb>
<!--卡片-->
    <el-card class="box-card">
      <div style="margin-top: 15px;">

        <el-row :gutter="15">
          <el-col :span="7">
            <el-input placeholder="请输入内容"  class="input-with-select">

              <el-button slot="append" icon="el-icon-search"></el-button>
            </el-input>

          </el-col>
          <el-col :span="4">

            <el-button type="">搜索</el-button>
          </el-col>


          <el-table :data="userlist">

            <el-table-column label="姓名" prop="nickname"></el-table-column>
            <el-table-column label="账户" prop="username"></el-table-column>
            <el-table-column label="邮箱" prop="email"></el-table-column>
            <el-table-column label="创建时间" prop="create_time"></el-table-column>
            <el-table-column label="更新时间" prop="update_time"></el-table-column>

          </el-table>

        </el-row>



      </div>
    </el-card>
  </div>
</template>

<script>
    import getApi from '@/api/aa'

    export default{
    data(){
        return{
            //获取用户列表参数数据
            queryInfo:{
                // query:'',
                pagenum:1,
                pagesize:2

            },
            userlist:[],
            total:0


        }
    },
        created() {
            this.getUserList()
        },
        methods: {
            // async getUserList() {
            //     const { data: res } = await this.$http.get('users', {
            //         params: this.queryInfo
            //     })
            //     if (res.meta.status !== 200) {
            //         return this.$message.error('获取用户列表失败！')
            //     }
            //     this.userlist = res.data.users
            //     this.total = res.data.total
            //     console.log(res)
            // },
            getUserList(){
                getApi.findAllByPage(this.queryInfo.pagenum,this.queryInfo.pagesize).then( res => {
                    if (res.data.code !== 20000) {
                                return this.$message.error('获取用户列表失败！')
                            }

                    this.userlist = this.userlist.concat( res.data.data.rows )
                    this.total =  res.data.data.total

                    console.log(res)
                })



            },
      }

  }

</script>

<style lang="less" scoped></style>

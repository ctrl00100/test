<template>
  <div>
    <!-- 面包屑导航区域 -->
    <el-breadcrumb separator-class="el-icon-arrow-right">
      <el-breadcrumb-item :to="{ path: '/home' }">首页</el-breadcrumb-item>
      <el-breadcrumb-item>用户管理</el-breadcrumb-item>
      <el-breadcrumb-item>用户列表</el-breadcrumb-item>
    </el-breadcrumb>

    <!-- 卡片视图区域 -->
    <el-card>
      <!-- 搜索与添加区域 -->
      <el-row :gutter="20">
        <el-col :span="8">
          <el-input placeholder="请输入内容" v-model="queryInfo.query" clearable @clear="getUserList">
            <el-button slot="append" icon="el-icon-search" @click="getUserList"></el-button>
          </el-input>
        </el-col>
        <el-col :span="4">
          <el-button type="primary" @click="addDialogVisible = true">添加用户</el-button>
        </el-col>
      </el-row>

      <!-- 用户列表区域 -->
      <el-table :data="userlist" border stripe>
        <!-- 展开列 -->
        <el-table-column type="expand"></el-table-column>
        <!-- 展开列 -->

        <el-table-column type="index"></el-table-column>
        <el-table-column label="姓名" prop="nickname"></el-table-column>
        <el-table-column label="账户" prop="username"></el-table-column>
        <el-table-column label="邮箱" prop="email"></el-table-column>
        <el-table-column label="创建时间" prop="create_time"></el-table-column>
        <el-table-column label="更新时间" prop="update_time"></el-table-column>

<!--        res.data.rows-->
        <el-table-column label="状态"   >
          <template slot-scope="scope">
            {{scope.row.bo}}

            <el-switch v-model="scope.row.bo" @change="userStateChanged(scope.row)">
            </el-switch>
          </template>
        </el-table-column>
        <el-table-column label="操作" width="200px">
          <template slot-scope="scope">
            <!-- 修改按钮 -->
            <el-button type="primary" icon="el-icon-edit" size="mini" @click="showEditDialog(scope.row.id)"></el-button>
            <!-- 删除按钮 -->
            <el-button type="danger" icon="el-icon-delete" size="mini" @click="removeUserById(scope.row.id)"></el-button>
            <!-- 分配角色按钮 -->
            <el-tooltip effect="dark" content="分配角色" placement="top" :enterable="false">
              <el-button type="warning" icon="el-icon-setting" size="mini" @click="setRole(scope.row)"></el-button>
            </el-tooltip>
          </template>
        </el-table-column>
      </el-table>

      <!-- 分页区域 -->
      <el-pagination @size-change="handleSizeChange" @current-change="handleCurrentChange" :current-page="queryInfo.pagenum" :page-sizes="[3,  5, 10]" :page-size="queryInfo.pagesize" layout="total, sizes, prev, pager, next, jumper" :total="total">
      </el-pagination>
    </el-card>

    <!-- 添加用户的对话框 -->
    <el-dialog title="添加用户" :visible.sync="addDialogVisible" width="40%" @close="addDialogClosed">
      <!-- 内容主体区域 -->
      <el-form :model="addForm" :rules="addFormRules" ref="addFormRef" label-width="70px">
        <el-form-item label="用户名" prop="nickname" label-width="20%">
          <el-input v-model="addForm.nickname"></el-input>
        </el-form-item>
        <el-form-item label="登陆账号" prop="username" label-width="20%">
          <el-input v-model="addForm.username"></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="addForm.password"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="addForm.email"></el-input>
        </el-form-item>

      </el-form>
      <!-- 底部区域 -->
      <span slot="footer" class="dialog-footer">
        <el-button @click="addDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="addUser">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 修改用户的对话框 -->
    <el-dialog title="修改用户" :visible.sync="editDialogVisible" width="50%" @close="editDialogClosed">
      <el-form :model="editForm" :rules="editFormRules" ref="editFormRef" label-width="70px">
        <el-form-item label="用户名" prop="nickname" label-width="20%">
          <el-input v-model="editForm.nickname"></el-input>
        </el-form-item>
        <el-form-item label="id" prop="id" label-width="20%" hidden>
          <el-input v-model="editForm.id"></el-input>
        </el-form-item>
        <el-form-item label="登陆账号" prop="username" label-width="20%">
          <el-input v-model="editForm.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="editForm.password"></el-input>
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="editForm.email"></el-input>
        </el-form-item>

      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="editDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="editUserInfo">确 定</el-button>
      </span>
    </el-dialog>

    <!-- 分配角色的对话框 -->
    <el-dialog title="分配角色" :visible.sync="setRoleDialogVisible" width="50%" @close="setRoleDialogClosed">
      <div>
        <p>当前的用户：{{userInfo.username}}</p>
        <p>当前的角色：{{userInfo.role_name}}</p>
        <p>分配新角色：
          <el-select v-model="selectedRoleId" placeholder="请选择">
            <el-option v-for="item in rolesList" :key="item.id" :label="item.roleName" :value="item.id">
            </el-option>
          </el-select>
        </p>
      </div>
      <span slot="footer" class="dialog-footer">
        <el-button @click="setRoleDialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="saveRoleInfo">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
    import getApi from '@/api/aa'
    export default {
        data() {
            // 验证邮箱的规则
            var checkEmail = (rule, value, cb) => {
                // 验证邮箱的正则表达式
                const regEmail = /^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+(\.[a-zA-Z0-9_-])+/

                if (regEmail.test(value)) {
                    // 合法的邮箱
                    return cb()
                }

                cb(new Error('请输入合法的邮箱'))
            }

            // // 验证手机号的规则
            // var checkMobile = (rule, value, cb) => {
            //     // 验证手机号的正则表达式
            //     const regMobile = /^(0|86|17951)?(13[0-9]|15[012356789]|17[678]|18[0-9]|14[57])[0-9]{8}$/
            //
            //     if (regMobile.test(value)) {
            //         return cb()
            //     }
            //
            //     cb(new Error('请输入合法的手机号'))
            // }

            return {
                // 获取用户列表的参数对象
                queryInfo: {
                    query: '',
                    email: '',
                    username: '',
                    // 当前的页数
                    pagenum: 1,
                    // 当前每页显示多少条数据
                    pagesize: 3
                },
                userlist: [],
                total: 0,
                // 控制添加用户对话框的显示与隐藏
                addDialogVisible: false,
                // 添加用户的表单数据
                addForm: {
                    nickname: '',
                    username: '',
                    password: '',
                    email: '',

                },
                editForm: {
                    id:'',
                    nickname: '',
                    username: '',
                    password: '',
                    email: '',},
                // 添加表单的验证规则对象
                addFormRules: {


                    nickname: [
                    { required: true, message: '请输入用户名', trigger: 'blur' },
                    {  min: 3,
                        max: 10,
                        message: '用户名的长度在3~10个字符之间',
                        trigger: 'blur' }
                ],
                    username: [
                        { required: true, message: '请输入登陆账号', trigger: 'blur' },
                        {
                            min: 3,
                            max: 10,
                            message: '登陆账号的长度在3~10个字符之间',
                            trigger: 'blur'
                        }
                    ],
                    password: [
                        { required: false, message: '请输入密码', trigger: 'blur' },
                        {
                            min: 6,
                            max: 999,
                            message: '密码的长度在6~15个字符之间',
                            trigger: 'blur'
                        }
                    ],
                    email: [
                        { required: true, message: '请输入邮箱', trigger: 'blur' },
                        { validator: checkEmail, trigger: 'blur' }
                    ]
                },
                // 控制修改用户对话框的显示与隐藏
                editDialogVisible: false,
                // 查询到的用户信息对象
                editForm: {},
                // 修改表单的验证规则对象
                editFormRules: {

                    nickname: [
                        { required: true, message: '请输入用户名', trigger: 'blur' },
                        {  min: 3,
                            max: 10,
                            message: '用户名的长度在3~10个字符之间',
                            trigger: 'blur' }
                    ],
                    username: [
                        { required: true, message: '请输入登陆账号', trigger: 'blur' },
                        {
                            min: 3,
                            max: 10,
                            message: '登陆账号的长度在3~10个字符之间',
                            trigger: 'blur'
                        }
                    ],
                    password: [
                        { required: true, message: '请输入密码', trigger: 'blur' },
                        {
                            min: 6,
                            max: 1599,
                            message: '密码的长度在6~15个字符之间',
                            trigger: 'blur'
                        }
                    ],
                    email: [
                        { required: false, message: '请输入邮箱', trigger: 'blur' },
                        // { validator: checkEmail, trigger: 'blur' }
                    ]
                },
                // 控制分配角色对话框的显示与隐藏
                setRoleDialogVisible: false,
                // 需要被分配角色的用户信息
                userInfo: {},
                // 所有角色的数据列表
                rolesList: [],
                // 已选中的角色Id值
                selectedRoleId: ''
            }
        },
        created() {
            this.getUserList()
            // this.getUserList2()

        },
        methods: {
            // async getUserList() {
            //     const { data: res } = await this.$http.get('findAll'
            //     )
            //     // if (res.meta.status !== 200) {
            //     //     return this.$message.error('获取用户列表失败！')
            //     // }
            //     this.userlist = res.data.data.rows
            //     this.total = res.data.data.total
            //     console.log(res)
            // },
            // getUserList(){
            //     getApi.findAllByPage( this.queryInfo.pagenum,this.queryInfo.pagesize).then( res => {
            //         this.userlist = this.userlist.concat( res.data.data.rows )
            //         this.total =  res.data.data.total
            //
            //         console.log(res)
            //     })
            //
            //
            //
            // },
            async getUserList() {
                const { data: res } = await this.$http.post('findAll2',
                    this.queryInfo
                )
                if (res.code !== 20000) return this.$message.error(res.message)
                this.total = res.data.total
                this.userlist=res.data.rows
                  console.log(res)



            },
            // async getUserList2() {
            //     // const {data: res} = await this.$http.get('findAll')
            //     const {data: res} = await this.$http.get
            //     (`findAllByPage/${this.queryInfo.pagenum}/${this.queryInfo.pagesize}`)
            //     // ('findAll')
            //     console.log(res)
            //
            //     if (res.code !== 20000) return this.$message.error(res.message)
            //     this.total = res.data.total
            //     this.userlist=res.data.rows
            //
            // },



            // 监听 pagesize 改变的事件
            handleSizeChange(newSize) {
                // console.log(newSize)
                this.queryInfo.pagesize = newSize
                this.getUserList()
            },
            // 监听 页码值 改变的事件
            handleCurrentChange(newPage) {
                // console.log(newPage)
                this.queryInfo.pagenum = newPage
                this.getUserList()
            },
            // 监听 switch 开关状态的改变
            async userStateChanged(userinfo) {
                console.log(userinfo)
                const { data: res } = await this.$http.put
                (`user/${userinfo.id}/${userinfo.bo}`)
                // (`findAllByPage/${this.queryInfo.pagenum}/${this.queryInfo.pagesize}`)
                console.log(res.code)

                if (res.code !== 20000) {
                    userinfo.bo = !userinfo.bo
                    return this.$message.error('更新用户状态失败！')
                }
                // this.getUserList()
                this.$message.success('更新用户状态成功！')
            },
            // 监听添加用户对话框的关闭事件
            addDialogClosed() {
                this.$refs.addFormRef.resetFields()
            },


            // 点击按钮，添加新用户
            addUser() {
                this.$refs.addFormRef.validate(async valid => {
                    if (!valid) return
                    // 可以发起添加用户的网络请求
                    const { data: res } = await this.$http.post('add', this.addForm)

                    if (res.code !== 20000){
                        this.$message.error('添加用户失败！')
                    }

                    this.$message.success('添加用户成功！')
                    // 隐藏添加用户的对话框
                    this.addDialogVisible = false
                    // 重新获取用户列表数据
                    this.getUserList()
                })
            },
            // 展示编辑用户的对话框
            async showEditDialog(id) {
                // console.log(id)
                const { data: res } = await this.$http.get('findById/' + id)

                // console.log(res)

                if (res.code !== 20000){
                    return this.$message.error('查询用户信息失败！')
                }
                this.editForm = res.data
                this.editDialogVisible = true
            },
            // 监听修改用户对话框的关闭事件
            editDialogClosed() {
                this.$refs.editFormRef.resetFields()
            },
            // 修改用户信息并提交
            editUserInfo() {
                this.$refs.editFormRef.validate(async valid => {



                    /*  const { data: res } = await this.$http.post('add', this.addForm)

                    if (res.code !== 20000){
                        this.$message.error('添加用户失败！')
                    }

                    this.$message.success('添加用户成功！')*/


                    if (!valid) return
                    // 发起修改用户信息的数据请求
                    const { data: res } = await this.$http.put(
                        'update' , this.editForm

                    )

                    if (res.code !== 20000){
                        return this.$message.error('更新用户信息失败！')
                    }

                    // 关闭对话框
                    this.editDialogVisible = false
                    // 刷新数据列表
                    this.getUserList()
                    // 提示修改成功
                    this.$message.success('更新用户信息成功！')
                })
            },
            // 根据Id删除对应的用户信息
            async removeUserById(id) {
                // 弹框询问用户是否删除数据
                const confirmResult = await this.$confirm(
                    '此操作将永久删除该用户, 是否继续?',
                    '提示',
                    {
                        confirmButtonText: '确定',
                        cancelButtonText: '取消',
                        type: 'warning'
                    }
                ).catch(err => err)

                // 如果用户确认删除，则返回值为字符串 confirm
                // 如果用户取消了删除，则返回值为字符串 cancel
                // console.log(confirmResult)
                if (confirmResult !== 'confirm') {
                    return this.$message.info('已取消删除')
                }

                const { data: res } = await this.$http.delete(id)

                if (res.code !== 20000) {
                    return this.$message.error('删除用户失败！')
                }

                this.$message.success('删除用户成功！')
                this.getUserList()
            },
            // 展示分配角色的对话框
            async setRole(userInfo) {
                this.userInfo = userInfo

                // 在展示对话框之前，获取所有角色的列表
                const { data: res } = await this.$http.get('roles')
                if (res.meta.status !== 200) {
                    return this.$message.error('获取角色列表失败！')
                }

                this.rolesList = res.data

                this.setRoleDialogVisible = true
            },
            // 点击按钮，分配角色
            async saveRoleInfo() {
                if (!this.selectedRoleId) {
                    return this.$message.error('请选择要分配的角色！')
                }

                const { data: res } = await this.$http.put(
                    `users/${this.userInfo.id}/role`,
                    {
                        rid: this.selectedRoleId
                    }
                )

                if (res.meta.status !== 200) {
                    return this.$message.error('更新角色失败！')
                }

                this.$message.success('更新角色成功！')
                this.getUserList()
                this.setRoleDialogVisible = false
            },
            // 监听分配角色对话框的关闭事件
            setRoleDialogClosed() {
                this.selectedRoleId = ''
                this.userInfo = {}
            }
        }
    }
</script>

<style lang="less" scoped>
</style>

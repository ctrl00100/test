<template>

  <div class="hello">


    <div class="before_box">

      <!--头像区域-->
      <div class="avatar-box">
        <img src="../assets/logo.png" alt=""/>
      </div>
      <!--表单区域-->
      <!--    loginFormRef  表单重置-->
      <el-form ref="beforeFormRef" :model="beforeForm" :rules="beforeFormRules" label-width="0px" class="before_form">
        <!--        user-->
        <el-form-item prop="username">
          <el-input v-model="beforeForm.username" prefix-icon="el-icon-user"></el-input>
        </el-form-item>
        <!--        password-->
        <el-form-item prop="password">
          <el-input v-model="beforeForm.password" prefix-icon="el-icon-key" type="password"></el-input>
        </el-form-item>
        <!--        nickename-->
        <el-form-item prop="nickname">
          <el-input v-model="beforeForm.nickname" prefix-icon="el-icon-chat-line-round"></el-input>
        </el-form-item>
        <!--        email-->
        <el-form-item prop="email">
          <el-input v-model="beforeForm.email" prefix-icon="el-icon-files" type="email"></el-input>
        </el-form-item>


<!--                bottom-->
        <el-form-item class="btns">
          <el-button type="primary" plain @click="beforelogin">注册</el-button>
          <el-button type="success" plain @click="resetLoginFormRef">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
  </div>
</template>

<script>
    export default {
        data() {
            return {
                //登陆表单绑定数据
                beforeForm: {
                    username: ``,
                    password: ``,
                    nickname: ``,
                    email: ``

                },
                //登陆表单的验证
                beforeFormRules: {
                    //验证用户名
                    username: [
                        {required: true, message: '请输入登陆账号', trigger: 'blur'},
                        {min: 3, max: 55, message: '长度在 3 到 55 个字符', trigger: 'blur'}

                    ],
                    //验证密码
                    password: [

                        {required: true, message: '请输入登陆密码', trigger: 'blur'},
                        {min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur'}
                    ],
                    //验证小名
                    nickname: [

                        {required: true, message: '请输入登陆小名', trigger: 'blur'},
                        {min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur'}
                    ],
                    email: [

                        {required: true, message: '请输入个人邮箱', trigger: 'blur'},
                        {min: 3, max: 50, message: '长度在 3 到 50 个字符', trigger: 'blur'}
                    ]
                }

            }

        },
        methods: {
            resetLoginFormRef() {
                // console.log(this);
                this.$refs.beforeFormRef.resetFields();
            },
            beforelogin() {
                this.$refs.beforeFormRef.validate(async valid => {
                    // console.log(valid);

                    if (!valid) return;
                    // const  result =await this.$http.post("login", this.loginForm);
                    // console.log(result);

                    const {data: res} = await this.$http.post("add", this.beforeForm);
                    // if(res.code!==20000) return  console.log("登陆失败")
                    if (res.code !== 20000) return this.$message.error('注册失败')
                    this.$message.success('注册成功')
                    // console.log(res);
                    this.$router.push('/login')
                });
            }
        }
    }
</script>

<style lang="less" scoped>

  .before_form {
    position: absolute;
    top: 30%;
    width: 100%;
    padding: 0 20px;
    box-sizing: border-box;
  }

  .btns {
    display: flex;
    justify-content: flex-end;
  }

  .hello {
    background-color: #2c3e50;
    height: 100%;
  }

  .before_box {
    width: 450px;
    height: 500px;
    background-color: honeydew;
    border-radius: 3px;
    position: absolute;
    left: 50%;
    top: 50%;
    transform: translate(-50%, -50%);


    .avatar-box {
      height: 130px;
      width: 130px;
      //
      border: 3px solid #eee;
      border-radius: 50%;
      //
      padding: 10px;
      //
      box-shadow: 0 0 10px #ddd;
      //
      position: absolute;
      left: 50%;
      /*top: 50%;*/
      transform: translate(-50%, -50%);

      img {
        width: 100%;
        height: 100%;
        //
        border-radius: 50%;
        background-color: #eee;
      }
    }
  }
</style>

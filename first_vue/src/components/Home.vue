<template>
  <!--  <div>-->
  <!--    Home组件-->
  <!--  </div>-->

  <!--  <div>-->
  <!--    <el-button type="info" @click="loginout">退出</el-button>-->
  <!--  </div>-->
  <el-container class="home-container">
    <!--      头-->
    <el-header>


      <div id="oop">

        <img src="../assets/22xiaodongwu_12.png"/>
        <span>主页面</span>
      </div>


      <el-button type="info" @click="loginout">退出</el-button>
    </el-header>

    <!--      主体-->
    <el-container>

      <!-- 侧边栏 -->
      <el-aside :width="isCollapse ? '64px' : '200px'">
        <div class="toggle-button" @click="toggleCollapse">|||</div>
        <!-- 侧边栏菜单区域 -->
        <el-menu background-color="#333744" text-color="#fff" active-text-color="#409EFF" unique-opened
                 :collapse="isCollapse" :collapse-transition="false" router :default-active="activePath">
          <!-- 一级菜单 -->
          <el-submenu :index="item.id + ''" v-for="item in menulist" :key="item.id">
            <!-- 一级菜单的模板区域 -->
            <template slot="title">
              <!-- 图标 -->
              <i :class="iconsObj[item.id]"></i>
              <!-- 文本 -->
              <span>{{item.name}}</span>
            </template>

            <!-- 二级菜单 -->
            <el-menu-item :index="'/' + subItem.path" v-for="subItem in item.children" :key="subItem.id"
                          @click="saveNavState('/' + subItem.path)">
              <template slot="title">
                <!-- 图标 -->
                <i :class="iconsObj[subItem.id]"></i>
                <!-- 文本 -->
                <span>{{subItem.name}}</span>
              </template>
            </el-menu-item>
          </el-submenu>
        </el-menu>
      </el-aside>
      <!--            111-->


      <!--        右侧主体-->
      <el-main>
        <!--          -->


        <!--          <el-table :data="userlist">-->

        <!--            <el-table-column label="姓名" prop="nickname"></el-table-column>-->
        <!--            <el-table-column label="账户" prop="username"></el-table-column>-->
        <!--            <el-table-column label="邮箱" prop="email"></el-table-column>-->
        <!--            <el-table-column label="创建时间" prop="create_time"></el-table-column>-->
        <!--            <el-table-column label="更新时间" prop="update_time"></el-table-column>-->

        <!--          </el-table>-->
        <!--          -->


        <router-view></router-view>
      </el-main>
    </el-container>
  </el-container>

</template>
<script>
    export default {
        data() {
            return {
                //左侧菜单数据
                menulist: [],

                isCollapse: false,
                activePath: '',
                // userlist:[],
                total: 0,
                iconsObj: {
                    '10': 'el-icon-user',
                    '11': 'el-icon-setting',
                    '20': 'el-icon-star-off',
                    '21': 'el-icon-star-on',
                }
            }
        },
        created() {
            this.getMenuList()
            this.activePath = window.sessionStorage.getItem('activePath')
        },

        methods: {
            loginout() {
                window.sessionStorage.clear();
                this.$router.push('/login');
                this.$message.success('登出成功');
            },
            async getMenuList() {
                const {data: res} = await this.$http.get('findList')
                console.log(res)
                if (res.code !== 20000) return this.$message.error(res.message)
                this.menulist = res.data
                // this.userlist=res.data

            },
            //切换菜单
            toggleCollapse() {
                this.isCollapse = !this.isCollapse

            },
            saveNavState(activePath) {
                window.sessionStorage.setItem('activePath', activePath)
                this.activePath = activePath
            }

        }


    };
</script>

<style lang="less" scoped>
  .home-container {
    height: 100%;
  }

  .el-header {
    background-color: #373d41;
    //文字两边走
    display: flex;
    justify-content: space-between;
    //图片靠左边
    padding-left: 0px;
    //按钮居中
    align-items: center;
    color: #fff;
    font-size: 20px;

    > div {
      display: flex;
      align-items: center;

      span {
        margin-left: 5px;
      }
    }
  }

  .el-aside {
    background-color: #323744;

    .el-menu {
      border-right: none;
    }
  }

  .el-main {
    background-color: #dddddd;
  }

  #op {
    /*height: 10px;*/
    /*width: 10px;*/

  }

  .toggle-button {
    background-color: #4a5064;
    font-size: 10px;
    line-height: 24px;
    color: #ffffff;
    /*文字居中*/
    text-align: center;
    letter-spacing: 0.2em;

  }

</style>

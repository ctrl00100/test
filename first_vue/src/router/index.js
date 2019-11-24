import Vue from 'vue'
import Router from 'vue-router'
import HelloWorld from '@/components/HelloWorld'
import Login from '@/components/Login.vue'
import Home from '@/components/Home.vue'
import Beforelogin from '@/components/Beforelogin.vue'
import Welcome from '@/components/Welcome.vue'
import User from '@/components/User.vue'
import User2 from '@/components/User2.vue'
import Power1 from '@/components/Power1.vue'
import '../assets/css/global.css'



import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'


Vue.use(ElementUI)

Vue.use(Router)

const router= new Router({
  routes: [

    {
      path: '/',
      redirect:'/login'  //重定向
    },
    {
      path: '/66',
      name: 'HelloWorld',
      component: HelloWorld
    },
    {
      path: '/login',
      name: 'Login',
      component: Login

    },{
      path: '/home',
      component: Home,
      redirect:'/welcome',
      children:[{path:'/welcome',component:Welcome},
        {path:'/user',component: User},
        {path:'/user2',component: User2},
        {path:'/power1',component: Power1},

      ]

    },{
      path: '/beforelogin',
      component: Beforelogin

    }
  ]
})

//挂载路由导航守卫
router.beforeEach((to,from,next) => {


  if(to.path==='/login') return next();
  if(to.path==='/beforelogin') return next();

  //
  const  tokenStr =window.sessionStorage.getItem('token')

  if(!tokenStr) return  next('/login')
  next()


})

export default router

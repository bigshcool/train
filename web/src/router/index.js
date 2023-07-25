import { createRouter, createWebHistory } from 'vue-router'
const routes = [{
  path: '/login',
  component: () => import('../views/LoginView')
},
  {
    path: '/welcome',
    component: () => import('../views/MainView')
  }, {
  path: '/',
  component: () => import('../views/MainView'),
  meta: {
    loginRequire: true
  },
}];

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router

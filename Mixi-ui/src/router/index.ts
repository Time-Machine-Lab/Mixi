/*
 * @Author: Dhx
 * @Date: 2024-07-05 15:32:16
 * @Description: 
 * @FilePath: \Mixi\Mixi-ui\src\router\index.ts
 */
import { createRouter, createWebHistory } from 'vue-router'
import { routes, handleHotUpdate } from 'vue-router/auto-routes'

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes,
})
if (import.meta.hot) { 
  handleHotUpdate(router) 
}
export default router

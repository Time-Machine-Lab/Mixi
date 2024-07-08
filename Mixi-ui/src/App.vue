<!--
 * @Author: LisianthusLeaf
 * @Date: 2024-06-24 10:25:41
 * @LastEditors: LisianthusLeaf
 * @LastEditTime: 2024-07-08 15:20:11
 * @FilePath: \Mi\Mixi-ui\src\App.vue
 * @Description: 
 * WX:baiyao0211
 * Copyright (c) 2024 by LisianthusLeaf, All Rights Reserved. 
-->
<script setup lang="ts">
import { RouterView } from 'vue-router'
import router from '@/router'
const footer = [
  { name: '关于我们', router: '/about' },
  { name: '关于我们', router: '/about' },
  { name: '关于我们', router: '/about' }
]
</script>

<template>
  <header class="flex">
    <div class="logo flex">Mixi</div>
    <div class="navs flex">
      <nav class="flex" @click="router.push('/price')">使用指南</nav>
      <nav class="flex" @click="router.push('/price')">定价</nav>
      <nav class="flex" @click="router.push('/about')">关于我们</nav>
      <nav class="flex">
        <button class="M-btn">登录/注册</button>
      </nav>
    </div>
  </header>
  <RouterView />
  <footer>
    <div>
      <svg height="32" viewBox="0 0 16 16" width="32">
        <path
          d="M8 0c4.42 0 8 3.58 8 8a8.013 8.013 0 0 1-5.45 7.59c-.4.08-.55-.17-.55-.38 0-.27.01-1.13.01-2.2 0-.75-.25-1.23-.54-1.48 1.78-.2 3.65-.88 3.65-3.95 0-.88-.31-1.59-.82-2.15.08-.2.36-1.02-.08-2.12 0 0-.67-.22-2.2.82-.64-.18-1.32-.27-2-.27-.68 0-1.36.09-2 .27-1.53-1.03-2.2-.82-2.2-.82-.44 1.1-.16 1.92-.08 2.12-.51.56-.82 1.28-.82 2.15 0 3.06 1.86 3.75 3.64 3.95-.23.2-.44.55-.51 1.07-.46.21-1.61.55-2.33-.66-.15-.24-.6-.83-1.23-.82-.67.01-.27.38.01.53.34.19.73.9.82 1.13.16.45.68 1.31 2.69.94 0 .67.01 1.3.01 1.49 0 .21-.15.45-.55.38A7.995 7.995 0 0 1 0 8c0-4.42 3.58-8 8-8Z"
        ></path>
      </svg>
    </div>
    <div>
      <h5 style="margin-bottom: 20px">网站介绍</h5>
      <p v-for="item in footer" @click="router.push(item.router)" style="margin-top: 5px">
        {{ item.name }}
      </p>
    </div>
    <div>
      <h5 style="margin-bottom: 20px">网站介绍</h5>
      <p v-for="item in footer" @click="router.push(item.router)" style="margin-top: 5px">
        {{ item.name }}
      </p>
    </div>
    <div>
      <h5 style="margin-bottom: 20px">网站介绍</h5>
      <p v-for="item in footer" @click="router.push(item.router)" style="margin-top: 5px">
        {{ item.name }}
      </p>
    </div>
  </footer>
</template>

<style scoped>
header {
  position: fixed;
  top: 0;
  left: 0;
  height: 5rem;
  width: 100%;
  box-shadow: #d7d7d7 1px 1px 3px;
  z-index: 10;
  background-color: white;
  justify-content: space-between;
  .logo {
    width: 200px;
    height: 100%;
    font-family: 'Juice ITC';
    font-size: 50px;
  }
  .M-btn {
    width: 150px;
    height: 40px;
  }
  .navs {
    height: 100%;
    nav {
      position: relative;
      padding: 20px;
      height: 100%;
    }
  }
}
footer {
  position: relative;
  top: 0;
  height: 500px;
  width: 100%;
  border-top: solid 1px #d7d7d7;
  display: grid;
  grid-template-columns: auto auto auto auto;
  grid-gap: 10px;
  div {
    margin: 10%;
  }
}
</style>

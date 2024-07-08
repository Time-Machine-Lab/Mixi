<!--
 * @Author: LisianthusLeaf
 * @Date: 2024-06-24 10:25:41
 * @LastEditors: LisianthusLeaf
 * @LastEditTime: 2024-07-08 15:21:43
 * @FilePath: \Mi\Mixi-ui\src\views\HomeView.vue
 * @Description: 
 * WX:baiyao0211
 * Copyright (c) 2024 by LisianthusLeaf, All Rights Reserved. 
-->
<script setup lang="ts">
import { ref } from 'vue'

let members = ref([
  { name: 'Weisir', slogan: 'weisir', description: 'weisir', avatar: '' },
  { name: 'Weisir', slogan: 'weisir', description: 'weisir', avatar: '' },
  { name: 'Weisir', slogan: 'weisir', description: 'weisir', avatar: '' },
  { name: 'Weisir', slogan: 'weisir', description: 'weisir', avatar: '' },
  { name: 'Weisir', slogan: 'weisir', description: 'weisir', avatar: '' },
  { name: 'Weisir', slogan: 'weisir', description: 'weisir', avatar: '' },
  { name: 'Weisir', slogan: 'weisir', description: 'weisir', avatar: '' }
])
</script>

<template>
  <div class="main flex">
    <div class="create flex">
      <div class="create-box flex">
        <h1>Mixi</h1>
        <p>便捷实时通讯网站</p>
        <button class="M-btn" style="width: 200px; height: 40px">+创建房间</button>
      </div>
    </div>
    <div class="team">
      <h5>项目成员</h5>
      <p>TML</p>
      <div class="members">
        <div
          v-for="(item, index) in members"
          :key="index"
          class="M-card"
          style="width: 100%; height: 150px"
        >
          <h5>“{{ item.slogan }}”</h5>
          <img :src="item.avatar" alt="avatar" />
          <div>
            <h6>{{ item.name }}</h6>
            <p>{{ item.description }}</p>
          </div>
        </div>
      </div>
    </div>
  </div>
</template>
<style scoped>
.main {
  flex-direction: column;
  position: relative;
  width: 100%;
  margin-top: 50px;
}
.create {
  position: relative;
  width: 100%;
  height: 500px;
  background-color: #efefef;
  .create-box {
    width: 500px;
    height: 300px;
    flex-direction: column;
    h1 {
      font-size: 60px;
    }
    p {
      font-size: 25px;
      color: #6b6b6b;
      margin-bottom: 25px;
    }
  }
}
.team {
  position: relative;
  width: 90%;
  background-color: white;
  margin-top: 50px;
  margin-bottom: 50px;
  p {
    color: #949494;
    margin-top: 5px;
    margin-bottom: 30px;
  }
  .members {
    display: grid;
    /*  声明列的宽度  */
    grid-template-columns: auto auto auto;
    grid-gap: 30px;
  }
  img {
    margin-left: 10px;
    width: 50px;
    height: 50px;
    border-radius: 50px;
    background-color: #dadada;
  }
  .M-card {
    display: grid;
    grid-template-columns: 100px auto;
    grid-template-rows: 50% 50%;
    padding: 0 10px;
    h5 {
      display: flex;
      align-items: center;
      grid-column-start: 1;
      grid-column-end: 3;
    }
  }
}
</style>

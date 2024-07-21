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
import router from "@/router";

let members = ref([
  { name: 'Weisir', slogan: 'weisir', description: 'weisir', avatar: '' },
  { name: 'Weisir', slogan: 'weisir', description: 'weisir', avatar: '' },
  { name: 'Weisir', slogan: 'weisir', description: 'weisir', avatar: '' },
  { name: 'Weisir', slogan: 'weisir', description: 'weisir', avatar: '' },
  { name: 'Weisir', slogan: 'weisir', description: 'weisir', avatar: '' },
  { name: 'Weisir', slogan: 'weisir', description: 'weisir', avatar: '' },
  { name: 'Weisir', slogan: 'weisir', description: 'weisir', avatar: '' }
])

let join = ref(false)
let RoomId = ref()
</script>

<template>
  <div class="main flex">
    <div class="create flex">
      <div v-show="!join" class="create-box flex">
        <h1>Mixi</h1>
        <p>便捷实时通讯网站</p>
        <div>
          <button @click="join = true" class="join-btn">加入房间</button>
          <button @click="router.push('/CreatRoom')" class="M-btn" style="width: 100px; height: 40px;margin-left:15px">+创建房间</button>
        </div>
      </div>
      <div v-show="join" class="join-room">
        <div @click="join = false" class="close">X</div>
        <h4>加入房间</h4>
        <label style="margin-top: 10px"><input type="checkbox">同意我们的《用户协议》和《隐私计划》</label>
        <div style="margin-top:25px">
          <input v-model="RoomId" placeholder="请输入房间号" class="M-input">
          <button class="M-btn" style="width:60px;height:40px;margin-left:20px">加入</button>
        </div>
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
    .join-btn{
      background-color: #d7d7d7;
      border: solid 2px #a2a2a2;
      color: #545454;
      width: 100px;
      height: 40px;
      border-radius: 8px;
      cursor: pointer;
    }
    .join-btn:hover{
      background-color: #cccccc;
    }
  }
  .join-room{
    position: relative;
    width:700px;
    height:300px;
    background-color: white;
    display: flex;
    flex-direction: column;
    justify-content: center;
    align-items: center;
    .close{
      position: absolute;
      width:50px;
      height:50px;
      display: flex;
      justify-content: center;
      align-items: center;
      top:0;
      left:calc(100% - 50px);
      cursor: pointer;
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

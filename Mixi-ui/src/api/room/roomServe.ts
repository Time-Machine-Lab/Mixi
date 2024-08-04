/*
 * @Author: Dhx
 * @Date: 2024-07-25 14:49:33
 * @Description: 
 * @FilePath: \Mixi\Mixi-ui\src\api\room\roomServe.ts
 */
import {callbackApi, createApi, joinApi, pullApi, quitApi, shareApi} from '@/api/room/roomApi'
import type {callback, Parameter} from '@/api/room/roomType'
import router from "@/router";

// 创建房间
export async function create(data: Parameter) {
  const response = await createApi(data)
  if (response.data.code != 200) {
    console.log(response.data.msg)
    return false;
  }
  // await router.push('')
  return response.data.data.link // 返回房间链接
}
// 分享房间
export async function share() {
  const response = await shareApi()
  if (response.data.code != 200) {
    console.log(response.data.msg)
    return false;
  }
  return response.data.data.link // 返回房间链接
}
// 加入房间
export async function join(key: string) {
  const response = await joinApi(key)
  if (response.data.code != 200) {
    console.log(response.data.msg)
    return false
  }
  return true // 返回房间链接
}
// 拉人
export async function pull(data: string[]) {
  const response = await pullApi(data)
  if (response.data.code != 200) {
    console.log(response.data.msg)
    return false;
  }
  return true;
}
// 退出房间
export async function quit(roomId: string) {
  const response = await quitApi(roomId)
  if (response.data.code != 200) {
    console.log(response.data.msg)
    return false;
  }
  return true;
}
// 心跳
export async function callback(data: callback) {
  const response = await callbackApi(data)
  if (response.data.code != 200) {
    console.log(response.data.msg)
    return false;
  }
  return true;
}

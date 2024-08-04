/*
 * @Author: Dhx
 * @Date: 2024-07-25 20:32:46
 * @Description: 
 * @FilePath: \Mixi\Mixi-ui\src\api\room\roomApi.ts
 */
import request from "@/util/request";
import 'axios'
import type {callback, Parameter} from "@/api/room/roomType";
// 创建房间
export function createApi(data: Parameter) {
    return request({
        url: '/webRoom/create',
        method: 'post',
        data: data
    })
}
// 分享房间
export function shareApi() {
    return request({
        url: '/webRoom/linkShare',
        method: 'get'
    })
}
// 发送邀请链接到用户邮箱
export function pullApi(data: String[]) {
    return request({
        url: '/webRoom/pull',
        method: 'post',
        data: data
    })
}
// 加入房间
export function joinApi(key:string) {
    return request({
        url: '/webRoom/linkJoin',
        method: 'get',
        params: { key: key }
    })
}
// 退出房间
export function quitApi(roomId:string) {
    return request({
        url: '/webRoom/quit',
        method: 'post',
        data: { roomId: roomId }
    })
}
// 心跳
export function callbackApi(data: callback) {
    return request({
        url: '/webRoom/linkJoin',
        method: 'get',
        data: data
    })
}
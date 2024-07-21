import request from "@/util/request";
import 'axios'
import type {callback, parameter} from "@/api/room/roomType";
// 创建房间
export function createApi(data: parameter) {
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
export function pullApi(data: object) {
    return request({
        url: '/webRoom/pull',
        method: 'post',
        data: data
    })
}
// 邀请加入
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
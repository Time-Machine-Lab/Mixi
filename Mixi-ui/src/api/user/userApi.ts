import request from "@/util/request";
import {useAuthStore} from "@/stores/authStore";
import type {Profile,linkLoginForm, linkVerifyForm} from "@/api/user/userType";

export function login(form: Profile) {
    return request({
        url: '/user/login',
        method: 'post',
        data: form
    })
}
export function logoutApi() {
    return request({
        url: '/user/logout',
        method: 'post'
    })
}
export function linkLoginApi(form:linkLoginForm) {
    return request({
        url: '/user/linkLogin',
        method: 'post',
        data:form
    })
}
export function linkVerifyApi(form:linkVerifyForm) {
    return request({
        url: '/user/linkVerify',
        method: 'get',
        params: form
    })
}
export function getUserInfoApi(uid:string){
    return request({
        url: '/user/getUserInfo',
        method: 'get',
        params:uid
    })
}
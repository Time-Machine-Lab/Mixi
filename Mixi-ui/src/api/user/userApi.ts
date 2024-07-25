import request from "@/util/request";
import {useAuthStore} from "@/stores/authStore";
import type {Profile,VisitorLoginForm,LinkLoginForm, LinkVerifyForm} from "@/api/user/userType";

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
export function linkLoginApi(form:LinkLoginForm) {
    return request({
        url: '/user/linkLogin',
        method: 'post',
        data:form
    })
}
export function linkVerifyApi(form:LinkVerifyForm) {
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
export function visitorLoginApi(form:VisitorLoginForm){
    return request({
        url: '/user/visit/login',
        method: 'post',
        data:form
    })
}
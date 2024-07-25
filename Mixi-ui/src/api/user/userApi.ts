import {storage} from "@/util/storage";
import request from "@/util/request";
import {useAuthStore} from "@/stores/authStore";
import type {Profile,linkLoginForm, linkVerifyForm} from "@/api/user/userType";
import 'axios'
export function loginApi(form: Profile) {
    return request({
        url: '/user/login',
        method: 'post',
        data: form
    }).then(response => {
        const { token } = response.data;
        storage.set('token', token); // 将token保存到localStorage中
        useAuthStore().setLoggedIn(response.data)
    }).catch(error => {
        console.error('Login failed:', error);
    });
}
export function logout() {
    return request({
        url: '/user/logout',
        method: 'post'
    }).then(response => {
        storage.remove('token'); // 移除token
        useAuthStore().setLoggedOut()
    }).catch(error => {
        console.error('Login failed:', error);
    });
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
import {storage} from "@/util/storage";
import request from "@/util/request";
import {useAuthStore} from "@/stores/authStore";
import type {Profile} from "@/api/user/userType";

export function login(form: Profile) {
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
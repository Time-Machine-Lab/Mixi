import request from "@/util/request";
import type {Profile} from "@/api/user/userType";
import 'axios'
export function loginApi(form: Profile) {
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

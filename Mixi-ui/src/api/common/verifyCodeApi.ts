import request from "@/util/request";
export function getVerifyCodeApi () {
    return request({
        method: 'get',
        url: '/api/user/code/pic'
    })
}
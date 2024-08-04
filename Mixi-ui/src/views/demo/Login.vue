<!--
 * @Author: Dhx
 * @Date: 2024-07-22 16:49:56
 * @Description: 
 * @FilePath: \Mixi\Mixi-ui\src\views\demo\Login.vue
-->
<template>
    <div style="height: 100vh;width:100vw;position: relative;">
        <div
            style="height: 50%;width: 50%;position: relative;top: 50%;left: 50%;transform: translate(-50%,-50%);padding: 20px;display: flex;">
            <div style="width: 300px;height: 100%;">
                <div class="btn" :style="{ backgroundColor: loginMethod == 0 ? 'rgba(230,230,230)' : 'white' }"
                    @click="loginMethod = 0">
                    邮箱登录
                </div>
                <div class="btn" :style="{ backgroundColor: loginMethod == 1 ? 'rgba(230,230,230)' : 'white' }"
                    @click="loginMethod = 1">
                    游客登陆
                </div>
            </div>
            <div>
                <div v-if="loginMethod == 0">
                    <label>邮箱</label><input style="width: 200px;" v-model="loginForm.email">
                </div>
                <div>
                    <label>验证码</label><input style="width: 100px;" v-model="loginForm.picCode">
                    <VerifyCode :getPicId="getPicId" :picWidth="100" :picHeight="50"></VerifyCode>
                </div>
                <div>
                    <button @click="loginFunc">登录/注册</button>
                </div>
            </div>
        </div>
    </div>
</template>
<script lang="ts" setup>
import { ref } from 'vue'
import type { LinkLoginForm, VisitorLoginForm } from "@/api/user/userType";
import { linkLoginApi, visitorLoginApi } from '@/api/user/userApi';
import VerifyCode from '@/components/common/VerifyCode.vue';
import { storage } from '@/util/storage';
let loginMethod = ref(0)
let loginForm = ref<LinkLoginForm>({
    email: '',
    picId: '',
    picCode: '',
})
let visitorLoginForm = ref<VisitorLoginForm>({
    fingerprint: 'TWJMIXI666',
    picId: '',
    picCode: ''
})
const getPicId = (picId: string) => {
    loginForm.value.picId = picId
}
const loginFunc = () => {
    if (loginMethod.value == 0) {
        linkLoginApi(loginForm.value).then((res: any) => {
            if (res.code == 200) {
                console.log('success')
            }
        }, (res: any) => {
            console.log(res.message)
        })
    } else if (loginMethod.value == 1) {
        visitorLoginForm.value.picId = loginForm.value.picId
        visitorLoginForm.value.picCode = loginForm.value.picCode
        visitorLoginApi(visitorLoginForm.value).then((res: any) => {
            if (res.code == 200) {
                storage.set('Authorization', res.message)
            }
        })
    }
}
</script>
<style scoped>
input {
    height: 50px;
    outline: none;
    border: black 1px solid;
    margin-left: 10px;
}

.btn {
    width: 150px;
    height: 80px;
    border-radius: 10px;
    line-height: 80px;
    text-align: center;
    color: black;
    font-size: 24px;
    margin-top: 20px;
    cursor: pointer;
    border: rgba(220, 220, 220) 1px solid;
}

.btn:hover {
    background-color: rgba(230, 230, 230);
}
</style>
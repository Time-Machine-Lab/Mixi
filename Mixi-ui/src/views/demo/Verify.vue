<!--
 * @Author: Dhx
 * @Date: 2024-07-22 16:49:56
 * @Description: 
 * @FilePath: \Mixi\Mixi-ui\src\views\demo\Verify.vue
-->
<template>
    <div style="height: 100vh;width:100vw;position: relative;">
        <div style="height: 50%;width: 50%;position: relative;top: 50%;left: 50%;transform: translate(-50%,-50%);padding: 20px;">
            <div>
                <label>邮箱</label><input style="width: 200px;" v-model="loginForm.email">
            </div>
            <div>
                <label>验证码</label><input style="width: 100px;" v-model="loginForm.picCode"><VerifyCode :getPicId="getPicId" :picWidth="100" :picHeight="50"></VerifyCode>
            </div>
            <div>
                <button @click="loginFunc">登录/注册</button>
            </div>
        </div>
    </div>
</template>
<script lang="ts" setup>
import {ref} from 'vue'
import type {linkLoginForm} from "@/api/user/userType";
import { linkLoginApi } from '@/api/user/userApi';
import VerifyCode from '@/components/common/VerifyCode.vue';
let loginForm = ref<linkLoginForm>({
    email:'',
    picId:'',
    picCode:'',
})
const getPicId = (picId:string) => {
    loginForm.value.picId = picId
}
const loginFunc = () => {
    linkLoginApi(loginForm.value).then((res:any)=>{
        if(res.code == 200) {
            console.log('success')
        }
    },(res:any)=>{
        console.log(res.message)
    })
}
</script>
<style scoped>
input {
    height: 50px;
    outline: none;
    border: black 1px solid;
    margin-left: 10px;
}
</style>
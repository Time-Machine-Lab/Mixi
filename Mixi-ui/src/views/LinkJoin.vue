<template>
<div>

</div>
</template>
<script lang="ts" setup>
import { useRouter } from 'vue-router';
import {joinApi} from '@/api/room/roomApi'
import { onMounted } from 'vue';
let router = useRouter();
onMounted(()=>{
    joinApi(router.currentRoute.value.query.key as string).then((res:any)=>{
        if(res.code == 200) {
            //
        }
    },(res:any)=>{
        // error
    })
})
</script>
<style scoped>
</style>
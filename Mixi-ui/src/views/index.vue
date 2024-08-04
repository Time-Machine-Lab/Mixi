<!--
 * @Author: Dhx
 * @Date: 2024-07-22 16:05:16
 * @Description: 
 * @FilePath: \Mixi\Mixi-ui\src\views\index.vue
-->
<script lang="ts" setup>
import { onMounted } from 'vue';
import { useRouter } from 'vue-router';
const router = useRouter()
onMounted(()=>{
    router.push('/HomeView')
})
</script>
<template><div>
    
</div></template>
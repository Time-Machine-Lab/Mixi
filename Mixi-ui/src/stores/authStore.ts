import { defineStore } from "pinia";

import router from "../router";
import {storage} from "@/util/storage";
import type {Profile} from "@/api/user/userType";


export const useAuthStore = defineStore({
  id: "auth",
  state: () => ({
    isLoggedIn: false, // 登录状态
    profile: null as Profile | null,
  }),

  getters: {
    getLoginState(state){
      return state.isLoggedIn
    },
    getProfile(state){
      return state.profile
    }
  },

  actions: {
    // 登录
    setLoggedIn(profile:Profile) {
      this.isLoggedIn = true
      storage.set('isLoggedIn',this.isLoggedIn)
      this.updateProfile(profile)
    },
    // 登出
    setLoggedOut() {
      this.isLoggedIn = false
      storage.remove('profile')
    },
    // 更新用户信息
    updateProfile(profile:Profile) {
      this.profile = profile
      storage.set('profile',profile)
    },
  },
});

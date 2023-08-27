<template>
  <a-layout-header class="header">
    <div class="logo">
      <router-link to="/welcome" style="color: white; font-size: 18px">
        12306
      </router-link>
    </div>
    <div style="float: right; color: white;">
      欢迎使用登录控台
    </div>
    <a-menu
        v-model:selectedKeys="selectedKeys"
        theme="dark"
        mode="horizontal"
        :style="{ lineHeight: '64px' }"
    >
      <a-menu-item key="/welcome">
        <router-link to="/welcome">
          <coffee-outlined /> &nbsp; 欢迎
        </router-link>
      </a-menu-item>
      <a-menu-item key="/about">
        <router-link to="/about">
          <user-outlined /> &nbsp; &nbsp; 关于
        </router-link>
      </a-menu-item>
      <a-menu-item key="/station">
        <router-link to="/station">
          <user-outlined /> &nbsp; &nbsp; 车站管理
        </router-link>
      </a-menu-item>

      <a-menu-item key="/train">
        <router-link to="/train">
          <user-outlined /> &nbsp; &nbsp; 火车管理
        </router-link>
      </a-menu-item>

      <a-menu-item key="/train-station">
        <router-link to="/train-station">
          <user-outlined /> &nbsp; &nbsp; 火车车站管理
        </router-link>
      </a-menu-item>

      <a-menu-item key="/train-carriage">
        <router-link to="/train-carriage">
          <user-outlined /> &nbsp; &nbsp; 火车车厢管理
        </router-link>
      </a-menu-item>

      <a-menu-item key="/train-seat">
        <router-link to="/train-seat">
          <user-outlined /> &nbsp; &nbsp; 火车座位
        </router-link>
      </a-menu-item>


    </a-menu>
  </a-layout-header>
</template>

<script>
import {defineComponent, ref, watch} from 'vue';
import router from '@/router'

export default defineComponent({
  name: "the-header-view",
  setup() {
    const selectedKeys = ref([]);

    watch(() => router.currentRoute.value.path, (newValue) => {
      console.log('watch', newValue);
      selectedKeys.value = [];
      selectedKeys.value.push(newValue);
    }, {immediate: true});
    return {
      selectedKeys
    };
  },
});
</script>

<!-- Add "scoped" attribute to limit CSS to this component only -->
<style scoped>
.logo {
  float: left;
  height: 31px;
  width: 150px;
  color: white;
  font-size: 20px;
}
</style>

<template>
  <div id="app">
    <app-header></app-header>
    <!--    <router-view></router-view>-->
    <router-view id="router-view" :key="$route.fullPath"></router-view>
    <app-footer></app-footer>
  </div>
</template>

<script>
import AppHeader from "@/components/AppHeader";
import AppFooter from "@/components/AppFooter";
export default {
  name: "App",
  components: { AppFooter, AppHeader },
  created: function () {
    //store.fetchCategories
    this.$store.dispatch("fetchCategories");
  },
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}
</style>

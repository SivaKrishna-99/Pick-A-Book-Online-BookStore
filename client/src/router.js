import Vue from "vue";
import Router from "vue-router";
import HomeView from "./views/HomeView.vue";
import CategoryView from "./views/CategoryView.vue";
import Cart from "@/views/Cart";
import Checkout from "@/views/Checkout";
import Confirmation from "@/views/Confirmation";
import NotFound from "@/views/NotFound";

Vue.use(Router);

export default new Router({
  mode: "history",
  base: process.env.BASE_URL,
  routes: [
    {
      path: "/",
      name: "home-view",
      component: HomeView,
    },
    {
      path: "/home",
      name: "home-view",
      component: HomeView,
      alias: ["/", "/index.html"],
    },
    {
      path: "/index.html",
      name: "home-view",
      component: HomeView,
    },
    {
      path: "/category/:name",
      //keep the name consistent with the component
      name: "category-view",
      component: CategoryView,
      props: true,
    },
    {
      path: "/category",
      redirect: "/category/Mystery",
    },
    {
      path: "/cart",
      name: "cart",
      component: Cart,
    },
    {
      path: "/checkout",
      name: "checkout",
      component: Checkout,
    },
    {
      path: "/confirmation",
      name: "confirmation",
      component: Confirmation,
    },
    {
      path: "*",
      name: "notfound",
      component: NotFound,
    },
  ],
});

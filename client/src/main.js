import Vue from "vue";
import App from "./App.vue";
import router from "@/router";
import "@/assets/css/global.css";
import store from "@/store";

import { CART_STORAGE_KEY } from "./store";
import { ORDER_DETAILS_STORAGE_KEY } from "./store";

import Vuelidate from "vuelidate";

Vue.use(Vuelidate);

Vue.config.productionTip = false;
// From https://flaviocopes.com/how-to-format-number-as-currency-javascript/
export const PriceFormatter = new Intl.NumberFormat("en-US", {
  style: "currency",
  currency: "USD",
  minimumFractionDigits: 2,
});

Vue.filter("asDollarsAndCents", function (cents) {
  return PriceFormatter.format(cents / 100.0);
});

new Vue({
  router,
  store,
  render: function (h) {
    return h(App);
  },
  created() {
    const cartString = localStorage.getItem(CART_STORAGE_KEY);
    if (cartString) {
      const shoppingCart = JSON.parse(cartString);
      this.$store.commit("SET_CART", shoppingCart);
    }
    const orderDetailsString = sessionStorage.getItem(
      ORDER_DETAILS_STORAGE_KEY
    );
    if (orderDetailsString) {
      const orderDetailsData = JSON.parse(orderDetailsString);
      this.$store.commit("SET_ORDER_DETAILS", orderDetailsData);
    }
  },
}).$mount("#app");

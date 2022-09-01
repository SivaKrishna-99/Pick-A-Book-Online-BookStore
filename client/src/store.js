import Vue from "vue";
import Vuex from "vuex";
import ApiService from "@/services/ApiService";
import { ShoppingCart } from "@/models/ShoppingCart";

export const CART_STORAGE_KEY = "cart";
export const ORDER_DETAILS_STORAGE_KEY = "order";

Vue.use(Vuex);

export default new Vuex.Store({
  state: {
    categories: [],
    selectedCategoryName: "",
    selectedCategoryBooks: [],
    cart: new ShoppingCart(),
    orderDetails: null,
  },
  mutations: {
    SET_CATEGORIES(state, newCategories) {
      state.categories = newCategories;
    },
    SELECT_CATEGORY(state, categoryName) {
      state.selectedCategoryName = categoryName;
    },
    SET_SELECTED_CATEGORY_BOOKS(state, books) {
      state.selectedCategoryBooks = books;
    },
    ADD_TO_CART(state, book) {
      state.cart.addItem(book, 1);
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.state.cart));
    },
    UPDATE_CART(state, { book, quantity }) {
      state.cart.update(book, quantity);
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.state.cart));
    },
    SET_CART(state, shoppingCart) {
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(shoppingCart));
      let newCart = new ShoppingCart();
      shoppingCart.items.forEach((item) => {
        newCart.addItem(item.book, item.quantity);
      });
      state.cart = newCart;
    },
    CLEAR_ORDER_DETAILS(state) {
      sessionStorage.removeItem(ORDER_DETAILS_STORAGE_KEY);
      state.orderDetails = null;
    },
    SET_ORDER_DETAILS(state, orderDetails) {
      state.orderDetails = orderDetails;
      sessionStorage.setItem(
        ORDER_DETAILS_STORAGE_KEY,
        JSON.stringify(orderDetails)
      );
    },
    CLEAR_CART(state) {
      state.cart = new ShoppingCart();
      localStorage.setItem(CART_STORAGE_KEY, JSON.stringify(this.state.cart));
    },
  },
  actions: {
    fetchCategories(context) {
      ApiService.fetchCategories()
        .then((categories) => {
          console.log("Categories: ", categories);
          context.commit("SET_CATEGORIES", categories);
        })
        .catch((reason) => {
          console.log("Error: " + reason);
        });
    },
    selectCategory(context, categoryName) {
      context.commit("SELECT_CATEGORY", categoryName);
    },
    fetchSelectedCategoryBooks(context) {
      return ApiService.fetchSelectedCategoryBooks(
        context.state.selectedCategoryName
      )
        .then((books) => {
          context.commit("SET_SELECTED_CATEGORY_BOOKS", books);
        })
        .catch((reason) => {
          console.log("Error: " + reason);
          throw reason;
        });
    },
    addToCart(context, book) {
      context.commit("ADD_TO_CART", book);
    },
    updateCart(context, book, quantity) {
      context.commit("UPDATE_CART", book, quantity);
    },
    removeBook(context, book) {
      context.commit("REMOVE_BOOK", book);
    },
    clearCart(context) {
      context.commit("CLEAR_CART");
    },
    placeOrder({ commit, state }, customerForm) {
      commit("CLEAR_ORDER_DETAILS");
      return ApiService.placeOrder({
        cart: state.cart,
        customerForm: customerForm,
      }).then((orderDetail) => {
        commit("SET_ORDER_DETAILS", orderDetail);
        commit("CLEAR_CART");
      });
    },
  },
});

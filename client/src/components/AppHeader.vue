<template>
  <header class="container">
    <div class="left-header">
      <div>
        <router-link to="/">
          <img
            src="@/assets/images/site/bookstore-logo.jpg"
            alt="Pick-A-Book Logo"
          />
        </router-link>
      </div>
      <div>
        <router-link to="/" tag="h1">Pick-A-Book</router-link>
      </div>
    </div>

    <div>
      <header-search-bar></header-search-bar>
    </div>

    <div class="right-header">
      <div class="welcome-message">
        <Span>Hello Guest</Span>
        <br />
        <a href="">Sign In / Register</a>
      </div>

      <router-link to="/cart" tag="div" class="cart-container">
        <span class="cart-count"> {{ $store.state.cart.numberOfItems }} </span>
        <!--        <i class="fa-solid fa-cart-shopping"></i>-->
        <i class="fas fa-shopping-bag"></i>
      </router-link>
      <header-dropdown-menu></header-dropdown-menu>
    </div>
  </header>
</template>

<script>
import HeaderDropdownMenu from "@/components/HeaderDropdown";
import HeaderSearchBar from "@/components/HeaderSearchBar";
export default {
  name: "AppHeader",
  components: { HeaderDropdownMenu, HeaderSearchBar },
};
</script>

<style scoped>
header {
  background: var(--secondary-background-color);
  display: flex;
  padding: 1em;
  gap: 1em;
  flex-flow: row wrap;
  justify-content: space-between;
  align-items: center;
}

img {
  border-radius: 10px;
  width: 135px;
  height: auto;
}

.left-header {
  display: flex;
  flex-flow: row wrap;
  justify-content: center;
  align-items: center;
  gap: 1em;
}

.right-header {
  display: flex;
  align-items: center;
  flex-flow: row nowrap;
  justify-content: center;
  gap: 1.5rem;
}

h1 {
  font-family: "Annie Use Your Telescope", cursive;
  line-height: 74%;
  font-size: 60px;
  font-weight: normal;
  color: var(--logo-color);
}

h1:hover {
  cursor: pointer;
}

.welcome-message {
  padding: 1rem;
}

.cart-container {
  padding: 1rem;
  position: relative;
  text-align: center;
}
.cart-container i,
.header-dropdown i {
  font-size: 1.5em;
}

.cart-container:hover {
  cursor: pointer;
}

.cart-count {
  width: 100%;
  font-weight: bold;
  font-size: 0.8rem;
  color: white;
  position: absolute;
  left: 0rem;
  right: auto;
  top: 1.5rem;
}

@media (max-width: 600px) {
  header {
    flex-direction: column;
  }
}
</style>

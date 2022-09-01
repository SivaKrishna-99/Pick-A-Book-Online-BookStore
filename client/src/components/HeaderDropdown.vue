<template>
  <div class="main-menu">
    <div
      class="main-menu-icon"
      :class="mainMenuOpen ? 'highlight' : 'highlight-invisible'"
      @click="toggleMainMenu"
    >
      <i class="fas fa-bars"></i>
    </div>
    <ul class="dropdown-menu" :class="{ 'main-menu-open': mainMenuOpen }">
      <router-link to="/" tag="li">Sign In</router-link>
      <router-link to="/" tag="li">Register</router-link>
      <li class="categories-dropdown" @click="toggleSubMenu">
        <div><i class="fas fa-caret-left fa-10x"></i>&nbsp;Categories</div>
      </li>
    </ul>
    <ul class="dropdown-sub-menu" :class="{ 'sub-menu-open': subMenuOpen }">
      <template v-for="category in $store.state.categories">
        <router-link
          tag="li"
          :key="category.categoryId"
          :to="'../category/' + category.name"
          >{{ category.name }}</router-link
        >
      </template>
    </ul>
  </div>
</template>

<script>
export default {
  name: "HeaderDropdownMenu",
  data: function () {
    return {
      categories: this.$store.state.categories,
      mainMenuOpen: false,
      subMenuOpen: false,
    };
  },
  watch: {
    $route() {
      this.mainMenuOpen = false;
      this.subMenuOpen = false;
    },
  },
  methods: {
    toggleMainMenu() {
      this.mainMenuOpen = !this.mainMenuOpen;
      if (!this.mainMenuOpen) {
        this.subMenuOpen = false;
      }
    },
    toggleSubMenu() {
      this.subMenuOpen = !this.subMenuOpen;
    },
  },
};
</script>

<style scoped>
.main-menu,
.categories-dropdown {
  position: relative;
}

.dropdown-menu {
  box-shadow: 0 0 10px 0 #0008;
  background-color: white;
  overflow-y: hidden;
  transition: max-height 300ms ease-in-out;
  position: absolute;
  color: black;
  max-height: 0;
  min-width: 10rem;
  right: 0rem;
}

.main-menu-open {
  max-height: 10rem;
  z-index: 1;
  cursor: pointer;
}

.dropdown-sub-menu {
  overflow-x: hidden;
  max-width: 0;
  color: black;
  z-index: 2;
  position: absolute;
  box-shadow: 0 0 10px 0 #0008;
  background-color: white;
}

.sub-menu-open {
  white-space: nowrap;
  right: 10rem;
  transition: max-width 400ms ease-in-out;
  z-index: 2;
  cursor: pointer;
  top: 8.4rem;
  display: block;
  max-height: 20rem;
  max-width: 20rem;
}

li {
  padding: 0.25em 0.5em;
}

li:hover {
  background: lightgray;
  color: var(--primary-color);
}

li i {
  font-size: 1.25rem;
}

.main-menu-icon {
  padding: 1rem;
}

.main-menu-icon:hover {
  cursor: pointer;
}
</style>

<template>
  <nav class="category-nav">
    <ul class="/category-buttons">
      <template v-for="category in $store.state.categories">
        <li
          :key="category.categoryId"
          v-if="$route.params.name === category.name"
          class="button selected-category-button"
        >
          {{ category.name }}
        </li>

        <li
          v-else
          :key="category.categoryId"
          class="button unselected-category-button"
        >
          <router-link :to="'../category/' + category.name">
            <!--          <router-link to="category.name">-->
            {{ category.name }}
          </router-link>
        </li>
      </template>
    </ul>
  </nav>
</template>

<script>
export default {
  name: "CategoryNav",
  data: function () {
    return {
      categories: this.$store.state.categories,
    };
  },
};
</script>

<!--This style is just scoped to this file-->
<style scoped>
nav {
  background-color: var(--secondary-background-color);
}

ul {
  display: flex;
  flex-direction: row;
  gap: 0.2em;
  justify-content: center;
  align-items: center;
}

a {
  border-radius: 0.6em 0.6em 0 0;
}

.button.selected-category-button {
  background-color: var(--primary-background-color);
}

.button.selected-category-button:hover {
  color: black;
}

.button.unselected-category-button,
.button.unselected-category-button:visited {
  background-color: var(--button-color-dark);
}

.button.unselected-category-button:hover,
.button.unselected-category-button:active {
  background-color: var(--primary-color);
}
</style>

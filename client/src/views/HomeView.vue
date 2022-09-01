<template>
  <div class="main">
    <div class="welcome-text">
      <p>Welcome to <b>Pick-A-Book</b>!</p>
      <b>
        <router-link to="/category/" tag="button" class="button"
          >Shop For Books</router-link
        >
      </b>
      <br /><br />
      <p>Shop for the books in your favorite genre.!</p>
    </div>
    <home-category-list></home-category-list>
  </div>
</template>

<script>
import HomeCategoryList from "@/components/HomeCategoryList";
export default {
  name: "HomeView",
  components: { HomeCategoryList },
};
</script>

<style scoped>
.main {
  text-align: center;
  gap: 3em;
  flex: 1 1 auto;
  justify-content: flex-start;
  align-items: center;
  display: flex;
  flex-direction: column;
  font-size: 1.2em;
}

.welcome-text {
  width: 20em;
}

.welcome-text p {
  margin: 1em;
}
</style>

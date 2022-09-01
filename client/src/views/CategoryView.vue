<template>
  <div>
    <category-nav></category-nav>
    <category-book-list> </category-book-list>
  </div>
</template>

<script>
import CategoryNav from "@/components/CategoryNav";
import CategoryBookList from "@/components/CategoryBookList";
export default {
  name: "categoryView",
  components: {
    CategoryNav,
    CategoryBookList,
  },

  data: function () {
    return {
      books: this.$store.state.selectedCategoryBooks,
    };
  },

  created: function () {
    this.$store.dispatch("selectCategory", this.$route.params.name);
    this.$store.dispatch("fetchSelectedCategoryBooks").catch(function () {
      self.$router.push("/404"); // '/404' triggers NotFound
    });
  },
};
</script>

<style scoped>
div {
  display: flex;
  justify-content: flex-start;
  flex: 1 1 auto;
  flex-flow: column;
}
</style>

<template>
  <ul>
    <template v-for="book in $store.state.selectedCategoryBooks">
      <category-book-list-item
        :key="book.book"
        :book="book"
      ></category-book-list-item>
    </template>
    <li class="empty-block"></li>
    <li class="empty-block"></li>
    <li class="empty-block"></li>
    <li class="empty-block"></li>
    <li class="empty-block"></li>
    <li class="empty-block"></li>
  </ul>
</template>

<script>
import CategoryBookListItem from "./CategoryBookListItem";
export default {
  name: "CategoryBookList",
  components: {
    CategoryBookListItem,
  },
};
</script>

<style scoped>
ul {
  display: flex;
  flex-wrap: wrap;
  align-items: flex-start;
  padding: 1rem;
  gap: 1rem;
  justify-content: space-around;
  align-content: stretch;
}
.empty-block {
  width: 23rem;
  height: 0;
  flex-grow: 1;
}
</style>

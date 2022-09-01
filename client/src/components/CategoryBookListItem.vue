<template>
  <li>
    <div class="book-box">
      <div>
        <img
          :src="require('@/assets/images/books/' + bookImageFileName(book))"
          :alt="book.title"
        />
        <button class="s-button" v-if="book.isPublic">Read Now</button>
      </div>

      <div class="book-details">
        <div class="book-description">
          <div class="book-title">{{ book.title }}</div>
          <div class="book-author">{{ book.author }}</div>
          <div class="book-price">${{ (book.price / 100).toFixed(2) }}</div>
        </div>
        <div class="spring-block"></div>
        <button class="button" @click="$store.dispatch('addToCart', book)">
          Add to Cart
        </button>
      </div>
    </div>
  </li>
</template>

<script>
export default {
  name: "categoryBookListItem",
  props: {
    book: {
      type: Object,
      required: true,
    },
  },
  methods: {
    bookImageFileName: function (book) {
      let name = book.title.toLowerCase();
      name = name.replace(/ /g, "-");
      return `${name}.jpeg`;
    },
  },
};
</script>
<style scoped>
li {
  flex-grow: 1;
}

.book-box {
  width: fit-content;
  display: flex;
  padding: 1.25rem;
  gap: 0.25em;
  flex-flow: row wrap;
  background-color: var(--card-background-color);
}

.book-box button {
  max-width: 8em;
  font-size: 0.9em;
}
.book-details {
  width: 12.5rem;
  display: flex;
  gap: 0.25em;
  flex: 1 1 auto;
  flex-flow: column wrap;
  justify-content: flex-start;
}

.book-description {
  margin-bottom: 10px;
}

.book-details button {
  max-width: 8em;
  font-size: 0.9em;
}
.book-details button {
  max-width: 8em;
  font-size: 0.9em;
}

img {
  height: 200px;
  width: 135px;
  border: 2px solid var(--book-image-border);
}

.s-button {
  padding: 0.5em 0;
  max-width: 8em;
  font-size: 0.9em;
  transform: translateX(5em);
  transform: translateY(-2.5em);
}
</style>

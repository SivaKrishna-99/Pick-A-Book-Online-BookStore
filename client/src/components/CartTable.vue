<template>
  <div class="cart-table">
    <br />
    <span class="cart-table-title"><b>Your Shopping Cart</b></span>
    &nbsp;&nbsp;(<a @click="$store.dispatch('clearCart')">Clear Cart</a>)
    <br /><br />
    <table>
      <tr class="cart-table-header">
        <th></th>
        <th>Title</th>
        <th>Quantity</th>
        <th>Price</th>
      </tr>
      <tr v-for="item in $store.state.cart.items" :key="item.book.bookId">
        <td>
          <img
            :src="
              require('@/assets/images/books/' + bookImageFileName(item.book))
            "
            :alt="item.book.title"
            height="10"
          />
        </td>
        <td>
          <div class="book-title">{{ item.book.title }}</div>
          <div class="book-author">{{ item.book.author }}</div>
          <div class="book-price">
            {{ item.book.price | asDollarsAndCents }}
          </div>
        </td>
        <td>
          <input
            min="0"
            type="number"
            v-model.trim.number.lazy="item.quantity"
            @change="updateCart({ book: item.book, quantity: item.quantity })"
          />&nbsp;&nbsp;
        </td>
        <td>{{ item.total | asDollarsAndCents }}</td>
      </tr>
    </table>
    <div class="cart-table-buttons">
      <router-link
        :to="
          $store.state.selectedCategoryName
            ? '/category/' + $store.state.selectedCategoryName
            : '/category/Mystery/'
        "
        class="secondary-button"
        tag="button"
        >Continue Shopping</router-link
      >
    </div>
    <br />
  </div>
</template>

<script>
export default {
  name: "CartTable",
  methods: {
    bookImageFileName: function (book) {
      let name = book.title.toLowerCase();
      name = name.replace(/ /g, "-");
      return `${name}.jpeg`;
    },
    updateCart: function (payload) {
      if (payload.quantity === 0 || payload.quantity === "") {
        this.removeBook(payload.book);
      } else {
        this.$store.dispatch("updateCart", payload);
      }
    },
    removeBook: function (book) {
      this.$store.dispatch("removeBook", book);
    },
  },
};
</script>

<style scoped>
.cart-table {
  padding: 1rem;
  background-color: var(--card-background-color);
  flex-grow: 1;
}

.cart-table-title {
  font-size: 1.15em;
}

.cart-table-buttons {
  flex-direction: row;
  justify-content: flex-end;
  padding-top: 2rem;
  display: flex;
}

table {
  width: 100%;
  border-spacing: 0 1rem;
}

tr td,
tr th {
  padding: 0 1rem;
  padding-bottom: 1rem;
  border-bottom: 1px solid grey;
}

.cart-table-header {
  margin-bottom: 1rem;
}

td {
  vertical-align: top;
}

td:nth-child(1) {
  width: 120px;
}

th:nth-child(2),
td:nth-child(2) {
  text-align: left;
}

th:nth-child(3),
td:nth-child(3) {
  text-align: center;
}

th:last-child,
td:last-child {
  text-align: right;
}

img {
  height: 140px;
}

.book-title {
  font-family: var(--book-title-font-family);
  font-weight: bold;
}

.book-author {
  font-style: italic;
}

input {
  text-align: right;
  background: #ffffff;
  border: 2px solid #a2a2a2;
  border-radius: 0.75rem;
  width: 6rem;
  padding: 0.2rem 0.9rem;
}

input:focus {
  border: 2px solid #a2a2a2;
  border-radius: 0.75rem;
  box-shadow: 0 0 0.2rem 0.2rem var(--button-color-dark);
  outline: none;
}

i {
  color: #ff3d3d;
}
</style>

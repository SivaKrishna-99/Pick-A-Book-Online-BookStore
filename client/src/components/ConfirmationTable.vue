<template>
  <table>
    <tr class="tableHeaders">
      <td>Title</td>
      <td>Quantity</td>
      <td>Price</td>
    </tr>
    <tr v-for="(item, index) in orderDetails.lineItems" :key="item.productId">
      <td>
        {{ orderDetails.books[index].title }}
      </td>
      <td>{{ item.quantity }}</td>
      <td>
        {{
          (orderDetails.books[index].price * item.quantity) | asDollarsAndCents
        }}
      </td>
    </tr>
  </table>
</template>

<script>
import { mapState } from "vuex";

export default {
  name: "ConfirmationTable",
  computed: {
    ...mapState(["orderDetails"]),
  },
};
</script>

<style scoped>
table {
  border: 1px black solid;
  margin-bottom: 1em;
  width: 100%;
  margin-top: 1em;
}

td {
  display: table-cell;
  vertical-align: middle;
  padding: 0.5em 0.5em;
  background-color: sandybrown;
}

td:nth-child(1) {
  text-align: left;
}

td:nth-child(2) {
  text-align: center;
}

td:nth-child(3) {
  text-align: right;
}

.tableHeaders {
  font-weight: bold;
}
.tableHeaders > td {
  text-align: center;
}
</style>

<template>
  <div class="cart-page">
    <template v-if="$store.state.cart.empty">
      <div class="empty-cart">
        <br />
        <div>Your Shopping Cart is empty.</div>
        <br />
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
    </template>
    <template v-else>
      <div class="cart-details">
        <cart-table></cart-table>
        <div class="cart-summary">
          <div>
            <table>
              <tr>
                <td style="width: 188px">
                  Subtotal ({{
                    $store.state.cart.numberOfItems +
                    " book" +
                    ($store.state.cart.numberOfItems > 1 ? "s" : "") +
                    "):"
                  }}
                </td>
                <td>
                  {{ $store.state.cart.subtotal | asDollarAndCents }}
                </td>
              </tr>
              <tr>
                <td>Total:</td>
                <td>
                  <b>{{ $store.state.cart.total | asDollarAndCents }}</b>
                </td>
              </tr>
            </table>

            <br />
            <router-link class="button" tag="button" to="/checkout"
              >Proceed to Checkout</router-link
            >
          </div>
        </div>
      </div>
      <div></div>
    </template>
  </div>
</template>

<script>
import CartTable from "@/components/CartTable";
import { PriceFormatter } from "@/main";

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Cart",
  components: { CartTable },
  methods: {},
  filters: {
    asDollarAndCents(cents) {
      return PriceFormatter.format(cents / 100.0);
    },
  },
};
</script>

<style scoped>
.cart-page {
  padding: 1rem;
  flex-grow: 1;
}

.empty-cart {
  text-align: center;
  width: fit-content;
  font-size: 1.15em;
  margin: 0 auto;
}

.cart-details {
  display: flex;
  flex-direction: row;
  gap: 1rem;
  justify-content: space-between;
}

.cart-summary {
  padding: 1rem;
  height: fit-content;
  background-color: var(--card-background-color);
  text-align: right;
}
</style>

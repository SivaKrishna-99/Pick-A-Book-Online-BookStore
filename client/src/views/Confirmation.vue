<template>
  <div class="confirmationView">
    <div v-if="orderDetails">
      <div class="orderConfirmation">
        <h2>Your Order Has Been Confirmed</h2>
        <div class="customerOrderDetails">
          <ul class="customerOrderDetailsList">
            <li>
              Confirmation #:
              {{ $store.state.orderDetails.order.confirmationNumber }}
            </li>
            <li>Time: {{ orderTime() }}</li>
          </ul>
        </div>
        <div class="customerPaymentDetails">
          <ul class="customerPaymentDetailsList">
            <li class="infoHeader">Payment Info</li>
            <li>{{ ccNumber() }}</li>
            <li>{{ expireDate() }}</li>
            <li>
              Charged:
              <span style="font-weight: bold">{{
                orderDetails.order.amount | asDollarsAndCents
              }}</span>
            </li>
          </ul>
          <div class="customerDetails">
            <ul>
              <li class="infoHeader">Customer Details</li>
              <li>{{ orderDetails.customer.customerName }}</li>
              <li>{{ orderDetails.customer.address }}</li>
              <li>{{ orderDetails.customer.email }}</li>
              <li>{{ orderDetails.customer.phone }}</li>
            </ul>
          </div>
        </div>
        <div class="confirmationTable">
          <confirmation-table class="confirmationTable"> </confirmation-table>
        </div>
      </div>
    </div>
    <div v-else>
      <br />
      <h1>You Have Not Ordered Anything. Please Do!</h1>
      <br />
    </div>
    <button class="button shopping" @click="homePage">Continue Shopping</button>
  </div>
</template>

<script>
import ConfirmationTable from "@/components/ConfirmationTable";
import { mapState } from "vuex";
export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Confirmation",
  computed: {
    ...mapState(["orderDetails"]),
  },
  components: { ConfirmationTable },
  methods: {
    ccNumber: function () {
      var str = this.$store.state.orderDetails.customer.ccNumber;
      return "**** **** **** " + str.substring(str.length - 4);
    },
    expireDate: function () {
      var date = new Date(this.$store.state.orderDetails.customer.ccExpDate);
      return date.getMonth() + 1 + "/" + date.getFullYear();
    },
    orderTime: function () {
      var date = new Date(this.$store.state.orderDetails.order.dateCreated);
      return date.toLocaleString();
    },
    homePage: function () {
      this.$router.push("/");
    },
  },
};
</script>

<style scoped>
.confirmationView {
  display: flex;
  margin: 1em auto;
  flex-direction: column;
  align-items: center;
}
ul {
  text-align: left;
}
.confirmationTable {
}
ul > li {
  margin: 0.25em;
}

.customerPaymentDetails {
  display: flex;
  justify-content: space-evenly;
  flex-direction: row;
}

.orderConfirmation {
  display: flex;
  flex-direction: column;
}
.customerOrderDetailsList {
  text-align: center;
}
.customerPaymentDetailsList {
}
.infoHeader {
  text-align: center;
  font-weight: bold;
}
</style>

<template>
  <div class="checkout-page">
    <section v-show="checkoutStatus != ''" class="checkoutStatusBox">
      <div v-if="checkoutStatus == 'ERROR'">
        Error: Please fix the issues and try again.
      </div>

      <div v-else-if="checkoutStatus == 'PENDING'">Processing..</div>
      <div v-else-if="checkoutStatus == 'OK'">Order Placed.</div>
      <div v-else>An unexpected error occurred,try Again.</div>
    </section>

    <section class="checkout-page-body" v-if="!cart.empty">
      <form @submit.prevent="submitOrder">
        <div class="form-input">
          <div class="label">
            <label for="name">Name</label>
            <input
              type="text"
              size="20"
              id="name"
              name="name"
              v-model.lazy="$v.name.$model"
            />
          </div>
          <div>
            <template v-if="$v.name.$error">
              <span class="error" v-if="!$v.name.required"
                >Name is required</span
              >
              <span class="error" v-else-if="!$v.name.minLength">
                Name must have at least
                {{ $v.name.$params.minLength.min }} letters.
              </span>
              <span class="error" v-else-if="!$v.name.maxLength">
                Name can have at most
                {{ $v.name.$params.maxLength.max }} letters.
              </span>
              <span class="error" v-else> An unexpected error occurred. </span>
            </template>
          </div>
        </div>
        <div class="form-input">
          <div class="label">
            <label for="address">Address</label>
            <input
              type="text"
              size="20"
              height="40"
              id="address"
              name="name"
              v-model.lazy="$v.address.$model"
            />
          </div>
          <div>
            <template v-if="$v.address.$error">
              <span class="error" v-if="!$v.address.required"
                >Address is required</span
              >
              <span class="error" v-else-if="!$v.address.minLength">
                Address must have at least
                {{ $v.address.$params.minLength.min }} letters.
              </span>
              <span class="error" v-else-if="!$v.address.maxLength">
                Address can have at most
                {{ $v.address.$params.maxLength.max }} letters.
              </span>
              <span class="error" v-else> An unexpected error occurred. </span>
            </template>
          </div>
        </div>

        <div class="form-input">
          <div class="label">
            <label for="phone">Phone</label>
            <input
              class="textField"
              type="text"
              size="20"
              id="phone"
              name="phone"
              v-model.lazy="$v.phone.$model"
            />
          </div>

          <div>
            <template v-if="$v.phone.$error">
              <span class="error" v-if="!$v.phone.required"
                >Phone Number is required</span
              >
              <span class="error" v-else-if="!$v.phone.phone">
                Please Enter Valid US Number
              </span>
              <span class="error" v-else> An unexpected error occurred. </span>
            </template>
          </div>
        </div>
        <div class="form-input">
          <div class="label">
            <label for="email">Email</label>
            <input
              type="text"
              size="20"
              id="email"
              name="email"
              v-model.lazy="$v.email.$model"
            />
          </div>
          <!-- DONE: Add email validation message(s) -->
          <div>
            <template v-if="$v.email.$error">
              <span class="error" v-if="!$v.email.required"
                >E-mail is required</span
              >
              <span class="error" v-else-if="!$v.email.email">
                Please Enter Valid E-mail
              </span>
              <span class="error" v-else> An unexpected error occurred. </span>
            </template>
          </div>
        </div>

        <div class="form-input">
          <div class="label">
            <label for="ccNumber">Credit card</label>
            <input
              type="text"
              size="20"
              id="ccNumber"
              name="ccNumber"
              v-model.lazy="$v.ccNumber.$model"
            />
          </div>
          <!-- DONE: Add credit card validation message(s) -->
          <div>
            <template v-if="$v.ccNumber.$error">
              <span class="error" v-if="!$v.ccNumber.required"
                >Credit Card is required</span
              >
              <span class="error" v-else-if="!$v.ccNumber.creditCard">
                Please Enter Valid Credit Card Number
              </span>
              <span class="error" v-else> An unexpected error occurred. </span>
            </template>
          </div>
        </div>

        <div class="expiration">
          <div class="label">
            <label>Exp Month</label>
            <select v-model="ccExpiryMonth">
              <option
                v-for="(month, index) in months"
                :key="index"
                :value="index + 1"
              >
                {{ month }} ({{ index + 1 }})
              </option>
            </select>
          </div>

          <div class="label">
            <label>Exp Year</label>
            <select>
              <option v-for="index in 15" :key="index" :value="index + 21">
                {{ index + 21 }}
              </option>
            </select>
          </div>
        </div>

        <section class="checkout-details">
          <div>
            Your Credit card will be charged
            <strong class="amount">
              ${{ ((cart.subtotal + cart.surcharge) / 100).toFixed(2) }}</strong
            >
          </div>
          <div>
            (
            <strong class="amount">
              ${{ (cart.subtotal / 100).toFixed(2) }}</strong
            >
            +
            <strong class="amount">
              ${{ (cart.surcharge / 100).toFixed(2) }}</strong
            >
            surcharge )
          </div>
        </section>

        <input
          type="submit"
          name="submit"
          class="button"
          :disabled="checkoutStatus === 'PENDING'"
          value="Complete Purchase"
        />
      </form>
    </section>
    <section class="empty-cart" v-else>
      <div>
        <br /><br />
        <h2>Your Cart is Empty</h2>
        <br />
        <br />
      </div>
      <div>
        <button class="button shopping" @click="latestCategory">
          Continue Shopping
        </button>
      </div>
      <br />
      <br />
    </section>
  </div>
</template>

<script>
import {
  required,
  email,
  minLength,
  maxLength,
} from "vuelidate/lib/validators";

import isMobilePhone from "validator/lib/isMobilePhone";
import isCreditCard from "validator/lib/isCreditCard";

const phone = (value) => isMobilePhone(value, "en-US");
const creditCard = (value) => isCreditCard(value);

export default {
  // eslint-disable-next-line vue/multi-word-component-names
  name: "Checkout",
  data() {
    return {
      name: "",
      address: "",
      phone: "",
      email: "",
      ccNumber: "",
      ccExpiryMonth: new Date().getMonth() + 1,
      ccExpiryYear: new Date().getFullYear(),
      checkoutStatus: "",
    };
  },
  validations: {
    name: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(45),
    },
    address: {
      required,
      minLength: minLength(4),
      maxLength: maxLength(45),
    },
    phone: {
      required,
      phone,
    },
    email: {
      required,
      email,
    },
    ccNumber: {
      required,
      creditCard,
    },
  },

  computed: {
    cart() {
      return this.$store.state.cart;
    },
    months() {
      return [
        "January",
        "February",
        "March",
        "April",
        "May",
        "June",
        "July",
        "August",
        "September",
        "October",
        "November",
        "December",
      ];
    },
  },

  methods: {
    latestCategory: function () {
      let path = this.$store.state.selectedCategoryName;
      if (path !== "") {
        this.$router.push("category/" + this.$store.state.selectedCategoryName);
      } else {
        this.$router.push("/");
      }
    },
    submitOrder() {
      console.log("Submit order");
      this.$v.$touch(); // Ensures validators always run
      if (this.$v.$invalid) {
        this.checkoutStatus = "ERROR";
      } else {
        this.checkoutStatus = "PENDING";
        this.$store
          .dispatch("placeOrder", {
            name: this.name,
            address: this.address,
            phone: this.phone,
            email: this.email,
            ccNumber: this.ccNumber,
            ccExpiryMonth: this.ccExpiryMonth,
            ccExpiryYear: this.ccExpiryYear,
          })
          .then(() => {
            this.checkoutStatus = "OK";
            this.$router.push({ name: "confirmation" });
          })
          .catch((reason) => {
            this.checkoutStatus = "SERVER_ERROR";
            console.log("Error placing order", reason);
          });
      }
    },
  },
};
</script>

<style scoped>
.checkout-page {
  display: flex;
  flex-direction: column-reverse;
  background: var(--primary-background-color);
  color: #863fb8;
  margin-right: auto;
  margin-left: auto;
}
.checkout-page-body {
  display: flex;
  padding: 1em;
  align-self: center;
}

form {
  display: flex;
  flex-direction: column;
}

form > div {
  display: flex;
  justify-content: flex-end;
  margin-bottom: 0.5em;
}

form > div > input,
form > div > select {
  margin-left: 0.5em;
}

.error {
  color: red;
  text-align: right;
}

.checkoutStatusBox {
  margin: 1em;
  padding: 1em;
  text-align: center;
}
.expiration {
  display: flex;
}
.expiration > .label > label {
  padding: 0.25em;
}

.empty-cart {
  align-self: center;
}

.form-input {
  display: flex;
  flex-direction: column;
  align-content: space-between;
  text-align: right;
}

.label {
  display: flex;
  justify-content: flex-end;
}

.label > label:after {
  content: "*";
  color: red;
  margin-right: 0.25em;
}

.checkout-details {
  text-align: center;
  padding: 0.5rem;
}

.label > input,
select {
  color: black;
  border-radius: 0.25em;
}
.amount {
  color: black;
}
</style>

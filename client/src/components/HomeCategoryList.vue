<template>
  <ul>
    <router-link to="../category/fiction" tag="li">
      <img
        src="@/assets/images/categories/fiction.jpg"
        alt="fiction category"
      />
      <div>fiction</div>
    </router-link>

    <router-link to="../category/mystery" tag="li">
      <img
        src="@/assets/images/categories/mystery.jpg"
        alt="mystery category"
      />
      <div>Mystery</div>
    </router-link>

    <router-link to="../category/romance" tag="li">
      <img
        src="@/assets/images/categories/romance.jpg"
        alt="romance category"
      />
      <div>Romance</div>
    </router-link>
  </ul>
</template>

<script>
export default {
  name: "HomeCategoryList",
};
</script>

<style scoped>
ul {
  display: flex;
  gap: 1em;
  flex-direction: row;
  flex-wrap: wrap;
}

li {
  text-align: center;
  cursor: pointer;
}

li div {
  padding: 0.5em 0;
  transform: translateY(-2.25em);
  margin-bottom: -2em;
  background: rgba(0, 0, 0, 0.5); /* last # is percent opacity */
  color: white;
}
</style>

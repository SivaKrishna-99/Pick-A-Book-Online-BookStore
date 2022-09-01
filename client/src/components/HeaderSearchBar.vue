<template>
  <div>
    <form>
      <input type="text" placeholder="Search For Books.." />
      <router-link to="../category/" class="icon-button" tag="button">
        <i class="fas fa-search"></i>
      </router-link>
    </form>
  </div>
</template>

<script>
export default {
  name: "HeaderSearchBar",
};
</script>

<style scoped>
div {
  display: flex;
  flex-flow: row wrap;
  justify-content: center;
  align-items: center;
  margin-right: 20px;
}

div .icon-button {
  transform: translateX(-2.25em);
}

input {
  width: 16em;
  border: 2px solid #a2a2a2;
  border-radius: 1.5em;
  padding: 0.5em 2.2em 0.5em 0.9em;
  background: #ffffff;
}

input:focus {
  border: 2px solid #a2a2a2;
  outline: none;
  border-radius: 1.5em;
  box-shadow: 0 0 0.2em 0.2em var(--button-color-dark);
}
</style>

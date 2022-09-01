<template>
  <footer class="container">
    <div class="social-media-icons">
      <a href="https://www.facebook.com/">
        <i class="fab fa-facebook-square"></i
      ></a>
      <a href="https://www.twitter.com/">
        <i class="fab fa-twitter-square"> </i
      ></a>
    </div>
    <div class="links">
      <span>
        <router-link to="/">About</router-link> |
        <router-link to="/">Contact</router-link> |
        <router-link to="/">Directions</router-link>
      </span>
    </div>
    <div class="copyright">&copy; 2022 Pick-A-Book</div>
  </footer>
</template>

<script>
export default {
  name: "AppFooter",
};
</script>

<style scoped>
footer {
  background: var(--secondary-background-color);
  display: flex;
  flex-flow: row wrap;
  justify-content: space-between;
  gap: 1em;

  position: fixed;
  bottom: 0;
  width: 100%;
  height: 3rem;
}

.links {
  margin-left: 5rem;
}

.copyright,
.links,
.social-media-icons {
  display: flex;
  flex-direction: row;
}

.social-media-icons {
  text-align: center;
  gap: 0.5em;
}

.social-media-icons a {
  color: var(--default-text-color);
}
.social-media-icons a:hover {
  color: var(--primary-color);
}
</style>

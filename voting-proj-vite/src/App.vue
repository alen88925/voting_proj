<script setup>
import { ref, onMounted } from 'vue'
import axios from 'axios'

const msg = ref("")

onMounted(async () => {
  const res = await axios.get("http://localhost:8080/hello")
  msg.value = res.data
})
</script>

<template>
  <h1>{{ msg }}</h1>
</template>
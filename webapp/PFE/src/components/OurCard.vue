<template>
  <div class="card">
    <div class="card-header">
      <h2 class="card-title">{{ title }}</h2>
      <p v-if="subtitle" class="card-subtitle">{{ subtitle }}</p>
    </div>
    <div class="card-content">
      <slot></slot>
    </div>
  </div>
</template>

<script setup>
defineProps({
  title: {
    type: String,
    required: true
  },
  subtitle: {
    type: String,
    default: ''
  }
});
</script>

<style scoped>
.card {
  background: white;
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  width: 100%;
}

.card-header {
  background: linear-gradient(to bottom, #004851 0%, #004851 100%);
  color: white;
  padding: 1.5rem;
  text-align: center;
}

.card-title {
  font-size: 1.5rem;
  font-weight: 600;
  margin: 0;
}

.card-subtitle {
  margin: 0.5rem 0 0;
  font-size: 0.875rem;
  opacity: 0.9;
}

.card-content {
  padding: 1.5rem;
  background: white;
}
</style>
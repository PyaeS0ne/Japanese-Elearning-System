<template>
  <!-- SPA - Single Page Application -->
  <v-app>
    <v-main>
      <heading></heading>
      
      <v-row ><v-col cols="12" class="mb-12"></v-col> </v-row>
      <router-view class="mx-0 my-0" style="height:100%" :key="$route.fullPath"/>
     
    </v-main>
  </v-app>
</template>

<script>
import heading from "./components/heading.vue";



export default {

  
  name: "App",

  components: {
    heading,
    
    
  },

  data: () => ({}),
};


</script>


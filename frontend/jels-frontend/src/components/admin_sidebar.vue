<template>
  
  <div class="fixed-component">
    <v-card color="#000000" class="mx-auto " width="190" >
      
        <v-list-item style="height: 70px;">
              <v-list-item-content >
                <v-list-item-title :style="{ color: '#ffd343' }" class="font-weight-bold text-h5 text-center" >
                   Dashboard
                </v-list-item-title>
                <!-- <v-list-item-subtitle class="font-weight-regular text-h6">Manage </v-list-item-subtitle>-->
              </v-list-item-content>
        </v-list-item>
    
        <v-divider></v-divider>
        <!-- dense nav -->
        <v-img src="../assets/adminSiber3.jpg">
       <!-- <v-list style="height: 800px;" dense nav >-->
        <v-list-item dense nav
          v-for="item in menuItemList"
          :key="item.title"
          link
          @click="goToRoute(item.path)"
        >
        <v-row ><v-col cols="3" class="mt-5">
          <!-- Icon -->
          <v-list-item-icon >
            <svg-icon :style="{ color: 'white' }" type="mdi" :path="item.icon" style=" width: 30px; height: 20px;"></svg-icon>
          </v-list-item-icon>
        </v-col> 
          <!-- Title -->
          <v-col cols="9" class="mt-5">
          <v-list-item-content>
            <v-list-item-title :style="{ color: 'white' }" class="font-weight-bold" style=" text-align: left;">{{ item.title }}</v-list-item-title>
          </v-list-item-content>
        </v-col>
        </v-row>
        </v-list-item>
    </v-img>
    </v-card>

  </div>
  </template>

<script>
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiViewList } from '@mdi/js';
import { mdiBook } from '@mdi/js';
import { mdiAccountMultiple,mdiAccountSupervisor } from '@mdi/js';
export default {
  name: 'admin_sidebar',
  components:{SvgIcon},

  data: () => ({
    menuItemList: [
    

    {
        title: "Course List",
        icon: mdiViewList,
        path: "/admin",
      },
      {
        title: "Knowledge Posts",
        icon: mdiViewList,
        path: "/admin/knowledge_list",
      },
      {
        title:"Test List",
        icon: mdiBook,
        path: "/admin/manage_test",
      },

      {
        title: "Admin List",
        icon: mdiAccountSupervisor,
        path: "/admin/admin_list",
      },
      {
        title:"User List",
        icon: mdiAccountMultiple,
        path: "/admin/user_list",
      },


      
    ],
  }),

  //

  methods:{
    goToRoute(path) {
      // If Current Path is same with Clicked Path, No Go to Route
      if (this.$route.path != path) {
        this.$router.push({ path: path });
      }
    },
  }
}
</script>

<style scoped>
.custom-card {
background-color: #ffd343; /* Replace with your desired color */
}
.image-container {
  background-image: url('../assets/adminSiber1.jpg');
  background-size: cover;
  background-position: center;
  /* width: 100%;
  height: 100vh; Adjust the height as needed */
}
.fixed-component{
  position: fixed;
}




</style>
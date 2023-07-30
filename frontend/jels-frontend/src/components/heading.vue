<template >
    <!-- yellow #ffd32c blue #29c0e2 light blue #29d6e5 transparent-->
    <v-app-bar  class="heading appBarClass" :style="appBarStyle" @scroll="handleScroll" 
    
    >
  <a  href="/">  <v-img src="../assets/logo.png" width="90"/></a>
    
  <v-spacer></v-spacer>

  <!-- Home -->
  <router-link  class="mx-2 navlink "   to="/" ><span :class="spanClass">Home</span></router-link>

    <!-- course -->
    <router-link  class="mx-2 navlink"  to="/course"><span :class="spanClass">Course</span></router-link>

   <!-- My Course -->
      
    <router-link v-if="isLogin && loginUser.role==='user' " class="mx-2 navlink"  to="/mycourse"
    ><span :class="spanClass">Enrolled Course</span></router-link>

   

    <!-- knowledge -->
    <router-link  class="mx-2 navlink"  to="/knowledge"><span :class="spanClass">Knowledge</span></router-link>

      <!-- Test -->
      <router-link  class="mx-2 navlink"  to="/test"><span :class="spanClass">Test</span></router-link>
    
    <!-- Contact -->
    <router-link v-if=" !isLogin ||  loginUser.role==='user'" class="mx-2 navlink"  to="/contact"><span :class="spanClass">Contact</span></router-link>
    
    <!-- Admin -->
    <router-link v-if=" isLogin &&  loginUser.role==='admin'" 
    class="mx-2 navlink" to="/admin"
    > <span :class="spanClass">Admin</span></router-link>


  <!-- Register -->
  <span v-if="!isLogin">   </span>
  <router-link v-if="!isLogin" class="mx-2 navlink" to="/register"
    ><v-btn class="rounded-btn custom-button" :style="{ 'background-color': backgroundColor }"> <span :class="spanClass">Register</span> </v-btn></router-link
  >

   <!-- Login -->
   <span v-if="!isLogin">  </span>
  <router-link v-if="!isLogin" class="mx-2 navlink" to="/login"
    ><v-btn class="rounded-btn custom-button" :style="{ 'background-color': backgroundColor }"> <span :class="spanClass">Login</span> </v-btn></router-link>

 <!-- Profile -->

 <span v-if="isLogin">  </span>

<router-link v-if="isLogin && loginUser.role==='user'" class="mx-2 navlink" to="/profile" >
 <svg-icon type="mdi" :path="path" :style="{ color: iconColor , width: iconSize + 'px', height: iconSize + 'px'}"></svg-icon></router-link >
  
 <router-link v-if="isLogin && loginUser.role==='admin'" class="mx-2 navlink" to="/admin/profile" >
 <svg-icon type="mdi" :path="path" :style="{ color: iconColor , width: iconSize + 'px', height: iconSize + 'px'}"></svg-icon></router-link >

      <!-- Login out -->
  <span v-if="isLogin"> </span>
  <a v-if="isLogin" class="mx-2 navlink" @click="logout()"><v-btn class="rounded-btn custom-button" :style="{ 'background-color': backgroundColor }"><span :class="spanClass">Logout</span></v-btn></a>
  </v-app-bar>

</template>

<script>
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiAccountCircle } from '@mdi/js';


export default {
  name: "heading",
  components: {
    SvgIcon
  },

  data: () => ({
    loginUser: {},
    isLogin: false,

    path: mdiAccountCircle,
    iconColor: 'black',
    iconSize: 35,
    isScrolled: false,

    isScrolling: false,
    backgroundColor: 'transparent',
  }),

  computed: {

spanClass() {
  return this.isScrolled ? 'scrolled-text' : 'not-scrolled-text';
},
/*
appBarClass() {
  return this.isScrolled ? 'scrolled-bar' : 'not-scrolled-bar';
},*/
appBarStyle() {
  if (this.isScrolled) {
    return {
      backgroundColor: '#ffd343', // Set your scrolled background color here
    };
  } else {
    return {
      backgroundColor: '#ffd343', // Set your initial background color here
    };
  }
},
},

  created() {
 // Login User
 this.loginUser = this.$store.state.loginUser;
    this.$store.watch(
      () => {
        return this.$store.state.loginUser;
      },
      (newVal,oldVal) => {
        this.loginUser = newVal;
      },
      {
        deep: true,
      }
    );

    // IsLogin
    this.isLogin = this.$store.state.isLogin;
    this.$store.watch(
      () => {
        return this.$store.state.isLogin;
      },
      (newVal,oldVal) => {
        this.isLogin = newVal;
      },
      {
        deep: true,
      }
    );
  },

  mounted() {
    window.addEventListener('scroll', this.handleScroll);
  },
  beforeDestroy() {
    window.removeEventListener('scroll', this.handleScroll);
  },

  methods: {
    async logout() {
      await this.$store.commit("logout");
      // If current path is not home page, Go to home page
      if (this.$route.path != "/") {
        this.$router.push({ path: "/" });
      }
      
      window.location.reload();

    },
    handleScroll() {
      const scrollPosition = window.pageYOffset || document.documentElement.scrollTop;
      this.isScrolled = scrollPosition > 0;
    },
  },
};
</script>

<style scoped>


.navlink {
  color: rgb(8, 8, 8) !important;
  text-decoration: none;
}

.navlink:hover {
  cursor: pointer;
  text-decoration: underline;
}
.heading {
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  z-index: 1;
}
.rounded-btn {
  border-radius: 999px; /* Adjust the value to change the roundness */
  position: relative;
  padding: 12px 24px;
  color: inherit;
  cursor: pointer;
 
}
.custom-button {
  border: 1px solid rgb(247, 241, 241);
}

.not-scrolled-text {
  color: #000000; /* Set your initial text color here */
}

.scrolled-text {
  color: #000000; /* Set your scrolled text color here */
}

/* not use */
.not-scrolled-bar {
  background-color: #ffd343; /* Set your initial background color here */
}

.scrolled-bar {
  background-color: #000000; /* Set your scrolled background color here */
}

</style>
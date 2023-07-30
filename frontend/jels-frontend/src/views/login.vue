<template>
  <div>
    <v-container>
      <!-- Login Form -->
      <v-form ref="loginForm" v-model="loginForm">
        <v-row align="center" justify="center" width="680">
          <v-col cols="12" sm="10">
            <v-card class="elevation-6 mt-10">
              <v-window v-model="loginForm">
                <v-window-item :value="1">
                  <v-row>
                    <v-col cols="12" sm="6">
                      <v-card-text class="mt-12">
                        <h2 class="text-center">Login To Your Account</h2>
                        <h5 class="text-center grey--text">Login to your account so you can continue building
                          <br>and editing your onboarding flows
                        </h5>
                        <v-row align="center" justify="center">
                          <v-col cols="12" sm="8">
                            <v-row> <svg-icon type="mdi" :path="path"></svg-icon></v-row>
                            <v-row>
                              <v-text-field v-model="email" :rules="[(v) => !!v || 'Required',
                              (v) => /.+@.+\..+/.test(v) || 'E-mail must be valid',]" label="E-mail"
                                required></v-text-field>
                            </v-row>
                            <v-row><svg-icon type="mdi" :path="path1"></svg-icon></v-row>
                            <v-row>
                              <v-text-field v-model="password" :counter="8" :rules="[
                                (v) => !!v || 'Required',
                                (v) =>
                                  (v && v.length <= 8) ||
                                  'Password must be less than 8 characters',]"
                                :type="passwordShow ? 'text' : 'password'"
                                :append-icon="passwordShow ? 'mdi-eye' : 'mdi-eye-off'"
                                @click:append="passwordShow = !passwordShow" label="Password"></v-text-field>
                            </v-row>
                            <v-row>
                              <router-link class="mx-2 navlink" color="red" to="/forgetPassword">Forget
                                Password</router-link>
                            </v-row><br />
                            <v-btn color="#ffd343" class="rounded-btn custom-button ml-4" @click="login()">
                              <span v-if="!loading">Login</span>
                              <v-progress-circular v-else indeterminate color="primary">
                              </v-progress-circular>
                            </v-btn>
                            <div>
                              <v-img height="150" align="center" justify="center" width="200" src="../assets/study.gif"></v-img>
                            </div>
                          </v-col>
                        </v-row>
                      </v-card-text>
                    </v-col>
                    <v-col cols="12" sm="6" class=" rounded-bl-xl">
                      <div style="text-align:center; padding: 180px 0;">
                        <!-- Switch to Register Form -->
                        <v-card-text class="white--text">
                          <h2 class="text-center">Don't Have an Account Yet?</h2>
                          <h5 class="text-center">
                            Let's get you all set up so you can start creating your first onboarding experience
                          </h5>
                        </v-card-text>
                        <div class="text-center">
                          <v-btn color="white" class="rounded-btn custom-button mr-4" @click="register()">
                            <span v-if="!loading">Register</span>
                            <v-progress-circular class="gif" v-else indeterminate color="grey lighten-3">
                            </v-progress-circular>
                          </v-btn>
                        </div>
                      </div>
                    </v-col>
                  </v-row>
                </v-window-item>
              </v-window>
            </v-card>
          </v-col>
        </v-row>

        <!-- Error Msg -->
        <v-alert class="mt-3" v-show="errorAlert" dense type="error">
          Login Failed! <br />
          Email or Password is wrong!
        </v-alert>
      </v-form>
    </v-container>
  </div>
</template>

<script>
import utils from "../utils/utils";
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiEmailHeartOutline } from '@mdi/js';
import { mdiShieldLockOutline } from '@mdi/js';
import { mdiLoginVariant } from '@mdi/js';


export default {
  name: "loginView",

  components: { SvgIcon },

  data()
  {
    return {
      loginForm: false,
      email: "",
      password: "",
      passwordShow: false,
      // email: "admin@gmail.com",
      // password: "11111111",
      errorAlert: false,
      loading: false,
      path: mdiEmailHeartOutline,
      path1: mdiShieldLockOutline,
      path2: mdiLoginVariant,
    };
  },

  async created() { },

  methods: {
    async login()
    {
      if ( this.$refs.loginForm.validate() ) {
        this.errorAlert = false;

        try {
          this.loading = true;
         // console.log( 'Email', this.email );
          //console.log( 'Password', this.password );

          // API Call
          const resp = await utils.http.post( "/user/login", {
            email: this.email,
            password: this.password,
          } );
          if ( resp && resp.status === 200 ) {
            const data = await resp.json();
            if ( data ) {
              // Store Login Info in Vuex
              this.$store.commit( "setLoginUser", data );
              // If Admin -> Go to Admin
              // If User -> Go to Home
              if ( data.role == "admin" ) {
                this.$router.push( { path: "/admin" } );
              } else {
                this.$router.push( { path: "/course" } );
              }
            }
          } else {
            this.errorAlert = true;
          }
        } catch ( error ) {
          //console.log( error );
        } finally {
          this.loading = false;
        }
      }
    },
    register()
    {
      this.$router.push( { path: "/register" } );
    }
  },
};
</script>
<style scoped>
.v-application .rounded-bl-xl {

  background-color: #ffd343;
  border-bottom-left-radius: 300px !important;
}

.rounded-btn {

  border-radius: 999px;
  /* Adjust the value to change the roundness */
  position: relative;
  padding: 12px 24px;
  color: inherit;
  cursor: pointer;

}
.gif{
  
  background-size:  contain;
}

.custom-button {

  border: 1px solid rgb(247, 241, 241);
}
</style>
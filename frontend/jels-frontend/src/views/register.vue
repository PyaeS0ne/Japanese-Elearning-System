<template>
  <div>
    <v-container>
      <!-- Register Form -->
      <v-form ref="registerForm" v-model="registerForm">
        <v-row align="center" justify="center" background-image="url('./assets/E-Learning.jpg')">
          <v-col cols="12" sm="10">
            <v-card class="elevation-6 mt-10">
              <v-window v-model="registerForm">
                <v-window-item :value="2">
                  <v-row>
                    <v-col cols="12" sm="6" class=" rounded-br-xl">
                      <div style="text-align: center; padding: 180px 0;">
                        <v-card-text color="#00000">
                          <h2   class="text-center white--text">Already have an account?</h2>
                          <h5 class="text-center white--text">Signing in to your account so you can continue building and editing your
                            onboarding flows</h5>
                        </v-card-text>
                        <div class="text-center">
                          <v-btn color="white lighten-1" class="rounded-btn custom-button mr-4" @click="login()">
                            <span v-if="!loading">Login</span>
                            <v-progress-circular v-else indeterminate color="primary">
                            </v-progress-circular>
                          </v-btn>
                        </div>
                      </div>
                    </v-col>
                    <v-col cols="12" sm="6">
                      <v-card-text class="mt-12">
                        <h2 class="text-center">Sign up for an account</h2>
                        <h5 class="text-center grey--text">
                          Let's get you all set up so you can start creation your first onboarding experience
                        </h5>
                        <v-row align="center" justify="center">
                          <v-col cols="12" sm="8">
                            <v-text-field v-model="name" :rules="[(v) => !!v || 'Required']" label="Name" 
                              color="error" autocomplete="false" class="mt-16"></v-text-field>
                            <v-text-field v-model="email" :rules="[(v) => !!v || 'Required',
                            (v) => /.+@.+\..+/.test(v) || 'E-mail must be valid',]" label="E-mail"
                              required></v-text-field>
                            <v-btn color="#ffd343" class="rounded-btn custom-button mr-4" @click="register()">
                              <span v-if="!loading">Register</span>
                              <v-progress-circular v-else indeterminate color="primary"></v-progress-circular>
                            </v-btn><br/>
                            <div>
                              <v-img height="150" align="center" justify="center" width="200" src="../assets/study.gif"></v-img>
                            </div>
                          </v-col>
                        </v-row>
                      </v-card-text>
                    </v-col>
                  </v-row>
                </v-window-item>
                <!-- Error Alert -->
                <v-alert class="mt-3" v-show="errorAlert" dense type="error">
                  Sorry Register Failed! Invalid Email or already registered!!
                </v-alert>
                <!-- Success Alert -->
                <v-alert class="mt-3" v-show="OkAlert" dense type="success">
                  Successful Registered ! We sent a login password to your mail! Please Check and Login with that Password
                  !!
                </v-alert>
              </v-window>
            </v-card>
          </v-col>
        </v-row>
      </v-form>
    </v-container>
  </div>
</template>

<script>
import utils from "../utils/utils";

export default {
  name: "registerView",

  components: {},
  data()
  {
    return {
      registerForm: false,

      name: "",
      email: "",
      // name: "Mg Mg",
      // email: "test@gmail.com",

      errorAlert: false,
      OkAlert: false,
      loading: false,
    };
  },

  async created() { },

  methods: {
    async register()
    {
      if ( this.$refs.registerForm.validate() ) {
        this.errorAlert = false;
        this.OkAlert = false;

        try {
          this.loading = true;
         // console.log( 'Name', this.name );
         // console.log( 'Email', this.email );


          // API Call
          const resp = await utils.http.post( "/user/register", {
            name: this.name,
            email: this.email,
          } );
          if ( resp.status === 200 ) {
            const data = await resp.json();
            if ( data ) {
              // Store Login Info in Vuex
              //this.$store.commit("setLoginUser", data);
              this.OkAlert = true;
            }
          } else {
            this.errorAlert = true;
          }
        } catch ( error ) {
         // console.log( error );
        } finally {
          this.loading = false;
        }
      }
    },

    login()
    {
      this.$router.push( { path: "/login" } );
    }
  },
};
</script>
<style scoped>
.v-application .rounded-bl-xl {
  border-bottom-left-radius: 300px !important;
}

.v-application .rounded-br-xl {
  background-color: #ffd343; 
  border-bottom-right-radius: 300px !important;
}

.rounded-btn {
  border-radius: 999px;
  /* Adjust the value to change the roundness */
  position: relative;
  padding: 12px 24px;
  color: inherit;
  cursor: pointer;

}

.custom-button {
  border: 1px solid rgb(247, 241, 241);
}
</style>
  
<template>
  <v-container class="main" id="user-name-change" fluid tag="section">
    <v-row><v-col cols="12" class="mt-15"></v-col></v-row>

    <v-row justify="center">
      <v-col cols="7">
        <div class="text-center text-h5">
          <h1>Forget Password?</h1>
        </div>
      </v-col>
    </v-row>

    <v-row><v-col cols="20"></v-col> </v-row>
    <v-row justify="center">
      <v-col cols="18">
        <v-row justify="center">
          <v-col cols="3">
            <div class="text-center">
              <v-card height="450" width="800" class="rounded-card corner-line-card ">
                <v-card-text>
                  <div>
                    <v-form ref="forgetPasswordForm" v-model="forgetPasswordForm">
                      <div class="text-h7 black--text">
                        <svg-icon type="mdi" :path="path"></svg-icon> Please enter registered email and We will sent a new
                        password
                        <svg-icon type="mdi" :path="path"></svg-icon>
                      </div>
                      <br /><br />
                      <!-- Email -->
                      <v-text-field v-model="email" :rules="[
                        (v) => !!v || 'Required',
                        (v) => /.+@.+\..+/.test(v) || 'E-mail must be valid',
                      ]" label="E-mail" required></v-text-field>

                      <row class="text-center text-h5" align="center" justify="center">
                        <p><v-img width="300" height="100" src="../assets/sendingMail1.gif"></v-img></p>
                      </row>
                      <br />

                      <!-- Forget Password Btn -->
                      <v-btn :disabled="!forgetPasswordForm" color="#ffd343" class="mr-4" @click="forget()">
                        <span v-if="!loading">Send Email</span>
                        <v-progress-circular v-else indeterminate color="primary"></v-progress-circular>
                      </v-btn>
                    </v-form>
                  </div>
                </v-card-text>
                <!-- Error Alert -->
                <v-alert class="mt-3" v-show="errorAlert" dense type="error">
                  Sending Email Failed!
                </v-alert>
                <!-- Success Alert -->
                <v-alert class="mt-3" v-show="successAlert" dense type="success">
                  Sending Email Successful!
                </v-alert>
              </v-card>
            </div>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </v-container>
</template>

<script>
import utils from "../utils/utils";
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiEmoticonExcitedOutline } from '@mdi/js';

export default {
  name: "forget_password_view",

  components: { SvgIcon },

  data()
  {
    return {
      forgetPasswordForm: false,
      email: "",
      // email: "test@gmail.com",
      errorAlert: false,
      successAlert: false,
      loading: false,
      path: mdiEmoticonExcitedOutline,
    };
  },

  async created() { },

  methods: {
    async forget()
    {
      if ( this.$refs.forgetPasswordForm.validate() ) {
        this.errorAlert = false;
        this.successAlert = false;

        try {
          this.loading = true;
         // console.log( 'Email', this.email );


          // API Call
          const resp = await utils.http.forgetPassword( `${ "/user/forgetPassword" }?email=${ this.email }` );
          if ( resp.status === 200 ) {
            const data = await resp.json();
            if ( data ) {
              // Store Login Info in Vuex
              this.successAlert = true;
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
  },
};
</script>
<style scoped>
.rounded-card {
  border-radius: 17px;
}

.corner-line-card {
  position: relative;
  border: 10px solid #ffd343;
  background-color: white;
}

.main {
  background-color: papayawhip;
}
</style>
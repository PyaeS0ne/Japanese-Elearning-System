<template>
  <v-container class="main" id="user-password-change" fluid tag="section">
    <v-row><v-col cols="12" class="mt-15"></v-col></v-row>

    <v-row justify="center">
      <v-col cols="20">
        <div class="text-center text-h5">
          <h1>Change Password</h1>
        </div>
      </v-col>
    </v-row>

    <v-row><v-col cols="20"></v-col> </v-row>
    <v-row justify="center">
      <v-col cols="18">
        <v-row justify="center">
          <v-col cols="10">
            <div class="text-center">
              <v-card height="680" class="rounded-card corner-line-card ">
                <v-card-text>
                  <div>
                    <v-text-field v-show="false" v-model="ori_id" hidden></v-text-field>
                    <div class="text-h5 black--text">
                      <svg-icon type="mdi" :path="path"></svg-icon> Please Enter the current password and type new
                      password you want to change!!
                      <svg-icon type="mdi" :path="path"></svg-icon>
                    </div>
                    <br /><br /><br />

                    <!-- Current Password -->
                    <v-text-field v-model="current_pwd" :counter="8" label="Current Password" required></v-text-field>
                    <br /><br />

                    <!-- New Password -->
                    <v-text-field v-model="new_pwd" :counter="8" label="New Password" required></v-text-field>
                    <br /><br />

                    <!-- Confirm New Password -->
                    <v-text-field v-model="comfirm_new_pwd" :counter="8" label="Confirm New Password"
                      required></v-text-field>
                    <br /><br /><br /><br />

                    <!-- Submit Btn -->
                    <v-btn class="mr-4" color="#ffd343" @click="submit"> submit </v-btn>

                    <!-- Clear Btn -->
                    <v-btn class="mr-4" color="#ffd343" @click="clear"> clear </v-btn>

                    <!-- Cancel Btn -->
                    <v-btn color="#ffd343" @click="cancel"> back to your profile</v-btn>
                  </div>
                </v-card-text>
              </v-card>
            </div>
          </v-col>
        </v-row>
      </v-col>
    </v-row>

    <!-- Error Alert -->
    <v-alert class="mt-3" v-show="errorAlert" dense type="error">
      {{ errMsg }}
    </v-alert>

    <!-- Success Alert -->
    <v-alert class="mt-3" v-show="OkAlert" dense type="success">
      Your New Password Successfully Updated
    </v-alert>
  </v-container>
</template>

<script>
import utils from "../utils/utils";
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiEmoticonExcitedOutline } from '@mdi/js';
export default {
  name: "changePwd",
  components: { SvgIcon },
  data()
  {
    return {
      loginUser: {},
      comfirm_new_pwd: "",
      new_pwd: "",
      ori_id: "",
      current_pwd: "",
      errorAlert: false,
      OkAlert: false,
      errMsg: "",
      path: mdiEmoticonExcitedOutline,
    };
  },

  created()
  {
    this.loginUser = this.$store.state.loginUser;
    this.$store.watch(
      () =>
      {
        return this.$store.state.loginUser;
      },
      ( newVal ) =>
      {
        this.loginUser = newVal;
      },
      {
        deep: true,
      }
    );
  },

  methods: {
    async submit()
    {
      const resp = await utils.http.post( "/user/changePwd", {
        ori_id: this.loginUser.id,
        current_pwd: this.current_pwd,
        new_pwd: this.new_pwd,
        comfirm_new_pwd: this.comfirm_new_pwd,
      } );
      if ( resp.status === 200 ) {
        this.OkAlert = true;
        //this.$router.push({ path: "/profile" });
      } else {
        this.errorAlert = true;
        const data = await resp.json();
        this.errMsg = data.message;
      }
    },

    clear()
    {
      this.current_pwd = "";
      this.new_pwd = "";
      this.con_new_pwd = "";
    },

    cancel()
    {
      if ( this.loginUser.role == 'admin' ) {
        this.$router.push( { path: "/admin/profile" } );
      }
      else {
        this.$router.push( { path: "/profile" } );
      }
    }
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

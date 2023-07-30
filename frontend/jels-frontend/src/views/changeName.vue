<template>
  <v-container class="main" id="user-name-change" fluid tag="section">
    <v-row><v-col cols="12" class="mt-15"></v-col></v-row>

    <v-row justify="center">
      <v-col cols="7">
        <div class="text-center text-h5">
          <h1>Change Name</h1>
        </div>
      </v-col>
    </v-row>

    <v-row><v-col cols="20"></v-col> </v-row>
    <v-row justify="center">
      <v-col cols="18">
        <v-row justify="center">
          <v-col cols="10">
            <div class="text-center">
              <v-card height="480" class="rounded-card corner-line-card ">
                <v-card-text>
                  <div>
                    <v-text-field v-show="false" v-model="ori_id" hidden></v-text-field>
                    <div class="text-h5 black--text">
                      <svg-icon type="mdi" :path="path"></svg-icon> Please type new name you want to change
                      <svg-icon type="mdi" :path="path"></svg-icon>
                    </div>
                    <br /><br /><br />
                    <!-- New Name -->
                    <v-text-field v-model="new_name" label="New Name" required></v-text-field>
                    <br /><br /><br /><br /><br /><br />

                    <!-- Submit Btn -->
                    <v-btn class="mr-4" color="#ffd343" @click="submit"> submit </v-btn>

                    <!-- Clear Btn -->
                    <v-btn class="mr-4" color="#ffd343" @click="clear"> clear </v-btn>

                    <!-- Cancel Btn -->
                    <v-btn color="#ffd343" @click="cancel"> back to your profile </v-btn>
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
      Your New Name Successfully Updated
    </v-alert>
  </v-container>
</template>

<script>
import utils from "../utils/utils";
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiEmoticonExcitedOutline } from '@mdi/js';
export default {
  name: "changeName",
  components: { SvgIcon },
  data()
  {
    return {
      new_name: "",
      ori_id: "",
      errorAlert: false,
      OkAlert: false,
      errMsg: "",
      path: mdiEmoticonExcitedOutline,
    };
  },

  async created()
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
      const resp = await utils.http.post( "/user/changeName", {
        ori_id: this.loginUser.id,
        new_name: this.new_name,
      } );
      if ( resp.status === 200 ) {
        this.OkAlert= true;
        this.$store.state.loginUser.name=this.new_name;
        //this.$router.push( { path: "/profile" } );
      } else {
        this.errorAlert = true;
        const data = await resp.json();
        this.errMsg = data.message;
      }
    },

    clear()
    {
      this.new_name = "";
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

<template>
  <div>
    <v-row>
      
      <!-- Sidebar -->
      <v-col cols="2">

<admin_sidebar></admin_sidebar>
</v-col>
      <!-- User table -->
      <v-col cols="10" class="mt-4">
        <v-data-table style="width: 1010px;"
          :headers="headers"
          :items="userList"
          :items-per-page="10"
          class="elevation-3"
        >
        <template v-slot:top>
          <v-toolbar style="background-color: #ffd343;" flat rounded>
            <v-toolbar-title class="text-center font-weight-bold">User List</v-toolbar-title>
          </v-toolbar>
      </template>

          <!-- Buttons -->
          <template v-slot:item.actions="{ item }">
            <!-- Promote User -->
            <v-btn
              class="mr-2"
              color="primary"
              fab
              x-small
              elevation="2"
              @click="onClickPromoteUser(item)"
            >
            <svg-icon type="mdi" :path="Up">Promote</svg-icon>
            </v-btn>
          </template>
        </v-data-table>
        <!-- Error Alert -->
    <v-alert class="mt-3" v-show="errorAlert" dense type="error" dismissible>
      {{errMsg}}
    </v-alert>

    <!-- Success Alert -->
    <v-alert class="mt-3" v-show="OkAlert" dense type="success" dismissible>
      Successfully Promoted
    </v-alert>
      </v-col>
    </v-row>

    <!-- Promote Dialog -->
    <v-dialog v-model="promoteDialog" width="680">
      <v-card>
        <!-- Promote Heading -->
        <v-toolbar color="#ffd343" dark>
          <div class="black--text">Promote This User?</div>
          <v-spacer></v-spacer>
          <v-btn icon @click="promoteDialog = false">
            <svg-icon type="mdi" color="black" :path="close"></svg-icon>
          </v-btn>
        </v-toolbar>

        <!-- Promote User As Admin Role -->
        <v-card-text class="pa-4">
          <v-row dense>
            <v-col cols="3" class="text-h5 black--text">ID:</v-col>
            <v-col cols="7" class="text-h5 black--text">{{ toPromoteUser.id }}</v-col>
            <v-col cols="3" class="text-h5 black--text">Name:</v-col>
            <v-col cols="7" class="text-h5 black--text">{{ toPromoteUser.name }}</v-col>
            <v-col cols="3" class="text-h5 black--text">Email:</v-col>
            <v-col cols="7" class="text-h5 black--text">{{ toPromoteUser.email }}</v-col>
            <v-col cols="3" class="text-h5 black--text">User Role:</v-col>
            <v-col cols="7" class="text-h5 black--text">{{ toPromoteUser.role }}</v-col>
          </v-row>
        </v-card-text>

        <!-- Promote User Btn -->
        <v-card-actions class="justify-end">
          <v-btn class="black--text" color="#ffd343" dark @click="promoteUser()">Promote</v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

   
  </div>
</template>

<script>
import utils from "../utils/utils";
import SvgIcon from '@jamescoyle/vue-icon';
import admin_sidebar from "../components/admin_sidebar.vue";
import { mdiTransferUp } from '@mdi/js';
import { mdiCloseBoxOutline } from '@mdi/js';

export default {
  name: "user_list",

  components: { admin_sidebar, SvgIcon },

  data() {
    return {
      localDomain: utils.constant.localDomain,

      headers: [
        { text: "ID", value: "id", sortable: true },
       { text: "Name", value: "name", sortable: true },
      { text: "Email", value: "email", sortable: true },
      { text: "User Role", value: "role", sortable: true },   
      { text: "Actions", value: "actions", sortable: false}     
      ],
      userList: [],
      promoteDialog: false,
      toPromoteUser: {},
      errMsg: '',
      errorAlert: false,
      OkAlert: false,
      loading: false,
      Up: mdiTransferUp,
      close: mdiCloseBoxOutline,
    };
  },

  async created() {
    await this.fetchUsers();
  },

  methods: {
    async fetchUsers() {
      const resp = await utils.http.get("/admin/nonAdminList");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.userList = data;
        }
      }
    },

    onClickPromoteUser(item) {
      this.promoteDialog= true;
      this.toPromoteUser = Object.assign({}, item);
    },

    async promoteUser(){
      const resp = await utils.http.post(
        "/admin/promote/" + this.toPromoteUser.id
      );
      if (resp && resp.status === 200) {
        this.OkAlert=true;
        this.promoteDialog = false;
        // Refresh User
        await this.fetchUsers();
      } else {
        this.errorAlert = true;
      }
    },
  },
};
</script>

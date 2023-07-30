<template>
  <div>
    <v-row>
      
    
      <!-- Sidebar -->
      <v-col cols="2">
        <admin_sidebar></admin_sidebar>
      </v-col>

      <!-- Admin table -->
      <v-col cols="10" class="mt-4">
        
        <v-data-table style="width: 1010px;"
          :headers="headers"
          :items="adminList"
          :items-per-page="10"
          class="elevation-3"
        >
        <template v-slot:top>
          <v-toolbar style="background-color: #ffd343;" flat rounded>
            <v-toolbar-title class="text-center font-weight-bold">Admin List</v-toolbar-title>
          </v-toolbar>
      </template>
          <!-- Buttons -->
          <template v-slot:item.actions="{ item }">
            <!-- Demote User -->
            <v-btn
              class="mr-2"
              color="primary"
              fab
              x-small
              elevation="2"
              @click="onClickDemoteUser(item)"
              :disabled="disableBtn"
            >
              <svg-icon type="mdi" :path="pencil"></svg-icon>
            </v-btn>
          </template>
        </v-data-table>
         <!-- Error Alert -->
    <v-alert class="mt-3" v-show="errorAlert" dense type="error" dismissible>
      {{ errMsg }}
    </v-alert>

    <!-- Success Alert -->
    <v-alert class="mt-3" v-show="OkAlert" dense type="success" dismissible>
      Successfully Demoted
    </v-alert>
      </v-col>
    
    </v-row>

    <!-- Demote Dialog -->
    <v-dialog v-model="DemoteDialog" width="680">
      <v-card>
        <!-- Demote Heading -->
        <v-toolbar color="#ffd343" dark>
          <div class="black--text">Change This User's Role as a user?</div>
          <v-spacer></v-spacer>
          <v-btn icon @click="DemoteDialog = false">
            <svg-icon type="mdi" color="black" :path="close"></svg-icon>
          </v-btn>
        </v-toolbar>

        <!-- Demote User As User Role -->
        <v-card-text class="pa-4">
          <v-row dense>
            <v-col cols="3" class="text-h5 black--text">ID:</v-col>
            <v-col cols="7" class="text-h5 black--text">{{
              toDemoteUser.id
            }}</v-col>
            <v-col cols="3" class="text-h5 black--text">Name:</v-col>
            <v-col cols="7" class="text-h5 black--text">{{
              toDemoteUser.name
            }}</v-col>
            <v-col cols="3" class="text-h5 black--text">Email:</v-col>
            <v-col cols="7" class="text-h5 black--text">{{
              toDemoteUser.email
            }}</v-col>
            <v-col cols="3" class="text-h5 black--text">User Role:</v-col>
            <v-col cols="7" class="text-h5 black--text">{{
              toDemoteUser.role
            }}</v-col>
          </v-row>
        </v-card-text>

        <!-- Demote User Btn -->
        <v-card-actions class="justify-end">
          <v-btn color="#ffd343" class="black--text" dark @click="DemoteUser()"
            >Demote</v-btn
          >
        </v-card-actions>
      </v-card>
    </v-dialog>

   
  </div>
</template>

<script>
import utils from "../utils/utils";
import SvgIcon from "@jamescoyle/vue-icon";
import admin_sidebar from "../components/admin_sidebar.vue";
import { mdiTransferDown } from "@mdi/js";
import { mdiCloseBoxOutline } from "@mdi/js";

export default {
  name: "admin_list",

  components: { admin_sidebar, SvgIcon },

  data() {
    return {
      localDomain: utils.constant.localDomain,

      headers: [
        { text: "ID", value: "id", sortable: true },
        { text: "Name", value: "name", sortable: true },
        { text: "Email", value: "email", sortable: true },
        { text: "User Role", value: "role", sortable: true },
        { text: "Actions", value: "actions", sortable: false },
      ],
      adminList: [],
      DemoteDialog: false,
      disableBtn: false,
      toDemoteUser: {},
      errMsg: "",
      errorAlert: false,
      OkAlert: false,
      loading: false,
      pencil: mdiTransferDown,
      close: mdiCloseBoxOutline,
    };
  },

  async created() {
    await this.fetchAdmin();
  },

  methods: {
    async fetchAdmin() {
      const resp = await utils.http.get("/admin/adminList");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.adminList = data;
        }
      }
      if (this.adminList.length < 2) {
        this.disableBtn = true;
      }
    },

    onClickDemoteUser(item) {
      this.DemoteDialog = true;
      this.toDemoteUser = Object.assign({}, item);
    },

    async DemoteUser() {
      const resp = await utils.http.post(
        "/admin/demote/" + this.toDemoteUser.id
      );
      if (resp && resp.status === 200) {
        this.OkAlert = true;
        this.DemoteDialog = false;
        // Refresh User
        await this.fetchAdmin();
      } else {
        this.errorAlert = true;
        const data = await resp.json();
        this.errMsg = data.message;
      }
    },
  },
};
</script>

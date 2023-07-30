<template>
  <div class="scrollable-container main">
    <v-container id="user-profile" fluid tag="section">
      <heading></heading>
      <v-row><v-col cols="12" class="mt-12"></v-col></v-row>

      <v-row justify="center">
        <v-col cols="12">
          <div class="text-center text-h2">
            <h1>Profile Page</h1>
          </div>
        </v-col>
      </v-row>

      <v-row><v-col cols="20"></v-col> </v-row>
      <v-row justify="center">
        <v-col cols="18">
          <v-row justify="center">
            <v-col cols="10">
              <div class="text-center">
                <v-card height="800" class="rounded-card corner-line-card">
                  <v-card-text>
                    <v-row><v-col cols="12" class="mt-8"></v-col> </v-row>

                    <br /><br /><v-img
                      src="../assets/userProfile.jpg"
                      class="rounded-profile mx-auto"
                      contain
                      width="300"
                    ></v-img>
                    <v-row><v-col cols="12" class="mt-8"></v-col> </v-row>
                    <div class="text-h2 black--text">
                      Welcome To JELS's User
                    </div>
                    <div class="text-h3 black--text">
                      <svg-icon type="mdi" :path="path"></svg-icon
                      >{{ loginUser.name
                      }}<svg-icon type="mdi" :path="path"></svg-icon>
                    </div>
                    <br /><br />
                    <div class="text-h3 black--text">
                      <svg-icon type="mdi" :path="path1"></svg-icon>
                      {{ loginUser.email }}
                    </div>
                    <br /><br /><br /><br />

                    <!-- Change Password Btn -->
                    <v-btn class="mr-4" color="#ffd343" @click="changePwd()"
                      ><svg-icon type="mdi" :path="path2"></svg-icon> change
                      password
                    </v-btn>

                    <!-- Change Name Btn -->
                    <v-btn class="mr-4" color="#ffd343" @click="changeName()"
                      ><svg-icon type="mdi" :path="path2"></svg-icon> change
                      name
                    </v-btn>
                  </v-card-text>
                </v-card>
              </div>
            </v-col>
          </v-row>
        </v-col>

        <v-col cols="12">
        <div align="center">
          <v-card width="1025" class="rounded-card corner-line-card" >
            <v-card-title> Saved Cards </v-card-title>

            <div v-if="cards.length>0">
              <v-simple-table rounded>
              <template v-slot:default>
                <thead>
                  <tr>
                    <th class="text-left">No.</th>
                    <th class="text-left">Card Brand</th>
                    <th class="text-left">Card Number</th>
                    <th class="text-left">Action</th>
                  </tr>
                </thead>
                <tbody>
                  <tr v-for="(card, index) in cards" :key="card.id">
                    <td class="text-left">{{ index + 1 }}</td>
                    <td class="text-left">{{ card.card.brand }}</td>
                    <td class="text-left">**** **** **** {{ card.card.last4 }}</td>
                    <td class="text-left">
                      <v-icon
                        @click="
                          toDeleteCard = card;
                          dialogDelete = true;
                        "
                      >
                        mdi-delete
                      </v-icon>
                    </td>
                  </tr>
                </tbody>
              </template>
            </v-simple-table>

            </div>
            <div v-else algin center style="padding-bottom: 30px;">
              
              <label 
              style="color: rgb(255, 170, 0);"
              >Cards that saved during payment will be displayed here.  
            
            </label>
            </div>
            
          </v-card>
        </div>
        </v-col>
      
      </v-row>
      <v-dialog width="400" v-model="dialogDelete">
        <v-card>
          <v-card-title>
            <div>Delete Card</div>
          </v-card-title>
          <v-card-text class="pa-6">
            Are you sure you want to delete this card ?
          </v-card-text>

          <v-card-actions class="justify-end">
            <v-btn
              class="ma-2"
              :disabled="loading"
              @click="
                toDeleteCard = {};
                dialogDelete = false;
              "
              >Cancel</v-btn
            >

            <v-btn color="#ffd343" @click="deleteCard()">
              <span v-if="!loading">Confirm</span>
              <v-progress-circular
                v-else
                indeterminate
                color="primary"
              ></v-progress-circular>
            </v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-container>
  </div>
</template>

<script>
import constant from "@/utils/constant";
import heading from "../components/heading.vue";
import SvgIcon from "@jamescoyle/vue-icon";
import { mdiHeartMultipleOutline } from "@mdi/js";
import { mdiCardAccountMailOutline } from "@mdi/js";
import { mdiPencil } from "@mdi/js";
import axios from "axios";
export default {
  name: "userProfile",
  components: { SvgIcon, heading },

  data() {
    return {
      Bio: "",
      loginUser: {},
      path: mdiHeartMultipleOutline,
      path1: mdiCardAccountMailOutline,
      path2: mdiPencil,
      cards: [],
      dialogDelete: false,
      toDeleteCard: {},
      loading: false,
    };
  },

  async created() {
    this.loginUser = this.$store.state.loginUser;
    this.$store.watch(
      () => {
        return this.$store.state.loginUser;
      },
      (newVal) => {
        this.loginUser = newVal;
      },
      {
        deep: true,
      }
    );
    await this.fetchCustomerCards();
  },

  methods: {
    async changePwd() {
      this.$router.push({ path: "/changePwd" });
    },

    async changeName() {
      this.$router.push({ path: "/changeName" });
    },
    async fetchCustomerCards() {
      await axios
        .get(constant.localDomain + "/payment/cardList", {
          params: {
            userId: this.loginUser.id,
          },
        })
        .then((response) => {
          this.cards = response.data;
         // console.log("cards ", this.cards);
        })
        .catch((error) => {
         // console.error("Error fetching customer cards:", error);
        });
    },
    async deleteCard() {
      this.loading = true;
     // console.log("card id ", this.toDeleteCard.id);
      const resp = await axios.delete(
        constant.localDomain + "/payment/delete_card",
        {
          params: {
            card_id: this.toDeleteCard.id,
          },
        }
      );
      if ((await resp).status === 200) {
        this.loading = false;
        this.dialogDelete = false;
        this.toDeleteCard + {}, await this.fetchCustomerCards();
      }
    },
  },
};
</script>
<style scoped lang="scss">
.rounded-card {
  border-radius: 17px;
}
.user-card {

  

}

.rounded-profile {
  border-radius: 500px;
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

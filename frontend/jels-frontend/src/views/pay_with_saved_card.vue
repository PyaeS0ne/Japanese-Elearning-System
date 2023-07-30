<template>
  <div>
    <div v-if="!paymentStatus" class="custom-padding">
      <v-card>
        <v-row>
          <v-col cols="5">
            <div class="image">
              <v-img
                :src="localDomain + '/' + course.imagePath"
                width="200"
                class="mx-auto"
                style="border: 1px solid #000000c7; border-radius: 8px"
              ></v-img>
              <v-card-title class="justify-center">{{
                course.title
              }}</v-card-title>
              <v-card-subtitle class="text-center"
                >Amount : {{ course.fee }} Kyats</v-card-subtitle
              >
            </div>
          </v-col>
          <v-col cols="6">
            <v-card-title> Select a card for payment </v-card-title>
            <v-card-text class="pa-2">
              <v-row>
                <v-col cols="1"></v-col>
                
                <v-col cols="5">
                  <label>
                  Card Brand
                </label>
                </v-col>
              
                
                <v-col cols="6">
                  <label>
                  Card Number
                </label>
                </v-col>
              
              </v-row>
              <v-radio-group style="padding-left: 10px" v-model="selectedCard">
                <v-divider></v-divider>
                <v-list v-for="card in cards" :key="card.id" three-line>
                  <div>
                    <v-row>
                      <v-col cols="1">
                        <v-radio
                          :value="card"
                          
                        ></v-radio>
                      </v-col>
                      <v-col cols="5">
                        <label>{{ card.card.brand }}</label>
                      </v-col>
                      <v-col cols="6">
                        <label>**** **** **** {{ card.card.last4 }}</label>
                      </v-col>
                    </v-row>
                  </div>
                  <div style="padding-top: 10px">
                    <v-divider></v-divider>
                  </div>
                </v-list>
              </v-radio-group>
              
            </v-card-text>
            <v-card-actions class="justify-end">
            
            <v-btn rounded color="#ffd343" class="ma-2" :disabled="!selectedCard" @click="handlePayment">
              <span v-if="!loading">Pay</span>
                  <v-progress-circular
                    v-else
                    indeterminate
                    color="#ffd343"
                  ></v-progress-circular>
            </v-btn>
      
          </v-card-actions>
          <v-card-actions class="justify-end">
            
            <v-btn rounded small color="#ffd343" class="ma-2"  @click="payWithNewCard">pAy with new card</v-btn>
      
          </v-card-actions>
          </v-col>
        </v-row>
        <v-row>
          <v-col>
          
        </v-col>
        </v-row>
      </v-card>
    </div>
    <div v-if="paymentStatus">
      You Already paid ,
      <v-btn @click="pushToCourse">Go To Course</v-btn>
    </div>
  </div>
</template>

<script>
import constant from "@/utils/constant";
import http from "@/utils/http";
import axios from "axios";

export default {
  name:'pay_with_saved_card',
  data() {
    return {
      paymentStatus: false,
      loginUser: {},
      localDomain: constant.localDomain,
      cards: [],
      selectedCard: null,
      course: {},
      course_id: this.$route.params.id,
      loading:false,
    };
  },
  async mounted() {
    await this.fetchCustomerCards();
    await this.fetchCourse();
    await this.getPaymentStatus();
    await this.pushToCourse();
  },
  created() {
    // LoginUser from Vuex
    this.loginUser = this.$store.state.loginUser;
    this.$store.watch(
      () => {
        return this.$store.state.loginUser;
      },
      (newVal, oldVal) => {
        this.loginUser = newVal;
      },
      {
        deep: true,
      }
    );
  },
  methods: {
    async getPaymentStatus() {
      const resp = await axios.get(
        constant.localDomain + "/enroll/users/" + this.loginUser.id + "/courses"
      );
      console.log("Response status", resp.status);
      console.log("Response Data", resp.data);

      if (resp && resp.status === 200) {
        const data = resp.data; // Access response data directly
        if (data) {
          this.user_courses = data; // Assign response data to user_courses
        }
      }

      for (let course of this.user_courses) {
        console.log("this.course_id  ", this.course_id);

        console.log("Course Id", course.id);
        if (parseInt(this.course_id) === course.id) {
          console.log("True Status");
          this.paymentStatus = true;
          break;
        } else {
          console.log("error");
        }
      }
      console.log("paid ? ", this.paymentStatus);
    },
    fetchCustomerCards() {
      axios
        .get(constant.localDomain + "/payment/cardList", {
          params: {
            userId: this.loginUser.id,
          },
        })
        .then((response) => {
          this.cards = response.data;
          console.log("cards ", this.cards);
        })
        .catch((error) => {
        // console.error("Error fetching customer cards:", error);
        });
    },
    async selectCard(card) {
      // Set the selected card in the 'selectedCard' data property
      this.selectedCard = card;
    },

    async fetchCourse() {
      //console.log("fetching course fee");
      const resp = await http.get("/course/" + this.course_id);
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.course = data;
        }
      }
      //console.log("course ", this.course);
    },

    async handlePayment() {
      this.loading=true;
      //console.log("handleing payment ", this.selectedCard.id, this.course.fee);
      //console.log("user id ", this.loginUser);
      const resp = await axios.post(
        constant.localDomain + "/payment/charge",
        null,
        {
          params: {
            amount: this.course.fee * 100,
            user_id: this.loginUser.id,
            paymentMethodId: this.selectedCard.id,
          },
        }
      );
      if (resp.status === 200) {
        //console.log("ok");

        //go to BackEnd

        const resp = await axios.post(
          constant.localDomain +
            "/enroll/userId/" +
            this.loginUser.id +
            "/courseId/" +
            this.course_id
        );
        //console.log("enroll ok?", resp.data); //String Response
        if (resp && resp.status === 200) {
          //console.log('Enroll Status:', resp.status);
          //Frontend Route
          this.loading=false;
          this.$router.push({ path: "/course_detail/" + this.course_id });

        } else {
          //console.log("error add course");
        }
      } else {
        //console.log("error payment");
      }
    },
    payWithNewCard() {
      this.$router.push({
        path: "/payment/course/" + this.course_id,
      });
    },
    pushToCourse() {
      if (this.paymentStatus == true) {
        this.$router.go(-1);
      }
    },
  },
};
</script>
<style scoped>
.custom-padding {
  padding-top: 50px;
  padding-left: 30px;
  padding-right: 30px;
  padding-bottom: 20px;
}
.image {
  padding-top: 10px;
}
</style>

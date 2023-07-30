<template>
  <div>
  <div v-if="!paymentStatus" style="padding-top:50px" >

   
    


    <v-card width="1000" class="mx-auto"  >

      <v-row>
        <v-col cols="5">

          <div class="image">

<v-img :src="localDomain+'/'+course.imagePath"   width="200" class="mx-auto" style="border:1px solid #000000c7 ; border-radius: 8px;"></v-img>
<v-card-title class="justify-center">{{course.title}}</v-card-title>
<v-card-subtitle class="text-center">Amount : {{ course.fee }} Kyats</v-card-subtitle>

</div>

        </v-col>
        


        <v-col cols="6">
          <v-card-title class="title"> Enter Card Number</v-card-title>
          
          <form @submit.prevent="handlePayment"  :disabled="true" class="payment" >
      <!-- <v-col>
        

        <v-row>
          <label for="email">email : </label>
          <input type="email" id="email" v-model="customerEmail" required />
        </v-row>
      </v-col> -->
      
      

        
      
          
            <div id="card-element" class="card-number"></div>
            <div id="card_cvc" ></div>
            <div id="card-errors" role="alert" >{{ err }}</div>
          
          
      
      
      
      

        
      

   


            

    </form>
    <v-checkbox class="custom-checkbox" 
            color="success"
            v-model="saveCard" label="save for future use" ></v-checkbox>
    
    <v-card-actions class="justify-end">
      
      
      <v-btn type="submit" 
      class="ma-2"
      rounded
      color="#ffd343" :disabled="paymentStatus" @click="handlePayment">
      <span v-if="!loading">Pay</span>
              <v-progress-circular
                v-else
                indeterminate
                color="primary"
              ></v-progress-circular>
    </v-btn>
    </v-card-actions>
        </v-col>
      </v-row>
      
      
      

    
  </v-card>
    </div>
    <div v-if="paymentStatus"
    >
    You Already paid ,
    <v-btn @click="pushToCourse">Go To Course</v-btn>
  </div>

</div>
    
  
</template>
  
<script>
import { loadStripe } from '@stripe/stripe-js';
import constant from '@/utils/constant';
import axios from 'axios';
import utils from '@/utils/utils';

export default {
  name: "payment",
  data() {
    return {
      localDomain:constant.localDomain,
      loginUser:{},
      saveCard: false,
      course_id: this.$route.params.id,
      
      course:{},
      err: "",
      stripe: null,
      cardElement: null,
      client_secret:null,
      paymentStatus:false,
      loading:false,
      

      //customerEmail:"",
    };
  },
  async mounted() {
    // const stripe = await loadStripe('pk_test_51NP0YxLt68QWeWuV611FdfkbdJzU9Y0lbXb8UcFkRK0GcoGV1iH6IhW4qENLiw5zG23GzW9Wj0L06W6EYmoxVbVf00GW7ywxwF');
    // const elements = stripe.elements();
    // const cardElement = elements.create('card');
    // cardElement.mount('#card-element');
    await this.initStripe();
    await this.fetchCourse();
    await this.getPaymentStatus();
    await this.pushToCourse();

  },
  created(){
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
      const resp = await  axios.get(constant.localDomain +"/enroll/users/" +this.loginUser.id+"/courses");
     // console.log('Response status', resp.status);
      //console.log('Response Data', resp.data);

      if (resp && resp.status === 200) {
        const data = resp.data; // Access response data directly
        if (data) {
          this.user_courses = data; // Assign response data to user_courses
        }
      }
      
      for (let course of this.user_courses) {
        //console.log('this.course_id  ', this.course_id  );

        //console.log('Course Id', course.id );
          if(parseInt(this.course_id) === course.id ) {
          //  console.log('True Status');
            this.paymentStatus=true;
            break;
          }
          else{
            //console.log('error');
          }
      }
      //console.log("paid ? ",this.paymentStatus);

    },

    async fetchCourse() {
      const resp = await utils.http.get("/course/" + this.course_id);
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.course = data;
        }
      }
    },

    async initStripe() {

      const stripe = await loadStripe(
        'pk_test_51NPPFuCniK2jK2adI4vpqrlJnldliXBWHHBIxW8jtuHwmSB4BRZwpG3y6aieUb0hwEM2p1Ay6Udv0t2y5GpbtjGZ001wiGfPM8'
      );

      const elements = stripe.elements();
      const cardElement = elements.create('card');

      // const cardElement = elements.getElement('card');
      cardElement.mount('#card-element');
      this.stripe = stripe;
      this.cardElement = elements.getElement('card');
    },
    async handlePayment() {
      this.loading=true;
      //console.log('amout', this.course.fee);
      //console.log('course :',this.course )
      try {

        await axios.post(constant.localDomain + '/payment/saveAndPay', null, {
          params: {
            userId: this.loginUser.id,
            amount: this.course.fee*100,
            saveCard: this.saveCard,
          }
        }).then((Response) => {
          console.log('response');
          this.client_secret = Response.data;
        });

        // const response = await axios.post(constant.localDomain+"/create-payment-intent",{
        //    "amount":this.amount*100, 
        //   "mail":this.customerEmail,

        // }
        //   );
        
        //console.log('status', this.client_secret);

        const result = await this.stripe.confirmCardPayment(this.client_secret, {


          payment_method: {
            card: this.cardElement,
            // billing_details: {
            //     email: this.customerEmail

            //   },
          },
        });

        if (result.error) {
          this.loading=false;
          //console.log("error",error.message);
          this.err = result.error.message;
        } else {
          // Payment successful, handle success action here

          //console.log('Payment successful:', result.paymentIntent);
          //console.log('card detial'.this.cardElement);
          
          //go to BackEnd 
          //console.log('userId :',this.loginUser.id,'courseId :',this.course_id)
          const resp = await  axios.post(constant.localDomain +"/enroll/userId/"+this.loginUser.id+"/courseId/"+this.course_id);
           //console.log("enroll ok?",resp.data);//String Response
            if (resp && resp.status === 200) {
              //console.log('Enroll Status:', resp.status);
              //Frontend Route
              this.loading=false;
             this.$router.push({ path: "/course_detail/" + this.course_id});
              
            }
           else { this.errorAlert = true;} 
           //this.$router.push({ path: "/" });
          
        
        }


      } catch (error) {
        if (error.response && error.response.data) {
          //console.log('Error message from server :', error.response.data);
          //this.err = error.response.data;
        } else {
          //console.error('Error during payment request:', error);
        }
      }

    },
    pushToCourse(){
      if(this.paymentStatus==true){
        this.$router.go(-1)
      }
    } 
  },
};
</script>
<style scoped>
.payment{
  width: 600;
  
  padding-top:2%;
  padding-left: 4%;
  padding-right: 2%;
  padding-bottom: 5%;

}
.image{

  padding-top: 10px;
  
  
}
.custom-checkbox{
  padding-left: 15px;
}
.title{
  padding-top: 16%;

}

</style>
  
   
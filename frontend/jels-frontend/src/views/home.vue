<template>
  <div class="scrollable-container" >
   <!--<div class="background-image"></div>-->
   <!-- Home Page image-->
   <v-row >
       <v-col cols="12">
         <v-img src="../assets/home3.jpg"></v-img>
       </v-col>
   </v-row>
 
   <v-row ><v-col cols="12"></v-col> </v-row>

    <!-- Our Courses are Suitable -->   
   <v-row justify="center">
       <v-col cols="12">
         <div class="text-center text-h5"><h1>Our Courses are Suitable.... </h1></div>
       </v-col>
   </v-row> 

   <v-row >
       <v-col cols="12">
         <v-img src="../assets/Course3.jpg"></v-img>
       </v-col>
   </v-row>

   <!-- Our Team -->
   <!--
   <v-row style="background-color: #ffd343;"><v-col cols="12"></v-col> </v-row>
   <v-row justify="center" style="background-color: #ffd343;">
       <v-col cols="12">
         <div style="color: #1a1a1a;" class="text-center text-h5 "  ><h1 >Our Team</h1></div>
       </v-col>
   </v-row>
 
   <v-row style="background-color: #ffd343;"><v-col cols="12"></v-col> </v-row>
   <v-row justify="center" style="background-color: #ffd343;">
       <v-col cols="10">
        
         <v-row justify="center">
           
           <v-col cols="3" >
             <div class="text-center">
             <v-card  height="450" class="rounded-card corner-line-card ">
               <v-card-text>
                 <v-row ><v-col cols="12" class="mt-8"></v-col> </v-row>
                 <v-img src="../assets/nan.png" class="mx-auto" contain width="150"></v-img>
                 <v-row ><v-col cols="12" class="mt-8"></v-col> </v-row>
                 <div class="text-h4">Nan Yu</div>
                 <v-row ><v-col cols="12" class="mt-8"></v-col> </v-row>
                 <div>
                   <v-row justify="center"><v-col cols="3">
                 <a :href="facebookUrl" target="_blank">
                   <v-img src="../assets/facebook.png" width="30"/>
                    <font-awesome-icon :icon="faFacebook" />
                 </a></v-col>
                 <v-col cols="3"><a :href="facebookUrl" target="_blank">
                   <v-img src="../assets/mail.png" width="30"/>
                   <font-awesome-icon :icon="faFacebook" />
                 </a></v-col>
                </v-row>
                 </div>
 
               </v-card-text>
             </v-card>
             </div>
           </v-col>
         
           <v-spacer></v-spacer>
 
           <v-col cols="3" >
             <div class="text-center">
             <v-card  height="450" class="rounded-card corner-line-card ">
               <v-card-text>
                 <v-row ><v-col cols="12" class="mt-8"></v-col> </v-row>
                 <v-img src="../assets/nan.png" class="mx-auto" contain width="150"></v-img>
                 <v-row ><v-col cols="12" class="mt-8"></v-col> </v-row>
                 <div class="text-h4">Nan Yu</div>
                 <v-row ><v-col cols="12" class="mt-8"></v-col> </v-row>
                 <div>
                   <v-row justify="center"><v-col cols="3">
                 <a :href="facebookUrl" target="_blank">
                   <v-img src="../assets/facebook.png" width="30"/>
                   <font-awesome-icon :icon="faFacebook" />
                 </a></v-col>
                 <v-col cols="3"><a :href="facebookUrl" target="_blank">
                   <v-img src="../assets/mail.png" width="30"/>
                   <font-awesome-icon :icon="faFacebook" />
                 </a></v-col>
                </v-row>
                 </div>
 
               </v-card-text>
             </v-card>
             </div>
           </v-col>
 
           <v-spacer></v-spacer>
 
           <v-col cols="3" >
             <div class="text-center">
             <v-card  height="450" class="rounded-card corner-line-card ">
               <v-card-text>
                 <v-row ><v-col cols="12" class="mt-8"></v-col> </v-row>
                 <v-img src="../assets/nan.png" class="mx-auto" contain width="150"></v-img>
                 <v-row ><v-col cols="12" class="mt-8"></v-col> </v-row>
                 <div class="text-h4">Nan Yu</div>
                 <v-row ><v-col cols="12" class="mt-8"></v-col> </v-row>
                 <div>
                   <v-row justify="center"><v-col cols="3">
                     
                 <a :href="facebookUrl" target="_blank">
                   <v-img src="../assets/facebook.png" width="30"/>
                  <font-awesome-icon :icon="faFacebook" />
                 </a></v-col>
                 <v-col cols="3"><a :href="facebookUrl" target="_blank">
                   <v-img src="../assets/mail.png" width="30"/>
                   <font-awesome-icon :icon="faFacebook" />
                 </a></v-col>
                </v-row>
                 </div>
 
               </v-card-text>
             </v-card>
             </div>
           </v-col>
 
         </v-row> 
       </v-col>
   </v-row>   -->

   <v-row style="height: 300px; background-color: #ffd343; "><v-col cols="12"></v-col> </v-row>
 </div>
 </template>
 <script>
 //import { faFacebook } from '@fortawesome/free-brands-svg-icons';
 //import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
 export default {
   
   name: 'BackgroundImage',
 
   data() {
   return {
     //faFacebook: faFacebook,
    facebookUrl: 'https://www.facebook.com',
   };
 },
   components: {
   //FontAwesomeIcon,
 },
 
 }
 
 </script>
 
 <style scoped>
 .background-image {
   /* 
   background-image: url('../assets/home2.jpg');
   background-size: cover;
   background-position: center;
   */
 }
 .rounded-image {
    /*border-radius: 100%;
   border-image-width: "30";*/
  
 }
 .rounded-card {
   border-radius: 17px; /* Adjust the value to change the roundness */
 }
 .corner-line-card {
   position: relative;
   border: 4px solid #ffd343;
   
 }
 
 </style>
 
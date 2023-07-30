<template>
  <div v-if="paymentStatus='true' || loginUser.role==='admin'" >
 
    <v-row>
      <!-- Course Video List -->
      <v-col  cols="12" class="mt-4">
        <center>
            <v-card  class="card-container rounded-card corner-line-card" width="750" height="500">
              <v-card-text >
               <!-- <div class="text-h6">Day</div>-->
                
                <video @ended="markAsWatched()"
                  width="700px"
                  :src="localDomain + '/'+currentVideo.path"
                  controls
                ></video>
                
               <!-- <div class="text-h6 text-center">{{ courseVideo.title }}</div>-->
               <div class="text-left" :style="{ fontSize: '13px'}" > {{ currentVideo.title }} </div>
              
          
                    <button class="mt-3 mr-12" @click="navigateToPreviousVideo" >
                    <svg-icon type="mdi" :path="pathLeft" :style="{ color: '#242526'}"  style="width: 35px; height: 25px;"></svg-icon> 
                    </button>

                    <button class="mt-3 ml-12"  @click="navigateToNextVideo">
                    <svg-icon type="mdi" :path="pathRight" :style="{ color : '#242526'}" style="width: 35px; height: 25px; "></svg-icon>
                  </button>
              </v-card-text>
            </v-card></center>
      </v-col>
    </v-row>
  </div>
</template>

<style scoped>
.card-container {
  justify-content: center;
  align-items: center;
  align-content: center;
  /*height: 100vh; 
   display: flex;*/
}
.rounded-card {
   border-radius: 15px; /* Adjust the value to change the roundness */
 }
 .corner-line-card {
   position: relative;
   border: 3px solid #ffd343;
   
 }


</style>
<script>
import utils from "../utils/utils";
import constant from '@/utils/constant';
import router from "@/router";
import axios from 'axios';
import SvgIcon from '@jamescoyle/vue-icon'
import { mdiMenuLeft } from '@mdi/js';
import { mdiMenuRight } from '@mdi/js';
import { mdiArrowLeftDropCircle } from '@mdi/js';
import { mdiArrowRightDropCircle } from '@mdi/js';


export default {
  
  name: "courseVideoDetail",
  components: {
    SvgIcon
  },

  data() {
    return {
      loginUser:{},
      localDomain: utils.constant.localDomain,
      course_video_id: this.$route.params.id,
      course_id:this.$route.params.course_id,
      currentVideo: {},
      courseVideoList: [],
      user_courseVideos :[],
      //currentVideo:null,
      //pathLeft: mdiMenuLeft,
      //pathRight: mdiMenuRight,
      pathLeft: mdiArrowLeftDropCircle,
      pathRight: mdiArrowRightDropCircle,
      paymentStatus:false,
      saveStatus:false,
    };
  },

  async created() {
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
    await this.fetchCourseVideo();
    await this.fetchVideo();
    await this.getPaymentStatus();
  },
  
  //Get CourseVideo By Course ID
  methods: {
    async markAsWatched()
    {
     //get user Watch video
     const resp = await  axios.get(constant.localDomain +"/watched/users/" +this.loginUser.id+"/videos");
      if (resp && resp.status === 200) {
        const data = resp.data; 
        if (data) {
          this.user_courseVideos = data;
        }
      }
      //condition for CourseVideo already save or not
        for(let user_coursevideo of this.user_courseVideos){
            if(user_coursevideo.id === parseInt( this.course_video_id) ) 
            { 
             //  console.log("OK");
               this.saveStatus=true;
               break; 
              } 
        }
      
      if(!this.saveStatus)
      {
        //Save User,CourseVideo
      const resp = await  axios.post(constant.localDomain +"/watched/userId/"+this.loginUser.id+"/videoId/"+parseInt(this.course_video_id));
     // console.log("Save ok?",resp.data);//String Response
      }
    },
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
       // console.log('this.course_id  ', this.course_id  );

       // console.log('Course Id', course.id );
          if(parseInt(this.course_id) === course.id ) {
           // console.log('True Status');
            this.paymentStatus=true;
            break;
          }
          else{
           // console.log('error');
          }
      }
     // console.log("paid ? ",this.paymentStatus);
    },
    async fetchCourseVideo() {
      const resp = await utils.http.get("/course/"+this.course_id+"/course_videos");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.courseVideoList = data;
        }
      }
    },
    async fetchVideo() {
          const resp = await utils.http.get("/course_video/"+this.course_video_id);
            if (resp && resp.status === 200) {
              const data = await resp.json();
              if (data) {
                this.currentVideo = data;
              }
            }           
    },
    async navigateToPreviousVideo() {
      this.course_video_id--;
      for (let coursevideo of this.courseVideoList) {
          if(coursevideo.id === this.course_video_id ) {
            router.push({ path: "/course/"+this.course_id+"/course_video_detail/" +this.course_video_id});
          }
      }
      
    },

    async navigateToNextVideo() {
      this.course_video_id++;
      //await this.fetchVideoList();
      for (let coursevideo of this.courseVideoList) {
          if(coursevideo.id === this.course_video_id ) {
            router.push({ path: "/course/"+this.course_id+"/course_video_detail/" +this.course_video_id});
          }
      }
    },

    /*
    async fetchVideoList() {
      const resp = await utils.http.get("/course/"+this.course_id+"/course_videos");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          console.log("data",data)
          this.videoList = data;

          const resp = await utils.http.get("/course_video/"+this.course_video_id);
            if (resp && resp.status === 200) {
              const data = await resp.json();
              if (data) {
                this.currentVideo = data;
              }
            }
            
        }//if
      }//if          
    },
    */

  },//Method
};//Export
</script>

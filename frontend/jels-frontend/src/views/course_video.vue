<template>
  <div v-if="paymentStatus='true' || loginUser.role==='admin'">
    
    <v-row >
      <v-col cols="2"  > 
        <v-card :style="fixedCardStyle" color="#000000" class="mx-auto " width="270"   >
          <v-row >
        <v-col cols="12" class="mt-7">
          <div  style=" color: #ffd343; height: 70px;" class="font-weight-bold text-h5 text-center" >
            {{course.title}}
          </div>
       </v-col></v-row>
      <v-divider></v-divider>
      <!-- dense nav -->
      <v-img src="../assets/adminSiber3.jpg">
        
      <v-list-item dense nav>
      <v-row >
        <v-col cols="2" class="mt-9">
        <!-- Icon -->
          <svg-icon :style="{ color: 'white' }" type="mdi" :path="path" style=" width: 30px; height: 20px;"></svg-icon>
      </v-col> 
        <!-- Title -->
        <v-col cols="10" class="mt-9">
          <span :style="{ color: 'white' }" class="font-weight-bold text-center " style=" text-align: right;">Reference Book</span>
      </v-col>
      </v-row>
      </v-list-item>

      <v-list-item dense nav>
      <v-row justify="center">
        <v-col cols="12" class="mt-5" justify="center">
          <div :style="{ color: 'white' }" class="font-weight-bold text-center" style=" text-align: left;">{{ course.refBook }}</div>
      </v-col>
      </v-row>
    </v-list-item>

    <v-list-item dense nav>
      <v-row justify="center">
        <v-col cols="12" class="mt-9">
          <v-btn class="custom-button" color="#ffd343" style="color: #ffd343;width:160px ;" @click="downloadBook()">
            <svg-icon class="ml-1" type="mdi" :style="{ color: 'black' }" style=" width: 25px; height: 15px;" :path="pathDownload"></svg-icon>
            <span class="mr-2" :style="{ color: 'black', fontSize: '13px'}" >Download Book</span>
         
          <!--  <v-list-item-title :style="{ color: 'white' }" class="font-weight-bold" style=" text-align: left;"> <a href="#" @click="downloadBook">Download Book</a></v-list-item-title>-->
      
      </v-btn>
      </v-col>
      </v-row>
      </v-list-item>
  </v-img>
  </v-card>
</v-col>

      <!-- Course Video List -->
      <v-col cols="10">
        <v-row ><v-col cols="12" class="mt-5"></v-col> </v-row>
        <v-row>
          <v-col cols="3" v-for="(courseVideo, index) in courseVideoList" :key="index">
            <v-card  @click=" goToCourseVideoDetail(courseVideo)" width="330 " height="175">
            
               <!-- <div class="text-h6">Day</div>-->
                <video ref="videoPlayer" 
                  width="100%"
                  :src="localDomain + '/'+courseVideo.path"
                > 
                <div :style="{ fontSize: '11px'}">{{ courseVideo.watchedstatus}} </div>
                </video> 
                <div v-if="courseVideo.watchedstatus==='Watched' " class="video-title" :style="{ fontSize: '11px'}">{{ courseVideo.watchedstatus}} </div>
              
                <div class=" font-weight-normal mr-2 ml-2" :style="{ fontSize: '11px'}">{{ courseVideo.title }}</div>
              
            </v-card>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </div>
</template>
<style scoped>

.custom-button {
  border: 10px solid #eee9e9;
  border-radius: 16px;
  }
.video-title {
    position: absolute;
    top: 100px;
    right: 10px;
    color: white;
    font-size: 15px;
    background-color: rgba(0, 0, 0, 0.7);
    padding: 4px 7px;
    border-radius: 5px;
}
  

</style>

<script>
import utils from "../utils/utils";
import constant from '@/utils/constant';
import SvgIcon from '@jamescoyle/vue-icon';
import axios from 'axios';
import { mdiBookOpenVariant } from '@mdi/js';
import { mdiDownloadOutline } from '@mdi/js';
export default {
  name: "courseVideo",

  components: {
    SvgIcon
  },

  data() {
    return {
      path: mdiBookOpenVariant,
      pathDownload: mdiDownloadOutline,
      loginUser:{},
      localDomain: utils.constant.localDomain,
      course_id: this.$route.params.id,
      course: [],
      courseVideoList: [],
      user_courseVideos:[],
      paymentStatus:false,
      fixedCardStyle: {
        position: "fixed",
        width:"190px",
        //top: "50px", // Set the desired value for the top position
        //left: "10px", // Set the desired value for the left position
        // You can also set 'right' or 'bottom' properties if needed
        // right: '1000px',
        // bottom: '50px',
      },
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

    await this.fetchCourse();
    await this.fetchCourseVideo();
    await this.getWatchStatus();
    await this.getPaymentStatus();
   
   
    
  },
  
  //Get CourseVideo By Course ID
  methods: {
    async getPaymentStatus() {
      const resp = await  axios.get(constant.localDomain +"/enroll/users/" +this.loginUser.id+"/courses");
      //console.log('Response status', resp.status);
      //console.log('Response Data', resp.data);

      if (resp && resp.status === 200) {
        const data = resp.data; // Access response data directly
        if (data) {
          this.user_courses = data; // Assign response data to user_courses
        }
      }
      
      for (let course of this.user_courses) {
       // console.log('this.course_id  ', this.course_id  );

        //console.log('Course Id', course.id );
          if(parseInt(this.course_id) === course.id ) {
          //  console.log('True Status');
            this.paymentStatus=true;
            break;
          }
          else{
          //  console.log('error');
          }
      }
     // console.log("paid ? ",this.paymentStatus);
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

    async getWatchStatus() {
      
      //get user Watch video
      const resp = await  axios.get(constant.localDomain +"/watched/users/" +this.loginUser.id+"/videos");
      //console.log('Response status', resp.status);
      // console.log('Response Data', resp.data);

      if (resp && resp.status === 200) {
        const data = resp.data; 
        if (data) {
          this.user_courseVideos = data;
        }
       // console.log("user_courseVideos",this.user_courseVideos);

        //get all courese video list
        const response = await utils.http.get("/course/"+this.course_id+"/course_videos");
      if (response && response.status === 200) {
        const data = await response.json();
        if (data) {
          this.courseVideoList = data;
          
        }
      }
      //condition for watched status
      await this.user_courseVideos.forEach((user_coursevideo) => {
        for(let coursevideo of this.courseVideoList){
            if(user_coursevideo.id === coursevideo.id ) 
            { 
              coursevideo.watchedstatus="Watched";
              //console.log(coursevideo.watchedstatus);
               break;
             }
            
          }
       });
      
      
     /* 
       

      await this.user_courseVideos.forEach((user_coursevideo) => {
        for(let coursevideo of this.courseVideoList){
            if(user_coursevideo.id === coursevideo.id ) 
            { console.log("Hello");
              user_coursevideo.watchedstatus="Watched";
              console.log(user_coursevideo.watchedstatus);
               break;
             }
            
          }
       });
      
      for (const user_coursevideo of this.user_courseVideos) {
      for (const coursevideo of this.courseVideoList) {
      if (user_coursevideo.id === coursevideo.id) {
        user_coursevideo.watchedstatus="Watched";
        console.log(user_coursevideo.watchedstatus);
        break;
      } else {
        user_coursevideo.watchedstatus = " "; // This line is not necessary if the default value is already set to an empty string
      }
      }
     }
      
    }*/
      
      }
   },
   async fetchCourseVideo() {
     // console.log("course id",this.course_id);
      const resp = await utils.http.get("/course/"+this.course_id+"/course_videos");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.courseVideoList = data;
          
        }
      }
     
      },
   
    downloadBook() {
    
    //console.log(this.course);

    const link = document.createElement('a');
    const pdfUrl = this.localDomain + '/' + this.course.pdfPath; // Concatenate the URL parts correctly
    window.open(pdfUrl, '_blank');

    //link.href = pdfUrl;
    //link.download = 'book.pdf';   // Specify the desired file name for the downloaded book
    //link.click(); 

    //for a href
    //const pdfUrl = this.localDomain + '/' + this.course.pdfPath; // Concatenate the URL parts correctly
    //window.location.href = pdfUrl;

      //const bookPath =this.localDomain + '/'+this.course.pdfPath; 
      //console.log(this.localDomain + '/'+this.course.pdfPath);
      //const link = document.createElement('a');
      //link.href = window.URL.createObjectURL(new Blob([this.localDomain + '/'+this.course.pdfPath], { type: 'application/pdf' }));
      //console.log("link href",link.href);
      //link.download = 'book.pdf'; // Specify the desired file name for the downloaded book
      //link.click();

  
    },



    goToCourseVideoDetail(course_video) {
      this.$router.push({
        path: "/course/"+this.course_id+"/course_video_detail/" + course_video.id,
      });
    },

  },//Method
};//Export
</script>

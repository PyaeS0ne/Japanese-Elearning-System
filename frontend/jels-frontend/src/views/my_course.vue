<template backgroundColor="#ffd343">
  <div  v-if="loginUser.role==='user'"  class="scrollable-container background-image" >
    <v-row><v-col cols="12" class="mb-3"></v-col></v-row>
      <!-- Course List -->
        <v-row>
          <v-col cols="12" v-for="(mycourse, index) in mycourseList" :key="index">
            <v-card class="rounded-card corner-line-card ml-15 mr-15" @click="goToCourseVideo (mycourse)" height="150px" :style="{ backgroundColor: '#ffffff' }">
              <v-card-text>
                <v-row > 
                <v-col cols="4" >
                  <v-img height="120"
                    :src="localDomain +'/'+ mycourse.imagePath"
                    cover
                  ></v-img>
                </v-col>
                <v-col cols="8" >
                <div class="text-h4 font-weight-bold ml-3">{{ mycourse.title }}</div>
                <div class="text-body-1 mt-4 ml-3">Reference Book : {{ mycourse.refBook }} </div>
                <div class="text-body-1 mt-4 ml-3">Total Video : {{ mycourse.totalVideos }} </div>
              </v-col>
            </v-row>
            </v-card-text>
            </v-card>

          </v-col>
        </v-row>
  </div>
</template>
<style scoped>
.rounded-card {
   border-radius: 17px; /* Adjust the value to change the roundness */
 }
 .corner-line-card {
   position: relative;
   border: 3px solid #ffd343;
   
 }
 .background-image {
  /* Set the background image properties */
  background-image: url('../assets/AllBack1.jpg');
  background-size: cover;
  background-position: center;
  /* Add any other styles you need for the component */
}
</style>
 
<script>
import utils from "../utils/utils";

export default {
  name: "MyCourse",

  components: {},

  data() {
    return {
      loginUser:{},
      localDomain: utils.constant.localDomain,
      //imagePath: utils.constant.imagePath,
      courseVideoList:[],
      mycourseList: [],
      

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
   
    await this.fetchMyCourses();
    
  },

  methods: {
  
    async fetchMyCourses() {
      const resp = await utils.http.get("/enroll/users/"+this.loginUser.id+"/courses");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.mycourseList = data;
        }
         // Calculate video counts for each course
        await this.mycourseList.forEach((course) => {
       // console.log('getting video for course :', course.id);
        this.fetchCourseVideo(course).then((videoCount) => {
        course. totalVideos= videoCount;
        
        });
      });
      
      }
    },
   
    async fetchCourseVideo(course) {
      //console.log("fetichng cidoeos");
      
      const resp = await utils.http.get("/course/"+course.id+"/course_videos");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
           this.courseVideoList = await data;
           return data.length;
        }
        //console.log();
        return 0; 
      }
    },

    goToCourseVideo(course) {
      this.$router.push({
        path: "/course/" + course.id+"/course_videos",
      });
    },

    
  },
};
</script>

<template>
  <div class="scrollable-container">

    <v-row >
      <v-col cols="3" ></v-col>
      <v-col cols="6" class="mt-5">
        <v-card  class="rounded-card " >
             <!-- <v-text-field 
              v-model="searchQuery" class="no-underline" type="text" placeholder="  
              Search by title" @input="searchTitles">
            </v-text-field> -->
            <v-card-text>
              <v-row >
             <v-col cols="11" > 
              <input class="no-border" v-model="searchQuery" type="text" placeholder="Search by Title" @input="searchTitles" />
            </v-col>
            <v-col cols="1" ><svg-icon type="mdi" :path="path"></svg-icon></v-col>
             </v-row>
              
             
            </v-card-text>
        </v-card>
    
      </v-col> 
    </v-row>

    <v-row>
      <!-- Level -->
      <v-col cols="2">  
        <v-card class="mx-auto" width="150" height="300" :style="fixedCardStyle">
        
            <v-list dense nav > 
              <v-list-item class="list_title " @click="goToCourse()"><v-spacer>LEVEL </v-spacer> </v-list-item>
              <v-list-item class="lists"
                v-for="(level, index) in courseLevelList"
                :key="index"
                link
                @click="onClickLevel(level)"
              >
                <v-list-item-content class="list_item font-weight-bold">
                  <v-list-item-title :style="{ fontSize: '18px'}" >{{ level.name }}</v-list-item-title>
                </v-list-item-content>
              </v-list-item>
            </v-list>
        </v-card>
      </v-col>

      <!-- Course List -->
      <v-col cols="10">
        <!--
        <v-row>
          <v-col cols="10">
          
          <v-card
            class="mx-auto"
            color="grey-lighten-3"
            max-width="800">
            <v-card-text>
              <v-text-field
                :loading="loading"
                density="compact"
                variant="solo"
                label="Search templates"
                append-inner-icon="mdi-magnify"
                single-line
                hide-details
                @click:append-inner="onClickTitle"
              ></v-text-field>
            </v-card-text>
          </v-card>
       
          </v-col>
        </v-row>
         -->
         <v-row >
           <!--  <div v-if="courseList.length<1" > No Post Avaliable </div> -->
          <v-col cols="3" v-for="(course, index) in courseList" :key="index">
            <v-card @click="goToCourseDetail(course)" color="#ffffff" width="230" height="320">
              <v-card-text>
                <v-img    
                  :src="localDomain +'/'+ course.imagePath"
                  class="align-end"
                   height="180"
                ></v-img>

                <div style="font-size: 18px;" class="font-weight-bold ">{{ course.title }}</div>
                <div style="font-size: 12px;" >Reference Book:{{ course.refBook }} </div>
                <div style="font-size: 12px;">Fee: {{ course.fee }} MMk</div>
                <div style="font-size: 12px;"> </div>
                <center><v-btn style="font-size: 8px; width:50px ; height:25px ;" class="rounded-btn custom-button"  @click="goToCourseDetail(course)"> See More...</v-btn></center>
                 <!-- <v-btn color="#ffd343"> See More..</v-btn>-->
              </v-card-text>
            </v-card>
          </v-col>
        </v-row>
        
      </v-col>
    </v-row>
  </div>
</template>
<style scoped>
.no-border {
  border: none;
  outline: none;
  border-radius: 0;
}
.no-underline .v-text-field__details {
  display: none;
}
.rounded-card {
   border-radius: 50px;
   height: 50px; /* Adjust the value to change the roundness */
 }

.list_title {
  text-align: center;
  font-size: 19px;
  font-weight: bold;
  background: #ffd343;
 
}
.lists {
  text-align: center;
}
.list_item {
  font-size: 20px;
  font-weight: bold;
}
.rounded-btn {
  border-radius: 13px; /* Adjust the value to change the roundness */
  position: relative;
  padding: 12px 24px;
  cursor: pointer;
  height: 30;
  width: 90px;
  
}
.custom-button {
  border: 3px solid #ffd343;
}
</style>

<script>
import utils from "../utils/utils";
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiMagnify } from '@mdi/js';
export default {
  name: "course",

  components: {
    SvgIcon
   },

  data() {
    return {
      loginUser:{},
      path: mdiMagnify,

      localDomain: utils.constant.localDomain,
      
      courseList: [],
      searchQuery: '',
      courseLevelList: [],
      fixedCardStyle: {
        position: "fixed",
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
    await this.fetchCourseLevels();
    await this.fetchCourses();
    await this.searchTitles();
  },

  methods: {
    async searchTitles() {
    const searchQuery = this.searchQuery.replace(/\s/g, ''); // Remove spaces from the search query
    const resp = await utils.http.get("/course/title/"+this.searchQuery);
   // console.log('Course Status:', resp.status);
    //console.log('Response Data', resp.data);
    if (resp && resp.status === 200) {
      const data = await resp.json();
      if (data) {
        this.courseList = data;
      }
    }
    if(this.searchQuery==='')
    {
      //console.log('query status',this.searchQuery)
      await this.fetchCourses();  
    }
    
    },

    async fetchCourses() {
      const resp = await utils.http.get("/course");
      //console.log('Course Status:', resp.status);
      //console.log('Response Data', resp.data);
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.courseList = data;
        }
      }
    },

    async fetchCourseLevels() {
      const resp = await utils.http.get("/level");
      if (resp && resp.status === 200) {
        const data = await resp.json();
        // data is not undefined, not null
        if (data) {
          this.courseLevelList = data;
        }
      }
    },

    goToCourseDetail(course) {
      this.$router.push({
        path: "/course_detail/" + course.id,
      });
    },
   
    async goToCourse() {
      const resp = await utils.http.get("/course");
      //console.log('Course Status:', resp.status);
      //console.log('Response Data', resp.data);
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.courseList = data;
        }
      }
    },

    async onClickLevel(level) {
      const resp = await utils.http.get("/course/level/" + level.id);
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.courseList = data;
        }
      }
    },

    
  },
};
</script>

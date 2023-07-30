<template>
  <div class="custom-padding">
    <v-sheet width="1000" class="mx-auto">
    <v-row>
        
        <v-col cols="12">
            <div>
        <v-toolbar style="background-color: #ffd343;" flat rounded>
        <v-toolbar-title class="text-center font-weight-bold">Manage Course Videos</v-toolbar-title>
        <v-spacer></v-spacer>
        <v-btn rounded @click="dialogCreate=true" dark >Add New Video</v-btn>
      </v-toolbar>
    </div>
    
    </v-col>

    
        <div v-if="courseVideoList.length>0" class="custom-padding">
            <v-row>
        
      <v-col cols="3" v-for="(video, index) in courseVideoList" :key="index">
        
        
        <v-card   width="900" height="300">
          <v-card-title>
            {{ video.title }}
          </v-card-title>
          <video 
         
            ref="videoPlayer"
            width="100%"
           
           @click="toPlay=video ;showVideoPlayer=true "
            :src="localDomain + '/' + video.path"
          ></video>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-icon
        
        class="mr-2"
        @click="onClickUpdate(video)"
      >
        mdi-pencil
      </v-icon>
      <v-icon
        
        @click="toDeleteItem=video; dialogDelete=true" 
      >
        mdi-delete
      </v-icon>
          </v-card-actions>
        
        </v-card>
      
      </v-col>
    </v-row>
   
    </div>
      <div  v-else class="custom-padding">
        <v-col> No Video Avaliable</v-col>
      </div>
      
    

    
   
    </v-row>

    <!----delete dialog-->

    <!-- Delete Dialog -->
  <v-dialog v-model="dialogDelete" width="400"
  
  >
      <v-card>
        <!-- Dialog Heading -->
        <v-card-title  >
          <div>Delete this video ?</div>
          <v-spacer></v-spacer>
          <v-btn icon @click="toDeleteItem={};
                              dialogDelete = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-card-title>

        <!-- Delete video Info -->
        <v-card-text class="pa-6">
          Are you sure you want to delete this video ?
        </v-card-text>

        <!-- Delete video Btn -->
        <v-card-actions class="justify-end">
          <v-btn  class="ma-2"  @click="toDeleteItem={}; dialogDelete = false">Cancel</v-btn>
          
          <v-btn color="#ffd343"  @click="deleteVideo()"> <span v-if="!loading">Confirm</span>
                  <v-progress-circular
                    v-else
                    indeterminate
                    color="#ffd343"
                  ></v-progress-circular></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
   <!----update dialog-->
    <v-dialog v-model="dialogUpdate" width="500" scrollable>
      <v-card>
        <!-- Dialog Heading -->
        <v-toolbar color="#ffd343">
          <div>Update Video</div>
          <v-spacer></v-spacer>
          <v-btn
            icon
            @click="
              toUpdateItem = {};
              dialogUpdate = false;
            "
          >
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>

        <!-- Update Form -->
        <v-card-text class="pa-4">
          <v-form ref="videoForm" v-model="videoForm">
            <!-- Update Video Title -->
            <v-text-field
              v-model="toUpdateItem.title"
              :counter="50"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length <= 50) ||
                  'Title must be less than 50 characters',
              ]"
              label="Title"
              required
            ></v-text-field>

            <!---update video file-->
            <v-file-input
              show-size
              outlined
              dense
              prepend-icon=""
              append-icon="mdi-movie"
              v-model="toUpdateItem.video"
              accept=".mp4, .avi"
              label="Video"
            ></v-file-input>
          </v-form>
        </v-card-text>

        <v-card-actions>
          <!-- Update Btn -->
          <v-btn
            :disabled="!videoForm"
            color="#ffd343"
            class="mr-4"
            @click="updateVideo()"
          >
            <span v-if="!loading">Update</span>
            <v-progress-circular
              v-else
              indeterminate
              color="primary"
            ></v-progress-circular>
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

     <!----Add dialog-->
     <v-dialog v-model="dialogCreate" width="500" scrollable>
      <v-card>
        <!-- Dialog Heading -->
        <v-toolbar color="#ffd343">
          <div>Add Video</div>
          <v-spacer></v-spacer>
          <v-btn
            icon
            @click="
              
              dialogCreate = false;
            "
          >
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>

        <!-- add Form -->
        <v-card-text class="pa-4">
          <v-form ref="videoForm" v-model="videoForm">
            <!-- add Video Title -->
            <v-text-field
              v-model="toCreateItem.title"
              :counter="50"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length <= 50) ||
                  'Title must be less than 50 characters',
              ]"
              label="Title"
              required
            ></v-text-field>

            <!---update video file-->
            <v-file-input
              show-size
              outlined
              dense
              prepend-icon=""
              append-icon="mdi-movie"
              v-model="toCreateItem.video"
              accept=".mp4, .avi"
              label="Video"
            ></v-file-input>
          </v-form>
        </v-card-text>

        <v-card-actions>
          <!-- Update Btn -->
          <v-btn
            :disabled="!videoForm"
            color="#ffd343"
            class="mr-4"
            @click="addVideo()"
          >
            <span v-if="!loading">Add Video</span>
            <v-progress-circular
              v-else
              indeterminate
              color="primary"
            ></v-progress-circular>
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>
<v-dialog v-model="showVideoPlayer"  width="800" persistent>
    
        
            <v-card class="card-container" >
                <v-toolbar flat style="background-color: #e7b614e9;">{{ toPlay.title }}
                    <v-spacer></v-spacer>
              <v-btn
                icon
                @click="
                  toPlay = {};

                  showVideoPlayer = false;
                  
                "
              >
                <v-icon>mdi-close</v-icon>
              </v-btn>
                </v-toolbar>
              <v-card-text >
               
        
                <video
                  width="98%"
                  :src="localDomain + '/'+toPlay.path"
                  controls
                ></video>
                
               <!-- <div class="text-h6 text-center">{{ courseVideo.title }}</div>-->
               
              
               
                
              </v-card-text>
            </v-card>

    

</v-dialog>
    
    
    

</v-sheet>  

  
  </div>
</template>

<script>

import constant from "@/utils/constant";
import http from "@/utils/http";
import utils from "@/utils/utils";
import axios from "axios";

export default {
  name: "admin_course_video",
  components: {},
  data() {
    return {
      course: [],
      courseVideoList: [],
      localDomain: utils.constant.localDomain,
      course_id: this.$route.params.id,
      toCreateItem: {
       
        title: "",
        path: "",
        video:null,
        
        
      },
      toUpdateItem: {
        id: null,
        title: "",
        path: "",
        video: null,
        course: null,
      },
      toDeleteItem: {
        id: null,
        title: "",
        path: "",
        
        course: null,
      },
      dialogCreate:false,
      dialogDelete: false,
      dialogUpdate: false,
      videoForm: false,
      loading:false,
      showVideoPlayer:false,
      toPlay:{},
    };
  },

  async created() {
    await this.fetchCourseVideo();
  },
  methods: {
    async fetchCourseVideo() {
      const resp = await utils.http.get(
        "/course/" + this.course_id + "/course_videos"
      );
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.courseVideoList = data;
        }
      }
    },
    async onClickUpdate(video) {
      this.toUpdateItem.id = video.id;
      this.toUpdateItem.title = video.title;
      this.toUpdateItem.path = video.path;
      //this.toUpdateItem.course = video.course.id;
      this.dialogUpdate = true;
    },
    async updateVideo(){
        this.loading=true;

        if(this.toUpdateItem.video!==null){
            const respFile=await http.putMedia('/admin/file/update',this.toUpdateItem.video,this.toUpdateItem.path);
    
    if (respFile.status === 200) {
        this.toUpdateItem.path = await respFile.text();
        
        
    }
    else {
       // console.log("error : file update failed");
    }
        }
       // console.log("new path , ",this.toUpdateItem.path);
        const respVideo=axios.put(constant.localDomain+'/admin/courseVideo/update/'+this.toUpdateItem.id,{
            title:this.toUpdateItem.title,
            path:this.toUpdateItem.path,
            course:{id:this.course_id}
        });
        if((await respVideo).status===200){
           // console.log('ok', (await respVideo).data)

            this.dialogUpdate=false;
            await this.fetchCourseVideo();

            this.loading=false;
            window.location.reload();
            
        }
        
    },
    async deleteVideo(){
        //console.log("in delete");
        this.loading=true;
        const resp=axios.delete(constant.localDomain+"/admin/courseVideo/deleteByVideoId/"+this.toDeleteItem.id);

        if((await resp).status===200){
           // console.log("deleted")
            this.todeleteItem={};
            this.loading=false;
            this.dialogDelete=false;
            await this.fetchCourseVideo();
        }
    },
    async addVideo(){
      this.loading=true;
        const resp = await http.postMedia("/admin/file/create", this.toCreateItem.video);
        //console.log("status", resp.status);
        if (resp.status === 200) {
          this.toCreateItem.path = await resp.text();
          

          await axios
            .post(constant.localDomain + "/admin/createCourseVideo", {
              title: this.toCreateItem.title,
              path: this.toCreateItem.path,
              course: { id: this.course_id },
            })
            .then((Response) => {
                if(Response.status===200){
              
              this.dialogCreate=false;
              window.location.reload();

                }else{
                   // console.log("error while adding video");
                }
            });
        } else {
          this.errorAlert = true;
         // console.log("error while vido add ", resp);
        } 
    },
    closePlayer() {
      this.showVideoPlayer = false;
    },
    onclickDelete(video){
        this.toDeleteItem.id=video.id;
        this.toDeleteItem.title=video.title;
        this.toDeleteItem.path=video.path;
        this.toDeleteItem.course=video.course;
        this.dialogDelete=true;
    }
  },
};
</script>
<style scoped>
.custom-padding{

    padding-left: 20px;
    padding-right: 20px;
    padding-top: 30px;
    padding-bottom: 20px;

}
.fix-component{
    position: fixed;

}


</style>

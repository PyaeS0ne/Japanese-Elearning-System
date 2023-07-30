<template>
  <div>
    <v-row justify="center">
      <!-- Sidebar -->

      <v-col cols="2">
        <admin_sidebar></admin_sidebar>
      </v-col>

      <v-col cols="10" class="mt-4" justify="center">
        <v-data-table
          style="width: 1010px"
          :headers="headers"
          :items="courseList"
          :item-key="title"
          :items-per-page="5"
          sort-by="title"
          class="custom-table elevation-3"
        >
          <template v-slot:top >
            <v-toolbar 
            
            flat
            rounded
            color="#ffd343"
            
            >
              <v-toolbar-title class="text-center font-weight-bold">
                <v-col>Course List</v-col></v-toolbar-title
              >
             
              
              <v-spacer></v-spacer>
              <v-btn @click="onClickAdd" small rounded dark>Add Course</v-btn>
            </v-toolbar>
          </template>
         <template v-slot:item.number="{item,index}">
          {{index+1}}
         </template>

         
         <template v-slot:item.videos="{ item }">
            <router-link :to="'/admin/manage_courseVideo/'+ item.id ">
              Manage Videos
            </router-link>
          </template>

         <template v-slot:item.image="{item}">
          <v-img :src='localDomain + "/"+item.imagePath '
          
          width="70"
          height="110"
          @click=""
          contain
          

          ></v-img>
         </template>

          <template v-slot:item.actions="{ item }">
            <v-icon  class="mr-2" @click="onClickUpdateItem(item)">
              mdi-pencil
            </v-icon>
            <v-icon
              
              @click="
                toDeleteItem = item;
                dialogDelete = true;
                onclickdelete();
              "
            >
              mdi-delete
            </v-icon>
          </template>
        </v-data-table>
        <!-- Delete Dialog -->
        <v-dialog v-model="dialogDelete" width="550" scrollable>
          <v-card>
            <!-- Dialog Heading -->
            <v-toolbar color="#ffd343" >
              <div>Delete this Course ?</div>
              <v-spacer></v-spacer>
              <v-btn
                icon
                @click="
                  toDeleteItem = {};

                  dialogDelete = false;
                  hasUser = false;
                "
              >
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </v-toolbar>

            <!-- Delete Course Info -->
            <v-card-text class="pa-4">
              <v-row dense>
                <v-col cols="3" class="font-weight-bold">Ttile</v-col>
                <v-col cols="7">{{ toDeleteItem.title }}</v-col>
                <v-col cols="3" class="font-weight-bold">Level</v-col>
                <v-col cols="7">{{ deleteItemLevel }}</v-col>
                <v-col cols="3" class="font-weight-bold">fee</v-col>
                <v-col cols="7">{{ toDeleteItem.fee }}</v-col>
                <v-col cols="3" class="font-weight-bold">Reference book</v-col>
                <v-col cols="7">{{ toDeleteItem.refBook }}</v-col>
                <v-col cols="3" class="font-weight-bold">Description</v-col>
                <v-col cols="7">{{ toDeleteItem.description }}</v-col>

                <v-alert
                  v-if="hasUser"
                  dense
                  outlined
                  prominent
                  border="left"
                  type="warning"
                >
                  Cannot Delete! This course has enrolled users. Try updating
                  instead.
                </v-alert>
              </v-row>
            </v-card-text>

            <!-- Delete knowledge Btn -->
            <v-card-actions class="justify-end">
              <v-btn
                class="ma-2"
                @click="
                  toDeleteItem = {};
                  dialogDelete = false;
                  hasUser = false;
                "
                :disabled="loading"
                >Cancel</v-btn
              >

              <v-btn color="#ffd343" :disabled="hasUser" @click="deleteCourse()"
                > <span v-if="!loading">Confirm</span>
                  <v-progress-circular
                    v-else
                    indeterminate
                    color="#ffd343"
                  ></v-progress-circular></v-btn
              >
            </v-card-actions>
          </v-card>
        </v-dialog>

        <!-- Update Dialog -->
        <v-dialog v-model="dialogUpdate" width="600" persistent scrollable>
          <v-card>
            <!-- Dialog Heading -->
            <v-toolbar color="#ffd343" >
              <div>Update This Course?</div>
              <v-spacer></v-spacer>
              <v-btn icon @click="dialogUpdate = false">
                <v-icon>mdi-close</v-icon>
              </v-btn>
            </v-toolbar>

            <!-- Update Form -->
            <v-card-text class="pa-4">
              <v-form ref="knowledgeForm" v-model="courseForm">
                <!-- Update Course Title -->
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
                <!-- Update Course level -->

                <v-select
                  v-model="toUpdateItem.level"
                  dense
                  outlined
                  item-text="name"
                  item-value="id"
                  :items="levelList"
                  label="Level"
                ></v-select>

                <!-- Update Course RefBook -->
                <v-text-field
                  v-model="toUpdateItem.refBook"
                  :counter="50"
                  :rules="[
                    (v) => !!v || 'Reference Book required',
                    (v) =>
                      (v && v.length <= 100) ||
                      'Reference Book must be less than 100 characters',
                  ]"
                  label="Reference Book"
                  required
                ></v-text-field>

                <!-- Update Course Fee -->

                <v-text-field
                  v-model="toUpdateItem.fee"
                  outlined
                  dense
                  label="Course Fee"
                  :rules="[
                    (v) => !!v || 'Required',
                    (v) =>
                      (v && v > 999 && v <= 999999) ||
                      'fee must be between 1000 and 999999 MMK',
                  ]"
                ></v-text-field>

                <!-- Update knowledge Description -->
                <v-textarea
                  v-model="toUpdateItem.description"
                  :counter="500"
                  :rules="[
                    (v) => !!v || 'Required',
                    (v) =>
                      (v && v.length <= 500) ||
                      'Title must be less than 500 characters',
                  ]"
                  label="Description"
                  required
                ></v-textarea>

                <!-- Update Course Image -->
                <v-file-input
                  show-size
                  outlined
                  dense
                  prepend-icon=""
                  append-icon="mdi-image"
                  v-model="toUpdateItem.imageFile"
                  accept=".jpg, .png"
                  label="Image"
                  @change="onChangeImage"
                ></v-file-input>
                <v-img
                  v-if="imagePreviewPath != null"
                  :src="imagePreviewPath"
                  width="200"
                  height="150"
                  contain
                ></v-img>

                <!-- Update Course PDF -->
                <v-file-input
                  show-size
                  dense
                  accept=".pdf"
                  label="Reference Book PDF"
                  v-model="toUpdateItem.pdfFile"
                ></v-file-input>

              
                
              </v-form>
            </v-card-text>
            <v-card-actions>
              <!-- Update Btn -->
              <v-btn
                  :disabled="!courseForm"
                  color="#ffd343" 
                  class="mr-4"
                  @click="updateCourse()"
                >
                  <span v-if="!loading">Update</span>
                  <v-progress-circular
                    v-else
                    indeterminate
                    color="#ffd343"
                  ></v-progress-circular>
                </v-btn>
            </v-card-actions>
          </v-card>
        </v-dialog>
        <!-- Success Alert -->
        <v-expand-transition>
          <v-alert
            class="mt-3"
            v-show="successAlert"
            dense
            type="success"
            dismissible
          >
            Update Course success!
          </v-alert>
        </v-expand-transition>
        <!-- Error Alert -->
        <v-alert
          class="mt-3"
          v-show="errorAlert"
          dense
          type="error"
          dismissible
        >
          Update Course Failed!
        </v-alert>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import admin_sidebar from "../components/admin_sidebar.vue";
import constant from "@/utils/constant";
import http from "@/utils/http";
import utils from "@/utils/utils";
import axios from "axios";

export default {
  name: "admin_knowledge_list",
  components: { admin_sidebar },
  data() {
    return {
      localDomain:constant.localDomain,
      loginUser: {},
      headers: [
        {
          text: "No.",
          align: "start",
          sortable: false,
          value:'number',
        },
        { text: "Image",  value :"image",sortable: false },
        {
          text: "Title",

          sortable: false,
          value: "title",
        },

        { text: "Level", value: "level.name", sortable: false },
        //{ text: "Description ", value: "description", sortable: false },
        { text: "fee ", value: "fee", sortable: false },
        
        //{ text: "Pdf Path ", value: "pdfPath", sortable: false },
        { text: "Videos ", value: "videos", sortable: false },
        { text: "Enroller Users ", value: "enrolledUsers", sortable: false },

        { text: "Actions", value: "actions", sortable: false },
      ],
      courseList: [],
      levelList: [],

      courseVideoList: [],
      courseForm: false,
      toDeleteItem: {},
      hasUser: false,
      dialogDelete: false,

      dialogUpdate: false,
      toUpdateItem: {
        id: null,

        title: "",
        level: 0,
        fee: null,
        refBook: "",
        description: "",
        imageFile: null,
        imagePath: "",
        pdfFile: null,
        pdfPath: "",
        videoFiles: null,
      },
      imagePreviewPath: null,
      deleteItemLevel: "",

      loading: false,
      errorAlert: false,
      successAlert: false,
    };
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
    this.fetchCourses();
    this.fetchLevel();
  },

  methods: {
    async fetchCourses() {
      const resp = await utils.http.get("/course");
      //console.log("Course Status:", resp.status);
      //console.log("Response Data", resp.data);
      if (resp && resp.status === 200) {
        const data = await resp.json();
        if (data) {
          this.courseList = data;
        }
        // Calculate video counts for each course
        await this.courseList.forEach((course) => {
         // console.log("getting video for course :", course.id);
          this.fetchCourseVideo(course).then((videoCount) => {
            course.totalVideos = videoCount;
          });
        });
      }
    },

    async fetchCourseVideo(item) {
      //console.log("fetichng cidoeos");

      const resp = await utils.http.get(
        "/course/" + item.id + "/course_videos"
      );
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
    async fetchLevel() {
      //console.log("fetching levels");
      await axios.get(constant.localDomain + "/level").then((Response) => {
        this.levelList = Response.data;
        //console.log(this.levelList);
      });
    },
    async deleteCourse() {
      //console.log("course has no user");

      this.loading = true;
      await axios
        .delete(
          constant.localDomain + "/admin/course/delete/" + this.toDeleteItem.id
        )
        .then((Response) => {
          if (Response.status == 200) {
            this.loading = false;
            this.dialogDelete = false;
            this.fetchCourses();
          } else {
            this.errorAlert = true;
          }
        });
    },
    async onClickUpdateItem(item) {
      this.dialogUpdate = true;
      //this.toUpdateItem = Object.assign({}, item);
      this.toUpdateItem.id = item.id;
      this.toUpdateItem.title = item.title;
      this.toUpdateItem.level = item.level.id;
      this.toUpdateItem.fee = item.fee;
      this.toUpdateItem.refBook = item.refBook;
      this.toUpdateItem.description = item.description;
      this.toUpdateItem.imagePath = item.imagePath;
      this.toUpdateItem.pdfPath = item.pdfPath;
      this.toUpdateItem.imageFile = null;
      this.toUpdateItem.pdfFile = null;
      this.toUpdateItem.videoFiles = null;
    },
    async updateCourse() {
      this.loading = true;

      if (this.toUpdateItem.imageFile != null) {
        //console.log("old image path ", this.toUpdateItem.imagePath);
        const resp = await http.putMedia(
          "/admin/file/update",
          this.toUpdateItem.imageFile,
          this.toUpdateItem.imagePath
        );

        if (resp.status === 200) {
          this.toUpdateItem.imagePath = await resp.text();
          //console.log("image updated ", this.toUpdateItem.imagePath);
        } else {
          //console.log("error : file update failed");
        }
      }
      if (this.toUpdateItem.pdfFile != null) {
        //console.log("new file ", this.toUpdateItem.pdfPath);
        const resp = await http.putMedia(
          "/admin/file/update",
          this.toUpdateItem.pdfFile,
          this.toUpdateItem.pdfPath
        );

        if (resp.status === 200) {
          this.toUpdateItem.pdfPath = await resp.text();
        } else {
          this.errorAlert=true;
          //console.log("error : file update failed");
        }
      }

      if (this.toUpdateItem.videoFiles != null) {
        //console.log("updating videos");

        await this.fetchCourseVideo(this.toUpdateItem);
        if (this.courseVideoList.length > 0) {
          await axios
            .delete(
              constant.localDomain +
                "/admin/courseVideo/delete/" +
                this.toUpdateItem.id
            )
            .then((Response) => {
             // console.log("old videos deleted :", Response.data);
            });
        }

        for (
          let index = 0;
          index < this.toUpdateItem.videoFiles.length;
          index++
        ) {
          //console.log("current video :", this.toUpdateItem.videoFiles[index]);
          await this.createVideo(this.toUpdateItem.videoFiles[index]);
        }
      }

      console.log("image path ", this.toUpdateItem.imagePath);
      const respCourse = await axios.put(
        constant.localDomain + "/admin/course/update/" + this.toUpdateItem.id,
        {
          title: this.toUpdateItem.title,
          level: { id: this.toUpdateItem.level },
          refBook: this.toUpdateItem.refBook,
          fee: this.toUpdateItem.fee,
          description: this.toUpdateItem.description,
          imagePath: this.toUpdateItem.imagePath,
          pdfPath: this.toUpdateItem.pdfPath,
        }
      );
      if (respCourse && respCourse.status == 200) {
        this.dialogUpdate = false;

        await this.fetchCourses();

        setTimeout(() => {
          this.successAlert = true;
        }, 500);
        setTimeout(() => {
          this.successAlert = false;
        }, 10000);
      } else {
        this.errorAlert = true;
      }
      this.loading = false;
      document.location.reload();
    },
    async onclickdelete() {
     // console.log("level", this.toDeleteItem.level);

      this.deleteItemLevel = this.toDeleteItem.level.name;

     // console.log("totalUser ", this.toDeleteItem.enrolledUsers);
      if (this.toDeleteItem.enrolledUsers > 0) {
        this.hasUser = true;
      }
    },
    async createVideo(video) {
      try {
        const videoTitle = video.name.substring(0, video.name.lastIndexOf("."));
        let videoPath = null;
        const resp = await http.postMedia("/admin/file/create", video);
       // console.log("status", resp.status);
        if (resp.status === 200) {
          videoPath = await resp.text();
          //console.log("videot path ", videoPath, "video naem : ", videoTitle);
         // console.log("course ", this.createdCourse);

          await axios
            .post(constant.localDomain + "/admin/createCourseVideo", {
              title: videoTitle,
              path: videoPath,
              course: { id: this.toUpdateItem.id },
            })
            .then((Response) => {
              //console.log("rsponse ", Response);
            });
        } else {
          this.errorAlert = true;
         // console.log("error while vido add ", resp);
        }
      } catch (error) {
        //console.log("error");
      }
    },
    async onChangeImage() {
      this.imagePreviewPath = null;
      this.imagePreviewPath = URL.createObjectURL(this.toUpdateItem.imageFile);
    },
    async onClickAdd(){
      this.$router.push("/admin/course/create")
    }
  },
};
</script>
<style scoped>
.custom-table th:nth-child(1) {
  max-width: 60px;
}

.custom-table th:nth-child(2) {
  max-width: 50px;
}

/* Additional column styles */

.div-sticky {
  position: -webkit-sticky;
  position: sticky;
  top: 0;
}

</style>
<style lang="scss">  

  thead{

    
  }
  tbody {
    
    
     tr:hover {
        background-color: transparent !important;
     }
  }
</style>

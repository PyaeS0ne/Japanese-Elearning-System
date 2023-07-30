<template>
  <div>
    <v-row>
      <!-- Sidebar -->
      <v-col cols="2">
        <admin_sidebar></admin_sidebar>
      </v-col>

      <v-col cols="10" class="mt-4">
        <v-row>
          <v-col cols="1"></v-col>
          <v-col cols="9">
            <v-toolbar style="background-color: #ffd343" flat rounded>
              <v-toolbar-title class="text-center font-weight-bold"
                >Create Knowledge</v-toolbar-title
              >
            </v-toolbar>

            <v-sheet class="mx-auto mt-4">
              <v-form ref="myform" fast-fail @submit.prevent>
                <v-text-field
                  v-model="title"
                  outlined
                  dense
                  label="Title"
                  :rules="titleRules"
                ></v-text-field>

                <v-textarea
                  counter
                  outlined
                  label="Introduction"
                  :rules="introRules"
                  v-model="intro"
                ></v-textarea>

                <v-select
                  v-model="topics"
                  multiple
                  dense
                  outlined
                  :items="['anime', 'food', 'culture', 'language', 'history']"
                  :rules="topicRules"
                  label="Select topic"
                  required
                ></v-select>

                <v-file-input
                  show-size
                  outlined
                  dense
                  prepend-icon=""
                  append-icon="mdi-image"
                  v-model="featureImage"
                  accept=".jpg, .png"
                  label="Feature Image"
                  @change="onChangeImage"
                ></v-file-input>
                <v-img
                  v-if="imagePreviewPath != null"
                  :src="imagePreviewPath"
                  width="200"
                  height="150"
                  contain
                ></v-img>

                <v-file-input
                  show-size
                  outlined
                  dense
                  prepend-icon=""
                  append-icon="mdi-file-word"
                  v-model="contentFile"
                  accept=".docx"
                  label="Content file"
                  @change="generatetitle"
                ></v-file-input>

                <center>
                  <v-btn
                    color="#ffd343"
                    style="width: 180px"
                    class="mt-2"
                    @click="submit"
                    :disabled="!iscomplete"
                    ><span v-if="!loading">Create Knowledge</span>
                    <v-progress-circular v-else indeterminate color="black">
                    </v-progress-circular
                  ></v-btn>
                </center>
                <v-alert type="success" v-model="successAlert" dismissible>
                  knowledge created !
                </v-alert>
                <v-alert
                  class="mt-3"
                  v-show="errorAlert"
                  dense
                  type="error"
                  dismissible
                >
                  Create knowledge Failed !
                </v-alert>
              </v-form>
            </v-sheet>
          </v-col>
        </v-row>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import admin_sidebar from "../components/admin_sidebar.vue";
import constant from "@/utils/constant";
import http from "@/utils/http";
import axios from "axios";

export default {
  name: "checkout",
  components: { admin_sidebar },
  data: () => ({
    loading: false,
    featureImage: null,
    imagePreviewPath: null,
    contentFile: null,
    title: "",
    titleRules: [
      (value) => {
        if (value?.length > 2) return true;
        return "Title  must be at least 3 characters.";
      },
    ],
    topics: [],

    topicRules: [(v) => v.length > 0 || "Please select at least one item"],
    intro: "",
    introRules: [
      (value) => {
        if (value?.length > 10) {
          if (value?.length > 200) return " not more than 200 characters";
          return true;
        }
        return "Introduction must have at least 10 characters";
      },
    ],
    errorAlert: false,
    successAlert: false,
  }),
  computed: {
    iscomplete() {
      return (
        this.title &&
        this.intro &&
        this.contentFile != null &&
        this.topics != null
      );
    },
  },
  methods: {
    async submit() {
      this.loading = true;
      let contentPath = null;
      const resp = await http.postMedia("/admin/file/create", this.contentFile);
      // console.log('resp staus',resp.status,'resp text ');
      if (resp.status === 200) {
        contentPath = await resp.text();
      } else {
        this.errorAlert = true;
      }
      let imagePath = null;
      const respImg = await http.postMedia(
        "/admin/file/create",
        this.featureImage
      );
      // console.log('resp staus',resp.status,'resp text ');
      if (respImg.status === 200) {
        imagePath = await respImg.text();
      } else {
        this.errorAlert = true;
      }

      try {
        await axios
          .post(constant.localDomain + "/admin/knowledge/create ", {
            title: this.title,
            tag: this.topics.join(","),
            contentPath: contentPath,
            featureImage: imagePath,
            intro: this.intro,
          })
          .then((Response) => {
            // console.log("created : ", Response.data);
            this.$refs.myform.reset();
            this.loading = false;
            this.successAlert = true;
          });
      } catch (error) {
        this.errorAlert = true;
        // console.log("error while creating content ", error);
      }
    },
    async onChangeImage(featureImage) {
      this.imagePreviewPath = null;
      this.imagePreviewPath = URL.createObjectURL(featureImage);
    },
  },
};
</script>

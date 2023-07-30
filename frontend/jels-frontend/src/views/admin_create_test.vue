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
            <v-toolbar style="background-color: #ffd343" rounded flat>
              <v-toolbar-title class="text-center font-weight-bold"
                >Create Test</v-toolbar-title
              >
            </v-toolbar>
            <v-sheet class="mx-auto mt-5">
              <v-form ref="testForm" fast-fail @submit.prevent>
                <v-text-field
                  v-model="title"
                  outlined
                  dense
                  label="Test Title"
                  :rules="titleRules"
                >
                </v-text-field>
                <v-select
                  v-model="level"
                  dense
                  outlined
                  item-text="name"
                  item-value="id"
                  :items="levelList"
                  label="Select Level"
                  :rules="levelRules"
                  required
                ></v-select>

                <center>
                  <v-btn
                    color="#ffd343"
                    style="width: 180px"
                    class="mt-2"
                    @click="createTest"
                    :disabled="!iscomplete"
                    ><span v-if="!loading">Create Test</span>
                    <v-progress-circular v-else indeterminate color="black">
                    </v-progress-circular>
                  </v-btn>
                </center>
                <v-alert type="success" v-model="successAlert" dismissible>
                  Test Successfully created !
                </v-alert>
                <v-alert
                  class="mt-3"
                  v-show="errorAlert"
                  dense
                  type="error"
                  dismissible
                >
                  Create Test Failed !
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
import constant from "@/utils/constant";
import axios from "axios";
import admin_sidebar from "@/components/admin_sidebar.vue";

export default {
  name: "admin_create_test",
  components: { admin_sidebar },
  data() {
    return {
      title: "",
      titleRules: [
        (value) => {
          if (value?.length > 3) return true;
          return "Title  must be at least 3 characters.";
        },
      ],
      level: null,
      levelList: [],
      levelRules: [(v) => !!v || "Please Choose Level"],

      successAlert: false,
      errorAlert: false,
      loading: false,
    };
  },
  computed: {
    iscomplete() {
      return this.title && this.level;
    },
  },
  async created() {
    await this.fetchLevel();
  },
  methods: {
    async fetchLevel() {
      // console.log("fetching levels");
      await axios.get(constant.localDomain + "/level").then((Response) => {
        this.levelList = Response.data;
        // console.log(this.levelList);
      });
    },
    async createTest() {
      this.loading = true;
      // console.log("title : ", this.title);
      // console.log("level id ", this.level);

      const resp = await axios.post(
        constant.localDomain + "/admin/test/create",
        {
          title: this.title,
          published: true,
          level: { id: this.level },
        }
      );
      if (resp && (await resp).status == 200) {
        // console.log("created test : ", (await resp).data);
        this.loading = false;
        this.successAlert = true;
        this.$refs.testForm.reset();
      } else {
        //console.log("error while creating test ", (await resp).data);
        this.errorAlert = true;
      }
    },
  },
};
</script>
<style scoped>
.test {
  padding-top: 20px;
}
</style>

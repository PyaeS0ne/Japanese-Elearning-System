<template>
    
  <div class="testList">
    <v-row>
      <v-col cols="8">
        <label for="testList" class="testLabel"> Tests</label>
      </v-col>
      <v-spacer></v-spacer>
      <v-col cols="2">
        <v-select
          rounded
          solo
          dense
          v-model="selectedLevel"
          item-text="name"
          item-value="id"
          :items="levelList"
          label="Level"
          @change="showByLevel"
        ></v-select>
      </v-col>
    </v-row>
    <v-row>
        <v-col v-if="testList.length<1" class="testList" align="center"> No Test Avaliable </v-col>
      <v-col 
      cols="6" v-for="test in testList" :key="test.id">
        <v-card class="rounded-card corner-line-card elevation-2">
          <v-card-title> {{ test.title }}</v-card-title>
          <v-card-subtitle> Level - {{ test.level.name }}</v-card-subtitle>
          <v-card-actions class="justify-end">
            <v-btn
              class="elevation-1 ma-1"
              rounded
              small
              color="#ffd343"
              @click="goToTestDetail(test)"
              >take test</v-btn
            >
          </v-card-actions>
        </v-card>
      </v-col>
    </v-row>
  </div>
</template>
<script>
import constant from "@/utils/constant";
import axios from "axios";

export default {
  name: "testList",
  components: {},
  data() {
    return {
      testList: [],
      selectedLevel: null,
      levelList: [],
    };
  },
  async created() {
    await this.fetchTestList();
    await this.fetchLevel();
  },
  methods: {
    async fetchTestList() {
      const resp = await axios.get(constant.localDomain + "/test/all");

      if (resp && resp.status == 200) {
        this.testList = resp.data;
      }
    },
    async fetchLevel() {
      //console.log("fetching levels");
      await axios.get(constant.localDomain + "/level").then((Response) => {
        this.levelList = Response.data;
        //  console.log(this.levelList);
      });
      this.levelList.push("All");
    },
    async goToTestDetail(test) {
      this.$router.push("/test_detail/" + test.id);
    },
    async showByLevel() {
      if (this.selectedLevel == "All") {
        await this.fetchTestList();
      }
      // console.log("level id ", this.selectedLevel);
      await axios
        .get(constant.localDomain + "/test/level/" + this.selectedLevel)
        .then((Response) => {
          this.testList = Response.data;
        });
    },
  },
};
</script>
<style scoped>
.testList {
  padding-top: 30px;
  padding-left: 50px;
  padding-right: 50px;
}
.testLabel {
  padding-left: 5px;
  font-size: 4ch;
  font-weight: bold;
}
.rounded-card {
  border-radius: 15px; /* Adjust the value to change the roundness */
}
.corner-line-card {
  position: relative;
  border: 1px solid #ffd343;
}
</style>

<template>
  <div>
    <v-row >
     <v-col cols="12">
       <v-img src="../assets/Knowledge1.jpg"></v-img>
     </v-col>
 </v-row>
 <v-row >
     <v-col cols="10" > </v-col>
     <v-col cols="2" >
      <v-select 
      
          v-model="selectedTopic"
          :style="{ backgroundColor: 'transparent', width: '120px' }"
          :items="topics"
          rounded
          solo
          dense
          label="TOPICS"
          @change="showbyCategory"
  ></v-select>
     </v-col>
 </v-row>
 
 <div v-if="knowledgeList.length<1" class="text-center"> No post available </div>
  <div v-else>
    <div class="text-center">
    
    </div>
  <v-col>

    <v-row   >
      <v-col cols="6" v-for="knowledge in knowledgeList" :key="knowledge.id">
        <v-card  @click="goToKnowledgeDetail(knowledge)" height="280" >
          <v-img
    class="white--text align-end"
    height="150"
    :src="localDomain +'/'+knowledge.featureImage"
  >
  <v-card-title class="outlined-text">
            {{knowledge.title}}
            
          </v-card-title>
  </v-img>
          
  <v-card-subtitle style="color: rgb(255, 149, 0); ">
            {{ knowledge.date }}
          </v-card-subtitle> 
          <v-card-text >  {{knowledge.intro}}</v-card-text>
          
        </v-card>
      </v-col>

        
        
    
    </v-row>
  </v-col>
</div>
</div>
</template>

<script>
import constant from "@/utils/constant";
import utils from "../utils/utils";
import axios from "axios";
import router from "@/router";

export default {
  name: "knowledge",

  components: {},

  data() {
    return {
      selectedTopic:"",
      topics:[
          "all",
        "language",
        "culture",
        "history",
        "food",
        "anime"

        

      ],
     
      localDomain: utils.constant.localDomain,
      knowledgeList: [],
      tag:this.$route.params.tag,
    };
  },

  async created() {
    await this.fetchKnowledges();
  },
  
  methods: {
    async fetchKnowledges() {
      axios.get(this.localDomain+'/knowledge/tag',{
        params:{
          tag:this.tag
        }
      }).then((Response) => {
        this.knowledgeList = Response.data.reverse();
      });
     // console.log('list',this.knowledgeList)
    },
 
    goToKnowledgeDetail(knowledge) { 
      this.$router.push({
        path: "/knowledge_detail_tag/"+this.tag +"/"+this.knowledgeList.indexOf(knowledge),
      });
    },

    async showbyCategory(){
  //  console.log(this.selectedTopic);
    if(this.selectedTopic=="all"){
    //  console.log("all")
    this.$router.push("/knowledge")
    }else{
      this.$router.push("/knowledgeByTag/"+this.selectedTopic);
    }
    
  }
    
  },
};
</script>

<style scoped>
.container {
  margin-left: 10vw !important;
  margin-right: 10vw !important;
}
.outlined-text{

color: #ffffff;
font-size: 30px;

-webkit-text-stroke: 1px #000000;

}
</style>

<template>
  <v-container fluid>
    <div v-if="loading" class="knowledge text-center">loading content...</div>

    <div v-else class="knowledge">
      <v-row>
        <v-col>
          <div >
          <v-card class="mx-auto rounded-card corner-line-card" width="1100">
            <div class="card-title">
            <label class="outlined-text"
                          
            
            
            >{{ knowledgeList[currentIndex].title }}</label>

            <div style="padding-top: 10px;">
          <v-row dense>
            
            <v-col cols="1"> 
              <label style="color: rgb(235, 145, 19); font-style: italic; font-size: smaller;">
                Posted on</label></v-col>
            <v-col  ><label style="color: rgb(61, 67, 57); text-decoration: underline; font-size: smaller;" >{{ knowledgeList[currentIndex].date }}
            </label>
            </v-col>
           
            
          </v-row>
        </div>
          </div>
            <div class="custom-padding">
            

            

           

            <v-card-text v-for="(item, index) in contentArray" :key="index">
              <div v-if="item.type === 'text'" v-html="item.content"></div>
              <div v-else-if=" 
                  item.type === 'image' && docxContent.images.length >= item.id
                ">
              <v-img 
              width="700"
              class="mx-auto" style="border:1px transparent; border-radius: 30px;"
                include
                :src="docxContent.images[item.id - 1]"
                alt="Image"
                :draggable="true"
              ></v-img>
            </div>
            </v-card-text>
            <v-card-actions> 
              <v-btn @click="previous()" class="ma-2" color="#ffd343" rounded 
              :disabled="disablePre" width="100px"> previous</v-btn><v-spacer></v-spacer>
          <v-btn @click="next()" class="ma-2" color="#ffd343" rounded 
          :disabled="disableNext" width="100px"> next</v-btn>  
            </v-card-actions>
          </div>
          </v-card>
        </div>

          
          
         
        </v-col>
      </v-row>
    </div>
  </v-container>
</template>
  <script>
  import utils from "../utils/utils";
  import http from "../utils/http";

  import router from "@/router";
  import axios from "axios";
  import constant from "@/utils/constant";
  
  export default {
    name: "knowledge_detail",
    components: {
      
    },
  
    data() {
      return {
        localDomain: utils.constant.localDomain,
        knowledgeList:[],
        currentIndex:this.$route.params.index,
        tag:this.$route.params.tag,
        contentArray: [],
        docxContent: null,
        loading: true,
        disableNext:false,
      disablePre:false,
      };
    },
  
    async created() {
      // LoginUser from Vuex
  
      //await this.fetchKnowledge();
      await this.fetchKnowledges();
    },
  
    methods: {
      // async fetchKnowledge() {
      //   await http
      //     .get(this.localDomain + "/knowledge/" + this.knowledge_id)
      //     .then((Response) => {
      //       this.knowledge = Response.data;
      //       this.fetchDocx();
      //     });
      // },
      async fetchKnowledges() {
       // console.log(this.tag);
        await axios.get(this.localDomain+'/knowledge/tag',{
          params:{
            tag:this.tag
          }
        }).then((Response) => {
          this.knowledgeList = Response.data.reverse();
          this.fetchDocx();
        });
       // console.log('list',this.knowledgeList);
       await this.buttonControl();
       
      },
   
   
      async fetchDocx() {
        //const currentKnowledege= this.knowledgeList[this.currentIndex];
       // console.log('id',this.currentIndex)
        //console.log('list',this.knowledgeList);

        
        //console.log('file name :',this.knowledgeList[this.currentIndex].contentPath);
          await axios.get(constant.localDomain+"/admin/readDocx",{
            params:{
              fileName: this.knowledgeList[this.currentIndex].contentPath,
            }
          })
          .then((Response) => {
            this.docxContent = Response.data;
            this.splitContent();
          });
      },
      splitContent() {
        const lines = this.docxContent.textContent.split("\n");
        var imageindex = 0;
        this.contentArray = lines.map((line) => {
          if (line.startsWith("IMAGE:")) {
            imageindex++;
          //  console.log("var", imageindex);
            return { type: "image", id: imageindex };
          } else {
            return { type: "text", content: line };
          }
        });
        
        this.loading = false;
        window.scrollTo({
          top: 0,
          behavior: 'auto' // Optional: Adds smooth scrolling animation
        });
      },
  
    
      next() {
    if (this.currentIndex < this.knowledgeList.length - 1) {
      this.currentIndex++;
      //this.loading = true;
      //this.fetchDocx();
      router.push({ path: "/knowledge_detail_tag/" +this.tag+'/' + this.currentIndex });
    }
    else{
   //   console.log("last endex")
    }
  },
  previous() {
    if (this.currentIndex > 0) {
      this.currentIndex--;
      //this.loading = true;
      //this.fetchDocx();
      router.push({ path: "/knowledge_detail_tag/"+this.tag+'/' + this.currentIndex});
    }
    else{
     // console.log("smallest endex")
    }
  },
  async buttonControl(){

console.log("current index ", this.currentIndex);
console.log("knowledge length  ", this.knowledgeList.length);

if(this.currentIndex == 0){
  this.disablePre=true;
}
if(this.currentIndex >= this.knowledgeList.length-1){
  this.disableNext=true;
}


}
    },
  };
  </script>
  
  <style scoped>
.knowledge{

  padding-top: 30px;
}
.custom-padding{
  padding-top: 10px;
  padding-bottom: 20px;
  padding-left: 100px;
  padding-right: 100px;
  
}
.card-title{
  padding-top: 30px;
  padding-left: 115px;
  padding-bottom: 40px;
  
  
}
.rounded-card {
   border-radius: 15px; /* Adjust the value to change the roundness */
 }
 .corner-line-card {
   position: relative;
   border: 3px solid #ffd343;
   
 }
 .outlined-text{

  color: #000000;
	font-size: 40px;
  font-weight: bold;
	

 }

</style>
  
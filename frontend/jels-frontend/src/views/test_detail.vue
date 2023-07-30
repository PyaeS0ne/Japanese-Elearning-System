<template>
  <div class="testDetail">
    <v-card width="1100" class="mx-auto rounded-card corner-line-card">
      <v-card-title class="text-h5" style="padding-left: 30px; padding-top: 30px;">
        <u>{{ test.title }}</u>
      </v-card-title>
      <div
        v-for="(que, index) in questionList"
        :key="que.id"
        class="custom-padding"
      >
        <v-card-text>
          {{ index + 1 }}. {{ que.question }}

          <v-radio-group 
          :disabled="submitted"         
          style="padding-left: 10px" 
          v-model="answers[index]" 
          >
            <v-list v-for="option in que.answerOptions" :key="option.id">

                

                <div >
                  

                  <v-radio :value="option" :label="option.optionText" 
                
                 
                  ></v-radio>
                </div>
              
            </v-list>
          </v-radio-group>
        </v-card-text>
        <v-divider></v-divider>
      </div>
      <v-card-actions class="justify-end">
        <v-btn @click="submitAnswer" class="ma-3" rounded :disabled="!isAllAnswer" color="#ffd343"> submit</v-btn>
      </v-card-actions>
      
      
    </v-card>
    <v-dialog v-model="resultDialog"  width="300">
      <v-card>
         <!-- Dialog Heading -->
         <v-card-title class="justify-center">

          Your score is {{ result.correctAns }}/{{ result.totalAns }}.
         </v-card-title>
        <v-card-text>

          
          <!-- <v-row dense>
           
            <v-col cols="3" class="font-weight-bold">Correct Answer</v-col>
            <v-col cols="7">{{ result.correctAns }}</v-col>
            <v-col cols="3" class="font-weight-bold">Wrong Answer</v-col>
            <v-col cols="7">{{ result.inCorrectAns }}</v-col>
            <v-col cols="3" class="font-weight-bold">Total</v-col>
            <v-col cols="7">{{ result.totalAns }}</v-col>
          </v-row> -->


        </v-card-text>

        <v-card-actions class="justify-end">

<v-btn
    
    
    @click='resultDialog=false ; this.answers=[]'

  
    color="#ffd343"
    >Okay</v-btn
  >

</v-card-actions>
      </v-card>

    


    </v-dialog>
  </div>
</template>

<script>
import constant from "@/utils/constant";
import http from "@/utils/http";
import axios from "axios";

export default {
  name: "test_detail",
  components: {},
  data() {
    return {
      test_id: this.$route.params.id,
      test: {},
      questionList: [],
      answers: [],
      selectedAns:[],
      result: {

      },
      resultDialog:false,
      showAnswer:false,
      submitted:false,
      selected:null,
    };
  },

  computed: {
  isAllAnswer() {
    //console.log("question :",this.questionList.length);
    console.log("answer :",this.answers.length);
    let count=0;
    let b=false;
    
    
    
     this.answers.forEach(()=>{
      count++;
        console.log('count',count);
        if(this.answers.length===count){
            b=true;
        }
       
        
        
    })
    
    return b&&this.answers.length===this.questionList.length;
  },
},

  async created() {
    await this.fetchTestDetail();
    await this.fetchQuestions();
    await this.shuffleQuestions();
  },
  methods: {
    validateAnswers() {
    for (let i = 0; i < this.answers.length; i++) {
      if (!this.answers[i]) {
        this.validationError = "Please answer all questions.";
        return false;
      }
    }
    return true;
  },
    async fetchTestDetail() {
      await axios
        .get(constant.localDomain + "/test/" + this.test_id)
        .then((Response) => {
          this.test = Response.data;
        });
    },

    async fetchQuestions() {
      await axios
        .get(constant.localDomain + "/test/question/" + this.test_id)
        .then((Response) => {
          this.questionList = Response.data;
        });
    },
    async submitAnswer() {
     // console.log("ansers ", this.answers);
      const resp = await http.put("/test/result", this.answers);
      if (resp.status === 200) {
        //console.log("fetch ans success ",resp.text());
        this.result = await resp.json();
       // console.log("result :", this.result);
        this.resultDialog=true;
       
        
      }
    },
    async refresh(){
      await this.fetchTestDetail();
    await this.fetchQuestions();
    await this.shuffleQuestions();
    },
    

    //randomzie que and ans

    shuffleArray(array) {
      // Fisher-Yates (Knuth) shuffle algorithm
      let currentIndex = array.length;
      let randomIndex;

      while (currentIndex !== 0) {
        randomIndex = Math.floor(Math.random() * currentIndex);
        currentIndex--;

        // Swap elements
        [array[currentIndex], array[randomIndex]] = [
          array[randomIndex],
          array[currentIndex],
        ];
      }

      return array;
    },
   
    shuffleQuestions() {
       // console.log("radomizing question ",this.questionList.length);
      this.questionList = this.shuffleArray([...this.questionList]);
      this.questionList.forEach((question)=>{
        question.answerOptions=this.shuffleArray([...question.answerOptions]);
      })
    },
  },
};
</script>
<style scoped>
.custom-padding {
  padding-left: 50px;
  padding-right: 50px;
}
.testDetail {
  padding-top: 30px;
  padding-left: 20px;
  padding-right: 20px;
}
.rounded-card {
   border-radius: 15px; /* Adjust the value to change the roundness */
 }
 .corner-line-card {
   position: relative;
   border: 3px solid #ffd343;
   
 }
</style>

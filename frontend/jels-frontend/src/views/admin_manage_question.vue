<template>
  <div class="question">
    

    <v-row>
      <v-col>
        <v-data-table
        style="width: auto;"
          :headers="headers"
          :items="questionList"
          
          sort-by="question"
          class="elevation-3"
        >

        <template v-slot:top>
      <v-toolbar
      style="background-color: #ffd343;" flat rounded
      >
        <v-toolbar-title class="text-center font-weight-bold">Question List</v-toolbar-title>
       
        <v-spacer></v-spacer>
        <v-btn @click="onClickAdd" rounded small dark>Add question</v-btn>
      </v-toolbar>
    </template>

    <template v-slot:item.number="{item,index}">
          {{index+1}}
    </template>

          <template v-slot:item.Options="{ item }">
            <v-list 
              v-for="(option, index) in item.answerOptions"
              :key="index"
              style="padding-left: 5px; background-color: transparent; " 
            >
              <div v-if="option.isCorrect === true">
                {{ index + 1 }}. {{ option.optionText }} ( Correct )
              </div>
              <div v-else>{{ index + 1 }}. {{ option.optionText }}</div>
            </v-list>
          </template>

          <template v-slot:item.actions="{ item }">
            <v-icon 
            color=""
             class="mr-2"
              @click="onClickUpdateItem(item)">
              mdi-pencil
            </v-icon>
            <v-icon
              
              
              @click="
                toDeleteItem = item;
                dialogDelete = true;
              "
            >
              mdi-delete
            </v-icon>
          </template>
        </v-data-table>
      </v-col>
    </v-row>

    <v-row>
      <v-col>
        
      </v-col>
    </v-row>
    <!--------Question Create Dialog-->
    <v-dialog v-model="dialogCreate" width="600" scrollable>
      <v-card>
        <v-toolbar color="#ffd343">Add new question</v-toolbar>
        

        <v-card-text>
          <v-form ref="createForm" v-model="queform" fast-fail @submit.prevent>
            <v-row>
              <v-col cols="12">
                <v-textarea
                  v-model="toCreateItem.question"
                  :counter="300"
                  :rules="[
                    (v) => !!v || 'Required',
                    (v) =>
                      (v && v.length <= 300) ||
                      'Question must be less than 300 characters',
                  ]"
                  label="Question"
                  auto-grow
                  outlined
                  rows="2"
                  row-height="25"
                ></v-textarea>
              </v-col>

              <v-col>
                <v-row v-for="(option, index) in toCreateItem.answerOption" :key="index">
                  <v-col cols="10">
                    <v-text-field
                      v-model="option.optionText"
                      :rules="[
                        (v) => !!v || 'Required',
                        (v) =>
                          (v && v.length <= 100) ||
                          'Question must be less than 300 characters',
                      ]"
                      :label="`Option  ${index + 1}`"
                      required
                    ></v-text-field>
                  </v-col>
                  <v-col cols="2">
                    <v-checkbox
                      v-model="option.isCorrect"
                      label="correct"
                    ></v-checkbox>
                  </v-col>
                </v-row>
              </v-col>
            </v-row>
          </v-form>
        </v-card-text>

        <!--  Btn -->
        <v-card-actions class="justify-end">
          <v-btn
            class="ma-2"
            @click="dialogCreate = false;"
            >Cancel</v-btn
          >

          <v-btn :disabled="!queform" color="success" @click="addQuestion()"> <span v-if="!loading">Confirm</span>
                  <v-progress-circular
                    v-else
                    indeterminate
                    color="#ffd343"
                  ></v-progress-circular></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!--Delete Dialog-->

    <v-dialog v-model="dialogDelete" width="400"
  >
      <v-card>
        <!-- Dialog Heading -->
       <v-card-title> Delete question ?</v-card-title>

        
        <v-card-text class="pa-6">
         Are you sure want to delete this question ?
        </v-card-text>

        <!-- Delete question Btn -->
        <v-card-actions class="justify-end">
          <v-btn  class="ma-2"  @click="toDeleteItem={}; dialogDelete = false">Cancel</v-btn>
          
          <v-btn color="red"  @click="deleteQuestion()"> <span v-if="!loading">Confirm</span>
                  <v-progress-circular
                    v-else
                    indeterminate
                    color="#ffd343"
                  ></v-progress-circular></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!--Update Dialog-->
    <v-dialog v-model="dialogUpdate" width="600" scrollable>

        <v-card>
          <v-toolbar color="#ffd343">Update question?</v-toolbar>
        <v-divider></v-divider>

        <v-card-text>
          <v-form ref="updateForm">
            <v-row>
              <v-col cols="12">
                <v-textarea
                  v-model="toUpdateItem.question"
                  :counter="300"
                  :rules="[
                    (v) => !!v || 'Required',
                    (v) =>
                      (v && v.length <= 300) ||
                      'Question must be less than 300 characters',
                  ]"
                  label="Question"
                  auto-grow
                  outlined
                  rows="3"
                  row-height="25"
                ></v-textarea>
              </v-col>

              <v-col>
                <v-row v-for="(option, index) in toUpdateItem.answerOption" :key="index">
                  <v-col cols="10">
                    <v-text-field
                      v-model="option.optionText"
                      :rules="[
                        (v) => !!v || 'Required',
                        (v) =>
                          (v && v.length <= 100) ||
                          'Question must be less than 300 characters',
                      ]"
                      :label="`Option  ${index + 1}`"
                      required
                    ></v-text-field>
                  </v-col>
                  <v-col cols="2">
                    <v-checkbox
                      v-model="option.isCorrect"
                      label="correct"
                    ></v-checkbox>
                  </v-col>
                </v-row>
              </v-col>
            </v-row>
          </v-form>
        </v-card-text>

        <!--  Btn -->
        <v-card-actions class="justify-end">
          <v-btn
            class="ma-2"
            @click="
              
              dialogUpdate = false;
            "
            >Cancel</v-btn
          >

          <v-btn color="success" @click="updateQuestion"> <span v-if="!loading">Update</span>
                  <v-progress-circular
                    v-else
                    indeterminate
                    color="#ffd343"
                  ></v-progress-circular></v-btn>
        </v-card-actions>
      </v-card>




    </v-dialog>
  </div>
</template>
<script>
import constant from "@/utils/constant";
import axios from "axios";

export default {
  data() {
    return {
        intialData:{},
      test_id: this.$route.params.test_id,
      toCreateItem:{
        
        question:'',
        answerOption: [
        { optionText: "", isCorrect: false },
        { optionText: "", isCorrect: false },
        { optionText: "", isCorrect: false },
        { optionText: "", isCorrect: false },
      ],
      },
      loading:false,
      
      questionList: [],
      headers: [
      {
          text: 'No.',
          align: 'start',
          sortable: false,
          value: 'number',
        },
        {
          text: "Question",
          
          value: "question",
          sortable: false,
        },
        { text: "Options", value: "Options", sortable: false },
        { text: "Actions ", value: "actions", sortable: false },
      ],

      dialogCreate: false,
      dialogDelete:false,
      dialogUpdate:false,
      queform:false,
      
      toDeleteItem:{},
      toUpdateItem:{
        
        id:null,
        question:'',
        answerOption: [
        { optionText: "", isCorrect: false },
        { optionText: "", isCorrect: false },
        { optionText: "", isCorrect: false },
        { optionText: "", isCorrect: false },
      ],
      }

    };
  },

  async created() {
    await this.fetchQuestions();
  },
  methods: {
    async fetchQuestions() {
      //console.log("test id : ", this.test_id);
      await axios
        .get(constant.localDomain + "/test/question/" + this.test_id)
        .then((Response) => {
          this.questionList = Response.data;
        });
    },
    async addQuestion() {
      this.loading=true;
      //console.log("question : ", this.toCreateItem.question);
      //console.log("ans options : ", this.toCreateItem.answerOption);
      const resp = await axios.post(
        constant.localDomain + "/question/create/",
        {
          question: this.toCreateItem.question,
          answerOptions: this.toCreateItem.answerOption,
          test: { id: this.test_id },
        }
      );
      if ((await resp).status === 200) {
       // console.log("created ", resp.data);
        this.loading=false;
        this.$refs.createForm.reset();
        this.dialogCreate=false;
        
        await this.fetchQuestions();


        
      }
    },
    async deleteQuestion(){
      this.loading=true;
    const resp = await axios.delete(constant.localDomain+'/question/delete/'+this.toDeleteItem.id);
    if((await resp).status===200){
       // console.log("successfully deleted : ",resp.data);
        this.toDeleteItem={};
        this.loading=false;
        this.dialogDelete=false;
        this.queform=false;
        await this.fetchQuestions();
    }

    },
    async onClickAdd(){
      //this.intialData=JSON.parse(JSON.stringify(this.toCreateItem));
       
      this.dialogCreate=true;
      
     
      
  
    },

    async onClickUpdateItem(item){
     // console.log(item.answerOptions);

        this.toUpdateItem.id=item.id;
        this.toUpdateItem.question=item.question;
        this.toUpdateItem.answerOption=item.answerOptions;
        this.dialogUpdate=true;

    },

    async updateQuestion(){
      this.loading=true;

       // console.log("question id : ",this.toUpdateItem.id);
        //console.log("question : ", this.toUpdateItem.question);
      //console.log("ans options : ", this.toUpdateItem.answerOption);
      const resp = await axios.put(
        constant.localDomain + "/question/update/"+this.toUpdateItem.id,
        {
          question: this.toUpdateItem.question,
          answerOptions: this.toUpdateItem.answerOption,
          test: { id: this.test_id },
        }
      );
      if ((await resp).status === 200) {
       // console.log("Updated ", resp.data);
        this.loading=false;
        this.dialogUpdate=false;
        await this.fetchQuestions();
      }
        
    }
  },
};
</script>
<style scoped>
.question {
  padding-top: 30px;
  padding-left: 20px;
  padding-right: 20px;
}
.question-card {
  padding-left: 20px;
}
</style>

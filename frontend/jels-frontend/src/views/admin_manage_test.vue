<template>
  <div >
    <v-row>
       <!-- Sidebar -->
      <v-col cols="2">
        <admin_sidebar></admin_sidebar>
      </v-col>
      <v-col cols="10" class="mt-4" justify="center">
        <v-data-table style="width: 1010px;"
          :headers="headers"
          :items="testList"
          :item-key="title"
          sort-by="title"
          class="elevation-3"

        >
          <template v-slot:top>
            <v-toolbar style="background-color: #ffd343;" flat rounded>
              <v-toolbar-title class="text-center font-weight-bold">Test List</v-toolbar-title>
              <v-spacer></v-spacer>
              <v-btn @click="onClickAdd" rounded dark small>Add Test</v-btn>
            </v-toolbar>
          </template>

          <template v-slot:item.number="{item,index}">
          {{index+1}}
         </template>

          <template v-slot:item.questions="{ item }">
            <router-link :to="'/admin/manage_question/' + item.id">
              Manage Questions
            </router-link>
          </template>


          <template v-slot:item.actions="{ item }">
            <v-icon color="" class="mr-2" @click="onClickUpdateItem(item)">
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
        

        <!------Update dialog ---->
        <v-dialog width="600" v-model="dialogUpdate">
          <v-card>
            <!-- Dialog Heading -->
        <v-toolbar color="#ffd343" >
          <div>Update Test ?</div>
          <v-spacer></v-spacer>
          <v-btn icon @click="dialogUpdate = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>

            <!-- Update Form -->
        <v-card-text class="pa-4">
              <v-form  fast-fail @submit.prevent v-model="updateForm">
                <v-text-field
                  v-model="toUpdateItem.title"
                  outlined
                  dense
                  label="Test Title"
                  :rules="titleRules"
                >
                </v-text-field>
                <v-select
                  v-model="toUpdateItem.level"
                  dense
                  outlined
                  item-text="name"
                  item-value="id"
                  :items="levelList"
                  :rules="levelRules"
                  label="Select Level"
                  required
                ></v-select>

               
                
              </v-form>
            </v-card-text>
            <v-card-actions>

              <v-btn
                  
                  class="mr-2"
                  @click="updateTest"
                  :disabled="!updateForm"
                  color="#ffd343"
                  ><span v-if="!loading">Update</span>
              <v-progress-circular
                v-else
                indeterminate
                color="primary"
              ></v-progress-circular></v-btn
                >

            </v-card-actions>
          </v-card>
        </v-dialog>

        <!--Delete Dialog-->

    <v-dialog v-model="dialogDelete" width="400"
  >
      <v-card>
        <!-- Dialog Heading -->
       <v-card-title> Delete Test </v-card-title>

        
        <v-card-text class="pa-6">
         Are you sure you want to delete this Test ?
        </v-card-text>

        <!-- Delete question Btn -->
        <v-card-actions class="justify-end">
          <v-btn  class="ma-2"  @click="toDeleteItem={}; dialogDelete = false">Cancel</v-btn>
          
          <v-btn color="red"  @click="deleteTest()">
            <span v-if="!loading">Confirm</span>
              <v-progress-circular
                v-else
                indeterminate
                color="primary"
              ></v-progress-circular>
          
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

        <v-alert type="success" v-model="successAlert" dismissible>
                  Test Successfully Updated !
                </v-alert>
                <v-alert
                  class="mt-3"
                  v-show="errorAlert"
                  dense
                  type="error"
                  dismissible
                >
                  Update Test Failed !
                </v-alert>
      </v-col>
    </v-row>
  </div>
</template>

<script>
import admin_sidebar from "@/components/admin_sidebar.vue";
import router from "@/router";
import constant from "@/utils/constant";
import axios from "axios";

export default {
  name: "admin_manage_test",
  components: { admin_sidebar },
  data() {
    return {
      testList: [],

      headers: [
      {
          text: 'No.',
          align: 'start',
          sortable: false,
          value: 'number',
        },
        {
          text: "Test Title",
          
          sortable: true,
          value: "title",
        },

        {
          text: "Level",
          value: "level.name",
          sortable: true,
        },
        {
          text: "Questions",
          value: "questions",
          sortable: false,
        },
        {
          text: "Actions",
          value: "actions",
          sortable: false,
        },
      ],
      titleRules:[ value => {
                  if (value?.length > 3)
                      return true;
                  return "Title  must be at least 3 characters.";
              }],
      levelList: [],
      levelRules: [(v) => !!v || 'Please Choose Level'],
      toDeleteItem: {},
      toUpdateItem: {
        id: null,
        title: null,
        level: null,
      },
      updateForm:false,
      dialogUpdate: false,
      dialogDelete: false,
      successAlert: false,
      errorAlert: false,
      loading:false,
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
     // console.log("fetching levels");
      await axios.get(constant.localDomain + "/level").then((Response) => {
        this.levelList = Response.data;
       // console.log(this.levelList);
      });
    },

    async onClickUpdateItem(item) {
      this.toUpdateItem.id = item.id;
      this.toUpdateItem.title = item.title;
      this.toUpdateItem.level = item.level.id;
      this.dialogUpdate = true;
    },

    async updateTest(){
      this.loading=true;
        const resp = await axios.put(constant.localDomain+'/test/update/'+this.toUpdateItem.id,{
            title:this.toUpdateItem.title,
            level:{id:this.toUpdateItem.level}

        })
        if(resp.status===200){
          this.loading=false;
            this.dialogUpdate=false;
            this.successAlert=true;
            await this.fetchTestList();
        }

    },
    async deleteTest(){
      this.loading=true;
        const resp = await axios.delete(constant.localDomain+'/test/delete/'+this.toDeleteItem.id);
        if(resp.status===200){
           // console.log("successfully deleted");
            this.toDeleteItem={},
            this.loading=false;
            this.dialogDelete=false;
            await this.fetchTestList();

        }
    },
    async onClickAdd(){
      this.$router.push('/admin/test/create')
    }
  },
};
</script>
<style scoped>
.testpage {
  padding-top: 30px;
  padding-left: 20px;
  padding-right: 20px;
}
</style>

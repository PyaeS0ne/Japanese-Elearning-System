<template>
  <div>
    <v-row>
      <!-- Sidebar -->
      <v-col cols="2">

      <admin_sidebar ></admin_sidebar>
    </v-col>

    <v-col cols="10" class="mt-4" justify="center">
  
  <v-data-table style="width: 1010px;"
    :headers="headers"
    :items="knowledgeList"
    :items-per-page="5"
    :item-key="title"
    sort-by="title"
    class="elevation-3" >
  
    <template v-slot:top>
      <v-toolbar style="background-color: #ffd343;" flat rounded>
        <v-toolbar-title class="text-center font-weight-bold">Knowledge Posts</v-toolbar-title>
        <v-spacer></v-spacer>
              <v-btn @click="onClickAdd" rounded small dark>Add Post</v-btn>
      </v-toolbar>

    </template>

    <template v-slot:item.number="{item,index}">
          {{index+1}}
    </template>

    <template v-slot:item.featureImage="{item}">
          <v-img 
          :src='localDomain + "/"+item.featureImage ' 
          width="100"
          height="100"
          @click=""
          contain
          
          ></v-img>
    </template>
    <template v-slot:item.actions="{ item }">
      <v-icon
        
        class="mr-2"
        @click="onClickUpdateItem(item)"
      >
        mdi-pencil
      </v-icon>
      <v-icon
        
        @click="toDeleteItem=item;
                dialogDelete=true;
        "
      >
        mdi-delete
      </v-icon>
    </template>

  </v-data-table>
  <!-- Delete Dialog -->
  <v-dialog v-model="dialogDelete" width="400"
  scrollable
  >
      <v-card>
        <!-- Dialog Heading -->
        <v-toolbar color="#ffd343" >
          <div>Delete this Knowledge Post ?</div>
          <v-spacer></v-spacer>
          <v-btn icon @click="toDeleteItem={};
                              dialogDelete = false">
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>

        <!-- Delete Knowledge Info -->
        <v-card-text class="pa-4">
          <v-row dense>
            <v-col cols="3" class="font-weight-bold">Ttile</v-col>
            <v-col cols="7">{{ toDeleteItem.title }}</v-col>
            <v-col cols="3" class="font-weight-bold">Topic</v-col>
            <v-col cols="7">{{ toDeleteItem.tag }}</v-col>
            <v-col cols="3" class="font-weight-bold">Introduction</v-col>
            <v-col cols="7">{{ toDeleteItem.intro }}</v-col>
          </v-row>
        </v-card-text>

        <!-- Delete knowledge Btn -->
        <v-card-actions class="justify-end">
          <v-btn  class="ma-2"  @click="toDeleteItem={}; dialogDelete = false" >Cancel</v-btn>
          
          <v-btn color="#ffd343"  @click="deleteKnowledge()"> <span v-if="!loading">Confirm</span>
                  <v-progress-circular
                    v-else
                    indeterminate
                    color="#ffd343"
                  ></v-progress-circular></v-btn>
        </v-card-actions>
      </v-card>
    </v-dialog>

    <!-- Update Dialog -->
    <v-dialog v-model="dialogUpdate" width="600" persistent
    scrollable >
      <v-card>
        <!-- Dialog Heading -->
        <v-toolbar color="#ffd343" >
          <div>Update This Knowledge?</div>
          <v-spacer></v-spacer>
          <v-btn icon @click="dialogUpdate = false ; imagePreviewPath=null ; toUpdateItem.featureImage=null" >
            <v-icon>mdi-close</v-icon>
          </v-btn>
        </v-toolbar>

        <!-- Update Form -->
        <v-card-text class="pa-4">
          <v-form ref="knowledgeForm" v-model="knowledgeForm">
            <!-- Update Knowledge Title -->
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

            <!-- Update knowledge topic -->
            <v-select 
      v-model="toUpdateItem.topic"
      multiple
       dense
      outlined
      required
      :items="['anime', 'food', 'culture', 'language', 'history']"
      :rules="[(v) => v.length>0 || 'Please select at least one item']"
      label="select topic"
      
    ></v-select>

            <!-- Update knowledge introduction -->
            <v-textarea
              v-model="toUpdateItem.introduction"

            
              :counter="200"
              :rules="[
                (v) => !!v || 'Required',
                (v) =>
                  (v && v.length <= 500) ||
                  'Title must be less than 200 characters',
              ]"
              label="Introduction"
              required
            ></v-textarea>

            <v-file-input 
      show-size
      outlined
      dense
      prepend-icon=''
      append-icon="mdi-image"
        v-model="toUpdateItem.featureImage"
      accept=".jpg, .png"
      label="Feature Image"
      @change="onChangeImage"
      
    ></v-file-input>
    <v-img v-if="imagePreviewPath != null"
    :src="imagePreviewPath"
    width="200"
    height="150"
    contain
    ></v-img>


            <!-- Update knowledge content file -->
            <v-file-input
              v-model="toUpdateItem.file"
              label="Content File"
              show-size
              prepend-icon="mdi-file-word"
              placeholder="Choose content file"
              accept=".docx"
              :rules="[
                (v) =>
                  !v ||
                  v.size < 20000000 ||
                  'file size should be less than 20 MB!',
              ]"
              
            ></v-file-input>

           

            
          </v-form>
        </v-card-text>
        <v-card-actions>
           <!-- Update Btn -->
           <v-btn
              :disabled="!knowledgeForm"
              color="#ffd343"
              class="mr-4"
              @click="updateKnowledge()"
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
    <!-- Success Alert -->
    <v-expand-transition>
    <v-alert class="mt-3" v-show="successAlert" dense type="success"
              dismissible
              >
              Update knowledge success!
            </v-alert>
          </v-expand-transition>
            <!-- Error Alert -->
            <v-alert class="mt-3" v-show="errorAlert" dense type="error" 
            dismissible>
              Update knowledge Failed!
            </v-alert>
    
    
          </v-col>
 </v-row>
  </div>

</template>
     
     <script>
     import admin_sidebar from '../components/admin_sidebar.vue'
     import constant from '@/utils/constant';
import http from '@/utils/http';
     import axios from 'axios';
     
     export default {
        name:'admin_knowledge_list',
        components:{admin_sidebar},
       data() {
         return {
          localDomain:constant.localDomain,
          loginUser:{},
            
      headers: [
        {
          text: 'No.',
          align: 'start',
          sortable: false,
          value: 'number',
        },
        { text: 'Feature Image', value: 'featureImage',sortable: false  },

        { text: 'Title', value: 'title',sortable: false  },
        { text: 'Topic', value: 'tag',sortable: false  },
        
       // { text: 'Content File ', value: 'contentPath',sortable: false  },
        { text: 'Introduction', value: 'intro' ,sortable: false },
        
        { text: 'Actions', value: 'actions', sortable: false },
      ],
      knowledgeList:[],
      knowledgeForm: false,
      toDeleteItem:{},
      dialogDelete: false,

      dialogUpdate: false,
      toUpdateItem: {
        id:null,
        title: '',
        introduction: '',
        featureImage:null,
        topic:[],
        file:null,
      
      },
      featureImagePath:null,
      imagePreviewPath:null,
      contentPath:null,
      loading:false,
      errorAlert:false,
      successAlert:false,
      
      
      
         };
       },

    created () {
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
      this.fetchKnowledges()
  
    },

    methods: {
      async fetchKnowledges() {
      axios.get(constant.get_knowledge_url).then((Response) => {
        this.knowledgeList = Response.data;
      });
      //console.log('list',this.knowledgeList);
    },
    async deleteKnowledge(){
      this.loading=true;
      
        await axios.delete(constant.localDomain+'/admin/knowledge/delete/'+this.toDeleteItem.id)
        .then((Response)=>{
          if(Response.status==200){
            this.loading=false;
            this.dialogDelete=false;
            
            
          }else{
            this.errorAlert=true;
          }
        })
        await this.fetchKnowledges();
    },
    async onClickUpdateItem(item){
     
      this.dialogUpdate=true;
      //this.toUpdateItem = Object.assign({}, item);
      this.toUpdateItem.id=item.id;
      this.toUpdateItem.title=item.title;
      this.toUpdateItem.introduction= item.intro;
      this.toUpdateItem.topic=item.tag.split(',');    
      this.contentPath=item.contentPath;  
      this.featureImagePath=item.featureImage;  
    },
    async updateKnowledge(){
      this.loading=true;
     

      if (this.toUpdateItem.file!=null){
        
          //console.log('new file ',this.contentPath);  
      const resp=await http.putMedia('/admin/file/update',this.toUpdateItem.file,this.contentPath);
      
            if (resp.status === 200) {
                this.contentPath = await resp.text();
                
            }
            else {
               // console.log("error : file update failed");
            }
          
        
        
      }
      if (this.toUpdateItem.featureImage!=null){
        
      //  console.log('old image ',this.featureImagePath);  
    const respImg=await http.putMedia('/admin/file/update',this.toUpdateItem.featureImage,this.featureImagePath);
    
          if (respImg.status === 200) {
              this.featureImagePath = await respImg.text();
              
          }
          else {
              //console.log("error : file update failed");
          }
        
      //console.log('new image', this.featureImagePath);
      
    }
    //console.log('intro ',this.toUpdateItem.introduction);
        
      const respKnowledge=   await axios.put(constant.localDomain + "/admin/knowledge/update/"+
                  this.toUpdateItem.id, 
                {
                   title: this.toUpdateItem.title,
                  tag: this.toUpdateItem.topic.join(","),
                  contentPath:this.contentPath, 
                  intro: this.toUpdateItem.introduction,
                  featureImage:this.featureImagePath
                })
            if(respKnowledge && respKnowledge.status==200){
              this.contentPath=null;
              this.dialogUpdate=false;
              this.toUpdateItem.file=null;
              this.imagePreviewPath=null;
              this.toUpdateItem.featureImage=null;
              
              await this.fetchKnowledges();
              
              setTimeout(() => {
                this.successAlert=true;
              }, 500);
              setTimeout(() => {
                this.successAlert=false;
              }, 10000);
            }else{
              this.errorAlert=true;
            }
       this.loading=false;  
       document.location.reload(); 
    
     },
     async onChangeImage(){
            this.imagePreviewPath=null;
            this.imagePreviewPath = URL.createObjectURL(this.toUpdateItem.featureImage);

          },
          async onClickAdd(){
      this.$router.push("/admin/knowledge/create")
    }
    }
     
     };
     </script>
     <style scoped>
     .colorful-table{
       background-color: #f69e07;
     }
     
     /* Apply a predefined color class to the table rows on hover */
     .colorful-table tbody tr:hover {
       background-color:aqua;
     }
     
   
     
import Vue from "vue";
import VueRouter from "vue-router";
import store from "@/store";

import home from "../views/home.vue";
import login from "../views/login.vue";
import register from "../views/register.vue";
import adminProfile from "../views/adminProfile.vue";
import profile from "../views/profile.vue";
import changePwd from "../views/changePwd.vue";
import knowledge from "../views/knowledge.vue";
import testList from "../views/testList.vue"
import testDetail from "../views/test_detail.vue"

import admin_manage_test from "../views/admin_manage_test.vue"
import admin_manage_question from "../views/admin_manage_question.vue"
import knowledgeByTag from "../views/knowledgeByTag";
import knowledge_detail from "../views/knowledge_detail";
import knowledge_detail_tag from "../views/knowledge_detail_tag";
import admin_create_test from "../views/admin_create_test.vue"

//import admin from "../views/admin.vue";
import payment from "../views/payment.vue"
import admin_create_knowledge from "../views/admin_create_knowledge.vue"
import pay_with_saved_card from "../views/pay_with_saved_card.vue"


import admin_list from "../views/admin_list.vue"
import user_list from "../views/user_list";
import forget_password from "../views/forget_password";
import changeName from "../views/changeName.vue";
import admin from "../views/admin.vue";
import admin_knowledge_list from "../views/admin_knowledge_list.vue";
import admin_create_course from "../views/admin_create_course.vue";
import admin_manage_courseVideo from "../views/admin_manage_courseVideo.vue"

import course from "../views/course.vue";
import course_detail from "../views/course_detail.vue";
import course_video from "../views/course_video.vue";
import course_video_detail from "../views/course_video_detail.vue";
import mycourse from "../views/my_course.vue";
import contact from "../views/contact.vue"

import { Store } from "vuex";


Vue.use(VueRouter);

const routes = [
  // ------- This is Home Page
  {
    path: "/",
    name: "home",
    component: home,
  },

  {
    path: "/contact",
    name: "contact",
    component: contact,
    meta: {
      requiresUser: true,
    },
  },
  // ------User & Admin Login
  {
    path: "/login",
    name: "login",
    component: login,
  },
// --------User Register
{
  path: "/register",
  name: "register",
  component: register,
},

// --------User & Admin Profile
{
  path: "/profile",
  name: "profile",
  component: profile,
  meta: {
    requiresAuth: true,
    requiresUser:true,
  },
},
{
  path: "/admin/profile",
  name: "admin/profile",
  component: adminProfile,
  meta: {
    requiresAuth: true,
    requiresAdmin:true,
  },
},
// -----------User & Admin Changing Pasword
{
  path: "/changePwd",
  name: "changePwd",
  component: changePwd,
  meta: {
    requiresAuth: true,
  },
},
{
  path: "/changeName",
  name: "changeName",
  component: changeName,
  meta: {
    requiresAuth: true,
  },
},
// --------User & Admin Password Requesting
{
  path: "/forgetPassword",
  name: "forgetPassword",
  component: forget_password,
  meta: {
    requiresAuth: false,
  },
},

// ----------- Admin

{
  path: "/admin/admin_list",
  name: "adminList",
  component:admin_list,
  meta: {
    requiresAuth: true,
    requiresAdmin:true,
  },
},

{
  path: "/admin/user_list",
  name: "user_list",
  component:user_list,
  meta: {
    requiresAuth: true,
    requiresAdmin:true,
  },
},
{
  path: "/admin",
  name:"admin dashboard",
  component :admin,
  meta: {
    requiresAuth: true,
    requiresAdmin:true,
  },
},
{
  path: "/admin/manage_courseVideo/:id",
  name:"admin_mangae_courseVideo",
  component :admin_manage_courseVideo,
  meta: {
    requiresAuth: true,
    requiresAdmin:true,
  },
},
{
  path: "/admin/course/create",
  name:"admin_create_course",
  component :admin_create_course,
  meta: {
    requiresAuth: true,
    requiresAdmin:true,
  },
},

{
  path: "/admin/knowledge_list",
  name:"admin_knowledge_list",
  component :admin_knowledge_list,
  meta: {
    requiresAuth: true,
    requiresAdmin:true,
  },
},

{
  path: "/admin/knowledge/create",
  name: "admin_create_knowledge",
  component: admin_create_knowledge,
  meta: {
    requiresAuth: true,
    requiresAdmin:true,
  },
 
},
{
  path: "/admin/test/create",
  name: "admin_create_test",
  component:admin_create_test,
  meta: {
    requiresAuth: true,
    requiresAdmin:true,
  },
},
{
  path: "/admin/manage_question/:test_id",
  name: "admin_manage_question",
  component:admin_manage_question,
  meta: {
    requiresAuth: true,
    requiresAdmin:true,
    
  },
},
{
  path: "/admin/manage_test",
  name: "admin_manage_test",
  component:admin_manage_test,
  meta: {
    requiresAuth: true,
    requiresAdmin:true,
    
  },
},


//-------------------

{
  path: "/payment/course/:id",
  name: "payment",
  component: payment,
  meta: {
    requiresAuth: true,
    requiresUser:true,
    
    
  },
},

{
  path: "/charge/course/:id",
  name: "charge",
  component: pay_with_saved_card,
  meta: {
    requiresAuth: true,
    requiresUser:true,
    
  },
},

{
  path: "/course",
  name: "course",
  component: course,
  meta: {
    requiresAuth: false,
    requiresUser:false,
    
  },
  

  
},
{
  path: "/mycourse",
  name: "mycourse",
  component: mycourse,
  meta: {
    requiresAuth: true,
    requiresUser:true,
    
  },
},
{
  path: "/course_detail/:id",
  name: "course_detail",
  component: course_detail,
  meta: {
    requiresAuth: true,
    requiresUser:false,
    
  },
},
{
  path: "/course/:id/course_videos",
  name: "course_video",
  component: course_video,
  meta: {
    requiresAuth: true,
  },
},
{
  path: "/course/:course_id/course_video_detail/:id",
  name: "course_video_detail",
  component: course_video_detail,
  meta: {
    requiresAuth: true,
    
    
  },
},

{
  path: "/knowledge",
  name: "knowledge",
  component:knowledge,
  meta: {
    requiresAuth: false,
    requiresUser:false,
    
  },
},


{
  path: "/knowledgeBytag/:tag",
  name: "knowledgeByTag",

  component: knowledgeByTag,
  meta: {
    requiresAuth: false,
    requiresUser:false,
    
  },
 
},


{
  path: "/knowledge_detail/:index",
  name: "knowledge_detail",
  component: knowledge_detail,
  meta: {
    requiresAuth: false,
    requiresUser:false,
    
  },
 
},

  {
    path: "/knowledge_detail_tag/:tag/:index",
    name: "knowledge_detail_tag",
    component: knowledge_detail_tag,
    meta: {
      requiresAuth: false,
      requiresUser:false,
      
    },
   
  },

  {
    path: "/test",
    name: "testlist",
    component:testList,
    meta: {
      requiresAuth: false,
      requiresUser:false,
      
    },
  },
  {
    path: "/test_detail/:id",
    name: "test_detail",
    component:testDetail,
    meta: {
      requiresAuth: false,
      requiresUser:false,
      
    },
  },

  

  
 
  
   
];

const router = new VueRouter({
  mode: "history",
  base: process.env.BASE_URL,
  routes ,
});

router.beforeEach((to, from , next) => {
  let loginUser =store.getters.loginUser;
  let isLogin = store.getters.isLogin;

  // from - Home
  // to - Profile
  // Need to be login, But is not login
 
  if (to.meta.requiresUser == true  && isLogin&& loginUser.role!="user") {
    //console.log("in here");
    //console.log("role :",loginUser.role);
    next({ path: "/" });
  }


  if (to.meta.requiresAuth == true && !isLogin) {
    //console.log('role ', loginUser.role);
    next({ path: "/login" });
  }

  // Need to be login, Need to be admin
  else if (
    to.meta.requiresAuth == true &&
    to.meta.requiresAdmin == true &&
    loginUser.role != "admin"
  ) {
   // console.log("error");
    //console.log(" role ", loginUser.role);
    next({ path: "/" });
  }
  

  // If All Okay
  else {

   // console.log("all ok");
    //console.log("name :", loginUser.name);
    next();
  }
});

export default router;

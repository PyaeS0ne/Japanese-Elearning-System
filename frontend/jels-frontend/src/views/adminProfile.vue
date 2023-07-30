<template>
    <div class="scrollable-container main">
        <v-container id="user-profile" fluid tag="section">
            <heading></heading>
            <v-row><v-col cols="12" class="mt-12"></v-col></v-row>

            <v-row justify="center">
                <v-col cols="12">
                    <div class="text-center text-h3">
                        <h1>Serve as a Admin</h1>
                    </div>
                </v-col>
            </v-row>

            <v-row><v-col cols="20"></v-col> </v-row>
            <v-row justify="center">
                <v-col cols="18">

                    <v-row justify="center">

                        <v-col cols="10">
                            <div class="text-center">
                                <v-card height="800" class="rounded-card corner-line-card">
                                    <v-card-text>
                                        <v-row><v-col cols="12" class="mt-8"></v-col> </v-row>

                                        <br /><br /><v-img src="../assets/adminProfile.jpg" class="rounded-profile mx-auto"
                                            contain width="300"></v-img>
                                        <v-row><v-col cols="12" class="mt-8"></v-col> </v-row>
                                        <div class="text-h2 black--text">
                                            Admin Info</div>
                                        <div class="text-h3 black--text"><svg-icon type="mdi" :path="path"></svg-icon>{{
                                            loginUser.name }}<svg-icon type="mdi" :path="path"></svg-icon></div>
                                        <br /><br />
                                        <div class="text-h3 black--text"><svg-icon type="mdi" :path="path1"></svg-icon> {{
                                            loginUser.email }}
                                        </div>
                                        <br /><br /><br /><br />

                                        <!-- Change Password Btn -->
                                        <v-btn class="mr-4" color="#ffd343" @click="changePwd()"><svg-icon type="mdi"
                                                :path="path2"></svg-icon> change password </v-btn>

                                        <!-- Change Name Btn -->
                                        <v-btn class="mr-4" color="#ffd343" @click="changeName()"><svg-icon type="mdi"
                                                :path="path2"></svg-icon> change name </v-btn>

                                    </v-card-text>
                                </v-card>
                            </div>
                        </v-col>
                    </v-row>
                </v-col>
            </v-row>
        </v-container>
    </div>
</template>
  
<script>
import heading from "../components/heading.vue";
import SvgIcon from '@jamescoyle/vue-icon';
import { mdiHeartMultipleOutline } from '@mdi/js';
import { mdiCardAccountMailOutline } from '@mdi/js';
import { mdiPencil } from '@mdi/js';
export default {
    name: "adminProfile",
    components: { SvgIcon, heading },

    data()
    {
        return {
            Bio: "",
            loginUser: {},
            path: mdiHeartMultipleOutline,
            path1: mdiCardAccountMailOutline,
            path2: mdiPencil,
        };
    },

    created()
    {
        this.loginUser = this.$store.state.loginUser;
        this.$store.watch(
            () =>
            {
                return this.$store.state.loginUser;
            },
            ( newVal ) =>
            {
                this.loginUser = newVal;
            },
            {
                deep: true,
            }
        );
    },

    methods: {
        async changePwd()
        {
            this.$router.push( { path: "/changePwd" } );
        },

        async changeName()
        {
            this.$router.push( { path: "/changeName" } );
        }
    }
};
</script>
<style scoped lang="scss">
.rounded-card {
    border-radius: 17px;
}

.rounded-profile {
    border-radius: 500px;
}

.corner-line-card {
    position: relative;
    border: 10px solid #ffd343;
    background-color: white;
}

.main {
    background-color: papayawhip;
}
</style>
  
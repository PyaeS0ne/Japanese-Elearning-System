<template>
    <div>
        <heading></heading>
        <v-container>
            <!-- Contact Us Page -->
            <div>
                <v-row><v-col cols="12" class="mt-12"></v-col></v-row>
                <v-row align="center" justify="center" width="680">
                    <v-col cols="12" sm="10">
                        <v-card class="elevation-6 mt-10">
                            <v-window>
                                <v-window-item :value="1">
                                    <v-row>
                                        <v-col cols="12" sm="6">
                                            <v-card-text class="mt-12">
                                                <h1 class="text-center">Contact Info</h1>
                                                <h5 class="text-center grey--text">Please connect us if you have any
                                                    question...
                                                </h5><br /><br /><br />
                                                    
                                                    <row class="text-center text-h5" align="center" justify="center">
                                                        <p><v-img width="400" height="200" src="../assets/sendingMail1.gif"></v-img></p>
                                                        <p><span><svg-icon type="mdi" :path="map"></svg-icon></span>Yangon, Myanmar</p>
                                                    </row>
                                                    <row class="text-center text-h5" align="center" justify="center">
                                                        
                                                        <p><svg-icon type="mdi" :path="mail"></svg-icon><a href="mailto:elearning.jels@gmail.com"><span ></span>elearning.jels@gmail.com</a></p>
                                                    </row><br/>
                                                    <row class="text-center text-h5" align="center" justify="center">
                                                       
                                                        <p> <span><svg-icon type="mdi" :path="phone"></svg-icon><a href="mailto:elearning.jels@gmail.com">+959 782323647</a></span></p>
                                                    </row>
                                                    <row>
                                                        
                                                        <ul class="sci">
                                                            <p>For more information :
                                                            <li class="sci-li"><a class="sci-li-a" href="http://facebook.com"><svg-icon type="mdi" :path="facebook"></svg-icon></a></li>
                                                            <li class="sci-li"><a class="sci-li-a" href="http://twitter.com"><svg-icon type="mdi" :path="twitter"></svg-icon></a></li>
                                                            <li class="sci-li"><a class="sci-li-a" href="http://instagram.com"><svg-icon type="mdi" :path="instagram"></svg-icon></a></li>
                                                        </p>
                                                        </ul>
                                                    
                                                    </row>
                                               
                                            </v-card-text>
                                        </v-col>
                                        <v-col cols="12" sm="6" class="backgroundColour">
                                            <v-row>
                                                <v-img height="500" width="600" src="../assets/map.jpg"></v-img>
                                            </v-row>
                                            <v-row class="text-center text-h5 mt-15" align="center" justify="center"><div>Here is our Google Map</div></v-row>
                                        </v-col>
                                    </v-row>
                                </v-window-item>
                            </v-window>
                        </v-card>
                    </v-col>
                </v-row>
            </div>
        </v-container>
    </div>
</template>
<script>
import heading from "../components/heading.vue";
import SvgIcon from "@jamescoyle/vue-icon";
import { mdiEmailMultipleOutline } from '@mdi/js';
import { mdiMapMarker } from '@mdi/js';
import { mdiGmail } from '@mdi/js';
import { mdiPhone } from '@mdi/js';
import { mdiFacebook } from '@mdi/js';
import { mdiTwitter } from '@mdi/js';
import { mdiInstagram } from '@mdi/js';
export default {
    name: "contactView",
    components: { heading, SvgIcon },
    data()
    {
        return {
            gmailUrl: 'https://www.gmail.com',
            path: mdiEmailMultipleOutline,
            map: mdiMapMarker,
            mail: mdiGmail,
            phone: mdiPhone,
            facebook: mdiFacebook,
            twitter: mdiTwitter,
            instagram: mdiInstagram,
        }
    }
}
</script>
<style scoped>
.sci{
    margin-top: 40px;
    display: flex;
}
.sci-li{
    list-style: none;
    margin-right: 15px;
}
.sci-li-a{
    color: #fff;
    font-size: 2em;
    color: #0a0000;
}
.backgroundColour{
    background-color: #ffd343;
}
</style>

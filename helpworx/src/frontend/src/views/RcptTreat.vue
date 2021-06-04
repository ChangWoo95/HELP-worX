<template>
    <v-container> 
        <sr-detail :srData="srData"></sr-detail>
        <rcpt-treat-detail :userNm="userData.userNm" :srData="srData"  ></rcpt-treat-detail>
    </v-container>
</template>

<script>
import axios from "axios"

import SrDetail from '../components/rcpt-treat/SrDetail'
import RcptTreatDetail from '../components/rcpt-treat/RcptTreatDetail'
export default {
    props:['userData'],
    name: 'RcptTreat',
    components: {
        SrDetail
        ,RcptTreatDetail
    },
    data:function(){
        return{
            srData:'',
        }
    },
    created:function(){
        
        const resourceHost = "http://13.125.164.214:9090"
        axios.get(`${resourceHost}/api/oper/srs/${this.$route.params.id}`)
        .then(response => {
            // console.log(response.data);
            this.srData = response.data;
            })
        .catch(error => console.log(error));

    }
}
</script>

<style>

</style>
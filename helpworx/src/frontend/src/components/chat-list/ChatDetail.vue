<template>
<v-row justify="end">
    <v-dialog
      v-model="dialog"
      persistent
      scrollable
      max-width="500"
      max-height="850"
    >
    <v-card>
        
        <v-card height="30">
            <v-row>
                <v-col>
                    <v-btn
                        fixed
                        color="green darken-1"
                        text
                        @click="closeDialog"
                    >
                        닫기
                    </v-btn> 
                </v-col>
                <v-spacer></v-spacer>
                <v-col>
                    <v-btn v-if="chttRoom!=null && chttRoom.status!='완료' && isConnected==true && isConnecting==false"
                        fixed
                        color="red darken-1"
                        text
                        @click="shutDownChttRoom"
                    >
                    문의 종료하기
                    </v-btn>    
                </v-col>
            </v-row>
        </v-card>

        <v-card-text style="height: 750px;">
            <v-card  elevation="0"  id="chat-page"  class="hidden">

                <v-card height="650" class="scroll"  id="convrBox">
                    <ul v-if="isConnected==true && this.chttRoom!=null" id="messageArea">
                        <li v-for ="chtt in this.chttRoom.chttList" :key="chtt.id">
                            <i v-if="chtt.user.userNm==userData.userNm" style="background-color:yellow;">{{chttRoom.sysNm}}팀 (나)</i>
                            <i v-else style="background-color:lightgrey;"> {{chtt.user.userNm}}</i>
                            {{chtt.chttCreatDate.substring(11,20)}}
                            <p>{{chtt.convrs}}</p>
                        </li>
                    </ul>
                </v-card>

                <v-card elevation="0"  v-if="isConnected==true && chttRoom!=null && chttRoom.sysNm!=null && isConnecting!=true &&chttRoom.status=='진행중' "  >
                    <v-text-field ref="sendMessageArea" @keyup.enter="sendMessage" type="text" v-model="message" placeholder="Type a message..." autocomplete="off" dense hide-details ></v-text-field>
                    <v-btn  x-small @click="sendMessage" >보내기</v-btn>
                </v-card>

                <v-card color="#b5C34c" elevation="0"  v-else-if="chttRoom!=null &&chttRoom.status!='진행중' "  >
                    <v-card-text > 완료된 문의(채팅)입니다.</v-card-text>
                </v-card>


                <v-card color="#e3323e" elevation="0"  v-else-if="chttRoom!=null && chttRoom.sysNm==null"  >
                    <v-card-text >고객이 시스템을 입력하지 않았습니다.</v-card-text>
                </v-card>

                <v-card color="#5CB85C" elevation="0"  v-else  >
                    <v-card-text >연결중 . . .</v-card-text>
                </v-card>

            </v-card>
        </v-card-text >
    </v-card>

    </v-dialog>
</v-row>
</template>

<script>
import axios from "axios"
import Stomp from 'webstomp-client'
import SockJS from 'sockjs-client'
import 'vue-datetime/dist/vue-datetime.css'

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

const getYYYYMMDDHHMMSS=function(time){
    const format = "YYYY-MM-DD HH:mm:ss"
    return  time.format(format)

}

const resourceHost = "http://13.125.164.214:9090"
const headers = {headers:{"Content-Type": "application/json"}            } 
var socket1;
var stompClient1 ;

export default {
    props:['clickedChttRoom','userData'],
    watch:{
        clickedChttRoom:function(newVal){
            if(newVal!=null){
                this.dialog=true;
                this.getChttRoomDataAndConnect(newVal);        
            }
        }

    },
    data(){
        return{
            username : '',
            isConnected:false,
            isConnecting:false,
            message:'',
            dialog:false,
            chttRoomList:null,
            chttRoom:null,
            systems:null,
            subscribeObject:null,
        }
    },
    methods:{
        closeDialog(){
            this.$emit('closeDialog');

            this.dialog=false;
            this.isConnected=false;
            if(stompClient1!=undefined){
                stompClient1.disconnect();
            }
            stompClient1=null;
        },
        sendShutDownMessage(){
            var chtt = {
                'convrs': "감사합니다"+ this.chttRoom.sysNm +"에 대한 상담이 종료되었습니다..",
                'chttCreatDate':this.getNow(),
                'chttRoom':this.chttRoom,
                'convrsType':'CHAT',
                'user' :  { 'id': 1 }
            };
            stompClient1.send("/app/chat.sendMessage", JSON.stringify(chtt));

        },
        setChttRoomStatusAndEndTim(){
            this.chttRoom.status="완료"
            this.chttRoom.chttEndTim = this.getNow()

            var requestBody = JSON.stringify(this.chttRoom);
            axios.post(`${resourceHost}/api/cust/chttrooms/${this.chttRoom.id}`,requestBody,headers)
            .then(res => {
                this.chttRoom =  res.data ;
            })
            .catch(error => console.log(error));
        },
        async shutDownChttRoom(){
            await this.sendShutDownMessage();
            await this.setChttRoomStatusAndEndTim();
            await this.closeDialog()
        },
        getNow(){
            return new Date ( getYYYYMMDDHHMMSS(this.$moment(new Date()))).getTime()
        },


        getChttRoomDataAndConnect(chttRoom){
            axios.get(`${resourceHost}/api/cust/chttrooms/${chttRoom.id}`)
            .then(res => {
                this.chttRoom =  res.data ;
                this.existedConnect();
            })
            .catch(error => console.log(error));
        },

        async existedConnect(){
            if(this.chttRoom.sysNm!=null){
            socket1 = await new SockJS("/ws");
            stompClient1 = await Stomp.over(socket1);
            stompClient1.heartbeat.outgoing = 10000;
            stompClient1.heartbeat.incoming = 0;     
            this.isConnecting=true
            stompClient1.connect({}, this.onExistedConnected, this.onError);
            }
        },

        async onExistedConnected(){
            await stompClient1.subscribe(`/topic/public/${this.chttRoom.id}`, this.onMessageReceived,this.onError); //연결되면 구독하기.
            this.isConnecting=false
            this.isConnected= true
        },
        
        onError:function(error){
            this.isConnecting=false
            this.isConnected  =false
            alert("에러",error)
        },

        sendMessage() {
            this.message=this.message.trim();
            if(this.message!='' && stompClient1){
                var chtt = {
                    'convrs': this.message,
                    'chttCreatDate':this.getNow(),
                    'chttRoom':this.chttRoom,
                    'convrsType':'CHAT',
                    'user' :  { 'id': this.userData.userId }
                };
            
            stompClient1.send("/app/chat.sendMessage", JSON.stringify(chtt));
                this.message=''
            }
            this.$refs["sendMessageArea"].$refs.input.focus()


        },
        onMessageReceived(res) {
            this.chttRoom.chttList.push(JSON.parse(res.body))
            var convrBox = document.getElementById ( "convrBox" )
            var messageArea = document.getElementById ( "messageArea" )
            convrBox.scrollTop = Math.floor( convrBox.scrollHeight)
            messageArea.scrollTop = Math.floor(messageArea.scrollHeight)
        },

        getAvatarColor(messageUserName) {
            var hash = 0;
            for (var i = 0; i < messageUserName.length; i++) {
                hash = 31 * hash + messageUserName.charCodeAt(i);
            }
            var index = Math.abs(hash % colors.length);
            return colors[index];
        }
    }
}
</script>

<style>
.scroll {
   overflow-y: scroll;
   margin-top:10px;
   width:650px;
}
.v-btn__content  .v-btn:not(.v-btn--round).v-size--default{
    width:10px;
    height:10px;
    position: absolute;
	right: 0;

}

</style>